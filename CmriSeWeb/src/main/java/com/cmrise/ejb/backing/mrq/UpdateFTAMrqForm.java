package com.cmrise.ejb.backing.mrq;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.cmrise.ejb.model.mrqs.MrqsListasPalabras;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasFtaV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenes;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;
import com.cmrise.ejb.services.mrqs.MrqsListasPalabrasLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;
import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateFTAMrqForm {
	private String textoRespuesta;
	private long numeroHdr;
	private long numeroFta; 
	private String textoSugerencias;
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction = new MrqsPreguntasHdrV1();
	private MrqsPreguntasFtaV1 mrqsPreguntasFtaV1ForAction = new MrqsPreguntasFtaV1(); 
	
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
    
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
	
	
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	
	/**** 
	 * Instrucciones
	 */
	
	private List<SelectItem> selectInstruccionesItems; 
	private List<SelectItem> selectScoringMethodItems; 
		
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
	     enviromentInstrucciones();
	     environmentScoringMethod(); 
		 System.out.println("Sale UpdateFTAMrqForm init()");
	 }		 
	 
	private void refreshEntity() {
		 System.out.println("Entra UpdateFTAMrqForm refreshEntity()");
		 mrqsPreguntasHdrV1ForAction = mrqsPreguntasHdrLocal.findV1ByNumero(this.numeroHdr);
		 if(Utilitarios.RESP_TEXTO_LIBRE.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 this.setLimitedFreeTextAnswer(true);
		 }else if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 initListMrqsOpcionMultiple(); 
		 }else if(Utilitarios.IMAGEN_INDICADA.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
			 this.setIndicateImage(true);
		 }
		 
		 long lNumeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
		 
		 if(0l!=lNumeroFta) {
		   /** CONSULTA INFORMACION **/
			this.setNumeroFta(lNumeroFta);
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

	private void enviromentInstrucciones() {
		this.selectInstruccionesItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listInstruccionesValores =  tablasUtilitariasValoresLocal.findByTipoTabla("INSTRUCCIONES",mrqsPreguntasHdrV1ForAction.getTipoPregunta());  
		Iterator<TablasUtilitariasValoresDto> iterInstruccionesValores = listInstruccionesValores.iterator(); 
		while(iterInstruccionesValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterInstruccionesValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getDescripcion()); 
			this.selectInstruccionesItems.add(selectItem); 
		}
	}
	
	public List<SelectItem> getSelectInstruccionesItems(){
		
		return this.selectInstruccionesItems; 
	}
	
	private void environmentScoringMethod() {
		this.selectScoringMethodItems = new ArrayList<SelectItem>();
		List<TablasUtilitariasValoresDto> listScoringMethodValores =  tablasUtilitariasValoresLocal.findByTipoTabla("SCORING_METHOD");  
		Iterator<TablasUtilitariasValoresDto> iterScoringMethodValores = listScoringMethodValores.iterator(); 
		if("OPCION_MULTIPLE".equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
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
			mrqsPreguntasFtaV1ForAction.setTextoSugerencias(this.getTextoSugerencias());
			
		    if(Utilitarios.OPCION_MULTIPLE.equals(this.getMrqsPreguntasHdrV1ForAction().getTipoPregunta())) {
		     mrqsPreguntasFtaV1ForAction.setRespuestaCorrecta("OPCION_MULTIPLE"); 	
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
		    
		   }
			
			lNumeroFta = mrqsPreguntasFtaLocal.insert(mrqsPreguntasFtaV1ForAction); 
			
			
			for(MrqsOpcionMultiple mrqsOpcionMultiple:listMrqsOpcionMultiple) {
				MrqsOpcionMultipleDto mrqsOpcionMultipleDto = new MrqsOpcionMultipleDto();
				mrqsOpcionMultipleDto.setEstatus(mrqsOpcionMultiple.isEstatus());
				mrqsOpcionMultipleDto.setTextoRespuesta(mrqsOpcionMultiple.getTextoRespuesta());
			//	mrqsOpcionMultipleDto.setTextoSugerencia(mrqsOpcionMultiple.getTextoSugerencia());
				mrqsOpcionMultipleDto.setTextoExplicacion(mrqsOpcionMultiple.getTextoExplicacion());
				//mrqsOpcionMultipleDto.setTextoSugerencia(mrqsOpcionMultiple.getTextoSugerencia());
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
			}
			mrqsPreguntasFtaV1ForAction.setTextoSugerencias(this.getTextoSugerencias());
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
    **********************************************/
	
	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		mrqsPreguntasFtaV1ForAction.setNombreImagen(uploadedFile.getFileName());
		mrqsPreguntasFtaV1ForAction.setContentType(uploadedFile.getContentType());
		mrqsPreguntasFtaV1ForAction.setImagenContent(uploadedFile.getContent());
		mrqsPreguntasFtaV1ForAction.setImagenBase64(new String(Base64.getEncoder().encode(uploadedFile.getContent())));
		
        FacesMessage msg = new FacesMessage("El archivo", event.getFile().getFileName() + " ha sido subido.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
	public String getTextoRespuesta() {
		return textoRespuesta;
	}
	public void setTextoRespuesta(String textoRespuesta) {
		this.textoRespuesta = textoRespuesta;
	}
	public String getTextoSugerencias() {
		return textoSugerencias;
	}
	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}
}
