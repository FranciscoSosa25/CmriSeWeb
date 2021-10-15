package com.cmrise.ejb.backing.exams;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.ejb.model.candidates.exams.CandHExamenes;
import com.cmrise.ejb.model.exams.Examenes;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.ExamenesLocal;
import com.cmrise.utils.Utilitarios;


@ManagedBean
@ViewScoped
public class CandidateExamDetailsForm {
	
	private Examenes presentExamenes = new Examenes();
	private String returnView;
	private CandHExamenes presentCandHExamenes = new CandHExamenes();
	private List<CandExamRespuestasV1> listCandExamRespuestasV1 = new ArrayList<CandExamRespuestasV1>();
	 
	
	 @Inject 
	 CandExamRespuestasLocal candExamRespuestasLocal; 

	
	

	@Inject
	CandExamenesLocal candExamenesLocal;
	
	@Inject 
	ExamenesLocal examenesLocal; 
	
	 @PostConstruct
		public void init() {
		 System.out.println("Exam");
		 refreshEntity();
		 System.out.println("Sale "+this.getClass()+" init()");
		 }		 
		
	    public void refreshEntity() {
	     
	     FacesContext context = FacesContext.getCurrentInstance(); 
    	 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    	 
    	 this.presentExamenes = (Examenes) session.getAttribute("CEDExam");
    	 this.presentCandHExamenes = (CandHExamenes)session.getAttribute("CEDCandHExamenes");
    	 listCandExamRespuestasV1 = candExamRespuestasLocal.findV1ObjModByNumeroCandExamen(this.presentCandHExamenes.getNumeroCandExamen());
    	 
    	 this.returnView = (String)session.getAttribute("CEDReturnView");
    	 setScore();
    	 
	     
	    }

	public String toDetailExam(Examenes pExamenes) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroExamenSV", pExamenes.getNumero());
		session.setAttribute("TipoExamenSV", pExamenes.getTipoExamenCode());
		return "History-Exams-Detail"; 
	}
	
	
	public String returnToView() {
		return this.returnView;
		
	}
	public void setScore() {
		double totalScore = 0d;
		double obtainScore = 0d;
		double avgScore = 0d;
		double percentage = 0d;
		if(listCandExamRespuestasV1 !=null) {
			for(CandExamRespuestasV1 candExamRespuestasV1:listCandExamRespuestasV1) {
				totalScore +=candExamRespuestasV1.getValorPuntuacion();
				obtainScore +=candExamRespuestasV1.getPuntuacion();
			}
			avgScore = obtainScore/listCandExamRespuestasV1.size();
			percentage = obtainScore > 0 ? (obtainScore/totalScore) * 100 : obtainScore;
		
		}
		
		this.presentCandHExamenes.setTotalScore(String.format("%.2f",totalScore));
		this.presentCandHExamenes.setObtainScore(String.format("%.2f",obtainScore));
		this.presentCandHExamenes.setAvgScore(String.format("%.2f",avgScore));
		this.presentCandHExamenes.setPercentage(String.format("%.2f",percentage));
		
	}
	
	
	

	public Examenes getPresentExamenes() {
		return presentExamenes;
	}

	public void setPresentExamenes(Examenes presentExamenes) {
		this.presentExamenes = presentExamenes;
	}

	public CandHExamenes getPresentCandHExamenes() {
		return presentCandHExamenes;
	}

	public void setPresentCandHExamenes(CandHExamenes presentCandHExamenes) {
		this.presentCandHExamenes = presentCandHExamenes;
	}
	
	public List<CandExamRespuestasV1> getListCandExamRespuestasV1() {
		return listCandExamRespuestasV1;
	}

	public void setListCandExamRespuestasV1(List<CandExamRespuestasV1> listCandExamRespuestasV1) {
		this.listCandExamRespuestasV1 = listCandExamRespuestasV1;
	}

	public String getReturnView() {
		return returnView;
	}

	public void setReturnView(String returnView) {
		this.returnView = returnView;
	}
	
	
	
}
