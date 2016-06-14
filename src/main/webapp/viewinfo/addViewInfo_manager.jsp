<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.viewinfo.model.*"%>
<%@ page import="com.viewphoto.model.*"%>
<%@ page import="com.viewphoto.model.*"%>
<jsp:useBean id="viewinfoSvc" scope="page" class="com.viewinfo.model.ViewInfoService" />
<%
ViewInfoVO viewinfoVO = (ViewInfoVO) request.getAttribute("viewinfoVO");
ViewPhotoVO viewphotoVO = (ViewPhotoVO) request.getAttribute("viewphotoVO");

Integer viewno = (Integer) request.getAttribute("viewno");

%>




<html>
<head>
<title>景點資料新增 - addViewInfo.jsp</title></head>
<script src="js/jquery.MultiFile.js.js"></script>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
<div id="popupcalendar" class="text"></div> 

<body bgcolor='white'>

<table border='1' cellpadding='5' cellspacing='0' width='400'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>景點資料新增 - addViewInfo.jsp</h3>
		</td>
		<td>
		   <a href="select_page.jsp"><img src="images/tomcat.gif" width="100" height="100" border="1">回首頁</a>
	    </td>
	</tr>
</table>

<h3>資料景點:</h3>
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

<FORM METHOD="post" ACTION="viewinfo.do" name="form1">
<table border="0">

	<tr>
		<td>景點名稱:</td>
		<td><input type="TEXT" name="viewname" size="45" 
			value="<%= (viewinfoVO==null)? "豐源國小" : viewinfoVO.getViewname()%>" /></td>
	</tr>
	<tr>
		<td>景點管理單位:</td>
		<td><input type="TEXT" name="viewmanager" size="45"
			value="<%= (viewinfoVO==null)? "豐源國小" : viewinfoVO.getViewmanager()%>" /></td>
	</tr>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="viewphone" size="45"
			value="<%= (viewinfoVO==null)? "電話" : viewinfoVO.getViewphone()%>" /></td>
	</tr>
	<tr>
		<td>地址:</td>
		<td><input type="TEXT" name="viewaddress" size="45"
			value="<%= (viewinfoVO==null)? "地址" : viewinfoVO.getViewaddress()%>" /></td>
	</tr>
	
	<tr>
		<td>網站:</td>
		<td><input type="TEXT" name="viewweb" size="45"
			value="<%= (viewinfoVO==null)? "0.0" : viewinfoVO.getViewweb()%>" /></td>
	</tr>
	<tr>
		<td>經度:</td>
		<td><input type="TEXT" name="viewlon" size="45"
			value="<%= (viewinfoVO==null)? "0.0" : viewinfoVO.getViewlon()%>" /></td>
	</tr>
	
	<tr>
		<td>緯度:</td>
		<td><input type="TEXT" name="viewlat" size="45"
			value="<%= (viewinfoVO==null)? "0.0" : viewinfoVO.getViewlat()%>" /></td>
	</tr>
	<tr>
		<td>開放時間:</td>
		<td><input type="TEXT" name="viewopen" size="45"
			value="<%= (viewinfoVO==null)? "早上9:00至下午17:00" : viewinfoVO.getViewopen()%>" /></td>
	</tr>
	<tr>
		<td>景點票價:</td>
		<td><input type="TEXT" name="viewticket" size="45"
			value="<%= (viewinfoVO==null)? "免費" : viewinfoVO.getViewticket()%>" /></td>
	</tr>	<tr>
		<td>景點設備:</td>
		<td><input type="TEXT" name="viewequi" size="45"
			value="<%= (viewinfoVO==null)? "步道；觀景台" :viewinfoVO.getViewequi()%>" /></td>
	</tr>
		<tr>
		<td>景點介紹:</td>
		<td><input type="TEXT" name="viewcontent" size="45"
			value="<%= (viewinfoVO==null)? "豐源國小建築使用大量的白色並在線條上運用藍色滾邊，仿希臘風情的地中海建築，如果在這兒拍畢業照不知道是有多幸福的回憶。栗特別喜歡屋頂上有彎曲曲線的設計，感覺好像海浪一陣一陣，也讓整體建築多了點柔美，建築中心點上方的圓形桶子狀，不知道實際功能為何，但讓栗想起八仙樂園水裝滿就會灑下來的桶子。窗戶用的是西式拱門窗戶，特意使用反射窗讓天空白雲照印在國小上。" : viewinfoVO.getViewcontent()%>" /></td>
	</tr>


	
<c:if test="${empty i}">
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</c:if>	
	
	

</table>


<c:if test="${not empty i }">


	
	
	<form action="<%=request.getContextPath()%>/viewphoto/multipleUpload.do" method="post" enctype="multipart/form-data">
		<input type="file" class="btn btn-info btn-lg"
			name="file[]" multiple="multiple"/>		</br> </br>
		<input type="hidden" name="viewno" value="<%=viewno%>" />
		<input type="submit" style="float:left;" value="送出新增">
	</form>
	
	
	
	
	
</c:if>

</body>

</html>
ple"/>		</br> </br>
		<input type="hidden" name="viewno" value="<%=viewno%>" />
		<input type="submit" style="float:left;" value="�e�X�s�W">
	</form>
	
	
	
	
	


</body>

</html>
