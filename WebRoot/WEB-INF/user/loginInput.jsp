<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<form action="UserServlet?method=login" method="post">
	<table width="600" class="thin-border" align="center" class="thin-border">
		<tr>
		<td>用户名：</td><td><input type="text" name="username"/>
		</td>
		</tr>
		<tr>
		<td>用户密码：</td><td><input type="password" name="password"/>
		</td>
		</tr>
		<tr>
		<td colspan="2">
			<input type="submit" value="用户登录"/><input type="reset"/>
		</td>
		</tr>
	</table>
</form>
</body>
</html>