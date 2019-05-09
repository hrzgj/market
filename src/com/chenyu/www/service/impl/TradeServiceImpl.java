package com.chenyu.www.service.impl;

import com.chenyu.www.dao.TradeDao;
import com.chenyu.www.dao.UserDao;
import com.chenyu.www.dao.impl.TradeDaoImpl;
import com.chenyu.www.dao.impl.UserDaoImpl;
import com.chenyu.www.po.*;
import com.chenyu.www.service.TradeService;
import com.chenyu.www.util.Constant;

import java.util.ArrayList;


/**
 * @author 86323
 */
public class TradeServiceImpl implements TradeService {
    private static TradeDao tradeDao=new TradeDaoImpl();

    @Override
    public Boolean addTrade(Trade trade) {
        return tradeDao.addTrade(trade);
    }

    @Override
    public Page<Trade> findTrade(Page<Trade> tradePage,int sort) {
        return tradeDao.findTrade(tradePage,sort);
    }

    @Override
    public int getTotalTrade() {
        return tradeDao.getTotalTrade();
    }

    @Override
    public Page<Trade> adminFindCheck(Page<Trade> tradePage) {
        return  tradeDao.adminFindCheck(tradePage);
    }

    @Override
    public int getNeedCheck() {
        return tradeDao.getNeedCheck();
    }

    @Override
    public Trade findTradeByID(int tradeId) {
        return tradeDao.findTradeById(tradeId);
    }

    @Override
    public Boolean updateTradeState(int tradeId) {
        String sqlTradeState="'"+Constant.TRADE_STATE_TWO+"'";
        return tradeDao.updateTradeState(tradeId,sqlTradeState);
    }

    @Override
    public boolean adminSendBack(int tradeId) {
        String sqlTradeState = "'" + Constant.TRADE_STATE_ONE + "'";
        return tradeDao.updateTradeState(tradeId, sqlTradeState);
    }

    @Override
    public boolean removeTrade(int tradeId) {
        return tradeDao.removeTrade(tradeId);
    }

    @Override
    public boolean readyRemoveTrade(int tradeId) {
        String sqlTradeState="'"+Constant.TRADE_STATE_THREE+"'";
        return tradeDao.updateTradeState(tradeId,sqlTradeState);
    }

    @Override
    public int getTotalByUser(String userId) {
        return tradeDao.getTotalByUser(userId);
    }

    @Override
    public Page<Trade> findTradeByUser(Page<Trade> tradePage, String userID) {
        return tradeDao.findTradeByUser(tradePage,userID);
    }

    @Override
    public Boolean updateTrade(Trade trade) {
        return tradeDao.updateTrade(trade);
    }

    @Override
    public Boolean updateTradePhoto(Trade trade) {
        return tradeDao.updateTradePhoto(trade);
    }

    @Override
    public int getTotalVagueByUserAccount(String userAccount) {
        return tradeDao.getTotalVagueByUserAccount(userAccount);
    }

    @Override
    public Page<Trade> findVagueByUserAccount(String userAccount, Page<Trade> page) {
        userAccount="%"+userAccount+"%";
        return tradeDao.findVagueByUserAccount(userAccount,page);
    }

    @Override
    public int getTotalVagueByTradeName(String tradeName) {
        tradeName="%"+tradeName+"%";
        return tradeDao.getTotalVagueByTradeName(tradeName);
    }

    @Override
    public Page<Trade> findVagueByTradeName(String tradeName, Page<Trade> page) {
        tradeName="%"+tradeName+"%";
        return tradeDao.findVagueByTradeName(tradeName,page);
    }

    @Override
    public int getTotalByTradeKind(String tradeKind) {
        return tradeDao.getTotalByTradeKind(tradeKind);
    }

    @Override
    public Page<Trade> findByTradeKind(String tradeKind, Page<Trade> page) {
        return tradeDao.findByTradeKind(tradeKind,page);
    }

    @Override
    public boolean isRealTradeKind(String tradeKind) {
        if(tradeKind.equals(Constant.TRADE_KIND_FOUR)||tradeKind.equals(Constant.TRADE_KIND_FIVE)||tradeKind.equals(Constant.TRADE_KIND_ONE)
                ||tradeKind.equals(Constant.TRADE_KIND_TWO)||tradeKind.equals(Constant.TRADE_KIND_THREE)){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean addTradeToCar(Car car) {
        return tradeDao.addTradeToCar(car);
    }

    @Override
    public UserCar findCar(UserCar userCar) {
        return tradeDao.findCar(userCar);
    }

    @Override
    public boolean removeTrade(int tradeId, String userAccount) {
        return tradeDao.removeTradeInCar(tradeId,userAccount);
    }

    @Override
    public UserCar removeTrade(int tradeId, UserCar userCar) {
        for (int i = 0; i < userCar.getTrades().size(); i++) {
            if (userCar.getTrades().get(i).getTradeId() == tradeId) {
                userCar.getTrades().remove(i);
                userCar.getTradeAmount().remove(i);
                break;
            }
        }
        return userCar;
    }

    @Override
    public boolean removeOneAmount(int tradeId, String userAccount) {
        Car car=new Car();
        car.setCarUser(userAccount);
        car.setCarTrade(tradeId);
        int tradeAmount=tradeDao.isHaveTrade(car);
        //如果数量大于1就移除一个数量，否则直接移除该商品
        if(tradeAmount>1){
            car.setCarAmount(tradeAmount-1);
            return tradeDao.removeOneAmount(car);
        }else {
            return tradeDao.removeTradeInCar(tradeId,userAccount);
        }
    }

    @Override
    public UserCar removeOneAmount(int tradeId, UserCar userCar) {
        for(int i=0;i<userCar.getTrades().size();i++){
            if(userCar.getTrades().get(i).getTradeId()==tradeId){
                //如果该商品数量为1
                if(userCar.getTradeAmount().get(i)==1){
                    userCar.getTradeAmount().remove(i);
                    userCar.getTrades().remove(i);
                    break;
                }
                else {
                    userCar.getTradeAmount().set(i,userCar.getTradeAmount().get(i)-1);
                    break;
                }
            }
        }
        return userCar;
    }

    @Override
    public boolean removeAll(String userAccount) {
        return tradeDao.removeAll(userAccount);
    }

    @Override
    public boolean addCarToOrder(UserCar userCar,User user) {
       ArrayList<Order> list=new ArrayList<>(userCar.getTrades().size());
       for(int i=0;i<userCar.getTrades().size();i++){
           //设置各类属性：订单商品id，订单状态，订单购买用户账户，订单出售用户账户，订单商品数量，下单时间，订单号
           Order order=new Order();
           order.setOrderTradeId(userCar.getTrades().get(i).getTradeId());
           order.setOrderState(Constant.ORDER_STATE_ONE);
           order.setOrderBuyer(userCar.getUser().getUserAccount());
           order.setOrderSeller(userCar.getTrades().get(i).getTradeUser());
           order.setOrderAmount(userCar.getTradeAmount().get(i));
           order.setOrderTime(System.currentTimeMillis());
           order.setOrderNumber();
           list.add(order);
       }
       tradeDao.addCarToOrder(list,user);
       UserDao userDao=new UserDaoImpl();
       return userDao.updateUserPoint(user);
    }
}

