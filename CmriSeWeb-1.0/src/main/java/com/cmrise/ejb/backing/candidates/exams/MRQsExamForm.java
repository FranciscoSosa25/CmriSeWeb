package com.cmrise.ejb.backing.candidates.exams;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.backing.mrq.preview.Poligonos;
import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.exams.CandExamStatusEnum;
import com.cmrise.ejb.model.exams.ExamQuestion;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.exams.MrqsGrupoLinesV2;
import com.cmrise.ejb.model.mrqs.AnotacionesCorImg;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.RespReactCorImg;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.candidates.exams.CandExamRespSkipLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsCorrelacionColumnasLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.jpa.dao.mrqs.MrqsCorrelacionColumnaPair;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamSkipV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@ManagedBean
@ViewScoped
public class MRQsExamForm {

	private CandExamenesV1 candExamenesV1 = new CandExamenesV1();
	private CandExamRespuestasV1 candExamRespuestasV1 = new CandExamRespuestasV1();
	private MrqsExamenes mrqsExamen = new MrqsExamenes();
	private MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr();
	private MrqsGrupoLines mrqsGrupoLines = new MrqsGrupoLines();
	private TreeNode rootMrqsGrupo;
	private TreeNode selectedNode;
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>();
	private List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas = new ArrayList<MrqsPreguntasHdrV1>();
	private MrqsGrupoLinesV2 mrqsGrupoLinesV2 = new MrqsGrupoLinesV2();
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>();
	private List<MrqsImagenesGrp> listPresentMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>();
	private CandExamRespSkipDto candExamRespSkipDto = new CandExamRespSkipDto();
	List<MrqsGrupoLinesV2> listMrqsGrupoLinesV2 = new ArrayList<MrqsGrupoLinesV2>();
	List<CandExamSkipV1Dto> lCandExamSkipV1Dto = new ArrayList<CandExamSkipV1Dto>();

	private long numeroMRQsExamen;
	private long numeroCandExamen;
	private long numeroPreguntaFta;
	private boolean multipleChoice;
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private Short sSegundos = 60;
	private String respuestaCandidato;
	private String[] respuestasPreguntaCandidato;
	private String timerValue;
	private String strDate;
	private boolean flag2;
	private long reactivosSize;
	private int idxReactivos;
	private long hdrGrupoSize;
	private int idxGrupo;
	private boolean nuevaBusqueda = false;
	private int idxSkip = 0;
	private boolean busquedaSkip = false;
	private int skipMax = 0;
	private boolean showFinalMessage = false;
	private int limiteCaracteres = 50;
	private String tipoPregunta;
	private String errorMsg = "";
	

	
	private MrqsPreguntasFtaV1 mrqsPreguntasFtaV1ForRead = new MrqsPreguntasFtaV1();
	private List<RespReactCorImg> listRespReactCorImg = new ArrayList<RespReactCorImg>();
	private List<RespReactCorImg> listRespCandCorImg = new ArrayList<RespReactCorImg>();
	private List<AnotacionesCorImg> listAnotacionesCorImg = new ArrayList<AnotacionesCorImg>();
	private List<SelectItem> selectRespReactCorImg = new ArrayList<SelectItem>();
	private List<SelectItem> candRespReactCorImg = new ArrayList<SelectItem>();
	private SelectItem respuestaSelect = new SelectItem();
	
	
	private ExamQuestion  presentExamQuestion;
	Map<MrqsGrupoHdr, List<ExamQuestion>> questionMap = new LinkedHashMap<>();
	
	

	@Inject
	UtilitariosLocal utilitariosLocal;

	@Inject
	MrqsExamenesLocal mrqsExamenesLocal;

	@Inject
	MrqsGrupoHdrLocal mrqsGrupoHdrLocal;

	@Inject
	MrqsGrupoLinesLocal mrqsGrupoLinesLocal;

	@Inject
	MrqsOpcionMultipleLocal mrqsOpcionMultipleLocal;

	@Inject
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal;

	@Inject
	CandExamenesLocal candExamenesLocal;

	@Inject
	CandExamRespuestasLocal candExamRespuestasLocal;

	@Inject
	MrqsImagenesGrpLocal mrqsImagenesGrpLocal;

	@Inject
	CandExamRespSkipLocal candExamRespSkipLocal;

	@Inject
	MrqsCorrelacionColumnasLocal mrqsCorrelacionColumnasLocal;
	private List<MrqsCorrelacionColumnasDto> listMrqsCorrelacionColumnasDto = new ArrayList<MrqsCorrelacionColumnasDto>();
	private List<MrqsCorrelacionColumnasRespuestasDto> listMrqsCorrelacionColumnasRespuestasDto = new ArrayList<MrqsCorrelacionColumnasRespuestasDto>();
	private List<MrqsCorrelacionColumnaPair> listMrqsCorrelacionColumnas = new ArrayList<MrqsCorrelacionColumnaPair>();
	private boolean correlacionColumnas;
	private boolean panelCorrelacionColumnasResultados;
	Queue<ExamQuestion> questionQueue = new LinkedList<>();
	Queue<ExamQuestion> answerQueue = new LinkedList<>();
	private long examTimeout = 0;


	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		
		try {
		
		Object objNumeroCandExamen = session.getAttribute("NumeroCandExamenSV");
		numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
		
		Object objNumeroMRQsExamen = session.getAttribute("NumeroMrqsExamenSV");
		numeroMRQsExamen = utilitariosLocal.objToLong(objNumeroMRQsExamen);
		
		long numCand = utilitariosLocal.objToLong(session.getAttribute("numCand"));
		
		
		if(numeroCandExamen == 0 || numeroMRQsExamen == 0 || numCand == 0) {
			throw new IllegalArgumentException("No se encontró ningún examen, proporcione detalles válidos del examen del candidato.");
		}
		
		
		System.out.println("Numero buscado de examen: " + numCand);
		System.out.println("*** Candidato de examen: " + numeroCandExamen);
		
		this.candExamenesV1 = candExamenesLocal.findByNumero(numeroCandExamen);
		this.mrqsExamen = mrqsExamenesLocal.findByNumeroWD(numeroMRQsExamen, numCand);
		
		CandExamStatusEnum examStatusEnum  = CandExamStatusEnum.getCandExamStatusEnum(this.candExamenesV1.getEstatus());
		
		switch (examStatusEnum) {
		case PAUSAR:
			throw new IllegalArgumentException("Su examen está en pausa, comuníquese con el administrador para reanudar.");			
		case SUSPENDER:
			throw new IllegalArgumentException("Su examen está suspendido, comuníquese con el administrador.");
		default:
			break;		
		}
		
		Date startTime = candExamenesLocal.getStartTime(numeroCandExamen);
		
		Date todayDate = new Date();
		long timeDiffrence = 0;
		long examTimeout = this.mrqsExamen.getTiempoLimite() * sSegundos;
		
		if(startTime == null) {
			candExamenesLocal.updateStartTime(numeroCandExamen, todayDate);
		}else {
			timeDiffrence = (todayDate.getTime()-startTime.getTime())/1000;
			examTimeout = examTimeout - timeDiffrence + (this.candExamenesV1.getCandExamTime()*60);
		}
		
		System.out.println("Examen: " + mrqsExamen.getDescripcion());
		
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		strDate = formatter.format(date);
		if(examTimeout <=0) {
			throw new IllegalArgumentException("Examen completado. Comuníquese con el creador del examen.");
		}
		this.examTimeout = examTimeout;
		
		

		listMrqsGrupoHdr = mrqsGrupoHdrLocal.findByNumeroExamen(this.getMrqsExamen().getNumero());
		
		// Shuffle Group
		if(this.mrqsExamen.isAleatorioGrupo()) {
			Collections.shuffle(listMrqsGrupoHdr);
		}
		hdrGrupoSize = listMrqsGrupoHdr.size();
		
		questionQueue = new LinkedList<>();
		for(MrqsGrupoHdr idxHdr:listMrqsGrupoHdr) {
			List<MrqsGrupoLinesV2> listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());

			// Shuffle Question 
			if(this.mrqsExamen.isAleatorioPreguntas()) { 
				Collections.shuffle(listMrqsGrupoLinesV2);
			}
			
			int count = 0;
			for(MrqsGrupoLinesV2 mrqsGrupoLinesV2 : listMrqsGrupoLinesV2) {
				ExamQuestion examQuestion = new ExamQuestion();
				++count;
				String questionNumber = mrqsGrupoLinesV2.getTitulo()+" "+ count + " de " + listMrqsGrupoLinesV2.size();
				
				examQuestion.setQuestionNumber(questionNumber);
				examQuestion.setMrqsGrupoHdr(idxHdr);
				examQuestion.setNumeroCandExamen(numeroCandExamen);
				examQuestion.setNumeroMRQsExamen(numeroMRQsExamen);
				examQuestion.setMrqsGrupoLinesV2(mrqsGrupoLinesV2);
				questionQueue.add(examQuestion);
			}
		}
		
