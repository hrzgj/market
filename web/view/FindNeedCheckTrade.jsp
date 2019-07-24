<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/3
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>商品界面</title>
</head>
<body style="background:url(../image/timg.jpg);">
<link rel="stylesheet" type="text/css" href="../css/FindTrade.css"/>
<ul>
    <li><a href="view/AdminMain.jsp" class="ah">点击返回主菜单</a></li>
    <li><a href="/market_war_exploded/TradeServlet?method=adminFindCheckTrade&currentPage=1">首页</a></li>
      <li> <a href="/market_war_exploded/TradeServlet?method=adminFindCheckTrade&currentPage=${requestScope.pageTrade.currentPage-1}">上一页</a></li>
        <li><a href="/market_war_exploded/TradeServlet?method=adminFindCheckTrade&currentPage=${requestScope.pageTrade.currentPage+1}" >下一页</a></li>
    <li><a href="/market_war_exploded/TradeServlet?method=adminFindCheckTrade&currentPage=${requestScope.pageTrade.totalPage}" >尾页</a></li>
    <li><a>总${requestScope.pageTrade.totalCount}个</a></li>
    <li><a>共有${requestScope.pageTrade.totalPage}页</a></li>
</ul>
<div class="good">
<c:forEach items="${requestScope.pageTrade.list}" var="trade">
    <div class="img">
        <a href="/market_war_exploded/TradeServlet?method=findThisTrade&pageId=${trade.tradeId}"><img src="../image/${trade.tradePhoto}" alt="图片文本描述" width="300" height="200">
            <div class="desc">用户账户${trade.tradeUser}</div></a>
        <div class="desc">商品状态：${trade.tradeState}</div>
        <c:if test="${trade.tradeState=='审核'}">
        <a class="goods" href="/market_war_exploded/TradeServlet?method=checkTrade&pageId=${trade.tradeId}" onclick="return judge();">通过审核</a></c:if>
        <c:if test="${trade.tradeState=='申请删除'}">
            <a class="goods" href="/market_war_exploded/TradeServlet?method=removeTrade&tradeId=${trade.tradeId}" onclick="return checkRemove();">删除该商品</a>
        </c:if>
    </div>
</c:forEach>
</div>
</body>
<script type="text/javascript">
function judge() {
     if(confirm("确定通过审核？")) {
         return true;
     }
     else {
         return false;
     }
}

function checkRemove() {
    if(confirm("确定要删除嘛")) {
        return true;
    }
    else {
        return false;
    }
}
</script>
</html>
