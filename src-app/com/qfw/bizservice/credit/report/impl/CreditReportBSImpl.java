package com.qfw.bizservice.credit.report.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.credit.report.ICreditReportBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.credit.report.ICreditReportDAO;
import com.qfw.model.bo.BizCreditReportBO;

@Service("creditReportBS")
public class CreditReportBSImpl extends BaseServiceImpl implements ICreditReportBS {

	@Autowired
	private ICreditReportDAO creditReportDAO;
	
	@Override
	public BizCreditReportBO findByCustInfo(String custId) {
		List<BizCreditReportBO> boList = this.creditReportDAO.queryListByParams(custId);
		if(null!=boList&&boList.size()==1){
			return boList.get(0);
		}
		BizCreditReportBO bo = new BizCreditReportBO();
		bo.setCustId(Integer.valueOf(custId));
		bo.setApplyLoanNum(0);
		bo.setApproveNum(0);
		bo.setCreditAmt(BigDecimal.ZERO);
		bo.setLoanTolAmt(BigDecimal.ZERO);
		bo.setLoanBal(BigDecimal.ZERO);
		bo.setOverdueAmt(BigDecimal.ZERO);
		bo.setOverdueNum(0);
		bo.setPayOffNum(0);
		bo.setPrincipaInterestAmt(BigDecimal.ZERO);
		bo.setRemainAmt(BigDecimal.ZERO);
		bo.setSerOverdueNum(0);
		return bo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateCreditAmt(String custId, BigDecimal creditAmt,
			BigDecimal remainAmt) throws BizException {
		BizCreditReportBO bo = findByCustInfo(custId);
		int num = 0;
		if(null != creditAmt){
			bo.setCreditAmt(creditAmt);
			num ++;
		}
		if(null != remainAmt && remainAmt.compareTo(BigDecimal.ZERO)!=0){
			bo.setRemainAmt(bo.getRemainAmt().add(remainAmt));
			num++;
		}
		if(num>0){
			if(null == bo.getId()){
				creditReportDAO.save(bo);
			}else{
				creditReportDAO.update(bo);
			}
		}
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateTol(String custId, Integer payOffNum) throws BizException {
		BizCreditReportBO bo = findByCustInfo(custId);
		if(null != payOffNum && payOffNum>0){
			bo.setPayOffNum(bo.getPayOffNum()+payOffNum);
			creditReportDAO.update(bo);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateApplyNum(String custId, Integer applyNum,
			Integer success, BigDecimal applyAmt) throws BizException  {
		BizCreditReportBO bo = findByCustInfo(custId);
		int num = 0;
		if(null != applyNum && applyNum>0){
			bo.setApplyLoanNum(bo.getApplyLoanNum()+applyNum);
			if(null != applyAmt && applyAmt.compareTo(BigDecimal.ZERO)!=0){
				BigDecimal remain = bo.getRemainAmt();
				bo.setRemainAmt(remain.subtract(applyAmt));
				num++;
			}
			num++;
		}
		if(null != success && success>0){
			bo.setApproveNum(bo.getApproveNum()+success);
			if(null != applyAmt && applyAmt.compareTo(BigDecimal.ZERO)!=0){
				bo.setLoanTolAmt(bo.getLoanTolAmt().add(applyAmt));
				bo.setLoanBal(bo.getLoanBal().add(applyAmt));
			}
			num++;
		}
		
		if(num>0){
			if(null == bo.getId()){
				creditReportDAO.save(bo);
			}else{
				creditReportDAO.update(bo);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updateApplyAmtForRev(String custId,BigDecimal reAmt) throws BizException{

		BizCreditReportBO bo = findByCustInfo(custId);
		int num = 0;
		if(null != reAmt && reAmt.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal remain = bo.getRemainAmt();
			bo.setRemainAmt(remain.add(reAmt));
			bo.setLoanBal(bo.getLoanBal().subtract(reAmt));
			num++;
		}
		if(num>0){
			if(null == bo.getId()){
				creditReportDAO.save(bo);
			}else{
				creditReportDAO.update(bo);
			}
		}
	}

	@Override
	public void addOverdueAmt(String custId, BigDecimal amt)
			throws BizException {
		if(amt == null){
			throw new BizException("金额不能为空");
		}
		BizCreditReportBO bo = findByCustInfo(custId);
		bo.setOverdueAmt(bo.getOverdueAmt().add(amt));
		creditReportDAO.update(bo);
	}

	@Override
	public void subtractOverdueAmt(String custId, BigDecimal amt)
			throws BizException {
		if(amt == null){
			throw new BizException("金额不能为空");
		}
		addOverdueAmt(custId, BigDecimal.ZERO.subtract(amt));
		
	}

	@Override
	public void addOverdueNum(String custId, int count) throws BizException {
		BizCreditReportBO bo = findByCustInfo(custId);
		bo.setOverdueNum(bo.getOverdueNum()+count);
		creditReportDAO.update(bo);
		
	}

	@Override
	public void subtractOverdueNum(String custId, int count)
			throws BizException {
		addOverdueNum(custId, 0-count);
	}

	@Override
	public void addSeriousOverdueNum(String custId, int count)
			throws BizException {
		BizCreditReportBO bo = findByCustInfo(custId);
		bo.setOverdueNum(bo.getOverdueNum()-count);
		bo.setSerOverdueNum(bo.getSerOverdueNum()+count);
		creditReportDAO.update(bo);
	}

	@Override
	public void subtractSeriousOverdueNum(String custId, int count)
			throws BizException {
		BizCreditReportBO bo = findByCustInfo(custId);
		bo.setSerOverdueNum(bo.getSerOverdueNum()-count);
		creditReportDAO.update(bo);
	}
	
}
