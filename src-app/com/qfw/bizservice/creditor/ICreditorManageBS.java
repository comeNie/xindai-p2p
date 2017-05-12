package com.qfw.bizservice.creditor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.platform.model.transfer.TransferSearchVo;
import com.qfw.platform.model.transfer.TransferVo;

public interface ICreditorManageBS extends IBaseService {

	
	/**
	 * 根据id查询交易记录bo
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public CreditorManageVO findInfoById(Integer id ) ;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<CreditorManageVO> findInfoPagesByVO (CreditorSearchVO searchVO, int first, int pageSize) throws BizException;

	
	/**
	 * 债权转让列表
	 * @return
	 * @throws BizException
	 */
	public List<TransferVo> findListPagesByVO (TransferSearchVo searchVO, int first, int pageSize) throws BizException;

	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getCountByVO(CreditorSearchVO searchVO);
	
	
	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getTransCountByVO(TransferSearchVo searchVO);
	
	/**
	 * 新增债权信息
	 */
	public void addCreditorRightTran(BizCreditorRightTranBO creditorRightTranBO);
	
	
	/**
	 * 计算客户的各种债权收益金额
	 * @param custId
	 * @return
	 */
	public Map<String, Object> calAmtForCust(Integer custId);
}
