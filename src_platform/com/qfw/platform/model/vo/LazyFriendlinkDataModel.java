package com.qfw.platform.model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.platform.model.bo.CmsFriendLink;
import com.qfw.platform.service.ICmsFriendlinkService;

public class LazyFriendlinkDataModel extends LazyDataModel<CmsFriendLink> {

	private static final long serialVersionUID = 1L;

	private CmsFriendLink friendlinkVO;
	private ICmsFriendlinkService friendlinkService;
	private List<CmsFriendLink> friendlinkList;

	public LazyFriendlinkDataModel() {
		friendlinkList = new ArrayList<CmsFriendLink>();
		setRowCount(0);
	}

	public LazyFriendlinkDataModel(CmsFriendLink friendlinkVO,
			ICmsFriendlinkService friendlinkService) {
		this.friendlinkVO = friendlinkVO;
		this.friendlinkService = friendlinkService;
	}

	@Override
	public List<CmsFriendLink> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if (null != friendlinkService) {
			friendlinkList = friendlinkService.findFriendlinkPagesByVO(
					friendlinkVO, first, pageSize);
			setRowCount(friendlinkService.findFriendlinkCountByVO(friendlinkVO));
		}
		return friendlinkList;
	}

	@Override
	public CmsFriendLink getRowData(String rowKey) {
		if (StringUtils.isEmpty(rowKey))
			return null;

		for (CmsFriendLink cmsFriendLink : friendlinkList) {
			if (String.valueOf(cmsFriendLink.getId()).equals(rowKey))
				return cmsFriendLink;
		}

		CmsFriendLink cmsFriendLink = new CmsFriendLink();
		try {
			cmsFriendLink = friendlinkService.findFriendlinkById(Integer
					.valueOf(rowKey));
		} catch (Exception e) {
		}
		return cmsFriendLink;
	}

	@Override
	public Object getRowKey(CmsFriendLink cmsFriendLink) {
		if (null != cmsFriendLink) {
			return cmsFriendLink.getId();
		}
		return null;
	}

	public CmsFriendLink getFriendlinkVO() {
		return friendlinkVO;
	}

	public void setFriendlinkVO(CmsFriendLink friendlinkVO) {
		this.friendlinkVO = friendlinkVO;
	}

	public ICmsFriendlinkService getFriendlinkService() {
		return friendlinkService;
	}

	public void setFriendlinkService(ICmsFriendlinkService friendlinkService) {
		this.friendlinkService = friendlinkService;
	}

	public List<CmsFriendLink> getFriendlinkList() {
		return friendlinkList;
	}

	public void setFriendlinkList(List<CmsFriendLink> friendlinkList) {
		this.friendlinkList = friendlinkList;
	}
}