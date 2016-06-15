<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.roomType.model.RoomTypeService"%>
<%@page import="com.tenant.model.*"%>
<%@page import="com.hostel.model.*"%>
<%@page import="com.hostelOrder.model.*"%>
<%@page import="com.hostelOrderDetail.model.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%
	TenantVO tenantVO = (TenantVO) session.getAttribute("tenantVO");
	TenantService tenantService = new TenantService();
	Integer tenantNo = tenantVO.getTenantNo();
	HostelOrderService hoService = new HostelOrderService();
	pageContext.setAttribute("hoService", hoService);
	List<HostelOrderVO> hostelordlist = hoService.getAllByTenantNo(tenantNo);
	pageContext.setAttribute("hostelord", hostelordlist);
	HostelOrderDetailService hodService = new HostelOrderDetailService();
	pageContext.setAttribute("hodService", hodService);
	HostelService hostelService = new HostelService();
	pageContext.setAttribute("hostelService", hostelService);
	RoomTypeService rtService = new RoomTypeService();
	pageContext.setAttribute("rtService", rtService);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- ****favicon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />

<!-- ****bootstrap icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tenant_One.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript">
  $(function(){
	  $("#tab_1").click(function(){
		  $("#iframe").attr("src","<%=request.getContextPath()%>/management_hostel.jsp");
	  });
	  
	  $("[id^='traceUp']").click(function(){
			var lastIdx=$(this).attr('id').lastIndexOf("-")+1;
			var hostelNo=$(this).attr('id').substring(lastIdx);
			alert("hostelNo = "+hostelNo);
	    	$.ajax({
				 type:"GET",
				 url:"<%=request.getContextPath()%>/HostelTraceAjax",
				 data:{action:"addTrace",hostelNo:hostelNo},
				 dataType:"json",
				 success:function (data){
					 alert("success")
						traceListDisplay(data);
					
			     },
	             error:function(){alert("error")}
            })
	  });
	  
	  $(".order").click(function(){
		  
		  $(this).next(".detail").toggle(500);
		  $(this).next(".detail").next(".detail").toggle(500);
		  });
	  
	  
	    $("#traceDropDown").click(function() {
	    	$.ajax({
				 type:"GET",
				 url:"<%=request.getContextPath()%>/HostelTraceAjax",
				 data:{action:"getTrace"},
				 dataType:"json",
				 success:function (data){
					 
						traceListDisplay(data);
					
			     },
	             error:function(){}
             })
	    	});
	   
		  $("#roomTypeDiv").find("[id^='btn-']").click(function(){
			  bookBtnClick(this);
		  });
		  $( "div" ).find("[id^='effect']").hide();
	      
		  <c:if test="${cartList.size()>0}">
			var options = {};
			var cartDetail=$("#effect-cart");
			$(cartDetail).show( "drop", options, 500, showCartList);
			</c:if>
			
		$("[id^='checkin']").datepicker({
		      defaultDate: "+1w",
		      changeMonth: true,
		      numberOfMonths: 1,
		      dateFormat: 'yy-mm-dd',
		      showButtonPanel: true,
		      onClose: function( selectedDate ) {
		    	  $(this).next().datepicker( "option", "minDate", selectedDate );
						if($(this).next().val()!=""){
							var lastIdx=$(this).attr('id').lastIndexOf("-")+1;
							var roomTypeNo=$(this).attr('id').substring(lastIdx);
						 	$.ajax({
								 type:"GET",
								 url:"<%=request.getContextPath()%>/AjaxJqueryController",
								 data:{action:"getMaxQty",checkIn:$(this).val(),checkOut:$(this).val(),roomTypeNo:roomTypeNo,eleID:$(this).attr('id')},
								 dataType:"json",
								 success:function (data){
										var itSelf=document.getElementById(data[0].eleID);
										$(itSelf).parent().find("[id^='showMaxQty']").html("<b>您所選擇的時間尚有:"+ data[0].maxQty+"間空房</b>");
										$(itSelf).parent().find("p").find("[id^='roomQty']").attr("max",data[0].maxQty);
							     },
					             error:function(){}
				             })
							
					}
		      }
		    });
		$("[id^='checkout']").datepicker({
		      defaultDate: "+1w",
		      changeMonth: true,
		      numberOfMonths: 1,
		      dateFormat: 'yy-mm-dd',
		      showButtonPanel: true,
		      onClose: function( selectedDate ) {
		    	  $(this).prev().datepicker( "option", "maxDate", selectedDate );
					if($(this).prev().val()!=""){
						var lastIdx=$(this).attr('id').lastIndexOf("-")+1;
						var roomTypeNo=$(this).attr('id').substring(lastIdx);
					 	$.ajax({
							 type:"GET",
							 url:"<%=request.getContextPath()%>/AjaxJqueryController",
							 data:{action:"getMaxQty",checkIn:$(this).val(),checkOut:$(this).val(),roomTypeNo:roomTypeNo,eleID:$(this).attr('id')},
							 dataType:"json",
							 success:function (data){
									var itSelf=document.getElementById(data[0].eleID);
									$(itSelf).parent().find("[id^='showMaxQty']").html("<b>您所選擇的時間尚有:"+ data[0].maxQty+"間空房</b>");
									$(itSelf).parent().find("p").find("[id^='roomQty']").attr("max",data[0].maxQty);
						     },
				             error:function(){}
			             });
					}
		      }
		    });
		
		
		$("[id^='addOrderbtn']").click(function(){
			var lastIdx=$(this).attr('id').lastIndexOf("-")+1;
			var roomTypeNo=$(this).attr('id').substring(lastIdx);
			var roomQty=$(this).parent().find("[id^='roomQty']").val();
			var options = {};
			var cartDetail=$("#effect-cart");
			$(cartDetail).show( "drop", options, 500, showErList());
			var checkIn=$(this).parent().parent().find("[id^='checkin']").val();
			var checkOut=$(this).parent().parent().find("[id^='checkout']").val();
			if(roomQty>0){
		 		$.ajax({
				 type:"GET",
				 url:"<%=request.getContextPath()%>/AjaxJqueryController",
				 data:{action:"addToCart",checkIn:checkIn,checkOut:checkOut,roomTypeNo:roomTypeNo,roomQty:roomQty},
				 dataType:"json",
				 success:function (data){
					 
							showCartList(data)
							
			     },
	             error:function(){alert("error!")}
          		  });
			}

		});
		
		

  });
  
	function traceListDisplay(jsonList){
		  var traceListDisplay=document.getElementById("traceListDisplay");
		  $("#traceListDisplay").empty();
		  var showText=document.createElement("li");
		  showText.innerHTML="<a href='<%=request.getContextPath()%>/view/tenantViewList.jsp'>景點收藏清單<a>";
		  var hr=document.createElement("hr");
		  traceListDisplay.appendChild(showText);
		  traceListDisplay.appendChild(hr);
		  for(var i=0;i<jsonList.length;i++){
		  var li =document.createElement("li");
		  var a=document.createElement("a");
		  a.setAttribute("href","<%=request.getContextPath()%>/hostel/IDVhostelDisplay.jsp?hostelNo="+jsonList[i].hostelNo);
		  a.innerHTML=jsonList[i].traceHostelName;
		  li.appendChild(a);
		  traceListDisplay.appendChild(li);
		  }
	  }
	
	function bookBtnClick(itSelf){
	
// 		alert("test = "+		itSelf.id)
// 		alert("test2 = "+$(itSelf).parent().next().find("[id^='effect']").get(0).id)
		var effectDiv=$(itSelf).parent().next().find("[id^='effect']").get(0);
		
		var options = {};
		$( effectDiv ).show( "drop", options, 500, showErList(effectDiv));
	}
	
	function showErList(showDiv){
		

	}
	
	function showCartList(cartList){
		
		if(cartList==null){
			   $.ajax({
					 type:"GET",
					 url:"<%=request.getContextPath()%>/AjaxJqueryController",
					 data:{action:"getCartList" },
					 dataType:"json",
					 success:function (data){
						 		
								showCartList(data);
								return;
				     },
		             error:function(){alert("showCartError")}
	             });
	             
		}

   var cartListTable=document.getElementById("cartListTable");
   cartListTable.innerHTML = ""
   var cTr=cartListTable.insertRow(cartListTable.rows.length);
   var title=new Array();
   title[0]=document.createTextNode("房名");
   title[1]=document.createTextNode("間數");
   title[2]=document.createTextNode("總價");
   title[3]=document.createTextNode("入住日期");
   title[4]=document.createTextNode("退房日期");
   title[5]=document.createTextNode("移除這筆訂單");
   for(var i=0;i<title.length;i++){
	   var spanTag=document.createElement("span");
	   spanTag.appendChild(title[i])
	   var cTd=cTr.insertCell(i);
	   cTd.setAttribute("class","cartListClass");
	   cTd.appendChild(spanTag);
   }
   for(var i=0;i<cartList.length;i++){
	   var cTr=cartListTable.insertRow(cartListTable.rows.length);
	   cartArr=new Array();
	   cartArr[0]=document.createTextNode(cartList[i].roomTypeName);
	   cartArr[1]=document.createTextNode(cartList[i].roomQty);
	   cartArr[2]=document.createTextNode(cartList[i].price);
	   cartArr[3]=document.createTextNode(cartList[i].checkInDate);
	   cartArr[4]=document.createTextNode(cartList[i].checkOutDate);
	   cartArr[5]=document.createElement("input");
	   cartArr[5].setAttribute("type","button");
	   cartArr[5].setAttribute("id","del_"+i);
	   cartArr[5].setAttribute("value","刪除");
	   cartArr[5].setAttribute("onclick","deleteCart(this.id);");
	   for(var j=0;j<cartArr.length;j++){
		   var cTd=cTr.insertCell(j);
		   cTd.setAttribute("class","cartListClass");
		   cTd.appendChild(cartArr[j]);
	   }
   }
}
	function deleteCart(id){
		var delIndex=id.substring(4);
		   $.ajax({
				 type:"GET",
				 url:"<%=request.getContextPath()%>/AjaxJqueryController",
				 data:{action:"delCartList",delIndex:delIndex},
				 dataType:"json",
				 success:function (data){
					 showCartList(data);
			     },
	             error:function(){alert("delError")}
             })
		
	}
  
