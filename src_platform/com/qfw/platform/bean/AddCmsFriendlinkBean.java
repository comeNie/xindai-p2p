package com.qfw.platform.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import com.qfw.common.log.LogFactory;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.platform.model.bo.CmsFriendLink;
import com.qfw.platform.service.ICmsFriendlinkService;
import com.qfw.platform.utils.CommonUtil;

@ViewScoped
@ManagedBean(name = "addCmsFriendlinkBean")
public class AddCmsFriendlinkBean extends BaseBackingBean {

	private static final long serialVersionUID = 1663567494068458902L;

	@ManagedProperty(value = "#{cmsFriendlinkService}")
	private ICmsFriendlinkService cmsFriendlinkService;
	private String operateFlag;
	private CmsFriendLink cmsFriendlink = new CmsFriendLink();
	private UploadedFile logoFile;

	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if (id != null) {
			try {
				cmsFriendlink = cmsFriendlinkService
						.findFriendlinkById((Integer) id);
			} catch (Exception e) {
				log.error("通过ID获取友情链接信息异常：", e);
			}
		}
	}

	public String operate() {
		if ("add".equals(operateFlag)) {
			if (checkFriendlinkName(cmsFriendlink.getName(), null)) {
				alert("友情链接名称已经存在，请重新输入！");
				cmsFriendlink.setName("");
				return null;
			}

			String uploadImagePath = null;
			if (null != logoFile) {
				uploadImagePath = uploadFile();
			}
			// TODO
//			if (StringUtils.isEmpty(uploadImagePath)) {
//				alert("友情链接Logo未上传，请上传.");
//				return null;
//			}
			cmsFriendlink.setLogo(uploadImagePath);
			cmsFriendlinkService.save(cmsFriendlink);
			MessagesController.addInfo("友情链接" + cmsFriendlink.getName()
					+ "保存成功！", "友情链接" + cmsFriendlink.getName() + "保存成功！");
		} else if ("edit".equals(operateFlag)) {
			if (checkFriendlinkName(cmsFriendlink.getName(),
					cmsFriendlink.getId())) {
				alert("友情链接名称已经存在，请重新输入！");
				cmsFriendlink.setName("");
				return null;
			}
			
			String uploadImagePath = null;
			if (null != logoFile) {
				uploadImagePath = uploadFile();
				if (!StringUtils.isEmpty(uploadImagePath)) {
					cmsFriendlink.setLogo(uploadImagePath);
				}				
			}
			cmsFriendlinkService.update(cmsFriendlink);
			MessagesController.addInfo("友情链接" + cmsFriendlink.getName()
					+ "修改成功！", "友情链接" + cmsFriendlink.getName() + "修改成功！");
		}
		return null;
	}

	public boolean checkFriendlinkName(String name, Integer id) {
		List<CmsFriendLink> list = new ArrayList<CmsFriendLink>();
		try {
			list = cmsFriendlinkService.checkFriendlinkName(name, id);
		} catch (Exception e) {
			log.error("验证友情链接名称异常：", e);
			alert("验证友情链接名称异常：" + e);
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private String uploadFile() {
		String imageExtension = StringUtils.substringAfterLast(
				logoFile.getFileName(), ".").toLowerCase();
		if(StringUtils.isEmpty(imageExtension)){
			return "";
		}

		String upLoadDirStr = "/upload/images/" + CommonUtil.getDateStr();
		File uploadImageDir = new File(CommonUtil.getRealPath(upLoadDirStr));
		if (!uploadImageDir.exists()) {
			uploadImageDir.mkdirs();
		}
		String uploadImagePath = upLoadDirStr + "/friendlink" + CommonUtil.getUUID()
				+ "." + imageExtension;
		File file = new File(CommonUtil.getRealPath(uploadImagePath));
		try {
			file.deleteOnExit();
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(logoFile.getContents());
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return uploadImagePath;
	}

	public ICmsFriendlinkService getCmsFriendlinkService() {
		return cmsFriendlinkService;
	}

	public void setCmsFriendlinkService(
			ICmsFriendlinkService cmsFriendlinkService) {
		this.cmsFriendlinkService = cmsFriendlinkService;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public CmsFriendLink getCmsFriendlink() {
		return cmsFriendlink;
	}

	public void setCmsFriendlink(CmsFriendLink cmsFriendlink) {
		this.cmsFriendlink = cmsFriendlink;
	}

	public UploadedFile getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(UploadedFile logoFile) {
		this.logoFile = logoFile;
	}
}