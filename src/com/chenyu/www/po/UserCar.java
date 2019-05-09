package com.chenyu.www.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 每个user对应的购物车,装载好数据展示给前端
 * @author 86323
 */
public class UserCar {

    /**
     * 该用户的user类
     */
    private User user;

    /**
     * 购物车的商品集合
     */
    private List<Trade> trades;

    /**
     * 对应商品的商品数量
     */
    private List<Integer> tradeAmount;

    /**
     * 自动计算出商品价格
     */
    private double tradesPrice;

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(List<Integer> tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public double getTradesPrice() {
        return tradesPrice;
    }

    public void setTradesPrice() {
        int i;
        this.tradesPrice=0.0;
        for(i=0;i<this.trades.size();i++){
            tradesPrice+=trades.get(i).getTradePrice()*tradeAmount.get(i);
        }
    }
}
