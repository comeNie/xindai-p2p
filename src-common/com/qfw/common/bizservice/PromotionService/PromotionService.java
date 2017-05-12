package com.qfw.common.bizservice.PromotionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.bankcard.IBankCardBS;
import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.PmService.PmService;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.dao.PromotionDao.PromotionDao;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.BizPromotDetailBO;
import com.qfw.common.model.vo.CustInfoVo;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.bankcard.IBankCardDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCardBO;


@Service("promotionService")
public class PromotionService extends BaseServiceImpl {
	
	@Autowired
	private PromotionDao promotionDao;
	
	@Autowired
	private IParamBS iParam;
	
	@Autowired
	public ITransferAccountsBS transferAccountsBS; 
	
	@Autowired
	public ISeqBS seqBS;
	
	@Resource(name = "pmService")
	private PmService pmService;
	
	@Autowired
	private IBankCardDAO cardDAO;
	
	@Resource(name = "bankCardBS")
	private IBankCardBS bankCardBS;
	
	@Autowired
	private IUserBS userBS;
	/**
	 * 获取推广人信息
	 * @param cust_id
	 * @return
	 */
	public CustInfoVo getReferees(Integer USER_ID){
		StringBuilder buffer = new StringBuilder(
				"select a.USER_ID, " +
				"	   a.ACCOUNT_ID, " +
				"	   a.CUST_ID, " +
				"	   a.USER_CODE, " +
				"	   a.USER_NAME, " +
				"	   a.PASSANSWER, " +
				"	   b.USER_ID as ref_user_id, " +
				"	   b.CUST_ID as ref_cust_id, " +
				"      b.ACCOUNT_ID as ref_account_id, " +
				"      b.USER_CODE as ref_user_code, " +
				"      b.USER_NAME as ref_user_name, " +
				"      b.Introduction as ref_introduction, " +
				"      b.PASSQUESTION as ref_PASSQUESTION " +
				" from user_info a left join user_info b " +
				" ON a.user_referee = b.user_code " +
				" where a.USER_ID = ?" 
		);
		CustInfoVo custInfoVo = new CustInfoVo();
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1,new Object[]{USER_ID},CustInfoVo.class);
		if (list != null && list.size() > 0) {
			custInfoVo = (CustInfoVo)list.get(0);
		}
		return custInfoVo;
	}
	
	
	public List<BizPromotDetailBO> getListByCustId(Integer cust_id,int requestPage,int pageSize) throws BizException {
		StringBuilder buffer = new StringBuilder(
				"select id, " +
		        "		CUST_ID, " +
			    "		USER_CODE, "  +
			    "		BY_CUST_ID, "  + 
			    "		BY_USER_CODE, "  +    
			    "		BY_USER_NAME, "  +    
			    "		PROMOT_TYPE_CD, "  +  
			    "		TX_DATE, "  +         
			    "		PROMOT_AMT, "  +      
			    "		SYS_CREATE_USER, "  + 
			    "		SYS_CREATE_TIME, "  + 
			    "		SYS_UPDATE_USER, "  + 
			    "		SYS_UPDATE_TIME "  +  
			    "from biz_promot_detail a "  +
			    "where a.CUST_ID = ? "        
				);
		
		List<BizPromotDetailBO> list = commonQuery.findBySQLByPages(buffer.toString(),(requestPage-1)*pageSize, pageSize,new Object[]{cust_id},BizPromotDetailBO.class);
	
		return list;
	}
	 
	public int getCountByCustId(Integer cust_id)  throws BizException {
		StringBuilder buffer = new StringBuilder(
				"select id, " +
		        "		CUST_ID, " +
			    "		USER_CODE, "  +
			    "		BY_CUST_ID, "  + 
			    "		BY_USER_CODE, "  +    
			    "		BY_USER_NAME, "  +    
			    "		PROMOT_TYPE_CD, "  +  
			    "		TX_DATE, "  +         
			    "		PROMOT_AMT, "  +      
			    "		SYS_CREATE_USER, "  + 
			    "		SYS_CREATE_TIME, "  + 
			    "		SYS_UPDATE_USER, "  + 
			    "		SYS_UPDATE_TIME "  +  
			    "from biz_promot_detail a "  +
			    "where a.CUST_ID = ? "        
				);
		
		return commonQuery.findCountByWapSQL(buffer.toString(), new Object[]{cust_id});
	}
	
	public boolean isNotPutOutPromotAmt(Integer cust_id,Integer by_Cust_id) throws BizException {
		StringBuilder buffer = new StringBuilder(
				"select id, " +
		        "		CUST_ID, " +
			    "		USER_CODE, "  +
			    "		BY_CUST_ID, "  + 
			    "		BY_USER_CODE, "  +    
			    "		BY_USER_NAME, "  +    
			    "		PROMOT_TYPE_CD, "  +  
			    "		TX_DATE, "  +         
			    "		PROMOT_AMT, "  +      
			    "		SYS_CREATE_USER, "  + 
			    "		SYS_CREATE_TIME, "  + 
			    "		SYS_UPDATE_USER, "  + 
			    "		SYS_UPDATE_TIME "  +  
			    "from biz_promot_detail a "  +
			    "where a.CUST_ID = ? and a.BY_CUST_ID = ? "        
				);
		
		List list = commonQuery.findBySQLByPages(buffer.toString(),0, 1,new Object[]{cust_id,by_Cust_id},BizPromotDetailBO.class);
		
		if(CollectionUtil.isEmpty(list)){
			return true;
		}else{
			return false;
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void addBankCard(Integer user_id,BizCardBO cardBO) throws BizException{
		//保存银行卡
		bankCardBS.save(cardBO);
		//发放奖金
		//TODO
//		promIncome(user_id);
	}
	
	
	
	/**
	 * 发放推广奖金及pm币
	 * @param user_id登陆号
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void promIncome(Integer user_id) throws BizException{
		CustInfoVo custInfoVo = this.getReferees(user_id);
		if(custInfoVo == null){
			throw new BizException("找不到该会员");
		}
		//System.out.println("custInfoVo.getPassanswer()==="+custInfoVo.getPassanswer());
		String put_out_status =  custInfoVo.getPassanswer().substring(0, 1);
		//System.out.println("put_out_status==="+put_out_status);
		
		if(null != custInfoVo.getRefCustId() && 0 != custInfoVo.getRefCustId() ){
			toAddPromotLevel(custInfoVo);
		}
		
		
		if(null != custInfoVo.getRefCustId() && 0 != custInfoVo.getRefCustId() && "0".equals(put_out_status) ){
			if(StringUtils.isEmpty(StringUtils.convertNull(custInfoVo.getRefPassquestion()))){
				custInfoVo.setRefPassquestion(AppConst.PROMOT_LEVEL0);
			}
			
			//System.out.println("custInfoVo.getRefPassquestion()=="+custInfoVo.getRefPassquestion());
			 //现金奖励开关
			  String promotIsOpen = iParam.getParam("PROM_REG_BANK_ISOPEN");
			  if(AppConst.IS_OPEN.equals(promotIsOpen)){
				  //根据推广等级获取推广奖金
				  BigDecimal promotValue = getPromotValue(custInfoVo.getRefPassquestion());
				  //发放推广奖金
				  insertPromotionTrate(custInfoVo,promotValue);
				  //更新发放状态
				  String passanswer = replaceIndex(1,custInfoVo.getPassanswer(),"1") ;
				  //System.out.println("passanswer=="+passanswer);
				  userBS.updateUserOfpassanswer(passanswer, custInfoVo.getUserId());
			  }
			  
			  //pm币奖励开关
			  String pmIsOpen = iParam.getParam("PM_REG_BANK_ISOPEN");
			  if(AppConst.IS_OPEN.equals(pmIsOpen)){
				  //获取推广奖励PM币
				  BigDecimal pmValue = pmService.getPmValue(custInfoVo.getRefPassquestion());
				  //发放推广奖励pm币
				  pmService.insertPmTrate(AppConst.PM_TYPE_CARD_AUT, custInfoVo.getRefAccountId(), pmValue);
			  }
		}else{
			//System.out.println("不需要发放奖金");
		}
		
	}
	/**
	 * 替换指定位置的字符创
	 * @param index 位置
	 * @param res 原字符串
	 * @param str 替换的字符串
	 * @return
	 */
	public static String replaceIndex(int index,String res,String str){
		return res.substring(0, index-1)+str+res.substring(index);
	}
	
	/**
	 * 推广成功奖金
	 * @param cust_id
	 * @param promotAmt
	 * @throws BizException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void insertPromotionTrate(CustInfoVo custInfoVo,BigDecimal promotAmt) throws BizException{
		//推广奖金大于零，才做如下操作！
		if(promotAmt.compareTo(new BigDecimal(0)) == 1 ){
			String txNO = seqBS.getResultNum(AppConst.TXNO);// 获取交易编号
			// 调用账户管理(转账服务) 【平台收益账户】投资金转入【会员账户】
			transferAccountsBS.transferAccount(txNO, AppConst.TRADE_TYPE_PROMOTION,
					transferAccountsBS.getPMAccountBO(AppConst.ACCOUNT_TYPE_PMSY),
					transferAccountsBS.getAccountBO(custInfoVo.getRefCustId()),promotAmt);
			
			insertPromotionDetail(custInfoVo,promotAmt,custInfoVo.getRefUserName()+"推荐"+custInfoVo.getUserName()+"注册，获得的奖励。");
		}
	}
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void insertPromotionDetail(CustInfoVo custInfoVo,BigDecimal promotAmt,String txRemark) throws BizException{
		BizPromotDetailBO bizPromotDetailBO = new BizPromotDetailBO();
		bizPromotDetailBO.setCustId(custInfoVo.getRefCustId());
		bizPromotDetailBO.setUserCode(custInfoVo.getRefUserCode() );
		bizPromotDetailBO.setByCustId(custInfoVo.getCustId());
		bizPromotDetailBO.setByUserCode(custInfoVo.getUserCode());
		bizPromotDetailBO.setByUserName(custInfoVo.getUserName());
		bizPromotDetailBO.setPromotTypeCd(AppConst.PROMOT_TYPE_REG);
		bizPromotDetailBO.setTxDate(new Date());
		bizPromotDetailBO.setPromotAmt(promotAmt);
		bizPromotDetailBO.setTxRemark(txRemark);
		bizPromotDetailBO.setSysCreateUser(custInfoVo.getUserId());//自动化操作
		bizPromotDetailBO.setSysUpdateUser(custInfoVo.getUserId());
		bizPromotDetailBO.setSysCreateTime(new Date());
		bizPromotDetailBO.setSysUpdateTime(new Date());
		promotionDao.save(bizPromotDetailBO);
	}
	
	
	/**
	 * 计算奖金方法
	 * @param promLevel 推广等级
	 * @return
	 * @throws BizException 
	 */
	public BigDecimal getPromotValue(String promLevel) throws BizException{
		
		  if(null == promLevel || "".equals(promLevel) ){
			  throw new BizException("会员推荐等级不能为空！");
		  }
		  //根据推广等级获取Pm值
		  String promotValue = iParam.getParam(promLevel+"_AMT");
		  if(null == promotValue && "".equals(promotValue) ){
			 throw new BizException("未找到奖励措施！！");
		  }
		  return new BigDecimal(promotValue);
	}
	
	//推广人等级
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void toAddPromotLevel(CustInfoVo custInfoVo) throws BizException{
		
		//System.out.println("custInfoVo.getRefUserCode()==="+custInfoVo.getRefUserCode());
		if(StringUtils.isNotEmpty(custInfoVo.getRefUserCode())){
			//获取推荐个数
			int oldCount = new Integer(custInfoVo.getRefIntroduction()).intValue();
			int newCount = oldCount + 1;
			//System.out.println("newCount==="+newCount);
			//根据推荐个数，获取推荐等级
			String promotLevel =  getPromotLevleByCount(newCount);
			userBS.updateUserOfPromotLevel(promotLevel, custInfoVo.getRefUserId());
		}else{
			//System.out.println("推荐人未找到！");
		}
	}
	
	
	/**
	 * 获取推广人信息
	 * @param cust_id
	 * @return
	 */
	public CustInfoVo getRefereesByRefUserCode(String refUserCode){
		StringBuilder buffer = new StringBuilder(
				"select b.USER_ID as ref_user_id, " +
				"	   	b.CUST_ID as ref_cust_id, " +
				"      	b.ACCOUNT_ID as ref_account_id, " +
				"      	b.USER_CODE as ref_user_code, " +
				"      	b.USER_NAME as ref_user_name, " +
				"      	b.Introduction as ref_introduction, " +
				"      	b.PASSQUESTION as ref_PASSQUESTION " +
				" from user_info b " +
				" where b.USER_CODE = ?" 
		);
		CustInfoVo custInfoVo = new CustInfoVo();
		List list = commonQuery.findBySQLByPages(buffer.toString(), 0, 1,new Object[]{refUserCode},CustInfoVo.class);
		if (list != null && list.size() > 0) {
			custInfoVo = (CustInfoVo)list.get(0);
		}
		return custInfoVo;
	}
	
		//根据推荐个数获取去推荐等级
		public String getPromotLevleByCount(Integer promotCount){
			
			Integer LEVEL1_COUNT = new Integer(iParam.getParam("LEVEL1_COUNT"));
			Integer LEVEL2_COUNT = new Integer(iParam.getParam("LEVEL2_COUNT")); 
			
			if(promotCount.compareTo(LEVEL2_COUNT) >= 0){
				return "LEVEL2";
			}else if(promotCount.compareTo(LEVEL1_COUNT)>=0){
				return "LEVEL1";
			}else{
				return "LEVEL0";
			}
		}
	
	
	public PromotionDao getPromotionDao() {
		return promotionDao;
	}

	public void setPromotionDao(PromotionDao promotionDao) {
		this.promotionDao = promotionDao;
	}
	
	public IParamBS getiParam() {
		return iParam;
	}


	public void setiParam(IParamBS iParam) {
		this.iParam = iParam;
	}


	public ITransferAccountsBS getTransferAccountsBS() {
		return transferAccountsBS;
	}


	public void setTransferAccountsBS(ITransferAccountsBS transferAccountsBS) {
		this.transferAccountsBS = transferAccountsBS;
	}


	public ISeqBS getSeqBS() {
		return seqBS;
	}


	public void setSeqBS(ISeqBS seqBS) {
		this.seqBS = seqBS;
	}

	
	
}
