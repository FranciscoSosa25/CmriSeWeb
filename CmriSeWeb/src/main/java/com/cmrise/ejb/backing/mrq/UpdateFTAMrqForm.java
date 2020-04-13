package com.cmrise.ejb.backing.mrq;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.mrqs.MrqsListasPalabras;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenes;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.mrqs.MrqsListasPalabrasLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.img.MrqsImagenesGrpDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateFTAMrqForm {
	
	private long numeroHdr;
	private long numeroFta; 
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction = new MrqsPreguntasHdrV1();
	
	private String tituloPregunta;
	private String metodoPuntuacion;
	private String respuestaCorrecta; 
	private String textoPregunta; 
	private String valorPuntuacion; 
    private String textoSugerencias; 
	
    private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
    
	private boolean singleAnswerMode;
	private boolean suffleAnswerOrder; 
    
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
	
	/************************************************************************
	 * Archivos E Imagenes
	 */
	
	private UploadedFiles presentationFiles;
	private MrqsImagenesGrp presentMrqsImagenesGrp = new MrqsImagenesGrp(); 
	private List<MrqsImagenesGrp> listPresentMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
	
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
	
	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
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
	
	
	
	@PostConstruct
	public void init() {
		 System.out.println("Entra UpdateFTAMrqForm init()");
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
	 
	     refreshEntity();
		 System.out.println("Sale UpdateFTAMrqForm init()");
	 }		 
	 
	private void refreshEntity() {
		 System.out.println("Entra UpdateFTAMrqForm refreshEntity()");
		 MrqsPreguntasHdrV1Dto mrqsPreguntasHdrV1Dto = mrqsPreguntasHdrLocal.findByNumero(this.numeroHdr);
		 if(Utilitarios.RESP_TEXTO_LIBRE.equals(mrqsPreguntasHdrV1Dto.getTipoPregunta())) {
			 this.setLimitedFreeTextAnswer(true);
		 }else if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV1Dto.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 initListMrqsOpcionMultiple(); 
		 }
		 mrqsPreguntasHdrV1ForAction.setNumero(mrqsPreguntasHdrV1Dto.getNumero());
		 mrqsPreguntasHdrV1ForAction.setEstatus(mrqsPreguntasHdrV1Dto.getEstatus());
		 mrqsPreguntasHdrV1ForAction.setNombre(mrqsPreguntasHdrV1Dto.getNombre());
		 mrqsPreguntasHdrV1ForAction.setTitulo(mrqsPreguntasHdrV1Dto.getTitulo());
		 mrqsPreguntasHdrV1ForAction.setTipoPregunta(mrqsPreguntasHdrV1Dto.getTipoPregunta());
		 mrqsPreguntasHdrV1ForAction.setTemaPregunta(mrqsPreguntasHdrV1Dto.getTemaPregunta());
		 mrqsPreguntasHdrV1ForAction.setEtiquetas(mrqsPreguntasHdrV1Dto.getEtiquetas());
		 mrqsPreguntasHdrV1ForAction.setComentarios(mrqsPreguntasHdrV1Dto.getComentarios());
		 
		 long lNumeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
		 if(0l!=lNumeroFta) {
		   /** CONSULTA INFORMACION **/
			this.setNumeroFta(lNumeroFta);
			MrqsPreguntasFtaDto mrqsPreguntasFtaDto = mrqsPreguntasFtaLocal.findDtoByNumeroFta(lNumeroFta);
			this.setTituloPregunta(mrqsPreguntasFtaDto.getTitulo());
			this.setTextoPregunta(mrqsPreguntasFtaDto.getTextoPregunta());
			this.setTextoSugerencias(mrqsPreguntasFtaDto.getTextoSugerencias());
			this.setRespuestaCorrecta(mrqsPreguntasFtaDto.getRespuestaCorrecta());
			this.setSingleAnswerMode(mrqsPreguntasFtaDto.isSingleAnswerMode());
			this.setSuffleAnswerOrder(mrqsPreguntasFtaDto.isSuffleAnswerOrder());
			this.setMetodoPuntuacion(mrqsPreguntasFtaDto.getMetodoPuntuacion());
			this.setValorPuntuacion(mrqsPreguntasFtaDto.getValorPuntuacion());
			List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto = mrqsOpcionMultipleLocal.findByNumeroFta(mrqsPreguntasFtaDto.getNumero());
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
            
		 }
		
		 
		 System.out.println("Sale UpdateFTAMrqForm refreshEntity()");
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

	public void update() {
		
		System.out.println("Entra UpdateFTAMrqForm update");
		System.out.println("this.getNumeroHdr():"+this.getNumeroHdr());
		long lNumeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
		System.out.println("lNumeroFta:"+lNumeroFta);
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = new MrqsPreguntasFtaDto();
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		if(0l==lNumeroFta) {
		   /** INSERTA **/	
			mrqsPreguntasHdrDto.setNumero(this.numeroHdr);
			mrqsPreguntasFtaDto.setMrqsPreguntasHdr2(mrqsPreguntasHdrDto);
			mrqsPreguntasFtaDto.setTitulo(this.tituloPregunta);
			mrqsPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			mrqsPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			System.out.println("this.metodoPuntuacion:"+this.metodoPuntuacion);
			mrqsPreguntasFtaDto.setMetodoPuntuacion(this.getMetodoPuntuacion());
		    if(Utilitarios.OPCION_MULTIPLE.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
		    mrqsPreguntasFtaDto.setRespuestaCorrecta("OPCION_MULTIPLE"); 	
		    }else {
			mrqsPreguntasFtaDto.setRespuestaCorrecta(this.respuestaCorrecta);
		    }
			
			mrqsPreguntasFtaDto.setTextoPregunta(this.textoPregunta);
			mrqsPreguntasFtaDto.setValorPuntuacion(this.valorPuntuacion);
			mrqsPreguntasFtaDto.setTextoSugerencias(this.textoSugerencias);
			mrqsPreguntasFtaDto.setSingleAnswerMode(this.isSingleAnswerMode());
			mrqsPreguntasFtaDto.setSuffleAnswerOrder(this.isSuffleAnswerOrder());
			mrqsPreguntasFtaDto.setValorPuntuacion(this.valorPuntuacion);
			lNumeroFta = mrqsPreguntasFtaLocal.insert(mrqsPreguntasFtaDto);
			
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
		  /** ACTUALIZA **/
			mrqsPreguntasHdrDto.setNumero(this.numeroHdr);
			mrqsPreguntasFtaDto.setMrqsPreguntasHdr2(mrqsPreguntasHdrDto);
			mrqsPreguntasFtaDto.setTitulo(this.tituloPregunta);
			mrqsPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			mrqsPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			System.out.println("this.metodoPuntuacion:"+this.metodoPuntuacion);
			mrqsPreguntasFtaDto.setMetodoPuntuacion(this.getMetodoPuntuacion());
			if(Utilitarios.OPCION_MULTIPLE.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
			  mrqsPreguntasFtaDto.setRespuestaCorrecta("OPCION_MULTIPLE"); 	
			}else {
			mrqsPreguntasFtaDto.setRespuestaCorrecta(this.respuestaCorrecta);
			}
			mrqsPreguntasFtaDto.setTextoPregunta(this.textoPregunta);
			mrqsPreguntasFtaDto.setValorPuntuacion(this.valorPuntuacion);
			mrqsPreguntasFtaDto.setTextoSugerencias(this.textoSugerencias);
			mrqsPreguntasFtaDto.setSingleAnswerMode(this.isSingleAnswerMode());
			mrqsPreguntasFtaDto.setSuffleAnswerOrder(this.isSuffleAnswerOrder());
			mrqsPreguntasFtaLocal.update(lNumeroFta, mrqsPreguntasFtaDto);
			
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
		mrqsPreguntasHdrDto.setNombre(this.getMrqsPreguntasHdrV1ForAction().getNombre());
		mrqsPreguntasHdrDto.setTitulo(this.getMrqsPreguntasHdrV1ForAction().getTitulo());
		mrqsPreguntasHdrDto.setTipoPregunta(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta());
		mrqsPreguntasHdrDto.setTemaPregunta(this.getMrqsPreguntasHdrV1ForAction().getTemaPregunta());
		mrqsPreguntasHdrDto.setEtiquetas(this.getMrqsPreguntasHdrV1ForAction().getEtiquetas());
		mrqsPreguntasHdrDto.setComentarios(this.getMrqsPreguntasHdrV1ForAction().getComentarios());
		mrqsPreguntasHdrLocal.update(this.getNumeroHdr(), mrqsPreguntasHdrDto);
	
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Los cambios se actualizaron:","Correctamente") );
        
		System.out.println("Entra UpdateFTAMrqForm Sale");
		
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
		mrqsImagenesGrpLocal.insert(pNumetoFta,pMrqsImagenesGrp);
	}
	
	public String cancel() {
		return "Preguntas-ManageNewMrqs"; 
	}
	
	public String saveAndPreview() {
		 update();
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
		        		presentacionImagen.setImagenStreamed(streamedContent);
		        			
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
    **********************************************/
	
	
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

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public String getMetodoPuntuacion() {
		return metodoPuntuacion;
	}

	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getValorPuntuacion() {
		return valorPuntuacion;
	}

	public void setValorPuntuacion(String valorPuntuacion) {
		this.valorPuntuacion = valorPuntuacion;
	}

	public String getTextoSugerencias() {
		return textoSugerencias;
	}

	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
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

}
