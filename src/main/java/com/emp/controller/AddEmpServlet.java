package com.emp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.MailService;
import com.accessemp.model.AccessEmpService;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;


@WebServlet("/backEnd/emp/emp.add")
@MultipartConfig
public class AddEmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			
			String nameUniqueErr= null;
			String phoneNumErr= null;
		
		
			// random password
			
			
			String empAccount = req.getParameter("empAccount");
			Random ran = new Random();
			String empPassword = Integer.toString(ran.nextInt(9999));
			String empName = req.getParameter("empName");
			String empSex = req.getParameter("empSex");
			String empAddress = req.getParameter("empAddress");
			String empPhone = req.getParameter("empPhone");
			try {
				Integer.parseInt(empPhone);
			}catch(Exception e){
				phoneNumErr = "電話不能有中文字";
				req.setAttribute("phoneNumErr", phoneNumErr);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backEnd/emp/empAddNew.jsp");
				failureView.forward(req, resp);
				return;
			}
			String empMail = req.getParameter("empMail");
			
			
			Part empPicture =req.getPart("empPic");
			InputStream empPicStream=empPicture.getInputStream();
			byte[]  empPic =new byte[empPicStream.available()];
			empPicStream.read(empPic);
			empPicStream.close(); 
			empPicStream=null;
			
			EmpVO empVO = new EmpVO();
			empVO.setEmpAccount(empAccount);
			empVO.setEmpPassword(empPassword);
			empVO.setEmpName(empName);
			empVO.setEmpSex(empSex);
			empVO.setEmpAddress(empAddress);
			empVO.setEmpPhone(empPhone);
			empVO.setEmpMail(empMail);
			empVO.setEmpPic(empPic);
			
			EmpService empService = new EmpService();
			
			try {
				empVO = empService.addEmp(empAccount, empPassword, empName, empSex, empAddress, empMail, empPhone,
						empPic);
			} catch (Exception e) {
				nameUniqueErr = "帳號已被使用";
				req.setAttribute("nameUniqueErr", nameUniqueErr);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backEnd/emp/empAddNew.jsp");
				failureView.forward(req, resp);
				return;
			}
			
			
			//寄信
			String message = empName+"君你好，你在eChoice BackEnd的帳號為:"+empAccount+"	密碼為:"+empPassword;
			MailService ms = new MailService();
			ms.sendMail(empMail, "eChoice 後台帳號", message);
			
			
			AccessEmpService accessEmpService = new AccessEmpService();
			accessEmpService.grantAccess(empVO.getEmpNo(), 9006);
			
			RequestDispatcher successView =req.getRequestDispatcher("/backEnd/emp/empManager.jsp");
			successView.forward(req, resp);
			
			
	
	}

}
