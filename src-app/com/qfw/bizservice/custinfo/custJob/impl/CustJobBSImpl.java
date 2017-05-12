package com.qfw.bizservice.custinfo.custJob.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.custinfo.custJob.ICustJobBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.dao.custinfo.custJob.ICustJobDAO;
import com.qfw.model.bo.BizJobBO;
import com.qfw.model.vo.custinfo.custJob.CustJobSearchVO;

@Service("custJobBS")
public class CustJobBSImpl extends BaseServiceImpl implements ICustJobBS {

	@Autowired
	private ICustJobDAO jobDAO;
	
	@Override
	public List<BizJobBO> findBOByParams(CustJobSearchVO searchVO) {
		return this.jobDAO.findBOByParams(searchVO);
	}
	
}
