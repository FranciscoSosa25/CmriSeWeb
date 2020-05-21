package com.cmrise.ejb.backing.mrq.preview;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class MrqPreviewForm {

	private long numeroHdr; 
	private long numetoFta; 
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private String tituloPregunta; 
	private String textoPregunta; 
	private String textoSugerencias; 
	private String respuestaPreguntaCandidato;
	private String respuestaPreguntaSistema; 
	
	private boolean questionView; 
	private boolean answerView; 
	private boolean correctAnswer; 
	
	private boolean wrongAnswer; 
	private float puntuacion; 
	private String metodoPuntuacion; 
	private int totalCorrectAnswers; 
	/**********************************************************************
	  Atributos Opcion Multiple
	 **********************************************************************/
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
	private boolean singleAnswerMode;
	private boolean suffleAnswerOrder; 
	private String[] respuestasPreguntaCandidato;
	private String correctAnswers="0 Respuesta(s) correctas"; 
	private String wrongAnswers="0 Respuesta(s) incorrectas"; 
	
	/********************************************************************
	 * Attributos Imagenes 
	 */
	
	private List<MrqsImagenesGrp> listPresentMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
	
	
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal; 
	
	@Inject 
	MrqsOpcionMultipleLocal mrqsOpcionMultipleLocal; 
	
	@Inject 
	MrqsImagenesGrpLocal mrqsImagenesGrpLocal;
	
	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
	
	@PostConstruct
	public void init() {
		 System.out.println("Entra MrqPreviewForm init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object objMrqNumeroHdr = session.getAttribute("mrqNumeroHdrSV");
	     this.setNumeroHdr(Utilitarios.objToLong(objMrqNumeroHdr)); 
	     MrqsPreguntasHdrV2Dto  mrqsPreguntasHdrV2Dto = mrqsPreguntasHdrLocal.findV2ByNumeroHdr(this.getNumeroHdr()); 
	     this.setNumetoFta(mrqsPreguntasHdrV2Dto.getNumeroMpf());
	     this.setTituloPregunta(mrqsPreguntasHdrV2Dto.getTituloPregunta());
	     this.setTextoPregunta(mrqsPreguntasHdrV2Dto.getTextoPregunta());
	     this.setTextoSugerencias(mrqsPreguntasHdrV2Dto.getTextoSugerenciasDesc());
	     this.setRespuestaPreguntaSistema(mrqsPreguntasHdrV2Dto.getRespuestaCorrecta());
	     this.puntuacion = Float.parseFloat(mrqsPreguntasHdrV2Dto.getValorPuntuacion()); 
	     this.metodoPuntuacion = mrqsPreguntasHdrV2Dto.getMetodoPuntuacion(); 
	     if(Utilitarios.RESP_TEXTO_LIBRE.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	 this.setLimitedFreeTextAnswer(true);
	     }else if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	 this.setMultipleChoice(true);
	    	 initListMrqsOpcionMultiple(mrqsPreguntasHdrV2Dto.getNumeroMpf(),mrqsPreguntasHdrV2Dto.isSuffleAnswerOrder()); 
	         this.setSingleAnswerMode(mrqsPreguntasHdrV2Dto.isSingleAnswerMode());
	         this.setSuffleAnswerOrder(mrqsPreguntasHdrV2Dto.isSuffleAnswerOrder());
	         this.totalCorrectAnswers = mrqsOpcionMultipleLocal.totalCorrectAnswers(mrqsPreguntasHdrV2Dto.getNumeroMpf()); 
	     }else if(Utilitarios.IMAGEN_INDICADA.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	        this.setIndicateImage(true);	 
	     }
	     this.setQuestionView(true);
	     
	     listPresentMrqsImagenesGrp =  mrqsImagenesGrpLocal.findByFta(mrqsPreguntasHdrV2Dto.getNumeroMpf(),Utilitarios.INTRODUCCION);
         
	     
	     System.out.println("Sale MrqPreviewForm init()");
	}		 
	
	private void initListMrqsOpcionMultiple(long pNumeroMpf
			                               ,boolean pShuffleAnswerOrder
			                               ) {
		listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
		List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto = mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrder(pNumeroMpf, pShuffleAnswerOrder);
        for(MrqsOpcionMultipleDto mrqsOpcionMultipleDto:listMrqsOpcionMultipleDto) {
        	MrqsOpcionMultiple mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
        	mrqsOpcionMultiple.setNumero(mrqsOpcionMultipleDto.getNumero());
        	mrqsOpcionMultiple.setTextoRespuesta(mrqsOpcionMultipleDto.getTextoRespuesta());
        	mrqsOpcionMultiple.setEstatus(mrqsOpcionMultipleDto.isEstatus());
        	listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
        }
	}

	public void  saveProceed() {
	  System.out.println("Entra saveProceed");	
	  this.setAnswerView(true);
	  if(this.isLimitedFreeTextAnswer()) {
		  if(this.getRespuestaPreguntaCandidato().equalsIgnoreCase(this.getRespuestaPreguntaSistema())) {
			  this.setCorrectAnswer(true);
		  }else {
			  this.setWrongAnswer(true);
			  this.setPuntuacion(0);
		  }
	  }else if(this.isMultipleChoice()) {
		 /** List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto = mrqsOpcionMultipleLocal.findByNumeroFta(this.getNumetoFta()); **/
		  if(this.isSingleAnswerMode()) {
			  evaluateIsSingleAnswerMode(listMrqsOpcionMultiple);
		  }else{
			  evaluateNotIsSingleAnswerMode(listMrqsOpcionMultiple);
		  }
	  }
	  System.out.println("Sale saveProceed");	
	}
	
	
   private void evaluateIsSingleAnswerMode(List<MrqsOpcionMultiple> pListMrqsOpcionMultiple) {
	  
	   for(MrqsOpcionMultiple mrqsOpcionMultiple:listMrqsOpcionMultiple) {
			  long longRespuestaCandidato = Long.parseLong(this.getRespuestaPreguntaCandidato()); 
			  if(mrqsOpcionMultiple.getNumero()==longRespuestaCandidato) {
				  mrqsOpcionMultiple.setEstatusCandidato(true); 
			  }
		  }
	   
			for(MrqsOpcionMultiple mrqsOpcionMultiple:pListMrqsOpcionMultiple) {
			 if(mrqsOpcionMultiple.isEstatus()) {
				 String strNumero = mrqsOpcionMultiple.getNumero()+""; 
				 if(strNumero.equals(this.getRespuestaPreguntaCandidato())) {
					 this.setCorrectAnswer(true);
					 break; 
				 }
			  }		 
		    }
		 if(this.isCorrectAnswer()) {
		 this.setCorrectAnswers("1 Respuesta(s) correctas");
		 this.setWrongAnswers("0 Respuesta(s) incorrectas"); 
		 }else {
			this.setWrongAnswer(true);
			this.setCorrectAnswers("0 Respuesta(s) correctas");
		    this.setWrongAnswers("1 Respuesta(s) incorrectas");  
		    this.setPuntuacion(0);
		 }
	}
		
	private void evaluateNotIsSingleAnswerMode(List<MrqsOpcionMultiple> pListMrqsOpcionMultiple) {
	   System.out.println("this.getRespuestasPreguntaCandidato():"+this.getRespuestasPreguntaCandidato());
	   int countCorrectAnswers =0; 
	   int countWrongAnswers = 0; 
	  if(null!=this.getRespuestasPreguntaCandidato()) {
		  String [] array = this.getRespuestasPreguntaCandidato(); 
		  
		  for(int idx =0;idx<array.length;idx=idx+1) {
			  for(MrqsOpcionMultiple mrqsOpcionMultiple:listMrqsOpcionMultiple) {
				  long longRespuestaCandidato = Long.parseLong(array[idx]); 
				  if(mrqsOpcionMultiple.getNumero()==longRespuestaCandidato) {
					  mrqsOpcionMultiple.setEstatusCandidato(true); 
				  }
			  }
		  }
		  
		   for(int idx =0;idx<array.length;idx=idx+1) {
			   System.out.println(array[idx]);
			   Long longValue = Long.parseLong(array[idx]); 
			   int intCorrectWrong = mrqsOpcionMultipleLocal.correctOrWrongAnswer(longValue, this.getNumetoFta()); 
			   if(1==intCorrectWrong) {
				   countCorrectAnswers++;
			   }else if(0==intCorrectWrong) {
				   countWrongAnswers++;
			   }
		   }
	   }
	  System.out.println("countCorrectAnswers:"+countCorrectAnswers);
	  if(countCorrectAnswers>0) {
		  this.setCorrectAnswer(true);
		  System.out.println("metodoPuntuacion:"+this.metodoPuntuacion);
		  if(Utilitarios.PROP_SCORING.equals(this.metodoPuntuacion)) {
			  System.out.println("totalCorrectAnswers:"+this.totalCorrectAnswers);
			  System.out.println("puntuacion:"+this.puntuacion);
		   float floatPuntuacionProp = ((float)countCorrectAnswers/(float)this.totalCorrectAnswers)*this.puntuacion; 
		   System.out.println("floatPuntuacionProp:"+floatPuntuacionProp);
		   this.setPuntuacion(floatPuntuacionProp);
		  }
	  }else {
		  this.setWrongAnswer(true);
		  this.setPuntuacion(0);
	  }
	  
	  this.setCorrectAnswers(countCorrectAnswers+" Respuesta(s) correctas");
	  this.setWrongAnswers(countWrongAnswers+" Respuesta(s) incorrectas"); 
	  
	}

	public String skip() {
		guestPreferences.setTheme(Utilitarios.DEFAULT_THEME);
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroHdrSV", this.getNumeroHdr());
		return "Preguntas-UpdateFreeTextAnswer-NewMrqs";
	}
	
	public String proceed() {
		return skip();
	}
	
	
	public boolean isMultipleChoice() {
		return multipleChoice;
	}
	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}
	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}
	public void setLimitedFreeTextAnswer(boolean limitedFreeTextAnswer) {
		this.limitedFreeTextAnswer = limitedFreeTextAnswer;
	}
	public boolean isIndicateImage() {
		return indicateImage;
	}
	public void setIndicateImage(boolean indicateImage) {
		this.indicateImage = indicateImage;
	}
	public boolean isAnnotatedImage() {
		return annotatedImage;
	}
	public void setAnnotatedImage(boolean annotatedImage) {
		this.annotatedImage = annotatedImage;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public long getNumetoFta() {
		return numetoFta;
	}

	public void setNumetoFta(long numetoFta) {
		this.numetoFta = numetoFta;
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

	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}

	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}

	public List<MrqsOpcionMultiple> getListMrqsOpcionMultiple() {
		return listMrqsOpcionMultiple;
	}

	public void setListMrqsOpcionMultiple(List<MrqsOpcionMultiple> listMrqsOpcionMultiple) {
		this.listMrqsOpcionMultiple = listMrqsOpcionMultiple;
	}

	public boolean isQuestionView() {
		return questionView;
	}

	public void setQuestionView(boolean questionView) {
		if(questionView) {
			this.setAnswerView(false);
		}
		this.questionView = questionView;
	}

	public boolean isAnswerView() {
		return answerView;
	}

	public void setAnswerView(boolean answerView) {
		if(answerView) {
			this.setQuestionView(false);
		}
		this.answerView = answerView;
	}

	public String getRespuestaPreguntaCandidato() {
		return respuestaPreguntaCandidato;
	}

	public void setRespuestaPreguntaCandidato(String respuestaPreguntaCandidato) {
		this.respuestaPreguntaCandidato = respuestaPreguntaCandidato;
	}

	public String getRespuestaPreguntaSistema() {
		return respuestaPreguntaSistema;
	}

	public void setRespuestaPreguntaSistema(String respuestaPreguntaSistema) {
		this.respuestaPreguntaSistema = respuestaPreguntaSistema;
	}

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		if(correctAnswer) {
			this.setWrongAnswer(false);
		}
		this.correctAnswer = correctAnswer;
	}

	public boolean isWrongAnswer() {
		return wrongAnswer;
	}

	public void setWrongAnswer(boolean wrongAnswer) {
		if(wrongAnswer) {
			this.setCorrectAnswer(false);
		}
		this.wrongAnswer = wrongAnswer;
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

	public String[] getRespuestasPreguntaCandidato() {
		return respuestasPreguntaCandidato;
	}

	public void setRespuestasPreguntaCandidato(String[] respuestasPreguntaCandidato) {
		this.respuestasPreguntaCandidato = respuestasPreguntaCandidato;
	}

	public String getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(String correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public String getWrongAnswers() {
		return wrongAnswers;
	}

	public void setWrongAnswers(String wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	public float getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(float puntuacion) {
		this.puntuacion = puntuacion;
	}

	public int getTotalCorrectAnswers() {
		return totalCorrectAnswers;
	}

	public void setTotalCorrectAnswers(int totalCorrectAnswers) {
		this.totalCorrectAnswers = totalCorrectAnswers;
	}

	public String getMetodoPuntuacion() {
		return metodoPuntuacion;
	}

	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}

	public List<MrqsImagenesGrp> getListPresentMrqsImagenesGrp() {
		return listPresentMrqsImagenesGrp;
	}

	public void setListPresentMrqsImagenesGrp(List<MrqsImagenesGrp> listPresentMrqsImagenesGrp) {
		this.listPresentMrqsImagenesGrp = listPresentMrqsImagenesGrp;
	}
	
	
}
