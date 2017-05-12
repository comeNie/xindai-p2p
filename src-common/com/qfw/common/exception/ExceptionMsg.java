/*
 * Created on 2005-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.qfw.common.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.PropertyResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;


public class ExceptionMsg {
	private final static Log logger = LogFactory.getLog(ExceptionMsg.class);

	public String setMainMsg(Exception ex) {
		logger.info("进入异常显示！", ex);
		StringBuffer sb = new StringBuffer();
		String detailMsg = null;
		String mainMsg = null;
		if (ex.getMessage() != null) {
			sb.append(ex.getMessage());// v1.3 get custom message from // exception
			sb.append("\\n");
		}
		sb.append(this.getDetail(ex));
		detailMsg = sb.toString();
		if (ex instanceof MultiBizException) {
			List list = ((MultiBizException) ex).getExceptionList();
			StringBuffer msg = new StringBuffer();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BizException e = (BizException) iter.next();
				msg.append(getExceptionMsg(e)).append("\\n");
			}
			mainMsg = msg.toString();
		} else if (ex instanceof BizException) {
			mainMsg = getExceptionMsg(ex);
		} else if (ex instanceof DataAccessException) {
			DataAccessException dae = (DataAccessException) ex;
			logger.error("", dae);
			mainMsg = "数据访问异常！异常信息：" + dae.getMessage();
		} else if (ex instanceof IOException) {
			logger.error("文件不存在!" + ex.getMessage());
			mainMsg = "文件不存在!" + ex.getMessage();
		} else if (ex instanceof NullPointerException) {
			logger.error("系统空指针异常,请重试或联系系统管理员!" + ex.getMessage());
			mainMsg = "系统空指针异常,请重试或联系系统管理员!" + ex.getMessage();
		} else if (ex instanceof ClassNotFoundException) {
			logger.error("指定的类不存在:" + ex.getMessage());
			mainMsg = "指定的类不存在:" + ex.getMessage();
		} else if (ex instanceof SQLException) {
			logger.error("查询语句错误：" + ex.getMessage());
			mainMsg = "查询语句错误：" + ex.getMessage();
		} else {
			logger.error("系统异常!异常信息：", ex);
			mainMsg = "系统异常!异常信息：" + ex.getMessage();
		}
		// if(detailMsg!=null)
		// facesMessage = new
		// FacesMessage(FacesMessage.SEVERITY_ERROR,detailMsg, null);
		// else
		// facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,mainMsg,
		// null);
		//FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mainMsg + "\\n"
		//		+ detailMsg, null);
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mainMsg, null);

		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return null;
	}

	public void setTitle(String tl) {
		PropertyResolver pr = FacesContext.getCurrentInstance().getApplication().getPropertyResolver();
		pr.setValue(FacesContext.getCurrentInstance().getApplication().getVariableResolver().resolveVariable(FacesContext.getCurrentInstance(), "showException"), "title", tl);
		FacesContext.getCurrentInstance().getApplication().setPropertyResolver(pr);
	}

//	private String getString(String file, String param) {
//		String value = null;
//		try {
//			value = ResourceBundle.getBundle(file).getString(param);
//		} catch (MissingResourceException e) {
//			return null;
//		}
//		return value;
//	}

	public String getString(String bundle, String resourceId, Object[] params) {
		FacesContext context = FacesContext.getCurrentInstance();
		String appBundle = null;
		if (context != null) {
			Application app = context.getApplication();
			appBundle = app.getMessageBundle();
		}

		Locale locale = getLocale(context);
		ClassLoader loader = getClassLoader();
		return getString(appBundle, bundle, resourceId, locale, loader, params);
	}

	private String getString(String bundlex, String bundle2, String resourceId, Locale locale,
			ClassLoader loader, Object[] params) {
		String resource = null;
		ResourceBundle bundle;

		if (bundlex != null) {
			bundle = ResourceBundle.getBundle(bundlex, locale, loader);
			if (bundle != null)
				try {
					resource = bundle.getString(resourceId);
				} catch (MissingResourceException ex) {
				}
		}

		if (resource == null) {
			bundle = ResourceBundle.getBundle(bundle2, locale, loader);
			if (bundle != null)
				try {
					resource = bundle.getString(resourceId);
				} catch (MissingResourceException ex) {
				}
		}

		if (resource == null)
			resource= resourceId; // no match
		if (params == null)
			return resource;
		
		//lijie 2007/05/17 add
		for (int i = 0; i < params.length; i++) {
			if(params[i]==null){
				params[i] = "null";
			}else{
				params[i] = getString(bundle2,params[i].toString(),null);
			}
		}

		MessageFormat formatter = new MessageFormat(resource, locale);
		return formatter.format(params);
	}

	private Locale getLocale(FacesContext context) {
		Locale locale = null;
		if (context != null) {
			UIViewRoot viewRoot = context.getViewRoot();
			if (viewRoot != null)
				locale = viewRoot.getLocale();
			if (locale == null)
				locale = Locale.getDefault();
		}
		return locale;
	}

	private ClassLoader getClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null)
			loader = ClassLoader.getSystemClassLoader();
		return loader;
	}

	private String getDetail(Exception e) {// zhangfeng add ,show detail
		// messages
		StackTraceElement[] es = e.getStackTrace();
		// update zhangfeng 2006-6-6 若异常信息不包含详细信息，显示下面的信息
		if (es.length == 0)
			return "该异常未按照规范加入原始异常，无详细信息可以显示，请检查代码 \\n ";
		StringBuffer sb = new StringBuffer();
		sb.append("\\n文件[:" + es[0].getFileName());
		sb.append("] \\n类:[" + es[0].getClassName());
		sb.append("] \\n方法[:" + es[0].getMethodName());
		sb.append("]\\n代码行数: " + es[0].getLineNumber());

		return sb.toString();
	}

	private String getExceptionMsg(Exception ex) {
		BizException be = (BizException) ex;
		if (be.getSysMsg() != null) {
			String id = be.getSysMsg().getKey();
			Object[] obj = be.getSysMsg().getParams();
			String bundlename = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("exceptionMessageDir");
			if (bundlename == null) {
				logger.error("初始化参数尚未定义，请在Web.xml中定义初始化参数：exceptionMessageDir。");
				return "初始化参数尚未定义，请在Web.xml中定义初始化参数：exceptionMessageDir。";
			}
			
			String message = null;
			try {
				message = this.getString(bundlename, id, obj);
			} catch (MissingResourceException e) {
				logger.error("请检查Web.xml中定义初始化参数exceptionMessageDir所对应的文件" + bundlename
						+ ".properties是否存在。");
				// facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mainMsg, null);
				// FacesContext.getCurrentInstance().addMessage(null,facesMessage);
				return "请检查Web.xml中定义初始化参数exceptionMessageDir所对应的文件" + bundlename
						+ ".properties是否存在。";
			}
//			if (message == null) {
//				message = getString(bundlename, id);
//			}
			if (message == null) {
				logger.error(id + "的配置有误，在配置文件" + bundlename + "中不存在。");
				return id;
			}
			return message;
		} else {
			logger.error("业务处理异常。" + ex.getMessage());
			return "业务处理异常。" + ex.getMessage();
		}
	}
	
	//打印异常的栈轨迹信息，但是页面不提示异常信息
    public  void printStackTraceLog(Exception e) {   
        StackTraceElement[] ste = e.getStackTrace(); 
        StringBuffer sb = new StringBuffer();
        sb.append(e.getMessage() + "\n");
        for(int i = 0; i < ste.length; i++) {
            sb.append("	at ").append(ste[i].toString()).append("\n");   
        }   
        ////System.out.println(sb.toString());
        logger.error(sb.toString());
    }
}
