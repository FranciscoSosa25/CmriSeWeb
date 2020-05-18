package com.cmrise.ejb.backing.exams.mrqs.preview;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class PreviewExamenReactivosForm {

	private MrqsExamenes mrqsExamen = new MrqsExamenes(); 
	
	@Inject
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	MrqsExamenesLocal mrqsExamenesLocal; 
	
	@Inject 
	MrqsGrupoHdrLocal mrqsGrupoHdrLocal; 
	
	@Inject 
	MrqsGrupoLinesLocal mrqsGrupoLinesLocal; 
	
	@Inject 
	MrqsOpcionMultipleLocal mrqsOpcionMultipleLocal; 
	
	@Inject 
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	private Date fechaActual  = new Date(); 
	
	@PostConstruct
	public void init() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroMrqsExamenSV = session.getAttribute("NumeroMrqsExamenSV"); 
	     long numeroMrqsExamenSV = Utilitarios.objToLong(objNumeroMrqsExamenSV); 
	     System.out.println("numeroMrqsExamenSV:"+numeroMrqsExamenSV);
	    
	     mrqsExamen = mrqsExamenesLocal.findObjMod(numeroMrqsExamenSV); 
	}

	public String backExamenesReactivos() {
		return "Exams-MRQs-Update"; 
	}
	
	public MrqsExamenes getMrqsExamen() {
		return mrqsExamen;
	}

	public void setMrqsExamen(MrqsExamenes mrqsExamen) {
		this.mrqsExamen = mrqsExamen;
	}
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}


	
}
