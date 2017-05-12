/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: ICmsFriendlinkBSImpl.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月11日
 */
package com.qfw.bizservice.loan.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.loan.ICmsFriendlinkBS;
import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.loan.ICmsFriendLinkDAO;
import com.qfw.dao.loan.ILoanManageDAO;
import com.qfw.model.vo.loan.CmsFriendLinkVO;
import com.qfw.model.vo.loan.LoanApproveVO;

/**
 * 
 * @author: yangyang.wu
 * @version Revision: 0.0.1
 * @Date: 2015年7月11日
 */
@Service("cmsFriendlinkBS")
public class CmsFriendlinkBSImpl extends BaseServiceImpl implements ICmsFriendlinkBS {

	@Autowired
	private ICmsFriendLinkDAO cmsFriendlinkDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Override
	public List<CmsFriendLinkVO> findInfoByIDVO()
			throws BizException {
		try {
			 List<CmsFriendLinkVO> result= this.cmsFriendlinkDAO.findInfoByID();
			 if(null!=result && result.size()>0){
				 return result;
			 }
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
			throw new BizException(e);
		}
		return null;
	}	

}
