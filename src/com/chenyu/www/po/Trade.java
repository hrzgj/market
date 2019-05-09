package com.chenyu.www.po;

/**
 * 商品实体类
 * @author 86323
 */
public class Trade {
    /**
     * 商品id
     */
    private int tradeId;
    /**
     *商品价格
     */
    private double tradePrice;
    /**
     *商品剩余数量
     */
    private int tradeAmount;
    /**
     *商品名称
     */
    private String tradeName;
    /**
     *商品介绍
     */
    private String tradeIntroduce;
    /**
     *商品已卖数量
     */
    private int tradeBeenAmount;
    /**
     *商品种类
     */
    private String tradeKind;
    /**
     *商品照片名称
     */
    private String tradePhoto;
    /**
     *出售商品用户账户
     */
    private String tradeUser;
    /**
     *商品状态
     * 1.在售状态
     * 2.审核状态
     */
    private String tradeState;

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public int getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(int tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeIntroduce() {
        return tradeIntroduce;
    }

    public void setTradeIntroduce(String tradeIntroduce) {
        this.tradeIntroduce = tradeIntroduce;
    }

    public int getTradeBeenAmount() {
        return tradeBeenAmount;
    }

    public void setTradeBeenAmount(int tradeBeenAmount) {
        this.tradeBeenAmount = tradeBeenAmount;
    }

    public String getTradeKind() {
        return tradeKind;
    }

    public void setTradeKind(String tradeKind) {
        this.tradeKind = tradeKind;
    }

    public String getTradePhoto() {
        return tradePhoto;
    }

    public void setTradePhoto(String tradePhoto) {
        this.tradePhoto = tradePhoto;
    }

    public String getTradeUser() {
        return tradeUser;
    }

    public void setTradeUser(String tradeUser) {
        this.tradeUser = tradeUser;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }
}
