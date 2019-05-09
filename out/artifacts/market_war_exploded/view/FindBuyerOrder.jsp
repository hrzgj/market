<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/6
  Time: 20:16
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
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<ul>
    <li><a class="active" href="Main.jsp">返回主页</a></li>
    <li ><a href="/market_war_exploded/UserServlet?method=buyFindSendOrder">查看已发送</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=buyFindDeliverOrder">查看已发货</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=buyFindTakeOrder">查看已到货</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=buyFindRefuseOrder">查看拒绝发货</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=findBuyerOrder">查看所有</a></li>
    <li><button class="button4" id="bt01" href="">导出订单</button></li>
</ul>
<c:forEach items="${sessionScope.buyerOrder.orderList}" var="order">
    <div class="life">
        <div>
            <div class="img">
                <a href="/market_war_exploded/TradeServlet?method=findThisTrade&pageId=${order.trades.tradeId}&buyer=1">
                    <img class="img" src="../image/${order.trades.tradePhoto}" alt="图片文本描述" width="300" height="200">
                    <div class="desc">订单号${order.orderNumber}</div>
                    <div class="desc">商品名称:${order.trades.tradeName}</div>
                </a>
            </div>
            <div class="price">出售者：${order.user.userAccount}</div>
            <div class="amountPrice" style="left: 0px; top:10px;">发货地:${order.user.userAddress},收货地:${sessionScope.buyerOrder.user.userAddress}</div>
            <div class="phone">手机号：${order.user.userPhone}</div>
            <div class="amount">购买量：${order.tradesAmount}</div>
            <div>购买者昵称：${order.user.userName}</div>
            <div class="amountPrice">应付款：${order.orderOriginSum}</div>
            <div class="factPay">实付款:${order.orderSum}</div>
            <div class="time">订单时间：${order.orderTime}</div>
            <div class="state">订单状态：${order.orderState}</div>
            <c:if test="${order.orderState=='已收货'&&not empty order.orderReply}">
            <div class="reply">商家回复:${order.orderReply}</div>
            </c:if>
        </div>
        <c:if test="${order.orderState=='已发货'}">
            <a href="/market_war_exploded/UserServlet?method=buyerTakeOrder&orderId=${order.orderId}" class="a send">收货</a>
        </c:if>
        <c:if test="${order.orderState=='已发送'}">
            <a href="/market_war_exploded/UserServlet?method=deleteOrder&orderId=${order.orderId}" class="a send">取消订单</a>
        </c:if>
        <c:if test="${order.orderState=='已收货'||order.orderState=='拒绝发货'}">
            <a href="/market_war_exploded/UserServlet?method=buyRemoverOrder&orderId=${order.orderId}" class="a send">删除订单</a>
        </c:if>
        <c:if test="${order.orderState=='已收货'&&empty order.orderComment}">
            <form action="/market_war_exploded/UserServlet?method=buyCommentOrder&orderId=${order.orderId}" method="post"  onsubmit="return checkContent();">
                <input type="hidden" id="star" name="orderGrade" placeholder="评价等级1-100哦">
                <input type="hidden" id="text" name="orderComment" placeholder="订单评价内容"  />
                <input type="button" id="button" onclick="change()" value="点击评价" class="k">
                <input type="hidden" id="submit"  class="button1"/>
            </form>
        </c:if>
        <c:if test="${not empty order.orderComment}">
            <span class="commentRight">已评分和评价</span>
        </c:if>
    </div>
</c:forEach>
</body>
<c:if test="${not empty sessionScope.commentSuccess}">
    <script type="text/javascript">alert("评价成功")</script>
    <c:remove var="commentSuccess" scope="session"/>
</c:if>
<script type="text/javascript">
    function change(){
        document.getElementById("text").type = "text";
        document.getElementById("star").type = "text";
        document.getElementById("submit").type = "submit";
        document.getElementById("button").type = "hidden"
    }

    function checkContent() {
        var orderGrade=document.getElementById("star").value;
        var orderComment=document.getElementById("text").value;
        if((parseInt(orderGrade)>100||parseInt(orderGrade)<0||parseInt(orderGrade)!=orderGrade)){
            alert("评价等级只能输入1-100的范围哦");
            return false;
        }
        if(!orderComment.match("^.{1,100}$")){
            alert("评价内容格式错误");
            return false;
        }
        return true;
    }
</script>
<script type="text/javascript">
var bt01 = document.getElementById("bt01");
bt01.onclick = function() {
    window.location.href="/market_war_exploded/UserServlet?method=buyExportFile";
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