</script>	






<script>
	    //利用canvas產生一個內含文字的圖檔
	    function createMarkerIcon(text, opt) {
	        //定義預設參數
	        var defaultOptions = {
	            fontStyle: "normal", //normal, bold, italic
	            fontName: "Arial",
	            fontSize: 12, //以Pixel為單位
	            bgColor: "darkblue",
	            fgColor: "white",
	            padding: 4,
	            arrowHeight: 6 //下方尖角高度
	        };
	        options = $.extend(defaultOptions, opt);
	        //建立Canvas，開始幹活兒
	        var canvas = document.createElement("canvas"),
	            context = canvas.getContext("2d");
	        //評估文字尺寸
	        var font = options.fontStyle + " " + options.fontSize + "px " + 
	                   options.fontName;
	        context.font = font;
	        var metrics = context.measureText(text);
	        //文字大小加上padding作為外部尺寸
	        var w = metrics.width + options.padding * 2;
	        //高度以Font的大小為準
	        var h = options.fontSize + options.padding * 2 +
	                options.arrowHeight;
	        canvas.width = w;
	        canvas.height = h;
	        //邊框及背景
	        context.beginPath();
	        context.rect(0, 0, w, h - options.arrowHeight);
	        context.fillStyle = options.bgColor;
	        context.fill();
	        //畫出下方尖角
	        context.beginPath();
	        var x = w / 2, y = h, arwSz = options.arrowHeight;
	        context.moveTo(x, y);
	        context.lineTo(x - arwSz, y - arwSz);
	        context.lineTo(x + arwSz, y - arwSz);
	        context.lineTo(x, y);
	        context.fill();
	        //印出文字
	        context.textAlign = "center";
	        context.fillStyle = options.fgColor;
	        context.font = font;
	        context.fillText(text,
	            w / 2,
	            (h - options.arrowHeight) / 2 + options.padding);
	        //傳回DataURI字串
	        return canvas.toDataURL();
	    }

		function doFirst(){

			var latlng = new google.maps.LatLng('${hostelVO.hostelLat}','${hostelVO.hostelLon}'); 
			var area = document.getElementById('message');
			var option = {
				zoom : 15,
				center : latlng,
				panControl: true, 
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};

			var map = new google.maps.Map(area, option); 

			var marker = new google.maps.Marker({
				position : latlng, 
				map : map,
	            icon: createMarkerIcon('${hostelVO.hostelName}', {
	                bgColor: "#228b22"
	            }),
	            map: map

	        });


		}
		window.addEventListener('load', doFirst, false);
