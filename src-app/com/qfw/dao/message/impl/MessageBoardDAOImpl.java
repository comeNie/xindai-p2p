package com.qfw.dao.message.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.loan.ILoanApplyDAO;
import com.qfw.dao.message.IMessageBoardDAO;
import com.qfw.dao.message.IMessageManageDAO;
import com.qfw.model.bo.BizLoanApplyBO;
import com.qfw.model.bo.BizMessageBoardBO;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.model.vo.loan.LoanApplyVO;
import com.qfw.model.vo.message.MessageBoardVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;

/**
 * 信息dao
 * 
 * @author jiyb
 */
@Repository("messageBoardDAO")
public class MessageBoardDAOImpl extends BaseDAOImpl implements
		IMessageBoardDAO {

	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Autowired
	private ICommonQuery commonQuery;

	@Override
	public List<MessageBoardVO> findInfoPagesByLoanId(Integer loadId,
			int first, int pageSize) throws BizException {

		StringBuffer sql = new StringBuffer();

		sql.append(this.mainSQL(loadId));

		return this.commonQuery.findBySQLByPages(sql.toString(), first,
				pageSize, MessageBoardVO.class);

	}

	@Override
	public List<MessageBoardVO> findInfoPagesByLinkId(Integer linkId,
			int first, int pageSize) throws BizException {

		StringBuffer sql = new StringBuffer();

		sql.append(this.subSQL(linkId));

		return this.commonQuery.findBySQLByPages(sql.toString(), first,
				pageSize, MessageBoardVO.class);

	}

	/**
	 * 组装查询sql
	 * 
	 * @param searchVO
	 * @return
	 */
	private String mainSQL(Integer loadId) {

		StringBuffer str = new StringBuffer();

		str.append(" select bmb.id as  msgId.");
		str.append(" 	loanid as load_id, ");
		str.append(" 	remianuser as remain_user, remuser.user_code as remain_user_name, ");
		str.append(" 	replyuser as reply_user, repuser.user_code as reply_user_name, ");
		str.append("    bmb.SYS_CREATE_TIME as sendTime , bmb.SYS_UPDATE_TIME as updateTime");
		str.append(" 	from biz_message_board bmb ");
		str.append(" 	left join sys_user remuser on bmb.remainUser=remuser.user_id ");
		str.append(" 	left join sys_user repuser on bmb.replyUser=repuser.user_id ");
		str.append(" 	where loanid=" + loadId);
		
		str.append(" order by bmb.sys_create_time asc");
		return str.toString();

	}

	/**
	 * 组装查询sql
	 * 
	 * @param searchVO
	 * @return
	 */
	private String subSQL(Integer linkId) {
		StringBuffer str = new StringBuffer();
		str.append(" select bmb.id as  msgId.");
		str.append(" 	loanid as load_id, ");
		str.append(" 	remianuser as remain_user, remuser.user_code as remain_user_name, ");
		str.append(" 	replyuser as reply_user, repuser.user_code as reply_user_name, ");
		str.append("    bmb.SYS_CREATE_TIME as sendTime , bmb.SYS_UPDATE_TIME as updateTime");
		str.append(" 	from biz_message_board bmb ");
		str.append(" 	left join sys_user remuser on bmb.remainUser=remuser.user_id ");
		str.append(" 	left join sys_user repuser on bmb.replyUser=repuser.user_id ");
		str.append(" 	where linkid=" + linkId);

		str.append(" order by bmb.sys_create_time asc");
		return str.toString();
	}

}
