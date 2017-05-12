package com.qfw.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.exception.BizException;
import com.qfw.platform.dao.ICmsCatalogDao;
import com.qfw.platform.model.bo.CmsCatalog;
import com.qfw.platform.service.ICmsCatalogService;

/**
 * CMS栏目管理服务实现
 * 
 * @author Administrator
 * 
 */
@Service("cmsCatalogService")
public class CmsCatalogServiceImpl extends BaseServiceImpl implements
		ICmsCatalogService {

	@Autowired
	private ICommonQuery commonQuery;

	@Autowired
	private ICmsCatalogDao cmsCatalogDAO;

	@Override
	public List<CmsCatalog> findCatalogPagesByVO(CmsCatalog catalogVO,
			int first, int pageSize) {
		StringBuilder buffer = new StringBuilder("select * from cms_catalog cc");
		buffer.append(VO2Condition(catalogVO));
		return commonQuery.findBySQLByPages(buffer.toString(), first, pageSize,
				CmsCatalog.class);
	}

	@Override
	public int findCatalogCountByVO(CmsCatalog catalogVO) {
		StringBuilder buffer = new StringBuilder(
				"SELECT COUNT(id) FROM cms_catalog cc ");
		buffer.append(VO2Condition(catalogVO));
		return commonQuery.findCountBySQL(buffer, null);
	}

	@Override
	public CmsCatalog findCatalogById(int id) {
		return (CmsCatalog) cmsCatalogDAO.findById(CmsCatalog.class, id);
	}

	@Override
	public List<CmsCatalog> checkCatalogName(String title, Integer id)
			throws BizException {
		if (StringUtils.isNotEmpty(title)) {
			StringBuilder sb = new StringBuilder("from CmsCatalog where ");
			sb.append(" title = '").append(title).append("'");
			if (id != null && !id.equals(Integer.valueOf(0))) {
				sb.append(" and id != ").append(id);
			}
			List<CmsCatalog> list = baseDAO.findByHQL(sb.toString());
			return list;
		} else {
			return null;
		}
	}

	private String VO2Condition(CmsCatalog catalogVO) {
		StringBuilder buffer = new StringBuilder(" where 1=1");
		if (null != catalogVO) {
			if (StringUtils.isNotEmpty(catalogVO.getTitle())) {
				buffer.append(" and cc.title like '%")
						.append(catalogVO.getTitle()).append("%'");
			}
		}
		return buffer.toString();
	}

	@Override
	public List<CmsCatalog> getCatalogList() {
		StringBuilder buffer = new StringBuilder("from CmsCatalog");
		buffer.append(" order by sort");
		List<CmsCatalog> list = baseDAO.findByHQL(buffer.toString());
		return list;
	}

	@Override
	public void deleteCatalogById(int id) throws Exception {
		String sql = "delete from cms_catalog where id = " + id;
		try{
			getJdbcTemplate().execute(sql);
		}catch(Exception e){
			throw e;
		}		
	}
}