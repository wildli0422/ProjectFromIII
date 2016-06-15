<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.room.model.*"%>
<%@ page import="com.dealer.model.*"%>
<%@ page import="com.hostel.model.*"%>
<%
	DealerVO dealerVO=(DealerVO)session.getAttribute("dealerVObyAccount");
	Integer dealerNo=dealerVO.getDealerNo();
	
	DealerService dealerService=new DealerService();
	HostelVO hostelVO=dealerService.getHostelByDealerNo(dealerNo);
	
	HostelService hostelService= new HostelService();
	Integer hostelNo=hostelVO.getHostelNo();
	
	List<RoomVO> roomList=hostelService.getRoomsByHostelNo(hostelNo);
	
	RoomService roomService=new RoomService();
	pageContext.setAttribute("roomService", roomService);
	
	RoomTypeService roomTypeService=new RoomTypeService();
	pageContext.setAttribute("roomTypeService", roomTypeService);
%>



<!DOCTYPE html >
<html>
<head>
<!-- <meta http-equiv="refresh" content="10"> -->
<!-- ****bootstrap icon**** -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="path/to/font-awesome/css/font-awesome.min.css">

<!-- ****jquery**** -->
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<!-- ****bootstrap**** -->
<link href="<%=request.getContextPath()%>/css/reset.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/NavBar_top.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<script type="text/javascript">
	   $(function(){
		   var curCss="";
		   $(".live").find("span").hover(
					function(){
						curCss=$(this).css("color");
						$(this).css("color","#ff9999");						
					},function(){$(this).css("color",curCss);});
		   $(".empty").find("span").hover(
					function(){
						curCss=$(this).css("color");
						$(this).css("color","#99ff99");						
					},function(){$(this).css("color",curCss);})
			$(".clean").find("span").hover(
					function(){
						curCss=$(this).css("color");
						$(this).css("color","#ffcc99");						
					},function(){$(this).css("color",curCss);})
	   })
	   
// 	   $(document).ready(function(){
		   
// 	   })
// 	   function getRoomState(){
// 		   $('.forAjax').ready(function(){
// 			   $.ajax({
// 				   type:"get",
// 				   url:"/emptyRoomType.jsp",
				   
// 			   })
// 		   } )
		   
// 	   }
	   
	   
// 	   function getRoomState(){
// 		   var url = "/room/emptyRoomType.jsp";  
// 	       var data = {type:1};  
// 	       $.ajax({  
// 	           type : "get",  
// 	           async : false,  //同步请求  
// 	           url : url,  
// 	           data : data,  
// 	           success:function(dates){  
// // 	               alert(dates);  
// 	               $("#forAjax").html(dates);//要刷新的div  
// 	           },  
// 	           error: function() {  
// 	              alert("error");  
// 	           }  
// 	       });  
// 	   }
		function getRoomState(){
			
			$('#forAjax').load('<%=request.getContextPath()%>/room/emptyRoomType.jsp #forAjax');
<%-- 			alert('<%=request.getContextPath()%>/room/emptypRoomType.jsp #forAjax'); --%>
			$("#forAjax").empty();
		}
		
	   
	   setInterval(getRoomState, 1000);
		
	   function getCurrTime(){
		 		 var theDate = new Date();
	      		document.getElementById("currTime").innerHTML = theDate.getHours()+":"+theDate.getMinutes()+":"+theDate.getSeconds();
// 	      		setTimeout('getCurrTime()',1000);  //每1000毫秒別叫一次
	   }
	   setInterval(getCurrTime, 1000);
	   
	   
	</script>
