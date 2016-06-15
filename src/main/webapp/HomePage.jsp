<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hostel.model.*"%>
<%@ page import="com.ad.model.*" %>
<%@ page import="com.viewinfo.model.*" %>
<%@ page import="com.viewphoto.model.*" %>
<%@ page import="java.sql.Date"%>
<%@ page import="java.util.Random"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<!-- ****favicon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- <link href="css/reset.css" rel="stylesheet" type="text/css" /> -->
   	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/HomePage.css">
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">	
	
<title>eChioce| 台灣民宿  | choose your life.</title>

<%  	
			Random rand = new Random();
			HostelService hosetlServ=new HostelService();
			List<HostelVO> hostelList = hosetlServ.getAll();
			int randomHostelIndex=rand.nextInt(hostelList.size());
			HostelVO showHostelVO=hostelList.get(randomHostelIndex);
			AdService adServ=new AdService();
			List<AdVO> adList=adServ.getAll();
			Date nowDate=new Date(System.currentTimeMillis());
			for(int i=adList.size()-1;i<0;i--){
				AdVO ad=adList.get(i);
				if((ad.getOdStatus()!=1)&&(ad.getAdStatus()!=1)){
					adList.remove(i);
				}
				else if((ad.getAdStartline().equals(nowDate)||ad.getAdDeadline().equals(nowDate))||
							(ad.getAdStartline().before(nowDate)&&ad.getAdDeadline().after(nowDate))){
					adList.remove(i);
				}
			}
			AdVO showAdVO=adList.get(rand.nextInt(adList.size()));
			AdVO showAdVO2=adList.get(rand.nextInt(adList.size()));
			pageContext.setAttribute("showAdVO2", showAdVO2);
			for(int i =0;i<hostelList.size();i++){
				if(hostelList.get(i).getDealerNo().equals(showAdVO.getDealerNo())){
					pageContext.setAttribute("showAdHostelVO", hostelList.get(i));
				}
			}
			for(int i =0;i<hostelList.size();i++){
				if(hostelList.get(i).getDealerNo().equals(showAdVO2.getDealerNo())){
					pageContext.setAttribute("showAdHostelVO2", hostelList.get(i));
				}
			}
			ViewInfoService viewServ=new ViewInfoService();
			ViewPhotoService viewPhotoServ=new ViewPhotoService();
			List<ViewInfoVO> viewInfoList=viewServ.getAll();
			int randomIndex=rand.nextInt(viewInfoList.size());
			ViewInfoVO showViewInfoVOLeft=viewInfoList.get(randomIndex);
			
			pageContext.setAttribute("showViewPicNoLeft", viewPhotoServ.getOneByViewNo(showViewInfoVOLeft.getViewno()).getViewpicno());
			pageContext.setAttribute("showViewInfoVOLeft", showViewInfoVOLeft);
			viewInfoList.remove(randomIndex);
			randomIndex=rand.nextInt(viewInfoList.size());
			ViewInfoVO showViewInfoVORight=viewInfoList.get(randomIndex);
			//in case take the same hostelVO above 
			hostelList.remove(randomHostelIndex);
			HostelVO showHostelVOmiddle=hostelList.get(rand.nextInt(hostelList.size()));
			pageContext.setAttribute("showHostelVOmiddle", showHostelVOmiddle);
			pageContext.setAttribute("showViewInfoVORight", showViewInfoVORight);
			pageContext.setAttribute("showViewPicNoRight", viewPhotoServ.getOneByViewNo(showViewInfoVORight.getViewno()).getViewpicno());
			pageContext.setAttribute("showHostelVO", showHostelVO);
			pageContext.setAttribute("showAdVO", showAdVO);
			
			
  %>
<script>
	$(function() {
		$("#location").autocomplete({
		      source: "<%=request.getContextPath()%>/AjaxAutoComplete",
		      delay:100
	    });
		
	    $( "#checkin" ).datepicker({
	      defaultDate: "+1w",
	      changeMonth: true,
	      numberOfMonths: 1,
	      dateFormat: 'yy-mm-dd',
	      onClose: function( selectedDate ) {
	        $( "#checkout" ).datepicker( "option", "minDate", selectedDate );
	      }
	    });
	    $( "#checkout" ).datepicker({
	      defaultDate: "+1w",
	      changeMonth: true,
	      numberOfMonths: 1,
	      dateFormat: 'yy-mm-dd',
	      onClose: function( selectedDate ) {
	        $( "#checkin" ).datepicker( "option", "maxDate", selectedDate );
	      }
	    });
	  }); 
