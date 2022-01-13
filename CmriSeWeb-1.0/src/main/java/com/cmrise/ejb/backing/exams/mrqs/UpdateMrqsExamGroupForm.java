package com.cmrise.ejb.backing.exams.mrqs;

import java.io.IOException;
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
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateMrqsExamGroupForm {

	private MrqsExamenes mrqsExamenesForRead = new MrqsExamenes();  
	private MrqsGrupoHdr mrqsGrupoHdrForUpdate = new MrqsGrupoHdr(); 
	private String tituloGrupo;
	private long numeroMrqsExamen; 
	private long numeroMrqsGrupo;
	private List<MrqsPreguntasHdrV1> listMrqsPreguntasHdrV1 = new ArrayList<MrqsPreguntasHdrV1>();
	private List<MrqsPreguntasHdrV1> selectedsMrqsPreguntasHdrV1 = new ArrayList<MrqsPreguntasHdrV1>();
	private List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas = new ArrayList<MrqsPreguntasHdrV1>();
	private MrqsPreguntasHdrV1 mrqPreguntaForAction = new MrqsPreguntasHdrV1(); 
	
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	MrqsExamenesLocal mrqsExamenesLocal; 
	
	@Inject 
	MrqsGrupoHdrLocal mrqsGrupoHdrLocal; 
	
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal;
	
	@Inject 
	MrqsGrupoLinesLocal mrqsGrupoLinesLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin;
	
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza UpdateMrqsExamGroupForm init()");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroMrqsExamenSV = session.getAttribute("NumeroMrqsExamenSV");
		Object objNumeroMrqsGrupoSV = session.getAttribute("NumeroMrqsGrupoSV");
		this.numeroMrqsExamen = utilitariosLocal.objToLong(objNumeroMrqsExamenSV); 
		this.numeroMrqsGrupo = utilitariosLocal.objToLong(objNumeroMrqsGrupoSV); 
		mrqsExamenesForRead = mrqsExamenesLocal.findByNumeroForRead(this.numeroMrqsExamen,userLogin.getNumeroUsuario()); 
		mrqsGrupoHdrForUpdate = mrqsGrupoHdrLocal.findByNumeroWD(this.numeroMrqsGrupo); 
		MrqsGrupoHdrDto mrqsGrupoHdrDto = mrqsGrupoHdrLocal.findByNumero(this.numeroMrqsGrupo); 
		/**
		this.setTituloGrupo(mrqsGrupoHdrDto.getTitulo());
		mrqsGrupoHdr.setTitulo(mrqsGrupoHdrDto.getTitulo());
		mrqsGrupoHdr.setTema(mrqsGrupoHdrDto.getTema());
		**/
		mrqsGrupoHdrForUpdate.setComentarios(mrqsGrupoHdrDto.getComentarios());
	   
		refreshEntitys(); 
		
		System.out.println("Sale UpdateMrqsExamGroupForm init()");
	 }
	
	public void refreshEntitys() {
		listMrqsGrupoPreguntas = mrqsGrupoLinesLocal.findByNumeroHdrWD(this.getNumeroMrqsGrupo()); 
		/* listMrqsPreguntasHdrV1 = mrqsPreguntasHdrLocal.findWithFilterExam(this.getNumeroMrqsExamen()); */
		listMrqsPreguntasHdrV1 = mrqsPreguntasHdrLocal.findWithFilterExam(this.getNumeroMrqsExamen()
				                                                         ,mrqsExamenesForRead.getAdmonExamen()
				                                                         ,mrqsGrupoHdrForUpdate.getAdmonMateria()
				                                                         ); 
	}
	 
	public String addMRQsQuestions() {
		
		for(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1:selectedsMrqsPreguntasHdrV1) {
			MrqsGrupoLinesDto mrqsGrupoLinesDto = new MrqsGrupoLinesDto();
			mrqsGrupoLinesDto.setNumeroHdr(this.getNumeroMrqsGrupo());
			mrqsGrupoLinesDto.setNumeroPregunta(mrqsPreguntasHdrV1.getNumero());
			mrqsGrupoLinesLocal.insert(mrqsGrupoLinesDto); 
		}
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", (long)this.getNumeroMrqsExamen());
		session.setAttribute("NumeroMrqsGrupoSV", (long)this.getNumeroMrqsGrupo());
		return "Exams-MRQs-Update-Group"; 
	}
	
	public String updateMRQsGroup() {
		MrqsGrupoHdrDto mrqsGrupoHdrDto = new MrqsGrupoHdrDto(); 
		/** mrqsGrupoHdrDto.setTitulo(mrqsGrupoHdr.getTitulo());
		mrqsGrupoHdrDto.setTema(mrqsGrupoHdr.getTema()); **/
		mrqsGrupoHdrDto.setComentarios(mrqsGrupoHdrForUpdate.getComentarios());
		mrqsGrupoHdrLocal.update(this.getNumeroMrqsGrupo(), mrqsGrupoHdrDto);
				
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", (long)this.getNumeroMrqsExamen());
		session.setAttribute("NumeroMrqsGrupoSV", (long)this.getNumeroMrqsGrupo());
		return "Exams-MRQs-Update-Group"; 
	}
	
	public String cancelMRQsGroup() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", (long)this.getNumeroMrqsExamen());
		session.setAttribute("NumeroMrqsGrupoSV", (long)0);
		return "Exams-MRQs-Update"; 
	}
	
	public String deleteMRQsGroup() {
		mrqsGrupoHdrLocal.delete(this.getNumeroMrqsGrupo());
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", (long)this.getNumeroMrqsExamen());
		session.setAttribute("NumeroMrqsGrupoSV", (long)0);
		return "Exams-MRQs-Update"; 
	}
	
	public void selectForAction(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1) {
		mrqPreguntaForAction.setNumero(pMrqsPreguntasHdrV1.getNumero());
	}
	
	public void delete() {
		mrqsGrupoLinesLocal.delete(mrqPreguntaForAction.getNumero());
		refreshEntitys(); 
	}
	
	public void regresar() throws IOException {
		
		if(listMrqsGrupoPreguntas == null || listMrqsGrupoPreguntas.size() == 0) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe asignar por lo menos una pregunta dentro de la materia"));
		}
		else {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);							
		    context.getExternalContext().redirect("/CmriSeWeb/faces/cmrise/examenes/mrqs/UpdateMrqsExam.xhtml");
		}
	}
	
	public String getTituloGrupo() {
		return tituloGrupo;
	}
	public void setTituloGrupo(String tituloGrupo) {
		this.tituloGrupo = tituloGrupo;
	}
	public long getNumeroMrqsExamen() {
		return numeroMrqsExamen;
	}
	public void setNumeroMrqsExamen(long numeroMrqsExamen) {
		this.numeroMrqsExamen = numeroMrqsExamen;
	}
	public long getNumeroMrqsGrupo() {
		return numeroMrqsGrupo;
	}
	public void setNumeroMrqsGrupo(long numeroMrqsGrupo) {
		this.numeroMrqsGrupo = numeroMrqsGrupo;
	}

	public List<MrqsPreguntasHdrV1> getListMrqsPreguntasHdrV1() {
		return listMrqsPreguntasHdrV1;
	}

	public void setListMrqsPreguntasHdrV1(List<MrqsPreguntasHdrV1> listMrqsPreguntasHdrV1) {
		this.listMrqsPreguntasHdrV1 = listMrqsPreguntasHdrV1;
	}

	public List<MrqsPreguntasHdrV1> getSelectedsMrqsPreguntasHdrV1() {
		return selectedsMrqsPreguntasHdrV1;
	}

	public void setSelectedsMrqsPreguntasHdrV1(List<MrqsPreguntasHdrV1> selectedsMrqsPreguntasHdrV1) {
		this.selectedsMrqsPreguntasHdrV1 = selectedsMrqsPreguntasHdrV1;
	}

	public List<MrqsPreguntasHdrV1> getListMrqsGrupoPreguntas() {
		return listMrqsGrupoPreguntas;
	}

	public void setListMrqsGrupoPreguntas(List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas) {
		this.listMrqsGrupoPreguntas = listMrqsGrupoPreguntas;
	}

	public MrqsPreguntasHdrV1 getMrqPreguntaForAction() {
		return mrqPreguntaForAction;
	}

	public void setMrqPreguntaForAction(MrqsPreguntasHdrV1 mrqPreguntaForAction) {
		this.mrqPreguntaForAction = mrqPreguntaForAction;
	}

	public MrqsGrupoHdr getMrqsGrupoHdrForUpdate() {
		return mrqsGrupoHdrForUpdate;
	}

	public void setMrqsGrupoHdrForUpdate(MrqsGrupoHdr mrqsGrupoHdrForUpdate) {
		this.mrqsGrupoHdrForUpdate = mrqsGrupoHdrForUpdate;
	}

	public MrqsExamenes getMrqsExamenesForRead() {
		return mrqsExamenesForRead;
	}

	public void setMrqsExamenesForRead(MrqsExamenes mrqsExamenesForRead) {
		this.mrqsExamenesForRead = mrqsExamenesForRead;
	} 
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}


}
