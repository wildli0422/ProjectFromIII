package com.viewinfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.viewinfo.model.ViewInfoService;
import com.viewinfo.model.ViewInfoVO;
@WebServlet("/ViewSearchServlet")
public class ViewSearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String location = req.getParameter("location");

		System.out.println("location" + location);
		System.out.println("location.length" + location.length());

		Map<String, String> paraMap = new HashMap<String, String>();
		Map<String, Boolean> nullMap = new HashMap<String, Boolean>();

		paraMap.put("location", location);
		if (location.length() == 0) {
			nullMap.put("location", true);
		} else {
			System.out.println("location not null !");
			nullMap.put("location", false);
		}

		ViewInfoService viewinfoService = new ViewInfoService();
		List<ViewInfoVO> viewinfoList = null;

		if (!nullMap.get("location")) {
			// 有輸入搜尋地點
			System.out.println("搜尋關鍵字為：" + location);
			viewinfoList = viewinfoService.likeByColumn("viewname", "%"
					+ paraMap.get(location) + "%");
			List<ViewInfoVO> newList = viewinfoService.likeByColumn(
					"viewaddress", "%" + paraMap.get("location") + "%");
			for (ViewInfoVO newVO : newList) {
				boolean haveSame = false;
				for (int i = 0; i < viewinfoList.size(); i++) {
					ViewInfoVO oldVO = viewinfoList.get(i);
					if (newVO.getViewno().equals(oldVO.getViewno())) {
						haveSame = true;
					}//if
				}//for
				if (!haveSame) {
					viewinfoList.add(newVO);
				}//if
			}//for
			newList = viewinfoService.likeByColumn("viewcontent",
					"%" + paraMap.get("location") + "%");
			for (ViewInfoVO newVO : newList) {
				boolean haveSame = false;
				for (int i = 0; i < viewinfoList.size(); i++) {
					ViewInfoVO oldVO = viewinfoList.get(i);
					if (newVO.getViewno().equals(oldVO.getViewno())) {
						haveSame = true;
					}//if
				}//for
				if (!haveSame) {
					// 不同景點編號則新增
					viewinfoList.add(newVO);
				}//if

			}//for

		}//if
		
		
		
		List<String> errorMsgs = new ArrayList<String>();
		
		RequestDispatcher dispather=req.getRequestDispatcher("/view/searchViewInfo.jsp");
		
		if(errorMsgs.size()!=0){
			req.setAttribute("errorMsgs", errorMsgs);
			dispather.forward(req, res);
		}
		else if(viewinfoList.size()==0){
			ViewInfoService viewinfoSvc = new ViewInfoService();
			viewinfoList= viewinfoSvc.getAll();
			req.setAttribute("viewinfoList", viewinfoList);
			dispather.forward(req, res);
		}else{
		req.setAttribute("viewinfoList", viewinfoList);
		dispather.forward(req, res);
		}
		
		System.out.println("即將輸出的景點名稱：");
		for(ViewInfoVO vVO:viewinfoList){
			//測試用
			System.out.println("viewName = "+vVO.getViewname());
		}
		

	}
}
