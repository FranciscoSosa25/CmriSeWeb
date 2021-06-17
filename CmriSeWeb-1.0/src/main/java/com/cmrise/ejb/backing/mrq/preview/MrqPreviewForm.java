package com.cmrise.ejb.backing.mrq.preview;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.RequestContext;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.mrqs.AnotacionesCorImg;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.RespReactCorImg;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.mrqs.MrqsCorrelacionColumnasLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaSinonimosLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.jpa.dao.mrqs.MrqsCorrelacionColumnaPair;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;
import com.cmrise.utils.Utilitarios;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;
@ManagedBean
@ViewScoped
public class MrqPreviewForm {

	private long numeroHdr; 
	private long numetoFta; 
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private String tipoPregunta; 
	private String tituloPregunta; 
	private String textoPregunta; 
	private String textoSugerencias; 
	private String respuestaPreguntaCandidato;
	private String respuestaPreguntaSistema; 
	private String textoExplicacion;
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
	
	@Inject 
	MrqsCorrelacionColumnasLocal mrqsCorrelacionColumnasLocal;
	private List<MrqsCorrelacionColumnasDto> listMrqsCorrelacionColumnasDto = new ArrayList<MrqsCorrelacionColumnasDto>();
	private List<MrqsCorrelacionColumnasRespuestasDto> listMrqsCorrelacionColumnasRespuestasDto = new ArrayList<MrqsCorrelacionColumnasRespuestasDto>();
	private List<MrqsCorrelacionColumnaPair> listMrqsCorrelacionColumnasPrev = new ArrayList<MrqsCorrelacionColumnaPair>();
	private boolean correlacionColumnas;
	private boolean panelCorrelacionColumnasResultados;
	/********************************************************************
	 * Attributos Imagenes 
	 */
	
	private List<MrqsImagenesGrp> listPresentMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
	
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForRead = new MrqsPreguntasHdrV1();
	private MrqsPreguntasFtaV1 mrqsPreguntasFtaV1ForRead = new MrqsPreguntasFtaV1(); 
	
	private List<SelectItem> selectRespReactCorImg = new ArrayList<SelectItem>(); 
	private List<RespReactCorImg> listRespReactCorImg = new ArrayList<RespReactCorImg>(); 
	private List<AnotacionesCorImg> listAnotacionesCorImg = new ArrayList<AnotacionesCorImg>();

	private String indicateImageResult;
	private Integer limiteCaracteres;
	private List<MrqsPreguntasFtaSinonimos> mrqsListaSinonimos= new ArrayList<MrqsPreguntasFtaSinonimos>();
	
	private int puntaje;
	private float calificacion; 
	private List<SelectItem> listRespuestasCandImg = new ArrayList<SelectItem>();
	private SelectItem respuestaSelect = new SelectItem();
	
	@Inject 
	MrqsPreguntasFtaSinonimosLocal mrqsPreguntasFtaSinonimosLocal;
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal; 
	
	@Inject
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal;
	
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
		 System.out.println("Entra context ()"+ context);
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     System.out.println("Entra session ()"+ session);
	     Object objMrqNumeroHdr = session.getAttribute("mrqNumeroHdrSV");
	     System.out.println("Entra objMrqNumeroHdr ()"+ objMrqNumeroHdr);
	     this.setNumeroHdr(Utilitarios.objToLong(objMrqNumeroHdr)); 
	    
	     MrqsPreguntasHdrV2Dto  mrqsPreguntasHdrV2Dto = mrqsPreguntasHdrLocal.findV2ByNumeroHdr(this.getNumeroHdr()); 
	    System.out.println("Entra MrqsPreguntasHdrV2Dto"+mrqsPreguntasHdrV2Dto);
	     this.setTipoPregunta(mrqsPreguntasHdrV2Dto.getTipoPregunta());
	     
	     this.setNumetoFta(mrqsPreguntasHdrV2Dto.getNumeroMpf());
	     
	     this.setTituloPregunta(mrqsPreguntasHdrV2Dto.getTituloPregunta());
	    
	     this.setTextoPregunta(mrqsPreguntasHdrV2Dto.getTextoPregunta());
	    
