package com.qfw.platform.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.bizservice.message.IMessageManageBS;
import com.qfw.common.bizservice.IMsgConfBS;
import com.qfw.common.exception.BizException;
import com.qfw.manager.action.BaseAction;
import com.qfw.model.vo.loan.LoanApproveVO;
import com.qfw.model.vo.loan.LoanSearchVO;


/**
 * 关于我们
 * 
 * 
 */

@Controller
@RequestMapping(value = "/about")
public class AboutAction extends BaseAction {
	
	/**
	 * 关于我们——公司简介
	 */
	@RequestMapping(value = "/aboutUs")
	public String aboutUs(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US);
	}
	/**
	 * 世纪创投 vip
	 */
	@RequestMapping(value = "/sjctVip")
	public String sjctVip(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(SJCT_VIP);
	}
	/**
	 * 关于我们——移动端公告
	 */
	@RequestMapping(value = "/mobileNotice")
	public String mobileNotice(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(MOBILE_NOTICE);
	}
	/**
	 * 关于我们——了解我们
	 */
	@RequestMapping(value = "/mobileAboutUs")
	public String mobileAboutUs(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(MOBILE_ABOUT_US);
	}
	/**
	 * 关于我们——充值成功
	 */
	@RequestMapping(value = "/mobileRechargeSuccess")
	public String mobileRechargeSuccess(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(MOBILE_RECHARGE_SUCCESS);
	}

	/**
	 * 关于我们——公司资质
	 */
	@RequestMapping(value = "/aboutZz")
	public String aboutZz(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_ZZ);
	}
	/**
	 * 关于我们——网站公告
	 */
	@RequestMapping(value = "/aboutUsAnnouncement")
	public String aboutUsAnnouncement(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT);
	}
	
	/**
	 * 关于我们——信息披露
	 */
	@RequestMapping(value = "/aboutUsInfo")
	public String aboutUsInformation(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO);
	}
	
	/**
	 * 关于我们——合作伙伴
	 */
	@RequestMapping(value = "/aboutUsPartner")
	public String aboutUsPartner(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_PARTNER);
	}
	
	/**
	 * 关于我们——网点建设
	 */
	@RequestMapping(value = "/aboutUsNetBuild")
	public String aboutUsNetBuilding(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_NETBUILD);
	}
	
	/**
	 * 关于我们——招贤纳士
	 */
	@RequestMapping(value = "/aboutUsCareers")
	public String aboutUsCareers(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_CAREERS);
	}
	
	/**
	 * 关于我们——联系我们
	 */
	@RequestMapping(value = "/aboutUsContact")
	public String aboutUsContact(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_CONTACT);
	}
	
	/**
	 * 关于我们——网站动态
	 */
	@RequestMapping(value = "/aboutUsNews")
	public String aboutUsNews(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_NEWS);
	}
	
	/**
	 * 关于我们——网站动态明细
	 */
	@RequestMapping(value = "/aboutUsNewsDetail_01")
	public String aboutUsNewsDetail_01(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_NEWS_DETAIL_01);
	}

	
	/**
	 * 关于我们——信息披露详细内容
	 */
	@RequestMapping(value = "/aboutUsInfoDetail_1")
	public String aboutUsInformationDetail_1(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_1);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_2")
	public String aboutUsInformationDetail_2(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_2);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_3")
	public String aboutUsInformationDetail_3(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_3);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_4")
	public String aboutUsInformationDetail_4(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_4);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_5")
	public String aboutUsInformationDetail_5(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_5);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_6")
	public String aboutUsInformationDetail_6(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_6);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_7")
	public String aboutUsInformationDetail_7(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_7);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_8")
	public String aboutUsInformationDetail_8(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_8);
	}
	@RequestMapping(value = "/aboutUsInfoDetail_9")
	public String aboutUsInformationDetail_9(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_INFO_DETAIL_9);
	}
	
	/**
	 * 关于我们——网站公告详细内容
	 */
	@RequestMapping(value = "/aboutUsAnnouncementDetail_1")
	public String aboutUsAnnouncementDetail_1(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_1);
	}
	
	@RequestMapping(value = "/aboutUsAnnouncementDetail_2")
	public String aboutUsAnnouncementDetail_2(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_2);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_3")
	public String aboutUsAnnouncementDetail_3(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_3);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_4")
	public String aboutUsAnnouncementDetail_4(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_4);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_5")
	public String aboutUsAnnouncementDetail_5(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_5);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_6")
	public String aboutUsAnnouncementDetail_6(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_6);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_7")
	public String aboutUsAnnouncementDetail_7(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_7);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_8")
	public String aboutUsAnnouncementDetail_8(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_8);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_9")
	public String aboutUsAnnouncementDetail_9(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_9);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_10")
	public String aboutUsAnnouncementDetail_10(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_10);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_11")
	public String aboutUsAnnouncementDetail_11(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_11);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_12")
	public String aboutUsAnnouncementDetail_12(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_12);
	}
	@RequestMapping(value = "/aboutUsAnnouncementDetail_14")
	public String aboutUsAnnouncementDetail_14(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_ANNOUNCEMENT_DETAIL_14);
	}
	@RequestMapping(value = "/aboutUsManager")
	public String aboutUsAnnouncementDetail_13(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_MANAGER);
	}
	
	/**
	 * 关于我们——网点建设详细内容
	 */
	@RequestMapping(value = "/aboutUsNetBuildDetail_1")
	public String aboutUsNetBuildingDetail_1(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_NETBUILD_DETAIL_1);
	}
	@RequestMapping(value = "/aboutUsNetBuildDetail_2")
	public String aboutUsNetBuildingDetail_2(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_NETBUILD_DETAIL_2);
	}
	@RequestMapping(value = "/aboutUsNetBuildDetail_3")
	public String aboutUsNetBuildingDetail_3(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(ABOUT_US_NETBUILD_DETAIL_3);
	}
}
