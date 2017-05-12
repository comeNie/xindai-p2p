package com.qfw.bizservice.custinfo;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.custinfo.SearchCustInfoVO;
import com.qfw.model.vo.custinfo.basicInfo.CustBasicInfoVO;
import com.qfw.platform.model.json.Pager;

public interface ICustInfoBS extends IBaseService{

	/**
	 * 查询客户相关信息
	 * @param searchCustInfoVO
	 * @return
	 * @throws BizException
	 */
	public List<CustInfoVO> findCustInfo (SearchCustInfoVO searchCustInfoVO) throws BizException;
	
	/**
	 * 根据客户id查询客户基本信息
	 * @param custId
	 * @return
	 */
	public CustBasicInfoVO findCustBasicInfo(Integer custId) ;
	
	/**
	 * 分页查询客户相关信息
	 * @param searchCustInfoVO
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<CustInfoVO> findCustInfoPagesByVO (SearchCustInfoVO searchCustInfoVO, int first, int pageSize);
	
	/**
	 * 查询客户信息笔数
	 * @param searchCustInfoVO
	 * @return
	 * @throws BizException
	 */
	public int findCustInfoCountByVO (SearchCustInfoVO searchCustInfoVO);
	
	/**
	 * 根据客户编号查询客户相关信息
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	public CustInfoVO findCustInfoById (Integer custId);
	
	/**
	 * 添加用户信息
	 * @param user
	 * @param deptIds
	 */
	public void addCust(BizCustomerBO  cust) throws BizException;
	
	/**
	 * 保存用户信息,同时保存账号信息 
	 * @param user
	 * @param deptIds
	 */
	public void saveCust(BizCustomerBO  cust) throws BizException;
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param deptIds
	 */
	public void updateCust(BizCustomerBO  cust) throws BizException;
	
	public BizCustomerBO findCustById(Integer id);

	
	/**
	 * 根据用户ID 查找对应的客户ID
	 * @param userId
	 * @return
	 */
	public CustInfoVO findCust(Integer userId) throws BizException;
	
	
	/**
	 * 通过用户id查询客户信息
	 * @param userId
	 * @return
	 * @throws BizException
	 */
	public BizCustomerBO findCustByUserId(Integer userId) throws BizException;
	
	public int getCustCountByIdCard(String IdCard) throws BizException;
	
	public List<BizCustomerBO> findCustomer(String mobile) throws BizException;
	
	public BizCustomerBO findCustByRefereeCode(String refereeCode) throws BizException;
	
	public int countReferee(Integer custId);

	public CustInfoVO getPlatCustInfo() throws BizException;

	public Pager findMyRecommend(int requestPage, int pageSize, Integer custId)
			throws BizException;
}
