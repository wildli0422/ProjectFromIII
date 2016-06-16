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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery_UI_methods.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-image-gallery.css">
    <script src="<%=request.getContextPath()%>/js/bootstrap-image-gallery.js"></script>
    <link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
    <script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
	<title>hostel management</title>
	
	<script>
// 		$(document).ready(function(){
// 			$('.hostelPics').ekkoLightbox(options);	
// 		})
// 		$(document).delegate('*[data-toggle="lightbox"]', 'click', function(event) {
// 		    event.preventDefault();
// 		    $(this).ekkoLightbox();
// 		}); 
	</script>
</head>

<body>

<!-- 	header -->
	<%
		DealerVO dealerVO=(DealerVO)session.getAttribute("dealerVObyAccount");
		Integer dealerNo=dealerVO.getDealerNo();
		
		DealerService dealerService=new DealerService();
		HostelVO hostelVO=dealerService.getHostelByDealerNo(dealerNo);
		Integer hostelNo=hostelVO.getHostelNo();
		
// 		Integer roomTypeNo=dealerService.
		
		HostelService hostelService=new HostelService();
		Set<RoomTypeVO> roomTypeSet=hostelService.getRoomTypesByHostelNo(hostelNo);
		Set<HostelPicVO> hostelPicSet=hostelService.getHostelPicsByHostelNo(hostelNo);
		List<HostelNewsVO> newsList=hostelService.getNewsByHostelNo(hostelNo);
		
		RoomTypeService roomTypeService=new RoomTypeService();
		List<List<RoomTypePicVO>> PicList=new ArrayList<List<RoomTypePicVO>>();
		
		
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
<!-- 	hostel img -->	
	<div class="col-md-12">
		<div class="row">
			<div class="col-md-1">
			</div>
			
			<div class="col-md-10" >
				<div class="row">
					<img style="width:100%;height:540px;" 
					src="<%=request.getContextPath()%>/ImageReader?hostelNo=<%=hostelVO.getHostelNo()%>&hostelPicture=<%=hostelVO.getHostelPicture()%>">
				</div>
			</div>
			
			<div class="col-md-1">
			</div>
		</div>
	</div>
	
<!-- 	hostel img -->
<!-- 		hostel dealer name	Content -->
	<div class="col-md-12" style="margin-top:15px;">
		<div class="row">
			<div class="col-md-2">
			</div>
	
			<div class="col-md-1">
				<div class="row">
					<div style="text-align:center;">
						<span class="glyphicon glyphicon-user" style="font-size:80px;"></span>
						<div id="hostel_dealerName">${dealerVObyAccount.dealerName}</div>
					</div>
				</div>
			</div>
			<div class="col-md-5" >
				<div class="row">
					<div id="hostelName">
						<span id="hostelName">【<%=hostelVO.getHostelName() %>】</span>
						<hr>
						<div id="hostelAddress"><%=hostelVO.getHostelAddress() %></div>
					</div>
				</div>
			</div>
			<div class="col-md-2">
				<div class="row">
					<div style="text-align:center;">
						<span class="glyphicon glyphicon-star" style="color:#F0E68C;font-size:45px;"></span>
						<span class="glyphicon glyphicon-star" style="color:#F0E68C;font-size:45px;"></span>
						<span class="glyphicon glyphicon-star" style="color:#F0E68C;font-size:45px;"></span>
						<span class="glyphicon glyphicon-star" style="color:#F0E68C;font-size:45px;"></span>
						<span class="glyphicon glyphicon-star" style="color:#F0E68C;font-size:45px;"></span>
						<div><a href="https://www.facebook.com/funtaiwanbnb/?fref=ts" target=_blank>
							<img src="<%=request.getContextPath()%>/images/facebook.png" height="71px"></a>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-2">
			</div>
		</div>
	</div>
<!-- 		hostel dealer name	Content -->

<!-- <hr style="border:1px solid silver;"> -->
<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<hr>
	</div>
	<div class="col-md-2">
	</div>
</div>


<!------ content  ---->
	<div class="col-md-12" style="margin-top:15px;">
		<div class="row">
			<div class="col-md-2">
			</div>
			
<!-- 			content content -->

			<div class="col-md-8" >
				<div class="row">
					<div id="cautions">最新消息:<p>
						<c:forEach var="hostelNewsVO" items="<%=newsList %>">
							<div>
								<span>${hostelNewsVO.newsContent}</span>
								<span style="float:right;">${hostelNewsVO.updateDate }</span>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
<!-- 			content content -->
			
			<div class="col-md-2">
			</div>
		</div>
	</div>
<!------ content  ---->
<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<hr>
	</div>
	<div class="col-md-2">
	</div>
