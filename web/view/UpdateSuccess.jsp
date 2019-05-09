<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>恭喜修改成功，稍后进入主界面</center>

<%
    response.setHeader("refresh","3;url=Main.jsp");
%>
</body>
</html>
