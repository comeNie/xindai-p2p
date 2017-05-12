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
import com.qfw.platform.model.bo.CmsCatalog;
import com.qfw.platform.model.bo.CmsCatalogType;
import com.qfw.platform.model.vo.LazyCatalogDataModel;
import com.qfw.platform.service.ICmsCatalogService;
import com.qfw.platform.service.ICmsCatalogTypeService;

/**
 * 栏目管理
 * 
 * @author Administrator
 * 
 */
@ViewScoped
@ManagedBean(name = "cmsCatalogManagerBean")
public class CmsCatalogManagerBean extends BaseBackingBean {

	private static final long serialVersionUID = 4078371054725601890L;

	private Logger log = LogFactory.getInstance().getPlatformLogger();

	private LazyDataModel<CmsCatalog> catalogList;

	private CmsCatalog catalogVO = new CmsCatalog();

	private CmsCatalog selectCatalog;

	@ManagedProperty(value = "#{cmsCatalogService}")
	private ICmsCatalogService cmsCatalogService;

	@ManagedProperty(value = "#{cmsCatalogTypeService}")
	private ICmsCatalogTypeService cmsCatalogTypeService;

	@PostConstruct
	public void init() {
		if (null == catalogList) {
			catalogList = new LazyCatalogDataModel(catalogVO, cmsCatalogService);
		}
	}

	public void search() {
		super.search();
		if (null == catalogList) {
			catalogList = new LazyCatalogDataModel(catalogVO, cmsCatalogService);
		}
	}

	public void operate() {
		String operateFlag = ViewOper.getParameter("operateFlag");
		if ("view".equalsIgnoreCase(operateFlag)
				|| "edit".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession().setAttribute("id", selectCatalog.getId());
		} else if ("delete".equalsIgnoreCase(operateFlag)) {
			try {
				cmsCatalogService.deleteCatalogById(selectCatalog.getId());
				catalogList.setRowCount(catalogList.getRowCount() - 1);
			} catch (Exception e) {
				log.error("删除栏目信息" + selectCatalog.getTitle() + "异常：", e);
				alert("删除栏目信息异常：" + e);
			}
		}
	}

	public LazyDataModel<CmsCatalog> getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(LazyDataModel<CmsCatalog> catalogList) {
		this.catalogList = catalogList;
	}

	public CmsCatalog getCatalogVO() {
		return catalogVO;
	}

	public void setCatalogVO(CmsCatalog catalogVO) {
		this.catalogVO = catalogVO;
	}

	public CmsCatalog getSelectCatalog() {
		return selectCatalog;
	}

	public void setSelectCatalog(CmsCatalog selectCatalog) {
		this.selectCatalog = selectCatalog;
	}

	public ICmsCatalogService getCmsCatalogService() {
		return cmsCatalogService;
	}

	public void setCmsCatalogService(ICmsCatalogService cmsCatalogService) {
		this.cmsCatalogService = cmsCatalogService;
	}
	
	public ICmsCatalogTypeService getCmsCatalogTypeService() {
		return cmsCatalogTypeService;
	}

	public void setCmsCatalogTypeService(
			ICmsCatalogTypeService cmsCatalogTypeService) {
		this.cmsCatalogTypeService = cmsCatalogTypeService;
	}

	public String getCatalogTypeName(String id) {
		CmsCatalogType catalogType = cmsCatalogTypeService
				.findCatalogTypeById(Integer.parseInt(id));
		if (null != catalogType) {
			return catalogType.getName();
		}
		return "暂无";
	}
}