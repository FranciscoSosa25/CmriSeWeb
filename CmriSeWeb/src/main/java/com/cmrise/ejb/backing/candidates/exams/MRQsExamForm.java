package com.cmrise.ejb.backing.candidates.exams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.exams.MrqsGrupoLinesV2;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.candidates.exams.CandExamRespSkipLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
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
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;
import java.io.IOException;
import java.text.SimpleDateFormat;

@ManagedBean
@ViewScoped
public class MRQsExamForm {

	private CandExamenesV1 candExamenesV1 =  new CandExamenesV1(); 
	private CandExamRespuestasV1 candExamRespuestasV1 = new CandExamRespuestasV1(); 
	private MrqsExamenes mrqsExamen = new MrqsExamenes(); 
	private MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr();
	private MrqsGrupoLines mrqsGrupoLines = new MrqsGrupoLines(); 
	private TreeNode rootMrqsGrupo;
	private TreeNode selectedNode;
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
	private List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas = new ArrayList<MrqsPreguntasHdrV1>();
    private MrqsGrupoLinesV2 mrqsGrupoLinesV2 = new MrqsGrupoLinesV2(); 
    private long numeroPreguntaFta; 
    private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private Short sSegundos =60;
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
	private String respuestaCandidato; 
	private String[] respuestasPreguntaCandidato;
	private String timerValue;
	private String strDate;
	private List<MrqsImagenesGrp> listPresentMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
	private long numeroMRQsExamen;
	private long numeroCandExamen;
	private CandExamRespSkipDto candExamRespSkipDto = new CandExamRespSkipDto();
	private boolean flag2;
	private long reactivosSize;
	private int idxReactivos;
	private long hdrGrupoSize;
	private int idxGrupo;
	private boolean nuevaBusqueda = false;
	List<MrqsGrupoLinesV2> listMrqsGrupoLinesV2 =new ArrayList<MrqsGrupoLinesV2>();
	List<CandExamSkipV1Dto> lCandExamSkipV1Dto = new ArrayList<CandExamSkipV1Dto>();
	private int idxSkip = 0;
	private boolean busquedaSkip = false;
	private int skipMax = 0;
	
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
	
	@PostConstruct
	public void init() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroCandExamen = session.getAttribute("NumeroCandExamenSV"); 
		 numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
		 candExamRespSkipDto.setNumeroCandExamen(numeroCandExamen);
		 System.out.println("*** Candidato de examen: "+numeroCandExamen);
		 this.candExamenesV1 = candExamenesLocal.findByNumero(numeroCandExamen); 
		 idxReactivos=0;
		 idxGrupo=0;
		 flag2=false;
		 reactivosSize=0;
		 hdrGrupoSize=0;
		 Object objNumeroMRQsExamen = session.getAttribute("NumeroMrqsExamenSV"); 
		 numeroMRQsExamen = utilitariosLocal.objToLong(objNumeroMRQsExamen); 
		 candExamRespSkipDto.setExamen(numeroMRQsExamen);
		 long numCand = Long.valueOf(session.getAttribute("numCand").toString()).longValue();
		 System.out.println("Numero buscado de examen: "+numCand);
		 //this.mrqsExamen = mrqsExamenesLocal.findByIdWD(numeroMRQsExamen,numCand); 
		 this.mrqsExamen = mrqsExamenesLocal.findByNumeroWD(numeroMRQsExamen,numCand); 
		 candExamRespSkipDto.setExamen(numeroMRQsExamen);
		 System.out.println("Examen: "+mrqsExamen.getDescripcion());
		 Date date = new Date();  
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		 strDate= formatter.format(date); 
		 Short intTime = Short.parseShort(String.valueOf(Integer.parseInt(session.getAttribute("tiempoExamen").toString())*sSegundos));
		 this.mrqsExamen.setTiempoLimite(intTime);
		 rootMrqsGrupo = new DefaultTreeNode("Root", null);
			listMrqsGrupoHdr = mrqsGrupoHdrLocal.findByNumeroExamen(this.getMrqsExamen().getNumero()); 
			listMrqsGrupoHdr = GrupoAleatorio(listMrqsGrupoHdr);
			hdrGrupoSize = listMrqsGrupoHdr.size();
			for(MrqsGrupoHdr idxHdr:listMrqsGrupoHdr) {
				TreeNode nodeGrupoHdr = new DefaultTreeNode(idxHdr, rootMrqsGrupo);
				listMrqsGrupoPreguntas = mrqsGrupoLinesLocal.findByNumeroHdrWD(idxHdr.getNumero());
				nodeGrupoHdr.setExpanded(true);
				//reactivosSize = listMrqsGrupoPreguntas.size();
				for(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1:listMrqsGrupoPreguntas) {
					TreeNode nodeGrupoPreguntaHdr = new DefaultTreeNode(mrqsPreguntasHdrV1, nodeGrupoHdr);
				}
			}
		
			
			Object objNumeroMglSV = session.getAttribute("NumeroMglSV"); 
			long numeroMgl = utilitariosLocal.objToLong(objNumeroMglSV); 
			System.out.println("numeroMgl:"+numeroMgl);
			if(0==numeroMgl) {
				//for(MrqsGrupoHdr idxHdr:listMrqsGrupoHdr) {
				MrqsGrupoHdr idxHdr = listMrqsGrupoHdr.get(idxGrupo);
					mrqsGrupoHdr.setNumero(idxHdr.getNumero());
					candExamRespSkipDto.setNumeroGrupo(idxHdr.getNumero());
					mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
					mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
	//				mrqsGrupoHdr.setTitulo(idxHdr.getTitulo());
					/* Grupolines preguntas */
					listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
					reactivosSize=listMrqsGrupoLinesV2.size();
					System.out.println("********Cantidad de Preguntas: "+reactivosSize);
					listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);
					
