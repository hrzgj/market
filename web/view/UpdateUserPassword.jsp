<%@ page import="com.chenyu.www.util.PasswordEndrypt" %><%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>更新用户密码</title>
</head>
<style>
    input[type=password], select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type=submit] {
        width: 100%;
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type=submit]:hover {
        background-color: #45a049;
    }

    div {
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
    }
    a:link,a:visited
    {
        font-weight: bold;
        color:#FFFFFF;
        background-color: #8ebfbf;
        width:120px;
        text-align:center;
        padding:4px;
        text-decoration:none;
    }
    a:hover,a:active
    {
        background-color: #959999;
    }
</style>
<body style="background:url(../image/timg.jpg);">
<c:if test="${sessionScope.user.userIdentity=='普通用户'}">
<a href="Main.jsp">点击返回主菜单</a></c:if>
<c:if test="${sessionScope.user.userIdentity=='管理员'}">
    <a href="AdminMain.jsp">点击返回主菜单</a>
</c:if>
<div>
    <form action="/market_war_exploded/UserServlet?method=updatePassword" method="post" onsubmit="return check();">
        <label >旧密码：</label><input type="password" name="usersOld"  >${requestScope.passwordError}
        <label>新密码</label><input type="password" name="userNew" value="${requestScope.newWord}">
        <input type="submit" value="修改密码"/>
    </form>
</div>
</body>
<script type="text/javascript">
    function check() {
        var oldPassword=document.getElementsByName("usersOld")[0].value;
        var newPassword=document.getElementsByName("userNew")[0].value;
        if(!oldPassword.match("^.{6,15}$")||!oldPassword.match("^[A-Za-z0-9]+$")) {
            alert("旧密码输入格式错误");
            return false;
        }
        if(!newPassword.match("^.{6,15}$")||!newPassword.match("^[A-Za-z0-9]+$")) {
            alert("新密码输入格式错误");
            return false;
        }
        return true;
    }
</script>
</html>
