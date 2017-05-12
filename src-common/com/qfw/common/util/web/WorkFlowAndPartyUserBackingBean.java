package com.qfw.common.util.web;

import java.util.List;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;

import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysUserVO;
import com.qfw.common.model.permission.vo.UserDataModel;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;

/**
 * 工作流backingbean同时可查询第三方用户
 * @author kang
 *
 */
public class WorkFlowAndPartyUserBackingBean extends WorkFlowBackingBean{

	private static final long serialVersionUID = 1L;
	
	/** 第三方用户*/
	private SysUserVO filterPartyUser = new SysUserVO();//用于页面请求vo用
	private LazyDataModel<SysUser> userPartyList;//用户列表
	private SysUser selectPartyUser = new SysUser();
	private DataTable userPartyTable;
	
	public void searchUser(){
		super.searchUser();
	}
	
	public void searchPartyUser(){
		if(filterPartyUser!=null){
			userPartyTable.setFirst(0);
			if(userPartyList == null){
				userPartyList = new UserDataModel(filterPartyUser, getUserBS());
			}
			
		}
	}
	
	public void selectUser(){
		////System.out.println(selectUser);
	}
	
	public void selectPartyUser(){
		////System.out.println(selectUser);
	}
		
	public IUserBS getUserBS() {
		return super.getUserBS();
	}
	
	public SysUserVO getFilterUser() {
		return super.getFilterUser();
	}

	public void setFilterUser(SysUserVO filterUser) {
		super.setFilterUser(filterUser);
	}

	public SysUser getSelectUser() {
		return super.getSelectUser();
	}

	public void setSelectUser(SysUser selectUser) {
		super.setSelectUser(selectUser);
	}
	public LazyDataModel<SysUser> getUserList() {
		return super.getUserList();
	}
	public void setUserList(LazyDataModel<SysUser> userList) {
		super.setUserList(userList);
	}

	public DataTable getUserTable() {
		return super.getUserTable();
	}

	public void setUserTable(DataTable userTable) {
		super.setUserTable(userTable);
	}

	public List<Jbpm4AuditOpinion> getAuditOpinionList() {
		return super.getAuditOpinionList();
	}

	public void setAuditOpinionList(List<Jbpm4AuditOpinion> auditOpinionList) {
		super.setAuditOpinionList(auditOpinionList);
	}

	public String getAuditMsg() {
		return super.getAuditMsg();
	}

	public void setAuditMsg(String auditMsg) {
		super.setAuditMsg(auditMsg);
	}

	public SysUserVO getFilterPartyUser() {
		return filterPartyUser;
	}

	public void setFilterPartyUser(SysUserVO filterPartyUser) {
		this.filterPartyUser = filterPartyUser;
	}

	public LazyDataModel<SysUser> getUserPartyList() {
		return userPartyList;
	}

	public void setUserPartyList(LazyDataModel<SysUser> userPartyList) {
		this.userPartyList = userPartyList;
	}

	public SysUser getSelectPartyUser() {
		return selectPartyUser;
	}

	public void setSelectPartyUser(SysUser selectPartyUser) {
		this.selectPartyUser = selectPartyUser;
	}

	public DataTable getUserPartyTable() {
		return userPartyTable;
	}

	public void setUserPartyTable(DataTable userPartyTable) {
		this.userPartyTable = userPartyTable;
	}
	
}
