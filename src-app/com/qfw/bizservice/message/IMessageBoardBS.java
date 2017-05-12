package com.qfw.bizservice.message;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.bo.BizMessageBoardBO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.loan.LoanApplyVO;
import com.qfw.model.vo.message.MessageBoardVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;

public interface IMessageBoardBS extends IBaseService {

	public List<MessageBoardVO> findInfoPagesByLoanId (Integer loadid ,int first, int pageSize) throws BizException;

	
	public List<MessageBoardVO> findInfoPagesByLinkId(Integer linkId, int first,int pageSize) throws BizException;


	public void deleteMsg(Integer MsgId) throws BizException;
}
