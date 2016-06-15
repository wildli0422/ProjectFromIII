package com.hostelPic.model;

import java.util.List;

public interface HostelPicDAO_interface {
	public void insert(HostelPicVO hostelPicVO);
    public void update(HostelPicVO hostelPicVO);
    public void delete(Integer hostelPicNo);
    public HostelPicVO findByPrimaryKey(Integer hostelPicNo);
    public List<HostelPicVO> getAll();
}
