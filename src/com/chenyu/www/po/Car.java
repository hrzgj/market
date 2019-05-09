package com.chenyu.www.po;

/**
 * 装载好数据给数据库增删改
 * @author 86323
 */
public class Car {
    private String carUser;
    private int carTrade;
    private int carAmount;

    public String getCarUser() {
        return carUser;
    }

    public void setCarUser(String carUser) {
        this.carUser = carUser;
    }

    public int getCarTrade() {
        return carTrade;
    }

    public void setCarTrade(int carTrade) {
        this.carTrade = carTrade;
    }


    public int getCarAmount() {
        return carAmount;
    }

    public void setCarAmount(int carAmount) {
        this.carAmount = carAmount;
    }
}
