package com.qfw.bizservice.postloan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.postloan.IPostLoanTaskBS;
import com.qfw.common.exception.BizException;
import com.qfw.dao.postloan.IPostLoanTaskDAO;
import com.qfw.model.vo.postLoan.LoanTaskSearchVO;
import com.qfw.model.vo.postLoan.PostLoanCheckVO;
import com.qfw.model.vo.postLoan.PostLoanTaskInfoVO;
@Service("postLoanTaskBS")
public class PostLoanTaskBSImpl implements IPostLoanTaskBS {
	@Autowired
	private IPostLoanTaskDAO postLoanTaskDao;

	public List<PostLoanTaskInfoVO> findPostLoanTasks(LoanTaskSearchVO vo,
			int first, int pageSize) throws BizException {
		// TODO Auto-generated method stub
		return this.getPostLoanTaskDao().findPostLoanTasks(vo, first, pageSize);
	}

	public IPostLoanTaskDAO getPostLoanTaskDao() {
		return postLoanTaskDao;
	}

	public void setPostLoanTaskDao(IPostLoanTaskDAO postLoanTaskDao) {
		this.postLoanTaskDao = postLoanTaskDao;
	}

	public List<PostLoanCheckVO> getCheckByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		return this.postLoanTaskDao.getCheckByTaskId(taskId);
	}

	public void addCheck(PostLoanCheckVO vo) {
		// TODO Auto-generated method stub
		this.postLoanTaskDao.addCheck(vo);
	}

	public void updateCheck(PostLoanCheckVO vo) {
		// TODO Auto-generated method stub
		this.postLoanTaskDao.updateCheck(vo);
	}

	public void deleteCheck(Integer logId) {
		// TODO Auto-generated method stub
		this.postLoanTaskDao.deleteCheck(logId);
	}
	public void updateCheckResult(PostLoanTaskInfoVO vo){
		this.postLoanTaskDao.updateCheckResult(vo);
	}
}
