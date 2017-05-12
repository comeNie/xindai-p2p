package com.qfw.model.vo.bussparams;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.bussparams.IMessageInfoBS;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.util.StringUtils;

public class LazyMessageDataModel extends LazyDataModel<SysMessageTemplate>{
	
	private static final long serialVersionUID = 1L;
	private List<SysMessageTemplate> messageInfoList;	
	private IMessageInfoBS messageInfoBS;
	private SysMessageTemplate searchMessageInfoVO;
	
	public LazyMessageDataModel(SysMessageTemplate searchMessageInfoVO, IMessageInfoBS messageInfoBS){
		this.searchMessageInfoVO = searchMessageInfoVO;
		this.messageInfoBS = messageInfoBS;
	}

	@Override
	public List<SysMessageTemplate> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			messageInfoList = this.messageInfoBS.findMessageInfoPagesByVO(searchMessageInfoVO, first, pageSize);
			setRowCount(this.messageInfoBS.findMessageInfoCountByVO(searchMessageInfoVO));
		} catch (Exception e) {
			 
		}
		return messageInfoList;
	}
	
	@Override
	public SysMessageTemplate getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for(SysMessageTemplate messageInfoVO : messageInfoList){
			if(String.valueOf(messageInfoVO.getId()).equals(rowKey))
				return messageInfoVO;
		}
		SysMessageTemplate messageInfoVO = new SysMessageTemplate();
		try {
			messageInfoVO = messageInfoBS.findMessageInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
		}
		return messageInfoVO;
	}
	
	@Override
	public Object getRowKey(SysMessageTemplate messageInfoVO){
		return messageInfoVO.getId();
	}

	public List<SysMessageTemplate> getMessageInfoList() {
		return messageInfoList;
	}

	public void setMessageInfoList(List<SysMessageTemplate> messageInfoList) {
		this.messageInfoList = messageInfoList;
	}

	public IMessageInfoBS getMessageInfoBS() {
		return messageInfoBS;
	}

	public void setMessageInfoBS(IMessageInfoBS messageInfoBS) {
		this.messageInfoBS = messageInfoBS;
	}

	public SysMessageTemplate getSearchMessageInfoVO() {
		return searchMessageInfoVO;
	}

	public void setSearchMessageInfoVO(SysMessageTemplate searchMessageInfoVO) {
		this.searchMessageInfoVO = searchMessageInfoVO;
	}

}
