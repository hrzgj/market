package com.chenyu.www.po;


/**
 * 订单实体类,与数据库表一一对应
 * @author 86323
 */
public class Order {
    /**
     * 订单id
     */
    private int orderId;
    /**
     * 订单中购买用户账户
     */
    private String orderBuyer;
    /**
     * 订单中出售用户账户
     */
    private String orderSeller;
    /**
     * 订单状态
     * 1.已发送(等待发售者确定)
     * 2.已出货
     * 3.已到货
     * 4.拒绝出售
     */
    private String orderState;
    /**
     *订单的商品类（包含商品的各类信息）
     * 如出售用户的账户
     */
    private int orderTradeId;
    /**
     * 订单号(随机生成)
     */
    private String orderNumber;
    /**
     * 订单下单时间
     */
    private long orderTime;
    /**
     * 订单商品数量
     */
    private int orderAmount;
    /**
     * 买家对该订单的评价
     */
    private String orderComment;
    /**
     *卖家对买家订单评价的回复
     */
    private String orderReply;
    /**
     * 订单实际付款
     */
    private double orderSum;
    /**
     * 订单原金额
     */
    private double orderOriginSum;
    /**
     * 买家对订单进行评价等级
     */
    private int orderStar;


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

    public String getOrderBuyer() {
        return orderBuyer;
    }

    public void setOrderBuyer(String orderBuyer) {
        this.orderBuyer = orderBuyer;
    }

    public String getOrderSeller() {
        return orderSeller;
    }

    public void setOrderSeller(String orderSeller) {
        this.orderSeller = orderSeller;
    }

    public int getOrderTradeId() {
        return orderTradeId;
    }

    public void setOrderTradeId(int orderTradeId) {
        this.orderTradeId = orderTradeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public void setOrderNumber() {
        this.orderNumber =String.valueOf(System.currentTimeMillis());
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }


    public int getOrderStar() {
        return orderStar;
    }

    public void setOrderStar(int orderStar) {
        this.orderStar = orderStar;
    }

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
}
