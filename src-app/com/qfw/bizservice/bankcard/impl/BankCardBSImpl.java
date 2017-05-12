package com.qfw.bizservice.bankcard.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.bankcard.IBankCardBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.bankcard.IBankCardDAO;
import com.qfw.model.bo.BizCardBO;
import com.qfw.model.vo.bankcard.BankCardVO;

/**
 * 银行卡bs
 *
 * @author kyc
 */
@Service("bankCardBS")
public class BankCardBSImpl extends BaseServiceImpl implements IBankCardBS {
	private Logger log = Logger.getLogger(BankCardBSImpl.class);
	@Autowired
	private IBankCardDAO cardDAO;
	
	@Override
	public List<BizCardBO> findByParams(BankCardVO vo) {
		return cardDAO.findByParams(vo);
	}
	
	public void deleteCard(Integer cardId) throws BizException {
		//BizCardBO cardBO = (BizCardBO)this.cardDAO.findById(BizCardBO.class, cardId);
		this.cardDAO.deleteById(cardId);
	}

	@Override
	public boolean validate(String accountNum) {
		return cardDAO.validate(accountNum);
	}

}
