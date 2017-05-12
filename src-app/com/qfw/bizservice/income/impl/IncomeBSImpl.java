package com.qfw.bizservice.income.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.income.IIncomeBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.BussConst;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizIncomeDetailBO;
import com.qfw.model.bo.BizRepayDetailBO;

/**
 * 收益核心服务
 * @author kindion
 *
 */
@Service("iIncomeBS")
public class IncomeBSImpl extends BaseServiceImpl implements IIncomeBS {

	@Autowired
	public ITransferAccountsBS transferAccountsBS; 
	@Autowired
	public ISeqBS seqBS;
	@Autowired
	public IParamBS paramBS;
	
	@Autowired
	private IMsgTemplateBS msgTemplateBS;  //获取短信模块
	
	@Autowired
	private ICustInfoBS custInfoBS;   //客户信息
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void incomeForRepay(BizRepayDetailBO bizRepayDetailBO,Date batchDate) throws BizException {
		if(bizRepayDetailBO == null) throw new BizException("参数[bizRepayDetailBO]不能为空");
		if(batchDate==null) batchDate = new Date();
		bizRepayDetailBO = (BizRepayDetailBO)this.baseDAO.findById(BizRepayDetailBO.class, bizRepayDetailBO.getId());
		BigDecimal unIncomePrincipalAmt = bizRepayDetailBO.getUnIncomePrincipalAmt();//可分配本金
		BigDecimal unIncomeInterestAmt = bizRepayDetailBO.getUnIncomeInterestAmt();//可分配利息
		BigDecimal unIncomePenaltyAmt = bizRepayDetailBO.getUnIncomePenaltyAmt();//可分配罚息
		
		if((unIncomePrincipalAmt.add(unIncomeInterestAmt)).compareTo(BigDecimal.ZERO)<=0) return;//可分配金额小于0时不处理
		
		List<?> bizCreditorRightBOs = this.baseDAO.findByHQL("From BizCreditorRightBO where loanId = ? ", new Object[]{bizRepayDetailBO.getBizLoanBO().getId()});
		BizCreditorRightBO bizCreditorRightBO = null;
		BizIncomeDetailBO bizIncomeDetailBO = null;
		Map<Integer,BigDecimal> incomeAmtMap = new HashMap<Integer,BigDecimal>();
		
		Map<Integer,List> msgMap = new HashMap<Integer, List>();
		for (Object object : bizCreditorRightBOs) {
			String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
			bizIncomeDetailBO = new BizIncomeDetailBO();
			bizCreditorRightBO = (BizCreditorRightBO)object;

			BizCustomerBO cust = custInfoBS.findCustById(bizCreditorRightBO.getCustId());
			
			//组装收益明细表
			bizIncomeDetailBO.setIncomeRelId(bizCreditorRightBO.getId());//债权表ID
			bizIncomeDetailBO.setIncomeTypeCd(AppConst.INCOME_TYPE_CD_CREDITOR);//收益类型=债权
			bizIncomeDetailBO.setIncomeSourTypeCd(AppConst.INCOME_SOUR_TYPE_CD_REPAY);//收益来源=还款
			bizIncomeDetailBO.setIncomeSourRelId(bizRepayDetailBO.getId());//还款明细表ID
			bizIncomeDetailBO.setIncomeBaseAmt(bizCreditorRightBO.getUnretrieveAmt());//收益基础金额=债权未回收金额
			bizIncomeDetailBO.setIncomeRate(bizCreditorRightBO.getLoanRate());//收益利率 = 借款年利率
			bizIncomeDetailBO.setTenderScale(bizCreditorRightBO.getCrAmt().divide(bizCreditorRightBO.getLoanAmt(),AppConst.DECIMAL_AMT_RATE,BigDecimal.ROUND_HALF_UP));//投资占比=债权金额/借款金额,四舍五入取地板
			//如果是债权转让,收益开始时间为债权转让接手时间
			if(bizCreditorRightBO.getCrId()!=null && bizCreditorRightBO.getCrTranDate()!=null){
				bizIncomeDetailBO.setIncomeStartDate(bizCreditorRightBO.getCrTranDate());//债权接手时间
			}else{
				bizIncomeDetailBO.setIncomeStartDate(bizRepayDetailBO.getBizRepayPlanDetailBO().getStartInterestDate());//收益开始时间
			}
			if(bizRepayDetailBO.getPlatRepayedDate()!=null){
				bizIncomeDetailBO.setIncomeEndDate(bizRepayDetailBO.getPlatRepayedDate());//平台垫款日
			}else{
				bizIncomeDetailBO.setIncomeEndDate(bizRepayDetailBO.getRepayDate());//实际还款日
			}
			bizIncomeDetailBO.setIncomeDay((int)DateUtils.minuDay(bizIncomeDetailBO.getIncomeStartDate(), bizIncomeDetailBO.getIncomeEndDate()));//收益天数
			//如果是债权转让,先计算受让者的收益后在反推转让者的收益
			BigDecimal t_unincomeInterestAmt = BigDecimal.ZERO;
			BigDecimal t_unincomePnaltyAmt = BigDecimal.ZERO;
			
			if(bizCreditorRightBO.getCrId()!=null && bizCreditorRightBO.getCrTranDate()!=null){
				//判断转让的时间属于哪一期的分配
				if((bizCreditorRightBO.getCrTranDate().compareTo(bizRepayDetailBO.getBizRepayPlanDetailBO().getStartInterestDate())>=0 && (bizCreditorRightBO.getCrTranDate().compareTo(bizRepayDetailBO.getBizRepayPlanDetailBO().getRepayplanDate())<=0))){
					//收益金额 = 可分配利息*占比,四舍五入取地板*债权天数占比
					t_unincomeInterestAmt = (unIncomeInterestAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					t_unincomePnaltyAmt = (unIncomePenaltyAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					BigDecimal incomeAmt =t_unincomeInterestAmt.add(t_unincomePnaltyAmt);
					//债权天数占比
					BigDecimal crscale = BigDecimal.valueOf(DateUtils.minuDay(bizCreditorRightBO.getCrTranDate(), bizIncomeDetailBO.getIncomeEndDate())
							/DateUtils.minuDay(bizRepayDetailBO.getBizRepayPlanDetailBO().getStartInterestDate(), bizIncomeDetailBO.getIncomeEndDate()));
					bizIncomeDetailBO.setIncomeAmt(incomeAmt.multiply(crscale).setScale(AppConst.DECIMAL_AMT_PRECISION,BigDecimal.ROUND_DOWN));
					
					if(incomeAmtMap.containsKey(bizCreditorRightBO.getCrId())){
						incomeAmtMap.put(bizCreditorRightBO.getCrId(), incomeAmtMap.get(bizCreditorRightBO.getCrId()).add(bizIncomeDetailBO.getIncomeAmt()));
					}else{
						incomeAmtMap.put(bizCreditorRightBO.getCrId(), bizIncomeDetailBO.getIncomeAmt());
					}
					
				}else{
					Log.info("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"unIncomeInterestAmt:"+unIncomeInterestAmt+"bizIncomeDetailBO.getTenderScale()=="+bizIncomeDetailBO.getTenderScale());
					t_unincomeInterestAmt = (unIncomeInterestAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					t_unincomePnaltyAmt = (unIncomePenaltyAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					BigDecimal incomeAmt = t_unincomeInterestAmt.add(t_unincomePnaltyAmt);
					bizIncomeDetailBO.setIncomeAmt(incomeAmt.setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN));
				}
			}else{
				if(incomeAmtMap.containsKey(bizCreditorRightBO.getId())){
					//收益金额 = 可分配利息*原债权金额占比 - 债权转让收益金额
					//Log.info("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"unIncomeInterestAmt:"+unIncomeInterestAmt+"bizIncomeDetailBO.getTenderScale()=="+bizIncomeDetailBO.getTenderScale());
					//System.out.println("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"unIncomeInterestAmt:"+unIncomeInterestAmt+"bizIncomeDetailBO.getTenderScale()=="+bizIncomeDetailBO.getTenderScale());
					t_unincomeInterestAmt = (unIncomeInterestAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					//Log.info("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"t_unincomeInterestAmt:"+t_unincomeInterestAmt);
					//System.out.println("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"t_unincomeInterestAmt:"+t_unincomeInterestAmt);
					t_unincomePnaltyAmt = (unIncomePenaltyAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					BigDecimal incomeAmt =t_unincomeInterestAmt.add(t_unincomePnaltyAmt);
					//Log.info("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"incomeAmt:"+incomeAmt);
					//System.out.println("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"incomeAmt:"+incomeAmt);
					incomeAmtMap.put(bizCreditorRightBO.getCrId(), incomeAmt.subtract(incomeAmtMap.get(bizCreditorRightBO.getId())).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN));
				}else{
					//Log.info("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"unIncomeInterestAmt:"+unIncomeInterestAmt+"bizIncomeDetailBO.getTenderScale()=="+bizIncomeDetailBO.getTenderScale());
					//System.out.println("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"unIncomeInterestAmt:"+unIncomeInterestAmt+"bizIncomeDetailBO.getTenderScale()=="+bizIncomeDetailBO.getTenderScale());
					t_unincomeInterestAmt = (unIncomeInterestAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					//Log.info("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"t_unincomeInterestAmt:"+t_unincomeInterestAmt);
					t_unincomePnaltyAmt = (unIncomePenaltyAmt).multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN);
					//System.out.println("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"t_unincomeInterestAmt:"+t_unincomeInterestAmt);
					
					BigDecimal incomeAmt =t_unincomeInterestAmt.add(t_unincomePnaltyAmt);
					//Log.info("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"incomeAmt:"+incomeAmt);
					//System.out.println("bizCreditorRightBO.getId()=="+bizCreditorRightBO.getId()+"incomeAmt:"+incomeAmt);
					bizIncomeDetailBO.setIncomeAmt(incomeAmt.setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN));
				}
			}
			
			bizIncomeDetailBO.setIncomeDate(bizIncomeDetailBO.getIncomeEndDate());//收益日期
			BigDecimal PAmt = unIncomePrincipalAmt.multiply(bizIncomeDetailBO.getTenderScale()).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_DOWN); //回收本金 = 可分配本金*占比,,四舍五入取地板
			
			//判断是否最后一期,如果是最后一期,补齐0.01元问题
			Log.info("bizRepayDetailBO.getBizLoanBO().getLoanDueDate()=="+bizRepayDetailBO.getBizLoanBO().getLoanDueDate());
			//System.out.println("bizRepayDetailBO.getBizLoanBO().getLoanDueDate()=="+bizRepayDetailBO.getBizLoanBO().getLoanDueDate());
			Log.info("bizRepayDetailBO.getBizRepayPlanDetailBO().getRepayplanDate()=="+bizRepayDetailBO.getBizRepayPlanDetailBO().getRepayplanDate());
			//System.out.println("bizRepayDetailBO.getBizRepayPlanDetailBO().getRepayplanDate()=="+bizRepayDetailBO.getBizRepayPlanDetailBO().getRepayplanDate());
			Log.info("bizCreditorRightBO.getLoanId()=="+bizCreditorRightBO.getLoanId());
			//System.out.println("bizCreditorRightBO.getLoanId()=="+bizCreditorRightBO.getLoanId());
			Log.info("bizCreditorRightBO.getUnretrieveAmt()=="+bizCreditorRightBO.getUnretrieveAmt());
			//System.out.println("bizCreditorRightBO.getUnretrieveAmt()=="+bizCreditorRightBO.getUnretrieveAmt());
			Log.info("PAmt=="+PAmt);
			//System.out.println("PAmt=="+PAmt);
			Log.info("bizCreditorRightBO.getUnretrieveAmt().subtract(PAmt).compareTo(new BigDecimal(0.36))=="+bizCreditorRightBO.getUnretrieveAmt().subtract(PAmt).compareTo(new BigDecimal(0.36)));
			//System.out.println("bizCreditorRightBO.getUnretrieveAmt().subtract(PAmt).compareTo(new BigDecimal(0.36))=="+bizCreditorRightBO.getUnretrieveAmt().subtract(PAmt).compareTo(new BigDecimal(0.36)));
			String loanStatusCd= bizRepayDetailBO.getBizLoanBO().getLoanStatusCd();
			if(loanStatusCd.equals(AppConst.LOAN_STATUS_CD_JQ)){
				if (bizCreditorRightBO.getUnretrieveAmt().subtract(PAmt).compareTo(new BigDecimal(0.36)) <= 0 ){
					bizIncomeDetailBO.setIncomeTenderAmt(bizCreditorRightBO.getUnretrieveAmt());
					//System.out.println("bizIncomeDetailBO1=="+bizIncomeDetailBO.getIncomeTenderAmt());
				}else{
					bizIncomeDetailBO.setIncomeTenderAmt(PAmt);
					//System.out.println("bizIncomeDetailBO2=="+bizIncomeDetailBO.getIncomeTenderAmt());
				}
				
			}else{
				bizIncomeDetailBO.setIncomeTenderAmt(PAmt);
				//System.out.println("bizIncomeDetailBO3=="+bizIncomeDetailBO.getIncomeTenderAmt());
			}
			String param = paramBS.getParam(AppConst.PARAMETER_CODE_PTSYFL);
			if(param==null || "".equals(param)) throw new BizException("平台参数[平台收益费率]未设定");
			BigDecimal ptsyfl = new BigDecimal(param);
			bizIncomeDetailBO.setIncomeTakeAmt(bizIncomeDetailBO.getIncomeAmt().multiply(ptsyfl).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP));//平台收益抽成
			
			//add by yangjj 20151030 for vip收益
			BigDecimal vipPnaltyAmt = BigDecimal.ZERO;
			BigDecimal normalIncomeAmt = bizIncomeDetailBO.getIncomeAmt();//一般收益
			if(BussConst.YES_FLAG.equals(cust.getIsVip()) && BussConst.YES_FLAG.equals(bizCreditorRightBO.getIsVip())){//计算vip收益，不扣收平台收益
				BigDecimal vipIncomeScale = new BigDecimal(paramBS.getParam(AppConst.PARAMETER_VIP_INCOME_SCALE));
				vipPnaltyAmt = vipPnaltyAmt.add(bizIncomeDetailBO.getIncomeTenderAmt().multiply(vipIncomeScale));
				bizIncomeDetailBO.setIncomeAmt(normalIncomeAmt.add(vipPnaltyAmt));
			}
			
			bizIncomeDetailBO.setWorkItemId("0");
			createOperator(bizIncomeDetailBO);
			updateOperator(bizIncomeDetailBO);
			this.baseDAO.save(bizIncomeDetailBO);
			
			//处理债权表
			bizCreditorRightBO.setRetrieveAmt(bizCreditorRightBO.getRetrieveAmt().add(bizIncomeDetailBO.getIncomeTenderAmt()));//回收金额
			bizCreditorRightBO.setUnretrieveAmt(bizCreditorRightBO.getUnretrieveAmt().subtract(bizIncomeDetailBO.getIncomeTenderAmt()));//待收本金
			bizCreditorRightBO.setTotalProfitAmt(bizCreditorRightBO.getTotalProfitAmt().add(bizIncomeDetailBO.getIncomeAmt().subtract(bizIncomeDetailBO.getIncomeTakeAmt())));//累计收益
			bizCreditorRightBO.setNextGatherDate(null);//下次收款日 TODO 待加工处理
			
			if(loanStatusCd.equals(AppConst.LOAN_STATUS_CD_JQ)){
				bizCreditorRightBO.setSurplusPeriod(0);//剩余期数
			}else{
				bizCreditorRightBO.setSurplusPeriod(bizCreditorRightBO.getSurplusPeriod()-1);//剩余期数
			}
			
			if(bizCreditorRightBO.getSurplusPeriod()<=0){
				bizCreditorRightBO.setCrStatusCd(AppConst.CR_STATUS_CD_END);//状态
				bizCreditorRightBO.setSettleDate(ViewOper.getOperTime());//结清日期	
			}
			updateOperator(bizCreditorRightBO);
			baseDAO.update(bizCreditorRightBO);

			//处理还款明细表
			bizRepayDetailBO.setUnIncomePrincipalAmt(bizRepayDetailBO.getUnIncomePrincipalAmt().subtract(bizIncomeDetailBO.getIncomeTenderAmt()));//可分配本金
			bizRepayDetailBO.setUnIncomeInterestAmt(bizRepayDetailBO.getUnIncomeInterestAmt().subtract(t_unincomeInterestAmt));//可分配利息
			bizRepayDetailBO.setUnIncomePenaltyAmt(bizRepayDetailBO.getUnIncomePenaltyAmt().subtract(t_unincomePnaltyAmt));//可分配罚息
			bizRepayDetailBO.setTtlIncomePrincipalAmt(bizRepayDetailBO.getTtlIncomePrincipalAmt().add(bizRepayDetailBO.getUnIncomePrincipalAmt()));//总已分配本金
			bizRepayDetailBO.setTtlIncomeInterestAmt(bizRepayDetailBO.getTtlIncomeInterestAmt().add(bizRepayDetailBO.getUnIncomeInterestAmt()));//总已分配利息
			bizRepayDetailBO.setTtlIncomePenaltyAmt(bizRepayDetailBO.getTtlIncomePenaltyAmt().add(bizRepayDetailBO.getUnIncomePenaltyAmt()));//总已分配罚息
			
			List<Map<String,String>> msgList = new ArrayList<Map<String,String>>();
			
			//调用账户管理(转账服务) 【平台还款账户】资金收回转入【投资者会员账户】
			transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_ZJHS, 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMHK), 
					transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId()), bizIncomeDetailBO.getIncomeTenderAmt());
			try {
				if(bizIncomeDetailBO.getIncomeTenderAmt().compareTo(BigDecimal.ZERO) > 0){
					Map<String,String> dataMap = new HashMap<String,String>();
					BizAccountBO account = transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId());
					dataMap.put("BALANCE", NumberUtils.format2(account.getUsableBalAmt()));
					dataMap.put("AMT", NumberUtils.format2(bizIncomeDetailBO.getIncomeTenderAmt()));
					dataMap.put("EVENT_TYPE", AppConst.EVENTTYPE_BACK);
					if(msgMap.get(bizCreditorRightBO.getCustId()) != null){
						msgMap.get(bizCreditorRightBO.getCustId()).add(dataMap);
					}else{
						msgList.add(dataMap);
						msgMap.put(bizCreditorRightBO.getCustId(),msgList);
					}
					
				}
			} catch (Exception e) {
				log.error("回款短信发送封装异常:==========="+e.getMessage());
			}
			//调用账户管理(转账服务) 【平台还款账户】利息收益转入【投资者会员账户】
			transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_LXSY, 
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMHK), 
					transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId()), normalIncomeAmt);
			//调用账户管理(转账服务) 【投资者会员账户】收益抽成转入【平台收益账户】
			transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_PTSYCC, 
					transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId()),
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMSY), bizIncomeDetailBO.getIncomeTakeAmt());
			try {
				if(normalIncomeAmt.compareTo(BigDecimal.ZERO) > 0){
					Map<String,String> dataMap = new HashMap<String,String>();
					BizAccountBO account = transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId());
					dataMap.put("BALANCE", NumberUtils.format2(account.getUsableBalAmt()));
					dataMap.put("AMT", NumberUtils.format2(normalIncomeAmt));
					dataMap.put("EVENT_TYPE", AppConst.EVENTTYPE_INCOME);
					if(msgMap.get(bizCreditorRightBO.getCustId()) != null){
						msgMap.get(bizCreditorRightBO.getCustId()).add(dataMap);
					}else{
						msgList.add(dataMap);
						msgMap.put(bizCreditorRightBO.getCustId(),msgList);
					}
				}
			} catch (Exception e) {
				log.error("收益短信发送封装异常:==========="+e.getMessage());
			}
			if(vipPnaltyAmt.compareTo(BigDecimal.ZERO) > 0){//vip收益
				//调用账户管理(转账服务) 【平台收益账户】vip收益转入【投资者会员账户】
				transferAccountsBS.transferAccount(txNO,AppConst.TRADE_TYPE_VIP_INCOME, 
						transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMSY),
						transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId()),
						vipPnaltyAmt);
				try {
					Map<String,String> dataMap = new HashMap<String,String>();
					BizAccountBO account = transferAccountsBS.getAccountBO(bizCreditorRightBO.getCustId());
					dataMap.put("BALANCE",NumberUtils.format2(account.getUsableBalAmt()));
					dataMap.put("AMT", NumberUtils.format2(vipPnaltyAmt));
					dataMap.put("EVENT_TYPE", AppConst.EVENTTYPE_VIP_INCOME);
					if(msgMap.get(bizCreditorRightBO.getCustId()) != null){
						msgMap.get(bizCreditorRightBO.getCustId()).add(dataMap);
					}else{
						msgList.add(dataMap);
						msgMap.put(bizCreditorRightBO.getCustId(),msgList);
					}
				} catch (Exception e) {
					log.error("vip收益短信发送封装异常:==========="+e.getMessage());
				}
			}
		}
		
		//更新还款明细表
		bizRepayDetailBO.setIsIncomeCd(AppConst.IS_INCOME_CD_CD_YES);//是否完成收益分配
		updateOperator(bizRepayDetailBO);
		this.baseDAO.update(bizRepayDetailBO);
		
		//调用短信发送
		try {
			if(!msgMap.isEmpty()){
				Set<Entry<Integer, List>> set = msgMap.entrySet();
				Iterator<Entry<Integer, List>> it = set.iterator();
				while (it.hasNext()) {
					Entry<Integer, List> entry = it.next();
					BizCustomerBO cust = custInfoBS.findCustById(entry.getKey());
					
					List<Map<String,String>> msgList = entry.getValue();
					for(Map dataMap : msgList){
						try {
							dataMap.put("CUST_NAME", cust.getCustName());
							dataMap.put("DATE", DateUtils.getYmdhms(new Date()));
						
							msgTemplateBS.sendMsg(dataMap.get("EVENT_TYPE").toString(), cust, dataMap);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.error("收益分配短信发送失败"+e.getMessage());
		}
	}

}
