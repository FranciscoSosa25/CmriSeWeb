package com.cmrise.ejb.backing.mrq;

import java.util.List;
import java.util.Set;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.mrqs.AnotacionesCorImg;
import com.cmrise.ejb.model.mrqs.MrqsListasPalabras;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.RespCorrectReactCorImg;
import com.cmrise.ejb.model.mrqs.RespReactCorImg;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenes;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;
import com.cmrise.ejb.services.mrqs.MrqsCorrelacionColumnasLocal;
import com.cmrise.ejb.services.mrqs.MrqsListasPalabrasLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaSinonimosLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.jpa.dao.mrqs.MrqsCorrelacionColumnaPair;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.utils.CorrelacionColumnasInsertException;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ManagedBean
@ViewScoped
public class UpdateFTAMrqForm {
	
	private long numeroHdr;
	private long numeroFta; 
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction = new MrqsPreguntasHdrV1();
	private MrqsPreguntasFtaV1 mrqsPreguntasFtaV1ForAction = new MrqsPreguntasFtaV1(); 
	
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private String textoSugerencias; 
    private String textoExplicacion;
	/**********************************************************************
	  Atributos Opcion Multiple
	 **********************************************************************/
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>();
	private MrqsOpcionMultiple mrqsOpcionMultipleForAction = new MrqsOpcionMultiple(); 
	private int idxOM = 0; 
	/*********************************************************************
	 Atributos Listas de Palabras
	 *********************************************************************/
	
	private List<MrqsListasPalabras> listIncWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>();
	private List<MrqsListasPalabras> listCanWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>();
	private List<MrqsListasPalabras> listExcWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>();
	private int idxIncW = 0; 
	private int idxCanW = 0; 
	private int idxExcW = 0; 
	private MrqsListasPalabras mrqsListasPalabrasForAction = new MrqsListasPalabras(); 
	
	/**********************************************************************
	  Atributos Opcion Correlacion
	 **********************************************************************/
	
	private List<MrqsCorrelacionColumnasDto> listMrqsCorrelacionColumnas = new ArrayList<MrqsCorrelacionColumnasDto>();
	private List<MrqsCorrelacionColumnasRespuestasDto> listMrqsCorrelacionRespuestas = new ArrayList<MrqsCorrelacionColumnasRespuestasDto>();
	private boolean panelCorrelacionColumnas;
	private String respuestaCorrelacionColumnas="Respuesta: ";
	private String textCorrelacionColumnas="Texto) ";
	/************************************************************************
	 * Archivos E Imagenes
	 */
	
	private UploadedFiles presentationFiles;
	private MrqsImagenesGrp presentMrqsImagenesGrp = new MrqsImagenesGrp(); 
	private List<MrqsImagenesGrp> listPresentMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
	
	
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	
	/**** 
	 * Instrucciones
	 */
	
//	private List<SelectItem> selectInstruccionesItems; 
	private List<SelectItem> selectScoringMethodItems; 
	
	private int idxRespuestas = 0; 
	private List<RespReactCorImg> listRespReactCorImg = new ArrayList<RespReactCorImg>(); 
    private int idxRespuestasCorrelacionadas=0; 
	private List<RespCorrectReactCorImg> listRespCorrectReactCorImg = new ArrayList<RespCorrectReactCorImg>(); 
	private List<SelectItem> selectRespReactCorImg = new ArrayList<SelectItem>(); 
	private int idxLabels = 0; 
	private String[] labels = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"}; 
	private Boolean isRequired = true;
	
	private List<MrqsPreguntasFtaSinonimos> mrqsListaSinonimos= new ArrayList<MrqsPreguntasFtaSinonimos>();
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal;
	
	@Inject
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal;
	
	@Inject 
	MrqsOpcionMultipleLocal mrqsOpcionMultipleLocal; 
	
	@Inject 
	MrqsListasPalabrasLocal mrqsListasPalabrasLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	MrqsImagenesGrpLocal mrqsImagenesGrpLocal;
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal; 
	
	@Inject 
	AdmonSubMateriaLocal admonSubMateriaLocal; 
	
	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
	@Inject 
	TablasUtilitariasValoresLocal tablasUtilitariasValoresLocal; 
	
	@Inject 
	MrqsCorrelacionColumnasLocal mrqsCorrelacionColumnasLocal;
	
	@Inject 
	MrqsPreguntasFtaSinonimosLocal mrqsPreguntasFtaSinonimosLocal;
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	@PostConstruct
	public void init() {
		 System.out.println("Entra UpdateFTAMrqForm init()");
		 if(!Utilitarios.DEFAULT_THEME.equals(guestPreferences.getTheme())) {
		 guestPreferences.setTheme(Utilitarios.DEFAULT_THEME);
	     }
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroHdr= session.getAttribute("NumeroHdrSV");
	     if(null!=obNumeroHdr) {
	    	 if(obNumeroHdr instanceof Long) {
	    		 long numeroHdr = (Long)obNumeroHdr;
	    		 System.out.println("numeroHdr:"+numeroHdr);
	    		 this.numeroHdr = numeroHdr; 
	    	 }else {
	    		 System.out.println("obNumeroHdr instanceof Long:false");
	    	 }
	     }else {
	    	 System.out.println("(null!=obNumeroHdr:false");
	    	 return;
	     }
	 
	     examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.MRQS); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
	     
	     refreshEntity();
	     
