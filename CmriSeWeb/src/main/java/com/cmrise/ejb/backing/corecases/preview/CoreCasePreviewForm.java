package com.cmrise.ejb.backing.corecases.preview;

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
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcOpcionMultipleLocal;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class CoreCasePreviewForm {

	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
	private long numeroCcHdr; 
	private CcHdrV1 ccHdrV1; 	
	private List<CcPreguntasHdrV1> listCcPreguntasHdrV1 = new ArrayList<CcPreguntasHdrV1>();
	private CcPreguntasHdrV1 ccPreguntasHdrV1 = new CcPreguntasHdrV1(); 
	private CcPreguntasFtaV1 ccPreguntasFtaV1 = new CcPreguntasFtaV1(); 
	private List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
    private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean questionView; 
	private boolean answerView; 
	private boolean singleAnswerMode;
	private boolean suffleAnswerOrder; 
    private boolean correctAnswer; 
    private boolean wrongAnswer; 
    private float puntuacion;
    private String metodoPuntuacion; 
    private String respuestaPreguntaSistema;
    private int totalCorrectAnswers; 
    private long numetoFta; 
	private boolean annotatedImage;

	
	

	
	private String correctAnswers="0 Respuesta(s) correctas"; 
	private String wrongAnswers="0 Respuesta(s) incorrectas"; 
	
	

	
	private String respuestaPreguntaCandidato;
	private String[] respuestasPreguntaCandidato;
	
	/********************************************************************
	 * Attributos Imagenes 
	 */
	//private CcPreguntasFtaV1 ccPreguntasFtaV1ForRead = new CcPreguntasFtaV1(); 
	
	private List<CcImagenesGrp> listPresentCcImagenesGrp = new ArrayList<CcImagenesGrp>(); 
	
	@Inject 
	CcHdrLocal ccHdrLocal; 
	
	@Inject 
	CcOpcionMultipleLocal ccOpcionMultipleLocal; 
	
	
	@PostConstruct
	 public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroCcHdr= session.getAttribute("NumeroCcHdrSV");
	     this.numeroCcHdr = Utilitarios.objToLong(obNumeroCcHdr);
	     refreshEntity(); /** Inicializa Objetos **/
	     Object objNumeroCcPreguntaHdrSV= session.getAttribute("NumeroCcPreguntaHdrSV");
		 long  longNumeroCcPreguntaHdrSV = Utilitarios.objToLong(objNumeroCcPreguntaHdrSV); 
		 System.out.println("longNumeroCcPreguntaHdrSV:"+longNumeroCcPreguntaHdrSV);
		 if(0!=longNumeroCcPreguntaHdrSV) {
		 for(CcPreguntasHdrV1 i:listCcPreguntasHdrV1) {
			 if(i.getNumero()==longNumeroCcPreguntaHdrSV) {
				 ccPreguntasHdrV1 = i; 
				 ccPreguntasFtaV1 = i.getCcPreguntasFtaV1();
				 listCcOpcionMultiple = ccPreguntasFtaV1.getListCcOpcionMultiple(); 
				 listPresentCcImagenesGrp = ccPreguntasFtaV1.getListCcImagenesGrp();
				 break;
			 }
		 }
		 }else {
			 for(CcPreguntasHdrV1 i:listCcPreguntasHdrV1) {
				 ccPreguntasHdrV1 = i; 
				 ccPreguntasFtaV1 = i.getCcPreguntasFtaV1();
				 listCcOpcionMultiple = ccPreguntasFtaV1.getListCcOpcionMultiple(); 
				 listPresentCcImagenesGrp = ccPreguntasFtaV1.getListCcImagenesGrp();
				 break;
			 }
		
			 
		 }
          
		 
																	             
		 System.out.println("ccPreguntasHdrV1.getTipoPregunta():"+ccPreguntasHdrV1.getTipoPregunta());
		 System.out.println("ccPreguntasFtaV1.isSingleAnswerMode():"+ccPreguntasFtaV1.isSingleAnswerMode());
		 
		 if(Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(ccPreguntasHdrV1.getTipoPregunta())){
			 this.setLimitedFreeTextAnswer(true);
			 this.setRespuestaPreguntaSistema(ccPreguntasFtaV1.getRespuestaCorrecta());
		 }else if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasHdrV1.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 this.setSingleAnswerMode(ccPreguntasFtaV1.isSingleAnswerMode());
		 }
		
		 this.setQuestionView(true);
	
	}
	
	public void  saveProceed() {
		  System.out.println("Entra saveProceed");	
		  this.setAnswerView(true);
		  if(this.isLimitedFreeTextAnswer()) {
			  if(this.getRespuestaPreguntaCandidato().equalsIgnoreCase(this.getRespuestaPreguntaSistema())) {
				  System.out.println("Respuesta Candidato es:"+this.getRespuestaPreguntaCandidato());
				  this.setCorrectAnswer(true);
				  this.setPuntuacion(1);
			  }else {
				  this.setWrongAnswer(true);
				  this.setPuntuacion(0);
			  }
		  }else if(this.isMultipleChoice()) {
			 /** List<CcOpcionMultipleDto> listMrqsOpcionMultipleDto = mrqsOpcionMultipleLocal.findByNumeroFta(this.getNumetoFta()); **/
			  if(this.isSingleAnswerMode()) {
				  evaluateIsSingleAnswerMode(listCcOpcionMultiple);
			  }else{
				  evaluateNotIsSingleAnswerMode(listCcOpcionMultiple);
			  }
		  }
		  System.out.println("Sale saveProceed");	
		}
	
	 public void initListCcOpcionMultiple(long pNumeroMpf
             ,boolean pShuffleAnswerOrder
                                               ) {
    listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
    List<CcOpcionMultipleDto> listCcOpcionMultipleDto = ccOpcionMultipleLocal.findByNumeroFtaShuffleOrder(pNumeroMpf, pShuffleAnswerOrder);
    for(CcOpcionMultipleDto ccOpcionMultipleDto:listCcOpcionMultipleDto) {
    CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
    ccOpcionMultiple.setNumero(ccOpcionMultipleDto.getNumero());
    ccOpcionMultiple.setTextoRespuesta(ccOpcionMultipleDto.getTextoRespuesta());
    ccOpcionMultiple.setEstatus(ccOpcionMultipleDto.isEstatus());
    ccOpcionMultiple.setTextoExplicacion(ccOpcionMultipleDto.getTextoExplicacion());

    listCcOpcionMultiple.add(ccOpcionMultiple); 
}
}

	 private void evaluateIsSingleAnswerMode(List<CcOpcionMultiple> pListCcOpcionMultiple) {
		  
		   for(CcOpcionMultiple ccOpcionMultiple:listCcOpcionMultiple) {
				  long longRespuestaCandidato = Long.parseLong(this.getRespuestaPreguntaCandidato()); 
				  if(ccOpcionMultiple.getNumero()==longRespuestaCandidato) {
					  ccOpcionMultiple.setEstatusCandidato(true); 
				  }
			  }
		   
				for(CcOpcionMultiple ccOpcionMultiple:pListCcOpcionMultiple) {
				 if(ccOpcionMultiple.isEstatus()) {
					 String strNumero = ccOpcionMultiple.getNumero()+""; 
					 if(strNumero.equals(this.getRespuestaPreguntaCandidato())) {
						 this.setCorrectAnswer(true);
						 break; 
					 }
				  }		 
			    }
			 if(this.isCorrectAnswer()) {
			 this.setCorrectAnswers("1 Respuesta(s) correctas");
			 this.setWrongAnswers("0 Respuesta(s) incorrectas"); 
			 this.setPuntuacion(1);
			 }else {
				this.setWrongAnswer(true);
				this.setCorrectAnswers("0 Respuesta(s) correctas");
			    this.setWrongAnswers("1 Respuesta(s) incorrectas");  
			    this.setPuntuacion(0);
			 }
		}
			
		private void evaluateNotIsSingleAnswerMode(List<CcOpcionMultiple> pListCcOpcionMultiple) {
		   System.out.println("this.getRespuestasPreguntaCandidato():"+this.getRespuestasPreguntaCandidato());
		   int countCorrectAnswers =0; 
		   int countWrongAnswers = 0; 
		  if(null!=this.getRespuestasPreguntaCandidato()) {
			  String [] array = this.getRespuestasPreguntaCandidato(); 
			  
			  for(int idx =0;idx<array.length;idx=idx+1) {
				  for(CcOpcionMultiple ccOpcionMultiple:listCcOpcionMultiple) {
					  long longRespuestaCandidato = Long.parseLong(array[idx]); 
					  if(ccOpcionMultiple.getNumero()==longRespuestaCandidato) {
						  ccOpcionMultiple.setEstatusCandidato(true); 
					  }
				  }
			  }
			  
			   for(int idx =0;idx<array.length;idx=idx+1) {
				   System.out.println(array[idx]);
				   Long longValue = Long.parseLong(array[idx]); 
				   int intCorrectWrong = ccOpcionMultipleLocal.correctOrWrongAnswer(longValue, this.getNumetoFta()); 
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

	
	  private void refreshEntity() {
			ccHdrV1 = ccHdrLocal.findByNumeroObjMod(this.numeroCcHdr);
			listCcPreguntasHdrV1 = ccHdrV1.getListCcPreguntasHdrV1();
	  }
	
	  public String returnUpdateFta() {
			guestPreferences.setTheme(Utilitarios.DEFAULT_THEME);
			return "Actualizar-Pregunta-Fta-CoreCase"; 
		}
		
	  public String returnUpdate() {
			guestPreferences.setTheme(Utilitarios.DEFAULT_THEME);
			return "Preguntas-Update-CoreCase"; 
		}
	 
	
	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}
	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}

	public CcHdrV1 getCcHdrV1() {
		return ccHdrV1;
	}

	public void setCcHdrV1(CcHdrV1 ccHdrV1) {
		this.ccHdrV1 = ccHdrV1;
	}


	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}


	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}


	public List<CcPreguntasHdrV1> getListCcPreguntasHdrV1() {
		return listCcPreguntasHdrV1;
	}


	public void setListCcPreguntasHdrV1(List<CcPreguntasHdrV1> listCcPreguntasHdrV1) {
		this.listCcPreguntasHdrV1 = listCcPreguntasHdrV1;
	}


	public CcPreguntasFtaV1 getCcPreguntasFtaV1() {
		return ccPreguntasFtaV1;
	}


	public void setCcPreguntasFtaV1(CcPreguntasFtaV1 ccPreguntasFtaV1) {
		this.ccPreguntasFtaV1 = ccPreguntasFtaV1;
	}


	public CcPreguntasHdrV1 getCcPreguntasHdrV1() {
		return ccPreguntasHdrV1;
	}


	public void setCcPreguntasHdrV1(CcPreguntasHdrV1 ccPreguntasHdrV1) {
		this.ccPreguntasHdrV1 = ccPreguntasHdrV1;
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
		if(questionView) {
			this.setQuestionView(false);
		}
		this.answerView = answerView;
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


	public String getRespuestaPreguntaCandidato() {
		return respuestaPreguntaCandidato;
	}


	public void setRespuestaPreguntaCandidato(String respuestaPreguntaCandidato) {
		this.respuestaPreguntaCandidato = respuestaPreguntaCandidato;
	}


	public String[] getRespuestasPreguntaCandidato() {
		return respuestasPreguntaCandidato;
	}


	public void setRespuestasPreguntaCandidato(String[] respuestasPreguntaCandidato) {
		this.respuestasPreguntaCandidato = respuestasPreguntaCandidato;
	}


	public List<CcOpcionMultiple> getListCcOpcionMultiple() {
		return listCcOpcionMultiple;
	}


	public void setListCcOpcionMultiple(List<CcOpcionMultiple> listCcOpcionMultiple) {
		this.listCcOpcionMultiple = listCcOpcionMultiple;
	}


	public List<CcImagenesGrp> getListPresentCcImagenesGrp() {
		return listPresentCcImagenesGrp;
	}


	public void setListPresentCcImagenesGrp(List<CcImagenesGrp> listPresentCcImagenesGrp) {
		this.listPresentCcImagenesGrp = listPresentCcImagenesGrp;
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
	public float getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(float puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	public String getMetodoPuntuacion() {
		return metodoPuntuacion;
	}

	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}
	
	public int getTotalCorrectAnswers() {
		return totalCorrectAnswers;
	}

	public void setTotalCorrectAnswers(int totalCorrectAnswers) {
		this.totalCorrectAnswers = totalCorrectAnswers;
	}
	public long getNumetoFta() {
		return numetoFta;
	}

	public void setNumetoFta(long numetoFta) {
		this.numetoFta = numetoFta;
	}
	public String getRespuestaPreguntaSistema() {
		return respuestaPreguntaSistema;
	}

	public void setRespuestaPreguntaSistema(String respuestaPreguntaSistema) {
		this.respuestaPreguntaSistema = respuestaPreguntaSistema;
	}

	public void setAnnotatedImage(boolean annotatedImage) {
		this.annotatedImage = annotatedImage;
	}
	public boolean isAnnotatedImage() {
		return annotatedImage;
	}
	}

