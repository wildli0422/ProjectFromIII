package com.hostelPic.model;

import java.util.List;


public class HostelPicService {
	private HostelPicDAO_interface dao;
	
	public HostelPicService(){
		dao=new HostelPicDAO();
	}
	
	public HostelPicVO addHostelPic(Integer hostelNo,byte[] hostelPhoto){
		HostelPicVO hostelPicVO=new HostelPicVO();
		
		hostelPicVO.setHostelNo(hostelNo);
		hostelPicVO.setHostelPhoto(hostelPhoto);
		
		dao.insert(hostelPicVO);
		
		return hostelPicVO;
	}
	
	public HostelPicVO updateHostelPicVO(Integer hostelPicNo,Integer hostelNo
			,byte[] hostelPhoto){
		
		HostelPicVO hostelPicVO=new HostelPicVO();
		
		hostelPicVO.setHostelPicNo(hostelPicNo);
		hostelPicVO.setHostelNo(hostelNo);
		hostelPicVO.setHostelPhoto(hostelPhoto);
		
		dao.update(hostelPicVO);
		
		return hostelPicVO;
	}
	
	public void deleteHostelPic(Integer hostelPicNo){
		dao.delete(hostelPicNo);
	}
	
	public List<HostelPicVO> getAll(){
		return dao.getAll();
	}
	
	public HostelPicVO getOneHostelPic(Integer hostelPicNo){
		return dao.findByPrimaryKey(hostelPicNo);
	}
	
}
