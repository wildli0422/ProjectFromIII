<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hostel.model.*" %>
<%@ page import="com.dealer.model.*" %>
<%@ page import="com.roomType.model.*" %>
<%@ page import="java.util.Set" %>
<%
	DealerVO dealerVO=(DealerVO)session.getAttribute("dealerVObyAccount");
	Integer dealerNo=dealerVO.getDealerNo();

	DealerService dealerService=new DealerService();
	HostelVO hostelVO=dealerService.getHostelByDealerNo(dealerNo);
	Integer hostelNo=hostelVO.getHostelNo();
	
	HostelService hostelService=new HostelService();
	Set<RoomTypeVO> set=hostelService.getRoomTypesByHostelNo(hostelNo);
%>
<!DOCTYPE html>
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />
	<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">

<!-- ****jquery**** -->  
<!--  <script src="http://code.jquery.com/jquery.js"></script>  -->
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<!-- ****bootstrap**** -->
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/NavBar_top.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/roomType_modify.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/sweetalert-dev.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/sweetalert.css">
		
	<script>


// 			$(document).ready(function() {
// 				$("div #flip").click(function() {
// 					$(this).next().toggle("slow");
// 				});
// 			});
// 			$(document).ready(function() {
// 				$("[class='flip']").click(function() {
// 					$(this).next().toggle("slow");
// 				});
// 			});
			$(document).ready(function() {
				$("button[value='修改']").click(function() {
					$(this).parents('div[class="flip"]').next().toggle("slow");
				});
// 				$("button[value='修改']").focus();
			});

// 			$(document).ready(function() {
// 				$("div #flip [type='submit']").click(function() {
// 					$(this).next().toggle("slow");
// 				});
// 			});
// 			$('#exampleModal').on('show.bs.modal', function (event) {
// 			  var button = $(event.relatedTarget) // Button that triggered the modal
// 			  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
// 			  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
// 			  var modal = $(this)
// 			  modal.find('.modal-title').text('New message to ' + recipient)
// 			  modal.find('.modal-body input').val(recipient)
// 			});
			$(document).ready(function() {
				if($('#check').text()=='false'){
					swal("新增資料失敗", "請填入正確/完整的資料", "error");
				}
				if($('#check').text()=='true' && $('#check').attr('value')==1){
					swal("新增資料成功", "新增一筆房型", "success");
					$('#check').attr('value',0);
				}
			});
			
// 		
// 		因為id只能有一個，因為有多個刪除，所以要用class做變化
		$(document).ready(function(){
			$('.deleteBt').click(function(e) {
				e.preventDefault();
				var form=$(this).parents('form');
				swal({
					title: 'Are you sure?',
					text: 'You will not be able to recover this imaginary file!',
					type: 'warning',
					showCancelButton: true,
					confirmButtonColor: '#3085d6',
					cancelButtonColor: '#d33',
					confirmButtonText: 'Yes, delete it!',
					closeOnConfirm: false
					
					},function(isConfirm){
						if (isConfirm) {
							swal({
								title: 'Are you sure?',
								text: 'You will not be able to recover this imaginary file!',
								type: 'success'
									},function(){
										form.submit();
								});							
						}	
					});
			});
		});
			
// 		
		$(document).ready(function(){
			
			$('.ajaxUpdate').click(function(){
				alert("132");
				$.ajax({
					url:"<%=request.getContextPath()%>/roomType_modify.do",
					type:"post",
					async: false,					
					data:{
						action:"ajaxUpdate",
						'roomTypeNo':'',
						
					},
					success:function(data){
						alert("success");
					},
					error:function(){alert("error")}
						
				});
			});
			
		});
		
// 		$(document).ready(function(){
			
// 			$('#ajaxInsert').click(function(){
// 				alert("!!!");
// 				console.log($('#hostelNo').val());
// 				console.log($('#facilityNo').val());
// 				console.log($('#roomTypeName').val());
// 				console.log($('#roomTypeContain').val());
// 				console.log($('#roomTypePrice').val());
// 				console.log($('#roomQuantity').val());
// 				console.log($('#roomTypePicture').val());
// 				console.log($('#roomTypeContent').val());
// 				$.ajax({
<%-- 					url:"<%=request.getContextPath()%>/roomType_modify.do", --%>
// 					type:"post",
// 					async: false,					
// 					data:{
// 						action:"ajaxInsert",
// 						hostelNo:"$('#hostelNo').val()",
// 						facilityNo:"$('#facilityNo').val()",
// 						roomTypeName:"$('#roomTypeName').val()",
// 						roomTypeContain:"$('#roomTypeContain').val()",
// 						roomTypePrice:"$('#roomTypePrice').val()",
// 						roomQuantity:"$(#roomQuantity).val()",
// 						roomTypePicture:"$('#roomTypePicture').val()",
// 						roomTypeContent:"$('#roomTypeContent').val()"
// 					},
// 					success:function(data){
// 						alert("success");
// 					},
// 					error:function(){alert("error")}
						
