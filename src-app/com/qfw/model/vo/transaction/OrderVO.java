package com.qfw.model.vo.transaction;

import java.util.ArrayList;
import java.util.List;

public class OrderVO {
	private String merCode;
	private String beginDate;
	private String endDate;
	private String resultCount;
	private String pageIndex;
	private String pageSize;
	private String resultCode;
	private List<OrderDetailVO> orderDetails = new ArrayList<OrderDetailVO>();
	
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getResultCount() {
		return resultCount;
	}
	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public List<OrderDetailVO> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailVO> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
