package com.qfw.bizservice.message;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.bo.BizMessageConfBO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;
import com.qfw.model.vo.loan.LoanApplyVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;

public interface IMessageManageBS extends IBaseService {

	public List<RecMessageVO> findInfoPagesByRecVO (Integer userId,Integer messageType ,int first, int pageSize) throws BizException;

	
	public List<SendMessageVO> findInfoPagesBySendVO(Integer userId, int first,int pageSize) throws BizException;


	public void deleteMsg(Integer MsgId) throws BizException;
	
	public int findMyMessageCnt(Integer userId) throws BizException;
	
	public void setMsgReadStatus(Integer MsgId) throws BizException;
	
	public int[] saveOrUpdateMessageConf(List<BizMessageConfBO> addBoList,List<BizMessageConfBO> updateBoList) 
	throws BizException;
}