// 				});
// 			});
			
// 		});
		
// 		function updateTable(jsonObj){
			
// 		}
		
			
	</script>
<title>hostel_modify.jsp</title>
</head>
<body bgcolor="white">
	
<!------------------------------ nav bar ---------------------------------->
	<div class="col-md-12" id="topBar">
	<div class="row" class="navbar navbar-default" role="navigation">
	<div class="col-md-3" style="margin-top:20px;">
		<a href="<%=request.getContextPath()%>/HomePage.jsp"><img src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
	</div>
	<div class="col-md-9" id="top_menu" style="margin-top:20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a href="<%=request.getContextPath()%>/hostel/hostelManager.jsp" id=""><i class="fa fa-home fa-2x"></i> 民宿基本資料</a></li>
					<li class="dropdown">
				        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
				        <i class="fa fa-bed fa-2x"></i>房務管理 
				        <span class="caret"></span></a>
				        <ul class="dropdown-menu">
				           <li><a id="tab_1" href="<%=request.getContextPath()%>/room/roomType_modify.jsp"">房間基本資料</a></li>
				           <li><a href="<%=request.getContextPath()%>/room/emptyRoomType.jsp" target="iframe">房間狀態管理</a></li>       
				        </ul>
				     </li>
					<li><a href="<%=request.getContextPath()%>/hostel/hostelCalendar.jsp" id=""><i class="fa fa-calendar fa-2x"></i> 訂單管理</a></li>					
					<li><a href="<%=request.getContextPath()%>/dealer/dealer_One.jsp" id=""><i class="fa fa-user fa-2x"></i> 個人資料頁面</a></li>
					 <c:if test="${empty dealerVObyAccount}">
					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-in fa-2x"></i> 登入</a></li> 
					 </c:if>
					 <c:if test="${not empty dealerVObyAccount}">
					 	<li><a href="<%=request.getContextPath()%>/loginServlet.do?action=logout" id=""><i class="fa fa-sign-out fa-2x"></i> 登出</a></li>
					 </c:if>
				 	
				</ul>
     </div>				
	</div>
</div>
<!------------------------------ nav bar ---------------------------------->
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
			<span style="font-size:20px;">房務管理>房間基本資料</span>
			<button type="button" class="btn btn-info btn-lg"  id="roomType_Add"
				data-toggle="modal" data-target="#myModal"
				style="float:right;font-size:20px;margin:10px;font-family:標楷體;">
			 新增房型</button>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
<!-- 	 <button id="test">try</button> -->
	 
	<div class="col-md-12">
		<div class="row" >
			<div class="col-md-2">
			</div>
			<div class="col-md-8" style="background-color:#6999D1;height:34px;">		
				<p style="font-size:22px;color:#ffffff;text-align:center;letter-spacing:20px">房型介紹</p>
			</div>
			</div>
			<div class="col-md-2">
		</div>
	</div>
	<div class="container">
		  		<div style="text-align:center;font-size:20px;">
				<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs }">
<!-- 				           <div id="check" hidden>false</div>  -->
					</c:if>
					<c:if test="${empty errorMsgs }">
<!-- 				           <div id="check" value="1" hidden>true</div>  -->
					</c:if>
				</div>
				
		  <!-- Trigger the modal with a button -->		
		  <!-- Modal -->
		  <div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		    
		      <!-- Modal content-->
		      <div class="modal-content" id="formInsert">
			        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					          <h4 class="modal-title">新增房型</h4>
					          <div style="text-align:center;font-size:20px;">
						<%-- 錯誤表列 --%>
							<c:if test="${not empty errorMsgs }">
								<font color='red'>請修正以下錯誤:
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
											<li>${message}</li>
									</c:forEach>
								</ul>
								</font>
							</c:if>
						</div>
			        </div>
			        <form method="post" action="<%=request.getContextPath() %>/roomType_modify.do" name="form1" enctype="multipart/form-data">

			        <div class="modal-body">
			        	<div class="form-group">
			        	 	<label  class="control-label">民宿編號:</label>
			        	 	<span><%=hostelNo%></span>
			        	 	<input type="hidden" id="hostelNo" name="hostelNo" value="<%=hostelNo%>">
