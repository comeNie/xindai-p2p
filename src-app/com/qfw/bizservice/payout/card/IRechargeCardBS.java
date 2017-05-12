package com.qfw.bizservice.payout.card;


import java.util.List;

import com.qfw.common.bizservice.IBaseService;

/**
 * 充值卡bs
 *
 * @author kyc
 */
public interface IRechargeCardBS extends IBaseService{
	
	public static final int pass_length = 8;
	public static final int card_length = 17;
	
	/**
	 * 获取随机密码
	 * @return
	 */
	public String getPassword(int length);
	
	/**
	 * 获取卡号
	 * @param length
	 * @return
	 */
	public List<String> getCardNums(int count);
	/**
	 * 生成批次号
	 * @return
	 */
	public String getBatchCardNum();
}
