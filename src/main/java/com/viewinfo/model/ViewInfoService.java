package com.viewinfo.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.viewphoto.model.ViewPhotoVO;
import com.viewlist.model.ViewListVO;

public class ViewInfoService{
	
	private ViewInfoDAO_interface dao;
	
	public ViewInfoService(){
		dao =new ViewInfoDAO();
	}
	
	public ViewInfoVO addViewInfo(String viewname, String viewmanager, String viewphone, String viewaddress, String viewweb, 
			Double viewlon, Double viewlat, String viewopen, String viewticket, String viewequi, String viewcontent  ){
		
		ViewInfoVO viewinfoVO=new ViewInfoVO();
		
		viewinfoVO.setViewname(viewname);
		viewinfoVO.setViewmanager(viewmanager);
		viewinfoVO.setViewphone(viewphone);
		viewinfoVO.setViewaddress(viewaddress);
		viewinfoVO.setViewweb(viewweb);
		viewinfoVO.setViewlon(viewlon);
		viewinfoVO.setViewlat(viewlat);
		viewinfoVO.setViewopen(viewopen);
		viewinfoVO.setViewticket(viewticket);
		viewinfoVO.setViewequi(viewequi);
		viewinfoVO.setViewcontent(viewcontent);
//		System.out.println(viewname+viewmanager+viewphone+viewaddress+viewweb+viewlon+viewlat+viewopen+viewticket+viewequi+viewcontent);
		dao.insert(viewinfoVO);
		
		return viewinfoVO;
	}
	
	public ViewInfoVO updateViewInfo(Integer viewno, String viewname, String viewmanager, String viewphone, String viewaddress, String viewweb, 
			Double viewlon, Double viewlat, String viewopen, String viewticket, String viewequi, String viewcontent ){
				
	 	ViewInfoVO viewinfoVO = new ViewInfoVO();
	 	
	 	viewinfoVO.setViewno(viewno);
	 	viewinfoVO.setViewname(viewname);
		viewinfoVO.setViewmanager(viewmanager);
		viewinfoVO.setViewphone(viewphone);
		viewinfoVO.setViewaddress(viewaddress);
		viewinfoVO.setViewweb(viewweb);
		viewinfoVO.setViewlon(viewlon);
		viewinfoVO.setViewlat(viewlat);
		viewinfoVO.setViewopen(viewopen);
		viewinfoVO.setViewticket(viewticket);
		viewinfoVO.setViewequi(viewequi);
		viewinfoVO.setViewcontent(viewcontent);
		
		dao.update(viewinfoVO);
		
		
		return viewinfoVO;
		
		
	}
	public List<ViewInfoVO> likeByColumn(String columnName,String likeString){
		 return dao.likeByColumn(columnName, likeString);
	}
	

	public void deleteViewInfo(Integer viewno) {
		dao.delete(viewno);
	}

	public ViewInfoVO getOneViewInfo(Integer viewno) {
		return dao.findByPrimaryKey(viewno);
	}

	public List<ViewInfoVO> getAll() {
		return dao.getAll();
	}
	
	//�B�z�@��h 
	public Set<ViewPhotoVO> getViewPhotoByViewno(Integer viewno){
		return dao.getViewPhotoByViewno(viewno);
	}
	public Set<ViewListVO> getViewListByViewno(Integer viewno){
		return dao.getViewListByViewno(viewno);
	}
	
	public List<ViewInfoVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	
	public ViewInfoVO getLastOneViewInfo() {
		return dao.getLastOneViewInfo();
	}
	
	
	
} 