/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: CmsFriendlinkDAOImpl.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月11日
 */
package com.qfw.dao.loan.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.loan.ICmsFriendLinkDAO;
import com.qfw.model.vo.loan.CmsFriendLinkVO;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;
import com.qfw.platform.model.bo.CmsFriendLink;

/**
 * 
 * @author: yangyang.wu
 * @version Revision: 0.0.1
 * @Date: 2015年7月11日
 */
@Repository("cmsFriendLinkDAO")
public class CmsFriendLinkDAOImpl extends BaseDAOImpl implements ICmsFriendLinkDAO {
	@Autowired
	private ICommonQuery commonQuery;
	@Override
	public List<CmsFriendLinkVO> findInfoByID() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from cms_friendlink");
		List<CmsFriendLinkVO> list = this.commonQuery.findBySQL(sql.toString());
		
		return list;
	}

}
