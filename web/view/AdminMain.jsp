<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员界面</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/AdminMain.css"/>
<ul>
    <li><a class="active" href="/market_war_exploded/TradeServlet?method=findAllTrade">商品中心</a></li>
    <li class="dropdown">
        <a href="#" class="dropbtn">个人中心</a>
        <div class="dropdown-content">
            <a href="UpdateUser.jsp">修改自己信息</a>
            <a href="UpdateUserPassword.jsp" >修改密码</a>
        </div>
    </li>
    <li class="dropdown">
        <a href="#" class="dropbtn">用户中心</a>
        <div class="dropdown-content">
            <a href="/market_war_exploded/UserServlet?method=findAllUser">查看所有用户</a>
            <a href="/market_war_exploded/TradeServlet?method=adminFindCheckTrade">审核用户发布二手商品</a>
        </div>
    </li>
    <li><a href="http://localhost:8080/market_war_exploded">退出登录</a></li>
</ul>
</body>
</html>
