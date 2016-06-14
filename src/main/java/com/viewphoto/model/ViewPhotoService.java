package com.viewphoto.model;

import java.util.List;

import com.viewphoto.model.ViewPhotoVO;



public class ViewPhotoService {
	
	private ViewPhotoDAO_interface dao;

	public ViewPhotoService() {
		dao = new ViewPhotoDAO();
	}

	public ViewPhotoVO addViewPhoto(Integer viewno, byte[] viewpic) {

		ViewPhotoVO viewphotoVO = new ViewPhotoVO();

		viewphotoVO.setViewno(viewno);
		viewphotoVO.setViewpic(viewpic);
		dao.insert(viewphotoVO);

		return viewphotoVO;
	}

	public ViewPhotoVO updateViewPhoto(Integer viewpicno ,Integer viewno, byte[] viewpic) {

		ViewPhotoVO viewphotoVO = new ViewPhotoVO();

		viewphotoVO.setViewpicno(viewpicno);
		viewphotoVO.setViewno(viewno);
		viewphotoVO.setViewpic(viewpic);
		dao.update(viewphotoVO);

		return viewphotoVO;
	}

	public void deleteViewPhoto(Integer viewpicno) {
		dao.delete(viewpicno);
	}

	public ViewPhotoVO getOneViewPhoto(Integer viewpicno) {
		return dao.findByPrimaryKey(viewpicno);
	}

	public List<ViewPhotoVO> getAll() {
		return dao.getAll();
	}
	
	public List<ViewPhotoVO> getOneViewpic() {
		return dao.getOneViewpic();
	}

	 public ViewPhotoVO getOneByViewNo(Integer viewNo) {
		 return dao.findByViewNo(viewNo);
		 	}
}
