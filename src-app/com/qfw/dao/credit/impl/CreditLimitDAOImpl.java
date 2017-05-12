package com.qfw.dao.credit.impl;

import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.dao.credit.ICreditLimitDAO;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.vo.credit.CreditQueryVO;

@Repository("creditLimitDAO")
public class CreditLimitDAOImpl extends BaseDAOImpl implements ICreditLimitDAO{

	@Override
	@SuppressWarnings("unchecked")
	public List<BizCreditLimitBO> queryListByParams(CreditQueryVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from BizCreditLimitBO limit where 1=1 ");
		if(null!=vo.getId()){
			hql.append(" and limit.id = '").append(vo.getId()).append("'");
		}
		if(StringUtils.isNotEmpty(vo.getRelId())){
			hql.append(" and limit.relId = '").append(vo.getRelId()).append("'");
		}
		if(StringUtils.isNotEmpty(vo.getRelTypeCd())){
			hql.append(" and limit.relTypeCd = '").append(vo.getRelTypeCd()).append("'");
		}
		if(StringUtils.isNotEmpty(vo.getState())){
			hql.append(" and limit.creditStateCd = '").append(vo.getState()).append("'");
		}
		
		return super.findByHQL(hql.toString());
	}

	
}
