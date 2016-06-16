<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostel.model.*" %>
<%@ page import="com.hostelNews.model.*" %>
<%@ page import="com.dealer.model.*" %>
<%@ page import="java.util.*" %>
<%
	DealerVO dealerVO=(DealerVO)session.getAttribute("dealerVObyAccount");
	Integer dealerNo=dealerVO.getDealerNo();

	DealerService dealerService=new DealerService();
	HostelVO hostelVO=dealerService.getHostelByDealerNo(dealerNo);
	Integer hostelNo=hostelVO.getHostelNo();
			
	HostelService hostelService = new HostelService();
	List<HostelNewsVO> newsList = hostelService.getNewsByHostelNo(hostelNo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!-- ****FavIcon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />

<!-- ****BootStrap Icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

<!-- ****Jquery**** -->  
<script src="http://code.jquery.com/jquery.js"></script>  
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- Jquery Toggle -->


<!-- ****BootStrap**** -->
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<!-- ****LightBox**** -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-image-gallery.css">
<script src="<%=request.getContextPath()%>/js/bootstrap-image-gallery.js"></script>
<link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>

<title>eChioce| ${hostelVO.hostelName} | choose your life.</title>

</head>
<body bgcolor="white">
	
<!-- topBar -->
<div class="col-md-12" id="topBar">
	<div class="row" class="navbar navbar-default" role="navigation">
	<div class="col-md-3" style="margin-top:20px;">
		<a href="<%=request.getContextPath()%>/HomePage.jsp"><img src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
	</div>
	<div class="col-md-9" id="top_menu" style="margin-top:20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a href="<%=request.getContextPath()%>/hostel/hostelManager.jsp" id=""><i class="fa fa-home fa-2x"></i> 民宿基本資料</a></li>
					<li><a href="<%=request.getContextPath()%>/dealer/dealer_One.jsp" id=""><i class="fa fa-user fa-2x"></i> 個人資料頁面</a></li>
					 <c:if test="${empty dealerVObyAccount}">
					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-in fa-2x"></i> 登入</a></li> 
					 </c:if>
					 <c:if test="${not empty dealerVObyAccount}">
					 	<li><a href="<%=request.getContextPath()%>/loginServlet.do?action=logout" id=""><i class="fa fa-sign-out fa-2x"></i> 登出</a></li>
					 </c:if>
				 	
				</ul>
     </div>				
	</div>
</div>
<!-- topBar -->
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-8">
				<div class="col-md-4"></div>
				<div class="col-md-4">
				   <form action="<%=request.getContextPath()%>/multipleUpload.do" method="post" enctype="multipart/form-data">
						<input type="file" class="btn btn-info btn-lg" style="font-size:15px;margin:10px;font-family:標楷體;"
							name="file[]" multiple="multiple"/>		
						<input type="hidden" name="hostelNo" value="<%=hostelVO.getHostelNo()%>" />
						<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
						<input type="submit" style="float:left;margin-left:60px;" value="上傳圖片">
					</form>
				</div>
			</div>
		</div>
	</div>
		    
		
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="row">
					<div id="hostelName">
						【<%=hostelVO.getHostelName() %>】
					</div>
					<hr>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="row">
					<div style="text-align:center;font-size:20px;">
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs }">
							<font color='red'>請修正以下錯誤:
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li>${message}</li>
								</c:forEach>
							</ul>
							</font>
						</c:if>
					</div>
					<hr>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
				
				<div  class="col-md-8" style="font-size:20px;">
					<div style="text-align:center;">最新消息:</div>
						<c:forEach var="hostelNewsVO" items="<%=newsList %>">
							<div class="row">
								<div class="col-md-3"></div>
								<form action="<%=request.getContextPath()%>/hostelModify.do" method="post">
									<div class="col-md-2">${hostelNewsVO.updateDate }</div>
									<div class="col-md-4">${hostelNewsVO.newsContent.trim()}</div>
									
									<div class="col-md-2">
										<input type="hidden" name="hostelNewsNo" value="${hostelNewsVO.hostelNewsNo }">
										<input type="hidden" name="action" value="deleteNews">
										<input type="submit" value="刪除">
									</div>
								
								</form>
							</div><!-- 	row -->
						</c:forEach>
							<div class="row">
