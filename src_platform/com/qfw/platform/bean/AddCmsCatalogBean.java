package com.qfw.platform.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.qfw.common.log.LogFactory;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.platform.model.bo.CmsCatalog;
import com.qfw.platform.model.bo.CmsCatalogType;
import com.qfw.platform.service.ICmsCatalogService;
import com.qfw.platform.service.ICmsCatalogTypeService;

@ViewScoped
@ManagedBean(name = "addCmsCatalogBean")
public class AddCmsCatalogBean extends BaseBackingBean {

	private static final long serialVersionUID = 1663567494068458902L;

	@ManagedProperty(value = "#{cmsCatalogService}")
	private ICmsCatalogService cmsCatalogService;
	private String operateFlag;
	private CmsCatalog cmsCatalog = new CmsCatalog();
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@ManagedProperty(value = "#{cmsCatalogTypeService}")
	private ICmsCatalogTypeService cmsCatalogTypeService;

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if (id != null) {
			try {
				cmsCatalog = cmsCatalogService.findCatalogById((Integer) id);
			} catch (Exception e) {
				log.error("通过ID获取栏目信息异常：", e);
			}
		}
	}

	public String operate() {
		if ("add".equals(operateFlag)) {
			if (checkCatalogName(cmsCatalog.getTitle(), null)) {
				alert("栏目名称已经存在，请重新输入！");
				cmsCatalog.setTitle("");
				return null;
			}
			cmsCatalogService.save(cmsCatalog);
			//MessagesController.addInfo("栏目" + cmsCatalog.getTitle() + "保存成功！","栏目" + cmsCatalog.getTitle() + "保存成功！");
			executeJS("alert('栏目["+cmsCatalog.getTitle()+"]保存成功！');closeParMainDialog();");
		} else if ("edit".equals(operateFlag)) {
			if (checkCatalogName(cmsCatalog.getTitle(), cmsCatalog.getId())) {
				alert("栏目名称已经存在，请重新输入！");
				cmsCatalog.setTitle("");
				return null;
			}
			cmsCatalogService.update(cmsCatalog);
			//MessagesController.addInfo("栏目" + cmsCatalog.getTitle() + "修改成功！","栏目" + cmsCatalog.getTitle() + "修改成功！");
			executeJS("alert('栏目["+cmsCatalog.getTitle()+"]修改成功！');closeParMainDialog();");
		}
		return null;
	}

	public boolean checkCatalogName(String name, Integer id) {
		List<CmsCatalog> list = new ArrayList<CmsCatalog>();
		try {
			list = cmsCatalogService.checkCatalogName(name, id);
		} catch (Exception e) {
			log.error("验证栏目名称异常：", e);
			alert("验证栏目名称异常：" + e);
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ICmsCatalogService getCmsCatalogService() {
		return cmsCatalogService;
	}

	public void setCmsCatalogService(ICmsCatalogService cmsCatalogService) {
		this.cmsCatalogService = cmsCatalogService;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public CmsCatalog getCmsCatalog() {
		return cmsCatalog;
	}

	public void setCmsCatalog(CmsCatalog cmsCatalog) {
		this.cmsCatalog = cmsCatalog;
	}

	public ICmsCatalogTypeService getCmsCatalogTypeService() {
		return cmsCatalogTypeService;
	}

	public void setCmsCatalogTypeService(
			ICmsCatalogTypeService cmsCatalogTypeService) {
		this.cmsCatalogTypeService = cmsCatalogTypeService;
	}

	public List<SelectItem> getSelectCatalogTypeItems() {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		List<CmsCatalogType> catalogTypeList = cmsCatalogTypeService
				.getCatalogTypeList();
		if (null != catalogTypeList && !catalogTypeList.isEmpty()) {
			for (CmsCatalogType catalogType : catalogTypeList) {
				selectItemList.add(new SelectItem(catalogType.getId(),
						catalogType.getName()));
			}
		}
		return selectItemList;
	}

	public List<SelectItem> getSelectWhetherItems() {
		List<SelectItem> selectItemList = new ArrayList<SelectItem>();
		selectItemList.add(new SelectItem("1", "是"));
		selectItemList.add(new SelectItem("0", "否"));
		return selectItemList;
	}
}