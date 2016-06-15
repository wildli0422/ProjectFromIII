<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
<title>eChioce| 台灣民宿  | choose your life.</title>
   
</head>


<Style>
	h1{
	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 40px;
	}

</Style>
<body background="<%=request.getContextPath() %>/images/500.jpg">



<br>
<br>
<br>
<div class="row" >
	<div class="col-md-4"></div>
	<div class="col-md-4">
	<div>
		
		<h1 id='oops' style="color: white">OOPS!  500</h1><br>
		<h3 id='text' style="color: white">
		eChoice出了些問題!
		<b><a href='<%=request.getContextPath() %>/HomePage.jsp' style="text-decoration:none;" style="color: white">回首頁</a></b>
		
		</h3><br>
		</div>
	
	</div>
	<div class="col-md-4"></div>
</div>

</body>
</html>