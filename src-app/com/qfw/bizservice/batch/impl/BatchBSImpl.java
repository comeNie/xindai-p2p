package com.qfw.bizservice.batch.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.batch.dao.IBatchJobDAO;
import com.qfw.batch.model.bo.BatchErrorDetailBO;
import com.qfw.bizservice.batch.IBatchBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.custinfo.recommendation.IRecommendationBS;
import com.qfw.bizservice.income.IIncomeBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.bizservice.transaction.chanel.IPayInterfaceBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizArrearsDetailBO;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizRepayDetailBO;
import com.qfw.model.vo.repay.RepayInfoVO;
import com.qfw.model.vo.transaction.OrderDetailVO;
import com.qfw.model.vo.transaction.OrderVO;
import com.qfw.model.vo.transaction.QueryOrderVO;

@Service("batchBS")
public class BatchBSImpl extends BaseServiceImpl implements IBatchBS {
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Autowired
	private IRepayBS repayBS;
	@Autowired
	private ILoanBS loanBS;
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	@Autowired
	private IIncomeBS iIncomeBS;
	@Autowired
	private IParamBS paramBS;
	@Autowired
	@Qualifier("payInterfaceBS")
	private IPayInterfaceBS payInterfaceBS;
	@Autowired
	private ICustInfoBS custInfoBS;
	@Autowired
	private ITransferAccountsBS transferAccountsBS;
	@Autowired
	private ICreditorRightBS creditorRightBS;
	@Autowired
	private IBatchJobDAO iBatchJobDAO;
	
