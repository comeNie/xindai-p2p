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
import com.qfw.platform.model.bo.CmsFriendLink;
import com.qfw.platform.model.vo.LazyFriendlinkDataModel;
import com.qfw.platform.service.ICmsFriendlinkService;

/**
 * 友情链接管理
 * 
 * @author Administrator
 * 
 */
@ViewScoped
@ManagedBean(name = "cmsFriendlinkManagerBean")
public class CmsFriendlinkManagerBean extends BaseBackingBean {

	private static final long serialVersionUID = 4078371054725601890L;

	private Logger log = LogFactory.getInstance().getPlatformLogger();

	private LazyDataModel<CmsFriendLink> friendlinkList;

	private CmsFriendLink friendlinkVO = new CmsFriendLink();

	private CmsFriendLink selectFriendlink;

	@ManagedProperty(value = "#{cmsFriendlinkService}")
	private ICmsFriendlinkService cmsFriendlinkService;

	@PostConstruct
	public void init() {
		if (null == friendlinkList) {
			friendlinkList = new LazyFriendlinkDataModel(friendlinkVO,
					cmsFriendlinkService);
		}
	}

	public void search() {
		super.search();
		if (null == friendlinkList) {
			friendlinkList = new LazyFriendlinkDataModel(friendlinkVO,
					cmsFriendlinkService);
		}
	}

	public void operate() {
		String operateFlag = ViewOper.getParameter("operateFlag");
		if ("view".equalsIgnoreCase(operateFlag)
				|| "edit".equalsIgnoreCase(operateFlag)) {
			ViewOper.getSession().setAttribute("id", selectFriendlink.getId());
		} else if ("delete".equalsIgnoreCase(operateFlag)) {
			String id = String.valueOf(selectFriendlink.getId());
			String sql = "delete from cms_friendlink where id = " + id;
			try {
				cmsFriendlinkService.getJdbcTemplate().execute(sql);
				friendlinkList.setRowCount(friendlinkList.getRowCount() - 1);
			} catch (Exception e) {
				log.error("删除友情链接" + selectFriendlink.getName() + "异常：", e);
				alert("删除友情链接异常：" + e);
			}
		}
	}

	public LazyDataModel<CmsFriendLink> getFriendlinkList() {
		return friendlinkList;
	}

	public void setFriendlinkList(LazyDataModel<CmsFriendLink> friendlinkList) {
		this.friendlinkList = friendlinkList;
	}

	public CmsFriendLink getFriendlinkVO() {
		return friendlinkVO;
	}

	public void setFriendlinkVO(CmsFriendLink friendlinkVO) {
		this.friendlinkVO = friendlinkVO;
	}

	public CmsFriendLink getSelectFriendlink() {
		return selectFriendlink;
	}

	public void setSelectFriendlink(CmsFriendLink selectFriendlink) {
		this.selectFriendlink = selectFriendlink;
	}

	public ICmsFriendlinkService getCmsFriendlinkService() {
		return cmsFriendlinkService;
	}

	public void setCmsFriendlinkService(ICmsFriendlinkService cmsFriendlinkService) {
		this.cmsFriendlinkService = cmsFriendlinkService;
	}
}