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
							<th width="7%">�q�Ф��</th>
							<th width="5%">�Ы�</th>
							<th width="5%">�ʧO</th>
							<th width="5%">�Ыȹq��</th>
							<th width="3%">�Ы�Mail</th>
							<th>�ЫȤH��</th>
							<th>�Ыȵ���</th>
							<th>�q�Х��J</th>
							<th>���J����</th>
							<th>���J����</th>
							<th>�I�ڤ覡</th>
							<th>�I�ڪ��A</th>
							<th>�q�檬�A</th>
							<th></th>
							<th>�B�z�q��</th>
							
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
<!-- 										<input type="submit" value="�q��B�z"> -->
<!-- 									</form> -->
									<button type="button" class="btn btn-info btn-lg"  id="roomType_Add"
										data-toggle="modal" data-target="#myModal${hostelOrderVO.hostelOrderNo}"
										style="float:right;font-size:20px;margin:10px;font-family:�з���;">
										�f�֭q��
									 </button>
								</td>
								
							</tr>
								
							<c:forEach var="hostelOrderDetailVO" items="${hostelOrderService.getAllOrderDetail(hostelOrderVO.hostelOrderNo) }">
								<tr style="text-align:center;" class="flips${hostelOrderVO.hostelOrderNo}">
									<td>${hostelOrderDetailVO.hostelOrderDetailNo }</td>
									<td width="10%" colspan="4">�Ы��Ϥ�:<br><img width="50%" src="<%=request.getContextPath()%>/ImageReader?roomTypeNo=${hostelOrderDetailVO.roomTypeNo }"></td>
									<td>�Ы��W��:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypeName }<br>${hostelOrderDetailVO.roomTypeNo }</td>
									<td>�Ы��e�ǤH��:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypeContain }</td>
									<td>�Ы�����:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypePrice }</td>
									<td>�ж���:<br>${hostelOrderDetailVO.roomQuantity }</td>
									<td>�`���B:<br>${roomTypeService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypePrice*hostelOrderDetailVO.roomQuantity }</td>
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
				        <h4 class="modal-title">�f�֭q��</h4>
			        </div>
			        
			        <form method="post" action="<%=request.getContextPath()%>/verify.do" name="form1">

			        <div class="modal-body">
			        	<div class="form-group">
			        		<label  class="control-label">�q��s��:</label>
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
										<label  class="control-label">�I�ڪ��A:</label>
										<select name="paymentState">
										<option value="���I��">���I��</option>
										<option value="�w�I��">�w�I��</option>
										</select>
									</td>
									<td>
										<label  class="control-label">�f�֪��A:</label>
										<select name="orderState">
										<option value="����">����</option>
										<option value="�ڵ�">�ڵ�</option>
										</select>
									</td>
									<td>
										<label  class="control-label">�ж��N��:</label>
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
										<td>�и�:<br>${roomVO.roomNo}</td>
										<td>check in:<br>${hostelOrderDetailVO.checkInDate }</td>
										<td>check out:<br>${hostelOrderDetailVO.checkOutDate }</td>
										<td>�ж����A:<br>${roomVO.roomState }
											<select name="roomState">
												<option value="���">���</option>
												<option value="�ũ�">�ũ�</option>
												<option value="�M��">�M��</option>
											</select>
										</td>
										<td>
											<button type="button" class="btn btn-default" onclick="selectRoom(${roomVO.roomNo},${s.index},${hostelOrderDetailVO.hostelOrderDetailNo})" 
											style="font-size:20px;font-familty:�з���"><b>���</b></button>
										</td>
									</tr>
									</c:forEach>
								</c:forEach>
			        		</table>
			        	
			        	 	
						</div>
						
					</div>
						
			        <div class="modal-footer">
			            <input type="hidden" name="action" value="update">
			            <input type="submit" value="�f�֧���">
			            <input type="hidden" name="hostelOrderNo" value="${hostelOrderVO.hostelOrderNo}">
<!-- 						<button type="submit" value="�f�֧���" class="btn btn-default" -->
<!-- 						style="font-size:20px;"><b>�f�֧���</b></button> -->
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