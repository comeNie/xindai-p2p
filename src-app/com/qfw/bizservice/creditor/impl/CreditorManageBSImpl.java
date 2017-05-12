package com.qfw.bizservice.creditor.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.creditor.ICreditorManageDAO;
import com.qfw.jbpm.bizservice.JbpmService;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.bo.BizLoanApproveBO;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.platform.model.transfer.TransferSearchVo;
import com.qfw.platform.model.transfer.TransferVo;

/**
 * 债权管理服务
 * @author jie
 *
 */
@Service("creditorManageBS")
public class CreditorManageBSImpl extends BaseServiceImpl implements ICreditorManageBS {
	
	@Autowired
	@Qualifier("baseDAO")
	private IBaseDAO baseDAO;	
	@Autowired
	private ICreditorManageDAO creditorManageDAO;
	@Autowired
    private JbpmService jbpmService;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public CreditorManageVO findInfoById(Integer id) {
		return this.creditorManageDAO.findInfoByID(id);
	}
	
	@Override
	public List<CreditorManageVO> findInfoPagesByVO(CreditorSearchVO searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<CreditorManageVO> result= this.creditorManageDAO.findInfoPagesByVO(searchVO, first, pageSize);
			 if(null!=result && result.size()>0){
				 return result;
			 }
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
			throw new BizException(e);
		}
		return null;
	}
	
	@Override
	public int getCountByVO(CreditorSearchVO searchVO) {
		int num = 0;
		try {
			num = this.creditorManageDAO.getCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}
	
	/**
	 * 新增债权信息
	 */
	public void addCreditorRightTran(BizCreditorRightTranBO creditorRightTranBO){
		creditorRightTranBO.setSysCreateTime(new Timestamp(new Date().getTime()));
		creditorRightTranBO.setSysCreateUser(ViewOper.getUser().getUserId());
		creditorRightTranBO.setSysUpdateTime(new Timestamp(new Date().getTime()));
		creditorRightTranBO.setSysUpdateUser(ViewOper.getUser().getUserId());
		this.getHibernateTemplate().save(creditorRightTranBO);
	}

	@Override
	public Map<String, Object> calAmtForCust(Integer custId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		CreditorSearchVO searchVO = new CreditorSearchVO();
		searchVO.setCustId(custId);
		List<CreditorManageVO> list = this.creditorManageDAO.findInfoList(searchVO);
		if(null!=list&&list.size()>0){
			for (CreditorManageVO creditorManageVO : list) {
				if(null != creditorManageVO.getCrId()){
					continue;
				}
				
				BigDecimal income = BigDecimal.ZERO;  // 已赚金额
				BigDecimal interest = BigDecimal.ZERO;	// 利息收益
				int num = 0;						  // 回收中的债权数量
				if(result.containsKey("income")){
					income = (BigDecimal)result.get("income");
				}
				if(result.containsKey("interest")){
					interest = (BigDecimal)result.get("interest");
				}
				if(result.containsKey("creNum")){
					num = (Integer)result.get("creNum");
				}
				
				if(AppConst.CR_STATUS_CD_TENDERING.equals(creditorManageVO.getCrStatusCd())){
					// 投标中
					
				}else if(AppConst.CR_STATUS_CD_REPAYING.equals(creditorManageVO.getCrStatusCd())){
					// 还款中
					income = income.add(creditorManageVO.getTotalProfitAmt());
					interest = interest.add(creditorManageVO.getInterest());
					num = num+1;
				}else if(AppConst.CR_STATUS_CD_END.equals(creditorManageVO.getCrStatusCd())){
					// 结清
					income = income.add(creditorManageVO.getTotalProfitAmt());
					interest = interest.add(creditorManageVO.getInterest());
				}
				
				result.put("income", income);
				result.put("interest", interest);
				result.put("creNum", num);
			}
		}
		
		searchVO.setCreditorRightTran("crTran");
		List<CreditorManageVO> tranList = this.creditorManageDAO.findInfoList(searchVO);
		if(null!=tranList&&tranList.size()>0){
			for (CreditorManageVO creditorManageVO : tranList) {
				
				BigDecimal income = BigDecimal.ZERO;  // 已赚金额
				BigDecimal lose = BigDecimal.ZERO;  // 转让盈亏
				BigDecimal interest = BigDecimal.ZERO;	// 利息收益
				int num = 0;						  // 回收中的债权数量
				if(result.containsKey("income")){
					income = (BigDecimal)result.get("income");
				}
				if(result.containsKey("interest")){
					interest = (BigDecimal)result.get("interest");
				}
				if(result.containsKey("lose")){
					lose = (BigDecimal)result.get("lose");
				}
				if(result.containsKey("creNum")){
					num = (Integer)result.get("creNum");
				}
				
				if(AppConst.CR_STATUS_CD_REPAYING.equals(creditorManageVO.getCrStatusCd())){
					// 还款中
					income = income.add(creditorManageVO.getTotalProfitAmt());
					interest = interest.add(creditorManageVO.getInterest());
					lose = lose.add(creditorManageVO.getTakeAmt().subtract(creditorManageVO.getTakeBalAmt()));
					num = num+1;
				}else if(AppConst.CR_STATUS_CD_END.equals(creditorManageVO.getCrStatusCd())){
					// 结清
					income = income.add(creditorManageVO.getTotalProfitAmt());
					interest = interest.add(creditorManageVO.getInterest());
					lose = lose.add(creditorManageVO.getTakeAmt().subtract(creditorManageVO.getTakeBalAmt()));
				}
				
				result.put("income", income);
				result.put("interest", interest);
				result.put("lose", lose);
				result.put("creNum", num);
			}
		}
		
		return result;
	}

	@Override
	public List<TransferVo> findListPagesByVO(TransferSearchVo searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<TransferVo> result= this.creditorManageDAO.findListPagesByVO(searchVO, first, pageSize);
			 if(null!=result && result.size()>0){
				 return result;
			 }
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
			throw new BizException(e);
		}
		return null;
	}

	@Override
	public int getTransCountByVO(TransferSearchVo searchVO) {
 
		int num = 0;
		try {
			num = this.creditorManageDAO.getTransCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}
}
