<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
</head>
<body>
<jsp:include page="inc.jsp"></jsp:include>
<table width="800" align="center" cellpadding="0" cellspacing="0" class="thin-border">
	<tr>
		<td>用户标识</td><td>用户名称</td><td>用户密码</td><td>用户昵称</td>
		<td>用户类型</td><td>操作</td>
	</tr>
	<c:forEach items="${users.datas }" var="u">
		<tr>
			<td>${u.id }</td><td>${u.username }</td><td>${u.password }</td>
			<td><a href="UserServlet?method=show&id=${u.id }">${u.nickname }</a></td>
			<td>
				<c:if test="${u.type eq 0 }">普通用户</c:if>
				<c:if test="${u.type eq 1 }">管理员</c:if>
				<a href="UserServlet?method=changeType&id=${u.id}">变更</a>
			</td>
			<td>
				
				<a href="UserServlet?method=updateInput&id=${u.id}">修改</a> 
				<a href="UserServlet?method=delete&id=${u.id}">删除</a>
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="6">
			<jsp:include page="/inc/pager.jsp">
				<jsp:param value="UserServlet" name="url"/>
				<jsp:param value="${users.totalRecord}" name="items"/>
				<jsp:param value="method" name="params"/>
			</jsp:include>
		</td>
	</tr>
	
</table>




</body>
</html>