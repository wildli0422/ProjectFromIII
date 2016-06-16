<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%-- �����m�߱ĥ� EL ���g�k���� --%>

<jsp:useBean id="listViewPhoto_ByViewno" scope="request" type="java.util.Set" />
<jsp:useBean id="viewinfoSvc" scope="page"
	class="com.viewinfo.model.ViewInfoService" />
<html>
<head>
<title>���I�Ӥ� - listViewPhoto_ByViewno.jsp</title>
</head>
<body bgcolor='white'>

	<b><font color=red>�����m�߱ĥ� EL ���g�k����:</font></b>
	<table border='1' cellpadding='5' cellspacing='0' width='800'>
		<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
			<td>
				<h3>���I�Ӥ� - listViewPhoto_ByViewno.jsp</h3> <a
				href="<%=request.getContextPath()%>/select_page.jsp"><img
					src="images/back1.gif" width="100" height="32" border="0">�^����</a>
			</td>
		</tr>
	</table>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>�Эץ��H�U���~:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>

	<table border='1' bordercolor='#CCCCFF' width='800'>
		<tr>
			<th>���I�Ӥ��s��</th>
			<th>���I�s��</th>
			<th>���I�Ӥ�</th>
			<th>���I�W��</th>
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
	                    ${viewinfoVO.viewno}�i<font color=orange>${viewinfoVO.viewname}</font>�j
                    </c:if>
                </c:forEach>
			</td>
			</tr>
		</c:forEach>
	</table>

	<br>�����������|:
	<br>
	<b> <font color=blue>request.getServletPath():</font> <%=request.getServletPath()%><br>
		<font color=blue>request.getRequestURI(): </font> <%=request.getRequestURI()%>
	</b>
</body>
</html>
