package com.qfw.model.vo.postLoan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.postloan.IPostLoanTaskBS;

@Service("postLoanCheckModel")
public class LazyPostLoanCheckModel extends LazyDataModel<PostLoanCheckVO> {

	private Integer taskId;

	private Map<String, PostLoanCheckVO> postLoanChecks = new HashMap<String, PostLoanCheckVO>();

	@Autowired
	private IPostLoanTaskBS postLoanTaskBS;


	public LazyPostLoanCheckModel(){
		this.setRowCount(Integer.MAX_VALUE);
	}
	
	
	@Override
	public List<PostLoanCheckVO> load(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map filters) {
		// TODO Auto-generated method stub

		List ls = this.getPostLoanTaskBS().getCheckByTaskId(this.taskId);
		this.setRowCount(ls.size());
		return ls;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public IPostLoanTaskBS getPostLoanTaskBS() {
		return postLoanTaskBS;
	}

	public void setPostLoanTaskBS(IPostLoanTaskBS postLoanTaskBS) {
		this.postLoanTaskBS = postLoanTaskBS;
	}


	@Override
	public PostLoanCheckVO getRowData(String rowKey) {
		return this.postLoanChecks.get(rowKey);
	}

	public Object getRowKey(PostLoanCheckVO vo) {
		if (vo != null && vo.getLogId() != null) {
			this.postLoanChecks.put(vo.getLogId().toString(), vo);
			return vo.getLogId();
		}
		return null;
	}
}
