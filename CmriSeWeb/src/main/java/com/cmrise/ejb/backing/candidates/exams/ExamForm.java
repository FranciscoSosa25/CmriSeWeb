package com.cmrise.ejb.backing.candidates.exams;

import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.candidates.exams.CandCcExamenesV1;
import com.cmrise.ejb.services.candidates.exams.CandCcExamenesLocal;
import com.cmrise.ejb.services.candidates.exams.CandCcPreguntasFtaLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class ExamForm {
    private long numeroCandCcPreguntaFta; 
	private long numeroCandidato;
	private String tituloPregunta; 
	private String textoPregunta; 
	private String textoSugerencias; 
	private String respuestaPregunta; 
	
	private long numeroCandCcExamen; 
    private ListIterator<CandCcExamenesV1> listIterCandCcExamenesV1; 
	
	@Inject 
	CandCcExamenesLocal candCcExamenesLocal; 
	
	@Inject
	CcPreguntasFtaLocal ccPreguntasFtaLocal; 
	
	@Inject 
	CandCcPreguntasFtaLocal candCcPreguntasFtaLocal; 
	
	@PostConstruct
	public void init() {
		 System.out.println("Entra ExamForm init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroCandidatoSV = session.getAttribute("NumeroCandidatoSV"); 	
		 Object objNumeroCandCcExamenSV = session.getAttribute("numeroCandCcExamenSV"); 
		 this.numeroCandidato = Utilitarios.objToLong(objNumeroCandidatoSV); 
		 this.numeroCandCcExamen = Utilitarios.objToLong(objNumeroCandCcExamenSV); 
		 System.out.println("this.numeroCandCcExamen:"+this.numeroCandCcExamen);
		 List<CandCcExamenesV1> listCandCcExamenesV1 = candCcExamenesLocal.findCandCcExamInfoByNumero(this.numeroCandCcExamen); 
		 listIterCandCcExamenesV1 = listCandCcExamenesV1.listIterator();
		 System.out.println(listIterCandCcExamenesV1);
		 if(listIterCandCcExamenesV1.hasNext()) {
		 System.out.println("*");
		 CandCcExamenesV1 candCcExamenesV1 = listIterCandCcExamenesV1.next();
		 CcPreguntasFtaDto ccPreguntasFtaDto = ccPreguntasFtaLocal.findDtoByNumeroFta(candCcExamenesV1.getNumeroCpf()); 
		 this.setTituloPregunta(ccPreguntasFtaDto.getTituloPregunta());
		 this.setTextoPregunta(ccPreguntasFtaDto.getTextoPregunta());
		 this.setTextoSugerencias(ccPreguntasFtaDto.getTextoSugerencias());
		 }
		 System.out.println("Entra ExamForm init()");
	}
	
	public void backAction() {
		 System.out.println("Entra backAction");
		 int previousIndex = getListIterCandCcExamenesV1().previousIndex();
    	 System.out.println("previousIndex:"+previousIndex);
    	 if(getListIterCandCcExamenesV1().hasPrevious()) {
    	 CandCcExamenesV1 candCcExamenesV1 = getListIterCandCcExamenesV1().previous(); 
    	 CcPreguntasFtaDto ccPreguntasFtaDto = ccPreguntasFtaLocal.findDtoByNumeroFta(candCcExamenesV1.getNumeroCpf()); 
		 this.setTituloPregunta(ccPreguntasFtaDto.getTituloPregunta());
		 this.setTextoPregunta(ccPreguntasFtaDto.getTextoPregunta());
		 this.setTextoSugerencias(ccPreguntasFtaDto.getTextoSugerencias());
		 this.setNumeroCandCcPreguntaFta(candCcExamenesV1.getNumeroCpf());
    	 }
    	 System.out.println("Sale backAction");
	}
	
    public void saveTrxAction() {
    	 System.out.println("Entra saveTrxAction");
    	 candCcPreguntasFtaLocal.update(this.getNumeroCandCcPreguntaFta()
    			                      , this.getRespuestaPregunta()
    			                      );
    	 int nextIndex = getListIterCandCcExamenesV1().nextIndex(); 
    	 System.out.println("nextIndex:"+nextIndex);
    	 if(getListIterCandCcExamenesV1().hasNext()) {
        	 CandCcExamenesV1 candCcExamenesV1 = getListIterCandCcExamenesV1().next(); 
        	 CcPreguntasFtaDto ccPreguntasFtaDto = ccPreguntasFtaLocal.findDtoByNumeroFta(candCcExamenesV1.getNumeroCpf()); 
    		 this.setTituloPregunta(ccPreguntasFtaDto.getTituloPregunta());
    		 this.setTextoPregunta(ccPreguntasFtaDto.getTextoPregunta());
    		 this.setTextoSugerencias(ccPreguntasFtaDto.getTextoSugerencias());
    		 this.setNumeroCandCcPreguntaFta(candCcExamenesV1.getNumeroCpf());
        	 }
    	 System.out.println("Sale saveTrxAction");
	}

    public void skipAction() {
    	 System.out.println("Entra skipAction");
    	 int nextIndex = getListIterCandCcExamenesV1().nextIndex(); 
    	 System.out.println("nextIndex:"+nextIndex);
    	 if(getListIterCandCcExamenesV1().hasNext()) {
    	 CandCcExamenesV1 candCcExamenesV1 = getListIterCandCcExamenesV1().next(); 
    	 CcPreguntasFtaDto ccPreguntasFtaDto = ccPreguntasFtaLocal.findDtoByNumeroFta(candCcExamenesV1.getNumeroCpf()); 
		 this.setTituloPregunta(ccPreguntasFtaDto.getTituloPregunta());
		 this.setTextoPregunta(ccPreguntasFtaDto.getTextoPregunta());
		 this.setTextoSugerencias(ccPreguntasFtaDto.getTextoSugerencias());
		 this.setNumeroCandCcPreguntaFta(candCcExamenesV1.getNumeroCpf());
    	 }
    	 System.out.println("Sale skipAction");
	}
    
	public long getNumeroCandidato() {
		return numeroCandidato;
	}

	public void setNumeroCandidato(long numeroCandidato) {
		this.numeroCandidato = numeroCandidato;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoSugerencias() {
		return textoSugerencias;
	}

	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}

	public long getNumeroCandCcExamen() {
		return numeroCandCcExamen;
	}

	public void setNumeroCandCcExamen(long numeroCandCcExamen) {
		this.numeroCandCcExamen = numeroCandCcExamen;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	private ListIterator<CandCcExamenesV1> getListIterCandCcExamenesV1() {
		return listIterCandCcExamenesV1;
	}

	private void setListIterCandCcExamenesV1(ListIterator<CandCcExamenesV1> listIterCandCcExamenesV1) {
		this.listIterCandCcExamenesV1 = listIterCandCcExamenesV1;
	}

	public String getRespuestaPregunta() {
		return respuestaPregunta;
	}

	public void setRespuestaPregunta(String respuestaPregunta) {
		this.respuestaPregunta = respuestaPregunta;
	}

	public long getNumeroCandCcPreguntaFta() {
		return numeroCandCcPreguntaFta;
	}

	public void setNumeroCandCcPreguntaFta(long numeroCandCcPreguntaFta) {
		this.numeroCandCcPreguntaFta = numeroCandCcPreguntaFta;
	}
	
}
