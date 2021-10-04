package com.cmrise.ejb.backing.candidates.exams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.ejb.model.exams.CcGrupoHdr;
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
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.corecases.CcOpcionMultipleLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.ejb.services.corecases.img.CcImagenesGrpLocal;
import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamSkipV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

@ManagedBean
@ViewScoped
public class CcExamForm {

	private CandExamenesV1 candExamenesV1 =  new CandExamenesV1(); 
	private CandExamRespuestasV1 candExamRespuestasV1 = new CandExamRespuestasV1(); 
	private CcExamenes ccExamen = new CcExamenes(); 
	private CcHdrV1 ccGrupoHdr = new CcHdrV1();
	private MrqsGrupoLines mrqsGrupoLines = new MrqsGrupoLines(); 
	private TreeNode rootCcHdr;
	private TreeNode selectedNode;
	private List<CcHdrV1> listGrupoCcHdr = new ArrayList<CcHdrV1>(); 
	private List<CcPreguntasHdrV1Dto> listCcPreguntas = new ArrayList<CcPreguntasHdrV1Dto>();
	private List<CcPreguntasHdrV1> listCcPreguntas2 = new ArrayList<CcPreguntasHdrV1>();
	private List<CcPreguntasHdrV1> listCcPreguntas1 = new ArrayList<CcPreguntasHdrV1>();
	private CcPreguntasHdrV1 ccPreguntasV1 = new CcPreguntasHdrV1();     
    private List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>();
    private List<CcImagenesGrp> listPresentCcImagenesGrp = new ArrayList<CcImagenesGrp>(); 
	private CandExamRespSkipDto candExamRespSkipDto = new CandExamRespSkipDto();
	List<CandExamSkipV1Dto> lCandExamSkipV1Dto = new ArrayList<CandExamSkipV1Dto>();
	
	private long numeroCcExamen;
	private long numeroCandExamen;
	private long numeroPreguntaFta; 
    private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private Short sSegundos =60;
	private String respuestaCandidato; 
	private String[] respuestasPreguntaCandidato;
	private String timerValue;
	private String strDate;
	private long casosSize;
	private int idxCasos;
	private long hdrGrupoSize;
	private int idxGrupo;
	private boolean nuevaBusqueda = false;
	private boolean flag2;
	private int idxSkip = 0;
	private boolean busquedaSkip = false;
	private int skipMax = 0;
	private boolean showFinalMessage = false;
	private int limiteCaracteres = 50;
	private String tipoPregunta; 
	private String titularPregunta;
	private String titularCasos;
	private String limiteCaracteresString = "";
	
	private CcPreguntasFtaV1 ccPreguntasFtaV1ForRead = new CcPreguntasFtaV1(); 
	private List<RespReactCorImg> listRespReactCorImg = new ArrayList<RespReactCorImg>();
	private List<RespReactCorImg> listRespCandCorImg = new ArrayList<RespReactCorImg>();
	private List<AnotacionesCorImg> listAnotacionesCorImg = new ArrayList<AnotacionesCorImg>();
	private List<SelectItem> selectRespReactCorImg = new ArrayList<SelectItem>(); 
	private List<SelectItem> candRespReactCorImg = new ArrayList<SelectItem>(); 
	private SelectItem respuestaSelect = new SelectItem();
	
	@Inject
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	CcExamenesLocal ccExamenesLocal; 
	
	@Inject 
	CcHdrLocal ccHdrLocal; 
	
	@Inject 
	MrqsGrupoLinesLocal mrqsGrupoLinesLocal; 
	
	@Inject
	CcOpcionMultipleLocal ccOpcionMultipleLocal;
	
	@Inject 
	CcPreguntasHdrLocal ccPreguntasHdrLocal; 
	
	@Inject
	CandExamenesLocal candExamenesLocal; 
	
	@Inject
	CandExamRespuestasLocal candExamRespuestasLocal; 
	
	@Inject 
	CcImagenesGrpLocal ccImagenesGrpLocal;
	
	@Inject
	CandExamRespSkipLocal candExamRespSkipLocal;
			
	@PostConstruct
	public void init() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroCandExamen = session.getAttribute("NumeroCandExamenSV"); 
		 numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
		 
