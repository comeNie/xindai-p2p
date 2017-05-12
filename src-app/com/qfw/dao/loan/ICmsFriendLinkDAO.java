/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: ICmsFriendlinkDAO.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月11日
 */
package com.qfw.dao.loan;

import java.io.Serializable;
import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.loan.CmsFriendLinkVO;


/**
 * 
 * @author:yangyang.wu
 * @version Revision: 0.0.1
 * @Date: 2015年7月11日
 */
public interface ICmsFriendLinkDAO extends IBaseDAO{
	/**
	 * 根据类型查询信息
	 * @param loanApproveId
	 * @return
	 */
	public List<CmsFriendLinkVO> findInfoByID() ;
	


}
