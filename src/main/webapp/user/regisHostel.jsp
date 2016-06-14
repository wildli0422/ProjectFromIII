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
<title>Login</title>
	<link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

	
</head>
<body>
<!-- ****top bar for �ȫ�**** -->
<div class="col-md-12" id="topBar">
	<div class="row" class="navbar navbar-default" role="navigation">
	<div class="col-md-3" style="margin-top:20px;">
		<a href="<%=request.getContextPath()%>/HomePage.jsp"><img src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
	</div>
	<div class="col-md-9" id="top_menu" style="margin-top:20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a href="<%=request.getContextPath()%>/hostel_management.jsp" id=""><i class="fa fa-home fa-2x"></i>����J</a></li>
					<li><a href="<%=request.getContextPath()%>/hostel/hostel_select_page.jsp" target="iframe" ><i class="fa fa-picture-o fa-2x"></i>�䴺�I</a></li>
				</ul>
     </div>				

	</div>
</div>
<!-- ****top bar for �ȫ�**** -->	
	<div class="col-md-12">
		<div class="row">
	
			<div class='col-md-2'></div>
			<div class='col-md-4'>
			      	<c:if test="${not empty errorMsgs}">
						<font color="red">
						<c:forEach var="message" items="${errorMsgs}">
								${message}
						</c:forEach>
						</font>
					</c:if>
		        <div class="modal-body ">
		        	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dealer.reg"   enctype="multipart/form-data">
						
						<div class="form-group">
							<label for="">�W��</label>
							<input type="text" name="hostelName" class="form-control" placeholder="���J�W��"  >
						</div>
						<div class="form-group">
						  <label for="hostelPhone">�p���q��</label>
						  <input type="text" name="hostelPhone" class="form-control" id="hostelPhone" placeholder="�p���q��">
						</div>
						
						<div class="form-group">
						  <label for="">���J�a�}</label>
						  <input type=text name="hostelAddress" class="form-control" id="" placeholder="���J�a�}">
						</div>
						<div class="form-group">
						  <label for="hostelPicture">�ФW�Ǥ@�i���J�Ӥ�</label>
						  <input type="file" name="hostelPicture" class="form-control" id="hostelPicture" >
						</div>
						
						<div class="form-group">
						  <label for="dealerVerify">�����ҥ�</label>
						  <input type="file" name="dealerVerify" class="form-control" id="dealerVerify" >
						</div>
						
						
												
						<hr>
						<input type='hidden' name="action" value="hostel">
						<button class='btn btn-lg btn-block btn-info' type="submit" >�W��</button>
					</FORM>
				</div>
		        </div>
		        <div class='col-md-6'>
		        	<img src="<%=request.getContextPath()%>/images/dealer.png">
		        </div>
			
			
		</div>
	</div>


	
</body>
</html>