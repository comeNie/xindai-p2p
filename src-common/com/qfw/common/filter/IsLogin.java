package com.qfw.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.ViewOper;


/**
 * 
 * @author lsj
 *
 */
public class IsLogin implements Filter {

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest =(HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();		
		SysUser user = (SysUser)session.getAttribute("user");
		if(user==null){
//			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/index.jsp");
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/login.jsf");
		}else{
			String path = httpRequest.getServletPath();
			/*if(path!=null && path.endsWith(".jsf")){
				DefaultTreeNode funRoot = (DefaultTreeNode)session.getAttribute("userFuns");
				if(!hasAccess(funRoot, path)){
					PrintWriter pw = response.getWriter();
					pw.println("<html>");
					pw.println("<head>");
					pw.println("<meta content='text/html; charset=UTF-8' http-equiv=\"Content-Type\" />");
					pw.println("<script>");
					pw.print("alert('你没有权限访问');");//你没有权限访问
					pw.print("history.go(-1);");
					pw.println("</script>");
					pw.println("</head>");
					pw.println("</html>");
					pw.flush();
					pw.close();
					return;
				}
			}else{
				//System.out.println(path);
			}*/
			chain.doFilter(request, response);
		}
	}

	private boolean hasAccess(TreeNode funRoot,String path){
		Object data = funRoot.getData();
		if(data instanceof SysFunction){
			if(path.equals(((SysFunction) data).getFunPath())){
				return true;
			}else{
				List<TreeNode> treeNode = funRoot.getChildren();
				for (TreeNode treeNode2 : treeNode) {
					if(hasAccess(treeNode2,path)){
						return true;
					}
				}
			}
		}else{
			List<TreeNode> treeNode = funRoot.getChildren();
			for (TreeNode treeNode2 : treeNode) {
				if(hasAccess(treeNode2,path)){
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
