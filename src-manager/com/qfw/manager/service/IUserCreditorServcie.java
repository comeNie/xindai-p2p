package com.qfw.manager.service;

import java.math.BigDecimal;
import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.manager.model.MyCreditorTranVO;
import com.qfw.platform.model.json.Pager;

public interface IUserCreditorServcie {

	/**
	 * 回收中的债权[债权状态为 2-还款中；3-逾期中]
	 */
	public Pager findUserCreditorRecovering(int requestPage, int pageSize, Integer custId) throws BizException;
	
	/**
	 * 已结清的债权[债权状态为 4-已结清；5-撤标]
	 */
	public Pager findUserCreditorRecovered(int requestPage, int pageSize, Integer custId) throws BizException;
	
	/**
	 * 投标中的债权[债权状态为 1-投标中]
	 */
	public Pager findUserCreditorTendering(int requestPage, int pageSize, Integer custId) throws BizException;
	
	/**
	 * 债权转让列表
	 */
	public Pager findUserCreditorTran(int requestPage, int pageSize, Integer custId) throws BizException;
	
	/**
	 * 获取债权信息用于进行债权转让
	 */
	public MyCreditorTranVO getMyCreditorTranVO(Integer creditorId) throws BizException;
	
	/**
	 * 获取某笔债权是否有债权转让申请
	 */
	public Pager findObjects(Integer creditorId);
	
	public int countCreditor(Integer custId) throws BizException;
	
	public BigDecimal calculateMyCreditor(Integer custId)throws BizException;
	
}
