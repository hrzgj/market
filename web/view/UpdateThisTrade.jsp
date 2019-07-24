<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/4
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindThisTrade.css"/>
<div class="flex-container">
    <div class="row">
        <div class="column side"></div>
        <form action="/market_war_exploded/TradeServlet?method=updateTradePhoto&tradeId=${requestScope.thisTrade.tradeId}" method="post" enctype="multipart/form-data" accept-charset="UTF-8" onsubmit="return checkPhoto();">
        <button type="submit" class="button button1">商品图片,点击更改图片</button>
            <input type="file" class="file" name="image">
        </form>
        <img src="../image/${requestScope.thisTrade.tradePhoto}">
    </div>
    <form action="/market_war_exploded/TradeServlet?method=updateTrade&tradeId=${requestScope.thisTrade.tradeId}" method="post" onsubmit="return check();">
    <div class="column middle">
        <h2>商品名称:<label><input type="text" value="${requestScope.thisTrade.tradeName}" name="tradeName"></label><br>
            商品种类：${requestScope.thisTrade.tradeKind}</h2>
        价格:<input style="color: red;font-size: 40px" type="text" value="${requestScope.thisTrade.tradePrice}" name="tradePrice">元
        <p>商品数量：<label><input value="${requestScope.thisTrade.tradeAmount}" name="tradeAmount" type="text" style="font-size: 40px"></label></p>
        <p>商品发售者：${requestScope.thisTrade.tradeUser}</p>
        <p>商品已售数量：<span style="font-size: 40px">${requestScope.thisTrade.tradeBeenAmount}</span></p>
    </div>
    <div class="column side">
        <h2>商品介绍：</h2>
        <p><label><textarea name="tradeIntroduce">${requestScope.thisTrade.tradeIntroduce}</textarea></label></p>
       <button class="button button1" type="submit">确定修改(图片另外修改)</button>
    </div>
    </form>
</div>
<div>
    <ul>
        <li><a class="active" href="/market_war_exploded/TradeServlet?method=findTradeByUser">返回</a></li>
    </ul>
</div>
</body>
<script type="text/javascript">
    function checkPhoto() {
        var fileInput=document.getElementsByName("image")[0];
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
    function check() {
        var tradePrice=document.getElementsByName("tradePrice")[0].value;
        var tradeAmount=document.getElementsByName("tradeAmount")[0].value;
        var tradeName=document.getElementsByName("tradeName")[0].value;
        var tradeIntroduce=document.getElementsByName("tradeIntroduce")[0].value;
        if(!tradePrice.match("^[0-9]+(.[0-9]{0,2})?$")){
            alert("商品价格格式错误,最多两位小数哦");
            return false;
        }
        if(!tradeAmount.match("^[1-9]\\d*$")){
            alert("商品数量格式错误");
            return false;
        }
        if(!tradeName.match("^[\u4E00-\u9FA5A-Za-z0-9]+$")||!tradeName.match("^.{1,32}$")){
            alert("商品名称格式错误");
            return false;
        }
        if(!tradeIntroduce.match("^.{1,100}$")||!tradeIntroduce.match("^[\u4E00-\u9FA5A-Za-z0-9]+$")){
            alert("商品介绍格式错误");
            return false;
        }
    }
</script>
</html>
