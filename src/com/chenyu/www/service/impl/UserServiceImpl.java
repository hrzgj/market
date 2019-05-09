package com.chenyu.www.service.impl;

import com.chenyu.www.dao.TradeDao;
import com.chenyu.www.dao.UserDao;
import com.chenyu.www.dao.impl.TradeDaoImpl;
import com.chenyu.www.dao.impl.UserDaoImpl;
import com.chenyu.www.po.*;

import com.chenyu.www.service.UserService;
import com.chenyu.www.util.Constant;
import com.chenyu.www.util.PasswordEndrypt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 * @author 86323
 */
public class UserServiceImpl implements UserService {
    private static UserDao userDao=new UserDaoImpl();

    @Override
    public Boolean login(User user) {
        return userDao.login(user);
    }

    @Override
    public User informationInput(User user)  {
            return  userDao.informationInput(user);
    }


    @Override
    public Boolean register(User user) {
        user.setUserIdentity(Constant.USER_IDENTITY2);
        userDao.register(user);
        userDao.informationInput(user);
        return true;
        }


    @Override
    public String findPassword(User user) {
        if(!userDao.isHaveAccountAndIdCard(user)){
            return "账户身份证不匹配";
        }
        else {
            userDao.updatePassword(user);
            return "找回密码成功";
        }
    }

    @Override
    public User updateNameAddressAndPhone(User user) {
        if(!userDao.updateUser(user)){
            user=userDao.informationInput(user);
            return user;
        }
        return null;
    }

    @Override
    public Boolean updatePassword(User user) {
        userDao.updatePassword(user);
        return true;
    }

    @Override
    public Boolean judgePassword(User user, String oldPassword) {
        return PasswordEndrypt.getMD5(oldPassword).equals(user.getUserPassword());
    }

    @Override
    public int getTotalCount() {
        return userDao.totalCount();
    }

    @Override
    public Page<User> getSomeUser(Page<User> page) {
        return userDao.findAllUser(page);
    }

    @Override
    public Boolean isHaveAccount(String userAccount) {
        return userDao.isHaveAccount(userAccount);
    }

    @Override
    public Boolean isHaveIdCard(String userIdCard) {
        return userDao.isHaveIdCard(userIdCard);
    }

    @Override
    public Boolean updateUserPhoto(User user) {
        return userDao.updateUserPhoto(user);
    }

    @Override
    public Page<User> findUserByVagueAccount(String userAccount,Page<User> page) {
        page=userDao.findUserByVagueAccount(userAccount,page);
        return page;
    }

    @Override
    public int getTotalVagueUser(String userAccount) {
        return userDao.getTotalVagueUser(userAccount);
    }

    @Override
    public boolean banUserSell(User user) {
        return userDao.banUserSell(user,Constant.USER_CANNOT);
    }

    @Override
    public boolean appealSell(User user) {
        return userDao.appealSell(user);
    }

    @Override
    public boolean allowSell(String userAccount) {
        return userDao.allowSell(userAccount);
    }

    @Override
    public double addOrder(Order order) {
        //设置下单时间
        order.setOrderTime(System.currentTimeMillis());
        //设置订单状态为已发送
        order.setOrderState(Constant.ORDER_STATE_ONE);
        //设置订单号
        order.setOrderNumber();
        return userDao.addOrderNoPoint(order);
    }

    @Override
    public double addOneOrderWithPoint(Order order) {
        order.setOrderTime(System.currentTimeMillis());
        order.setOrderNumber();
        order.setOrderState(Constant.ORDER_STATE_ONE);
        order.setOrderNumber();
        return userDao.addOneOrderWithPoint(order);
    }

    @Override
    public UserOrder findSellerOrder(UserOrder userOrder) {
        //如果用户以出售者查看订单，则需要得到
        return userDao.findOrder(userOrder,"orders_seller","orders_buyer","orders_sellDelete");
    }

    @Override
    public boolean sellDeliverOrder(Order order, UserOrder userOrder) {
        TradeDao tradeDao=new TradeDaoImpl();
        Trade trade=new Trade();
        int i=updateShowOrder(userOrder.getOrderList(),order.getOrderId(), Constant.ORDER_STATE_TWO);
        //将商品出售的数目封装到trade
        trade.setTradeAmount(userOrder.getOrderList().get(i).getTradesAmount());
        //将商品id封装到trade里
        trade.setTradeId(userOrder.getOrderList().get(i).getTrades().getTradeId());
        tradeDao.reduceTradeAmount(trade);
        return userDao.updateOrderState(order,Constant.ORDER_STATE_TWO);
    }

