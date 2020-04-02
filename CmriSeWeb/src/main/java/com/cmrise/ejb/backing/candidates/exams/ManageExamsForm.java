package com.cmrise.ejb.backing.candidates.exams;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.candidates.exams.Examinations;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.services.admin.AdmonCcCandidatosLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcExamenesLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcPreguntasFtaLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcPreguntasHdrLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.ejb.services.exams.CcExamAsignacionesLocal;
import com.cmrise.jpa.dto.candidates.exams.CandCcExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasFtaDto;
import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class ManageExamsForm {

private List<Examinations> listExaminations = new ArrayList<Examinations>(); 

@Inject 
AdmonCcCandidatosLocal admonCcCandidatosLocal; 

@Inject 
CcExamAsignacionesLocal ccExamAsignacionesLocal;

@Inject 
CcPreguntasFtaLocal ccPreguntasFtaLocal; 

@Inject 
CandCcExamenesLocal candCcExamenesLocal; 

@Inject 
CandCcPreguntasHdrLocal candCcPreguntasHdrLocal; 

@Inject 
CandCcPreguntasFtaLocal candCcPreguntasFtaLocal; 

@Inject 
CandExamenesLocal candExamenesLocal; 


@ManagedProperty(value="#{userLogin}")
private UserLogin userLogin; 

	@PostConstruct
	public void init() {
		 System.out.println("Entra ManageExamsForm init()");
		 System.out.println("userLogin.getNumeroUsuario():"+userLogin.getNumeroUsuario());
		 refreshEntity();
		 System.out.println("Sale ManageExamsForm init()");
	}
	
	public void buscarExamenes() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 refreshEntity();
		 System.out.println("listExaminations.size():"+listExaminations.size());
		 if(0==listExaminations.size()) {
			 context.addMessage(null, new FacesMessage("A continuacion","Se presentan algunas referencias") );
			 context.addMessage(null, new FacesMessage("El candidato","No tiene examenes asociados") );
			 context.addMessage(null, new FacesMessage("Los Examenes","No Estan Activos") );
		 }else {
			 context.addMessage(null, new FacesMessage("Se econtraron","Los siguientes examenes") );
		 }
	}

	public String createCandCcExamen(Examinations pExaminations) {
		System.out.println("Entra ManageExamsForm createCandCcExamen()");
		
		long numeroCandCcExamenTmp = candCcExamenesLocal.findNumeroCandCcExamen(pExaminations.getNumeroCcExamen()
				                                  ,pExaminations.getNumeroAdmonUsuario()
				                                  ); 
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		
		if(0!=numeroCandCcExamenTmp) {
			  session.setAttribute("numeroCandCcExamenSV", numeroCandCcExamenTmp);
			  session.setAttribute("numeroCandCcPreguntaHdrSV", 0);
			  return "Candidates-Exam";
		}
		
		java.util.Date sysUtilFechaDesde = new java.util.Date(); 
		java.sql.Timestamp sysSqlFechaDesde = new java.sql.Timestamp(sysUtilFechaDesde.getTime());
		long tiempoLimite = (long)pExaminations.getTiempoLimite();
		java.util.Date utilFechaHasta = new java.util.Date(sysUtilFechaDesde.getTime()+(1000*60*tiempoLimite));
		java.sql.Timestamp sqlFechaHasta = new java.sql.Timestamp(utilFechaHasta.getTime()); 
		CandCcExamenesDto candCcExamenesDto = new CandCcExamenesDto(); 
		candCcExamenesDto.setFechaEfectivaDesde(sysSqlFechaDesde);
		candCcExamenesDto.setFechaEfectivaHasta(sqlFechaHasta);
		//candCcExamenesDto.setNumeroUsuario(this.getNumeroCandidato());
		candCcExamenesDto.setNumeroCcExamen(pExaminations.getNumeroCcExamen());
		candCcExamenesDto.setEstatus("INICIALIZADO");
		long numeroCandCcExamen = candCcExamenesLocal.insert(candCcExamenesDto); 
		
		List<CcExamAsignaciones> listCcExamAsignaciones = ccExamAsignacionesLocal.findByNumeroExamenWD(pExaminations.getNumeroCcExamen()); 
		
		for(CcExamAsignaciones ccExamAsignaciones:listCcExamAsignaciones) {
			CandCcPreguntasHdrDto candCcPreguntasHdrDto = new CandCcPreguntasHdrDto();
			candCcPreguntasHdrDto.setNumeroCandCcExamen(numeroCandCcExamen);
			candCcPreguntasHdrDto.setNumeroCcPreguntaHdr(ccExamAsignaciones.getNumeroPreguntaHdr());
			candCcPreguntasHdrDto.setFechaEfectivaDesde(sysSqlFechaDesde);
			candCcPreguntasHdrDto.setFechaEfectivaHasta(sqlFechaHasta);
			long longNumeroCcPreguntaHdr = candCcPreguntasHdrLocal.insert(candCcPreguntasHdrDto); 
			
			CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto = ccPreguntasFtaLocal.findDtoByNumeroHdr(ccExamAsignaciones.getNumeroPreguntaHdr()); 
			CandCcPreguntasFtaDto candCcPreguntasFtaDto = new CandCcPreguntasFtaDto();
			candCcPreguntasFtaDto.setNumeroCcCandPreguntaHdr(longNumeroCcPreguntaHdr);
			candCcPreguntasFtaDto.setNumeroCcPreguntaFta(ccPreguntasFtaV1Dto.getNumero());
			candCcPreguntasFtaDto.setFechaEfectivaDesde(sysSqlFechaDesde);
			candCcPreguntasFtaDto.setFechaEfectivaHasta(sqlFechaHasta);
			candCcPreguntasFtaDto.setRespuestaCorrecta(ccPreguntasFtaV1Dto.getRespuestaCorrecta());
			candCcPreguntasFtaLocal.insert(candCcPreguntasFtaDto); 
			
		}
		
		  session.setAttribute("numeroCandCcExamenSV", numeroCandCcExamen);
		  session.setAttribute("numeroCandCcPreguntaHdrSV", 0);
		
		System.out.println("Sale ManageExamsForm createCandCcExamen()");
		return "Candidates-Exam";
	} 
	
	public String takeExam(Examinations pExaminations) {
	   FacesContext context = FacesContext.getCurrentInstance(); 
	   HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	   session.setAttribute("NumeroMrqsExamenSV", pExaminations.getNumeroMrqsExamen());
	  return "Candidates-MRQs-Exam"; 	
	}
	
	public void refreshEntity() {
		listExaminations = candExamenesLocal.findByUsuario(userLogin.getNumeroUsuario()); 
	}
	
	public List<Examinations> getListExaminations() {
		return listExaminations;
	}

	public void setListExaminations(List<Examinations> listExaminations) {
		this.listExaminations = listExaminations;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}		 

}
