package com.qfw.dao.credit.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.dao.credit.ICreditTxRecordDAO;
import com.qfw.model.bo.BizCreditTxRecordBO;

@Repository("creditTxRecordDAO")
public class CreditTxRecordDAOImpl extends BaseDAOImpl implements
		ICreditTxRecordDAO {
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BizCreditTxRecordBO> queryListByTxNo(String txNo,String state) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from BizCreditTxRecord tx where tx.txno = '").append(txNo).append("'");
		hql.append(" and tx.state = '").append(state).append("'");
		List<BizCreditTxRecordBO> result = super.findByHQL(hql.toString());
		return result;
	}

}
