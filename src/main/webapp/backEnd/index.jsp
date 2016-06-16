<%@page import="com.tenant.model.TenantVO"%>
<%@page import="com.tenant.model.TenantService"%>
<%@page import="com.hostel.model.HostelService"%>
<%@page import="com.hostelOrder.model.HostelOrderService"%>
<%@page import="com.dealer.model.DealerService"%>
<%@page import="com.accessemp.model.AccessEmpService"%>
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
HostelService hService = new HostelService();
TenantService tenantService = new TenantService();
HostelOrderService hoService = new HostelOrderService();
List<Integer> accessList = new LinkedList<Integer>();
accessList = accessEmpService.getEmpAccess(empVO.getEmpNo());

pageContext.setAttribute("hService", hService);
pageContext.setAttribute("hoService", hoService);
pageContext.setAttribute("tenantService",tenantService);
List<TenantVO> monthNew = tenantService.addThisMonth();
pageContext.setAttribute("monthNew", monthNew);


if (accessList.contains(9001)) {
	session.setAttribute("ac9001", 9001);
}else {
	session.removeAttribute("ac9001");
}
if (accessList.contains(9002)) {
	session.setAttribute("ac9002", 9002);
}else {
	session.removeAttribute("ac9002");
}
if (accessList.contains(9003)) {
	session.setAttribute("ac9003", 9003);
}else {
	session.removeAttribute("ac9003");
}
if (accessList.contains(9004)) {
	session.setAttribute("ac9004", 9004);
}else {
	session.removeAttribute("ac9004");
}
if (accessList.contains(9005)) {
	session.setAttribute("ac9005", 9005);
}else {
	session.removeAttribute("ac9005");
}

%>

<!DOCTYPE html>
<html>
<head>
    <title>eChoice | BackEnd</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/skins/skin-blue.min.css">
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
        <section class="content">
        	<div class='row'>
        		<div class="col-md-6">
        			<div class="col-md-2"></div>
        			<br>
        			<br>
	        		<img src="<%=request.getContextPath()%>/images/Logo_BO_01.png">
	        		<br>
	        		
	        		
	        		<br><b>歡迎${loginEmp.empName}</b>
	        		<br>
	        		<br>
	        		<br>
	        		<br>
	        		<br>
	        		<br>.
						<div class="box">
						<div class="box-header">
			                  <h3 class="box-title">最近一個月的新旅客</h3>
			                  <div class="box-tools">
			                    <div class="input-group" style="width: 150px;">
			                      <div class="input-group-btn">
			                      </div>
			                    </div>
			                  </div>
			                </div><!-- /.box-header -->
			                  <table class="table table-condensed">
			                    <tr>
			                      <th>TenantNo</th>
			                      <th>Name</th>
			                      <th>Email</th>
			                      <th></th>
			                    </tr>
			                    <c:forEach var="tenantVO" items="${ monthNew}" >							
								<tr align='center' valign='middle'>
									<td>${tenantVO.tenantNo}</td>
									<td>${tenantVO.tenantName}</td>
									<td>${tenantVO.tenantMail}</td>
								</tr>
								</c:forEach>
			                    
			                  </table>
			             	</div>
			            	        		
					
	        		
        		
        		</div>
        		<div class="col-md-6">
		                
        			
        			
        			
        			
        			<div class="box">
			                <div class="box-header">
			                  <h3 class="box-title">最近10筆完成的訂單</h3>
			                  <div class="box-tools">
			                    <div class="input-group" style="width: 150px;">
			                      <div class="input-group-btn">
			                      </div>
			                    </div>
			                  </div>
			                </div><!-- /.box-header -->
			                <div class="box-body table-responsive no-padding">
			                  <table class="table table-hover" >
			                   		<tr>
		               				<td>訂單編號</td>
		               				<td>民宿名稱</td>
		               				<td>旅客名稱</td>
		               				<td>訂單日期</td>
		               			</tr>
          					<c:forEach var="hostelOrderVO" items="${hoService.getAll()}"  begin="0" end="9"  >
										<tr>
											<td>${hostelOrderVO.hostelOrderNo }</td>
											<td>${hService.getOneHostel(hostelOrderVO.hostelNo).hostelName}</td>
											<td>${tenantService.getOneTenant(hostelOrderVO.tenantNo).tenantName }</td>
											<td>${hostelOrderVO.hostelOrderDate}</td>
										</tr>
							</c:forEach>
								
			                  </table>
			                </div><!-- /.box-body -->
			              </div><!-- /.box -->
					
        		
        		
        		
        	
        	</div>
			</div>
			
			
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->



      <!-- Main Footer -->
      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          .
        </div>
        <strong>Copyright &copy; 2016 <a href="#">eChoice 民宿訂房</a>.</strong> All rights reserved.
      </footer>
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->
     <script src="<%=request.getContextPath()%>/backEnd/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/select2/select2.full.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/fastclick/fastclick.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/dist/js/app.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/dist/js/demo.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/iCheck/icheck.min.js"></script>
	<script src="<%=request.getContextPath()%>/backEnd/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/slimScroll/jquery.slimscroll.min.js"></script>
  </body>
</html>
