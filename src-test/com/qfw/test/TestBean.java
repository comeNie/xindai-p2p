package com.qfw.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.qfw.common.util.web.ViewOper;


@RequestScoped
@ManagedBean(name = "testBean")
public class TestBean {

	private String a = "1";
	private String b ="<body><p>*</p></body>";
	public void test(){
		try {
			InputStreamReader isr = new InputStreamReader (ViewOper.getRequest().getInputStream(),"GBK");
			BufferedReader br = new BufferedReader (isr);
			String inLine = null;
			StringBuilder sb = new StringBuilder();
			while((inLine=br.readLine())!=null){
				sb.append(inLine);
			}
			br.close();
			isr.close();
			System.out.println(sb.toString());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String name = ViewOper.getRequestParameter("name");
		//String name1 = ViewOper.getRequest().getAttribute("name");
		try {
			PrintWriter pw = ViewOper.getResponse().getWriter();
			pw.print("参数name"+name);
			pw.flush();
			pw.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	
	
	
}
