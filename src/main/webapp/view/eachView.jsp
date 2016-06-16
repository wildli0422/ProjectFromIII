<%@page import="com.caculateDistance.controller.caculDistanceServlet"%>
<%@page import="com.viewphoto.model.ViewPhotoVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.viewinfo.model.*"%>
<%@ page import="com.hostel.model.*"%>
<%@ page import="com.viewphoto.model.*"%>
<%@ page import="com.viewlist.model.*"%>
<%@ page import="com.tenant.model.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <jsp:useBean id="listViewPhoto_ByViewno" scope="request" --%>
<%-- 	type="java.util.Set" /> --%>




<%
	//取房客編號
TenantVO tenantVO = (TenantVO)session.getAttribute("tenantVO");
Integer tenantNo = tenantVO.getTenantNo();
pageContext.setAttribute("tenantNo", tenantNo);

//取得個別房客景點清單收藏資料
ViewListService viewlistSvc = new ViewListService();
Set<ViewListVO> viewlistlist = viewlistSvc
		.getViewListByTenantno(tenantNo);
pageContext.setAttribute("viewlistVO", viewlistlist);


//取景點編號	
Integer viewno = new Integer(request.getParameter("viewno"));
pageContext.setAttribute("viewno", viewno);

//取該景點的景點資訊
ViewInfoService viewInfoService = new ViewInfoService();
ViewInfoVO viewinfoVO = viewInfoService.getOneViewInfo(viewno);
pageContext.setAttribute("viewinfoVO", viewinfoVO);
//pageContext.setAttribute("tenantNo", tenantNo);

//取該景點所有的景點照片
ViewInfoService viewInfoService1 = new ViewInfoService();
Set<ViewPhotoVO> viewphotoSetVO = viewInfoService1.getViewPhotoByViewno(viewno);
pageContext.setAttribute("set", viewphotoSetVO);

//取所有民宿的資料
HostelService hostelService = new HostelService();
List<HostelVO> hostellistVO = hostelService.getAll();
List<HostelVO> kiloRangeList = new ArrayList<HostelVO>();

for (HostelVO hostelVO : hostellistVO) {
	HostelVO newHostelVO=new HostelVO();
	Double distance;
	distance = caculDistanceServlet.GetDistance(
			viewinfoVO.getViewlon(), viewinfoVO.getViewlat(),
			hostelVO.getHostelLon(), hostelVO.getHostelLat());
	
	
	
	if(distance <=20 )  
	{
		newHostelVO.setHostelNo(hostelVO.getHostelNo());
		newHostelVO.setHostelName(hostelVO.getHostelName());
		newHostelVO.setHostelAddress(hostelVO.getHostelAddress());
		newHostelVO.setHostelLat(hostelVO.getHostelLat());
		newHostelVO.setHostelLon(hostelVO.getHostelLon());
		System.out.println("hostel Name in 50kilo "+hostelVO.getHostelName());
		kiloRangeList.add(newHostelVO);
		
	}
	
	
	System.out.println("lon:" + newHostelVO.getHostelName());
	
	System.out.println(distance);

}



pageContext.setAttribute("hostelVO", kiloRangeList);

int tenantHasViewlist = 0;

if (viewlistlist.isEmpty()) {
	tenantHasViewlist = 0;
} else {
	tenantHasViewlist++;
}
%>


<html>
<head>
<link href="<%=request.getContextPath()%>/view/style/css/reset.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/eachView.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/NavBar_top.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="<%=request.getContextPath()%>/images/Logo_S.png" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/view/style/js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type='text/javascript'
	src='http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.js'></script>


<style>
body {
	background-image:
		url('<%=request.getContextPath()%>/view/style/images/bkpic1.jpg');
	background-attachment: fixed;
	background-position: center;
	background-size: cover;
}
/* img { margin: 5px } */
</style>



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

			var latlng = new google.maps.LatLng('${viewinfoVO.viewlat}','${viewinfoVO.viewlon}'); 
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
	            icon: createMarkerIcon('${viewinfoVO.viewname}', {
	                bgColor: "#228b22"
	            }),
	            map: map

	        });

			var marker=[];

	 		var i=0;
	 		<c:forEach var="hostelVO1" items="${hostelVO}">

	 			marker[i] = new google.maps.Marker({
	 			position: new google.maps.LatLng('${hostelVO1.hostelLat}', '${hostelVO1.hostelLon}'),
	 			map: map,
	 			icon: createMarkerIcon('${hostelVO1.hostelName}', {
	                bgColor: "#dc143c"
	            }),
	            map: map

	 		});

	 		</c:forEach>	
		}
		window.addEventListener('load', doFirst, false);
