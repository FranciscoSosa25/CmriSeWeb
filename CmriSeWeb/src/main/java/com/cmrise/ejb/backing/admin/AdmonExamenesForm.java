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

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonExamenLine;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonExamenLineLocal;
import com.cmrise.utils.XxSqlConstraints;

@ManagedBean
@ViewScoped
public class AdmonExamenesForm {

	
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private AdmonExamenHdr admonExamenHdrForAction = new AdmonExamenHdr(); 
	private int idxExamen = 0; 
	private int idxExamenLine = 0; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 

	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@Inject 
	AdmonExamenLineLocal admonExamenLineLocal; 
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra AdmonExamenesForm init()");
		 System.out.println("admonExamenHdrForAction.getNumero():"+admonExamenHdrForAction.getNumero());
		 refreshEntity();
		 System.out.println("Sale AdmonExamenesForm init()");
	 }		 
	
	public void refreshEntity() {
		examenesHdr = admonExamenHdrLocal.findAll();
	}
    
	
	public void onAddNew() {
		idxExamen = idxExamen+1; 
		AdmonExamenHdr admonExamenHdr = new AdmonExamenHdr(); 
		admonExamenHdr.setFechaEfectivaDesde(new java.util.Date());
		admonExamenHdr.setCreadoPor(userLogin.getNumeroUsuario());
		admonExamenHdr.setActualizadoPor(userLogin.getNumeroUsuario());
		admonExamenHdr.setIdxTemp(idxExamen);
		examenesHdr.add(admonExamenHdr); 
	}

	public void saveAndUpdate() {
		for(AdmonExamenHdr i:examenesHdr) {
			if(0!=i.getNumero()) {
				if(i.isaChange()) {
					i.setFechaActualizacion(new java.util.Date());
					i.setActualizadoPor(userLogin.getNumeroUsuario());
					admonExamenHdrLocal.update(i); 
				}
			}else {
				 i.setFechaCreacion(new java.util.Date());
				 i.setFechaActualizacion(new java.util.Date());
				 i.setCreadoPor(userLogin.getNumeroUsuario());
				 i.setActualizadoPor(userLogin.getNumeroUsuario());
				 admonExamenHdrLocal.insert(i); 
			}
		}
		  FacesMessage msg = new FacesMessage("Se Agregaron", "Los Cambios");
	      FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	
	public String toAdmonExamenesDetail(AdmonExamenHdr pAdmonExamenHdr) {
		String accion = null; 
		if(0==pAdmonExamenHdr.getNumero()) {
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Antes de continuar hay que guardar"));
	    }else {
	    	 FacesContext context = FacesContext.getCurrentInstance();  
	    	 HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
		     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		     session.setAttribute("NumeroAdmonExamenSV", pAdmonExamenHdr.getNumero());
		     accion = "Admon-Examenes-Detail"; 
	    }
		
		return accion;
	}
	
	
	public String backAdmonExamenes() {
		return "Admon-Examenes";
	}
	
	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	} 
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public AdmonExamenHdr getAdmonExamenHdrForAction() {
		return admonExamenHdrForAction;
	}

	public void setAdmonExamenHdrForAction(AdmonExamenHdr admonExamenHdrForAction) {
		this.admonExamenHdrForAction = admonExamenHdrForAction;
	}
	
}
