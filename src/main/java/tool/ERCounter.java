package tool;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tool.emptyRoom.model.EmptyRoomVO;

import com.*;
import com.hostelOrderDetail.model.HostelOrderDetailService;
import com.hostelOrderDetail.model.HostelOrderDetailVO;
import com.room.model.RoomService;
public class ERCounter {

		public  List<EmptyRoomVO> countER(Integer roomTypeNo,Date startDate,Date endDate){
			Calendar nowCal=Calendar.getInstance();
			nowCal.setTime(startDate);
			List<Integer> erList=new ArrayList<Integer>();
			int dayGap=getDayGap(startDate, endDate);
			System.out.println("dayGap = "+dayGap);
			RoomService roomServ=new RoomService();
			int roomQty=	roomServ.getAllByRoomTypeNo(roomTypeNo).size();		
			HostelOrderDetailService hodServ=new HostelOrderDetailService();
			List<HostelOrderDetailVO> hodList=hodServ.getAllByRoomTypeNo(roomTypeNo);
			
			
			for(int i=0;i<=dayGap;i++){
				erList.add(roomQty);
			}
			
			
			for(int i=0;i<=dayGap;i++){
				for(int j=0;j<hodList.size();j++){
					HostelOrderDetailVO hodVO=hodList.get(j);
					if(isSameDay(nowCal, new Date(hodVO.getCheckInDate().getTime()))){
						for(int k=0;k<getDayGap(hodVO.getCheckInDate(), hodVO.getCheckOutDate());k++){
							int temp=0;
							//如碰到最後一天有連續性訂單則會out of bond 
							try{
								temp=erList.get(i+k);
								erList.set(i+k, temp-hodVO.getRoomQuantity());
							}catch(IndexOutOfBoundsException e){
								System.out.println("order date :"+hodVO.getCheckOutDate() +"meet last day !");
							}
							
						}
					}
				}
				nowCal.add(Calendar.DATE, 1); //查詢日期內每次前進一天
			}
			List<EmptyRoomVO> erVOList=new ArrayList<EmptyRoomVO>();
			nowCal.setTime(startDate);
			
			for(int i=0;i<=dayGap;i++){
				EmptyRoomVO erVO=new EmptyRoomVO();
				String nowDate=nowCal.get(Calendar.YEAR)+"-"+(nowCal.get(Calendar.MONTH)+1)+"-"+nowCal.get(Calendar.DATE);
//				System.out.println("nowDate = "+nowDate);
				erVO.setNowDate(nowDate);
				erVO.setEmptyRoomQty(erList.get(i));
				erVOList.add(erVO);
				nowCal.add(Calendar.DATE, 1);
			}
			
//			for(int i=0;i<erVOList.size();i++){
//				EmptyRoomVO erVO=erVOList.get(i);
//				System.out.println("erVO  nowDate ="+erVO.getNowDate()+"  erQTY = "+erVO.getEmptyRoomQty());
//			}

			return erVOList;
		}
		protected boolean isSameDay(Calendar cal1,Date date) {
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date);
			return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)&&
					cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		}
		
		protected boolean isSameDay(Date startDate,Date endDate) {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(startDate);
			cal2.setTime(endDate);
			return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)&&
					cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		}
		
		protected int getDayGap(Date startDate,Date endDate) {
			int dayGap=0;
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			startCal.setTime(startDate);
			endCal.setTime(endDate);
			if(startCal.get(Calendar.YEAR)==endCal.get(Calendar.YEAR)){
					return Math.abs(endCal.get(Calendar.DAY_OF_YEAR)-startCal.get(Calendar.DAY_OF_YEAR));
			}else if(endCal.get(Calendar.YEAR)-startCal.get(Calendar.YEAR)>0){
					while(startCal.get(Calendar.YEAR)!=endCal.get(Calendar.YEAR)){
						startCal.add(Calendar.DATE, 1);
						dayGap++;
					}
					if(startCal.get(Calendar.YEAR)==endCal.get(Calendar.YEAR)){
						return dayGap+endCal.get(Calendar.DAY_OF_YEAR);
					}
			}else{
					while(startCal.get(Calendar.YEAR)!=endCal.get(Calendar.YEAR)){
						endCal.add(Calendar.DATE, 1);
						dayGap++;
					}
					if(startCal.get(Calendar.YEAR)==endCal.get(Calendar.YEAR)){
						return dayGap+startCal.get(Calendar.DAY_OF_YEAR);
					}
			}
			return 0;
		}
		
		
		protected int getDayGap(Timestamp startDate,Timestamp endDate) {
			int dayGap=0;
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			startCal.setTime(startDate);
			endCal.setTime(endDate);
			if(startCal.get(Calendar.YEAR)==endCal.get(Calendar.YEAR)){
					return Math.abs(endCal.get(Calendar.DAY_OF_YEAR)-startCal.get(Calendar.DAY_OF_YEAR));
			}else if(endCal.get(Calendar.YEAR)-startCal.get(Calendar.YEAR)>0){
					while(startCal.get(Calendar.YEAR)!=endCal.get(Calendar.YEAR)){
						startCal.add(Calendar.DATE, 1);
						dayGap++;
					}
					if(startCal.get(Calendar.YEAR)==endCal.get(Calendar.YEAR)){
						return dayGap+endCal.get(Calendar.DAY_OF_YEAR);
					}
			}else{
					while(startCal.get(Calendar.YEAR)!=endCal.get(Calendar.YEAR)){
						endCal.add(Calendar.DATE, 1);
						dayGap++;
					}
					if(startCal.get(Calendar.YEAR)==endCal.get(Calendar.YEAR)){
						return dayGap+startCal.get(Calendar.DAY_OF_YEAR);
					}
			}
			return 0;
		}
}
