<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/4
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>用户发布的商品</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindTrade.css"/>
<ul>
    <li><a href="view/Main.jsp" class="ah">点击返回主菜单</a></li>
    <li><a href="/market_war_exploded/TradeServlet?method=findTradeByUser&currentPage=1">首页</a></li>
    <c:if test="${requestScope.userPage.currentPage !=1&&requestScope.userPage.currentPage>1}">
    <li> <a href="/market_war_exploded/TradeServlet?method=findTradeByUser&currentPage=${requestScope.pageTrade.currentPage-1}">上一页</a></c:if></li>
    <c:if test="${requestScope.userPage.currentPage!=requestScope.userPage.totalPage}">
        <li><a href="/market_war_exploded/TradeServlet?method=findTradeByUser&currentPage=${requestScope.userPage.currentPage+1}" >下一页</a></li>
    </c:if>
<c:if test="${requestScope.userPage.currentPage>1}">
    <li><a href="/market_war_exploded/TradeServlet?method=findTradeByUser&currentPage=${requestScope.userPage.totalPage}" >尾页</a></li></c:if>
</ul>
<div class="good">
    <c:forEach items="${requestScope.userPage.list}" var="trade">
        <div class="img">
            <a href="/market_war_exploded/TradeServlet?method=findThisTrade&pageId=${trade.tradeId}"><img src="../image/${trade.tradePhoto}" alt="图片文本描述" width="300" height="200">
                <c:if test="${sessionScope.user.userIdentity=='管理员'}">
                <div class="desc">用户账户${trade.tradeUser}</div></a>
            <a class="goods" href="/market_war_exploded/TradeServlet?method=checkTrade&pageId=${trade.tradeId}" onclick="return confirm();">通过审核</a></c:if>
            <c:if test="${sessionScope.user.userIdentity=='普通用户'}">
                <div class="desc">商品名称：${trade.tradeName}</div>
                <div class="desc">商品状态：${trade.tradeState}</div>
                <c:if test="${trade.tradeState=='审核'&&trade.tradeBeenAmount>0}">
                    <div class="desc"><a href="/market_war_exploded/TradeServlet?method=readyRemoveTrade&tradeId=${trade.tradeId}">申请删除商品</a></div>
                </c:if>
                <c:if test="${trade.tradeState=='审核'&&trade.tradeBeenAmount==0}">
                    <div class="desc"><a href="/market_war_exploded/TradeServlet?method=removeTrade&tradeId=${trade.tradeId}">删除商品</a></div>
                </c:if>
            </c:if>
        </div>
    </c:forEach>
</div>
</body>
</html>
