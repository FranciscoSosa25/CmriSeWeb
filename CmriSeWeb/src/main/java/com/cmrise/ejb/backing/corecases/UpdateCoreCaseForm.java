package com.cmrise.ejb.backing.corecases;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;

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

   @Inject 
   CcHdrLocal ccHdrLocal; 
   
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
  }


  public String createCoreQuestion(){
	  String retval = null; 
	  retval ="Crear-Pregunta-CoreCase"; 
	  return retval; 
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

}
