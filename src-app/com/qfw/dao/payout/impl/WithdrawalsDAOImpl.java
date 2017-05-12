package com.qfw.dao.payout.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.payout.IWithdrawalsDAO;
import com.qfw.model.bo.BizWithdrawalApplyBO;
import com.qfw.model.bo.BizWithdrawalsBO;
import com.qfw.model.vo.payout.WithdrawalsVO;

@Repository("withdrawalsDAO")
public class WithdrawalsDAOImpl extends BaseDAOImpl implements IWithdrawalsDAO {

	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public List<BizWithdrawalsBO> findByParams(WithdrawalsVO vo) {
		List<BizWithdrawalsBO> list = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append(" from BizWithdrawalsBO bo where 1=1");
			if(null != vo.getWorkItemId() && vo.getWorkItemId().length() >0 ){
				str.append(" and bo.workItemId = '").append(vo.getWorkItemId()).append("'");
			}
			if(null != vo.getStatus() && vo.getStatus().length() >0 ){
				str.append(" and bo.ctrStateCd = '").append(vo.getStatus()).append("'");
			}
			if(null != vo.getTradeNum() && vo.getTradeNum().length() >0 ){
				str.append(" and bo.tradeNum = '").append(vo.getTradeNum()).append("'");
			}
			list =  super.findByHQL(str.toString());
		} catch (Exception e) {
			log.error("提现信息查询异常："+e);
		}
		return list;
	}
	
	public List<BizWithdrawalApplyBO> findApplyByParams(WithdrawalsVO vo) {
		List<BizWithdrawalApplyBO> list = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append(" from BizWithdrawalApplyBO bo where 1=1");
			if(null != vo.getStatus() && vo.getStatus().length() >0 ){
				str.append(" and bo.status = '").append(vo.getStatus()).append("'");
			}
			if(null != vo.getTradeNum() && vo.getTradeNum().length() >0 ){
				str.append(" and bo.reqSn = '").append(vo.getTradeNum()).append("'");
			}
			if(null != vo.getTradeDate() && vo.getTradeDate().length() >0 ){
				str.append(" and bo.txDate = '").append(vo.getTradeDate()).append("'");
			}
			if(null != vo.getCustId()){
				str.append(" and bo.custId = '").append(vo.getCustId()).append("'");
			}
			list =  super.findByHQL(str.toString());
		} catch (Exception e) {
			log.error("提现申请查询异常："+e);
		}
		return list;
	}

}
