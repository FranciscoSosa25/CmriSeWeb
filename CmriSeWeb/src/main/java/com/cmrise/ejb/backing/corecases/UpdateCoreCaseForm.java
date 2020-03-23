package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;

@ManagedBean
@ViewScoped
public class UpdateCoreCaseForm {
   private long numeroCcHdr;
   private String estatusCc;  
   private String nombreCc;
   private String temaCc;
   private String historialClinicoCc; 
   private String descripcionTecnicaCc;
   private String notaCc; 
   private boolean opcionInseguraCc; 
   private String etiquetasCc;

   private List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
   
   @Inject 
   CcHdrLocal ccHdrLocal; 
   
   @Inject 
   CcPreguntasHdrLocal ccPreguntasHdrLocal;
   @ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
   
   @PostConstruct
	 public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroCcHdr= session.getAttribute("NumeroCcHdrSV");
	     if(null!=obNumeroCcHdr) {
	    	 if(obNumeroCcHdr instanceof Long) {
	    		 long numeroCcHdr = (Long)obNumeroCcHdr;
	    		 System.out.println("numeroCcHdr:"+numeroCcHdr);
	    		 this.numeroCcHdr = numeroCcHdr; 
	    	 }else {
	    		 System.out.println("numeroCcHdr instanceof Long:false");
	    	 }
	     }else {
	    	 System.out.println("(null!=numeroCcHdr:false");
	    	 return;
	     }	
		 refreshEntity();
		 System.out.println("Sale "+this.getClass()+" init()");
	 }		 
	 
   
  private void refreshEntity() {
	CcHdrV1Dto ccHdrV1Dto = ccHdrLocal.findByNumero(this.numeroCcHdr);
	this.setEstatusCc(ccHdrV1Dto.getEstatus());
	this.setNombreCc(ccHdrV1Dto.getNombre());
	this.setTemaCc(ccHdrV1Dto.getTema());
	this.setHistorialClinicoCc(ccHdrV1Dto.getHistorialClinico());
	this.setDescripcionTecnicaCc(ccHdrV1Dto.getDescripcionTecnica());
	this.setNotaCc(ccHdrV1Dto.getNota());
	this.setOpcionInseguraCc(ccHdrV1Dto.getOpcionInsegura());
	this.setEtiquetasCc(ccHdrV1Dto.getEtiquetas());
	
	List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrLocal.findListByNumeroCcHdr(ccHdrV1Dto.getNumero());
	Iterator<CcPreguntasHdrV1Dto> iterlistCcPreguntasHdrV1Dto = listCcPreguntasHdrV1Dto.iterator();
	listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
	while(iterlistCcPreguntasHdrV1Dto.hasNext()) {
    	CcPreguntasHdrV1Dto  ccPreguntasHdrV1Dto= iterlistCcPreguntasHdrV1Dto.next(); 
    	CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
    	ccPreguntasHdrV1.setNumero(ccPreguntasHdrV1Dto.getNumero());
    	ccPreguntasHdrV1.setNumeroCcHdr(ccPreguntasHdrV1Dto.getNumeroCcHdr());
    	ccPreguntasHdrV1.setTitulo(ccPreguntasHdrV1Dto.getTitulo());
    	ccPreguntasHdrV1.setTipoPreguntaDesc(ccPreguntasHdrV1Dto.getTipoPreguntaDesc());
    	ccPreguntasHdrV1.setEstatusDesc(ccPreguntasHdrV1Dto.getEstatusDesc());
    	ccPreguntasHdrV1.setMaxPuntuacion(ccPreguntasHdrV1Dto.getMaxPuntuacion());
    	ccPreguntasHdrV1.setEtiquetas(ccPreguntasHdrV1Dto.getEtiquetas());
    	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);
    }
    
  }


  public String createCoreQuestion(){
	  String retval = null; 
	  retval ="Crear-Pregunta-CoreCase"; 
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
//  
	public String saveAndPreview() {	     
		/**updatePregunta();**/ 
		 getGuestPreferences().setTheme("deep-purple");
	 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());	     
		return "coreCase-Preview"; 
	}
 
  
public long getNumeroCcHdr() {
	return numeroCcHdr;
 }

 public void setNumeroCcHdr(long numeroCcHdr) {
	this.numeroCcHdr = numeroCcHdr;
 }


public String getEstatusCc() {
	return estatusCc;
}


public void setEstatusCc(String estatusCc) {
	this.estatusCc = estatusCc;
}


public String getNombreCc() {
	return nombreCc;
}


public void setNombreCc(String nombreCc) {
	this.nombreCc = nombreCc;
}


public String getTemaCc() {
	return temaCc;
}


public void setTemaCc(String temaCc) {
	this.temaCc = temaCc;
}


public String getHistorialClinicoCc() {
	return historialClinicoCc;
}


public void setHistorialClinicoCc(String historialClinicoCc) {
	this.historialClinicoCc = historialClinicoCc;
}


public String getDescripcionTecnicaCc() {
	return descripcionTecnicaCc;
}


public void setDescripcionTecnicaCc(String descripcionTecnicaCc) {
	this.descripcionTecnicaCc = descripcionTecnicaCc;
}


public String getNotaCc() {
	return notaCc;
}


public void setNotaCc(String notaCc) {
	this.notaCc = notaCc;
}


public boolean isOpcionInseguraCc() {
	return opcionInseguraCc;
}


public void setOpcionInseguraCc(boolean opcionInseguraCc) {
	this.opcionInseguraCc = opcionInseguraCc;
}


public String getEtiquetasCc() {
	return etiquetasCc;
}


public void setEtiquetasCc(String etiquetasCc) {
	this.etiquetasCc = etiquetasCc;
}


public List<CcPreguntasHdrV1> getListCcPreguntasHdrV1() {
	return listCcPreguntasHdrV1;
}


public void setListCcPreguntasHdrV1(List<CcPreguntasHdrV1> listCcPreguntasHdrV1) {
	this.listCcPreguntasHdrV1 = listCcPreguntasHdrV1;
}
public GuestPreferences getGuestPreferences() {
	return guestPreferences;
}

public void setGuestPreferences(GuestPreferences guestPreferences) {
	this.guestPreferences = guestPreferences;
}

}
