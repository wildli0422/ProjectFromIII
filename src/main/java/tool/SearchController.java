package tool;

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

import tool.emptyRoom.model.EmptyRoomVO;

import com.hostel.model.HostelService;
import com.hostel.model.HostelVO;
import com.roomType.model.RoomTypeService;
import com.roomType.model.RoomTypeVO;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/tool/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hi i am SearchController !");
		request.setCharacterEncoding("UTF-8");
		String location =request.getParameter("location");
		String checkin=request.getParameter("checkin");
		System.out.println("checkin = "+checkin);
		String checkout=request.getParameter("checkout");
		System.out.println("checkout = "+checkout);
		String guests=request.getParameter("guests");
		
		int minGuest=new Integer(guests);
		
		System.out.println("location = "+ location);
		System.out.println("location.length = "+ location.length());
		
		Map<String, String> paraMap=new HashMap<String, String>();
		Map<String,Boolean> nullMap=new HashMap<String, Boolean>();
		paraMap.put("location", location);
		if(location.length()==0){
			nullMap.put("location", true);
		}else{
			System.out.println("location not null !");
			nullMap.put("location", false);
		}
		paraMap.put("checkin", checkin);
		if(checkin.length()==0){
			nullMap.put("checkin", true);
		}else{
			nullMap.put("checkin", false);
		}
		paraMap.put("checkout", checkout);
		if(checkout.length()==0){
			nullMap.put("checkout", true);
		}else{
			nullMap.put("checkout", false);
		}
		paraMap.put("guests", guests);
		if(guests.length()==0){
			nullMap.put("guests", true);
		}else{
			nullMap.put("guests", false);
		}
		
		HostelService hostelService =new HostelService();
		List<HostelVO> hostelList=null;
		
		//location !=null 故要搜尋地點
		if(!nullMap.get("location")){
			//有輸入搜尋地點
			System.out.println("搜尋關鍵字為："+location);
			hostelList=hostelService.likeByColumn("hosteladdress","%"+paraMap.get("location")+"%");
			List<HostelVO> newList=hostelService.likeByColumn("hostelname","%"+paraMap.get("location")+"%");
			for(HostelVO newVO:newList){
				boolean haveSame=false;
				for(int i=0;i<hostelList.size();i++){
					HostelVO oldVO=hostelList.get(i);
					if(newVO.getHostelNo().equals(oldVO.getHostelNo())){
						haveSame=true;
					}
				}
				if(!haveSame){
					//不同民宿編號則新增
					hostelList.add(newVO);
				}
			}
			newList=hostelService.likeByColumn("hostelcontent","%"+paraMap.get("location")+"%");
			for(HostelVO newVO:newList){
				boolean haveSame=false;
				for(int i=0;i<hostelList.size();i++){
					HostelVO oldVO=hostelList.get(i);
					if(newVO.getHostelNo().equals(oldVO.getHostelNo())){
						haveSame=true;
					}
				}
				if(!haveSame){
					//不同民宿編號則新增
					hostelList.add(newVO);
				}
			}
			newList=hostelService.likeByColumn("hostelcautions","%"+paraMap.get("location")+"%");
			for(HostelVO newVO:newList){
				boolean haveSame=false;
				for(int i=0;i<hostelList.size();i++){
					HostelVO oldVO=hostelList.get(i);
					if(newVO.getHostelNo().equals(oldVO.getHostelNo())){
						haveSame=true;
					}
				}
				if(!haveSame){
					//不同民宿編號則新增
					hostelList.add(newVO);
				}
			}
			newList=hostelService.likeByColumn("hostelphone","%"+paraMap.get("location")+"%");
			for(HostelVO newVO:newList){
				boolean haveSame=false;
				for(int i=0;i<hostelList.size();i++){
					HostelVO oldVO=hostelList.get(i);
					if(newVO.getHostelNo().equals(oldVO.getHostelNo())){
						haveSame=true;
					}
				}
				if(!haveSame){
					//不同民宿編號則新增
					hostelList.add(newVO);
				}
			}
		}else if(nullMap.get("location")){
			//location 為空 故搜全部的民宿 
			System.out.println("未輸入location");
			hostelList=hostelService.getAll();
		}
		ERCounter erCounter=new ERCounter();
		RoomTypeService roomTypeService=new RoomTypeService();
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
		List<String> errorMsgs = new ArrayList<String>();
		
		if((nullMap.get("checkin")==false) && (nullMap.get("checkout")==false)){
			Date startDate=null;
			Date endDate=null;
			try {
				startDate = new Date(sm.parse(checkin).getTime());
				endDate=new Date(sm.parse(checkout).getTime());
			} catch (ParseException e) {
				errorMsgs.add("日期格式錯誤！？" );
			}
			
			List<HostelVO> removeList=new ArrayList<HostelVO>();
			for(int i=0;i<hostelList.size();i++){
				HostelVO hostelVO=hostelList.get(i);
				System.out.println("Count ER hostelName = "+hostelVO.getHostelName() );
				List<RoomTypeVO> roomTypeList=roomTypeService.getAllByHostelNo(hostelVO.getHostelNo());
				int[][] timeRangeCust=new int[roomTypeList.size()][];
				for(int j=0;j<roomTypeList.size();j++){
					RoomTypeVO roomTypeVO=roomTypeList.get(j);
					List<EmptyRoomVO> erList=erCounter.countER(roomTypeVO.getRoomTypeNo(), startDate, endDate);
					 timeRangeCust[j]=new int[erList.size()];
					for(int k=0;k<erList.size();k++){
						timeRangeCust[j][k]=erList.get(k).getEmptyRoomQty()*roomTypeVO.getRoomTypeContain();
						System.out.println(timeRangeCust[j][k]);
					}
				}
				int[] hostelMinCust=new int[timeRangeCust[0].length];
				for(int j=0;j<timeRangeCust.length;j++){
					for(int k=0;k<timeRangeCust[j].length;k++){
						hostelMinCust[k]+=timeRangeCust[j][k];
					}
				}
				int minCust=9999;
				for(int j=0;j<hostelMinCust.length;j++){
					System.out.println("hostelMinCust["+j+"] = "+hostelMinCust[j]);
					if(hostelMinCust[j]<minCust){
						minCust=hostelMinCust[j];
					}
				}
				System.out.println("minCust = "+minCust);
				System.out.println("至少要有 "+ minGuest+" 位房客");
				if(minCust<minGuest){
					System.out.println(" 容客數不足  民宿-"+hostelVO.getHostelName()+" 將被刪除");
					removeList.add(hostelVO);
				}
			}
			hostelList.removeAll(removeList);
		}else{
			errorMsgs.add("日期輸入錯誤！");
		}
		
		
		
		
		
		
		System.out.println("即將輸出的民宿名稱：");
		for(HostelVO hVO:hostelList){
			//測試用
			System.out.println("hostelName = "+hVO.getHostelName());
		}
		
		RequestDispatcher dispather=request.getRequestDispatcher("/hostel/searchResult.jsp");
	  
		
		if(errorMsgs.size()!=0){
			request.setAttribute("errorMsgs", errorMsgs);
			dispather.forward(request, response);
		}
		else if(hostelList.size()==0){
			errorMsgs.add("查無民宿");
			request.setAttribute("errorMsgs", errorMsgs);
			dispather.forward(request, response);
		}else{
		request.setAttribute("hostelList", hostelList);
		dispather.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
