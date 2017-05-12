package com.qfw.bean.bussparams;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysCodeDict;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.vo.bussparams.LazyCodeDictDataModel;

@ViewScoped
@ManagedBean(name="codeDictManageBean")
public class CodeDictManageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;

	private SysCodeDict searchCodeDictVO = new SysCodeDict();
	
	private LazyDataModel<SysCodeDict> codeDictList;
	
	@ManagedProperty(value = "#{codeDictBS}")
	private ICodeDictBS codeDictBS;
	
	private SysCodeDict selectCodeDict;
	
	private Logger log = LogFactory.getInstance().getPlatformLogger();
	
	@PostConstruct
    public void init(){
		searchCodeDict();
	} 
	
	public void searchCodeDict() {
		try {
			super.search();
			codeDictList = new LazyCodeDictDataModel(searchCodeDictVO, codeDictBS);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag) || "edit".equalsIgnoreCase(operateFlag)){
			ViewOper.getSession().setAttribute("id", selectCodeDict.getCodeId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			String codeId = String.valueOf(selectCodeDict.getCodeId());
			String sql = "delete from SYS_CODE_DICT where CODE_ID = '"+codeId+"'";
			try {
				codeDictBS.getJdbcTemplate().execute(sql);
				codeDictList.setRowCount(codeDictList.getRowCount()-1);
			} catch (Exception e) {
				log.error("删除字典参数"+selectCodeDict.getCodeTypeName()+"异常：", e);
				alert("删除字典参数异常："+ e);
			}
		}
	}

	public SysCodeDict getSearchCodeDictVO() {
		return searchCodeDictVO;
	}

	public void setSearchCodeDictVO(SysCodeDict searchCodeDictVO) {
		this.searchCodeDictVO = searchCodeDictVO;
	}

	public LazyDataModel<SysCodeDict> getCodeDictList() {
		return codeDictList;
	}

	public void setCodeDictList(LazyDataModel<SysCodeDict> codeDictList) {
		this.codeDictList = codeDictList;
	}

	public ICodeDictBS getCodeDictBS() {
		return codeDictBS;
	}

	public void setCodeDictBS(ICodeDictBS codeDictBS) {
		this.codeDictBS = codeDictBS;
	}

	public SysCodeDict getSelectCodeDict() {
		return selectCodeDict;
	}

	public void setSelectCodeDict(SysCodeDict selectCodeDict) {
		this.selectCodeDict = selectCodeDict;
	}
	
}
