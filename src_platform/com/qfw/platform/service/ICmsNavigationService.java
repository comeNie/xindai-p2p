package com.qfw.platform.service;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.platform.model.bo.CmsNavigation;

/**
 * CMS导航管理服务接口
 * 
 * @author Administrator
 * 
 */
public interface ICmsNavigationService extends IBaseService {

	public List<CmsNavigation> findNavigationPagesByVO(
			CmsNavigation navigationVO, int first, int pageSize);

	public int findNavigationsCountByVO(CmsNavigation navigationVO);

	public CmsNavigation findNavigationsById(int id);

	public void deleteNavigationById(int id) throws Exception;

	public List<CmsNavigation> checkNavigationName(String navName, Integer id)
			throws BizException;

	/**
	 * 获取顶部的导航列表
	 * 
	 * @param position
	 * @return
	 */
	public List<CmsNavigation> getTopNavigationList();

	/**
	 * 获取底部的导航列表
	 * 
	 * @param position
	 * @return
	 */
	public List<CmsNavigation> getBottomNavigationList();

}