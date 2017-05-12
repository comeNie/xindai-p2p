package com.qfw.common.model;

import javax.persistence.Transient;


public abstract class BaseBO {
	
	
	/*protected Date sysCreateTime;
	protected Date sysUpdateTime;
	protected String sysUpdateUser;
	protected String remark;*/
	
	
	@Transient
	public abstract String getRowKey();
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;		
		if(!(obj instanceof BaseBO))
			return false;		
		return this.getRowKey().equals(((BaseBO)obj).getRowKey());
	}

	@Override
	public int hashCode() {
		int hash = 1;		
	    return hash * 31 + getRowKey().hashCode();
	}
}