<title>empty RoomType</title>
</head>
<body bgcolor="white" onload="connect()" onunload="disconnect()">

	<div class="col-md-12" id="topBar">
		<div class="row" class="navbar navbar-default" role="navigation">
			<div class="col-md-3" style="margin-top: 20px;">
				<a href="<%=request.getContextPath()%>/HomePage.jsp"><img
					src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
			</div>
			<div class="col-md-9" id="top_menu" style="margin-top: 20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a
						href="<%=request.getContextPath()%>/hostel/management_hostel.jsp"
						id=""><i class="fa fa-home fa-2x"></i> 民宿基本資料</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"> <i class="fa fa-bed fa-2x"></i>房務管理
							<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a id="tab_1"
								href="<%=request.getContextPath()%>/room/roomType_modify.jsp">房間基本資料</a></li>
							<li><a
								href="<%=request.getContextPath()%>/room/emptyRoomType.jsp"
								target="iframe">房間狀態管理</a></li>
						</ul></li>
					<li><a
						href="<%=request.getContextPath()%>/hostel/hostelCalendar.jsp"
						id=""><i class="fa fa-calendar fa-2x"></i> 訂單管理</a></li>
					<li><a
						href="<%=request.getContextPath()%>/dealer/dealer_One.jsp" id=""><i
							class="fa fa-user fa-2x"></i> 個人資料頁面</a></li>
					<c:if test="${empty dealerVObyAccount}">
						<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i
								class="fa fa-sign-in fa-2x"></i> 登入</a></li>
					</c:if>
					<c:if test="${not empty dealerVObyAccount}">
						<li><a
							href="<%=request.getContextPath()%>/loginServlet.do?action=logout"
							id=""><i class="fa fa-sign-out fa-2x"></i> 登出</a></li>
					</c:if>

				</ul>
			</div>
		</div>
	</div>

	<!--------------------------------- HEADER ------------------------------------>

	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="row" style="margin-top: 50px;">
				現在時間:<span id="currTime" style="color: blue;"></span>
					<c:forEach var="roomVO" items="<%=roomList%>">
						<div id="forAjax" >
							
							<div class="col-md-3" style="text-align: center;" id="inner">
								<div style="font-size: 40px;">${roomVO.roomNo }
									<span style="font-size: 40px;">${roomTypeService.getOneRoomType(roomVO.roomTypeNo).roomTypeName }</span>
								</div>
								<%-- 						<a href="#"><span class="glyphicon glyphicon-bed" style="${(roomVO.roomState=='住房')?'color:#ff6666;':''};font-size:160px;"></span></a> --%>
								<div>
								<c:choose>
									<c:when test="${roomVO.roomState=='住房' }">
										<a href="#" class="live"
											onClick="alertRoom(${roomVO.roomNo},${roomTypeService.getOneRoomType(roomVO.roomTypeNo).roomTypeNo })">
											<span class="glyphicon glyphicon-bed"
											style="color: #ff8080; font-size: 160px;"> </span>
										</a>
									</c:when>
									<c:when test="${roomVO.roomState=='空房'}">
										<a href="#" class="empty"
											onClick="alertRoom(${roomVO.roomNo},${roomTypeService.getOneRoomType(roomVO.roomTypeNo).roomTypeNo })">
											<span class="glyphicon glyphicon-bed"
											style="color: #00cc99; font-size: 160px;"> </span>
										</a>
									</c:when>
									<c:when test="${roomVO.roomState=='清潔' }">
										<a href="#" class="clean"
											onClick="alertRoom(${roomVO.roomNo},${roomTypeService.getOneRoomType(roomVO.roomTypeNo).roomTypeNo })">
											<span class="glyphicon glyphicon-bed"
											style="color: #ffb366; font-size: 160px;"> </span>
										</a>
									</c:when>
									<c:when test="${roomVO.roomState=='訂房' }">
										<a href="#"
											onClick="alertRoom(${roomVO.roomNo},${roomTypeService.getOneRoomType(roomVO.roomTypeNo).roomTypeNo })">
											<span class="glyphicon glyphicon-bed"
											style="font-size: 160px;"> </span>
										</a>
									</c:when>
								</c:choose>
								<hr>
								</div>
							</div>
						</div>

					</c:forEach>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>

	<script>
		function alertRoom(roomNo,roomTypeNo){
			var getRoomNo=roomNo;
			var getRoomTypeNo=roomTypeNo;
			window.open('<%=request.getContextPath()%>/room/roomState.jsp?roomNo='+getRoomNo+'&roomTypeNo='+getRoomTypeNo ,'room',
					'scrollbars=no,resizable=no,location=no,status=no,menubar=no,toolbar=no,height=200,width=800,top=285,left=600');
		}
		
		
		
	</script>




	<!-- //------------footer-------------- -->
	<!--**** footer ****-->

	<div class="col-md-12" id="footer01">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<p>© eChoice, Inc.</p>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>

	<div class="col-md-12" id="footer02"
		style="height: 200px; margin-top: -1px">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-4">

				<ul>
					<li>關於我們</li>
					<li>政策</li>
					<li>幫助</li>
					<li>Blog</li>
					<li>服務條款與私隱聲明</li>
				</ul>

			</div>
			<div class="col-md-2"></div>
			<div class="col-md-4">
				<ul>
					<li>常見問題</li>
					<li>聯絡我們</li>
					<li>廣告刊登</li>
				</ul>
				<i class="fa fa-phone fa-2x"></i>&nbsp;&nbsp; <i
					class="fa fa-envelope-o fa-2x"></i>&nbsp;&nbsp; <i
					class="fa fa-facebook-square fa-2x"></i>&nbsp;&nbsp; <i
					class="fa fa-twitter-square fa-2x"></i>&nbsp;&nbsp; <i
					class="fa fa-android fa-2x"></i>&nbsp;&nbsp; <i
					class="fa fa-print fa-2x"></i>


			</div>
		</div>
	</div>
	<!-- //------------footer-------------- -->

</body>
</html>