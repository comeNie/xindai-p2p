package com.qfw.common.util.web;

import java.util.List;

import javax.faces.model.SelectItem;

import com.qfw.common.tag.TagFunction;

public class SelectItemUtil {
	public static String getDictKey(String codeType,String dictValue){
		List<SelectItem> dicts = (List<SelectItem>)TagFunction.getSelectItems(codeType);
		if(dicts!=null && dicts.size() > 0){
			for(Object dict : dicts){
				SelectItem item = (SelectItem)dict;
				if(dictValue.equals(item.getValue())){
					return (String) item.getLabel();
				}
			}
		}
		return null;
	}
	public static String getDictValue(String codeType,String dictKey){
		List<SelectItem> dicts = (List<SelectItem>)TagFunction.getSelectItems(codeType);
		if(dicts!=null && dicts.size() > 0){
			for(Object dict : dicts){
				SelectItem item = (SelectItem)dict;
				if(dictKey.equals(item.getLabel())){
					return (String) item.getValue();
				}
			}
		}
		return null;
	}

}
