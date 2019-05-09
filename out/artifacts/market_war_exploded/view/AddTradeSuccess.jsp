<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/2
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加商品成功</title>
</head>
<body>
<center>恭喜增加商品成功，稍后进入主界面</center>

<%
    response.setHeader("refresh","3;url=Main.jsp");
%>
</body>
</html>
