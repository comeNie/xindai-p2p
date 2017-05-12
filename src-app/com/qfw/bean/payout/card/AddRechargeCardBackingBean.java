package com.qfw.bean.payout.card;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.bizservice.payout.card.IRechargeCardBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizRechargeCardBO;

@ViewScoped
@ManagedBean(name = "addRechargeCardBean")
public class AddRechargeCardBackingBean extends BaseBackingBean{
	
	private static final long serialVersionUID = 6645962607435522022L;
	
	@ManagedProperty(value = "#{rechargePayoutBS}")
	private IRechargePayoutBS payoutBS;
	@ManagedProperty(value = "#{rechargeCardBS}")
	private IRechargeCardBS rechargeCardBS;
	private String operateFlag;
	private BizRechargeCardBO cardBO = new BizRechargeCardBO();
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	private String num;
	
	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("carDId");
		if(id!=null){
			try {
				cardBO = this.payoutBS.findCardBOById((Integer)id);
			} catch (BizException e) {
				log.error("通过ID获取机构异常：", e);
			}
		}
	}
	
	public String operate(){
		
		if("add".equals(operateFlag)){
			/*if(!checkDeptCode(cardBO.getCardCd(),null)){
				alert("充值卡卡号已经存在，请重新输入！");
				cardBO.setCardCd("");
				return null;
			}*/
			if(null == num || num.length()<=0 || num.equals("0")){
				MessagesController.addInfo("充值卡新增失败：张数错误","充值卡新增失败：张数错误");
				return null;
			}
			if(cardBO.getEndDate().before(cardBO.getStartDate())){
				MessagesController.addInfo("充值卡新增失败：有效截止时间不能小于有效起始时间","充值卡新增失败：有效截止时间不能小于有效起始时间");
				return null;
			}
			if(null==cardBO.getAmt()){
				MessagesController.addInfo("充值卡新增失败：金额不能为空","充值卡新增失败：金额不能为空");
				return null;
			}
			Date time = null;
			String fix = "";
			// 创建一个新的excel
			HSSFWorkbook wb = new HSSFWorkbook();
			// 创建sheet页
			HSSFSheet sheet = wb.createSheet("充值卡");
			/*// 创建header页
			HSSFHeader header = sheet.getHeader();
			// 设置标题居中
			header.setCenter("充值卡");*/
			HSSFRow row = sheet.createRow(0);
			//HSSFCell cell = row.createCell(0);
			setCellValue(row, "卡号","密码","金额","有效起始时间","有效截止时间");
			List<String> cardCds = rechargeCardBS.getCardNums(Integer.valueOf(num));
			String batchNo = rechargeCardBS.getBatchCardNum();
			String password = null;
			for (int i=1; i<= Integer.valueOf(num);i++) {
				time = new Date();
				fix = String.valueOf(time.getTime());
				password = rechargeCardBS.getPassword(IRechargeCardBS.pass_length);
				cardBO.setCardCd(cardCds.get(i-1));
				cardBO.setCardPwd(MD5Utils.getMD5Str(password));
				cardBO.setStatus(AppConst.RECHARGE_STATUS_INIT);
				cardBO.setSysCreateTime(new Date());
				cardBO.setSysCreateUser(ViewOper.getUser().getUserId());
				cardBO.setSysUpdateTime(new Date());
				cardBO.setSysUpdateUser(ViewOper.getUser().getUserId());
				cardBO.setBatchNo(batchNo);
				this.payoutBS.save(cardBO);
				row = sheet.createRow(i);
				setCellValue(row, cardBO.getCardCd(),password,cardBO.getAmt().toPlainString(),DateUtils.getYmd(cardBO.getStartDate()),DateUtils.getYmd(cardBO.getEndDate()));
			}
			
			HttpServletResponse response = ViewOper.getResponse();
			HttpServletRequest request = ViewOper.getRequest();
			String filename = "导出充值卡("+DateUtils.getYmdOfToday()+").xls";// 设置下载时客户端Excel的名称
			filename = StringUtils.encodeFilename(filename, request);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream;
			try {
				ouputStream = response.getOutputStream();
				wb.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
				FacesContext context = FacesContext.getCurrentInstance();
				context.responseComplete();
				context.renderResponse();
				this.payoutBS.updateRechargeCardStatus(AppConst.RECHARGE_STATUS_EXPORT, cardCds);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		}
		return null;
	}
	
	private void setCellValue(HSSFRow row,String... value){
		HSSFCell cell = null;
		for (int i = 0; i < value.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(new HSSFRichTextString(value[i]));
		}
	}
	public boolean checkDeptCode(String cardCD,Integer id){
		List<BizRechargeCardBO> list = new ArrayList<BizRechargeCardBO>();
//		try {
//			list = this.payoutBS.checkDeptCode(cardCD,id);
//		} catch (BizException e) {
//			log.error("验证理财卡号异常：", e);
//			alert("验证机构代号异常："+e);
//		}
//		if(list != null && list.size() > 0){
			return true;
//		}else{
//			return false;
//		}
	}

	public IRechargePayoutBS getPayoutBS() {
		return payoutBS;
	}

	public void setPayoutBS(IRechargePayoutBS payoutBS) {
		this.payoutBS = payoutBS;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public BizRechargeCardBO getCardBO() {
		return cardBO;
	}

	public void setCardBO(BizRechargeCardBO cardBO) {
		this.cardBO = cardBO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public IRechargeCardBS getRechargeCardBS() {
		return rechargeCardBS;
	}

	public void setRechargeCardBS(IRechargeCardBS rechargeCardBS) {
		this.rechargeCardBS = rechargeCardBS;
	}
}
