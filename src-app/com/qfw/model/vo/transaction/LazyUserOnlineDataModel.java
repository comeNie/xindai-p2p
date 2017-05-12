package com.qfw.model.vo.transaction;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.dailyinfo.IUserOnlineBS;
import com.qfw.common.log.LogFactory;
import com.qfw.model.bo.BizUserOnlineBO;

public class LazyUserOnlineDataModel extends LazyDataModel<BizUserOnlineBO> {

	private static final long serialVersionUID = 1L;

	private BizUserOnlineBO searchVO;
	private IUserOnlineBS userOnlineBS;
	private List<BizUserOnlineBO> userOnlineList;

	Logger log = LogFactory.getInstance().getBusinessLogger();

	public LazyUserOnlineDataModel(BizUserOnlineBO searchVO,
			IUserOnlineBS userOnlineBS) {
		this.searchVO = searchVO;
		this.userOnlineBS = userOnlineBS;
	}

	@Override
	public List<BizUserOnlineBO> load(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, String> filters) {
		try {
			userOnlineList = this.userOnlineBS.findUserOnlinePagesByVO(
					searchVO, first, pageSize);
			setRowCount(this.userOnlineBS.findUserOnlineCountByVO(searchVO));
		} catch (Exception e) {
			 
		}
		return userOnlineList;
	}

	@Override
	public BizUserOnlineBO getRowData(String rowKey) {
		for (BizUserOnlineBO bo : userOnlineList) {
			if (String.valueOf(bo.getId()).equals(rowKey))
				return bo;
		}
		BizUserOnlineBO bo = new BizUserOnlineBO();
		try {
			bo = userOnlineBS.findBizUserOnlineBOById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取登陆信息记录异常：", e);
		}
		return bo;
	}

	@Override
	public Object getRowKey(BizUserOnlineBO bo) {
		return bo.getId();

	}
}
