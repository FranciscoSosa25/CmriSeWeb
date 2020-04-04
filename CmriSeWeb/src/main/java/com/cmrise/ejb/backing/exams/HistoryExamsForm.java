package com.cmrise.ejb.backing.exams;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;


import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;


@ManagedBean
@ViewScoped
public class HistoryExamsForm {
	


	private List<CandExamenesV2> listCandExamenesV2 = new ArrayList<CandExamenesV2>();
	
	@Inject
	CandExamenesLocal candExamenesLocal;
	
	 @PostConstruct
		public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 refreshEntity();
		 System.out.println("Sale "+this.getClass()+" init()");
			 
		 }		 
		
	    public void refreshEntity() {
	    	 
	    	 listCandExamenesV2 = candExamenesLocal.findAll();

	    	 }
	    

	public List<CandExamenesV2> getListCandExamenesV2() {
		return listCandExamenesV2;
	}

	public void setListCandExamenesV2(List<CandExamenesV2> listCandExamenesV2) {
		this.listCandExamenesV2 = listCandExamenesV2;
	} 


}
