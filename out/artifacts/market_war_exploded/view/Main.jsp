<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/29
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>主界面</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/Main.css"/>
<ul>
    <li><a href="FindUserMessage.jsp" >查看个人信息</a></li>
    <li><a href="UpdateUser.jsp"  >修改个人信息</a></li>
    <li><a href="UpdateUserPassword.jsp" >修改密码</a></li>
    <li><a href="http://localhost:8080/market_war_exploded" onclick="removeAllSession()">退出登录</a></li>
</ul>
<div>
    <form action="/market_war_exploded/UserServlet?method=updateUserPhoto"  enctype="multipart/form-data" accept-charset="UTF-8" method="post" onsubmit="return checkPhoto();">
        <input type="file" class="inputPicture" name="image"><button type="submit">点击修改头像</button></form>
    <img src="http://localhost:8080/image/${sessionScope.user.userPhoto}" alt="用户的头像">
</div>
<div class="div2">
    <h3>买家操作</h3>
        <a href="/market_war_exploded/TradeServlet?method=findAllTrade" class="a1">浏览二手商品</a>
        <a href="/market_war_exploded/CarServlet?method=findCar" class="a2">查看购物车</a>
        <a href="/market_war_exploded/UserServlet?method=findBuyerOrder" class="a1">查看订单</a>
</div>
<div class="div3">
    <h3>卖家操作</h3>
        <c:if test="${sessionScope.user.userCan == '是'}">
            <a href="AddTrade.jsp" class="a1">发布二手商品</a>
        </c:if>
        <c:if test="${sessionScope.user.userCan == '否'}">
            <button id="myBtn" class="button5">你已被禁售点击可发送申诉</button>
            <span style="position:absolute;left:163px;top:76px;">禁售原因：${sessionScope.user.userReason}</span>
        </c:if>
        <a href="/market_war_exploded/TradeServlet?method=findTradeByUser" class="a2">查看修改自己发布的商品</a>
        <a href="/market_war_exploded/UserServlet?method=findSellerOrder" class="a1">查看其它用户提交的订单</a>
</div>
<!-- 弹窗 -->
<div id="myModal" class="modal">

    <!-- 弹窗内容 -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <form onsubmit="return checkAppeal();" action="/market_war_exploded/UserServlet?method=appealSell" method="post" >
            <label>申诉原因：<input style="width:300px;" type="text" placeholder="请输入申诉原因" name="appeal"></label>
            <button class="button" style="vertical-align:middle" type="submit"><span>点击发送</span></button>
        </form>
    </div>

</div>
</body>
<c:if test="${not empty sessionScope.outDated||empty sessionScope.user}">
    <script type="text/javascript">alert("登录状态过时了，请重新登录")</script>
    <c:remove var="outDated" scope="session"/>
    <%response.setHeader("refresh","0;url=http://localhost:8080/market_war_exploded");%>
</c:if>
<script type="text/javascript">
    function checkPhoto() {
        var fileInput = document.getElementsByClassName("inputPicture")[0];
        if(!fileInput.value){
            alert("没有选择文件！");
            return false;
        }
        if(fileInput.files[0].size > 2*1024*1024){
            alert("文件大于2M！");
            return false;
        }
        if(fileInput.files[0].type !== 'image/jpeg' && fileInput.files[0].type !== 'image/jpg' && fileInput.files[0].type !== 'image/gif' && fileInput.files[0].type !== 'image/png'){
            alert("不是有效的图片格式！");
            return false;
        }
        if(fileInput.value && fileInput.files[0].size <= 2*1024*1024){
            return true;
        }
    }
    function checkAppeal() {
        var appeal=document.getElementsByName("appeal")[0].value;
        if(!appeal.match("^.{1,100}$")){
            alert("输入格式错误");
            return false;
        }
        return true;
    }
    function removeAllSession() {
        sessionStorage.clear();
    }
</script>
<script type="text/javascript">
    // 获取弹窗
    var modal = document.getElementById('myModal');

    // 打开弹窗的按钮对象
    var btn = document.getElementById("myBtn");

    // 获取 <span> 元素，用于关闭弹窗
    var span = document.querySelector('.close');

    // 点击按钮打开弹窗
    btn.onclick = function() {
        modal.style.display = "block";
    };

    // 点击 <span> (x), 关闭弹窗
    span.onclick = function() {
        modal.style.display = "none";
    };

    // 在用户点击其他地方时，关闭弹窗
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>
</html>
