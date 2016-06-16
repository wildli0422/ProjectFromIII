<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostelOrder.model.*" %>
<%@ page import="com.hostel.model.*" %>
<%@ page import="com.dealer.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Date" %>
<%
// 	HttpSession session =request.getSession();
	DealerVO dealerVO=(DealerVO)session.getAttribute("dealerVObyAccount");
	
	DealerService dealerService = new DealerService();
	HostelVO hostelVO=dealerService.getHostelByDealerNo(dealerVO.getDealerNo());
	
	HostelOrderService hostelOrderService=new HostelOrderService();
	List<HostelOrderVO> hostelOrdList=hostelOrderService.getAllByHostelNo(hostelVO.getHostelNo());
// 	pageContext.setAttribute("hostelOrdList", hostelOrdList);
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
	
	Calendar cal=Calendar.getInstance();
	int year,month,dayOfMonth;
	String date;
	
	List<String> dateList=new ArrayList<String>();
	for(HostelOrderVO OrdVO:hostelOrdList){
		cal.setTime(OrdVO.getHostelOrderDate());
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH)+1;
		dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
		date=year+"-"+month+"-"+dayOfMonth;
		dateList.add(date);
		System.out.println(date);
	}
	pageContext.setAttribute("dateList", dateList);
	
%>
<!DOCTYPE html>
<html>
<head>
<!-- ****favicon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />
    <meta charset="utf-8">
<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">


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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/hostelCalendar.css">
<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css"> --%>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

	
<title>eChioce| 台灣民宿 | choose your life.</title>
    
	<script>
		
	
	
		$(function() {
			$("#tabs").tabs();
		});
		
		$(document).ready(function(){
			$("#management").click(function(){
				$(".managementBg").show();
				$(".managementContent").html(
					"<center><div style=\"font-weight:bold;font-size:26px;margin-top:120px; \">請登入帳號</div></center>"		
				).show();
			});
			$(".managementBg").click(function(){
				$(".managementBg").hide();
				$(".managementContent").hide();
			});
		});
		
		$(function(){
			$('tr td').append('<div style="text-align:center;height:100px">'+'<a href="javascript:presses${s.index}()">'
			+'<button style="width:50px;height:25px;background-color:#ccffcc">'
			+'</button></a>'
			+'</div>');  
		});
		$(function() {
			var count=0;
			$("button").click(function(){
				
				count++;
				if(count==1){
					$(this).css("background-color","#ff6666");
				}else if(count==2){
					$(this).css("background-color","#ff944d");
				}else{
					count=0;
					$(this).css("background-color","#ccffcc");
				}
			});
		});
		
		function drawCal(increment){
			month+=increment;
			if(month==0){
				month=12;
				year--;
			}else if(month==13){
				month=1;
				year++;
			}
			calendar(year,month);
			<c:forEach var="Date" items="${dateList}">
				$('button[id="date${Date}"]').attr("class","bt btn-warning btn-md");
			</c:forEach>
		}
		
		function calendar(year,month){
			var todayDate=new Date();
			var today=new Date(td.getFullYear(),td.getMonth(),td.getDate());
			var s='<table class="table table-bordered">';
			s+='<thead style="background-color:grey">'
			+'<tr><th class="drawLess" onclick="drawCal(-1)"><span class="glyphicon glyphicon-backward"></span><th colspan=5>'
				+year+'年'+month+'月<th class="drawMore" onclick="drawCal(1)"><span class="glyphicon glyphicon-forward"></span>';
			month--;
			s+='<tr><th>Sun<th>Mon<th>Tue<th>Wed<th>Thu<th>Fri<th>Sat</thead>';
			var date=new Date(year,month,1);
			var weekDay=date.getDay();
			var i;
			s+='<tr>';
			for(;weekDay>0;weekDay--)
				s+='<td>';
			for(i=1;i<32;){
				date=new Date(year,month,i);
				
					s+='<td>'+i+'<div style="text-align:center;height:50px">'
					+'<form method="post" action="<%=request.getContextPath()%>/cal.do"'
					+'target="_blank" onsubmit="window.open("","_blank","height=250,width=850,resizable=yes,scrollbars=yes")" >'
					+'<button class="btn btn-primary btn-md"style="width:50px;height:20px" id="date'+ year+"-"+(month+1)+"-"+i +'"></button>'					
					+'<input type="hidden" name="date"  id="date'+ year+"-"+(month+1)+"-"+i +'" value="'+ year+"-"+(month+1)+"-"+i +'">'
					+'</form></div>';
				
				date=new Date(year,month,++i);
				if(date.getMonth()!=month)
					break;
				weekDay=date.getDay();
				if(weekDay==0)
					s+='<tr>';
			}
			for(;weekDay<6;weekDay++)
				s+='<td>';
			s+='</table>';
			document.getElementById('cal').innerHTML=s;
		}
		var td=new Date();
		var year=td.getFullYear();
		var month=td.getMonth()+1;
		
		$(document).ready(function(){
				calendar(year,month);
		});

		//顯示有訂單的
		$(document).ready(function(){
			<c:forEach var="Date" items="${dateList}">
// 			alert("!")
				$('button[id="date${Date}"]').attr("class","bt btn-warning btn-md");
			</c:forEach>
			
		});
		
// 		function presses${s.index}(){
//        	 document.open("yahoo.com.tw", "" ,"height=250,width=850,left=65,top=157,resizable=yes,scrollbars=yes");
//         }
		
		

	</script>

</head>

<body>


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


<!-- 	test calendar -->
 
<!-- <div class="col-md-12"> -->
<!-- 	<div class="row"> -->
<!-- 		<div class="col-md-12" > -->
<!-- 			<div id="cont"></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->

<!-- 	test calendar -->

	
<!-- 	<div> -->
<!-- 		<a href=""><button class="btn btn-md btn-default">訂房</button></a> -->
<!-- 	</div> -->

<div class="col-md-12" style="margin-top:100px">
		<div class="row">
			<div class="col-md-2">
			</div>
			<div class="col-md-8" >
				<div id="test"></div>
				<div id="cal" style="width:100%;height:60%;">
				</div>
			</div>
			<div class="col-md-2">
			</div>
			
		</div>
</div>
<!-- //--------------container------------------ -->

<!-- //--------------------responese--------------- -->
	
<div class="col-md-12" id="footer01" style="margin-top:100px;">
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