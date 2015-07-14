<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
	<title><decorator:title default="欢迎使用网上商城"/></title>  
	<decorator:head/> 
	<link rel="stylesheet" type="text/css" href="css/main.css"> 
</head>  
<body> 
	<c:if test="${not empty loginUser}">
		欢迎${loginUser.nickname }登录!&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/UserServlet?method=updateSelfInput">修改个人信息</a>&nbsp;
		<a href="<%=request.getContextPath()%>/UserServlet?method=show&id=${loginUser.id}"> 查询个人信息</a>&nbsp;
				<a href="<%=request.getContextPath()%>/OrdersServlet?method=showCart&id=${loginUser.id}"> 查看购物车</a>&nbsp;
		<a href="<%=request.getContextPath()%>/UserServlet?method=logout">退出</a>&nbsp;
	</c:if>
	<c:if test="${empty loginUser}">
		<a href="<%=request.getContextPath()%>/UserServlet?method=loginInput">用户登录</a>&nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/UserServlet?method=addInput">用户注册</a>&nbsp;&nbsp;
	</c:if>

    <a href="<%=request.getContextPath()%>/UserServlet?method=list">用户管理</a>&nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/CategoryServlet?method=list">商品类别管理</a> &nbsp;&nbsp;
    <a href="<%=request.getContextPath()%>/ProductServlet?method=list">商品管理</a> &nbsp;&nbsp;
    <hr/>
    <h3 align="center"><decorator:title default="商城管理系统"></decorator:title></h3>
    <decorator:body/>  
    <hr/>
    <div align="center" style="width:100%;border-top:1px solid; float:left;margin-top:10px;">
    	CopyRight@2012-2016<br/>
    	重庆邮电大学
    </div>
</body>  
</html>  