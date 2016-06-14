<%@page import="com.roomType.model.RoomTypeService"%>
<%@page import="com.hostelOrderDetail.model.HostelOrderDetailService"%>
<%@page import="com.hostelOrderDetail.model.HostelOrderDetailVO"%>
<%@page import="java.util.List"%>
<%@page import="com.hostel.model.HostelVO"%>
<%@page import="com.hostel.model.HostelService"%>
<%@page import="com.hostelOrder.model.HostelOrderVO"%>
<%@page import="com.hostelOrder.model.HostelOrderService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%


	int hostelOrderNo=(Integer)request.getAttribute("hostelOrderNo");
	HostelOrderService hoServ=new HostelOrderService();
	HostelOrderVO hoVO= hoServ.getOneHostelOrder(hostelOrderNo);
	pageContext.setAttribute("hoVO", hoVO);
	
	HostelService hServ=new HostelService();
	HostelVO hVO=hServ.getOneHostel(hoVO.getHostelNo());
	pageContext.setAttribute("hVO", hVO);
	
	
	HostelOrderDetailService hodServ=new HostelOrderDetailService();
	List<HostelOrderDetailVO> hodList=hodServ.getAllByHostelOrderNo(hostelOrderNo);
	pageContext.setAttribute("hodList", hodList);
	
	
	int orderTotalMoney=0;
	for(HostelOrderDetailVO newHodVO:hodList){
		orderTotalMoney+=newHodVO.getTotalPrice();

	}
	pageContext.setAttribute("orderTotalMoney", orderTotalMoney);
	
	RoomTypeService rtServ=new RoomTypeService();
	pageContext.setAttribute("rtServ", rtServ);
	
	
%>
<!DOCTYPE html>
<html>
<head>

<!-- ****favicon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="images/Logo_S.png" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta charset="utf-8">
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/OK.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">


<!-- ****jquery**** -->
<script src="http://code.jquery.com/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


    
<title>eChioce| 台灣民宿  | choose your life.</title>



</head>

<body>
<div class="col-md-12" style="margin-top:150px">
	<div class="row">
	
	</div>
</div>
<div class="col-md-12">
	<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-6" style="border:5px solid #6999D1;">
		<p style="font-size:40px">預訂成功!</p>
		<p>*請待房東審核後才匯款</p>
		<p>*如急需確認請聯繫該房東</p>
		
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-4" style="width:300px;height:200px;background-image:url('<%=request.getContextPath()%>/hostel/GetHostelPicture?pkNo=${hoVO.hostelNo}');
				background-size:cover;no-repeat">
				</div>
				<div class="col-md-5">
					<p style="font-size:20px">訂單編號 :${hoVO.hostelOrderNo }</p>
					<p>民宿名稱:${hVO.hostelName}</p>	
					<c:forEach var="hodVO" items="${hodList}">
					<p>房型:${rtServ.getOneRoomType(hodVO.roomTypeNo).roomTypeName}</p>
					<p>入住日期:${hodVO.checkInDate}</p>
					<p>退房日期:${hodVO.checkOutDate}</p>
					<hr>
					</c:forEach>
				</div>
				<div class="col-md-3">		
					<p style="margin-top:160px;font-size:22px">價格$${orderTotalMoney }/元</p>
					<p><a href="<%=request.getContextPath()%>/tenant/tenant_One.jsp">查看訂單</a></p>					
				</div>
					
				<p style="font-size:30px">注意事項:</p>
				<p>一、訂房確認後，請於三天內轉帳房價五成到民宿帳戶，並以電話 (${ hVO.hostelPhone})通知。超過三天未通知，房間恕不保留。</p>
				<p>二、入住(check in)：PM3:00後，請提供入住辦理登記及繳交尾款。</p>
				<p>三、退房(check out)：AM11:00前辦理退房及結清其他消費。</p>
				<p>四、依衛生局規定室內全面禁菸，抽菸請至戶外庭院，若於房內抽菸，衛生局將依規定罰款2千～1萬元。</p>
				<p>五、本民宿歡迎攜帶寵物入住，但僅限5公斤以下之寵物入住一樓標準房。</p>
				<p>六、入住人數總量管制，請依訂房人數入住，若超出人數拒絕入住，恕不退還訂金。</p>
	
			</div>			
		</div>			
	</div>
	<div class="col-md-3">
	</div>
	
	
	
	
	</div>
</div>







</body>
</html>