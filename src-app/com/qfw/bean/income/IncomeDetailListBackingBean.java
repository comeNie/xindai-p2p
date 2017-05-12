package com.qfw.bean.income;

/**
 * 收益信息列表Bean
 * 
 * @author 
 */

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.income.IIncomeManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.vo.income.IncomeDetailVO;
import com.qfw.model.vo.income.IncomeSearchVO;
import com.qfw.model.vo.income.LazyIncomeDataModel;

@ViewScoped
@ManagedBean(name = "incomeDetailListBean")
public class IncomeDetailListBackingBean extends BaseBackingBean {
    
	private static final long serialVersionUID = -2631989147972388606L;
	
	@ManagedProperty(value = "#{incomeManageBS}")
	private IIncomeManageBS incomeManageBS;
	private IncomeSearchVO searchVO = new IncomeSearchVO();
	private LazyDataModel<IncomeDetailVO> dataModel;
	private IncomeDetailVO incomeVO = new IncomeDetailVO();
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
    public void init(){
		try {
			Object creditorId = ViewOper.getSessionTmpAttr("creditorId");
			if(null != creditorId && (creditorId instanceof Integer)){
				this.searchVO.setIncomeRelId((Integer)creditorId);
				this.searchVO.setIncomeTypeCd(AppConst.INCOME_TYPE_CD_CREDITOR);
				search();
			}else{
				this.alert("收益详情查看异常");
			}
		} catch (Exception e) {
			this.alert("收益详情查看异常");
		}
    }
	
	public void search() {
		super.search();
		if (searchVO != null) {
			try {        
				incomeVO = new IncomeDetailVO();
				dataModel = new LazyIncomeDataModel(searchVO, incomeManageBS);
			} catch (Exception e) {
				log.error("收益列表异常：", e);
				alert("收益列表异常："+e.getMessage());
			}
			
		}
	}
	
	public String reset () {
		searchVO = new IncomeSearchVO();
		return null;
	}

	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)){// 查看
			ViewOper.getSession().setAttribute("incomeId", incomeVO.getId());
		} 
	}
	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public IIncomeManageBS getIncomeManageBS() {
		return incomeManageBS;
	}

	public void setIncomeManageBS(IIncomeManageBS incomeManageBS) {
		this.incomeManageBS = incomeManageBS;
	}

	public IncomeSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(IncomeSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public LazyDataModel<IncomeDetailVO> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<IncomeDetailVO> dataModel) {
		this.dataModel = dataModel;
	}

	public IncomeDetailVO getIncomeVO() {
		return incomeVO;
	}

	public void setIncomeVO(IncomeDetailVO incomeVO) {
		this.incomeVO = incomeVO;
	}
	
}
