package com.qfw.bizservice.message.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.credit.report.ICreditReportBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.merge.IMergeApplyBS;
import com.qfw.bizservice.message.IMessageBoardBS;
import com.qfw.bizservice.message.IMessageManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IRoleBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.custinfo.ICustInfoDAO;
import com.qfw.dao.message.IMessageBoardDAO;
import com.qfw.dao.message.IMessageManageDAO;
import com.qfw.jbpm.bizservice.IFlowBS;
import com.qfw.jbpm.model.JbpmConst;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.bo.BizMessageBoardBO;
import com.qfw.model.bo.BizMessageInfoBO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.loan.LoanApplyVO;
import com.qfw.model.vo.message.MessageBoardVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;

@Service("messageBoardBS")
public class MessageBoardBSImpl extends BaseServiceImpl implements IMessageBoardBS {

	private Logger log = Logger.getLogger(MessageManageBSImpl.class);

	@Autowired
	private IMessageBoardDAO messageBoardDAO;

	@Override
	public void deleteMsg(Integer MsgId) throws BizException {
		BizMessageBoardBO msgBO = (BizMessageBoardBO)this.messageBoardDAO.findById(BizMessageBoardBO.class, MsgId);
		this.messageBoardDAO.delete(msgBO);
	}

	@Override
	public List<MessageBoardVO> findInfoPagesByLinkId(Integer linkId,
			int first, int pageSize) throws BizException {
		// TODO Auto-generated method stub
		return messageBoardDAO.findInfoPagesByLinkId(linkId, first, pageSize);
	}

	@Override
	public List<MessageBoardVO> findInfoPagesByLoanId(Integer loadid,
			int first, int pageSize) throws BizException {
		// TODO Auto-generated method stub
		return messageBoardDAO.findInfoPagesByLoanId(loadid, first, pageSize);
	}

	
	
}
