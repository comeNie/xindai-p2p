package com.qfw.dao.transaction.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.batch.bizservice.util.DateUtil;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.CollectionUtil;
import com.qfw.dao.transaction.ITradeDAO;
import com.qfw.model.vo.transaction.TradeResponseVO;
import com.qfw.model.vo.transaction.TradeVO;

@Service("tradeDAO")
public class TradeDAOImpl extends BaseDAOImpl implements ITradeDAO {

	@Autowired
	private ICommonQuery commonQuery;
	
	@SuppressWarnings("unchecked")
	@Override
	public TradeResponseVO findInfoByID(Integer id) {
		TradeVO vo = new TradeVO();
		vo.setId(id);
		List<TradeResponseVO> list = this.commonQuery.findObjects(this.assembleSQL(vo), TradeResponseVO.class);
		if(null!=list&&list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public int findCountByVO(TradeVO vo) throws BizException {
		return commonQuery.findCountByWapSQL(this.assembleSQL(vo), null);
	}
	@Override
	public BigDecimal sumByVO(TradeVO vo){
		StringBuffer str = new StringBuffer();
		str.append(" select sum(tra.TRADE_AMT) as TRADE_AMT");
		str.append(" from biz_trade tra , biz_account acc,biz_customer cust");
		str.append(" where 1=1");
		str.append("  and tra.ACCOUNT_NUM = acc.ACCOUNT ");
		str.append("  and acc.CUST_ID = cust.ID");
		str.append(" and ACCOUNT_NUM <> 'sjct'");
		if(null != vo.getId() && vo.getId() != 0 ){
			str.append(" and tra.id = '").append(vo.getId()).append("'");
		}
		if(null != vo.getCustName() && vo.getCustName().length()>0 ){
			str.append(" and cust.CUST_NAME like '%").append(vo.getCustName()).append("%'");
		}
		if(null != vo.getTradeTypeCd() && vo.getTradeTypeCd().length()>0){
			str.append(" and tra.TRADE_TYPE_CD = '").append(vo.getTradeTypeCd()).append("'");
		}
		if(null != vo.getStartDate()  ){
			str.append(" and tra.TRADA_TIME >= '").append(DateUtil.getYmdhms(vo.getStartDate())).append("'");
		}
		if(null != vo.getEndDate()  ){
			str.append(" and tra.TRADA_TIME <= '").append(DateUtil.getYmdhms(vo.getEndDate())).append("'");
		}
		if(null != vo.getComment() &&vo.getComment().length()>0 ){
			str.append(" and tra.COMMENT = '").append(vo.getComment()).append("'");
		}
		List<?> sum = commonQuery.findBySQL(str.toString());
		if(CollectionUtil.isEmpty(sum)){
			return new BigDecimal(0);
		}else{
			return (BigDecimal)((Map)sum.get(0)).get("TRADE_AMT");
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeResponseVO> findPageListByVO(TradeVO tradeVO, int first,
			int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(this.assembleSQL(tradeVO), first, pageSize,TradeResponseVO.class);
	}

	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String assembleSQL(TradeVO vo){
		StringBuffer str = new StringBuffer();
		str.append(" select * from (");
		str.append(" select tra.ID,tra.ACCOUNT_BAL,tra.ACCOUNT_NUM,tra.`COMMENT`,tra.TRADA_TIME,tra.TRADE_AMT,");
		str.append(" tra.TRADE_NUM,tra.TRADE_TYPE_CD,cust.CUST_NAME,sysuser.USER_CODE,sysuser.USER_NAME");
		str.append(" from biz_trade tra , biz_account acc,biz_customer cust ,sys_user sysuser");
		str.append(" where cust.USER_ID = sysuser.USER_ID");
		str.append("  and tra.ACCOUNT_NUM = acc.ACCOUNT ");
		str.append("  and acc.CUST_ID = cust.ID");
		str.append(" and ACCOUNT_NUM <> 'sjct'");
		if(null != vo.getId() && vo.getId() != 0 ){
			str.append(" and tra.id = '").append(vo.getId()).append("'");
		}
		if(null != vo.getUserCode() && vo.getUserCode().length()>0 ){
			str.append(" and sysuser.USER_CODE like '%").append(vo.getUserCode()).append("%'");
		}
		if(null != vo.getCustName() && vo.getCustName().length()>0 ){
			str.append(" and cust.CUST_NAME like '%").append(vo.getCustName()).append("%'");
		}
		if(null != vo.getUserName() && vo.getUserName().length()>0 ){
			str.append(" and sysuser.USER_NAME like '%").append(vo.getUserName()).append("%'");
		}
		if(null != vo.getTradeTypeCd() && vo.getTradeTypeCd().length()>0){
			str.append(" and tra.TRADE_TYPE_CD = '").append(vo.getTradeTypeCd()).append("'");
		}
		if(null != vo.getStartDate()  ){
			str.append(" and tra.TRADA_TIME >= '").append(DateUtil.getYmdhms(vo.getStartDate())).append("'");
		}
		if(null != vo.getEndDate()  ){
			str.append(" and tra.TRADA_TIME <= '").append(DateUtil.getYmdhms(vo.getEndDate())).append("'");
		}
		if(null != vo.getComment() &&vo.getComment().length()>0 ){
			str.append(" and tra.COMMENT = '").append(vo.getComment()).append("'");
		}
		str.append(" order by tra.TRADA_TIME desc) temp where 1=1");
		return str.toString();
	}
	
}
