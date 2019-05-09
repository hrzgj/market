<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/29
  Time: 1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <title>登录界面</title>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/Login.css"/>
<div id="head">二手市场</div>
<div id="login">
    <table >
        <h1 style="color: red;" >用户登录</h1>
        <span style="color: red;">${requestScope.error}</span>
        <form  action="UserServlet?method=login" method="post" onsubmit="return check();">
            <p><span style="color: green">账号：</span><label>
            <input type="text" name="user_account" placeholder="账号: 6-15位的数字英文组合" value="${requestScope.userAccount}" ></label></p>
            <p><span style="color: green">密码：</span><label><input type="password" name="user_password" placeholder="密码：6-15位的数字英文组合"></label></p>
            <p><button class="button2" type="submit">点击登陆</button></p>
        </form>
        <a href="view/ForgetPassword.jsp" class="a2">忘记密码</a>
    </table>
    <table ><tr><a href="view/Register.jsp" class="button2">注册账户</a></tr></table>
    <a href="TradeServlet?method=visit" class="a2" onclick="clear()" id="visitor">游客登录</a>
</div>
</body>

<script type="text/javascript">
    function check() {
        var userAccount=document.getElementsByName("user_account")[0].value;
        var userPassword=document.getElementsByName("user_password")[0].value;
        if(!userAccount.match("^.{6,15}$")||!userAccount.match("^[A-Za-z0-9]+$"))
        {
            alert("账户输入格式错误");
            return false;
        }
        else if(!userPassword.match("^.{6,15}$")||!userPassword.match("^[A-Za-z0-9]+$"))
        {
            alert("密码输入格式错误");
            return false;
        }
        else return true;
    }
</script>




</html>

