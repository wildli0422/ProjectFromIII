<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.roomType.model.*" %>
<%@ page import="com.roomTypePic.model.*" %>
<%@ page import="com.facility.model.*" %>
<%@ page import="java.util.*" %>
<%
	RoomTypeService roomTypeService=new RoomTypeService();
	RoomTypeVO roomTypeVO=roomTypeService.getOneRoomType(new Integer(request.getParameter("roomTypeNo")));
	
	String check=null;
	try{
		check =request.getParameter("check").trim();
	}catch(Exception e){
		check="0";
	}
	pageContext.setAttribute("check", check);
	
	FacilityService facilityService =new FacilityService();
	FacilityVO facilityVO= facilityService.getOneFacility(roomTypeVO.getFacilityNo());
	
	List<RoomTypePicVO> picList=roomTypeService.getPicByRoomTypeNo(roomTypeVO.getRoomTypeNo());
%>
<!DOCTYPE html>

<html>
<head>
	<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">

<!-- ****jquery**** -->  
<script src="http://code.jquery.com/jquery.js"></script>  
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<!-- ****bootstrap**** -->
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/NavBar_top.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.MultiFile.js"></script>
	<script src="<%=request.getContextPath()%>/js/lightslider.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/lightslider.css" />
<title>One RoomType</title>
<script>
$(document).ready(function() {
	$("#content-slider").lightSlider({
        loop:true,
        keyPress:true
    });
    $('#image-gallery').lightSlider({
        gallery:true,
        item:1,
        thumbItem:3,
        slideMargin: 0,
        speed:500,
        auto:true,
        loop:true,
        onSliderLoad: function() {
            $('#image-gallery').removeClass('cS-hidden');
        }  
    });
});
</script>
<style>
	
</style>
    	
</head>
<body>
	<!------------------------------ nav bar ---------------------------------->
	<div class="col-md-12" id="topBar">
	<div class="row" class="navbar navbar-default" role="navigation">
	<div class="col-md-3" style="margin-top:20px;">
		<a href="<%=request.getContextPath()%>/HomePage.jsp"><img src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
	</div>
	<div class="col-md-9" id="top_menu" style="margin-top:20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a href="<%=request.getContextPath()%>/hostel/hostelManager.jsp" id=""><i class="fa fa-home fa-2x"></i> 民宿基本資料</a></li>
					<li class="dropdown">
				        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
				        <i class="fa fa-bed fa-2x"></i>房務管理 
				        <span class="caret"></span></a>
				        <ul class="dropdown-menu">
				           <li><a id="tab_1" href="<%=request.getContextPath()%>/room/roomType_modify.jsp"">房間基本資料</a></li>
				           <li><a href="<%=request.getContextPath()%>/room/emptyRoomType.jsp" target="iframe">房間狀態管理</a></li>       
				        </ul>
				     </li>
					<li><a href="<%=request.getContextPath()%>/hostel/hostelCalendar.jsp" id=""><i class="fa fa-calendar fa-2x"></i> 訂單管理</a></li>					
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
<!------------------------------ nav bar ---------------------------------->

<%-- 	<%=request.getParameter("roomTypeNo") %> --%>
<%-- 	<%=roomTypeVO.getFacilityNo() %> --%>
<%-- 	<%=roomTypeVO.getHostelNo() %> --%>

	<c:if test="${check=='check' or not empty dealerVObyAccount}">
	<form action="<%=request.getContextPath()%>/multipleUpload.do" method="post" enctype="multipart/form-data">
		<div>
		<input type="file" class="btn btn-info btn-lg" style="float:right;font-size:20px;margin:10px;font-family:標楷體;"
			name="file[]" multiple="multiple"/>
		<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">		
		<input type="hidden" name="roomTypeNo" value="<%=roomTypeVO.getRoomTypeNo()%>" />
		<input type="submit" style="float:right;" value="上傳">
		</div>
	</form>
	</c:if>
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div style="font-size:32px;text-align:center;font-family:微軟正黑體;">
					<%=roomTypeVO.getRoomTypeName()%>
					</div>
				</div>
				<div class="col-md-2"></div>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
		
	<div class="col-md-12" id="container">
		<div class="row">
			<div class="col-md-2"></div>
			
			<div class="col-md-8"  >
				<div class="row">
				<div class="col-md-2"></div>
				
				<div class="col-md-8">
					<div style="text-align:center;width:100%;">
							<div class="demo">
					            <div class="clearfix" style="max-width:100%;">
					                <ul id="image-gallery">
					                	<li data-thumb="<%=request.getContextPath()%>/ImageReader?roomTypeNo=<%=roomTypeVO.getRoomTypeNo()%>">
					                		<img style="width:100%;" src="<%=request.getContextPath()%>/ImageReader?roomTypeNo=<%=roomTypeVO.getRoomTypeNo()%>">
					                	</li>
					                	<c:forEach var="roomTypePicVO" items="<%=picList%>">
						                	<li data-thumb="<%=request.getContextPath()%>/ImageReader?roomTypePicNo=${roomTypePicVO.roomTypePicNo}">
						                		<img style="width:100%;" src="<%=request.getContextPath()%>/ImageReader?roomTypePicNo=${roomTypePicVO.roomTypePicNo}" />
						                	</li>
					                	</c:forEach>				                  
					                </ul>
					        </div>	
					    </div>					
					</div>		
				</div>
				
				<div class="col-md-2"></div>
					
				</div>
			</div>
			
			<div class="col-md-2"></div>
		</div>
	</div>
	
	
	<div class="col-md-12" id="container">
		<div class="row">
			<div class="col-md-2"></div>
			
			<div class="col-md-8" >
				<div class="row">
					<div class="col-md-2"></div>
					
					<div class="col-md-8">
