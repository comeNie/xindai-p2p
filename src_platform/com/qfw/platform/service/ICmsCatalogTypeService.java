package com.qfw.platform.service;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.platform.model.bo.CmsCatalogType;

/**
 * CMS栏目分类管理服务接口
 * 
 * @author Administrator
 * 
 */
public interface ICmsCatalogTypeService extends IBaseService {

	public List<CmsCatalogType> findCatalogTypePagesByVO(
			CmsCatalogType catalogTypeVO, int first, int pageSize);

	public int findCatalogTypeCountByVO(CmsCatalogType catalogTypeVO);

	public CmsCatalogType findCatalogTypeById(int id);
	
	public void deleteCatalogTypeById(int id) throws Exception;

	public List<CmsCatalogType> checkCatalogTypeName(String name, Integer id)
			throws BizException;

	public List<CmsCatalogType> getCatalogTypeList();

}