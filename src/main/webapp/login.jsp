<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String account=(String)request.getAttribute("account");
	if(account==null || account.trim().length()==0){
		account="";
	}
%>
<!DOCTYPE html>
<html>
<head>

<!-- ****favicon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="images/Logo_S.png" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta charset="utf-8">
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

<!-- ****jquery**** -->
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


    
<title>eChioce| 台灣民宿  | choose your life.</title>


<script>
	$(document).ready(function() {
		$("#tenant").show();
		$("#dealer").hide();

		$("#tenantBt").click(function() {
			$("#tenant").show();
			$("#dealer").hide();
		});

		$("#dealerBt").click(function() {
			$("#tenant").hide();
			$("#dealer").show();
		});

	});
</script>

</head>

<body style="background-image:url('images/Login01.jpg');
			background-attachment:fixed;
            background-position:center;
            background-size:cover;">

<!--**** log in ****-->


	<div class="col-md-12" style="height:200px;">
		<div class=row>
		</div>
	</div>


	<div class="col-md-12" >
		<div class=row>
		<div class="col-md-4">
		</div>
		<div class="col-md-4" id="loginbox">
		<br>
		<br>
		<a href="<%=request.getContextPath() %>/HomePage.jsp">
		<img src="images/Logo_BO_01.png" style="margin-left:95px"></a>
		
	<!-- 	errorMsgs		 -->
	
			<p style="text-align: center; margin-top: 20px; font-size: 20px; height: 50px;">
				<c:if test="${not empty errorMsgs}">
				<font color="red">
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
						<li>${message}</li>
						</c:forEach>
					</ul>
				</font>
				</c:if>
			</p>
			<p style="text-align:center;">我是
				<a id="tenantBt" style="color:#33CCFF;font-size:20px">旅客</a>
				<a id="dealerBt" style="color:#FF6633;font-size:20px">民宿業者</a>
			</p>
			<!-- 旅客登入 -->
			
			<form action="<%=request.getContextPath()%>/loginServlet.do" method="post" id="tenant">
				
		          <p style="font-size:16px;text-align:center;">HI~旅人!</p>
		          <br>
		          <input class="form-control" type="email"name="account" value="<%=account%>" placeholder="Email/帳號" />
		          <br>
		          <input class="form-control" type="password" name="password" value="" placeholder="密碼" />
		          <br>
		          <input type="hidden" name="action"  value="tenantLogin">
		          <input type="submit" class="btn btn-primary btn-lg btn-block" value="Login">
		          <center><a href="<%=request.getContextPath() %>/user/forget.jsp" style="font-size:14px;">忘記密碼</a></center>
		        
			</form>
			
			<!-- 民宿業者登入 -->
			
			<form action="<%=request.getContextPath()%>/loginServlet.do" method="post" id="dealer">
				
				<p style="font-size:16px;text-align:center;">民宿業者,您好!</p>
				<br>
				<input class="form-control" type="text"name="account" value="<%=account%>" placeholder="帳號" />
				<br>
		        <input class="form-control" type="password" name="password" value="" placeholder="密碼" />
		        <br>
		         <input type="hidden" name="action" value="dealerLogin" >
		        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Login">
		        <center><a href="<%=request.getContextPath() %>/user/forget.jsp" style="font-size:14px;">忘記密碼</a></center>
					
			</form>
			
			<a style="text-align:center;font-size:25px;">一起旅行吧！</a>
			
			
			
			
			
			
			
			
		</div>
		<div class="col-md-4">
		</div>
	
		
		</div>
	</div>
	<div class="col-md-12" style="height:200px;">
		<div class=row>
		</div>
	</div>


</body>
</html>