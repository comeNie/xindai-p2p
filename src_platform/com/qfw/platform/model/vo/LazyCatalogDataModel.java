package com.qfw.platform.model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.platform.model.bo.CmsCatalog;
import com.qfw.platform.service.ICmsCatalogService;

public class LazyCatalogDataModel extends LazyDataModel<CmsCatalog> {

	private static final long serialVersionUID = 1L;

	private CmsCatalog catalogVO;
	private ICmsCatalogService catalogService;
	private List<CmsCatalog> catalogList;

	public LazyCatalogDataModel() {
		catalogList = new ArrayList<CmsCatalog>();
		setRowCount(0);
	}

	public LazyCatalogDataModel(CmsCatalog catalogVO,
			ICmsCatalogService catalogService) {
		this.catalogVO = catalogVO;
		this.catalogService = catalogService;
	}

	@Override
	public List<CmsCatalog> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if (null != catalogService) {
			catalogList = catalogService.findCatalogPagesByVO(catalogVO, first,
					pageSize);
			setRowCount(catalogService.findCatalogCountByVO(catalogVO));
		}
		return catalogList;
	}

	@Override
	public CmsCatalog getRowData(String rowKey) {
		if (StringUtils.isEmpty(rowKey))
			return null;

		for (CmsCatalog cmsCatalog : catalogList) {
			if (String.valueOf(cmsCatalog.getId()).equals(rowKey))
				return cmsCatalog;
		}

		CmsCatalog cmsCatalog = new CmsCatalog();
		try {
			cmsCatalog = catalogService
					.findCatalogById(Integer.valueOf(rowKey));
		} catch (Exception e) {
		}
		return cmsCatalog;
	}

	@Override
	public Object getRowKey(CmsCatalog cmsCatalog) {
		if (null != cmsCatalog) {
			return cmsCatalog.getId();
		}
		return null;
	}

	public CmsCatalog getCatalogVO() {
		return catalogVO;
	}

	public void setCatalogVO(CmsCatalog catalogVO) {
		this.catalogVO = catalogVO;
	}

	public ICmsCatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(ICmsCatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public List<CmsCatalog> getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(List<CmsCatalog> catalogList) {
		this.catalogList = catalogList;
	}
}