package com.qfw.bizservice.bussparams.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.bussparams.IMessageInfoBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.bussparams.IMessageInfoDAO;

@Service("messageInfoBS")
public class MessageInfoBSImpl extends BaseServiceImpl implements IMessageInfoBS {
	
	@Autowired
	private IMessageInfoDAO messageInfoDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public List<SysMessageTemplate> findMessageInfo (SysMessageTemplate searchMessageInfoVO) throws BizException {
		return this.messageInfoDAO.findMessageInfo(searchMessageInfoVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMessageTemplate> findMessageInfoPagesByVO(
			SysMessageTemplate searchMessageInfoVO, int first, int pageSize) {
		try {
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
			return commonQuery.findBySQLByPages(sb.toString(), first, pageSize, SysMessageTemplate.class);
		} catch (Exception e) {
			 
		}
		return null;
	}

	@Override
	public int findMessageInfoCountByVO(SysMessageTemplate searchMessageInfoVO) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1)");
		sb.append(" from SYS_MESSAGE_TEMPLATE s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchMessageInfoVO.getMsgTmpCode())) {
			sb.append(" and s.MSG_TMP_CODE like '%").append(searchMessageInfoVO.getMsgTmpCode()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchMessageInfoVO.getMsgTmpName())) {
			sb.append(" and s.MSG_TMP_NAME like '%").append(searchMessageInfoVO.getMsgTmpName()).append("%'");
		}
		return this.commonQuery.findCountBySQL(sb, null);
	}

	@Override
	public SysMessageTemplate findMessageInfoById(Integer id) throws BizException {
		this.messageInfoDAO.findById(SysMessageTemplate.class,id);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<SysMessageTemplate> checkMessageCode(String msgTmpCode,Integer id) throws BizException{
		if(msgTmpCode != null && !"".equals(msgTmpCode)){
			StringBuilder sb = new StringBuilder("from SysMessageTemplate where ");
			sb.append(" msgTmpCode = '").append(msgTmpCode).append("'");
			if(id!=null && !id.equals(Integer.valueOf(0))){
				sb.append(" and id != '").append(id).append("'");
			}
			List<SysMessageTemplate> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SysMessageTemplate> checkMessageName(String msgTmpName,Integer id)throws BizException{
		if(msgTmpName != null && !"".equals(msgTmpName)){
			StringBuilder sb = new StringBuilder("from SysMessageTemplate where ");
			sb.append(" msgTmpName = '").append(msgTmpName).append("'");
			if(id!=null && !id.equals(Integer.valueOf(0))){
				sb.append(" and id != '").append(id).append("'");
			}
			List<SysMessageTemplate> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public SysMessageTemplate getSysMessageTemplateById(Integer id) throws BizException{
		return (SysMessageTemplate) messageInfoDAO.findById(SysMessageTemplate.class, id);
	}

	public IMessageInfoDAO getMessageInfoDAO() {
		return messageInfoDAO;
	}

	public void setMessageInfoDAO(IMessageInfoDAO messageInfoDAO) {
		this.messageInfoDAO = messageInfoDAO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
