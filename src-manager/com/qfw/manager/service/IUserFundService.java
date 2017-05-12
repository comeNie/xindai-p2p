package com.qfw.manager.service;

import java.util.List;
import java.util.Map;

import com.qfw.common.exception.BizException;
import com.qfw.model.vo.payout.WithdrawalsVO;
import com.qfw.platform.model.json.Pager;

public interface IUserFundService {
	
	/**
	 * 获取客户交易流水
	 * @param endTime 
	 * @param startTime 
	 * @param tradeType 
	 */
	public Pager findTradeList(String tradeType, String startTime, String endTime, int requestPage, int pageSize, String userId) throws BizException;
	
	/**
	 * 获取客户银行卡列表
	 */
	public Pager findBankCardList(int requestPage, int pageSize, Integer custId) throws BizException;
	
	/**
	 * 获取客户提现申请
	 */
	public Pager findWithdrawalsList(int requestPage, int pageSize, Integer custId) throws BizException;
	
	
	/**
	 * 提现时选择银行卡
	 */
	public List findBankCardList(Integer custId) throws BizException;
	
	/**
	 * 提交提现申请
	 */
	public void submitDrawal(WithdrawalsVO withdrawalsVO) throws BizException;
	
	public List<Map<String,Object>> getProvinceList();
	public List<Map<String,Object>> getCityListByProId(String proId);

}
