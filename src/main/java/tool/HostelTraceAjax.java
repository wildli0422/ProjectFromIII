package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hostel.model.HostelService;
import com.hostelTrace.model.HostelTraceService;
import com.hostelTrace.model.HostelTraceVO;
import com.tenant.model.TenantVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/HostelTraceAjax")
public class HostelTraceAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("hi motherFucker");
		String action=request.getParameter("action");
		
		if("getTrace".equalsIgnoreCase(action)){
			HostelTraceService traceServ = new HostelTraceService();
			TenantVO tenantVO = (TenantVO) session.getAttribute("tenantVO");
			List<HostelTraceVO> traceList=traceServ.getAllByTenantNo(tenantVO.getTenantNo());
			HostelService hosteServ=new HostelService();
			JSONArray array = new JSONArray();
			for(HostelTraceVO traceVO:traceList){
				JSONObject obj = new JSONObject();
				obj.put("traceHostelName", hosteServ.getOneHostel(traceVO.getHostelNo()).getHostelName());
				obj.put("hostelNo", traceVO.getHostelNo());
				array.add(obj);
			}
			System.out.println("array.toString() = "+array.toString());
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		if("addTrace".equalsIgnoreCase(action)){
			Integer hostelNo=new Integer(request.getParameter("hostelNo"));
			HostelTraceService traceServ = new HostelTraceService();
			TenantVO tenantVO = (TenantVO) session.getAttribute("tenantVO");
			List<HostelTraceVO> traceList=traceServ.getAllByTenantNo(tenantVO.getTenantNo());
			//findout hostelNo have same ?
			int hostelTraceNo=0;
			boolean haveSame=false;
			for(HostelTraceVO traceVO:traceList){
				if(traceVO.getHostelNo().equals(hostelNo)){
					haveSame=true;
					hostelTraceNo=traceVO.getHostelTraceNo();
				}
			}
			if(haveSame==false){
				traceServ.addHostelTrace(hostelNo, tenantVO.getTenantNo());
			}
			if(haveSame){
				traceServ.deleteHostelTrace(hostelTraceNo);
			}
			
			
			
			traceList=traceServ.getAllByTenantNo(tenantVO.getTenantNo());
			
			HostelService hosteServ=new HostelService();
			JSONArray array = new JSONArray();
			for(HostelTraceVO traceVO:traceList){
				JSONObject obj = new JSONObject();
				obj.put("traceHostelName", hosteServ.getOneHostel(traceVO.getHostelNo()).getHostelName());
				obj.put("hostelNo", traceVO.getHostelNo());
				array.add(obj);
			}
			System.out.println("array.toString() = "+array.toString());
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
