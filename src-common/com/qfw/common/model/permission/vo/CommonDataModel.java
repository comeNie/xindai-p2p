package com.qfw.common.model.permission.vo;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.model.BaseBO;

@SuppressWarnings("serial")
public class CommonDataModel<T> extends LazyDataModel<T> {

	private IBaseService baseService;
	private Class<T> clazz;
	public CommonDataModel(IBaseService baseService,Class<T> clazz){
		this.baseService = baseService;
		this.clazz = clazz;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {	
		List list = baseService.findObjectsByPages(first, pageSize, sortField, sortOrder, filters,this);
		if(list == null || list.isEmpty()){
			if(first>0){
				list = baseService.findObjectsByPages(first-pageSize, pageSize, sortField, sortOrder, filters,this);
			}
		}
		return list;	
	}
	
	@Override
	public Object getRowKey(T object) {
		return ((BaseBO)object).getRowKey();
	}
	@SuppressWarnings("unchecked")
	@Override
	public T getRowData(String rowKey) {
		// TODO Auto-generated method stub
		//Class<T> clazz =  (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		//return (T) baseService.find(Class, rowKey);
		Object obj = baseService.find(clazz, rowKey);
		return (T) obj;
	}
	
	

}
