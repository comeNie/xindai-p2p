package com.qfw.platform.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.platform.model.bo.CmsCatalogType;
import com.qfw.platform.model.vo.LazyCatalogTypeDataModel;
import com.qfw.platform.service.ICmsCatalogTypeService;

/**
 * 栏目分类管理
 * 
 * @author Administrator
 * 
 */
@ViewScoped
@ManagedBean(name = "cmsCatalogTypeManagerBean")
public class CmsCatalogTypeManagerBean extends BaseBackingBean {

	private static final long serialVersionUID = 4078371054725601890L;

	private Logger log = LogFactory.getInstance().getPlatformLogger();

	private LazyDataModel<CmsCatalogType> catalogTypeList;

	private CmsCatalogType catalogTypeVO = new CmsCatalogType();

	private CmsCatalogType selectCatalogType;

	@ManagedProperty(value = "#{cmsCatalogTypeService}")
	private ICmsCatalogTypeService cmsCatalogTypeService;

	@PostConstruct
	public void init() {
		if (null == catalogTypeList) {
			catalogTypeList = new LazyCatalogTypeDataModel(catalogTypeVO,
					cmsCatalogTypeService);
		}
	}

	public void search() {
		super.search();
		if (null == catalogTypeList) {
			catalogTypeList = new LazyCatalogTypeDataModel(catalogTypeVO,
					cmsCatalogTypeService);
		}
	}

	public void operate() {
		String operateFlag = ViewOper.getParameter("operateFlag");
		if ("view".equalsIgnoreCase(operateFlag)
				|| "edit".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession().setAttribute("id", selectCatalogType.getId());
		} else if ("delete".equalsIgnoreCase(operateFlag)) {
			try {
				cmsCatalogTypeService.deleteCatalogTypeById(selectCatalogType
						.getId());
				catalogTypeList.setRowCount(catalogTypeList.getRowCount() - 1);
			} catch (Exception e) {
				log.error("删除栏目分类信息" + selectCatalogType.getName() + "异常：", e);
				alert("删除栏目分类信息异常：" + e);
			}
		}
	}

	public LazyDataModel<CmsCatalogType> getCatalogTypeList() {
		return catalogTypeList;
	}

	public void setCatalogTypeList(LazyDataModel<CmsCatalogType> catalogTypeList) {
		this.catalogTypeList = catalogTypeList;
	}

	public CmsCatalogType getCatalogTypeVO() {
		return catalogTypeVO;
	}

	public void setCatalogTypeVO(CmsCatalogType catalogTypeVO) {
		this.catalogTypeVO = catalogTypeVO;
	}

	public CmsCatalogType getSelectCatalogType() {
		return selectCatalogType;
	}

	public void setSelectCatalogType(CmsCatalogType selectCatalogType) {
		this.selectCatalogType = selectCatalogType;
	}

	public ICmsCatalogTypeService getCmsCatalogTypeService() {
		return cmsCatalogTypeService;
	}

	public void setCmsCatalogTypeService(
			ICmsCatalogTypeService cmsCatalogTypeService) {
		this.cmsCatalogTypeService = cmsCatalogTypeService;
	}
}