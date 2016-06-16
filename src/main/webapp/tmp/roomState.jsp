<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.room.model.*" %>
<%@ page import="com.roomType.model.*" %>
<%@ page import="com.hostelOrderDetail.model.*"%>
<%@ page import="java.util.*" %>
<%
	Integer roomNo=null;
	try{
		 roomNo=new Integer(request.getParameter("roomNo").trim());
	}catch(Exception e){
		roomNo=0;
		e.getMessage();
	}
	Integer roomTypeNo=null;
	try{
		roomTypeNo=new Integer(request.getParameter("roomTypeNo"));
	}catch(Exception e){
		roomTypeNo=0;
		e.getMessage();
	}

	RoomService roomService=new RoomService();
	RoomVO roomVO =roomService.getOneRoom(roomNo);
	System.out.print(roomVO.getHostelOrderDetailNo());
	RoomTypeService roomTypeService=new RoomTypeService();
	RoomTypeVO roomTypeVO=roomTypeService.getOneRoomType(roomTypeNo);
	pageContext.setAttribute("roomTypeVO", roomTypeVO);
	pageContext.setAttribute("roomVO", roomVO);
	
	if(roomVO.getHostelOrderDetailNo()!=0){
		HostelOrderDetailService hostelOrderDetailService =new HostelOrderDetailService();
		HostelOrderDetailVO hostelOrderDetailVO=hostelOrderDetailService.getOneHostelOrderDetail(roomVO.getHostelOrderDetailNo());
		System.out.println(hostelOrderDetailVO.getCheckInDate());
		pageContext.setAttribute("hostelOrderDetailVO",hostelOrderDetailVO );
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />
	<!-- ****jquery**** -->  
<script src="http://code.jquery.com/jquery.js"></script>  
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<!-- ****bootstrap**** -->
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/NavBar_top.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<title>roomState</title>
</head>
<body>
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<form action="<%=request.getContextPath()%>/verify.do" method="post">
					<div class="row">
					<table class="table table-hover table-expandable table-striped">
						<thead>
							<tr>
								<th>房號</th>
								<th>房型名稱</th>
								<th>Check in</th>
								<th>Check out</th>
								<th>訂單編號</th>
								<th>房間狀態</th>
							</tr>
						</thead>
						<tr style="text-align:center;">
							<td>${roomVO.roomNo }</td>
							<td><a href="#" onClick="alertRoomType(${roomTypeVO.roomTypeNo })">
								${roomTypeVO.roomTypeName }</a></td>
							<td>${hostelOrderDetailVO.checkInDate}</td>
							<td>${hostelOrderDetailVO.checkOutDate }</td>
							<td><select name="hostelOrderDetailNo">
									<option value="${roomVO.hostelOrderDetailNo }">${roomVO.hostelOrderDetailNo }</option>
									<option value="0">0</option>
								</select>
							</td>
							<td>${roomVO.roomState }
								<select name="roomState">
									<option value="住房">住房</option>
									<option value="空房">空房</option>
									<option value="清潔">清潔</option>
								</select>
							</td>
						</tr>
					</table>
					</div>
					
					<div class="row" style="text-align:center;">
							<input type="hidden" name="action" value="updateRoom">
							<input type="hidden" name="roomNo" value="${roomVO.roomNo }">
							<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
							<input style="font-size:20px;font-family:微軟正黑體;" type="submit" class="btn btn-default" value="更新房間">
					</div>
				</form>
<%-- 				<c:if test="${roomVO.hostelOrderDetailNo!=0}"> --%>
					
<%-- 				</c:if> --%>
			
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	
	<script>
		function alertRoomType(roomTypeNo){
			var getRoomTypeNo=roomTypeNo;
			window.open('<%=request.getContextPath()%>/roomType_One.jsp?roomTypeNo='+roomTypeNo
			,'roomType','location=no,status=no,menubar=no,toolbar=no');
		}
	
	</script>

</body>
</html>