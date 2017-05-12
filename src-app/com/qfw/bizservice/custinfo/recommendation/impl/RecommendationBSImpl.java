package com.qfw.bizservice.custinfo.recommendation.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.custinfo.recommendation.IRecommendationBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.vo.MessageVO;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.NumberUtils;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCreditorCountBO;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.custinfo.CustInfoVO;
@Service("recommendationBS")
public class RecommendationBSImpl extends BaseServiceImpl implements IRecommendationBS {
	
	@Autowired
	private ICustInfoBS custInfoBS;
	
	@Autowired
	private IUserBS userBS;
	
	@Autowired
	private ITransferAccountsBS transferAccountsBS;
	@Autowired
	@Qualifier("paramBS")
	private IParamBS paramBS;
	
	@Autowired
	public ISeqBS seqBS;
	
	@Autowired
	private IMsgTemplateBS msgTemplateBS;  //获取短信模块
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public MessageVO recommendDeduct(Integer custId,BigDecimal recommendPercent,int count,BigDecimal crAmt,String txNO) throws BizException {
		if(custId==null) throw new BizException("参数[custId]不能为空");
		if(crAmt==null) throw new BizException("参数[crAmt]不能为空");
		BigDecimal transferAmt = new BigDecimal(0);
		BizAccountBO outAccountBO = new BizAccountBO();
		BizAccountBO inAccountBO = new BizAccountBO();
		BigDecimal recScale = new BigDecimal(0);
		String para = "0";
		MessageVO vo = null;
		
		CustInfoVO custInfoVO = custInfoBS.findCustInfoById(custId);
		if(custInfoVO != null && custInfoVO.getRefereeId() != null
				&& custInfoVO.getRefereeId() > 0){
			SysUser sysUser = userBS.findUserByCustId(custInfoVO.getRefereeId());
			if(sysUser != null && AppConst.USER_FREEZE_YES.equals(sysUser.getStatus())){
				return null;
			}
			
			outAccountBO.setAccountTypeCd(AppConst.ACCOUNT_TYPE_PMSY);
			inAccountBO.setAccountTypeCd(AppConst.ACCOUNT_TYPE_CUST);
			inAccountBO.setCustId(custInfoVO.getRefereeId());
			if(recommendPercent == null){
				if(count >=1 && count < 3){
					para = paramBS.getParam(AppConst.PROJECT_ONE_MON);
				}else if(count >=3 && count < 6){
					para = paramBS.getParam(AppConst.PROJECT_THREE_MON);
				}else if(count >=6 && count < 12){
					para = paramBS.getParam(AppConst.PROJECT_SIX_MON);
				}else if(count >= 12){
					para = paramBS.getParam(AppConst.PROJECT_TWELVE_MON);
				}
				recScale = new BigDecimal(para);
			}else{
				recScale = recommendPercent;
			}
			
			
			transferAmt = crAmt.multiply(recScale);
			transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_TJTC, outAccountBO, inAccountBO, transferAmt);
			try {
				vo = new MessageVO();
				BizCustomerBO cust = custInfoBS.findCustById(custInfoVO.getRefereeId());
				BizAccountBO account = transferAccountsBS.getAccountBO(custInfoVO.getRefereeId());
				vo.setBalance(NumberUtils.format2(account.getUsableBalAmt()));
				vo.setCustName(cust.getCustName());
				vo.setDate(DateUtils.getYmdhms(new Date()));
				vo.setTxAmt(NumberUtils.format2(transferAmt));
				vo.setCustId(custInfoVO.getRefereeId());
				return vo;
			} catch (Exception e) {
					log.error("推荐提成短信发送失败",e);
			}
		}
		return null;
		
	}
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void recommendReward(Integer custId) throws BizException {
		if(custId==null) throw new BizException("参数[custId]不能为空");
		BizAccountBO outAccountBO = new BizAccountBO();
		BizAccountBO inAccountBO = new BizAccountBO();
		BigDecimal reward = BigDecimal.ZERO;
		int count = custInfoBS.countReferee(custId);
		if(count <= 10){
			reward = new BigDecimal(paramBS.getParam(AppConst.REFEREE_PER));
		}else if(count > 10 && count <= 50){
			reward = new BigDecimal(paramBS.getParam(AppConst.REFEREE_TEN));
		}else if(count > 50 && count <= 100){
			reward = new BigDecimal(paramBS.getParam(AppConst.REFEREE_FIFTY));
		}else if(count > 100 && count <= 200){
			reward = new BigDecimal(paramBS.getParam(AppConst.REFEREE_HUNDRED));
		}else if(count > 200 && count <= 300){
			reward = new BigDecimal(paramBS.getParam(AppConst.REFEREE_TWO_HUNDRED));
		}
		else if(count > 300 && count <= 500){//300人
			reward = new BigDecimal(paramBS.getParam(AppConst.REFEREE_THR_HUNDRED));
		}
		else if(count > 500){//500人
			reward = new BigDecimal(paramBS.getParam(AppConst.REFEREE_FIVE_HUNDRED));
		}
		outAccountBO.setAccountTypeCd(AppConst.ACCOUNT_TYPE_PMSY);
		inAccountBO.setAccountTypeCd(AppConst.ACCOUNT_TYPE_CUST);
		inAccountBO.setCustId(custId);
		String txNO =  seqBS.getResultNum(AppConst.TXNO);
		transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_TJJL, outAccountBO, inAccountBO, reward);
		//短信提醒
		try {
			BizCustomerBO cust = custInfoBS.findCustById(custId);
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("amt", NumberUtils.format2(reward));
			dataMap.put("custName", cust.getCustName());
			msgTemplateBS.sendMsg(AppConst.EVENTTYPE_RECOMMEND, cust, dataMap);
		} catch (Exception e) {
			log.error("推荐奖励短信发送失败"+e.getMessage());
		}
		
	}
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void creatCreditorCount(BizCreditorRightBO bizCreditorRightBO)throws Exception{
		Integer custId = bizCreditorRightBO.getCustId();
		while(true){
			BizCustomerBO bizCustomerBO = custInfoBS.findCustById(custId);
			if(bizCustomerBO.getRefereeId() == null){//没有推荐人
				break;
			}
			custId = bizCustomerBO.getRefereeId();
			BizCreditorCountBO bizCreditorCountBO = new BizCreditorCountBO();
			bizCreditorCountBO.setCrAmt(bizCreditorRightBO.getCrAmt());
			bizCreditorCountBO.setCustId(custId);
			bizCreditorCountBO.setDate(bizCreditorRightBO.getSysCreateTime());
			bizCreditorCountBO.setLoanId(bizCreditorRightBO.getLoanId());
			createOperator(bizCreditorCountBO);
			baseDAO.save(bizCreditorCountBO);
		}
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

	public ITransferAccountsBS getTransferAccountsBS() {
		return transferAccountsBS;
	}

	public void setTransferAccountsBS(ITransferAccountsBS transferAccountsBS) {
		this.transferAccountsBS = transferAccountsBS;
	}

	public IParamBS getParamBS() {
		return paramBS;
	}

	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
	}
}