	     onAdmonExamenChange(); 
	     onAdmonMateriaChange(); 
//	     enviromentInstrucciones();
	     environmentScoringMethod(); 
		 System.out.println("Sale UpdateFTAMrqForm init()");
	 }		 
	 
	private void refreshEntity() {
		 System.out.println("Entra UpdateFTAMrqForm refreshEntity()");
		 mrqsPreguntasHdrV1ForAction = mrqsPreguntasHdrLocal.findV1ByNumero(this.numeroHdr);
		 if(Utilitarios.RESP_TEXTO_LIBRE.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 this.setLimitedFreeTextAnswer(true);
			 obtenerSinonimos();
		 }else if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 initListMrqsOpcionMultiple(); 
		 }else if(Utilitarios.IMAGEN_INDICADA.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 this.setIndicateImage(true);
		 }else if(Utilitarios.IMAGEN_ANOTADA.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 this.setAnnotatedImage(true);
			 
			 agregarRespReact(); 
		 }else if(Utilitarios.CORRELACION_COLUMNA.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 setPanelCorrelacionColumnas(true);
			 obtenerColumnasGuardadas();
		 }
		 
		 long lNumeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
		 
		 if(0l!=lNumeroFta) {
		   /** CONSULTA INFORMACION **/
			this.setNumeroFta(lNumeroFta);
			mrqsPreguntasFtaV1ForAction.setNumero(lNumeroFta);
			mrqsPreguntasFtaV1ForAction = mrqsPreguntasFtaLocal.findObjModByNumeroFta(lNumeroFta
					                                                                 ,mrqsPreguntasHdrV1ForAction.getTipoPregunta()
					                                                                 );
			
			List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto = mrqsOpcionMultipleLocal.findByNumeroFta(mrqsPreguntasFtaV1ForAction.getNumero());
			System.out.println("listMrqsOpcionMultipleDto.size():"+listMrqsOpcionMultipleDto.size());
			if(listMrqsOpcionMultipleDto.size()>0) {
				listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>();
			}
			
			
			for(MrqsOpcionMultipleDto mrqsOpcionMultipleDto:listMrqsOpcionMultipleDto) {
				 MrqsOpcionMultiple mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
				 mrqsOpcionMultiple.setEstatus(mrqsOpcionMultipleDto.isEstatus());
	        	 mrqsOpcionMultiple.setNumero(mrqsOpcionMultipleDto.getNumero());
	        	 mrqsOpcionMultiple.setNumeroFta(mrqsOpcionMultipleDto.getNumeroFta());
	        	 mrqsOpcionMultiple.setTextoExplicacion(mrqsOpcionMultipleDto.getTextoExplicacion());
	        	 
	       
	        	 System.out.println("mrqsOpcionMultipleDto.getTextoRespuesta():"+mrqsOpcionMultipleDto.getTextoRespuesta());
	        	 mrqsOpcionMultiple.setTextoRespuesta(mrqsOpcionMultipleDto.getTextoRespuesta());
	        	 mrqsOpcionMultiple.setNumeroLinea(mrqsOpcionMultipleDto.getNumeroLinea());
	        	 listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
			}
			
			List<MrqsListasPalabrasDto> listMrqsListasPalabrasDto = null; 
			listMrqsListasPalabrasDto = mrqsListasPalabrasLocal.findByFta(lNumeroFta, Utilitarios.INCLUDED_WORDS); 
			if(null!=listMrqsListasPalabrasDto) {
				listIncWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>();
				for(MrqsListasPalabrasDto mrqsListasPalabrasDto : listMrqsListasPalabrasDto) {
					MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 	
					mrqsListasPalabras.setNumero(mrqsListasPalabrasDto.getNumero());
					mrqsListasPalabras.setPalabra(mrqsListasPalabrasDto.getPalabra());
					mrqsListasPalabras.setSinonimos(mrqsListasPalabrasDto.getSinonimos());
					mrqsListasPalabras.setTipoRegistro(mrqsListasPalabrasDto.getTipoRegistro());
					listIncWmrqsListasPalabras.add(mrqsListasPalabras); 
				}
			}
			listMrqsListasPalabrasDto = mrqsListasPalabrasLocal.findByFta(lNumeroFta, Utilitarios.CAN_WORDS);
            if(null!=listMrqsListasPalabrasDto) {
            	listCanWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>(); 
               for(MrqsListasPalabrasDto mrqsListasPalabrasDto : listMrqsListasPalabrasDto) {
            	   MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 	
					mrqsListasPalabras.setNumero(mrqsListasPalabrasDto.getNumero());
					mrqsListasPalabras.setPalabra(mrqsListasPalabrasDto.getPalabra());
					mrqsListasPalabras.setSinonimos(mrqsListasPalabrasDto.getSinonimos());
					mrqsListasPalabras.setTipoRegistro(mrqsListasPalabrasDto.getTipoRegistro());
					listCanWmrqsListasPalabras.add(mrqsListasPalabras); 
				}
			}
            listMrqsListasPalabrasDto = mrqsListasPalabrasLocal.findByFta(lNumeroFta, Utilitarios.EXCLUDED_WORDS);
            if(null!=listMrqsListasPalabrasDto) {
            	listExcWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>();
                for(MrqsListasPalabrasDto mrqsListasPalabrasDto : listMrqsListasPalabrasDto) {
                	MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 	
					mrqsListasPalabras.setNumero(mrqsListasPalabrasDto.getNumero());
					mrqsListasPalabras.setPalabra(mrqsListasPalabrasDto.getPalabra());
					mrqsListasPalabras.setSinonimos(mrqsListasPalabrasDto.getSinonimos());
					mrqsListasPalabras.setTipoRegistro(mrqsListasPalabrasDto.getTipoRegistro());
					listExcWmrqsListasPalabras.add(mrqsListasPalabras); 
				}
			}
            
            listPresentMrqsImagenesGrp =  mrqsImagenesGrpLocal.findByFta(lNumeroFta,Utilitarios.INTRODUCCION);
            
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
            if(null!=mrqsPreguntasFtaV1ForAction.getRespuestas()) {
            	listRespReactCorImg = gson.fromJson(mrqsPreguntasFtaV1ForAction.getRespuestas(), collectionType);
            	idxLabels = listRespReactCorImg.size();
            	refreshRespuestas();
            }
            collectionType = new TypeToken<List<RespCorrectReactCorImg>>(){}.getType();
            if(null!=mrqsPreguntasFtaV1ForAction.getCorrelaciones()) {
            	  listRespCorrectReactCorImg = gson.fromJson(mrqsPreguntasFtaV1ForAction.getCorrelaciones(), collectionType); 
            }
            
		 }
		
		 
		 System.out.println("Sale UpdateFTAMrqForm refreshEntity()");
	}

