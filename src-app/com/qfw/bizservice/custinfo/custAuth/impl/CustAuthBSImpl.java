package com.qfw.bizservice.custinfo.custAuth.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.custinfo.custAuth.ICustAuthBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.custinfo.custAuth.ICustAuthDAO;
import com.qfw.model.bo.BizAuthBO;
import com.qfw.model.vo.custinfo.custAuth.CustAuthSearchVO;

@Service("custAuthBS")
public class CustAuthBSImpl extends BaseServiceImpl implements ICustAuthBS {

	@Autowired
	private ICustAuthDAO authDAO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public BizAuthBO findBOByCustID(Integer custId) {
		BizAuthBO bo = new BizAuthBO();
		try {
			CustAuthSearchVO searchVO = new CustAuthSearchVO();
			searchVO.setCustId(custId);
			List<BizAuthBO> boList = this.findBOByParams(searchVO);
			if(null!=boList && boList.size() == 1){
				bo = boList.get(0);
			}
		} catch (Exception e) {
			log.error("客户工作信息获取失败：",e);
		}
		return bo;
	}

	@Override
	public List<BizAuthBO> findBOByParams(CustAuthSearchVO searchVO) {
		return this.authDAO.findBOByParams(searchVO);
	}
	
}
