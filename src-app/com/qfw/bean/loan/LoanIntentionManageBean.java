package com.qfw.bean.loan;

/**
 * 借款申请列表Bean
 * 
 * @author weiqs
 */

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.loan.ILoanApplyBS;
import com.qfw.bizservice.loan.ILoanApplyManageBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.vo.loan.LazyLoanIntentionDataModel;

@ViewScoped
@ManagedBean(name = "loanIntentionManage")
public class LoanIntentionManageBean extends BaseBackingBean {

	private static final long serialVersionUID = -2631989147972388606L;

	@ManagedProperty(value = "#{loanApplyManageBS}")
	private ILoanApplyManageBS loanManageBS;
	
	@ManagedProperty(value = "#{loanApplyBS}")
	private ILoanApplyBS loanApplyBS;
	
	private LazyDataModel<Map> dataModel;

	private Logger log = LogFactory.getInstance().getBusinessLogger();

	private Map<String, String> filter = new HashMap<String, String>();
	
	private Map data;
	
	private String refuseReason;

	@PostConstruct
	public void init() {
		if (dataModel == null) {
			filter.put("processStatus", "0");
			try {
				dataModel = new LazyLoanIntentionDataModel(filter, loanManageBS);
			} catch (Exception e) {
				log.error("借款申请列表异常：", e);
				alert("借款申请列表异常：" + e.getMessage());
			}

		}
	}

	public void operate() {
		ViewOper.getSession().setAttribute("data", data);
	}

	public void setDisValid(){
		try {
			loanApplyBS.setloanIntentionDisValid((Integer)data.get("ID"),refuseReason);
			data.put("PROCESS_STATUS_CD", "2");
			this.alertInfo("操作成功");
		} catch (BizException e) {
			this.alert(e.getMessage());
		}
	}
	public ILoanApplyManageBS getLoanManageBS() {
		return loanManageBS;
	}

	public void setLoanManageBS(ILoanApplyManageBS loanManageBS) {
		this.loanManageBS = loanManageBS;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Map<String, String> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
	}

	public LazyDataModel<Map> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Map> dataModel) {
		this.dataModel = dataModel;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public ILoanApplyBS getLoanApplyBS() {
		return loanApplyBS;
	}

	public void setLoanApplyBS(ILoanApplyBS loanApplyBS) {
		this.loanApplyBS = loanApplyBS;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

}
