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

@ManagedBean
@ViewScoped
public class HistoryCandidatesForm {

	private List<CandExamenesV2> listHistoCandV2 = new ArrayList<CandExamenesV2>();
	
	private String curp; 
	
	@Inject
	CandExamenesLocal candExamenesLocal;
	
	    @PostConstruct
		public void init() {
		 refreshEntity();
		 }		 
		
	    public void findByCURP() {
	    	 listHistoCandV2 = candExamenesLocal.findByCURP(this.curp); 
	    }
	    
		public void refreshEntity() {
			 listHistoCandV2 = candExamenesLocal.findAll();
		}
		
		
		 public String candidatesDetail(CandExamenesV2 pCandExamenesV2) {
			 FacesContext context = FacesContext.getCurrentInstance(); 
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false); 
			 session.setAttribute("NumeroCandExamenSV", pCandExamenesV2.getNumero());
			 session.setAttribute("NumeroExamenSV", pCandExamenesV2.getNumeroExamen());
			 session.setAttribute("TipoExamenSV", pCandExamenesV2.getTipo());
			 return "Historial-Candidatos-Detail"; 
		 }
		 
		public List<CandExamenesV2> getListCandExamenesV2() {
    		return listHistoCandV2;
		 }
		
		public void setListCandExamenesV2(List<CandExamenesV2> listCandExamenesV2) {
	    	this.listHistoCandV2 = listCandExamenesV2;
		}

		public String getCurp() {
			return curp;
		}

		public void setCurp(String curp) {
			this.curp = curp;
		}
		
}
