package com.qfw.bizservice.creditor.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.creditor.IAutoTenderConfigBS;
import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.creditor.ICreditorManageDAO;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAutoTenderConfigBO;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.platform.model.transfer.TransferSearchVo;
import com.qfw.platform.model.transfer.TransferVo;

/**
 * 债权管理服务
 * @author ji
 *
 */
@Service("autoTenderConfigBS")
public class AutoTenderConfigBSImpl extends BaseServiceImpl implements IAutoTenderConfigBS {
	
	@Autowired
	@Qualifier("baseDAO")
	private IBaseDAO baseDAO;	
	
	
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	
	/**
	 * 获取自动投标配置信息
	 */
	public BizAutoTenderConfigBO findAutoTenderConfigBOByCustId(Integer custId,Integer isAuto){
		
		List<BizAutoTenderConfigBO>  bizCreditorRightBOs = 
			this.getHibernateTemplate().find("From BizAutoTenderConfigBO Where custId = ? and isAuto=? ",custId,isAuto);
		if( bizCreditorRightBOs.size()>0 ){
			return (BizAutoTenderConfigBO)bizCreditorRightBOs.get(0);
		}else{
			
			return null;
		}
		
	}

	/**
	 * 获取自动投标配置信息
	 */
	public BizAutoTenderConfigBO findAutoTenderConfigBOByCustId(Integer custId){
		
		List<BizAutoTenderConfigBO>  bizCreditorRightBOs = 
			this.getHibernateTemplate().find("From BizAutoTenderConfigBO Where custId = ? ",custId);
		if( bizCreditorRightBOs.size()>0 ){
			return (BizAutoTenderConfigBO)bizCreditorRightBOs.get(0);
		}else{
			return null;
		}
		
	}
}
