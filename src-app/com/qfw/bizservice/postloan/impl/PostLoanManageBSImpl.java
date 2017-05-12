package com.qfw.bizservice.postloan.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.postloan.IPostLoanManageBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.dao.postloan.IPostLoanManageDAO;
import com.qfw.dao.postloan.IPostLoanTaskDAO;
import com.qfw.model.vo.postLoan.PostLoanManageVO;
import com.qfw.model.vo.postLoan.PostLoanSearchVO;
import com.qfw.model.vo.postLoan.PostLoanTaskInfoVO;

/**
 * 贷后管理bs
 *
 * @author weiqs
 */
@Service("postLoanManageBS")
public class PostLoanManageBSImpl extends BaseServiceImpl implements IPostLoanManageBS {

	@Autowired
	private IPostLoanManageDAO postLoanManageDAO;
	@Autowired
	private IPostLoanTaskDAO postLoanTaskDAO;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();

	@Override
	public PostLoanManageVO findInfoById(Integer id) {
		return this.postLoanManageDAO.findInfoByID(id);
	}

	@Override
	public List<PostLoanManageVO> findInfoPagesByVO(PostLoanSearchVO searchVO,
			int first, int pageSize) throws BizException {
		try {
			 List<PostLoanManageVO> result= this.postLoanManageDAO.findInfoPagesByVO(searchVO, first, pageSize);
			 if(null!=result && result.size()>0){
				 return result;
			 }
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
			throw new BizException(e);
		}
		return null;
	}

	@Override
	public int getCountByVO(PostLoanSearchVO searchVO) {
		int num = 0;
		try {
			num = this.postLoanManageDAO.getCountByVO(searchVO);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.error(e.getMessage());
			}
		}
		return num;
	}

	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void savePostLoan(PostLoanTaskInfoVO infoVO) throws BizException{
		try {
			this.postLoanTaskDAO.addPostLoanTask(infoVO);
		} catch (Exception e) {
			 
			log.error("生成贷后任务失败，原因：",e);
			throw new BizException(e);
		}
	}
	

}
