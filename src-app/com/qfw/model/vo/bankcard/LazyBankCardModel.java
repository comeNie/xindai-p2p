package com.qfw.model.vo.bankcard;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.bankcard.IBankCardBS;
import com.qfw.common.log.LogFactory;
import com.qfw.model.bo.BizCardBO;

@SuppressWarnings("serial")
public class LazyBankCardModel extends LazyDataModel<BizCardBO>{

	private BankCardVO cardVO;
	private IBankCardBS cardBS;
	private List<BizCardBO> boList;
	
	Logger log = LogFactory.getInstance().getBusinessLogger();
	
	public LazyBankCardModel(BankCardVO cardVO,IBankCardBS cardBS){
		this.cardVO = cardVO;
		this.cardBS = cardBS;
	}

	@Override
	public List<BizCardBO> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
//			boList = this.cardBS.findCardBOPagesByVO(cardVO, first, pageSize);
//			setRowCount(this.cardBS.findCardCountByVO(cardVO));
		} catch (Exception e) {
			log.error("获取理财卡翻页信息异常：", e);
		}
		return boList;
	}
	
	public BizCardBO getRowData(String rowKey){
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for( BizCardBO bo : boList){
			if(String.valueOf(bo.getId()).equals(rowKey)){
				return bo;
			}
		}
		BizCardBO bo = new BizCardBO();
		try {
//			bo = this.cardBS.findCardBOById(Integer.valueOf(rowKey));
		} catch (Exception e) {
			log.error("通过ID获取理财卡异常：", e);
		}
		return bo;
	}
	
	@Override
	public Object getRowKey(BizCardBO bo) {
		return bo.getId();
	}
}

