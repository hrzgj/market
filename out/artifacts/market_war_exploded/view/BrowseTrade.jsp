<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/2
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Title</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindTrade.css"/>
<ul>
    <c:if test="${sessionScope.user.userIdentity=='普通用户'}">
    <li><a class="active" href="Main.jsp">点击返回主菜单</a></li></c:if>
    <c:if test="${empty sessionScope.user.userIdentity}"><li><a class="active" href="http://localhost:8080/market_war_exploded">点击返回登录界面</a></li></c:if>
    <c:if test="${sessionScope.user.userIdentity=='管理员'}">
        <li><a class="active" href="AdminMain.jsp">点击返回主菜单</a></li></c:if>
    <li><a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&currentPage=1">首页</a></li>
    <li><a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&currentPage=${sessionScope.pageTrade.currentPage-1}">上一页</a></li>
    <li><a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&currentPage=${sessionScope.pageTrade.currentPage+1}">下一页</a></li>
    <li><a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&currentPage=${sessionScope.pageTrade.totalPage}">尾页</a></li>
    <li><a>总${sessionScope.pageTrade.totalCount}个商品</a></li>
    <li><a>当前${sessionScope.pageTrade.currentPage}页</a></li>
    <li><a>共有${sessionScope.pageTrade.totalPage}页</a></li>
    <li><form action="/market_war_exploded/TradeServlet?method=findAllTradeByUser" method="post" onsubmit="return checkAccount();">
        <input type="text" name="userAccount" placeholder="搜索用户账户" class="input">
        <button type="submit">搜索用户账户</button>
    </form></li>
    <li><a style="color: red">${sessionScope.noTrade}</a></li>
    <li><form action="/market_war_exploded/TradeServlet?method=findAllTradeByName" method="post">
        <input type="text" name="tradeName" placeholder="搜索商品名称" class="input" >
        <button type="submit">搜索商品名称</button>
    </form></li>
    <li><a style="color: red">${sessionScope.noName}</a></li>
</ul>
<div class="good">
    <c:forEach items="${sessionScope.pageTrade.list}" var="trade">
        <div class="img">
            <a href="/market_war_exploded/TradeServlet?method=findThisTrade&pageId=${trade.tradeId}&buyer=1">
                <img src="http://localhost:8080/market_war_exploded/image/${trade.tradePhoto}" alt="图片文本描述" width="300" height="200">
                <div class="desc">商品名称：${trade.tradeName}</div>
                <div class="desc">商品价格：${trade.tradePrice}</div>
            <div class="desc">商品已售数量：${trade.tradeBeenAmount}</div>
                <c:if test="${sessionScope.user.userIdentity=='管理员'}">
                    <div class="desc"><a href="/market_war_exploded/TradeServlet?method=adminSendBackTrade&tradeId=${trade.tradeId}" onclick="return checkSendBack();">退回商品</a></div>
                </c:if>
            </a>
        </div>
    </c:forEach>
</div>
<div class="sort">
    <a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&priceSort=1" class="a">按价格升序</a>
    <a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&priceSort=2" class="a">按价格降序</a>
    <a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&priceSort=3" class="a">按销量降序</a>
    <a href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}&priceSort=4" class="a">按销量升序</a>
    <div class="q">
    <a class="b">商品分类：</a>
    <a class="b" href="/market_war_exploded/TradeServlet?method=findAllTradeByType&tradeType=日用品">日用品</a>
    <a class="b" href="/market_war_exploded/TradeServlet?method=findAllTradeByType&tradeType=服装">服装</a>
    <a class="b" href="/market_war_exploded/TradeServlet?method=findAllTradeByType&tradeType=学习">学习</a>
    <a class="b" href="/market_war_exploded/TradeServlet?method=findAllTradeByType&tradeType=食品">食品</a>
    <a class="b" href="/market_war_exploded/TradeServlet?method=findAllTradeByType&tradeType=其他">其他</a>
        <a class="b" style="position:absolute;top: 35px;" href="/market_war_exploded/TradeServlet?method=findAllTrade">查看全部类型</a>
    </div>
</div>
</body>
<c:if test="${not empty sessionScope.youCanNot}">
    <script type="text/javascript">alert("不可以耍小聪明哦")</script>
   <c:remove var="youCanNot" scope="session"/>
</c:if>
<script type="text/javascript">
    function checkAccount() {
        var userAccount=document.getElementsByName("userAccount")[0].value;
        if(!userAccount.match("^[A-Za-z0-9]+$")) {
            alert("账户输入格式错误");
            return false;
        }
        return true;
    }
    
    function checkSendBack() {
        if(confirm("确定退回该商品？")) {
            return true;
        }
        else {
            return false;
        }
    }
</script>
</html>
