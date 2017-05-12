package com.qfw.platform.service;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.platform.model.bo.CmsCatalog;

/**
 * CMS栏目服务接口
 * 
 * @author Administrator
 * 
 */
public interface ICmsCatalogService extends IBaseService {

	public List<CmsCatalog> findCatalogPagesByVO(CmsCatalog cmsCatalogVO,
			int first, int pageSize);

	public int findCatalogCountByVO(CmsCatalog catalogVO);

	public CmsCatalog findCatalogById(int id);
	
	public void deleteCatalogById(int id) throws Exception;

	public List<CmsCatalog> checkCatalogName(String name, Integer id)
			throws BizException;

	public List<CmsCatalog> getCatalogList();

}