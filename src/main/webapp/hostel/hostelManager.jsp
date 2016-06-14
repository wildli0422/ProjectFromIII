<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.dealer.model.*" %>
<%@ page import="com.hostel.model.*" %>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.facility.model.*"%>
<%@ page import="com.roomTypePic.model.*"%>
<%@ page import="com.hostelPic.model.*" %>
<%@ page import="com.hostelNews.model.*" %>
<%@ page import="java.util.*"%>
<jsp:useBean id="facilityService" scope="page" class="com.facility.model.FacilityService" />

<!DOCTYPE html>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/hostelManager.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<!-- ****LightBox**** -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-image-gallery.css">
<script src="<%=request.getContextPath()%>/js/bootstrap-image-gallery.js"></script>
<link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>

<title>eChioce| ${hostelVO.hostelName} | choose your life.</title>
	
	<script>
// 		$(document).ready(function(){
// 			$('.hostelPics').ekkoLightbox(options);	
// 		})
// 		$(document).delegate('*[data-toggle="lightbox"]', 'click', function(event) {
// 		    event.preventDefault();
// 		    $(this).ekkoLightbox();
// 		}); 
	</script>
<!-- NavBar	 -->
<script type="text/javascript">
  $(function(){
	  $("#tab_1").click(function(){
		  $("#iframe").attr("src","<%=request.getContextPath()%>/management_hostel.jsp");
	  })
  })
  
</script>	
<!-- NavBar	 -->	
	
	
</head>

<body>

<!-- 	header -->
	<%
		DealerVO dealerVO=(DealerVO)session.getAttribute("dealerVObyAccount");
		Integer dealerNo=dealerVO.getDealerNo();
		
		DealerService dealerService=new DealerService();
		HostelVO hostelVO=dealerService.getHostelByDealerNo(dealerNo);
		Integer hostelNo=hostelVO.getHostelNo();
		
// 		Integer roomTypeNo=dealerService.
		
		HostelService hostelService=new HostelService();
		Set<RoomTypeVO> roomTypeSet=hostelService.getRoomTypesByHostelNo(hostelNo);
		Set<HostelPicVO> hostelPicSet=hostelService.getHostelPicsByHostelNo(hostelNo);
		List<HostelNewsVO> newsList=hostelService.getNewsByHostelNo(hostelNo);
		
		RoomTypeService roomTypeService=new RoomTypeService();
		List<List<RoomTypePicVO>> PicList=new ArrayList<List<RoomTypePicVO>>();
		
		
// 		FacilityService facilityService=new FacilityService();
// 		pageContext.setAttribute("facilityService", facilityService);
// 		System.out.println(dealerNo);
// 		System.out.println(hostelNo);
// 		System.out.println(set.size());
		for(RoomTypeVO roomTypeVO:roomTypeSet){
			PicList.add(roomTypeService.getPicByRoomTypeNo(roomTypeVO.getRoomTypeNo()));
// 			System.out.println(roomTypeVO.getRoomTypeNo());
// 			System.out.println(roomTypeService.getPicByRoomTypeNo(roomTypeVO.getRoomTypeNo()));
		}
// 		session.setAttribute("hostelVO", hostelVO);
// 		${hostelVO.getHostelNo()}
	%>

<!-- topBar -->
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
<!-- topBar -->	

<div class="col-md-12" >
	<div class="row" id="cover"
		style="background-image:url('<%=request.getContextPath()%>/ImageReader?hostelNo=<%=hostelVO.getHostelNo()%>&hostelPicture=<%=hostelVO.getHostelPicture()%>');"	
	>	
		
	</div>

</div>	
<!-- 	hostel img -->

<div class="col-md-12" style="height:60px;margin-top:15px">
	<div class="row">
		
		<a href="<%=request.getContextPath()%>/hostel/hostel_modify.jsp" style="font-size:14px;margin-left:940px;color:maroon"><i class="fa fa-cog fa-3x"></i>修改</a>
			
	</div>
</div>


