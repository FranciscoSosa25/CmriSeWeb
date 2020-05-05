package com.cmrise.ejb.backing.corecases;

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

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;

@ManagedBean
@ViewScoped
public class UpdateCoreCaseForm {

   private CcHdrV1 ccHdrV1; 	
   private long numeroCcHdr;
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
	ccHdrV1 = ccHdrLocal.findByNumeroObjMod(this.numeroCcHdr);

	listCcPreguntasHdrV1 = ccHdrV1.getListCcPreguntasHdrV1();
	
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
  
  public void actualizar () {
	  System.out.println("Entra actualizar");
	  /**
	  CcHdrDto ccHdrDto = new CcHdrDto();
	  //ccHdrDto.setNombre(this.getNombreCc());
	  ccHdrDto.setEstatus(this.getEstatusCc());
	  //ccHdrDto.setTema(this.getTemaCc());
	  ccHdrDto.setEtiquetas(this.getEtiquetasCc());
	  ccHdrDto.setHistorialClinico(this.getHistorialClinicoCc());
	  ccHdrDto.setDescripcionTecnica(this.getDescripcionTecnicaCc());
	  ccHdrDto.setNota(this.getNotaCc());
	  ccHdrLocal.update(this.getNumeroCcHdr(), ccHdrDto);
	  **/
	  FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage("Se actualizaron los datos correctamente", "Actualizacion correcta"));
	  System.out.println("Sale Actualizar");
  }

  
	public String saveAndPreview() {	     
		 actualizar(); 
		 getGuestPreferences().setTheme("deep-purple");
	     FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());	
	     System.out.println("Sale saveAndPreview()");
	    return "CoreCase-Preview"; 
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
public GuestPreferences getGuestPreferences() {
	return guestPreferences;
}

public void setGuestPreferences(GuestPreferences guestPreferences) {
	this.guestPreferences = guestPreferences;
}


public CcHdrV1 getCcHdrV1() {
	return ccHdrV1;
}


public void setCcHdrV1(CcHdrV1 ccHdrV1) {
	this.ccHdrV1 = ccHdrV1;
}

}