		if(questionQueue.size() > 0) {
			setQuestion(questionQueue.peek());
		}else {
			context.addMessage(null, new FacesMessage("No se encontró ninguna pregunta para el examen", "Actualizacion correcta"));
		}
		
		}catch (RuntimeException e) {
			onException(e);	
		}
		/**
		
		System.out.println("numeroMgl:" + numeroMgl);
		if (0 == numeroMgl) {
			// for(MrqsGrupoHdr idxHdr:listMrqsGrupoHdr) {
			MrqsGrupoHdr idxHdr = listMrqsGrupoHdr.get(idxGrupo);
			mrqsGrupoHdr.setNumero(idxHdr.getNumero());
			candExamRespSkipDto.setNumeroGrupo(idxHdr.getNumero());
			mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
			mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
			// mrqsGrupoHdr.setTitulo(idxHdr.getTitulo());
			
			listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
			reactivosSize = listMrqsGrupoLinesV2.size();
			System.out.println("********Cantidad de Preguntas: " + reactivosSize);
			listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);

			// for(MrqsGrupoLinesV2 idx:listMrqsGrupoLinesV2) {
			MrqsGrupoLinesV2 idx = null;
			if (listMrqsGrupoLinesV2 != null && !listMrqsGrupoLinesV2.isEmpty()) { // Algunas veces llegaba vacía la lista y truena
																		// la página sin está validación
				idx = listMrqsGrupoLinesV2.get(idxReactivos);
				System.out.println("idx.getTextoPregunta():" + idx.getNumeroPregunta());
				mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
				mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
				mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
				mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
				mrqsGrupoLinesV2.setStartTime(new Date());
				this.setTipoPregunta(idx.getTipoPregunta());
				System.err.println("TIPO PREGUNTA: " + this.getTipoPregunta());

				candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
				
				this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());

				MrqsPreguntasFtaV1 pregunta = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,idx.getTipoPregunta());
				
				
				if (pregunta.getLimiteCaracteres() != null)
					limiteCaracteres = pregunta.getLimiteCaracteres();
				else
					limiteCaracteres = 50;
				candExamRespSkipDto.setNumeroPreguntaFta(this.numeroPreguntaFta);
				if (Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
				} else if (Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
					
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
						}
						else if(Utilitarios.IMAGEN_ANOTADA.equals(idx.getTipoPregunta())) {
					this.setAnnotatedImage(true);
						}
						else if(Utilitarios.CORRELACION_COLUMNA.equals(idx.getTipoPregunta())) {
							this.setCorrelacionColumnas(true);
					    	 actualizarTablaCorrelacionColumnas(getNumeroPreguntaFta(),idx);
					     }
					
						
				}//
					
				if(Utilitarios.IMAGEN_ANOTADA.equals(idx.getTipoPregunta())) {
					mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,
							idx.getTipoPregunta());
					Gson gson = new Gson();
					System.out.println("mrqsPreguntasFtaV1ForRead.widht" + mrqsPreguntasFtaV1ForRead.getWidth());
				    	System.out.println("mrqsPreguntasFtaV1ForRead el content type"+ mrqsPreguntasFtaV1ForRead.getContentType());
					if (null != mrqsPreguntasFtaV1ForRead.getRespuestas()) {
			            	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
							setListRespReactCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getRespuestas(), collectionType));
							refreshRespuestas();
	
					}
					if (null != mrqsPreguntasFtaV1ForRead.getAnotaciones()) {
			            	 Type collectionType = new TypeToken<List<AnotacionesCorImg>>(){}.getType();
			            	 setListAnotacionesCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getCorrelaciones(), collectionType)); 
					}
				}
				
				
				this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
						                                                     , this.mrqsGrupoHdr.getNumero()
						                                                     , this.mrqsGrupoLinesV2.getNumeroPregunta()
						                                                     , this.numeroPreguntaFta
						                                                     );
				
				if(!mrqsGrupoLinesV2.isSingleAnswerMode()) {
				  if(null!=this.candExamRespuestasV1.getRespuesta()) {
					  if(this.candExamRespuestasV1.getRespuesta().contains(",")) {
						  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
					  }else {
						  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
					  }
				 }
				}
				
				
				this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
				
				
			
			}else {
				MrqsGrupoLinesV2 tmp = mrqsGrupoLinesLocal.findByNumeroV2(numeroMgl);
				
				mrqsGrupoHdr.setNumero(tmp.getNumeroHdr());
				mrqsGrupoHdr.setAdmonMateriaDesc(tmp.getTitulo()); 
				//mrqsGrupoHdr.setAdmonMateriaDesc(tmp.getTemaPreguntaDesc());
				mrqsGrupoHdr.setAdmonSubMateriaDesc(tmp.getTemaPreguntaDesc());
			
				mrqsGrupoLinesV2.setNumero(tmp.getNumero());
			//	mrqsGrupoLinesV2.setTitulo(tmp.getTitulo());
				mrqsGrupoLinesV2.setTextoPregunta(tmp.getTextoPregunta());
				mrqsGrupoLinesV2.setTextoSugerencias(tmp.getTextoSugerencias());
				mrqsGrupoLinesV2.setTipoPregunta(tmp.getTipoPregunta());
				mrqsGrupoLinesV2.setNumeroPregunta(tmp.getNumeroPregunta());
				mrqsGrupoLinesV2.setStartTime(new Date());
				this.setTipoPregunta(tmp.getTipoPregunta());
				System.err.println("TIPO PREGUNTA: "+ this.getTipoPregunta());
				this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
			
				MrqsPreguntasFtaV1 pregunta = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta, tmp.getTipoPregunta());
				if(pregunta.getLimiteCaracteres() != null)
					limiteCaracteres = pregunta.getLimiteCaracteres();
				else
					limiteCaracteres = 50;
				
				
				if(Utilitarios.RESP_TEXTO_LIBRE.equals(tmp.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
				}else if(Utilitarios.OPCION_MULTIPLE.equals(tmp.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(tmp.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(tmp.isSuffleAnswerOrder());
					listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
				}

				if(Utilitarios.IMAGEN_ANOTADA.equals(tmp.getTipoPregunta())) {
					mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,
							tmp.getTipoPregunta());
			    	 Gson gson = new Gson();
			    	System.out.println("mrqsPreguntasFtaV1ForRead.widht"+mrqsPreguntasFtaV1ForRead.getWidth());
			    	System.out.println("mrqsPreguntasFtaV1ForRead el content type"+ mrqsPreguntasFtaV1ForRead.getContentType());
		             if(null!=mrqsPreguntasFtaV1ForRead.getRespuestas()) {
		            	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
		            	setListRespReactCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getRespuestas(), collectionType)); 
		            	refreshRespuestas();
		             }
		             if(null!=mrqsPreguntasFtaV1ForRead.getAnotaciones()) {
		            	 Type collectionType = new TypeToken<List<AnotacionesCorImg>>(){}.getType();
		            	 setListAnotacionesCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getCorrelaciones(), collectionType)); 
		             }
			     }

				
				this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
                        , this.mrqsGrupoHdr.getNumero()
                        , this.mrqsGrupoLinesV2.getNumeroPregunta()
                        , this.numeroPreguntaFta
                        ); 
			if (!mrqsGrupoLinesV2.isSingleAnswerMode()) {
				if (null != this.candExamRespuestasV1.getRespuesta()) {
					if (this.candExamRespuestasV1.getRespuesta().contains(",")) {
						this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
					  }
					  else {
						this.respuestasPreguntaCandidato = new String[] { this.candExamRespuestasV1.getRespuesta() };
					}
				}
			}
			this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
		
		}

		listPresentMrqsImagenesGrp = mrqsImagenesGrpLocal.findByFta(this.numeroPreguntaFta, Utilitarios.INTRODUCCION);
		
		*/
	}
	
	public void onException(Throwable e) {
		if(e instanceof IllegalArgumentException) {
			this.setErrorMsg(e.getMessage());
		}else {
			this.setErrorMsg("No se pudo procesar su solicitud.");
		}
	}
	
	
	
	
	private void setNextQuestion() {
		
		ExamQuestion examQuestion = questionQueue.peek();
		if(examQuestion == null) {
			showFinalMessage = true;
			CandExamenesDto candExamenesDto = new CandExamenesDto();
			candExamenesDto.setExamEndTime(new java.sql.Timestamp(new Date().getTime()));
			candExamenesDto.setEstatus(CandExamStatusEnum.REVISADO.getStatus());
			candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
		}else {
			setQuestion(examQuestion);
		}
	}
	
	
	public void setQuestion(ExamQuestion examQuestion) {
		this.presentExamQuestion = examQuestion;
		
		MrqsGrupoLinesV2 mgV2 =  examQuestion.getMrqsGrupoLinesV2(); 
		long numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mgV2.getNumeroPregunta());
		this.presentExamQuestion.setNumeroPreguntaFta(numeroPreguntaFta);
		
		// Retrive Question;
		MrqsPreguntasFtaV1 pregunta = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,mgV2.getTipoPregunta());
		
		if (pregunta.getLimiteCaracteres() != null)
			limiteCaracteres = pregunta.getLimiteCaracteres();
		else
			limiteCaracteres = 50;
		
		
		
		// Set User Question view text
		mrqsGrupoLinesV2.setTitulo(mgV2.getTitulo());
		mrqsGrupoLinesV2.setTextoPregunta(mgV2.getTextoPregunta());
		mrqsGrupoLinesV2.setTextoSugerencias(mgV2.getTextoSugerencias());
		mrqsGrupoLinesV2.setNumeroPregunta(mgV2.getNumeroPregunta());
		mrqsGrupoLinesV2.setStartTime(new Date());
		mrqsGrupoLinesV2.setQuestionNumber(examQuestion.getQuestionNumber());
		mrqsGrupoLinesV2.setQuestionType(mgV2.getTipoPregunta());
		
		// Retrive saved answer
		this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(examQuestion.getNumeroCandExamen()
                , examQuestion.getMrqsGrupoHdr().getNumero()
                , mgV2.getNumeroPregunta()
                , numeroPreguntaFta
                );
		
		if(null!=this.candExamRespuestasV1.getRespuesta()) {
			this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
		}else {
			this.respuestaCandidato = "";
		}
		 
		// Set Answer type
		switch (mgV2.getTipoPregunta()) {
			case Utilitarios.OPCION_MULTIPLE:
				mrqsGrupoLinesV2.setSingleAnswerMode(mgV2.isSingleAnswerMode());
				mrqsGrupoLinesV2.setSuffleAnswerOrder(mgV2.isSuffleAnswerOrder());
				listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(numeroPreguntaFta,mgV2.isSuffleAnswerOrder());
				if(!mgV2.isSingleAnswerMode()) {
					  if(StringUtils.isNotEmpty(this.respuestaCandidato)) {
							  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
					 }
				}
				break;
			case Utilitarios.IMAGEN_INDICADA:
				mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,mgV2.getTipoPregunta());
				break;	
			case Utilitarios.CORRELACION_COLUMNA:
				actualizarTablaCorrelacionColumnas(numeroPreguntaFta,mgV2);
				break;
	
			default:
				break;
		}		
		
		if(Utilitarios.IMAGEN_ANOTADA.equals(mgV2.getTipoPregunta())) {
			mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,mgV2.getTipoPregunta());
			Gson gson = new Gson();
			System.out.println("mrqsPreguntasFtaV1ForRead.widht" + mrqsPreguntasFtaV1ForRead.getWidth());
		    	System.out.println("mrqsPreguntasFtaV1ForRead el content type"+ mrqsPreguntasFtaV1ForRead.getContentType());
			if (null != mrqsPreguntasFtaV1ForRead.getRespuestas()) {
	            	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
					setListRespReactCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getRespuestas(), collectionType));
					refreshRespuestas();

			}
			if (null != mrqsPreguntasFtaV1ForRead.getCorrelaciones()) {
	            	 Type collectionType = new TypeToken<List<AnotacionesCorImg>>(){}.getType();
	            	 setListAnotacionesCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getCorrelaciones(), collectionType)); 
			}
		}
		
		listPresentMrqsImagenesGrp = mrqsImagenesGrpLocal.findByFta(numeroPreguntaFta, Utilitarios.INTRODUCCION);
		
		
		
		
	}
	
	/**
	 * 
	 * @param event
	 *
	public void onNodeSelect(NodeSelectEvent event) {
		String strEvent = event.getTreeNode().toString();
		System.out.println("strEvent:" + strEvent);
		if (strEvent.contains("LINE")) {
			long lNumeroHeader = Long.parseLong(strEvent.substring(4));
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("NumeroMrqsExamenSV", this.getMrqsExamen().getNumero());
			session.setAttribute("NumeroMglSV", lNumeroHeader);
			context.getApplication().getNavigationHandler().handleNavigation(context, null, "Candidates-MRQs-Exam");
			return;
		}
	}
	
	/**
	 * 
	 */
	public void saveAndProceed() {
		System.out.println("Entra saveAndProceed()");
		System.out.println("this.candExamenesV1.getNumero():" + this.candExamenesV1.getNumero());
		System.out.println("this.mrqsGrupoHdr.getNumero():" + this.presentExamQuestion.getMrqsGrupoHdr().getNumero());
		System.out.println(" this.mrqsGrupoLinesV2.getNumeroPregunta():" + this.mrqsGrupoLinesV2.getNumeroPregunta());
		System.out.println("this.numeroPreguntaFta:" + this.numeroPreguntaFta);
		this.presentExamQuestion.setSkip(false);
		String questionType = this.mrqsGrupoLinesV2.getQuestionType();
		
		double puntuacionScore = 0d;
		
		switch (questionType) {
			case Utilitarios.OPCION_MULTIPLE:
				if (!this.getMrqsGrupoLinesV2().isSingleAnswerMode()) {
					System.out.println(this.getRespuestasPreguntaCandidato());
					if (null != this.getRespuestasPreguntaCandidato()) {
						String strRespuestas = "";
						for (int i = 0; i < this.getRespuestasPreguntaCandidato().length; i++) {
							if ((i + 1) == this.getRespuestasPreguntaCandidato().length) {
								strRespuestas = strRespuestas + this.getRespuestasPreguntaCandidato()[i];
							} else {
								strRespuestas = strRespuestas + this.getRespuestasPreguntaCandidato()[i] + ",";
							}
						}
						this.setRespuestaCandidato(strRespuestas);
					}
				}
				break;
			case Utilitarios.IMAGEN_ANOTADA:
				Gson gson = new Gson();
				String strRespuestas = "";
				strRespuestas = gson.toJson(listRespCandCorImg);
				System.out.println("strRespuestas: " + strRespuestas);
				this.setRespuestaCandidato(strRespuestas);
				break;
			case Utilitarios.IMAGEN_INDICADA:				
				
				 int puntuacion= Integer.valueOf(mrqsPreguntasFtaV1ForRead.getValorPuntuacion());
				 int poligonos=Integer.valueOf(mrqsPreguntasFtaV1ForRead.getPoligonos());
				 String coordenadasPoligonos=mrqsPreguntasFtaV1ForRead.getRespuestas();
				 String coordenadasUsuario= this.getRespuestaCandidato();
				 int ancho=mrqsPreguntasFtaV1ForRead.getWidth();
				 Poligonos ob= new Poligonos();
				 double puntuacionR=ob.obtenerPuntuacion(puntuacion,poligonos,ancho,coordenadasUsuario,coordenadasPoligonos);
				 BigDecimal bd = new BigDecimal(puntuacionR).setScale(2, BigDecimal.ROUND_DOWN);
				 puntuacionScore = bd.doubleValue();
				break;	
			case Utilitarios.CORRELACION_COLUMNA:
				
			/*
			 * Iterator<MrqsCorrelacionColumnasRespuestasDto> lista=
			 * listMrqsCorrelacionColumnasRespuestasDto.iterator(); float
			 * valorItem=getPuntuacion()/listMrqsCorrelacionColumnasRespuestasDto.size();
			 * int respuestasCorrectas=0; float puntuacion=0.0f; while(lista.hasNext()) {
			 * 
			 * MrqsCorrelacionColumnasRespuestasDto var=lista.next();
			 * if(var.getTexto().equals(var.getValorSeleccionado())) {
			 * respuestasCorrectas++; puntuacion+=valorItem; } }
			 * 
			 * setCorrectAnswers(correctAnswers.replace("0",
			 * String.valueOf(respuestasCorrectas)));
			 * setWrongAnswers(wrongAnswers.replace("0",
			 * String.valueOf(listMrqsCorrelacionColumnasRespuestasDto.size()-
			 * respuestasCorrectas))); BigDecimal bd = new
			 * BigDecimal(puntuacion).setScale(2, BigDecimal.ROUND_DOWN);
			 */
		}
		if (this.isCorrelacionColumnas()) {
			// falta añadir logica.
		}
		long duration =  0;
		if(mrqsGrupoLinesV2.getStartTime()!=null) {
			duration = new Date().getTime() - mrqsGrupoLinesV2.getStartTime().getTime();
			
			switch (questionType) {
			case Utilitarios.OPCION_MULTIPLE:
			case Utilitarios.RESP_TEXTO_LIBRE:
				
				// Calculate Score and Result(Correct/InCorrect) using DB store procedure
				
				
				candExamRespuestasLocal.updateRespuesta(this.candExamenesV1.getNumero(), this.presentExamQuestion.getMrqsGrupoHdr().getNumero(),
						this.mrqsGrupoLinesV2.getNumeroPregunta(), this.presentExamQuestion.getNumeroPreguntaFta(), duration, this.respuestaCandidato);
				candExamRespuestasLocal.calificaRespuesta(this.candExamenesV1.getNumero(), this.presentExamQuestion.getMrqsGrupoHdr().getNumero(),
						this.mrqsGrupoLinesV2.getNumeroPregunta(), this.presentExamQuestion.getNumeroCandExamen());
				
				break;
			case Utilitarios.IMAGEN_INDICADA:
				// Calculate Score and Result(Correct/InCorrect) at Server
					this.presentExamQuestion.setEstatus(puntuacionScore > 0 ? Utilitarios.CORRECTA : Utilitarios.INCORRECTA);
					this.presentExamQuestion.setPuntuacion(puntuacionScore);
					candExamRespuestasLocal.updateRespuesta(this.candExamenesV1.getNumero(), this.presentExamQuestion.getMrqsGrupoHdr().getNumero(),
					this.mrqsGrupoLinesV2.getNumeroPregunta(), this.presentExamQuestion.getNumeroPreguntaFta(), duration,
					this.getRespuestaCandidato(),
					this.presentExamQuestion.getEstatus(),
					this.presentExamQuestion.getPuntuacion()
					);
				break;
			}
		}
		System.out.println("Sale saveAndProceed()");
	}
		
	private void actualizarTablaCorrelacionColumnas(long lNumeroFta,MrqsGrupoLinesV2 idx) {
		listMrqsCorrelacionColumnasDto=mrqsCorrelacionColumnasLocal.findByFta(lNumeroFta);
		
		listMrqsCorrelacionColumnasRespuestasDto=mrqsCorrelacionColumnasLocal.findRespuestasCorrectasByFta(lNumeroFta);
		
		
		int length=listMrqsCorrelacionColumnasDto.size()>listMrqsCorrelacionColumnasRespuestasDto.size()?listMrqsCorrelacionColumnasDto.size():listMrqsCorrelacionColumnasRespuestasDto.size();
		listMrqsCorrelacionColumnas = new ArrayList<MrqsCorrelacionColumnaPair>();
		for(int i=0;i<length;i++) {
	
			listMrqsCorrelacionColumnas.add(new 
					MrqsCorrelacionColumnaPair(i>=listMrqsCorrelacionColumnasDto.size()?
					null:listMrqsCorrelacionColumnasDto.get(i), 
					i>=listMrqsCorrelacionColumnasRespuestasDto.size()?
							null:listMrqsCorrelacionColumnasRespuestasDto.get(i) ));
		}
		
	}
	
	public MrqsExamenes getMrqsExamen() {
		return mrqsExamen;
	}

	public void setMrqsExamen(MrqsExamenes mrqsExamen) {
		this.mrqsExamen = mrqsExamen;
	}

	public MrqsGrupoHdr getMrqsGrupoHdr() {
		return mrqsGrupoHdr;
	}

	public void setMrqsGrupoHdr(MrqsGrupoHdr mrqsGrupoHdr) {
		this.mrqsGrupoHdr = mrqsGrupoHdr;
	}

	public MrqsGrupoLines getMrqsGrupoLines() {
		return mrqsGrupoLines;
	}

	public void setMrqsGrupoLines(MrqsGrupoLines mrqsGrupoLines) {
		this.mrqsGrupoLines = mrqsGrupoLines;
	}

	public TreeNode getRootMrqsGrupo() {
		return rootMrqsGrupo;
	}

	public void setRootMrqsGrupo(TreeNode rootMrqsGrupo) {
		this.rootMrqsGrupo = rootMrqsGrupo;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<MrqsGrupoHdr> getListMrqsGrupoHdr() {
		return listMrqsGrupoHdr;
	}

	public void setListMrqsGrupoHdr(List<MrqsGrupoHdr> listMrqsGrupoHdr) {
		this.listMrqsGrupoHdr = listMrqsGrupoHdr;
	}

	public List<MrqsPreguntasHdrV1> getListMrqsGrupoPreguntas() {
		return listMrqsGrupoPreguntas;
	}

	public void setListMrqsGrupoPreguntas(List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas) {
		this.listMrqsGrupoPreguntas = listMrqsGrupoPreguntas;
	}



	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		if(multipleChoice) {
			this.setLimitedFreeTextAnswer(false);
			this.setAnnotatedImage(false);
			this.setIndicateImage(false);
			this.setCorrelacionColumnas(false);
		}
		this.multipleChoice = multipleChoice;
	}

	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}

	public void setLimitedFreeTextAnswer(boolean limitedFreeTextAnswer) {
		if(limitedFreeTextAnswer) {
			this.setMultipleChoice(false);
			this.setAnnotatedImage(false);
			this.setIndicateImage(false);
			this.setCorrelacionColumnas(false);
		}
		this.limitedFreeTextAnswer = limitedFreeTextAnswer;
	}

	public boolean isIndicateImage() {
		return indicateImage;
	}

	public void setIndicateImage(boolean indicateImage) {
		if(indicateImage) {
			this.setMultipleChoice(false);
			this.setAnnotatedImage(false);
			this.setLimitedFreeTextAnswer(false);
			this.setCorrelacionColumnas(false);
		}
		this.indicateImage = indicateImage;
	}

	public boolean isAnnotatedImage() {
		return annotatedImage;
	}

	public void setAnnotatedImage(boolean annotatedImage) {
		if(annotatedImage) {
			this.setCorrelacionColumnas(false);
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.annotatedImage = annotatedImage;
	}

	public List<MrqsOpcionMultiple> getListMrqsOpcionMultiple() {
		return listMrqsOpcionMultiple;
	}

	public void setListMrqsOpcionMultiple(List<MrqsOpcionMultiple> listMrqsOpcionMultiple) {
		this.listMrqsOpcionMultiple = listMrqsOpcionMultiple;
	}

	public MrqsGrupoLinesV2 getMrqsGrupoLinesV2() {
		return mrqsGrupoLinesV2;
	}

	public void setMrqsGrupoLinesV2(MrqsGrupoLinesV2 mrqsGrupoLinesV2) {
		this.mrqsGrupoLinesV2 = mrqsGrupoLinesV2;
	}

	public CandExamenesV1 getCandExamenesV1() {
		return candExamenesV1;
	}

	public void setCandExamenesV1(CandExamenesV1 candExamenesV1) {
		this.candExamenesV1 = candExamenesV1;
	}

	public long getNumeroPreguntaFta() {
		return numeroPreguntaFta;
	}

	public void setNumeroPreguntaFta(long numeroPreguntaFta) {
		this.numeroPreguntaFta = numeroPreguntaFta;
	}

	public String getRespuestaCandidato() {
		return respuestaCandidato;
	}

	public void setRespuestaCandidato(String respuestaCandidato) {
		this.respuestaCandidato = respuestaCandidato;
	}

	public String[] getRespuestasPreguntaCandidato() {
		return respuestasPreguntaCandidato;
	}

	public void setRespuestasPreguntaCandidato(String[] respuestasPreguntaCandidato) {
		this.respuestasPreguntaCandidato = respuestasPreguntaCandidato;
	}

	public CandExamRespuestasV1 getCandExamRespuestasV1() {
		return candExamRespuestasV1;
	}

	public void setCandExamRespuestasV1(CandExamRespuestasV1 candExamRespuestasV1) {
		this.candExamRespuestasV1 = candExamRespuestasV1;
	}

	public List<MrqsImagenesGrp> getListPresentMrqsImagenesGrp() {
		return listPresentMrqsImagenesGrp;
	}

	public void setListPresentMrqsImagenesGrp(List<MrqsImagenesGrp> listPresentMrqsImagenesGrp) {
		this.listPresentMrqsImagenesGrp = listPresentMrqsImagenesGrp;
	}

	public boolean isShowFinalMessage() {
		return showFinalMessage;
	}

	public void setShowFinalMessage(boolean showFinalMessage) {
		this.showFinalMessage = showFinalMessage;
	}

		public long getReactivosSize() {
		return reactivosSize;
	}

	public void setReactivosSize(long reactivosSize) {
		this.reactivosSize = reactivosSize;
	}

		public int getIdxReactivos() {
		return idxReactivos;
	}

	public void setIdxReactivos(int idxReactivos) {
		this.idxReactivos = idxReactivos;
	}

		/**
	 * @return the limiteCaracteres
	 */
	public int getLimiteCaracteres() {
		return limiteCaracteres;
	}

	/**
	 * @param limiteCaracteres the limiteCaracteres to set
	 */
	public void setLimiteCaracteres(int limiteCaracteres) {
		this.limiteCaracteres = limiteCaracteres;
	}
	
	
		public void onTimerStep() {
			
			
			
			
		}
	
	
	
		public void onTimeout() {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FIN DE TIEMPO", "Fin de tiempo"));
				CandExamenesDto candExamenesDto = new CandExamenesDto();
				candExamenesDto.setExamEndTime(new java.sql.Timestamp(new Date().getTime()));
				candExamenesDto.setEstatus(CandExamStatusEnum.REVISADO.getStatus());
		    	candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
				redirectPage();
		}
		
		public void redirectPage() {
			try {
				this.errorMsg = "";
	            FacesContext.getCurrentInstance().getExternalContext().redirect("ManageExams.xhtml");
	         } catch (IOException e) {
	            e.printStackTrace();
	        }
		}

		public String getStrDate() {
			return strDate;
		}

		public void setStrDate(String strDate) {
			this.strDate = strDate;
		}
		
		
		 
		public boolean isFlag2() {
			return flag2;
		}

		public void setFlag2(boolean flag2) {
			this.flag2 = flag2;
		}

		public void SaltarPregunta() {
			 CandExamRespSkipDto dto = new CandExamRespSkipDto();
			 dto.setExamen(this.presentExamQuestion.getNumeroMRQsExamen());
			 dto.setNumeroGrupo(this.presentExamQuestion.getMrqsGrupoHdr().getNumero());
			 dto.setNumeroCandExamen(this.presentExamQuestion.getNumeroCandExamen());
			 dto.setNumeroPreguntaFta(this.presentExamQuestion.getNumeroPreguntaFta());
			 dto.setNumeroPreguntaHdr(this.presentExamQuestion.getMrqsGrupoLinesV2().getNumeroPregunta());
			 dto.setSkip(1);
			 
			 if(busquedaSkip == false && 
					 candExamRespSkipLocal
					 .existsSkip(dto.getExamen(),
							 dto.getNumeroCandExamen(),
							 dto.getNumeroGrupo(),
							 dto.getNumeroPreguntaHdr(),
							 dto.getNumeroPreguntaFta(),1)== false) {
				 dto.setNumero(candExamRespSkipLocal.insert(dto)); 
			 }
			 
			 ExamQuestion examQuestion = questionQueue.remove(); // remove queue head
			 examQuestion.setSkip(true);
			 questionQueue.add(examQuestion); // add at end of queue
			 setNextQuestion();
			 /**
			 System.out.println("idxReactivos:"+idxReactivos);
			 reactivosSize = listMrqsGrupoLinesV2.size();
			 if(busquedaSkip == false && idxReactivos<reactivosSize) {
			    idxReactivos = idxReactivos+1; 
		     }
			if(idxReactivos< reactivosSize &&  busquedaSkip==false) {
			  idx =listMrqsGrupoLinesV2.get(idxReactivos);
			}
			if (busquedaSkip==false && idxReactivos == reactivosSize && (idxGrupo+1 < hdrGrupoSize)) {
				idxGrupo++;
				idxReactivos = 0;
				nuevaBusqueda=true;
			}else if(busquedaSkip==true || (idxReactivos == reactivosSize && idxGrupo+1 == hdrGrupoSize)) {
				saltarSkip();
				busquedaSkip=true;
			}else {
			Object objNumeroMglSV = session.getAttribute("NumeroMglSV"); 
			long numeroMgl = utilitariosLocal.objToLong(objNumeroMglSV); 
            
			if(0==numeroMgl) {
				MrqsGrupoHdr idxHdr =listMrqsGrupoHdr.get(idxGrupo);
				  if(nuevaBusqueda) {
					  listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
						reactivosSize=listMrqsGrupoLinesV2.size();
						System.out.println("********Cantidad de Preguntas: "+reactivosSize);
						listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);
						idx =listMrqsGrupoLinesV2.get(idxReactivos);
						nuevaBusqueda=false;
				  }
					mrqsGrupoHdr.setNumero(idxHdr.getNumero());
					candExamRespSkipDto.setNumeroGrupo(mrqsGrupoHdr.getNumero());
					mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
					mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
	//				mrqsGrupoHdr.setTitulo(idxHdr.getTitulo());
					System.out.println("********Cantidad de Preguntas: "+reactivosSize);
					
						System.out.println("idx.getTextoPregunta():"+idx.getTextoPregunta());
						
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
						this.setTipoPregunta(idx.getTipoPregunta());
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
						candExamRespSkipDto.setNumeroPreguntaFta(this.numeroPreguntaFta);
						
						MrqsPreguntasFtaV1 pregunta = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta, idx.getTipoPregunta());
						System.out.println(pregunta.getTextoPregunta());
						
						if(pregunta.getLimiteCaracteres() != null)
							limiteCaracteres = pregunta.getLimiteCaracteres();
						else
							limiteCaracteres = 50;
						if(Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
							mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
							mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
							this.setMultipleChoice(true);
						}
						else if(Utilitarios.CORRELACION_COLUMNA.equals(idx.getTipoPregunta())) {
							this.setCorrelacionColumnas(true);
					    	 actualizarTablaCorrelacionColumnas(getNumeroPreguntaFta(),idx);
					     }
						this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
								                                                     , this.mrqsGrupoHdr.getNumero()
								                                                     , this.mrqsGrupoLinesV2.getNumeroPregunta()
								                                                     , this.numeroPreguntaFta
								                                                     ); 
						if(!mrqsGrupoLinesV2.isSingleAnswerMode()) {
						  if(null!=this.candExamRespuestasV1.getRespuesta()) {	
						  if(this.candExamRespuestasV1.getRespuesta().contains(",")) {
							  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
						  }else {
							  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
						  }
						 }
						}
						this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
				
			}else {
				MrqsGrupoLinesV2 tmp = mrqsGrupoLinesLocal.findByNumeroV2(numeroMgl);
				
				mrqsGrupoHdr.setNumero(tmp.getNumeroHdr());
				mrqsGrupoHdr.setAdmonMateriaDesc(tmp.getTitulo()); 
				//mrqsGrupoHdr.setAdmonMateriaDesc(tmp.getTemaPreguntaDesc());
				mrqsGrupoHdr.setAdmonSubMateriaDesc(tmp.getTemaPreguntaDesc());

				mrqsGrupoLinesV2.setNumero(tmp.getNumero());
			//	mrqsGrupoLinesV2.setTitulo(tmp.getTitulo());
				mrqsGrupoLinesV2.setTextoPregunta(tmp.getTextoPregunta());
				mrqsGrupoLinesV2.setTextoSugerencias(tmp.getTextoSugerencias());
				mrqsGrupoLinesV2.setTipoPregunta(tmp.getTipoPregunta());
				mrqsGrupoLinesV2.setNumeroPregunta(tmp.getNumeroPregunta());
				this.setTipoPregunta(tmp.getTipoPregunta());
				this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
				
				MrqsPreguntasFtaV1 pregunta = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta, tmp.getTipoPregunta());
				System.out.println(pregunta.getTextoPregunta());
				if(pregunta.getLimiteCaracteres() != null)
					limiteCaracteres = pregunta.getLimiteCaracteres();
				else
					limiteCaracteres = 50;
				
				if(Utilitarios.RESP_TEXTO_LIBRE.equals(tmp.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
				}else if(Utilitarios.OPCION_MULTIPLE.equals(tmp.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(tmp.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(tmp.isSuffleAnswerOrder());
					listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
				}
				else if(Utilitarios.CORRELACION_COLUMNA.equals(idx.getTipoPregunta())) {
					this.setCorrelacionColumnas(true);
			    	 actualizarTablaCorrelacionColumnas(getNumeroPreguntaFta(),idx);
			     }

				this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
                        , this.mrqsGrupoHdr.getNumero()
                        , this.mrqsGrupoLinesV2.getNumeroPregunta()
                        , this.numeroPreguntaFta
                        ); 
				if(!mrqsGrupoLinesV2.isSingleAnswerMode()) {
					  if(null!=this.candExamRespuestasV1.getRespuesta()) {	
					  if(this.candExamRespuestasV1.getRespuesta().contains(",")) {
						  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
					  }
					  else {
						  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
					  }
					 }
					}
				this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();

		}

			 listPresentMrqsImagenesGrp =  mrqsImagenesGrpLocal.findByFta(this.numeroPreguntaFta,Utilitarios.INTRODUCCION);
		        	
			
	}

**/
		}

/**
 * 
 *
	public void saltarSkip() {
		lCandExamSkipV1Dto = candExamRespSkipLocal.findAllListSkip(numeroMRQsExamen, numeroCandExamen, 1, 1);
		skipMax = lCandExamSkipV1Dto.size();
		MrqsGrupoHdr idxHdr = new MrqsGrupoHdr();
		if (skipMax == idxSkip) {
			idxSkip = 0;
		}
		if (skipMax == 1) {
			flag2 = true;
			return;
		} else {
			for (int i = 0; i < listMrqsGrupoHdr.size(); i++) {
				if (listMrqsGrupoHdr.get(i).getNumero() == (int) lCandExamSkipV1Dto.get(idxSkip).getNumeroGrupo())
					idxHdr = listMrqsGrupoHdr.get(i);
			}

			mrqsGrupoHdr.setNumero(idxHdr.getNumero());
			mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
			mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
			/* Grupolines preguntas *
			listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
			reactivosSize = listMrqsGrupoLinesV2.size();
			MrqsGrupoLinesV2 idx = new MrqsGrupoLinesV2();
			for (int i = 0; i < reactivosSize; i++) {
						if(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr() == listMrqsGrupoLinesV2.get(i).getNumeroPregunta())
					idx = listMrqsGrupoLinesV2.get(i);
			}
			System.out.println("********Cantidad de Preguntas: " + reactivosSize);
			// listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);

					
			System.out.println("idx.getTextoPregunta():" + idx.getNumeroPregunta());

			mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
			mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
			mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
			mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
			// candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
			/* preguntas *
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr());
			if (Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
				this.setLimitedFreeTextAnswer(true);
			} else if (Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
				mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
				mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
				/* opciones *
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
				this.setMultipleChoice(true);
						}
						else if(Utilitarios.CORRELACION_COLUMNA.equals(idx.getTipoPregunta())) {
				this.setCorrelacionColumnas(true);
				actualizarTablaCorrelacionColumnas(getNumeroPreguntaFta(), idx);
			}
						this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
								                                                     , this.mrqsGrupoHdr.getNumero()
								                                                     , this.mrqsGrupoLinesV2.getNumeroPregunta()
								                                                     , this.numeroPreguntaFta
								                                                     ); 
			if (!mrqsGrupoLinesV2.isSingleAnswerMode()) {
				if (null != this.candExamRespuestasV1.getRespuesta()) {
					if (this.candExamRespuestasV1.getRespuesta().contains(",")) {
						this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
					} else {
						this.respuestasPreguntaCandidato = new String[] { this.candExamRespuestasV1.getRespuesta() };
					}
				}
			}
			this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
			// break;

			idxSkip++;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTimerValue() {
		return timerValue;
	}

	public void setTimerValue(String timerValue) {
		this.timerValue = timerValue;
	}

	public void siguienteGuardarResp(){
			saveAndProceed();
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			Object objNumeroCandExamen = session.getAttribute("NumeroCandExamenSV");
			long numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
			MrqsGrupoLinesV2 idx = new MrqsGrupoLinesV2();
				/* candExamRespSkipDto.setSkip(0);
				 if(busquedaSkip == false && candExamRespSkipLocal.existsSkip(candExamRespSkipDto.getExamen(),candExamRespSkipDto.getNumeroCandExamen(),candExamRespSkipDto.getNumeroGrupo(),candExamRespSkipDto.getNumeroPreguntaHdr(),candExamRespSkipDto.getNumeroPreguntaFta(),1)== true) {
					 candExamRespSkipLocal.update(candExamRespSkipDto.getNumero(),candExamRespSkipDto); 
				 }*/
			answerQueue.add(this.presentExamQuestion);
			questionQueue.remove();
			setNextQuestion();		
		
		/**
		System.out.println("idxReactivos:" + idxReactivos);
		reactivosSize = listMrqsGrupoLinesV2.size();
			 if(busquedaSkip==false && idxReactivos<reactivosSize) {
			 idxReactivos = idxReactivos+1; 
		}
			if(idxReactivos< reactivosSize &&  busquedaSkip==false) {
			  idx =listMrqsGrupoLinesV2.get(idxReactivos);
			}
			if (busquedaSkip==false && idxReactivos == reactivosSize && (idxGrupo+1 < hdrGrupoSize)) {
			idxGrupo++;
			idxReactivos = 0;
			nuevaBusqueda = true;
		} else if (busquedaSkip == true || (idxReactivos == reactivosSize && idxGrupo + 1 == hdrGrupoSize)) {
			guardarSkip();
			busquedaSkip = true;
		} else {
			Object objNumeroMglSV = session.getAttribute("NumeroMglSV");
			long numeroMgl = utilitariosLocal.objToLong(objNumeroMglSV);

			if (0 == numeroMgl) {
				MrqsGrupoHdr idxHdr = listMrqsGrupoHdr.get(idxGrupo);
				  if(nuevaBusqueda) {
					  listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
						reactivosSize=listMrqsGrupoLinesV2.size();
						System.out.println("********Cantidad de Preguntas: "+reactivosSize);
						listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);
						idx =listMrqsGrupoLinesV2.get(idxReactivos);
						nuevaBusqueda=false;
				  }
					mrqsGrupoHdr.setNumero(idxHdr.getNumero());
					candExamRespSkipDto.setNumeroGrupo(mrqsGrupoHdr.getNumero());
					mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
					mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
										
					System.out.println("********Cantidad de Preguntas: "+reactivosSize);
					
						System.out.println("idx.getTextoPregunta():"+idx.getTextoPregunta());
						
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						mrqsGrupoLinesV2.setStartTime(new Date());
						candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
						this.setTipoPregunta(idx.getTipoPregunta());
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
				candExamRespSkipDto.setNumeroPreguntaFta(this.numeroPreguntaFta);
				if (Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
					mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,
							idx.getTipoPregunta());
					if (mrqsPreguntasFtaV1ForRead.getLimiteCaracteres() != null)
						limiteCaracteres = mrqsPreguntasFtaV1ForRead.getLimiteCaracteres();
					else
						limiteCaracteres = 50;

				} else if (Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
						}
						else
						if(Utilitarios.IMAGEN_ANOTADA.equals(idx.getTipoPregunta())) {
					this.setAnnotatedImage(true);
					mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,
							idx.getTipoPregunta());
					Gson gson = new Gson();
					System.out.println("mrqsPreguntasFtaV1ForRead.widht" + mrqsPreguntasFtaV1ForRead.getWidth());
					    	System.out.println("mrqsPreguntasFtaV1ForRead el content type"+ mrqsPreguntasFtaV1ForRead.getContentType());
					if (null != mrqsPreguntasFtaV1ForRead.getRespuestas()) {
				            	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
				            	setListRespReactCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getRespuestas(), collectionType)); 
						refreshRespuestas();
					}
					if (null != mrqsPreguntasFtaV1ForRead.getAnotaciones()) {
				            	 Type collectionType = new TypeToken<List<AnotacionesCorImg>>(){}.getType();
				            	 setListAnotacionesCorImg(gson.fromJson(mrqsPreguntasFtaV1ForRead.getCorrelaciones(), collectionType)); 
					}
					     }
						else if(Utilitarios.CORRELACION_COLUMNA.equals(idx.getTipoPregunta())) {
					this.setCorrelacionColumnas(true);
					actualizarTablaCorrelacionColumnas(getNumeroPreguntaFta(), idx);
				}

						this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
								                                                     , this.mrqsGrupoHdr.getNumero()
								                                                     , this.mrqsGrupoLinesV2.getNumeroPregunta()
								                                                     , this.numeroPreguntaFta
								                                                     ); 
				if (!mrqsGrupoLinesV2.isSingleAnswerMode()) {
					if (null != this.candExamRespuestasV1.getRespuesta()) {
						if (this.candExamRespuestasV1.getRespuesta().contains(",")) {
							this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
						} else {
							  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
						}
					}
				}
				this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();

			} else {
				MrqsGrupoLinesV2 tmp = mrqsGrupoLinesLocal.findByNumeroV2(numeroMgl);

				mrqsGrupoHdr.setNumero(tmp.getNumeroHdr());
				mrqsGrupoHdr.setAdmonMateriaDesc(tmp.getTitulo()); 
				//mrqsGrupoHdr.setAdmonMateriaDesc(tmp.getTemaPreguntaDesc());
				mrqsGrupoHdr.setAdmonSubMateriaDesc(tmp.getTemaPreguntaDesc());

				mrqsGrupoLinesV2.setNumero(tmp.getNumero());
			//	mrqsGrupoLinesV2.setTitulo(tmp.getTitulo());
				mrqsGrupoLinesV2.setTextoPregunta(tmp.getTextoPregunta());
				mrqsGrupoLinesV2.setTextoSugerencias(tmp.getTextoSugerencias());
				mrqsGrupoLinesV2.setTipoPregunta(tmp.getTipoPregunta());
				mrqsGrupoLinesV2.setNumeroPregunta(tmp.getNumeroPregunta());
				mrqsGrupoLinesV2.setStartTime(new Date());
				this.setTipoPregunta(tmp.getTipoPregunta());
				this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());

				if(Utilitarios.RESP_TEXTO_LIBRE.equals(tmp.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
					MrqsPreguntasFtaV1 pregunta = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,idx.getTipoPregunta());
					if(pregunta.getLimiteCaracteres() != null)
						limiteCaracteres = pregunta.getLimiteCaracteres();
					else
						limiteCaracteres = 50;
				}else if(Utilitarios.OPCION_MULTIPLE.equals(tmp.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(tmp.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(tmp.isSuffleAnswerOrder());
					listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
		}
				else if(Utilitarios.CORRELACION_COLUMNA.equals(idx.getTipoPregunta())) {
					this.setCorrelacionColumnas(true);
			    	 actualizarTablaCorrelacionColumnas(getNumeroPreguntaFta(),idx);
		}

				this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
                        , this.mrqsGrupoHdr.getNumero()
                        , this.mrqsGrupoLinesV2.getNumeroPregunta()
                        , this.numeroPreguntaFta
                        ); 
				if(!mrqsGrupoLinesV2.isSingleAnswerMode()) {
					  if(null!=this.candExamRespuestasV1.getRespuesta()) {	
					  if(this.candExamRespuestasV1.getRespuesta().contains(",")) {
						  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
	}
					  else {
						  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
					  }
					 }
					}
				this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
	
		}
		
			 listPresentMrqsImagenesGrp =  mrqsImagenesGrpLocal.findByFta(this.numeroPreguntaFta,Utilitarios.INTRODUCCION);
		

		}
		
			System.out.println("id " + idxReactivos + " size " +  reactivosSize + " "+busquedaSkip);
			if(idxReactivos == reactivosSize ) { // ??usar busquedaSkip aquí ocasiona que algunas veces el examen no termine:
				//this.candExamenesV1 = candExamenesLocal.findByNumero(numeroCandExamen); ???
			  	CandExamenesDto candExamenesDto = new CandExamenesDto();
		    	candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
		    	idxReactivos--;
		    	showFinalMessage = true;
				//redirectPage(); //no se llama aquí por que ahora se llama la función al cerrar el mensaje final
				return;
		
			}
			/*if(null!=this.candExamRespuestasV1.getRespuesta()) {	
				if (this.candExamRespuestasV1.getRespuesta().contains(",")) {
					this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
				} else {
					  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
				  }
				 }
				for(int i = 0;i< this.respuestasPreguntaCandidato.length;i++) {
					System.out.println("Respuesta "+i+" : "+respuestasPreguntaCandidato[i]);				
		}
				*/

	}
	

	public void refreshRespuestas() {
		selectRespReactCorImg = new ArrayList<SelectItem>();
		for (RespReactCorImg i : listRespReactCorImg) {
			SelectItem selectItem = new SelectItem(i.getNumero(), i.getRespuesta());
			selectRespReactCorImg.add(selectItem);
			System.out.println("SELECT ITEM " + selectItem);
			candRespReactCorImg.add(selectItem);
		}
	}

	public void updateRespuestasImgCor(Object x) {
		System.out.println("X=>" + x.toString());
			SelectItem selectItem = new SelectItem(
					x,
				listRespReactCorImg.get(Integer.parseInt(respuestaSelect.getValue().toString())).getRespuesta());
		System.out.println("getLabel " + selectItem.getLabel());
		System.out.println("getValue " + selectItem.getValue());

		candRespReactCorImg.add(selectItem);
		// listRespReactCandCorImg.add(candRespReactCorImg);
	}
	
	/**
	 * 
	 * @param lmqrsGrouoHdr
	 * @return
	 *
	public List<MrqsGrupoHdr> GrupoAleatorio(List<MrqsGrupoHdr> lmqrsGrouoHdr) {
		int elementosMax = lmqrsGrouoHdr.size();
		int numero;
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		ArrayList<Integer> arrayRandom = new ArrayList<Integer>();
		List<MrqsGrupoHdr> lmgh = new ArrayList<MrqsGrupoHdr>();
		// llenaValores
		for (int i = 0; i < elementosMax; i++) {
			numeros.add(i);
		}

			
		for (int i = 0; i < elementosMax; i++) {
			numero = (int) (Math.random() * numeros.size());
			if (arrayRandom.contains(numero)) {
				i--;
			} else {
				arrayRandom.add(numero);
			}
		}

		for (int i = 0; i < elementosMax; i++) {
			lmgh.add(lmqrsGrouoHdr.get(arrayRandom.get(i)));
		}

		return lmgh;
	}
	
	/**
	 * 
	 * @param lmqrsGrupoLines
	 * @return
	 *
	public List<MrqsGrupoLinesV2> PreguntasAleatorio(List<MrqsGrupoLinesV2> lmqrsGrupoLines) {
		int elementosMax = lmqrsGrupoLines.size();
		int numero;
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		ArrayList<Integer> arrayRandom = new ArrayList<Integer>();
		List<MrqsGrupoLinesV2> lmgl = new ArrayList<MrqsGrupoLinesV2>();
		// llenaValores
		for (int i = 0; i < elementosMax; i++) {
			numeros.add(i);
		}

			
		for (int i = 0; i < elementosMax; i++) {
			numero = (int) (Math.random() * numeros.size());
			if (arrayRandom.contains(numero)) {
				i--;
			} else {
				arrayRandom.add(numero);
			}
		}

		for (int i = 0; i < elementosMax; i++) {
			// System.out.println("Random: "+arrayRandom.get(i));
			lmgl.add(lmqrsGrupoLines.get(arrayRandom.get(i)));
		}

		return lmgl;
	}
	
	/**
	 * 
	 *
	public void guardarSkip() {
		MrqsGrupoHdr idxHdr = new MrqsGrupoHdr();
		saveAndProceed();

		candExamRespSkipDto.setSkip(0);

		lCandExamSkipV1Dto = candExamRespSkipLocal.findAllListSkip(numeroMRQsExamen, numeroCandExamen, 1, 1);
		skipMax = lCandExamSkipV1Dto.size();
		idxSkip = 0;
		if (skipMax == 0) {
			flag2 = true;

		} else {
			for (int i = 0; i < listMrqsGrupoHdr.size(); i++) {
				if (listMrqsGrupoHdr.get(i).getNumero() == (int) lCandExamSkipV1Dto.get(idxSkip).getNumeroGrupo()) {
					candExamRespSkipDto.setNumeroGrupo(lCandExamSkipV1Dto.get(idxSkip).getNumeroGrupo());
					candExamRespSkipDto.setExamen(lCandExamSkipV1Dto.get(idxSkip).getExamen());
					candExamRespSkipDto.setEstatus(0);
					candExamRespSkipDto.setNumero(lCandExamSkipV1Dto.get(idxSkip).getNumero());
					candExamRespSkipDto.setNumeroCandExamen(lCandExamSkipV1Dto.get(idxSkip).getNumeroCandExamen());
					candExamRespSkipDto.setNumeroPreguntaHdr(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr());
					candExamRespSkipDto.setNumeroPreguntaFta(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaFta());

					candExamRespSkipLocal.update(lCandExamSkipV1Dto.get(idxSkip).getNumero(), candExamRespSkipDto);
					idxHdr = listMrqsGrupoHdr.get(i);
				}
			}
			if (skipMax == 1) {
				CandExamenesDto candExamenesDto = new CandExamenesDto();
				candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
				redirectPage();
				return;
			}
			mrqsGrupoHdr.setNumero(idxHdr.getNumero());
			mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
			mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
			/* Grupolines preguntas *
			listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
			reactivosSize = listMrqsGrupoLinesV2.size();
			MrqsGrupoLinesV2 idx = new MrqsGrupoLinesV2();
			for (int i = 0; i < reactivosSize; i++) {
						if(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr() == listMrqsGrupoLinesV2.get(i).getNumeroPregunta())
					idx = listMrqsGrupoLinesV2.get(i);
			}
			System.out.println("********Cantidad de Preguntas: " + reactivosSize);
			// listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);

					
			System.out.println("idx.getTextoPregunta():" + idx.getNumeroPregunta());

			mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
			mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
			mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
			mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
			// candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
			/* preguntas *
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr());
			if (Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
				this.setLimitedFreeTextAnswer(true);
							MrqsPreguntasFtaV1 pregunta = mrqsPreguntasFtaLocal.findObjModByNumeroFta(numeroPreguntaFta,idx.getTipoPregunta());
				if (pregunta.getLimiteCaracteres() != null)
					limiteCaracteres = pregunta.getLimiteCaracteres();
				else
					limiteCaracteres = 50;
			} else if (Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
				mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
				mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
				/* opciones *
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
				this.setMultipleChoice(true);
			}

						this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
								                                                     , this.mrqsGrupoHdr.getNumero()
								                                                     , this.mrqsGrupoLinesV2.getNumeroPregunta()
								                                                     , this.numeroPreguntaFta
								                                                     ); 
			if (!mrqsGrupoLinesV2.isSingleAnswerMode()) {
				if (null != this.candExamRespuestasV1.getRespuesta()) {
					if (this.candExamRespuestasV1.getRespuesta().contains(",")) {
						this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
					} else {
						this.respuestasPreguntaCandidato = new String[] { this.candExamRespuestasV1.getRespuesta() };
					}
				}
			}
			this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
			// break;

			idxSkip++;
		}
	}
	
	/**
	 * 
	 */
	public MrqsPreguntasFtaV1 getMrqsPreguntasFtaV1ForRead() {
		return mrqsPreguntasFtaV1ForRead;
	}

	public void setMrqsPreguntasFtaV1ForRead(MrqsPreguntasFtaV1 mrqsPreguntasFtaV1ForRead) {
		this.mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaV1ForRead;
	}

	/**
	 * @return the listAnotacionesCorImg
	 */
	public List<AnotacionesCorImg> getListAnotacionesCorImg() {
		return listAnotacionesCorImg;
	}

	/**
	 * @param listAnotacionesCorImg the listAnotacionesCorImg to set
	 */
	public void setListAnotacionesCorImg(List<AnotacionesCorImg> listAnotacionesCorImg) {
		this.listAnotacionesCorImg = listAnotacionesCorImg;
	}

	/**
	 * @return the listRespReactCorImg
	 */
	public List<RespReactCorImg> getListRespReactCorImg() {
		return listRespReactCorImg;
	}

	/**
	 * @param listRespReactCorImg the listRespReactCorImg to set
	 */
	public void setListRespReactCorImg(List<RespReactCorImg> listRespReactCorImg) {
		this.listRespReactCorImg = listRespReactCorImg;
	}

	/**
	 * @return the selectRespReactCorImg
	 */
	public List<SelectItem> getSelectRespReactCorImg() {
		return selectRespReactCorImg;
	}

	/**
	 * @param selectRespReactCorImg the selectRespReactCorImg to set
	 */
	public void setSelectRespReactCorImg(List<SelectItem> selectRespReactCorImg) {
		this.selectRespReactCorImg = selectRespReactCorImg;
	}

	/**
	 * @return the respuestaSelect
	 */
	public SelectItem getRespuestaSelect() {
		return respuestaSelect;
	}

	/**
	 * @param respuestaSelect the respuestaSelect to set
	 */
	public void setRespuestaSelect(SelectItem respuestaSelect) {
		this.respuestaSelect = respuestaSelect;
	}

	/**
	 * @return the tipoPregunta
	 */
	public String getTipoPregunta() {
		return tipoPregunta;
	}

	/**
	 * @param tipoPregunta the tipoPregunta to set
	 */
	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	/**
	 * @return the listMrqsCorrelacionColumnasDto
	 */
	public List<MrqsCorrelacionColumnasDto> getListMrqsCorrelacionColumnasDto() {
		return listMrqsCorrelacionColumnasDto;
	}

	/**
		 * @param listMrqsCorrelacionColumnasDto the listMrqsCorrelacionColumnasDto to set
	 */
	public void setListMrqsCorrelacionColumnasDto(List<MrqsCorrelacionColumnasDto> listMrqsCorrelacionColumnasDto) {
		this.listMrqsCorrelacionColumnasDto = listMrqsCorrelacionColumnasDto;
	}

	/**
	 * @return the listMrqsCorrelacionColumnasRespuestasDto
	 */
	public List<MrqsCorrelacionColumnasRespuestasDto> getListMrqsCorrelacionColumnasRespuestasDto() {
		return listMrqsCorrelacionColumnasRespuestasDto;
	}

	/**
		 * @param listMrqsCorrelacionColumnasRespuestasDto the listMrqsCorrelacionColumnasRespuestasDto to set
	 */
		public void setListMrqsCorrelacionColumnasRespuestasDto(List<MrqsCorrelacionColumnasRespuestasDto> listMrqsCorrelacionColumnasRespuestasDto) {
		this.listMrqsCorrelacionColumnasRespuestasDto = listMrqsCorrelacionColumnasRespuestasDto;
	}

	/**
	 * @return the listMrqsCorrelacionColumnasPrev
	 */
	public List<MrqsCorrelacionColumnaPair> getListMrqsCorrelacionColumnas() {
		return listMrqsCorrelacionColumnas;
	}

	/**
		 * @param listMrqsCorrelacionColumnasPrev the listMrqsCorrelacionColumnasPrev to set
	 */
	public void setListMrqsCorrelacionColumnas(List<MrqsCorrelacionColumnaPair> listMrqsCorrelacionColumnasPrev) {
		this.listMrqsCorrelacionColumnas = listMrqsCorrelacionColumnasPrev;
	}

	/**
	 * @return the correlacionColumnas
	 */
	public boolean isCorrelacionColumnas() {
		return correlacionColumnas;
	}

	/**
	 * @param correlacionColumnas the correlacionColumnas to set
	 */
	public void setCorrelacionColumnas(boolean correlacionColumnas) {
		if (correlacionColumnas) {
			this.setAnnotatedImage(false);
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.correlacionColumnas = correlacionColumnas;
	}

	/**
	 * @return the panelCorrelacionColumnasResultados
	 */
	public boolean isPanelCorrelacionColumnasResultados() {
		return panelCorrelacionColumnasResultados;
	}

	/**
		 * @param panelCorrelacionColumnasResultados the panelCorrelacionColumnasResultados to set
	 */
	public void setPanelCorrelacionColumnasResultados(boolean panelCorrelacionColumnasResultados) {
		this.panelCorrelacionColumnasResultados = panelCorrelacionColumnasResultados;
	}



	public Queue<ExamQuestion> getQuestionQueue() {
		return questionQueue;
	}



	public void setQuestionQueue(Queue<ExamQuestion> questionQueue) {
		this.questionQueue = questionQueue;
	}



	public ExamQuestion getPresentExamQuestion() {
		return presentExamQuestion;
	}



	public void setPresentExamQuestion(ExamQuestion presentExamQuestion) {
		this.presentExamQuestion = presentExamQuestion;
	}




	public long getExamTimeout() {
		return examTimeout;
	}




	public void setExamTimeout(long examTimeout) {
		this.examTimeout = examTimeout;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Queue<ExamQuestion> getAnswerQueue() {
		return answerQueue;
	}

	public void setAnswerQueue(Queue<ExamQuestion> answerQueue) {
		this.answerQueue = answerQueue;
	}
	
	public long getSkipCount() {
		return questionQueue.stream().filter(e->e.isSkip()).count();
	}
	
	public long getQuestionCount() {
		return questionQueue.stream().filter(e->!e.isSkip()).count();
	}
	
	
	
}
