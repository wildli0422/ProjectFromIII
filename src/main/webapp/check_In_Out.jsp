<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostelOrder.model.*" %>
<%@ page import="com.tenant.model.*" %>
<%@ page import="com.hostel.model.*" %>
<%@ page import="com.roomType.model.*" %>
<%@ page import="com.room.model.*" %>
<%@ page import="java.util.*" %>
<%
	List<HostelOrderVO> hostelOrderList=(List<HostelOrderVO>)request.getAttribute("hostelOrderList");

	TenantService tenantService=new TenantService();
	pageContext.setAttribute("tenantService", tenantService);
	
	HostelService hostelService=new HostelService();
	pageContext.setAttribute("hostelService", hostelService);
	
	HostelOrderService hostelOrderService=new HostelOrderService();
	pageContext.setAttribute("hostelOrderService", hostelOrderService);
	
	RoomTypeService roomTypeService=new RoomTypeService();
	pageContext.setAttribute("roomTypeService", roomTypeService);
	
	
	RoomService roomService=new RoomService();
	pageContext.setAttribute("roomService", roomService);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />
	<link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<script src="js/jquery_UI_methods.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	
	<script>
	
	//hide order detail
	$(document).ready(function() {
		$('[class^="flips"]').hide();
	});
	//click toggle detail
	var formId='';
	function expand(n){
		formId='.flips'+n;
		$(formId).toggle('fast');
	}
	
	var roomId='';
	function selectRoom(roomNo,index,detailNo){
		roomId='#room'+index;
		$(roomId).attr("value",roomNo);
		$(detailId).attr("value",detailNo);
	}
	
	
	</script>
	
<title>Check in & out</title>
</head>
<body>
<!------------------------------ nav bar ---------------------------------->
	<div class="col-md-12" id="topBar">
	<div class="row" class="navbar navbar-default" role="navigation">
		<div class="col-md-3" style="margin-top:20px;">
			<a href="<%=request.getContextPath()%>/HomePage.jsp"><img src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
		</div>
	</div>
</div>
<!------------------------------ nav bar ---------------------------------->

	<div class="col-md-12" id="container" style="margin-top:30px;">
		<div class="row">
		
			<div class="col-md-1"></div>
			
			<div class="col-md-10" >
				<table class="table table-hover table-expandable" >
					<thead>
						<tr>
							<th width="7%">訂房日期</th>
							<th width="5%">房客</th>
							<th width="5%">性別</th>
							<th width="5%">房客電話</th>
							<th width="3%">房客Mail</th>
							<th>房客人數</th>
							<th>房客評價</th>
							<th>訂房民宿</th>
							<th>民宿評分</th>
							<th>民宿評價</th>
							<th>付款方式</th>
							<th>付款狀態</th>
							<th>訂單狀態</th>
							<th></th>
							<th>處理訂單</th>
							
						</tr>
					</thead>
					<c:forEach var="hostelOrderVO" items="<%=hostelOrderList%>">
						
							<tr style="text-align:center;" class="flip${hostelOrderVO.hostelOrderNo}">
								<td>${hostelOrderVO.hostelOrderDate}</td>
								<td>${tenantService.getOneTenant(hostelOrderVO.tenantNo).tenantName}</td>
								<td>${tenantService.getOneTenant(hostelOrderVO.tenantNo).tenantSex}</td>
								<td>${tenantService.getOneTenant(hostelOrderVO.tenantNo).tenantPhone}</td>
								<td>${tenantService.getOneTenant(hostelOrderVO.tenantNo).tenantMail}</td>
								<td>${hostelOrderVO.customerQuantity }</td>
								<td>${hostelOrderVO.tenantScore }</td>
								<td>${hostelService.getOneHostel(hostelOrderVO.hostelNo).hostelName }</td>
								<td>${hostelOrderVO.hostelScore }</td>
								<td>${hostelOrderVO.hostelComment }</td>
								<td>${hostelOrderVO.paymentWay }</td>
								<td>${hostelOrderVO.paymentState }</td>
								<td>${hostelOrderVO.orderState }</td>
								<td class="table-expandable-arrow" onclick="expand(${hostelOrderVO.hostelOrderNo})"></td>
								<td>
