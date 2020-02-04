package com.cmrise.ejb.backing.admin;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ApplicationScoped
public class MenuForm {

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
}
