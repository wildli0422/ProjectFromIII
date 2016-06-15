<%@page import="com.dealer.model.DealerService"%>
<%@page import="com.accessemp.model.AccessEmpService"%>
<%@page import="com.emp.model.EmpVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.viewinfo.model.*"%>
<%@ page import="com.hostel.model.*"%>

<%
	EmpVO empVO = new EmpVO();
	empVO = (EmpVO) session.getAttribute("loginEmp");
	AccessEmpService accessEmpService = new AccessEmpService();
	DealerService dealerService = new DealerService();

	List<Integer> accessList = new LinkedList<Integer>();
	accessList = accessEmpService.getEmpAccess(empVO.getEmpNo());
	ViewInfoService viewInfoService = new ViewInfoService();
	
	
	ViewInfoVO viewinfoVO = (ViewInfoVO) request.getAttribute("viewinfoVO");
	Integer viewno = new Integer(viewinfoVO.getViewno());
	pageContext.setAttribute("viewno", viewno);
%>

<!DOCTYPE html>
<html>
<head>
<title>eChoice | BackEnd</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/backEnd/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/backEnd/dist/css/AdminLTE.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/backEnd/dist/css/skins/skin-blue.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>


<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Main Header -->
		<header class="main-header">

			<!-- Logo -->
			<a href="<%=request.getContextPath()%>/backEnd/index.jsp"
				class="logo"> <span class="logo-mini"><b>e</b>C</span> <span
				class="logo-lg"><b>BackEnd</b></span>
			</a>

			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">

						<li class="dropdown notifications-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-bell-o"></i> <span class="label label-warning"><%=dealerService.countState0()%></span>
						</a>
							<ul class="dropdown-menu">
								<li class="header"></li>
								<li>
									<ul class="menu">
										<li>
											<!-- start notification --> <a href="#"> <i
												class="fa fa-users text-aqua"></i>有<%=dealerService.countState0()%>位業者尚未通過認證
										</a>
										</li>
										<!-- end notification -->
									</ul>
								</li>
								<li class="footer"><a href="#"></a></li>
							</ul></li>
						<!-- Tasks Menu -->

						<!-- User Account Menu -->
						<li class="dropdown user user-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <img
								src="<%=request.getContextPath()%>/ImageReader?empNo=${loginEmp.empNo}"
								class="user-image" alt="User Image"> <span
								class="hidden-xs">${loginEmp.empName}</span>
						</a>
							<ul class="dropdown-menu">

								<!-- Menu Footer-->
								<li class="user-footer">
									<div class="pull-left">
										<a
											href="<%=request.getContextPath()%>/backEnd/emp/empMyInfo.jsp"
											class="btn btn-default btn-flat">Profile</a>
									</div>
									<div class="pull-right">
										<a href="<%=request.getContextPath()%>/backEnd/logout.jsp"
											class="btn btn-default btn-flat">Sign out</a>
									</div>
								</li>
							</ul></li>
					</ul>
				</div>
			</nav>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">

			<section class="sidebar">



				<!-- Sidebar Menu -->
				<ul class="sidebar-menu">
					<li class="header">${loginEmp.empMail}</li>
            <!-- Optionally, you can add icons to the links -->            
            <li class="active"><a href="<%=request.getContextPath()%>/backEnd/emp/empMyInfo.jsp"><i class="fa fa-dashboard"></i> <span>個人資料管理</span></a></li>
            
            
           	<c:if test="${not empty ac9001 }">
				<li><a href="<%=request.getContextPath()%>/backEnd/emp/empManager.jsp"><i class="fa fa-user"></i> <span>員工查詢/管理</span></a></li>
			</c:if>
            

			<c:if test="${not empty ac9002}">
				<li class="treeview">
              	<a href="#"><i class="fa fa-search"></i> <span>會員查詢/管理</span> <i class="fa fa-angle-left pull-right"></i></a>
              	<ul class="treeview-menu">
               		<li><a href="<%=request.getContextPath()%>/backEnd/user/tenant.jsp">旅客會員</a></li>
               		
               		<c:if test="${not empty ac9004}">
              		<li><a href="<%=request.getContextPath()%>/backEnd/user/dealerManager.jsp">民宿業者會員</a></li>
              		<li><a href="<%=request.getContextPath()%>/backEnd/user/hostel.jsp">民宿查詢</a></li>
              		</c:if>
              	</ul>
            	</li>
			</c:if>
			
			<c:if test="${not empty ac9003}">
				<li><a href="<%=request.getContextPath()%>/backEnd/view/view.jsp"><i class="fa fa-link"></i> <span>景點資訊管理</span></a></li>
			</c:if>
            
				</ul>
				<!-- /.sidebar-menu -->
			</section>
			<!-- /.sidebar -->
		</aside>


		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">

			<section class="content">
				<div class='row'>

					<form role="form" class="form-horizontal" METHOD="post"
						ACTION="<%=request.getContextPath()%>/viewinfo/viewinfo.do">
						<div class="row">

							<div class=" col-md-1">
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<input type="hidden" name="requestURL"
											value="<%=request.getServletPath()%>"> <input
											type="hidden" name="viewno" value="${viewinfoVO.viewno}">
										<input type="hidden" name="action" value="update">
										<button type="submit" class="btn btn-primary">修改資料</button>
										</br> </br>
									</div>
								</div>







							</div>
						</div>

						<div class="row col-md-6" style="margin-left: 10px;">
							<div class="box box-primary">
								<div class="box-header">
									<h3 class="box-title">景點資料</h3>
								</div>
								<!-- /.box-header -->
								<div class="box-body ">

									<div class="row form-group">
										<label for="viewno" class="col-sm-2 control-label">景點編號</label>
										<div class="col-sm-8">
											<b>${viewinfoVO.viewno}</b>
										</div>
										<div class="col-sm-1"></div>
									</div>
									<div class="row form-group">
										<label for="viewname" class="col-sm-2 control-label">景點名稱</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="viewname"
												name="viewname" value="${viewinfoVO.viewname}">
										</div>
										<div class="col-sm-1"></div>
									</div>
									<div class="row form-group">
										<label for="viewmanager" class="col-sm-2 control-label">景點管理單位</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="viewmanager"
												name="viewmanager" value="${viewinfoVO.viewmanager}">
										</div>
										<div class="col-sm-1"></div>
									</div>
									<div class="row form-group">
										<label for="viewaddress" class="col-sm-2 control-label">景點位址</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="viewaddress"
												name="viewaddress" value="${viewinfoVO.viewaddress}">
										</div>
										<div class="col-sm-1"></div>
									</div>
									<div class="row form-group">
										<label for="viewweb" class="col-sm-2 control-label">網址</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="viewweb"
												name="viewweb" value="${viewinfoVO.viewweb}">
										</div>
										<div class="col-sm-1"></div>
									</div>
									<div class="row form-group">
										<label for="viewphone" class="col-sm-2 control-label">電話</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="viewphone"
												name="viewphone" value="${viewinfoVO.viewphone}">
										</div>
										<div class="col-sm-1"></div>
									</div>

									<div class="row form-group">
										<label for="viewlon" class="col-sm-2 control-label">經度</label>
										<div class="col-sm-8">
											<input type="text" class="form-control" id="viewlon"
												name="viewlon" value="${viewinfoVO.viewlon}">
										</div>
										<div class="col-sm-1"></div>
									</div>

									<div class="row form-group">

										<label for="viewlat" class="col-sm-2 control-label">緯度</label>

										<div class="col-sm-8">
											<input type="text" class="form-control" id="viewlat"
												name="viewlat" value="${viewinfoVO.viewlat}">
										</div>
										<div class="col-sm-1"></div>
									</div>

									<div class="row form-group">

										<label for="viewopen" class="col-sm-2 control-label">開放時間</label>
										<div class=" col-sm-8">
											<input type="text" class="form-control" id="viewopen"
												name="viewopen" value="${viewinfoVO.viewopen}">
										</div>
										<div class="col-sm-1"></div>
									</div>

									<div class="row form-group">
										<label for="viewticket" class="col-sm-2 control-label">景點票價</label>
										<div class=" col-sm-8">
											<input type="text" class="form-control" id="viewticket"
												name="viewticket" value="${viewinfoVO.viewticket}">
										</div>
										<div class="col-sm-1"></div>
									</div>

									<div class="row form-group">
										<label for="viewequi" class="col-sm-2 control-label">景點設備</label>
										<div class=" col-sm-8">
											<input type="text" class="form-control" id="viewequi"
												name="viewequi" value="${viewinfoVO.viewequi}">
										</div>
										<div class="col-sm-1"></div>
									</div>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
						<div class='col-md-6'>
							<div class="box box-primary">
								<div class="box-header">
									<h3 class="box-title">景點簡介</h3>
								</div>
								<!-- /.box-header -->
								<div class="box-body ">
									<div class='form-group'>
										<div class="col-sm-1"></div>
										<div class="col-sm-10">
											<%-- 		                	<input type="text" class="form-control" id="viewcontent" name="viewcontent"	style="hei" value="${viewinfoVO.viewcontent}" > --%>
											<textarea class="form-control" rows="3" id="viewcontent"
												name="viewcontent" style="height: 531px;">${viewinfoVO.viewcontent}</textarea>
										</div>
										<div class="col-sm-1"></div>
									</div>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->

						</div>
					</form>
				</div>

				<jsp:useBean id="listViewPhoto_ByViewno" scope="request"
					type="java.util.Set" />
				<div class='row'>



					<div class='col-md-11' style="margin-left: 60px;">
						<div class="box box-primary">
							<div class="box-header">
								<h3 class="box-title">景點照片</h3> &nbsp;&nbsp;&nbsp;
								<!-- Button trigger modal -->
							<button type="button" class="btn btn-warning btn-md"
								data-toggle="modal" data-target="#myModal">新增景點照片</button>
									<!-- Modal -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											
											<h4 class="modal-title" id="myModalLabel">新增景點照片</h4>
										</div>
										<form
											action="<%=request.getContextPath()%>/viewphoto/multipleUpload.do"
											method="post" enctype="multipart/form-data">
											<div class="modal-body">
												<input type="file" class="btn btn-lg" style="background-color:white;" name="file[]"
													multiple="multiple" /> 
													<input type="hidden" name="viewno"
													value="<%=viewno%>" />
											</div>
											<div class="modal-footer">
												<input type="submit" style="float: left;" value="送出新增">
											</div>
										</form>
									</div>
								</div>
							</div><!-- Modal -->
								
							</div>



							

						

							

							<!-- /.box-header -->
							<div class="box-body ">

								<c:forEach var="viewphotoVO" items="${listViewPhoto_ByViewno}">
									<div class='col-md-4' style="margin-top: 20px;">
										<div class="view"
											style="border: 1px solid #d3d3d3; height: 450px;">
											<img style="height: 350px; width: 560px;"
												class="img-responsive"
												src="<%= request.getContextPath()%>/viewphoto/readviewphoto.do?viewpicno=${viewphotoVO.viewpicno}">
											</br>
											<form method="post"
												action="<%=request.getContextPath()%>/viewphoto/viewphoto.do">
												<input type='hidden' name="viewpicno"
													value="${viewphotoVO.viewpicno }"> 
												<input type="hidden" name="action" value="delete">
												<input type="hidden" name="viewno" value="${viewinfoVO.viewno}">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<button type="submit" class="btn btn-danger accBtn">
													<span class="glyphicon glyphicon-remove iconType ">刪除相片</span>
												</button>
											</form>
										</div>
									</div>

								</c:forEach>

							</div>
							<!-- /.box-body -->







						</div>





					</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->



		<!-- Main Footer -->
		<footer class="main-footer">
			<div class="pull-right hidden-xs">.</div>
			<strong>Copyright &copy; 2016 <a href="#">eChoice 民宿訂房</a>.
			</strong> All rights reserved.
		</footer>
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->
	<script
		src="<%=request.getContextPath()%>/backEnd/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/backEnd/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/backEnd/dist/js/app.min.js"></script>

</body>
</html>
