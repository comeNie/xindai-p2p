package com.qfw.bizservice.payout;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysUser;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizWithdrawalsBO;
import com.qfw.model.vo.payout.WithdrawalsResponseVO;
import com.qfw.model.vo.payout.WithdrawalsVO;

/**
 * 提现bs
 *
 * @author kyc
 */
public interface IWithdrawalsPayoutBS extends IBaseService {
	
	/**
	 * 提现
	 * @param vo
	 * @throws BizException
	 */
	public void transWithDrawPayou(WithdrawalsVO vo) throws BizException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BizException
	 */
	public WithdrawalsResponseVO findWithBOById(Integer id) throws BizException ;
	
	/**
	 * 获取总数
	 * @param withVO
	 * @return
	 * @throws BizException
	 */
	public int findWithCountByVO(WithdrawalsVO withVO)throws BizException;
	
	/**
	 * 根据参数查询分页信息
	 * @param withVO
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<WithdrawalsResponseVO> findWithBOPagesByVO(WithdrawalsVO withVO,
			int first, int pageSize) throws BizException;
	
	/**
	 * 查询提现信息
	 * @param withVO
	 * @return
	 * @throws BizException
	 */
	public List<WithdrawalsResponseVO> findWithBOByVO(WithdrawalsVO withVO) throws BizException;
	
	/**
	 * 保存提现申请--工作流
	 * @param vo
	 */
	public void saveWithdrawalsApply(WithdrawalsVO vo) throws BizException;
	
	/**
	 * 获取任务并执行下一任务
	 * @param vo
	 * @param auditUser
	 * @throws BizException
	 */
	public void tranTakeAndCompleteTask(String taskId, String to,
			WithdrawalsVO vo, SysUser auditUser, String auditStatus,
			boolean finalApp, String html) throws BizException;

	/**
	 * 获取审批意见信息
	 * @param workItemId
	 * @return
	 */
	public List<Jbpm4AuditOpinion> getAuditOpinion(String workItemId);
	
	/**
	 * 根据参数查询提现信息
	 * @param vo
	 * @return
	 */
	public  List<BizWithdrawalsBO> findByParams(WithdrawalsVO vo) ;
	
	/**
	 * 查询参数vo
	 * @param vo
	 * @return
	 */
	public WithdrawalsVO findVOInfoByVO(WithdrawalsVO vo);
	
	/**
	 * 更新提现申请状态
	 * 
	 * @throws BizException
	 */
	public void updateWithdrawalsStatus(String status , List<String> idList) throws BizException;
	
	public void drawalTransfer(WithdrawalsVO withdrawalsVO,BizAccountBO custAcc) throws BizException;
	
	public String submitDrawal(WithdrawalsVO withdrawalsVO) throws BizException;
	
	public void withdrawalOrderHandle() throws BizException;
}