    @Override
    public int updateShowOrder(List<OrderShow> list,int orderId,String orderState) {
        int i;
        //改变对应商品id的状态
        for(i=0;i<list.size();i++){
            if(list.get(i).getOrderId()==orderId){
                list.get(i).setOrderState(orderState);
                break;
            }
        }
        return i;
    }

    @Override
    public boolean sellRefuseOrder(Order order) {
        return userDao.updateOrderState(order,Constant.ORDER_STATE_FOUR);
    }

    @Override
    public UserOrder findBuyerOrder(UserOrder userOrder) {
        return userDao.findOrder(userOrder,"orders_buyer","orders_seller","orders_buyDelete");
    }

    @Override
    public boolean buyerTakeOrder(Order order) {
        return userDao.updateOrderState(order,Constant.ORDER_STATE_THREE);
    }

    @Override
    public boolean deleteOrder(Order order,User user) {
        //返回应该用户购买商品时使用的积分
        double userPoint=userDao.checkOrderSumIsSame(order);
        BigDecimal point=BigDecimal.valueOf(userPoint);
        //如果是小于等于0，说明没有使用积分购买该订单，只消去将这次购买所得积分，然后直接删除订单
        if(point.compareTo(BigDecimal.ZERO)<=0){
            //删去订单前，用户剩余积分=用户现有积分-该订单应付款/11(此时购买所得积分)
            BigDecimal orderOriginSum=BigDecimal.valueOf(order.getOrderOriginSum()).divide(BigDecimal.valueOf(Constant.GET_POINT),3, RoundingMode.DOWN);
            BigDecimal sum=BigDecimal.valueOf(user.getUserPoint()).subtract(orderOriginSum);
            user.setUserPoint(sum.doubleValue());
            userDao.updateUserPoint(user);
            return userDao.deleteOrder(order);
        }
        else {
            //否则，用户剩余积分=返还购买该订单使用的积分+用户现有积分-该订单实付款/11(此时购买所得积分)
            user.setUserPoint(userPoint+user.getUserPoint()-order.getOrderOriginSum()/Constant.GET_POINT);
            //更新数据库对应用户的积分
            userDao.updateUserPoint(user);
            //最后删除订单
            return userDao.deleteOrder(order);
        }
    }

    @Override
    public UserOrder removerShowOrder(UserOrder userOrder,int orderId) {
        for(int i=0;i<userOrder.getOrderList().size();i++){
            if(userOrder.getOrderList().get(i).getOrderId()==orderId){
                userOrder.getOrderList().remove(i);
                break;
            }
        }
        return userOrder;
    }

    @Override
    public boolean sellerRemoveOrder(Order order) {
        //为订单出售者删除订单
        return userDao.removerOrder(order,"orders_sellDelete");
    }

    @Override
    public boolean buyerRemoverOrder(Order order) {
        return userDao.removerOrder(order,"orders_buyDelete");
    }

    @Override
    public UserOrder setNullUserAccount(UserOrder userOrder, int orderId) {
        for(int i=0;i<userOrder.getOrderList().size();i++){
            if(userOrder.getOrderList().get(i).getOrderId()==orderId){
                userOrder.getOrderList().get(i).getUser().setUserAccount(null);
                break;
            }
        }
        return userOrder;
    }

    @Override
    public UserOrder buyerFindSendOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_buyer=?",Constant.ORDER_STATE_ONE);
    }

    @Override
    public UserOrder buyerFindDeliverOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_buyer=?",Constant.ORDER_STATE_TWO);
    }

    @Override
    public UserOrder buyerFindTakeOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_buyer=?",Constant.ORDER_STATE_THREE);
    }

    @Override
    public UserOrder buyerFindRefuseOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_buyer=?",Constant.ORDER_STATE_FOUR);
    }

    @Override
    public UserOrder sellerFindSendOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_seller=?",Constant.ORDER_STATE_ONE);
    }

    @Override
    public UserOrder sellerFindDeliverOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_seller=?",Constant.ORDER_STATE_TWO);
    }

    @Override
    public UserOrder sellerFindTakeOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_seller=?",Constant.ORDER_STATE_THREE);
    }

    @Override
    public UserOrder sellerFindRefuseOrder(UserOrder userOrder) {
        return userDao.findLimitOrder(userOrder,"orders_seller=?",Constant.ORDER_STATE_FOUR);
    }

    @Override
    public boolean buyCommentOrder(Order order) {
        return userDao.buyCommentOrder(order);
    }

    @Override
    public boolean sellReplyOrder(Order order) {
        return userDao.sellReplyOrder(order);
    }

    @Override
    public boolean sellExportFile(UserOrder userOrder, String fileName) {
        return userDao.sellExportFile(userOrder,fileName);
    }

    @Override
    public boolean buyExportFile(UserOrder userOrder, String fileName) {
        return userDao.buyExportFile(userOrder,fileName);
    }


}
