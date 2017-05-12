package com.qfw.platform.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.common.log.LogFactory;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.platform.model.bo.CmsCatalogType;
import com.qfw.platform.service.ICmsCatalogTypeService;

@ViewScoped
@ManagedBean(name = "addCmsCatalogTypeBean")
public class AddCmsCatalogTypeBean extends BaseBackingBean {

	private static final long serialVersionUID = 1663567494068458902L;

	@ManagedProperty(value = "#{cmsCatalogTypeService}")
	private ICmsCatalogTypeService cmsCatalogTypeService;
	private String operateFlag;
	private CmsCatalogType cmsCatalogType = new CmsCatalogType();

	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if (id != null) {
			try {
				cmsCatalogType = cmsCatalogTypeService
						.findCatalogTypeById((Integer) id);
			} catch (Exception e) {
				log.error("通过ID获取栏目类型信息异常：", e);
			}
		}
	}

	public String operate() {
		if ("add".equals(operateFlag)) {
			if (checkCatalogTypeName(cmsCatalogType.getName(), null)) {
				alert("栏目类型名称已经存在，请重新输入！");
				cmsCatalogType.setName("");
				return null;
			}
			cmsCatalogTypeService.save(cmsCatalogType);
			//MessagesController.addInfo("栏目类型" + cmsCatalogType.getName()+ "保存成功！", "栏目类型" + cmsCatalogType.getName() + "保存成功！");
			executeJS("alert('栏目类型["+cmsCatalogType.getName()+"]保存成功！');closeParMainDialog();");
		} else if ("edit".equals(operateFlag)) {
			if (checkCatalogTypeName(cmsCatalogType.getName(),
					cmsCatalogType.getId())) {
				alert("栏目类型名称已经存在，请重新输入！");
				cmsCatalogType.setName("");
				return null;
			}
			cmsCatalogTypeService.update(cmsCatalogType);
			//MessagesController.addInfo("栏目类型" + cmsCatalogType.getName()+ "修改成功！", "栏目类型" + cmsCatalogType.getName() + "修改成功！");
			executeJS("alert('栏目类型["+cmsCatalogType.getName()+"]修改成功！');closeParMainDialog();");
			
		}
		return null;
	}

	public boolean checkCatalogTypeName(String name, Integer id) {
		List<CmsCatalogType> list = new ArrayList<CmsCatalogType>();
		try {
			list = cmsCatalogTypeService.checkCatalogTypeName(name, id);
		} catch (Exception e) {
			log.error("验证栏目类型名称异常：", e);
			alert("验证栏目类型名称异常：" + e);
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ICmsCatalogTypeService getCmsCatalogTypeService() {
		return cmsCatalogTypeService;
	}

	public void setCmsCatalogTypeService(
			ICmsCatalogTypeService cmsCatalogTypeService) {
		this.cmsCatalogTypeService = cmsCatalogTypeService;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public CmsCatalogType getCmsCatalogType() {
		return cmsCatalogType;
	}

	public void setCmsCatalogType(CmsCatalogType cmsCatalogType) {
		this.cmsCatalogType = cmsCatalogType;
	}
}