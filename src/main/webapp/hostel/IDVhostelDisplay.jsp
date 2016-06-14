<%@page import="com.tenant.model.TenantService"%>
<%@page import="com.hostelOrder.model.HostelOrderVO"%>
<%@page import="com.hostelOrder.model.HostelOrderService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.dealer.model.*" %>
<%@ page import="com.hostel.model.*" %>
<%@ page import="com.roomType.model.*"%>
<%@ page import="com.facility.model.*"%>
<%@ page import="com.roomTypePic.model.*"%>
<%@ page import="com.hostelPic.model.*" %>
<%@ page import="com.hostelNews.model.*" %>
<%@ page import="java.util.*"%>

<jsp:useBean id="facilityService" scope="page" class="com.facility.model.FacilityService" />

<!DOCTYPE html>
<html>
<head>
<%
int hostelNo = new Integer(request.getParameter("hostelNo"));
HostelService hostelServ=new HostelService();
HostelVO hostelVO=hostelServ.getOneHostel(hostelNo);
pageContext.setAttribute("hostelVO", hostelVO);




%>
    
<!-- ****FavIcon**** -->
<link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/Logo_S.png" />

<!-- ****BootStrap Icon**** -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

<!-- ****Jquery**** -->  
<script src="http://code.jquery.com/jquery.js"></script>  
<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- Jquery Toggle -->

<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
<!-- ****BootStrap**** -->
<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/hostelManager.css">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<!-- ****LightBox**** -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-image-gallery.css">
<script src="<%=request.getContextPath()%>/js/bootstrap-image-gallery.js"></script>
<link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/hostel/style/IDVhostelDisplay.css">
<title>eChioce| ${hostelVO.hostelName} | choose your life.</title>
	
	<script>
// 		$(document).ready(function(){
// 			$('.hostelPics').ekkoLightbox(options);	
// 		})
// 		$(document).delegate('*[data-toggle="lightbox"]', 'click', function(event) {
// 		    event.preventDefault();
// 		    $(this).ekkoLightbox();
// 		}); 

	
	</script>
	<style type="text/css">
	
	.cartListClass {
	   border-style: solid;
    border-width: 1px;
    padding:2px;
}
	
	</style>