<%-- 									<form action="<%=request.getContextPath()%>/verify.do" method="post"> --%>
<%-- 										<input type="hidden" name="hostelOrderNo" value="${hostelOrderVO.hostelOrderNo}"> --%>
<!-- 										<input type="submit" value="訂單處理"> -->
<!-- 									</form> -->
									<button type="button" class="btn btn-info btn-lg"  id="roomType_Add"
										data-toggle="modal" data-target="#myModal${hostelOrderVO.hostelOrderNo}"
										style="float:right;font-size:20px;margin:10px;font-family:標楷體;">
										審核訂單
									 </button>
								</td>
								
							</tr>
								
							<c:forEach var="hostelOrderDetailVO" items="${hostelOrderService.getAllOrderDetail(hostelOrderVO.hostelOrderNo) }">
								<tr style="text-align:center;" class="flips${hostelOrderVO.hostelOrderNo}">
									<td>${hostelOrderDetailVO.hostelOrderDetailNo }</td>
									<td width="10%" colspan="4">房型圖片:<br><img width="50%" src="<%=request.getContextPath()%>/ImageReader?roomTypeNo=${hostelOrderDetailVO.roomTypeNo }"></td>
									<td>房型名稱:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypeName }<br>${hostelOrderDetailVO.roomTypeNo }</td>
									<td>房型容納人數:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypeContain }</td>
									<td>房型價錢:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypePrice }</td>
									<td>房間數:<br>${hostelOrderDetailVO.roomQuantity }</td>
									<td>總金額:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypePrice*hostelOrderDetailVO.roomQuantity }</td>
									<td colspan="2">check in:<br>${hostelOrderDetailVO.checkInDate }</td>
									<td colspan="2">check out:<br>${hostelOrderDetailVO.checkOutDate }</td>
									<td>
										<c:forEach var="roomVO" items="${roomTypeService.getRoomsByRoomTypeNo(hostelOrderDetailVO.roomTypeNo) }">
											<div>${roomVO.roomNo }</div>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>					
																	
					</c:forEach>
				</table>	
			</div>
			
			<div class="col-md-1"></div>
		
		</div>
	</div>
<!-- 	container -->
	
	
<!------------------------------- 	Modal  -->
	 <c:forEach var="hostelOrderVO" items="<%=hostelOrderList%>" varStatus="s">
	 <div class="container">			
		  <!-- Modal -->
		  <div class="modal fade" id="myModal${hostelOrderVO.hostelOrderNo}" role="dialog">
		    <div class="modal-dialog modal-lg">
		      <!-- Modal content-->
		      <div class="modal-content">
			        <div class="modal-header">
			        	<button type="button" class="close" data-dismiss="modal">&times;</button>
				        <h4 class="modal-title">審核訂單</h4>
			        </div>
			        
			        <form method="post" action="<%=request.getContextPath()%>/verify.do" name="form1">

			        <div class="modal-body">
			        	<div class="form-group">
			        		<label  class="control-label">訂單編號:</label>
			        	 	${hostelOrderVO.hostelOrderNo}
			        		<table class="table table-hover table-expandable">
			        			<tr style="text-align:center;">
									<td width="10%">
										<label  class="control-label">${tenantService.getOneTenant(hostelOrderVO.tenantNo).tenantName}</label>
									</td>
									<td width="15%">
										${hostelOrderVO.paymentWay }
									</td>
									<td>
										<label  class="control-label">付款狀態:</label>
										<select name="paymentState">
										<option value="未付款">未付款</option>
										<option value="已付款">已付款</option>
										</select>
									</td>
									<td>
										<label  class="control-label">審核狀態:</label>
										<select name="orderState">
										<option value="接受">接受</option>
										<option value="拒絕">拒絕</option>
										</select>
									</td>
									<td>
										<label  class="control-label">房間代號:</label>
										<input name="roomNo" type="text" value="" size="10" id="room${s.index}">
										<input type="text" name="hostelOrderDetailNo" value="" id="detailId">
									</td>
								</tr>
								<c:forEach var="hostelOrderDetailVO" items="${hostelOrderService.getAllOrderDetail(hostelOrderVO.hostelOrderNo) }">
									<c:forEach var="roomVO" items="${roomTypeService.getRoomsByRoomTypeNo(hostelOrderDetailVO.roomTypeNo)}">
									!!!${hostelOrderDetailVO.hostelOrderDetailNo }
									<tr style="text-align:center;">
										<td>${hostelOrderDetailVO.hostelOrderDetailNo }
										</td>
										<td>房號:<br>${roomVO.roomNo}</td>
										<td>check in:<br>${hostelOrderDetailVO.checkInDate }</td>
										<td>check out:<br>${hostelOrderDetailVO.checkOutDate }</td>
										<td>房間狀態:<br>${roomVO.roomState }
											<select name="roomState">
												<option value="住房">住房</option>
												<option value="空房">空房</option>
												<option value="清潔">清潔</option>
											</select>
										</td>
										<td>
											<button type="button" class="btn btn-default" onclick="selectRoom(${roomVO.roomNo},${s.index},${hostelOrderDetailVO.hostelOrderDetailNo})" 
											style="font-size:20px;font-familty:標楷體"><b>選擇</b></button>
										</td>
									</tr>
									</c:forEach>
								</c:forEach>
			        		</table>
			        	
			        	 	
						</div>
						
					</div>
						
			        <div class="modal-footer">
			            <input type="hidden" name="action" value="update">
			            <input type="submit" value="審核完成">
			            <input type="hidden" name="hostelOrderNo" value="${hostelOrderVO.hostelOrderNo}">
<!-- 						<button type="submit" value="審核完成" class="btn btn-default" -->
<!-- 						style="font-size:20px;"><b>審核完成</b></button> -->
			        </div>
			        </form>
		      </div>
		      <!-- Modal content-->
		    </div>
		</div><!-- 		modal -->
	</div><!-- 	container -->
	</c:forEach>
<!------------------------------- 	Modal  -->	
	

	
</body>
</html>