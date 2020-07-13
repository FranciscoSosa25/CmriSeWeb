package com.cmrise.ejb.backing.corecases;

import java.sql.Timestamp;
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

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.utils.Utilitarios;

import jj2000.j2k.codestream.HeaderInfo.SOT;

@ManagedBean
@ViewScoped
public class UpdateCoreCaseForm {

   private CcHdrV1 ccHdrV1; 
   private CcHdrDto ccHdrDto;

   private long numeroCcHdr;
	private String estatus;
	private Timestamp fechaActualizacion;
	private CcPreguntasHdrV1 ccPreguntasHdrV1ForAction = new CcPreguntasHdrV1();

   
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
  public void selectForAction(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
	  ccPreguntasHdrV1ForAction.setNumero(pCcPreguntasHdrV1.getNumero());
	}
	
  public void deletePregunta(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
System.out.println("Entra deletePregunta");
         boolean deleteIn = false; 
	    // System.out.println("la pregunta es:"+pCcPreguntasHdrV1.getNumero());
//	     long lNumeroPreguntaHdr = pCcPreguntasHdrV1.getNumero();
		 ccHdrLocal.deletePregunta(pCcPreguntasHdrV1.getNumero());
		 System.out.println("se elimino pregunta:"+ccPreguntasHdrV1ForAction.getNumero());
	//	System.out.println("se eliminara la pregunta:"+pCcPreguntasHdrV1.getNumero());
		 refreshEntity();
		 deleteIn = true;
		 PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
		 System.out.println("Sale "+this.getClass()+" delete()");
		 
  } 
  
  public void actualizar () {
	  System.out.println("Entra actualizar CC");
	  System.out.println(""+this.getNumeroCcHdr());
	  CcHdrV1Dto lNumeroCC = ccHdrLocal.findByNumero(this.getNumeroCcHdr());
	  System.out.println("lNumeroCC" +lNumeroCC);
	  CcHdrDto ccHdrDto = new CcHdrDto ();

		 /* INSERTA */
		  ccHdrDto.setNumero(this.numeroCcHdr);  
		  
		  ccHdrDto.setNumero(this.numeroCcHdr);		    
          ccHdrDto.setEstatus(this.getCcHdrV1().getEstatus());
          ccHdrDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(lNumeroCC.getFechaCreacion()));
  		  ccHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(lNumeroCC.getFechaActualizacion()));
          System.out.println("Inserta estatus "+this.getCcHdrV1().getEstatus());
          ccHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
  		ccHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
  		ccHdrDto.setFechaElaboracion(Utilitarios.utilDateToSqlDate(lNumeroCC.getFechaElaboracion()));
         ccHdrLocal.insert(ccHdrDto);
          ccHdrLocal.update(lNumeroCC,ccHdrDto);
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

public CcHdrDto getCcHdrDto() {
	return ccHdrDto;
}


public void setCcHdrDto(CcHdrDto ccHdrDto) {
	this.ccHdrDto = ccHdrDto;
}
public String getEstatus() {
	return estatus;
}

public void setEstatus(String estatus) {
	this.estatus = estatus;
}
public Timestamp getFechaActualizacion() {
	return this.fechaActualizacion;
}

public void setFechaActualizacion(Timestamp fechaActualizacion) {
	this.fechaActualizacion = fechaActualizacion;
}
public CcPreguntasHdrV1 getCcPreguntasHdrV1ForAction() {
	return ccPreguntasHdrV1ForAction;
}

public void setCcPreguntasHdrV1ForAction(CcPreguntasHdrV1 ccPreguntasHdrV1ForAction) {
	this.ccPreguntasHdrV1ForAction = ccPreguntasHdrV1ForAction;
} 	
}
