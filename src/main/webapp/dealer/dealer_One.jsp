<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.dealer.model.*" %>
<%@ page import="com.hostel.model.*" %>
<%
	DealerService dealerService=new DealerService();
	DealerVO dealerVO=(DealerVO)session.getAttribute("dealerVObyAccount");
	Integer dealerNo=new Integer(dealerVO.getDealerNo());
	HostelVO hostelVO=dealerService.getHostelByDealerNo(dealerNo);
%>    
<!DOCTYPE html>
<html>
<head>

<!-- ****favicon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="images/Logo_S.png" />



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
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<title>eChioce| 台灣民宿 | choose your life.</title>
<script type="text/javascript">
  $(function(){
	  $("#tab_1").click(function(){
		  $("#iframe").attr("src","<%=request.getContextPath()%>/management_hostel.jsp");
	  })
  })
</script>
</head>

<body>

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
					 <c:if test="${empty tenantVO}">
					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-in fa-2x"></i>登入</a></li> 
					 </c:if>
					 <c:if test="${not empty tenantVO}">
					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-out fa-2x"></i>登出</a></li>
					 </c:if>
				 	
				</ul>
     </div>				
	</div>
</div>

<!-- ****top bar for 旅客**** -->

<!-- <div class="col-md-12" id="topBar"> -->
<!-- 	<div class="row" class="navbar navbar-default" role="navigation"> -->
<!-- 	<div class="col-md-3" style="margin-top:20px;"> -->
<%-- 		<a href="<%=request.getContextPath()%>/checkCalendar_home.jsp"><img src="images/logo.png" id="logo"></a> --%>
<!-- 	</div> -->
<!-- 	<div class="col-md-9" id="top_menu" style="margin-top:20px;"> -->
<!-- 				<ul class="nav navbar-nav navbar-right" id="myTabs"> -->
<%-- 					<li><a href="<%=request.getContextPath()%>/hostel_management.jsp" id=""><i class="fa fa-home fa-2x"></i>找民宿</a></li> --%>
										
<%-- 					 <li><a href="<%=request.getContextPath()%>/hostel/hostel_select_page.jsp" target="iframe" ><i class="fa fa-picture-o fa-2x"></i>找景點</a></li> --%>
<%-- 					 <li><a href="<%=request.getContextPath()%>/dealer_management.jsp" id=""><i class="fa fa-user fa-2x"></i>個人資料管理</a></li> --%>
<!-- 					 <li class="dropdown"> -->
<!-- 				        <a class="dropdown-toggle" data-toggle="dropdown" href="#"> -->
<!-- 				        <i class="fa fa-heart fa-2x"></i>收藏 -->
<!-- 				        <span class="caret"></span></a> -->
<!-- 				        <ul class="dropdown-menu"> -->
<!-- 				           <li><a id="tab_1" href="#">景點收藏</a></li> -->
<%-- 				           <li><a href="<%=request.getContextPath()%>" target="iframe">民宿收藏</a></li>        --%>
<!-- 				        </ul> -->
<!-- 				      </li> -->
<%-- 					 <c:if test="${empty dealerVObyAccount}"> --%>
<%-- 					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-in fa-2x"></i>登入</a></li>  --%>
<%-- 					 </c:if> --%>
<%-- 					 <c:if test="${not empty dealerVObyAccount}"> --%>
<%-- 					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-out fa-2x"></i>登出</a></li> --%>
<%-- 					 </c:if> --%>
				 
				 	
<!-- 				</ul> -->
<!--      </div>				 -->

<!-- 	</div> -->
<!-- </div> -->

<div class="col-md-12" style="height:100px;">
	<div class="row">
		
	</div>
</div>

<div class="col-md-12" style="height:50px;">
	<div class="row">
		<div class="col-md-3">
		</div>	
		<div class="col-md-3" style="font-size:32px;">${dealerVObyAccount.dealerName}
				<c:if test="${dealerVObyAccount.dealerSex.equals("男") }">
				<span style="font-size:27px;font-family:微軟正黑體"><i> 先生		您好!</i></span>
				</c:if>
				<c:if test="${dealerVObyAccount.dealerSex.equals("女") }">
				<i><span style="font-size:27px;font-family:微軟正黑體"> 小姐		您好!</span></i>
				</c:if>
								
		</div>
		<div class="col-md-3" style="margin-left:200px;">
			<a href="" id="dealerUpdate" data-toggle="modal" data-target="#myModal">
			<i class="fa fa-cog fa-2x"></i>
			<span style="font-size:16px;">修改個人資料</span>
			</a>
		</div>
		<div class="col-md-3">
		</div>
	</div>
