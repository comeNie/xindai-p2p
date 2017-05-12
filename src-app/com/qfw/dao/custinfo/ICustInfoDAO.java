package com.qfw.dao.custinfo;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.SearchCustInfoVO;
import com.qfw.model.vo.custinfo.basicInfo.CustBasicInfoVO;
import com.qfw.platform.model.json.Pager;

public interface ICustInfoDAO  extends IBaseDAO{
	
	/**
	 * 查询客户相关信息
	 * @param searchCustInfoVO
	 * @return
	 * @throws BizException
	 */
	public List<CustInfoVO> findCustInfo (SearchCustInfoVO searchCustInfoVO) throws BizException;
	
	/**
	 * 分页查询客户相关信息
	 * @param searchCustInfoVO
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<CustInfoVO> findCustInfoPagesByVO (SearchCustInfoVO searchCustInfoVO, int first, int pageSize) throws BizException;
	
	/**
	 * 查询客户信息笔数
	 * @param searchCustInfoVO
	 * @return
	 * @throws BizException
	 */
	public int findCustInfoCountByVO (SearchCustInfoVO searchCustInfoVO) throws BizException;
	
	/**
	 * 根据客户编号查询客户相关信息
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	public List<CustInfoVO> findCustInfoById (Integer custId) throws BizException;
	
	/**
	 * 根据用户编号查询客户，客户账号相关信息
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public List<CustInfoVO> findCustInfoByUserId (Integer userId) throws BizException;
	
	public int countReferee(Integer custId) throws BizException;

	public int countCust() throws BizException;

	public List<CustInfoVO> sumAccount() throws BizException;

	public Pager findMyRecommend(int requestPage, int pageSize, Integer custId)
			throws BizException;
    /**
     * 统计vip数量
     * @return
     * @throws BizException
     */
	public int countVip() throws BizException;
	/**
	 * 统计有推荐码的人数
	 * @return
	 * @throws BizException
	 */
	public int countRecommend() throws BizException;
 
}