</script>

<title>eChioce| 台灣民宿  | choose your life.</title>
</head>
<body>
<!-- ****top bar for 旅客**** -->

<div class="col-md-12" id="topBar">
	<div class="row" class="navbar navbar-default" role="navigation">
	<div class="col-md-3" style="margin-top:20px;">
		<a href="<%=request.getContextPath()%>/HomePage.jsp"><img src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
	</div>
	<div class="col-md-9" id="top_menu" style="margin-top:20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a href="<%=request.getContextPath()%>/tool/SearchController?checkin=1971-1-1&checkout=1971-1-1&guests=1&location" id=""><i class="fa fa-home fa-2x"></i>找民宿</a></li>										
					 <li><a href="<%=request.getContextPath()%>/view/allViewInfo.jsp" target="iframe" ><i class="fa fa-picture-o fa-2x"></i>找景點</a></li>
					  <li><a href="<%=request.getContextPath()%>/tenant/tenant_One.jsp" id=""><i class="fa fa-user fa-2x"></i>個人資料管理</a></li>
					 	<li class="dropdown" id="traceDropDown" >
				        <a class="dropdown-toggle" data-toggle="dropdown" href="<%=request.getContextPath()%>/view/tenantViewList.jsp"  >
				        <i class="fa fa-heart fa-2x" ></i>收藏
				        <span class="caret" ></span></a>
				        <ul class="dropdown-menu" id="traceListDisplay" >

				        </ul>
				   </li>
					  <c:if test="${empty tenantVO}">
					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-in fa-2x"></i>登入</a></li> 
					 </c:if>
					 <c:if test="${not empty tenantVO}">
					 	<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i class="fa fa-sign-out fa-2x"></i>登出</a></li>
					 </c:if>				 	
				</ul>
     </div>				

	</div>
