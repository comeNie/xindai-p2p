package com.qfw.model.vo.message;

import java.math.BigDecimal;
import java.util.Date;

public class SendMessageVO {

	private Integer msgid; // 信息ID
	private Integer senduserid;//发送人ID
	private String sendusername;//发送人名字
	private Integer recuserid;//接收人ID
	private String recusername;//接送人名字
	private String contenttxt;//信息内容
	private String titletxt;//信息内容
	private Integer messagetype;//信息类型
	private Integer readstatus;
	
	private Date sendtime;//发送时间
	private Date updatetime;//最新更新时间
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
	public Integer getSenduserid() {
		return senduserid;
	}
	public void setSenduserid(Integer senduserid) {
		this.senduserid = senduserid;
	}
	
	public Integer getRecuserid() {
		return recuserid;
	}
	public void setRecuserid(Integer recuserid) {
		this.recuserid = recuserid;
	}
	
	public String getSendusername() {
		return sendusername;
	}
	public void setSendusername(String sendusername) {
		this.sendusername = sendusername;
	}
	public String getRecusername() {
		return recusername;
	}
	public void setRecusername(String recusername) {
		this.recusername = recusername;
	}
	public String getContenttxt() {
		return contenttxt;
	}
	public void setContenttxt(String contenttxt) {
		this.contenttxt = contenttxt;
	}
	public Integer getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(Integer messagetype) {
		this.messagetype = messagetype;
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
	public Integer getReadstatus() {
		return readstatus;
	}
	public void setReadstatus(Integer readstatus) {
		this.readstatus = readstatus;
	}
	public String getTitletxt() {
		return titletxt;
	}
	public void setTitletxt(String titletxt) {
		this.titletxt = titletxt;
	}
	
	
}