//	private void enviromentInstrucciones() {
//		this.selectInstruccionesItems = new ArrayList<SelectItem>();
//		List<TablasUtilitariasValoresDto> listInstruccionesValores =  tablasUtilitariasValoresLocal.findByTipoTabla("INSTRUCCIONES",mrqsPreguntasHdrV1ForAction.getTipoPregunta());  
//		Iterator<TablasUtilitariasValoresDto> iterInstruccionesValores = listInstruccionesValores.iterator(); 
//		while(iterInstruccionesValores.hasNext()) {
//			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterInstruccionesValores.next();
//			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getDescripcion()); 
//			this.selectInstruccionesItems.add(selectItem); 
//		}
//	}
//	
//	public List<SelectItem> getSelectInstruccionesItems(){
//		
//		return this.selectInstruccionesItems; 
//	}
//	
	private void environmentScoringMethod() {
		this.selectScoringMethodItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listScoringMethodValores =  tablasUtilitariasValoresLocal.findByTipoTabla("SCORING_METHOD");  
		Iterator<TablasUtilitariasValoresDto> iterScoringMethodValores = listScoringMethodValores.iterator(); 
		if("OPCION_MULTIPLE".equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())||Utilitarios.CORRELACION_COLUMNA.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			while(iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if("WRONG_CORRECT".equals(tablasUtilitariasValoresDto.getCodigoTabla())
				  ||"PROP_SCORING".equals(tablasUtilitariasValoresDto.getCodigoTabla())
					) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
					this.selectScoringMethodItems.add(selectItem); 	
				  }
			}	
		}
		
		if(Utilitarios.RESP_TEXTO_LIBRE.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			while(iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if("WRONG_CORRECT".equals(tablasUtilitariasValoresDto.getCodigoTabla())
				 	) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
					this.selectScoringMethodItems.add(selectItem); 	
				  }
			}	
		}
		
		if(Utilitarios.IMAGEN_INDICADA.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			while(iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if("WRONG_CORRECT".equals(tablasUtilitariasValoresDto.getCodigoTabla())
				 	) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
					this.selectScoringMethodItems.add(selectItem); 	
				  }
			}	
		}
		
		if(Utilitarios.IMAGEN_ANOTADA.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			while(iterScoringMethodValores.hasNext()) {
				TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterScoringMethodValores.next();
				if(/*"WRONG_CORRECT".equals(tablasUtilitariasValoresDto.getCodigoTabla())
				  ||*/"PROP_SCORING".equals(tablasUtilitariasValoresDto.getCodigoTabla())
					) {
					SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
					this.selectScoringMethodItems.add(selectItem); 	
				  }
			}	
		}
		
	}
	
	public List<SelectItem> getSelectScoringMethodItems(){
		return this.selectScoringMethodItems; 
	}
	
	private void initListMrqsOpcionMultiple() {
		listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
		int lineNumber = 1; 
		MrqsOpcionMultiple mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
		mrqsOpcionMultiple.setNumeroLinea(lineNumber);
		listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
		lineNumber = lineNumber+1; 
		mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
		mrqsOpcionMultiple.setNumeroLinea(lineNumber);
		listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
		lineNumber = lineNumber+1; 
		mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
		mrqsOpcionMultiple.setNumeroLinea(lineNumber);
		listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
		lineNumber = lineNumber+1; 
		mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
		mrqsOpcionMultiple.setNumeroLinea(lineNumber);
		listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
		lineNumber = lineNumber+1; 
		
	}

	
	public void onAdmonExamenChange() {
		System.out.println("mrqsPreguntasHdrV1ForAction.getAdmonExamen():"+mrqsPreguntasHdrV1ForAction.getAdmonExamen());
		if(0!=mrqsPreguntasHdrV1ForAction.getAdmonExamen()) {
			materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(mrqsPreguntasHdrV1ForAction.getAdmonExamen()); 
			selectMateriasHdr = new ArrayList<SelectItem>();  
			for(AdmonMateriaHdr i:materiasHdr) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectMateriasHdr.add(selectItem); 
			}
		}
	}
	
	public void onAdmonMateriaChange() {
		System.out.println("mrqsPreguntasHdrV1ForAction.getAdmonMateria():"+mrqsPreguntasHdrV1ForAction.getAdmonMateria());
		if(0!=mrqsPreguntasHdrV1ForAction.getAdmonMateria()) {
			subMaterias = admonSubMateriaLocal.findByNumeroMateria(mrqsPreguntasHdrV1ForAction.getAdmonMateria()); 
			selectSubMaterias = new ArrayList<SelectItem>(); 
			for(AdmonSubMateria i:subMaterias) {
				System.out.println("*");
				SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				selectSubMaterias.add(selectItem); 
			}
		}
	}
	
	public void addOpcionMultiple() {
		MrqsOpcionMultiple mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
		idxOM++; 
		mrqsOpcionMultiple.setIdxTemp(idxOM);
		listMrqsOpcionMultiple.add(mrqsOpcionMultiple);
	}
	
	public void selectOpcionMultipleForAction(MrqsOpcionMultiple pMrqsOpcionMultiple) {
		mrqsListasPalabrasForAction = new MrqsListasPalabras(); 
		mrqsListasPalabrasForAction.setIdxTemp(pMrqsOpcionMultiple.getIdxTemp());
		mrqsListasPalabrasForAction.setNumero(pMrqsOpcionMultiple.getNumero());
	}
	
	public void deleteOpcionMultiple() {
		boolean updateedDB = false; 
		if(0!=mrqsListasPalabrasForAction.getIdxTemp()) {
			for(MrqsOpcionMultiple mrqsOpcionMultiple:listMrqsOpcionMultiple) {
				if(mrqsListasPalabrasForAction.getIdxTemp()==mrqsOpcionMultiple.getIdxTemp()) {
					listMrqsOpcionMultiple.remove(mrqsOpcionMultiple); 
					break; 
				}
			}
		}else if(0!=mrqsListasPalabrasForAction.getNumero()) {
			mrqsOpcionMultipleLocal.delete(mrqsListasPalabrasForAction.getNumero());
			for(MrqsOpcionMultiple mrqsOpcionMultiple:listMrqsOpcionMultiple) {
				if(mrqsListasPalabrasForAction.getNumero()==mrqsOpcionMultiple.getNumero()) {
					listMrqsOpcionMultiple.remove(mrqsOpcionMultiple); 
					break; 
				}
			}
			updateedDB = true; 
		}
		
	}
	
	public void update() {
		
		System.out.println("Entra UpdateFTAMrqForm update");
		System.out.println("this.getNumeroHdr():"+this.getNumeroHdr());
		long lNumeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
		System.out.println("lNumeroFta:"+lNumeroFta);
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		if(0l==lNumeroFta) {
		   /** INSERTA **/	
			mrqsPreguntasHdrDto.setNumero(this.numeroHdr);
			
			mrqsPreguntasFtaV1ForAction.setNumeroHdr(this.numeroHdr);
			mrqsPreguntasFtaV1ForAction.setCreadoPor(userLogin.getNumeroUsuario());
			mrqsPreguntasFtaV1ForAction.setActualizadoPor(userLogin.getNumeroUsuario());
			mrqsPreguntasFtaV1ForAction.setFechaCreacion(new java.util.Date());
			mrqsPreguntasFtaV1ForAction.setFechaActualizacion(new java.util.Date());
			
		    if(Utilitarios.OPCION_MULTIPLE.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
		     mrqsPreguntasFtaV1ForAction.setRespuestaCorrecta("OPCION_MULTIPLE"); 	
		    }if(Utilitarios.CORRELACION_COLUMNA.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
			     mrqsPreguntasFtaV1ForAction.setRespuestaCorrecta(Utilitarios.CORRELACION_COLUMNA); 	
			}else if(Utilitarios.RESP_TEXTO_LIBRE.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())){
		     mrqsPreguntasFtaV1ForAction.setRespuestaCorrecta(this.mrqsPreguntasFtaV1ForAction.getRespuestaCorrecta());
		    }else if(Utilitarios.IMAGEN_INDICADA.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
		     mrqsPreguntasFtaV1ForAction.setRespuestaCorrecta("Coordenadas Poligonos");
		    if(null==mrqsPreguntasFtaV1ForAction.getNombreImagen()) {
		    	FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Se nececita una","imagen") );
				return; 
		     }else if(null==mrqsPreguntasFtaV1ForAction.getPoligonos()||"".equals(mrqsPreguntasFtaV1ForAction.getPoligonos())) {
		    	 FacesContext context = FacesContext.getCurrentInstance();
				 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Se nececita dibujar un","Poligono") );
				 return; 
		     }
		   }else if(Utilitarios.IMAGEN_ANOTADA.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
			   mrqsPreguntasFtaV1ForAction.setRespuestaCorrecta("Modelo de Relaciones");
			   List<AnotacionesCorImg> listAnotacionesCorImg = new ArrayList<AnotacionesCorImg>(); 
			   for(RespCorrectReactCorImg i:listRespCorrectReactCorImg) {
				   AnotacionesCorImg anotacionesCorImg = new AnotacionesCorImg(); 
				   anotacionesCorImg.setNumero(i.getNumero());
				   anotacionesCorImg.setNodo(i.getNodo());
				   listAnotacionesCorImg.add(anotacionesCorImg); 
			   }
			   Gson gson = new Gson();
			   if(null!=listAnotacionesCorImg&&listAnotacionesCorImg.size()>0) {
				   mrqsPreguntasFtaV1ForAction.setAnotaciones(gson.toJson(listAnotacionesCorImg));
			   }
			   if(null!=listRespReactCorImg&&listRespReactCorImg.size()>0) {
				   mrqsPreguntasFtaV1ForAction.setRespuestas(gson.toJson(listRespReactCorImg));
			   }
			   if(null!=listRespCorrectReactCorImg&&listRespCorrectReactCorImg.size()>0) {
				   mrqsPreguntasFtaV1ForAction.setCorrelaciones(gson.toJson(listRespCorrectReactCorImg));
			   }
			  
		   }
		   
			
			lNumeroFta = mrqsPreguntasFtaLocal.insert(mrqsPreguntasFtaV1ForAction); 
			setNumeroFta(lNumeroFta);
			
			for(MrqsOpcionMultiple mrqsOpcionMultiple:listMrqsOpcionMultiple) {
				MrqsOpcionMultipleDto mrqsOpcionMultipleDto = new MrqsOpcionMultipleDto();
				mrqsOpcionMultipleDto.setEstatus(mrqsOpcionMultiple.isEstatus());
				mrqsOpcionMultipleDto.setTextoRespuesta(mrqsOpcionMultiple.getTextoRespuesta());
				mrqsOpcionMultipleDto.setTextoExplicacion(mrqsOpcionMultiple.getTextoExplicacion());
				mrqsOpcionMultipleDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
				mrqsOpcionMultipleDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
				mrqsOpcionMultipleDto.setNumeroFta(lNumeroFta);
				mrqsOpcionMultipleDto.setNumeroLinea(mrqsOpcionMultiple.getNumeroLinea());
				mrqsOpcionMultipleLocal.insert(mrqsOpcionMultipleDto); 
				mrqsOpcionMultiple.setNumero(mrqsOpcionMultipleDto.getNumero());
			}
			
			for(MrqsListasPalabras mrqsListasPalabras:listIncWmrqsListasPalabras) {
				insertListasPalabras(lNumeroFta,mrqsListasPalabras); 
			}
			
			for(MrqsListasPalabras mrqsListasPalabras:listCanWmrqsListasPalabras) {
				insertListasPalabras(lNumeroFta,mrqsListasPalabras); 
			}
			
			for(MrqsListasPalabras mrqsListasPalabras:listExcWmrqsListasPalabras) {
				insertListasPalabras(lNumeroFta,mrqsListasPalabras); 
			}
			
		}else {
		 
			if(Utilitarios.IMAGEN_INDICADA.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
			if(null==mrqsPreguntasFtaV1ForAction.getNombreImagen()||"".equals(mrqsPreguntasFtaV1ForAction.getNombreImagen())) {
		    	FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Se nececita una","imagen") );
				return; 
		     }else if(null==mrqsPreguntasFtaV1ForAction.getPoligonos()||"".equals(mrqsPreguntasFtaV1ForAction.getPoligonos())) {
		    	 FacesContext context = FacesContext.getCurrentInstance();
				 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Se nececita dibujar un","Poligono") );
				 return; 
		     }
			
			}else if(Utilitarios.IMAGEN_ANOTADA.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
				List<AnotacionesCorImg> listAnotacionesCorImg = new ArrayList<AnotacionesCorImg>(); 
				   for(RespCorrectReactCorImg i:listRespCorrectReactCorImg) {
					   AnotacionesCorImg anotacionesCorImg = new AnotacionesCorImg(); 
					   anotacionesCorImg.setNumero(i.getNumero());
					   anotacionesCorImg.setNodo(i.getNodo());
					   listAnotacionesCorImg.add(anotacionesCorImg); 
				   }
				   Gson gson = new Gson();
				   if(null!=listAnotacionesCorImg&&listAnotacionesCorImg.size()>0) {
					   mrqsPreguntasFtaV1ForAction.setAnotaciones(gson.toJson(listAnotacionesCorImg));
				   }
				   if(null!=listRespReactCorImg&&listRespReactCorImg.size()>0) {
					   mrqsPreguntasFtaV1ForAction.setRespuestas(gson.toJson(listRespReactCorImg));
				   }
				   if(null!=listRespCorrectReactCorImg&&listRespCorrectReactCorImg.size()>0) {
					   mrqsPreguntasFtaV1ForAction.setCorrelaciones(gson.toJson(listRespCorrectReactCorImg));
				   }
			}
			
			mrqsPreguntasFtaLocal.update(lNumeroFta, mrqsPreguntasFtaV1ForAction); 
			
			for(MrqsOpcionMultiple mrqsOpcionMultiple:listMrqsOpcionMultiple) {
				MrqsOpcionMultipleDto mrqsOpcionMultipleDto = new MrqsOpcionMultipleDto();
				if(0!=mrqsOpcionMultiple.getNumero()) {
				mrqsOpcionMultipleDto.setNumero(mrqsOpcionMultiple.getNumero());
				mrqsOpcionMultipleDto.setEstatus(mrqsOpcionMultiple.isEstatus());
				mrqsOpcionMultipleDto.setTextoExplicacion(mrqsOpcionMultiple.getTextoExplicacion());
				mrqsOpcionMultipleDto.setTextoRespuesta(mrqsOpcionMultiple.getTextoRespuesta());
				mrqsOpcionMultipleDto.setNumeroLinea(mrqsOpcionMultiple.getNumeroLinea());
				mrqsOpcionMultipleLocal.update(mrqsOpcionMultiple.getNumero(), mrqsOpcionMultipleDto);
				}else {
					mrqsOpcionMultipleDto.setEstatus(mrqsOpcionMultiple.isEstatus());
					mrqsOpcionMultipleDto.setTextoRespuesta(mrqsOpcionMultiple.getTextoRespuesta());
					mrqsOpcionMultipleDto.setTextoExplicacion(mrqsOpcionMultiple.getTextoExplicacion());
					mrqsOpcionMultipleDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
					mrqsOpcionMultipleDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
					mrqsOpcionMultipleDto.setNumeroFta(this.getNumeroFta());
					mrqsOpcionMultipleDto.setNumeroLinea(mrqsOpcionMultiple.getNumeroLinea());
					mrqsOpcionMultipleLocal.insert(mrqsOpcionMultipleDto); 
				}
			}
			
			for(MrqsListasPalabras mrqsListasPalabras:listIncWmrqsListasPalabras) {
				updateListasPalabras(lNumeroFta,mrqsListasPalabras); 
			}
			
			for(MrqsListasPalabras mrqsListasPalabras:listCanWmrqsListasPalabras) {
				updateListasPalabras(lNumeroFta,mrqsListasPalabras); 
			}
			
			for(MrqsListasPalabras mrqsListasPalabras:listExcWmrqsListasPalabras) {
				updateListasPalabras(lNumeroFta,mrqsListasPalabras); 
			}
			
			
			for(MrqsImagenesGrp mrqsImagenesGrp:listPresentMrqsImagenesGrp) {
				mrqsImagenesGrp.setTipo(Utilitarios.MRQS);
				mrqsImagenesGrp.setSeccion(Utilitarios.INTRODUCCION);
				updateImagenesGrp(lNumeroFta,mrqsImagenesGrp);   
			 }
			
		}
		
		mrqsPreguntasHdrDto.setEstatus(this.getMrqsPreguntasHdrV1ForAction().getEstatus());
		mrqsPreguntasHdrDto.setAdmonExamen(this.getMrqsPreguntasHdrV1ForAction().getAdmonExamen());
		mrqsPreguntasHdrDto.setAdmonMateria(this.getMrqsPreguntasHdrV1ForAction().getAdmonMateria());
		mrqsPreguntasHdrDto.setAdmonSubmateria(this.getMrqsPreguntasHdrV1ForAction().getAdmonSubmateria());
		mrqsPreguntasHdrDto.setTipoPregunta(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta());
		mrqsPreguntasHdrDto.setDiagnostico(this.getMrqsPreguntasHdrV1ForAction().getDiagnostico());
		mrqsPreguntasHdrDto.setNotas(this.getMrqsPreguntasHdrV1ForAction().getNotas());
		mrqsPreguntasHdrDto.setFechaElaboracion(Utilitarios.utilDateToSqlDate(this.getMrqsPreguntasHdrV1ForAction().getFechaElaboracion()));
		mrqsPreguntasHdrDto.setBibliografia(this.getMrqsPreguntasHdrV1ForAction().getBibliografia());
		mrqsPreguntasHdrLocal.update(this.getNumeroHdr(), mrqsPreguntasHdrDto);
	
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Los cambios se actualizaron:","Correctamente") );
        
		System.out.println("Entra UpdateFTAMrqForm Sale");
		
	}
	private long validarCorrelacionColumnas(long lNumeroFta) {
		if(!Utilitarios.CORRELACION_COLUMNA.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta()))
			return 1;		
		 return insertarCorrelacionColumas(listMrqsCorrelacionColumnas, listMrqsCorrelacionRespuestas,lNumeroFta);
			 
		
	}
	private long validarTextoLibre(long lNumeroFta) {
		if(!Utilitarios.RESP_TEXTO_LIBRE.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta()))
			return 1;		
		 return insertarSinonimos(mrqsListaSinonimos,lNumeroFta);
			 
	}
	private void obtenerColumnasGuardadas() {
		long id=mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(getNumeroHdr());
		listMrqsCorrelacionColumnas = mrqsCorrelacionColumnasLocal.findByFta( id);
		listMrqsCorrelacionRespuestas = mrqsCorrelacionColumnasLocal.findRespuestasCorrectasByFta(id);
		
	}
	private long insertarCorrelacionColumas(List<MrqsCorrelacionColumnasDto> respuestas, List<MrqsCorrelacionColumnasRespuestasDto> preguntas,long lNumeroFta) {
		try {
			return mrqsCorrelacionColumnasLocal.insert(respuestas, preguntas, lNumeroFta);
		} catch (CorrelacionColumnasInsertException e) {
			limpiarMensajes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, CorrelacionColumnasInsertException.ERROR_INSERTAR, null));
			return -1;
		}	
	}	
	private void limpiarMensajes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> it = context.getMessages();
		while ( it.hasNext() ) {
		    it.next();
		    it.remove();
		}
	}
	private void insertListasPalabras(long pNumetoFta
			                         ,MrqsListasPalabras pMrqsListasPalabras) {
		
		MrqsListasPalabrasDto mrqsListasPalabrasDto = new MrqsListasPalabrasDto(); 
		mrqsListasPalabrasDto.setTipoRegistro(pMrqsListasPalabras.getTipoRegistro());
		mrqsListasPalabrasDto.setNumeroFta(pNumetoFta);
		mrqsListasPalabrasDto.setPalabra(pMrqsListasPalabras.getPalabra());
		mrqsListasPalabrasDto.setSinonimos(pMrqsListasPalabras.getSinonimos());
		mrqsListasPalabrasLocal.insert(mrqsListasPalabrasDto); 
		
	}
	
	private void updateListasPalabras(long pNumetoFta
            ,MrqsListasPalabras pMrqsListasPalabras) {
	  if(0!=pMrqsListasPalabras.getNumero()) {
		  MrqsListasPalabrasDto mrqsListasPalabrasDto = new MrqsListasPalabrasDto(); 
		  mrqsListasPalabrasDto.setPalabra(pMrqsListasPalabras.getPalabra());
	      mrqsListasPalabrasDto.setSinonimos(pMrqsListasPalabras.getSinonimos());
	      mrqsListasPalabrasLocal.update(pMrqsListasPalabras.getNumero(), mrqsListasPalabrasDto);
	  }else {
		  insertListasPalabras(pNumetoFta,pMrqsListasPalabras); 
	  }	
	}
	
	private void updateImagenesGrp(long pNumetoFta,MrqsImagenesGrp pMrqsImagenesGrp) {
		
		if(0!=pMrqsImagenesGrp.getNumero()) {
			
		}else {
			insertaImagenesGrp(pNumetoFta,pMrqsImagenesGrp); 
		}
	}
	
	private void insertaImagenesGrp(long pNumetoFta
			                       ,MrqsImagenesGrp pMrqsImagenesGrp) {
		System.out.println("pMrqsImagenesGrp.getNumero():"+pMrqsImagenesGrp.getNumero());
		mrqsImagenesGrpLocal.insert(pNumetoFta,pMrqsImagenesGrp);
		System.out.println("pMrqsImagenesGrp.getNumero():"+pMrqsImagenesGrp.getNumero());
	}
	
	public String cancel() {
		return "Preguntas-ManageNewMrqs"; 
	}
	
	public String saveAndPreview() {
		 update();	
		 validarTextoLibre(getNumeroFta());
		 if( validarCorrelacionColumnas(getNumeroFta())==-1)
		 return "#";
		 getGuestPreferences().setTheme("deep-purple");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     session.setAttribute("mrqNumeroHdrSV", this.getNumeroHdr());
		return "Mrq-Preview"; 
	}
	
	public String duplicate() {
		long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsPreguntasHdrV1ForAction.getNumero());
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = mrqsPreguntasHdrLocal.copyPaste(mrqsPreguntasHdrV1ForAction.getNumero());
		long numeroFtaCopy = mrqsPreguntasFtaLocal.copyPaste(numeroFta, mrqsPreguntasHdrDto);
		mrqsOpcionMultipleLocal.copyPaste(numeroFta, numeroFtaCopy);
		refreshEntity();
		return "Preguntas-ManageNewMrqs"; 
	}
	public String delete() {
		long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsPreguntasHdrV1ForAction.getNumero());
		if(0l!=numeroFta) { 
			if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
				mrqsOpcionMultipleLocal.deleteByNumeroFta(numeroFta);
			}
			mrqsPreguntasFtaLocal.delete(numeroFta);
			mrqsPreguntasHdrLocal.delete(mrqsPreguntasHdrV1ForAction.getNumero());
			refreshEntity();
		}else {
	    	mrqsPreguntasHdrLocal.delete(mrqsPreguntasHdrV1ForAction.getNumero());
			refreshEntity();
		}
		return "Preguntas-ManageNewMrqs"; 
	}
	
	
	public void selectWordForAction(MrqsListasPalabras pMrqsListasPalabras) {
		System.out.println("pMrqsListasPalabras.getIdxTemp():"+pMrqsListasPalabras.getIdxTemp());
		System.out.println("pMrqsListasPalabras.getNumero():"+pMrqsListasPalabras.getNumero());
		mrqsListasPalabrasForAction = new MrqsListasPalabras(); 
		mrqsListasPalabrasForAction.setIdxTemp(pMrqsListasPalabras.getIdxTemp());
		mrqsListasPalabrasForAction.setNumero(pMrqsListasPalabras.getNumero());
		mrqsListasPalabrasForAction.setTipoRegistro(pMrqsListasPalabras.getTipoRegistro());
	}
	
	public void deleteWord() {
		boolean updateedDB = false; 
		System.out.println("mrqsListasPalabrasForAction.getTipoRegistro():"+mrqsListasPalabrasForAction.getTipoRegistro());
		if(Utilitarios.INCLUDED_WORDS.equals(mrqsListasPalabrasForAction.getTipoRegistro())) {
			if(mrqsListasPalabrasForAction.getIdxTemp()!=0) {
				for(MrqsListasPalabras mrqsListasPalabras:listIncWmrqsListasPalabras) {
					if(mrqsListasPalabrasForAction.getIdxTemp()==mrqsListasPalabras.getIdxTemp()) {
						listIncWmrqsListasPalabras.remove(mrqsListasPalabras); 
						break; 
					}
				}
			}else if(mrqsListasPalabrasForAction.getNumero()!=0) {
				mrqsListasPalabrasLocal.delete(mrqsListasPalabrasForAction.getNumero());
				updateedDB = true; 
			}
			
		}else if(Utilitarios.CAN_WORDS.equals(mrqsListasPalabrasForAction.getTipoRegistro())) {
			
			if(mrqsListasPalabrasForAction.getIdxTemp()!=0) {
				for(MrqsListasPalabras mrqsListasPalabras:listCanWmrqsListasPalabras) {
					if(mrqsListasPalabrasForAction.getIdxTemp()==mrqsListasPalabras.getIdxTemp()) {
						listCanWmrqsListasPalabras.remove(mrqsListasPalabras); 
						break; 
					}
				}
			}else if(mrqsListasPalabrasForAction.getNumero()!=0) {
				mrqsListasPalabrasLocal.delete(mrqsListasPalabrasForAction.getNumero());
				updateedDB = true; 
			}
			
		}else if(Utilitarios.EXCLUDED_WORDS.equals(mrqsListasPalabrasForAction.getTipoRegistro())) {
			
			if(mrqsListasPalabrasForAction.getIdxTemp()!=0) {
				for(MrqsListasPalabras mrqsListasPalabras:listExcWmrqsListasPalabras) {
					if(mrqsListasPalabrasForAction.getIdxTemp()==mrqsListasPalabras.getIdxTemp()) {
						listExcWmrqsListasPalabras.remove(mrqsListasPalabras); 
						break; 
					}
				}
			}else if(mrqsListasPalabrasForAction.getNumero()!=0) {
				mrqsListasPalabrasLocal.delete(mrqsListasPalabrasForAction.getNumero());
				updateedDB = true; 
			}
			
			
		}
		
		 if(updateedDB) {
			 long lNumeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
			 if(0l!=lNumeroFta) {
				 refreshWordsEntity(lNumeroFta);
			 }
		 }
		
	}
		 
		 
    public void refreshWordsEntity(long pNumeroFta) {
    
    	List<MrqsListasPalabrasDto> listMrqsListasPalabrasDto = null; 
		listMrqsListasPalabrasDto = mrqsListasPalabrasLocal.findByFta(pNumeroFta, Utilitarios.INCLUDED_WORDS); 
		if(null!=listMrqsListasPalabrasDto) {
			listIncWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>();
			for(MrqsListasPalabrasDto mrqsListasPalabrasDto : listMrqsListasPalabrasDto) {
				MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 	
				mrqsListasPalabras.setNumero(mrqsListasPalabrasDto.getNumero());
				mrqsListasPalabras.setPalabra(mrqsListasPalabrasDto.getPalabra());
				mrqsListasPalabras.setSinonimos(mrqsListasPalabrasDto.getSinonimos());
				mrqsListasPalabras.setTipoRegistro(mrqsListasPalabrasDto.getTipoRegistro());
				listIncWmrqsListasPalabras.add(mrqsListasPalabras); 
			}
		}
		listMrqsListasPalabrasDto = mrqsListasPalabrasLocal.findByFta(pNumeroFta, Utilitarios.CAN_WORDS);
        if(null!=listMrqsListasPalabrasDto) {
        	listCanWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>(); 
           for(MrqsListasPalabrasDto mrqsListasPalabrasDto : listMrqsListasPalabrasDto) {
        	   MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 	
				mrqsListasPalabras.setNumero(mrqsListasPalabrasDto.getNumero());
				mrqsListasPalabras.setPalabra(mrqsListasPalabrasDto.getPalabra());
				mrqsListasPalabras.setSinonimos(mrqsListasPalabrasDto.getSinonimos());
				mrqsListasPalabras.setTipoRegistro(mrqsListasPalabrasDto.getTipoRegistro());
				listCanWmrqsListasPalabras.add(mrqsListasPalabras); 
			}
		}
        listMrqsListasPalabrasDto = mrqsListasPalabrasLocal.findByFta(pNumeroFta, Utilitarios.EXCLUDED_WORDS);
        if(null!=listMrqsListasPalabrasDto) {
        	listExcWmrqsListasPalabras = new ArrayList<MrqsListasPalabras>();
            for(MrqsListasPalabrasDto mrqsListasPalabrasDto : listMrqsListasPalabrasDto) {
            	MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 	
				mrqsListasPalabras.setNumero(mrqsListasPalabrasDto.getNumero());
				mrqsListasPalabras.setPalabra(mrqsListasPalabrasDto.getPalabra());
				mrqsListasPalabras.setSinonimos(mrqsListasPalabrasDto.getSinonimos());
				mrqsListasPalabras.setTipoRegistro(mrqsListasPalabrasDto.getTipoRegistro());
				listExcWmrqsListasPalabras.add(mrqsListasPalabras); 
			}
		}
    		 
    }
	
	public void addIncWord() {
		MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 
		idxIncW++; 
		mrqsListasPalabras.setTipoRegistro(Utilitarios.INCLUDED_WORDS);
		mrqsListasPalabras.setIdxTemp(idxIncW);
		listIncWmrqsListasPalabras.add(mrqsListasPalabras); 
	}
	
	public void addCanWord() {
		MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 
		 idxCanW++; 
	    mrqsListasPalabras.setTipoRegistro(Utilitarios.CAN_WORDS);
	    mrqsListasPalabras.setIdxTemp(idxCanW);
	    listCanWmrqsListasPalabras.add(mrqsListasPalabras); 
	}
	
	public void addExcWord() {
		MrqsListasPalabras mrqsListasPalabras = new MrqsListasPalabras(); 
		idxExcW++; 
		mrqsListasPalabras.setTipoRegistro(Utilitarios.EXCLUDED_WORDS);
		mrqsListasPalabras.setIdxTemp(idxExcW);
		listExcWmrqsListasPalabras.add(mrqsListasPalabras);
	}
	
	
	 public void uploadMultiple() {
		 System.out.println("Entra uploadMultiple");
	        if (this.presentationFiles != null) {
	        	if(this.presentationFiles.getSize()>0) {
	        		MrqsImagenesGrp mrqsPresentaciones = new MrqsImagenesGrp(); 
	        		mrqsPresentaciones.setTituloSuperior(this.presentMrqsImagenesGrp.getTituloSuperior());
	        		mrqsPresentaciones.setTituloInferior(this.presentMrqsImagenesGrp.getTituloInferior());
	                List<MrqsImagenes> listMrqsPresentaciones = new ArrayList<MrqsImagenes>();
	                
	        		for (UploadedFile f : this.presentationFiles.getFiles()) {
		            	
	        			byte[] byteContent = f.getContent(); 
	        			
		            	System.out.println(f.getFileName());
		            	MrqsImagenes presentacionImagen = new MrqsImagenes(); 
		            	
		            	presentacionImagen.setNombreImagen(f.getFileName());
		            	presentacionImagen.setImagenContent(byteContent);
		            	presentacionImagen.setImagenBase64(new String(Base64.getEncoder().encode(byteContent)));
		            	presentacionImagen.setContentType(f.getContentType());
		            	
		        			StreamedContent streamedContent = DefaultStreamedContent.builder()
		        		                                                            .contentType(f.getContentType())
		        		                                                            .stream(() -> {
		        		                                                            	  try(InputStream is = f.getInputStream()) {
		        		                                                      				return is;	
		        			                                                      	     } catch (IOException e) {
		        			                                                      			e.printStackTrace();
		        			                                                      			return null; 
		        			                                                      		} 
		        		                                                              })
		        		                                                             .build()
		        		                                                             ;
		        		if(f.getContentType().contains("image")) {
		        			presentacionImagen.setImagenStreamed(streamedContent);
		        			presentacionImagen.setImage(true);
			        	}else if(f.getContentType().contains("video")) {
			        		presentacionImagen.setVideo(true);
			        		presentacionImagen.setVideoStreamed(streamedContent);
		        		}
		        			
		        		listMrqsPresentaciones.add(presentacionImagen); 
		        		
		                FacesMessage message = new FacesMessage("Aviso", f.getFileName() + " ha sido subido");
		                FacesContext.getCurrentInstance().addMessage(null, message);
		            }
	        		
	        		mrqsPresentaciones.setListMrqsImagenes(listMrqsPresentaciones);
	        		this.listPresentMrqsImagenesGrp.add(mrqsPresentaciones);
	                
	        	}
	           	
	        	
	        }
	        System.out.println("Sale uploadMultiple");
	    }
	 
	/**************************************************************
	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		System.out.println(file.getFileName());
		MrqsPresentaciones mrqsPresentaciones = new MrqsPresentaciones(); 
		mrqsPresentaciones.setNombreImagen(file.getFileName());
		mrqsPresentaciones.setPresentacionBase64(new String(Base64.getEncoder().encode(file.getContent())));
		
			StreamedContent streamedContent = DefaultStreamedContent.builder()
		                                                            .contentType(file.getContentType())
		                                                            .stream(() -> {
		                                                            	  try {
		                                                      				return file.getInputStream();	
			                                                      	     } catch (IOException e) {
			                                                      			e.printStackTrace();
			                                                      			return null; 
			                                                      		} 
		                                                              })
		                                                             .build()
		                                                             ;
		mrqsPresentaciones.setPresentacionStreamed(streamedContent);
			
		listMrqsPresentaciones.add(mrqsPresentaciones); 
		System.out.println("Sale handleFileUpload");
    }
	 * @throws IOException 
    **********************************************/
	
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		FacesMessage msg;
		UploadedFile uploadedFile = event.getFile();
		
		byte[] image = uploadedFile.getContent();
		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(image));
		int width = bi.getWidth();
		int height = bi.getHeight();
		System.out.println("Width: " + width + ", height: " + height);
		
			msg = new FacesMessage("El archivo", event.getFile().getFileName() + " ha sido subido.");
			mrqsPreguntasFtaV1ForAction.setNombreImagen(uploadedFile.getFileName());
			mrqsPreguntasFtaV1ForAction.setContentType(uploadedFile.getContentType());
			mrqsPreguntasFtaV1ForAction.setImagenContent(uploadedFile.getContent());
			mrqsPreguntasFtaV1ForAction.setImagenBase64(new String(Base64.getEncoder().encode(uploadedFile.getContent())));
		
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void agregarRespReact() {
		System.out.println("Entra agregarRespReact");
		RespReactCorImg respReactCorImg = new RespReactCorImg(); 
		respReactCorImg.setNumero(idxRespuestas++);
		listRespReactCorImg.add(respReactCorImg); 
		System.out.println(listRespReactCorImg);
		System.out.println("Sale agregarRespReact");
	}
	
	public void removerRespReact(Object id) {
		System.out.println("entra removerRespReact");
		listRespReactCorImg.remove(id);
		System.out.println(listRespReactCorImg);
		System.out.println("sale removerRespReact");
	}
	
	public void agregarRespCorrelacionadas() {
		System.out.println("Entra agregarRespCorrelacionadas");
		RespCorrectReactCorImg respCorrectReactCorImg = new RespCorrectReactCorImg(); 
		respCorrectReactCorImg.setNumero(idxRespuestasCorrelacionadas++);
		respCorrectReactCorImg.setNodo(" "+labels[idxLabels++]);
		listRespCorrectReactCorImg.add(respCorrectReactCorImg);
		refreshRespuestas(); 
		PrimeFaces.current().ajax().addCallbackParam("nodo", labels[idxLabels-1]);
		System.out.println("Sale agregarRespCorrelacionadas");
	}
	
	public <E> void removerRespCorrelacionadas(Object id) {
		int contador = 0;
		
		this.setIsRequired(false);
		System.out.println("Entra removerRespCorrelacionadas");
		listRespCorrectReactCorImg.remove(id);
		idxLabels--;
		for(RespCorrectReactCorImg i: listRespCorrectReactCorImg) {
			System.out.println("así va el ordern po: "+labels[contador]);
			i.setNodo(" "+labels[contador]);
			contador++;
		}
		refreshRespuestas();
		System.out.println("Sale removerRespCorrelacionadas");
	}
	
	private void refreshRespuestas() {
		selectRespReactCorImg = new ArrayList<SelectItem>(); 
		for(RespReactCorImg i:listRespReactCorImg) {
			SelectItem selectItem = new SelectItem(i.getNumero(),i.getRespuesta()); 
			selectRespReactCorImg.add(selectItem); 
			System.out.println("SELECT ITEM "+selectItem);
		}
	}
	public void agregarRespuestaColumna() {
		listMrqsCorrelacionColumnas.add(new MrqsCorrelacionColumnasDto(respuestaCorrelacionColumnas+listMrqsCorrelacionColumnas.size()));
	}
	public void eliminarRespuestaColumna(MrqsCorrelacionColumnasDto item) {
		if(item.getNumero()!=0)
			deleteItem(item);
		listMrqsCorrelacionColumnas.remove(item);	
		
	}
	private <E> void deleteItem(E item) {		
			try {
				if(item instanceof MrqsCorrelacionColumnasDto)
				mrqsCorrelacionColumnasLocal.deleteColumna((MrqsCorrelacionColumnasDto)item);
				else
				mrqsCorrelacionColumnasLocal.deleteColumna((MrqsCorrelacionColumnasRespuestasDto)item);
			} catch (CorrelacionColumnasInsertException e) {
				limpiarMensajes();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, CorrelacionColumnasInsertException.ERROR_INSERTAR, null));
			}
	}
	private  void deleteItemSinonimos(MrqsPreguntasFtaSinonimos item) {		
		try {
			
			mrqsPreguntasFtaSinonimosLocal.deleteSinonimo(item);
			
		} catch (Exception e) {
			limpiarMensajes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, CorrelacionColumnasInsertException.ERROR_INSERTAR, null));
		}
}
	public void eliminarRespuestaCorrecta(MrqsCorrelacionColumnasRespuestasDto item) {
		if(item.getNumero()!=0)
			deleteItem(item);
		listMrqsCorrelacionRespuestas.remove(item);
	}
	public void agregarRespuestaColumnaDerecha() {
		listMrqsCorrelacionRespuestas.add(new MrqsCorrelacionColumnasRespuestasDto(textCorrelacionColumnas+listMrqsCorrelacionRespuestas.size()));
	}
	public void asignarValor(MrqsCorrelacionColumnasRespuestasDto respuesta) {

		if(validarValoresAsignados(respuesta))
			respuesta.setTexto(null);
			
	
	}	
	public void limpiarRespuestaCorrecta() {
		if(mrqsPreguntasFtaV1ForAction.getRespuestaCorrecta()!=null && mrqsPreguntasFtaV1ForAction.getLimiteCaracteres()!=null)
		mrqsPreguntasFtaV1ForAction.setRespuestaCorrecta(mrqsPreguntasFtaV1ForAction.getRespuestaCorrecta().length()>mrqsPreguntasFtaV1ForAction.getLimiteCaracteres()?
				mrqsPreguntasFtaV1ForAction.getRespuestaCorrecta().substring(0, mrqsPreguntasFtaV1ForAction.getLimiteCaracteres()):
					mrqsPreguntasFtaV1ForAction.getRespuestaCorrecta());
	}
	private boolean validarValoresAsignados(MrqsCorrelacionColumnasRespuestasDto item) {
		
		Iterator<MrqsCorrelacionColumnasRespuestasDto> it=listMrqsCorrelacionRespuestas.iterator();
		while(it.hasNext()) {
			MrqsCorrelacionColumnasRespuestasDto val=it.next();
			if(val.getTexto()!=null && !val.getTexto().isEmpty()&& val.getTexto().equals(item.getTexto())&& !val.equals(item)) {
				return true;
			}
		}
		return false;
	}
	public void eliminarSinonimo(MrqsPreguntasFtaSinonimos item) {
		if(item.getNumero()!=0)
			deleteItemSinonimos(item);
		mrqsListaSinonimos.remove(item);	
		
	}
	public void agregarSinonimo() {
		mrqsListaSinonimos.add(new MrqsPreguntasFtaSinonimos("SINONIMO"));
	}
	private long insertarSinonimos(List<MrqsPreguntasFtaSinonimos> sinonimos,long lNumeroFta) {
		try {
			return mrqsPreguntasFtaSinonimosLocal.insert(sinonimos, lNumeroFta,mrqsPreguntasFtaV1ForAction.getTextoPregunta()!=null?mrqsPreguntasFtaV1ForAction.getTextoPregunta():"Texto pregunta");
		} catch (Exception e) {
			limpiarMensajes();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, CorrelacionColumnasInsertException.ERROR_INSERTAR, null));
			return -1;
		}	
	}	
	private void obtenerSinonimos() {
		long id=mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(getNumeroHdr());
		mrqsListaSinonimos = mrqsPreguntasFtaSinonimosLocal.findByFta( id);
		
		
	}
	
	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1ForAction() {
		return mrqsPreguntasHdrV1ForAction;
	}

	public void setMrqsPreguntasHdrV1ForAction(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction) {
		this.mrqsPreguntasHdrV1ForAction = mrqsPreguntasHdrV1ForAction;
	}

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}


	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean pMultipleChoice) {
		if(pMultipleChoice) {
		this.setAnnotatedImage(false);
		this.setIndicateImage(false);
		this.setLimitedFreeTextAnswer(false);
		}
		this.multipleChoice = pMultipleChoice;
	}

	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}

	public void setLimitedFreeTextAnswer(boolean pLimitedFreeTextAnswer) {
		if(pLimitedFreeTextAnswer) {
			this.setMultipleChoice(false);
			this.setAnnotatedImage(false);
			this.setIndicateImage(false);
		}
		this.limitedFreeTextAnswer = pLimitedFreeTextAnswer;
	}

	public boolean isIndicateImage() {
		return indicateImage;
	}

	public void setIndicateImage(boolean pIndicateImage) {
		if(pIndicateImage) {
			this.setMultipleChoice(false);
			this.setAnnotatedImage(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.indicateImage = pIndicateImage;
	}

	public boolean isAnnotatedImage() {
		return annotatedImage;
	}

	public void setAnnotatedImage(boolean pAnnotatedImage) {
		if(pAnnotatedImage) {
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.annotatedImage = pAnnotatedImage;
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

	public long getNumeroFta() {
		return numeroFta;
	}

	public void setNumeroFta(long numeroFta) {
		this.numeroFta = numeroFta;
	}


	public MrqsOpcionMultiple getMrqsOpcionMultipleForAction() {
		return mrqsOpcionMultipleForAction;
	}

	public void setMrqsOpcionMultipleForAction(MrqsOpcionMultiple mrqsOpcionMultipleForAction) {
		this.mrqsOpcionMultipleForAction = mrqsOpcionMultipleForAction;
	}

	public List<MrqsListasPalabras> getListIncWmrqsListasPalabras() {
		return listIncWmrqsListasPalabras;
	}

	public void setListIncWmrqsListasPalabras(List<MrqsListasPalabras> listIncWmrqsListasPalabras) {
		this.listIncWmrqsListasPalabras = listIncWmrqsListasPalabras;
	}

	public List<MrqsListasPalabras> getListCanWmrqsListasPalabras() {
		return listCanWmrqsListasPalabras;
	}

	public void setListCanWmrqsListasPalabras(List<MrqsListasPalabras> listCanWmrqsListasPalabras) {
		this.listCanWmrqsListasPalabras = listCanWmrqsListasPalabras;
	}

	public List<MrqsListasPalabras> getListExcWmrqsListasPalabras() {
		return listExcWmrqsListasPalabras;
	}

	public void setListExcWmrqsListasPalabras(List<MrqsListasPalabras> listExcWmrqsListasPalabras) {
		this.listExcWmrqsListasPalabras = listExcWmrqsListasPalabras;
	}

	public int getIdxIncW() {
		return idxIncW;
	}

	public void setIdxIncW(int idxIncW) {
		this.idxIncW = idxIncW;
	}

	public int getIdxCanW() {
		return idxCanW;
	}

	public void setIdxCanW(int idxCanW) {
		this.idxCanW = idxCanW;
	}

	public int getIdxExcW() {
		return idxExcW;
	}

	public void setIdxExcW(int idxExcW) {
		this.idxExcW = idxExcW;
	}

	public MrqsListasPalabras getMrqsListasPalabrasForAction() {
		return mrqsListasPalabrasForAction;
	}

	public void setMrqsListasPalabrasForAction(MrqsListasPalabras mrqsListasPalabrasForAction) {
		this.mrqsListasPalabrasForAction = mrqsListasPalabrasForAction;
	}

	public int getIdxOM() {
		return idxOM;
	}

	public void setIdxOM(int idxOM) {
		this.idxOM = idxOM;
	}

	public UploadedFiles getPresentationFiles() {
		return presentationFiles;
	}

	public void setPresentationFiles(UploadedFiles presentationFiles) {
		System.out.println("Entra setPresentationFiles");
		this.presentationFiles = presentationFiles;
		System.out.println("Sale setPresentationFiles");
	}

	public MrqsImagenesGrp getPresentMrqsImagenesGrp() {
		return presentMrqsImagenesGrp;
	}

	public void setPresentMrqsImagenesGrp(MrqsImagenesGrp presentMrqsImagenesGrp) {
		this.presentMrqsImagenesGrp = presentMrqsImagenesGrp;
	}

	public List<MrqsImagenesGrp> getListPresentMrqsImagenesGrp() {
		return listPresentMrqsImagenesGrp;
	}

	public void setListPresentMrqsImagenesGrp(List<MrqsImagenesGrp> listPresentMrqsImagenesGrp) {
		this.listPresentMrqsImagenesGrp = listPresentMrqsImagenesGrp;
	}

	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	}

	public List<AdmonMateriaHdr> getMateriasHdr() {
		return materiasHdr;
	}

	public void setMateriasHdr(List<AdmonMateriaHdr> materiasHdr) {
		this.materiasHdr = materiasHdr;
	}

	public List<AdmonSubMateria> getSubMaterias() {
		return subMaterias;
	}

	public void setSubMaterias(List<AdmonSubMateria> subMaterias) {
		this.subMaterias = subMaterias;
	}

	public List<SelectItem> getSelectExamenesHdr() {
		return selectExamenesHdr;
	}

	public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
		this.selectExamenesHdr = selectExamenesHdr;
	}

	public List<SelectItem> getSelectMateriasHdr() {
		return selectMateriasHdr;
	}

	public void setSelectMateriasHdr(List<SelectItem> selectMateriasHdr) {
		this.selectMateriasHdr = selectMateriasHdr;
	}

	public List<SelectItem> getSelectSubMaterias() {
		return selectSubMaterias;
	}

	public void setSelectSubMaterias(List<SelectItem> selectSubMaterias) {
		this.selectSubMaterias = selectSubMaterias;
	}

	public MrqsPreguntasFtaV1 getMrqsPreguntasFtaV1ForAction() {
		return mrqsPreguntasFtaV1ForAction;
	}

	public void setMrqsPreguntasFtaV1ForAction(MrqsPreguntasFtaV1 mrqsPreguntasFtaV1ForAction) {
		this.mrqsPreguntasFtaV1ForAction = mrqsPreguntasFtaV1ForAction;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public List<RespCorrectReactCorImg> getListRespCorrectReactCorImg() {
		return listRespCorrectReactCorImg;
	}

	public void setListRespCorrectReactCorImg(List<RespCorrectReactCorImg> listRespCorrectReactCorImg) {
		this.listRespCorrectReactCorImg = listRespCorrectReactCorImg;
	}

	public List<RespReactCorImg> getListRespReactCorImg() {
		return listRespReactCorImg;
	}

	public void setListRespReactCorImg(List<RespReactCorImg> listRespReactCorImg) {
		this.listRespReactCorImg = listRespReactCorImg;
	}

	public List<SelectItem> getSelectRespReactCorImg() {
		return selectRespReactCorImg;
	}

	public void setSelectRespReactCorImg(List<SelectItem> selectRespReactCorImg) {
		this.selectRespReactCorImg = selectRespReactCorImg;
	}
	public String getTextoSugerencias() {
		return textoSugerencias;
	}
	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}
	public String getTextoExplicacion() {
		return textoExplicacion;
	}
	public void setTextoExplicacion(String textoExplicacion) {
		this.textoExplicacion = textoExplicacion;
	}
	public List<MrqsCorrelacionColumnasDto> getListMrqsCorrelacionColumnas() {
		return listMrqsCorrelacionColumnas;
	}

	public void setListMrqsCorrelacionColumnas(List<MrqsCorrelacionColumnasDto> listMrqsCorrelacionColumnas) {
		this.listMrqsCorrelacionColumnas = listMrqsCorrelacionColumnas;
	}

	public List<MrqsCorrelacionColumnasRespuestasDto> getListMrqsCorrelacionRespuestas() {
		return listMrqsCorrelacionRespuestas;
	}

	public void setListMrqsCorrelacionRespuestas(List<MrqsCorrelacionColumnasRespuestasDto> listMrqsCorrelacionRespuestas) {
		this.listMrqsCorrelacionRespuestas = listMrqsCorrelacionRespuestas;
	}

	public boolean isPanelCorrelacionColumnas() {
		return panelCorrelacionColumnas;
	}

	public void setPanelCorrelacionColumnas(boolean panelCorrelacionColumnas) {
		this.panelCorrelacionColumnas = panelCorrelacionColumnas;
	}


	public List<MrqsPreguntasFtaSinonimos> getMrqsListaSinonimos() {
		return mrqsListaSinonimos;
	}

	public void setMrqsListaSinonimos(List<MrqsPreguntasFtaSinonimos> mrqsListaSinonimos) {
		this.mrqsListaSinonimos = mrqsListaSinonimos;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;

	}

	
	
	
}
