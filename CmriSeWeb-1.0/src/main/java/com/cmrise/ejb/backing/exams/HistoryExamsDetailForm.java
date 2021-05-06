package com.cmrise.ejb.backing.exams;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class HistoryExamsDetailForm {
	
	private List<CandExamenesV2> listCandExamenesV2 = new ArrayList<CandExamenesV2>();

	@Inject
	CandExamenesLocal candExamenesLocal;
	
	 @PostConstruct
	 public void init() {
	 System.out.println("Entra "+this.getClass()+" init()");
	 FacesContext context = FacesContext.getCurrentInstance(); 
	 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	 Object objNumeroExamen = session.getAttribute("NumeroExamenSV"); 
	 Object objTipoExamen = session.getAttribute("TipoExamenSV"); 
	 long longNumeroExamen = Utilitarios.objToLong(objNumeroExamen); 
	 listCandExamenesV2 = candExamenesLocal.findByExamen(longNumeroExamen, (String)objTipoExamen); 
	 System.out.println("Sale "+this.getClass()+" init()");
	 }		 
	 
	 public String toQuestions(CandExamenesV1 pCandExamenesV1) {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false); 
		 session.setAttribute("NumeroCandExamenSV", pCandExamenesV1.getNumero());
		 return "History-Questions"; 
	 }
	 
	public List<CandExamenesV2> getListCandExamenesV1() {
		return listCandExamenesV2;
	}

	public void setListCandExamenesV1(List<CandExamenesV2> listCandExamenesV2) {
		this.listCandExamenesV2 = listCandExamenesV2;
	} 

}
