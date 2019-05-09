<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/30
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background:url(../image/timg.jpg);">
<a href="http://localhost:8080/market_war_exploded">返回登录界面</a>
<link rel="stylesheet" type="text/css" href="../css/ForgetPassword.css" />
<div id="Forget">
    <form action="/market_war_exploded/UserServlet?method=forgetPassword" method="post" onsubmit="return check();">
        <p>
            <span> 用户账户：</span>
        <label>
            <font color="red">
                ${requestScope.account}</font>
           <input type="text" name="user_account" placeholder="账号: 6-20位的数字英文组合" >

        </label>
        </p>
        <br><br><br><br>
        <p>
        <label>
            <span>用户身份证：</span><input type="text" name="user_idCard" >
            <font color="red">
                ${requestScope.idCard}</font>
        </label>
        </p>
        <br><br><br><br>
        <p>
            <span>新密码：</span><label>
            <input type="password" name="user_password">
        </label>
        </p>
        <button type="submit">找回密码</button>
    </form>
</div>
</body>
<script type="text/javascript">
    function check() {
        var userAccount=document.getElementsByName("user_account")[0].value;
        var userIdCard=document.getElementsByName("user_idCard")[0].value;
        var userPassword=document.getElementsByName("user_password")[0].value;
        if(!userAccount.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")) {
            alert("账户输入格式错误");
            return false;
        }
        if(!userPassword.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")) {
            alert("新密码输入格式错误");
            return false;
        }
        if(!userIdCard.match("^\\d{15}|\\d{18}$")){
            alert("身份证格式输入错误");
            return false
        }
        return true;
    }
</script>
</html>
