package com.cmrise.ejb.backing.exams.corecases;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class AssignCoreCasesCandidatesForm {
	
	private long numeroCcExamen; 
	
	private List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1 = new ArrayList<AdmonUsuariosRolesV1>();
    private List<CandExamenesV1> listCandExamenesV1 = new ArrayList<CandExamenesV1>(); 
    private List<AdmonUsuariosRolesV1> selectedsAdmonUsuariosRolesV1 = new ArrayList<AdmonUsuariosRolesV1>(); 
	private CandExamenesV1 candExamenesV1ForAction = new CandExamenesV1(); 
    
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject
	AdmonUsuariosRolesLocal admonUsuariosRolesLocal;
	
	@Inject 
	CandExamenesLocal candExamenesLocal; 
	
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza AssignCoreCasesCandidatesForm init()");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroCcExamen = session.getAttribute("NumeroCcExamenSV");
		this.numeroCcExamen = utilitariosLocal.objToLong(objNumeroCcExamen); 
		refreshEntity(); 
	    System.out.println("Finaliza AssignCoreCasesCandidatesForm init()");
	 }
	 
	private void refreshEntity() {
		listCandExamenesV1 = candExamenesLocal.findByExamen(this.getNumeroCcExamen()
				                                           ,Utilitarios.CORE_CASES
				                                           ); 
		listAdmonUsuariosRolesV1 = admonUsuariosRolesLocal.findWithFilterExam(this.getNumeroCcExamen()
				                                                             ,Utilitarios.CORE_CASES 
				                                                             ); 
		
	}
	
	public String cancel() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV",this.getNumeroCcExamen());
		return "Exams-CoreCases-Update"; 
	}
	
	public String addCoreCasesCandidates() {
		for(AdmonUsuariosRolesV1 admonUsuariosRolesV1:selectedsAdmonUsuariosRolesV1) {
			CandExamenesDto candExamenesDto = new CandExamenesDto(); 
			candExamenesDto.setNumeroUsuario(admonUsuariosRolesV1.getNumeroUsuario());
			candExamenesDto.setNumeroExamen(this.getNumeroCcExamen());
			candExamenesDto.setTipo(Utilitarios.CORE_CASES);
			candExamenesDto.setEstatus(Utilitarios.EE_ASIGNADO);
			candExamenesLocal.insert(candExamenesDto); 
		}
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", this.getNumeroCcExamen());
		return "Assign-CoreCases-Candidates"; 
	}
	
	public void selectForAction(CandExamenesV1 pCandExamenesV1) {
		candExamenesV1ForAction.setNumero(pCandExamenesV1.getNumero());
	}
	
	public void delete() {
		candExamenesLocal.delete(candExamenesV1ForAction.getNumero());
		refreshEntity(); 
	}
	
	public List<AdmonUsuariosRolesV1> getListAdmonUsuariosRolesV1() {
		return listAdmonUsuariosRolesV1;
	}

	public void setListAdmonUsuariosRolesV1(List<AdmonUsuariosRolesV1> listAdmonUsuariosRolesV1) {
		this.listAdmonUsuariosRolesV1 = listAdmonUsuariosRolesV1;
	}


	public List<CandExamenesV1> getListCandExamenesV1() {
		return listCandExamenesV1;
	}

	public void setListCandExamenesV1(List<CandExamenesV1> listCandExamenesV1) {
		this.listCandExamenesV1 = listCandExamenesV1;
	}

	public List<AdmonUsuariosRolesV1> getSelectedsAdmonUsuariosRolesV1() {
		return selectedsAdmonUsuariosRolesV1;
	}

	public void setSelectedsAdmonUsuariosRolesV1(List<AdmonUsuariosRolesV1> selectedsAdmonUsuariosRolesV1) {
		this.selectedsAdmonUsuariosRolesV1 = selectedsAdmonUsuariosRolesV1;
	}

	public CandExamenesV1 getCandExamenesV1ForAction() {
		return candExamenesV1ForAction;
	}

	public void setCandExamenesV1ForAction(CandExamenesV1 candExamenesV1ForAction) {
		this.candExamenesV1ForAction = candExamenesV1ForAction;
	}

	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}
	
	

}
