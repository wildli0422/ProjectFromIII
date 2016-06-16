package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.roomType.model.RoomTypeService;
import com.roomType.model.RoomTypeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tool.cart.model.OrderCartVO;
import tool.emptyRoom.model.EmptyRoomVO;

@WebServlet("/AjaxJqueryController")
public class AjaxJqueryController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action=request.getParameter("action");
		
		if("getMaxQty".equalsIgnoreCase(action)){
			System.out.println("hi i am motherFuck");
			String checkIn=request.getParameter("checkIn");
			String checkOut=request.getParameter("checkOut");
			Integer roomTypeNo=new Integer(request.getParameter("roomTypeNo"));
			String eleID=request.getParameter("eleID");
			System.out.println("checkIn = "+checkIn+" , checkOut = "+ checkOut +" ,roomTypeNo = "+roomTypeNo+" ,eleID = "+eleID);
			
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			List<EmptyRoomVO> erList = new ArrayList<EmptyRoomVO>();
			
			
		
			try {
				Date startDate = new java.sql.Date(sm.parse(checkIn).getTime());
				Date endDate = new java.sql.Date(sm.parse(checkOut).getTime());
				ERCounter erCounter = new ERCounter();
				erList = erCounter.countER(roomTypeNo, startDate, endDate);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("parse exception!");
			}

			int maxQty=9999;
			for(EmptyRoomVO erVO:erList){
				if(erVO.getEmptyRoomQty()<maxQty){
					maxQty=erVO.getEmptyRoomQty();
				}
			}

			JSONArray array = new JSONArray();
			JSONObject obj = new JSONObject();
			try {
				obj.put("maxQty", maxQty);
				obj.put("eleID",eleID);

			} catch (Exception e) {
			}
			array.add(obj);

			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		
		if("addToCart".equalsIgnoreCase(action)){
			System.out.println("hi i am motherFuck -II");
			String checkIn=request.getParameter("checkIn");
			String checkOut=request.getParameter("checkOut");
			Integer roomTypeNo=new Integer(request.getParameter("roomTypeNo"));
			Integer roomQty=new Integer(request.getParameter("roomQty"));
			System.out.println("checkIn = "+checkIn+" , checkOut = "+ checkOut +" ,roomTypeNo = "+roomTypeNo+" ,roomQty = "+roomQty);
			RoomTypeService roomTypeServ=new RoomTypeService();
			HttpSession session=request.getSession();
			List<OrderCartVO> cartList=null;
			if(session.getAttribute("cartList")==null){
			 cartList=new ArrayList<OrderCartVO>();
			}else{
				cartList=(List)session.getAttribute("cartList");
			}
			
			OrderCartVO cartVO=new OrderCartVO();
			cartVO.setCheckInDate(checkIn);
			cartVO.setCheckOutDate(checkOut);
			cartVO.setRoomQty(roomQty);
			cartVO.setRoomTypeNo(roomTypeNo);
			RoomTypeVO roomTypeVO=roomTypeServ.getOneRoomType(roomTypeNo);
			cartVO.setTotalPrice(roomQty*roomTypeVO.getRoomTypePrice());
			cartVO.setRoomTypeName(roomTypeVO.getRoomTypeName());
			
			
			
			boolean haveSame=false;
			for(int i=0;i<cartList.size();i++){
				OrderCartVO cartVOinList=cartList.get(i);
				if((haveSame==false)&&(cartVO.getRoomTypeNo().equals(cartVOinList.getRoomTypeNo()))){
					if((cartVO.getCheckInDate().equals(cartVOinList.getCheckInDate())) && (cartVO.getCheckOutDate().equals(cartVOinList.getCheckOutDate()))){
					haveSame=true;
					int tempQty=cartVOinList.getRoomQty();
					cartVOinList.setRoomQty(tempQty+cartVO.getRoomQty());
					cartVOinList.setTotalPrice(cartVOinList.getTotalPrice()+cartVO.getTotalPrice());
					cartList.set(i, cartVOinList);
					}
				}
			}
			if(haveSame==false){
			cartList.add(cartVO);
			}
			
			
			
			
			
			JSONArray array = new JSONArray();
			for (OrderCartVO cart : cartList) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("roomTypeName", cart.getRoomTypeName());
					obj.put("roomTypeNo", cart.getRoomTypeNo());
					obj.put("roomQty", cart.getRoomQty());
					obj.put("price", cart.getTotalPrice());
					obj.put("checkInDate", cart.getCheckInDate());
					obj.put("checkOutDate", cart.getCheckOutDate());
				} catch (Exception e) {
				}
				array.add(obj);
			}
			session.setAttribute("cartList", cartList);
			System.out.println("array.toString() = "+array.toString());
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			
		}
		
		
		
		
		
		if ("getErJson".equalsIgnoreCase(action)) {
			Integer roomTypeNo = new Integer(
					request.getParameter("ajaxRoomTypeNo"));
			RoomTypeService roomTypeServ=new RoomTypeService();
			RoomTypeVO roomTypeVO=roomTypeServ.getOneRoomType(roomTypeNo);
			
			Integer year = new Integer(request.getParameter("ajaxYear"));
			Integer month = new Integer(request.getParameter("ajaxMonth"));
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, 1);
			int lastDay = cal.getActualMaximum(Calendar.DATE);
			System.out.println("lastDay = " + lastDay);
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			List<EmptyRoomVO> erList = new ArrayList<EmptyRoomVO>();
			try {
				sm.parse(year + "-" + month + "-" + 1);
				Date startDate = new java.sql.Date(sm.parse(
						year + "-" + month + "-" + 1).getTime());
				Date endDate = new java.sql.Date(sm.parse(
						year + "-" + month + "-" + lastDay).getTime());
				ERCounter erCounter = new ERCounter();
				erList = erCounter.countER(roomTypeNo, startDate, endDate);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("parse exception!");
			}

			JSONArray array = new JSONArray();
			for (EmptyRoomVO erVO : erList) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("Date", erVO.getNowDate());
					obj.put("erQty", erVO.getEmptyRoomQty());
					obj.put("price", roomTypeVO.getRoomTypePrice());
				} catch (Exception e) {
				}
				array.add(obj);
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
//		if("addToCartXXXX".equalsIgnoreCase(action)){
////			System.out.println("hi i am ajax jquery");
////
////			System.out.println("ajaxRoomTypeNo = "
////					+ request.getParameter("ajaxRoomTypeNo"));
////			System.out.println("ajaxYear = " + request.getParameter("ajaxYear"));
////			System.out.println("ajaxMonth = " + request.getParameter("ajaxMonth"));
////			System.out.println("startDay = "+request.getParameter("startDay"));
////			System.out.println("endDay = "+request.getParameter("endDay"));
////			System.out.println("roomQty = "+request.getParameter("roomQty"));
////			System.out.println("monthCross = "+request.getParameter("monthCross"));
//			HttpSession session=request.getSession();
//			List<OrderCartVO> cartList=null;
//			if(session.getAttribute("cartList")==null){
//			 cartList=new ArrayList<OrderCartVO>();
//			}else{
//				cartList=(List)session.getAttribute("cartList");
//			}
//			OrderCartVO cartVO=new OrderCartVO();
//			String tempStr=request.getParameter("ajaxYear")+"-"+request.getParameter("ajaxMonth")+"-"+request.getParameter("startDay");
//			cartVO.setCheckInDate(tempStr);
//			if(new Boolean(request.getParameter("monthCross"))){
//				int tempMonth=new Integer(request.getParameter("ajaxMonth"))+1;
//				tempStr=request.getParameter("ajaxYear")+"-"+tempMonth+"-1";
//			}else{
//				int tempDay=new Integer(request.getParameter("endDay"))+1;
//				tempStr=request.getParameter("ajaxYear")+"-"+request.getParameter("ajaxMonth")+"-"+tempDay;
//			}
//			cartVO.setCheckOutDate(tempStr);
//			
//			Integer roomQty =new Integer(request.getParameter("roomQty"));
//			cartVO.setRoomQty(roomQty);
//			Integer roomTypeNo = new Integer(
//					request.getParameter("ajaxRoomTypeNo"));
//			cartVO.setRoomTypeNo(roomTypeNo);
//			RoomTypeService roomTypeServ=new RoomTypeService();
//			RoomTypeVO roomTypeVO=roomTypeServ.getOneRoomType(roomTypeNo);
//			cartVO.setTotalPrice(roomQty*roomTypeVO.getRoomTypePrice());
//			cartVO.setRoomTypeName(roomTypeVO.getRoomTypeName());
//			
//			boolean haveSame=false;
//			System.out.println("footAAA");
//			for(int i=0;i<cartList.size();i++){
//				OrderCartVO cartVOinList=cartList.get(i);
//				if((haveSame==false)&&(cartVO.getRoomTypeNo().equals(cartVOinList.getRoomTypeNo()))){
//					if((cartVO.getCheckInDate().equals(cartVOinList.getCheckInDate())) && (cartVO.getCheckOutDate().equals(cartVOinList.getCheckOutDate()))){
//					haveSame=true;
//					int tempQty=cartVOinList.getRoomQty();
//					cartVOinList.setRoomQty(tempQty+cartVO.getRoomQty());
//					cartList.set(i, cartVOinList);
//					}
//				}
//			}
//			if(haveSame==false){
//			cartList.add(cartVO);
//			}
//			
//		  session.setAttribute("cartList", cartList);
//			JSONArray array = new JSONArray();
//			for (OrderCartVO cart : cartList) {
//				JSONObject obj = new JSONObject();
//				try {
//					obj.put("roomTypeName", cart.getRoomTypeName());
//					obj.put("roomTypeNo", cart.getRoomTypeNo());
//					obj.put("roomQty", cart.getRoomQty());
//					obj.put("price", cart.getTotalPrice());
//					obj.put("checkInDate", cart.getCheckInDate());
//					obj.put("checkOutDate", cart.getCheckOutDate());
//				} catch (Exception e) {
//				}
//				array.add(obj);
//			}
//			System.out.println("array.toString() = "+array.toString());
//			response.setContentType("text/plain");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter out = response.getWriter();
//			out.write(array.toString());
//			out.flush();
//			out.close();
//		}
		
		if("getCartList".equalsIgnoreCase(action)){
			
			System.out.println("hi i am ajax getCartList");
			HttpSession session=request.getSession();
			List<OrderCartVO> cartList=null;
			if(session.getAttribute("cartList")==null){
			 cartList=new ArrayList<OrderCartVO>();
			}else{
				cartList=(List)session.getAttribute("cartList");
			}
			JSONArray array = new JSONArray();
			for (OrderCartVO cart : cartList) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("roomTypeName", cart.getRoomTypeName());
					obj.put("roomTypeNo", cart.getRoomTypeNo());
					obj.put("roomQty", cart.getRoomQty());
					obj.put("price", cart.getTotalPrice());
					obj.put("checkInDate", cart.getCheckInDate());
					obj.put("checkOutDate", cart.getCheckOutDate());
				} catch (Exception e) {
				}
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
		
		if("delCartList".equalsIgnoreCase(action)){
			int delIndex=new Integer(request.getParameter("delIndex"));
			
			System.out.println("hi iam delCartList");
			HttpSession session=request.getSession();
			List<OrderCartVO> cartList=null;
			if(session.getAttribute("cartList")==null){
			 cartList=new ArrayList<OrderCartVO>();
			}else{
				cartList=(List)session.getAttribute("cartList");
			}
			cartList.remove(delIndex);
			JSONArray array = new JSONArray();
			for (OrderCartVO cart : cartList) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("roomTypeName", cart.getRoomTypeName());
					obj.put("roomTypeNo", cart.getRoomTypeNo());
					obj.put("roomQty", cart.getRoomQty());
					obj.put("price", cart.getTotalPrice());
					obj.put("checkInDate", cart.getCheckInDate());
					obj.put("checkOutDate", cart.getCheckOutDate());
				} catch (Exception e) {
				}
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
