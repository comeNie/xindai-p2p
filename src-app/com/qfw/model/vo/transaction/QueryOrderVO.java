package com.qfw.model.vo.transaction;

public class QueryOrderVO {
	private String tx;//无需提供，接口服务提供
	private String merCode;//无需提供，接口服务提供
	private String orderNumber;
	private String beginTime;
	private String endTime;
	private String pageIndex;
	private String sign;//无需提供，接口服务提供
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	
}
