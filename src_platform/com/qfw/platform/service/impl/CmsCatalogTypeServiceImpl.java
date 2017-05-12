package com.qfw.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.exception.BizException;
import com.qfw.platform.dao.ICmsCatalogTypeDao;
import com.qfw.platform.model.bo.CmsCatalogType;
import com.qfw.platform.service.ICmsCatalogTypeService;

/**
 * CMS栏目分类服务实现
 * 
 * @author Administrator
 * 
 */
@Service("cmsCatalogTypeService")
public class CmsCatalogTypeServiceImpl extends BaseServiceImpl implements
		ICmsCatalogTypeService {

	@Autowired
	private ICommonQuery commonQuery;

	@Autowired
	private ICmsCatalogTypeDao cmsCatalogTypeDAO;

	@Override
	public List<CmsCatalogType> findCatalogTypePagesByVO(
			CmsCatalogType catalogTypeVO, int first, int pageSize) {
		StringBuilder buffer = new StringBuilder("select * from cms_catalog_type cct");
		buffer.append(VO2Condition(catalogTypeVO));
		return commonQuery.findBySQLByPages(buffer.toString(), first, pageSize,
				CmsCatalogType.class);
	}

	@Override
	public int findCatalogTypeCountByVO(CmsCatalogType catalogTypeVO) {
		StringBuilder buffer = new StringBuilder(
				"SELECT COUNT(id) FROM cms_catalog_type cct ");
		buffer.append(VO2Condition(catalogTypeVO));
		return commonQuery.findCountBySQL(buffer, null);
	}

	@Override
	public CmsCatalogType findCatalogTypeById(int id) {
		return (CmsCatalogType) cmsCatalogTypeDAO.findById(CmsCatalogType.class, id);
	}

	@Override
	public List<CmsCatalogType> checkCatalogTypeName(String name, Integer id)
			throws BizException {
		if (StringUtils.isNotEmpty(name)) {
			StringBuilder sb = new StringBuilder("from CmsCatalogType where ");
			sb.append(" name = '").append(name).append("'");
			if (id != null && !id.equals(Integer.valueOf(0))) {
				sb.append(" and id != ").append(id);
			}
			List<CmsCatalogType> list = baseDAO.findByHQL(sb.toString());
			return list;
		} else {
			return null;
		}
	}

	private String VO2Condition(CmsCatalogType catalogTypeVO) {
		StringBuilder buffer = new StringBuilder(" where 1=1");
		if (null != catalogTypeVO) {
			if (StringUtils.isNotEmpty(catalogTypeVO.getName())) {
				buffer.append(" and cct.name like '%")
						.append(catalogTypeVO.getName()).append("%'");
			}
		}
		return buffer.toString();
	}

	@Override
	public List<CmsCatalogType> getCatalogTypeList() {
		StringBuilder buffer = new StringBuilder("from CmsCatalogType");
		buffer.append(" order by sort");
		List<CmsCatalogType> list = baseDAO.findByHQL(buffer.toString());
		return list;
	}

	@Override
	public void deleteCatalogTypeById(int id) throws Exception {
		String sql = "delete from cms_catalog_type where id =  " + id;
		try {
			getJdbcTemplate().execute(sql);
		} catch (Exception e) {
			throw e;
		}
	}
}