	@Autowired
	private IRecommendationBS recommendationBS;
	
	
	@Override
	public void autoRepayForFull(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		String hqlStr = "From BizArrearsDetailBO Where repayPlanDate = ? and (arrearsStatusCd = ? or arrearsStatusCd = ?) Order by custId,bizLoanBO.id,period ASC";
		List<?> bizArrearsDetailBOs = this.baseDAO.findByHQL(hqlStr, new Object[]{batchDate,AppConst.ARREARS_STATUS_CD_WH,AppConst.ARREARS_STATUS_CD_PTDF});
		if(CollectionUtil.isEmpty(bizArrearsDetailBOs))
			return ;
		for (Object object : bizArrearsDetailBOs) {
			BizArrearsDetailBO bizArrearsDetailBO = (BizArrearsDetailBO)object;
			try {
				repayBS.repayForFull(AppConst.REPAY_WAY_CD_ZC,bizArrearsDetailBO.getBizLoanBO().getId(),bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
			} catch (BizException e) {
				//出现异常,自动扣款失败,调用短信提醒 TODO
				//msgTemplateBS.sendMsg(null, null, null, null);
				log.error("自动到期还款跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				log.error("自动到期还款跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}
	
	@Override
	public void autoRepayOverdueForFull(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		String hqlStr = "From BizArrearsDetailBO Where repayPlanDate < ? and (arrearsStatusCd = ? or arrearsStatusCd = ?) Order by custId,bizLoanBO.id,period ASC";
		List<?> bizArrearsDetailBOs = this.baseDAO.findByHQL(hqlStr, new Object[]{batchDate,AppConst.ARREARS_STATUS_CD_WH,AppConst.ARREARS_STATUS_CD_PTDF});
		if(CollectionUtil.isEmpty(bizArrearsDetailBOs))
			return ;
		for (Object object : bizArrearsDetailBOs) {
			BizArrearsDetailBO bizArrearsDetailBO = (BizArrearsDetailBO)object;
			try {
				repayBS.repayForFull(AppConst.REPAY_WAY_CD_ZC,bizArrearsDetailBO.getBizLoanBO().getId(),bizArrearsDetailBO.getBizRepayPlanDetailBO().getId());
			} catch (BizException e) {
				//出现异常,自动扣款失败,调用短信提醒 TODO
				//msgTemplateBS.sendMsg(null, null, null, null);
				log.error("自动逾期还款跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				log.error("自动逾期还款跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}
	
	@Override
	public void batchDelayLoan(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("借款延期处理批开始,当前跑批日期为:"+batchDate);
		String hqlStr = "From BizArrearsDetailBO Where repayPlanDate <= ? and (repayPlanDate+graceDays > ? or delayDays<=graceDays) and lastInterestDate<=? and (arrearsStatusCd = ? or arrearsStatusCd = ? ) and (arrearsFlagCd!=? and arrearsFlagCd!=?)";
		List<?>  bizArrearsDetailBOs= this.baseDAO.findByHQL(hqlStr, new Object[]{batchDate,batchDate,batchDate,AppConst.ARREARS_STATUS_CD_WH,AppConst.ARREARS_STATUS_CD_PTDF,AppConst.ARREARS_FLAG_CD_YQ,AppConst.ARREARS_FLAG_CD_DZ});
		if(CollectionUtil.isEmpty(bizArrearsDetailBOs))
			return;
		for (Object object : bizArrearsDetailBOs) {
			BizArrearsDetailBO bizArrearsDetailBO = (BizArrearsDetailBO)object;
			try {
				loanBS.delayLoan(bizArrearsDetailBO, batchDate);
			} catch (BizException e) {
				log.error("借款延期处理跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				log.error("借款延期处理跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}
	
	
	@Override
	public void batchOverdueLoan(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("借款逾期处理批开始,当前跑批日期为:"+batchDate);
		/*String hqlStr = "From BizArrearsDetailBO Where repayPlanDate <= ? and repayPlanDate+graceDays <= ? and lastInterestDate<=? and (arrearsStatusCd = ? or arrearsStatusCd = ?) and arrearsFlagCd!=? ";
		List<?>  bizArrearsDetailBOs= baseDAO.findByHQL(hqlStr, new Object[]{batchDate,batchDate,batchDate,AppConst.ARREARS_STATUS_CD_WH,AppConst.ARREARS_STATUS_CD_PTDF,AppConst.ARREARS_FLAG_CD_DZ});
		if(CollectionUtil.isEmpty(bizArrearsDetailBOs))
			return;
		
		//处理欠款信息表
		for (Object object : bizArrearsDetailBOs) {
			BizArrearsDetailBO bizArrearsDetailBO =  (BizArrearsDetailBO)object;
			try {
				loanBS.overdueLoan(bizArrearsDetailBO, batchDate);
			} catch (BizException e) {
				log.error("借款逾期处理跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				log.error("借款逾期处理跑批失败,异常信息为:"+e.getMessage());
			}
		}*/
		
		String sqlstr= "SELECT ID FROM BIZ_ARREARS_DETAIL WHERE REPAY_PLAN_DATE <= DATE('"+DateUtils.getYmd(batchDate).toString()+"') AND (REPAY_PLAN_DATE+GRACE_DAYS)<= DATE('"+DateUtils.getYmd(batchDate).toString()+"') AND LAST_INTEREST_DATE<= DATE('"+DateUtils.getYmd(batchDate).toString()+"') AND (ARREARS_STATUS_CD=? OR ARREARS_STATUS_CD=?) AND ARREARS_FLAG_CD <> ? ";
		List<?>  bizArrearsDetailBOs = this.baseDAO.findBySQL(sqlstr, new Object[]{AppConst.ARREARS_STATUS_CD_WH,AppConst.ARREARS_STATUS_CD_PTDF,AppConst.ARREARS_FLAG_CD_DZ});
		//System.out.println("sqlstr[===="+sqlstr);
		if(CollectionUtil.isEmpty(bizArrearsDetailBOs))
			return;
		BizArrearsDetailBO bizArrearsDetailBO = new BizArrearsDetailBO();
		for (Object obj : bizArrearsDetailBOs) {
			Integer arrearsId = (Integer)((Map<?,?>)obj).get("ID");
			bizArrearsDetailBO.setId(arrearsId);
			try {
				loanBS.overdueLoan(bizArrearsDetailBO, batchDate);
			} catch (BizException e) {
				log.error("借款逾期处理跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				log.error("借款逾期处理跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}

	@Override
	public void batchIncomeForRepay(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("收益批处理批开始,当前跑批日期为:"+batchDate);
		String sqlstr= "select distinct LOAN_ID from BIZ_REPAY_DETAIL where IS_INCOME_CD = ? and (UNINCOME__PRINCIPAL_AMT+UNINCOME_INTEREST_AMT+UNINCOME_PENALTY_AMT)>0 order by CUST_ID";
		List<?>  loanIds = this.baseDAO.findBySQL(sqlstr, new Object[]{AppConst.IS_INCOME_CD_NO});
		for (Object obj : loanIds) {
			Integer loanId = (Integer)((Map<?,?>)obj).get("LOAN_ID");
			if(log.isInfoEnabled()) log.info("开始对[loanId]="+loanId+"进行分配收益");
			sqlstr= "SELECT ID FROM BIZ_REPAY_DETAIL WHERE IS_INCOME_CD = ? AND (UNINCOME__PRINCIPAL_AMT+UNINCOME_INTEREST_AMT+UNINCOME_PENALTY_AMT)>0 AND LOAN_ID=?  ORDER BY PERIOD";
			List<?> repayIds = this.baseDAO.findBySQL(sqlstr,new Object[]{AppConst.IS_INCOME_CD_NO,loanId});
			if(CollectionUtil.isEmpty(repayIds))
				return;
			for (Object object : repayIds) {
				try {
					BizRepayDetailBO bizRepayDetailBO = new BizRepayDetailBO();
					bizRepayDetailBO.setId((Integer)((Map<?,?>)object).get("ID"));
					iIncomeBS.incomeForRepay(bizRepayDetailBO, batchDate);
				} catch (Exception e) {
					 
					log.error("收益批处理跑批失败,异常信息为:"+e.getMessage());
				}
			}
		}
		
	}
	
	@Override
	public void batchCreditorCount(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("推荐投资统计批处理批开始,当前跑批日期为:"+batchDate);
		String strDate = DateUtils.getYmd(batchDate);
		String hqlStr = "select c From BizCreditorRightBO c,BizLoanBO l "
				+ " Where c.loanId = l.id and not EXISTS "
				+ " (select cc.loanId from BizCreditorCountBO cc where cc.loanId = c.loanId)"
				+ " and DATE_FORMAT(l.sysCreateTime, '%Y-%m-%d') = ?";
		List<?>  bos= this.baseDAO.findByHQL(hqlStr, new Object[]{strDate});
		if(CollectionUtil.isEmpty(bos))
			return;
		for (Object object : bos) {
			BizCreditorRightBO bizCreditorRightBO = (BizCreditorRightBO)object;
			try {
				recommendationBS.creatCreditorCount(bizCreditorRightBO);
			} catch (BizException e) {
				log.error("推荐投资统计处理跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				log.error("推荐投资统计处理跑批失败,异常信息为:"+e.getMessage());
			}
		}
		
	}
	
	
	@Override
	public void batchInvalidLoan(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("未满标X天流标批处理批开始,当前跑批日期为:"+batchDate);
		String param = paramBS.getParam(AppConst.PARAMETER_CODE_WMBXTLB);
		if(param==null || "".equals(param)) {
			log.error("参数[未满标X天流标]未设定");
			throw new BizException("参数[未满标X天流标]未设定");
		}
		Integer day = Integer.valueOf(param);
		Date expire = DateUtils.addDay(batchDate, -day);
//		String sqlstr = "SELECT ID FROM BIZ_LOAN_APPROVE WHERE (TENDER_DUE_TIME + ?) <= ? AND APPROVE_STATUS_CD NOT IN (?,?,?) ORDER BY ID";
//		List<?> bizLoanApproves = this.baseDAO.findBySQL(sqlstr, new Object[]{day,DateUtils.getYmd(batchDate) ,AppConst.APPROVE_STATUS_CD_CANCEL,AppConst.APPROVE_STATUS_CD_FULL,AppConst.APPROVE_STATUS_CD_LOAN});
		String sqlstr = "SELECT ID FROM BIZ_LOAN_APPROVE WHERE TENDER_DUE_TIME <= ? AND APPROVE_STATUS_CD = ? ORDER BY ID";
		List<?> bizLoanApproves = this.baseDAO.findBySQL(sqlstr, new Object[]{expire,AppConst.APPROVE_STATUS_CD_TENDERING});
		for (Object object : bizLoanApproves) {
			try {
				Integer loanApproveId = (Integer)((Map)object).get("ID");
				loanBS.invalidLoan(loanApproveId);
			} catch (Exception e) {
				 
				log.error("未满标"+day+"天流标批处理跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}
	
	@Override
	public void batchInvalidCreditorRightTran(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("债权转让标失效批处理批开始,当前跑批日期为:"+batchDate);
		String sqlstr = "SELECT ID FROM BIZ_CREDITOR_RIGHT_TRAN WHERE TRAN_DUE_DATE <= ? ";
		List<?> bizCreditorRightTrans = this.baseDAO.findBySQL(sqlstr, new Object[]{batchDate});
		for (Object object : bizCreditorRightTrans) {
			try {
				Integer crtId = (Integer)((Map)object).get("ID");
				creditorRightBS.invalidCreditorRightTran(crtId);
			} catch (Exception e) {
				 
				log.error("债权转让标失效批处理跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}
	
	@Override
	//平台账号${accno}在${date}有一笔${amt}元还款，请在${date}前还款，避免产生息费，详咨XXXXXXXXXXXX
	public void batchRepayForTip(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("还款提醒批处理批开始,当前跑批日期为:"+batchDate);
		String param = paramBS.getParam(AppConst.PARAMETER_CODE_HKTX);
		if(param==null || "".equals(param)) {
			log.error("参数[提前X天还款提醒]未设定");
			throw new BizException("参数[提前X天还款提醒]未设定");
		}
		Integer day = Integer.valueOf(param);
		String repayDate = DateUtils.getYmd(DateUtils.addDay(batchDate, day));
		String sqlstr = "SELECT d.ID,d.CUST_ID,d.LOAN_ID,d.REPAYPLAN_DATE,l.LOAN_NAME FROM BIZ_REPAY_PLAN_DETAIL d,BIZ_LOAN l"
				+ " WHERE d.LOAN_ID = l.ID"
//				+ " AND (REPAYPLAN_DATE - "+day+") <= DATE('"+DateUtils.getYmd(batchDate)+"') "
//				+ " AND  REPAYPLAN_DATE >= DATE('"+DateUtils.getYmd(batchDate)+"')"; 
				+ " AND d.REPAYPLAN_DATE = '"+repayDate+"'";
		List<?> repayPlans = this.baseDAO.findBySQL(sqlstr);
		
		RepayInfoVO repayInfoVO = null ; 
		for (Object object : repayPlans) {
			try {
				Integer repayPlanId = (Integer)((Map<?,?>)object).get("ID");
				Integer loanId = (Integer)((Map<?,?>)object).get("LOAN_ID");
				Integer custId = (Integer)((Map<?,?>)object).get("CUST_ID");
				Date repayPlanDate = (Date)((Map<?,?>)object).get("REPAYPLAN_DATE");
				String loanName = (String)((Map<?,?>)object).get("LOAN_NAME");
				repayInfoVO = repayBS.repayForTrial(AppConst.REPAY_WAY_CD_ZC, loanId, repayPlanId);
				BizCustomerBO bizCustomerBO = custInfoBS.findCustById(custId);
				BigDecimal ttlRepayedAmt =  repayInfoVO.getTtlRepayedAmt();
				Map<String,String> dataMap = new HashMap<String,String>();
				dataMap.put("LOAN_NAME", loanName);
				dataMap.put("DATE", repayPlanDate.toString());
				dataMap.put("AMT", NumberUtils.format2(ttlRepayedAmt));
				dataMap.put("CUST_NAME", bizCustomerBO.getCustName());
				
				msgTemplateBS.sendMsg(AppConst.EVENTTYPE_REAPY_REMIND, bizCustomerBO, dataMap);
			} catch (Exception e) {
				 
				log.error("短信信息发送失败:"+e.getMessage());
			}
		}
	}

	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public void clearUserErrorCount(Date batchDate)throws BizException{
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("每天清空登陆失败数批处理批开始,当前跑批日期为:"+batchDate);
		try {
			String sqlstr = "UPDATE SYS_USER SET ERROR_COUNT = 0";
			this.getJdbcTemplate().execute(sqlstr);
		} catch (Exception e) {
			 
			log.error("每天清空登陆失败数批处理批失败:"+e.getMessage());
		}
	}
	
	@Transactional(rollbackFor = BizException.class,propagation = Propagation.REQUIRED)
	public void batchPostLoanStatus(Date batchDate)throws BizException{
		if(batchDate==null) batchDate = new Date();
		if(Log.isInfoEnabled()) Log.info("贷后任务状态更新批处理批开始,当前跑批日期为:"+batchDate);
		try {
			String sqlstr = "UPDATE POSTLOAN_TASK_INFO SET POSTLOAN_STATUS ='"+AppConst.POSTLOAN_STATUS_CD_OVER+"' WHERE FINISH_DATE <=DATE('"+DateUtils.getYmd(batchDate)+"') AND POSTLOAN_STATUS NOT IN ('"+AppConst.POSTLOAN_STATUS_CD_OVER+"','"+AppConst.POSTLOAN_STATUS_CD_END+"')";
			this.getJdbcTemplate().execute(sqlstr);
		} catch (Exception e) {
			 
			log.error("贷后任务状态更新批处理批失败:"+e.getMessage());
		}
	}
	
	/**
	 * 查询订单信息
	 * @param batchDate
	 * @throws BizException
	 */
	public void queryOrder(Date batchDate) throws BizException{
		QueryOrderVO queryOrderVO = new QueryOrderVO();
		queryOrderVO.setBeginTime(DateUtils.getDateString("yyyyMMdd", batchDate)+"000000");
		queryOrderVO.setEndTime(DateUtils.getDateString("yyyyMMdd", batchDate)+"235959");
		int pageIndex = 1;
		int pageCount = 1;
		do{
			OrderVO orderVO = payInterfaceBS.queryOrder(queryOrderVO);
			if(orderVO == null){
				return;
			}
			List<OrderDetailVO> orderDetails = orderVO.getOrderDetails();
			if(CollectionUtil.isEmpty(orderDetails)){
				return;
			}
			
			//保存orderDetails start
			//保存orderDetails end
			int count = Integer.valueOf(orderVO.getResultCount());
			int pageSize = Integer.valueOf(orderVO.getPageSize());
			pageCount = (count + pageSize - 1)/pageSize;
			pageIndex = Integer.valueOf(orderVO.getPageIndex());
			queryOrderVO.setPageIndex(String.valueOf(pageIndex+1));
		}while(pageIndex != pageCount);
		
	}

	@Override
	public void autoRepayForFullDh(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		String hqlStr = "From BizArrearsDetailBO bd inner join fetch bd.bizLoanBO bl Where bd.repayPlanDate = ? and (bd.arrearsStatusCd = ? or bd.arrearsStatusCd = ?) and bl.paymentWayCd=? Order by bd.custId,bl.id,bd.period ASC";
		List<?> bizArrearsDetailBOs = this.baseDAO.findByHQL(hqlStr, new Object[]{batchDate,AppConst.ARREARS_STATUS_CD_WH,AppConst.ARREARS_STATUS_CD_PTDF,AppConst.PAYMENT_WAY_CD_TRUSTEE});
		BatchErrorDetailBO BD = null;
		String err_desc = null;
		String exception_desc = null;
		if(CollectionUtil.isEmpty(bizArrearsDetailBOs)){
			BD = new BatchErrorDetailBO(batchDate, "到期自动还款（代付）", "这种写法HQL，找不到数据", "");
			iBatchJobDAO.save(BD);
			return ;
		}
		
		
		
		for (Object object : bizArrearsDetailBOs) {
			BizArrearsDetailBO bizArrearsDetailBO = (BizArrearsDetailBO)object;
			try {
				err_desc = "还款借据号："+bizArrearsDetailBO.getBizLoanBO().getId()+";代还人："+bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId()+";期数:"+bizArrearsDetailBO.getPeriod();
				BD = new BatchErrorDetailBO(batchDate, "到期自动还款（代付）", err_desc, "");
				iBatchJobDAO.save(BD);
				repayBS.repayForFullDh(AppConst.REPAY_WAY_CD_ZC,bizArrearsDetailBO.getBizLoanBO().getId(),bizArrearsDetailBO.getBizRepayPlanDetailBO().getId(),bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId());
			} catch (BizException e) {
				//出现异常,自动扣款失败,调用短信提醒 TODO
				//msgTemplateBS.sendMsg(null, null, null, null);
				err_desc = "还款借据号："+bizArrearsDetailBO.getBizLoanBO().getId()+";代还人："+bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId()+";期数:"+bizArrearsDetailBO.getPeriod();
				if(e.getMessage().length() > 399){
					exception_desc = e.getMessage().substring(0, 399);
				}else{
					exception_desc = e.getMessage();
				}
				
				BD = new BatchErrorDetailBO(batchDate, "到期自动还款（代付）", err_desc, exception_desc);
				log.error("自动到期还款跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				err_desc = "还款借据号："+bizArrearsDetailBO.getBizLoanBO().getId()+";代还人："+bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId();
				if(e.getMessage().length() > 399){
					exception_desc = e.getMessage().substring(0, 399);
				}else{
					exception_desc = e.getMessage();
				}
				BD = new BatchErrorDetailBO(batchDate, "到期自动还款（代付）", err_desc, exception_desc);
				log.error("自动到期还款跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}

	@Override
	public void autoRepayOverdueForFullDh(Date batchDate) throws BizException {
		if(batchDate==null) batchDate = new Date();
		BatchErrorDetailBO BD = null;
		String err_desc = null;
		String exception_desc = null;
		String hqlStr = "From BizArrearsDetailBO bd inner join fetch bd.bizLoanBO bl Where bd.repayPlanDate < ? and (bd.arrearsStatusCd = ? or bd.arrearsStatusCd = ?) and bl.paymentWayCd=? Order by bd.custId,bl.id,bd.period ASC";
		List<?> bizArrearsDetailBOs = this.baseDAO.findByHQL(hqlStr, new Object[]{batchDate,AppConst.ARREARS_STATUS_CD_WH,AppConst.ARREARS_STATUS_CD_PTDF,AppConst.PAYMENT_WAY_CD_TRUSTEE});
		if(CollectionUtil.isEmpty(bizArrearsDetailBOs)){
			BD = new BatchErrorDetailBO(batchDate, "逾期自动还款（代付）", "无数据", "");
			iBatchJobDAO.save(BD);
			return ;
		}
			
		for (Object object : bizArrearsDetailBOs) {
			BizArrearsDetailBO bizArrearsDetailBO = (BizArrearsDetailBO)object;
			try {

				err_desc = "还款借据号："+bizArrearsDetailBO.getBizLoanBO().getId()+";代还人："+bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId()+";期数:"+bizArrearsDetailBO.getPeriod();
				BD = new BatchErrorDetailBO(batchDate, "期自动还款（代付）", err_desc, "");
				iBatchJobDAO.save(BD);
				repayBS.repayForFullDh(AppConst.REPAY_WAY_CD_ZC,bizArrearsDetailBO.getBizLoanBO().getId(),bizArrearsDetailBO.getBizRepayPlanDetailBO().getId(),bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId());
			} catch (BizException e) {
				if(e.getMessage().length() > 399){
					exception_desc = e.getMessage().substring(0, 399);
				}else{
					exception_desc = e.getMessage();
				}
				err_desc = "还款借据号："+bizArrearsDetailBO.getBizLoanBO().getId()+";代还人："+bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId()+";期数:"+bizArrearsDetailBO.getPeriod();
				BD = new BatchErrorDetailBO(batchDate, "期自动还款（代付）", err_desc, exception_desc);
				iBatchJobDAO.save(BD);
				log.error("自动逾期还款跑批失败,异常信息为:"+e.getMessage());
			}catch (Exception e) {
				if(e.getMessage().length() > 399){
					exception_desc = e.getMessage().substring(0, 399);
				}else{
					exception_desc = e.getMessage();
				}
				err_desc = "还款借据号："+bizArrearsDetailBO.getBizLoanBO().getId()+";代还人："+bizArrearsDetailBO.getBizLoanBO().getTrusteeCustId()+";期数:"+bizArrearsDetailBO.getPeriod();
				BD = new BatchErrorDetailBO(batchDate, "期自动还款（代付）", err_desc, exception_desc);
				iBatchJobDAO.save(BD);
				log.error("自动逾期还款跑批失败,异常信息为:"+e.getMessage());
			}
		}
	}
	public static void main(String[] args) {
		String str = "5355353535";
		//System.out.println(str.substring(0, 1222));
	}

}
