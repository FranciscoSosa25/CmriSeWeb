package com.cmrise.ejb.backing.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonExamenLine;
import com.cmrise.ejb.services.admin.AdmonExamenLineLocal;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class AdmonExamenesDetailForm {

	private long numeroAdmonExamen; 
	private int idxExamenLine; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 

	@Inject 
	AdmonExamenLineLocal admonExamenLineLocal; 
	
	private List<AdmonExamenLine> examenesLines = new ArrayList<AdmonExamenLine>(); 
	private AdmonExamenLine admonExamenLineForAction = new AdmonExamenLine(); 
	
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra AdmonExamenesDetailForm init()");
		 refreshEntity();
		 System.out.println("Sale AdmonExamenesDetailForm init()");
	 }		 
	
	public void refreshEntity() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object objNumeroAdmonExamen = session.getAttribute("NumeroAdmonExamenSV");
	     numeroAdmonExamen = Utilitarios.objToLong(objNumeroAdmonExamen); 
		 examenesLines = admonExamenLineLocal.findByNumeroExamen(numeroAdmonExamen); 
	}

	public void onAddNewLine() {
		idxExamenLine = idxExamenLine+1; 
		AdmonExamenLine admonExamenLine = new AdmonExamenLine(); 
		System.out.println("numeroAdmonExamen:"+numeroAdmonExamen);
		admonExamenLine.setFechaEfectivaDesde(new java.util.Date());
		admonExamenLine.setNumeroExamen(numeroAdmonExamen);
		admonExamenLine.setCreadoPor(userLogin.getNumeroUsuario());
		admonExamenLine.setActualizadoPor(userLogin.getNumeroUsuario());
		admonExamenLine.setIdxTemp(idxExamenLine);
		examenesLines.add(admonExamenLine); 
	}
	
	public void saveAndUpdateLine() {
		System.out.println("Entra saveAndUpdateLine");
		for(AdmonExamenLine i:examenesLines) {
			if(0!=i.getNumero()) {
				
			}else {
				 i.setNumeroExamen(numeroAdmonExamen);
				 i.setFechaCreacion(new java.util.Date());
				 i.setFechaActualizacion(new java.util.Date());
				 i.setCreadoPor(userLogin.getNumeroUsuario());
				 i.setActualizadoPor(userLogin.getNumeroUsuario());
				 admonExamenLineLocal.insert(i); 
			}
		}
		System.out.println("Sale saveAndUpdateLine");
		FacesMessage msg = new FacesMessage("Se Agregaron", "Los Cambios");
	    FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
    public void selectForActionLine(AdmonExamenLine pAdmonExamenLine) {
    	System.out.println("pAdmonExamenLine.getIdxTemp():"+pAdmonExamenLine.getIdxTemp());
    	admonExamenLineForAction.setNumero(pAdmonExamenLine.getNumero());
    	admonExamenLineForAction.setIdxTemp(pAdmonExamenLine.getIdxTemp());
	}
    
    public void delete() {
    	System.out.println("admonExamenLineForAction.getNumero():"+admonExamenLineForAction.getNumero());
    	System.out.println("admonExamenLineForAction.getIdxTemp():"+admonExamenLineForAction.getIdxTemp());
    	boolean deleteIn = false;
    	AdmonExamenLine admonExamenLine=null;
    	if(0!=admonExamenLineForAction.getNumero()) {
    		for(AdmonExamenLine i:examenesLines) {
    			if(i.getNumero()==admonExamenLineForAction.getNumero()) {
    				admonExamenLineLocal.delete(admonExamenLineForAction.getNumero()); 
    				admonExamenLine = i; 
    				break; 
    			}
    		}
    	}else {
    		if(0!=admonExamenLineForAction.getIdxTemp()) {
    			for(AdmonExamenLine i:examenesLines) {
    				if(i.getIdxTemp()==admonExamenLineForAction.getIdxTemp()) {
    					admonExamenLine = i; 
    				}
    			}
    		}
    	}
    	
        if(null!=admonExamenLine) {
	  	 deleteIn = true;
		 PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
    	 examenesLines.remove(admonExamenLine); 
    	}
    	
    }
	
	public List<AdmonExamenLine> getExamenesLines() {
		return examenesLines;
	}

	public void setExamenesLines(List<AdmonExamenLine> examenesLines) {
		this.examenesLines = examenesLines;
	}

	public long getNumeroAdmonExamen() {
		return numeroAdmonExamen;
	}

	public void setNumeroAdmonExamen(long numeroAdmonExamen) {
		this.numeroAdmonExamen = numeroAdmonExamen;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public AdmonExamenLine getAdmonExamenLineForAction() {
		return admonExamenLineForAction;
	}

	public void setAdmonExamenLineForAction(AdmonExamenLine admonExamenLineForAction) {
		this.admonExamenLineForAction = admonExamenLineForAction;
	}
	
	
}
