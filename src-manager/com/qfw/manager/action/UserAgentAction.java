package com.qfw.manager.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.agent.IAgentIncomeBS;
import com.qfw.bizservice.agent.IAgentInfoBS;
import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.common.bizservice.PromotionService.PromotionService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.BizPromotDetailBO;
import com.qfw.common.util.CollectionUtil;
import com.qfw.manager.service.IUserCreditorServcie;
import com.qfw.model.AppConst;
import com.qfw.model.vo.agent.AgentIncomeVO;
import com.qfw.model.vo.agent.AgentInfoVO;
import com.qfw.model.vo.agent.SearchAgentIncomeVO;
import com.qfw.model.vo.agent.SearchAgentInfoVO;
import com.qfw.platform.cache.CacheManager;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.vo.LoginInfo;

/**
 * 会员中心-经纪人管理
 * @author Teddy
 */
@Controller
@RequestMapping("/userAgent")
public class UserAgentAction extends BaseAction {
	
	@Resource(name = "agentInfoBS")
	private IAgentInfoBS agentInfoBS;
	
	@Resource(name = "agentIncomeBS")
	private IAgentIncomeBS agentIncomeBS;

	@Resource(name = "promotionService")
	private PromotionService promotionService;
	
	@Resource(name = "cacheManager")
	private CacheManager cacheManager;
	/**
	 * 我的推荐
	 */
	@RequestMapping(value = "/myRecommend")
	public String myRecommend(HttpServletRequest request, HttpServletResponse response
			, HttpSession session) {
		// 设置公共数据
		setCommonData(request);
		String userCode = "";
		if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
			LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
			userCode = loginInfo.getUserCode();
		}
		
		 
		
		setAttribute(request, "userCode", userCode);
		String url_pre = request.getServerName();
		if( request.getServerPort()!=80){
			url_pre += ":" + request.getServerPort();
		}
		setAttribute(request,"url_pre",url_pre );
		//setAttribute(request,"myVid","userinfohead6");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		// 绑定数据
		return getResultPath(MY_RECOMMEND);
	}
	
	
	
	/**
	 *  ajax 查询我的推荐
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyRecommend")
	public Pager ajaxQueryMyRecommend(
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = new Pager();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				
				List recommendList = new ArrayList();//agentInfoBS.getTeamLeaders(serachVo,(requestPage*pageSize+1),pageSize);
				pager.setList(recommendList);
				pager.setPageCount(recommendList.size()/(pageSize==0?1:pageSize));
				pager.setTotalCount(recommendList.size());
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	
	
	/**
	 * 我的经纪人
	 * @throws BizException 
	 */
	@RequestMapping(value = "/myAgent")
	public String myAgent(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		//setAttribute(request,"myVid","userinfohead6");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		
		// 绑定数据
		return getResultPath(MY_AGENT);
	}
	
	/**
	 *  ajax 查询我的经纪人
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyAgent")
	public Pager ajaxQuerymyPayment(
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = new Pager();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				SearchAgentInfoVO serachVo = new SearchAgentInfoVO();
				String userName = loginInfo.getCustName();
				serachVo.setUserName(userName);
				List<AgentInfoVO> agentList = agentInfoBS.getTeamLeaders(serachVo,(requestPage*pageSize+1),pageSize);
				pager.setList(agentList);
				pager.setPageCount(agentList.size()/(pageSize==0?1:pageSize));
				pager.setTotalCount(agentList.size());
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	
	/**
	 * 我的收益
	 */
	@RequestMapping(value = "/myIncome")
	public String myIncome(HttpServletRequest request, HttpServletResponse response) {
		// 设置公共数据
		setCommonData(request);
		setAttribute(request, "test", 500);
		//setAttribute(request,"myVid","userinfohead6");
		String myVid = request.getParameter("myVid");
		setAttribute(request,"myVid",myVid);
		
		// 绑定数据
		return getResultPath(MY_INCOME);
	}
	
	
	/**
	 *  ajax 查询我的收益
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQuerymyIncome")
	public Pager ajaxQuerymyIncome(
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = new Pager();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null ) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION) ;
				int count = promotionService.getCountByCustId(loginInfo.getCustId());
				List<BizPromotDetailBO> agentList = promotionService.getListByCustId(loginInfo.getCustId(), requestPage, pageSize);
				//替换代码
				if (CollectionUtil.isNotEmpty(agentList)) {
					for (BizPromotDetailBO promotDetail : agentList) {
						String proType = promotDetail.getPromotTypeCd();
						promotDetail.setPromotTypeCd(cacheManager.getDisPlayNameByCodeTypeAndVal("promotTypeCd",proType));
					}
				}
				pager.setList(agentList);
				pager.setTotalCount(count);
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

}