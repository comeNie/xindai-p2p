package com.qfw.bean.bussparams;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.bussparams.IParameterInfoBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysParameter;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.bussparams.LazyParameterDataModel;

@ViewScoped
@ManagedBean(name="parameterManageBean")
public class ParameterManageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;

	private SysParameter searchParameterVO = new SysParameter();
	
	private LazyDataModel<SysParameter> parameterList;

	@ManagedProperty(value = "#{parameterInfoBS}")
	private IParameterInfoBS parameterInfoBS;
	
	private SysParameter selectParameter;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	
	
	@PostConstruct
    public void init(){
		searchParameter();
	}
	
	public void searchParameter() {
		try {
			super.search();
			parameterList = new LazyParameterDataModel(searchParameterVO, parameterInfoBS);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag) || "edit".equalsIgnoreCase(operateFlag)){
			ViewOper.getSession().setAttribute("id", selectParameter.getParameterId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			String productId = String.valueOf(selectParameter.getParameterId());
			String sql = "delete from SYS_PARAMETER where parameter_id = '"+productId+"'";
			try {
				parameterInfoBS.getJdbcTemplate().execute(sql);
				parameterList.setRowCount(parameterList.getRowCount()-1);
			} catch (Exception e) {
				log.error("删除参数"+selectParameter.getParameterCode()+"异常：", e);
				alert("删除参数异常："+ e);
			}
		}
	}
	
	public void reflesh(){
		paramBS.refreshParam();
	}

	public SysParameter getSearchParameterVO() {
		return searchParameterVO;
	}

	public void setSearchParameterVO(SysParameter searchParameterVO) {
		this.searchParameterVO = searchParameterVO;
	}

	public LazyDataModel<SysParameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(LazyDataModel<SysParameter> parameterList) {
		this.parameterList = parameterList;
	}

	public IParameterInfoBS getParameterInfoBS() {
		return parameterInfoBS;
	}

	public void setParameterInfoBS(IParameterInfoBS parameterInfoBS) {
		this.parameterInfoBS = parameterInfoBS;
	}

	public SysParameter getSelectParameter() {
		return selectParameter;
	}

	public void setSelectParameter(SysParameter selectParameter) {
		this.selectParameter = selectParameter;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}
	
	
	
}