<!-- 		hostel dealer name	Content -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<div class="col-md-12">
			<div class="row">
			<div class="col-md-4">
				<i class="fa fa-home fa-3x" style="color:#565a5c;"></i>
				<span style="font-size:37px;font-weight:600;color:#565a5c;letter-spacing:4px"><%=hostelVO.getHostelName()%></span>
			</div>
			<div class="col-md-2">
			</div>
			<div class="col-md-3" style="margin-top:20px;">
				<span style="font-size:15px"><%=hostelVO.getHostelAddress()%></span>
			</div>
			<div class="col-md-3" style="margin-top:20px;">
				<span style="font-size:15px"><%=hostelVO.getHostelPhone()%></span>
			</div>			
			</div>
		</div>
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>
<!-- 		hostel dealer name	Content -->

<!-- 空白 -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
	<p style="height:0;overflow:hiddne;border-top:1px solid #dcdcdc"></p>
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>
<!-- 空白 -->

<!------about content  ---->
<div class="col-md-12">
	<div class="row">
		<div class="col-md-2">
		</div>
			
<!--    content -->

		<div class="col-md-8">
			<div class="col-md-12">
			<div class="row">
				<div class="col-md-3">
					<p style="font-weight:560;font-size:22px;color:#6999D1;letter-spacing:20px;margin-top:20px;">簡介</p>
				</div>			
				<div class="col-md-9" style="font-size:16px;margin-bottom:30px;margin-top:20px;">	
					<%=hostelVO.getHostelContent()%>
				</div>
			</div>
					
			</div>	
				
		</div>
				
		<div class="col-md-2">
		</div>		
	</div>
</div>
<!------about content  ---->



<!------news content  ---->
<div class="col-md-12">
	<div class="row">
		<div class="col-md-2">
		</div>
			
<!--    content -->

		<div class="col-md-8" >
			<div class="col-md-12">	
			<div class="row">		
				<div class="col-md-3">
					<p style="font-weight:560;font-size:22px;color:#6999D1;letter-spacing:20px">公告</p>
				</div>			
				<div class="col-md-9">	
						<c:forEach var="hostelNewsVO" items="<%=newsList %>">
							<div style="font-size:16px;">
								<span style="float:right;">${hostelNewsVO.newsContent}</span>												
								<span> ▪  ${hostelNewsVO.updateDate }</span>
														
							</div>
						</c:forEach>
				</div>
			</div>			
			</div>						
		</div>			
		<div class="col-md-2">
		</div>
	</div>
</div>
<!-- content -->

<!------news content  ---->

<div class="col-md-12" style="height:20px">
	<div class="row">
	</div>
</div>

<!------ facility  ---->
<div class="col-md-12">
	<div class="row" >
		<div class="col-md-2">
		</div>
		<div class="col-md-8" style="background-color:#6999D1;height:34px;">		
			<p style="font-size:22px;color:#ffffff;text-align:center;letter-spacing:20px">提供設施</p>
		</div>
		</div>
		<div class="col-md-2">
	</div>
</div>
<!-- content -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8" style="border:0.5px solid #f7f8f8;">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-1">
				</div>
				<div class="col-md-3">
				<p>
					<p style="font-size:14px;"><i class="fa fa-cutlery fa-2x" title="有廚房"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						廚房</p>
					<p style="font-size:14px;"><i class="fa fa-coffee fa-2x" title="有供應早餐"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						有供應早餐</p>				
				</div>	
				<div class="col-md-3">	
				<p>
					<p style="font-size:14px;"><i class="fa fa-tv fa-2x" title="電視"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						電視</p>
					<p style="font-size:14px;"><i class="fa fa-wifi fa-2x" title="wifi"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						wifi</p>				
				</div>
				<div class="col-md-3">
				<p>
					<p style="font-size:14px;"><i class="fa fa-bicycle fa-2x" title="腳踏車"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						腳踏車</p>	
				</div>
				<div class="col-md-2">
				</div>	
			</div>			
		</div>
	<div class="col-md-2">
	</div>
	</div>
	</div>
</div>

<!------ content  ---->

<div class="col-md-12" style="height:10px;">
	<div class="row">

	</div>
</div>
<!------ facility  ---->



<!------ roomType   ---->

<div class="col-md-12">
	<div class="row" >
		<div class="col-md-2">
		</div>
		<div class="col-md-8" style="background-color:#6999D1;height:34px;">		
			<p style="font-size:22px;color:#ffffff;text-align:center;letter-spacing:20px">房型介紹</p>
		</div>
		</div>
		<div class="col-md-2">
	</div>
</div>


