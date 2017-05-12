package com.qfw.bizservice.custinfo.account;

import java.math.BigDecimal;

import com.qfw.bizservice.custinfo.account.impl.TransferAccountsBSImpl.AccountDirectionEnum;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizAccountBO;

/**
 * 账户-转账服务
 * @author kindion
 *
 */
public interface ITransferAccountsBS {
	
	/**
	 * 转账服务(适用于会员之间转账)
	 * @param txNO 交易编号
	 * @param tradeType 交易类型
	 * @param outCustId 转出会员ID
	 * @param inCustId 转入会员ID
	 * @param transferAmt 转账金额
	 * @throws BizException
	 */
	public void transferAccount(String txNO,String tradeType,Integer outCustId ,Integer inCustId,BigDecimal transferAmt) throws BizException;
	
	
	/**
	 * 转账服务(适用于会员与平台之间转账)
	 * @param txNO 交易编号
	 * @param tradeType 交易类型
	 * @param outAccountBO 转出会员账户
	 * @param inAccountBO  转入会员账户
	 * @param transferAmt 转账金额
	 * @throws BizException
	 */
	public void transferAccount(String txNO,String tradeType,BizAccountBO outAccountBO ,BizAccountBO inAccountBO,BigDecimal transferAmt) throws BizException;
	
	/**
	 * 转账服务(适用于单边转账操作)
	 * @param txNO 交易编号
	 * @param tradeType 交易类型
	 * @param direction 枚举类型:AccountDirectionEnum:ADD 余额增加、SUBTRACT 余额减少
	 * @param accountBO 帐号
	 * @param transferAmt 转账金额
	 * @throws BizException
	 */
	public void oneSidedTransferAccount(String txNO,String tradeType,AccountDirectionEnum direction,BizAccountBO accountBO,BigDecimal transferAmt) throws BizException;
	
	/**
	 * 通过会员ID获取会员账户
	 * @param custId
	 * @return
	 * @throws BizException
	 */
	public BizAccountBO getAccountBO(Integer custId)throws BizException;
	
	/**
	 * 获取平台账户
	 * @param pmAccountType 账户类型
	 * @return
	 * @throws BizException
	 */
	public BizAccountBO getPMAccountBO(String pmAccountType) throws BizException;
}
