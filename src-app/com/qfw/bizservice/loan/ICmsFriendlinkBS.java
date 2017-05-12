/**
// * Copyright 2015 Software innovation and R & D center. All rights reserved.
// * File Name: ICmsFriendlinkBS.java
// * Encoding UTF-8
// * Version: 0.0.1
// * History:	2015年7月11日
// */
package com.qfw.bizservice.loan;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.loan.CmsFriendLinkVO;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanSearchVO;

/**
 * 友情链接管理BS
 * @author: yangyang.wu(改成自己的名字)
 * @version Revision: 0.0.1
 * @Date: 2015年7月11日
 */
public interface ICmsFriendlinkBS extends IBaseService {
	/**
	 * 分页查询(投资列表)
	 * @author 535
	 * @return
	 * @throws BizException
	 */
	public List<CmsFriendLinkVO> findInfoByIDVO () throws BizException;
	
	
}
