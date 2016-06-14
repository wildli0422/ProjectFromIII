package com.viewphoto.controller;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import com.viewphoto.model.ViewPhotoService;
import com.roomTypePic.model.RoomTypePicService;

@MultipartConfig
// 當數據量大於fileSizeThreshold值時，內容將被寫入磁碟
// 上傳過程中無論是單個文件超過maxFileSize值，或者上傳的總量大於maxRequestSize 值都會拋出IllegalStateException 異常
@WebServlet("/viewphoto/multipleUpload.do")
public class MultipleUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String saveDirectory = "/images_uploaded"; // 上傳檔案的目地目錄;
											   // 將由底下的第26~30行用 java.io.File 於 ContextPath 之下, 自動建立目地目錄

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		System.out.println("ContentType="+req.getContentType()); // 測試用


		
		ViewPhotoService viewphotoSvc=new ViewPhotoService();
		
		
		Integer viewno=null;
		try {
			viewno=new Integer(req.getParameter("viewno"));
			
		} catch (Exception e) {
			viewno=0;
			e.getMessage();
		}
		System.out.println(viewno);
		

		Collection<Part> parts = req.getParts(); // Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理
		

		for (Part part : parts) {
			if (getFileNameFromPart(part) != null && part.getContentType()!=null) {
				
				String name = part.getName();
				String filename = getFileNameFromPart(part);
				String ContentType = part.getContentType();
				long size = part.getSize();
//				File f = new File(fsaveDirectory, filename);

	
				
				// 利用File物件,寫入目地目錄,上傳成功
//				part.write(f.toString());

				// 額外測試 InputStream 與 byte[] (幫將來model的VO預作準備)
				InputStream in = part.getInputStream();
				byte[] img = new byte[in.available()];
				in.read(img);
				if(viewno!=0)
					viewphotoSvc.addViewPhoto(viewno, img);
				
				
				in.close();

			}
		}
		
		RequestDispatcher successView = req.getRequestDispatcher("/backEnd/view/view.jsp");
		successView.forward(req, res);
	}

	// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}