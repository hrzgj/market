<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>恭喜${sessionScope.user.userName}注册成功，您是${sessionScope.user.userIdentity}稍后进入主界面</center>

<%
    response.setHeader("refresh","3;url=Main.jsp");
%>
</body>
</html>
