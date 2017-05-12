package com.qfw.model.vo.credit;

import java.math.BigDecimal;

/**
 * 额度模块请求vo
 * @author kyc
 */
public class RequestCreditVO{
	
	/**
	 * 金额变化属性
	 */
	private String relId;				// 关联id(客户id)
	private BigDecimal passAmt;			// 通过金额
	private BigDecimal unPassAmt;		// 撤销金额(作废金额):针对待使用的金额进行作废
										// 通过金额和撤销金额在内部操作时，不能先进行计算后当成更新金额进行操作
										// 在投资操作时，可存在部分投资情况，因此需要作废部分未投资金额(作废待使用金额)
	private Integer eventTypeCd;		// 交易型态
	
	/**
	 * 记录信息
	 */
	private String txNO;				// 交易编号
	
	/**
	 * 额度内部参数（内部使用）
	 */
	private String clId;					// 额度id
	private String relTypeCd;			// 关联类型
	private String opstateCd;			// 操作
	private boolean freeze;				// 是否冻结
	private boolean approve;			// 是否最终放行操作
										// 是最终放行：更新待使用金额及已使用金额（已判断过更新金额是否小于可用金额）
										// 否：更新待使用金额
	private boolean autoflag;			// 是否自动操作
	
	public String getTxNO() {
		return txNO;
	}
	public void setTxNO(String txNO) {
		this.txNO = txNO;
	}
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	public boolean getFreeze() {
		return freeze;
	}
	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}
	public boolean getAutoflag() {
		return autoflag;
	}
	public void setAutoflag(boolean autoflag) {
		this.autoflag = autoflag;
	}
	public boolean getApprove() {
		return approve;
	}
	public void setApprove(boolean approve) {
		this.approve = approve;
	}
	public Integer getEventTypeCd() {
		return eventTypeCd;
	}
	public void setEventTypeCd(Integer eventTypeCd) {
		this.eventTypeCd = eventTypeCd;
	}
	public String getRelTypeCd() {
		return relTypeCd;
	}
	public void setRelTypeCd(String relTypeCd) {
		this.relTypeCd = relTypeCd;
	}
	public String getOpstateCd() {
		return opstateCd;
	}
	public void setOpstateCd(String opstateCd) {
		this.opstateCd = opstateCd;
	}
	public BigDecimal getPassAmt() {
		return passAmt;
	}
	public void setPassAmt(BigDecimal passAmt) {
		this.passAmt = passAmt;
	}
	public BigDecimal getUnPassAmt() {
		return unPassAmt;
	}
	public void setUnPassAmt(BigDecimal unPassAmt) {
		this.unPassAmt = unPassAmt;
	}
	public String getClId() {
		return clId;
	}
	public void setClId(String clId) {
		this.clId = clId;
	}
	
}
