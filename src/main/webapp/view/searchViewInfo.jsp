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
	
// 	List<ViewInfoVO> viewinfolist=viewinfoSvc.getAll();
	List<ViewInfoVO> viewinfolist =(List<ViewInfoVO>) request.getAttribute("viewinfoList");
	
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
	  })
  })
</script>
<!-- NavBar	 -->

<title>eChoice-所有景點</title>
</head>
<body>

	<!-- ****top bar for 旅客**** -->

	<div class="col-md-12" id="topBar">
		<div class="row" class="navbar navbar-default" role="navigation">
			<div class="col-md-3" style="margin-top: 20px;">
				<a href="<%=request.getContextPath()%>/HomePage.jsp"><img
					src="<%=request.getContextPath()%>/images/logo.png" id="logo"></a>
			</div>
			<div class="col-md-9" id="top_menu" style="margin-top: 20px;">
				<ul class="nav navbar-nav navbar-right" id="myTabs">
					<li><a
						href="<%=request.getContextPath()%>/hostel_management.jsp" id=""><i
							class="fa fa-home fa-2x"></i>找民宿</a></li>

					<li><a
						href="<%=request.getContextPath()%>/view/allViewInfo.jsp"
						target="iframe"><i class="fa fa-picture-o fa-2x"></i>找景點</a></li>
					<li><a
						href="<%=request.getContextPath()%>/dealer_management.jsp" id=""><i
							class="fa fa-user fa-2x"></i>個人資料管理</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"> <i class="fa fa-heart fa-2x"></i>收藏
							<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a id="tab_1" href="#">景點收藏</a></li>
							<li><a href="<%=request.getContextPath()%>" target="iframe">民宿收藏</a></li>
						</ul></li>
					<c:if test="${empty dealerVObyAccount}">
						<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i
								class="fa fa-sign-in fa-2x"></i>登入</a></li>
					</c:if>
					<c:if test="${not empty dealerVObyAccount}">
						<li><a href="<%=request.getContextPath()%>/login.jsp" id=""><i
								class="fa fa-sign-out fa-2x"></i>登出</a></li>
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

							<p>
								&nbsp;&nbsp;<%=viewinfoVO.getViewname()%></p> </br> </br> <%
							if(tenantHasViewlist==0){  //判斷房客景點清單是否為空%>

							<form name="addViewList"
								action="<%=request.getContextPath()%>/viewlist/viewlist.do"
								method="POST">

								<input type="hidden" name="tenantNo" value="<%=tenantNo%>">
								<input type="hidden" name="viewno"
									value="<%=viewinfoVO.getViewno()%>"> <input
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