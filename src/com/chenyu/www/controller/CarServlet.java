package com.chenyu.www.controller;

import com.chenyu.www.po.*;
import com.chenyu.www.service.TradeService;
import com.chenyu.www.service.impl.TradeServiceImpl;
import com.chenyu.www.util.BaseServlet;
import com.chenyu.www.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86323
 */
public class CarServlet extends BaseServlet {
    private TradeService tradeService=new TradeServiceImpl();
    //增加商品到购物车，接收前端传来的商品id和商品数量(tradeID和carAccount)
    public void addTradeToCar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user;
        if(examineUser(request,response)){
            user= (User) request.getSession().getAttribute("user");
        }
        else {
            return;
        }
        Car car=new Car();
        if(!examineTradeId(request,response)){
            return;
        }
        if(request.getParameter("carAccount")==null){
             request.getSession().setAttribute("error",1);
             response.sendRedirect("view/Main.jsp");
             return;
        }
        car.setCarTrade(Integer.parseInt(request.getParameter("tradeId")));
        car.setCarAmount(Integer.parseInt(request.getParameter("carAccount")));
        //先找出这个商品的信息
        Trade trade=tradeService.findTradeByID(car.getCarTrade());
        if(car.getCarAmount()>trade.getTradeAmount()){
            request.setAttribute("exceedAccount","超出商品的数量了");
            request.setAttribute("thisTrade",trade);
            request.getRequestDispatcher("view/FindThisTrade.jsp").forward(request,response);
            return;
        }
        car.setCarUser(user.getUserAccount());
        if(!tradeService.addTradeToCar(car)){
            response.sendRedirect("view/BrowseTrade.jsp");
            return;
        }

    }

    //查找当前登录用户的购物车
    public void findCar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserCar userCar = new UserCar();
        User user;
        if(examineUser(request,response)){
            user= (User) request.getSession().getAttribute("user");
        }
        else {
            return;
        }
        userCar.setUser(user);
        userCar=tradeService.findCar(userCar);
        userCar.setTradesPrice();
        request.getSession().setAttribute("userCar",userCar);
        response.sendRedirect("view/FindCar.jsp");
    }

    //移除当前用户购物车某一个商品(tradeId)
    public void removeOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        if(!examineTradeId(request,response)){
            return;
        }
        int tradeId= Integer.parseInt(request.getParameter("tradeId"));
        UserCar userCar= (UserCar) request.getSession().getAttribute("userCar");
        if(userCar==null){
            request.getSession().setAttribute(Constant.OUT_DATED,"登录过时了");
            response.sendRedirect("view/Main.jsp");
            return;
        }
        String userAccount=userCar.getUser().getUserAccount();
        if(!tradeService.removeTrade(tradeId,userAccount)) {
            userCar = tradeService.removeTrade(tradeId, userCar);
            userCar.setTradesPrice();
            request.getSession().setAttribute("userCar", userCar);
            response.sendRedirect("view/FindCar.jsp");
        }
    }

    //移除当前用户购物车某一个商品的一个数量(tradeId)
    public void removeOneAmount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        if(!examineTradeId(request,response)){
            return;
        }
        int tradeId= Integer.parseInt(request.getParameter("tradeId"));
        UserCar userCar= (UserCar) request.getSession().getAttribute("userCar");
        if(!tradeService.removeOneAmount(tradeId,userCar.getUser().getUserAccount())){
            userCar=tradeService.removeOneAmount(tradeId,userCar);
            userCar.setTradesPrice();
            request.getSession().setAttribute("userCar",userCar);
            response.sendRedirect("view/FindCar.jsp");
        }
    }

    //清除当前用户购物车所有商品
    public void removeAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserCar userCar= (UserCar) request.getSession().getAttribute("userCar");
        //如果购物车为空不进行操作
        if(userCar.getTrades().size()==0){
            response.sendRedirect("view/FindCar.jsp");
            return;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            request.getSession().setAttribute(Constant.OUT_DATED,"登录过时了");
            response.sendRedirect("view/Main.jsp");
            return;
        }
        if(!tradeService.removeAll(userCar.getUser().getUserAccount())){
            request.getSession().removeAttribute("userCar");
            response.sendRedirect("view/FindCar.jsp");
        }
    }

    //把当前用户的购物车商品全部变成订单,
    public void addCarToOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserCar userCar= (UserCar) request.getSession().getAttribute("userCar");
        //如果购物车是空，不进行操作
        if(userCar.getTrades().size()==0){
            response.sendRedirect("view/FindCar.jsp");
            return;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(!tradeService.addCarToOrder(userCar,user)){
            //将购物车商品变成订单同时，清空购物车
            tradeService.removeAll(userCar.getUser().getUserAccount());
            request.getSession().setAttribute("user",user);
            request.getSession().removeAttribute("userCar");
            response.sendRedirect("view/FindCar.jsp");
        }
    }
}
