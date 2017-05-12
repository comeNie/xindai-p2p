package com.qfw.platform.model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.platform.model.bo.CmsNavigation;
import com.qfw.platform.service.ICmsNavigationService;

public class LazyNavigationtDataModel extends LazyDataModel<CmsNavigation> {

	private static final long serialVersionUID = 1L;

	private CmsNavigation navigationVO;
	private ICmsNavigationService navigationService;
	private List<CmsNavigation> navigationList;

	public LazyNavigationtDataModel() {
		navigationList = new ArrayList<CmsNavigation>();
		setRowCount(0);
	}

	public LazyNavigationtDataModel(CmsNavigation navigationVO,
			ICmsNavigationService navigationService) {
		this.navigationVO = navigationVO;
		this.navigationService = navigationService;
	}

	@Override
	public List<CmsNavigation> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if (null != navigationService) {
			navigationList = navigationService.findNavigationPagesByVO(
					navigationVO, first, pageSize);
			setRowCount(navigationService
					.findNavigationsCountByVO(navigationVO));
		}
		return navigationList;
	}

	@Override
	public CmsNavigation getRowData(String rowKey) {
		if (StringUtils.isEmpty(rowKey))
			return null;

		for (CmsNavigation cmsNavigation : navigationList) {
			if (String.valueOf(cmsNavigation.getId()).equals(rowKey))
				return cmsNavigation;
		}

		CmsNavigation cmsNavigation = new CmsNavigation();
		try {
			cmsNavigation = navigationService.findNavigationsById(Integer
					.valueOf(rowKey));
		} catch (Exception e) {
		}
		return cmsNavigation;
	}

	@Override
	public Object getRowKey(CmsNavigation navigation) {
		if(null != navigation){
			return navigation.getId();
		}
		return null;
	}

	public CmsNavigation getNavigationVO() {
		return navigationVO;
	}

	public void setNavigationVO(CmsNavigation navigationVO) {
		this.navigationVO = navigationVO;
	}

	public List<CmsNavigation> getNavigationList() {
		return navigationList;
	}

	public void setNavigationList(List<CmsNavigation> navigationList) {
		this.navigationList = navigationList;
	}
}