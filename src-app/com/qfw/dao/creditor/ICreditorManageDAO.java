package com.qfw.dao.creditor;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.platform.model.transfer.TransferSearchVo;
import com.qfw.platform.model.transfer.TransferVo;

public interface ICreditorManageDAO extends IBaseDAO {

	/**
	 * 根据债权id查询信息
	 * @param id
	 * @return
	 */
	public CreditorManageVO findInfoByID(Integer id) ;
	
	/**
	 * 分页查询list
	 * @return
	 * @throws BizException
	 */
	public List<CreditorManageVO> findInfoPagesByVO (CreditorSearchVO searchVO, int first, int pageSize) throws BizException;


	/**
	 * 债券转让列表
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
	 * 获取债权数据list
	 * @param searchVO
	 * @return
	 */
	public List<CreditorManageVO> findInfoList(CreditorSearchVO searchVO);
}
