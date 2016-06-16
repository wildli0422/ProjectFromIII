<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.viewinfo.model.*" %>

<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
   // ViewInfoService viewinfoSvc = new ViewInfoService();
    //List<ViewInfoVO> list = viewinfoSvc.getAll();
    //pageContext.setAttribute("list",list);
%>

<jsp:useBean id="viewinfoSvc" scope="page" class="com.viewinfo.model.ViewInfoService"/>

<html>
<head>
 <meta charset="utf-8">
    <link href="<%=request.getContextPath() %>/viewinfo/style/css/reset.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/viewinfo/style/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/viewinfo/style/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/viewinfo/style/css/main.css">
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=request.getContextPath() %>/viewinfo/style/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/viewinfo/style/js/jquery_UI_methods.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

    <title>Document</title>
	
	

<title>景點管理 - listAllViewInfo.jsp</title>
</head>
<body bgcolor='white'>


	<!-- <div class="col-md-12" id="container"> -->
     
<div class="col-md-12">


		<div class="row" style="background-color:#87ADDA;">
			<div clas="col-md-1"></div>
            <div class="col-md-10">
                <img id="logImg" src="<%=request.getContextPath() %>/viewinfo/style/images/Logo.png" >
            </div>
            <div id="loginDiv" class="col-md-1" >
				<button type="button" class="btn btn-default btn-sm "  style="float:right;font-size:18px;" >
					<span class="glyphicon glyphicon-log-in" aria-hidden="true"  ></span>&nbsp;&nbsp;登入
				</button>
            </div><!--header-->
			
        </div><!-- row -->
<div class="cantainer">
	<div class="row">
		<div class="col-md-1">
		</div>
		<div class="col-md-10" id="title" >
		<p>廣告管理</p>
		</div>
		<div class="col-md-1">
		</div>
	</div>
</div>		
	
		
		
<div class="cantainer" style="margin-top:20px;">
	<div class="row" >		
        <div class="col-md-1">
		</div>
		<div class="col-md-10">
			<table style="width:90%; font-size:20px;" class="table table-hover" >
				
				
				<tr>
					<th>景點編號</th>
					<th>景點名稱</th>
					<th>景點電話</th>
          <th>景點資訊</th>
          <th>修改</th>
          <th>刪除</th>
					
					
				</tr>
				
				<c:forEach var="viewinfoVO" items="${viewinfoSvc.all}" >
				<tr>
				
					<td>${viewinfoVO.viewno}</td>
					<td>${viewinfoVO.viewname}</td>
					<td>${viewinfoVO.viewphone}</td>
					
					<td>
					 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/viewinfo/viewinfo.do">
			    <button type="submit" class="btn btn-info accBtn">
              <span  class="glyphicon glyphicon-search iconType "></span></button> 
			    <input type="hidden" name="viewno" value="${viewinfoVO.viewno}">
			    <input type="hidden" name="action" value="listViewPhoto_ByViewno_B">
			</td></FORM>
					
					
					
	
					
					
					
					
					</td>
            
            <td>
           
                <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/viewinfo/viewinfo.do">
                 <button type="submit" class="btn btn-success accBtn" >
              <span  class="glyphicon glyphicon-cog iconType" ></span></button>
			     <input type="hidden" name="viewno" value="${viewinfoVO.viewno}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></td></FORM>
              </td>  
            
            <td> 
            
            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/viewinfo/viewinfo.do">
			    <button type="submit" class="btn btn-danger accBtn">
              <span  class="glyphicon glyphicon-remove iconType "></span></button>
			    <input type="hidden" name="viewno" value="${viewinfoVO.viewno}">
			    <input type="hidden" name="action"value="delete"></FORM>
            
            
            
            
            </td>
				</tr>
				</c:forEach>
			</table>	
		</div>
		<div class="col-md-1">
		</div>

	</div>
</div>


	
	<div class="row">
		<div  id="footer" class="col-md-12" >
		All Content Copyright &copy; 2016 Silvia Inc.
		</div>
	</div>
<!-- //------------footer-------------- -->


</body>
</html>



