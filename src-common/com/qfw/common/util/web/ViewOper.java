package com.qfw.common.util.web;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.model.TreeNode;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.model.bo.BizCustomerBO;

/**
 * 显示层的工具方法类
 * @author lsj
 */
public class ViewOper {
	// private static IBussOperBS bussOper = null;

	/**
	 * 得到请求参数
	 * @param key
	 * @return 
	 */
	public static String getRequestParameter(String key) {
		if (key == null) {
			return null;
		}

		return getRequest().getParameter(key); // Mod [1.21]
	}

	/**
	 * 获取RemoteAddr
	 * @return
	 */
	public static String getRemoteAddr() {
		return getRequest().getRemoteAddr();
	}

	/**
	 * 得到请求参数组
	 * @param key
	 * @return 
	 */
	public static String[] getParameterValues(String key) {
		if (key == null) {
			return null;
		}

		return getRequest().getParameterValues(key);
	}

		
	/**
	 * 得到request
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return (HttpServletRequest) context.getRequest();
	}

	public static Object getClassInstance(String classID) {
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		return ctx.getBean(classID);
	}

	/**
	 * 得到session
	 * @return
	 */
	public static HttpSession getSession() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return (HttpSession) context.getSession(true);
	}
	/**
	 * 获取操作用户
	 * @return
	 */
	public static SysUser getUser(){
		return (SysUser) getSession().getAttribute("user");
	}
	/**
	 * 获取客户信息
	 * @return
	 */
	public static BizCustomerBO getCust(){
		return (BizCustomerBO) getSession().getAttribute("cust");
	}
	/**
	 * 获取sessionMap
	 * @return
	 */
	public static Map<String,Object>  getSessionMap(){
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return context.getSessionMap();
	}
	public static Flash flashScope(){
		return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
	}
	/**
	 * 得到response
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return (HttpServletResponse)context.getResponse();
	}

	public static Timestamp getOperTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	
	public static Locale getLocale() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		Locale locale = null;
		if (viewRoot != null) {
			locale = viewRoot.getLocale();
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
	public static String getParameter(String parm){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parm);
		
	}
	/**
	 * 临时存在session的变量使用后移除
	 * @param attr
	 * @return
	 */
	public static Object getSessionTmpAttr(String attr){
		Object obj = getSession().getAttribute(attr);
		getSession().removeAttribute(attr);
		return obj;
	}
	/**
	 * 获取UIViewRoot
	 * @return
	 */
	public static UIViewRoot getUIViewRoot(){
		UIViewRoot uv =  FacesContext.getCurrentInstance().getViewRoot();
		return uv;
	}

	public static String getOriginalURLWithOutPageNum() {
		HttpServletRequest request = getRequest();
        StringBuffer originalURL = request.getRequestURL();
        Map parameters = request.getParameterMap();
        if (parameters != null && parameters.size() > 0) {
        	int length = originalURL.length();
            for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();) {
                String key = (String) iter.next();
                if("pageNum".equals(key)){
                	continue;
                }
                String[] values = (String[]) parameters.get(key);
                for (int i = 0; i < values.length; i++) {
                	originalURL.append("&").append(key).append("=").append(values[i]);
                }
            }
            originalURL.deleteCharAt(length).insert(length, "?");
        }
       
        return originalURL.toString();
    }
	public static boolean hasAccess(String funCode){
		TreeNode funRoot = (TreeNode) getSession().getAttribute("userFuns");
		if(funRoot == null){
			return false;
		}
		return hasAccess(funRoot,funCode);
	}
	public static boolean hasAccess(TreeNode funRoot,String funCode){
		Object data = funRoot.getData();
		if(data instanceof SysFunction){
			if(funCode.equals(((SysFunction) data).getFunCode())){
				return true;
			}else{
				List<TreeNode> treeNode = funRoot.getChildren();
				for (TreeNode treeNode2 : treeNode) {
					if(hasAccess(treeNode2,funCode)){
						return true;
					}
				}
			}
		}else{
			List<TreeNode> treeNode = funRoot.getChildren();
			for (TreeNode treeNode2 : treeNode) {
				if(hasAccess(treeNode2,funCode)){
					return true;
				}
			}
		}
		return false;
	}
	
}