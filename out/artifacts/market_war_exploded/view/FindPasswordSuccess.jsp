<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>恭喜找回密码成功，记住这次密码哦，稍后进入登录界面</center>

<%
    response.setHeader("refresh","3;url=http://localhost:8080/market_war_exploded");
%>
</body>
</html>
