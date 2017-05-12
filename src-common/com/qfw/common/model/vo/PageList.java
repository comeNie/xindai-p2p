package com.qfw.common.model.vo;

import java.io.Serializable;
import java.util.List;


public class PageList<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<T> data;
	private int count;
	private int pageSize;
	private int curPage;
	
	
	
	public PageList() {
		super();
	}
	public PageList(List<T> data, int count, int pageSize, int curPage) {
		super();
		this.data = data;
		this.count = count;
		this.pageSize = pageSize;
		this.curPage = curPage;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	
}
