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
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.model.candidates.exams.Examinations;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.services.admin.AdmonCcCandidatosLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcExamenesLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.ejb.services.exams.CcExamAsignacionesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.jpa.dto.candidates.exams.CandCcExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class ManageExamsForm {

private List<CandExamenesV2> listCandExamenesV2 = new ArrayList<CandExamenesV2>(); 

@Inject 
AdmonCcCandidatosLocal admonCcCandidatosLocal; 

@Inject 
CcExamAsignacionesLocal ccExamAsignacionesLocal;

@Inject 
CcPreguntasFtaLocal ccPreguntasFtaLocal; 

@Inject 
CandCcExamenesLocal candCcExamenesLocal; 

@Inject 
CandExamenesLocal candExamenesLocal; 

@Inject 
MrqsGrupoHdrLocal mrqsGrupoHdrLocal; 

@Inject 
MrqsGrupoLinesLocal mrqsGrupoLinesLocal; 

@Inject 
CandExamRespuestasLocal candExamRespuestasLocal; 

@Inject 
MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal;  


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
		 System.out.println("listCandExamenesV2.size():"+listCandExamenesV2.size());
		 if(0==listCandExamenesV2.size()) {
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
		
		  session.setAttribute("numeroCandCcExamenSV", numeroCandCcExamen);
		  session.setAttribute("numeroCandCcPreguntaHdrSV", 0);
		
		System.out.println("Sale ManageExamsForm createCandCcExamen()");
		return "Candidates-Exam";
	} 
	
	public String takeExam(CandExamenesV2 pCandExamenesV2) {
	   System.out.println("pCandExamenesV2.getTipo():"+pCandExamenesV2.getTipo());
	   if(Utilitarios.MRQS.equals(pCandExamenesV2.getTipo())) {
		   List<MrqsGrupoHdr> listMrqsGrupoHdr = mrqsGrupoHdrLocal.findByNumeroExamen(pCandExamenesV2.getNumeroExamen()); 
	       for(MrqsGrupoHdr idx:listMrqsGrupoHdr) {
	    	   List<MrqsGrupoLines> listMrqsGrupoLines =  mrqsGrupoLinesLocal.findByNumeroHdr(idx.getNumero()); 
	    	   for(MrqsGrupoLines jdx:listMrqsGrupoLines) {
	    		   long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(jdx.getNumeroPregunta()); 
	    		   System.out.println("numeroFta:"+numeroFta);
	    		   int intValidaRegistro = candExamRespuestasLocal.validaRegistro(pCandExamenesV2.getNumero()
	    				                                 ,idx.getNumero()
	    				                                 ,jdx.getNumeroPregunta()
	    				                                 ,numeroFta
	    				                                 );
	    		   System.out.println("intValidaRegistro:"+intValidaRegistro);
	    		   if(0==intValidaRegistro) {
	    		   CandExamRespuestasDto candExamRespuestasDto = new CandExamRespuestasDto();
	    		   candExamRespuestasDto.setNumeroCandExamen(pCandExamenesV2.getNumero());
	    		   candExamRespuestasDto.setNumeroGrupo(idx.getNumero());
	    		   candExamRespuestasDto.setNumeroPreguntaHdr(jdx.getNumeroPregunta());
	    		   candExamRespuestasDto.setNumeroPreguntaFta(numeroFta);
	    		   candExamRespuestasDto.setCreadoPor(userLogin.getNumeroUsuario());
	    		   candExamRespuestasDto.setActualizadoPor(userLogin.getNumeroUsuario());
	    		   candExamRespuestasLocal.insert(candExamRespuestasDto); 
	    		   }
	    	   }
	       }
	     
	       FacesContext context = FacesContext.getCurrentInstance(); 
		   HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		   System.out.println("put NumeroCandExamenSV:"+pCandExamenesV2.getNumero());
		   System.out.println("put NumeroMrqsExamenSV:"+pCandExamenesV2.getNumeroExamen());
		   session.setAttribute("NumeroCandExamenSV",pCandExamenesV2.getNumero());
		   session.setAttribute("NumeroMrqsExamenSV", pCandExamenesV2.getNumeroExamen());
		   session.setAttribute("tiempoExamen", pCandExamenesV2.getTiempoLimite());
		   session.setAttribute("numCand", userLogin.getNumeroUsuario());
		   session.removeAttribute("NumeroMglSV"); /** CAUSA CONFLICTOS en la siguiente pagina 07042020 **/
		   return "Candidates-MRQs-Exam"; 	
		  
	   }else if(Utilitarios.CORE_CASES.equals(pCandExamenesV2.getTipo())) {
		   
		   List<CcExamAsignaciones> lListCcExamAsignaciones = ccExamAsignacionesLocal.findByNumeroExamenObjMod(pCandExamenesV2.getNumeroExamen()); 
		   for(CcExamAsignaciones i:lListCcExamAsignaciones) {
			   CcHdrV1 ccHdrV1 = i.getCcHdrV1();
			   List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = ccHdrV1.getListCcPreguntasHdrV1(); 
			   for(CcPreguntasHdrV1 j:listCcPreguntasHdrV1) {
				   CcPreguntasFtaV1 ccPreguntasFtaV1 = j.getCcPreguntasFtaV1(); 
				   int intValidaRegistro = candExamRespuestasLocal.validaRegistro(pCandExamenesV2.getNumero()
													                             ,ccHdrV1.getNumero()
													                             ,j.getNumero()
													                             ,ccPreguntasFtaV1.getNumero()
													                            );
					System.out.println("intValidaRegistro:"+intValidaRegistro);
					if(0==intValidaRegistro) {
					CandExamRespuestasDto candExamRespuestasDto = new CandExamRespuestasDto();
					candExamRespuestasDto.setNumeroCandExamen(pCandExamenesV2.getNumero());
					candExamRespuestasDto.setNumeroGrupo(ccHdrV1.getNumero());
					candExamRespuestasDto.setNumeroPreguntaHdr(j.getNumero());
					candExamRespuestasDto.setNumeroPreguntaFta(ccPreguntasFtaV1.getNumero());
					candExamRespuestasDto.setCreadoPor(userLogin.getNumeroUsuario());
					candExamRespuestasDto.setActualizadoPor(userLogin.getNumeroUsuario());
					candExamRespuestasLocal.insert(candExamRespuestasDto); 
					}
			   }
		   }
		   FacesContext context = FacesContext.getCurrentInstance(); 
		   HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		   System.out.println("put NumeroCandExamenSV:"+pCandExamenesV2.getNumero());
		   System.out.println("put NumeroMrqsExamenSV:"+pCandExamenesV2.getNumeroExamen());
		   session.setAttribute("NumeroCandExamenSV",pCandExamenesV2.getNumero());
		   session.setAttribute("NumeroCcExamenSV", pCandExamenesV2.getNumeroExamen());
		   session.removeAttribute("NumeroMglSV"); /** CAUSA CONFLICTOS en la siguiente pagina 07042020 **/
		   return "Candidates-CoreCases-Exam"; 	
	   }
	  
	   return "Candidates-Manage-Exams"; 
	   
	}
	
	public void refreshEntity() {
		listCandExamenesV2 = candExamenesLocal.findByUsuario(userLogin.getNumeroUsuario()); 
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public List<CandExamenesV2> getListCandExamenesV2() {
		return listCandExamenesV2;
	}

	public void setListCandExamenesV2(List<CandExamenesV2> listCandExamenesV2) {
		this.listCandExamenesV2 = listCandExamenesV2;
	}		 

}
