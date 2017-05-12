package com.qfw.bean.income;

/**
 * 收益信息Bean
 * 
 * @author qswei
 */

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.qfw.bizservice.income.IIncomeManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.income.IncomeDetailVO;

@ViewScoped
@ManagedBean(name = "incomeDetailBean")
public class IncomeDetailBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{incomeManageBS}")
	private IIncomeManageBS incomeManageBS;
	private IncomeDetailVO incomeVO = new IncomeDetailVO();
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@PostConstruct
	public void init() {
		try {
			Object incomeId = ViewOper.getSessionTmpAttr("incomeId");
			if(null != incomeId && (incomeId instanceof Integer)){
				incomeVO = this.incomeManageBS.findInfoById((Integer)incomeId);
			}else{
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "收益详情查看异常",
						null);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (Exception e) {
			log.error("收益详情查看异常："+e);
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "收益详情查看异常",
					null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public IIncomeManageBS getIncomeManageBS() {
		return incomeManageBS;
	}

	public void setIncomeManageBS(IIncomeManageBS incomeManageBS) {
		this.incomeManageBS = incomeManageBS;
	}

	public IncomeDetailVO getIncomeVO() {
		return incomeVO;
	}

	public void setIncomeVO(IncomeDetailVO incomeVO) {
		this.incomeVO = incomeVO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
