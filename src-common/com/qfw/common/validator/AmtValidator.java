package com.qfw.common.validator;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class AmtValidator implements Validator {

	private String label;
	private int len;
	
	
	public AmtValidator(String label, int len) {
		super();
		this.label = label;
		this.len = len;
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		FacesMessage message = null;
		if(value == null){
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,label+"金额不能为空", null);
			throw new ValidatorException(message);
		}
		
		if(value instanceof BigDecimal){
			if(((BigDecimal)value).scale()>len){
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR,label+"金额小数点位数为"+len, null);
				throw new ValidatorException(message);
			}
		}else{
			String s = String.valueOf(value);
			
		}

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}
	
	

}
