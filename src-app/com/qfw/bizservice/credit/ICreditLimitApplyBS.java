package com.qfw.bizservice.credit;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.vo.PageList;
import com.qfw.model.bo.BizCollateralInfoBO;
import com.qfw.model.bo.BizContactsBO;
import com.qfw.model.bo.BizCreditLimitApplyBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizDisclosureInfoBO;
import com.qfw.model.bo.BizGuaranteeInfoBO;
import com.qfw.model.bo.BizGuarantorInfoBO;
import com.qfw.model.bo.BizJobBO;
import com.qfw.model.vo.credit.BizCreditLimitApplyVO;


public interface ICreditLimitApplyBS extends IBaseService{
	
	/**
	 * 删除额度申请信息
	 */
	public void delCreditLimitApply(int creditLimitApplyID) throws BizException;
	
	/**
	 * 查看额度申请信息
	 */
	public BizCreditLimitApplyBO getCreditLimitApply(int id) throws BizException;
	
	/**
	 * 查看额度申请信息
	 */
	public BizCreditLimitApplyBO getCreditLimitApplyByWorkItemId(String workItemId) throws BizException;
	
	/**
	 * 查看担保基本信息
	 */
	public BizGuaranteeInfoBO getGuaranteeInfo(int guaranteeID) throws BizException;
	
	
	/**
	 * 查看客户基本信息
	 */
	public BizCustomerBO getCustomerInfo(int custID) throws BizException;
	
	/**
	 * 查看会员联系人
	 */
	public BizContactsBO getContactsInfo(int custID) throws BizException;
	/**
	 * 查看会员工作信息
	 */
	public BizJobBO getJobInfo(int custID) throws BizException;
	/**
	 * 查看额度申请列表信息
	 * 
	 */
	public List getCreditLimitApplyList() throws BizException;
	
	/**
	 * 更新额度申请基本信息
	 */
	public void updateCreditLimitApply(int creditLimitApplyID) throws BizException;
	
	/**
	 * 查询额度申请列表
	 * 
	 * @param userId
	 * @param first
	 * @param pageSize
	 * @return
	 */
	public PageList<BizCreditLimitApplyVO> getCreditLimitApply(BizCreditLimitApplyVO creditLimitApplyVO, int first, int pageSize);
	
	/**
	 * 保存申请额度信息
	 * @throws BizException
	 */
	public void saveCreditLimitApply(BizCreditLimitApplyVO creditLimitApplyVO,SysUser auditUser) throws BizException;
	
	/**
	 * 工作流流转
	 * @param taskId
	 * @param to
	 * @param loanApplyVO
	 * @param auditUser
	 * @param auditStatus
	 * @param appNode	
	 * @throws BizException
	 */
	//public void tranTakeAndCompleteTask(String taskId,String to ,BizCreditLimitApplyVO creditLimitApplyVO, SysUser auditUser, String auditStatus,String appNode) throws BizException;
	
	/**
	 * 修改额度申请信息（工作流使用）
	 * @param creditLimitApplyVO
	 * @param auditUser
	 * @param auditStatus
	 * @param appNode
	 * @throws BizException
	 */
	//public void updateApplyForTake(BizCreditLimitApplyVO creditLimitApplyVO, SysUser auditUser, String auditStatus,String appNode) throws BizException;
	
	/**
	 * 通过客户ID，初始化额度申请vo信息
	 * @param custId
	 * @param creditLimitApplyVO
	 * @throws BizException
	 */
	public void initCreditLimitApplyVOByCustId(String custId,BizCreditLimitApplyVO creditLimitApplyVO) throws BizException;

	/**
	 * 保存额度VO信息
	 * @param creditLimitApplyVO
	 * @param workItemId
	 */
	public void saveCreditLimitApply(BizCreditLimitApplyVO creditLimitApplyVO,String workItemId) throws BizException;
	
	/**
	 * 通过工作流信息查找额度申请信息
	 * @param workItemId
	 * @return
	 * @throws BizException
	 */
	public BizCreditLimitApplyVO getCreditLimitApplyVOByWorkItemId(
			String workItemId) throws BizException;


	public void updateApplyForTake(BizCreditLimitApplyVO creditLimitApplyVO,
			SysUser auditUser, String auditStatus, boolean isLast,boolean isSubmitAgain)
			throws BizException;

	public void tranTakeAndCompleteTask(String taskId, String to,
			BizCreditLimitApplyVO creditLimitApplyVO, SysUser auditUser,
			SysRole auditRole, String auditStatus, boolean isLast, String html)
			throws BizException;
	
	/**
	 * 检查联系人的信息是否正确(目前支持检查  电话，手机，身份证)
	 * @param contact
	 * @return
	 */
	public String verContactsInfo(BizContactsBO contact);
	
	/**
	 * 检查担保品的信息是否正确
	 * @param contact
	 * @return
	 */
	public String verCollateralInfo(BizCollateralInfoBO collateralInfoBO);
	
	/**
	 * 检查保证人的信息是否正确
	 * @param contact
	 * @return
	 */
	public String verGuarantorInfo(BizGuarantorInfoBO guarantorInfoBO);
	
	/**
	 * 校验客户信息
	 * @param cust
	 * @return
	 */
	public String validateCust(BizCustomerBO cust);
	
	/**
	 * 保存额度信息中的标签页信息
	 * 
	 * @param creditLimitApplyVO
	 * @throws BizException
	 */
	public void saveCreditlimtTabInfo(BizCreditLimitApplyVO creditLimitApplyVO)throws BizException ;

	public BizCollateralInfoBO getCollateralInfo(Integer custId)
			throws BizException;

	public BizDisclosureInfoBO getDisclosureInfo(Integer custId) throws BizException;
}
