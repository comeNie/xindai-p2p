package com.qfw.dao.message;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizMessageConfBO;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanManageVO;
import com.qfw.model.vo.loan.LoanSearchVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;

public interface IMessageManageDAO extends IBaseDAO {

	
	public List<RecMessageVO> findInfoPagesByRecVO (Integer userId,Integer messageType, int first, int pageSize) throws BizException;

	
	public List<SendMessageVO> findInfoPagesBySendVO(Integer userId, int first,int pageSize) throws BizException;
	
	
	public int findMyMessageCnt(Integer userId) throws BizException;
	
	public int[] saveOrUpdateMessageConf(List<BizMessageConfBO> addBoList,List<BizMessageConfBO> updateBoList) 
	throws BizException;
}