					//for(MrqsGrupoLinesV2 idx:listMrqsGrupoLinesV2) {
					MrqsGrupoLinesV2 idx = null ;
					if(listMrqsGrupoLinesV2 != null || reactivosSize>=0) { //Algunas veces llegaba vacía la lista y truena la página sin está validación
					 idx = listMrqsGrupoLinesV2.get(idxReactivos);
					    System.out.println("idx.getTextoPregunta():"+idx.getNumeroPregunta());
						
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
						/* preguntas */
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
						candExamRespSkipDto.setNumeroPreguntaFta(this.numeroPreguntaFta);
						if(Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
							mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
							mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							/* opciones */
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
							this.setMultipleChoice(true);
						}
					}//
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
						//break; 
				//	}
					//break; 
			//	}
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
				
				this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
				
				if(Utilitarios.RESP_TEXTO_LIBRE.equals(tmp.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
				}else if(Utilitarios.OPCION_MULTIPLE.equals(tmp.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(tmp.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(tmp.isSuffleAnswerOrder());
					listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
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

	public void onNodeSelect(NodeSelectEvent event) {
		String strEvent = event.getTreeNode().toString(); 
		System.out.println("strEvent:"+strEvent);
		if(strEvent.contains("LINE")) {
			 long lNumeroHeader =  Long.parseLong(strEvent.substring(4)); 
			 FacesContext context = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 session.setAttribute("NumeroMrqsExamenSV", this.getMrqsExamen().getNumero());
			 session.setAttribute("NumeroMglSV", lNumeroHeader);
			 context.getApplication().getNavigationHandler().handleNavigation(context, null, "Candidates-MRQs-Exam");
		     return; 
		}
	}
	
	public void saveAndProceed() {
		System.out.println("Entra saveAndProceed()");
		System.out.println("this.candExamenesV1.getNumero():"+this.candExamenesV1.getNumero());
		System.out.println("this.mrqsGrupoHdr.getNumero():"+this.mrqsGrupoHdr.getNumero());
		System.out.println(" this.mrqsGrupoLinesV2.getNumeroPregunta():"+ this.mrqsGrupoLinesV2.getNumeroPregunta());
		System.out.println("this.numeroPreguntaFta:"+this.numeroPreguntaFta);
		
		if(this.isMultipleChoice()) {
			if(!this.getMrqsGrupoLinesV2().isSingleAnswerMode()) {
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
		
		candExamRespuestasLocal.updateRespuesta( this.candExamenesV1.getNumero()
				                              , this.mrqsGrupoHdr.getNumero()
				                              , this.mrqsGrupoLinesV2.getNumeroPregunta()
				                              , this.numeroPreguntaFta
				                              , this.respuestaCandidato
				                              ); 
		candExamRespuestasLocal.calificaRespuesta(this.candExamenesV1.getNumero()
								                , this.mrqsGrupoHdr.getNumero()
								                , this.mrqsGrupoLinesV2.getNumeroPregunta()
								                , this.numeroPreguntaFta
								                );
		System.out.println("Sale saveAndProceed()");
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
			 FacesContext context = FacesContext.getCurrentInstance(); 
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 Object objNumeroCandExamen = session.getAttribute("NumeroCandExamenSV"); 
			 long numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
			 MrqsGrupoLinesV2 idx = new MrqsGrupoLinesV2();
			 candExamRespSkipDto.setSkip(1);
			 if(busquedaSkip == false && candExamRespSkipLocal.existsSkip(candExamRespSkipDto.getExamen(),candExamRespSkipDto.getNumeroCandExamen(),candExamRespSkipDto.getNumeroGrupo(),candExamRespSkipDto.getNumeroPreguntaHdr(),candExamRespSkipDto.getNumeroPreguntaFta(),1)== false) {
				 candExamRespSkipDto.setNumero(candExamRespSkipLocal.insert(candExamRespSkipDto)); 
			 }
			 
			 System.out.println("idxReactivos:"+idxReactivos);
			 reactivosSize = listMrqsGrupoLinesV2.size();
			 if(busquedaSkip == false) {
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
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
						candExamRespSkipDto.setNumeroPreguntaFta(this.numeroPreguntaFta);
						if(Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
							mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
							mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
							this.setMultipleChoice(true);
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
				
				this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
				
				if(Utilitarios.RESP_TEXTO_LIBRE.equals(tmp.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
				}else if(Utilitarios.OPCION_MULTIPLE.equals(tmp.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(tmp.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(tmp.isSuffleAnswerOrder());
					listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
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
			
		}
		
		public void saltarSkip() {
			lCandExamSkipV1Dto=candExamRespSkipLocal.findAllListSkip(numeroMRQsExamen,numeroCandExamen,1,1);
			skipMax = lCandExamSkipV1Dto.size();
			MrqsGrupoHdr idxHdr = new MrqsGrupoHdr();
			if(skipMax == idxSkip) {
				idxSkip = 0;
			}
			if(skipMax == 1){
				flag2 = true; 
				return; 
			}else {
		    for(int i=0;i <	listMrqsGrupoHdr.size();i++) {
		    	if(listMrqsGrupoHdr.get(i).getNumero() == (int)lCandExamSkipV1Dto.get(idxSkip).getNumeroGrupo())
		    		idxHdr = listMrqsGrupoHdr.get(i);
		    }

					mrqsGrupoHdr.setNumero(idxHdr.getNumero()); 
					mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
					mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
					/* Grupolines preguntas */
					listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
					reactivosSize=listMrqsGrupoLinesV2.size();
					MrqsGrupoLinesV2 idx = new MrqsGrupoLinesV2();
					for(int i=0;i<reactivosSize;i++) {
						if(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr() == listMrqsGrupoLinesV2.get(i).getNumeroPregunta())
						 idx = listMrqsGrupoLinesV2.get(i);												
					}
					System.out.println("********Cantidad de Preguntas: "+reactivosSize);
					//listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);
					
					
					    System.out.println("idx.getTextoPregunta():"+idx.getNumeroPregunta());
						
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						//candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
						/* preguntas */
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr());
						if(Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
							mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
							mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							/* opciones */
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
							this.setMultipleChoice(true);
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
						//break; 
					
						idxSkip++;
		   }
		}
		
		public String getTimerValue() {
			return timerValue;
		}

		public void setTimerValue(String timerValue) {	
			this.timerValue = timerValue;
		}

		public void siguienteGuardarResp() {
			
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
			 
			 System.out.println("idxReactivos:"+idxReactivos);
			 reactivosSize = listMrqsGrupoLinesV2.size();
			 if(busquedaSkip==false) {
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
				guardarSkip();
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
										
					System.out.println("********Cantidad de Preguntas: "+reactivosSize);
					
						System.out.println("idx.getTextoPregunta():"+idx.getTextoPregunta());
						
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
						candExamRespSkipDto.setNumeroPreguntaFta(this.numeroPreguntaFta);
						if(Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
							mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
							mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
							this.setMultipleChoice(true);
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
				
				this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
				
				if(Utilitarios.RESP_TEXTO_LIBRE.equals(tmp.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
				}else if(Utilitarios.OPCION_MULTIPLE.equals(tmp.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(tmp.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(tmp.isSuffleAnswerOrder());
					listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
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
			if(idxReactivos == reactivosSize /*&&  busquedaSkip==false*/) { // usar busquedaSkip aquí ocasiona que algunas veces el examen no termine:
				//this.candExamenesV1 = candExamenesLocal.findByNumero(numeroCandExamen); 
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay más preguntas", "Aqui va el mensaje"));
				System.out.println("ULTIMA PREGUNTA");
			  	CandExamenesDto candExamenesDto = new CandExamenesDto();
		    	candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
		    	
		    	
		    	try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				redirectPage();
				return;
				
			}
			/*if(null!=this.candExamRespuestasV1.getRespuesta()) {	
				  if(this.candExamRespuestasV1.getRespuesta().contains(",")) {
					  this.respuestasPreguntaCandidato = this.candExamRespuestasV1.getRespuesta().split(",");
				  }else {
					  this.respuestasPreguntaCandidato = new String[] {this.candExamRespuestasV1.getRespuesta()};
				  }
				 }
				for(int i = 0;i< this.respuestasPreguntaCandidato.length;i++) {
					System.out.println("Respuesta "+i+" : "+respuestasPreguntaCandidato[i]);				
				}
				*/
			
		}
		
		
		public List<MrqsGrupoHdr> GrupoAleatorio(List<MrqsGrupoHdr> lmqrsGrouoHdr) {
			int elementosMax = lmqrsGrouoHdr.size();
			int numero;
			ArrayList<Integer> numeros = new ArrayList<Integer>();
			ArrayList<Integer> arrayRandom = new ArrayList<Integer>();
			List<MrqsGrupoHdr> lmgh = new ArrayList<MrqsGrupoHdr>();
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
				lmgh.add(lmqrsGrouoHdr.get(arrayRandom.get(i)));
			}
						
			return lmgh;
		}
		
		public List<MrqsGrupoLinesV2> PreguntasAleatorio(List<MrqsGrupoLinesV2> lmqrsGrupoLines) {
			int elementosMax = lmqrsGrupoLines.size();
			int numero;
			ArrayList<Integer> numeros = new ArrayList<Integer>();
			ArrayList<Integer> arrayRandom = new ArrayList<Integer>();
			List<MrqsGrupoLinesV2> lmgl = new ArrayList<MrqsGrupoLinesV2>();
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
				//System.out.println("Random: "+arrayRandom.get(i));
				lmgl.add(lmqrsGrupoLines.get(arrayRandom.get(i)));
			}
						
			return lmgl;
		}
		
		public void guardarSkip() {
			MrqsGrupoHdr idxHdr = new MrqsGrupoHdr();
			saveAndProceed();
			
			 candExamRespSkipDto.setSkip(0);
			
			lCandExamSkipV1Dto=candExamRespSkipLocal.findAllListSkip(numeroMRQsExamen,numeroCandExamen,1,1);
			skipMax = lCandExamSkipV1Dto.size();
    		idxSkip = 0;
			if(skipMax == 0){
				flag2 = true; 
				
			}else {
		    for(int i=0;i <	listMrqsGrupoHdr.size();i++) {
		    	if(listMrqsGrupoHdr.get(i).getNumero() == (int)lCandExamSkipV1Dto.get(idxSkip).getNumeroGrupo()) {
		    		candExamRespSkipDto.setNumeroGrupo(lCandExamSkipV1Dto.get(idxSkip).getNumeroGrupo());
		    		candExamRespSkipDto.setExamen(lCandExamSkipV1Dto.get(idxSkip).getExamen());
		    		candExamRespSkipDto.setEstatus(0);
		    		candExamRespSkipDto.setNumero(lCandExamSkipV1Dto.get(idxSkip).getNumero());
		    		candExamRespSkipDto.setNumeroCandExamen(lCandExamSkipV1Dto.get(idxSkip).getNumeroCandExamen());
		    		candExamRespSkipDto.setNumeroPreguntaHdr(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr());
		    		candExamRespSkipDto.setNumeroPreguntaFta(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaFta());
		    		
		    	    candExamRespSkipLocal.update(lCandExamSkipV1Dto.get(idxSkip).getNumero(),candExamRespSkipDto); 
		    		idxHdr = listMrqsGrupoHdr.get(i);	
		    	}
		    }
		    if(skipMax == 1  ) {
		    	CandExamenesDto candExamenesDto = new CandExamenesDto();
		    	candExamenesLocal.updateEstatus(numeroCandExamen, candExamenesDto);
				redirectPage();
				return;
			}
					mrqsGrupoHdr.setNumero(idxHdr.getNumero()); 
					mrqsGrupoHdr.setAdmonMateriaDesc(idxHdr.getAdmonMateriaDesc());
					mrqsGrupoHdr.setAdmonSubMateriaDesc(idxHdr.getAdmonSubMateriaDesc());
					/* Grupolines preguntas */
					listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
					reactivosSize=listMrqsGrupoLinesV2.size();
					MrqsGrupoLinesV2 idx = new MrqsGrupoLinesV2();
					for(int i=0;i<reactivosSize;i++) {
						if(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr() == listMrqsGrupoLinesV2.get(i).getNumeroPregunta())
						 idx = listMrqsGrupoLinesV2.get(i);												
					}
					System.out.println("********Cantidad de Preguntas: "+reactivosSize);
					//listMrqsGrupoLinesV2 = PreguntasAleatorio(listMrqsGrupoLinesV2);
					
					
					    System.out.println("idx.getTextoPregunta():"+idx.getNumeroPregunta());
						
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						//candExamRespSkipDto.setNumeroPreguntaHdr(idx.getNumeroPregunta());
						/* preguntas */
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(lCandExamSkipV1Dto.get(idxSkip).getNumeroPreguntaHdr());
						if(Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
							mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
							mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							/* opciones */
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(this.numeroPreguntaFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
							this.setMultipleChoice(true);
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
						//break; 
					
						idxSkip++;
		   }
		}
}
