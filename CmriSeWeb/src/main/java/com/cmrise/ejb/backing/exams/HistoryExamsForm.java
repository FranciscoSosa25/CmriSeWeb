package com.cmrise.ejb.backing.exams;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import com.cmrise.ejb.model.exams.Examenes;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.ExamenesLocal;


@ManagedBean
@ViewScoped
public class HistoryExamsForm {
	
	private List<Examenes> listExamenes = new ArrayList<Examenes>(); 
	private String tipoExamen; 
	
	@Inject
	CandExamenesLocal candExamenesLocal;
	
	@Inject 
	ExamenesLocal examenesLocal; 
	
	 @PostConstruct
		public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 refreshEntity();
		 System.out.println("Sale "+this.getClass()+" init()");
		 }		 
		
	    public void refreshEntity() {
	     listExamenes = examenesLocal.findAllObjMod(); 
	    }

	public String toDetailExam(Examenes pExamenes) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroExamenSV", pExamenes.getNumero());
		session.setAttribute("TipoExamenSV", pExamenes.getTipoExamenCode());
		return "History-Exams-Detail"; 
	}    
	
	public void findByTituloExamen() {
      System.out.println("Sin Implementacion");
	}

	public List<Examenes> getListExamenes() {
		return listExamenes;
	}

	public void setListExamenes(List<Examenes> listExamenes) {
		this.listExamenes = listExamenes;
	}

	public String getTipoExamen() {
		return tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	} 

}
