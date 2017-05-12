package com.qfw.common.util.web;

import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;

import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysUserVO;
import com.qfw.common.model.permission.vo.UserDataModel;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.jbpm.model.bo.Jbpm4AuditOpinion;

/**
 * 工作流backingbean
 * @author Jie
 *
 */
public class WorkFlowBackingBean extends BaseBackingBean{

	@ManagedProperty(value="#{userBS}")
	private IUserBS userBS;
	private SysUserVO filterUser = new SysUserVO();//用于页面强求vo用
	private LazyDataModel<SysUser> userList;//用户列表
	private SysUser selectUser = new SysUser();
	private DataTable userTable;
	private List<Jbpm4AuditOpinion> auditOpinionList;
	private String auditMsg;
	private String html;
	
	public void searchUser(){
		if(filterUser!=null){
			userTable.setFirst(0);
			if(userList == null){
				userList = new UserDataModel(filterUser, userBS);
			}
			
		}
	}
	
	public void selectUser(){
		////System.out.println(selectUser);
	}
		
	public IUserBS getUserBS() {
		return userBS;
	}
	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	
	public SysUserVO getFilterUser() {
		return filterUser;
	}

	public void setFilterUser(SysUserVO filterUser) {
		this.filterUser = filterUser;
	}

	public SysUser getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(SysUser selectUser) {
		this.selectUser = selectUser;
	}
	public LazyDataModel<SysUser> getUserList() {
		return userList;
	}
	public void setUserList(LazyDataModel<SysUser> userList) {
		this.userList = userList;
	}

	public DataTable getUserTable() {
		return userTable;
	}

	public void setUserTable(DataTable userTable) {
		this.userTable = userTable;
	}

	public List<Jbpm4AuditOpinion> getAuditOpinionList() {
		return auditOpinionList;
	}

	public void setAuditOpinionList(List<Jbpm4AuditOpinion> auditOpinionList) {
		this.auditOpinionList = auditOpinionList;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	
	
}
