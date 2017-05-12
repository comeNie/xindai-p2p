package com.qfw.bizservice.custinfo.account.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.custinfo.account.ICustAccountDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizAccountDetailBO;
import com.qfw.model.bo.BizTradeBO;

@Service("transferAccountsBS")
public class TransferAccountsBSImpl extends BaseServiceImpl implements ITransferAccountsBS {

	@Autowired
	@Qualifier("custAccountDAO")
	private ICustAccountDAO accountDAO;
	
	public static enum AccountDirectionEnum{  
	    ADD,SUBTRACT  
	}  

	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public synchronized void oneSidedTransferAccount(String txNO,String tradeType,AccountDirectionEnum direction,BizAccountBO accountBO,BigDecimal transferAmt) throws BizException{

		if(txNO==null||accountBO==null || transferAmt==null) 
			throw new BizException("参数[txNO][tradeType][accountBO][transferAmt]不能为空");
		if(transferAmt.compareTo(BigDecimal.ZERO)<0)
			throw new BizException("转账金额[transferAmt]不能为小于零");
		if(transferAmt.compareTo(BigDecimal.ZERO)==0)
			return;
		
		if(AppConst.ACCOUNT_TYPE_CUST.equals(accountBO.getAccountTypeCd())){
			accountBO = getAccountBO(accountBO.getCustId()) ;
		}else{
			accountBO = getPMAccountBO(accountBO.getAccountTypeCd());
		}
		
		BizTradeBO outTradeBO = new BizTradeBO();//记录交易流水
		switch (direction) {
		case ADD:{
			accountBO.setAccountBalAmt(accountBO.getAccountBalAmt().add(transferAmt));//更新账户余额
			accountBO.setUsableBalAmt(accountBO.getAccountBalAmt().add(accountBO.getFreezeBalAmt()));//更新可用余额
			outTradeBO.setTradeAmt(transferAmt);
		}
		break;
		case SUBTRACT:{
			if(accountBO.getAccountBalAmt().compareTo(transferAmt)<0 
					|| accountBO.getUsableBalAmt().compareTo(transferAmt)<0)
				throw new BizException("账户余额不足,发生金额=["+transferAmt+"]");
			accountBO.setAccountBalAmt(accountBO.getAccountBalAmt().subtract(transferAmt));//更新账户余额
			accountBO.setUsableBalAmt(accountBO.getAccountBalAmt().subtract(accountBO.getFreezeBalAmt()));//更新可用余额
			outTradeBO.setTradeAmt(transferAmt.negate());
		}
		break;
		default:throw new BizException("无效的扣款方向");
		}
		
		updateOperator(accountBO);
		accountDAO.update(accountBO);//更新账户表
		accountDAO.save(tranFromDetail(txNO, tradeType, accountBO, outTradeBO.getTradeAmt()));//新增账户变动表
		
		
		outTradeBO.setTradeNum(txNO);//交易编号
		outTradeBO.setTradaTime(ViewOper.getOperTime());
		outTradeBO.setTradeTypeCd(tradeType);
		outTradeBO.setAccountNum(accountBO.getAccount());
		outTradeBO.setAccountBal(accountBO.getAccountBalAmt());
		outTradeBO.setComment("");
		createOperator(outTradeBO);
		updateOperator(outTradeBO);
		accountDAO.save(outTradeBO);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public synchronized void transferAccount(String txNO,String tradeType,Integer outCustId ,Integer inCustId,BigDecimal transferAmt) throws BizException {
		if(txNO==null||outCustId==null || inCustId == null || transferAmt==null) 
			throw new BizException("参数[txNO][tradeType][outCustId][inCustId][transferAmt]不能为空");
		if(transferAmt.compareTo(BigDecimal.ZERO)<=0)
			throw new BizException("转账金额[transferAmt]不能为小于零");
		
		//开始处理转出金额
		BizAccountBO outAccountBO = getAccountBO(outCustId) ;
		if(outAccountBO.getAccountBalAmt().compareTo(transferAmt)<0 
				|| outAccountBO.getUsableBalAmt().compareTo(transferAmt)<0)
			throw new BizException("账户余额不足,发生金额=["+transferAmt+"]");
		outAccountBO.setAccountBalAmt(outAccountBO.getAccountBalAmt().subtract(transferAmt));//更新账户余额
		outAccountBO.setUsableBalAmt(outAccountBO.getAccountBalAmt().subtract(outAccountBO.getFreezeBalAmt()));//更新可用余额
		updateOperator(outAccountBO);
		accountDAO.update(outAccountBO);//更新账户表
		accountDAO.save(tranFromDetail(txNO, tradeType, outAccountBO, transferAmt.negate()));//新增账户变动表
		
		//记录交易流水
		BizTradeBO outTradeBO = new BizTradeBO();
		outTradeBO.setTradeNum(txNO);//交易编号
		outTradeBO.setTradaTime(ViewOper.getOperTime());;
		outTradeBO.setTradeAmt(transferAmt.negate());
		outTradeBO.setTradeTypeCd(tradeType);
		outTradeBO.setAccountNum(outAccountBO.getAccount());
		outTradeBO.setAccountBal(outAccountBO.getAccountBalAmt());
		outTradeBO.setComment("");
		createOperator(outTradeBO);
		updateOperator(outTradeBO);
		accountDAO.save(outTradeBO);
		
		//开始处理转入金额
		BizAccountBO inAccountBO = getAccountBO(inCustId) ;
		inAccountBO.setAccountBalAmt(inAccountBO.getAccountBalAmt().add(transferAmt));
		inAccountBO.setUsableBalAmt(inAccountBO.getAccountBalAmt().subtract(inAccountBO.getFreezeBalAmt()));//更新可用余额
		updateOperator(inAccountBO);
		accountDAO.update(inAccountBO);//更新账户表
		accountDAO.save(tranFromDetail(txNO, tradeType, inAccountBO, transferAmt));//新增账户变动表
		
		//记录交易流水
		BizTradeBO inTradeBO = new BizTradeBO();
		inTradeBO.setTradeNum(txNO);
		inTradeBO.setTradaTime(ViewOper.getOperTime());;
		inTradeBO.setTradeAmt(transferAmt);
		inTradeBO.setTradeTypeCd(tradeType);
		inTradeBO.setAccountNum(inAccountBO.getAccount());
		inTradeBO.setAccountBal(inAccountBO.getAccountBalAmt());
		inTradeBO.setComment("");
		createOperator(inTradeBO);
		updateOperator(inTradeBO);
		accountDAO.save(inTradeBO);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public synchronized void transferAccount(String txNO, String tradeType,BizAccountBO outAccountBO, BizAccountBO inAccountBO,BigDecimal transferAmt) throws BizException {
		if(txNO==null||outAccountBO==null || inAccountBO == null || transferAmt==null) 
			throw new BizException("参数[txNO][tradeType][outAccountBO][inAccountBO][transferAmt]不能为空");
		if(transferAmt.compareTo(BigDecimal.ZERO)<0)
			throw new BizException("转账金额[transferAmt]不能为小于零");
		if(transferAmt.compareTo(BigDecimal.ZERO)==0)
			return;
		//开始处理转出金额
		if(AppConst.ACCOUNT_TYPE_CUST.equals(outAccountBO.getAccountTypeCd())){
			outAccountBO = getAccountBO(outAccountBO.getCustId()) ;
			if(AppConst.TRADE_TYPE_WITHDRAW.equals(tradeType)){
				if(outAccountBO.getUsableBalAmt().subtract(outAccountBO.getRechargeAmt())
						.compareTo(transferAmt) < 0){
					outAccountBO.setRechargeAmt(
							outAccountBO.getUsableBalAmt().subtract(transferAmt));
					if(outAccountBO.getRechargeAmt().compareTo(BigDecimal.ZERO) < 0){
						outAccountBO.setRechargeAmt(BigDecimal.ZERO);
					}
				}
			}else{
				outAccountBO.setRechargeAmt(outAccountBO.getRechargeAmt().subtract(transferAmt));
				if(outAccountBO.getRechargeAmt().compareTo(BigDecimal.ZERO) < 0){
					outAccountBO.setRechargeAmt(BigDecimal.ZERO);
				}
			}
			
		}else{
			outAccountBO = getPMAccountBO(outAccountBO.getAccountTypeCd());
		}
		
		//System.out.println("outAccountBO.getAccountBalAmt()=="+outAccountBO.getAccountBalAmt());
		
		if(outAccountBO.getAccountBalAmt().compareTo(transferAmt)<0 
				|| outAccountBO.getUsableBalAmt().compareTo(transferAmt)<0)
			throw new BizException("账户余额不足,发生金额=["+transferAmt+"]");
		outAccountBO.setAccountBalAmt(outAccountBO.getAccountBalAmt().subtract(transferAmt));//更新账户余额
		outAccountBO.setUsableBalAmt(outAccountBO.getAccountBalAmt().subtract(outAccountBO.getFreezeBalAmt()));//更新可用余额
		updateOperator(outAccountBO);
		accountDAO.update(outAccountBO);//更新账户表
		accountDAO.save(tranFromDetail(txNO, tradeType, outAccountBO, transferAmt.negate()));//新增账户变动表
		
		//记录交易流水
		BizTradeBO outTradeBO = new BizTradeBO();
		outTradeBO.setTradeNum(txNO);//交易编号
		outTradeBO.setTradaTime(ViewOper.getOperTime());;
		outTradeBO.setTradeAmt(transferAmt.negate());
		outTradeBO.setTradeTypeCd(tradeType);
		outTradeBO.setAccountNum(outAccountBO.getAccount());
		outTradeBO.setAccountBal(outAccountBO.getAccountBalAmt());
		outTradeBO.setComment("");
		createOperator(outTradeBO);
		updateOperator(outTradeBO);
		accountDAO.save(outTradeBO);
		
		//开始处理转入金额
		if(AppConst.ACCOUNT_TYPE_CUST.equals(inAccountBO.getAccountTypeCd())){
			inAccountBO = getAccountBO(inAccountBO.getCustId()) ;
			//add by yangjj 充值账户金额 start
			if(AppConst.TRADE_TYPE_RECHARGE_BANK.equals(tradeType)){
				inAccountBO.setRechargeAmt(inAccountBO.getRechargeAmt().add(transferAmt));
			}
			//add by yangjj 充值账户金额 end
		}else{
			inAccountBO = getPMAccountBO(inAccountBO.getAccountTypeCd());
		}
		inAccountBO.setAccountBalAmt(inAccountBO.getAccountBalAmt().add(transferAmt));
		inAccountBO.setUsableBalAmt(inAccountBO.getAccountBalAmt().subtract(inAccountBO.getFreezeBalAmt()));//更新可用余额
		
		updateOperator(inAccountBO);
		accountDAO.update(inAccountBO);//更新账户表
		accountDAO.save(tranFromDetail(txNO, tradeType, inAccountBO, transferAmt));//新增账户变动表
		
		//记录交易流水
		BizTradeBO inTradeBO = new BizTradeBO();
		inTradeBO.setTradeNum(txNO);
		inTradeBO.setTradaTime(ViewOper.getOperTime());;
		inTradeBO.setTradeAmt(transferAmt);
		inTradeBO.setTradeTypeCd(tradeType);
		inTradeBO.setAccountNum(inAccountBO.getAccount());
		inTradeBO.setAccountBal(inAccountBO.getAccountBalAmt());
		inTradeBO.setComment("");
		createOperator(inTradeBO);
		updateOperator(inTradeBO);
		accountDAO.save(inTradeBO);
		
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public BizAccountBO getAccountBO(Integer custId)throws BizException{
		if(custId == null)
			throw new BizException("参数[CustId]不能为空");
		List<?> bizAccountBOs = accountDAO.findByHQL("FROM BizAccountBO WHERE custId = ?", new Object[]{custId});
		if(CollectionUtil.isEmpty(bizAccountBOs))
			throw new BizException("查询不到该会员账户,[CustId]="+custId);
		if(bizAccountBOs.size()>1)
			throw new BizException("该会员存在多个账户,[CustId]="+custId);
		return (BizAccountBO)bizAccountBOs.get(0);
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public BizAccountBO getPMAccountBO(String pmAccountType) throws BizException{
		if(pmAccountType==null)
			throw new BizException("参数[pmAccountType]不能为空");
		if(AppConst.ACCOUNT_TYPE_CUST.equals(pmAccountType))
			throw new BizException("该方法不支持查询会员账户,请调用服务[getAccountBO]");
		List<?> accounts = accountDAO.findByHQL("From BizAccountBO Where accountTypeCd = ?",new Object[]{pmAccountType});
		if(CollectionUtil.isEmpty(accounts))
			throw new BizException("查询不到该[pmAccountType]="+pmAccountType+"类型的账户");
		if(accounts.size()>1)
			throw new BizException("该账户类型存在多个账户,[pmAccountType]="+pmAccountType);
		BizAccountBO bizAccountBO = (BizAccountBO)accounts.get(0);
		return bizAccountBO;
	}
	
	/**
	 * 封装账户交易明细信息
	 * @param accountBO
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	private BizAccountDetailBO tranFromDetail(String txNO,String tradeType,BizAccountBO accountBO,BigDecimal transferAmt){
		BizAccountDetailBO detailBO = new BizAccountDetailBO();
		detailBO.setAccount(accountBO.getAccount());
		detailBO.setAccountBalAmt(accountBO.getAccountBalAmt());
		detailBO.setCustId(accountBO.getCustId());
		detailBO.setFreezeBalAmt(accountBO.getFreezeBalAmt());
		detailBO.setPmAmt(accountBO.getPmAmt());
		detailBO.setTxAmt(transferAmt);
		detailBO.setTxDate(new Date());
		detailBO.setTxNo(txNO);
		detailBO.setEventTypeCd(tradeType);
		detailBO.setSysCreateTime(accountBO.getSysCreateTime());
		detailBO.setSysCreateUser(accountBO.getSysCreateUser());
		detailBO.setSysUpdateTime(accountBO.getSysUpdateTime());
		detailBO.setSysUpdateUser(accountBO.getSysUpdateUser());
		return detailBO;
	}

	
}
