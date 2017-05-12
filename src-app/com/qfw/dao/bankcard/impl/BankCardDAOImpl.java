package com.qfw.dao.bankcard.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.bankcard.IBankCardDAO;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.vo.bankcard.BankCardVO;

@Repository("bankCardDAO")
public class BankCardDAOImpl extends BaseDAOImpl implements IBankCardDAO {

	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public List<BizCardBO> findByParams(BankCardVO vo) {
		List<BizCardBO> list = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append(" from BizCardBO bo where 1=1");
			if(null != vo.getCustId() ){
				str.append(" and bo.custId = '").append(vo.getCustId()).append("'");
			}
			if(null != vo.getAccountNum() && vo.getAccountNum().length() >0 ){
				str.append(" and bo.accountNum = '").append(vo.getAccountNum()).append("'");
			}
			if(null != vo.getAccountBank() && vo.getAccountBank().length() >0 ){
				str.append(" and bo.accountBank = '").append(vo.getAccountBank()).append("'");
			}
			list =  super.findByHQL(str.toString());
		} catch (Exception e) {
			log.error("提现申请查询异常："+e);
		}
		return list;
	}

	@Override
	public boolean validate(String accountNum) {
		List<BizCardBO> list = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append(" from BizCardBO bo where 1=1");
				str.append(" and bo.accountNum = '").append(accountNum).append("'");
			list =  super.findByHQL(str.toString());
			if(list==null||list.size()==0){
				return true;
			}
		} catch (Exception e) {
			log.error("提现申请查询异常："+e);
		}
		return false;
	}

	@Override
	public void deleteById(Serializable id) {
		StringBuffer str = new StringBuffer();
		str.append("delete from BizCardBO bo where bo.id="+id);
		super.deleteById(str.toString());
	}

}