	     this.setTextoSugerencias(mrqsPreguntasHdrV2Dto.getTextoSugerencias());
	     System.out.println("Texto sugerencias ()"+ mrqsPreguntasHdrV2Dto.getTextoSugerencias());
	     this.setRespuestaPreguntaSistema(mrqsPreguntasHdrV2Dto.getRespuestaCorrecta());
	    
	
	     this.puntuacion = Float.parseFloat(mrqsPreguntasHdrV2Dto.getValorPuntuacion()); 
	     this.metodoPuntuacion = mrqsPreguntasHdrV2Dto.getMetodoPuntuacion(); 
	     if(Utilitarios.RESP_TEXTO_LIBRE.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	if(mrqsPreguntasHdrV2Dto.getLimiteCaracteres()!=null)
	    	 setLimiteCaracteres(mrqsPreguntasHdrV2Dto.getLimiteCaracteres());
	    	 this.setLimitedFreeTextAnswer(true);
	    	 obtenerSinonimos(getNumetoFta());
	     }else if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	 this.setMultipleChoice(true);
	    	 initListMrqsOpcionMultiple(mrqsPreguntasHdrV2Dto.getNumeroMpf(),mrqsPreguntasHdrV2Dto.isSuffleAnswerOrder()); 
	         this.setSingleAnswerMode(mrqsPreguntasHdrV2Dto.isSingleAnswerMode());
	         this.setSuffleAnswerOrder(mrqsPreguntasHdrV2Dto.isSuffleAnswerOrder());
	         this.totalCorrectAnswers = mrqsOpcionMultipleLocal.totalCorrectAnswers(mrqsPreguntasHdrV2Dto.getNumeroMpf()); 
	     }else if(Utilitarios.IMAGEN_INDICADA.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	        this.setIndicateImage(true);	 
	     }else if(Utilitarios.IMAGEN_ANOTADA.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	this.setAnnotatedImage(true); 
	     }else if(Utilitarios.CORRELACION_COLUMNA.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	 actualizarPrevisualizacion(getNumetoFta(),mrqsPreguntasHdrV2Dto);
	     }
	     this.setQuestionView(true);
	     
	     mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaLocal.findObjModByNumeroFta(mrqsPreguntasHdrV2Dto.getNumeroMpf()
																	             ,mrqsPreguntasHdrV2Dto.getTipoPregunta()
																	             );
	     System.out.println(mrqsPreguntasFtaV1ForRead.getRespuestas());
	     
	     if(Utilitarios.IMAGEN_ANOTADA.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	 Gson gson = new Gson();
             if(null!=mrqsPreguntasFtaV1ForRead.getRespuestas()) {
            	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
            	listRespReactCorImg = gson.fromJson(mrqsPreguntasFtaV1ForRead.getRespuestas(), collectionType); 
            	refreshRespuestas();
             }
             if(null!=mrqsPreguntasFtaV1ForRead.getAnotaciones()) {
            	 Type collectionType = new TypeToken<List<AnotacionesCorImg>>(){}.getType();
            	 listAnotacionesCorImg = gson.fromJson(mrqsPreguntasFtaV1ForRead.getCorrelaciones(), collectionType); 
             }
	     }
	     
	     listPresentMrqsImagenesGrp =  mrqsImagenesGrpLocal.findByFta(mrqsPreguntasHdrV2Dto.getNumeroMpf(),Utilitarios.INTRODUCCION);
         
	     System.out.println(" valores list present mrqs imagen:"+listPresentMrqsImagenesGrp);
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
        	mrqsOpcionMultiple.setTextoExplicacion(mrqsOpcionMultipleDto.getTextoExplicacion());
        	
        	listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
        }
	}
    private void validarPuntosUsuario() {
    	 limpiarMensajes();
   	  if( (getRespuestaPreguntaCandidato()==null || getRespuestaPreguntaCandidato().isEmpty()  )  ) {
   		
   		  PrimeRequestContext context = PrimeRequestContext.getCurrentInstance();   		 
   		//  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Utilitarios.ERROR_PUNTOS_USUARIO, null));
   		  
			
			// PrimeFaces.current().executeScript("location.reload();");
		
   		
   		  return;
   	  }
    }
	public void  saveProceed() {
	  System.out.println("Entra saveProceed");	
	  validarPuntosUsuario();
	  this.setAnswerView(true);
	  if(this.isLimitedFreeTextAnswer()) {
		  if(this.getRespuestaPreguntaCandidato().equalsIgnoreCase(this.getRespuestaPreguntaSistema()) || validarSinonimos(getRespuestaPreguntaCandidato())) {
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
	  } else if (this.isIndicateImage()) {
		  
		 
		 int puntuacion= Integer.valueOf(mrqsPreguntasFtaV1ForRead.getValorPuntuacion());
		 int poligonos=Integer.valueOf(mrqsPreguntasFtaV1ForRead.getPoligonos());
		 String coordenadasPoligonos=mrqsPreguntasFtaV1ForRead.getRespuestas();
		 String coordenadasUsuario=getRespuestaPreguntaCandidato();
		 int ancho=mrqsPreguntasFtaV1ForRead.getWidth();
		 Poligonos ob= new Poligonos();
		 double puntuacionR=ob.obtenerPuntuacion(puntuacion,poligonos,ancho,coordenadasUsuario,coordenadasPoligonos);
		 BigDecimal bd = new BigDecimal(puntuacionR).setScale(2, BigDecimal.ROUND_DOWN);
	  	if (puntuacionR>0) {
	  		setPuntuacion((float)bd.floatValue());
			this.setCorrectAnswer(true);

		} else {

			this.setWrongAnswer(true);

			this.setPuntuacion(0);
		}
	  	
	  }
	  else if (this.isAnnotatedImage()) {
			evaluateAnnotatedImage();
		}
	  
	  comprobarRespuestasCorrelacionColumnas();
	  System.out.println("Sale saveProceed");	
	}
	private void limpiarMensajes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> it = context.getMessages();
		while ( it.hasNext() ) {
		    it.next();
		    it.remove();
		}
	}
	private void comprobarRespuestasCorrelacionColumnas() {
		if(Utilitarios.CORRELACION_COLUMNA.equals(getTipoPregunta())){
		Iterator<MrqsCorrelacionColumnasRespuestasDto> lista=	listMrqsCorrelacionColumnasRespuestasDto.iterator();
	    float valorItem=getPuntuacion()/listMrqsCorrelacionColumnasRespuestasDto.size();
		int respuestasCorrectas=0;		
		float puntuacion=0.0f;
		while(lista.hasNext()) {
	
			MrqsCorrelacionColumnasRespuestasDto var=lista.next();
			if(var.getTexto().equals(var.getValorSeleccionado())) {
				respuestasCorrectas++;
				puntuacion+=valorItem;
			}
		}			
	    setWrongAnswer((respuestasCorrectas== listMrqsCorrelacionColumnasRespuestasDto.size())?true:false);
		setCorrectAnswers(correctAnswers.replace("0", String.valueOf(respuestasCorrectas)));
	    setWrongAnswers(wrongAnswers.replace("0", String.valueOf(listMrqsCorrelacionColumnasRespuestasDto.size()-respuestasCorrectas)));  
	    BigDecimal bd = new BigDecimal(puntuacion).setScale(2, BigDecimal.ROUND_DOWN);
	    setPuntuacion(bd.floatValue());
	    setPanelCorrelacionColumnasResultados(true);
	    setCorrelacionColumnas(false);
	
		}
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
	   int respuestasCorrectasReactivo=0;
	  if(null!=this.getRespuestasPreguntaCandidato()) {
		  String [] array = this.getRespuestasPreguntaCandidato(); 
		  respuestasCorrectasReactivo= (int)listMrqsOpcionMultiple.stream().filter(a->a.isEstatus()).count();
          
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
	  if(countCorrectAnswers>0 && respuestasCorrectasReactivo==countCorrectAnswers) {
		  this.setCorrectAnswer(true);
		  System.out.println("metodoPuntuacion:"+this.metodoPuntuacion);
		  if(Utilitarios.PROP_SCORING.equals(this.metodoPuntuacion)) {
			  System.out.println("totalCorrectAnswers:"+this.totalCorrectAnswers);
			  System.out.println("puntuacion:"+this.puntuacion); 		   
		   float floatPuntuacionProp= (getPuntuacion()*(float)countCorrectAnswers)/(float)getTotalCorrectAnswers();
		   System.out.println("floatPuntuacionProp:"+floatPuntuacionProp);
		   BigDecimal bd = new BigDecimal(floatPuntuacionProp).setScale(2, BigDecimal.ROUND_DOWN);
		   this.setPuntuacion(bd.floatValue() );
		  }
	  }else {
		  this.setWrongAnswer(true);
		  this.setPuntuacion(0);
	  }
	  
	  this.setCorrectAnswers(countCorrectAnswers+" Respuesta(s) correctas");
	  this.setWrongAnswers(countWrongAnswers+" Respuesta(s) incorrectas"); 
	  
	}
	
	public void evaluateAnnotatedImage() {
		int contador = 0;
		puntaje = 0;
		
		System.out.println("valor puntuacion "+puntuacion);
		for(SelectItem i: listRespuestasCandImg)
		{
			System.out.println("value: "+this.listRespuestasCandImg.get(contador).getValue());
			System.out.println("numeroRespuesta"+this.listAnotacionesCorImg.get(contador).getNumeroRespuesta());
			System.out.println("contador "+ contador);
			if(Integer.parseInt((String) i.getValue()) == this.listAnotacionesCorImg.get(contador).getNumeroRespuesta()) {
				puntaje++;
			}
			contador++;
		}
		calificacion = puntuacion/listRespuestasCandImg.size() * puntaje;
		System.out.println("respuestas correctas totales: "+ puntaje);
		System.out.println("valor al evaluar: "+ calificacion);
	}
	
	public void agregarRespCandidato(int index) {
		SelectItem nuevaRespuesta = new SelectItem();
		nuevaRespuesta.setValue(respuestaSelect.getValue());
		listRespuestasCandImg.set(index, nuevaRespuesta);
		System.out.println("Sale agregarRespCandidato");
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
	
	private void refreshRespuestas() {
		//para inicializar la lista de respuestas a evaluar se inicializa listRespuestasCandImg
		SelectItem e = new SelectItem();
		selectRespReactCorImg = new ArrayList<SelectItem>(); 
		for(RespReactCorImg i:listRespReactCorImg) {
		     listRespuestasCandImg.add(e);
			SelectItem selectItem = new SelectItem(i.getNumero(),i.getRespuesta()); 
			selectRespReactCorImg.add(selectItem); 
		}
	}
	private void actualizarPrevisualizacion(long lNumeroFta,MrqsPreguntasHdrV2Dto mrqsPreguntasHdrV2Dto) {
		setCorrelacionColumnas(Utilitarios.CORRELACION_COLUMNA.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta()));
		listMrqsCorrelacionColumnasDto=mrqsCorrelacionColumnasLocal.findByFta(lNumeroFta);
		listMrqsCorrelacionColumnasRespuestasDto=mrqsCorrelacionColumnasLocal.findRespuestasCorrectasByFta(lNumeroFta);
		int length=listMrqsCorrelacionColumnasDto.size()>listMrqsCorrelacionColumnasRespuestasDto.size()?listMrqsCorrelacionColumnasDto.size():listMrqsCorrelacionColumnasRespuestasDto.size();
		for(int i=0;i<length;i++) {
	
			listMrqsCorrelacionColumnasPrev.add(new 
					MrqsCorrelacionColumnaPair(i>=listMrqsCorrelacionColumnasDto.size()?
					null:listMrqsCorrelacionColumnasDto.get(i), 
					i>=listMrqsCorrelacionColumnasRespuestasDto.size()?
							null:listMrqsCorrelacionColumnasRespuestasDto.get(i) ));
		}
		
	}
	private void obtenerSinonimos(long pNumeroFta) {
		mrqsListaSinonimos=mrqsPreguntasFtaSinonimosLocal.findByFta(pNumeroFta);
	}
	private boolean validarSinonimos(String textoUsuario) {
		Iterator<MrqsPreguntasFtaSinonimos> it=mrqsListaSinonimos.iterator();
		while(it.hasNext()) {
			if(textoUsuario.equalsIgnoreCase(it.next().getTextoSinonimo()))
					return true;
		}
		return false;
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

	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1ForRead() {
		return mrqsPreguntasHdrV1ForRead;
	}

	public void setMrqsPreguntasHdrV1ForRead(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForRead) {
		this.mrqsPreguntasHdrV1ForRead = mrqsPreguntasHdrV1ForRead;
	}

	public MrqsPreguntasFtaV1 getMrqsPreguntasFtaV1ForRead() {
		return mrqsPreguntasFtaV1ForRead;
	}

	public void setMrqsPreguntasFtaV1ForRead(MrqsPreguntasFtaV1 mrqsPreguntasFtaV1ForRead) {
		this.mrqsPreguntasFtaV1ForRead = mrqsPreguntasFtaV1ForRead;
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public List<SelectItem> getSelectRespReactCorImg() {
		return selectRespReactCorImg;
	}

	public void setSelectRespReactCorImg(List<SelectItem> selectRespReactCorImg) {
		this.selectRespReactCorImg = selectRespReactCorImg;
	}
	
	public List<RespReactCorImg> getListRespReactCorImg() {
		return listRespReactCorImg;
	}

	public void setListRespReactCorImg(List<RespReactCorImg> listRespReactCorImg) {
		this.listRespReactCorImg = listRespReactCorImg;
	}

	public List<AnotacionesCorImg> getListAnotacionesCorImg() {
		return listAnotacionesCorImg;
	}

	public void setListAnotacionesCorImg(List<AnotacionesCorImg> listAnotacionesCorImg) {
		this.listAnotacionesCorImg = listAnotacionesCorImg;
	}

	public String getTextoExplicacion() {
		return textoExplicacion;
	}
	public void setTextoExplicacion(String textoExplicacion) {
		this.textoExplicacion = textoExplicacion;
	}

	public String getIndicateImageResult() {
		return indicateImageResult;
	}

	public void setIndicateImageResult(String indicateImageResult) {
		this.indicateImageResult = indicateImageResult;
	}

	public List<MrqsCorrelacionColumnasDto> getListMrqsCorrelacionColumnasDto() {
		return listMrqsCorrelacionColumnasDto;
	}

	public void setListMrqsCorrelacionColumnasDto(List<MrqsCorrelacionColumnasDto> listMrqsCorrelacionColumnasDto) {
		this.listMrqsCorrelacionColumnasDto = listMrqsCorrelacionColumnasDto;
	}

	public List<MrqsCorrelacionColumnasRespuestasDto> getListMrqsCorrelacionColumnasRespuestasDto() {
		return listMrqsCorrelacionColumnasRespuestasDto;
	}

	public void setListMrqsCorrelacionColumnasRespuestasDto(
			List<MrqsCorrelacionColumnasRespuestasDto> listMrqsCorrelacionColumnasRespuestasDto) {
		this.listMrqsCorrelacionColumnasRespuestasDto = listMrqsCorrelacionColumnasRespuestasDto;
	}

	public boolean isCorrelacionColumnas() {
		return correlacionColumnas;
	}

	public void setCorrelacionColumnas(boolean correlacionColumnas) {
		this.correlacionColumnas = correlacionColumnas;
	}

	public boolean getPanelCorrelacionColumnasResultados() {
		return panelCorrelacionColumnasResultados;
	}

	public void setPanelCorrelacionColumnasResultados(boolean panelCorrelacionColumnasResultados) {
		this.panelCorrelacionColumnasResultados = panelCorrelacionColumnasResultados;
	}

	public List<MrqsCorrelacionColumnaPair> getListMrqsCorrelacionColumnasPrev() {
		return listMrqsCorrelacionColumnasPrev;
	}

	public void setListMrqsCorrelacionColumnasPrev(List<MrqsCorrelacionColumnaPair> listMrqsCorrelacionColumnasPrev) {
		this.listMrqsCorrelacionColumnasPrev = listMrqsCorrelacionColumnasPrev;
	}

	public List<MrqsPreguntasFtaSinonimos> getMrqsListaSinonimos() {
		return mrqsListaSinonimos;
	}

	public void setMrqsListaSinonimos(List<MrqsPreguntasFtaSinonimos> mrqsListaSinonimos) {
		this.mrqsListaSinonimos = mrqsListaSinonimos;
	}

	public Integer getLimiteCaracteres() {
		return limiteCaracteres;
	}

	public void setLimiteCaracteres(Integer limiteCaracteres) {
		this.limiteCaracteres = limiteCaracteres;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(float calificacion) {
		this.calificacion = calificacion;
	}

	public List<SelectItem> getListRespuestasCandImg() {
		return listRespuestasCandImg;
	}

	public void setListRespuestasCandImg(List<SelectItem> listRespuestasCandImg) {
		this.listRespuestasCandImg = listRespuestasCandImg;
	}

	public SelectItem getRespuestaSelect() {
		return respuestaSelect;
	}

	public void setRespuestaSelect(SelectItem respuestaSelect) {
		this.respuestaSelect = respuestaSelect;
	}
	
	
	
}