<!-- 								<div class="col-md-2"></div> -->
								<div class="col-md-12">
								<form action="<%=request.getContextPath()%>/hostelModify.do" method="post">
									<div style="text-align:center;margin-left:55px;margin-top:10px;">
									<textarea  name="newsContent" cols="45"></textarea>
									<input type="submit" value="新增">
									</div>
									<input type="hidden" name="hostelNo" value="<%=hostelVO.getHostelNo() %>">
									<input type="hidden" name="action" value="insertNews">
								</form>
								</div>
<!-- 								<div class="col-md-3"></div> -->
							</div>
					<hr>
				</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			
			<div class="col-md-8" id="modifyTable">
			
			<form method="post" action="<%=request.getContextPath()%>/hostelModify.do" name="form1" enctype="multipart/form-data">

				
				<div >民宿簡介:<p>
				<br>
					<textarea  name="hostelContent" cols="45"
								rows="5" ><%=hostelVO.getHostelContent() %></textarea>
				</div>
				<div>民宿電話:</div>
				<div>
					<input type="text" name="hostelPhone" size="45"
					value="<%=hostelVO.getHostelPhone() %>">
				</div>
				<hr>
				<div>民宿地址:</div>
				<div >
					<input type="text" name="hostelAddress" size="45"
						value="<%=hostelVO.getHostelAddress() %>">
				</div>
				<hr>
<!-- 				<div>民宿專頁:</div> -->
<!-- 				<div> -->
					<input type="hidden" name="hostelWebPages" size="45"
						value="<%=hostelVO.getHostelWebPages() %>">
<!-- 				</div> -->
<!-- 				<hr> -->
				<div>民宿首頁照片:</div>
				<br>
				<div>
					<input type="file" name="hostelPicture" size="45" value="<%=hostelVO.getHostelPicture() %>"
						id="hostelPicture" style="display:inline;" >
					<div>
						<img style="width:50%;"
						src="<%=request.getContextPath()%>/ImageReader?hostelNo=<%=hostelVO.getHostelNo()%>&hostelPicture=<%=hostelVO.getHostelPicture()%>">
						
					</div>
				</div>
				<br>
				<hr>
<!-- 				<div>民宿照片(多張):</div> -->
<!-- 				<br> -->
<!-- 				<div> -->
<!-- 					<input name="file[]" type="file" style="display:inline;" multiple="multiple" class="multi with-preview" /> -->
<%-- 					<input type="hidden" name="hostelNo" value="<%=hostelVO.getHostelNo()%>"> --%>
<!-- 				</div> -->
<!-- 				<div>民宿照片(刪除多張):</div> -->
<!-- 				<br> -->
<!-- 				<div> -->
<!-- 					<input name="file[]" type="file" style="display:inline;" multiple="multiple" class="multi with-preview" /> -->
<%-- 					<input type="hidden" name="hostelNo" value="<%=hostelVO.getHostelNo()%>"> --%>
<!-- 				</div> -->
<!-- 				<br> -->
<!-- 				<hr> -->
<!-- 				<div>注意事項:<p> -->
<!-- 						<br> -->
<%-- 						<textarea name="hostelCautions" cols="45" rows="5" ><%=hostelVO.getHostelCautions() %> --%>
<!-- 						</textarea> -->
<!-- 				</div> -->

				<hr>
					<input type="hidden" name="hostelNo" value="<%=hostelVO.getHostelNo()%>">
					<input type="hidden" name="action" value="update">
					<input type="submit" value="修改">
				</form>
			</div>
			
			<div class="col-md-2"></div>
		</div>
	</div>

<!-- //------------footer-------------- -->	
	<div class="col-md-12">
		<div class="row">
			<div  id="footer" class="col-md-12">
			All Content Copyright &copy; 2016 Silvia Inc.
			</div>
		</div>
	</div>
<!-- //------------footer-------------- -->
</body>
</html>