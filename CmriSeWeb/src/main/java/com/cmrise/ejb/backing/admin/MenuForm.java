package com.cmrise.ejb.backing.admin;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@ApplicationScoped
public class MenuForm {

	public String fpActualizarCasoPrincipal() {
		String retval = null;
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", (long)6);  
		retval = "Preguntas-Update-CoreCase";
		return retval; 
	}
	
	public String fpCrearPreguntaCasoPrincipal() {
	  FacesContext context = FacesContext.getCurrentInstance(); 
	  HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	  session.setAttribute("NumeroCcHdrSV", (long)6);
	  return  "Crear-Pregunta-CoreCase";
	}
	
	public String fpActualizarPreguntaFtaCoreCase() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", (long)6);
		session.setAttribute("NumeroCcPreguntaHdrSV", (long)5);
		
		return  "Actualizar-Pregunta-Fta-CoreCase";
	}
	
	public String fpActualizarPruebaExamen() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", (long)11);
		return "Exams-CoreCases-Update"; 
	}
	
}
