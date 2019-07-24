<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>更改用户信息</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/Register.css" />
<c:if test="${sessionScope.user.userIdentity=='普通用户'}">
    <a href="Main.jsp" class="a3">点击返回主菜单</a></c:if>
<c:if test="${sessionScope.user.userIdentity=='管理员'}">
    <a href="AdminMain.jsp" class="a3">点击返回主菜单</a>
</c:if>
<div id="update">
    <form action="/market_war_exploded/UserServlet?method=updateUser" method="post" onsubmit="return check();">
        <p></p><span style="color: green">账号：</span>${sessionScope.user.userAccount}
        <p></p><span style="color: green">身份证：</span>${sessionScope.user.userIdCard}
        <p></p><span style="color: green">昵称:</span><label><input value="${sessionScope.user.userName}" name="userName" placeholder="昵称：32位内的字符"></label>
        <p></p><span style="color: green">手机号：</span><label><input value="${sessionScope.user.userPhone}" name="userPhone" placeholder="11位数字"></label>
        <p></p><span style="color: green">发货收货地址：</span><label><input value="${sessionScope.user.userAddress}" name="userAddress"></label>
        <p><button class="button2" type="submit">点击修改</button></p>
    </form>
</div>
</body>
<script type="text/javascript">
    function check() {
        var userName=document.getElementsByName("userName")[0].value;
        var userPhone=document.getElementsByName("userPhone")[0].value;
        var userAddress=document.getElementsByName("userAddress")[0].value;
        if(!userName.match("^.{1,32}$")||!userName.match("^[\u4E00-\u9FA5A-Za-z0-9]+$")){
            alert("昵称格式输入错误");
            return false;
        }
        if(!userPhone.match("^\\d{11}$")){
            alert("手机格式输入错误");
            return false;
        }
        if(!userAddress.match("^[\u4E00-\u9FA5A-Za-z0-9]+$")||!userAddress.match("^.{1,100}$")){
            alert("地址输入错误");
            return false;
        }
        return true;
    }
</script>
</html>
