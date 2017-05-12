package com.qfw.dao.credit.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.dao.credit.ICreditUseDAO;
import com.qfw.model.bo.BizCreditUseBO;

@Repository("creidtUseDAO")
public class CreidtUseDAOImpl extends BaseDAOImpl implements ICreditUseDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<BizCreditUseBO> findByClId(String clId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from BizCreditUseBO u where u.clId = '").append(clId).append("'");
		List<BizCreditUseBO> result = super.findByHQL(hql.toString());
		return result;
	}

}