</div>
<!------ cautions  ---->
	<div class="col-md-12" style="margin-top:15px;">
		<div class="row">
			<div class="col-md-2">
			</div>
			
<!-- 			cautions content -->
			<div class="col-md-8" >
				<div class="row">
					<div id="cautions">注意事項:<p>
					<%=hostelVO.getHostelCautions() %>
					</div>
				</div>
			</div>
<!-- 			cautions content -->
			
			<div class="col-md-2">
			</div>
		</div>
	</div>
<!------ cautions  ---->
<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<hr>
	</div>
	<div class="col-md-2">
	</div>
</div>
<!------ content  ---->
	<div class="col-md-12" style="margin-top:15px;">
		<div class="row">
			<div class="col-md-2">
			</div>
			
<!-- 			content content -->
			<div class="col-md-8" >
				<div class="row">
					<div id="cautions">民宿簡介:<p>
					<%=hostelVO.getHostelContent() %>
					</div>
				</div>
			</div>
<!-- 			content content -->
			
			<div class="col-md-2">
			</div>
		</div>
	</div>
<!------ content  ---->
<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<hr>
	</div>
	<div class="col-md-2">
	</div>
</div>
<!------ hostelPics  ---->
<!-- 	<div class="col-md-12" style="margin-top:15px;"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-md-2"> -->
<!-- 			</div> -->
			
<!-- <!-- 			hostelPics content --> 
<!-- 			<div class="col-md-8" > -->
<!-- 				<div class="row"> -->
<!-- 					<div >民宿照片:<p> -->
<%-- 						<c:forEach var="hostelPicVO" items="<%=hostelPicSet%>"> --%>
<%-- 							<span><a href="<%=request.getContextPath()%>/ImageReader?hostelPicNo=${hostelPicVO.hostelPicNo}" data-gallery> --%>
<%-- 							<img style="height:100px;" src="<%=request.getContextPath()%>/ImageReader?hostelPicNo=${hostelPicVO.hostelPicNo}"> --%>
<!-- 							</a></span> -->
<%-- 						</c:forEach> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- <!-- 			hostelPics content -->
			
<!-- 			<div class="col-md-2"> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!------ hostelPics  ---->

<!------ facility  ---->
	<div class="col-md-12" style="margin-top:15px;">
		<div class="row">
			<div class="col-md-2">
			</div>
			
<!-- 			content content -->
			<div class="col-md-8" >
				<div class="row">
					<div id="facility">設備內容:<p>
						<c:forEach var="roomTypeVO" items="<%=roomTypeSet %>">
							<div>${facilityService.getOneFacility(roomTypeVO.getFacilityNo()).facilityNo }</div>
						</c:forEach>
					</div>
				</div>
			</div>
<!-- 			content content -->
			
			<div class="col-md-2">
			</div>
		</div>
	</div>
<!------ facility  ---->
<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<hr>
	</div>
	<div class="col-md-2">
	</div>
</div>
<!------ roomType   ---->
	<div class="col-md-12" style="margin-top:15px;">
		<div class="row">
			<div class="col-md-2">
			</div>
			
<!-- 			roomType content -->
			<div class="col-md-8" >
				<div class="row">
					<div id="cautions">房型簡介:<p>
						<c:forEach var="roomTypeVO" items="<%=roomTypeSet %>">
							<div id="hostelPage_roomType" style="background-color:#fff3e6">
							<table >
								<tr>
									<td width="20%">
										<img style="margin:10px;width:200px;" src="<%=request.getContextPath()%>/ImageReader?roomTypeNo=${roomTypeVO.roomTypeNo}">
									</td>
									<td width="15%">
										【${roomTypeVO.roomTypeName}】
									</td>
									<td width="15%">
										價錢:${roomTypeVO.roomTypePrice }
									</td>
									<td width="50%">
										content: ${roomTypeVO.roomTypeContent}
									</td>
								</tr>
							</table>
							</div>		
						</c:forEach>
					</div>
				</div>
			</div>
<!-- 		roomType content -->
			
			<div class="col-md-2">
			</div>
		</div>
	</div>
<!------ roomType   ---->
<div class="row">
	<div class="col-md-2">
	</div>
	<div class="col-md-8">
		<hr>
	</div>
	<div class="col-md-2">
	</div>
</div>
<!------ roomTypePic   ---->
	<div class="col-md-12" style="margin-top:15px;">
		<div class="row">
			<div class="col-md-2">
			</div>
			
<!-- 			roomTypePic content -->
			<div class="col-md-8" >
				<div class="row">
					<div id="cautions">民宿照片:<p>
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
				</div>
			</div>
<!-- 		roomTypePic content -->
			
			<div class="col-md-2">
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

<!-- //------------footer-------------- -->	
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