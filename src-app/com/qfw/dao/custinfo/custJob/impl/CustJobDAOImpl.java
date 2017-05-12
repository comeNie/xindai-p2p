package com.qfw.dao.custinfo.custJob.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.dao.custinfo.custJob.ICustJobDAO;
import com.qfw.model.bo.BizJobBO;
import com.qfw.model.vo.custinfo.custJob.CustJobSearchVO;

@Repository("custJobDAO")
public class CustJobDAOImpl extends BaseDAOImpl implements ICustJobDAO {

	@Override
	public List<BizJobBO> findBOByParams(CustJobSearchVO searchVO) {
		StringBuffer str = new StringBuffer();
		str.append(" from BizJobBO job where 1=1");
		if(null != searchVO.getCustId() ){
			str.append(" and job.custId = '").append(searchVO.getCustId()).append("'");
		}
		return this.findByHQL(str.toString());
	}
	
}
