package com.chenyu.www.controller;

import com.chenyu.www.po.*;
import com.chenyu.www.service.TradeService;
import com.chenyu.www.service.UserService;
import com.chenyu.www.service.impl.TradeServiceImpl;
import com.chenyu.www.service.impl.UserServiceImpl;
import com.chenyu.www.util.BaseServlet;
import com.chenyu.www.util.Constant;
import com.chenyu.www.util.PageUtil;
import com.chenyu.www.util.PasswordEndrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 86323
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends BaseServlet {
    private static UserService userService=new UserServiceImpl();
    //登录
    public void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user=new User();
        String account=request.getParameter("user_account");
        user.setUserAccount(request.getParameter("user_account"));
        user.setUserPassword(PasswordEndrypt.getMD5(request.getParameter("user_password")));
        //先判断登录是否成功
        if(userService.login(user)) {
            try {
                //登录成功，将数据库中该用户信息存入user
                user=userService.informationInput(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.getSession().setAttribute("user",user);
            response.sendRedirect("view/LoginSuccess.jsp");

        }
        else {
            request.setAttribute("error","账户密码不符");
            request.setAttribute("userAccount",user.getUserAccount());
            request.getRequestDispatcher("view/Login.jsp").forward(request,response);
        }
    }

    //注册
    public void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Iterator<FileItem> itemIterator=loadPhoto(request);
        User user=new User();
        if (itemIterator != null) {
            while (itemIterator.hasNext()){
                FileItem fileItem=itemIterator.next();
                //获取普通表单字段名字
                String itemName=fileItem.getFieldName();
                //判断form表单是普通字段还是文件
                if(fileItem.isFormField()){
                    if("user_idCard".equals(itemName)){
                        user.setUserIdCard(fileItem.getString());
                    }
                    if("user_account".equals(itemName)){
                        user.setUserAccount(fileItem.getString());
                    }
                    if("user_password".equals(itemName)){
                        user.setUserPassword(PasswordEndrypt.getMD5(fileItem.getString()));
                    }
                    if("user_name".equals(itemName)){
                        user.setUserName(fileItem.getString("UTF-8"));
                    }
                    if("user_phone".equals(itemName)){
                        user.setUserPhone(fileItem.getString());
                    }
                    if("user_address".equals(itemName)){
                        user.setUserAddress(fileItem.getString("UTF-8"));
                    }
                    if("picture".equals(itemName)){
                        //判断验证码是否正确
                        String picture=fileItem.getString();
                        String pictureText= String.valueOf(request.getSession().getAttribute("pictureText"));
                        if(!picture.equalsIgnoreCase(pictureText)){
                            request.setAttribute("msg","验证码错误");
                            request.getRequestDispatcher("view/Register.jsp").forward(request,response);
                            return;
                        }
                    }
                }
                else {
                    //设置该文件存入文件夹中的名
                    user.setUserPhoto(String.valueOf(System.currentTimeMillis()));
                    //获取上传文件的文件名
                    String pictureName=fileItem.getName();
                    //获取文件后缀
                    String ext = pictureName.substring(pictureName.indexOf(".")+1 ) ;
                    String fileName=user.getUserPhoto()+"."+ext;
                    user.setUserPhoto(fileName);
                    File file=new File(Constant.PATH,fileName);
                    try {
                        fileItem.write(file);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //注册成功进入主菜单
        if(userService.register(user)){
            request.getSession().setAttribute("user",user);
            response.sendRedirect("view/RegisterSuccess.jsp");
        }
    }

    //忘记密码
    public void forgetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user=new User();
        user.setUserAccount(request.getParameter("user_account"));
        user.setUserIdCard(request.getParameter("user_idCard"));
        user.setUserPassword(PasswordEndrypt.getMD5(request.getParameter("user_password")));
        if(Constant.FIND_PASSWORD_ERROR.equals(userService.findPassword(user))){
            request.setAttribute("account",Constant.FIND_PASSWORD_ERROR);
            request.getRequestDispatcher("view/ForgetPassword.jsp").forward(request,response);
        }
        if(Constant.FIND_PASSWORD.equals(userService.findPassword(user))){
            response.sendRedirect("view/FindPasswordSuccess.jsp");
        }
    }

    //修改密码
    public void updatePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user= (User) request.getSession().getAttribute("user");
        String oldPassword=request.getParameter("usersOld");
        if(userService.judgePassword(user,oldPassword)){
            user.setUserPassword(PasswordEndrypt.getMD5(request.getParameter("userNew")));
            if(userService.updatePassword(user)){
                request.getSession().setAttribute("user",user);
                response.sendRedirect("view/UpdateSuccess.jsp");
            }

        }
        else {
            request.setAttribute("passwordError","旧密码不符，请重新输入");
            request.setAttribute("newWord",request.getParameter("userNew"));
            request.getRequestDispatcher("view/UpdateUserPassword.jsp").forward(request,response);
        }
    }

    //修改用户信息
    public void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user= (User) request.getSession().getAttribute("user");
        user.setUserPhone(request.getParameter("userPhone"));
        user.setUserAddress(request.getParameter("userAddress"));
        user.setUserName(request.getParameter("userName"));
        user=userService.updateNameAddressAndPhone(user);
        request.getSession().setAttribute("user",user);
        response.sendRedirect("view/UpdateSuccess.jsp");
    }

    // 管理员查询所有用户
    public void findAllUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Page<User> page=new Page<>();
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setTotalCount(userService.getTotalCount());
        //设置页码的时候会自动计算总页数
        page.setPageSize(PageUtil.loadPageSize(request,"5"));
        //如果当前页大于总页数，设为最大页数
        if(page.getCurrentPage()>page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());
        }
        page=userService.getSomeUser(page);
        request.getSession().setAttribute("pageBean",page);
        //同样给予前端提示
        request.getSession().setAttribute("judge","findAllUser");
        response.sendRedirect("view/FindAllUser.jsp");
    }

    //用户修改自己头像
    public void updateUserPhoto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException {
        User user= (User) request.getSession().getAttribute("user");
        Iterator<FileItem> itemIterator=loadPhoto(request);
        if (itemIterator != null) {
            while (itemIterator.hasNext()){
                FileItem fileItem=itemIterator.next();
                //获取普通表单字段名字
                String itemName=fileItem.getFieldName();
                //判断form表单是普通字段还是文件
                if(!fileItem.isFormField()){
                    String pictureName=fileItem.getName();
                    //获取文件后缀
                    String ext = pictureName.substring(pictureName.indexOf(".")+1 ) ;
                    user.setUserPhoto(String.valueOf(System.currentTimeMillis()));
                    String fileName=user.getUserPhoto()+"."+ext;
                    File file=new File(Constant.PATH,fileName);
                    user.setUserPhoto(fileName);
                    try {
                        fileItem.write(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(!userService.updateUserPhoto(user)){
            request.getSession().setAttribute("user",user);
            response.sendRedirect("view/Main.jsp");
        }

    }

    //判断账户是否存在
    public void haveAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String userAccount=request.getParameter("userAccount");
        Map<String,Object> map=new HashMap<>(2);
        if(!userAccount.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")){
            map.put("userExist",true);
            map.put("msg","此用户名格式错误");
            ObjectMapper mapper=new ObjectMapper();
            mapper.writeValue(response.getWriter(),map);
            return;
        }
        if(userService.isHaveAccount(userAccount)){
            map.put("userExist",true);
            map.put("msg","此用户名已被注册");
        }else {
            map.put("userExist",false);
            map.put("msg","此用户名可用");
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),map);
    }

    //判断身份证是否存在
    public void haveIdCard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
            String userIdCard=request.getParameter("userIdCard");
            Map<String,Object> map=new HashMap<>(2);
            if(!userIdCard.matches("^\\d{15}|\\d{18}$")){
                map.put("userCard",true);
                map.put("msg","该身份证格式错误");
                ObjectMapper mapper=new ObjectMapper();
                mapper.writeValue(response.getWriter(),map);
                return;
            }
            if(userService.isHaveIdCard(userIdCard)){
                map.put("userCard",true);
                map.put("msg","该身份证已被使用");
            }
            else {
                map.put("userCard",false);
                map.put("msg","该身份证可用");
            }
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),map);
    }

    //根据用户账户搜索用户
    public void findUserByAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String userAccount = null;
        //第一次接收把该值放到session中
        if(request.getParameter(Constant.USER_ACCOUNT)!=null){
            userAccount=request.getParameter("userAccount");
            request.getSession().setAttribute("userAccount",userAccount);
        }
        else {
            userAccount= (String) request.getSession().getAttribute("userAccount");
        }
        Page<User> page=new Page<>();
        int totalCount=userService.getTotalVagueUser(userAccount);
        //如果没有，给予前端提示，并且跳到原界面
        if(totalCount==0){
            request.getSession().setAttribute("noAccount","没有该用户");
            findAllUser(request,response);
            return;
        }
        request.getSession().removeAttribute("noAccount");
        page.setTotalCount(totalCount);
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setPageSize(PageUtil.loadPageSize(request,"5"));
        page=userService.findUserByVagueAccount(userAccount,page);
        //给予前端判断，使分页的跳转界面不同
        request.getSession().setAttribute("judge","findUserByAccount");
        request.getSession().setAttribute("pageBean",page);
        response.sendRedirect("view/FindAllUser.jsp");
    }

    //管理员禁止指定用户出售商品
    public void banUserSell(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String userAccount=request.getParameter("userAccount");
        String reason=request.getParameter("reason");
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserReason(reason);
        //修改成功跳转
        if(!userService.banUserSell(user)){
            response.sendRedirect("view/FindAllUser.jsp");
        }
    }

    //被禁用户发起申诉请求
    public void appealSell(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        User user= (User) request.getSession().getAttribute("user");
        user.setUserAppeal(request.getParameter("appeal"));
        if(!userService.appealSell(user)){
            response.sendRedirect("view/AppealSuccess.jsp");
        }
    }


    //管理员解禁被禁售的用户
    public void allowSell(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String userAccount=request.getParameter("userAccount");
        if(!userService.allowSell(userAccount)){
            response.sendRedirect("view/FindAllUser.jsp");
        }
    }



    //购买用户增加一个订单
    public void addOneOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Order order=new Order();
        User user= (User) request.getSession().getAttribute("user");
        UserCar userCar= (UserCar) request.getSession().getAttribute("userCar");
        int tradeId= Integer.parseInt(request.getParameter("tradeId"));
        //设置订单的id，商品数量，购买用户账户，和当前用户的积分
        order.setOrderTradeId(tradeId);
        order.setOrderAmount(Integer.parseInt(request.getParameter("tradeAmount")));
        order.setOrderBuyer(userCar.getUser().getUserAccount());
        order.setOrderSum(user.getUserPoint());
        //返回剩余积分放在user上
        user.setUserPoint(userService.addOrder(order));
        //增加订单成功后移除购物车内该商品
        TradeService tradeService=new TradeServiceImpl();
        tradeService.removeTrade(tradeId,userCar.getUser().getUserAccount());
        userCar=tradeService.removeTrade(tradeId,userCar);
        request.getSession().setAttribute("user",user);
        request.getSession().setAttribute("userCar",userCar);
        response.sendRedirect("view/FindCar.jsp");
    }

    //购买用户使用积分购买商品，增加一个订单
    public void addOneOrderWithPoint(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        int tradeId= Integer.parseInt(request.getParameter("tradeId"));
        Order order=new Order();
        order.setOrderTradeId(tradeId);
        User user= (User) request.getSession().getAttribute("user");
        order.setOrderAmount(Integer.parseInt(request.getParameter("tradeAmount")));
        UserCar userCar= (UserCar) request.getSession().getAttribute("userCar");
        order.setOrderBuyer(userCar.getUser().getUserAccount());
        //先将积分存到sum里面
        order.setOrderSum(userCar.getUser().getUserPoint());
        //订单增加成功后
        //把剩余积分赋给当前登录的user
        user.setUserPoint(userService.addOneOrderWithPoint(order));
        TradeService tradeService=new TradeServiceImpl();
        //移走购物车的相应商品
        tradeService.removeTrade(tradeId,userCar.getUser().getUserAccount());
        //不经过数据库将useCar对应商品清除
        userCar=tradeService.removeTrade(tradeId,userCar);
        //将更新好的user和userCar推到session
        request.getSession().setAttribute("userCar",userCar);
        request.getSession().setAttribute("user",user);
        response.sendRedirect("view/FindCar.jsp");


    }

    //卖家查询自己出售商品的订单
    public void findSellerOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.findSellerOrder(userOrder);
        request.getSession().setAttribute("sellerOrder",userOrder);
        response.sendRedirect("view/FindSellerOrder.jsp");
    }

    //卖家对某一个订单的商品发货
    public void sellDeliverOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("sellerOrder");
        int orderId= Integer.parseInt(request.getParameter("orderId"));
        Order order=new Order();
        order.setOrderId(orderId);
        //直接修改userOrder，而不需要重新经过数据库,同时减少对应商品的剩余数量，订单状态变为以发货
        if(!userService.sellDeliverOrder(order,userOrder)){
            request.getSession().setAttribute("sellerOrder",userOrder);
            response.sendRedirect("view/FindSellerOrder.jsp");
        }
    }

    //卖家对某一个订单的商品拒绝发货
    public void sellRefuseOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("sellerOrder");
        int orderId= Integer.parseInt(request.getParameter("orderId"));
        Order order=new Order();
        order.setOrderId(orderId);
        if(!userService.sellRefuseOrder(order)){
           userService.updateShowOrder(userOrder.getOrderList(),orderId,Constant.ORDER_STATE_FOUR);
            request.getSession().setAttribute("sellerOrder",userOrder);
            response.sendRedirect("view/FindSellerOrder.jsp");
        }

    }

    //卖家对某一个已收货订单进行删除
    public void sellRemoverOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("sellerOrder");
        int orderId= Integer.parseInt(request.getParameter("orderId"));
        Order order=new Order();
        order.setOrderId(orderId);
        //判断是否有删除订单
        if(!userService.sellerRemoveOrder(order)){
            //如果删除了，就把userOrder中对应的订单删除
            userOrder=userService.removerShowOrder(userOrder,orderId);
        }
        else {
            //如果没有删除，就把userOrder中对应订单的出售用户账户设为空
            userOrder=userService.setNullUserAccount(userOrder,orderId);
        }
        request.getSession().setAttribute("sellerOrder",userOrder);
        response.sendRedirect("view/FindSellerOrder.jsp");
    }

    //卖家查询所有已发送的订单
    public void sellFindSendOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.sellerFindSendOrder(userOrder);
        request.getSession().setAttribute("sellerOrder",userOrder);
        response.sendRedirect("view/FindSellerOrder.jsp");
    }

    //卖家查询所有已发货的订单
    public void sellFindDeliverOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.sellerFindDeliverOrder(userOrder);
        request.getSession().setAttribute("sellerOrder",userOrder);
        response.sendRedirect("view/FindSellerOrder.jsp");
    }

    //卖家用户查询所有已收货的订单
    public void sellFindTakeOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.sellerFindTakeOrder(userOrder);
        request.getSession().setAttribute("sellerOrder",userOrder);
        response.sendRedirect("view/FindSellerOrder.jsp");
    }

    //卖家用户查询所有已收货的订单
    public void sellFindRefuseOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.sellerFindRefuseOrder(userOrder);
        request.getSession().setAttribute("sellerOrder",userOrder);
        response.sendRedirect("view/FindSellerOrder.jsp");
    }

    //卖家用户回复用户评价
    public void sellReply(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Order order=new Order();
        order.setOrderId(Integer.parseInt(request.getParameter("orderId")));
        order.setOrderReply(request.getParameter("orderReply"));
        if(!userService.sellReplyOrder(order)){
            request.getSession().setAttribute("replySuccess","成功");
            this.findSellerOrder(request,response);
        }

    }

    //卖家导出当前显示的订单
    public void sellExportFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //必须生成不同文件名称
        String fileName= String.valueOf(System.currentTimeMillis())+".txt";
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("sellerOrder");
        if(!userService.sellExportFile(userOrder,fileName)){
            //通过文件名获取Mime类型
            fileName=Constant.ORDER_PATH+fileName;
            String contentType=this.getServletContext().getMimeType(fileName);
            String disposition="attachment;filename=orders.txt";
            FileInputStream inputStream=new FileInputStream(fileName);
            //设置头
            response.setHeader("Content-Type",contentType);
            response.setHeader("Content-Disposition",disposition);
            ServletOutputStream outputStream=response.getOutputStream();
            //把输入流中数据写到输出流中，io包
            IOUtils.copy(inputStream,outputStream);
            //关闭流
            inputStream.close();
        }
    }





    //买家用户查询自己购买的订单
    public void findBuyerOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.findBuyerOrder(userOrder);
        request.getSession().setAttribute("buyerOrder",userOrder);
        response.sendRedirect("view/FindBuyerOrder.jsp");
    }

    //买家用户对某一个订单的商品收货
    public void buyerTakeOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("buyerOrder");
        int orderId= Integer.parseInt(request.getParameter("orderId"));
        Order order=new Order();
        order.setOrderId(orderId);
        if(!userService.buyerTakeOrder(order)){
           userService.updateShowOrder(userOrder.getOrderList(),orderId,Constant.ORDER_STATE_THREE);
            request.getSession().setAttribute("buyerOrder",userOrder);
            response.sendRedirect("view/FindBuyerOrder.jsp");
        }
    }

    //买家用户对某一个未发货的订单进行取消
    public void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("buyerOrder");
        User user= (User) request.getSession().getAttribute("user");
        int orderId= Integer.parseInt(request.getParameter("orderId"));
        Order order=new Order();
        order.setOrderId(orderId);
        //同时传入user改变用户当前积分
        if(!userService.deleteOrder(order,user)){
            //删除成功后，删除userOrder对应订单
            userOrder=userService.removerShowOrder(userOrder,orderId);
            request.getSession().setAttribute("buyOrder",userOrder);
            request.getSession().setAttribute("user",user);
            response.sendRedirect("view/FindBuyerOrder.jsp");
        }

    }

    //买家用户对某一个已收货订单进行删除
    public void buyRemoverOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("buyerOrder");
        int orderId= Integer.parseInt(request.getParameter("orderId"));
        Order order=new Order();
        order.setOrderId(orderId);
        //判断是否有删除订单
        if(!userService.buyerRemoverOrder(order)){
            //如果删除了，就把userOrder中对应的订单删除
            userOrder=userService.removerShowOrder(userOrder,orderId);
        }else {
            //如果没有删除，就把userOrder中对应订单的出售用户账户设为空
            userOrder=userService.setNullUserAccount(userOrder,orderId);
        }
        request.getSession().setAttribute("buyerOrder",userOrder);
        response.sendRedirect("view/FindBuyerOrder.jsp");
    }

    //买家用户查询所有已发送的订单
    public void buyFindSendOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.buyerFindSendOrder(userOrder);
        request.getSession().setAttribute("buyerOrder",userOrder);
        response.sendRedirect("view/FindBuyerOrder.jsp");
    }

    //买家用户查询所有已发货的订单
    public void buyFindDeliverOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.buyerFindDeliverOrder(userOrder);
        request.getSession().setAttribute("buyerOrder",userOrder);
        response.sendRedirect("view/FindBuyerOrder.jsp");
    }

    //买家用户查询所有已收货的订单
    public void buyFindTakeOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.buyerFindTakeOrder(userOrder);
        request.getSession().setAttribute("buyerOrder",userOrder);
        response.sendRedirect("view/FindBuyerOrder.jsp");
    }

    //买家用户查询所有已收货的订单
    public void buyFindRefuseOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserOrder userOrder=new UserOrder();
        userOrder.setUser((User) request.getSession().getAttribute("user"));
        userOrder=userService.buyerFindRefuseOrder(userOrder);
        request.getSession().setAttribute("buyerOrder",userOrder);
        response.sendRedirect("view/FindBuyerOrder.jsp");
    }

    //买家用户评价一个已收货的订单
    public void buyCommentOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Order order=new Order();
        order.setOrderId(Integer.parseInt(request.getParameter("orderId")));
        order.setOrderComment(request.getParameter("orderComment"));
        order.setOrderStar(Integer.parseInt(request.getParameter("orderGrade")));
        if(!userService.buyCommentOrder(order)){
            //给予前端评价成功的提示
            request.getSession().setAttribute("commentSuccess","评价成功");
            this.findBuyerOrder(request,response);
        }
    }

    //买家用户导出当前显示订单
    public void buyExportFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //必须生成不同文件名称
        String fileName= String.valueOf(System.currentTimeMillis())+".txt";
        UserOrder userOrder= (UserOrder) request.getSession().getAttribute("buyerOrder");
        if(!userService.buyExportFile(userOrder,fileName)){
            //通过文件名获取Mime类型
            fileName=Constant.ORDER_PATH+fileName;
            FileInputStream inputStream=new FileInputStream(fileName);
            //设置头
            response.setHeader("Content-Type",this.getServletContext().getMimeType(fileName));
            response.setHeader("Content-Disposition","attachment;filename=orders.txt");
            ServletOutputStream outputStream=response.getOutputStream();
            //把输入流中数据写到输出流中，io包
            IOUtils.copy(inputStream,outputStream);
            //关闭流
            inputStream.close();
        }
    }


}