<div class="col-md-12" style="height:20px">
<div class="row">
</div>
</div>
<!-- content -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
	<c:forEach var="roomTypeVO" items="<%=roomTypeSet%>">
		<div class="col-md-12">
			<div class="row" style="height:225px;">			
				<div class="col-md-5">				
					<img style="width:300px;margin-top:10px;"
					src="<%=request.getContextPath()%>/ImageReader?roomTypeNo=${roomTypeVO.roomTypeNo}">					
				</div>
				<div class="col-md-5" style="margin-top:10px;font-size:14px">
				<p>
				  <p>房型 : ${roomTypeVO.roomTypeName}</p>
				  <p>人數 : ${roomTypeVO.roomTypeContain}人</p>
				  <p>價格 : $${roomTypeVO.roomTypePrice}/天</p>
				  <p>描述 : ${roomTypeVO.roomTypeContent}</p>
				</div>
				<div class="col-md-2" style="margin-top:90px">
					<span>
						<a href="<%=request.getContextPath()%>/room/roomType_One.jsp?roomTypeNo=${roomTypeVO.roomTypeNo}&check=check" 
							style="float:right;" target="_blank"  class="glyphicon glyphicon-search" style="margin:10px;font-size:20px;"></a>
					</span>
				</div>			
			</div>
		</div>
	</c:forEach>	
	</div>	
	<div class="col-md-2">
	</div>
	
	</div>
</div>

<!------ content   ---->
	
<!------ roomType   ---->



<!------ roomTypePic   -- -->
<div class="col-md-12" style="margin-top:60px;">
	<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-6">
		<c:forEach var="hostelPicVO" items="<%=hostelPicSet%>" varStatus="i">
				<span>
				<a href="<%=request.getContextPath()%>/ImageReader?hostelPicNo=${hostelPicVO.hostelPicNo}" data-gallery ${(i.index>2)?"hidden":""}>
				<img style="height:100px;" src="<%=request.getContextPath()%>/ImageReader?hostelPicNo=${hostelPicVO.hostelPicNo}">
				</a></span>
		</c:forEach>
		<c:forEach var="list" items="<%=PicList %>" >
				<span >
				<c:forEach var="roomTypePicVO" items="${list}" varStatus="s">
				<a href="<%=request.getContextPath()%>/ImageReader?roomTypePicNo=${roomTypePicVO.roomTypePicNo}" data-gallery ${(s.index>4)?"hidden":""}>
					<img id="roomTypePic" style="height:100px"
					src="<%=request.getContextPath()%>/ImageReader?roomTypePicNo=${roomTypePicVO.roomTypePicNo}&roomTypePhoto=${roomTypePicVO.roomTypePhoto}">
				</a>	
				</c:forEach>
				</span>
		</c:forEach>
	</div>
	<div class="col-md-3">
	</div>
	</div>
</div>
	
<!------ roomTypePic   ---->

<div id="blueimp-gallery" class="blueimp-gallery">
    <!-- The container for the modal slides -->
    <div class="slides"></div>
    <!-- Controls for the borderless lightbox -->
    <h3 class="title"></h3>
    <a class="prev" style="font-size:35px;padding-top:10px;">&lt</a>
    <a class="next" style="font-size:35px;padding-top:10px;">&gt</a>
    <a class="close">X</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
    <!-- The modal dialog, which will be used to wrap the lightbox content -->
    <div class="modal fade">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body next"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left prev">
                        <i class="glyphicon glyphicon-chevron-left"></i>
                        Previous
                    </button>
                    <button type="button" class="btn btn-primary next">
                        Next
                        <i class="glyphicon glyphicon-chevron-right"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<!------ roomTypePic   ---->

<div class="col-md-12" style="height:30px;">
	<div class="row">
	</div>
</div>


<!-- google map -->

<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8"  style="height:400px;border:0.5px solid #f7f8f8;">
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>

<!-- google map -->

<div class="col-md-12" style="height:30px;">
	<div class="row">
	</div>
</div>
<!-- google map -->





<!-- 橫線	 -->
<!--**** 旅客評論 ****-->


<!-- 空白 -->
<div class="col-md-12" style="height:50px;">
	<div class="row">
	</div>	
</div>
<!-- 空白 -->







<!--**** footer ****-->

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

<div class="col-md-12" id="footer02" style="height:200px;margin-top:-1px">
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
	
</body>
</html>