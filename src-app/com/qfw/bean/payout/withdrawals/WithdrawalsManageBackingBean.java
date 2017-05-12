package com.qfw.bean.payout.withdrawals;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.primefaces.model.LazyDataModel;

import com.qfw.bizservice.payout.IWithdrawalsPayoutBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.AppConst;
import com.qfw.model.vo.payout.LazyWithDataModel;
import com.qfw.model.vo.payout.WithdrawalsResponseVO;
import com.qfw.model.vo.payout.WithdrawalsVO;

/**
 * 提现列表查询backingbean
 *
 * @author kyc
 */
@ViewScoped
@ManagedBean(name="withdrawalsManageBean")
public class WithdrawalsManageBackingBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{withdrawalsPayoutBS}")
	private IWithdrawalsPayoutBS payoutBS;
	private LazyDataModel<WithdrawalsResponseVO> tradeModel;
	private WithdrawalsVO draVO = new WithdrawalsVO();
	private WithdrawalsResponseVO draResVO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@PostConstruct
	public void init() {
		if(draVO != null){ 
			try {
				draResVO = new WithdrawalsResponseVO();
				tradeModel = new LazyWithDataModel(draVO, payoutBS);
			} catch (Exception e) {
				log.error("提现列表搜索异常：", e);
				alert("提现列表搜索异常："+e.getMessage());
			}
		}
	}
	
	public void export(){
		try {
			String status = draVO.getStatus();
			draVO.setStatus(AppConst.WITHDRAWALS_STATUS_SUCCESS);
			List<WithdrawalsResponseVO> list = payoutBS.findWithBOByVO(draVO);
			if(CollectionUtil.isNotEmpty(list)){
				List<String> idList = new ArrayList<String>();
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("提现");
				HSSFRow row = sheet.createRow(0);
				setCellValue(row, "序号","银行","地区（省）","地区（市/区）","支行名","开户名","卡号","金额","电话号码","备注");
				int i = 1;
				for (WithdrawalsResponseVO vo : list) {
					idList.add(vo.getId().toString());
					row = sheet.createRow(i);
					setCellValue(row, String.valueOf(i++),vo.getBankName(),vo.getAreaProvince(),vo.getAreaCity(),vo.getBank(),vo.getAccountName(),vo.getOutAccount(),vo.getTxAmt().toPlainString(),vo.getPhone(),"");
				}
				
				HttpServletResponse response = ViewOper.getResponse();
				HttpServletRequest request = ViewOper.getRequest();
				String filename = "导出提现数据("+DateUtils.getYmdOfToday()+").xls";// 设置下载时客户端Excel的名称
				// 请见：http://zmx.iteye.com/blog/622529
				filename = StringUtils.encodeFilename(filename, request);
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment;filename="
						+ filename);
				OutputStream ouputStream;
				try {
					ouputStream = response.getOutputStream();
					wb.write(ouputStream);
					ouputStream.flush();
					ouputStream.close();
					FacesContext context = FacesContext.getCurrentInstance();
					context.responseComplete();
					context.renderResponse();
					this.payoutBS.updateWithdrawalsStatus(AppConst.WITHDRAWALS_STATUS_EXPORT, idList);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					 
				}
				
			}else{
				HttpServletResponse response = ViewOper.getResponse();
				response.setCharacterEncoding("UTF-8");
				try {
					PrintWriter write = response.getWriter();
					write.write("<script>alert('没有可导出的提现列表数据');</script>");
					write.flush();
					write.close();
					FacesContext context = FacesContext.getCurrentInstance();
					context.responseComplete();
					context.renderResponse();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					 
				}
			}
			
			draVO.setStatus(status);
		} catch (BizException e) {
			
		}
	}
	private void setCellValue(HSSFRow row,String... value){
		HSSFCell cell = null;
		for (int i = 0; i < value.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(new HSSFRichTextString(value[i]));
		}
	}
	
	public void search(){
		super.search();
		init();
	}
	
	public void operate(){
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IWithdrawalsPayoutBS getPayoutBS() {
		return payoutBS;
	}

	public void setPayoutBS(IWithdrawalsPayoutBS payoutBS) {
		this.payoutBS = payoutBS;
	}

	public WithdrawalsVO getDraVO() {
		return draVO;
	}

	public void setDraVO(WithdrawalsVO draVO) {
		this.draVO = draVO;
	}

	public LazyDataModel<WithdrawalsResponseVO> getTradeModel() {
		return tradeModel;
	}

	public void setTradeModel(LazyDataModel<WithdrawalsResponseVO> tradeModel) {
		this.tradeModel = tradeModel;
	}

	public WithdrawalsResponseVO getDraResVO() {
		return draResVO;
	}

	public void setDraResVO(WithdrawalsResponseVO draResVO) {
		this.draResVO = draResVO;
	}

}
