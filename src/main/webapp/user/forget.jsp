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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">


<!-- ****jquery**** -->
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<!-- ****Sweet Alert**** -->
<script src="<%=request.getContextPath()%>/js/sweetalert-dev.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/sweetalert.css">

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
		
		$("#forget").click(function() {
			 swal("密碼已寄至你的信箱");
			 setTimeout(function() {submit()} , 5000);
			
		});
		
	});
	
	
</script>

</head>

<body style="background-image:url('<%=request.getContextPath()%>/images/Login01.jpg');
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
		<img src="<%=request.getContextPath()%>/images/Logo_BO_01.png" style="margin-left:95px;">
		
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
			<p style="text-align:center;">
				<a id="tenantBt" style="color:#33CCFF;font-size:20px">旅客</a>
				<a id="dealerBt" style="color:#FF6633;font-size:20px">民宿業者</a>
			</p>
			
			
			<form action="<%=request.getContextPath()%>/forget" id="tenant" method="post">
				<div>旅客</div>
				<div class="form-group">
					<label for="tenantMail"></label>
					<input type="hidden" name="who" value="tenant">
					<input type="text" name="Mail" class="form-control" id="tenantMail" placeholder="輸入註冊時的信箱">
				</div>	
				<br>
				<button class='btn btn-lg btn-block btn-info'  id="forget">送出</button>
			
			</form>


			<form action="<%=request.getContextPath()%>/forget" id="dealer" method="post" >
				<div>民宿業者</div>
				<div class="form-group">
					<label for="dealerMail"></label>
					<input type="hidden" name="who" value="dealer">
					<input type="text" name="Mail" class="form-control" id="dealerMail" placeholder="輸入註冊時的信箱">
				</div>
				<br>
				<button class='btn btn-lg btn-block btn-info'  id="forget">送出</button>
			</form>			
			
			
			
			
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