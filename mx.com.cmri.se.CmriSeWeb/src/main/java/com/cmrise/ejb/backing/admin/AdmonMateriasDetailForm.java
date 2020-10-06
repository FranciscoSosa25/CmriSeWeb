package com.cmrise.ejb.backing.admin;

import java.util.List;
import java.util.ArrayList; 

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
import com.cmrise.ejb.model.admin.AdmonMateriaLine;
import com.cmrise.ejb.services.admin.AdmonMateriaLineLocal;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class AdmonMateriasDetailForm {

	private long numeroAdmonMateria; 
	private int idxMateriaLine; 
	private List<AdmonMateriaLine> materiasLines = new ArrayList<AdmonMateriaLine>(); 
	private AdmonMateriaLine admonMateriaLineForAction = new AdmonMateriaLine(); 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	@Inject 
	AdmonMateriaLineLocal admonMateriaLineLocal; 
	
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
	     Object objNumeroMateriaExamen = session.getAttribute("NumeroAdmonMateriaSV");
	     numeroAdmonMateria = Utilitarios.objToLong(objNumeroMateriaExamen); 
	     System.out.println("numeroAdmonMateria:"+numeroAdmonMateria);
	     materiasLines = admonMateriaLineLocal.findByNumeroMateria(numeroAdmonMateria); 
	}
	
	public void onAddNewLine() {
		idxMateriaLine = idxMateriaLine+1; 
		AdmonMateriaLine admonMateriaLine = new AdmonMateriaLine(); 
		System.out.println("numeroAdmonMateria:"+numeroAdmonMateria);
		admonMateriaLine.setFechaEfectivaDesde(new java.util.Date());
		admonMateriaLine.setNumeroMateriaHdr(numeroAdmonMateria);
		admonMateriaLine.setCreadoPor(userLogin.getNumeroUsuario());
		admonMateriaLine.setActualizadoPor(userLogin.getNumeroUsuario());
		admonMateriaLine.setIdxTemp(idxMateriaLine);
		materiasLines.add(admonMateriaLine); 
	}
	
	public void saveAndUpdateLine() {
		System.out.println("Entra saveAndUpdateLine");
		boolean exceptions = false; 
		for(AdmonMateriaLine i:materiasLines) {
			if(0!=i.getNumero()) {
				
			}else {
				 i.setFechaCreacion(new java.util.Date());
				 i.setFechaActualizacion(new java.util.Date());
				 i.setCreadoPor(userLogin.getNumeroUsuario());
				 i.setActualizadoPor(userLogin.getNumeroUsuario());
				 admonMateriaLineLocal.insert(i); 
			}
		}
		 if(!exceptions) {
			  FacesMessage msg = new FacesMessage("Se Agregaron", "Los Cambios");
		      FacesContext.getCurrentInstance().addMessage(null, msg);
		 }
		 
		System.out.println("Sale saveAndUpdateLine");
	}

	public String backAdmonMaterias() {
		return "Admon-Materias"; 
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}
	public long getNumeroAdmonMateria() {
		return numeroAdmonMateria;
	}
	public void setNumeroAdmonMateria(long numeroAdmonMateria) {
		this.numeroAdmonMateria = numeroAdmonMateria;
	}
	public int getIdxMateriaLine() {
		return idxMateriaLine;
	}
	public void setIdxMateriaLine(int idxMateriaLine) {
		this.idxMateriaLine = idxMateriaLine;
	}

	public List<AdmonMateriaLine> getMateriasLines() {
		return materiasLines;
	}

	public void setMateriasLines(List<AdmonMateriaLine> materiasLines) {
		this.materiasLines = materiasLines;
	}

	public AdmonMateriaLine getAdmonMateriaLineForAction() {
		return admonMateriaLineForAction;
	}

	public void setAdmonMateriaLineForAction(AdmonMateriaLine admonMateriaLineForAction) {
		this.admonMateriaLineForAction = admonMateriaLineForAction;
	}
	
}
