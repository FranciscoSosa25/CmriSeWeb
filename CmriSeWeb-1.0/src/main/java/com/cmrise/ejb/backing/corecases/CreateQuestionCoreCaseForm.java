package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class CreateQuestionCoreCaseForm {
	
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	
	private long numeroCcHdr;
    private CcHdrV1 ccHdrV1ForInsert = new CcHdrV1(); 
    private CcPreguntasHdrV1 ccPreguntasHdrV1ForInsert = new CcPreguntasHdrV1(); 
    private List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
    
    
	@Inject 
	CcHdrLocal ccHdrLocal; 
	
    @Inject 
    CcPreguntasHdrLocal ccPreguntasHdrLocal; 
    
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
		 System.out.println("Entra "+this.getClass()+" init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroCcHdr= session.getAttribute("NumeroCcHdrSV");
	     long numeroCcHdr = 0;
	     if(null!=obNumeroCcHdr) {
	    	 if(obNumeroCcHdr instanceof Long) {
	    		 numeroCcHdr = (long)obNumeroCcHdr;
	    		 System.out.println("numeroCcHdr:"+numeroCcHdr);
	    		 this.numeroCcHdr = numeroCcHdr; 
	    	 }else {
	    		 System.out.println("numeroCcHdr instanceof Long:false");
	    	 }
	     }else {
	    	 System.out.println("(null!=numeroCcHdr:false");
	    	 return;
	     }	
	     
	     ccHdrV1ForInsert = ccHdrLocal.findByNumeroObjMod(numeroCcHdr); 
	     ccPreguntasHdrV1ForInsert.setFechaElaboracion(new java.util.Date());
	     ccPreguntasHdrV1ForInsert.setAdmonExamen(ccHdrV1ForInsert.getAdmonExamen());
	     ccPreguntasHdrV1ForInsert.setAdmonMateria(ccHdrV1ForInsert.getAdmonMateria());
	     ccPreguntasHdrV1ForInsert.setAdmonSubMateria(ccHdrV1ForInsert.getAdmonSubMateria());
	     examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.CORE_CASES); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
	     onAdmonExamenChange(); 
	     onAdmonMateriaChange(); 
	    		 
	     List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrLocal.findListByNumeroCcHdr(numeroCcHdr);
		    listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
		 	for(CcPreguntasHdrV1Dto ccPreguntasHdrV1DtoTmp :listCcPreguntasHdrV1Dto) {
		 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
		 		ccPreguntasHdrV1.setNumero(ccPreguntasHdrV1DtoTmp.getNumero());
		     	ccPreguntasHdrV1.setNumeroCcHdr(ccPreguntasHdrV1DtoTmp.getNumeroCcHdr());
		     	//ccPreguntasHdrV1.setTitulo(ccPreguntasHdrV1DtoTmp.getTitulo());
		     	ccPreguntasHdrV1.setTipoPreguntaDesc(ccPreguntasHdrV1DtoTmp.getTipoPreguntaDesc());
		     	ccPreguntasHdrV1.setEstatusDesc(ccPreguntasHdrV1DtoTmp.getEstatusDesc());
		     	ccPreguntasHdrV1.setMaxPuntuacion(ccPreguntasHdrV1DtoTmp.getMaxPuntuacion());
		     	ccPreguntasHdrV1.setEtiquetas(ccPreguntasHdrV1DtoTmp.getEtiquetas());
		     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);
		 	}
	     
		 System.out.println("Entra "+this.getClass()+" init()");
	}
	
	
	public String create() {
		boolean exceptions = false; 
		String retval = null; 
		ccPreguntasHdrV1ForInsert.setEstatus(Utilitarios.INITIAL_STATUS_MRQ);
		ccPreguntasHdrV1ForInsert.setNumeroCcHdr(this.getNumeroCcHdr());
		ccPreguntasHdrV1ForInsert.setCreadoPor(userLogin.getNumeroUsuario());
		ccPreguntasHdrV1ForInsert.setActualizadoPor(userLogin.getNumeroUsuario());
		ccPreguntasHdrV1ForInsert.setFechaCreacion(new java.util.Date());
		ccPreguntasHdrV1ForInsert.setFechaActualizacion(new java.util.Date());
		
		long longNumeroPreguntaHdr = 0; 
		try {
		 longNumeroPreguntaHdr = ccPreguntasHdrLocal.insert(ccPreguntasHdrV1ForInsert);
		}catch(Exception e) {
			 Throwable throwable = e.getCause();
			 while(null!=throwable) {
				 throwable = throwable.getCause();
				 if(null!=throwable) {
					 if(throwable.toString().contains("CC_PREGUNTAS_HDR")) {
						 exceptions = true; 
						 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",throwable.toString());
						 FacesContext.getCurrentInstance().addMessage(null, msg);
						 break;
					 }
				 }
			 }
		}
		
		if(!exceptions) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", this.numeroCcHdr);  
		session.setAttribute("NumeroCcPreguntaHdrSV", longNumeroPreguntaHdr); 
		retval = "Actualizar-Pregunta-Fta-CoreCase"; 
		}
		
		return retval;
	}
	
	public String updatePregunta(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
		  FacesContext context = FacesContext.getCurrentInstance(); 
		  HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		  long lNumeroCcHdr = pCcPreguntasHdrV1.getNumeroCcHdr();
		  long lNumeroPreguntaHdr = pCcPreguntasHdrV1.getNumero();
		  session.setAttribute("NumeroCcHdrSV", lNumeroCcHdr);
	      session.setAttribute("NumeroCcPreguntaHdrSV", lNumeroPreguntaHdr);
	      return  "Actualizar-Pregunta-Fta-CoreCase";
	 }
	
	public String nuevaPregunta() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());
	    return  "Crear-Pregunta-CoreCase";
	}
	
	public void onAdmonExamenChange() {
		if(0!=ccPreguntasHdrV1ForInsert.getAdmonExamen()) {
			materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(ccPreguntasHdrV1ForInsert.getAdmonExamen()); 
			selectMateriasHdr = new ArrayList<SelectItem>();  
			for(AdmonMateriaHdr i:materiasHdr) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectMateriasHdr.add(selectItem); 
			}
		}
	}
	
	public void onAdmonMateriaChange() {
		System.out.println("ccPreguntasHdrV1ForInsert.getAdmonMateria():"+ccPreguntasHdrV1ForInsert.getAdmonMateria());
		if(0!=ccPreguntasHdrV1ForInsert.getAdmonMateria()) {
			subMaterias = admonSubMateriaLocal.findByNumeroMateria(ccPreguntasHdrV1ForInsert.getAdmonMateria()); 
			selectSubMaterias = new ArrayList<SelectItem>(); 
			for(AdmonSubMateria i:subMaterias) {
				System.out.println("*");
				SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				selectSubMaterias.add(selectItem); 
			}
		}
	}
	
	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}

	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}

	public List<CcPreguntasHdrV1> getListCcPreguntasHdrV1() {
		return listCcPreguntasHdrV1;
	}

	public void setListCcPreguntasHdrV1(List<CcPreguntasHdrV1> listCcPreguntasHdrV1) {
		this.listCcPreguntasHdrV1 = listCcPreguntasHdrV1;
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


	public CcPreguntasHdrV1 getCcPreguntasHdrV1ForInsert() {
		return ccPreguntasHdrV1ForInsert;
	}


	public void setCcPreguntasHdrV1ForInsert(CcPreguntasHdrV1 ccPreguntasHdrV1ForInsert) {
		this.ccPreguntasHdrV1ForInsert = ccPreguntasHdrV1ForInsert;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}


	public CcHdrV1 getCcHdrV1ForInsert() {
		return ccHdrV1ForInsert;
	}


	public void setCcHdrV1ForInsert(CcHdrV1 ccHdrV1ForInsert) {
		this.ccHdrV1ForInsert = ccHdrV1ForInsert;
	}
	
}
