package com.qfw.bizservice.postloan;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.model.vo.postLoan.LoanTaskSearchVO;
import com.qfw.model.vo.postLoan.PostLoanCheckVO;
import com.qfw.model.vo.postLoan.PostLoanTaskInfoVO;

public interface IPostLoanTaskBS {
	public List<PostLoanTaskInfoVO> findPostLoanTasks(LoanTaskSearchVO vo,
			int first, int pageSize) throws BizException;
	public List<PostLoanCheckVO> getCheckByTaskId(Integer taskId) ;
	public void addCheck(PostLoanCheckVO vo);
	public void updateCheck(PostLoanCheckVO vo);
	public void deleteCheck(Integer logId);
	public void updateCheckResult(PostLoanTaskInfoVO vo);
}
