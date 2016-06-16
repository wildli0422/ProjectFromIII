/*本程式是使用Service產生器自動產生 copyRight:wildli0422@gmail.com 我愛一條柴*/
package com.ad.model;
import java.util.List;

public class AdService {

  private AdDAO_interface dao;

  public AdService() {
		dao = new AdDAO();
	}

	public AdVO addAd(Integer dealerNo,Integer odStatus,byte[] adPic,Integer odPrice,Integer adStatus,java.sql.Date adStartline,java.sql.Date adDeadline) {
AdVO adVO = new AdVO();

  adVO.setDealerNo(dealerNo);
  adVO.setOdStatus(odStatus);
  adVO.setAdPic(adPic);
  adVO.setOdPrice(odPrice);
  adVO.setAdStatus(adStatus);
  adVO.setAdStartline(adStartline);
  adVO.setAdDeadline(adDeadline);
		dao.insert(adVO);

		return adVO;
}

	public AdVO updateAd(Integer adNo,Integer dealerNo,java.sql.Date adOdDate,Integer odStatus,byte[] adPic,Integer odPrice,Integer adStatus,java.sql.Date adStartline,java.sql.Date adDeadline) {
AdVO adVO = new AdVO();

  adVO.setAdNo(adNo);
  adVO.setDealerNo(dealerNo);
  adVO.setAdOdDate(adOdDate);
  adVO.setOdStatus(odStatus);
  adVO.setAdPic(adPic);
  adVO.setOdPrice(odPrice);
  adVO.setAdStatus(adStatus);
  adVO.setAdStartline(adStartline);
  adVO.setAdDeadline(adDeadline);
		dao.update(adVO);

		return adVO;
}

	public void deleteAd(Integer adNo) {
		dao.delete(adNo);
	}
 public AdVO getOneAd(Integer adNo) {
return dao.findByPrimaryKey(adNo);
	}
 public List<AdVO> getAll() {
		return dao.getAll();
}
}
