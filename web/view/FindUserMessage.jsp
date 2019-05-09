
<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>个人信息</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindUserMessage.css" />
<c:if test="${sessionScope.user.userIdentity=='普通用户'}">
    <a href="Main.jsp" class="a3">点击返回主菜单</a></c:if>
<c:if test="${sessionScope.user.userIdentity=='管理员'}">
    <a href="AdminMain.jsp" class="a3">点击返回主菜单</a>
</c:if>
<div>
    <img src="http://localhost:8080/image/${sessionScope.user.userPhoto}" class="image1" alt="用户头像">
</div>
<div class="div2" >
    <span>账户: ${sessionScope.user.userAccount}</span>
    <br>
    <br>
    <span>昵称：${sessionScope.user.userName}</span>
    <br>
    <span>地址：${sessionScope.user.userAddress}</span>
    <br>
    <span>手机号:${sessionScope.user.userPhone}</span>
    <br>
    <span>用户星级：<c:forEach var="i" begin="1" end="${sessionScope.user.userStar}">
        <img src="../image/star.jpg" height="5%" width="5%">
</c:forEach>
        </span>
    <br>
    <span>用户积分:<fmt:formatNumber type="number" value="${sessionScope.user.userPoint}" maxFractionDigits="2"/></span>
    <br>
    <span>用户等级：${sessionScope.user.userGrade}</span>
    <br>
    <span>用户能否发售:${sessionScope.user.userCan}</span>
</div>
</body>
</html>
