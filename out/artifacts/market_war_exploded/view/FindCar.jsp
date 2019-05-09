<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/5
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindCar.css"/>
<ul>
    <li><a class="active" href="Main.jsp">返回</a></li>
    <li><a href="/market_war_exploded/CarServlet?method=addCarToOrder">全部添加订单(不使用积分)</a></li>
    <li><a href="/market_war_exploded/CarServlet?method=removeAll">全部移除购物车</a></li>
    <li style="float:right"><a>合计：<fmt:formatNumber type="number" value="${sessionScope.userCar.tradesPrice}" maxFractionDigits="2"/> </a></li>
</ul>
<c:forEach items="${sessionScope.userCar.trades}" var="trade" varStatus="loop">
    <div class="life">
        <div class="img">
            <a href="/market_war_exploded/TradeServlet?method=findThisTrade&pageId=${trade.tradeId}&buyer=1">
                <img src="../image/${trade.tradePhoto}" alt="图片文本描述" width="300" height="200">
            </a>
            <div class="desc">商品名称:${trade.tradeName}</div>
            <div class="desc">商品类型:${trade.tradeKind}</div>
        </div>
        <div class="introduce">${trade.tradeIntroduce}</div>
        <div class="price">单价${trade.tradePrice}</div>
        <div class="amountPrice">小计<fmt:formatNumber type="number" value="${trade.tradePrice*sessionScope.userCar.tradeAmount[loop.count-1]}" maxFractionDigits="2"/></div>
        <div class="amount">购买数量：${sessionScope.userCar.tradeAmount[loop.count-1]}</div>
        <div class="buyWithPoint">使用积分购买:<c:if test="${(trade.tradePrice*sessionScope.userCar.tradeAmount[loop.count-1]-sessionScope.user.userPoint/10)>=0}">
<!--商品使用积分购买所需金钱公式：小计减去用户积分的10分钟之一-->
<fmt:formatNumber type="number" value="${trade.tradePrice*sessionScope.userCar.tradeAmount[loop.count-1]-sessionScope.user.userPoint/10}" maxFractionDigits="2"/></c:if>
        <c:if test="${((trade.tradePrice*sessionScope.userCar.tradeAmount[loop.count-1]*10-sessionScope.user.userPoint)/10)<0}">0</c:if></div>
        <div class="getPoint">购买可获得积分:<fmt:formatNumber type="number" value="${trade.tradePrice*sessionScope.userCar.tradeAmount[loop.count-1]/11}" maxFractionDigits="2"/></div>
        <a class="a addGet" href="/market_war_exploded/UserServlet?method=addOneOrderWithPoint&tradeId=${trade.tradeId}&tradeAmount=${sessionScope.userCar.tradeAmount[loop.count-1]}">使用积分提交订单</a>
        <a class="add a" href="/market_war_exploded/UserServlet?method=addOneOrder&tradeId=${trade.tradeId}&tradeAmount=${sessionScope.userCar.tradeAmount[loop.count-1]}">提交订单</a>
        <a class="remove a" href="/market_war_exploded/CarServlet?method=removeOne&tradeId=${trade.tradeId}">移除商品</a>
        <a class="removeOne a" href="/market_war_exploded/CarServlet?method=removeOneAmount&tradeId=${trade.tradeId}">移除一个</a>
       <div class="cut">-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</div>
    </div>
</c:forEach>
</body>
</html>
