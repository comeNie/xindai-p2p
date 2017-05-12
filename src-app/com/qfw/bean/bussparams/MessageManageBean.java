package com.qfw.bean.bussparams;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.bussparams.IMessageInfoBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.bussparams.LazyMessageDataModel;

@ViewScoped
@ManagedBean(name="messageManageBean")
public class MessageManageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;

	private SysMessageTemplate searchMessageVO = new SysMessageTemplate();
	
	private LazyDataModel<SysMessageTemplate> messageList;
	
	@ManagedProperty(value = "#{messageInfoBS}")
	private IMessageInfoBS messageInfoBS;
	
	private SysMessageTemplate selectMessage;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@PostConstruct
    public void init(){
		searchMessage();
	} 
	
	public void searchMessage() {
		try {
			super.search();
			messageList = new LazyMessageDataModel(searchMessageVO, messageInfoBS);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag) || "edit".equalsIgnoreCase(operateFlag)){
			ViewOper.getSession().setAttribute("id", selectMessage.getId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			String messageId = String.valueOf(selectMessage.getId());
			String sql = "delete from SYS_MESSAGE_TEMPLATE where id = '"+messageId+"'";
			try {
				messageInfoBS.getJdbcTemplate().execute(sql);
				messageList.setRowCount(messageList.getRowCount()-1);
			} catch (Exception e) {
				log.error("删除短信配置"+selectMessage.getMsgTmpName()+"异常：", e);
				alert("删除短信配置异常："+ e);
			}
		}
	}

	public SysMessageTemplate getSearchMessageVO() {
		return searchMessageVO;
	}

	public void setSearchMessageVO(SysMessageTemplate searchMessageVO) {
		this.searchMessageVO = searchMessageVO;
	}

	public LazyDataModel<SysMessageTemplate> getMessageList() {
		return messageList;
	}

	public void setMessageList(LazyDataModel<SysMessageTemplate> messageList) {
		this.messageList = messageList;
	}

	public IMessageInfoBS getMessageInfoBS() {
		return messageInfoBS;
	}

	public void setMessageInfoBS(IMessageInfoBS messageInfoBS) {
		this.messageInfoBS = messageInfoBS;
	}

	public SysMessageTemplate getSelectMessage() {
		return selectMessage;
	}

	public void setSelectMessage(SysMessageTemplate selectMessage) {
		this.selectMessage = selectMessage;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
