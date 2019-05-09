package com.chenyu.www.dao.impl;

import com.chenyu.www.dao.TradeDao;
import com.chenyu.www.dao.UserDao;
import com.chenyu.www.po.*;
import com.chenyu.www.util.Constant;
import com.chenyu.www.util.DBUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author 86323
 */
public class UserDaoImpl implements UserDao {


    @Override
    public Boolean login(User user)  {
        String sql="select * from user where user_account=? and user_password=?";
        Object[] params={user.getUserAccount(),user.getUserPassword()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        boolean judge=DBUtil.isHaveResultSet(resultSet);
        return judge;
    }

    @Override
    public User informationInput(User user)  {
        String sql="select * from user where user_account=?";
        Object[] params={user.getUserAccount()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        try {
            while (resultSet!=null &&resultSet.next()){
                user=this.load(user,resultSet);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }

        return null;
    }

    @Override
    public User load(User user, ResultSet resultSet) throws SQLException {
        user.setUserAccount(resultSet.getString("user_account"));
        user.setUserPassword(resultSet.getString("user_password"));
        user.setUserPhone(resultSet.getString("user_phone"));
        user.setUserPhoto(resultSet.getString("user_photo"));
        user.setUserAddress(resultSet.getString("user_address"));
        user.setUserCan(resultSet.getString("user_can"));
        user.setUserCar(resultSet.getInt("user_car"));
        user.setUserName(resultSet.getString("user_name"));
        user.setUserIdentity(resultSet.getString("user_identity"));
        user.setUserIdCard(resultSet.getString("user_idCard"));
        user.setUserGrade(resultSet.getInt("user_grade"));
        user.setUserPoint(resultSet.getDouble("user_point"));
        user.setUserStar(resultSet.getInt("user_star"));
        user.setUserReason(resultSet.getString("user_reason"));
        user.setUserAppeal(resultSet.getString("user_appeal"));
        return user;
    }

    @Override
    public Boolean isHaveAccount(String userAccount) {
        String sql="select * from user where user_account=?";
        Object[] params={userAccount};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        return DBUtil.isHaveResultSet(resultSet);
    }

    @Override
    public Boolean isHaveIdCard(String userIdCard) {
        String sql="select * from user where user_idCard=?";
        Object[] params={userIdCard};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        return DBUtil.isHaveResultSet(resultSet);
    }

    @Override
    public void register(User user) {
        String sql="insert into user"+
            "(user_name,user_account,user_phone,user_password,user_idCard,user_photo,user_identity,user_address)" +
             "values(?,?,?,?,?,?,?,?)";
        Object[] params={user.getUserName(),user.getUserAccount(),user.getUserPhone(),
        user.getUserPassword(),user.getUserIdCard(),user.getUserPhoto(),user.getUserIdentity(),user.getUserAddress()};
        DBUtil.executeUpdate(sql, params);
    }

    @Override
    public void updatePassword(User user) {
        String sql="update user set user_password=? where user_account=?";
        Object[] params={user.getUserPassword(),user.getUserAccount()};
        DBUtil.executeUpdate(sql,params);
    }

    @Override
    public Boolean isHaveAccountAndIdCard(User user) {
        String sql="select * from user where user_account=? and user_idCard=?";
        Object[] params={user.getUserAccount(),user.getUserIdCard()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        return DBUtil.isHaveResultSet(resultSet);
    }

    @Override
    public Boolean updateUser(User user) {
        String sql="update user set user_phone=?,user_name=?,user_address=? where user_account=?";
        Object[] params={user.getUserPhone(),user.getUserName(),user.getUserAddress(),user.getUserAccount()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public int totalCount() {
        String sql="select count(*) from user";
        return DBUtil.getTotalCount(sql,null);
    }

    @Override
    public Page<User> findAllUser(Page<User> page) {
        String sql="select * from user limit ?,?";
        //第一个参数为从第几行开始（行数从0开始算）
        //第二个参数为有几行
        Object[] params={(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        ArrayList<User> list=new ArrayList<>();
            try {
                while (resultSet != null && resultSet.next()) {
                    User user=new User();
                    user=load(user,resultSet);
                    list.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
            }
        page.setList(list);
        return page;
    }

    @Override
    public Boolean updateUserPhoto(User user) {
        String sql="update user set user_photo=? where user_account=?";
        Object[] params={user.getUserPhoto(),user.getUserAccount()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public Page<User> findUserByVagueAccount(String userAccount,Page<User> page) {
        String sql="select * from user where user_account like ? limit ?,?";
        Object[] params={"%"+userAccount+"%",(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        ArrayList<User> list=new ArrayList<>(page.getPageSize());
        try {
            if(resultSet!=null) {
                while (resultSet.next()) {
                    User user = new User();
                    user = load(user, resultSet);
                    list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
        page.setList(list);
        return page;
    }

    @Override
    public int getTotalVagueUser(String userAccount) {
        String sql="select count(*) from user where user_account like ?";
        Object[] params={"%"+userAccount+"%"};
        return DBUtil.getTotalCount(sql,params);
    }

    @Override
    public boolean banUserSell(User user,String sqlUserCan) {
        String sql="update user set user_can='"+sqlUserCan+"' ,user_reason=?,user_appeal=null where user_account=?";
        Object[] params={user.getUserReason(),user.getUserAccount()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean appealSell(User user) {
        String sql="update user set user_appeal=? where user_account=?";
        Object[] params={user.getUserAppeal(),user.getUserAccount()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean allowSell(String userAccount) {
        String sql="update user set user_can='" +Constant.USER_CAN +"' where user_account=?";
        Object[] params={userAccount};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean updateUserPoint(User user) {
        String sql="update user set user_point=? where user_account=?";
        Object[] params={user.getUserPoint(),user.getUserAccount()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean addOrder(Order order) {
        String sql="insert into orders (orders_buyer,orders_seller,orders_state,orders_trade," +
                "orders_number,orders_time,orders_amount,orders_sum,orders_originSum) " +
                "values(?,?,?,?,?,?,?,?,?)";
        Timestamp timestamp=new Timestamp(order.getOrderTime());
        Object[] params={order.getOrderBuyer(),order.getOrderSeller(),order.getOrderState(),order.getOrderTradeId(),
                order.getOrderNumber(),timestamp,order.getOrderAmount(),order.getOrderSum(),order.getOrderOriginSum()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public double addOrderNoPoint(Order order) {
        //先根据商品id得到商品信息
        String findSql="select * from trade where trade_id=?";
        Object[] p={order.getOrderTradeId()};
        ResultSet resultSet=DBUtil.executeQuery(findSql,p);
        //用户剩余的积分,此时orderSum储存的是用户当前的积分，取出该值
        double userPointSurplus =order.getOrderSum();
        //当前订单总价
        double sum=0.0;
        try {
            if(resultSet!=null &&resultSet.next()){
                //将实付款和应付款和出售用户账户放入订单中，不使用积分的情况下实付款和应付款相同
                sum=resultSet.getDouble("trade_price")*order.getOrderAmount();
                order.setOrderSum(sum);
                order.setOrderOriginSum(sum);
                order.setOrderSeller(resultSet.getString("trade_user"));
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setUserAccount(order.getOrderBuyer());
        //积分为此时购买金钱的11分之一与当前积分的和
        userPointSurplus+=sum/11;
        user.setUserPoint(userPointSurplus);
        updateUserPoint(user);
        //增加订单
        addOrder(order);
        return userPointSurplus;
    }

    @Override
    public double addOneOrderWithPoint(Order order) {
        //要先找到
        String findSql="select * from trade where trade_id=?";
        Object[] p={order.getOrderTradeId()};
        ResultSet resultSet=DBUtil.executeQuery(findSql,p);
        //商品价格
        double tradePrice=0.0;
        //用户剩余积分
        double userPointSurplus=0.0;
        try {
            if(resultSet!=null &&resultSet.next()){
               tradePrice=resultSet.getDouble("trade_price");
               //商品实际付款=商品单价*商品数量
               order.setOrderOriginSum(tradePrice*order.getOrderAmount());
               //订单最后应付款=实付款-用户积分/10，此时的orderSum存的是用户现有积分
               double sum=order.getOrderOriginSum()-order.getOrderSum()/10;
               BigDecimal bigDecimal=BigDecimal.valueOf(sum);
               //如果小于0，即积分比较多，将orderSum设为0，并存储剩余积分
               if(bigDecimal.compareTo(BigDecimal.ZERO)<0){
                   order.setOrderSum(0);
                   //剩余积分
                   userPointSurplus=-sum*10;
               }
               else {
                   //如果大于0，则清空积分
                   order.setOrderSum(sum);
               }
               //再加上这次购买得到的积分,购买所得积分=实付款/11
               userPointSurplus+=tradePrice*order.getOrderAmount()/Constant.GET_POINT;
                //得到商品的出售者
                order.setOrderSeller(resultSet.getString("trade_user"));
                resultSet.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //结束后增加订单
        addOrder(order);
        User user=new User();
        user.setUserAccount(order.getOrderBuyer());
        user.setUserPoint(userPointSurplus);
        updateUserPoint(user);
        return userPointSurplus;
    }

    @Override
    public UserOrder findOrderByState(UserOrder userOrder, String sqlOrderUser, String sqlOrderState) {
        Object[] params={userOrder.getUser().getUserAccount()};
        String sql="select * from orders,trade,user where "
                +sqlOrderUser+ " and orders_trade=trade_id and trade_user=user_account and "+sqlOrderState;
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        LinkedList<OrderShow> linkedList=new LinkedList<>();
        linkedList=loadLinkList(linkedList,resultSet);
        userOrder.setOrderList(linkedList);
        return userOrder;
    }

    @Override
    public UserOrder findOrder(UserOrder userOrder,String sqlOrderUser,String sqlOrderAccount,String sqlOrderDelete) {
        String sql="select * from orders,trade,user where "
                +sqlOrderUser+ "=? and orders_trade=trade_id and " +sqlOrderAccount+ "=user_account and "+
                sqlOrderDelete +"!=1";
        Object[] params={userOrder.getUser().getUserAccount()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        LinkedList<OrderShow> linkedList=new LinkedList<>();
        linkedList=loadLinkList(linkedList,resultSet);
        userOrder.setOrderList(linkedList);
        return userOrder;
    }

    @Override
    public LinkedList<OrderShow> loadLinkList(LinkedList<OrderShow> linkedList, ResultSet resultSet) {
        try {
            TradeDao tradeDao=new TradeDaoImpl();
            while (resultSet!=null &&resultSet.next()){
                OrderShow orderShow=new OrderShow();
                User user=new User();
                user=load(user,resultSet);
                orderShow.setUser(user);
                orderShow.setOrderNumber(resultSet.getString("orders_number"));
                orderShow.setOrderState(resultSet.getString("orders_state"));
                String time=resultSet.getString("orders_time");
                orderShow.setOrderTime(time.substring(0,time.lastIndexOf(":")));
                orderShow.setTradesAmount(resultSet.getInt("orders_amount"));
                orderShow.setOrderId(resultSet.getInt("orders_id"));
                orderShow.setOrderComment(resultSet.getString("orders_comment"));
                orderShow.setOrderReply(resultSet.getString("orders_reply"));
                orderShow.setOrderStar(resultSet.getInt("orders_star"));
                orderShow.setOrderOriginSum(resultSet.getDouble("orders_originSum"));
                orderShow.setOrderSum(resultSet.getDouble("orders_sum"));
                Trade trade=new Trade();
                trade=tradeDao.loadTrade(trade,resultSet);
                orderShow.setTrades(trade);
                linkedList.add(orderShow);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
        return linkedList;
    }

    @Override
    public boolean updateOrderState(Order order,String orderState) {
        String sql="update orders set orders_state='" +orderState+"' where orders_id=?";
        Object[] params={order.getOrderId()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean removerOrder(Order order,String sqlOrderUser) {
        //先将订单的对应用户申请删除设为1
        String sql="update orders set "+sqlOrderUser+"=1 where orders_id=?";
        Object[] params={order.getOrderId()};
        DBUtil.executeUpdate(sql,params);
        //如果该订单的出售者和购买者都为null，就从数据库永久删除该订单
        sql="delete from orders where orders_id=? and orders_buyDelete=1 and orders_sellDelete=1";
        //返回是否删除成功
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean deleteOrder(Order order) {
        String sql="delete from orders where orders_id=?";
        Object[] params={order.getOrderId()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public double checkOrderSumIsSame(Order order) {
        String sql="select * from orders where orders_id=?";
        Object[] params={order.getOrderId()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        try {
            if(resultSet!=null &&resultSet.next()){
                double orderSum=resultSet.getDouble("orders_sum");
                double orderOriginSum=resultSet.getDouble("orders_originSum");
                //设置当前订单的应付款，以便service层查出购买原订单获得的积分
                order.setOrderOriginSum(orderOriginSum);
                BigDecimal sumBig=BigDecimal.valueOf(orderSum);
                BigDecimal originSumBig=BigDecimal.valueOf(orderOriginSum);
                //如果相等，随意返还一个负数
                if(sumBig.compareTo(originSumBig)==0) {
                    return -10.0;
                }
                //否则返还所用积分
                else {
                    BigDecimal userUserPoint=originSumBig.subtract(sumBig);
                    userUserPoint=userUserPoint.multiply(BigDecimal.valueOf(10));
                    return userUserPoint.doubleValue();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
        return 0;
    }

    @Override
    public UserOrder findLimitOrder(UserOrder order,String sqlOrderUser,String orderState) {
        String sql="orders_state="+"'"+orderState+"'";
        order=findOrderByState(order,sqlOrderUser,sql);
        return order;
    }

    @Override
    public boolean buyCommentOrder(Order order) {
        String sql="update orders set orders_comment=?,orders_star=? where orders_id=?";
        Object[] params={order.getOrderComment(),order.getOrderStar(),order.getOrderId()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean sellReplyOrder(Order order) {
        String sql="update orders set orders_reply=? where orders_id=?";
        Object[] params={order.getOrderReply(),order.getOrderId()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean sellExportFile(UserOrder userOrder, String fileName) {
        String sql="SELECT * FROM orders,trade WHERE orders_seller=? AND orders_trade=trade_id " +
                " AND orders_sellDelete!=1 INTO OUTFILE'D:/market/sql/" +fileName+
                "' FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\r\\n'";
        Object[] params={userOrder.getUser().getUserAccount()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean buyExportFile(UserOrder userOrder, String fileName) {
        String sql="SELECT * FROM orders,trade WHERE orders_buyer=? AND orders_trade=trade_id " +
                " AND orders_buyDelete!=1 INTO OUTFILE'D:/market/sql/" +fileName+
                "' FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\r\\n'";
        Object[] params={userOrder.getUser().getUserAccount()};
        return DBUtil.executeUpdate(sql,params);
    }


}
