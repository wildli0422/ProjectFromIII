<%@page import="com.hostelOrderDetail.model.HostelOrderDetailService"%>
<%@page import="com.hostelOrder.model.*"%>
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
	Integer dealerNo = new Integer(request.getParameter("dealerNo"));
	DealerVO dealerVO = new DealerVO();
	dealerVO = dealerService.getOneDealer(dealerNo);
	HostelService hostelService = new HostelService();
	HostelVO hostelVO = new HostelVO();
	hostelVO = hostelService.findByDealer(dealerNo);
	
	request.setAttribute("dealer", dealerVO);
	request.setAttribute("hostel", hostelVO);
	
	HostelOrderService hoService = new HostelOrderService();
	List<HostelOrderVO> hostelordlist = hoService.getAllByHostelNo(hostelVO.getHostelNo());
	pageContext.setAttribute("hostelord", hostelordlist);
	
	HostelOrderDetailService hodService = new HostelOrderDetailService();
	pageContext.setAttribute("hodService", hodService);

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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<script>
$(document).ready(function(){
    $("#order").click(function(){
        $("#detail").toggle();
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



		<div class="content-wrapper">
			<section class="content">
				
				<div class="row">
					<div class="col-md-6">
						<div class="box box-primary">
		                <div class="box-header">
		                  <h3 class="box-title">業者基本資料</h3>
		                  <h4 class="text-red">未必要請勿任意更改業者資料</h4>
		                </div><!-- /.box-header -->
		               <div class="box-body ">
          					<form  role="form" class="form-horizontal"   METHOD="post" ACTION="<%=request.getContextPath()%>/dealer/dealer.do" name="dealerProfile">
				            	<div class="form-group">     
				                  <label for="dealerNo" class="col-sm-3 control-label">業者編號</label>
		                       	  <div class="col-sm-8">
		                          <b>${dealerVO.dealerNo}</b>	                         
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
		                       	<div class="form-group">	 
	                       		  <label for="dealerName" class="col-sm-3 control-label">業者姓名</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="dealerName" name="dealerName"	value="${dealer.dealerName}">
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerMail" class="col-sm-3 control-label">電郵地址</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="dealerName" name="dealerMail"	value="${dealer.dealerMail}">
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerPassword" class="col-sm-3 control-label">密碼</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="dealerPassword" name="dealerPassword"	value="${dealer.dealerPassword}" readonly>
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerSex" class="col-sm-3 control-label">性別</label>
		                       	  <div class="col-sm-8">
		                          	<c:choose>
		                          		<c:when test="${dealer.dealerSex == '男'}">
			                      		<input type="radio" name="dealerSex" value="男" class="minimal-red"  checked>男
			                      		<input type="radio" name="dealerSex" value="女" class="minimal-red" >女
				                    	</c:when>
				                    	<c:otherwise>
				                      	<input type="radio" name="dealerSex" value="男" class="minimal-red" >男
			                      		<input type="radio" name="dealerSex" value="女" class="minimal-red" checked>女
				                      	</c:otherwise>
				                  	</c:choose>	
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerAddress" class="col-sm-3 control-label">通訊地址</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="dealerAddress" name="dealerAddress"	value="${dealer.dealerAddress}" >
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerPhone" class="col-sm-3 control-label">聯絡方式</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="dealerPhone" name="dealerPhone"	value="${dealer.dealerPhone}" >
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerAccount" class="col-sm-3 control-label">收款帳戶</label>
		                       	  <div class="col-sm-8">
		                          <input type="text" class="form-control" id="dealerAccount" name="dealerAccount"	value="${dealer.dealerAccount}" >
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
	                       		<div class="form-group">
	                       		  <label for="dealerState" class="col-sm-3 control-label">審核狀態</label>
	                       		  <div class="col-sm-8">
	                       		  <c:choose>
	                       		  	<c:when test = "${dealer.dealerState == 0 }">
	                       		  		基本資料審核完畢了嗎？<input type="checkbox" class="minimal-red" name="dealerState" value="1">
	                       		  	</c:when>
	                       		  	<c:otherwise>
	                       		  		<b>已完成基本資料審核</b>
	                       		  		<input type="hidden" name="dealerState" value="1">
	                       		  	</c:otherwise>
	                       		  </c:choose>
	                       		  </div>
	                       		  <div class="col-sm-1"></div>
	                       		</div>
                       		  	<div class="form-group">
			                        <div class="col-sm-offset-2 col-sm-10">
			                        <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			                        <input type="hidden" name="dealerNo" 	value="${dealer.dealerNo}">
			                        <input type="hidden" name="action" 	value="update2">
                         			<button type="submit" class="btn btn-danger">修改資料</button>
                       			 </div>
                      			</div>
		                  </form>
		                </div><!-- /.box-body -->
		              </div><!-- /.box -->
					
					</div>
					<div class="col-md-6">
						<div class="col-md-12 col-sm-12 col-xs-0">
							<br>
							<br>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12">
			              <c:choose>
			              	<c:when test="${dealer.dealerState =='1'}">
			              		<div class="info-box">
					                <span class="info-box-icon bg-green"><i class="fa fa-thumbs-o-up"></i></span>
					                <div class="info-box-content">
					                  <span class="info-box-text"><b>此業者帳號已通過審核</b></span>
					                  <span class="info-box-number">${dealer.dealerName }</span>
					                </div><!-- /.info-box-content -->
					            </div><!-- /.info-box -->
			              	</c:when>
			              	<c:otherwise>
			              		<div class="info-box">
					                <span class="info-box-icon bg-yellow"><i class="fa fa fa-exclamation"></i></span>
					                <div class="info-box-content">
					                  <span class="info-box-text"><b>此業者尚未通過審核</b></span>
					                  <span class="info-box-number">${dealer.dealerName }</span>
					                </div><!-- /.info-box-content -->
					            </div><!-- /.info-box -->
			              	</c:otherwise>			              		              
			              </c:choose>
			            </div><!-- /.col -->
			            <div class="col-md-12 col-sm-12 col-xs-12">
			              <c:choose>
			              	<c:when test="${hostel.hostelVerification =='1'}">
			              		<div class="info-box">
					                <span class="info-box-icon bg-green"><i class="fa fa-home"></i></span>
					                <div class="info-box-content">
					                  <span class="info-box-text"><b>此民宿已通過審核</b></span>
					                  <span class="info-box-number">${hostel.hostelName}</span>
					                </div><!-- /.info-box-content -->
					            </div><!-- /.info-box -->
			              	</c:when>
			              	<c:otherwise>
			              		<div class="info-box">
					                <span class="info-box-icon bg-yellow"><i class="fa fa fa-exclamation"></i></span>
					                <div class="info-box-content">
					                  <span class="info-box-text"><b>此民宿尚未通過審核</b></span>
					                  <span class="info-box-number">${hostel.hostelName}</span>
					                </div><!-- /.info-box-content -->
					            </div><!-- /.info-box -->
			              	</c:otherwise>			              		              
			              </c:choose>
			            </div><!-- /.col -->
			             <div class="col-md-12 col-sm-12 col-xs-12">
			              <c:choose>
			              	<c:when test="${hostel.hostelState =='1'}">
			              		<div class="info-box">
					                <span class="info-box-icon bg-green"><i class="fa fa-shopping-cart"></i></span>
					                <div class="info-box-content">
					                  <span class="info-box-text"><b>此民宿上架中</b></span>
					                  <span class="info-box-number">${hostel.hostelName}</span>
					                </div><!-- /.info-box-content -->
					            </div><!-- /.info-box -->
			              	</c:when>
			              	<c:otherwise>
			              		<div class="info-box">
					                <span class="info-box-icon bg-yellow"><i class="fa fa fa-exclamation"></i></span>
					                <div class="info-box-content">
					                  <span class="info-box-text"><b>此民宿尚未上架</b></span>
					                  <span class="info-box-number">${hostel.hostelName}</span>
					                </div><!-- /.info-box-content -->
					            </div><!-- /.info-box -->
			              	</c:otherwise>			              		              
			              </c:choose>
			            </div><!-- /.col -->
					</div>
				</div>
								
				<div class='row'>
					<div class="col-md-6">
						<div class="box box-success">
			                <div class="box-header with-border">
			                  <h3 class="box-title">民宿資訊</h3>
			                </div>
			                <div class="box-body">
			                  <form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/hostel.do" name="hostelProfile">
				                  <div class="box-body">
				                    <div class="form-group">
				                      <label for="hostelNo">民宿編號</label>
				                      <b> ${hostel.hostelNo }</b>
				                      <input type="hidden" name="hostelNo" value="${hostel.hostelNo}">
				                    </div>
				                    <div class="form-group">
				                      <label for="hostelName">民宿名稱</label>
				                      <input type="text" class="form-control" id="hostelName" name="hostelName" value="${hostel.hostelName }">
				                    </div>
				                    <div class="form-group">
				                      <label for="hostelPhone">民宿電話</label>
				                      <input type="text" class="form-control" id="hostelPhone" name="hostelPhone" value="${hostel.hostelPhone }">
				                    </div>				                    
				                    <div class="form-group">
				                      <label for="hostelAddress">民宿地址</label>
				                      <input type="text" class="form-control" id="hostelAddress" name="hostelAddress" value="${hostel.hostelAddress }">
				                    </div>
				                    <div class="form-group">
				                      <label for="hostelLat">座標</label><br>
				                    	緯<input type="text" class="form-control" id="hostelLat" name="hostelLat" value="${hostel.hostelLat }">
				                       	經<input type="text" class="form-control" id="hostelLon" name="hostelLon" value="${hostel.hostelLon }">
				                    </div>
				                     <div class="form-group">
				                      <label for="hostelWebPages">民宿專頁</label>
				                      <input type="text" class="form-control" id="hostelWebPages" name="hostelWebPages" value="${hostel.hostelWebPages }">
				                    </div>

				                    <div class="form-group">
				                      <label for="hostelVerification">民宿審核</label>
				                    	<c:choose>
		                       		  	<c:when test = "${hostel.hostelVerification == 0 }">
		                       		  		民宿資料審核完畢了嗎？<input type="checkbox" class="minimal-red" name="hostelVerification" value="1">
		                       		  	</c:when>
		                       		  	<c:otherwise>
		                       		  		<b>已完成民宿資料審核</b>
		                       		  		<input type="hidden" name="hostelVerification" value="1">
		                       		  	</c:otherwise>
	                       			</c:choose>
				                    </div>
				                    <c:if test='${hostel.hostelVerification == 1 }'>
				                    	<div class="form-group">
				                      <label for="hostelState">上下架狀態</label>
				                    	<c:choose>
		                          		<c:when test="${hostel.hostelState == '1'}">
			                      		<input type="radio" name="hostelState" value="1" class="minimal-red"  checked>上架
			                      		<input type="radio" name="hostelState" value="0" class="minimal-red" >下架
				                    	</c:when>
				                    	<c:otherwise>
				                      	<input type="radio" name="hostelState" value="1" class="minimal-red" >上架
			                      		<input type="radio" name="hostelState" value="0" class="minimal-red" checked>下架
				                      	</c:otherwise>
				                  		</c:choose>	
				                    </div>
				                    
				                    </c:if>
				                   
				                  </div><!-- /.box-body -->
				                  <div class="box-footer">
				                  	<input type="hidden" name="dealerNo" value="${hostel.dealerNo}">
				                  	<input type="hidden" name="hostelCautions" value="${hostel.hostelCautions}">
				                  	<input type="hidden" name="hostelContent" value="${hostel.hostelContent}">
				                  	<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
				                  	<input type="hidden" name="action" value="updateBK">
				                    <button type="submit" class="btn btn-primary">修改資訊</button>
				                  </div>
				                </form>
			                </div><!-- /.box-body -->
			                
			              </div><!-- /.box -->
			
					</div>
					<div class="col-md-6">
						<div class="box box-info">
			                <div class="box-header with-border">
			                  <h3 class="box-title">民宿資訊</h3>
			                </div>
			                <div class="box-body">
								<form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/hostel.do" name="hostelText">
									<div class="form-group">
				                      <label>Cautions</label>
				                      <textarea class="form-control" rows="3"  name="hostelCautions">${ hostel.hostelCautions}</textarea>
				                    </div>
				                    <div class="form-group">
				                      <label>Content</label>
				                      <textarea class="form-control" rows="3"  name="hostelContent">${ hostel.hostelContent}</textarea>
				                    </div>
				                    <div class="box-footer">
				                    		<input type="hidden" name="hostelNo" value="${hostel.hostelNo}">
						                  	<input type="hidden" name="dealerNo" value="${hostel.dealerNo}">
						                  	<input type="hidden" name="hostelName" value="${hostel.hostelName}">
						                  	<input type="hidden" name="hostelPhone" value="${hostel.hostelPhone}">
						                  	<input type="hidden" name="hostelAddress" value="${hostel.hostelAddress}">
						                  	<input type="hidden" name="hostelWebPages" value="${hostel.hostelWebPages}">
						                  	<input type="hidden" name="hostelState" value="${hostel.hostelState}">
						                  	<input type="hidden" name="hostelVerification" value="${hostel.hostelVerification}">
						                  	<input type="hidden" name="hostelLat" value="${hostel.hostelLat}">
						                  	<input type="hidden" name="hostelLon" value="${hostel.hostelLon}">
						                  	<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
						                  	<input type="hidden" name="action" value="updateBK">
						                    <button type="submit" class="btn btn-primary">修改資訊</button>
						            </div>
								</form>
								</div><!-- /.box-body -->			                
			              </div><!-- /.box -->
					</div>
				</div><!-- /.row -->
	        	<div class='row'>
	        		<div class='col-md-12'>
	        			<div class='row'>
							<div class='col-md-12'>
								<div class="box">
					                <div class="box-header">
					                  <h3 class="box-title">過往訂單</h3>
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
										<tr align='center' valign='middle'>
											<td>${hostelOrderVO.hostelOrderNo}</td>
											<td>${hostelOrderVO.orderState}</td>
											<td>${hostelService.getOneHostel(hostelOrderVO.hostelNo).hostelName}</td>
											<td>${hostelOrderVO.hostelOrderDate}</td>
											<td>${hodService.getAllByHostelOrderNo(hostelOrderVO.hostelOrderNo).get(0).checkInDate }</td>
											<td>${hodService.getAllByHostelOrderNo(hostelOrderVO.hostelOrderNo).get(0).checkOutDate }</td>
											<td>${hostelOrderVO.customerQuantity}</td>
											<td>${hostelOrderVO.hostelScore}</td>
											<td>${hostelOrderVO.tenantScore}</td></b>
										</tr>
									</c:forEach>
										
					                  </table>
					                </div><!-- /.box-body -->
					              </div><!-- /.box -->
							
							</div>
						
						
						</div>
	        	
	        		</div>
	        	
	        	
	        	</div>
	        	
	        	
	        	
	        	<div class='row'>
	        		<div class='col-md-12'>
	        			<img  class="img-responsive" src="<%=request.getContextPath()%>/ImageReader?hostelNo=${hostel.hostelNo}&hostelPicture=${hostel.hostelPicture}" >
	        		</div>
	        		<div class='col-md-12'>
						<img class="img-responsive" src="<%=request.getContextPath()%>/ImageReader?hostelNo=${hostel.hostelNo}&dealerVerify=${hostel.dealerVerify}">
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
