<%--
  Created by IntelliJ IDEA.
  User: 86323
  Date: 2019/5/1
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Title</title>
</head>
<body style="background:url(../image/timg.jpg);">
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<link rel="stylesheet" type="text/css" href="../css/FindAllUser.css"/>
<table>
<caption>用户信息</caption>
<tr>
    <td>用户账户</td><td>用户昵称</td><td>用户身份证</td><td>用户手机号</td><td>用户地址</td><td>用户等级</td>
    <td>用户星级</td><td>用户积分</td><td>用户禁售原因</td><td>确定</td>
<c:forEach items="${sessionScope.pageBean.list}" var="user" >
 <tr><td><c:out value="${user.userAccount}"/></td>
     <td><c:out value="${user.userName}"/></td>
     <td><c:out value="${user.userIdCard}"/></td>
     <td><c:out value="${user.userPhone}"/></td>
     <td><c:out value="${user.userAddress}"/></td>
     <td><c:out value="${user.userGrade}"/></td>
     <td><c:out value="${user.userStar}"/></td>
     <td><c:out value="${user.userPoint}"/></td>
    <c:if test="${user.userCan=='是'}">
<form action="/market_war_exploded/UserServlet?method=banUserSell" method="post" onsubmit="return checkReason()">
    <td><label><input type="text" placeholder="请填写禁用原因" name="reason"></label></td>
    <input type="hidden" name="userAccount" value="${user.userAccount}">
      <td><button class="button button5" type="submit" onclick="check()">禁用该用户</button></td>
    </form>
    </c:if>
    <c:if test="${user.userCan=='否'}">
        <c:if test="${empty user.userAppeal}"><td>已被禁售</td></c:if>
        <c:if test="${not empty user.userAppeal}">
            <td><a>申诉内容：${user.userAppeal}</></td></c:if>
        <td><a href="/market_war_exploded/UserServlet?method=allowSell&userAccount=${user.userAccount}" onclick="return checkAppeal();" class="a2">解禁</a></td>
    </c:if>
</tr>
</c:forEach>
</table>
<ul>
    <li><a href="AdminMain.jsp" >点击返回主菜单</a></li>
    <li><a href="/market_war_exploded/UserServlet?method=${sessionScope.judge}&currentPage=1" >首页</a></li>
    <c:if test="${sessionScope.pageBean.currentPage !=1}">
    <li><a href="/market_war_exploded/UserServlet?method=${sessionScope.judge}&currentPage=${sessionScope.pageBean.currentPage-1}" >上一页</a></li>
    </c:if><c:if test="${sessionScope.pageBean.currentPage!=sessionScope.pageBean.totalPage}">
    <li><a href="/market_war_exploded/UserServlet?method=${sessionScope.judge}&currentPage=${sessionScope.pageBean.currentPage+1}" >下一页</a></li>
    </c:if>
    <li><a href="/market_war_exploded/UserServlet?method=${sessionScope.judge}&currentPage=${sessionScope.pageBean.totalPage}" >尾页</a></li>
   <td>每页<label><select name="pageSize" id="select" onchange="change()"><option value="5">5</option><option value="8">8</option><option value="10">10</option></select></label>用户</td>
    <li><span>总${sessionScope.pageBean.totalCount}个</span></li>
    <li><span>第${sessionScope.pageBean.currentPage}页</span></li>
    <li><span>共有${sessionScope.pageBean.totalPage}页</span></li>
    <form action="/market_war_exploded/UserServlet?method=${sessionScope.judge}" method="post">
    <li>跳转到<label><input type="text" name="currentPage" style="width: 80px;height: 10px" size="10">页</label></li>
    <li><button type="submit" >确定</button></li></form>
    <li><form action="/market_war_exploded/UserServlet?method=findUserByAccount" method="post" onsubmit="return checkAccount();">
        <input type="text" name="userAccount" placeholder="搜索用户账户" class="input">
        <button type="submit">搜索</button>
    </form></li>
    <li><span style="color: red ;font-size:25px">${sessionScope.noAccount}</span></li>
</ul>

<script type="text/javascript">
    function change() {
        var pageLength=document.getElementsByName("pageSize")[0].value;

        $.ajax({
            type:"post",
            url:"/market_war_exploded/UserServlet?method=findAllUser&currentPage=1",
            data:{pageLength:pageLength}
        });
    }
    function checkAccount() {
        var userAccount=document.getElementsByName("userAccount")[0].value;
        if(!userAccount.match("^[A-Za-z0-9]+$")) {
            alert("账户输入格式错误");
            return false;
        }
        return true;
    }
    function checkReason() {
        var reason=document.getElementsByName("reason")[0].value;
        if(reason.match("^.{1,100}$")){
            alert("输入格式错误");
            return false;
        }
        return true;
    }
    function check() {
        if(confirm("确定要禁售嘛")){
            return true;
        }
        else {
            return false;
        }
    }
    function checkAppeal() {
        if(confirm("确定要解禁嘛")){
            return true;
        }
        else {
            return false;
        }
    }
</script>
</body>
</html>
