package com.qfw.bean.postLoan;

/**
 * 生成贷后任务Bean
 * @author weiqs
 */
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.postloan.IPostLoanManageBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.util.web.WorkFlowBackingBean;
import com.qfw.model.AppConst;
import com.qfw.model.vo.postLoan.PostLoanManageVO;
import com.qfw.model.vo.postLoan.PostLoanTaskInfoVO;

@ViewScoped
@ManagedBean(name="addPostLoanBean")
public class AddPostLoanBean extends WorkFlowBackingBean{
    
	private static final long serialVersionUID = -2631989147972388606L;
	
	@ManagedProperty(value = "#{postLoanManageBS}")
	private IPostLoanManageBS postLoanManageBS;
	private PostLoanManageVO  postLoanManageVO = new PostLoanManageVO();
	
	private PostLoanTaskInfoVO infoVO = new PostLoanTaskInfoVO();
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	private String loanStatusCd ;
	
	@PostConstruct
    public void init(){
		try {
			Object planId = ViewOper.getSessionTmpAttr("planId");
			if(null!=planId&&(planId instanceof Integer)){
				postLoanManageVO = this.postLoanManageBS.findInfoById((Integer)planId);
				infoVO.setCustId(postLoanManageVO.getCustId());
				infoVO.setCustName(postLoanManageVO.getCustName());
				infoVO.setPostLoanStatus(Integer.valueOf(AppConst.POSTLOAN_STATUS_CD_ADD));
				infoVO.setPostloanGenerateType(Integer.valueOf(AppConst.POSTLOAN_TYPE_CD_SD));
				infoVO.setCreatorId(ViewOper.getUser().getUserId());
				infoVO.setCreatorName(ViewOper.getUser().getUserName());
				infoVO.setCreateDate(new Date());
			}
		} catch (Exception e) {
			 
		}
		
	}
	
	public String submit(){
		try {
			if(null==getSelectUser()||null == getSelectUser().getUserId()){
				this.alert("请选择客户经理");
				return null;
			}
			String mess = this.validateVO();
			if(mess.length()<=0){
				infoVO.setManageId(getSelectUser().getUserId());
				this.postLoanManageBS.savePostLoan(infoVO);
				this.alertInfo("提交成功");
				executeJS("alert('提交成功');closeParMainDialog();");
			}else{
				this.alert(mess);
				return null;
			}
		} catch (Exception e) {
			 
			this.alert("贷后任务生成失败，原因："+e.getMessage());
		}
		return null;
	}
	
	/**
     * 数据校验
     * @return
     */
    private String validateVO(){
    	StringBuffer mess = new StringBuffer();
    	if(null==infoVO.getStartDate()){
    		mess.append("开始时间不能为空<br/>");
    	}
    	if(null==infoVO.getFinishDate()){
    		mess.append("结束时间不能为空<br/>");
    	}
    	if(infoVO.getStartDate().after(infoVO.getFinishDate())){
    		mess.append("开始时间不能小于结束日期<br/>");
    	}
		if((new Date()).after(infoVO.getFinishDate())){
			mess.append("结束时间不能小于当前日期<br/>");
    	}
    	
    	return mess.toString();
    }
	
	public IPostLoanManageBS getPostLoanManageBS() {
		return postLoanManageBS;
	}

	public void setPostLoanManageBS(IPostLoanManageBS postLoanManageBS) {
		this.postLoanManageBS = postLoanManageBS;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public PostLoanManageVO getPostLoanManageVO() {
		return postLoanManageVO;
	}

	public void setPostLoanManageVO(PostLoanManageVO postLoanManageVO) {
		this.postLoanManageVO = postLoanManageVO;
	}

	public String getLoanStatusCd() {
		return loanStatusCd;
	}

	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}

	public PostLoanTaskInfoVO getInfoVO() {
		return infoVO;
	}

	public void setInfoVO(PostLoanTaskInfoVO infoVO) {
		this.infoVO = infoVO;
	}

}
