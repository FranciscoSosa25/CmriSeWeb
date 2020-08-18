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
import com.cmrise.ejb.services.corecases.CcHdrLocal;
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
	private int idxCasoClinico = 0; 
	private int clinicoSize = 0;
	

	
	private String respuestaPreguntaCandidato;
	private String[] respuestasPreguntaCandidato;
	
	/********************************************************************
	 * Attributos Imagenes 
	 */
	
	private List<CcImagenesGrp> listPresentCcImagenesGrp = new ArrayList<CcImagenesGrp>(); 
	
	@Inject 
	CcHdrLocal ccHdrLocal; 

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
		 }else if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasHdrV1.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 this.setSingleAnswerMode(ccPreguntasFtaV1.isSingleAnswerMode());
		 }
		 
		 this.setQuestionView(true);
		 
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
	public int getIdxCasoClinico() {
		return idxCasoClinico;
	}
	public void setIdxCasoClinico(int idxCasoClinico) {
		this.idxCasoClinico = idxCasoClinico;
	}
	public int getClinicoSize() {
		return clinicoSize;
	}
	public void setClinicoSize(int clinicoSize) {
		this.clinicoSize = clinicoSize;
	}
	
}
