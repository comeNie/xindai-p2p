package com.qfw.common.component.pagination;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.qfw.common.util.StringUtils;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js")
})
public class Pagination extends UIComponentBase {
	
	private static final String BREAK_FLAG = "...";
	private static final String COMPONENT_FAMILY = "com.qfw.common.component";
	public Pagination(){
		
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		if(getRendered()){
			int count = getCount();
			int pageSize = getPageSize();
			int curPage = getCurPage();
			if(curPage ==0){
				curPage = 1;
			}
			String id = getId();
			String href = getHref();
			String mark = href.indexOf("?") == -1 ? "?" : "&";
			String onclick = getOnclick();
			int totalPage = (count+pageSize-1)/pageSize;
			if(totalPage > 1){
				int pageNumsSize = totalPage < 10 ? totalPage : 10;
				String[] pageNums = new String[pageNumsSize];
				ResponseWriter writer = context.getResponseWriter();
				
				if(totalPage<10){
					for(int i = 0;i<totalPage;i++){
						pageNums[i] = String.valueOf(i+1);
					}
				}else{
					if(curPage <= 5){
						for(int i = 0;i<9;i++){
							pageNums[i] = String.valueOf(i+1);
						}
						pageNums[9] = BREAK_FLAG;
					}else{
						pageNums[0] = "1";
						pageNums[1] = "2";
						pageNums[2] = BREAK_FLAG;
						if(totalPage-4 > curPage){
							for(int i=-2;i<4;i++){
								pageNums[5+i] = String.valueOf(curPage+i);
							}
							pageNums[9] = BREAK_FLAG;
						}else{
							int j = 0;
							for(int i=9;i>=3;i--){
								pageNums[i] = String.valueOf(totalPage-j);
								j++;
							}
						}
					}
				}
				
				
				writer.startElement("div", null);
				if(StringUtils.isNotEmpty(id)){
					writer.writeAttribute("id", id, null);
				}
				
				//编写上一页start
				writer.writeAttribute("class", "pagination", null);
				writer.startElement("a", null);
				if(curPage != 1){
					writer.writeAttribute("class", "J_SearchAsync prev", null);
					writer.writeAttribute("href", href+mark+"pageNum="+(curPage-1), null);
					if(StringUtils.isNotEmpty(onclick)){
						writer.writeAttribute("onclick", onclick, null);
					}
				}else{
					writer.writeAttribute("class", "disable", null);
				}
				writer.writeText("上一页", null);
				writer.endElement("a");
				//编写上一页end
				for(int i=0;i<pageNumsSize;i++){
					String pageNum = pageNums[i];
					if(BREAK_FLAG.equals(pageNum)){
						encodeBreak(writer);
					}else{
						encodePageNum(writer, Integer.valueOf(pageNum), curPage,href,mark);
					}
				}
				
				writer.startElement("a", null);
				//编写下一页start
				if(curPage != totalPage){
					writer.writeAttribute("class", "J_SearchAsync next", null);
					writer.writeAttribute("href", href+mark+"pageNum="+(curPage+1), null);
					if(StringUtils.isNotEmpty(onclick)){
						writer.writeAttribute("onclick", onclick, null);
					}
				}else{
					writer.writeAttribute("class", "disable", null);
				}
				writer.writeText("下一页", null);
				writer.endElement("a");
				writer.endElement("div");
				//编写下一页end
			}
		}
	}
	private void encodePageNum(ResponseWriter writer,int pageNum,int curPage,String href,String mark) throws IOException{
		writer.startElement("a", null);
		
		if(pageNum==curPage){
			writer.writeAttribute("class", "page-cur", null);
		}else{
			writer.writeAttribute("class", "J_SearchAsync", null);
			writer.writeAttribute("href", href+mark+"pageNum="+pageNum, null);
			if(StringUtils.isNotEmpty(getOnclick())){
				writer.writeAttribute("onclick", getOnclick(), null);
			}
		}
		writer.writeText(pageNum, null);
		writer.endElement("a");
	}
	
	private void encodeBreak(ResponseWriter writer) throws IOException{
		writer.startElement("span", null);
		writer.writeAttribute("class", "break", null);
		writer.writeText(BREAK_FLAG, null);
		writer.endElement("span");
	}

	public Integer getPageSize() {
		return (Integer) getStateHelper().eval("pageSize");
	}
	public void setPageSize(Integer pageSize) {
		getStateHelper().put("pageSize", pageSize);
	}
	public Integer getCurPage() {
		return (Integer) getStateHelper().eval("curPage");
	}
	public void setCurPage(Integer curPage) {
		getStateHelper().put("curPage", curPage);
	}
	public Integer getCount() {
		return (Integer) getStateHelper().eval("count");
	}
	public void setCount(Integer count) {
		getStateHelper().put("count", count);
	}

	public String getHref() {
		return (String) getStateHelper().eval("href");
	}

	public void setHref(String href) {
		getStateHelper().put("href", href);
	}

	public Boolean getRendered() {
		Boolean rendered = (Boolean) getStateHelper().eval("rendered");
		if(rendered == null){
			return true;
		}
		return rendered;
	}

	public void setRendered(Boolean rendered) {
		getStateHelper().put("rendered", rendered);
	}

	public String getId() {
		return (String) getStateHelper().eval("id");
	}

	public void setId(String id) {
		getStateHelper().put("id", id);
	}
	public String getOnclick() {
		return (String) getStateHelper().eval("onclick");
	}

	public void setOnclick(String onclick) {
		getStateHelper().put("onclick", onclick);
	}
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return COMPONENT_FAMILY;
	}
	
	
	
}
