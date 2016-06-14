<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <meta charset="utf-8" />
  <title>WebSocket Test</title>
  <script language="javascript" type="text/javascript">

  
  var MyPoint = "/EchoServer";
  var host = window.location.host;
  var path = window.location.pathname;
  var webCtx = path.substring(0, path.indexOf('/', 1));
  var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
</script>
 
 
 </html>
