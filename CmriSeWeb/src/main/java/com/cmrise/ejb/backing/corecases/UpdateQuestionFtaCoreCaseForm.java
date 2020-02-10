package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateQuestionFtaCoreCaseForm {
	private long numeroCcHdr;
	
	private String tituloPreguntaHdr;
	
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	
	private CcPreguntasHdrV1 ccPreguntasHdrV1ForAction = new CcPreguntasHdrV1();
	
	
	private boolean ftaRecord; 
	private String tituloPreguntaFta;
	private String textoPreguntaFta; 
	private String textoSugerenciasFta; 
	private String respuestaCorrecta; 
	private long numeroFtaRecord;
	
	private List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
	   
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	CcPreguntasHdrLocal ccPreguntasHdrLocal; 

	@Inject 
	CcPreguntasFtaLocal ccPreguntasFtaLocal; 
	
	@PostConstruct
    public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 refreshEntity();      
	     System.out.println("Sale "+this.getClass()+" init()");
	}

	private void refreshEntity() {
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
	     this.setNumeroCcHdr(longNumeroCcHdr);
	     CcPreguntasHdrV1Dto ccPreguntasHdrV1Dto = ccPreguntasHdrLocal.findByNumero(longNumeroCcPreguntaHdr);
	     
	     
	     this.setTituloPreguntaHdr(ccPreguntasHdrV1Dto.getTitulo());
	     if("RESP_TEXTO_LIBRE".equals(ccPreguntasHdrV1Dto.getTipoPregunta())) {
	    	 this.setLimitedFreeTextAnswer(true);
	     }else if("OPCION_MULTIPLE".equals(ccPreguntasHdrV1Dto.getTipoPregunta())) {
	    	 this.setMultipleChoice(true);
	     }
	     
	     ccPreguntasHdrV1ForAction.setNumero(ccPreguntasHdrV1Dto.getNumero());
	     ccPreguntasHdrV1ForAction.setEstatus(ccPreguntasHdrV1Dto.getEstatus());
	     ccPreguntasHdrV1ForAction.setNombre(ccPreguntasHdrV1Dto.getNombre());
	     ccPreguntasHdrV1ForAction.setTitulo(ccPreguntasHdrV1Dto.getTitulo());
	     ccPreguntasHdrV1ForAction.setTipoPregunta(ccPreguntasHdrV1Dto.getTipoPregunta());
	     ccPreguntasHdrV1ForAction.setTemaPregunta(ccPreguntasHdrV1Dto.getTemaPregunta());
	     ccPreguntasHdrV1ForAction.setMaxPuntuacion(ccPreguntasHdrV1Dto.getMaxPuntuacion());
	     ccPreguntasHdrV1ForAction.setSociedad(ccPreguntasHdrV1Dto.getSociedad());
	     ccPreguntasHdrV1ForAction.setEtiquetas(ccPreguntasHdrV1Dto.getEtiquetas());
	     ccPreguntasHdrV1ForAction.setComentarios(ccPreguntasHdrV1Dto.getComentarios());
	     
	     long lNumeroCcFta = ccPreguntasFtaLocal.finNumeroByHdr(longNumeroCcPreguntaHdr); 
	     if(0l!=lNumeroCcFta) {
	    	 this.setFtaRecord(true);
	    	 CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto =ccPreguntasFtaLocal.findDtoByNumeroHdr(longNumeroCcPreguntaHdr);
	         this.setTituloPreguntaFta(ccPreguntasFtaV1Dto.getTituloPregunta());
	         this.setTextoPreguntaFta(ccPreguntasFtaV1Dto.getTextoPregunta());
	         this.setTextoSugerenciasFta(ccPreguntasFtaV1Dto.getTextoSugerencias());
	         this.setRespuestaCorrecta(ccPreguntasFtaV1Dto.getRespuestaCorrecta());
	         this.setNumeroFtaRecord(ccPreguntasFtaV1Dto.getNumero());
	     }else {
	    	 this.setFtaRecord(false);
	     }
	     
	     
	    
	    List<CcPreguntasHdrV1Dto> listCcPreguntasHdrV1Dto =  ccPreguntasHdrLocal.findListByNumeroCcHdr(longNumeroCcHdr);
	    listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
	 	for(CcPreguntasHdrV1Dto ccPreguntasHdrV1DtoTmp :listCcPreguntasHdrV1Dto) {
	 		CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1();
	 		ccPreguntasHdrV1.setNumero(ccPreguntasHdrV1DtoTmp.getNumero());
	     	ccPreguntasHdrV1.setNumeroCcHdr(ccPreguntasHdrV1DtoTmp.getNumeroCcHdr());
	     	ccPreguntasHdrV1.setTitulo(ccPreguntasHdrV1DtoTmp.getTitulo());
	     	ccPreguntasHdrV1.setTipoPreguntaDesc(ccPreguntasHdrV1DtoTmp.getTipoPreguntaDesc());
	     	ccPreguntasHdrV1.setEstatusDesc(ccPreguntasHdrV1DtoTmp.getEstatusDesc());
	     	ccPreguntasHdrV1.setMaxPuntuacion(ccPreguntasHdrV1DtoTmp.getMaxPuntuacion());
	     	ccPreguntasHdrV1.setEtiquetas(ccPreguntasHdrV1DtoTmp.getEtiquetas());
	     	listCcPreguntasHdrV1.add(ccPreguntasHdrV1);
	 	}
	}
	
	public void update() {
		CcPreguntasHdrDto ccPreguntasHdrDto = new CcPreguntasHdrDto();
		ccPreguntasHdrDto.setNumero(ccPreguntasHdrV1ForAction.getNumero());
		ccPreguntasHdrDto.setNombre(ccPreguntasHdrV1ForAction.getNombre());
		ccPreguntasHdrDto.setTitulo(ccPreguntasHdrV1ForAction.getTitulo());
		ccPreguntasHdrDto.setEstatus(ccPreguntasHdrV1ForAction.getEstatus());
		ccPreguntasHdrDto.setTipoPregunta(ccPreguntasHdrV1ForAction.getTipoPregunta());
		ccPreguntasHdrDto.setTemaPregunta(ccPreguntasHdrV1ForAction.getTemaPregunta());
		ccPreguntasHdrDto.setMaxPuntuacion(ccPreguntasHdrV1ForAction.getMaxPuntuacion());
		ccPreguntasHdrDto.setEtiquetas(ccPreguntasHdrV1ForAction.getEtiquetas());
		ccPreguntasHdrDto.setComentarios(ccPreguntasHdrV1ForAction.getComentarios());
		
		if(this.isFtaRecord()) {
			CcPreguntasFtaDto ccPreguntasFtaDto = new CcPreguntasFtaDto();
			ccPreguntasFtaDto.setCcPreguntasHdr(ccPreguntasHdrDto);
			ccPreguntasFtaDto.setTituloPregunta(this.getTituloPreguntaFta());
			ccPreguntasFtaDto.setTextoPregunta(this.getTextoPreguntaFta());
			ccPreguntasFtaDto.setTextoSugerencias(this.getTextoSugerenciasFta());
			ccPreguntasFtaDto.setRespuestaCorrecta(this.getRespuestaCorrecta());
			ccPreguntasFtaLocal.update(this.getNumeroFtaRecord(), ccPreguntasFtaDto);
			
		}else {
			CcPreguntasFtaDto ccPreguntasFtaDto = new CcPreguntasFtaDto();
			ccPreguntasFtaDto.setCcPreguntasHdr(ccPreguntasHdrDto);
			ccPreguntasFtaDto.setTituloPregunta(this.getTituloPreguntaFta());
			ccPreguntasFtaDto.setTextoPregunta(this.getTextoPreguntaFta());
			ccPreguntasFtaDto.setTextoSugerencias(this.getTextoSugerenciasFta());
			ccPreguntasFtaDto.setRespuestaCorrecta(this.getRespuestaCorrecta());
			ccPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			ccPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			ccPreguntasFtaLocal.insert(ccPreguntasFtaDto);
		}
		
		ccPreguntasHdrLocal.update(ccPreguntasHdrV1ForAction.getNumero(), ccPreguntasHdrDto);
		refreshEntity();      
	}
	
	public String updatePregunta(CcPreguntasHdrV1 pCcPreguntasHdrV1) {
		  FacesContext context = FacesContext.getCurrentInstance(); 
		  HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		  long lNumeroCcHdr = pCcPreguntasHdrV1.getNumeroCcHdr();
		  long lNumeroPreguntaHdr = pCcPreguntasHdrV1.getNumero();
		  session.setAttribute("NumeroCcHdrSV", lNumeroCcHdr);
	      session.setAttribute("NumeroCcPreguntaHdrSV", lNumeroPreguntaHdr);
	      return  "Actualizar-Pregunta-Fta-CoreCase";
	 }
	
	public String nuevaPregunta() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());
	    return  "Crear-Pregunta-CoreCase";
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

	public CcPreguntasHdrV1 getCcPreguntasHdrV1ForAction() {
		return ccPreguntasHdrV1ForAction;
	}

	public void setCcPreguntasHdrV1ForAction(CcPreguntasHdrV1 pCcPreguntasHdrV1ForAction) {
		ccPreguntasHdrV1ForAction = pCcPreguntasHdrV1ForAction;
	}

	public String getTituloPreguntaFta() {
		return tituloPreguntaFta;
	}

	public void setTituloPreguntaFta(String tituloPreguntaFta) {
		this.tituloPreguntaFta = tituloPreguntaFta;
	}

	public String getTextoPreguntaFta() {
		return textoPreguntaFta;
	}

	public void setTextoPreguntaFta(String textoPreguntaFta) {
		this.textoPreguntaFta = textoPreguntaFta;
	}

	public String getTextoSugerenciasFta() {
		return textoSugerenciasFta;
	}

	public void setTextoSugerenciasFta(String textoSugerenciasFta) {
		this.textoSugerenciasFta = textoSugerenciasFta;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public boolean isFtaRecord() {
		return ftaRecord;
	}

	public void setFtaRecord(boolean ftaRecord) {
		this.ftaRecord = ftaRecord;
	}

	public long getNumeroFtaRecord() {
		return numeroFtaRecord;
	}

	public void setNumeroFtaRecord(long numeroFtaRecord) {
		this.numeroFtaRecord = numeroFtaRecord;
	}

	public List<CcPreguntasHdrV1> getListCcPreguntasHdrV1() {
		return listCcPreguntasHdrV1;
	}

	public void setListCcPreguntasHdrV1(List<CcPreguntasHdrV1> listCcPreguntasHdrV1) {
		this.listCcPreguntasHdrV1 = listCcPreguntasHdrV1;
	}

	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}

	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}
	     
}
