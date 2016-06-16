package com.viewlist.model;

import java.util.List;
import java.util.Set;

public class ViewListService {
	
	private ViewListDAO_interface dao;

	public ViewListService() {
		dao = new ViewListDAO();
	}
	
	public ViewListVO addViewList(Integer tenantNo, Integer viewno) {

		ViewListVO viewlistVO = new ViewListVO();

		viewlistVO.setTenantNo(tenantNo);
		viewlistVO.setViewno(viewno);
		//viewlistVO.setViewlistdate(viewlistdate);
		
		dao.insert(viewlistVO);

		return viewlistVO;

	}
	
	public ViewListVO updateViewList(Integer viewlistno, Integer tenantNo, Integer viewno, java.sql.Date viewlistdate) {

		ViewListVO viewlistVO = new ViewListVO();

		viewlistVO.setViewlistno(viewlistno);
		viewlistVO.setTenantNo(tenantNo);
		viewlistVO.setViewno(viewno);
		viewlistVO.setViewlistdate(viewlistdate);
		
		dao.update(viewlistVO);

		return viewlistVO;
	}
	
	public void deleteViewList(Integer viewlistno) {
		dao.delete(viewlistno);
	}
	
	public ViewListVO getOneViewList(Integer viewlistno) {
		return dao.findByPrimaryKey(viewlistno);
	}
	
	public List<ViewListVO> getAll() {
		return dao.getAll();
	}
	

	public Set<ViewListVO> getViewListByTenantno(Integer tenantNo)
	{
		return dao.getViewListByTenantno(tenantNo);
	}
	
	
	
	
}