package com.chenyu.www.dao.impl;

import com.chenyu.www.dao.TradeDao;
import com.chenyu.www.po.*;
import com.chenyu.www.util.Constant;
import com.chenyu.www.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author 86323
 */
public class TradeDaoImpl implements TradeDao {
    @Override
    public Boolean addTrade(Trade trade) {
        String sql = "insert into trade" +
                "(trade_price,trade_amount,trade_name,trade_introduce,trade_beenAmount,trade_kind," +
                "trade_photo,trade_user,trade_state)" +
                "values(?,?,?,?,?,?,?,?,?)";
        Object[] params = {trade.getTradePrice(), trade.getTradeAmount(), trade.getTradeName(), trade.getTradeIntroduce()
                , trade.getTradeBeenAmount(), trade.getTradeKind(), trade.getTradePhoto(), trade.getTradeUser(), trade.getTradeState()};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public Page<Trade> findTrade(Page<Trade> tradePage,int sort) {
        String sql="select * from trade where trade_state = '在售' and trade_amount>0";
        //0代表普通排序
        if(sort==0) {
            sql = sql+" limit ?,?";
        }
        //1代表价格升序
        if(sort== Constant.PRICE_ASCEND_SORT){
            sql=sql+" order by trade_price limit ?,?";
        }
        //2代表价格降序
        if(sort == Constant.PRICE_DESC_SORT){
            sql=sql+" order by trade_price desc limit ?,?";
        }
        //3代表商品已售数量升序
        if(sort==Constant.BEEN_AMOUNT_ASCEND){
            sql=sql+" order by trade_beenAmount limit ?,?";
        }
        //4代表商品已售数量降序
        if(sort==Constant.BEEN_AMOUNT_DESC){
            sql=sql+" order by trade_beenAmount desc limit ?,?";
        }
        Object[] params = {tradePage.getPageSize() * (tradePage.getCurrentPage() - 1), tradePage.getPageSize()};
        ArrayList<Trade> list = new ArrayList<>();
        ResultSet resultSet = DBUtil.executeQuery(sql, params);
        list=getTradeList(resultSet,list);
        tradePage.setList(list);
        return tradePage;

    }

    @Override
    public Trade loadTrade(Trade trade ,ResultSet resultSet) {
        try {
            trade.setTradeState(resultSet.getString("trade_state"));
            trade.setTradeName(resultSet.getString("trade_name"));
            trade.setTradeId(resultSet.getInt("trade_id"));
            trade.setTradePrice(resultSet.getDouble("trade_price"));
            trade.setTradeAmount(resultSet.getInt("trade_amount"));
            trade.setTradeIntroduce(resultSet.getString("trade_introduce"));
            trade.setTradeBeenAmount(resultSet.getInt("trade_beenAmount"));
            trade.setTradeKind(resultSet.getString("trade_kind"));
            trade.setTradePhoto(resultSet.getString("trade_photo"));
            trade.setTradeUser(resultSet.getString("trade_user"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trade;
    }

    @Override
    public int getTotalTrade() {
        String sql="select count(*) from trade where trade_state= '在售' ";
        return DBUtil.getTotalCount(sql,null);
    }

    @Override
    public int getNeedCheck() {
        String sql="select count(*) from trade where trade_state='审核' ";
        return DBUtil.getTotalCount(sql,null);
    }

    @Override
    public Page<Trade> adminFindCheck(Page<Trade> tradePage) {
        String sql = "select * from trade where trade_state= '审核' or trade_state='申请删除' and trade_amount>0 limit ?,?";
        Object[] params={(tradePage.getCurrentPage()-1)*tradePage.getPageSize(),tradePage.getPageSize()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        ArrayList<Trade> list=new ArrayList<>(tradePage.getPageSize());
        list=getTradeList(resultSet,list);
        tradePage.setList(list);
        return tradePage;
    }

    @Override
    public Trade findTradeById(int tradeId) {
        String sql="select * from trade where trade_id=?";
        Object[] params={tradeId};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        try {
            if(resultSet!= null&&resultSet.next()){
                Trade trade=new Trade();
                trade=loadTrade(trade,resultSet);
                return trade;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
        return null;
    }

    @Override
    public Boolean updateTradeState(int tradeId,String sqlTradeState) {
        String sql="update trade set trade_state=" +sqlTradeState+ " where trade_id=?";
        Object[] params={tradeId};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean removeTrade(int tradeId) {
        String sql="delete from trade where trade_id=?";
        Object[] params={tradeId};
        return DBUtil.executeUpdate(sql,params);
    }


    @Override
    public int getTotalByUser(String userId) {
        String sql="select count(*) from trade where trade_user=? ";
        Object[] params={userId};
        return DBUtil.getTotalCount(sql,params);
    }

    @Override
    public Page<Trade> findTradeByUser(Page<Trade> tradePage,String userId) {
        String sql = "trade_user=?";
        tradePage=vagueFind(sql,userId,tradePage);
        return tradePage;
    }

    @Override
    public ArrayList<Trade> getTradeList(ResultSet resultSet, ArrayList<Trade> list) {
        try {
            while (resultSet != null && resultSet.next()) {
                Trade trade = new Trade();
                trade = loadTrade(trade, resultSet);
                list.add(trade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
        return list;
    }

    @Override
    public Boolean updateTrade(Trade trade) {
        String sql="update trade set trade_name=?,trade_price=?,trade_amount=?,trade_introduce=?,trade_state='审核' where trade_id=?";
        Object[] params={trade.getTradeName(),trade.getTradePrice(),trade.getTradeAmount(),trade.getTradeIntroduce(),trade.getTradeId()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public Boolean updateTradePhoto(Trade trade) {
        String sql="update trade set trade_photo=?,trade_state='审核' where trade_id=?";
        Object[] params={trade.getTradePhoto(),trade.getTradeId()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public int getTotalVagueByUserAccount(String userAccount) {
        String sql="select count(*) from trade where trade_user like ?";
        Object[] params={"%"+userAccount+"%"};
        return DBUtil.getTotalCount(sql,params);
    }

    @Override
    public Page<Trade> findVagueByUserAccount(String userAccount, Page<Trade> tradePage) {
        String sql="trade_user like ?";
        tradePage=vagueFind(sql,userAccount,tradePage);
        return tradePage;
    }

    @Override
    public int getTotalVagueByTradeName(String tradeName) {
        String sql="select count(*) from trade where trade_name like ?";
        Object[] params={tradeName};
        return DBUtil.getTotalCount(sql,params);
    }

    @Override
    public Page<Trade> findVagueByTradeName(String tradeName, Page<Trade> page) {
        String sql="trade_name like ?";
        page=vagueFind(sql,tradeName,page);
        return page;
    }

    @Override
    public int getTotalByTradeKind(String tradeKind) {
        String sql="select count(*) from trade where trade_kind =?";
        Object[] params={tradeKind};
        return DBUtil.getTotalCount(sql,params);
    }

    @Override
    public Page<Trade> findByTradeKind(String tradeKind, Page<Trade> page) {
        String sql="trade_kind =?";
        page=vagueFind(sql,tradeKind,page);
        return page;
    }

    @Override
    public boolean addTradeToCar(Car car) {
        String sql=null;
        int thisTradeAmount=isHaveTrade(car);
        if(thisTradeAmount==0){
            sql="insert into car (car_amount,car_user,car_trade) values(?,?,?)";
        }
        else {
            sql="update car set car_amount=? where car_user=? and car_trade=?";
        }
        Object[] params={car.getCarAmount()+thisTradeAmount,car.getCarUser(),car.getCarTrade()};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public int isHaveTrade(Car car) {
        String sql="select * from car where car_user=? and car_trade=?";
        Object[] params={car.getCarUser(),car.getCarTrade()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        try {
            if(resultSet!=null &&resultSet.next()){
                return resultSet.getInt("car_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
       return 0;
    }

    @Override
    public UserCar findCar(UserCar userCar) {
        String sql="select * from car,trade where car_user=? and car_trade=trade_id";
        Object[] params={userCar.getUser().getUserAccount()};
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        //存储商品类
        ArrayList<Trade> list=new ArrayList<>();
        //存储购物车商品数量
        ArrayList<Integer> tradeAmount=new ArrayList<>();
        try {
            while(resultSet!=null &&resultSet.next()){
                Trade trade=new Trade();
                trade=loadTrade(trade,resultSet);
                list.add(trade);
                tradeAmount.add(resultSet.getInt("car_amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
        userCar.setTrades(list);
        userCar.setTradeAmount(tradeAmount);
        return userCar;
    }

    @Override
    public boolean removeTradeInCar(int tradeId, String userAccount) {
        String sql="delete from car where car_trade=? and car_user =?";
        Object[] params={tradeId,userAccount};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean removeOneAmount(Car car) {
        String sql="update car set car_amount=? where car_user=? and car_trade=? ";
        Object[] params={car.getCarAmount(),car.getCarUser(),car.getCarTrade()};
        return  DBUtil.executeUpdate(sql,params);
    }

    @Override
    public boolean removeAll(String userAccount) {
        String sql="delete from car where car_user =?";
        Object[] params={userAccount};
        return DBUtil.executeUpdate(sql,params);
    }

    @Override
    public void reduceTradeAmount(Trade trade) {
        //先要搜索出当前商品
        Trade newTrade=findTradeById(trade.getTradeId());
        String sql="update trade set trade_amount=?,trade_beenAmount=? where trade_id=?";
        Object[] params={newTrade.getTradeAmount()-trade.getTradeAmount(),
                newTrade.getTradeBeenAmount()+trade.getTradeAmount(),trade.getTradeId()};
        DBUtil.executeUpdate(sql, params);
    }

    @Override
    public boolean addCarToOrder(List<Order> list,User user) {
        StringBuilder sql=new StringBuilder("insert into orders " +
       "(orders_buyer,orders_seller,orders_state,orders_trade,orders_number,orders_time,orders_amount,orders_sum,orders_originSum) values");
        //用队列来储存购物车中的商品id
        Queue<Integer> tradeIds=new LinkedList<>();
        Object[] params=new Object[list.size()*9];
        //用来保存Object数组的下标
        int j=0;
        for(int i=0;i<list.size();i++){
            //第一次拼接sql时没有逗号
            if(i==0){
                sql.append("(?,?,?,?,?,?,?,?,?)");
            }
            else {
                sql.append(",(?,?,?,?,?,?,?,?,?)");
            }
            //获取商品id到tradeIds
            tradeIds.offer(list.get(i).getOrderTradeId());
        }
        //商品价格的队列
        Queue<Double> tradePrices=new LinkedList<>();
        tradePrices=findTradesId(tradeIds);
        for (Order order : list) {
            params[j++] = order.getOrderBuyer();
            params[j++] = order.getOrderSeller();
            params[j++] = order.getOrderState();
            params[j++] = order.getOrderTradeId();
            params[j++] = order.getOrderNumber();
            Timestamp timestamp = new Timestamp(order.getOrderTime());
            params[j++] = timestamp;
            params[j++] = order.getOrderAmount();
            user.setUserPoint(user.getUserPoint()+order.getOrderAmount()*tradePrices.peek()/Constant.GET_POINT);
            //因为不使用积分，订单实付款和应付款相同
            params[j++] = order.getOrderAmount() * tradePrices.peek();
            params[j++] = order.getOrderAmount() * tradePrices.poll();
        }
        return DBUtil.executeUpdate(String.valueOf(sql),params);
    }

    @Override
    public Queue<Double> findTradesId(Queue<Integer> tradesId) {
        Queue<Double> tradePrices=new LinkedList<>();
        StringBuilder sqlBuilder=new StringBuilder(" select * from trade where trade_id=? ");
        Object[] params=new Object[tradesId.size()];
        int j=0;
        //存储大小，防止出队的时候大小改变
        int size=tradesId.size();
        for(int i=0;i<size;i++){
            if(i!=0){
                sqlBuilder.append("or trade_id=?");
            }
            params[j++]=tradesId.poll();
        }
        String sql= String.valueOf(sqlBuilder);
        ResultSet resultSet=DBUtil.executeQuery(sql,params);
        try {
            while (resultSet!=null&&resultSet.next()){
                tradePrices.offer(resultSet.getDouble("trade_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(resultSet,DBUtil.pstmt,DBUtil.connection);
        }
        return tradePrices;
    }


    @Override
    public Page<Trade> vagueFind(String lineName, String lineDetails, Page<Trade> tradePage){
        //拼接where后的内容
        String sql="select * from trade where " +
                lineName +
                " limit ?,?";
        Object[] prams={lineDetails,(tradePage.getCurrentPage() - 1) * tradePage.getPageSize(), tradePage.getPageSize()};
        ResultSet resultSet=DBUtil.executeQuery(sql,prams);
        ArrayList<Trade> list=new ArrayList<>(tradePage.getPageSize());
        list=getTradeList(resultSet,list);
        tradePage.setList(list);
        return tradePage;
    }
}


