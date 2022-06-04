package com.cmrise.ejb.backing.exams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.model.candidates.exams.CandHExamenes;
import com.cmrise.ejb.model.exams.CandExamStatusEnum;
import com.cmrise.ejb.model.exams.CandExams;
import com.cmrise.ejb.model.exams.Examenes;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.ExamenesLocal;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class HistoryCandidatesForm {

	private List<CandExamenesV2> listHistoCandV2 = new ArrayList<CandExamenesV2>();
	private List<CandExams> listCandExams = new ArrayList<CandExams>();
	
	
	private String curp; 
	private String nombreUsuario; 
	private String matricula;
	
	private CandExams selCandExams = new CandExams();
	private CandExamenesV2 selCandExamenesV2;
	private String viewGrid = "candExam";
	
	private String addTime = "";
	private String removeTime = "";
	
	
	@Inject
	CandExamenesLocal candExamenesLocal;
	@Inject 
	ExamenesLocal examenesLocal; 

	
	    @PostConstruct
		public void init() {
		 refreshEntity();
		 }		 
		
	    public void findByCURP() {
	    	 listHistoCandV2 = candExamenesLocal.findByCURP(this.curp,this.nombreUsuario,"","");
	    	 listCandExams = candExamenesLocal.findAllByCandidate(0, this.matricula,this.curp);
	    	 
	    }
	    
	    
	    
		public void refreshEntity() {
			 listCandExams = candExamenesLocal.findAllByCandidate(0, "","");
			 FacesContext context = FacesContext.getCurrentInstance(); 
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 CandExams candExams = (CandExams)session.getAttribute("HCCandExams");
			 if(candExams!=null) {
				 session.removeAttribute("HCCandExams");
				 loadCandExams(candExams,viewGrid);
			 }
			 
		}
		
		private void updateCandExamGrid() {
			listHistoCandV2 = candExamenesLocal.findByCURP(selCandExams.getCurp(),String.valueOf(selCandExams.getNumeroUsuario()),"","");
		}
		
		
		public void loadCandExams(CandExams candExams, String viewGrid) {
			this.viewGrid = viewGrid;
			this.selCandExams = candExams;
			updateCandExamGrid();
		}
		
		public void selectCandExam(CandExamenesV2 pCandExamenesV2) {
			this.selCandExamenesV2 = pCandExamenesV2;
			
		}
		
		public void decreaseCandExamsTime() {
			int val = Utilitarios.strToInt(getRemoveTime())*-1;
			candExamenesLocal.updateExamTime(this.selCandExamenesV2.getNumero(), val);
			setRemoveTime("");
			updateCandExamGrid();
		}
		
		public void increaseCandExamsTime() {			
			int val = Utilitarios.strToInt(getAddTime());			
			candExamenesLocal.updateExamTime(this.selCandExamenesV2.getNumero(), val);
			setRemoveTime("");
			updateCandExamGrid();
		}
		
		
		
		
		public void resumeCandExams(CandExamenesV2 pCandExamenesV2) {			
			updateStatus(pCandExamenesV2.getNumero(),CandExamStatusEnum.RESUME.getStatus());
			updateCandExamGrid();
		}
		
		public void pauseCandExams(CandExamenesV2 pCandExamenesV2) {
			updateStatus(pCandExamenesV2.getNumero(),CandExamStatusEnum.PAUSAR.getStatus());
			updateCandExamGrid();
		}
		
		
		public void suspendCandExams(CandExamenesV2 pCandExamenesV2) {			
			updateStatus(pCandExamenesV2.getNumero(),CandExamStatusEnum.SUSPENDER.getStatus());
			updateCandExamGrid();	
		}

		
		private void updateStatus(long numeroCandExamen, String status ) {
			CandExamenesDto candExamenesDto = new CandExamenesDto();
			candExamenesDto.setEstatus(status);
			candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
		}
		
		
		
		
		
		 public String candidatesDetail(CandExamenesV2 pCandExamenesV2) {
			 FacesContext context = FacesContext.getCurrentInstance(); 
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 Examenes exames = examenesLocal.findByIdExamen((int)pCandExamenesV2.getNumeroExamen());
			 CandHExamenes candHExamenes = examenesLocal.findCandidatesExamDetails((int)pCandExamenesV2.getNumeroExamen(), pCandExamenesV2.getNumeroUsuario());
			 session.setAttribute("NumeroCandExamenSV", pCandExamenesV2.getNumero());
			 session.setAttribute("NumeroExamenSV", pCandExamenesV2.getNumeroExamen());
			 session.setAttribute("TipoExamenSV", pCandExamenesV2.getTipo());
			 
			 session.setAttribute("HCCandExams", this.selCandExams);
			 session.setAttribute("CEDExam", exames);
	    	 session.setAttribute("CEDCandHExamenes", candHExamenes);
	    	 session.setAttribute("CEDReturnView", "Historial-Candidatos");
	        	/*return "Preview-Examen-Reactivos";*/
	    	 return "Candidate-Exam-Details";
			 //return "Historial-Candidatos-Detail"; 
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
		public String getNombreUsuario() {
			return nombreUsuario;
		}

		public void setNombreUsuario(String nombreUsuario) {
			this.nombreUsuario = nombreUsuario;
		}
		

		public List<CandExams> getListCandExams() {
			return listCandExams;
		}

		public void setListCandExams(List<CandExams> listCandExams) {
			this.listCandExams = listCandExams;
		}

		public CandExams getSelCandExams() {
			return selCandExams;
		}

		public void setSelCandExams(CandExams selCandExams) {
			this.selCandExams = selCandExams;
		}

		public String getMatricula() {
			return matricula;
		}

		public void setMatricula(String matricula) {
			this.matricula = matricula;
		}

		public String getViewGrid() {
			return viewGrid;
		}

		public void setViewGrid(String viewGrid) {
			this.viewGrid = viewGrid;
		}

		public String getAddTime() {
			return addTime;
		}

		public void setAddTime(String addTime) {
			this.addTime = addTime;
		}

		public String getRemoveTime() {
			return removeTime;
		}

		public void setRemoveTime(String removeTime) {
			this.removeTime = removeTime;
		}

		public CandExamenesV2 getSelCandExamenesV2() {
			return selCandExamenesV2;
		}

		public void setSelCandExamenesV2(CandExamenesV2 selCandExamenesV2) {
			this.selCandExamenesV2 = selCandExamenesV2;
		}
		
		
		
		
		
}
