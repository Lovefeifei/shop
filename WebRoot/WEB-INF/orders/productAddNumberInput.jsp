<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品库存添加</title>
</head>
<body>
	<form action="OrdersServlet" method="post">
		<input type="hidden" name="method" value="productAddNumber">
		<input type="hidden" name="pid" value="${pid}">
		<table width="600" class="thin-border" align="center">
			<tr>
				<td>商品名称：</td><td>${p.name}</td>
			</tr>
			<tr>
				<td>增加数量：</td><td><input type="text" name="number" value="${param.number }"/><span class="errorContainer">${errors.number}</span></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="添加"/><input type="reset"/>
				</td>
			</tr>
		</table>
		
	</form>
</body>
</html>