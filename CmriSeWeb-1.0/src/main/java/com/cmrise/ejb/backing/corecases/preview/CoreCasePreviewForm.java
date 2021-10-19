package com.cmrise.ejb.backing.corecases.preview;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import com.cmrise.ejb.backing.corecases.UpdateQuestionFtaCoreCaseForm;
import com.cmrise.ejb.backing.mrq.preview.Poligonos;
import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenes;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcOpcionMultipleLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaSinonimosLocal;
import com.cmrise.ejb.services.corecases.img.CcImagenesGrpLocal;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaSinonimos;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class CoreCasePreviewForm {

	@ManagedProperty(value = "#{guestPreferences}")
	GuestPreferences guestPreferences;
	
	@Inject
	CcImagenesGrpLocal ccImagenesGrpLocal;

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
	private long numeroFta;
	private boolean annotatedImage;

	private String correctAnswers = "0 Respuesta(s) correctas";
	private String wrongAnswers = "0 Respuesta(s) incorrectas";

	private String respuestaPreguntaCandidato;
	private String[] respuestasPreguntaCandidato;
	private boolean visualizacionPregunta;
	private int element = -1;
	/********************************************************************
	 * Attributos Imagenes
	 */
	// private CcPreguntasFtaV1 ccPreguntasFtaV1ForRead = new CcPreguntasFtaV1();

	private List<CcImagenesGrp> listPresentCcImagenesGrp = new ArrayList<CcImagenesGrp>();
	private List<CcPreguntasFtaSinonimos> ccListaSinonimos = new ArrayList<CcPreguntasFtaSinonimos>();
	
	private CcImagenes selCcImagenes;
	private String respuestasPuntos;
	

	@Inject
	CcPreguntasFtaSinonimosLocal ccPreguntasFtaSinonimosLocal;
	@Inject
	CcHdrLocal ccHdrLocal;

	@Inject
	CcOpcionMultipleLocal ccOpcionMultipleLocal;
	@Inject
	UpdateQuestionFtaCoreCaseForm updateQuestionFtaCoreCaseForm;

	@PostConstruct
	public void init() {
		System.out.println("Entra " + this.getClass() + " init()");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object obNumeroCcHdr = session.getAttribute("NumeroCcHdrSV");
		this.numeroCcHdr = Utilitarios.objToLong(obNumeroCcHdr);
		refreshEntity(); /** Inicializa Objetos **/		
		Object objNumeroCcPreguntaHdrSV = session.getAttribute("NumeroCcPreguntaHdrSV");
		long longNumeroCcPreguntaHdrSV = Utilitarios.objToLong(objNumeroCcPreguntaHdrSV);
		System.out.println("longNumeroCcPreguntaHdrSV:" + longNumeroCcPreguntaHdrSV);
		
		buscarPregunta();
		if (0 != longNumeroCcPreguntaHdrSV) {
			
			
			for (CcPreguntasHdrV1 i : listCcPreguntasHdrV1) {
				if (i.getNumero() == longNumeroCcPreguntaHdrSV) {
					ccPreguntasHdrV1 = i;
					ccPreguntasFtaV1 = i.getCcPreguntasFtaV1();
					listCcOpcionMultiple = ccPreguntasFtaV1.getListCcOpcionMultiple();
					listPresentCcImagenesGrp =  ccImagenesGrpLocal.findByFta(i.getCcPreguntasFtaV1().getNumero(), Utilitarios.INTRODUCCION);
					//  ccPreguntasFtaV1.getListCcImagenesGrp();
					
					break;
				}
			}
		}

		System.out.println("ccPreguntasHdrV1.getTipoPregunta():" + ccPreguntasHdrV1.getTipoPregunta());
		System.out.println("ccPreguntasFtaV1.isSingleAnswerMode():" + ccPreguntasFtaV1.isSingleAnswerMode());
		if (Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(ccPreguntasHdrV1.getTipoPregunta())) {
			this.setLimitedFreeTextAnswer(true);
			
			this.setRespuestaPreguntaSistema(ccPreguntasFtaV1.getRespuestaCorrecta());
		} else if (Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasHdrV1.getTipoPregunta())) {
			this.setMultipleChoice(true);
			this.setSingleAnswerMode(ccPreguntasFtaV1.isSingleAnswerMode());
			contarTotalRespuestas();
		}

		this.setQuestionView(true);

	}
	
	private void buscarPregunta() {
		ccPreguntasHdrV1 = listCcPreguntasHdrV1.get(element == -1 ? 0 : element);
		ccPreguntasFtaV1 = ccPreguntasHdrV1.getCcPreguntasFtaV1();
		listCcOpcionMultiple = ccPreguntasFtaV1.getListCcOpcionMultiple();
		listPresentCcImagenesGrp = ccImagenesGrpLocal.findByCcHDR(ccPreguntasHdrV1.getNumeroCcHdr(), Utilitarios.INTRODUCCION);
	}

	public void saveProceed() {
		System.out.println("Entra saveProceed");
		this.setAnswerView(true);
		if (this.isLimitedFreeTextAnswer()) {
			obtenerSinonimos();
			if (this.getRespuestaPreguntaCandidato().equalsIgnoreCase(this.getRespuestaPreguntaSistema())  || validarSinonimos(getRespuestaPreguntaCandidato())) {
				System.out.println("Respuesta Candidato es:" + this.getRespuestaPreguntaCandidato());
				this.setCorrectAnswer(true);
				this.setPuntuacion(ccPreguntasFtaV1.getValorPuntuacion()!=null?Float.valueOf(ccPreguntasFtaV1.getValorPuntuacion()):1);
			} else {
				this.setWrongAnswer(true);
				this.setPuntuacion(0);
			}
		} else if (this.isMultipleChoice()) {
			/**
			 * List<CcOpcionMultipleDto> listMrqsOpcionMultipleDto =
			 * mrqsOpcionMultipleLocal.findByNumeroFta(this.getNumetoFta());
			 **/
			if (this.isSingleAnswerMode()) {
				evaluateIsSingleAnswerMode(listCcOpcionMultiple);
			} else {
				evaluateNotIsSingleAnswerMode(listCcOpcionMultiple);
			}
		}
		System.out.println("Sale saveProceed");
	}

	public void initListCcOpcionMultiple(long pNumeroMpf, boolean pShuffleAnswerOrder) {
		listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>();
		List<CcOpcionMultipleDto> listCcOpcionMultipleDto = ccOpcionMultipleLocal
				.findByNumeroFtaShuffleOrder(pNumeroMpf, pShuffleAnswerOrder);
		for (CcOpcionMultipleDto ccOpcionMultipleDto : listCcOpcionMultipleDto) {
			CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple();
			ccOpcionMultiple.setNumero(ccOpcionMultipleDto.getNumero());
			ccOpcionMultiple.setTextoRespuesta(ccOpcionMultipleDto.getTextoRespuesta());
			ccOpcionMultiple.setEstatus(ccOpcionMultipleDto.isEstatus());
			ccOpcionMultiple.setTextoExplicacion(ccOpcionMultipleDto.getTextoExplicacion());

			listCcOpcionMultiple.add(ccOpcionMultiple);
		}
	}

	private void evaluateIsSingleAnswerMode(List<CcOpcionMultiple> pListCcOpcionMultiple) {

		for (CcOpcionMultiple ccOpcionMultiple : listCcOpcionMultiple) {
			long longRespuestaCandidato = Long.parseLong(this.getRespuestaPreguntaCandidato());
			if (ccOpcionMultiple.getNumero() == longRespuestaCandidato) {
				ccOpcionMultiple.setEstatusCandidato(true);
			}
		}

		for (CcOpcionMultiple ccOpcionMultiple : pListCcOpcionMultiple) {
			if (ccOpcionMultiple.isEstatus()) {
				String strNumero = ccOpcionMultiple.getNumero() + "";
				if (strNumero.equals(this.getRespuestaPreguntaCandidato())) {
					this.setCorrectAnswer(true);
					break;
				}
			}
		}
		if (this.isCorrectAnswer()) {
			this.setCorrectAnswers("1 Respuesta(s) correctas");
			this.setWrongAnswers("0 Respuesta(s) incorrectas");
			BigDecimal bd = new BigDecimal(
					ccPreguntasFtaV1.getValorPuntuacion() == null ? "1.0" : ccPreguntasFtaV1.getValorPuntuacion())
							.setScale(2, BigDecimal.ROUND_DOWN);
			this.setPuntuacion(bd.floatValue());
		} else {
			this.setWrongAnswer(true);
			this.setCorrectAnswers("0 Respuesta(s) correctas");
			this.setWrongAnswers("1 Respuesta(s) incorrectas");
			this.setPuntuacion(0);
		}
	}

	private void contarTotalRespuestas() {
		long total = 0;
		total = listCcOpcionMultiple.stream().filter(obj -> obj.isEstatus() == true).count();
		setTotalCorrectAnswers((int) total);
	}

	private void evaluateNotIsSingleAnswerMode(List<CcOpcionMultiple> pListCcOpcionMultiple) {
		System.out.println("this.getRespuestasPreguntaCandidato():" + this.getRespuestasPreguntaCandidato());
		int countCorrectAnswers = 0;
		int countWrongAnswers = 0;
		if (null != this.getRespuestasPreguntaCandidato()) {
			String[] array = this.getRespuestasPreguntaCandidato();

			for (int idx = 0; idx < array.length; idx = idx + 1) {
				for (CcOpcionMultiple ccOpcionMultiple : listCcOpcionMultiple) {
					long longRespuestaCandidato = Long.parseLong(array[idx]);
					if (ccOpcionMultiple.getNumero() == longRespuestaCandidato) {
						ccOpcionMultiple.setEstatusCandidato(true);
					}
				}
			}

			for (int idx = 0; idx < array.length; idx = idx + 1) {
				System.out.println(array[idx]);
				Long longValue = Long.parseLong(array[idx]);
				int intCorrectWrong = ccOpcionMultipleLocal.correctOrWrongAnswer(longValue,
						ccPreguntasFtaV1.getNumero());
				if (1 == intCorrectWrong) {
					countCorrectAnswers++;
				} else if (0 == intCorrectWrong) {
					countWrongAnswers++;
				}
			}
		}
		System.out.println("countCorrectAnswers:" + countCorrectAnswers);
		if (countCorrectAnswers > 0) {
			this.setCorrectAnswer(true);
			System.out.println("metodoPuntuacion:" + this.metodoPuntuacion);
			if (Utilitarios.PROP_SCORING.equals(ccPreguntasFtaV1.getMetodoPuntuacion())) {
				System.out.println("totalCorrectAnswers:" + this.totalCorrectAnswers);
				System.out.println("puntuacion:" + this.puntuacion);
				float floatPuntuacionProp = (Float.valueOf(
						ccPreguntasFtaV1.getValorPuntuacion() == null ? "1.0" : ccPreguntasFtaV1.getValorPuntuacion())
						* (float) countCorrectAnswers) / (float) getTotalCorrectAnswers();
				System.out.println("floatPuntuacionProp:" + floatPuntuacionProp);
				BigDecimal bd = new BigDecimal(floatPuntuacionProp).setScale(2, BigDecimal.ROUND_DOWN);
				System.out.println("floatPuntuacionProp:" + floatPuntuacionProp);
				this.setPuntuacion(bd.floatValue());
			}
		} else {
			this.setWrongAnswer(true);
			this.setPuntuacion(0);
		}

		this.setCorrectAnswers(countCorrectAnswers + " Respuesta(s) correctas");
		this.setWrongAnswers(countWrongAnswers + " Respuesta(s) incorrectas");

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
		element--;
		if (element >= 0)
			init();
		return element < 0 ? "Preguntas-Update-CoreCase" : "#";
	}
    private void limpiar() {
    	setMultipleChoice(false);
    	setLimitedFreeTextAnswer(false);
    	setRespuestaPreguntaCandidato("");
    }
	public String verPregunta() {
		limpiar();
		setVisualizacionPregunta(true);
		setAnswerView(false);
		element = element >= listCcPreguntasHdrV1.size() ? 0 : element + 1;
		if (element < listCcPreguntasHdrV1.size()) {
			try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			long question = listCcPreguntasHdrV1.get(element).getNumero();
			session.setAttribute("NumeroCcPreguntaHdrSV", question);
			}catch (RuntimeException e) {
				// TODO: handle exception
			}
			
			init();
		}if (listCcPreguntasHdrV1.size() == 0) {
			limpiarMensajes();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Utilitarios.ERROR_LISTA_PREGUNTAS_VACIA, null));
			return "#";
		}
		return element >= listCcPreguntasHdrV1.size() ? "Preguntas-Update-CoreCase" : "#";

	}

	private void limpiarMensajes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> it = context.getMessages();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}

	public void calificar() {
		saveProceed();
		setAnswerView(true);
	}
	private void obtenerSinonimos() {
		if(!Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(ccPreguntasHdrV1.getTipoPregunta()))
			return ;
		ccListaSinonimos = ccPreguntasFtaSinonimosLocal.findByFta(ccPreguntasFtaV1.getNumero() );
		
		
	}
	private boolean validarSinonimos(String textoUsuario) {
		Iterator<CcPreguntasFtaSinonimos> it=ccListaSinonimos.iterator();
		while(it.hasNext()) {
			if(textoUsuario.equalsIgnoreCase(it.next().getTextoSinonimo()))
					return true;
		}
		return false;
	}
	
	
	private String selDICOMSeries = "{}";
	private List<CcImagenes> selCCImagenesList;
	
	public void selCCImagenesList(List<CcImagenes> ccImagenes) {
		this.selCCImagenesList = ccImagenes;
		JSONObject response = new JSONObject();
		JSONArray series = new JSONArray();
		for(CcImagenes imagenes: ccImagenes) {
			JSONObject dicom = new JSONObject();
			dicom.put("numero",imagenes.getNumero());
			dicom.put("height", imagenes.getHeight());
			dicom.put("width", imagenes.getWidth());
			dicom.put("respuestasPuntos", imagenes.getRespuestasPuntos());
			dicom.put("numeroPoligonos", imagenes.getPoligonos());
			dicom.put("jpgBase64", imagenes.getJpgBase64());
			dicom.put("fileName", imagenes.getFilePath().substring(imagenes.getFilePath().lastIndexOf("\\")));
			dicom.put("puntoCorrectos", imagenes.getPuntoCorrectos());
			dicom.put("score", imagenes.getPuntoCorrectos());
			series.put(dicom);
		}
		response.put("series", series);
		this.selDICOMSeries = response.toString();
	}
	
	
	public void calculateScore() {
		JSONObject jsonObject = new JSONObject(this.respuestasPuntos);
		if(jsonObject != null) {
			Poligonos ob= new Poligonos();
			JSONArray array = jsonObject.getJSONArray("series");
			for(int i=0;i<array.length();i++) {
				JSONObject dicom = array.getJSONObject(i);
				JSONArray points = dicom.optJSONArray("points");
				if(points!=null && points.length() > 0) {
					CcImagenes ccImagenes = this.selCCImagenesList.get(i);
					 double score = ob.calculateScore(ccImagenes.getPoligonos(), ccImagenes.getPoligonos(), ccImagenes.getWidth(), points, ccImagenes.getPoligonoModel());
					 BigDecimal bd = new BigDecimal(score).setScale(2, BigDecimal.ROUND_DOWN);
					 if(score > 0) {
						 ccImagenes.setPuntoCorrectos((float)bd.floatValue());
					 }else {
						 ccImagenes.setPuntoCorrectos(0f);
					 }
					 dicom.put("score", (float)bd.floatValue());
				}else {
					dicom.put("score", 0f);
				}
			}
		}
		this.selDICOMSeries = jsonObject.toString();
	}
	
	public void asignarRespuesta(String query) {
		setRespuestaPreguntaCandidato(query);
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
		if (questionView) {
			this.setAnswerView(false);
		}
		this.questionView = questionView;
	}

	public boolean isAnswerView() {
		return answerView;
	}

	public void setAnswerView(boolean answerView) {
		if (questionView) {
			this.setQuestionView(false);
		}
		this.answerView = answerView;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		if (multipleChoice) {
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
		if (indicateImage) {
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
		if (correctAnswer) {
			this.setWrongAnswer(false);
		}
		this.correctAnswer = correctAnswer;
	}

	public boolean isWrongAnswer() {
		return wrongAnswer;
	}

	public void setWrongAnswer(boolean wrongAnswer) {
		if (wrongAnswer) {
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

	public long getNumeroFta() {
		return numeroFta;
	}

	public void setNumeroFta(long numeroFta) {
		this.numeroFta = numeroFta;
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

	public boolean isVisualizacionPregunta() {
		return visualizacionPregunta;
	}

	public void setVisualizacionPregunta(boolean visualizacionPregunta) {
		this.visualizacionPregunta = visualizacionPregunta;
	}

	public List<CcPreguntasFtaSinonimos> getCcListaSinonimos() {
		return ccListaSinonimos;
	}

	public void setCcListaSinonimos(List<CcPreguntasFtaSinonimos> ccListaSinonimos) {
		this.ccListaSinonimos = ccListaSinonimos;
	}

	
	public CcImagenes getSelCcImagenes() {
		return selCcImagenes;
	}

	public void setSelCcImagenes(CcImagenes selCcImagenes) {
		this.selCcImagenes = selCcImagenes;
	}

	public String getRespuestasPuntos() {
		return respuestasPuntos;
	}

	public void setRespuestasPuntos(String respuestasPuntos) {
		this.respuestasPuntos = respuestasPuntos;
	}

	public String getSelDICOMSeries() {
		return selDICOMSeries;
	}

	public void setSelDICOMSeries(String selDICOMSeries) {
		this.selDICOMSeries = selDICOMSeries;
	}

	public List<CcImagenes> getSelCCImagenesList() {
		return selCCImagenesList;
	}

	public void setSelCCImagenesList(List<CcImagenes> selCCImagenesList) {
		this.selCCImagenesList = selCCImagenesList;
	}
	
	
	
	
}
