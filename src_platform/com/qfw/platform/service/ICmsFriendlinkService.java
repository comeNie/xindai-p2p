package com.qfw.platform.service;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.platform.model.bo.CmsFriendLink;

/**
 * CMS友情链接管理服务接口
 * 
 * @author Administrator
 * 
 */
public interface ICmsFriendlinkService extends IBaseService {

	public List<CmsFriendLink> findFriendlinkPagesByVO(
			CmsFriendLink friendlinkVO, int first, int pageSize);

	public int findFriendlinkCountByVO(CmsFriendLink friendlinkVO);

	public CmsFriendLink findFriendlinkById(int id);

	public List<CmsFriendLink> checkFriendlinkName(String navName, Integer id)
			throws BizException;

	/**
	 * 获取友情链接列表
	 * 
	 * @return
	 */
	public List<CmsFriendLink> getFriendLinkList();
	
	
	/**
	 * 获得轮播图片
	 */
	public List<CmsFriendLink> getCarouselList();

}