<!-- 						<div style="text-align:center;width:100%;"> -->
							<div>
								<div style="font-size:32px;text-align:center;font-family:微軟正黑體;width:100%;">房型詳細內容</div>
								<div><pre><%=roomTypeVO.getRoomTypeContent() %></pre></div>
							</div>

							<table style="width:100%;text-align:center;" class="table table-bordered">
								<tr>
									<td style="width:20%;background-color:#fff3e6;">價格</td>
									<td width="80%"><%=roomTypeVO.getRoomTypePrice()%>元</td>
								</tr>
								<tr>
									<td style="width:20%;background-color:#fff3e6;">容納人數</td>
									<td width="80%"><%=roomTypeVO.getRoomTypeContain()%>人</td>
								</tr>
								<tr>
									<td rowspan="5" style="width:20%;background-color:#fff3e6;">提供設備</td>
									<td width="80%" style="font-family:微軟正黑體;">
										<div class="row">
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getKitchen()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/kitchen.png"><b>廚房</b></div>
											</c:if>											
										</div>
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getParking()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/parking.png"><b>停車位</b></div>
											</c:if>
										</div>
										</div>
									</td>
								</tr>
								<tr>
									<td width="80%" style="font-family:微軟正黑體;">
										<div class="row">
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getAirConditioning()==1 %>">
												<span><img src="<%=request.getContextPath()%>/images/airConditioning.png"><b>空調</b></span>
											</c:if>
										</div>
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getBathroom()==1 %>">
												<span><img src="<%=request.getContextPath()%>/images/bathroom.png"><b>浴室</b></span>
											</c:if>
										</div>
										</div>
									</td>
								</tr>
								<tr>
									<td width="80%" style="font-family:微軟正黑體;">
										<div class="row">
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getPet()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/pet.png"><b>寵物</b></div>
											</c:if>
										</div>
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getRoomBedService()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/roomBedService.png"><b>加床服務</b></div>
											</c:if>
										</div>
										</div>
									</td>
								</tr>
								<tr>
									<td width="80%" style="font-family:微軟正黑體;">
										<div class="row">
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getRoomPhone()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/roomPhone.png"><b>電話</b></div>
											</c:if>											
										</div>
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getTelevision()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/television.png"><b>電視</b></div>
											</c:if>
										</div>
										</div>
									</td>
								</tr>
								<tr>
									<td width="80%" style="font-family:微軟正黑體;">
										<div class="row">
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getToiletries()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/toiletries.png"><b>盥洗用品</b></div>
											</c:if>									
										</div>
										<div class="col-md-6">
											<c:if test="<%=facilityVO.getWifi()==1 %>">
												<div><img src="<%=request.getContextPath()%>/images/wifi.png"><b>網路</b></div>
											</c:if>
										</div>
										</div>
									</td>
								</tr>
							</table>						
<!-- 						</div>		 -->
					</div>
					
					<div class="col-md-2"></div>	
				</div>
			</div>
			
			<div class="col-md-2"></div>
		</div>
	</div>

<!-- //------------footer-------------- -->	
<div class="col-md-12" id="footer01">
	<div class="row">
	<div class="col-md-4">
	</div>
	<div class="col-md-4">
	<p>© eChoice, Inc.</p>	
	</div>
	<div class="col-md-4">
	</div>	
	</div>
</div>

<div class="col-md-12" id="footer02" style="height:200px;">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-4">
	
	<ul>
		<li>關於我們</li>
		<li>政策</li>
		<li>幫助</li>
		<li>Blog</li>
		<li>服務條款與私隱聲明</li>
	</ul>
	
	</div>
	<div class="col-md-2">
	</div>
	<div class="col-md-4">
	<ul>
		<li>常見問題</li>
		<li>聯絡我們</li>
		<li>廣告刊登</li>	
	</ul>
	<i class="fa fa-phone fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-envelope-o fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-facebook-square fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-twitter-square fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-android fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-print fa-2x"></i>
	
	</div>
	</div>
</div>
<!-- //------------footer-------------- -->
		
	
</body>
</html>