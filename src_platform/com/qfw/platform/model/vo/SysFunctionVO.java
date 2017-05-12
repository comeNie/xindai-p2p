/**
 * Copyright 2015 Software innovation and R & D center. All rights reserved.
 * File Name: SysFuction.java
 * Encoding UTF-8
 * Version: 0.0.1
 * History:	2015年7月26日
 */
package com.qfw.platform.model.vo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.qfw.common.model.BaseBO;

/**
 * 
 * @author: yangyang.wu
 * @version Revision: 0.0.1
 * @Date: 2015年7月26日
 */
public class SysFunctionVO {
    private Integer funId;
    private String funIcon;
    private String funLevel;
    private String funName;
    private String funStatus;
    private String funType;
    private Integer parentFunId;
    private Integer sort;
    private String funPath;
    private Date sysCreateTime;
    private Date sysUpdateTime;
    private String sysUpdateUser;
    private String remark;
    private String funCode;
    private String isLast;
    private String parentName;
	public Integer getFunId() {
		return funId;
	}
	public void setFunId(Integer funId) {
		this.funId = funId;
	}
	public String getFunIcon() {
		return funIcon;
	}
	public void setFunIcon(String funIcon) {
		this.funIcon = funIcon;
	}
	public String getFunLevel() {
		return funLevel;
	}
	public void setFunLevel(String funLevel) {
		this.funLevel = funLevel;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getFunStatus() {
		return funStatus;
	}
	public void setFunStatus(String funStatus) {
		this.funStatus = funStatus;
	}
	public String getFunType() {
		return funType;
	}
	public void setFunType(String funType) {
		this.funType = funType;
	}
	public Integer getParentFunId() {
		return parentFunId;
	}
	public void setParentFunId(Integer parentFunId) {
		this.parentFunId = parentFunId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getFunPath() {
		return funPath;
	}
	public void setFunPath(String funPath) {
		this.funPath = funPath;
	}
	public Date getSysCreateTime() {
		return sysCreateTime;
	}
	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}
	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}
	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}
	public String getSysUpdateUser() {
		return sysUpdateUser;
	}
	public void setSysUpdateUser(String sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFunCode() {
		return funCode;
	}
	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}
	public String getIsLast() {
		return isLast;
	}
	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
    

}
