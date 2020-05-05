package com.cmrise.ejb.backing.mrq;

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
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.XxSqlConstraints;

@ManagedBean
@ViewScoped
public class CreateMrqForm {

	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForInsert = new MrqsPreguntasHdrV1(); 
	
	@Inject
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal; 
	
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
		 examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.MRQS); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
		 mrqsPreguntasHdrV1ForInsert.setFechaElaboracion(new java.util.Date());
	 }
	
	 
	public String create() {
		System.out.println("Entra CreateMrqForm create");
		boolean exceptions = false; 
		String retval = null; 
		
		mrqsPreguntasHdrV1ForInsert.setCreadoPor(userLogin.getNumeroUsuario());
		mrqsPreguntasHdrV1ForInsert.setActualizadoPor(userLogin.getNumeroUsuario());
		mrqsPreguntasHdrV1ForInsert.setEstatus(Utilitarios.INITIAL_STATUS_MRQ);
		mrqsPreguntasHdrV1ForInsert.setFechaCreacion(new java.util.Date());
		mrqsPreguntasHdrV1ForInsert.setFechaActualizacion(new java.util.Date());
		
		long numeroPreguntaHdr = 0; 
		try {
			numeroPreguntaHdr = mrqsPreguntasHdrLocal.insert(mrqsPreguntasHdrV1ForInsert);
		}catch(Exception e) {
			 Throwable throwable = e.getCause();
			 while(null!=throwable) {
				 throwable = throwable.getCause();
				 if(null!=throwable) {
					 if(throwable.toString().contains("MRQS_PREGUNTAS_HDR")) {
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
			 session.setAttribute("NumeroHdrSV", numeroPreguntaHdr);
			 retval = "Preguntas-UpdateFreeTextAnswer-NewMrqs"; 
		}
		
		return retval;
	}
	
	public void onAdmonExamenChange() {
		if(0!=mrqsPreguntasHdrV1ForInsert.getAdmonExamen()) {
			materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(mrqsPreguntasHdrV1ForInsert.getAdmonExamen()); 
			selectMateriasHdr = new ArrayList<SelectItem>();  
			for(AdmonMateriaHdr i:materiasHdr) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectMateriasHdr.add(selectItem); 
			}
		}
	}
	
	public void onAdmonMateriaChange() {
		if(0!=mrqsPreguntasHdrV1ForInsert.getAdmonMateria()) {
			subMaterias = admonSubMateriaLocal.findByNumeroMateria(mrqsPreguntasHdrV1ForInsert.getAdmonMateria()); 
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
	
	public List<SelectItem> getSelectExamenesHdr(){
		return this.selectExamenesHdr; 
	}

	public List<SelectItem> getSelectMateriasHdr(){
		return this.selectMateriasHdr; 
	}
	
	public List<SelectItem>  getSelectSubMaterias() {
		return this.selectSubMaterias; 
	}

	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1ForInsert() {
		return mrqsPreguntasHdrV1ForInsert;
	}


	public void setMrqsPreguntasHdrV1ForInsert(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForInsert) {
		this.mrqsPreguntasHdrV1ForInsert = mrqsPreguntasHdrV1ForInsert;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}
	
}
