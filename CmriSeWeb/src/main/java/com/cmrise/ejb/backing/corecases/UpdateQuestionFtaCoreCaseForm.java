package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.services.corecases.CcOpcionMultipleLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
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
	private boolean singleAnswerMode;
	private boolean suffleAnswerOrder; 
	
	private List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
	
	/**********************************************************************
	  Atributos Opcion Multiple
	 **********************************************************************/
	private List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
	private int idxOM = 0; 
	private CcOpcionMultiple ccOpcionMultipleForAction = new CcOpcionMultiple(); 
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	CcPreguntasHdrLocal ccPreguntasHdrLocal; 

	@Inject 
	CcPreguntasFtaLocal ccPreguntasFtaLocal; 
	
	@Inject 
	CcOpcionMultipleLocal  ccOpcionMultipleLocal; 
	
	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
	@PostConstruct
    public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 refreshEntity();      
	     System.out.println("Sale "+this.getClass()+" init()");
	}

	public void addOpcionMultiple() {
		CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
		idxOM++; 
		ccOpcionMultiple.setIdxTemp(idxOM);
		listCcOpcionMultiple.add(ccOpcionMultiple);
	}
	
	public void selectOpcionMultipleForAction(CcOpcionMultiple pCcOpcionMultiple) {
		ccOpcionMultipleForAction.setNumero(pCcOpcionMultiple.getNumero());
		ccOpcionMultipleForAction.setIdxTemp(pCcOpcionMultiple.getIdxTemp());
	}
	
	public void deleteOpcionMultiple() {
		
		if(0!=ccOpcionMultipleForAction.getNumero()) {
			ccOpcionMultipleLocal.delete(ccOpcionMultipleForAction.getNumero());
			for(CcOpcionMultiple i:listCcOpcionMultiple) {
				if(i.getNumero()==ccOpcionMultipleForAction.getNumero()) {
					listCcOpcionMultiple.remove(i);
				}
			}
		}else if(0!=ccOpcionMultipleForAction.getIdxTemp()) {
			for(CcOpcionMultiple i:listCcOpcionMultiple) {
				if(i.getIdxTemp()==ccOpcionMultipleForAction.getIdxTemp()) {
					listCcOpcionMultiple.remove(i);
				}
			}
		}
		
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
	     ccPreguntasHdrV1ForAction  = ccPreguntasHdrLocal.findByNumeroObjMod(longNumeroCcPreguntaHdr);
	     
	     
	     this.setTituloPreguntaHdr(ccPreguntasHdrV1ForAction.getTitulo());
	     if(Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
	    	 this.setLimitedFreeTextAnswer(true);
	     }else if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasHdrV1ForAction.getTipoPregunta())) {
	    	 this.setMultipleChoice(true);
	    	 initListCcOpcionMultiple(); 
	     }
	     
	   
	     
	     long lNumeroCcFta = ccPreguntasFtaLocal.finNumeroByHdr(longNumeroCcPreguntaHdr); 
	     System.out.println("lNumeroCcFta:"+lNumeroCcFta);
	     if(0l!=lNumeroCcFta) {
	    	 this.setFtaRecord(true);
	    	 CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto =ccPreguntasFtaLocal.findDtoByNumeroHdr(longNumeroCcPreguntaHdr);
	         this.setTituloPreguntaFta(ccPreguntasFtaV1Dto.getTituloPregunta());
	         this.setTextoPreguntaFta(ccPreguntasFtaV1Dto.getTextoPregunta());
	         this.setTextoSugerenciasFta(ccPreguntasFtaV1Dto.getTextoSugerencias());
	         this.setRespuestaCorrecta(ccPreguntasFtaV1Dto.getRespuestaCorrecta());
	         this.setNumeroFtaRecord(ccPreguntasFtaV1Dto.getNumero());
	         this.setSingleAnswerMode(ccPreguntasFtaV1Dto.isSingleAnswerMode());
	         this.setSuffleAnswerOrder(ccPreguntasFtaV1Dto.isSuffleAnswerOrder());
	         
	         System.out.println("ccPreguntasFtaV1Dto.getNumero():"+ccPreguntasFtaV1Dto.getNumero());
	         listCcOpcionMultiple = ccPreguntasHdrV1ForAction.getCcPreguntasFtaV1().getListCcOpcionMultiple(); 
	         
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
			ccPreguntasFtaDto.setSingleAnswerMode(this.singleAnswerMode);
			ccPreguntasFtaDto.setSuffleAnswerOrder(this.suffleAnswerOrder);
			ccPreguntasFtaLocal.update(this.getNumeroFtaRecord(), ccPreguntasFtaDto);
			
			if(null!=listCcOpcionMultiple) {
				for(CcOpcionMultiple ccOpcionMultiple:listCcOpcionMultiple) {
					CcOpcionMultipleDto ccOpcionMultipleDto = new CcOpcionMultipleDto();
					if(0!=ccOpcionMultiple.getNumero()) {
					ccOpcionMultipleDto.setNumero(ccOpcionMultiple.getNumero());
					ccOpcionMultipleDto.setEstatus(ccOpcionMultiple.isEstatus());
					ccOpcionMultipleDto.setTextoExplicacion(ccOpcionMultiple.getTextoExplicacion());
					ccOpcionMultipleDto.setTextoRespuesta(ccOpcionMultiple.getTextoRespuesta());
					ccOpcionMultipleDto.setNumeroLinea(ccOpcionMultiple.getNumeroLinea());
					ccOpcionMultipleLocal.update(ccOpcionMultiple.getNumero(), ccOpcionMultipleDto);
					}else {
						ccOpcionMultipleDto.setEstatus(ccOpcionMultiple.isEstatus());
						ccOpcionMultipleDto.setTextoRespuesta(ccOpcionMultiple.getTextoRespuesta());
						ccOpcionMultipleDto.setTextoExplicacion(ccOpcionMultiple.getTextoExplicacion());
						ccOpcionMultipleDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
						ccOpcionMultipleDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
						ccOpcionMultipleDto.setNumeroFta(this.getNumeroFtaRecord());
						ccOpcionMultipleDto.setNumeroLinea(ccOpcionMultiple.getNumeroLinea());
						ccOpcionMultipleLocal.insert(ccOpcionMultipleDto); 
					}
				}
			} /** END if(null!=listCcOpcionMultiple) { **/
			
			
		}else {
			CcPreguntasFtaDto ccPreguntasFtaDto = new CcPreguntasFtaDto();
			ccPreguntasFtaDto.setCcPreguntasHdr(ccPreguntasHdrDto);
			ccPreguntasFtaDto.setTituloPregunta(this.getTituloPreguntaFta());
			ccPreguntasFtaDto.setTextoPregunta(this.getTextoPreguntaFta());
			ccPreguntasFtaDto.setTextoSugerencias(this.getTextoSugerenciasFta());
			ccPreguntasFtaDto.setRespuestaCorrecta(this.getRespuestaCorrecta());
			ccPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			ccPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			ccPreguntasFtaDto.setSingleAnswerMode(this.singleAnswerMode);
			ccPreguntasFtaDto.setSuffleAnswerOrder(this.suffleAnswerOrder);
			
			long numeroPreguntaFta =ccPreguntasFtaLocal.insert(ccPreguntasFtaDto);
			
			if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasHdrDto.getTipoPregunta())) {
				for(CcOpcionMultiple ccOpcionMultiple:listCcOpcionMultiple) {
					updateOrInsertOpcionMultiple(ccOpcionMultiple); 
					CcOpcionMultipleDto ccOpcionMultipleDto = new CcOpcionMultipleDto(); 
					ccOpcionMultipleDto.setEstatus(ccOpcionMultiple.isEstatus());
					ccOpcionMultipleDto.setTextoRespuesta(ccOpcionMultiple.getTextoRespuesta());
					ccOpcionMultipleDto.setTextoExplicacion(ccOpcionMultiple.getTextoExplicacion());
					ccOpcionMultipleDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
					ccOpcionMultipleDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
					ccOpcionMultipleDto.setNumeroFta(numeroPreguntaFta);
					ccOpcionMultipleLocal.insert(ccOpcionMultipleDto); 
				}
			} 
			
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
	
	private void updateOrInsertOpcionMultiple(CcOpcionMultiple pCcOpcionMultiple) {
		System.out.println("Aun sin implementacion");
	}
	
	public String nuevaPregunta() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());
	    return  "Crear-Pregunta-CoreCase";
	}
	
	private void initListCcOpcionMultiple() {
		listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
		int lineNumber = 1; 
		CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple); 
		lineNumber = lineNumber+1; 
		ccOpcionMultiple = new CcOpcionMultiple(); 
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple); 
		lineNumber = lineNumber+1; 
		ccOpcionMultiple = new CcOpcionMultiple(); 
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple); 
		lineNumber = lineNumber+1; 
		ccOpcionMultiple = new CcOpcionMultiple(); 
		ccOpcionMultiple.setNumeroLinea(lineNumber);
		listCcOpcionMultiple.add(ccOpcionMultiple); 
		lineNumber = lineNumber+1; 
	}
	
	public String saveAndPreview() {
		 update();
		 getGuestPreferences().setTheme("deep-purple");
	     FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("NumeroCcHdrSV", this.getNumeroCcHdr());	
	     session.setAttribute("NumeroCcPreguntaHdrSV", this.getCcPreguntasHdrV1ForAction().getNumero());	
	     System.out.println("Sale saveAndPreview()");
	    return "CoreCase-Preview"; 
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

	public List<CcOpcionMultiple> getListCcOpcionMultiple() {
		return listCcOpcionMultiple;
	}

	public void setListCcOpcionMultiple(List<CcOpcionMultiple> listCcOpcionMultiple) {
		this.listCcOpcionMultiple = listCcOpcionMultiple;
	}
	
	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}

	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}

	public boolean isSingleAnswerMode() {
		return singleAnswerMode;
	}

	public void setSingleAnswerMode(boolean singleAnswerMode) {
		this.singleAnswerMode = singleAnswerMode;
	}

	public boolean isSuffleAnswerOrder() {
		return suffleAnswerOrder;
	}

	public void setSuffleAnswerOrder(boolean suffleAnswerOrder) {
		this.suffleAnswerOrder = suffleAnswerOrder;
	}

	public int getIdxOM() {
		return idxOM;
	}

	public void setIdxOM(int idxOM) {
		this.idxOM = idxOM;
	}

	public CcOpcionMultiple getCcOpcionMultipleForAction() {
		return ccOpcionMultipleForAction;
	}

	public void setCcOpcionMultipleForAction(CcOpcionMultiple ccOpcionMultipleForAction) {
		this.ccOpcionMultipleForAction = ccOpcionMultipleForAction;
	}
	
}
