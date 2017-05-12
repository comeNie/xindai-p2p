package com.qfw.model.vo.postLoan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.postloan.IPostLoanTaskBS;
import com.qfw.common.exception.BizException;

@Service("postLoanTaskModel")
public class LazyPostLoanTaskModel extends LazyDataModel<PostLoanTaskInfoVO> {
	private LoanTaskSearchVO searchVO = new LoanTaskSearchVO();

	private Map<String, PostLoanTaskInfoVO> postLoanTasks = new HashMap<String, PostLoanTaskInfoVO>();

	@Autowired
	private IPostLoanTaskBS postLoanTaskBS;

	@Override
	public List<PostLoanTaskInfoVO> load(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, String> filters) {
		// TODO Auto-generated method stub

		try {
			List ls = postLoanTaskBS.findPostLoanTasks(searchVO, first,
					pageSize);

			setRowCount(ls.size());
			return ls;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		return null;
	}

	public LoanTaskSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(LoanTaskSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public IPostLoanTaskBS getPostLoanTaskBS() {
		return postLoanTaskBS;
	}

	public void setPostLoanTaskBS(IPostLoanTaskBS postLoanTaskBS) {
		this.postLoanTaskBS = postLoanTaskBS;
	}
	@Override
	public PostLoanTaskInfoVO getRowData(String rowKey) {
		return this.postLoanTasks.get(rowKey);
	}

	public Object getRowKey(PostLoanTaskInfoVO vo) {
		if(vo != null && vo.getTaskId()!=null){
			this.postLoanTasks.put(vo.getTaskId().toString(), vo);
			return vo.getTaskId();
		}
		return null;
	}
}
