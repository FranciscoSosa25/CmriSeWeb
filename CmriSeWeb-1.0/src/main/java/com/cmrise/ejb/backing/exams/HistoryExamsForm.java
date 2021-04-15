package com.cmrise.ejb.backing.exams;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.candidates.exams.CandHExamenes;
import com.cmrise.ejb.model.exams.Examenes;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.ExamenesLocal;


@ManagedBean
@ViewScoped
public class HistoryExamsForm {
	
	private List<Examenes> listExamenes = new ArrayList<Examenes>();
	private List<CandHExamenes> listCandidatos = new ArrayList<CandHExamenes>();
	private String tipoExamen; 
	private int idExamen;
	private int id_Examen;
	private String nombreExamen;
	private Date fechaDesde;
	private Date fechaHasta;
	private int tiempo;
	private Boolean showtable = true;
	private String buttonText = "Ver";

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
	
	public List<Examenes> updateTable(int id_examen) {
		changeButtonText();
		clearForm();
	    showtable = !showtable;
	    if(!showtable)
		{
	    	setIdExamen((int)id_examen);
	    	//findByTituloExamen();
	    	//listExamenes=examenesLocal.findByTituloExamen(idExamen, nombreExamen, fechaDesde, fechaHasta, tiempo, tipoExamen);
		}
	    setListCandidatos(examenesLocal.findCandidatesForthisExam(this.idExamen));
	    return listExamenes= examenesLocal.findByTituloExamen(this.idExamen,this.nombreExamen,this.fechaDesde,this.fechaHasta,this.tiempo,this.tipoExamen);
	}
	
	public String toPreviewExamDetails() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
    	 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    	 session.setAttribute("NumeroMrqsExamenSV", this.idExamen);
        	/*return "Preview-Examen-Reactivos";*/
    	 return "Candidate-Exam-Details";
	}
	
	public Boolean getShowtable() {
	    return showtable;
	}
	
	public void findByTituloExamen() {
		listExamenes = examenesLocal.findByTituloExamen(this.idExamen,this.nombreExamen,this.fechaDesde,this.fechaHasta,this.tiempo,this.tipoExamen);
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

	public int getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(int idExamen) {
		this.idExamen = idExamen;
	}

	public String getNombreExamen() {
		return nombreExamen;
	}

	public void setNombreExamen(String nombreExamen) {
		this.nombreExamen = nombreExamen;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	} 
	
	public int getId_Examen() {
		return id_Examen;
	}

	public void setId_Examen(int id_Examen) {
		this.id_Examen = id_Examen;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public void clearForm(){
        setIdExamen(0);
        setNombreExamen("");
        setFechaDesde(null);
        setFechaHasta(null);
        setTiempo(0);
        setTipoExamen("");
        refreshEntity();
    }
	
	public void changeButtonText() {
		if(buttonText.equals("Ver"))
			buttonText = "Regresar";
		else
			buttonText = "Ver";
	}

	public List<CandHExamenes> getListCandidatos() {
		return listCandidatos;
	}

	public void setListCandidatos(List<CandHExamenes> listCandidatos) {
		this.listCandidatos = listCandidatos;
	}
}