<!-- NavBar	 -->
<script type="text/javascript">
  $(function(){
	  $("#tab_1").click(function(){
		  $("#iframe").attr("src","<%=request.getContextPath()%>/management_hostel.jsp");
	  });
	  
	  $("[id^='traceUp']").click(function(){
			var lastIdx=$(this).attr('id').lastIndexOf("-")+1;
			var hostelNo=$(this).attr('id').substring(lastIdx);
	    	$.ajax({
				 type:"GET",
				 url:"<%=request.getContextPath()%>/HostelTraceAjax",
				 data:{action:"addTrace",hostelNo:hostelNo},
				 dataType:"json",
				 success:function (data){
						traceListDisplay(data);
					
			     },
	             error:function(){}
            })
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
	             error:function(){}
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
		             error:function(){}
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
	             error:function(){}
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
<!-- NavBar	 -->	
	
	
</head>

<body>

<!-- 	header -->
	<%


		
		
// 		Integer roomTypeNo=dealerService.
		
		HostelService hostelService=new HostelService();
		Set<RoomTypeVO> roomTypeSet=hostelService.getRoomTypesByHostelNo(hostelNo);
		Set<HostelPicVO> hostelPicSet=hostelService.getHostelPicsByHostelNo(hostelNo);
		List<HostelNewsVO> newsList=hostelService.getNewsByHostelNo(hostelNo);
		
		RoomTypeService roomTypeService=new RoomTypeService();
		List<List<RoomTypePicVO>> PicList=new ArrayList<List<RoomTypePicVO>>();
		
		HostelOrderService hostelOrderServ = new HostelOrderService();
		List<HostelOrderVO> hostelOrderList= hostelOrderServ.getAllByHostelNo(hostelVO.getHostelNo());
		Collections.sort(hostelOrderList, new Comparator<HostelOrderVO>(){
											public int compare(HostelOrderVO o1,HostelOrderVO o2){
											
												return o2.getHostelOrderDate().compareTo(o1.getHostelOrderDate());
											}
						});
		pageContext.setAttribute("hostelOrderList", hostelOrderList);
		TenantService tenantServ=new TenantService();
		pageContext.setAttribute("tenantServ", tenantServ);
		
// 		FacilityService facilityService=new FacilityService();
// 		pageContext.setAttribute("facilityService", facilityService);
// 		System.out.println(dealerNo);
// 		System.out.println(hostelNo);
// 		System.out.println(set.size());
		for(RoomTypeVO roomTypeVO:roomTypeSet){
			PicList.add(roomTypeService.getPicByRoomTypeNo(roomTypeVO.getRoomTypeNo()));
// 			System.out.println(roomTypeVO.getRoomTypeNo());
// 			System.out.println(roomTypeService.getPicByRoomTypeNo(roomTypeVO.getRoomTypeNo()));
		}
// 		session.setAttribute("hostelVO", hostelVO);
// 		${hostelVO.getHostelNo()}
	%>

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
	
	
<!-- 	hostel img -->	

<div class="col-md-12" >
	<div class="row" id="cover"
		style="background-image:url('<%=request.getContextPath()%>/ImageReader?hostelNo=<%=hostelVO.getHostelNo()%>&hostelPicture=<%=hostelVO.getHostelPicture()%>');"	
	>	
		
	</div>

</div>	
<!-- 	hostel img -->

<div class="col-md-12" style="height:60px;margin-top:15px">
	<div class="row">
		    <button  type="button" class="btn btn-default btn-lg" id="traceUp-${hostelVO.hostelNo}">
       <span id="viewCollectImg" class="glyphicon glyphicon-heart" style="color:red"></span>加到收藏清單</button>
		
			
	</div>
</div>


<!-- 		hostel dealer name	Content -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<div class="col-md-12">
			<div class="row">
			<div class="col-md-4">
				<i class="fa fa-home fa-3x" style="color:#565a5c;"></i>
				<span style="font-size:37px;font-weight:600;color:#565a5c;letter-spacing:4px"><%=hostelVO.getHostelName()%></span>
			</div>
			<div class="col-md-2">
			</div>
			<div class="col-md-3" style="margin-top:20px;">
				<span style="font-size:15px"><%=hostelVO.getHostelAddress()%></span>
			</div>
			<div class="col-md-3" style="margin-top:20px;">
				<span style="font-size:15px"><%=hostelVO.getHostelPhone()%></span>
			</div>			
			</div>
		</div>
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>
<!-- 		hostel dealer name	Content -->

<!-- 空白 -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
	<p style="height:0;overflow:hiddne;border-top:1px solid #dcdcdc"></p>
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>
<!-- 空白 -->

<!------about content  ---->
<div class="col-md-12">
	<div class="row">
		<div class="col-md-2">
		</div>
			
<!--    content -->

		<div class="col-md-8">
			<div class="col-md-12">
			<div class="row">
				<div class="col-md-3">
					<p style="font-weight:560;font-size:22px;color:#6999D1;letter-spacing:20px">簡介</p>
				</div>			
				<div class="col-md-9" style="font-size:16px;">	
					<%=hostelVO.getHostelContent()%>
				</div>
			</div>
					
			</div>	
				
		</div>
				
		<div class="col-md-2">
		</div>		
	</div>
</div>
<!------about content  ---->



<!------news content  ---->
<div class="col-md-12">
	<div class="row">
		<div class="col-md-2">
		</div>
			
<!--    content -->

		<div class="col-md-8" >
			<div class="col-md-12">	
			<div class="row">		
				<div class="col-md-3">
					<p style="font-weight:560;font-size:22px;color:#6999D1;letter-spacing:20px">公告</p>
				</div>			
				<div class="col-md-9">	
						<c:forEach var="hostelNewsVO" items="<%=newsList %>">
							<div style="font-size:16px;">
								<span style="float:right;">${hostelNewsVO.newsContent}</span>												
								<span> ▪  ${hostelNewsVO.updateDate }</span>
														
							</div>
						</c:forEach>
				</div>
			</div>			
			</div>						
		</div>			
		<div class="col-md-2">
		</div>
	</div>
</div>
<!-- content -->

<!------news content  ---->

<div class="col-md-12" style="height:20px">
	<div class="row">
	</div>
</div>

<!------ facility  ---->
<div class="col-md-12">
	<div class="row" >
		<div class="col-md-2">
		</div>
		<div class="col-md-8" style="background-color:#6999D1;height:34px;">		
			<p style="font-size:22px;color:#ffffff;text-align:center;letter-spacing:20px">提供設施</p>
		</div>
		</div>
		<div class="col-md-2">
	</div>
</div>
<!-- content -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8" style="border:0.5px solid #f7f8f8;">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-1">
				</div>
				<div class="col-md-3">
				<p>
					<p style="font-size:14px;"><i class="fa fa-cutlery fa-2x" title="有廚房"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						廚房</p>
					<p style="font-size:14px;"><i class="fa fa-coffee fa-2x" title="有供應早餐"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						有供應早餐</p>				
				</div>	
				<div class="col-md-3">	
				<p>
					<p style="font-size:14px;"><i class="fa fa-tv fa-2x" title="電視"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						電視</p>
					<p style="font-size:14px;"><i class="fa fa-wifi fa-2x" title="wifi"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						wifi</p>				
				</div>
				<div class="col-md-3">
				<p>
					<p style="font-size:14px;"><i class="fa fa-bicycle fa-2x" title="腳踏車"></i>&nbsp;&nbsp;&nbsp;&nbsp;
						腳踏車</p>	
				</div>
				<div class="col-md-2">
				</div>	
			</div>			
		</div>
	<div class="col-md-2">
	</div>
	</div>
	</div>
</div>

<!------ content  ---->

<div class="col-md-12" style="height:10px;">
	<div class="row">

	</div>
</div>
<!------ facility  ---->



<!------ roomType   ---->

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


<div class="col-md-12" style="height:20px">
<div class="row">
</div>
</div>
<!-- content -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8" id="roomTypeDiv">
	<c:forEach var="roomTypeVO" items="<%=roomTypeSet%>" varStatus="s">
		<div class="col-md-12">
			<div class="row" style="height:225px;">			
				<div class="col-md-5">				
					<img style="width:300px;margin-top:10px;"
					src="<%=request.getContextPath()%>/ImageReader?roomTypeNo=${roomTypeVO.roomTypeNo}">					
				</div>
				<div class="col-md-5" style="margin-top:10px;font-size:14px">
				<p>
				  <p>房型 : ${roomTypeVO.roomTypeName}</p>
				  <p>人數 : ${roomTypeVO.roomTypeContain}人</p>
				  <p>價格 : $${roomTypeVO.roomTypePrice}/天</p>
				  <p>描述 : ${roomTypeVO.roomTypeContent}</p>
				</div>
				<div class="col-md-2" style="margin-top:90px">
					<input type="button" class="btn btn-info btn-lg" value="我要訂房"  id="btn-${roomTypeVO.roomTypeNo}"   >
				</div>	
			  <div class="col-md-12">
			  
 				   <div id="effect-${roomTypeVO.roomTypeNo}" class="ui-widget-content ui-corner-all">
 				   <h3 class="ui-widget-header ui-corner-all">即將下訂：${roomTypeVO.roomTypeName}</h3>
										      <input
			      id="checkin-${roomTypeVO.roomTypeNo}"
			      type="text"
			      class="checkin input-large input-contrast"
			      name="checkin"
			      placeholder="入住日期" 
			      class="date"
			      readonly="true" 
			      			/>
			      
			              
			      <input
			      id="checkout-${roomTypeVO.roomTypeNo}"
			      type="text"
			      class="checkout input-large input-contrast"
			      name="checkout"
			      placeholder="退房日期" 
			      			class="date"
			      			readonly="true" 
			  				    /> 
			      <span id="showMaxQty-${roomTypeVO.roomTypeNo}"></span>
			      <p>
 							 <label for="roomNo-${roomTypeVO.roomTypeNo}">要訂幾間:</label>
 							 <input type="number" id="roomQty-${roomTypeVO.roomTypeNo}" min = "0" max = "10"  step = "1" value="1">
 							 <input type="button" class="btn btn-info btn-lg" value="下訂"  id="addOrderbtn-${roomTypeVO.roomTypeNo}"   >
							</p>
			      
			      
  			</div>		
			</div>
		</div>

</div>
	</c:forEach>	
				  <div class="col-md-12">
			  
 				   <div id="effect-cart" class="ui-widget-content ui-corner-all">
 				   <h3 class="ui-widget-header ui-corner-all">訂房明細</h3>
			<form action="<%=request.getContextPath()%>/controller/HostelOrderServlet" >
			<table id="cartListTable" class="cartListClass" >

			</table>
			<input type="hidden" value="${hostelVO.hostelNo}" name="hostelNo">
			<b>付款方式：</b>
			<select name="paymentWay">
			<option>ATM</option>
			<option>VISA</option>
			</select>
			<input type="submit" value="送出訂單">
		
  			</div>		
			</div>
			<div class="col-md-12">
			  
 				   <div id="effect-cart-check" class="ui-widget-content ui-corner-all" title="訂單確認">
 				  
		
  			</div>		
			</div>
	<div class="col-md-2">
	</div>

	</div>
</div>

<!------ content   ---->
	
<!------ roomType   ---->



<!------ roomTypePic   -- -->
<div class="col-md-12">
	<div class="row">
	<div class="col-md-3">
	</div>
	<div class="col-md-6">
		<c:forEach var="hostelPicVO" items="<%=hostelPicSet%>">
				<span><a href="<%=request.getContextPath()%>/ImageReader?hostelPicNo=${hostelPicVO.hostelPicNo}" data-gallery>
				<img style="height:100px;" src="<%=request.getContextPath()%>/ImageReader?hostelPicNo=${hostelPicVO.hostelPicNo}">
				</a></span>
		</c:forEach>
		<c:forEach var="list" items="<%=PicList %>">
				<c:forEach var="roomTypePicVO" items="${list}">
				<a href="<%=request.getContextPath()%>/ImageReader?roomTypePicNo=${roomTypePicVO.roomTypePicNo}" data-gallery>
				<img id="roomTypePic" style="height:100px"
				src="<%=request.getContextPath()%>/ImageReader?roomTypePicNo=${roomTypePicVO.roomTypePicNo}&roomTypePhoto=${roomTypePicVO.roomTypePhoto}">
				</a>	
				</c:forEach>
		</c:forEach>
	</div>
	<div class="col-md-3">
	</div>
	</div>
</div>
	
<!------ roomTypePic   ---->

<div id="blueimp-gallery" class="blueimp-gallery">
    <!-- The container for the modal slides -->
    <div class="slides"></div>
    <!-- Controls for the borderless lightbox -->
    <h3 class="title"></h3>
    <a class="prev" style="font-size:35px;padding-top:10px;">&lt</a>
    <a class="next" style="font-size:35px;padding-top:10px;">&gt</a>
    <a class="close">X</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
    <!-- The modal dialog, which will be used to wrap the lightbox content -->
    <div class="modal fade">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body next"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left prev">
                        <i class="glyphicon glyphicon-chevron-left"></i>
                        Previous
                    </button>
                    <button type="button" class="btn btn-primary next">
                        Next
                        <i class="glyphicon glyphicon-chevron-right"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<!------ roomTypePic   ---->

<div class="col-md-12" style="height:30px;">
	<div class="row">
	</div>
</div>


<!-- google map -->

<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">	
	</div>	
	<div class="col-md-8" style="background-color:#6999D1;height:34px;">
		<p style="font-size:22px;color:#ffffff;text-align:center;letter-spacing:20px">地理位置</p>	
	</div>
	<div class="col-md-2">	
	</div>
	</div>
</div>
<div class="col-md-12">
	<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8"  style="height:400px;border:0.5px solid #f7f8f8;" id="message">
	</div>
	<div class="col-md-2">
	</div>
	</div>
</div>

<!-- google map -->

<div class="col-md-12" style="height:30px;">
	<div class="row">
	</div>
</div>
<!-- google map -->





<!-- 旅客評論  標題-->
<div class="col-md-12">
	<div class="row">
		<div class="col-md-2">
		</div>
			
<!--    content -->

		<div class="col-md-8" >
			<div class="col-md-12">	
			<div class="row">		
				<div class="col-md-3">
					<p style="font-weight:560;font-size:22px;color:#6999D1;letter-spacing:20px">旅客評論</p>
				</div>			
				<div class="col-md-9">	
				</div>
			</div>			
			</div>									
		</div>	
		<div class="col-md-2">
		</div>
	</div>
</div>

<!--**** 旅客評論 ****-->

<!-- 空白 -->
<div class="col-md-12" style="height:30px;">
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
		<p style="height:0;overflow:hiddne;border-top:1px solid #dcdcdc;"></p>
		</div>
		<div class="col-md-2">
		</div>
	</div>
	
</div>
<!-- 空白 -->
<c:forEach var="hostelOrderVO" items="${hostelOrderList}">
<c:if test="${hostelOrderVO.hostelScore!=0 and hostelOrderVO.hostelComment!=null}">



<div class="col-md-12">
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
		 	<div class="col-md-12">
		 		<div class="row">
			 		<div class="col-md-3">
				 		<p>	<img   style="width:200px ;max-height:200px;" src="<%=request.getContextPath()%>/tool/GetPhoto?tableName=tenant&pkNo=${hostelOrderVO.tenantNo}"></p>
				 		<p style="text-align:center;">${tenantServ.getOneTenant(hostelOrderVO.tenantNo).tenantName}</p>
			 		</div>
			 		<div class="col-md-9">
			 			<div class="col-md-12">
			 				<div class="row">
			 				<div class="col-md-3" style="margin-top:10px;font-size:32px;font-weight:900;color:#1e90ff">
				 			<p>${hostelOrderVO.hostelScore}</p>
				 			</div>
				 			<div class="col-md-6" style="margin-top:15px;font-size:16px">
				 			<p>${hostelOrderVO.hostelComment}</p>
				 			</div>
				 			<div class="col-md-3" style="margin-top:40px;">
				 			<p>${hostelOrderVO.hostelOrderDate}</p>
				 			</div>
				 			
			 				</div>			 				
			 			</div>		 			
			 		</div>
		 		</div>
		 	</div>		 			
		</div>
		<div class="col-md-2">
		</div>
	</div>
</div>



</c:if>
</c:forEach>
<!-- 橫線	 -->
<div class="col-md-12" style="height:30px;">
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
		<p style="height:0;overflow:hiddne;border-top:1px solid #dcdcdc;"></p>
		</div>
		<div class="col-md-2">
		</div>
	</div>
	
</div>
<!-- 橫線	 -->
<!--**** 旅客評論 ****-->


<!-- 空白 -->
<div class="col-md-12" style="height:50px;">
	<div class="row">
	</div>	
</div>
<!-- 空白 -->







<!--**** footer ****-->

<div class="col-md-12" id="footer01">
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

<div class="col-md-12" id="footer02" style="height:200px;margin-top:-1px">
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
	
</body>
</html>