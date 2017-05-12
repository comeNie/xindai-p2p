package com.qfw.bean.bussparams;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.bussparams.IGrantUserBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.model.bo.Jbpm4GrantUser;
import com.qfw.model.vo.bussparams.LazyGrantUserDataModel;

@ViewScoped
@ManagedBean(name="grantUserManageBean")
public class GrantUserManageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;

	private Jbpm4GrantUser searchGrantUserVO = new Jbpm4GrantUser();
	
	private LazyDataModel<Jbpm4GrantUser> grantUserList;
	
	@ManagedProperty(value = "#{grantUserBS}")
	private IGrantUserBS grantUserBS;
	
	private Jbpm4GrantUser selectGrantUser;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@PostConstruct
    public void init(){
		searchGrantUser();
	}
	
	public void searchGrantUser() {
		try {
			super.search();
			grantUserList = new LazyGrantUserDataModel(searchGrantUserVO, grantUserBS);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag) || "edit".equalsIgnoreCase(operateFlag)){
			ViewOper.getSession().setAttribute("id", selectGrantUser.getId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			String id = String.valueOf(selectGrantUser.getId());
			String sql = "delete from JBPM4_GRANT_USER where id = '"+id+"'";
			try {
				grantUserBS.getJdbcTemplate().execute(sql);
				grantUserList.setRowCount(grantUserList.getRowCount()-1);
			} catch (Exception e) {
				log.error("删除授权信息异常：", e);
				alert("删除授权信息异常："+ e);
			}
		}
	}

	public Jbpm4GrantUser getSearchGrantUserVO() {
		return searchGrantUserVO;
	}

	public void setSearchGrantUserVO(Jbpm4GrantUser searchGrantUserVO) {
		this.searchGrantUserVO = searchGrantUserVO;
	}

	public LazyDataModel<Jbpm4GrantUser> getGrantUserList() {
		return grantUserList;
	}

	public void setGrantUserList(LazyDataModel<Jbpm4GrantUser> grantUserList) {
		this.grantUserList = grantUserList;
	}

	public IGrantUserBS getGrantUserBS() {
		return grantUserBS;
	}

	public void setGrantUserBS(IGrantUserBS grantUserBS) {
		this.grantUserBS = grantUserBS;
	}

	public Jbpm4GrantUser getSelectGrantUser() {
		return selectGrantUser;
	}

	public void setSelectGrantUser(Jbpm4GrantUser selectGrantUser) {
		this.selectGrantUser = selectGrantUser;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
