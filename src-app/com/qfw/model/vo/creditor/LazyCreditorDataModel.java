package com.qfw.model.vo.creditor;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyCreditorDataModel extends LazyDataModel<CreditorManageVO> {

	private CreditorSearchVO searchVO;
	private ICreditorManageBS creditorManageBS;
	private List<CreditorManageVO> creditorVOList ;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyCreditorDataModel(CreditorSearchVO searchVO, ICreditorManageBS creditorManageBS){
		this.searchVO = searchVO;
		this.creditorManageBS = creditorManageBS;
	}
	
	@Override
	public List<CreditorManageVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			creditorVOList = creditorManageBS.findInfoPagesByVO(searchVO, first, pageSize);
			setRowCount(this.creditorManageBS.getCountByVO(searchVO));
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.error("获取借款信息异常：", e);
			}
		}
		return creditorVOList;
	}
	
	@Override
	public CreditorManageVO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)|| (null == creditorVOList || creditorVOList.size()<=0)){
			return null;
		}
		for(CreditorManageVO creditorManageVO : creditorVOList){
			if(String.valueOf(creditorManageVO.getCreditorId()).equals(rowKey))
				return creditorManageVO;
		}
		CreditorManageVO vo = new CreditorManageVO();
		try {
			vo = creditorManageBS.findInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取异常：", e);
		}
		return vo;
	}
	
	@Override
	public Object getRowKey(CreditorManageVO infoVO) {
		return infoVO.getCreditorId();
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public CreditorSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(CreditorSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public ICreditorManageBS getCreditorManageBS() {
		return creditorManageBS;
	}

	public void setCreditorManageBS(ICreditorManageBS creditorManageBS) {
		this.creditorManageBS = creditorManageBS;
	}

	public List<CreditorManageVO> getCreditorVOList() {
		return creditorVOList;
	}

	public void setCreditorVOList(List<CreditorManageVO> creditorVOList) {
		this.creditorVOList = creditorVOList;
	}

}
