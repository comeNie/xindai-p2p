package com.qfw.bean.loan.repayPlan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.bizservice.repay.arrears.IRepayArrearsBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizArrearsDetailBO;
import com.qfw.model.bo.BizRepayPlanDetailBO;

@ViewScoped
@ManagedBean(name = "repayAdvanceManageBean")
public class RepayAdvanceManageBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@ManagedProperty(value = "#{repayArrearsBS}")
	private IRepayArrearsBS repayArrearsBS;
	@ManagedProperty(value = "#{repayBS}")
	private IRepayBS repayBS;

	private List<BizRepayPlanDetailBO> repayPlanList = new ArrayList<BizRepayPlanDetailBO>();

	private BizRepayPlanDetailBO bizRepayPlanDetailBO;
	private Integer loanId;

	private BigDecimal totalAmt = BigDecimal.ZERO;

	@PostConstruct
	public void init() {
		Object loanIdSession = ViewOper.getSessionTmpAttr("loanId");

		if (null == loanIdSession) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"借款信息获取失败", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		} else {
			loanId = (Integer) loanIdSession;
		}
		this.search();
		
		// 计算总应付金额
		this.calRepayTotalAmt();
	}

	public void search() {
		try {
			repayPlanList = this.repayArrearsBS.queryArrearsDetail(loanId);
		} catch (Exception e) {
			log.error("还款管理查询异常：", e);
			alert("还款管理查询异常：" + e.getMessage());
		}

	}

	/**
	 * 提交成功
	 */
	public String submit() {
		try {
			String mess = "";
			try {
				this.repayBS.repayForFull(AppConst.REPAY_WAY_CD_TQ, loanId,
						null);
				mess = "还款提交成功";
			} catch (Exception e) {
				mess = "还款提交失败：" + e.getMessage();
			}
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					mess, null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "还款提交失败", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}

	/**
	 * 计算提前还款时，总应付金额
	 */
	private void calRepayTotalAmt() {
		if (null != repayPlanList && repayPlanList.size() > 0) {
			for (BizRepayPlanDetailBO repayPlan : repayPlanList) {
				BizArrearsDetailBO arrears = repayPlan.getBizArrearsDetailBO();
				totalAmt = totalAmt.add(arrears.getUnpaidInterestAmt()).add(
						arrears.getUnpaidPenaltyAmt()).add(arrears.getUnpaidPrincipalAmt());
			}
		}
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public IRepayArrearsBS getRepayArrearsBS() {
		return repayArrearsBS;
	}

	public void setRepayArrearsBS(IRepayArrearsBS repayArrearsBS) {
		this.repayArrearsBS = repayArrearsBS;
	}

	public List<BizRepayPlanDetailBO> getRepayPlanList() {
		return repayPlanList;
	}

	public void setRepayPlanList(List<BizRepayPlanDetailBO> repayPlanList) {
		this.repayPlanList = repayPlanList;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public IRepayBS getRepayBS() {
		return repayBS;
	}

	public void setRepayBS(IRepayBS repayBS) {
		this.repayBS = repayBS;
	}

	public BizRepayPlanDetailBO getBizRepayPlanDetailBO() {
		return bizRepayPlanDetailBO;
	}

	public void setBizRepayPlanDetailBO(
			BizRepayPlanDetailBO bizRepayPlanDetailBO) {
		this.bizRepayPlanDetailBO = bizRepayPlanDetailBO;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

}
