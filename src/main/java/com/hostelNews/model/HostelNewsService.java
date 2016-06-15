/*本程式是使用Service產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.hostelNews.model;

import java.util.List;

public class HostelNewsService {

	private HostelNewsDAO_interface dao;

	public HostelNewsService() {
		dao = new HostelNewsDAO();
	}

	public HostelNewsVO addHostelNews(Integer hostelNo, String newsContent) {
		HostelNewsVO hostelNewsVO = new HostelNewsVO();

		hostelNewsVO.setHostelNo(hostelNo);
		hostelNewsVO.setNewsContent(newsContent);
		dao.insert(hostelNewsVO);

		return hostelNewsVO;
	}

	public HostelNewsVO updateHostelNews(Integer hostelNewsNo,
			Integer hostelNo, String newsContent, java.sql.Date updateDate) {
		HostelNewsVO hostelNewsVO = new HostelNewsVO();

		hostelNewsVO.setHostelNewsNo(hostelNewsNo);
		hostelNewsVO.setHostelNo(hostelNo);
		hostelNewsVO.setNewsContent(newsContent);
		hostelNewsVO.setUpdateDate(updateDate);
		dao.update(hostelNewsVO);

		return hostelNewsVO;
	}

	public void deleteHostelNews(Integer hostelNewsNo) {
		dao.delete(hostelNewsNo);
	}

	public HostelNewsVO getOneHostelNews(Integer hostelNewsNo) {
		return dao.findByPrimaryKey(hostelNewsNo);
	}

	public List<HostelNewsVO> getAll() {
		return dao.getAll();
	}

	public List<HostelNewsVO> getAllByHostelNo(Integer hostelNo) {
		return dao.getAllByHostelNo(hostelNo);

	}
}
