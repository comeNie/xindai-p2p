package com.qfw.bizservice.credit;

import java.math.BigDecimal;
import java.util.Map;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.vo.credit.CreditQueryVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.credit.ResponseCreditLimitVO;

/**
 * 额度模块核心方法
 *
 * @author kyc
 */
public interface ICreditBS extends IBaseService {
	
	/**
	 * 冻结额度
	 * @param vo
	 */
	public void tranFrozenAmt(RequestCreditVO vo) throws BizException;

	/**
	 * 外部接口：额度占用、恢复（统一入口）
	 * @param vo
	 */
	public void tranTransactionCredit(RequestCreditVO vo)  throws BizException;
	
	/**
	 * 外部接口：最终放行操作时的额度调用(需要在tranTransactionCredit方法后面调用)(舍弃)
	 * @param vo
	 */
	public void tranApproveCredit(RequestCreditVO vo)  throws BizException;
	
	/**
	 * 剩余可用额度金额(目前relvalue只传null)
	 * @param vo
	 * @param relValue 关联合同参数  
	 * @return
	 */
	public Map<String, BigDecimal> surplusCreditAmt(RequestCreditVO vo,String relValue) throws BizException;

	/**
	 * 查询额度明细信息
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	public ResponseCreditLimitVO queryCreditLimitVO(RequestCreditVO vo) throws BizException;
	
	/**
	 * 验证额度是否存在
	 * @param vo
	 * @return
	 * @throws BizException
	 */
	public boolean checkCreditlimit(RequestCreditVO vo) throws BizException;
	
	/**
	 * 校验客户额度够不够
	 * @param custRefcode 客户流水号
	 * @param amt 客户金额
	 * @return ture=额度够  false=额度不够
	 */
	public boolean checkCreditlimit(String custRefcode,BigDecimal amt);
	
	/**
	 * 通过客户好查找额度
	 * @param custId 客户编号
	 * @return BizCreditLimitBO额度对象
	 */
	public BizCreditLimitBO findObjectByCustId(CreditQueryVO vo);
}
