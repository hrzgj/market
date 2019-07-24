<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/6
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Title</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindCar.css"/>
<ul>
    <li><a class="active" href="Main.jsp">返回主页</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=sellFindSendOrder">查看已发送</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=sellFindDeliverOrder">查看已发货</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=sellFindTakeOrder">查看已收货</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=sellFindRefuseOrder">查看拒绝发货</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=findSellerOrder">查看全部</a></li>
    <li><button class="button4" id="bt01" >导出当前显示订单</button></li>
</ul>
<c:forEach items="${sessionScope.sellerOrder.orderList}" var="order">
    <div class="life" style="height:230px;">
        <div>
        <div class="img">
        <a href="/market_war_exploded/TradeServlet?method=findThisTrade&pageId=${order.trades.tradeId}&buyer=1&seller=1">
        <img class="img" src="../image/${order.trades.tradePhoto}" alt="图片文本描述" width="300" height="200">
        <div class="desc">订单号${order.orderNumber}</div>
        <div class="desc">商品名称:${order.trades.tradeName}</div>
        </a>
        </div>
            <div class="price">购买者：${order.user.userAccount}</div>
            <div class="amountPrice" style="left: 0px; top:10px;">收货地：${order.user.userAddress}</div>
            <div class="phone">手机号：${order.user.userPhone}</div>
            <div class="amount">购买量：${order.tradesAmount}</div>
            <div>购买者昵称：${order.user.userName}</div>
            <div class="amountPrice">应付款：${order.orderOriginSum}</div>
            <div class="factPay">实付款:${order.orderSum}</div>
            <div class="time">订单创建时间：${order.orderTime}</div>
            <div class="state">订单状态：${order.orderState}</div>
        </div>
        <c:if test="${order.orderState=='已发送'}">
            <a href="/market_war_exploded/UserServlet?method=sellDeliverOrder&orderId=${order.orderId}" class="a send">发货</a>
            <a href="/market_war_exploded/UserServlet?method=sellRefuseOrder&orderId=${order.orderId}" class="a refuse">拒绝发货</a>
        </c:if>
        <c:if test="${order.orderState=='已收货'||order.orderState=='拒绝发货'}">
            <a href="/market_war_exploded/UserServlet?method=sellRemoverOrder&orderId=${order.orderId}" class="a send">删除订单</a>
        </c:if>
        <c:if test="${order.orderState=='已收货'&&not empty order.orderComment}">
            <a class="comment">用户评价：${order.orderComment}</a>
            <a class="grade">用户评分：${order.orderStar}</a>
            <c:if test="${empty order.orderReply}">
                <form action="/market_war_exploded/UserServlet?method=sellReply&orderId=${order.orderId}" method="post" onsubmit="return checkContent();">
                    <input type="text" name="orderReply" placeholder="回复用户内容" class="reply"  />
                    <input type="submit" class="button1 replySubmit"/>
                </form>
            </c:if>
            <c:if test="${not empty order.orderReply}">
                <span class="replyRight">已回复</span>
            </c:if>
        </c:if>
    </div>
</c:forEach>
</body>
<c:if test="${not empty sessionScope.orderReply}">
    <script type="text/javascript">alert("回复成功")</script>
    <c:remove var="orderReply" scope="session"/>
</c:if>
<script type="text/javascript">
    var bt01 = document.getElementById("bt01");
    bt01.onclick = function() {
        window.location.href="/market_war_exploded/UserServlet?method=sellExportFile";
        bt01.disabled = true;//当点击后倒计时时候不能点击此按钮
        var time = 60;  //倒计时5秒
        var timer = setInterval(fun1, 1000);  //设置定时器
        function fun1() {
            time--;
        if (time >= 0) {
             bt01.innerHTML = time + "s后可再次导出";
        } else {
             bt01.innerHTML = "导出订单";
             bt01.disabled = false;  //倒计时结束能够重新点击发送的按钮
        clearTimeout(timer);  //清除定时器
        time = 5;  //设置循环重新开始条件
        }
    }
}
</script>
</html>
