package com.qfw.bean.dailyInfo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.model.vo.custinfo.CustInfoVO;

/**
 * pm币查询
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="platInfoBean")
public class PlatInfoBackingBean extends BaseBackingBean {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{custInfoBS}")
	private ICustInfoBS custInfoBS;
	
	private CustInfoVO custInfoVO = new CustInfoVO();
	
	@PostConstruct
    public void init(){
		try {
			custInfoVO = custInfoBS.getPlatCustInfo();
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public CustInfoVO getCustInfoVO() {
		return custInfoVO;
	}

	public void setCustInfoVO(CustInfoVO custInfoVO) {
		this.custInfoVO = custInfoVO;
	}
}
