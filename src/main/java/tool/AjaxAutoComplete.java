package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;

/**
 * Servlet implementation class AjaxAutoplete
 */
@WebServlet("/AjaxAutoComplete")
public class AjaxAutoComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String temp=request.getParameter("term");
		String term=new String(temp.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("hi i am AjaxAutoComplete");
		System.out.println("term = "+term);
		
		List<String> outList=new ArrayList<String>();
		
		HostelService hostelService =new HostelService();
		List<HostelVO> hostelList=null;
		
		hostelList=hostelService.likeByColumn("hostelname", "%"+term+"%");
		if(hostelList.size()!=0){
			for(HostelVO hVO:hostelList){
				outList.add("\""+hVO.getHostelName()+"\"");
			}
			PrintWriter out=response.getWriter();
			out.println(outList.toString());
			return;
		}
		
		
		hostelList=hostelService.likeByColumn("hosteladdress", "%"+term+"%");
		if(hostelList.size()!=0){
			for(HostelVO hVO:hostelList){
				outList.add("\""+hVO.getHostelAddress()+"\"");
			}
			PrintWriter out=response.getWriter();
			out.println(outList.toString());
			return;
		}

		
		hostelList=hostelService.likeByColumn("hostelcontent", "%"+term+"%");
		if(hostelList.size()!=0){
			for(HostelVO hVO:hostelList){
				outList.add("\""+hVO.getHostelContent()+"\"");
			}
			PrintWriter out=response.getWriter();
			out.println(outList.toString());
			return;
		}
		
		hostelList=hostelService.likeByColumn("hostelphone", "%"+term+"%");
		if(hostelList.size()!=0){
			for(HostelVO hVO:hostelList){
				outList.add("\""+hVO.getHostelPhone()+"\"");
			}
			PrintWriter out=response.getWriter();
			out.println(outList.toString());
			return;
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
