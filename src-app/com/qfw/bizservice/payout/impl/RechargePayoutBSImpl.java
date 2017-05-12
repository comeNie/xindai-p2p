package com.qfw.bizservice.payout.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.custinfo.account.ICustAccountBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.custinfo.account.impl.TransferAccountsBSImpl;
import com.qfw.bizservice.custinfo.account.impl.TransferAccountsBSImpl.AccountDirectionEnum;
import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.bizservice.transaction.ITradeBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.MobileUtil;
import com.qfw.common.util.NumberUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.payout.IRechargePayoutDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAccountBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizPaymentInfoBO;
import com.qfw.model.bo.BizRechargeCardBO;
import com.qfw.model.bo.BizTradeBO;
import com.qfw.model.vo.custinfo.account.AccountRequestVO;
import com.qfw.model.vo.payout.RechargeCardResponseVO;
import com.qfw.model.vo.payout.RechargeCardVO;
import com.qfw.model.vo.payout.RechargeVO;

/**
 * 充值实现类
 *
 * @author kyc
 */
@Service("rechargePayoutBS")
public class RechargePayoutBSImpl extends BaseServiceImpl implements
		IRechargePayoutBS {

	@Autowired
	private IRechargePayoutDAO rechargePayoutDAO;
	@Autowired
	private ICustAccountBS accountBS;
	@Autowired
	private ITradeBS tradeBS;
	@Autowired
	private ISeqBS seqBS;
	@Autowired
	private IParamBS paramBS;
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	@Autowired
	private ICustInfoBS custInfoBS;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Autowired
	@Qualifier("transferAccountsBS")
	private ITransferAccountsBS transferAccountsBS;
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void transRechargePayou(RechargeVO vo) throws BizException {
		try {
			if(null == vo || null== vo.getRechargeType() || vo.getRechargeType().length()<=0){
				throw new BizException("充值功能--传入参数不能为空");
			}
			if(AppConst.PAYOUTTYPE_ONLINE.equals(vo.getRechargeType())){
				this.payoutForInterface(vo);
			}
			if(AppConst.PAYOUTTYPE_CARD.equals(vo.getRechargeType())){
				this.payoutForCard(vo);
			}
		} catch (Exception e) {
			log.error("充值失败："+e);
			 
			throw new BizException("充值失败："+e.getMessage());
		}
	}
	
	/**
	 * 充值外部接口调用
	 * @throws BizException
	 */
	private void payoutForInterface(RechargeVO vo) throws BizException{
		try {
			// 调用外部接口
		} catch (Exception e) {
			log.error("在线充值失败,原因："+e);
			throw new BizException(e);
		}
	}

	/**
	 * 理财卡充值
	 * @param vo
	 */
	private void payoutForCard(RechargeVO vo) throws BizException {
		String mess = this.validateVO(vo);
		if(null!=mess&&mess.length()>0){
			throw new BizException("理财卡信息校验失败："+mess);
		}
		vo.setPassword(MD5Utils.getMD5Str(vo.getPassword()));
		List<String> statusList = new ArrayList<String>();
		statusList.add(AppConst.RECHARGE_STATUS_INIT);
		statusList.add(AppConst.RECHARGE_STATUS_EXPORT);
		List<BizRechargeCardBO> boList = this.rechargePayoutDAO.queryBOByParams(vo,statusList);
		if(null == boList || boList.size()<=0){
			throw new BizException("理财卡信息不存在,请确认信息输入是否正确!");
		}
		BizRechargeCardBO bo = boList.get(0);
		
		String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
		
		// 账户信息接口
		AccountRequestVO requestVO = new AccountRequestVO();
		requestVO.setDealAmt(bo.getAmt());;
		requestVO.setDealType(AppConst.ACCOUNT_AMT);
		requestVO.setAccount(vo.getAccount());
		requestVO.setId(vo.getAccountID());
		requestVO.setEventTypeCd(AppConst.DETAIL_TYPE_ACCOUNTAMT);
		requestVO.setTxNo(txNO);
		BigDecimal usableAmt =this.accountBS.updateAccountAmt(requestVO);
		
		// 更新理财卡信息
		bo.setAccount(vo.getAccount());
		bo.setAccountId(vo.getAccountID());
		bo.setRechargeDate(new Date());
		bo.setStatus(AppConst.RECHARGE_STATUS_USE);
		this.rechargePayoutDAO.update(bo);
		
		// 生成交易明细
		BizTradeBO tradeBO = new BizTradeBO();
		tradeBO.setAccountBal(usableAmt);
		tradeBO.setAccountNum(bo.getAccount());
		tradeBO.setTradaTime(new Date());
		tradeBO.setTradeAmt(bo.getAmt());
		tradeBO.setTradeTypeCd(AppConst.TRADE_TYPE_RECHARGE);
		tradeBO.setComment(AppConst.PAYOUTTYPE_CARD);
		tradeBO.setSysCreateTime(new Date());
		tradeBO.setSysUpdateTime(new Date());
		tradeBO.setSysCreateUser(ViewOper.getUser().getUserId());
		tradeBO.setSysUpdateUser(ViewOper.getUser().getUserId());
		tradeBO.setTradeNum(txNO);
		this.tradeBS.save(tradeBO);
	}
	
	/**
	 * 检查理财卡信息是否存在
	 * @param vo
	 * @return
	 */
	private String validateVO(RechargeVO vo) {
		String str = "";
		if(null==vo){
			return "充值信息参数不能为空!";
		}
		if(null == vo.getAccountID() || vo.getAccountID()==0){
			return "充值账号不能为空!";
		}
		if(null == vo.getAccount() || vo.getAccount().length()<=0){
			return "充值账号不能为空!";
		}
		if(null == vo.getCardCD() || vo.getCardCD().length()<=0){
			return "卡号不能为空!";
		}
		if(null == vo.getPassword() || vo.getPassword().length()<=0){
			return "密码不能为空!";
		}
		return str;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	@Override
	public BizRechargeCardBO findCardBOById(Integer id) throws BizException {
		return (BizRechargeCardBO)this.rechargePayoutDAO.findById(BizRechargeCardBO.class, id);
	}

	@Override
	public int findCardCountByVO(RechargeCardVO cardVO) throws BizException {
		return commonQuery.findCountByWapSQL(this.assembleSQL(cardVO), null);
	}

	@Override
	public List<RechargeCardResponseVO> findCardBOPagesByVO(RechargeCardVO cardVO,
			int first, int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(this.assembleSQL(cardVO), first, pageSize,RechargeCardResponseVO.class);
	};
	
	@Override
	public RechargeCardResponseVO findCardInfoByID(Integer rechargeCardID) {
		RechargeCardVO searchVO = new RechargeCardVO();
		searchVO.setId(rechargeCardID);
		List<RechargeCardResponseVO> list = this.commonQuery.findObjects(this.assembleSQL(searchVO), RechargeCardResponseVO.class);
		if(null!=list&&list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	private String assembleSQL(RechargeCardVO cardVO) {
		StringBuilder sb = new StringBuilder("");
		//sb.append(" select * from (");
		sb.append(" SELECT card.id ,card.batch_no,card.CARD_CD,card.CARD_PWD,card.AMT,card.START_DATE,card.END_DATE,card.RECHARGE_DATE,");
		sb.append(" card.ACCOUNT_ID,card.ACCOUNT,card.`STATUS`,card.SYS_CREATE_TIME,cust.CUST_NAME ,sysuser.USER_NAME");
		sb.append(" FROM biz_recharge_card card left JOIN biz_account acc on card.ACCOUNT_ID = acc.ID left join biz_customer cust on acc.CUST_ID = cust.ID left join sys_user sysuser on cust.USER_ID = sysuser.USER_ID");
		sb.append(" where 1=1 ");
		if(cardVO != null){
			if(StringUtils.isNotEmpty(cardVO.getCustName())){
				sb.append(" and cust.CUST_NAME like '%").append(cardVO.getCustName()).append("%'");
			}
			if(StringUtils.isNotEmpty(cardVO.getStatus())){
				sb.append(" and card.status = '").append(cardVO.getStatus()).append("'");
			}
			if(StringUtils.isNotEmpty(cardVO.getCardCd())){
				sb.append(" and card.CARD_CD like '%").append(cardVO.getCardCd()).append("%'");
			}
			if(StringUtils.isNotEmpty(cardVO.getBatchNo())){
				sb.append(" and card.BATCH_NO = '").append(cardVO.getBatchNo()).append("'");
			}
			
		}
		sb.append("order by card.id desc ");
		//sb.append(") temp where 1=1");
		return sb.toString();
	}

	@Override
	public void updateRechargeCardStatus(String status, List<String> cardList)
			throws BizException {
		if(null == cardList || cardList.size()<=0){
			return;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" update biz_recharge_card set status = '").append(status).append("'");
		sql.append(" where ");
		int num =1;
		for (String id : cardList) {
			if(num>1){
				sql.append(" or ");
			}
			sql.append(" CARD_CD = '").append(id).append("'");
			num=num+1;
		}
		this.getJdbcTemplate().update(sql.toString());
	}
	
	/**
	 * 生成充值信息
	 * @param bank 银行
	 * @param amount 金额
	 * @param custId 客户id
	 * @return
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public BizPaymentInfoBO createPaymentInfo(String bank,String amount,Integer custId,HttpServletRequest request) throws BizException{
		
		try{
			BizPaymentInfoBO paymentInfoBO = new BizPaymentInfoBO();
						
			Date now = new Date();
			String dateStr = DateUtils.getDateString("yyyyMMddHHmmss",now);
			String serverTime = "";
			
			String billNo =  dateStr+seqBS.getResultNum(AppConst.SEQ_PAY_NUM);//"Td0100d0101";6
			String signInfo = "";
			String returnURL = "";
			String adviceURL = "";
//			String merNo = paramBS.getParam("PAY_MER_CODE");
			int serverPort = request.getServerPort();
			if(serverPort == 80){
				returnURL = "http://"+request.getServerName()+request.getContextPath()+paramBS.getParam("PAY_RETURN_URL");
				adviceURL = "http://"+request.getServerName()+request.getContextPath()+paramBS.getParam("PAY_ADVICE_URL");
			}else{
				returnURL = "http://"+request.getServerName()+":"+serverPort+request.getContextPath()+paramBS.getParam("PAY_RETURN_URL");
				adviceURL = "http://"+request.getServerName()+":"+serverPort+request.getContextPath()+paramBS.getParam("PAY_ADVICE_URL");
			}
			
//			String md5key = paramBS.getParam("PAY_MD5_KEY");//
//			String md5 = MD5Utils.getMD5Str(merNo+"&"+billNo+"&"+amount+"&"+returnURL+"&"+md5key);
			if(AppConst.PAY_SERVICE_TL.equals(paramBS.getParam("PAY_SERVICE"))){
				//构造订单请求对象，生成signMsg。
				com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
				requestOrder.setInputCharset(Integer.parseInt(AppConst.TL_INPUT_CHARSET));
				requestOrder.setPickupUrl(returnURL);
				requestOrder.setReceiveUrl(adviceURL);
				requestOrder.setVersion(AppConst.TL_VERSION);
				requestOrder.setLanguage(Integer.parseInt(AppConst.TL_LANGUAGE));
				requestOrder.setSignType(Integer.parseInt(paramBS.getParam("SIGN_TYPE")));
				requestOrder.setPayType(Integer.parseInt(AppConst.TL_PAYTYPE));
				requestOrder.setIssuerId("");
				requestOrder.setMerchantId(paramBS.getParam("PAY_MER_CODE"));
				requestOrder.setPayerName("");
				requestOrder.setPayerEmail("");
				requestOrder.setPayerTelephone("");
				requestOrder.setPayerIDCard("");
				requestOrder.setPid("");
				requestOrder.setOrderNo(billNo);
				requestOrder.setOrderAmount(Long.parseLong(new BigDecimal(amount).multiply(new BigDecimal(100)).toString()));
				requestOrder.setOrderCurrency(AppConst.TL_ORDER_CURRENCY);
				requestOrder.setOrderDatetime(dateStr);
//				requestOrder.setOrderExpireDatetime(AppConst.TL_ORDER_EXPIREDATETIME);
				requestOrder.setProductName("");
//				if(null!=productPrice&&!"".equals(productPrice)){
//					requestOrder.setProductPrice(Long.parseLong(productPrice));
//				}
//				if(null!=productNum&&!"".equals(productNum)){
//					requestOrder.setProductNum(Integer.parseInt(productNum));
//				}	
				requestOrder.setProductId("");
				requestOrder.setProductDesc("");
				if(MobileUtil.JudgeIsMoblie(request)){
					requestOrder.setExt1("1");
				}
				
				requestOrder.setExt2("");
				requestOrder.setExtTL("");//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
				requestOrder.setPan("");
				requestOrder.setTradeNature("");
				requestOrder.setKey(paramBS.getParam("PAY_MD5_KEY")); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

				if(log.isDebugEnabled()){
					log.debug(requestOrder.getSrc());
				}
				signInfo = requestOrder.doSign(); // 签名，设为signMsg字段值。
			}else if(AppConst.PAY_SERVICE_GFB.equals(paramBS.getParam("PAY_SERVICE"))){
				serverTime = DateUtils.getGopayServerTime();
				signInfo = "version=[" + AppConst.PAYMENT_VERSION + "]"
						+ "tranCode=[" + AppConst.PAYMENT_TRANCODE + "]"
						+ "merchantID=[" + AppConst.PAYMENT_MERCHANTID + "]"
						+ "merOrderNum=[" + billNo + "]"
						+ "tranAmt=[" + amount + "]"
						+ "feeAmt=[" + AppConst.PAYMENT_FEEAMT+ "]"
						+ "tranDateTime=[" + dateStr + "]"
						+ "frontMerUrl=[" + returnURL + "]"
						+ "backgroundMerUrl=[" + adviceURL + "]"
						+ "orderId=[]"
						+ "gopayOutOrderId=[]"
						+ "tranIP=[" + AppConst.PAYMENT_TRANIP + "]"
						+ "respCode=[]"
						+ "gopayServerTime=[" + serverTime +"]"
						+ "VerficationCode=[" + AppConst.PAYMENT_VERFICATIONCODE + "]";
				if(log.isDebugEnabled()){
					log.debug(signInfo);
				}
				signInfo = MD5Utils.getMD5Str(signInfo);
			}else{
				log.error("支付服务方参数获取错误");
				throw new BizException("充值失败");
			}
			
			paymentInfoBO.setServerTime(serverTime);
			paymentInfoBO.setCustId(custId);
//			paymentInfoBO.setMerNo(merNo);
			paymentInfoBO.setBillNo(billNo);
			paymentInfoBO.setAmount(amount);
			paymentInfoBO.setReturnUrl(returnURL);
			paymentInfoBO.setAdviceUrl(adviceURL);
			paymentInfoBO.setSignInfo(signInfo);
			paymentInfoBO.setOrderTime(now);
			paymentInfoBO.setDefaultBankNumber(bank);
			paymentInfoBO.setRemark("平台充值");
			paymentInfoBO.setProducts("充值"+amount+"元");
			paymentInfoBO.setStatus(AppConst.PAY_STATUS_INIT);
			paymentInfoBO.setSysCreateTime(now);
			getHibernateTemplate().save(paymentInfoBO);
			
			return paymentInfoBO;
		}catch(Exception e){
			throw new BizException("充值失败");
		}
		
	}
	
	/**
	 * 生成充值信息
	 * @param bank
	 * @param amount
	 * @return
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void updatePaymentInfo(String billNo,String status) throws BizException{
		
		try{
			List<BizPaymentInfoBO> paymentInfos = getHibernateTemplate().find("from BizPaymentInfoBO b where b.billNo = ? ",billNo);
			if(CollectionUtil.isEmpty(paymentInfos)){
				log.error("订单号为"+billNo+"不存在");
				return;
			}
			BizPaymentInfoBO paymentInfoBO = paymentInfos.get(0);
			Integer custId = paymentInfoBO.getCustId();
			if(AppConst.PAY_SUCCESS.equals(status)){//支付成功
				if(!AppConst.PAY_STATUS_SUCCESS.equals(paymentInfoBO.getStatus())){
					
					String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
					
					paymentInfoBO.setStatus(AppConst.PAY_STATUS_SUCCESS);
					
					BizAccountBO custAcc = transferAccountsBS.getAccountBO(custId);//客户账号
					BizAccountBO pmczAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMCZ);//平台提现账号
					
					BigDecimal amt = new BigDecimal(paymentInfoBO.getAmount());
					
					//充值pm充值账号
					transferAccountsBS.oneSidedTransferAccount(txNO, AppConst.TRADE_TYPE_RECHARGE_BANK, TransferAccountsBSImpl.AccountDirectionEnum.ADD, pmczAccountBO, amt);
					
					//从 平台提现账号  转账到 客户账号
					transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_RECHARGE_BANK, pmczAccountBO, custAcc, amt);
					
				}else{
					log.error("订单号为"+billNo+"已经通知成功！");
					return;
				}
			}else{//支付失败
				paymentInfoBO.setStatus(AppConst.PAY_STATUS_FAILURE);
			}
			getHibernateTemplate().update(paymentInfoBO);
			if(AppConst.PAY_SUCCESS.equals(status)){
				try {
					BizAccountBO acc = transferAccountsBS.getAccountBO(custId);
					BizCustomerBO cust = custInfoBS.findCustById(custId);
					Map<String,String> dataMap = new HashMap<String,String>();
					dataMap.put("DATE", DateUtils.getYmdhms(new Date()));//对应模板的${date}
					dataMap.put("AMT", NumberUtils.format2(new BigDecimal(paymentInfoBO.getAmount())));//对应模板的${amt}
					dataMap.put("BALANCE", NumberUtils.format2(acc.getUsableBalAmt()));
					dataMap.put("CUSTNAME", cust.getCustName());
					msgTemplateBS.sendMsg(AppConst.EVENTTYPE_RECHARGE, cust, dataMap);
				} catch (Exception e) {
						log.error("充值短信发送失败"+e.getMessage());
				}
			}
			
		}catch(Exception e){
			log.error("订单号为"+billNo+"处理失败",e);
			throw new BizException("订单号为"+billNo+"处理失败");
		}
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void rechargeCard(RechargeVO vo, int custId) throws BizException {
		// TODO Auto-generated method stub
		try {
			if(null == vo || null== vo.getRechargeType() || vo.getRechargeType().length()<=0){
				throw new BizException("充值功能--传入参数不能为空");
			}
			this.payForCard(vo,custId);
		} catch (Exception e) {
			log.error("充值失败：",e);
			throw new BizException("充值失败："+e.getMessage());
		}
	}
	

	

	/**
	 * 理财卡充值
	 * @param vo
	 */
	private void payForCard(RechargeVO vo,int custId) throws BizException {
		String mess = this.validateVO(vo);
		if(null!=mess&&mess.length()>0){
			throw new BizException("理财卡信息校验失败："+mess);
		}
		vo.setPassword(MD5Utils.getMD5Str(vo.getPassword()));
		List<String> statusList = new ArrayList<String>();
		statusList.add(AppConst.RECHARGE_STATUS_INIT);
		statusList.add(AppConst.RECHARGE_STATUS_EXPORT);
		List<BizRechargeCardBO> boList = this.rechargePayoutDAO.queryBOByParams(vo,statusList);
		if(null == boList || boList.size()<=0){
			throw new BizException("理财卡信息不存在,请确认信息输入是否正确!");
		}
		BizRechargeCardBO bo = boList.get(0);
		
		String txNO =  seqBS.getResultNum(AppConst.TXNO);//获取交易编号
		
		BizAccountBO custAcc = transferAccountsBS.getAccountBO(custId);//客户账号
		BizAccountBO pmczAccountBO = transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMCZ);//平台提现账号
		
		
		//充值pm充值账号
		transferAccountsBS.oneSidedTransferAccount(txNO, AppConst.TRADE_TYPE_RECHARGE, TransferAccountsBSImpl.AccountDirectionEnum.ADD, pmczAccountBO, bo.getAmt());
		
		//从客户账号转账到平台提现账号
		transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_RECHARGE, pmczAccountBO, custAcc, bo.getAmt());
		
		// 更新理财卡信息
		bo.setAccount(vo.getAccount());
		bo.setAccountId(vo.getAccountID());
		bo.setRechargeDate(new Date());
		bo.setStatus(AppConst.RECHARGE_STATUS_USE);
		this.rechargePayoutDAO.update(bo);
		
	}
	

}
