package com.qfw.platform.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.qfw.common.log.LogFactory;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.platform.model.bo.CmsNavigation;
import com.qfw.platform.service.ICmsNavigationService;
import com.qfw.platform.service.IHtmlService;

@ViewScoped
@ManagedBean(name = "addCmsNavigationBean")
public class AddCmsNavigationBean extends BaseBackingBean {

	private static final long serialVersionUID = 1663567494068458902L;

	@ManagedProperty(value = "#{cmsNavigationService}")
	private ICmsNavigationService cmsNavigationService;
	
	private String operateFlag;
	private CmsNavigation cmsNavigation = new CmsNavigation();
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if (id != null) {
			try {
				cmsNavigation = cmsNavigationService
						.findNavigationsById((Integer) id);
			} catch (Exception e) {
				log.error("通过ID获取导航信息异常：", e);
			}
		}
	}

	public String operate() {
		if ("add".equals(operateFlag)) {
			if (checkNavName(cmsNavigation.getName(), null)) {
				alert("导航名称已经存在，请重新输入！");
				cmsNavigation.setName("");
				return null;
			}
			this.cmsNavigationService.save(cmsNavigation);
			//MessagesController.addInfo("导航" + cmsNavigation.getName()+ "保存成功！", "导航" + cmsNavigation.getName() + "保存成功！");
			executeJS("alert('导航["+cmsNavigation.getName()+"]保存成功！');closeParMainDialog();");
		} else if ("edit".equals(operateFlag)) {
			if (checkNavName(cmsNavigation.getName(), cmsNavigation.getId())) {
				alert("导航名称已经存在，请重新输入！");
				cmsNavigation.setName("");
				return null;
			}
			this.cmsNavigationService.update(cmsNavigation);
			//MessagesController.addInfo("导航" + cmsNavigation.getName()+ "修改成功！", "导航" + cmsNavigation.getName() + "修改成功！");
			executeJS("alert('导航["+cmsNavigation.getName()+"]保存成功！');closeParMainDialog();");
		}
		return null;
	}

	public boolean checkNavName(String navName, Integer id) {
		List<CmsNavigation> list = new ArrayList<CmsNavigation>();
		try {
			list = cmsNavigationService.checkNavigationName(navName, id);
		} catch (Exception e) {
			log.error("验证导航名称异常：", e);
			alert("验证导航名称异常：" + e);
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ICmsNavigationService getCmsNavigationService() {
		return cmsNavigationService;
	}

	public void setCmsNavigationService(ICmsNavigationService cmsNavigationService) {
		this.cmsNavigationService = cmsNavigationService;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public CmsNavigation getCmsNavigation() {
		return cmsNavigation;
	}

	public void setCmsNavigation(CmsNavigation cmsNavigation) {
		this.cmsNavigation = cmsNavigation;
	}
	
	public List<SelectItem> getSelectPositionItems() {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		selectItemList.add(new SelectItem(CmsNavigation.NAV_POSITION_TOP, "顶部"));
		selectItemList.add(new SelectItem(CmsNavigation.NAV_POSITION_MIDDLE, "中间"));
		selectItemList.add(new SelectItem(CmsNavigation.NAV_POSITION_BOTTOM, "底部"));
		return selectItemList;
	}
	
	public List<SelectItem> getSelectWhetherItems() {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		selectItemList.add(new SelectItem("1", "是"));
		selectItemList.add(new SelectItem("0", "否"));
		return selectItemList;
	}
}
