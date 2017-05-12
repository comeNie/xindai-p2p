package com.qfw.bizservice.repay.arrears.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.charge.IChargeBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.bizservice.repay.arrears.IRepayArrearsBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.repay.IRepayDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizArrearsDetailBO;
import com.qfw.model.bo.BizCostBO;
import com.qfw.model.bo.BizLoanBO;
import com.qfw.model.bo.BizRepayDetailBO;
import com.qfw.model.bo.BizRepayPlanDetailBO;
import com.qfw.model.vo.charge.ChargeVO;
import com.qfw.model.vo.repay.RepayPlanDetailVO;

@Service("repayArrearsBS")
public class RepayArrearsBSImpl extends BaseServiceImpl implements IRepayArrearsBS {
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Autowired
	private IRepayDAO repayDAO;
	@Autowired
	private ITransferAccountsBS transferAccountsBS;
	@Autowired
	private ISeqBS seqBS;
	@Autowired
	private IChargeBS chargeBS;
	@Autowired
	private IParamBS paramBS;
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public List<BizRepayPlanDetailBO> queryArrearsDetail(Integer loanId) throws BizException{
		List<BizRepayPlanDetailBO> repayPlanDetails = null;//还款计划明细表
		try {
			if(loanId == null) throw new BizException("参数[loanId]不能为空");
			List<BizArrearsDetailBO> arrearsDetails = new ArrayList<BizArrearsDetailBO>();
			
			repayPlanDetails = this.repayDAO.queryRepayPlanDetail(loanId);
			if(null != repayPlanDetails && repayPlanDetails.size()>0){
				for (BizRepayPlanDetailBO repayPlan : repayPlanDetails) {
					arrearsDetails.add(repayPlan.getBizArrearsDetailBO());
				}
			}
		} catch (Exception e) {
			 
		}
		return repayPlanDetails;
	}
	
}
