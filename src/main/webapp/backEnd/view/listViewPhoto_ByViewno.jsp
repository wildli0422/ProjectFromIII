<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%-- 此頁練習採用 EL 的寫法取值 --%>

<jsp:useBean id="listViewPhoto_ByViewno" scope="request" type="java.util.Set" />
<jsp:useBean id="viewinfoSvc" scope="page"
	class="com.viewinfo.model.ViewInfoService" />
<html>
<head>
<title>景點照片 - listViewPhoto_ByViewno.jsp</title>
</head>
<body bgcolor='white'>

	<b><font color=red>此頁練習採用 EL 的寫法取值:</font></b>
	<table border='1' cellpadding='5' cellspacing='0' width='800'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>景點照片 - listViewPhoto_ByViewno.jsp</h3> <a
				href="<%=request.getContextPath()%>/select_page.jsp"><img
					src="images/back1.gif" width="100" height="32" border="0">回首頁</a>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>

	<table border='1' bordercolor='#CCCCFF' width='800'>
		<tr>
			<th>景點照片編號</th>
			<th>景點編號</th>
			<th>景點照片</th>
			<th>景點名稱</th>
		</tr>





<%-- 	${listViewPhoto_ByViewno } --%>
		<c:forEach var="viewphotoVO" items="${listViewPhoto_ByViewno}">
			<tr align='center' valign='middle'>
			<td>${viewphotoVO.viewpicno}</td>
			<td>${viewphotoVO.viewno}</td>
			<td><img width=200
					src="<%= request.getContextPath()%>/viewphoto/readviewphoto.do?viewpicno=${viewphotoVO.viewpicno}" />
			</td>
			<td><c:forEach var="viewinfoVO" items="${viewinfoSvc.all}">
                    <c:if test="${viewphotoVO.viewno==viewinfoVO.viewno}">
	                    ${viewinfoVO.viewno}【<font color=orange>${viewinfoVO.viewname}</font>】
                    </c:if>
                </c:forEach>
			</td>
			</tr>
		</c:forEach>
	</table>

	<br>本網頁的路徑:
	<br>
	<b> <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
		<font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%>
	</b>
</body>
</html>
