package com.viewphoto.model;

import java.util.*;

public interface ViewPhotoDAO_interface {
	
	public void insert(ViewPhotoVO viewphotoVO);
	public void update(ViewPhotoVO viewphotoVO);
	public void delete(Integer viewpicno);
	public ViewPhotoVO findByPrimaryKey(Integer viewpicno);
	
	public List<ViewPhotoVO> getOneViewpic();
	public List<ViewPhotoVO> getAll();
	public ViewPhotoVO findByViewNo(Integer viewNo);
	
	
	
	

}
