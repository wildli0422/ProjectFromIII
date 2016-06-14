<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>eChioce| 台灣民宿  | choose your life.</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">

   
</head>


<Style>
	h1{
	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 40px;
	}

</Style>
<body style="background-color:#89B9F1">



<div class="row" >
	<div class="col-md-4"></div>
	<div class="col-md-4"  >
		<center>
		<img class="img-thumbnail img-circle" src="<%=request.getContextPath() %>/images/404.jpg"  height="100px">
		<div>
		
		<h1 id='oops' style="color: white">OOPS!  404</h1><br>
		
		<h3 id='text'>
		網頁不見了!
		<b><a href='<%=request.getContextPath() %>/HomePage.jsp' style="text-decoration:none;">回首頁</a></b>
		
		</h3><br>
		</center>
		</div>
	</div>
	<div class="col-md-4"></div>
</div>

</body>