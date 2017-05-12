package com.qfw.model.vo.credit;

import java.util.Date;

/**
 * 额度内部查询vo
 *
 * @author kyc
 */
public class CreditQueryVO {

	private Integer eventTypeCd;		// 交易型态
	private String txNO;				// 交易编号
	private Integer id;					// 额度id
	private String relId;				// 关联流水号
	private String relTypeCd;			// 关联类型
	private Date startDate;				// 开始时间
	private Date endDate;				// 失效时间
	private String state;				// 状态
	private String opstateCd;			// 工作项id

	public String getTxNO() {
		return txNO;
	}
	public void setTxNO(String txNO) {
		this.txNO = txNO;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOpstateCd() {
		return opstateCd;
	}
	public void setOpstateCd(String opstateCd) {
		this.opstateCd = opstateCd;
	}
	
}
