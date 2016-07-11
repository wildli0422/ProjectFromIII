<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

</head>


<body>


	<form action="<%=request.getContextPath()%>/ExcelCreater" method="post">
		<h2>excel file name = <input type="text" name="excelFileName" value="SODList"></h2>
		<table border="1">
			<tr>
				<td><input type="text" name="tr1td1" value="姓名" /></td>
				<td><input type="text" name="tr1td2" value="電話" /></td>
				<td><input type="text" name="tr1td3" value="性別" /></td>
			</tr>
			<tr>
				<td><input type="text" name="tr2td1" value="加藤鷹" /></td>
				<td><input type="text" name="tr2td2" value="33243" /></td>
				<td><input type="text" name="tr2td3" value="凸" /></td>
			</tr>
			<tr>
				<td><input type="text" name="tr3td1" value="三上悠亞" /></td>
				<td><input type="text" name="tr3td2" value="33241" /></td>
				<td><input type="text" name="tr3td3" value="凹" /></td>
			</tr>
			<tr>
				<td><input type="text" name="tr4td1" value="大橋未久" /></td>
				<td><input type="text" name="tr4td2" value="33242" /></td>
				<td><input type="text" name="tr4td3" value="凹" /></td>
			</tr>
		</table>
		<input type="hidden" name="trSize" value="4">
		<input type="hidden" name="tdSize" value="3">
		<input type="submit">

	</form>

</body>