package com.qfw.dao.payout.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.payout.IRechargePayoutDAO;
import com.qfw.model.bo.BizRechargeCardBO;
import com.qfw.model.vo.payout.RechargeVO;

@Repository("rechargePayoutDAO")
public class RechargePayoutDAOImpl  extends BaseDAOImpl implements IRechargePayoutDAO{

	@Override
	public List<BizRechargeCardBO> queryBOByParams(RechargeVO vo,List<String> statusList) throws BizException {
		List<BizRechargeCardBO> list = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append(" from BizRechargeCardBO bo where 1=1");
			str.append(" and bo.cardCd = '").append(vo.getCardCD()).append("'");
			str.append(" and bo.cardPwd = '").append(vo.getPassword()).append("'");
			if(null!=statusList&&statusList.size()>0){
				String temp = new String();
				for (String status : statusList) {
					temp = temp+status+",";
				}
				str.append(" and bo.status in ").append("("+temp.substring(0,temp.length()-1)+")").append("");
			}
			if(null != vo.getTxDate()){
				str.append(" and bo.startDate <=").append(vo.getTxDate());
				str.append(" and bo.endDate >= ").append(vo.getTxDate());
			}
			list =  super.findByHQL(str.toString());
		} catch (Exception e) {
			throw new BizException(e);
		}
		return list;
	}

}
