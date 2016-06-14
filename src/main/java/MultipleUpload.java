import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import com.hostelPic.model.HostelPicService;
import com.roomTypePic.model.RoomTypePicService;

@MultipartConfig
// ��ƾڶq�j��fileSizeThreshold�ȮɡA���e�N�Q�g�J�Ϻ�
// �W�ǹL�{���L�׬O��Ӥ��W�LmaxFileSize�ȡA�Ϊ̤W�Ǫ��`�q�j��maxRequestSize �ȳ��|�ߥXIllegalStateException ���`
public class MultipleUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String saveDirectory = "/images_uploaded"; // �W���ɮת��ئa�ؿ�;
											   // �N�ѩ��U����26~30��� java.io.File �� ContextPath ���U, �۰ʫإߥئa�ؿ�

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		System.out.println("ContentType="+req.getContentType()); // ���ե�
		
		String requestURL=req.getParameter("requestURL");
//		String realPath = getServletContext().getRealPath(saveDirectory);
//		System.out.println("realPath="+realPath); // ���ե�
//		File fsaveDirectory = new File(realPath);
//		if (!fsaveDirectory.exists())
//			 fsaveDirectory.mkdirs(); // �� ContextPath ���U,�۰ʫإߥئa�ؿ�
		RoomTypePicService roomTypePicService=new RoomTypePicService();
		HostelPicService hostelPicService=new HostelPicService();
		
		Integer roomTypeNo=null;
		try {
			roomTypeNo=new Integer(req.getParameter("roomTypeNo"));
//			if(roomTypeNo==null){
//				roomTypeNo=0;
//			}
		} catch (Exception e) {
			roomTypeNo=0;
			e.getMessage();
		}
		System.out.println(roomTypeNo);
		Integer hostelNo=null;
		try {
			hostelNo=new Integer(req.getParameter("hostelNo"));
		} catch (Exception e) {
			hostelNo=0;
			e.getMessage();
		}
		System.out.println(hostelNo);
		
		String test=req.getParameter("test");
		out.write("test: "+test);

		Collection<Part> parts = req.getParts(); // Servlet3.0�s�W�FPart�����A���ڭ̤�K���i���ɮפW�ǳB�z
		out.write("<h2> Total parts : " + parts.size() + "</h2>");

		for (Part part : parts) {
			if (getFileNameFromPart(part) != null && part.getContentType()!=null) {
				out.println("<PRE>");
				String name = part.getName();
				String filename = getFileNameFromPart(part);
				String ContentType = part.getContentType();
				long size = part.getSize();
//				File f = new File(fsaveDirectory, filename);

				out.println("name: " + name);
				out.println("filename: " + filename);
				out.println("ContentType: " + ContentType);
				out.println("size: " + size);
//				out.println("File: " + f);
				
				// �Q��File����,�g�J�ئa�ؿ�,�W�Ǧ��\
//				part.write(f.toString());

				// �B�~���� InputStream �P byte[] (���N��model��VO�w�@�ǳ�)
				InputStream in = part.getInputStream();
				byte[] img = new byte[in.available()];
				in.read(img);
				if(roomTypeNo!=0)
					roomTypePicService.addRoomTypePic(roomTypeNo, img);
				if(hostelNo!=0)
					hostelPicService.addHostelPic(hostelNo, img);
				
				in.close();
//				out.println("buffer length: " + img.length);
//				
//				// �B�~���ըq��
//				out.println("<br><img src=\""+req.getContextPath()+saveDirectory+"/"+filename+"\">");
//
//				out.println();
//				out.println("</PRE>");
				
			}
		}//part
		System.out.println(requestURL);
		req.setAttribute("check", "check");
		RequestDispatcher successView=req.getRequestDispatcher(requestURL);
		successView.forward(req, res);
//		res.sendRedirect(req.getContextPath()+requestURL);
			
	}

	// ���X�W�Ǫ��ɮצW�� (�]��API������method,�ҥH�����ۦ漶�g)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // ���ե�
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // ���ե�
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}