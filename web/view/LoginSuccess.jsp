<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/29
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>恭喜${sessionScope.user.userName}登录成功，您是${sessionScope.user.userIdentity}稍后进入主界面</center>
<c:if test="${sessionScope.user.userIdentity eq '普通用户'}">
<%
    response.setHeader("refresh","3;url=Main.jsp");
%>
</c:if>
<c:if test="${sessionScope.user.userIdentity eq '管理员'}">
    <%response.setHeader("refresh","3;url=AdminMain.jsp");%>
</c:if>

</body>
</html>
