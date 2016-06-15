package com.viewinfo.model;

import java.util.*;

import com.viewphoto.model.ViewPhotoVO;
import com.viewlist.model.ViewListVO;

public interface ViewInfoDAO_interface {
	
	public void insert(ViewInfoVO viewinfoVO);
    public void update(ViewInfoVO viewinfoVO);
    public void delete(Integer viewno);
    public ViewInfoVO findByPrimaryKey(Integer viewno);
    public List<ViewInfoVO> getAll();
    
    //�d�߬Y���I���Ӥ�(�@��h)(�^��set)
    public Set<ViewPhotoVO> getViewPhotoByViewno(Integer viewno);
   
    //�d�߬Y���I�����I���òM��(�@��h)(�^��set)
    public Set<ViewListVO> getViewListByViewno(Integer viewno);
    
    //�d�̫߳�@���s�W�����I
    public ViewInfoVO getLastOneViewInfo();
    
    public List<ViewInfoVO> likeByColumn(String columnName,String likeString);
    
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
  public List<ViewInfoVO> getAll(Map<String, String[]> map); 

}