</div>	
	

<div class='container'  >
		<div class='col-md-4'>
			<div class='col-md-2'></div>
			<div class='col-md-10' >
				<div class="row"  style="margin-top:35px;">
					
					<div class='col-md-12' >
						<img class=" img-responsive img-rounded img-thumbnail" src="<%=request.getContextPath()%>/ImageReader?tenantNo=${tenantVO.tenantNo}" class="user-image" >
		                <form method='post' action='<%=request.getContextPath()%>/tenant.do' enctype="multipart/form-data">
						 	<input type="file" name="tenantPic" size="45">
						    <input type='hidden' name='action' value='update_pic'>
						    <input type='hidden' name='tenantNo' value='${tenantVO.tenantNo }'>
						    <input type='hidden' name='requestURL' value='<%=request.getServletPath()%>'>
						    <button type="submit">上傳</button>
					    </form>
		                <hr>
		                <h3 class="profile-username text-center"><b>${tenantVO.tenantName}</b></h3>
					</div>
					<table class="table">
					      <tr  class="info">
					        <td><b>電郵地址</b></td>
					      </tr>
					      <tr>
					        <td>${tenantVO.tenantMail}</td>
					      </tr>
					      <tr  class="info">
					        <td><b>註冊日期</b></td>
					      </tr>
					      <tr>
					        <td>${tenantVO.registerDate}</td>
					      </tr>
					      <tr>
					      	<td><button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">修改個人資料</button></td>
					      </tr>
					      
					 </table>
			  		
			  
				</div>	
			</div>
		</div>
			<div class='col-md-8'>
				<table class="table table-striped" style="margin-top:35px;">
					<tr style="text-align:center;" class="info">
						<th colspan="2">旅客資訊</th>
					</tr>
					<tr>
				        <td colspan="2">旅遊統計</td>
				    </tr>
				    <tr>
				      	<td>註冊天數 ：</td>
				      	<td><%=tenantService.toDate(tenantNo) %></td>
				    </tr>
				    <tr>
				        <td>本月旅行次數：</td>
				        <td><%=tenantService.travelThisMonth(tenantNo) %></td>
				        
				   	</tr>
				   	<tr>
				   		<td>平均獲得房東評分</td>
				   		<td><%=tenantService.getScoreAvg(tenantNo) %></td>
				   	</tr>
				 </table>
				 
				 <table class="table table-striped" >
				
				<tr align='center' valign='middle'  style="text-align:center; ">
		                      <th style="font-size:15px; color:black ">訂單編號</th>
		                      <th style="font-size:15px; color:black ">訂單狀態</th>
		                      <th style="font-size:15px; color:black ">民宿名稱</th>
		                      <th style="font-size:15px; color:black ">訂單日期</th>
		                      <th style="font-size:15px; color:black ">入住日期</th>
		                      <th style="font-size:15px; color:black ">退房日期</th>
		                      <th style="font-size:15px; color:black ">總價</th>
		        </tr>
		                <c:forEach var="hostelOrderVO" items="${hostelord}" >
							<tr align='center' valign='middle' class="order">
								
								<td style="font-size:12px; color:black ">${hostelOrderVO.hostelOrderNo}</td>
								<td style="font-size:12px; color:black ">${hostelOrderVO.orderState}</td>
								<td style="font-size:12px; color:black ">${hostelService.getOneHostel(hostelOrderVO.hostelNo).hostelName}</td>
								<td style="font-size:12px; color:black ">${hostelOrderVO.hostelOrderDate}</td>
								<td style="font-size:12px; color:black ">${hodService.getAllByHostelOrderNo(hostelOrderVO.hostelOrderNo).get(0).checkInDate }</td>
								<td style="font-size:12px; color:black ">${hodService.getAllByHostelOrderNo(hostelOrderVO.hostelOrderNo).get(0).checkOutDate }</td>
								<%	HostelOrderVO hostelOrderVO = (HostelOrderVO) pageContext.getAttribute("hostelOrderVO");
									List<HostelOrderDetailVO> hodlist = hodService.getAllByHostelOrderNo(hostelOrderVO.getHostelOrderNo());
									int total = 0;
									for(int i = 0 ; i< hodlist.size(); i++) {
										total = total + hodlist.get(i).getTotalPrice();
									}
								%>
								<td style="font-size:15px; color:black "><%=total %></td>
																			
							</tr>
							<tr class="detail" hidden>
							<td colspan="7" style="font-size:15px; color:black ">
							<c:forEach var="hostelOrderDetailVO" items="${hodService.getAllByHostelOrderNo(hostelOrderVO.hostelOrderNo) }">
								<span style="margin-right:5px;">
								${rtService.getOneRoomType(hostelOrderDetailVO.roomTypeNo).roomTypeName }-
								${hostelOrderDetailVO.roomQuantity }間-
								${hostelOrderDetailVO.totalPrice }
								</span>
							</c:forEach>
							</td>
							</tr>
							<tr class="detail"  hidden>
							
							<td colspan='7' style="border: 1px solid #000;">
								<c:choose>
									<c:when test="${empty hostelOrderVO.hostelComment }">
										<form action="<%=request.getContextPath() %>/CommentServlet">
											<input type="text" name="comment" placeholder="寫下你的感想吧！" style="width:100%"  required>
											<input type="hidden" name="hostelOrderNo" value="${hostelOrderVO.hostelOrderNo}">
											<div class="form-group">
											  <label for="sel1"><h6><b><i class='fa fa-star' Style="color:#AAAAFF"></i>打個分數:</b></h6></label>
											  <select class="form-control" id="sel1" name="hostelScore">
											    <option>1</option>
											    <option>2</option>
											    <option>3</option>
											    <option>4</option>
											    <option>5</option>
											    <option>6</option>
											    <option>7</option>
											    <option>8</option>
											    <option>9</option>
											    <option>10</option>
											  </select>
											</div>
											<button class="btn btn-info">評價</button>
										</form>
										
									</c:when>
									<c:otherwise>
										<h4>對民宿的評價：${hostelOrderVO.hostelComment } <i class='fa fa-star' Style="color:#AAAAFF"></i>${hostelOrderVO.hostelScore }</h4>
										
									</c:otherwise>
								</c:choose>
								
							</td>
							</tr>
						</c:forEach>
		            </table>
			</div>
		</div>
		
		
		
	<!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog modal-lg">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">修改個人資料</h4>
	          
	        </div>
	        <div class="modal-body">
	        	
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/tenant.do" name="form1">
						電郵地址：${tenantVO.tenantMail}<br>
						<div></div><br>
						密碼:<input type="TEXT" name="tenantPassword" size="45"	value="${tenantVO.tenantPassword}" /><br>
						<div></div><br>
						姓名:<input type="TEXT" name="tenantName" size="45"	value="${tenantVO.tenantName}" /><br>
						<div></div><br>
						性別:<input type="TEXT" name="tenantSex" size="45" value="${tenantVO.tenantSex}" /><br>
						<div></div><br>
						地址:<input type="TEXT" name="tenantAddress" size="45" value="${tenantVO.tenantAddress}" /><br>
						<div></div><br>
						電話:<input type="TEXT" name="tenantPhone" size="45" value="${tenantVO.tenantPhone}" /><br>
						<div></div><br>
							<input type="hidden" name="tenantNo" value="${tenantVO.tenantNo}">
							<input type="hidden" name="tenantMail" value="${tenantVO.tenantMail}">
							<input type='hidden' name='action' value="update">
							<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
						<button type="submit" class="btn btn-danger" >修改</button>
					</FORM>		
				</div>
	        </div>
	      </div>
	    </div>
	<!-- Modal -->
	


<!-- //------------footer-------------- -->
	
<div class="col-md-12" id="footer01" style="margin-top:100px;">
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