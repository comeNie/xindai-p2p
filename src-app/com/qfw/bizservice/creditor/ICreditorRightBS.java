package com.qfw.bizservice.creditor;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.vo.creditor.CreditorRightTranVO;
import com.qfw.model.vo.creditor.CreditorRightVO;

/**
 * 债权核心服务
 * @author kindion
 *
 */
public interface ICreditorRightBS extends IBaseService {
	/**
	 * 投标提交服务
	 * @param 投资信息
	 * @return 是否满标
	 * @throws BizException
	 */
	public boolean submitTrender(CreditorRightVO creditorRightVO) throws BizException;
	
	/**
	 * 债权转让发布
	 * @param creditorRightTranVO
	 * @throws BizException
	 */
	public void creditorTranApprove(CreditorRightTranVO creditorRightTranVO) throws BizException;
	
	/**
	 * 债权转让投资服务
	 * @param creditorRightVO
	 * @return
	 * @throws BizException
	 */
	public boolean submitTrenderTran(CreditorRightVO creditorRightVO) throws BizException;
	
	/**
	 * 债权转让发布失效服务
	 * @param crtId
	 * @throws BizException
	 */
	public void invalidCreditorRightTran(Integer crtId) throws BizException;
	
	/**
	 * 通过债权id获取所有债权转让信息
	 * @param crId
	 * @return
	 * @throws BizException
	 */
	public List<BizCreditorRightTranBO> findCreditorRightTransByCrId(Integer crId) throws BizException;

	/**
	 * 根据债权转让id获取债权转让信息
	 * @param tranId
	 * @return
	 * @throws BizException
	 */
	public BizCreditorRightTranBO findCreditorRightTransById(Integer tranId) throws BizException;
}
	