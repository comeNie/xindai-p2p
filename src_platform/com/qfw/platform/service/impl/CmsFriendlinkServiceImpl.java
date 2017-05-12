package com.qfw.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.exception.BizException;
import com.qfw.platform.dao.ICmsFriendlinkDao;
import com.qfw.platform.model.bo.CmsFriendLink;
import com.qfw.platform.service.ICmsFriendlinkService;

/**
 * CMS友情链接管理服务实现
 * 
 * @author Administrator
 * 
 */
@Service("cmsFriendlinkService")
public class CmsFriendlinkServiceImpl extends BaseServiceImpl implements
		ICmsFriendlinkService {

	@Autowired
	private ICommonQuery commonQuery;

	@Autowired
	private ICmsFriendlinkDao cmsFriendlinkDAO;

	@Override
	public List<CmsFriendLink> findFriendlinkPagesByVO(
			CmsFriendLink fiendLinkVO, int first, int pageSize) {
		StringBuilder buffer = new StringBuilder(
				"select * from cms_friendlink cf");
		buffer.append(VO2Condition(fiendLinkVO));
		return commonQuery.findBySQLByPages(buffer.toString(), first, pageSize,
				CmsFriendLink.class);
	}

	@Override
	public int findFriendlinkCountByVO(CmsFriendLink fiendLinkVO) {
		StringBuilder buffer = new StringBuilder(
				"SELECT COUNT(id) FROM cms_friendlink cf ");
		buffer.append(VO2Condition(fiendLinkVO));
		return commonQuery.findCountBySQL(buffer, null);
	}

	@Override
	public CmsFriendLink findFriendlinkById(int id) {
		return (CmsFriendLink) cmsFriendlinkDAO.findById(CmsFriendLink.class,
				id);
	}

	@Override
	public List<CmsFriendLink> checkFriendlinkName(String name, Integer id)
			throws BizException {
		if (StringUtils.isNotEmpty(name)) {
			StringBuilder sb = new StringBuilder("from CmsFriendLink where ");
			sb.append(" name = '").append(name).append("'");
			if (id != null && !id.equals(Integer.valueOf(0))) {
				sb.append(" and id != ").append(id);
			}
			List<CmsFriendLink> list = baseDAO.findByHQL(sb.toString());
			return list;
		} else {
			return null;
		}
	}

	private String VO2Condition(CmsFriendLink fiendLinkVO) {
		StringBuilder buffer = new StringBuilder(" where 1=1");
		if (null != fiendLinkVO) {
			if (StringUtils.isNotEmpty(fiendLinkVO.getName())) {
				buffer.append(" and cf.name like '%")
						.append(fiendLinkVO.getName()).append("%'");
			}
		}
		return buffer.toString();
	}

	@Override
	public List<CmsFriendLink> getFriendLinkList() {
		StringBuilder buffer = new StringBuilder("from CmsFriendLink where type = 0");
		buffer.append(" order by sort");
		List<CmsFriendLink> list = baseDAO.findByHQL(buffer.toString());
		return list;
	}
	
	@Override
	public List<CmsFriendLink> getCarouselList() {
		StringBuilder buffer = new StringBuilder("from CmsFriendLink where type = 1");
		buffer.append(" order by sort");
		List<CmsFriendLink> list = baseDAO.findByHQL(buffer.toString());
		return list;
	}
}