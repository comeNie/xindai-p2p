package com.qfw.dao.credit.report.impl;

import java.util.List;





import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.dao.credit.ICreditLimitDAO;
import com.qfw.dao.credit.report.ICreditReportDAO;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.bo.BizCreditReportBO;
import com.qfw.model.vo.credit.CreditQueryVO;

@Repository("creditReportDAO")
public class CreditReportDAOImpl extends BaseDAOImpl implements ICreditReportDAO{

	@Override
	@SuppressWarnings("unchecked")
	public List<BizCreditReportBO> queryListByParams(String custId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from BizCreditReportBO bo where 1=1 ");
		if(StringUtils.isNotEmpty(custId)){
			hql.append(" and bo.custId = '").append(custId).append("'");
		}
		return super.findByHQL(hql.toString());
	}

	
}
