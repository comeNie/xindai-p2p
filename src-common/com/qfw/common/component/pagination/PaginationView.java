package com.qfw.common.component.pagination;

import java.io.IOException;

import javax.el.MethodExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js")
})
public class PaginationView extends UIComponentBase {
	
	private static final String BREAK_FLAG = "...";
	private static final String COMPONENT_FAMILY = "com.qfw.common.component";
	public PaginationView(){
		
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		this.getChildren().clear();
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
				
				HtmlOutputLink link = new HtmlOutputLink();
				this.getChildren().add(link);
				if(curPage != 1){
					link.setStyleClass("J_SearchAsync prev");
					link.setValue(href+mark+"pageNum="+(curPage-1));
					
				}else{
					link.setStyleClass("disable");
				}
				HtmlOutputText text = new HtmlOutputText();
				text.setValue("上一页");
				link.getChildren().add(text);
				//编写上一页end
				for(int i=0;i<pageNumsSize;i++){
					String pageNum = pageNums[i];
					if(BREAK_FLAG.equals(pageNum)){
						encodeBreak();
					}else{
						encodePageNum(context, Integer.valueOf(pageNum), curPage, href, mark);
					}
				}
				
				//编写下一页start
				link = new HtmlOutputLink();
				this.getChildren().add(link);
				if(curPage != totalPage){
					link.setStyleClass("J_SearchAsync next");
					link.setValue(href+mark+"pageNum="+(curPage+1));
				}else{
					link.setStyleClass("disable");
				}
				text = new HtmlOutputText();
				text.setValue("下一页");
				link.getChildren().add(text);
			}
		}
	}
	private  MethodExpression createMethod(FacesContext context,String url){
		return context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(),url, String.class, new Class<?>[0]);
	}
	
	private MethodBinding createMethodBinding(FacesContext context,String url){
		return context.getApplication().createMethodBinding(url, null);
	}
	private void encodePageNum(FacesContext context,int pageNum,int curPage,String href,String mark) throws IOException{
		HtmlOutputLink link = new HtmlOutputLink();
		
		if(pageNum==curPage){
			link.setStyleClass("page-cur");
		}else{
			link.setStyleClass("J_SearchAsync");
			HtmlOutputText text = new HtmlOutputText();
			text.setValue(pageNum);
			link.getChildren().add(text);
			link.setValue(href+mark+"pageNum="+pageNum);
		}
		this.getChildren().add(link);
	}
	
	private void encodePageNum(ResponseWriter writer,int pageNum,int curPage,String href) throws IOException{
		writer.startElement("a", null);
		
		if(pageNum==curPage){
			writer.writeAttribute("class", "page-cur", null);
		}else{
			writer.writeAttribute("class", "J_SearchAsync", null);
			writer.writeAttribute("href", href+"&pageNum="+pageNum, null);
		}
		writer.writeText(pageNum, null);
		writer.endElement("a");
	}
	
	private void encodeBreak() throws IOException{
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(BREAK_FLAG);
		link.setStyleClass("break");
		this.getChildren().add(link);
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
