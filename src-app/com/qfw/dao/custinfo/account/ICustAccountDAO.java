package com.qfw.dao.custinfo.account;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.custinfo.account.AccountResponseVO;
import com.qfw.model.vo.custinfo.account.PMInfoVO;
import com.qfw.model.vo.custinfo.account.PMSearchVO;

/**
 * 客户账号dao
 *
 * @author kyc
 */
public interface ICustAccountDAO extends IBaseDAO {

	/**
	 * 查询账号信息
	 * @param vo
	 * @return
	 */
	public List<BizAccountBO> findInfoByParams(AccountRequestVO vo ) throws BizException;
	
	/**
	 * 查询账号分页
	 * @param vo
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<BizAccountBO> findInfoPagesByParams(AccountRequestVO vo, int first, int pageSize) throws BizException;

	/**
	 * 查询分页笔数
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	public int findCountInfoByParams(AccountRequestVO vo)  throws BizException  ;
	
	/**
	 * 查询账号明细分页
	 * @param vo
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public AccountResponseVO findDetailById(Integer id) ;
	
	/**
	 * 查询账号明细分页
	 * @param vo
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<AccountResponseVO> findDetailPagesByParams(AccountRequestVO vo, int first, int pageSize) throws BizException;
	
	/**
	 * 查询账号明细分页笔数
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	public int findCountDetailByParams(AccountRequestVO vo)  throws BizException  ;

	/**
	 * 查询pm币分页
	 * @param vo
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<PMInfoVO> findPMInfoPagesByParams(PMSearchVO vo, int first, int pageSize) throws BizException;

	/**
	 * 获取笔数
	 * @param searchVO
	 * @return
	 */
	public int getCountByVO(PMSearchVO searchVO) ;
}
