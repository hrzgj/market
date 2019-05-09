<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/4/29
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册界面</title>
    <script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
</head>
<body style="background:url(../image/timg.jpg);">
<a href="http://localhost:8080/market_war_exploded">返回登录界面</a>
<link rel="stylesheet" type="text/css" href="../css/Register.css" />
<div id="register">
    <table align="center">
        <h1 style="color: red;">用户注册</h1>
        <form  action="/market_war_exploded/UserServlet?method=registerUser" method="post" enctype="multipart/form-data" accept-charset="UTF-8" onsubmit="return check();">
            <label>账号：<input type="text" name="user_account" placeholder="账号: 6-20位的数字英文组合" id="userAccount"><span  id="haveUserAccount" style="position:absolute;top:102px;left:165px;"></span></label>
            <p>密码：<label><input type="password" name="user_password" placeholder="密码: 6-20位的数字英文组合"></label></p>
            <p>昵称： <label><input type="text" name="user_name" placeholder="昵称：32位内的字符"></label></p>
            <p>手机号: <label><input type="text" name="user_phone" placeholder="手机号为11个数字"></label></p>
            <p>身份证: <label><input type="text" name="user_idCard" id="userIdCard"></label><span id="haveUserIdCard" style="position: absolute;left: 163px;top: 256px;"></span></p>
            <p>用户地址：<label><input type="text" name="user_address"></label></p>
            <p><span>用户头像：</span><input type="file" name="image" class="button1" ></p>
            <p>验证码：<label><input name="picture" type="text"  size="3" /></label>
                <img src="/market_war_exploded/VerifyCodeServlet" id="img" alt="验证码">
                <a href="javascript:change()">换一张</a>
                <font color="red">${requestScope.msg}</font>
            </p>
            <p><button class="button2" type="submit">点击注册</button></p>
        </form>
    </table>
</div>
</body>
<script type="text/javascript">
    //更换验证码
    function change() {
        var imgEle = document.getElementById("img");
        imgEle.src ="/market_war_exploded/VerifyCodeServlet?a=" + new Date().getTime();
    }
    //简单判断表单输入是否正确
    function check() {
        var userAccount=document.getElementsByName("user_account")[0].value;
        var userPassword=document.getElementsByName("user_password")[0].value;
        var userName=document.getElementsByName("user_name")[0].value;
        var userPhone=document.getElementsByName("user_phone")[0].value;
        var userIdCard=document.getElementsByName("user_idCard")[0].value;
        var userAddress=document.getElementsByName("user_address")[0].value;
        var fileInput=document.getElementsByName("image")[0];
        if(!userAccount.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")) {
            alert("账户输入格式错误");
            return false;
        }
        if(!userPassword.match("^.{6,15}$")||!userPassword.match("^[A-Za-z0-9]+$")) {
            alert("密码输入格式错误");
            return false;
        }
        if(userName.length>32||userName.length<0){
            alert("昵称格式输入错误");
            return false;
        }
        if(!userPhone.match("^\\d{11}$")){
            alert("手机格式输入错误");
            return false;
        }
        if(!userIdCard.match("^\\d{15}|\\d{18}$")){
            alert("身份证格式输入错误");
            return false
        }
        if(!userAddress.match("^[\u4e00-\u9fa5]{0,}$")){
            alert("地址输入错误");
            return false;
        }
        if(!fileInput.value){
            alert("没有选择文件！");
            return false;
        }
        if(fileInput.files[0].size > 2*1024*1024){
            alert("文件大于2M！");
            return false;
        }
        if(fileInput.files[0].type !== 'image/jpeg' && fileInput.files[0].type !== 'image/jpg' && fileInput.files[0].type !== 'image/gif' && fileInput.files[0].type !== 'image/png'){
            alert("不是有效的图片格式！");
            return false;
        }
        return true;
    }

    $(function () {
        //判断用户账户是否存在
        $("#userAccount").blur(function () {
            var userAccounr=$(this).val();
            $.post("/market_war_exploded/UserServlet?method=haveAccount",{userAccount:userAccounr},function (data) {
                var span=$("#haveUserAccount");
                //用户名存在
                if(data.userExist){
                    span.css("color","red");
                    span.html(data.msg);
                }else {
                    //不存在
                    span.css("color","green");
                    span.html(data.msg);
                }
            },"json");
        });
        //判断用户身份证是否存在
        $("#userIdCard").blur(function () {
            var userIdCard=$(this).val();
            $.post("/market_war_exploded/UserServlet?method=haveIdCard",{userIdCard:userIdCard},function (data) {
                var span=$("#haveUserIdCard");
                if(data.userCard){
                    span.css("color","red");
                    span.html(data.msg);
                }else {
                    //不存在
                    span.css("color","green");
                    span.html(data.msg);
                }
            },"json");
        });
    })
</script>
</html>
