package com.chenyu.www.po;

import java.util.Date;
/**
 * 一个订单，装载好传给前端
 * @author 86323
 */
public class OrderShow {
    /**
     * 商品
     */
    private Trade trades;
    /**
     * 与商品对应的订单商品数量
     */
    private int tradesAmount;
    /**
     * 如果用户作为购买者，这里设置为商品出售用户
     * 如果用户为出售者，这里设置为商品购买者
     */
    private User user;
    /**
     * 订单状态
     * 1.已发送(等待发售者确定)
     * 2.已出货
     * 3.已到货
     * 4.订单完成(可删除)
     * 5.拒绝出售
     */
    private String orderState;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 订单时间
     */
    private String orderTime;
    /**
     * 订单实际付款
     */
    private double orderSum;
    /**
     * 订单原金额
     */
    private double orderOriginSum;
    /**
     * 订单id
     */
    private int orderId;

    /**
     * 买家对该订单的评价
     */
    private String orderComment;

    /**
     *卖家对买家订单评价的回复
     */
    private String orderReply;

    /**
     * 买家对订单进行评价等级
     */
    private int orderStar;

    public double getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(double orderSum) {
        this.orderSum = orderSum;
    }

    public double getOrderOriginSum() {
        return orderOriginSum;
    }

    public void setOrderOriginSum(double orderOriginSum) {
        this.orderOriginSum = orderOriginSum;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderReply() {
        return orderReply;
    }

    public void setOrderReply(String orderReply) {
        this.orderReply = orderReply;
    }

    public Trade getTrades() {
        return trades;
    }

    public void setTrades(Trade trades) {
        this.trades = trades;
    }

    public int getTradesAmount() {
        return tradesAmount;
    }

    public void setTradesAmount(int tradesAmount) {
        this.tradesAmount = tradesAmount;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOrderStar() {
        return orderStar;
    }

    public void setOrderStar(int orderStar) {
        this.orderStar = orderStar;
    }
}