		 candExamRespSkipDto.setNumeroCandExamen(numeroCandExamen);		 
		 this.candExamenesV1 = candExamenesLocal.findByNumeroV2(numeroCandExamen); 
		 
		 setIdxCasos(0);
		 setIdxGrupo(0);
		 setCasosSize(0);
		 setHdrGrupoSize(0);
		 
		 Object objNumeroCcExamen = session.getAttribute("NumeroCcExamenSV"); 
		 numeroCcExamen = utilitariosLocal.objToLong(objNumeroCcExamen); 
		 candExamRespSkipDto.setExamen(numeroCcExamen);
		 long numCand = Long.valueOf(session.getAttribute("numCand").toString()).longValue();		 
		 ccExamen = ccExamenesLocal.findByNumeroWD(numeroCcExamen, numCand); 		 
		 
		 System.out.println("numero examen CC: " + ccExamen.getNumero());
		 
		 Date date = new Date();  
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		 strDate= formatter.format(date); 
		 Short intTime = Short.parseShort(String.valueOf(Integer.parseInt(session.getAttribute("tiempoExamen").toString())*sSegundos));
		 ccExamen.setTiempoLimite(intTime);
		 
		 rootCcHdr = new DefaultTreeNode("Root", null);
		 setListGrupoCcHdr(ccHdrLocal.findByCCByExamen(ccExamen.getNumero())); 
		 setHdrGrupoSize(listGrupoCcHdr.size());
		 
		 System.out.println("numero de casos: " + listGrupoCcHdr.size());
		 
		 if(getHdrGrupoSize()>1)
			 setListGrupoCcHdr(GrupoAleatorio(getListGrupoCcHdr()));		 
			
		for(CcHdrV1 idxHdr:listGrupoCcHdr) {
			TreeNode nodeGrupoHdr = new DefaultTreeNode(idxHdr, rootCcHdr);
			CcPreguntasHdrV1Dto hcCC = descripcionCoreCases(idxHdr.getHistorialClinico(), idxHdr.getEtiquetas());						
			setListCcPreguntas(ccPreguntasHdrLocal.findListByNumeroCcHdr(idxHdr.getNumero()));
			listCcPreguntas.add(0, hcCC);
			System.out.println("numero de preguntas: " + listCcPreguntas.size());
			nodeGrupoHdr.setExpanded(true);
			for(CcPreguntasHdrV1Dto ccPreguntasHdr:listCcPreguntas) {
				TreeNode nodeGrupoPreguntaHdr = new DefaultTreeNode(ccPreguntasHdr, nodeGrupoHdr);
			}
		}		
			
