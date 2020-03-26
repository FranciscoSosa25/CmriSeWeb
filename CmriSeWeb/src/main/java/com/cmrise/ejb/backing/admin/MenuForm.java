package com.cmrise.ejb.backing.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.GuestPreferences;

@ManagedBean
@ViewScoped
public class MenuForm {
	
	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 

	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}

	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}
	
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
		session.setAttribute("NumeroCcHdrSV", (long)11);
		session.setAttribute("NumeroCcPreguntaHdrSV", (long)11);
		
		return  "Actualizar-Pregunta-Fta-CoreCase";
	}
	
	public String fpActualizarPruebaExamen() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", (long)11);
		return "Exams-CoreCases-Update"; 
	}
	
	public String fpActualizarPruebaExamenAgregarCasosPrincipales() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", (long)11);
		return "Exams-CoreCases-Update-AddCoreCase"; 
	}
	
	public String fpActualizarPruebaExamenActualizarCasosPrincipales() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", (long)11);
		return "Exams-CoreCases-Update-UpdateCoreCaseGroup"; 
	}
	
	public String fpExamsCoreCasesManageCandidates() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", (long)11);
		return "Exams-CoreCases-Manage-Candidates";
	}
	
	public String fpCandidatesManageExams() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCandidatoSV", (long)7);
		return "Candidates-Manage-Exams";
	}
	
	public String fpCandidatesExamen() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("numeroCandCcExamenSV", (long)29);
		return "Candidates-Exam";
	}
	
	public String fpMrqPreview() {
		this.getGuestPreferences().setTheme("deep-purple");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("mrqNumeroHdrSV", (long)21);
		return "Mrq-Preview"; 
	}
	
	
}
