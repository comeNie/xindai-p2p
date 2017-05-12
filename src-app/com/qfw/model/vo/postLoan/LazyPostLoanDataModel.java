package com.qfw.model.vo.postLoan;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.postloan.IPostLoanManageBS;
import com.qfw.common.log.LogFactory;

@SuppressWarnings("serial")
public class LazyPostLoanDataModel extends LazyDataModel<PostLoanManageVO> {

	private PostLoanSearchVO searchVO;
	private IPostLoanManageBS postLoanManageBS;
	private List<PostLoanManageVO> postLoanVOList ;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyPostLoanDataModel(PostLoanSearchVO searchVO, IPostLoanManageBS postLoanManageBS){
		this.searchVO = searchVO;
		this.postLoanManageBS = postLoanManageBS;
	}
	
	@Override
	public List<PostLoanManageVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			postLoanVOList = postLoanManageBS.findInfoPagesByVO(searchVO, first, pageSize);
			setRowCount(this.postLoanManageBS.getCountByVO(searchVO));
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.error("贷后信息异常：", e);
			}
		}
		return postLoanVOList;
	}
	
	@Override
	public PostLoanManageVO getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)|| (null == postLoanVOList || postLoanVOList.size()<=0)){
			return null;
		}
		for(PostLoanManageVO postLoanManageVO : postLoanVOList){
			if(String.valueOf(postLoanManageVO.getPlanId()).equals(rowKey))
				return postLoanManageVO;
		}
		PostLoanManageVO vo = new PostLoanManageVO();
		try {
			vo = postLoanManageBS.findInfoById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取异常：", e);
		}
		return vo;
	}
	
	@Override
	public Object getRowKey(PostLoanManageVO infoVO) {
		return infoVO.getPlanId();
	}


	public PostLoanSearchVO getSearchVO() {
		return searchVO;
	}

	public void setSearchVO(PostLoanSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public IPostLoanManageBS getPostLoanManageBS() {
		return postLoanManageBS;
	}

	public void setPostLoanManageBS(IPostLoanManageBS postLoanManageBS) {
		this.postLoanManageBS = postLoanManageBS;
	}

	public List<PostLoanManageVO> getPostLoanVOList() {
		return postLoanVOList;
	}

	public void setPostLoanVOList(List<PostLoanManageVO> postLoanVOList) {
		this.postLoanVOList = postLoanVOList;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
