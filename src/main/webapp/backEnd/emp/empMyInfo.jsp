<%@page import="com.accessemp.model.AccessEmpService"%>
<%@page import="com.dealer.model.DealerService"%>
<%@page import="com.emp.model.EmpVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
EmpVO empVO = new EmpVO();
empVO = (EmpVO)session.getAttribute("loginEmp");
AccessEmpService accessEmpService = new AccessEmpService();
DealerService dealerService = new DealerService();

List<Integer> accessList = new LinkedList<Integer>();


%>


<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
    <title>eChoice | BackEnd</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/AdminLTE.min.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/skins/skin-blue.min.css">
	<!-- ****jquery**** -->
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>




<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

	
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">

      <!-- Main Header -->
      <header class="main-header">

        <!-- Logo -->
        <a href="<%=request.getContextPath()%>/backEnd/index.jsp" class="logo">
          <span class="logo-mini"><b>e</b>C</span>
          <span class="logo-lg"><b>BackEnd</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
             
              <li class="dropdown notifications-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-bell-o"></i>
                  <span class="label label-warning"><%= dealerService.countState0() %></span>
                </a>
                <ul class="dropdown-menu">
                  <li class="header"></li>
                  <li>
                    <ul class="menu">
                      <li><!-- start notification -->
                        <a href="#">
                          <i class="fa fa-users text-aqua"></i>有<%= dealerService.countState0() %>位業者尚未通過認證
                        </a>
                      </li><!-- end notification -->
                    </ul>
                  </li>
                  <li class="footer"><a href="#"></a></li>
                </ul>
              </li>
              <!-- Tasks Menu -->

              <!-- User Account Menu -->
				<li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <img src="<%=request.getContextPath()%>/ImageReader?empNo=${loginEmp.empNo}" class="user-image" alt="User Image">
                  <span class="hidden-xs">${loginEmp.empName}</span>
                </a>
                <ul class="dropdown-menu">
                                  
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="<%=request.getContextPath()%>/backEnd/emp/empMyInfo.jsp" class="btn btn-default btn-flat">Profile</a>
                    </div>
                    <div class="pull-right">
                      <a href="<%=request.getContextPath()%>/backEnd/logout.jsp" class="btn btn-default btn-flat">Sign out</a>
                    </div>
                  </li>
                </ul>
              </li>
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
            
            
          </ul><!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
      </aside>



      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">

			<div class="col-md-9">
				<img class="profile-user-img img-responsive img-circle" src="<%=request.getContextPath()%>/ImageReader?empNo=${loginEmp.empNo}" alt="User profile picture">
			
			</div>
			
			
			<div class="col-md-9">
                    <form class="form-horizontal" METHOD="post" ACTION="<%=request.getContextPath()%>/backEnd/emp/emp.do" name="empMyInfo">
                       <div class="form-group">
                        <label for="empPassword" class="col-sm-2 control-label">員工編號</label>
                        <div class="col-sm-4">
                        ${loginEmp.empNo}
                        </div>
                      
                        <label for="empMail" class="col-sm-2 control-label">帳號</label>
                        <div class="col-sm-4">
                        ${loginEmp.empAccount}
                        </div>
                      </div> 
                      <div class="form-group">
                        <label for="empPassword" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-4">
                        ${loginEmp.empName}
                        </div>
                      
                        <label for="empMail" class="col-sm-2 control-label">性別</label>
                        <div class="col-sm-4">
                        ${loginEmp.empSex}
                        </div>
                      </div>  
                      
                      
                      <div class="form-group">
                        <label for="empPassword" class="col-sm-2 control-label">密碼</label>
                        <div class="col-sm-10">
                          <input type="password" class="form-control" id="empPassword" name="empPassword"	value="${loginEmp.empPassword}">
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="empMail" class="col-sm-2 control-label">電郵地址</label>
                        <div class="col-sm-10">
                          <input type="email" class="form-control" id="empMail" name="empMail" size="45" value="${loginEmp.empMail}">
                        </div>
                      </div>
                      
                      
                      
                      
                      <div class="form-group">
                        <label for="empAddress" class="col-sm-2 control-label">通訊地址</label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control" id="empAddress" name="empAddress" value="${loginEmp.empAddress}" readonly>
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label for="empPhone" class="col-sm-2 control-label">聯絡電話</label>
                         <div class="col-sm-10">
                          <input type="text" class="form-control"  name="empPhone"	value="${loginEmp.empPhone}" 
                         	 onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="if 
　　							 ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false"> 
                        </div>
                      </div>
                                            
                      <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                        <input type="hidden"  name="empSex" 	value="${loginEmp.empSex}">
                        <input type="hidden"  name="empAccount" 	value="${loginEmp.empAccount}">
                        <input type="hidden" name="empNo" 	value="${loginEmp.empNo}">
                        <input type="hidden" name="empName" 	value="${loginEmp.empName}">
                        <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
                        <input type="hidden" name="action" 	value="update">
                          <button type="submit" class="btn btn-danger" id="">Submit</button>
                        </div>
                      </div>
                    </form>
            </div><!-- /.col -->   	
        </section>
        <section class="content">

          <!-- Your Page Content Here -->
			
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->



      <!-- Main Footer -->
      <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
          .
        </div>
        <!-- Default to the left -->
        <strong>Copyright &copy; 2016 <a href="#">eChoice 民宿訂房</a>.</strong> All rights reserved.
      </footer>


      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->

    <!-- REQUIRED JS SCRIPTS -->

    <!-- jQuery 2.1.4 -->
    <script src="<%=request.getContextPath()%>/backEnd/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <script src="<%=request.getContextPath()%>/backEnd/bootstrap/js/bootstrap.min.js"></script>
    <!-- AdminLTE App -->
    <script src="<%=request.getContextPath()%>/backEnd/dist/js/app.min.js"></script>


  </body>
</html>
