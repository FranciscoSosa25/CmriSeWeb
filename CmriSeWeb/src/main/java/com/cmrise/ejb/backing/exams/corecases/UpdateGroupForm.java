package com.cmrise.ejb.backing.exams.corecases;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.services.exams.CcExamAsignacionesLocal;

import java.util.List; 
import java.util.ArrayList;

@ManagedBean
@ViewScoped
public class UpdateGroupForm {
	
	 private long numeroCcExamen; 
	
	 private List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
	 
	 
	 @Inject 
	 CcExamAsignacionesLocal ccExamAsignacionesLocal; 
	 
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza "+this.getClass()+" init()");
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroCcExamenSV = session.getAttribute("NumeroCcExamenSV");
		if(objNumeroCcExamenSV instanceof Long) {
			this.setNumeroCcExamen((Long)objNumeroCcExamenSV); 
		}else {
			return;
		}
		
		listCcExamAsignaciones = ccExamAsignacionesLocal.findByNumeroExamenWD(this.getNumeroCcExamen());
		
	 }

	public String nuevoGrupo() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", this.getNumeroCcExamen());
		return "Exams-CoreCases-Update-AddCoreCase"; 
	}
	
	public String gestionarCandidatos() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", this.getNumeroCcExamen());
		return "Assign-CoreCases-Candidates"; 
	}
	
	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}

	public List<CcExamAsignaciones> getListCcExamAsignaciones() {
		return listCcExamAsignaciones;
	}

	public void setListCcExamAsignaciones(List<CcExamAsignaciones> listCcExamAsignaciones) {
		this.listCcExamAsignaciones = listCcExamAsignaciones;
	}

}
