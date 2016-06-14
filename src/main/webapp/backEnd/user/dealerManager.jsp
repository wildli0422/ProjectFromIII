<%@page import="com.hostel.model.HostelService"%>
<%@page import="com.dealer.model.DealerVO"%>
<%@page import="com.accessemp.model.AccessEmpService"%>
<%@page import="com.dealer.model.DealerService" %>
<%@page import="com.emp.model.EmpVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	AccessEmpService accessEmpService = new AccessEmpService();
	DealerService dealerService = new DealerService();
	HostelService hostelService = new HostelService();
	request.setAttribute("hostelSvc", hostelService);
	List<DealerVO> state0list = dealerService.getState0();
	request.setAttribute("state0", state0list);

%>

<!DOCTYPE html>
<html>
<head>
<title>eChoice | BackEnd</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/plugins/iCheck/all.css">
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



		<div class="content-wrapper">
			<section class="content">
			
			
			
				<section class="content-header">
		          <h1>
		          	<b>
		       		 民宿業者	
		            <small>查詢/管理</small>
		            </b>
		          </h1>
	        	</section>
	        	<hr>
	        	<p>
	        	<hr>
	        	<div class='row'>
	        		<div class='col-md-2 col-sm-12 col-xs-12'></div>
		        	<div class='col-md-5 col-sm-12 col-xs-12'>
		        		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dealer/dealer.do"
									id="dealerS">
							<b>電郵地址:</b> 
							<input type="email" name="dealerMail" placeholder="輸入電郵"><br>
							<br>							
							<b>會員姓名:</b> 
							<input type="text" name="dealerName" placeholder="輸入姓名"><br>
							<br>							 
							<b>通訊地址:</b>
							<input type="text" name="dealerAddress" placeholder="通訊地址"><br> 
							<br>
							<b>聯絡方式:</b>
							<input type="text" name="dealerPhone" placeholder="聯絡方式"><br> 
							<br>
							<b>選擇性別:</b> 
							<input type="checkbox" name="dealerSex" value="男" class="flat-red"> Male
							<input type="checkbox" name="dealerSex" value="女" class="flat-red"> Female
							<br>
							<b>認證審核:</b> 
							<input type="checkbox" name="dealerState" value="1" class="flat-red"> 通過
							<input type="checkbox" name="dealerState" value="0" class="flat-red"> 未通過
							<br>
							<input type="hidden" name="action" value="getlistByCQ">			
							<p>				
							<p>
							<button type="submit" class="btn btn-primary">查詢</button>							
						</FORM>
						<p>
		        	</div>
		        	<div class='col-md-5 col-sm-12 col-xs-12'>
		        		<div class="col-md-12 col-sm-12 col-xs-12">
			              <div class="info-box">
			                <span class="info-box-icon bg-yellow"><i class="fa fa-flag-o"></i></span>
			                <div class="info-box-content">
			                  <span class="info-box-text">未通過審核業者</span>
			                  <span class="info-box-number"><%= dealerService.countState0() %></span>
			                </div><!-- /.info-box-content -->
			              </div><!-- /.info-box -->
			            </div><!-- /.col -->
			            <div class="col-md-12 col-sm-12 col-xs-12">
			            	<div class="box">
			                  <table class="table table-condensed">
			                    <tr>
			                      <th>DealerNo</th>
			                      <th>Name</th>
			                      <th>Email</th>
			                      <th></th>
			                    </tr>
			                    <c:forEach var="dealerVO" items="${state0}" >							
								<tr align='center' valign='middle'>
									<td>${dealerVO.dealerNo}</td>
									<td>${dealerVO.dealerName}</td>
									<td>${hostelSvc.findByDealer(dealerVO.dealerNo).hostelName}</td>
								</tr>
								</c:forEach>
			                    
			                  </table>
			             	</div>
			             </div>
		        		</div>
	        	</div>
	        	<p>
	        	<div class='row'>
	        		<div class="col-xs-12">
			            	<div class="box">
			                <div class="box-body">
			                  <table id="example2" class="table table-bordered table-hover table-condensed">
			        			<thead>
			        			<tr>
			        				<td>業者編號</td>
			        				<td>姓名</td>
			        				<td>電郵地址</td>
			        				<td>性別</td>
			        				<td>通訊地址</td>
			        				<td>聯絡方式</td>
			        				<td>審核狀況</td>
			        				<td></td>
			        			</tr>
			        			</thead>
			        			<tbody>			        		
			        		<c:forEach var="dealerVO" items="${dealerlist}" >
								<tr align='center' valign='middle'>
									<td>${dealerVO.dealerNo}</td>
									<td>${dealerVO.dealerName}</td>
									<td>${dealerVO.dealerMail}</td>
									<td>${dealerVO.dealerSex}</td>
									<td>${dealerVO.dealerAddress}</td>
									<td>${dealerVO.dealerPhone}</td>
									<td>
										<c:choose>
											<c:when test="${dealerVO.dealerState == '0'}">未審核</c:when>
											<c:otherwise>已審核</c:otherwise>
										</c:choose>
									</td>
									<td>
										<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dealer/dealer.do">
											    <button type="submit" class="btn btn-info btn-xs">顯示詳情 </button>
											    <input type="hidden" name="dealerNo" value="${dealerVO.dealerNo}">
											    <input type="hidden" name="requestURL"	value="/backEnd/user/dealerProfile.jsp">
											    <input type="hidden" name="action"	value="getOne_For_Display_bk">
										</FORM>
										
									  	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dealer/dealer.do">
										   		<button type="submit" class="btn btn-danger btn-xs">刪除 </button>
										    	<input type="hidden" name="dealerNo" value="${dealerVO.dealerNo}">
										   	 	<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
											    <input type="hidden" name="action" value="delete">
										</FORM>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div><!-- /.box-body -->
			       </div><!-- /.box -->
			       </div>
	        	</div>
			</section><!-- /.content -->
		</div>



		<footer class="main-footer"> <!-- To the right -->
		<div class="pull-right hidden-xs">.</div>
		<!-- Default to the left --> <strong>Copyright &copy; 2016 <a
			href="#">eChoice 民宿訂房</a>.
		</strong> All rights reserved. </footer>


		<div class="control-sidebar-bg"></div>
	</div>
	
    <script src="<%=request.getContextPath()%>/backEnd/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/select2/select2.full.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/fastclick/fastclick.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/dist/js/app.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/dist/js/demo.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/iCheck/icheck.min.js"></script>
	<script src="<%=request.getContextPath()%>/backEnd/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/backEnd/plugins/slimScroll/jquery.slimscroll.min.js"></script>
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
