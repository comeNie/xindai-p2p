package com.qfw.dao.message.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.loan.ILoanApplyDAO;
import com.qfw.dao.message.IMessageManageDAO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.bo.BizMessageConfBO;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.model.vo.loan.LoanApplyVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;

/**
 * 信息dao
 *
 * @author jiyb
 */
@Repository("messageManageDAO")
public class MessageManageDAOImpl extends BaseDAOImpl implements IMessageManageDAO {
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Autowired
	private ICommonQuery commonQuery;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<RecMessageVO> findInfoPagesByRecVO(Integer userId,Integer messageType,int first,
			int pageSize) throws BizException {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(this.recSQL(userId,messageType));
		
		
		return this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,RecMessageVO.class);
		
	}
	
	
	@Override
	public List<SendMessageVO> findInfoPagesBySendVO(Integer userId, int first,
			int pageSize) throws BizException {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(this.sendSQL(userId));
		return this.commonQuery.findBySQLByPages(sql.toString(), first, pageSize,SendMessageVO.class);
		
	}
	
	
	@Override
	public int findMyMessageCnt(Integer userId) throws BizException {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select * from biz_message_info where readStatus=0 and recuserId=" + userId);
		
		return this.commonQuery.findCountByWapSQL(sql.toString());
		
	}
	
	
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String recSQL(Integer userId,Integer messageType){
		
		StringBuffer str = new StringBuffer();
		str.append(" select msginfo.id as msgId , ");
		str.append(" msginfo.senduserId, senduser.user_code as sendusername,");
		str.append(" msginfo.recuserId, recuser.user_code as recusername,");
		str.append(" msginfo.contentTxt,msginfo.titleTxt,msginfo.readStatus, msginfo.messageType,");
		str.append(" msginfo.SYS_CREATE_TIME as sendTime , msginfo.SYS_UPDATE_TIME as updateTime");
		str.append(" from biz_message_info msginfo ");
		str.append(" left join sys_user senduser on msginfo.senduserId=senduser.user_id");
		str.append(" left join sys_user recuser on msginfo.recuserId=recuser.user_id");
		str.append(" where 1=1 ");
		str.append(" and  msginfo.recuserId=" + userId + " ");
		str.append(" and  msginfo.messageType=" + messageType + " ");
		str.append(" order by msginfo.SYS_CREATE_TIME desc");
		return str.toString();
		
	}
	
	
	/**
	 * 组装查询sql
	 * @param searchVO
	 * @return
	 */
	private String sendSQL(Integer userId){
		StringBuffer str = new StringBuffer();
		str.append(" select msginfo.id as msgId , ");
		str.append(" msginfo.senduserId, senduser.user_code as sendusername,");
		str.append(" msginfo.recuserId, recuser.user_code as recusername,");
		str.append(" msginfo.contentTxt,msginfo.titleTxt,msginfo.readStatus, msginfo.messageType,");
		str.append(" msginfo.SYS_CREATE_TIME as sendTime , msginfo.SYS_UPDATE_TIME as updateTime");
		str.append(" from biz_message_info msginfo ");
		str.append(" left join sys_user senduser on msginfo.senduserId=senduser.user_id");
		str.append(" left join sys_user recuser on msginfo.recuserId=recuser.user_id");
		str.append(" where 1=1 ");
		str.append(" and  msginfo.senduserId=" + userId + " ");
		str.append(" order by msginfo.SYS_CREATE_TIME desc");
		return str.toString();
	}
	
	
	
	public int[] saveOrUpdateMessageConf(List<BizMessageConfBO> addBoList,List<BizMessageConfBO> updateBoList) 
			throws BizException{
		int addCnt = addBoList==null?0:addBoList.size();
		int updateCnt = updateBoList==null?0:updateBoList.size();
		int sqlCnt = addCnt + updateCnt;
		String[] sql = new String[sqlCnt];
		
		String tempSql;
		int index = 0;
		if( addBoList!=null ){
			for(BizMessageConfBO bo : addBoList){
				tempSql = "insert into BIZ_MESSAGE_CONF" +
						"(CUST_ID,MSG_TYPE_CD,EVENT_TYPE,IS_ENABLE,SYS_CREATE_USER,SYS_CREATE_TIME,SYS_UPDATE_USER,SYS_UPDATE_TIME) " +
						" values ( " +
						bo.getCustId() + ",'" + bo.getMsgTypeCd() + "'," +
						"'" + bo.getEventType() + "','" + bo.getIsEnable() +  "'," + 
						bo.getSysCreateUser() + ",'" + bo.getSysCreateTime() + "'," + 
						bo.getSysUpdateUser() + ",'" + bo.getSysUpdateTime() + "'" + 
						" )";
				sql[index++] = tempSql;
			}
		}
		
		if( updateBoList!=null ){
			for(BizMessageConfBO bo : updateBoList){
				tempSql = "update BIZ_MESSAGE_CONF " +
						" set IS_ENABLE='" + bo.getIsEnable() + "'" +
						" where id=" + bo.getId();
				sql[index++] = tempSql;
			}
			
		}
		
		
	    return jdbcTemplate.batchUpdate(sql);
	}
	
	
}
