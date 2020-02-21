package com.cmrise.ejb.backing.exams.corecases;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.corecases.CcHdrForAction;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.exams.CcExamAsignacionesLocal;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.utils.Utilitarios;

import java.util.ArrayList;
import java.util.List; 


@ManagedBean
@ViewScoped
public class CreateGroupForm {

	private long numeroCcHdr;
	private long numeroCcExamen; 
    private List<CcHdrForAction> selectedCcHdrForAction;
    private List<CcHdrForAction> listCcHdrForAction;
	
    @Inject 
    CcHdrLocal ccHdrLocal; 
    
    @Inject 
    CcExamAsignacionesLocal ccExamAsignacionesLocal; 
    
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza "+this.getClass()+" init()");
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroCcExamenSV = session.getAttribute("NumeroCcExamenSV");
		if(objNumeroCcExamenSV instanceof Long) {
			this.numeroCcExamen = (Long)objNumeroCcExamenSV; 
		}
		this.listCcHdrForAction = ccHdrLocal.findCoreCasesForExam(this.getNumeroCcExamen()); 
		
		/**
		List<CcHdrV1Dto> listCcHdrV1Dto =  ccHdrLocal.findAll(); 
		listCcHdrForAction = new ArrayList<CcHdrForAction>();
		System.out.println("listCcHdrV1Dto.size():"+listCcHdrV1Dto.size());
		for(CcHdrV1Dto ccHdrV1Dto:listCcHdrV1Dto) {
			CcHdrForAction ccHdrForAction = new CcHdrForAction(); 
			ccHdrForAction.setNumero(ccHdrV1Dto.getNumero());
			ccHdrForAction.setNombre(ccHdrV1Dto.getNombre());
			ccHdrForAction.setEstatus(ccHdrV1Dto.getEstatus());
			ccHdrForAction.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
			ccHdrForAction.setTema(ccHdrV1Dto.getTema());
			ccHdrForAction.setTemaDesc(ccHdrV1Dto.getTemaDesc());
			listCcHdrForAction.add(ccHdrForAction); 
		}
		**/
		System.out.println("Sale "+this.getClass()+" init()");
	 }
	
	public String recuperaCoreCases() {
		System.out.println("Entra recuperaCoreCases()");
		List<Long> listLongs = new ArrayList<Long>();
		for(CcHdrForAction ccHdrForAction :selectedCcHdrForAction) {
			System.out.println("ccHdrForAction.getNumero()"+ccHdrForAction.getNumero());
			listLongs.add(ccHdrForAction.getNumero());
			
			CcExamAsignacionesDto ccExamAsignacionesDto = new CcExamAsignacionesDto();
			CcExamenesDto ccExamenesDto = new CcExamenesDto(); 
			ccExamenesDto.setNumero(this.numeroCcExamen);
			ccExamAsignacionesDto.setCcExamene(ccExamenesDto);
			ccExamAsignacionesDto.setNumeroCoreCase(ccHdrForAction.getNumero());
			ccExamAsignacionesDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			ccExamAsignacionesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			ccExamAsignacionesLocal.insert(ccExamAsignacionesDto); 
		}
		System.out.println("Sale recuperaCoreCases()");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", this.numeroCcExamen);
		return "Exams-CoreCases-Update-UpdateCoreCaseGroup"; 
	} 
	 
	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}

	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}

	public List<CcHdrForAction> getSelectedCcHdrForAction() {
		return selectedCcHdrForAction;
	}

	public void setSelectedCcHdrForAction(List<CcHdrForAction> selectedCcHdrForAction) {
		this.selectedCcHdrForAction = selectedCcHdrForAction;
	}

	public List<CcHdrForAction> getListCcHdrForAction() {
		return listCcHdrForAction;
	}

	public void setListCcHdrForAction(List<CcHdrForAction> listCcHdrForAction) {
		this.listCcHdrForAction = listCcHdrForAction;
	}

	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}

	
}