<!-- 							<input type="text" name="hostelNo" size="45" class="form-control" -->
<%-- 									value="${(roomTypeVO==null)?"2001":roomTypeVO.getHostelNo()}"> --%>
						</div>
						
<!-- 						<div class="form-group"> -->
<!-- 								<label  class="control-label">設備編號:</label> -->
								<input type="hidden" id="facilityNo" name="facilityNo" size="45" class="form-control"
									value="4001">
<!-- 						</div> -->
						
							<div class="form-group">
								<label  class="control-label">房型名稱:</label>
								<input type="text" id="roomTypeName" name="roomTypeName" size="45" class="form-control"
									value="${(roomTypeVO==null)?"typeName":roomTypeVO.getRoomTypeName() }">
							</div>
							
							<div class="form-group">
								<label  class="control-label">房型容納人數:</label>
								<input type="text" id="roomTypeContain" name="roomTypeContain" size="10" class="form-control"
									value="${(roomTypeVO==null)?"0":roomTypeVO.getRoomTypeContain()}" required>
								
								<label  class="control-label">房型價錢:</label>
								<input type="text" id="roomTypePrice" name="roomTypePrice" size="45" class="form-control"
									value="${(roomTypeVO==null)?"1000":roomTypeVO.getRoomTypePrice()}" required>
							</div>
							<div class="form-group">
								<label  class="control-label">房間數:</label>
								<input type="text" id="roomQuantity" name="roomQuantity" size="45" class="form-control"
									value="" required>
							</div>
							<div class="form-group">
								<label  class="control-label">房型內容:</label>
								<textarea id="roomTypeContent" name="roomTypeContent" cols="45" rows="3" class="form-control"
									>${(roomTypeVO==null)?"please enter content":roomTypeVO.getRoomTypeContent() }
								</textarea>
							</div>
							<div class="form-group">
								<label  class="control-label">房型照片:</label>
								<input type="file" id="roomTypePicture" name="roomTypePicture" size="45" class="form-control"
									value="${(roomTypeVO==null)?null:roomTypeVO.getRoomTypePicture()}" required>
							</div>
						
			        </div>
			        <div class="modal-footer">
			        	<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			            <input type="hidden" name="action" value="insert">
<!-- 			            <button id="ajaxInsert">新增</button> -->
						<input type="submit" value="新增">
			        </div>
			        </form>
		      </div>
		      
		    </div>
		  </div>
	  
	</div>
	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<c:forEach var="roomTypeVO" items="<%=set%>">
				<div class="row" style="border:1px solid silver;margin-top:15px;">
<!-- 				flip -->
					<div  style="background-color:#fff3e6" class="flip" >
						<table id="roomType_modify">
						<tr align="center" valign="middle" >
							<td width="10%"><span ><img style="width:200px;margin:20px;" src="<%=request.getContextPath()%>/ImageReader?roomTypeNo=${roomTypeVO.roomTypeNo}"></span></td>
							<td width="10%" style="vertical-align:middle">【${roomTypeVO.roomTypeName }】</td>
							<td width="10%" style="vertical-align:middle">價格:$${roomTypeVO.roomTypePrice }/天</td>
							<td width="10%" style="vertical-align:middle">容納人數: ${roomTypeVO.roomTypeContain }</td>
							<td width="28%" style="vertical-align:middle;">房型描述: ${roomTypeVO.roomTypeContent }</td>
							<td width="5%" style="vertical-align:middle">
<%-- 							<form method="post" action="<%=request.getContextPath()%>/roomType_modify.do"> --%>
									<button type="submit" value="修改" style="margin:10px;">
									<span style="font-size:25px;padding:5px;" class="glyphicon glyphicon-wrench"></span>
									</button>
								<input type="hidden" name="roomTypeNo" value="${roomTypeVO.roomTypeNo}">
