package com.qfw.jbpm.bean;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.jdbc.core.JdbcTemplate;

import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@RequestScoped
@ManagedBean(name="endFolwBean")
public class EndFolwBean extends BaseBackingBean{
    
	private String html;
	@ManagedProperty(value = "#{jdbcTemplate}")
	private JdbcTemplate jdbcTemplate;
    @PostConstruct
    public void init(){
    	try {
    		String workItemId = ViewOper.getParameter("workItemId");
    		if(StringUtils.isNotEmpty(workItemId)){
    			List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT JE.HTML FROM JBPM4_EXPAND JE,JBPM4_HIST_TASK HTA WHERE JE.WORK_ITEM_ID = HTA.EXECUTION_ AND HTA.ASSIGNEE_ = ? AND JE.WORK_ITEM_ID = ?",String.valueOf(ViewOper.getUser().getUserId()), workItemId);
    			if(CollectionUtil.isNotEmpty(list)){
    				html = String.valueOf(list.get(0).get("HTML"));
    			}
    		}
		} catch (Exception e) {
		}
    }
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
