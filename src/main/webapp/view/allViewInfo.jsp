<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.viewinfo.model.*"%>
<%@ page import="com.viewphoto.model.*"%>
<%@ page import="com.viewlist.model.*"%>
<%@ page import="com.tenant.model.*"%>

<%
	ViewPhotoService viewphotoSvc = new ViewPhotoService();
	List<ViewPhotoVO> viewphotolist = viewphotoSvc.getOneViewpic();
%>

<%
TenantVO tenantVO = (TenantVO)session.getAttribute("tenantVO");
Integer tenantNo = tenantVO.getTenantNo();
pageContext.setAttribute("tenantNo", tenantNo);

	ViewListService viewlistSvc = new ViewListService();
	Set<ViewListVO> viewlistlist = viewlistSvc
			.getViewListByTenantno(tenantNo);
	ViewInfoService viewinfoSvc=new ViewInfoService();
	
	List<ViewInfoVO> viewinfolist=viewinfoSvc.getAll();
	
	int tenantHasViewlist = 0;

	if (viewlistlist.isEmpty()) {
		tenantHasViewlist = 0;
	} else {
		tenantHasViewlist++;
	}
%>

<!DOCTYPE html>
<html>
<head>
<link href="<%=request.getContextPath()%>/view/style/css/reset.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/allView.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/view/style/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/view/style/css/main.css">
<link rel="Shortcut Icon" type="image/x-icon"
	href="<%=request.getContextPath()%>/images/Logo_S.png" />

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<!-- ****BootStrap Icon**** -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

<!-- NavBar	 -->
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

<!-- NavBar	 -->

<title>eChoice-所有景點</title>
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


	</br>





	<div id="content" class="col-md-1"></div>

	<!-- //--------------內容-------------------->
	<div id="content" class="col-md-10">

		<div id="searchBar" class="row">
			<div class="col-md-2"></div>

			<div class="col-md-7">
				<h1>立刻開始你的下一段旅行</h1>
				</br>
				
<!-- 				<div class="input-group"> -->
					<div class="row">
					<div class="col-md-1"></div>
					<div class="col-md-9">
					<form action="<%=request.getContextPath()%>/ViewSearchServlet" method="post">
					<input id="location" name="location"  type="text" style="width:670px;" class="form-control" placeholder="您想去哪裡?" required>
					</div>

					<div class="col-md-1">
						<button type="submit" class="btn btn-info btn-lg" type="button">
							<i class="glyphicon glyphicon-search" style="height:20px;"></i>
						</button>
					</div>
					</span>
					</form>
				</div> 
				<!---input end--->
			</div>
			
			<!---searchBar end--->
		</div>
		<div name="addViewList"
			action="<%=request.getContextPath()%>/viewlist/viewlist.do"
			method="POST">
			<div id="viewInfo" class="row">
				<div id="viewLabel">精選景點</div>
				<!---viewLabel end--->



				<%
					for (ViewInfoVO viewinfoVO : viewinfolist) {
				%>

				<div id="viewDes">
					<div id="viewCard">
						<%
							for (ViewPhotoVO viewphotoVO : viewphotolist) {
									if (viewinfoVO.getViewno().equals(viewphotoVO.getViewno())) {
						%>
						<a
							href="<%=request.getContextPath()%>/view/eachView.jsp?viewno=<%=viewinfoVO.getViewno() %>"
							title=""> <img id="viewPic"
							src="<%=request.getContextPath()%>/viewphoto/readviewphoto.do?viewpicno=<%=viewphotoVO.getViewpicno()%>">
						</a>
							<%
							}//if
								}//foreach viewphotovo end
						%>
							<div id="viewContent"></div>

							<p style="font-size:20px">
								&nbsp;&nbsp;<%=viewinfoVO.getViewname()%></p> </br> </br> <%
							if(tenantHasViewlist==0){  //判斷房客景點清單是否為空%>

							<form name="addViewList"
								action="<%=request.getContextPath()%>/viewlist/viewlist.do"
								method="POST">

								<input type="hidden" name="tenantNo" value="<%=tenantNo%>" >
								<input type="hidden" name="viewno"
									value="<%=viewinfoVO.getViewno()%>"> 
								<input
									type="hidden" name="action" value="insert">
								<button id="viewCollect" type="submit"
									class="btn btn-default btn-lg">
									<span id="viewCollectImg" class="glyphicon glyphicon-heart"></span>加到收藏清單
								</button>
							</form> <% }else{
						
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
									class="btn btn-default btn-lg">
									<span id="viewCollectImg" class="glyphicon glyphicon-heart"
										style="color: red"></span>取消收藏清單
								</button>
								<input type="hidden" name="viewlistno"
									value="<%=viewlistVO.getViewlistno()%>"> <input
									type="hidden" name="requestURL"
									value="<%=request.getServletPath()%>">
								<!--送出本網頁的路徑給Controller-->
								<input type="hidden" name="action" value="allViewInfoDelete">
								<input type="hidden" name="tenantNo"
									value="<%=viewlistVO.getTenantNo()%>">
							</FORM> <%
							} else {
						%>
							<form name="addViewList"
								action="<%=request.getContextPath()%>/viewlist/viewlist.do"
								method="POST">

								<input type="hidden" name="tenantNo"
									value="<%=viewlistVO.getTenantNo()%>"> <input
									type="hidden" name="viewno" value="<%=viewinfoVO.getViewno()%>">
								<input type="hidden" name="action" value="insert">
								<button id="viewCollect" type="submit"
									class="btn btn-default btn-lg">
									<span id="viewCollectImg" class="glyphicon glyphicon-heart"></span>加到收藏清單
								</button>
							</form> <%
							}
									break;
								}// if collect or not
								
							}////判斷房客景點清單是否為空
						%> <!---viewContent end--->
					</div>
					<!---viewCard end--->
				</div>
				<!---viewDes end--->
				<%
					}//foreach viewinfo end
				%>


			</div>
			<!---viewInfo end--->
		</div>
		<!---content end --->

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