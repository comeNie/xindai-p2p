package com.qfw.manager.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.util.URLDecoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.message.IMessageManageBS;
import com.qfw.common.bizservice.IMsgConfBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MobileUtil;
import com.qfw.common.util.QrCodeUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.manager.service.IUserCreditorServcie;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAreaBO;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.bo.BizMessageConfBO;
import com.qfw.model.bo.BizMessageInfoBO;
import com.qfw.model.vo.custinfo.CustInfoVO;
import com.qfw.model.vo.message.RecMessageVO;
import com.qfw.model.vo.message.SendMessageVO;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.vo.LoginInfo;

/**
 * 会员中心-消息管理
 * 
 * @author Teddy
 */
@Controller
@RequestMapping("/userMessage")
public class UserMessageAction extends BaseAction {

	@Resource(name = "messageManageBS")
	private IMessageManageBS messageManageBS;// 

	@Resource(name = "msgConfBS")
	private IMsgConfBS msgConfBS;
	
	@Resource(name = "custInfoBS")
	private ICustInfoBS custInfoBS;
	
	@Resource(name = "userCreditorServcie")
	private IUserCreditorServcie userCreditorServcie;// 

	
	/**
	 * 通知设置
	 */
	@RequestMapping(value = "/noteSet")
	public String noteSet(HttpServletRequest request,
			HttpServletResponse response) {
		
		String myVid =request.getParameter("myVid");
		// 设置公共数据
		setCommonData(request);
		// 绑定数据
		setAttribute(request, "test", 550);
		setAttribute(request, "myVid", myVid);
		return getResultPath(NOTE_SET);
	}
	/**
	 * 推广协议
	 */
	@RequestMapping(value = "/recommendProtocol")
	public String recommendProtocol(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(
				AppConst.LOGIN_INFO_SESSION);
		if (loginInfo != null) {
			BizCustomerBO cust = custInfoBS.findCustById(loginInfo.getCustId());
			BizAreaBO area = (BizAreaBO)messageManageBS.find(BizAreaBO.class, new Long(cust.getLiveNationalityCd()));
			
			setAttribute(request, "custName", cust.getCustName());
			setAttribute(request, "cardNo",loginInfo.getUserSecurity().getShowIdCard());
			setAttribute(request, "address",area.getFullName()+cust.getLiveStreetAddress());
			setAttribute(request, "mobile",cust.getMobileTelephone());
			if(cust.getRefereeDate() != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(cust.getRefereeDate());
				setAttribute(request, "date",cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日");
			}else{
				setAttribute(request, "date","__年__月__日");
			}
			
		}
		
		return getResultPath(RECOMMEND_PROTOCOL);
	}
	/**
	 * 推广人信息
	 */
	@RequestMapping(value = "/recommendInfo")
	public String recommendInfo(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(
				AppConst.LOGIN_INFO_SESSION);
		if (loginInfo != null) {
			BizCustomerBO cust = custInfoBS.findCustById(loginInfo.getCustId());
			BizAreaBO area = (BizAreaBO)messageManageBS.find(BizAreaBO.class, new Long(cust.getLiveNationalityCd()));
			
			setAttribute(request, "custName", cust.getCustName());
			setAttribute(request, "cardNo",loginInfo.getUserSecurity().getShowIdCard());
			setAttribute(request, "address",area.getFullName()+cust.getLiveStreetAddress());
			setAttribute(request, "mobile",cust.getMobileTelephone());
			if(cust.getRefereeDate() != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(cust.getRefereeDate());
				setAttribute(request, "date",cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日");
			}else{
				setAttribute(request, "date","__年__月__日");
			}
		}
		return getResultPath(RECOMMEN_INFO);
	}
	/**
	 * 通知设置
	 * 
	 * @throws BizException
	 */
	@RequestMapping(value = "/noteSetSubmit")
	public String noteSetSubmit(HttpServletRequest request,
			HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(
				AppConst.LOGIN_INFO_SESSION);
		if (loginInfo != null) {
			String eventType = "";
			String msgTypeCd = "";
			String keyStr = "";
			String val = "";
			Integer custId = loginInfo.getCustId();
			boolean isAdd = false;
			BizMessageConfBO bo = null;
			List<BizMessageConfBO> addBoList = new ArrayList<BizMessageConfBO>();
			List<BizMessageConfBO> updateBoList = new ArrayList<BizMessageConfBO>();
			
			for (int i = 1; i <= 14; i++) {
				for (int j = 1; j <= 3; j++) {
					isAdd = false;
					eventType = "A" + i;
					msgTypeCd = String.valueOf(j);
					keyStr = eventType + "_" + msgTypeCd;
					bo = msgConfBS.getConf(custId, eventType, msgTypeCd);
					if (bo == null) {
						isAdd = true;
						bo = new BizMessageConfBO();
						bo.setCustId(custId);
						bo.setEventType(eventType);
						bo.setMsgTypeCd(msgTypeCd);
						bo
								.setSysCreateTime(new Timestamp(new Date()
										.getTime()));
						bo.setSysCreateUser(loginInfo.getUserId());
						bo
								.setSysUpdateTime(new Timestamp(new Date()
										.getTime()));
						bo.setSysUpdateUser(loginInfo.getUserId());
					}
					val = request.getParameter(keyStr) == null ? "" : request
							.getParameter(keyStr);
					if (val.equals("on")) {
						bo.setIsEnable("1");
					} else {
						bo.setIsEnable("0");
					}
					if (isAdd) {
						//messageManageBS.save(bo);
						addBoList.add(bo);
					} else {
						//messageManageBS.update(bo);
						updateBoList.add(bo);
					}

				}

			}
			messageManageBS.saveOrUpdateMessageConf(addBoList, updateBoList);

			setAttribute(request, "sucMes", "消息设置保存成功！");
		} else {
			setAttribute(request, "sucMes", "消息设置保存失败，请先进行登入！");
		}
		// 绑定数据
		return getResultPath(NOTE_SET);
	}

	
	
	/**
	 * ajax 通知设置
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxNoteSet")
	public Map<String, String> ajaxNoteSet(@RequestParam("noteSetKey") String noteSetKey,
			@RequestParam("ckVal") String ckVal,
			HttpServletRequest request, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(
					AppConst.LOGIN_INFO_SESSION);
			if (loginInfo != null) {
				String[] arr = noteSetKey.split("_");
				String eventType = arr[0];
				String msgTypeCd = arr[1];
				Integer custId = loginInfo.getCustId();
				BizMessageConfBO bo = null;
				bo = msgConfBS.getConf(custId, eventType, msgTypeCd);
				boolean isAdd = false;
				if (bo == null) {
					isAdd = true;
					bo = new BizMessageConfBO();
					bo.setCustId(custId);
					bo.setEventType(eventType);
					bo.setMsgTypeCd(msgTypeCd);
					bo.setSysCreateTime(new Timestamp(new Date()
									.getTime()));
					bo.setSysCreateUser(loginInfo.getUserId());
					bo.setSysUpdateTime(new Timestamp(new Date()
									.getTime()));
					bo.setSysUpdateUser(loginInfo.getUserId());
				}
				if (ckVal.equals("on")) {
					bo.setIsEnable("1");
				} else {
					bo.setIsEnable("0");
				}
				
				if (isAdd) {
					messageManageBS.save(bo);
				} else {
					messageManageBS.update(bo);
				}
				
				
				jsonMap.put("reMes", "通知设置成功");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "通知设置失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	
	/**
	 * 消息管理
	 * 
	 * @throws BizException
	 */
	@RequestMapping(value = "/messageManage")
	public String messageManage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws BizException {
		String refereeCode = "";
		String refereeStatus = "";
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		}
		String sucMes =request.getParameter("sucMes");
		String errMes = request.getParameter("errMes");
		String code = "";
		// 设置公共数据
		setCommonData(request);
		if(StringUtils.isNotEmpty(sucMes)){
			sucMes = URLDecoder.decode(sucMes, "UTF-8");
			setAttribute(request, "sucMes", sucMes);
		}
		if(StringUtils.isNotEmpty(errMes)){
			errMes = URLDecoder.decode(errMes, "UTF-8");
			setAttribute(request, "errMes", errMes);
		}
		//setAttribute(request, "myVid", "userinfohead5");
		String myVid =request.getParameter("myVid");
		
		LoginInfo loginInfo = (LoginInfo) session
				.getAttribute(AppConst.LOGIN_INFO_SESSION);
		if (loginInfo != null){
			BizCustomerBO cust = custInfoBS.findCustById(loginInfo.getCustId());
			if(cust != null && StringUtils.isNotEmpty(cust.getRefereeCode())){
				refereeStatus = cust.getRefereeStatus();
				if(AppConst.REFEREE_STATUS_AGREE.equals(refereeStatus)){
					refereeCode = "?recommender="+cust.getRefereeCode();
					code = cust.getRefereeCode();
				}
			}
		}
		int serverPort = request.getServerPort();
		String url = "http://" 
				+ request.getServerName() + (serverPort == 80 ? "" : (":"+serverPort))
				+ request.getContextPath() 
				+ "/register/index.do"
				+ refereeCode;
		setAttribute(request, "myVid", myVid);	
		setAttribute(request, "url", url);
		setAttribute(request, "refereeCode", code);
		setAttribute(request, "refereeStatus", refereeStatus);
		
		boolean isMobile = MobileUtil.JudgeIsMoblie(request);
		if(isMobile){
			return getResultPath(MOBILE_PROMOTE);
		}
		return getResultPath(MESSAGE_MANAGE);
	}
	
	@RequestMapping(value = "/recommendedDetail")
	public String recommendedDetail(HttpServletRequest request, HttpServletResponse response) throws BizException {
		// 设置公共数据
		setCommonData(request);
		return getResultPath(RECOMMENDED_DETAIL);
	}

	@RequestMapping(value = "/applyReferee")
	public String applyReferee(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,@ModelAttribute CustInfoVO custInfo)
					throws BizException {
		LoginInfo loginInfo = (LoginInfo) session
				.getAttribute(AppConst.LOGIN_INFO_SESSION);
		 
		if (loginInfo != null){
			String sucMes = "已发送平台管理人员，工作人员将于近期内进行电话回访。";
//			String errMes = "不能为空";
			try {
				sucMes = URLEncoder.encode(sucMes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			}
//			if(StringUtils.isEmpty(custInfo.getBirthDateStr())){
//				errMes = "出生日期"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
//			if(StringUtils.isEmpty(custInfo.getSex())){
//				errMes = "性别"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
//			if(StringUtils.isEmpty(custInfo.getEducation())){
//				errMes = "学历"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
//			if(StringUtils.isEmpty(custInfo.getMarital())){
//				errMes = "婚姻状况"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
//			if(StringUtils.isEmpty(custInfo.getProvincial())){
//				errMes = "省份"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
//			if(StringUtils.isEmpty(custInfo.getCity())){
//				errMes = "城市"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
//			if(StringUtils.isEmpty(custInfo.getArea())){
//				errMes = "地区"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
//			if(StringUtils.isEmpty(custInfo.getAddress())){
//				errMes = "街道地址"+errMes;
//				return "redirect:/userMessage/messageManage.do?errMes="+errMes;
//			}
			BizCustomerBO cust = custInfoBS.findCustById(loginInfo.getCustId());
			if(AppConst.REFEREE_STATUS_INITIAL.equals(cust.getRefereeStatus())){
				cust.setRefereeStatus(AppConst.REFEREE_STATUS_APPLY);//申请中
			}else if(AppConst.REFEREE_STATUS_NOTAGREE_AGAIN.equals(cust.getRefereeStatus())){
				cust.setRefereeStatus(AppConst.REFEREE_STATUS_APPLY_AGAIN );//再次申请中
			}else if(AppConst.REFEREE_STATUS_NOTAGREE_TWICE.equals(cust.getRefereeStatus())){
				cust.setRefereeStatus(AppConst.REFEREE_STATUS_APPLY_THIRD );//再次申请中
			}
			cust.setTelephone(custInfo.getPhone());
			cust.setBirthDate(DateUtils.getDateByYMD(custInfo.getBirthDateStr()));
//			cust.setBirth(custInfo.getBirthDateStr());
			cust.setSex(custInfo.getSex());
			cust.setEducationCd(custInfo.getEducation());
			cust.setMaritalStatusCd(custInfo.getMarital());
			cust.setProvinceCd(custInfo.getProvincial());
			cust.setCityCd(custInfo.getCity());
			cust.setNationalityCd(custInfo.getArea());
			cust.setStreetAddress(custInfo.getAddress());
//			cust.setLiveStreetAddress(custInfo.getAddress());
			custInfoBS.update(cust);
			return "redirect:/userMessage/messageManage.do?sucMes="+sucMes;
		}
		return "redirect:/userMessage/messageManage.do";
	}
	
	@RequestMapping(value = "/loadImage")
	public String loadImage(HttpServletRequest request,HttpServletResponse response)
			throws BizException {
		// 设置公共数据
		setCommonData(request);
//		String url = "http://" 
//				+ request.getServerName() 
//				+ request.getContextPath() 
//				+ "/register/index.do"
//				+ ;
		//生成二维码
		QrCodeUtil.refereeUrl(request.getParameter("url"), response);
		return null;
	}
	
	/**
	 * 删除信息
	 */
	@RequestMapping(value = "/deleteMsg")
	public String deleteMsg(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			setCommonData(request);// 设置公共数据
			if (request.getSession().getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				Integer msgId = Integer.valueOf(request.getParameter("msgId"));
				this.messageManageBS.deleteMsg(msgId);
			}
			// 绑定数据
		} catch (Exception e) {
			 
		}
		return getResultPath(MESSAGE_MANAGE);
	}

	/**
	 * ajax 删除消息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxDelMsg")
	public Map<String, String> ajaxDelMsg(@RequestParam("msgId")
	int msgId, HttpServletRequest request, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				this.messageManageBS.deleteMsg(msgId);
				jsonMap.put("reMes", "信息删除成功");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "信息删除失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	
	/**
	 * ajax 消息置为已读
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxSetReaded")
	public Map<String, String> ajaxSetReaded(@RequestParam("msgId")
	int msgId, HttpServletRequest request, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				this.messageManageBS.setMsgReadStatus(msgId);
				
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}

	/**
	 * ajax 系统消息列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQuerySystemMessage")
	public Pager ajaxQuerySystemMsg(@RequestParam("requestPage")
	int requestPage, @RequestParam("pageSize")
	int pageSize, HttpSession session) {
		Pager pager = null;
		List<RecMessageVO> list = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = new Pager();
				list = messageManageBS.findInfoPagesByRecVO(loginInfo
						.getUserId(), 0, 0, pageSize);
				pager.setList(list);
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMyRecommend")
	public Pager ajaxQueryMyRecommend(@RequestParam("requestPage")
		int requestPage, @RequestParam("pageSize") int pageSize, HttpSession session) {
		Pager pager = new Pager();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = custInfoBS.findMyRecommend(requestPage, pageSize, loginInfo.getCustId());
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * ajax 私信消息列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryPrivMessage")
	public Pager ajaxQueryPrivMsg(@RequestParam("requestPage")
	int requestPage, @RequestParam("pageSize")
	int pageSize, HttpSession session) {
		Pager pager = null;
		List<RecMessageVO> list = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = new Pager();
				list = messageManageBS.findInfoPagesByRecVO(loginInfo
						.getUserId(), 1, 0, pageSize);
				pager.setList(list);
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * ajax 发件箱列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQuerySendMessage")
	public Pager ajaxQuerySendMsg(@RequestParam("requestPage")
	int requestPage, @RequestParam("pageSize")
	int pageSize, HttpSession session) {
		Pager pager = null;
		List<SendMessageVO> list = null;
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				pager = new Pager();
				list = messageManageBS.findInfoPagesBySendVO(loginInfo
						.getUserId(), 0, pageSize);
				pager.setList(list);
				return pager;
			}
		} catch (Exception e) {
			 
		}
		return pager;
	}

	/**
	 * ajax 发送消息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxSendMsg")
	public Map<String, String> ajaxSendMsg(@RequestParam("recUserId")
	int recUserId, @RequestParam("msgContent")
	String msgContent, HttpServletRequest request, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				// request.setCharacterEncoding("UTF-8");
				msgContent = new String(request.getParameter("msgContent")
						.getBytes("iso8859-1"), "utf-8");
				String msgTitle = new String(request.getParameter("msgTitle")
						.getBytes("iso8859-1"), "utf-8");
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				BizMessageInfoBO bo = new BizMessageInfoBO();
				bo.setSendUserId(loginInfo.getUserId());
				bo.setRecUserId(recUserId);
				bo.setTitleTxt(msgTitle);
				bo.setContentTxt(msgContent);
				bo.setMessageType(1);
				bo.setReadStatus(0);
				bo.setSysCreateTime(new Timestamp(new Date().getTime()));
				bo.setSysCreateUser(loginInfo.getUserId());
				bo.setSysUpdateTime(new Timestamp(new Date().getTime()));
				bo.setSysUpdateUser(loginInfo.getUserId());

				messageManageBS.save(bo);
				jsonMap.put("reMes", "信息发送成功");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("reMes", "信息发送失败，请重试");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}

	/**
	 * ajax 发送消息
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryNoteSet")
	public Map<String, String> ajaxQueryNoteSet( HttpServletRequest request, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
				.getAttribute(AppConst.LOGIN_INFO_SESSION);
				List<BizMessageConfBO> list = msgConfBS.getConfList(loginInfo.getCustId());
				
				for(BizMessageConfBO bo : list){	
					jsonMap.put(bo.getEventType() + "_" + bo.getMsgTypeCd(), bo.getIsEnable());	
				}
				jsonMap.put("size", list.size()+"");
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}
	
	
	/**
	 * ajax 系统消息条数
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxQueryMsgCnt")
	public Map<String, String> ajaxQueryMsgCnt( HttpServletRequest request, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			if (session.getAttribute(AppConst.LOGIN_INFO_SESSION) != null) {
				LoginInfo loginInfo = (LoginInfo) session
						.getAttribute(AppConst.LOGIN_INFO_SESSION);
				int cnt = messageManageBS.findMyMessageCnt(loginInfo.getUserId());
				jsonMap.put("cnt", String.valueOf(cnt));
				jsonMap.put("status", "1");
			}
		} catch (Exception e) {
			 
			jsonMap.put("cnt", "0");
			jsonMap.put("status", "0");
		}
		return jsonMap;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}
}