package com.qfw.platform.model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import com.qfw.platform.model.bo.CmsCatalogType;
import com.qfw.platform.service.ICmsCatalogTypeService;

public class LazyCatalogTypeDataModel extends LazyDataModel<CmsCatalogType> {

	private static final long serialVersionUID = 1L;

	private CmsCatalogType catalogTypeVO;
	private ICmsCatalogTypeService catalogTypeService;
	private List<CmsCatalogType> catalogTypeList;

	public LazyCatalogTypeDataModel() {
		catalogTypeList = new ArrayList<CmsCatalogType>();
		setRowCount(0);
	}

	public LazyCatalogTypeDataModel(CmsCatalogType catalogTypeVO,
			ICmsCatalogTypeService catalogTypeService) {
		this.catalogTypeVO = catalogTypeVO;
		this.catalogTypeService = catalogTypeService;
	}

	@Override
	public List<CmsCatalogType> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if (null != catalogTypeService) {
			catalogTypeList = catalogTypeService.findCatalogTypePagesByVO(
					catalogTypeVO, first, pageSize);
			setRowCount(catalogTypeService
					.findCatalogTypeCountByVO(catalogTypeVO));
		}
		return catalogTypeList;
	}

	@Override
	public CmsCatalogType getRowData(String rowKey) {
		if (StringUtils.isEmpty(rowKey))
			return null;

		for (CmsCatalogType cmsCatalogType : catalogTypeList) {
			if (String.valueOf(cmsCatalogType.getId()).equals(rowKey))
				return cmsCatalogType;
		}

		CmsCatalogType cmsCatalogType = new CmsCatalogType();
		try {
			cmsCatalogType = catalogTypeService.findCatalogTypeById(Integer
					.valueOf(rowKey));
		} catch (Exception e) {
		}
		return cmsCatalogType;
	}

	@Override
	public Object getRowKey(CmsCatalogType catalogType) {
		if (null != catalogType) {
			return catalogType.getId();
		}
		return null;
	}

	public CmsCatalogType getCatalogTypeVO() {
		return catalogTypeVO;
	}

	public void setCatalogTypeVO(CmsCatalogType catalogTypeVO) {
		this.catalogTypeVO = catalogTypeVO;
	}

	public ICmsCatalogTypeService getCatalogTypeService() {
		return catalogTypeService;
	}

	public void setCatalogTypeService(ICmsCatalogTypeService catalogTypeService) {
		this.catalogTypeService = catalogTypeService;
	}

	public List<CmsCatalogType> getCatalogTypeList() {
		return catalogTypeList;
	}

	public void setCatalogTypeList(List<CmsCatalogType> catalogTypeList) {
		this.catalogTypeList = catalogTypeList;
	}
}