<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/hostelManager.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    
    
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/hostel/style/css/NavBar_top.css">
  
    <link rel="stylesheet" href="<%=request.getContextPath()%>/hostel/style/allView.css">
    
  <script>
	$(function() {
		
		$("#location").autocomplete({
		      source: "<%=request.getContextPath()%>/AjaxAutoComplete"
	    });
		
		
		  $("[name^='traceUp']").click(function(){
				var lastIdx=$(this).attr('name').lastIndexOf("-")+1;
				var hostelNo=$(this).attr('name').substring(lastIdx);
		    	$.ajax({
					 type:"GET",
					 url:"<%=request.getContextPath()%>/HostelTraceAjax",
					 data:{action:"addTrace",hostelNo:hostelNo},
					 dataType:"json",
					 success:function (data){
							traceListDisplay(data);
						
				     },
		             error:function(){}
	            })
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
	    $("#traceDropDown").click(function() {
	    
	    	$.ajax({
				 type:"GET",
				 url:"<%=request.getContextPath()%>/HostelTraceAjax",
				 data:{action:"getTrace"},
				 dataType:"json",
				 success:function (data){
					 
						traceListDisplay(data);
					
			     },
	             error:function(){}
             })
	    	  
	    	});

	  }); //jquery onReady end 
	
		function traceListDisplay(jsonList){
		  var traceListDisplay=document.getElementById("traceListDisplay");
		  $("#traceListDisplay").empty();
		  var showText=document.createElement("li");
		  showText.innerHTML="<a href='<%=request.getContextPath()%>/view/tenantViewList.jsp'>景點收藏清單<a>";
		  var hr=document.createElement("hr");
		  traceListDisplay.appendChild(showText);
		  traceListDisplay.appendChild(hr);
		  for(var i=0;i<jsonList.length;i++){
		  var li =document.createElement("li");
		  var a=document.createElement("a");
		  a.setAttribute("href","<%=request.getContextPath()%>/hostel/IDVhostelDisplay.jsp?hostelNo="+jsonList[i].hostelNo);
		  a.innerHTML=jsonList[i].traceHostelName;
		  li.appendChild(a);
		  traceListDisplay.appendChild(li);
		  }
	  }
</script> 


    <title>eChoice-民宿搜尋結果</title>
	

</head>
<body>


<!-- ****top bar for 旅客**** -->

<div class="col-md-12" id="topBar">
	<div class="row" class="navbar navbar-default" role="navigation">
	<div class="col-md-3" style="margin-top:20px;">
		<a href="<%=request.getContextPath()%>/HomePage.jsp"><img src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
	</div>
	<div class="col-md-9" id="top_menu" style="margin-top:20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a href="<%=request.getContextPath()%>/tool/SearchController?checkin=1971-1-1&checkout=1971-1-1&guests=1&location" id=""><i class="fa fa-home fa-2x"></i>找民宿</a></li>										
					 <li><a href="<%=request.getContextPath()%>/view/allViewInfo.jsp" target="iframe" ><i class="fa fa-picture-o fa-2x"></i>找景點</a></li>
					 <li><a href="<%=request.getContextPath()%>/tenant/tenant_One.jsp" id=""><i class="fa fa-user fa-2x"></i>個人資料管理</a></li>
					 <li class="dropdown" id="traceDropDown" >
				        <a class="dropdown-toggle" data-toggle="dropdown" href="<%=request.getContextPath()%>/view/tenantViewList.jsp"  >
				        <i class="fa fa-heart fa-2x" ></i>收藏
				        <span class="caret" ></span></a>
				        <ul class="dropdown-menu" id="traceListDisplay" >

				        </ul>
				   </li>
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
	<div class="col-md-12" id="Container">

				
	</div> </br></br></br>

<!-- //--------------container-------------------->
	<div id="content" class="col-md-1" ></div>
  
<!-- //--------------內容-------------------->
  <div id="content" class="col-md-10">
		
    <div id="searchBar" class="row">
			<div class="col-md-2" ></div>	
			 
      <div  class="col-md-7">
        <h1>立刻開始你的下一段旅行</h1></br>
        
        <div >
         <span> <form action="<%=request.getContextPath()%>/tool/SearchController" method="post">
				 <input
				  id="location"
				  type="text"
				  class="input-large input-contrast"
				  autocomplete="off"
				  name="location"
				  placeholder="您想去哪裡？"/>
					
					
			      <input
			      id="checkin"
			      type="text"
			      class="checkin input-large input-contrast"
			      name="checkin"
			      placeholder="入住日期" />
			      
			              
			      <input
			      id="checkout"
			      type="text"
			      class="checkout input-large input-contrast"
			      name="checkout"
			      placeholder="退房日期" />
		          
			     <select id="guests" name="guests">
			        <option value="1">1位房客</option>
					<option value="2">2位房客</option>
					<option value="3">3位房客</option>
					<option value="4">4位房客</option>
					<option value="5">5位房客</option>
					<option value="6">6位房客</option>
					<option value="7">7位房客</option>
					<option value="8">8位房客</option>
					<option value="9">9位房客</option>
			      </select>	        
          	&nbsp;
            <button class="btn btn-info btn-lg" type="submit" style="">
              <i class="glyphicon glyphicon-search"></i>
            </button>          
          
            </form>
            </span>
        </div> <!---input end--->
      </div> <!---searchBar end--->
   	</div>
  <div id="viewInfo" class="row">
 <c:if test="${not empty errorMsgs}">
  <font color='red'>請修正以下錯誤:
  <ul>
  <c:forEach var="message" items="${errorMsgs}">
    <li>${message}</li>
  </c:forEach>
  </ul>
  </font>
</c:if>
<c:if test="${empty errorMsgs}">
      <div id="viewLabel" >搜尋結果</div> <!---viewLabel end--->
        
        <c:forEach var="hostelVO" items="${hostelList}">
        <div id="viewDes">
          <div id="viewCard">
          <a href="<%=request.getContextPath()%>/hostel/IDVhostelDisplay.jsp?hostelNo=${hostelVO.hostelNo}" title="${hostelVO.hostelName}">
            <img id="viewPic" src="<%=request.getContextPath()%>/hostel/GetHostelPicture?pkNo=${hostelVO.hostelNo}" >
            				</a>
            <div id="viewContent">
              <p>${hostelVO.hostelName}</p>
            	<p>${hostelVO.hostelAddress}</p>
              
              <button id="viewCollect" type="button" class="btn btn-default btn-lg" name="traceUp-${hostelVO.hostelNo}">
                <span id="viewCollectImg" class="glyphicon glyphicon-heart" style="color:red"></span>加到收藏清單</button>
            </div><!---viewContent end--->
          </div><!---viewCard end--->
        </div> <!---viewDes end--->
      
        </c:forEach>

        
      
    </c:if>
  </div> <!---viewInfo end--->

  </div> <!---content end --->
  
 <!---不解為何跑版
  <div  id="footer">
		All Content Copyright &copy; 2016 Silvia Inc.
	</div>
--->
  
</body>
</html>