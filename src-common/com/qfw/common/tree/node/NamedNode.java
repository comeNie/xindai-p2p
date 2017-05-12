package com.qfw.common.tree.node;

import java.io.Serializable;

public class NamedNode implements Serializable {
	protected String type;//节点类型
	protected String name;//节点名称
	protected String code;//节点代码
    protected Boolean isSelect;//是否选中

    public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Boolean getIsSelect() {
		return isSelect;
	}


	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}

}