<%-- 								<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"> --%>
<!-- 								<input type="hidden" name="action" value="getOne_For_Update"> -->
<!-- 							</form> -->
							</td>
							<td width="5%" style="vertical-align:middle">
							<form method="post" action="<%=request.getContextPath()%>/roomType_modify.do" >
								<button  value="glyphicon glyphicon-trash" class="deleteBt" style="margin:10px;">
									<span style="font-size:25px;padding:5px;" class="glyphicon glyphicon-trash"></span>
								</button>
								<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
								<input type="hidden" name="roomTypeNo" value="${roomTypeVO.roomTypeNo}">
								<input type="hidden" name="action" value="delete">
							</form></td>
							<td>
								<span><a style="margin:10px;" 
								href="<%=request.getContextPath()%>/room/roomType_One.jsp?roomTypeNo=${roomTypeVO.roomTypeNo}&check=check" 
								style="float:right;" target="_blank"  class="glyphicon glyphicon-search" style="margin:10px;"></a></span>
							</td>
						</tr>
						</table>
					</div>
					<div  style="background-color:#ffd9b3;border:1px solid silver;text-align:center;" id="panel" hidden>
						<form method="post" action="<%=request.getContextPath() %>/roomType_modify.do" name="form1" enctype="multipart/form-data">
							<div >
							<table border="0" style="text-align:center;margin-left:auto;margin-right:auto">
								<tr>
									<td>房型編號:<font color=red><b>*</b></font></td>
									<td>${roomTypeVO.getRoomTypeNo()}</td>
								</tr>
							
<!-- 								<tr> -->
<!-- 									<td>民宿編號:</td> -->
<%-- 									<td>${roomTypeVO.getHostelNo()}</td> --%>
<!-- 								</tr> -->
								<tr>
									<td>設備編號:</td>
									<td><input type="text" name="facilityNo" size="45"
										value="${roomTypeVO.getFacilityNo()}"></td>
								</tr>
								<tr>
									<td>房型名稱:</td>
									<td><input type="text" name="roomTypeName" size="45"
										value="${roomTypeVO.roomTypeName }"></td>
								</tr>
								<tr>
									<td>房型容納人數:</td>
									<td><input type="text" name="roomTypeContain" size="45"
										value="${roomTypeVO.roomTypeContain}"></td>
								</tr>
								<tr>
									<td>房型價錢:</td>
									<td><input type="text" name="roomTypePrice" size="45"
										value="${roomTypeVO.roomTypePrice }"></td>
								</tr>
								<tr>
									<td>房型照片:</td>
									<td><input type="file" name="roomTypePicture" size="45"
										value="${roomTypeVO.getRoomTypePicture()}"></td>
								</tr>
								<tr>
									<td>房型內容:</td>			
									<td><br><textarea name="roomTypeContent" cols="45" rows="3"
										>${roomTypeVO.roomTypeContent }</textarea></td>
								</tr>
							</table>
								<input type="hidden" name="action" value="update">
								<input type="hidden" name="roomTypeNo" value="${roomTypeVO.getRoomTypeNo()}">
								<!--原送出修改的來源網頁路徑,從request取出後,再送給Controller準備轉交之用-->
						<!-- 												--------getAttribute->getParameter -->
								<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
								<!--只用於:roomType_listAll.jsp-->
								<!-- 										--------getAttribute->getParameter -->
								<input type="submit" value="update" id="">
<!-- 								<button class="ajaxUpdate">更新</button> -->
							</form>
						</div>
					</div>

				</div>
				</c:forEach>
			</div>
			<div class="col-md-1"></div>
		</div>
	</div>
	
	

<!-- //------------footer-------------- -->	
<div class="col-md-12" id="footer01" style="margin-top:60px;">
	<div class="row">
	<div class="col-md-4">
	</div>
	<div class="col-md-4">
	<p>© eChoice, Inc.</p>	
	</div>
	<div class="col-md-4">
	</div>	
	</div>
</div>

<div class="col-md-12" id="footer02" style="height:200px;">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-4">
	
	<ul>
		<li>關於我們</li>
		<li>政策</li>
		<li>幫助</li>
		<li>Blog</li>
		<li>服務條款與私隱聲明</li>
	</ul>
	
	</div>
	<div class="col-md-2">
	</div>
	<div class="col-md-4">
	<ul>
		<li>常見問題</li>
		<li>聯絡我們</li>
		<li>廣告刊登</li>	
	</ul>
	<i class="fa fa-phone fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-envelope-o fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-facebook-square fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-twitter-square fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-android fa-2x"></i>&nbsp;&nbsp;
	<i class="fa fa-print fa-2x"></i>
	
	</div>
	</div>
</div>
<!-- //------------footer-------------- -->
</body>
</html>