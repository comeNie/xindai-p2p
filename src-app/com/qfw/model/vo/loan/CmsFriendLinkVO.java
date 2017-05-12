/**
// * Copyright 2015 Software innovation and R & D center. All rights reserved.
// * File Name: CmsFriendLinkVO.java
// * Encoding UTF-8
// * Version: 0.0.1
// * History:	2015年7月13日
// */
package com.qfw.model.vo.loan;

/**
 * 
 * @author: yangyang.wu(改成自己的名字)
 * @version Revision: 0.0.1
 * @Date: 2015年7月13日
 */
public class CmsFriendLinkVO {
	private Integer id;// ID
	private String name;// 名称
	private String logo;// Logo
	private String url;// 链接地址
	private Integer sort;// 排序
	private String type;//类型 0:底部链接 1:广告轮询播放图片
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
