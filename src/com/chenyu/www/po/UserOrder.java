package com.chenyu.www.po;

import java.util.List;

/**
 * 一个用户对应的所有订单，包装好给予前端
 * @author 86323
 */
public class UserOrder {
    private List<OrderShow> orderList;

    /**
     * 设置当前登录用户
     * 如果用户查看自己的购买订单，则订单内的user为出售用户
     * 如果用户查看自己的出售订单，则订单内的user为购买用户
     */
    private User user;

    public List<OrderShow> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderShow> orderList) {
        this.orderList = orderList;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
