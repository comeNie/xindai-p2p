package com.qfw.jbpm.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jbpm.api.RepositoryService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.qfw.common.util.web.BaseBackingBean;

@ViewScoped
@ManagedBean(name="flowAddBean")
public class FlowAddBean extends BaseBackingBean {

    private UploadedFile file;
    
    @ManagedProperty(value = "#{repositoryService}")
    private RepositoryService repositoryService;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
    	try {
			InputStream is = file.getInputstream();
			ZipInputStream zis = new ZipInputStream(is);
			String id = repositoryService.createDeployment().addResourcesFromZipInputStream(zis).deploy();
			//System.out.println(id);
			zis.close();
			is.close();
			FacesMessage msg = new FacesMessage("流程发布", file.getFileName() + "流程发布成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IOException e) {
			this.alert("流程发布失败!");
			 
		}
    	
        
    }

    public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
    
}