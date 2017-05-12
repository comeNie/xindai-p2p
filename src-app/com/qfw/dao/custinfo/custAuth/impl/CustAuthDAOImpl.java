package com.qfw.dao.custinfo.custAuth.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.dao.custinfo.custAuth.ICustAuthDAO;
import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.vo.custinfo.custAuth.CustAuthSearchVO;

@Repository("custAuthDAO")
public class CustAuthDAOImpl extends BaseDAOImpl implements ICustAuthDAO {

	@Override
	public List<BizAuthBO> findBOByParams(CustAuthSearchVO searchVO) {
		StringBuffer str = new StringBuffer();
		str.append(" from BizAuthBO job where 1=1");
		if(null != searchVO.getCustId() ){
			str.append(" and job.custId = '").append(searchVO.getCustId()).append("'");
		}
		return this.findByHQL(str.toString());
	}
	
}
