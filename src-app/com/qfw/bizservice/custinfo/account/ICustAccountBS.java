package com.qfw.bizservice.custinfo.account;

import java.math.BigDecimal;
import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.custinfo.account.AccountResponseVO;
import com.qfw.model.vo.custinfo.account.PMInfoVO;
import com.qfw.model.vo.custinfo.account.PMSearchVO;
import com.qfw.model.vo.payout.WithdrawalsVO;

/**
 * 用户账号信息bs
 *
 * @author kyc
 */
public interface ICustAccountBS extends IBaseService{
	
	/**
	 * 根据id查询账号信息
	 * @param id
	 * @return
	 */
	public BizAccountBO findAccountInfoByid(Integer id);
	
	/**
	 * 根据账号查询账号信息
	 * @param account
	 * @return
	 */
	public BizAccountBO findInfoByAccount(AccountRequestVO vo ) throws BizException;
	
	/**
	 * 获取会员账号
	 * @param custId
	 * @return
	 */
	public BizAccountBO findCustAccount(Integer custId) throws BizException;
	/**
	 * 根据参数查询账号信息
	 * @param vo
	 * @return
	 */
	public List<BizAccountBO> findInfoByParams(AccountRequestVO vo) throws BizException;
	
	/**
	 * 根据参数查询账号信息
	 * @param vo
	 * @return
	 */
	public List<BizAccountBO> findInfoPagesByParams(AccountRequestVO vo, int first, int pageSize) throws BizException;
	
	/**
	 * 查询账号分页笔数
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	public int findCountInfoByParams(AccountRequestVO vo) throws BizException;
	
	/**
	 * 根据参数查询分页账号明细信息
	 * @param vo
	 * @return
	 */
	public List<AccountResponseVO> findDetailInfoByParams(AccountRequestVO vo, int first, int pageSize) throws BizException;
	
	/**
	 * 根据id查询账号明细信息
	 * @param id
	 * @return
	 */
	public AccountResponseVO findAccountDetailInfoByid(Integer id);
	
	/**
	 * 查询明细分页笔数
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	public int findCountDeatilByParams(AccountRequestVO vo) throws BizException;
	
	/**
	 * 更新账号金额
	 * @param vo
	 */
	public BigDecimal updateAccountAmt(AccountRequestVO vo ) throws BizException;
	
	/**
	 * 查询可用余额
	 * @param vo
	 * @param bo
	 * @return
	 * @throws BizException
	 */
	public BigDecimal queryUsableAmt(AccountRequestVO vo , BizAccountBO bo) throws BizException;
	
	/**
	 * 添加客户账号信息
	 * @param account
	 * @throws BizException
	 */
	public void addCustAccount(BizAccountBO  account) throws BizException;
	
	/**
	 * 根据参数查询pm币信息
	 * @param vo
	 * @return
	 */
	public List<PMInfoVO> findPMInfoPagesByParams(PMSearchVO vo, int first, int pageSize) throws BizException;

	/**
	 * 获取数据总数
	 * @param searchVO
	 * @return
	 * @throws BizException
	 */
	public int getCountByVO(PMSearchVO searchVO);

	/**
	 * 更新账号金额
	 * @param vo
	 */
	public BigDecimal updateAccountAmt(BizAccountBO bo, AccountRequestVO vo) throws BizException;
	
	
}
