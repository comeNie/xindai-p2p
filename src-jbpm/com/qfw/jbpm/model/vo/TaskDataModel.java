package com.qfw.jbpm.model.vo;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.common.model.vo.PageList;
import com.qfw.common.util.ApplicationContextUtil;
import com.qfw.jbpm.bizservice.IFlowBS;

public class TaskDataModel extends LazyDataModel<TaskVO> {
	
	private FilterTaskVO filterVO;
	
	private List<TaskVO> tasks;
	
	public TaskDataModel(FilterTaskVO filterVO){
		this.filterVO = filterVO;
	}
	 
	@Override
	public List<TaskVO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		IFlowBS flowBS = (IFlowBS) ApplicationContextUtil.getBean("flowBS");
		PageList<TaskVO> pl = flowBS.findMyTask(filterVO, first, pageSize);
		tasks = pl.getData();
		setRowCount(pl.getCount());
		return tasks;
	}

	@Override
	public Object getRowKey(TaskVO object) {
		return object.getExecutionId();
	}

	public FilterTaskVO getFilterVO() {
		return filterVO;
	}

	public void setFilterVO(FilterTaskVO filterVO) {
		this.filterVO = filterVO;
	}
	

}
