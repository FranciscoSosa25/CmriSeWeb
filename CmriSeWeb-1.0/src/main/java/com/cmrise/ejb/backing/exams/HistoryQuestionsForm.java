package com.cmrise.ejb.backing.exams;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class HistoryQuestionsForm {

	 private List<CandExamRespuestasV1> listCandExamRespuestasV1 = new ArrayList<CandExamRespuestasV1>(); 
	 
	 @Inject 
	 CandExamRespuestasLocal candExamRespuestasLocal; 
	
	 @PostConstruct
	 public void init() {
	 System.out.println("Entra "+this.getClass()+" init()");
	 FacesContext context = FacesContext.getCurrentInstance(); 
	 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	 Object objNumeroCandExamenSV = session.getAttribute("NumeroCandExamenSV"); 
	 long numeroCandExamenSV = Utilitarios.objToLong(objNumeroCandExamenSV); 
	 listCandExamRespuestasV1 = candExamRespuestasLocal.findV1ObjModByNumeroCandExamen(numeroCandExamenSV); 
	 System.out.println("Sale "+this.getClass()+" init()");
	 }

	public List<CandExamRespuestasV1> getListCandExamRespuestasV1() {
		return listCandExamRespuestasV1;
	}

	public void setListCandExamRespuestasV1(List<CandExamRespuestasV1> listCandExamRespuestasV1) {
		this.listCandExamRespuestasV1 = listCandExamRespuestasV1;
	}
}
