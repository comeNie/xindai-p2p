package com.qfw.dao.message;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizMessageBoardBO;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;
import com.qfw.model.vo.message.MessageBoardVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;

public interface IMessageBoardDAO extends IBaseDAO {

	
	public List<MessageBoardVO> findInfoPagesByLoanId (Integer loadid , int first, int pageSize) throws BizException;

	
	public List<MessageBoardVO> findInfoPagesByLinkId(Integer linkId, int first,int pageSize) throws BizException;
	
}
