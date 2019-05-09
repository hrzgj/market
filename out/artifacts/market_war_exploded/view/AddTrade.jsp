<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/1
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加商品</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/Register.css" />
<a href="Main.jsp" class="a3">点击返回主菜单</a>
<div id="addTrade">
    <form action="/market_war_exploded/TradeServlet?method=addTrade" method="post" enctype="multipart/form-data"  accept-charset="UTF-8" onsubmit="return check();">
        <p><label>商品价格：<input type="text" name="tradePrice" class="input1" placeholder="请输入大于0的数"></label></p>
        <p><label>商品数量：<input type="text" name="tradeAmount"class="input1" placeholder="请输入大于0的数"></label></p>
        <p><label>商品名称：<input type="text" name="tradeName" class="input1"></label></p>
        <p><label>商品种类：<select name="tradeKind">
            <option value="日用品">日用品</option>
            <option value="服装">服装</option>
            <option value="学习">学习</option>
            <option value="食品">食品</option>
            <option value="其他">其他</option>
        </select></label></p>
        <p><label>商品介绍：<textarea class="input1" name="tradeIntroduce" style="width: 100%;height: 109px"></textarea></label></p>
        <p>验证码：<label><input name="picture" type="text"  size="3"/></label><img src="/market_war_exploded/VerifyCodeServlet" id="img" alt="验证码">
            <a href="javascript:change()">换一张</a><font color="red">${requestScope.msg}</font></p>
        <p><label>商品图片：<input class="input1" type="file" name="tradeImage" ></label></p>
        <br><p><button type="submit" class="button3">点击添加，等待管理员审核</button></p>

    </form>
</div>
</body>
<script type="text/javascript">
    function change() {
        var imgEle = document.getElementById("img");
        imgEle.src ="/market_war_exploded/VerifyCodeServlet?a=" + new Date().getTime();
    }

    function check() {
        var tradePrice=document.getElementsByName("tradePrice")[0].value;
        var tradeAmount=document.getElementsByName("tradeAmount")[0].value;
        var tradeName=document.getElementsByName("tradeName")[0].value;
        var tradeIntroduce=document.getElementsByName("tradeIntroduce")[0].value;
        var fileInput=document.getElementsByName("tradeImage")[0];
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
        if(tradePrice == '' || tradePrice < 0){
            alert("商品价格格式错误");
            return false;
        }
        if(tradeAmount == '' || tradeAmount < 0){
            alert("商品数量格式错误");
            return false;
        }
        if(tradeName == '' || tradeName < 0){
            alert("商品名称格式错误");
            return false;
        }
        if(tradeIntroduce == '' || tradeIntroduce < 0){
            alert("商品介绍格式错误");
            return false;
        }
        return true;
    }
</script>
</html>
