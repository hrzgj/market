package com.chenyu.www.controller;

import com.chenyu.www.po.Page;
import com.chenyu.www.po.Trade;
import com.chenyu.www.po.User;
import com.chenyu.www.service.TradeService;
import com.chenyu.www.service.impl.TradeServiceImpl;
import com.chenyu.www.util.BaseServlet;
import com.chenyu.www.util.Constant;
import com.chenyu.www.util.PageUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author 86323
 */
public class TradeServlet extends BaseServlet {
    private static TradeService tradeService=new TradeServiceImpl();

    //游客登录
    public void visit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //清除所有的session
        request.getSession().invalidate();
        this.findAllTrade(request,response);
    }

    /**
     * 从Main.jsp的发布二手商品跳转而来,跳到AddTradeSuccess.jsp
     * @param request Main.jsp
     * @param response AddTradeSuccess.jsp
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public void addTrade(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            request.getSession().setAttribute(Constant.OUT_DATED,"登录过时了");
            response.sendRedirect("view/Main.jsp");
            return;
        }
        Iterator<FileItem> itemIterator=loadPhoto(request);
        Trade trade=new Trade();
        if (itemIterator != null) {
            while (itemIterator.hasNext()) {
                FileItem fileItem = itemIterator.next();
                String itemName = fileItem.getFieldName();
                //判断form表单是普通字段还是文件
                if (fileItem.isFormField()) {
                    if("tradePrice".equals(itemName)){
                        trade.setTradePrice(Double.parseDouble(fileItem.getString()));
                    }
                    if("tradeAmount".equals(itemName)){
                        trade.setTradeAmount(Integer.parseInt(fileItem.getString()));
                    }
                    if("tradeName".equals(itemName)){
                        trade.setTradeName(fileItem.getString("UTF-8"));
                    }
                    if("tradeKind".equals(itemName)){
                        trade.setTradeKind(fileItem.getString("UTF-8"));
                    }
                    if("tradeIntroduce".equals(itemName)){
                        trade.setTradeIntroduce(fileItem.getString("UTF-8"));
                    }
                    if("picture".equals(itemName)){
                        String pictureText= String.valueOf(request.getSession().getAttribute("pictureText"));
                        String picture=fileItem.getString();
                        if(!picture.equalsIgnoreCase(pictureText)){
                            request.setAttribute("msg","验证码错误");
                            request.getRequestDispatcher("view/AddTrade.jsp").forward(request,response);
                            return;
                        }
                    }
                }
                else{
                    //设置该文件存入文件夹中的名
                    trade.setTradePhoto(String.valueOf(System.currentTimeMillis()));
                    //获取上传文件的文件名
                    String pictureName=fileItem.getName();
                    //获取文件后缀
                    String ext = pictureName.substring(pictureName.indexOf(".")+1 );
                    String fileName=trade.getTradePhoto()+"."+ext;
                    trade.setTradePhoto(fileName);
                    File file=new File(Constant.PATH,fileName);
                    fileItem.write(file);
                }
            }
        }
        //发布者的账户
        trade.setTradeUser(user.getUserAccount());
        //默认为审核状态
        trade.setTradeState("审核");
        //默认已出售为0
        trade.setTradeBeenAmount(0);
        if(!tradeService.addTrade(trade)){
            response.sendRedirect("view/AddTradeSuccess.jsp");
        }
    }

    /**
     * 从Main.jsp的浏览全部商品跳转而来,跳到BrowseTrade.jsp
     * @param request Main.jsp
     * @param response BrowseTrade.jsp
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public void findAllTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Page<Trade> page=new Page<>();
        //排序信号
        int sort;
        //获取排序信号,无信号为自然排序
        if(request.getParameter(Constant.PRICE_SORT)==null &&request.getSession().getAttribute(Constant.PRICE_SORT)==null){
            sort=0;
        }
        //若request为空，表示信号已发送过，从session拿
        else if(request.getParameter(Constant.PRICE_SORT)==null){
            sort= (int) request.getSession().getAttribute(Constant.PRICE_SORT);
        }
        //第一次获取信号，从request拿，并存入session
        else {
            sort= Integer.parseInt(request.getParameter(Constant.PRICE_SORT));
            request.getSession().setAttribute(Constant.PRICE_SORT,sort);
        }
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setTotalCount(tradeService.getTotalTrade());
        page.setPageSize(PageUtil.loadPageSize(request,"10"));
        if(page.getCurrentPage()>page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());
        }
        page=tradeService.findTrade(page,sort);
        request.getSession().setAttribute("pageTrade",page);
        request.getSession().setAttribute(Constant.JUDGE_TRADE,"findAllTrade");
        response.sendRedirect("view/BrowseTrade.jsp");
    }

    /**
     * 从AdminMain.jsp的审核二手商品跳转而来,跳到FindNeedCheckTrade.jsp
     * @param request AdminMain.jsp
     * @param response FindNeedCheckTrade.jsp
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public void adminFindCheckTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Page<Trade> page=new Page<>();
        page.setTotalCount(tradeService.getNeedCheck());
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setPageSize(PageUtil.loadPageSize(request,"10"));
        if(page.getCurrentPage()!=1 && page.getCurrentPage()>page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());
        }
        page=tradeService.adminFindCheck(page);
        request.setAttribute("pageTrade",page);
        request.getRequestDispatcher("view/FindNeedCheckTrade.jsp").forward(request,response);
    }

    //通过前端传来的商品的id得到该商品信息
    public void findThisTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        User user= (User) request.getSession().getAttribute("user");
        //获得该商品id
        int tradeID= Integer.parseInt(request.getParameter("pageId"));
        //根据id得到数据库该商品所有信息
        Trade trade=tradeService.findTradeByID(tradeID);
        request.setAttribute("thisTrade",trade);
        //如果用户是游客，即user为空
        if(user==null){
            request.getSession().setAttribute("judgeTrade","findAllTrade");
            request.getRequestDispatcher("view/FindThisTrade.jsp").forward(request, response);
            return;
        }
        //如果用户身份是管理员或者是购买者，就进入查看界面
        if(Constant.USER_IDENTITY1.equals(user.getUserIdentity())||request.getParameter("buyer")!=null) {
            //如果用户账户和商品出售用户的账户相同，即不可以购买自己的商品
            if(user.getUserAccount().equals(trade.getTradeUser())){
                //给个提示
                request.setAttribute("seller","不可以买自己的商品哦");
                request.getSession().setAttribute("judgeTrade","findAllTrade");
            }
            request.getRequestDispatcher("view/FindThisTrade.jsp").forward(request, response);
            return;
        }
        //否则，则判定用户是出售者，可修改
        else {
            request.getRequestDispatcher("view/UpdateThisTrade.jsp").forward(request,response);
            return;
        }
    }

    //用户管理员通过审核用户发售商品
    public void checkTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        int tradeId= Integer.parseInt(request.getParameter("pageId"));
        if(!tradeService.updateTradeState(tradeId)){
            response.sendRedirect("/market_war_exploded/TradeServlet?method=adminFindCheckTrade");
        }
    }

    //用户查找自己出售的用户
    public void findTradeByUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            request.getSession().setAttribute("outDated","登录过时了");
            response.sendRedirect("view/Main.jsp");
            return;
        }
        Page<Trade> page=new Page<>();
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setTotalCount(tradeService.getTotalByUser(user.getUserAccount()));
        page.setPageSize(PageUtil.loadPageSize(request,"10"));
        page=tradeService.findTradeByUser(page,user.getUserAccount());
        request.setAttribute("userPage",page);
        request.getRequestDispatcher("view/UserTrade.jsp").forward(request,response);
    }

    //用户修改自己商品的信息
    public void updateTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Trade trade=new Trade();
        trade.setTradeId(Integer.parseInt(request.getParameter("tradeId")));
        trade.setTradeName(request.getParameter("tradeName"));
        trade.setTradePrice(Double.parseDouble(request.getParameter("tradePrice")));
        trade.setTradeAmount(Integer.parseInt(request.getParameter("tradeAmount")));
        trade.setTradeIntroduce(request.getParameter("tradeIntroduce"));
        if(!tradeService.updateTrade(trade)){
            this.findTradeByUser(request,response);
        }
    }

    //用户修改自己商品的图片
    public void updateTradePhoto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Iterator<FileItem> itemIterator=loadPhoto(request);
        Trade trade=new Trade();
        if(itemIterator!=null &&itemIterator.hasNext()) {
            FileItem fileItem=itemIterator.next();
            if(!fileItem.isFormField()){
                //获取上传文件的文件名
                String pictureName=fileItem.getName();
                //设置该文件存入文件夹中的名
                trade.setTradePhoto(String.valueOf(System.currentTimeMillis()));
                //获取文件后缀
                String ext = pictureName.substring(pictureName.indexOf(".")+1 );
                String fileName=trade.getTradePhoto()+"."+ext;
                trade.setTradePhoto(fileName);
                File file=new File(Constant.PATH,fileName);
                try {
                    fileItem.write(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        trade.setTradeId(Integer.parseInt(request.getParameter("tradeId")));
        if(!tradeService.updateTradePhoto(trade)){
            this.findTradeByUser(request,response);
        }
    }

    //用户通过出售用户账户来搜索所有商品
    public void findAllTradeByUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String userAccount=null;
        //判断是否第一次接收用户账户，如果是从request拿并推到session，如果不是从session拿
        if(request.getParameter(Constant.USER_ACCOUNT)!=null){
            userAccount=request.getParameter(Constant.USER_ACCOUNT);
            request.getSession().setAttribute(Constant.USER_ACCOUNT,userAccount);
        }
        else {
            userAccount= (String) request.getSession().getAttribute(Constant.USER_ACCOUNT);
        }
        int totalCount=tradeService.getTotalVagueByUserAccount(userAccount);
        //判断是否有该商品
        if(totalCount<=0){
            request.getSession().setAttribute("noTrade","没有该用户账户");
            findAllTrade(request,response);
            return;
        }
        request.getSession().removeAttribute("noTrade");
        Page<Trade> page=new Page<>();
        page.setTotalCount(totalCount);
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setPageSize(PageUtil.loadPageSize(request,"10"));
        if(page.getCurrentPage()>page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());
        }
        page=tradeService.findVagueByUserAccount(userAccount,page);
        //给予前端判断，使分页的跳转界面不同
        request.getSession().setAttribute(Constant.JUDGE_TRADE,"findAllTradeByUser");
        request.getSession().setAttribute("pageTrade",page);
        response.sendRedirect("view/BrowseTrade.jsp");
    }

    //用户通过商品名称来搜索所有商品
    public void findAllTradeByName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Page<Trade> page=new Page<>();
        String tradeName=null;
        //判断是否第一次接收到商品名称，如果是从request拿并推到session，如果不是从session拿
        if(request.getParameter(Constant.TRADE_NAME)!=null){
            tradeName=request.getParameter(Constant.TRADE_NAME);
            request.getSession().setAttribute(Constant.TRADE_NAME,tradeName);
        }
        else {
            tradeName= (String) request.getSession().getAttribute(Constant.TRADE_NAME);
        }
        int totalCount=tradeService.getTotalVagueByTradeName(tradeName);
        //判断是否有该商品
        if(totalCount<=0){
            request.getSession().setAttribute("noName","没有该商品名称");
            findAllTrade(request,response);
            return;
        }
        //如果有该商品，而noName因为是放在session，及时清理
        request.getSession().removeAttribute("noName");
        page.setTotalCount(totalCount);
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setPageSize(PageUtil.loadPageSize(request,"10"));
        //如果当前页大于总页数
        if(page.getCurrentPage()>page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());
        }
        page=tradeService.findVagueByTradeName(tradeName,page);
        //给予前端判断，使分页的跳转界面不同
        request.getSession().setAttribute(Constant.JUDGE_TRADE,"findAllTradeByName");
        request.getSession().setAttribute("pageTrade",page);
        response.sendRedirect("view/BrowseTrade.jsp");
    }

    //用户根据商品分类查询商品
    public void findAllTradeByType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Page<Trade> page=new Page<>();
        String tradeKind=null;
        //判断是否是第一次接收，如果是再判断类型是否正确，正确推送到session，否则给予提示
        if(request.getParameter(Constant.TRADE_TYPE)!=null){
            tradeKind=request.getParameter(Constant.TRADE_TYPE);
            //正确
            if(tradeService.isRealTradeKind(tradeKind)){
                request.getSession().setAttribute(Constant.TRADE_TYPE,tradeKind);
            }
            //错误
            else {
                request.getSession().setAttribute("youCanNot","1");
                response.sendRedirect("view/BrowseTrade.jsp");
                return;
            }
        }
        else {
            tradeKind= (String) request.getSession().getAttribute(Constant.TRADE_TYPE);
        }
        int totalCount=tradeService.getTotalByTradeKind(tradeKind);
        if(totalCount<0){
            request.getSession().setAttribute("pageTrade",null);
            response.sendRedirect("view/BrowseTrade.jsp");
            return;
        }
        page.setTotalCount(totalCount);
        page.setCurrentPage(PageUtil.loadCurrentPage(request));
        page.setPageSize(PageUtil.loadPageSize(request,"10"));
        //如果当前页大于总页数
        if(page.getCurrentPage()>page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());
        }
        page=tradeService.findByTradeKind(tradeKind,page);
        //给予前端判断，使分页的跳转界面不同
        request.getSession().setAttribute("judgeTrade","findAllTradeByType");
        request.getSession().setAttribute("pageTrade",page);
        response.sendRedirect("view/BrowseTrade.jsp");
    }

    //管理员退回商品，即将商品状态变为审核
    public void adminSendBackTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        int tradeId= Integer.parseInt(request.getParameter("tradeId"));
        if(!tradeService.adminSendBack(tradeId)){
            this.findAllTrade(request,response);
        }
    }

    //用户删除商品或管理员删除商品
    public void removeTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            request.getSession().setAttribute("outDated","登录过时了");
            response.sendRedirect("view/Main.jsp");
            return;
        }
        int tradeId= Integer.parseInt(request.getParameter("tradeId"));
        if(!tradeService.removeTrade(tradeId)){
            if(Constant.USER_IDENTITY1.equals(user.getUserIdentity())){
                this.adminFindCheckTrade(request,response);
            }
            else {
                this.findTradeByUser(request,response);
            }
        }
    }

    //用户删除商品或管理员删除商品
    public void readyRemoveTrade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        User user= (User) request.getSession().getAttribute("user");
        int tradeId= Integer.parseInt(request.getParameter("tradeId"));
        if(user==null){
            request.getSession().setAttribute("outDated","登录过时了");
            response.sendRedirect("view/Main.jsp");
            return;
        }
        if(!tradeService.readyRemoveTrade(tradeId)){
            if(Constant.USER_IDENTITY1.equals(user.getUserIdentity())){
                this.adminFindCheckTrade(request,response);
            }
            else {
                this.findTradeByUser(request,response);
            }
        }
    }

}
