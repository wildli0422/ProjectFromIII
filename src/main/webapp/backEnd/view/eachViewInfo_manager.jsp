
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.viewinfo.model.*"%>
<%@ page import="com.hostel.model.*"%>
<jsp:useBean id="listViewPhoto_ByViewno" scope="request"
	type="java.util.Set" />




<%
ViewInfoVO viewinfoVO = (ViewInfoVO) request.getAttribute("viewinfoVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
%>


<html>
<head>
<link href="style/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="style/css/eachView.css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery.js"></script>
<script src="style/js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=false"></script>



<meta charset="utf-8">
<link href="<%=request.getContextPath() %>/viewinfo/style/css/reset.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/viewinfo/style/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/viewinfo/style/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/viewinfo/style/css/eachView_manager.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script
	src="<%=request.getContextPath() %>/viewinfo/style/js/bootstrap.min.js"></script>
<script
	src="<%=request.getContextPath() %>/viewinfo/style/js/jquery_UI_methods.js"></script>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<title>���I�޲z�w�ӧO���I</title>



<title>each_echoice�w�޲z.jsp</title>
</head>

<body>



	<div class="col-md-12" id="Container">
		<div class="row" id="header" class="navbar navbar-default"
			role="navigation">

			<div class="col-md-5">
				<img src="style/images/Logo.png" id="logImg">
			</div>

			<div class="col-md-7" style="text-align: right;" id="top_menu">

				<ul class="nav navbar-nav navbar-right">
					<li><a href="#" id=""><span
							class="glyphicon glyphicon-sunglasses Member"></span> Member</a></li>
					<li><a href="#" id=""><span
							class="glyphicon glyphicon glyphicon-gift"></span> �s�i</a></li>
					<li><a href="#" id=""><span
							class="glyphicon glyphicon glyphicon-picture"></span> View</a></li>
					<li><a href="#" id=""><span
							class="glyphicon glyphicon glyphicon-pencil"></span> Verification</a></li>
					<li><a href="#" id=""><span
							class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<li><a href="#" id=""><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul>
			</div>
		</div>

	</div>
	</br>
	</br>
	</br>



	<!-- //--------------container------------------ -->



	<div id="titleDes" class="col-md-4">
		<h1>���I��T</h1>
	</div>
	<!-- titleDes -->


	<div id="titleDes" class="col-md-4" style="margin-left: 30px;">
		<h1>���I²��</h1>
	</div>
	<!-- titleDes -->


	<div id="content" class="col-md-12">
		<div id="viewcontent" class="col-md-12">
			<div class="row">


				<div id="viewDetail" class="col-md-5"
					style="background-color: pink;">

					<table>

						<tr>
							<td>���I�W��:&nbsp;&nbsp;</td>
							<td>${viewinfoVO.viewname}</td>
						</tr>

						<tr>
							<td>�޲z���:</td>
							<td>${viewinfoVO.viewmanager}</td>
						</tr>


						<tr>
							<td>�q��:</td>
							<td>${viewinfoVO.viewphone}</td>
						</tr>

						<tr>
							<td>�a�}:</td>
							<td>${viewinfoVO.viewaddress}</td>
						</tr>

						<tr>
							<td>����:</td>
							<td>${viewinfoVO.viewweb}</td>
						</tr>

						<tr>
							<td>�g�סG</td>
							<td>${viewinfoVO.viewlon}</td>
						</tr>
						<tr>
							<td>�n�סG</td>
							<td>${viewinfoVO.viewlat}</td>
						</tr>
						<tr>
							<td>�}��ɶ��G</td>
							<td>${viewinfoVO.viewopen}</td>
						</tr>
						<tr>
							<td>���I�����G</td>
							<td>${viewinfoVO.viewticket}</td>
						</tr>
						<tr>
							<td>���I�]�ơG</td>
							<td>${viewinfoVO.viewequi}</td>
						</tr>
						

					</table>

				</div>
				<!---viewDetail--->



				<div id="viewDes" class="col-md-3" style="background-color: pink;">

					<p>
						${viewinfoVO.viewcontent}
					<p>
				</div>
				<!---viewDes-->
			</div>
			<!--row-->

		</div>
		<!--viewcontent-->


//////////////////////////////////////////////////////////////



		<div id="viewImg" class="col-md-7" style="">

		<c:forEach var="viewphotoVO" items="${listViewPhoto_ByViewno}">
			<div class="view">
				<img class="viewpic" src="<%= request.getContextPath()%>/viewphoto/readviewphoto.do?viewpicno=${viewphotoVO.viewpicno}">
			</div>
		</c:forEach>
		</div>
		<!--viewImg-->
	</div>
	<!--content-->
























			
</body>
</html>
