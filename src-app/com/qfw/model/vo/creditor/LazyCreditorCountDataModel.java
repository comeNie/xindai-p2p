package com.qfw.model.vo.creditor;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.creditor.ICreditorCountManageBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyCreditorCountDataModel extends LazyDataModel<CreditorCountVO> {

	private CreditorSearchVO searchVO;
	private ICreditorCountManageBS creditorCountManageBS;
	private List<CreditorCountVO> list ;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyCreditorCountDataModel(CreditorSearchVO searchVO,ICreditorCountManageBS creditorCountManageBS){
		this.searchVO = searchVO;
		this.creditorCountManageBS = creditorCountManageBS;
	}
	
	@Override
	public List<CreditorCountVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			list = creditorCountManageBS.findInfoPagesByVO(searchVO, first, pageSize);
			setRowCount(this.creditorCountManageBS.getCountByVO(searchVO));
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.error("获取借款信息异常：", e);
			}
		}
		return list;
	}
	
	@Override
	public CreditorCountVO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)|| (null == list || list.size()<=0)){
			return null;
		}
		for(CreditorCountVO vo : list){
			if(String.valueOf(vo.getId()).equals(rowKey))
				return null;
		}
		CreditorCountVO creditorCountVO = new CreditorCountVO();
//		try {
//			creditorCountVO = creditorCountManageBS.findInfoById(Integer.valueOf(rowKey));
//		} catch (Exception e) {
//			log.error("通过ID获取异常：", e);
//		}
		return null;
	}
	
	@Override
	public Object getRowKey(CreditorCountVO infoVO) {
		return infoVO.getId();
	}


	public CreditorSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(CreditorSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public ICreditorCountManageBS getCreditorCountManageBS() {
		return creditorCountManageBS;
	}

	public void setCreditorCountManageBS(
			ICreditorCountManageBS creditorCountManageBS) {
		this.creditorCountManageBS = creditorCountManageBS;
	}

	public List<CreditorCountVO> getList() {
		return list;
	}

	public void setList(List<CreditorCountVO> list) {
		this.list = list;
	}


}
