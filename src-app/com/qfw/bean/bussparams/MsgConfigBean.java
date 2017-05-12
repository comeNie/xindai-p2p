package com.qfw.bean.bussparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.qfw.common.tag.TagFunction;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.BaseBackingBean;

@ViewScoped
@ManagedBean(name = "msgConfigBean")
public class MsgConfigBean extends BaseBackingBean{
	
	private Map baseConMsg = new TreeMap();//基本信息配置
	private List<String> baseKey = new ArrayList<String>();
	private Map loanConMsg = new TreeMap();//借款信息配置
	private Map invConfMsg = new TreeMap();//投资信息配置
	
	public void init(){
		if(baseConMsg.isEmpty()){
			
		}
	}
	
	private void newConMsg(){
		List<SelectItem> list = (List<SelectItem>) TagFunction.getSelectItems("baseConfMsg");
		if(CollectionUtil.isNotEmpty(list)){
			for (SelectItem selectItem : list) {
				baseConMsg.put(selectItem.getLabel(), selectItem.getValue());
				
			}
		}
		TagFunction.getSelectItems("loanConfMsg");
		TagFunction.getSelectItems("invConfMsg");
	}
	
	public Map getBaseConMsg() {
		return baseConMsg;
	}
	public void setBaseConMsg(Map baseConMsg) {
		this.baseConMsg = baseConMsg;
	}
	public Map getLoanConMsg() {
		return loanConMsg;
	}
	public void setLoanConMsg(Map loanConMsg) {
		this.loanConMsg = loanConMsg;
	}
	public Map getInvConfMsg() {
		return invConfMsg;
	}
	public void setInvConfMsg(Map invConfMsg) {
		this.invConfMsg = invConfMsg;
	}
	
}
