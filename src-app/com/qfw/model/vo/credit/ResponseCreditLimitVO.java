package com.qfw.model.vo.credit;

import java.math.BigDecimal;
import java.util.List;

import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.bo.BizCreditUseBO;

public class ResponseCreditLimitVO extends BizCreditLimitBO {

	private static final long serialVersionUID = 7661652002448199429L;
	
	private BigDecimal usableAmt;			// 可用金额
	private List<BizCreditUseBO> useList;		// 额度动用列表
	
	public void setCreditLimit(BizCreditLimitBO limit){
		this.setClAmt(limit.getClAmt());
		this.setEndDate(limit.getEndDate());
		this.setFreezeAmt(limit.getFreezeAmt());
		this.setId(limit.getId());
		this.setOpstateCd(limit.getOpstateCd());
		this.setRelId(limit.getRelId());
		this.setRelTypeCd(limit.getRelTypeCd());
		this.setStartDate(limit.getStartDate());
		this.setCreditStateCd(limit.getCreditStateCd());
		this.setSysCreateTime(limit.getSysCreateTime());
		this.setSysCreateUser(limit.getSysCreateUser());
		this.setSysUpdateTime(limit.getSysUpdateTime());
		this.setSysUpdateUser(limit.getSysUpdateUser());
		this.setWorkItemId(limit.getWorkItemId());
	}
	
	public BigDecimal getUsableAmt() {
		return usableAmt;
	}
	public void setUsableAmt(BigDecimal usableAmt) {
		this.usableAmt = usableAmt;
	}
	public List<BizCreditUseBO> getUseList() {
		return useList;
	}
	public void setUseList(List<BizCreditUseBO> useList) {
		this.useList = useList;
	}
	
}