</script>  
   
</head>
<body>

  
<div class="col-md-12">
	<div class="row">
		<div style="background-image:url('images/Travel_HomePage01.jpg');
			height:919px;
            background-position:center;
            background-size:cover;">
			<c:if test="${empty tenantVO and empty dealerVObyAccount}">
				<a href="<%=request.getContextPath()%>/login.jsp" class="login">登入</a>
				<a class="registered" data-toggle="modal" data-target="#myModal">註冊</a>
				<a href="<%=request.getContextPath()%>/user/regisDealer.jsp" style="border: 2px solid white;" class="registered">成為房東</a>	 
			</c:if>
			<c:if test="${not empty tenantVO or not empty dealerVObyAccount}">
				<a href="<%=request.getContextPath()%>/loginServlet.do?action=logout" class="login">登出</a>
			</c:if>
				
<!-- 			dealer -->					
			<c:if test="${not empty tenantVO}">
				<a href="<%=request.getContextPath()%>/tenant/tenant_One.jsp" class="login"
					>Hi ${tenantVO.tenantName}</a>
			</c:if>
			<c:if test="${not empty dealerVObyAccount}">
				<a href="<%=request.getContextPath()%>/dealer/dealer_One.jsp" class="login"
					>Hi ${dealerVObyAccount.dealerName}</a>
				<a href="<%=request.getContextPath()%>/hostel/hostelCalendar.jsp" class="login"> 民宿管理</a>
			</c:if>
			
		</div>
	</div>
</div>

<div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog modal-sm" >
		      <div class="modal-content">
		      	<div class='row'>
		      	<div class='col-md-1'></div>
		      	<div class='col-md-10'>
			      	<c:if test="${not empty errorMsgs}">
						<font color="red">
						<c:forEach var="message" items="${errorMsgs}">
								${message}
						</c:forEach>
						</font>
					</c:if>
		        <div class="modal-body ">
		        	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/registerTen.do" name="register">
						<div class="form-group">
						  <label for="tenantMail"></label>
						  <input type="email" name="tenantMail" class="form-control" id="tenantMail" placeholder="電郵地址" required>
						  
						</div>
						<div class="form-group">
						  <label for="tenantPassword"></label>
						  <input type="password" name="tenantPassword" class="form-control" id="tenantPassword" placeholder="密碼" required>
						</div>
						<div class="form-group">
						  <label for="tenantName"></label>
						  <input type="text" name="tenantName" class="form-control" id="tenantName" placeholder="姓名" required>
						</div>
						
						<div class="form-group">
						  <label for="tenantPhone"></label>
						  <input type="text" name="tenantPhone" class="form-control" id="tenantPhone"
						  onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="if 
　　							 ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false"
						   placeholder="連絡電話" required>
						</div>
						
						<hr>
						
						<div style="text-align:center;">與我們一同旅行吧</div>
						<br>
						<button class='btn btn-lg btn-block btn-info' type="submit" >註冊</button>
					</FORM>
				</div>
		        </div>
		        		      	<div class='col-md-1'></div>
		        </div>
		      </div>
		    </div>
		  </div>

<!-- <div class="col-md-12"> -->
<!-- 	<div class="row" style="margin-top:-1px;bottom:20px;"> -->
	
<%-- 		<img src="<%=request.getContextPath()%>/images/Travel_bottom02.jpg" width="100%"> --%>
	
<!-- 	</div> -->
<!-- </div> -->

		
	
<div class="col-md-12" id="searchbox">
		<div class="row">
		<div class="col-md-1">
		</div>
		 <div class="col-md-10" id="searchbox_in">
		 			
			<div style="margin-top:26px;vertical-align:middle;">
			<form action="<%=request.getContextPath()%>/tool/SearchController" method="post">
				 <input id="location" type="text" class="input-large input-contrast"
				  autocomplete="off"  name="location" placeholder="您想去哪裡？"/>
					
			      <input id="checkin" type="text" class="checkin input-large input-contrast"
			      name="checkin" placeholder="入住日期" />
			      
			              
			      <input id="checkout" type="text" class="checkout input-large input-contrast"
			      name="checkout" placeholder="退房日期" />
		          
			     <select id="guests" name="guests">
			     	<c:forEach begin="1" end="10" varStatus="i">
			        <option value="${i.count}">${i.count}位房客</option>
			        </c:forEach>
			      </select>	        
				  <button type="submit" id="search" class="btn btn-danger">
					搜索
				  </button>
				  </form>
			</div>
				    
		</div>
		<div class="col-md-1" >
		</div>
		</div>
