package com.cmrise.ejb.backing.candidates.exams;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.candidates.exams.Examinations;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.services.admin.AdmonCcCandidatosLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcExamenesLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcPreguntasFtaLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcPreguntasHdrLocal;
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

private long numeroCandidato;
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


	@PostConstruct
	public void init() {
		 System.out.println("Entra ManageExamsForm init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroCandidatoSV = session.getAttribute("NumeroCandidatoSV"); 	
		 long longCandidatoSV =  Utilitarios.objToLong(objNumeroCandidatoSV);
		 this.setNumeroCandidato(longCandidatoSV);
		 refreshEntity();
		 System.out.println("Sale ManageExamsForm init()");
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
		candCcExamenesDto.setNumeroUsuario(this.getNumeroCandidato());
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
		
		System.out.println("Sale ManageExamsForm createCandCcExamen()");
		return "Candidates-Exam";
	} 
	
	public void refreshEntity() {
		listExaminations = admonCcCandidatosLocal.findExaminationsByCandidato(this.getNumeroCandidato()); 
	}
	
	public long getNumeroCandidato() {
		return numeroCandidato;
	}

	public void setNumeroCandidato(long numeroCandidato) {
		this.numeroCandidato = numeroCandidato;
	}

	public List<Examinations> getListExaminations() {
		return listExaminations;
	}

	public void setListExaminations(List<Examinations> listExaminations) {
		this.listExaminations = listExaminations;
	}		 

}
