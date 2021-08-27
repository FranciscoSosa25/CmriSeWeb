package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class CreateCoreCaseForm {
	

	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	private CcHdrV1 ccHdrV1ForInsert = new CcHdrV1(); 
	private long numeroCcHdr;
	
	@Inject 
	CcHdrLocal ccHdrLocal; 
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal; 
	
	@Inject 
	AdmonSubMateriaLocal admonSubMateriaLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	@PostConstruct
	public void init() {
		 examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.CORE_CASES); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
		 ccHdrV1ForInsert.setFechaElaboracion(new java.util.Date());
	}
	
	public String create() {
		System.out.println("Entra CreateCoreCaseForm create");
		boolean exceptions = false; 
		String retval = null; 
	    
		ccHdrV1ForInsert.setCreadoPor(userLogin.getNumeroUsuario());
		ccHdrV1ForInsert.setActualizadoPor(userLogin.getNumeroUsuario());
   	    ccHdrV1ForInsert.setEstatus(Utilitarios.INITIAL_STATUS_MRQ);
		ccHdrV1ForInsert.setFechaCreacion(new java.util.Date());
		ccHdrV1ForInsert.setFechaActualizacion(new java.util.Date());
		setNumeroCcHdr(0); 
		try {
		 setNumeroCcHdr(ccHdrLocal.insert(ccHdrV1ForInsert));
		}catch(Exception e) {
			 Throwable throwable = e.getCause();
			 while(null!=throwable) {
				 throwable = throwable.getCause();
				 if(null!=throwable) {
					 if(throwable.toString().contains("CC_HDR")) {
						 exceptions = true; 
						 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",throwable.toString());
						 FacesContext.getCurrentInstance().addMessage(null, msg);
						 break;
					 }
				 }
			 }
		}
		if(!exceptions) {
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Se Agregaron", "Los Cambios");
		     FacesContext.getCurrentInstance().addMessage(null, msg);
		     FacesContext context = FacesContext.getCurrentInstance(); 
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 session.setAttribute("NumeroCcHdrSV", numeroCcHdr);
			 retval = "Preguntas-Update-CoreCase"; 
		}
		System.out.println("Sale CreateCoreCaseForm create");
		return retval;
		
	}
	
	public void onAdmonExamenChange() {
		if(0!=ccHdrV1ForInsert.getAdmonExamen()) {
			materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(ccHdrV1ForInsert.getAdmonExamen()); 
			selectMateriasHdr = new ArrayList<SelectItem>();  
			for(AdmonMateriaHdr i:materiasHdr) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectMateriasHdr.add(selectItem); 
			}
		}
	}
	
	public void onAdmonMateriaChange() {
		if(0!=ccHdrV1ForInsert.getAdmonMateria()) {
			subMaterias = admonSubMateriaLocal.findByNumeroMateria(ccHdrV1ForInsert.getAdmonMateria()); 
			selectSubMaterias = new ArrayList<SelectItem>(); 
			for(AdmonSubMateria i:subMaterias) {
				System.out.println("*");
				SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				selectSubMaterias.add(selectItem); 
			}
		}
	}
	

	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	}

	public List<AdmonMateriaHdr> getMateriasHdr() {
		return materiasHdr;
	}

	public void setMateriasHdr(List<AdmonMateriaHdr> materiasHdr) {
		this.materiasHdr = materiasHdr;
	}

	public List<AdmonSubMateria> getSubMaterias() {
		return subMaterias;
	}

	public void setSubMaterias(List<AdmonSubMateria> subMaterias) {
		this.subMaterias = subMaterias;
	}

	public List<SelectItem> getSelectExamenesHdr() {
		return selectExamenesHdr;
	}

	public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
		this.selectExamenesHdr = selectExamenesHdr;
	}

	public List<SelectItem> getSelectMateriasHdr() {
		return selectMateriasHdr;
	}

	public void setSelectMateriasHdr(List<SelectItem> selectMateriasHdr) {
		this.selectMateriasHdr = selectMateriasHdr;
	}

	public List<SelectItem> getSelectSubMaterias() {
		return selectSubMaterias;
	}

	public void setSelectSubMaterias(List<SelectItem> selectSubMaterias) {
		this.selectSubMaterias = selectSubMaterias;
	}

	public CcHdrV1 getCcHdrV1ForInsert() {
		return ccHdrV1ForInsert;
	}

	public void setCcHdrV1ForInsert(CcHdrV1 ccHdrV1ForInsert) {
		this.ccHdrV1ForInsert = ccHdrV1ForInsert;
	} 
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}

	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}
	
}
