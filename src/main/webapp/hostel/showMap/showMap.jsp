<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
      html, body { height: 100%; margin: 0; padding: 0; }
      #map { height: 100%; }
    </style>
    <% 
    		String lat= request.getParameter("lat");
    	  String lng=request.getParameter("lng");
    	  		pageContext.setAttribute("lat", lng);
    	  		pageContext.setAttribute("lng", lat);
    %>
  </head>
  <body>
    <div id="map"></div>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDLpJWPBCoFaC13A-FTKX88ByMwiYnbsIA
        &libraries=visualization&callback=initMap">
    </script>
    <script >


    function initMap() {
      var myLatLng = {lat:${lat}, lng: ${lng}};

      // Create a map object and specify the DOM element for display.
      var map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        scrollwheel: true,
        zoom: 14
      });

      // Create a marker and set its position.
      var marker = new google.maps.Marker({
        map: map,
        position: myLatLng,
        title: 'point'
      });
    }


 </script>
  </body>
</html>