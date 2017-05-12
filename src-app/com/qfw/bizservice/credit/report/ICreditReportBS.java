package com.qfw.bizservice.credit.report;

import java.math.BigDecimal;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizCreditReportBO;

/**
 * 信用报告
 *
 * @author kyc
 */
public interface ICreditReportBS extends IBaseService {
	
	/**
	 * 根据用户获取信用报告
	 * @param custId
	 * @return
	 */
	public BizCreditReportBO findByCustInfo(String custId);
	
	/**
	 * 更新信用额度
	 * @param custId
	 * @param creditAmt 额度上限
	 * @param remainAmt 剩余额度
	 */
	public void updateCreditAmt(String custId,BigDecimal creditAmt,BigDecimal remainAmt) throws BizException;
	
	/**
	 * 更新申请笔数
	 * @param custId
	 * @param applyNum	申请笔数
	 * @param success	成功笔数
	 * @param applyAmt 	申请金额
	 */
	public void updateApplyNum(String custId,Integer applyNum,Integer success,BigDecimal applyAmt) throws BizException;
	
	/**
	 * 更新还清次数
	 * @param custId
	 * @param payOffNum 还款笔数
	 */
	public void updateTol(String custId,Integer payOffNum) throws BizException;
	
	/**
	 * 更新申请金额(金额恢复使用)
	 * @param custId
	 * @param reAmt 撤销金额
	 */
	public void updateApplyAmtForRev(String custId,BigDecimal reAmt) throws BizException;
	
	/**
	 * 加逾期金额
	 * @param custId
	 * @param amt
	 * @throws BizException
	 */
	public void addOverdueAmt(String custId,BigDecimal amt) throws BizException;
	
	/**
	 * 减逾期金额
	 * @param custId
	 * @param amt
	 * @throws BizException
	 */
	public void subtractOverdueAmt(String custId,BigDecimal amt) throws BizException;
	
	/**
	 * 加逾期笔数
	 * @param custId
	 * @param amt
	 * @throws BizException
	 */
	public void addOverdueNum(String custId,int count) throws BizException;
	/**
	 * 减逾期笔数
	 * @param custId
	 * @param amt
	 * @throws BizException
	 */
	public void subtractOverdueNum(String custId,int count) throws BizException;
	/**
	 * 加严重逾期笔数
	 * @param custId
	 * @param amt
	 * @throws BizException
	 */
	public void addSeriousOverdueNum(String custId,int count) throws BizException;
	/**
	 * 减严重逾期笔数
	 * @param custId
	 * @param amt
	 * @throws BizException
	 */
	public void subtractSeriousOverdueNum(String custId,int count) throws BizException;
}
