<%@page import="com.emp.model.EmpService"%>
<%@page import="com.accessemp.model.AccessEmpService"%>
<%@page import="com.dealer.model.DealerService" %>
<%@page import="com.emp.model.EmpVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	EmpService empService = new EmpService();// get VO
	DealerService dealerService = new DealerService();
	AccessEmpService accessEmpService = new AccessEmpService(); //ger access
	List<Integer> accessList = new LinkedList<Integer>();
%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
    <title>eChoice | BackEnd</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/plugins/iCheck/all.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/skins/_all-skins.min.css">
	<!-- ****Sweet Alert**** -->
<script src="<%=request.getContextPath()%>/js/sweetalert-dev.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/sweetalert.css">
	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<style>
.file {
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}

</style>
<script>
$(document).ready(function() {

	$("#go").click(function() {
		 swal("新增中");
		 setTimeout(function() {submit()} , 2000);
		
	});
	
});
</script>

	
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
        		
	
		
        <section class="content">

          <!-- Your Page Content Here -->
          	<form class="form-horizontal" METHOD="post" ACTION="<%=request.getContextPath()%>/backEnd/emp/emp.add"  enctype="multipart/form-data">
                      
                      <div class="form-group">
                        <label for="" class="col-sm-2 control-label">員工帳號</label>
                        <c:if test="${not empty nameUniqueErr}">
							<font color="red">
								${ nameUniqueErr}
							</font>
						</c:if>
                        <div class="col-sm-6">
                          <input type="text" class="form-control"  name="empAccount" required>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="" class="col-sm-2 control-label">密碼</label>
                        <div class="col-sm-6">
                          <input type="text" class="form-control" value="系統自動生成" readonly>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-6">
                          <input type="text" class="form-control" name="empName" required>
                        </div>
                      </div>
                                            
                      <div class="form-group">
                      <label for="" class="col-sm-2 control-label">性別</label>
	                  	<div class="col-sm-6"   >	
	                      	<label>
		                      <input type="radio" name="empSex" value="male" class="minimal-red" checked>男
		                    </label>
		                    <label>
		                      <input type="radio" name="empSex" value="female" class="minimal-red" >女
		                    </label>
		                </div> 
	                  </div>
	                      <div class="form-group">
        		            <label for="" class="col-sm-2 control-label">通訊地址</label>
                        <div class="col-sm-6">
                          <input type="text" class="form-control" name="empAddress" required>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="" class="col-sm-2 control-label">聯絡電話</label>
                        <div class="col-sm-6">
                          <input type="text" class="form-control"  name="empPhone"	
                         	 onkeydown="if(event.keyCode==13)event.keyCode=9" onkeypress="if 
　　							 ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false" required> 
                        </div>
                        <c:if test="${not empty phoneNumErr}">
							<font color="red">
								${ phoneNumErr}
							</font>
						</c:if>
                      </div>
                      <div class="form-group">
                        <label for="" class="col-sm-2 control-label">電郵地址</label>
                        <div class="col-sm-6">
                          <input type="email" class="form-control" name="empMail" required>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="" class="col-sm-2 control-label">照片上傳</label>
                        <div class="col-sm-6">
                        <a href="javascript:;" class="file">選擇圖片
                          <input type="file" name="empPic" size="45">
                        </a>  
                        </div>
                      </div>
                      
                      

                      
                     <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                       	<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">	
						<input type="hidden" value="update" name="action">
						<button type="submit"  id="go" class="btn btn-primary">新增資料</button>
                        </div>
                      </div>
				</form>

			 </section >
			 
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

    <script src="<%=request.getContextPath()%>/backEnd/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/select2/select2.full.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/fastclick/fastclick.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/dist/js/app.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/dist/js/demo.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/iCheck/icheck.min.js"></script>
    <script src=".<%=request.getContextPath()%>/backEnd/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <script>
      $(function () {
 

        //iCheck for checkbox and radio inputs
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
          checkboxClass: 'icheckbox_minimal-blue',
          radioClass: 'iradio_minimal-blue'
        });
        //Red color scheme for iCheck
        $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
          checkboxClass: 'icheckbox_minimal-red',
          radioClass: 'iradio_minimal-red'
        });
        //Flat red color scheme for iCheck
        $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
          checkboxClass: 'icheckbox_flat-green',
          radioClass: 'iradio_flat-green'
        });


      });
      
    </script>

  </body>
</html>
