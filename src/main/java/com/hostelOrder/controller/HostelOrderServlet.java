package com.hostelOrder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hostelOrder.model.HostelOrderService;
import com.hostelOrder.model.HostelOrderVO;
import com.roomType.model.RoomTypeService;
import com.tenant.model.TenantVO;

import tool.cart.model.OrderCartVO;


@WebServlet("/controller/HostelOrderServlet")
public class HostelOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		TenantVO tenantVO=(TenantVO)session.getAttribute("tenantVO");
		Integer tenantNo=tenantVO.getTenantNo();
		String paymentWay=request.getParameter("paymentWay");
		System.out.println("paymentWay = "+paymentWay);
		List<OrderCartVO> cartList=null;
		if(session.getAttribute("cartList")==null){
		 cartList=new ArrayList<OrderCartVO>();
		}else{
			cartList=(List)session.getAttribute("cartList");
		}
		int totalCustomer=0;
		RoomTypeService roomTypeService =new RoomTypeService();
		for(int i=0;i<cartList.size();i++){
			OrderCartVO nowCart=cartList.get(i);
			int contain=roomTypeService.getOneRoomType(nowCart.getRoomTypeNo()).getRoomTypeContain();
			totalCustomer+=contain*nowCart.getRoomQty();
			System.out.println("inDate = "+nowCart.getCheckInDate());
			System.out.println("outDate = "+nowCart.getCheckOutDate());
		}
		Integer hostelNo=new Integer(request.getParameter("hostelNo"));
		HostelOrderService hostelOrderServ =new HostelOrderService();
		HostelOrderVO hostelOrderVO=new HostelOrderVO();
		hostelOrderVO.setHostelNo(hostelNo);
		hostelOrderVO.setTenantNo(tenantNo);
		hostelOrderVO.setCustomerQuantity(totalCustomer);
		hostelOrderVO.setPaymentWay(paymentWay);
		
	 int hostelOrderNo=	hostelOrderServ.orderInvoke(hostelOrderVO, cartList);
		System.out.println("hostelOrderNo = "+ hostelOrderNo);
		request.setAttribute("hostelOrderNo", hostelOrderNo);
		session.removeAttribute("cartList");
		RequestDispatcher patcher= request.getRequestDispatcher("/order/orderOk.jsp");
		patcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
