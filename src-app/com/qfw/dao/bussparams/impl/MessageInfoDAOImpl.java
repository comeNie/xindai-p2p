package com.qfw.dao.bussparams.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.bussparams.IMessageInfoDAO;

@Repository("messageInfoDAO")
public class MessageInfoDAOImpl extends BaseDAOImpl implements IMessageInfoDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMessageTemplate> findMessageInfo(SysMessageTemplate searchMessageInfoVO) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.ID,s.MSG_TMP_CODE,s.MSG_TMP_NAME,");
		sb.append(" s.MSG_TMP_SUBJECT,s.MSG_TMP_CONTENT,s.MSG_TMP_TYPE_CD");
		sb.append(" from SYS_MESSAGE_TEMPLATE s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchMessageInfoVO.getMsgTmpCode())) {
			sb.append(" and s.MSG_TMP_CODE like '%").append(searchMessageInfoVO.getMsgTmpCode()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchMessageInfoVO.getMsgTmpName())) {
			sb.append(" and s.MSG_TMP_NAME like '%").append(searchMessageInfoVO.getMsgTmpName()).append("%'");
		}
		sb.append(" order by s.MSG_TMP_CODE");
		List<SysMessageTemplate> messageInfoList = this.findByHQL(sb.toString());
		return messageInfoList;
	}
	
}