		CcHdrV1 idxHdr = listGrupoCcHdr.get(getIdxGrupo());		
		ccGrupoHdr.setNumero(idxHdr.getNumero());		
		ccGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
		ccGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());		
		candExamRespSkipDto.setNumeroGrupo(idxHdr.getNumero());
		listCcPreguntas2 = idxHdr.getListCcPreguntasHdrV1();			
		setCasosSize(listCcPreguntas2.size());
		System.out.println(idxGrupo + 1 + " de " + hdrGrupoSize + " numero CC: " + idxHdr.getNumero());
		setTitularCasos("Caso Clínico " + (idxGrupo + 1) + " de " + hdrGrupoSize);
		if(getCasosSize() > 1)
			listCcPreguntas2 = PreguntasAleatorio(listCcPreguntas2);		
		listCcPreguntas2.add(0, preguntaCoreCases(idxHdr.getHistorialClinico(), idxHdr.getEtiquetas()));
		setCasosSize(listCcPreguntas2.size());
		
		CcPreguntasHdrV1 idx = null ;
		
		if(getCasosSize() >= 0) { 
			idx = listCcPreguntas2.get(getIdxCasos());
			setCcPreguntasV1(idx);
			System.out.println(idxCasos + 1 + " de " + casosSize + " pregunta: " + idx.getNumero());
			if(idx.getTipoPregunta() == "HISTORIALCLINICO")
				setTitularPregunta("Historial Clínico: " + idx.getCcPreguntasFtaV1().getTituloPregunta());
			else
				setTitularPregunta("Pregunta " + idxCasos + " de " + (casosSize - 1) + " : " + idx.getCcPreguntasFtaV1().getTituloPregunta());
			ccPreguntasV1.setStartTime(new Date());
			ccPreguntasV1.setCcPreguntasFtaV1(idx.getCcPreguntasFtaV1());						
			candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumero());	
			setTipoPregunta(idx.getTipoPregunta());
			setNumeroPreguntaFta(ccPreguntasV1.getCcPreguntasFtaV1().getNumero());		
			setLimiteCaracteres((ccPreguntasV1.getCcPreguntasFtaV1().getLimiteCaracteres()== null)? 50: ccPreguntasV1.getCcPreguntasFtaV1().getLimiteCaracteres());
			
			candExamRespSkipDto.setNumeroPreguntaFta(getNumeroPreguntaFta());
			
			switch (idx.getTipoPregunta()) {
				case "LIMIT_RESP_TEXTO_LIBRE":  
					this.setLimitedFreeTextAnswer(true);
	            break;
				case "OPCION_MULTIPLE":
					listCcOpcionMultiple =  ccOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,ccPreguntasV1.getCcPreguntasFtaV1().isSuffleAnswerOrder());
					this.setMultipleChoice(true);
				break;
				case "IMAGEN_INDICADA":
					this.setAnnotatedImage(true);
					Gson gson = new Gson();
					setCcPreguntasFtaV1ForRead(ccPreguntasV1.getCcPreguntasFtaV1());
					if(null != ccPreguntasFtaV1ForRead.getRespuestaCorrecta()) {
			        	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
			        	setListRespReactCorImg(gson.fromJson(ccPreguntasFtaV1ForRead.getRespuestaCorrecta(), collectionType)); 
			        	refreshRespuestas();
					}		        				         
				break;	
				default:
					break;
			}
		}
		
			this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
				                                                     , this.ccGrupoHdr.getNumero()
				                                                     , this.ccPreguntasV1.getNumero()
				                                                     , getNumeroPreguntaFta()
				                                                     ); 
		
		if(!ccPreguntasV1.getCcPreguntasFtaV1().isSingleAnswerMode()) {
		   if(null!=this.candExamRespuestasV1.getRespuesta()) {
			  if(this.candExamRespuestasV1.getRespuesta().contains(",")) {
				  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
			  }else {
				  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
			  }
		   }
		}
		this.respuestaCandidato = this.getCandExamRespuestasV1().getRespuesta();
		setListPresentCcImagenesGrp(ccImagenesGrpLocal.findByFta(getNumeroPreguntaFta(),Utilitarios.INTRODUCCION));	
	}

	public void onNodeSelect(NodeSelectEvent event) {
		String strEvent = event.getTreeNode().toString(); 
		System.out.println("strEvent:"+strEvent);
		if(strEvent.contains("LINE")) {
			 long lNumeroHeader =  Long.parseLong(strEvent.substring(4)); 
			 FacesContext context = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 session.setAttribute("NumeroMrqsExamenSV", this.getCcExamen().getNumero());
			 session.setAttribute("NumeroMglSV", lNumeroHeader);
			 context.getApplication().getNavigationHandler().handleNavigation(context, null, "Candidates-MRQs-Exam");
		     return; 
		}
	}
	
	public void saveAndProceed() {		
		if(this.isMultipleChoice()) {
			if(!this.getCcPreguntasFtaV1ForRead().isSingleAnswerMode()) {
				System.out.println(this.getRespuestasPreguntaCandidato());
				if(null!=this.getRespuestasPreguntaCandidato()) {
				     String strRespuestas = "";
					for(int i =0;i<this.getRespuestasPreguntaCandidato().length;i++) {
						if((i+1)==this.getRespuestasPreguntaCandidato().length) {
						strRespuestas = strRespuestas+this.getRespuestasPreguntaCandidato()[i];
						}else {
							strRespuestas = strRespuestas+this.getRespuestasPreguntaCandidato()[i]+",";
						}
				    }
					this.setRespuestaCandidato(strRespuestas);
				}
			}
		}
		if (this.isAnnotatedImage()) {
			Gson gson = new Gson();
			String strRespuestas = "";
			strRespuestas = gson.toJson(listRespCandCorImg);
			this.setRespuestaCandidato(strRespuestas);
		}	
		//////SE COMENTO PORQUE NO SABEMOS DONDE GUSARDAR ESTOS DATOS///////
		
		long duration = 0;
		if(ccPreguntasV1.getStartTime()!=null) {
			duration = new Date().getTime() - ccPreguntasV1.getStartTime().getTime();
		}
		
		
		
		if(ccPreguntasV1.getTipoPregunta() != "HISTORIALCLINICO")
		{
			candExamRespuestasLocal.updateRespuesta( this.candExamenesV1.getNumero()
					                              , this.ccGrupoHdr.getNumero()
					                              , this.ccPreguntasV1.getNumero()
					                              , this.numeroPreguntaFta
					                              , duration
					                              , this.respuestaCandidato
					                              
					                              ); 
			candExamRespuestasLocal.calificaRespuesta(this.candExamenesV1.getNumero()
									                , this.ccGrupoHdr.getNumero()
									                , this.ccPreguntasV1.getNumero()
									                , this.numeroPreguntaFta
									                );
		}
		
	}
	
	public void SaltarCaso() {
		System.out.println("SaltarCaso() :)");
		
	}
	
	public void saltarSkip() {
		System.out.println("saltarSkip()  :)");
	}
	
	public void siguienteGuardarResp() {		
		saveAndProceed();		
		CcPreguntasHdrV1 idx = new CcPreguntasHdrV1();		
				
		if(busquedaSkip == false && getIdxCasos() < getCasosSize()) {
			idxCasos = idxCasos + 1; 
		}
		
		//if(getIdxCasos() <= getCasosSize() &&  busquedaSkip==false) {//ENTRA SI ES IGUAL
		//	idx = listCcPreguntas2.get(getIdxCasos());
		//}
		
		if (busquedaSkip == false && getIdxCasos() == getCasosSize() && (idxGrupo + 1 < getHdrGrupoSize())) {//ENTRA SI EL CASO ES IGUAL
			idxGrupo = idxGrupo + 1;
			setIdxCasos(0);
			nuevaBusqueda=true;
		}
		
		if(busquedaSkip==true || (getIdxCasos() == getCasosSize() && idxGrupo + 1 == getHdrGrupoSize())) {
			guardarSkip();
			busquedaSkip=true;
		}
		else {		
			CcHdrV1 idxHdr = listGrupoCcHdr.get(getIdxGrupo());
			if(nuevaBusqueda) {
				listCcPreguntas2 = idxHdr.getListCcPreguntasHdrV1();
				setHdrGrupoSize( listGrupoCcHdr.size());
				
				System.out.println(idxGrupo + 1 + " de " + hdrGrupoSize + " numero CC: " + idxHdr.getNumero());
				setTitularCasos("Caso Clínico " + (idxGrupo + 1) + " de " + hdrGrupoSize);
				//setCasosSize(listCcPreguntas2.size());
				if(getCasosSize() > 1)
					listCcPreguntas2 = PreguntasAleatorio(listCcPreguntas2);
				
				listCcPreguntas2.add(0, preguntaCoreCases(idxHdr.getHistorialClinico(), idxHdr.getEtiquetas()));
				//if(getCasosSize() >= 0)
					//idx = listCcPreguntas2.get(getIdxCasos());
								
				setCasosSize(listCcPreguntas2.size());
				nuevaBusqueda=false;
			}
			
			System.out.println(idxCasos + 1 + " de " + casosSize + " pregunta: " + idx.getNumero());
			
			
			if((getIdxCasos()+1) <= getCasosSize()) {
				ccGrupoHdr.setNumero(idxHdr.getNumero());
				candExamRespSkipDto.setNumeroGrupo(ccGrupoHdr.getNumero());
				ccGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
				ccGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
				idx = listCcPreguntas2.get(getIdxCasos());
				idx.setStartTime(new Date());
				setCcPreguntasV1(idx);
				
				if(idx.getTipoPregunta() == "HISTORIALCLINICO")
					setTitularPregunta("Historial Clínico: " + idx.getCcPreguntasFtaV1().getTituloPregunta());
				else
					setTitularPregunta("Pregunta " + idxCasos + " de " + (casosSize - 1) + " : " + idx.getCcPreguntasFtaV1().getTituloPregunta());
				
				ccPreguntasV1.setCcPreguntasFtaV1(idx.getCcPreguntasFtaV1());	
				setNumeroPreguntaFta(ccPreguntasV1.getCcPreguntasFtaV1().getNumero());
				candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumero());
				this.setTipoPregunta(idx.getTipoPregunta());			
				candExamRespSkipDto.setNumeroPreguntaFta(getNumeroPreguntaFta());
						
				switch (idx.getTipoPregunta()) {
					case "LIMIT_RESP_TEXTO_LIBRE":  
						this.setLimitedFreeTextAnswer(true);						
						ccPreguntasFtaV1ForRead = ccPreguntasV1.getCcPreguntasFtaV1();
						setLimiteCaracteres((ccPreguntasFtaV1ForRead.getLimiteCaracteres()== null)? 50: ccPreguntasFtaV1ForRead.getLimiteCaracteres());
						setLimiteCaracteresString("Límite de caracteres: " + getLimiteCaracteres() + "*");
					break;
					case "OPCION_MULTIPLE":
						listCcOpcionMultiple =  ccOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,ccPreguntasV1.getCcPreguntasFtaV1().isSuffleAnswerOrder());
						this.setMultipleChoice(true);
					break;
					case "IMAGEN_INDICADA":
						this.setAnnotatedImage(true);
						Gson gson = new Gson();
						setCcPreguntasFtaV1ForRead(ccPreguntasV1.getCcPreguntasFtaV1());
						if(null != ccPreguntasFtaV1ForRead.getRespuestaCorrecta()) {
				        	Type collectionType = new TypeToken<List<RespReactCorImg>>(){}.getType();
				        	setListRespReactCorImg(gson.fromJson(ccPreguntasFtaV1ForRead.getRespuestaCorrecta(), collectionType)); 
				        	refreshRespuestas();
						}		        				         
						break;	
					case "HISTORIALCLINICO":
						this.setLimitedFreeTextAnswer(false);
						this.setMultipleChoice(false);
						this.setAnnotatedImage(false);
						break;
				}
						
				this.candExamRespuestasV1 = candExamRespuestasLocal.findObjMod(numeroCandExamen
				                        , this.ccGrupoHdr.getNumero()
				                        , this.ccPreguntasV1.getNumero()
				                        , this.numeroPreguntaFta); 
					
				if(!ccPreguntasV1.getCcPreguntasFtaV1().isSingleAnswerMode()) {
					  if(null!=this.candExamRespuestasV1.getRespuesta()) {	
						  if(this.candExamRespuestasV1.getRespuesta().contains(",")) {
							  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
						  }else {
							  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
						  }
					 }
				}
				setRespuestaCandidato(getCandExamRespuestasV1().getRespuesta());			
			}
		}
		 setListPresentCcImagenesGrp(ccImagenesGrpLocal.findByFta(getNumeroPreguntaFta(),Utilitarios.INTRODUCCION));	

		 if(getIdxCasos() == getCasosSize() /*&&  busquedaSkip==false*/) { 
			 // ??usar busquedaSkip aquí ocasiona que algunas veces el examen no termine:
			 CandExamenesDto candExamenesDto = new CandExamenesDto();
	    	 candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
	    	 idxCasos--;
	    	 showFinalMessage = true;
			 return;			
		}		
	}
	
	public void guardarSkip() {
		System.out.println("guardarSkip()  :)");
	}
	
	public void onTimeout() {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FIN DE TIEMPO", "Fin de tiempo"));
			CandExamenesDto candExamenesDto = new CandExamenesDto();
	    	candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
			redirectPage();
	}
		
	public void redirectPage() {
		try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ManageExams.xhtml");
         } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void refreshRespuestas() {
		selectRespReactCorImg = new ArrayList<SelectItem>(); 
		for(RespReactCorImg i:listRespReactCorImg) {
			SelectItem selectItem = new SelectItem(i.getNumero(),i.getRespuesta()); 
			selectRespReactCorImg.add(selectItem); 
			System.out.println("SELECT ITEM "+selectItem);
			candRespReactCorImg.add(selectItem);
		}
	}
		
	public void updateRespuestasImgCor(Object x) {
		System.out.println("X=>"+x.toString());
		SelectItem selectItem = new SelectItem(
				x,
				listRespReactCorImg.get(Integer.parseInt(respuestaSelect.getValue().toString())).getRespuesta());
		candRespReactCorImg.add(selectItem);
	}
		
	public List<CcHdrV1> GrupoAleatorio(List<CcHdrV1> lccGrouoHdr) {
		int elementosMax = lccGrouoHdr.size();
		int numero;
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		ArrayList<Integer> arrayRandom = new ArrayList<Integer>();
		List<CcHdrV1> lmgh = new ArrayList<CcHdrV1>();
		// llenaValores
		for (int i = 0; i < elementosMax ; i++) {
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
		
		for  (int i = 0; i < elementosMax; i++) {
			lmgh.add(lccGrouoHdr.get(arrayRandom.get(i)));
		}					
		return lmgh;
	}
	
	private CcPreguntasHdrV1Dto descripcionCoreCases(String descripCC, String textoSug) {
		CcPreguntasHdrV1Dto historial = new CcPreguntasHdrV1Dto();
		historial.setComentarios(descripCC);
		historial.setEtiquetas(textoSug);
		return historial;
	}
	
	private CcPreguntasHdrV1 preguntaCoreCases(String descripCC, String textoSug) {
		CcPreguntasHdrV1 historial = new CcPreguntasHdrV1();
		CcPreguntasFtaV1 fta = new CcPreguntasFtaV1();
		fta.setTituloPregunta(descripCC);
		fta.setTextoSugerencias(textoSug);
		fta.setTipoPregunta("HISTORIALCLINICO");
		historial.setTipoPregunta("HISTORIALCLINICO");
		historial.setCcPreguntasFtaV1(fta);
		return historial;
	}
		
	public List<CcPreguntasHdrV1> PreguntasAleatorio(List<CcPreguntasHdrV1> lmqrsGrupoLines) {
		int elementosMax = lmqrsGrupoLines.size();
		int numero;
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		ArrayList<Integer> arrayRandom = new ArrayList<Integer>();
		List<CcPreguntasHdrV1> lmgl = new ArrayList<CcPreguntasHdrV1>();
		
		for (int i = 0; i < elementosMax ; i++) {
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
		
		for  (int i = 0; i < elementosMax; i++) {
			lmgl.add(lmqrsGrupoLines.get(arrayRandom.get(i)));
		}					
		return lmgl;
	}
		
	public CcExamenes getCcExamen() {
		return ccExamen;
	}

	public void setCcExamen(CcExamenes ccExamen) {
		this.ccExamen = ccExamen;
	}

	public MrqsGrupoLines getMrqsGrupoLines() {
		return mrqsGrupoLines;
	}

	public void setMrqsGrupoLines(MrqsGrupoLines mrqsGrupoLines) {
		this.mrqsGrupoLines = mrqsGrupoLines;
	}

	public TreeNode getRootCcHdr() {
		return rootCcHdr;
	}

	public void setRootCcHdr(TreeNode rootCcHdr) {
		this.rootCcHdr = rootCcHdr;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		if(multipleChoice) {
			this.setLimitedFreeTextAnswer(false);
			this.setAnnotatedImage(false);
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
			this.setAnnotatedImage(false);
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
			this.setAnnotatedImage(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.indicateImage = indicateImage;
	}

	public boolean isAnnotatedImage() {
		return annotatedImage;
	}

	public void setAnnotatedImage(boolean annotatedImage) {
		if(annotatedImage) {
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.annotatedImage = annotatedImage;
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

	public boolean isShowFinalMessage() {
		return showFinalMessage;
	}

	public void setShowFinalMessage(boolean showFinalMessage) {
		this.showFinalMessage = showFinalMessage;
	}

		public long getCasosSize() {
		return casosSize;
	}

	public void setCasosSize(long casosSize) {
		this.casosSize = casosSize;
	}

	public int getIdxCasos() {
		return idxCasos;
	}

	public void setIdxCasos(int idxCasos) {
		this.idxCasos = idxCasos;
	}
	
	public int getIdxGrupo() {
		return idxGrupo;
	}

	public void setIdxGrupo(int idxGrupo) {
		this.idxGrupo = idxGrupo;
	}
		
	public int getLimiteCaracteres() {
		return limiteCaracteres;
	}

	public void setLimiteCaracteres(int limiteCaracteres) {
		this.limiteCaracteres = limiteCaracteres;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	
	public String getTimerValue() {
		return timerValue;
	}

	public void setTimerValue(String timerValue) {	
		this.timerValue = timerValue;
	}
	
	public List<AnotacionesCorImg> getListAnotacionesCorImg() {
		return listAnotacionesCorImg;
	}
		
	public void setListAnotacionesCorImg(List<AnotacionesCorImg> listAnotacionesCorImg) {
		this.listAnotacionesCorImg = listAnotacionesCorImg;
	}

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

	public List<CcHdrV1> getListGrupoCcHdr() {
		return listGrupoCcHdr;
	}

	public void setListGrupoCcHdr(List<CcHdrV1> listGrupoCcHdr) {
		this.listGrupoCcHdr = listGrupoCcHdr;
	}

	public List<CcPreguntasHdrV1Dto> getListCcPreguntas() {
		return listCcPreguntas;
	}

	public void setListCcPreguntas(List<CcPreguntasHdrV1Dto> listCcPreguntas) {
		this.listCcPreguntas = listCcPreguntas;
	}

	public List<CcOpcionMultiple> getListCcOpcionMultiple() {
		return listCcOpcionMultiple;
	}

	public void setListCcOpcionMultiple(List<CcOpcionMultiple> listCcOpcionMultiple) {
		this.listCcOpcionMultiple = listCcOpcionMultiple;
	}

	public List<CcPreguntasHdrV1> getListCcPreguntas2() {
		return listCcPreguntas2;
	}

	public void setListCcPreguntas2(List<CcPreguntasHdrV1> listCcPreguntas2) {
		this.listCcPreguntas2 = listCcPreguntas2;
	}
	
	public CcPreguntasHdrV1 getCcPreguntasV1() {
		return ccPreguntasV1;
	}

	public void setCcPreguntasV1(CcPreguntasHdrV1 ccPreguntasV1) {
		this.ccPreguntasV1 = ccPreguntasV1;
	}

	public List<CcImagenesGrp> getListPresentCcImagenesGrp() {
		return listPresentCcImagenesGrp;
	}

	public void setListPresentCcImagenesGrp(List<CcImagenesGrp> listPresentCcImagenesGrp) {
		this.listPresentCcImagenesGrp = listPresentCcImagenesGrp;
	}

	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}

	public CcPreguntasFtaV1 getCcPreguntasFtaV1ForRead() {
		return ccPreguntasFtaV1ForRead;
	}

	public void setCcPreguntasFtaV1ForRead(CcPreguntasFtaV1 ccPreguntasFtaV1ForRead) {
		this.ccPreguntasFtaV1ForRead = ccPreguntasFtaV1ForRead;
	}

	public List<CcPreguntasHdrV1> getListCcPreguntas1() {
		return listCcPreguntas1;
	}

	public void setListCcPreguntas1(List<CcPreguntasHdrV1> listCcPreguntas1) {
		this.listCcPreguntas1 = listCcPreguntas1;
	}
	
	public CcHdrV1 getCcGrupoHdr() {
		return ccGrupoHdr;
	}

	public void setCcGrupoHdr(CcHdrV1 ccGrupoHdr) {
		this.ccGrupoHdr = ccGrupoHdr;
	}
	
	public long getHdrGrupoSize() {
		return hdrGrupoSize;
	}

	public void setHdrGrupoSize(long hdrGrupoSize) {
		this.hdrGrupoSize = hdrGrupoSize;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public String getTitularPregunta() {
		return titularPregunta;
	}

	public void setTitularPregunta(String titularPregunta) {
		this.titularPregunta = titularPregunta;
	}

	public String getTitularCasos() {
		return titularCasos;
	}

	public void setTitularCasos(String titularCasos) {
		this.titularCasos = titularCasos;
	}

	public String getLimiteCaracteresString() {
		return limiteCaracteresString;
	}

	public void setLimiteCaracteresString(String limiteCaracteresString) {
		this.limiteCaracteresString = limiteCaracteresString;
	}
}
