<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
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
<!-- <meta http-equiv="Content-Type" content="text/html; charset=BIG5"> -->
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


    
<title>eChioce| 台灣民宿  | choose your life.</title>
	<script>
		$(document).ready(function(){
			$("#tenant").show();
			$("#dealer").hide();
			
			$("#tenantBt").click(function(){
				$("#tenant").show();
				$("#dealer").hide();
			});
			
			$("#dealerBt").click(function(){
				$("#tenant").hide();
				$("#dealer").show();
			});
			
		});
	</script>
	
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
					<li><a href="<%=request.getContextPath()%>/hostel_management.jsp" id=""><i class="fa fa-home fa-2x"></i>找民宿</a></li>
					<li><a href="<%=request.getContextPath()%>/hostel/hostel_select_page.jsp" target="iframe" ><i class="fa fa-picture-o fa-2x"></i>找景點</a></li>
				</ul>
     </div>				

	</div>
</div>
<!-- ****top bar for 旅客**** -->	
	<div class="col-md-12">
		<div class="row">
	
			<div class='col-md-4'></div>
			<div class='col-md-4'>
			      	<c:if test="${not empty errorMsgs}">
						<font color="red">
						<c:forEach var="message" items="${errorMsgs}">
								${message}
						</c:forEach>
						</font>
					</c:if>
		        <div class="modal-body ">
		        	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dealer.reg" name="register">
						<div class="form-group">
						  <label for="dealerMail"></label>
						  <input type="email" name="dealerMail" class="form-control" id="dealerMail" placeholder="電郵地址">
						</div>
						<div class="form-group">
						  <label for="dealerPassword"></label>
						  <input type="password" name="dealerPassword" class="form-control" id="dealerPassword" placeholder="密碼">
						</div>
						<div class="form-group">
						  <label for="dealerName"></label>
						  <input type="text" name="dealerName" class="form-control" id="dealerName" placeholder="姓名">
						</div>
						
						<div class="form-group">
						  <label for="dealerSex">性別</label>
						  <input type="radio" name="dealerSex" id="dealerSex" value="男">男
						  <input type="radio" name="dealerSex" id="dealerSex" value="女">女
						</div>
						<div class="form-group">
						  <label for="dealerAddress"></label>
						  <input type="text" name="dealerAddress" class="form-control" id="dealerAddress" placeholder="通訊地址">
						</div>
						<div class="form-group">
						  <label for="dealerPhone"></label>
						  <input type="text" name="dealerPhone" class="form-control" id="dealerPhone" placeholder="連絡電話">
						</div>
						<div class="form-group">
						  <label for="dealerAccount"></label>
						  <input type="text" name="dealerAccount" class="form-control" id="dealerAccount" placeholder="收款帳號">
						</div>
												
						<hr>
						<input type='hidden' name="action" value="dealer">
						<button class='btn btn-lg btn-block btn-info' type="submit" >註冊</button>
					</FORM>
				</div>
		        </div>
		        <div class='col-md-4'></div>
			
			
		</div>
	</div>


	
</body>
</html>