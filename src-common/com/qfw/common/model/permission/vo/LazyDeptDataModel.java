package com.qfw.common.model.permission.vo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;

import com.qfw.common.bizservice.permission.IDeptBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.util.StringUtils;

@SuppressWarnings("serial")
public class LazyDeptDataModel extends LazyDataModel<SysDept>{

	private SysDeptVO deptVO;
	
	private IDeptBS deptBS;
	
	private List<SysDept> depts;
	
	Logger log = LogFactory.getInstance().getPlatformLogger();
	
	public LazyDeptDataModel(SysDeptVO deptVO,IDeptBS deptBS){
		this.deptVO = deptVO;
		this.deptBS = deptBS;
	}
	
	@Override
	public List<SysDept> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			depts = deptBS.findDeptsPagesByVO(deptVO, first, pageSize);
			setRowCount(deptBS.findDeptsCountByVO(deptVO));
		} catch (Exception e) {
			log.error("获取机构翻页信息异常：", e);
		}	
		return depts;
	}
	
	@Override
	public SysDept getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for(SysDept dept : depts){
			if(String.valueOf(dept.getDeptId()).equals(rowKey))
				return dept;
		}
		SysDept sysDept = new SysDept();
		try {
			sysDept = deptBS.findDeptById(Integer.valueOf(rowKey));
		} catch (BizException e) {
			log.error("通过ID获取机构异常：", e);
		}
		return sysDept;
	}
	@Override
	public Object getRowKey(SysDept dept) {
		return dept.getDeptId();
	}

	public SysDeptVO getDeptVO() {
		return deptVO;
	}

	public void setDeptVO(SysDeptVO deptVO) {
		this.deptVO = deptVO;
	}

	public IDeptBS getDeptBS() {
		return deptBS;
	}

	public void setDeptBS(IDeptBS deptBS) {
		this.deptBS = deptBS;
	}

	public List<SysDept> getDepts() {
		return depts;
	}

	public void setDepts(List<SysDept> depts) {
		this.depts = depts;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	
}
