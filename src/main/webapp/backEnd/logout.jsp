<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=loginPage.jsp">
<title>重新導向至登入頁面</title>
</head>
<body>
<%
session.invalidate() ;
%>


</body>
</html>