</div>

<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-md">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">修改個人資料</h4>
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
       
        	
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dealer/dealer.do" name="form1">
					<div class="modal-body">
						<div class="form-group">
							<label  class="control-label">帳號:</label>
							<span>${dealerVObyAccount.dealerMail}</span>
						</div>
						<div class="form-group">
							<label  class="control-label">密碼:</label>
							<input type="text"  name="dealerPassword" size="45" class="form-control"
								value="${dealerVObyAccount.dealerPassword}">
						</div>
						<div class="form-group">
							<label  class="control-label">匯款帳號:</label>
							<input type="text"  name="dealerAccount" size="45" class="form-control"
								value="${dealerVObyAccount.dealerAccount}">
						</div>
						<div class="form-group">
							<label  class="control-label">電話:</label>
							<input type="text"  name="dealerPhone" size="45" class="form-control"
								value="${dealerVObyAccount.dealerPhone}">
						</div>
						<div class="form-group">
							<label  class="control-label">地址:</label>
							<input type="text"  name="dealerAddress" size="45" class="form-control"
								value="${dealerVObyAccount.dealerAddress}">
						</div>
					</div>
					
					<div class="modal-footer">
						<input type="hidden" name="dealerNo" value="${dealerVObyAccount.dealerNo }">
			        	<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			            <input type="hidden" name="action" value="update">
						<input type="submit" value="修改" class="btn btn-danger">
			        </div>
				</FORM>		
			
        </div>
      </div>
    </div>
<!-- Modal -->

<!-- ********業者資訊******** -->

<div class="col-md-12">
	<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-6" style="font-size:32px;color:#ffffff;text-align:center;background-color:#5E676B;">
		<i class="fa fa-user fa-6x"></i>&nbsp;&nbsp;業者個人資料		
	</div>
	<div class="col-md-3">
	</div>
	</div>
</div>	
<div class="col-md-12">
	<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-6" style="font-size:22px;border:1px solid #5E676B;">
	<p>審核狀態  : 
	<c:if test="${dealerVObyAccount.dealerState==0}">未通過
	</c:if>
	<c:if test="${dealerVObyAccount.dealerState==1}">通過
	</c:if>
	</p>
	<p>手機/家裡電話  : ${dealerVObyAccount.dealerPhone}</p>
	<p>地址  : ${dealerVObyAccount.dealerAddress }</p>
	<p>帳戶  : ${dealerVObyAccount.dealerAccount }</p>
	</div>
	<div class="col-md-3">
	</div>
	</div>
</div>

<!-- ********民宿資訊******** -->

<div class="col-md-12">
	<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-6" style="font-size:32px;color:#ffffff;text-align:center;background-color:#5E676B;">
		<i class="fa fa-home fa-6x"></i>&nbsp;&nbsp;民宿資訊		
	</div>
	<div class="col-md-3">
	</div>
	</div>
</div>
<div class="col-md-12">
	<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-6" style="font-size:22px;border:1px solid #5E676B;">
	<p>民宿名稱  : <%=hostelVO.getHostelName()%></p>
	<p>民宿電話  : <%=hostelVO.getHostelPhone()%></p>
	<p>民宿地址  : <%=hostelVO.getHostelAddress()%></p>
	<p>民宿審核狀態  : 
	<c:if test="<%=hostelVO.getHostelVerification()==0%>">未通過
	</c:if>
	<c:if test="<%=hostelVO.getHostelVerification()==1%>">通過
	</c:if>
	</p>
	<p>民宿目前顯示狀態  : 
	<c:if test="<%=hostelVO.getHostelState()==0 %>">休息/下架中
	</c:if>
	<c:if test="<%=hostelVO.getHostelState()==1 %>">營業/上架中
	</c:if>		
	</p>
	</div>
	<div class="col-md-3">
	</div>
	</div>
</div>
<div class="col-md-12" style="height:150px;">
	<div class="row">
	</div>
</div>


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