</div>
				
<div class="col-md-12" style="height:100px;">
	<div class="row">
	
	
	</div>
</div>

<div class="col-md-12" style="height:200px;text-align:center;">
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-4" id="slogn">
			<p style="font-weight:bold;">台灣好好玩</p>
			<p style="font-size:20px;">台灣之美與夢想</p>
		</div>
		<div class="col-md-4">
		</div>
	</div>
</div>


<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<div class="col-md-12">
		<div class="row">
			<div class="col-md-8">
				<a href="<%=request.getContextPath()%>/hostel/IDVhostelDisplay.jsp?hostelNo=${showHostelVO.hostelNo}" title="${showHostelVO.hostelName}">
			    <img src="<%=request.getContextPath()%>/hostel/GetHostelPicture?pkNo=${showHostelVO.hostelNo}" style="width:720px;height:544px;margin-left:80px">
			    </a>
			</div>
			<div class="col-md-4">
				<a href="<%=request.getContextPath()%>/hostel/IDVhostelDisplay.jsp?hostelNo=${showAdHostelVO.hostelNo}" title="${showAdHostelVO.hostelName}">
			    <img src="<%=request.getContextPath()%>/tool/GetPhoto?tableName=Ad&pkNo=${showAdVO.adNo}" style="width:340px; height:272px;">
			    </a>
			    <a href="<%=request.getContextPath()%>/hostel/IDVhostelDisplay.jsp?hostelNo=${showAdHostelVO2.hostelNo}" title="${showAdHostelVO2.hostelName}">
			    <img src="<%=request.getContextPath()%>/tool/GetPhoto?tableName=Ad&pkNo=${showAdVO2.adNo}" style="width:340px; height:272px;">
			    </a>
			</div>
		</div>
		</div>
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>

<div class="container" style="width:930px;height:10px">
	<div class="row">
	<div class="col-md-12">
	
	</div>
	</div>
</div>	
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">	
		<div class="col-md-12">
		<div class="row">	
	 		<div class="col-md-4">
			    <a href="<%=request.getContextPath()%>/view/eachView.jsp?viewno=${showViewInfoVOLeft.viewno}" title="${showViewInfoVOLeft.viewname}">
			    <img src="<%=request.getContextPath()%>/tool/GetPhoto?tableName=viewPhoto&pkNo=${showViewPicNoLeft}" style="width:360px;height:236px;margin-left:80px;margin-top:20px">
			    </a>
		   	</div>
		   	<div class="col-md-4">
			    <a href="<%=request.getContextPath()%>/hostel/IDVhostelDisplay.jsp?hostelNo=${showHostelVOmiddle.hostelNo}" title="${showHostelVOmiddle.hostelName}">
			    <img src="<%=request.getContextPath()%>/hostel/GetHostelPicture?pkNo=${showHostelVOmiddle.hostelNo}" style="width:340px;height:236px;margin-left:50px;margin-top:20px">
			    </a>
		    </div>
		    <div class="col-md-4">
			    <a href="<%=request.getContextPath()%>/view/eachView.jsp?viewno=${showViewInfoVORight.viewno}" title="${showViewInfoVORight.viewname}">
			    <img src="<%=request.getContextPath()%>/tool/GetPhoto?tableName=viewPhoto&pkNo=${showViewPicNoRight}" style="width:340px;height:236px;margin-right:100px;margin-top:20px">
			    </a>
		    </div>
		</div>
		</div>
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>

<div class="col-md-12" style="height:300px">
	<div class="row">
	<form action="<%=request.getContextPath()%>/tool/SearchController" method="post">
		<input type="image" src="<%=request.getContextPath()%>/images/find_Hostel.png" style="padding:0 0 0;position:fixed;right:20px;bottom:10px" >
		<input  type="hidden"	name="checkin" value="1971-1-1" />
		<input  type="hidden" name="checkout" value="1971-1-1"/>
		<input type="hidden"  name="guests" value="1"/>
		<input type="hidden" name="location"/>
	</form>
	<a href="<%=request.getContextPath()%>/view/allViewInfo.jsp">
	<input type="image" src="<%=request.getContextPath()%>/images/find_View.png" style="padding:0 0 0;position:fixed;right:20px;bottom:120px"></a>
	</div>
</div>



<!-- footer -->

<div class="col-md-12" id="footer01" style="margin-top:2px">
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
</body>