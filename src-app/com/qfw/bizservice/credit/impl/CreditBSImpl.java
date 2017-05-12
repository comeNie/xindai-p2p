package com.qfw.bizservice.credit.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.credit.ICreditBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.dao.credit.ICreditLimitDAO;
import com.qfw.dao.credit.ICreditTxRecordDAO;
import com.qfw.dao.credit.ICreditUseDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCreditLimitBO;
import com.qfw.model.bo.BizCreditTxRecordBO;
import com.qfw.model.bo.BizCreditUseBO;
import com.qfw.model.vo.credit.CreditQueryVO;
import com.qfw.model.vo.credit.RequestCreditVO;
import com.qfw.model.vo.credit.ResponseCreditLimitVO;

@Service("creditBS")
public class CreditBSImpl extends BaseServiceImpl implements ICreditBS {

	@Autowired
	private ICreditLimitDAO creditLimitDAO;
	@Autowired
	private ICreditUseDAO creditUseDAO;
	@Autowired
	private ICreditTxRecordDAO txRecordDAO;
	@Autowired
	public ISeqBS seqBS;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public synchronized void tranTransactionCredit(RequestCreditVO vo) throws BizException{
		try {
			// 校验vo
			String mess = this.validateVO(vo);
			if(null!=mess&&mess.length()>0){
				throw new BizException(mess);
			}
			// 封装vo，添加额度内部操作参数
			vo = this.initVOForInternal(vo);
			if(null==vo.getOpstateCd()||vo.getOpstateCd().length()<=0){
				// 没有引起金额变化 
				return;
			}
			CreditQueryVO queryVO = this.gainRelKey(vo);
			queryVO.setState(AppConst.YES_FLAG);
			List<BizCreditLimitBO> limitList = this.queryCreditLimit(queryVO);
			if(CollectionUtil.isEmpty(limitList)){
				return;
			}
			this.calUNApproveAmt(vo, limitList);
		} catch (Exception e) {
			log.error("额度信息更新失败："+e);
			throw new BizException("额度更新失败："+e.getMessage());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void tranApproveCredit(RequestCreditVO vo)  throws BizException{
		try {
//			CreditQueryVO queryVO = this.gainRelKey(vo);
//			this.calApproveAmt(queryVO);
		} catch (Exception e) {
			log.error("额度审批更新失败，原因："+e);
			throw new BizException("额度审批更新失败，原因："+e.getMessage());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Map<String, BigDecimal> surplusCreditAmt(RequestCreditVO vo,String relValue) throws BizException {
		try {
			Map<String, BigDecimal> result = null;
			List<BizCreditUseBO> useList = null;
			CreditQueryVO queryVO = this.gainRelKey(vo);
			queryVO.setState(AppConst.YES_FLAG);
			List<BizCreditLimitBO> limitList = this.queryCreditLimit(queryVO);
			if(CollectionUtil.isEmpty(limitList)){
				result = new HashMap<String, BigDecimal>();
				result.put(vo.getRelId(), BigDecimal.ZERO);
				return result;
			}
			
			result = new HashMap<String, BigDecimal>();
			for (BizCreditLimitBO limitBO : limitList) {
				useList = this.queryCreditUse(limitBO.getId().toString());
				if(CollectionUtil.isEmpty(useList)){
					result.put(limitBO.getRelId(), BigDecimal.ZERO);
				}else{
					BigDecimal amt = this.calUseAbleAmt(limitBO, useList);
					result.put(limitBO.getRelId(), amt);
				}
			}
			return result;
		} catch (Exception e) {
			log.error("额度可用金额查询失败!"+e);
			throw new BizException("额度可用金额查询失败!"+e.getMessage());
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public ResponseCreditLimitVO queryCreditLimitVO(RequestCreditVO vo) throws BizException{
		try {
			CreditQueryVO queryVO = this.gainRelKey(vo);
			queryVO.setState(AppConst.YES_FLAG);
			List<BizCreditLimitBO> limitList = this.queryCreditLimit(queryVO);
			if(null==limitList||limitList.size()!=1){
				return null;
			}
			BizCreditLimitBO creditLimitBO = limitList.get(0);
			List<BizCreditUseBO> useList = this.queryCreditUse(creditLimitBO.getId().toString());
			ResponseCreditLimitVO responseVO = new ResponseCreditLimitVO();
			responseVO.setCreditLimit(creditLimitBO);
			responseVO.setUsableAmt(this.calUseAbleAmt(creditLimitBO, useList));
			return responseVO;
		} catch (Exception e) {
			log.error("查询额度明细信息失败："+e);
			throw new BizException("查询额度明细信息失败："+e.getMessage());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void tranFrozenAmt(RequestCreditVO vo) throws BizException{
		try {
			CreditQueryVO queryVO = this.gainRelKey(vo);
			queryVO.setState(AppConst.YES_FLAG);
			List<BizCreditLimitBO> limitList = this.queryCreditLimit(queryVO);
			if(CollectionUtil.isEmpty(limitList)){
				return;
			}
			this.frozenAmt(limitList, vo);
		} catch (Exception e) {
			log.error("额度冻结失败:"+e);
			throw new BizException("额度冻结失败:"+e.getMessage());
		}
	}
	
	/**
	 * 额度冻结方法，解冻时，传入的交易金额为负数
	 * @param limitList
	 * @param vo
	 * @throws BizException
	 */
	private void frozenAmt(List<BizCreditLimitBO> limitList,RequestCreditVO vo) throws BizException{
		for (BizCreditLimitBO limitBO : limitList) {
			limitBO.setFreezeAmt(limitBO.getFreezeAmt().add(vo.getPassAmt()));
			if((limitBO.getClAmt().subtract(limitBO.getFreezeAmt())).compareTo(BigDecimal.ZERO)<=0){
				limitBO.setFreezeAmt(limitBO.getClAmt());
				limitBO.setCreditStateCd(AppConst.CREDITLIMIT_STATUE_FZ);
			}else{
				limitBO.setCreditStateCd(AppConst.CREDITLIMIT_STATUE_USABLE);
			}
			this.creditLimitDAO.update(limitBO);
		}
	}
	
	/**
	 * 判断本次交易金额是否能通过
	 * @param operation
	 * @param bo
	 * @return
	 */
	private boolean decideOperation(String operation,boolean approFlag,BigDecimal applyAmt,BizCreditLimitBO limitBO,BizCreditUseBO useBO) throws BizException{
		if(AppConst.CREDITLIMIT_AMT_USE.equals(operation)&&!approFlag){	// 占用操作且为非最终放行
			BigDecimal amt = BigDecimal.ZERO;
			
			amt = this.calUseAbleAmt(limitBO, useBO);
			
			//System.out.println("amt=="+amt+";applyAmt=="+applyAmt+";比较="+amt.subtract(applyAmt).compareTo(BigDecimal.ZERO));
			
			if(amt.subtract(applyAmt).compareTo(BigDecimal.ZERO)<0){
				return false;
			}
		}else if(AppConst.CREDITLIMIT_AMT_USE.equals(operation)&&approFlag){// 占用操作且为最终放行
			BigDecimal amt = BigDecimal.ZERO;
			amt = this.calUseAbleAmt(limitBO, useBO);
			//System.out.println("amt535=="+amt);
			if(amt.compareTo(BigDecimal.ZERO)<0){
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private BigDecimal calUseAbleAmt(BizCreditLimitBO limitBO,Object obj) throws BizException {
		BigDecimal result = BigDecimal.ZERO;
		if(null==limitBO){
			return result;
		}
		if(obj instanceof java.util.List){
			BigDecimal temp = BigDecimal.ZERO;
			for (BizCreditUseBO useBO : (List<BizCreditUseBO>)obj) {
				// 上限 - 冻结金额 -已占用-待占用
				temp = limitBO.getClAmt().subtract(limitBO.getFreezeAmt())
						.subtract(useBO.getTieupAmt()).subtract(useBO.getPreTieupAmt());
				result = result.add(temp);
			}
		}else{
			BizCreditUseBO useBO = (BizCreditUseBO)obj;
			// 上限 - 冻结金额 -已占用-待占用
			result = limitBO.getClAmt().subtract(limitBO.getFreezeAmt())
					.subtract(useBO.getTieupAmt()).subtract(useBO.getPreTieupAmt());
		}
		
		if(result.compareTo(BigDecimal.ZERO)<0){
			return BigDecimal.ZERO;
		}else{
			return result;
		}
	}
	
	/**
	 * 转换请求vo，封装部分数据，用于额度内部服务使用
	 * @param vo
	 * @return
	 */
	public RequestCreditVO initVOForInternal(RequestCreditVO vo) throws BizException{
		if(null==vo.getRelTypeCd()||vo.getRelTypeCd().length()<=0){
			vo.setRelTypeCd(AppConst.CREDITLIMIT_RELTYPE_CUST);
		}
//		if(null!=vo.getPassAmt()&&vo.getPassAmt().compareTo(BigDecimal.ZERO)>0){
//			vo.setOpstateCd(AppConst.CREDITLIMIT_AMT_USE);
//		}else if(null!=vo.getUnPassAmt()&&vo.getUnPassAmt().compareTo(BigDecimal.ZERO)>0){
//			vo.setOpstateCd(AppConst.CREDITLIMIT_AMT_RECOVERY);
//		}else{
//			vo.setOpstateCd(null);
//		}
		if(AppConst.EVENTTYPE_LOAN.equals(vo.getEventTypeCd().toString())){// 借款申请
			vo.setAutoflag(false);
			vo.setApprove(false);
			if(null!=vo.getPassAmt()&&vo.getPassAmt().compareTo(BigDecimal.ZERO)>0){
				vo.setOpstateCd(AppConst.CREDITLIMIT_AMT_USE);
			}else{
				vo.setOpstateCd(AppConst.CREDITLIMIT_AMT_RECOVERY);
			}
		}else if(AppConst.EVENTTYPE_CREDITOR.equals(vo.getEventTypeCd().toString())){	// 放款
			vo.setAutoflag(false);
			vo.setApprove(true);
			vo.setOpstateCd(AppConst.CREDITLIMIT_AMT_USE);
		}else if(AppConst.EVENTTYPE_REAPY.equals(vo.getEventTypeCd().toString())){ // 还款
			vo.setAutoflag(true);
			vo.setOpstateCd(AppConst.CREDITLIMIT_AMT_RECOVERY);
		}else if(AppConst.EVENTTYPE_FREEZE.equals(vo.getEventTypeCd().toString())){	// 冻结
			vo.setFreeze(true);
		}else{
			throw new BizException("传入参数错误：目前不支持此交易型态");
		}
		return vo;
	}
	
	/**
	 * 校验vo
	 * @param vo
	 */
	private String validateVO(RequestCreditVO vo){
		String res = new String();
		if(null == vo){
			res = "传入参数不能为空";
		}else if(null == vo.getRelId() || vo.getRelId().length()<=0){
			res = "关联流水号不能为空";
		}else if(null == vo.getEventTypeCd()){
			res = "交易型态不能为空";
		}
		return res;
	}
	
	
	/**
	 * 获取额度查询vo,用于额度查询条件的封装，额度扩展
	 * @param vo
	 * @return
	 */
	private CreditQueryVO gainRelKey(RequestCreditVO vo) throws BizException{
		CreditQueryVO queryVO = new CreditQueryVO();  
		queryVO.setEventTypeCd(vo.getEventTypeCd());  // 交易型态
		queryVO.setOpstateCd(vo.getOpstateCd());    // 操作
		queryVO.setRelId(vo.getRelId());            // 关联id(客户id)
		queryVO.setRelTypeCd(vo.getRelTypeCd());    // 关联类型
		queryVO.setTxNO(vo.getTxNO());               // 交易编号
		return queryVO;
	}
	
	/**
	 * 查询待处理额度列表
	 * @param queryVO
	 * @return
	 */
	private List<BizCreditLimitBO> queryCreditLimit(CreditQueryVO queryVO) throws BizException{
		try {
			List<BizCreditLimitBO> list = this.creditLimitDAO.queryListByParams(queryVO); //获取额度主表信息列表
			if(CollectionUtil.isEmpty(list)){
				return null;
			}
			return list;
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	/**
	 * 根据额度id 查询额度动用信息
	 * @param clId
	 * @return
	 */
	public List<BizCreditUseBO> queryCreditUse(String clId) throws BizException{
		try {
			List<BizCreditUseBO> list = this.creditUseDAO.findByClId(clId);
			if(CollectionUtil.isEmpty(list)){
				throw new BizException("获取额度动用表失败!");
			}
			return list;
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	/**
	 * 额度金额更新:待放行操作调用或自动化调用
	 * @param vo
	 * @param creditList
	 */
	private void calUNApproveAmt(RequestCreditVO vo, List<BizCreditLimitBO> creditList) throws BizException{
		try {
			List<BizCreditUseBO> useList = null;
			for (BizCreditLimitBO limitBO : creditList) {
				useList  =  this.queryCreditUse(limitBO.getId().toString());
				for(BizCreditUseBO useBO : useList){
					boolean flag = this.decideOperation(vo.getOpstateCd(),vo.getApprove(),vo.getPassAmt(), limitBO, useBO);
					if(!flag){
						throw new BizException("额度操作：本次交易金额校验不通过。");
					}
					this.calCreditLimitAmt(vo.getAutoflag(),vo.getApprove(),vo.getOpstateCd(),vo.getPassAmt(),
							vo.getUnPassAmt(), useBO);
					this.creditUseDAO.update(useBO);
				}
				this.saveTxRecord(vo, limitBO);
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	/**
	 * 审核放行时，额度调用
	 * @param vo
	 * @param creditList
	 */
	private void calApproveAmt(CreditQueryVO vo) throws BizException{
		try {
//			List<BizCreditUseBO> useList = null;
//			List<BizCreditTxRecordBO> list = this.txRecordDAO.queryListByTxNo(vo.getTxNO(),AppConst.NO_FLAG);
//			if(CollectionUtil.isEmpty(list)){
//				throw new BizException("额度更新失败!");
//			}
//			for (BizCreditTxRecordBO txRecord : list) {
//				useList = this.queryCreditUse(txRecord.getClId().toString());
//				for(BizCreditUseBO useBO : useList){
//					this.calCreditLimitAmt(false,true,txRecord.getOpstateCd(),txRecord.getTxAmt(), useBO);
//					this.creditUseDAO.update(useBO);
//				}
//				this.updateTxTecord(txRecord);
//			}
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	/**
	 * 保存额度交易记录
	 * @param vo
	 * @param bo
	 */
	private void saveTxRecord(RequestCreditVO vo, BizCreditLimitBO bo) throws BizException{
		BizCreditTxRecordBO txRecord  = new BizCreditTxRecordBO();
		txRecord.setTxNo(vo.getTxNO());
		txRecord.setClId(bo.getId());
		txRecord.setEventTypeCd(vo.getEventTypeCd());
		txRecord.setOpstateCd(vo.getOpstateCd());
		if(null!=vo.getOpstateCd()&&vo.getOpstateCd().equals(AppConst.CREDITLIMIT_AMT_RECOVERY)){
			txRecord.setTxAmt(vo.getUnPassAmt());
		}else{
			txRecord.setTxAmt(vo.getPassAmt());
		}
		txRecord.setClAmt(bo.getClAmt());
		txRecord.setTxDate(new Date());
		txRecord.setCtrStateCd(AppConst.YES_FLAG);
		txRecord.setWorkItemId(Integer.valueOf(bo.getWorkItemId()));
		txRecord.setSysCreateUser(bo.getSysUpdateUser());
		txRecord.setSysCreateTime(bo.getSysUpdateTime());
		txRecord.setSysUpdateUser(bo.getSysUpdateUser());
		txRecord.setSysUpdateTime(bo.getSysUpdateTime());
		if(null==vo.getTxNO()||vo.getTxNO().length()<=0){
			String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
			txRecord.setTxNo(txNO);
		}
		
		this.txRecordDAO.save(txRecord);
	}
	
	/**
	 * 更新额度交易记录状态
	 * @param vo
	 * @param bo
	 */
//	private void updateTxTecord(BizCreditTxRecordBO txRecord) throws BizException{
//		txRecord.setCtrStateCd("1");
//		this.txRecordDAO.update(txRecord);;
//	} 
	
	/**
	 * 额度金额处理：占用恢复
	 * @param vo
	 * @param bo
	 */
	private void calCreditLimitAmt(boolean autoFlag, boolean approve, String operation,
			BigDecimal passamt,BigDecimal unPassAmt, BizCreditUseBO bo) throws BizException{
		if(autoFlag){	//自动
			if(AppConst.CREDITLIMIT_AMT_USE.equals(operation)){	//占用
				bo.setTieupAmt(bo.getTieupAmt().add(passamt));
			} else if(AppConst.CREDITLIMIT_AMT_RECOVERY.equals(operation)){	//恢复
				bo.setTieupAmt(bo.getTieupAmt().subtract(unPassAmt));
			}
		} else {
			if(AppConst.CREDITLIMIT_AMT_USE.equals(operation)){	//占用
				if(approve){	//最终放行操作
					if(bo.getPreTieupAmt().subtract(passamt).subtract(unPassAmt).compareTo(BigDecimal.ZERO)<0){
						//本次操作后，待占用金额为负，即待占用金额转占用金额出错。现增加报错控制
						throw new BizException("额度操作失败：待占用金额转占用金额异常，转移后，待占用金额为负数。");
					}
					bo.setTieupAmt(bo.getTieupAmt().add(passamt));
					bo.setPreTieupAmt(bo.getPreTieupAmt().subtract(passamt));
					bo.setPreTieupAmt(bo.getPreTieupAmt().subtract(unPassAmt));
				} else {	//普通放行操作
					bo.setPreTieupAmt(bo.getPreTieupAmt().add(passamt));
				}
			} else if(AppConst.CREDITLIMIT_AMT_RECOVERY.equals(operation)){	//恢复
				if(approve){	//放行操作
					bo.setTieupAmt(bo.getTieupAmt().subtract(unPassAmt));
					bo.setPreTieupAmt(bo.getPreTieupAmt().subtract(unPassAmt));
				} else {	//普通放行操作
					bo.setPreTieupAmt(bo.getPreTieupAmt().subtract(unPassAmt));
				}
			}
		}
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	@Override
	public boolean checkCreditlimit(RequestCreditVO vo) throws BizException {
		boolean result = false;
		try {
			CreditQueryVO queryVO = this.gainRelKey(vo);   //CreditQueryVO是指额度内部查询vo
			queryVO.setState(AppConst.YES_FLAG);            //// 状态  后面那个表示是否表示  
			List<BizCreditLimitBO> limitList = this.queryCreditLimit(queryVO);
			if(!CollectionUtil.isEmpty(limitList)){
				result = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	/**
	 * 校验客户额度够不够
	 * @param custRefcode 客户流水号
	 * @param amt 客户金额
	 * @return ture=额度够  false=额度不够
	 * custRefcode 客户ID
	 */
	public boolean checkCreditlimit(String custRefcode,BigDecimal amt){
		try{
			RequestCreditVO creditSearchVO = new RequestCreditVO();
			creditSearchVO.setRelId(custRefcode);
			Map<String, BigDecimal> creditMap = surplusCreditAmt(creditSearchVO, null);
			if(creditMap == null || creditMap.get(custRefcode) == null){
				return false;
			}
			return creditMap.get(custRefcode).compareTo(amt)>=0;
		}catch(Exception e){
			log.error("额度校验异常",e);
			return false;
		}
		
	}

	@Override
	public BizCreditLimitBO findObjectByCustId(CreditQueryVO vo) {
	    
		List<BizCreditLimitBO> list =  creditLimitDAO.queryListByParams(vo);
		if(null != list && list.size() != 0){
			return list.get(0);
		}else{
			return null;
		}
	
		
	}
}
