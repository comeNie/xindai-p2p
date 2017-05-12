package com.qfw.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.org.mozilla.javascript.internal.EcmaError;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.exception.BizException;
import com.qfw.platform.dao.ICmsNavigationDao;
import com.qfw.platform.model.bo.CmsNavigation;
import com.qfw.platform.service.ICmsNavigationService;

/**
 * CMS导航管理服务实现
 * 
 * @author Administrator
 * 
 */
@Service("cmsNavigationService")
public class CmsNavigationServiceImpl extends BaseServiceImpl implements
		ICmsNavigationService {

	@Autowired
	private ICommonQuery commonQuery;

	@Autowired
	private ICmsNavigationDao cmsNavigationDAO;

	@Override
	public List<CmsNavigation> findNavigationPagesByVO(
			CmsNavigation navigationVO, int first, int pageSize) {
		StringBuilder buffer = new StringBuilder(
				"select * from cms_navigation cn");
		buffer.append(VO2Condition(navigationVO));
		return commonQuery.findBySQLByPages(buffer.toString(), first, pageSize,
				CmsNavigation.class);
	}

	@Override
	public int findNavigationsCountByVO(CmsNavigation navigationVO) {
		StringBuilder buffer = new StringBuilder(
				"SELECT COUNT(id) FROM cms_navigation cn ");
		buffer.append(VO2Condition(navigationVO));
		return commonQuery.findCountBySQL(buffer, null);
	}

	@Override
	public CmsNavigation findNavigationsById(int id) {
		return (CmsNavigation) cmsNavigationDAO.findById(CmsNavigation.class,
				id);
	}

	public List<CmsNavigation> checkNavigationName(String navName, Integer id)
			throws BizException {
		if (StringUtils.isNotEmpty(navName)) {
			StringBuilder sb = new StringBuilder("from CmsNavigation where ");
			sb.append(" name = '").append(navName).append("'");
			if (id != null && !id.equals(Integer.valueOf(0))) {
				sb.append(" and id != ").append(id);
			}
			List<CmsNavigation> list = baseDAO.findByHQL(sb.toString());
			return list;
		} else {
			return null;
		}
	}

	private String VO2Condition(CmsNavigation navigationVO) {
		StringBuilder buffer = new StringBuilder(" where 1=1");
		if (null != navigationVO) {
			if (StringUtils.isNotEmpty(navigationVO.getName())) {
				buffer.append(" and cn.name like '%")
						.append(navigationVO.getName()).append("%'");
			}
		}
		return buffer.toString();
	}

	@Override
	public List<CmsNavigation> getTopNavigationList() {
		StringBuilder buffer = new StringBuilder("from CmsNavigation where ");
		buffer.append(" position = ").append(CmsNavigation.NAV_POSITION_TOP);
		buffer.append(" and isVisible = 1 order by sort");
		List<CmsNavigation> list = baseDAO.findByHQL(buffer.toString());
		return list;
	}

	@Override
	public List<CmsNavigation> getBottomNavigationList() {
		StringBuilder buffer = new StringBuilder("from CmsNavigation where ");
		buffer.append(" position = ").append(CmsNavigation.NAV_POSITION_BOTTOM);
		buffer.append(" and isVisible = 1 order by sort");
		List<CmsNavigation> list = baseDAO.findByHQL(buffer.toString());
		return list;
	}

	@Override
	public void deleteNavigationById(int id) throws Exception {
		String sql = "delete from cms_navigation where id = " + id;
		try{
			getJdbcTemplate().execute(sql);
		}catch(Exception e){
			throw e;
		}
	}
}