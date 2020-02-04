package com.cmrise.ejb.backing.corecases;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateQuestionFtaCoreCaseForm {

	private String tituloPreguntaHdr;
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	CcPreguntasHdrLocal ccPreguntasHdrLocal; 
	
	@PostConstruct
    public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroCcHdr= session.getAttribute("NumeroCcHdrSV");
	     Object obNumeroCcPreguntaHdrSV = session.getAttribute("NumeroCcPreguntaHdrSV");
	     long longNumeroCcHdr = utilitariosLocal.objToLong(obNumeroCcHdr); 
	     long longNumeroCcPreguntaHdr = utilitariosLocal.objToLong(obNumeroCcPreguntaHdrSV);
	     if(0==longNumeroCcHdr||0==longNumeroCcPreguntaHdr) {
	    	 return;
	     }
	     CcPreguntasHdrV1Dto ccPreguntasHdrV1Dto = ccPreguntasHdrLocal.findByNumero(longNumeroCcPreguntaHdr);
	     
	     this.setTituloPreguntaHdr(ccPreguntasHdrV1Dto.getTitulo());
	     System.out.println("Sale "+this.getClass()+" init()");
	}

	public String getTituloPreguntaHdr() {
		return tituloPreguntaHdr;
	}

	public void setTituloPreguntaHdr(String tituloPreguntaHdr) {
		this.tituloPreguntaHdr = tituloPreguntaHdr;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		if(multipleChoice) {
			this.setLimitedFreeTextAnswer(false);
			this.setIndicateImage(false);
		}
		this.multipleChoice = multipleChoice;
	}

	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}

	public void setLimitedFreeTextAnswer(boolean limitedFreeTextAnswer) {
		if(limitedFreeTextAnswer) {
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
		}
		this.limitedFreeTextAnswer = limitedFreeTextAnswer;
	}

	public boolean isIndicateImage() {
		return indicateImage;
	}

	public void setIndicateImage(boolean indicateImage) {
		if(indicateImage) {
			this.setMultipleChoice(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.indicateImage = indicateImage;
	}
	     
}