</script>

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

<title>each_echoice─景點.jsp</title>
</head>

<body>
	<!-- ****BootStrap Icon**** -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">


	<!-- NavBar	 -->








	<!-- NavBar	 -->


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
	<!-- //--------------viewName-------------------->
	<div id="viewName" class="col-md-12"
		style="background-color: #B0E0E6; height: 180px; opacity: 0.6;">
		<div class="col-md-5"></div>

		<div id=viewList style="float: left; margin-top: 50px;">

			<%
				if (tenantHasViewlist == 0) { //判斷房客景點清單是否為空
			%>

			<form name="addViewList"
				action="<%=request.getContextPath()%>/viewlist/viewlist.do"
				method="POST">
				<input type="hidden" name="tenantNo" value="<%=tenantNo%>">
				<input type="hidden" name="viewno"
					value="<%=viewinfoVO.getViewno()%>"> <input type="hidden"
					name="action" value="eachViewInsert"> <input type="hidden"
					name="requestURL" value="<%=request.getServletPath()%>">
				<button id="viewCollect" type="submit"
					class="btn btn-default btn-lg" style="height: 60px; width: 60px;">
					<span id="viewCollectImg" style="font-size: 30px;"
						class="glyphicon glyphicon-heart"></span>
				</button>
			</form>


			<%
				} else {

					int i = 0; //景點是否被收藏之標記

					for (ViewListVO viewlistVO : viewlistlist) {
						if (viewlistVO.getViewno().equals(viewinfoVO.getViewno())) {
							i++;
							break;
						}//if				
					} //foreach viewlistVO  end

					for (ViewListVO viewlistVO : viewlistlist) {
						if (i == 1) {
							System.out.println("1");
			%>


			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/viewlist/viewlist.do">
				<button id="viewCollect" type="submit"
					class="btn btn-default btn-lg" style="height: 60px; width: 60px;">
					<span id="viewCollectImg" class="glyphicon glyphicon-heart"
						style="color: red; font-size: 30px;"></span>
				</button>
				<input type="hidden" name="viewlistno"
					value="<%=viewlistVO.getViewlistno()%>">
				<!--送出本網頁的路徑給Controller-->
				<input type="hidden" name="action" value="eachViewDelete"> <input
					type="hidden" name="requestURL"
					value="<%=request.getServletPath()%>"> <input type="hidden"
					name="tenantNo" value="<%=viewlistVO.getTenantNo()%>"> <input
					type="hidden" name="viewno" value="<%=viewlistVO.getViewno()%>">
			</FORM>


			<%
				} else {
			%>
			<form name="addViewList"
				action="<%=request.getContextPath()%>/viewlist/viewlist.do"
				method="POST">
				<input type="hidden" name="tenantNo" value="<%=tenantNo%>">
				<input type="hidden" name="viewno"
					value="<%=viewinfoVO.getViewno()%>"> <input type="hidden"
					name="action" value="eachViewInsert"> <input type="hidden"
					name="requestURL" value="<%=request.getServletPath()%>">
				<button id="viewCollect" type="submit"
					class="btn btn-default btn-lg" style="height: 60px; width: 60px;">
					<span id="viewCollectImg" style="font-size: 30px;"
						class="glyphicon glyphicon-heart"></span>
				</button>
			</form>

			<%
				}
						break;
					}// if collect or not

				}////判斷房客景點清單是否為空
			%>

		</div>

		<div id=viewTitle style="float: left; margin-left: 10px;">
			<h1 style="color: black; margin-top: 50px; font-weight: bolder">${viewinfoVO.viewname}</h1>
		</div>
	</div>

	<!-- //--------------viewPic-------------------->

	<div id="picContainer" class="col-md-12" style="margin-top: 20px;">
		<div class="row">
			</br> </br>
			<!--pic--->
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6" id="searchList" style="">
					<div id="carousel-example-generic" class="carousel slide"
						data-ride="carousel">
						<div class="col-md-3"></div>
						<!-- Indicators -->

						<ol class="carousel-indicators">
							<%
								int i = 0;
							%>
							<c:forEach var="viewphotoVO" items="${set}">
								<li data-target="#carousel-example-generic"
									data-slide-to="<%=i%>" class=<%=i == 0 ? "active" : ""%>></li>
								<%=i++%>
							</c:forEach>
						</ol>
						<!-- Wrapper for slides -->
						<div class="carousel-inner" role="listbox">
							<%
								int j = 0;
							%>
							<c:forEach var="viewphotoVO" items="${set}">
								<%
									if (j == 0) {
								%>
								<div class="item active">
									<img id="viewPic"
										src="<%= request.getContextPath()%>/viewphoto/readviewphoto.do?viewpicno=${viewphotoVO.viewpicno}">
									<div class="carousel-caption"></div>
								</div>
								<%
									} else {
								%>
								<div class="item">
									<img id="viewPic"
										src="<%= request.getContextPath()%>/viewphoto/readviewphoto.do?viewpicno=${viewphotoVO.viewpicno}">
									<div class="carousel-caption"></div>
								</div>
								<%
									}
										j++;
								%>
							</c:forEach>
						</div>
						<!-- Controls -->
						<a class="left carousel-control" href="#carousel-example-generic"
							role="button" data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control"
							href="#carousel-example-generic" role="button" data-slide="next">
							<span class="glyphicon glyphicon-chevron-right"
							aria-hidden="true"></span> <span class="sr-only">Next</span>
						</a>
					</div>
				</div>
				</br> </br>
			</div>
			<!--tab--->

			<!-- //--------------viewAddress-------------------->

			<div id="viewaddress" class=" row col-md-12">
				<div id class="col-md-2"></div>
				<div id="des" class="col-md-8"
					style="margin-top: 50px; height: 80px; background-color: #6999d1; border-radius: 10px 10px 0px 0px;">
					<h2
						style="text-align: center; font-size: 35px; font-weight: bolder; letter-spacing: 10px;color:white">位址資訊
					</h2>
				</div>
			</div>

			<!---------------------Address------------------>
			<div id="content" class=" row col-md-12" style="">
				<div id class="col-md-2"></div>
				<div id="des" class="col-md-8"
					style="border: 1px solid cornflowerblue; border-radius: 0px 0px 10px 10px; margin-top: height: 600px; background-color: white;">
					<div class="col-md-7">
						<p style="margin-top: 30px; letter-spacing: 2px; font-size: 24px;">地址：&nbsp;${viewinfoVO.viewaddress}
						</p>
						<p style="margin-top: 30px; letter-spacing: 2px; font-size: 24px;">電話：&nbsp;${viewinfoVO.viewphone}
						</p>
					</div>

					<div class="col-md-5">
						<p style="margin-top: 30px; letter-spacing: 2px; font-size: 24px;">管理單位：&nbsp;${viewinfoVO.viewmanager}
						</p>
					</div>
				</div>
			</div>


			<!-- //--------------viewOpen-------------------->
			<div id="content" class=" row col-md-12">
				<div id class="col-md-2"></div>
				<div id="des" class="col-md-8"
					style="margin-top: 50px; height: 80px; background-color: #6999d1; border-radius: 10px 10px 0px 0px;">
					<h2
						style="text-align: center; font-size: 35px; font-weight: bolder; letter-spacing: 20px;color:white">開放資訊
					</h2>
				</div>
			</div>
			<!--------------------Open------------------------->

			<div id="content" class=" row col-md-12" style="">
				<div id class="col-md-2"></div>
				<div id="des" class="col-md-8"
					style="border: 1px solid cornflowerblue; margin-top: height: 600px; background-color: white; border-radius: 0px 0px 10px 10px;">

					<div class="col-md-7">
						<p style="margin-top: 30px; letter-spacing: 2px; font-size: 24px;">開放時間：<p>${viewinfoVO.viewopen}</p>
						</p>
						<p style="margin-top: 30px; letter-spacing: 2px; font-size: 24px;">門票資訊：
						</p>
						<c:forTokens var="viewticket1" items="${viewinfoVO.viewticket}"
							delims="；">

							<p style="margin-left: 0px; font-size: 20px;">◆&nbsp;${viewticket1}




							
							<p>
						</c:forTokens>
					</div>

					<div class="col-md-5">
						<p style="margin-top: 30px; letter-spacing: 2px; font-size: 24px;">景點設備：&nbsp;
						</p>
						<c:forTokens var="viewequi1" items="${viewinfoVO.viewequi}"
							delims="；">

							<p style="margin-left: 0px; font-size: 20px;">◆&nbsp;${viewequi1}




							
							<p>
						</c:forTokens>
					</div>


				</div>
			</div>

			<!-- //--------------viewContent-------------------->

			<div id="content" class=" row col-md-12">
				<div id class="col-md-2"></div>
				<div id="viewInfo" class="col-md-8"
					style="margin-top: 50px; height: 80px; background-color: #6999d1;">
					<h2
						style="text-align: center; font-size: 35px; font-weight: bolder; letter-spacing: 20px; border-radius: 10px 10px 0px 0px;color:white;">景點簡介
					</h2>
				</div>
			</div>
			<!--------------------content------------------------->
			<div id="content" class=" row col-md-12" style="">
				<div id class="col-md-2"></div>
				<div id="viewInfo" class="col-md-8"
					style="border: 1px solid cornflowerblue; margin-top: height: 600px; background-color: white; border-radius: 0px 0px 10px 10px;">

					<div class="col-md-12">



						<p
							style="margin-top: 30px; font-size: 20px; letter-spacing: 4px; line-height: 50px;">&nbsp;&nbsp;&nbsp;&nbsp;${viewinfoVO.viewcontent}




						
						<p>
					</div>



				</div>
			</div>



			<!-- //--------------viewMap-------------------->

			<div id="content" class=" row col-md-12">
				<div id class="col-md-2"></div>
				<div id="viewInfo" class="col-md-8"
					style="margin-top: 50px; height: 80px; background-color: #6999d1; border-radius: 10px 10px 0px 0px;">
					<h2
						style="text-align: center; font-size: 35px; font-weight: bolder; letter-spacing: 20px;color:white;">附近民宿
					</h2>
				</div>
			</div>
			<!--------------------map------------------------->
			<div id="content" class=" row col-md-12" style="">
				<div id class="col-md-2"></div>
				<div id="viewInfo" class="col-md-8"
					style="border: 1px solid cornflowerblue; margin-top: height: 600px; background-color: white; margin-bottom: 100px; border-radius: 0px 0px 10px 10px;">

					<div class="col-md-12">

						<div id="message" style="width: 1200px; height: 800px;"></div>

					</div>
				</div>
			</div>


				<!-- footer -->

				<div class="col-md-12" id="footer01">
					<div class="row">
						<div class="col-md-4"></div>
						<div class="col-md-4">
							<p>© eChoice, Inc.</p>
						</div>
						<div class="col-md-4"></div>
					</div>
				</div>

				<div class="col-md-12" id="footer02" style="height: 200px;">
					<div class="row">
						<div class="col-md-2"></div>
						<div class="col-md-4">

							<ul>
								<li>關於我們</li>
								<li>政策</li>
								<li>幫助</li>
								<li>Blog</li>
								<li>服務條款與私隱聲明</li>
							</ul>

						</div>
						<div class="col-md-2"></div>
						<div class="col-md-4">
							<ul>
								<li>常見問題</li>
								<li>聯絡我們</li>
								<li>廣告刊登</li>
							</ul>
							<i class="fa fa-phone fa-2x"></i>&nbsp;&nbsp; <i
								class="fa fa-envelope-o fa-2x"></i>&nbsp;&nbsp; <i
								class="fa fa-facebook-square fa-2x"></i>&nbsp;&nbsp; <i
								class="fa fa-twitter-square fa-2x"></i>&nbsp;&nbsp; <i
								class="fa fa-android fa-2x"></i>&nbsp;&nbsp; <i
								class="fa fa-print fa-2x"></i>


						</div>
					</div>
				</div>
</body>
</html>
