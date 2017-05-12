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
import com.qfw.platform.model.bo.CmsNavigation;
import com.qfw.platform.model.vo.LazyNavigationtDataModel;
import com.qfw.platform.service.ICmsNavigationService;

/**
 * 导航管理
 * 
 * @author Administrator
 * 
 */
@ViewScoped
@ManagedBean(name = "cmsNavigationManagerBean")
public class CmsNavigationManagerBean extends BaseBackingBean {

	private static final long serialVersionUID = 4078371054725601890L;

	private Logger log = LogFactory.getInstance().getPlatformLogger();

	private LazyDataModel<CmsNavigation> navigationList;

	private CmsNavigation navigationVO = new CmsNavigation();

	private CmsNavigation selectNavigation;

	@ManagedProperty(value = "#{cmsNavigationService}")
	private ICmsNavigationService cmsNavigationService;
	
	@PostConstruct
	public void init() {
		if (null == navigationList) {
			navigationList = new LazyNavigationtDataModel(navigationVO,
					cmsNavigationService);
		}
	}

	public void search() {
		super.search();
		if (null == navigationList) {
			navigationList = new LazyNavigationtDataModel(navigationVO,
					cmsNavigationService);
		}
	}

	public void operate() {
		String operateFlag = ViewOper.getParameter("operateFlag");
		if ("view".equalsIgnoreCase(operateFlag)
				|| "edit".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession().setAttribute("id", selectNavigation.getId());
		} else if ("delete".equalsIgnoreCase(operateFlag)) {
			try {
				cmsNavigationService.deleteNavigationById(selectNavigation
						.getId());
				navigationList.setRowCount(navigationList.getRowCount() - 1);
			} catch (Exception e) {
				log.error("删除导航信息" + selectNavigation.getName() + "异常：", e);
				alert("删除导航信息异常：" + e);
			}
		}
	}

	public ICmsNavigationService getCmsNavigationService() {
		return cmsNavigationService;
	}

	public void setCmsNavigationService(
			ICmsNavigationService cmsNavigationService) {
		this.cmsNavigationService = cmsNavigationService;
	}

	public LazyDataModel<CmsNavigation> getNavigationList() {
		return navigationList;
	}

	public void setNavigationList(LazyDataModel<CmsNavigation> navigationList) {
		this.navigationList = navigationList;
	}

	public CmsNavigation getNavigationVO() {
		return navigationVO;
	}

	public void setNavigationVO(CmsNavigation navigationVO) {
		this.navigationVO = navigationVO;
	}

	public CmsNavigation getSelectNavigation() {
		return selectNavigation;
	}

	public void setSelectNavigation(CmsNavigation selectNavigation) {
		this.selectNavigation = selectNavigation;
	}

	
}