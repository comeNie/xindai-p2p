package com.qfw.bean.postLoan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;

import com.qfw.bizservice.postloan.IPostLoanTaskBS;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.postLoan.LazyPostLoanCheckModel;
import com.qfw.model.vo.postLoan.LazyPostLoanTaskModel;
import com.qfw.model.vo.postLoan.LoanTaskSearchVO;
import com.qfw.model.vo.postLoan.PostLoanCheckVO;
import com.qfw.model.vo.postLoan.PostLoanTaskInfoVO;

/**
 * 贷后任务管理Bean
 * 
 * @author galford
 */
@ViewScoped
@ManagedBean(name = "postLoanTaskManageBean")
public class PostLoanTaskMagageBean extends BaseBackingBean {

	@ManagedProperty(value = "#{postLoanTaskModel}")
	private LazyPostLoanTaskModel model;

	@ManagedProperty(value = "#{postLoanCheckModel}")
	private LazyPostLoanCheckModel checkModel;

	private String managerName;

	private String operFlag = "add";

	private String oper = "view";

	private PostLoanTaskInfoVO selectedTask;

	private PostLoanCheckVO checkVo = new PostLoanCheckVO();

	private SysDept dept = new SysDept();

	@PostConstruct
	public void init() {

		String loanStatusCd = ViewOper.getRequestParameter("loanStatusCd");
		Object selectedTask = ViewOper.getSessionTmpAttr("selectedTask");
		this.oper = (String) ViewOper.getSessionTmpAttr("oper");
		if (this.model != null) {
			this.model.setSearchVO(new LoanTaskSearchVO());
			if (!StringUtils.isEmpty(loanStatusCd)) {
				this.model.getSearchVO().setPostLoanStatus(
						Integer.parseInt(loanStatusCd));
			}
			if (this.model.getSearchVO() != null) {
				this.model.getSearchVO().setManager(new SysUser());
			}

		}
		if (selectedTask != null) {
			this.selectedTask = (PostLoanTaskInfoVO) selectedTask;
			if (this.getSelectedTask() != null) {
				this.checkModel.setTaskId(this.getSelectedTask().getTaskId());
			}
		}
	}

	public String postLoanCheck() {
		ViewOper.getSession().setAttribute("selectedTask", this.selectedTask);
		ViewOper.getSession().setAttribute("oper", "check");

		return "postLoanCheck";
	}

	public String postLoanView() {
		ViewOper.getSession().setAttribute("selectedTask", this.selectedTask);
		ViewOper.getSession().setAttribute("oper", "view");
		return "postLoanCheck";
	}

	public SysDept getDept() {
		return dept;
	}

	public void setDept(SysDept dept) {
		this.dept = dept;
	}

	public LazyPostLoanTaskModel getModel() {
		return model;
	}

	public void setModel(LazyPostLoanTaskModel model) {
		this.model = model;
	}

	public void cleanManager() {
		this.model.getSearchVO().setManager(new SysUser());
		this.setManagerName("");
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void selectManager() {
		this.setManagerName(this.getModel().getSearchVO().getManager()
				.getUserName());
	}

	public PostLoanTaskInfoVO getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(PostLoanTaskInfoVO selectedTask) {
		this.selectedTask = selectedTask;
	}

	public LazyPostLoanCheckModel getCheckModel() {
		return checkModel;
	}

	public void setCheckModel(LazyPostLoanCheckModel checkModel) {
		this.checkModel = checkModel;
	}

	public PostLoanCheckVO getCheckVo() {
		return checkVo;
	}

	public void setCheckVo(PostLoanCheckVO checkVo) {
		this.checkVo = checkVo;
	}

	public List getSelectedTasks() {
		List ls = new ArrayList();
		if (this.selectedTask != null)
			ls.add(this.selectedTask);
		return ls;
	}

	public void addCheck() {
		if (this.operFlag.equals("add")) {
			try {
				this.checkVo.setTaskId(this.getSelectedTask().getTaskId());
				this.getCheckModel().getPostLoanTaskBS().addCheck(this.checkVo);
				this.alert("保存成功!");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.alert("操作失败，请与管理员联系");
			}
		} else {
			try {
				this.getCheckModel().getPostLoanTaskBS().updateCheck(
						this.checkVo);
				this.alert("保存成功!");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.alert("操作失败，请与管理员联系");
			}

		}

	}

	public void deleteCheck() {
		try {
			this.getCheckModel().getPostLoanTaskBS().deleteCheck(
					this.checkVo.getLogId());
			this.alert("删除成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.alert("操作失败，请与管理员联系");
		}
	}

	public void loadCheckInfo() {
		String oper = ViewOper.getRequestParameter("oper");
		if (!StringUtils.isEmpty(oper) && oper.equals("update")) {
		} else {
			this.checkVo = new PostLoanCheckVO();
			this.checkVo.setTaskId(this.getSelectedTask().getTaskId());
		}
		this.operFlag = oper;
	}

	public void updateCheckResult() {
		try {
			this.getCheckModel().getPostLoanTaskBS().updateCheckResult(
					this.selectedTask);
			this.alert("保存成功!");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.alert("操作失败，请与管理员联系");
		}
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

}
