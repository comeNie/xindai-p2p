package com.qfw.model.vo.message;

import java.math.BigDecimal;
import java.util.Date;

public class MessageBoardVO {

	private Integer msgid; // 信息ID
	private Integer loadId;//标的ID
	private Integer remainUser;//留言人userID
	private String remainUserName;//留言人usercode
	private Integer replyUser;//回复人userID
	private String replyUserName;//回复人usercode
	private String content;//留言内容
	
	private Date sendtime;//发送时间
	private Date updatetime;//最新更新时间
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
	public Integer getLoadId() {
		return loadId;
	}
	public void setLoadId(Integer loadId) {
		this.loadId = loadId;
	}
	public Integer getRemainUser() {
		return remainUser;
	}
	public void setRemainUser(Integer remainUser) {
		this.remainUser = remainUser;
	}
	public String getRemainUserName() {
		return remainUserName;
	}
	public void setRemainUserName(String remainUserName) {
		this.remainUserName = remainUserName;
	}
	public Integer getReplyUser() {
		return replyUser;
	}
	public void setReplyUser(Integer replyUser) {
		this.replyUser = replyUser;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}
