package com.tenant.model;

import java.util.List;
import java.util.Map;

public class TenantService {

	private TenantDAO_interface dao;

	public TenantService() {
		dao = new TenantDAO();
	}

	public TenantVO addTenant(String tenantMail, String tenantPassword,
			String tenantName, String tenantSex, String tenantAddress,
			String tenantPhone, byte[] tenantPic) {
		TenantVO tenantVO = new TenantVO();

		tenantVO.setTenantMail(tenantMail);
		tenantVO.setTenantPassword(tenantPassword);
		tenantVO.setTenantName(tenantName);
		tenantVO.setTenantSex(tenantSex);
		tenantVO.setTenantAddress(tenantAddress);
		tenantVO.setTenantPhone(tenantPhone);
		tenantVO.setTenantPic(tenantPic);
		dao.insert(tenantVO);

		return tenantVO;
	}

	public TenantVO updateTenant(Integer tenantNo, String tenantMail,
			String tenantPassword, String tenantName, String tenantSex,
			String tenantAddress, String tenantPhone, byte[] tenantPic,
			java.sql.Date registerDate) {
		TenantVO tenantVO = new TenantVO();

		tenantVO.setTenantNo(tenantNo);
		tenantVO.setTenantMail(tenantMail);
		tenantVO.setTenantPassword(tenantPassword);
		tenantVO.setTenantName(tenantName);
		tenantVO.setTenantSex(tenantSex);
		tenantVO.setTenantAddress(tenantAddress);
		tenantVO.setTenantPhone(tenantPhone);
		tenantVO.setTenantPic(tenantPic);
		tenantVO.setRegisterDate(registerDate);
		dao.update(tenantVO);

		return tenantVO;
	}

	public void deleteTenant(Integer tenantNo) {
		dao.delete(tenantNo);
	}

	public TenantVO getOneTenant(Integer tenantNo) {
		return dao.findByPrimaryKey(tenantNo);
	}

	public List<TenantVO> getAll() {
		return dao.getAll();
	}

	// //////////Something NEW///////////
	public List<TenantVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}

	public Integer getTenantNo(String tenantMail) {
		return dao.findTenantNo(tenantMail);
	}

	public String getTenantPassword(String tenantMail) {
		return dao.findTenantPassword(tenantMail);
	}

	public List<TenantVO> addThisMonth() {
		return dao.addThisMonth();
	}

	public Integer getScoreAvg(Integer tenantNo) {
		return dao.getScoreAvg(tenantNo);
	}

	public Integer countScore(Integer tenantNo, Integer tenantScore) {
		return dao.countScore(tenantNo, tenantScore);
	}

	public Integer toDate(Integer tenantNo) {
		return dao.toDate(tenantNo);
	}

	public Integer travelThisMonth(Integer tenantNo) {
		return dao.travelThisMonth(tenantNo);
	}

	public void update_pic_bk(TenantVO tenantVo) {
		dao.update_pic_bk(tenantVo);
	}
	public List<TenantVO> getListByName(String tenantName) {
		return dao.getListByName(tenantName);
	}
	
	public List<TenantVO> getListByAddress(String tenantAddress) {
		return dao.getListByAddress(tenantAddress);
	}
	

}
