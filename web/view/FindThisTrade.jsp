<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/3
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Title</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindThisTrade.css"/>
<div class="flex-container">
    <div class="row">
        <div class="column side">
            <h2>商品图片</h2>
            <img src="../image/${requestScope.thisTrade.tradePhoto}">
        </div>
        <div class="column middle">
            <h2>商品名称:${requestScope.thisTrade.tradeName} 商品种类：${requestScope.thisTrade.tradeKind}</h2>
            价格:<span style="color: red;font-size: 40px">${requestScope.thisTrade.tradePrice}</span>元
            <p>商品数量：<span style="font-size: 40px">${requestScope.thisTrade.tradeAmount}</span></p>
            <p>商品发售者：${requestScope.thisTrade.tradeUser}</p>
            <p>商品已售数量：<span style="font-size: 40px">${requestScope.thisTrade.tradeBeenAmount}</span></p>
        </div>
        <div class="column side">
            <h2>商品介绍：</h2>
            <p>${requestScope.thisTrade.tradeIntroduce}</p>
            <c:if test="${sessionScope.user.userIdentity=='普通用户'&&empty requestScope.seller}">
            <form method="post" action="/market_war_exploded/CarServlet?method=addTradeToCar&tradeId=${requestScope.thisTrade.tradeId}" onsubmit="return checkAmount();">
                <label>购买数量：<input type="text" name="carAccount"></label>
                <button type="submit">确定加入购物车</button>
            </form>
            </c:if>
        </div>
        <div class="sell">${requestScope.seller}</div>
    </div>
</div>
<div>
    <c:if test="${sessionScope.user.userIdentity=='管理员'}">
    <ul>
        <li><a class="active" href="view/BrowseTrade.jsp">返回商品中心</a></li>
        <li><a href="/market_war_exploded/TradeServlet?method=adminFindCheckTrade">返回审核主页</a></li>
        <c:if test="${requestScope.thisTrade.tradeState=='审核'}">
        <li><a href="/market_war_exploded/TradeServlet?method=checkTrade&pageId=${requestScope.thisTrade.tradeId}" onclick="return confirm();">通过审核</a></li></c:if>
    </ul>
    </c:if>
    <c:if test="${sessionScope.user.userIdentity=='普通用户'}">
        <ul>
            <li><a class="active" href="/market_war_exploded/TradeServlet?method=${sessionScope.judgeTrade}">返回</a></li>
            <c:if test="${empty requestScope.seller}">
            <li><a href="/market_war_exploded/CarServlet?method=addTradeToCar&tradeId=${requestScope.thisTrade.tradeId}&carAccount=1">加入购物车</a></li>
            </c:if>
        </ul>
    </c:if>
    <c:if test="${empty sessionScope.user.userIdentity}">
        <ul><li><a href="view/BrowseTrade.jsp" class="active">点击返回浏览商品</a></li>
            <li><a href="http://localhost:8080/market_war_exploded">想要购买？快点登录吧</a></li>
            <li><a href="view/Register.jsp">或者注册一个账号吧</a></li>
        </ul></c:if>
</div>
</body>
<c:if test="${not empty requestScope.exceedAccount}">
    <script type="text/javascript">alert("商品不够你买了，买少点")</script>
    <c:remove var="exceedAccount" scope="request"/>
</c:if>
<script type="text/javascript">
    function confirm() {
        return confirm("确定通过审核？");
    }
    function checkAmount() {
        var carAmount=document.getElementsByName("carAccount")[0].value;
        if(!carAmount.match("^[1-9]\\d*$")){
            alert("输入错误请重新输入");
            return false;
        }
        return true;
    }
</script>
</html>
