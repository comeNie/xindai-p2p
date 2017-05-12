package com.qfw.bizservice.creditor.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.creditor.ICreditorCountManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.creditor.ICreditorCountManageDAO;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.model.vo.creditor.CreditorCountVO;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;

/**
 * 投资统计管理服务
 *
 */
@Service("creditorCountManageBS")
public class CreditorCountManageBSImpl extends BaseServiceImpl implements ICreditorCountManageBS {
	
	@Autowired
	@Qualifier("baseDAO")
	private IBaseDAO baseDAO;	
	@Autowired
	private ICreditorCountManageDAO creditorCountManageDAO;
	@Autowired
    private JbpmService jbpmService;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public List<CreditorCountVO> findInfoPagesByVO(CreditorSearchVO searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<CreditorCountVO> result= this.creditorCountManageDAO.findInfoPagesByVO(searchVO, first, pageSize);
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
	
	@Override
	public int getCountByVO(CreditorSearchVO searchVO) {
		int num = 0;
		try {
			num = this.creditorCountManageDAO.getCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}
	
}
