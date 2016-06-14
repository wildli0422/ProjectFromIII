<%@page import="com.hostelOrderDetail.model.HostelOrderDetailService"%>
<%@page import="com.hostelOrder.model.HostelOrderVO"%>
<%@page import="com.hostelOrder.model.HostelOrderService"%>
<%@page import="com.tenant.model.TenantVO"%>
<%@page import="com.tenant.model.TenantService"%>
<%@page import="com.hostel.model.HostelService"%>
<%@page import="com.dealer.model.DealerVO"%>
<%@page import="com.accessemp.model.AccessEmpService"%>
<%@page import="com.dealer.model.DealerService" %>
<%@page import="com.emp.model.EmpVO"%>
<%@page import="com.hostel.model.HostelVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	DealerService dealerService = new DealerService();

	HostelService hostelService = new HostelService();
	pageContext.setAttribute("hostelService", hostelService);
	
	TenantService tenantService = new TenantService();
	pageContext.setAttribute("tenantService", tenantService);
	Integer tenantNo = new Integer(request.getParameter("tenantNo"));
	TenantVO tenantVO =	(TenantVO)tenantService.getOneTenant(tenantNo);
	pageContext.setAttribute("tenant", tenantVO);
	
	HostelOrderService hoService = new HostelOrderService();
	pageContext.setAttribute("hoService", hoService);
	List<HostelOrderVO> hostelordlist = hoService.getAllByTenantNo(tenantNo);
	pageContext.setAttribute("hostelord", hostelordlist);
	
	HostelOrderDetailService hodService = new HostelOrderDetailService();
	
	pageContext.setAttribute("hodService", hodService);
	String day = null;
	
	try {
		int a =tenantService.toDate(tenantNo)/ hostelordlist.size() ;
		day = Integer.toString(a);
		
	}catch (Exception e) {
		day ="尚未開始";
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/plugins/iCheck/all.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/backEnd/dist/css/skins/skin-blue.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

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
				
				<div class="row">
					<div class="col-md-6">
						<div class="box box-primary">
		                <div class="box-header">
		                  <h3 class="box-title">會員基本資料</h3>
		                  <h4 class="text-red">非必要請勿任意更改會員資料</h4>
		                </div><!-- /.box-header -->
		               <div class="box-body ">
          					<form  role="form" class="form-horizontal"   METHOD="post" ACTION="<%=request.getContextPath()%>/tenant.do" name="tenantProfile">
				            	<div class="form-group">     
				                  <label for="tenantNo" class="col-sm-3 control-label">會員編號</label>
		                       	  <div class="col-sm-8">
		                          <b>${tenant.tenantNo}</b>	                         
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
		                       	<div class="form-group">	 
	                       		  <label for="tenantName" class="col-sm-3 control-label">姓名</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="tenantName" name="tenantName"	value="${tenant.tenantName}">
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="tenantMail" class="col-sm-3 control-label">電郵地址</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="tenantMail" name="tenantMail"	value="${tenant.tenantMail}">
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="tenantPassword" class="col-sm-3 control-label">密碼</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="tenantPassword" name="tenantPassword"	value="${tenant.tenantPassword}" readonly>
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerSex" class="col-sm-3 control-label">性別</label>
		                       	  <div class="col-sm-8">
		                          	<c:choose>
		                          		<c:when test="${tenant.tenantSex == 'male'}">
			                      		<input type="radio" name="tenantSex" value="male" class="minimal-red"  checked>男
			                      		<input type="radio" name="tenantSex" value="female" class="minimal-red" >女
				                    	</c:when>
				                    	<c:otherwise>
				                      	<input type="radio" name="tenantSex" value="male" class="minimal-red" >男
			                      		<input type="radio" name="tenantSex" value="female" class="minimal-red" checked>女
				                      	</c:otherwise>
				                  	</c:choose>	
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="tenantAddress" class="col-sm-3 control-label">通訊地址</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="tenant" name="tenantAddress"	value="${tenant.tenantAddress}" >
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="tenantPhone" class="col-sm-3 control-label">聯絡方式</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="tenantPhone" name="tenantPhone"	value="${tenant.tenantPhone}" >
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>

                       		  	<div class="form-group">
			                        <div class="col-sm-offset-2 col-sm-10">
			                        <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			                        <input type="hidden" name="tenantNo" 	value="${tenant.tenantNo}">
			                        <input type="hidden" name="action" 	value="update">
                         			<button type="submit" class="btn btn-danger">修改資料</button>
                       			 </div>
                      			</div>
		                  </form>
		                </div><!-- /.box-body -->
		              </div><!-- /.box -->
					</div>
					<div class="col-md-6">
						
						<div class="col-md-12 col-sm-12 col-xs-0">
							<div style=" width:500px; height:400px;">
								<img src="<%=request.getContextPath()%>/ImageReader?tenantNo=${tenant.tenantNo}" class="img-rounded"   height="350px">
							</div>
						</div>
			            
			           
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">			
						<div class="box default">
			                <div class="box-header with-border">
			                  <h3 class="box-title"><b>過往的房東對此旅客評分</b> ： 平均<%=tenantService.getScoreAvg(tenantNo) %>分</h3>
			                  
			                </div>
			                <div class="box-body">
			                    <canvas id="pieChart" style="height:250px"></canvas>
			                </div><!-- /.box-body -->
			              </div><!-- /.box -->
		              </div>
		              <div class="col-md-3 col-sm-6 col-xs-12">
			              <div class="info-box bg-aqua">
			                <span class="info-box-icon"><i class="fa fa-suitcase"></i></span>
			                <div class="info-box-content">
			                  <span class="info-box-text">本月旅遊次數</span>
			                  <span class="info-box-number"><%=tenantService.travelThisMonth(tenantNo) %></span>
			                  <div class="progress">
			                    <div class="progress-bar" style="width: 100%"></div>
			                  </div>
			                  <span class="progress-description">
			                  		總共旅遊次數：<%=hostelordlist.size() %>次
			   				                
			                  </span>
			                </div><!-- /.info-box-content -->
			              </div><!-- /.info-box -->
			           </div><!-- /.col -->
			           <div class="col-md-3 col-sm-6 col-xs-12">
			              <div class="info-box bg-green">
			                <span class="info-box-icon"><i class="fa fa-plane"></i></span>
			                <div class="info-box-content">
			                  <span class="info-box-text">旅行週期</span>
			                  <span class="info-box-number"><%=day %>天旅行一次</span>
			                  <div class="progress">
			                    <div class="progress-bar" style="width: 100%"></div>
			                  </div>
			                  <span class="progress-description">
			                 	  註冊至今已<%=tenantService.toDate(tenantNo) %>天
			                  </span>
			                </div><!-- /.info-box-content -->
			              </div><!-- /.info-box -->
			            </div><!-- /.col -->
		              
		              
				</div>
				
				<div class='row'>
					<div class='col-md-12'>
						<div class="box">
			                <div class="box-header">
			                  <h3 class="box-title">過往訂房紀錄</h3>
			                  <div class="box-tools">
			                    <div class="input-group" style="width: 150px;">
			                      <div class="input-group-btn">
			                      </div>
			                    </div>
			                  </div>
			                </div><!-- /.box-header -->
			                <div class="box-body table-responsive no-padding">
			                  <table class="table table-hover" id='order'>
			                    
			                    <tr>
			                      <th>訂單編號</th>
			                      <th>訂單狀態</th>
			                      <th>民宿名稱</th>
			                      <th>訂單日期</th>
			                      <th>入住日期</th>
			                      <th>退房日期</th>
			                      <th>人數</th>
			                      <th>對房東評分</th>
			                      <th>對旅客評分</th>
			                    </tr>
			                <c:forEach var="hostelOrderVO" items="${hostelord}" >
								<tr align='center' valign='middle' >
									
									<td>${hostelOrderVO.hostelOrderNo}</td>
									<td>${hostelOrderVO.orderState}</td>
									<td>${hostelService.getOneHostel(hostelOrderVO.hostelNo).hostelName}</td>
									<td>${hostelOrderVO.hostelOrderDate}</td>
									<td>${hodService.getAllByHostelOrderNo(hostelOrderVO.hostelOrderNo).get(0).checkInDate }</td>
									<td>${hodService.getAllByHostelOrderNo(hostelOrderVO.hostelOrderNo).get(0).checkOutDate }</td>
									<td>${hostelOrderVO.customerQuantity}</td>
									<td>${hostelOrderVO.hostelScore}</td>
									<td>${hostelOrderVO.tenantScore}</td>
								</tr>
							</c:forEach>
								
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
    <script src="<%=request.getContextPath()%>/backEnd/plugins/chartjs/Chart.min.js"></script>
	
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
        //-------------
        //- PIE CHART -
        //-------------
        // Get context with jQuery - using jQuery's .get() method.
        var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
        var pieChart = new Chart(pieChartCanvas);
        var PieData = [
          {
            value: <%=tenantService.countScore(tenantNo,1)%>,
            color: "#f56954",
            highlight: "#f56954",
            label: "1分"
          },
          {
            value: <%=tenantService.countScore(tenantNo,2)%>,
            color: "#00a65a",
            highlight: "#00a65a",
            label: "2分"
          },
          {
            value: <%=tenantService.countScore(tenantNo,3)%>,
            color: "#f39c12",
            highlight: "#f39c12",
            label: "3分"
          },
          {
            value: <%=tenantService.countScore(tenantNo,4)%>,
            color: "#00c0ef",
            highlight: "#00c0ef",
            label: "4分"
          },
          {
            value: <%=tenantService.countScore(tenantNo,5)%>,
            color: "#3c8dbc",
            highlight: "#3c8dbc",
            label: "5分"
          },
          {
            value: <%=tenantService.countScore(tenantNo,6)%>,
            color: "#d2dcc",
            highlight: "#d2dcc",
            label: "6分"
          },
          {
              value: <%=tenantService.countScore(tenantNo,7)%>,
              color: "#f200f2",
              highlight: "#f200f2",
              label: "7分"
          },
          {
              value: <%=tenantService.countScore(tenantNo,8)%>,
              color: "#2b8836",
              highlight: "#2b8836",
              label: "8分"
          },
          {
              value: <%=tenantService.countScore(tenantNo,9)%>,
              color: "#800000",
              highlight: "#800000",
              label: "9分"
          },
          {
              value: <%=tenantService.countScore(tenantNo,10)%>,
              color: "#ff8040",
              highlight: "#ff8040",
              label: "10分"
          }
        ];
        var pieOptions = {
          //Boolean - Whether we should show a stroke on each segment
          segmentShowStroke: true,
          //String - The colour of each segment stroke
          segmentStrokeColor: "#fff",
          //Number - The width of each segment stroke
          segmentStrokeWidth: 2,
          //Number - The percentage of the chart that we cut out of the middle
          percentageInnerCutout: 50, // This is 0 for Pie charts
          //Number - Amount of animation steps
          animationSteps: 100,
          //String - Animation easing effect
          animationEasing: "easeOutBounce",
          //Boolean - Whether we animate the rotation of the Doughnut
          animateRotate: true,
          //Boolean - Whether we animate scaling the Doughnut from the centre
          animateScale: false,
          //Boolean - whether to make the chart responsive to window resizing
          responsive: true,
          // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
          maintainAspectRatio: true,
        };
        //Create pie or douhnut chart
        // You can switch between pie and douhnut using the method below.
        pieChart.Doughnut(PieData, pieOptions);

      });
      
    </script>

	


</body>
</html>
