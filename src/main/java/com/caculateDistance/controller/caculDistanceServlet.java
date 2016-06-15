package com.caculateDistance.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;
import com.viewinfo.model.ViewInfoService;
import com.viewinfo.model.ViewInfoVO;

public class caculDistanceServlet  {
	
	private static final double EARTH_RADIUS = 6378137;
    private static double rad(double d)
    {
       return d * Math.PI / 180.0;
    }
    
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2)
    {
       double radLat1 = rad(lat1);
       double radLat2 = rad(lat2);
       double a = radLat1 - radLat2;
       double b = rad(lng1) - rad(lng2);
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
       s = s * EARTH_RADIUS;
       s = Math.round(s * 10000) / 10000/1000;
       return s;
    }
	
		

			//GetDistance(viewlat, viewlon, hostelVO.getHostelLat(), hostelVO.getHostelLon());
			//System.out.println(GetDistance(121.491909, 31.233234, hostelVO.getHostelLat(), hostelVO.getHostelLon()));
			
			

		
		
		
	
	


	  
}
