package com.qfw.bizservice.charge.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.charge.IChargeBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCostBO;
import com.qfw.model.vo.charge.ChargeVO;

@Service("chargeBS")
public class ChargeBSImpl extends BaseServiceImpl implements IChargeBS {
	@Autowired
	private IParamBS paramBS;
	@Autowired
	public ITransferAccountsBS transferAccountsBS; 
	@Autowired
	public ISeqBS seqBS;
	
	@Override
	public BizCostBO genCharge(ChargeVO chargeVO)throws BizException {
		if(chargeVO == null)
			throw new BizException("参数[chargeVO]不能为空");
		if(chargeVO.getCostBasicAmt().compareTo(BigDecimal.ZERO)<=0)
			return null;
		String costTypeCd = chargeVO.getCostTypeCd();
		BigDecimal costRate = BigDecimal.ZERO;
		BizCostBO bizCost = new BizCostBO();
		bizCost.setCustId(chargeVO.getCustId());
		bizCost.setRelateId(chargeVO.getRelateId());
		bizCost.setRelateTypeCd(chargeVO.getRelateTypeCd());
		bizCost.setCurrency(AppConst.CURRENCY_CD_CNY);
		bizCost.setExchangeRate(BigDecimal.ONE);
		bizCost.setCostTypeCd(costTypeCd);
		bizCost.setCostBasicAmt(chargeVO.getCostBasicAmt());
		if(AppConst.COST_TYPE_CD_TXFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_TXGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[提现管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_CZFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_CZGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[充值管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_JKFY.equals(costTypeCd)){
			costRate = getCostRate(chargeVO.getLoanTerm());
		}else if(AppConst.COST_TYPE_CD_ZQFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_ZQGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[展期管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_YQFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_YQGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[逾期管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_ZQZRFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_ZQZRGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[债权转让管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_TQHKWYJ.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
			if(param==null || "".equals(param))
				throw new BizException("平台[提前还款违约金比例]未设置");
			costRate = new BigDecimal(param);
		}else{
			throw new BizException("未知参数[costTypeCd]");
		}
		bizCost.setCostRate(costRate);
		bizCost.setCostAmt((bizCost.getCostBasicAmt().multiply(bizCost.getCostRate())).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP));
		bizCost.setCostHappenDate(ViewOper.getOperTime());
		bizCost.setCostGatherDate(null);
		bizCost.setCostStatusCd(AppConst.COST_STATUS_CD_WKF);
		createOperator(bizCost);
		updateOperator(bizCost);
		this.baseDAO.save(bizCost);
		return bizCost;
	}

	@Override
	public BigDecimal deductCharge(String txNO,BizCostBO bizCost) throws BizException {
		if(bizCost==null)
			return BigDecimal.ZERO;
		BizCostBO bizCostBO = (BizCostBO) this.baseDAO.findById(BizCostBO.class, bizCost.getId());
		if(bizCostBO==null)
			throw new BizException("扣收的费用不存在");
		if(bizCostBO.getCostAmt().compareTo(BigDecimal.ZERO)<=0)
			return BigDecimal.ZERO;
		//获取交易编号
		if(txNO == null){
			txNO =  seqBS.getResultNum(AppConst.TXNO);
		}
		String costTypeCd = bizCost.getCostTypeCd();
		String tradeType = null;
		if(AppConst.COST_TYPE_CD_TXFY.equals(costTypeCd)){
			tradeType=AppConst.TRADE_TYPE_TXGLF;
		}else if(AppConst.COST_TYPE_CD_CZFY.equals(costTypeCd)){
			tradeType=AppConst.TRADE_TYPE_CZGLF;
		}else if(AppConst.COST_TYPE_CD_JKFY.equals(costTypeCd)){
			tradeType=AppConst.TRADE_TYPE_JKGLF;
		}else if(AppConst.COST_TYPE_CD_ZQFY.equals(costTypeCd)){
			tradeType=AppConst.TRADE_TYPE_ZQGLF;
		}else if(AppConst.COST_TYPE_CD_YQFY.equals(costTypeCd)){
			tradeType=AppConst.TRADE_TYPE_YQGLF;
		}else if(AppConst.COST_TYPE_CD_ZQZRFY.equals(costTypeCd)){
			tradeType=AppConst.TRADE_TYPE_ZQZRGLF;
		}else if(AppConst.COST_TYPE_CD_TQHKWYJ.equals(costTypeCd)){
			tradeType=AppConst.TRADE_TYPE_TQHKWYJ;
		}else{
			throw new BizException("未知参数[costTypeCd]");
		}
		bizCost.setCostStatusCd(AppConst.COST_STATUS_CD_YKF);//已扣费
		bizCost.setCostGatherDate(ViewOper.getOperTime());
		updateOperator(bizCostBO);
		this.baseDAO.update(bizCost);
		//调用账户管理(转账服务) 【会员账户】费用金额转入【平台收益账户】
		transferAccountsBS.transferAccount(txNO, tradeType,
				transferAccountsBS.getAccountBO(bizCostBO.getCustId()),
				transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMSY),bizCostBO.getCostAmt());
		return bizCostBO.getCostAmt();
	}
	
	@Override
	public BizCostBO trialCharge(ChargeVO chargeVO)throws BizException {
		if(chargeVO == null)
			throw new BizException("参数[chargeVO]不能为空");
		if(chargeVO.getCostBasicAmt().compareTo(BigDecimal.ZERO)<=0)
			return new BizCostBO();
		String costTypeCd = chargeVO.getCostTypeCd();
		BigDecimal costRate = BigDecimal.ZERO;
		BizCostBO bizCost = new BizCostBO();
		bizCost.setCustId(chargeVO.getCustId());
		bizCost.setRelateId(chargeVO.getRelateId());
		bizCost.setRelateTypeCd(chargeVO.getRelateTypeCd());
		bizCost.setCurrency(AppConst.CURRENCY_CD_CNY);
		bizCost.setExchangeRate(BigDecimal.ONE);
		bizCost.setCostTypeCd(costTypeCd);
		bizCost.setCostBasicAmt(chargeVO.getCostBasicAmt());
		if(AppConst.COST_TYPE_CD_TXFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_TXGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[提现管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_CZFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_CZGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[充值管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_JKFY.equals(costTypeCd)){
			costRate = getCostRate(chargeVO.getLoanTerm());
		}else if(AppConst.COST_TYPE_CD_ZQFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_ZQGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[展期管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_YQFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_YQGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[逾期管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_ZQZRFY.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_ZQZRGLFL);
			if(param==null || "".equals(param))
				throw new BizException("平台[债权转让管理费率]未设置");
			costRate = new BigDecimal(param);
		}else if(AppConst.COST_TYPE_CD_TQHKWYJ.equals(costTypeCd)){
			String param =  paramBS.getParam(AppConst.PARAMETER_CODE_TQHKWYJBL);
			if(param==null || "".equals(param))
				throw new BizException("平台[提前还款违约金比例]未设置");
			costRate = new BigDecimal(param);
		}else{
			throw new BizException("未知参数[costTypeCd]");
		}
		bizCost.setCostRate(costRate);
		bizCost.setCostAmt((bizCost.getCostBasicAmt().multiply(bizCost.getCostRate())).setScale(AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP));
		bizCost.setCostHappenDate(ViewOper.getOperTime());
		bizCost.setCostGatherDate(null);
		bizCost.setCostStatusCd(AppConst.COST_STATUS_CD_WKF);
		createOperator(bizCost);
		updateOperator(bizCost);
		return bizCost;
	}

	@Override
	public BigDecimal genAndDeductCharge(ChargeVO chargeVO) throws BizException {
		return deductCharge(chargeVO.getTxNO(),genCharge(chargeVO));
	}
	
	private BigDecimal getCostRate(int loanTerm)throws BizException{
		String param = "";
		if(loanTerm > 12){
			param = paramBS.getParam(AppConst.PARAMETER_CODE_YNBFL);
		}else{
			param = paramBS.getParam(AppConst.PARAMETER_CODE_JKGLFL);
		}
		if(param==null || "".equals(param))
			throw new BizException("平台[借款管理费率]未设置");
		BigDecimal costRate = new BigDecimal(param);
		return costRate;
	}


}
