package com.cmrise.ejb.backing.candidates.exams;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV2;
import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.img.MrqsImagenesGrpLocal;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

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
    private long numeroPreguntaFta; 
    private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
	private String respuestaCandidato; 
	private String[] respuestasPreguntaCandidato;
	
	private List<MrqsImagenesGrp> listPresentMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
	
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
	
	@PostConstruct
	public void init() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroCandExamen = session.getAttribute("NumeroCandExamenSV"); 
		 long numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
		 
		 this.candExamenesV1 = candExamenesLocal.findByNumero(numeroCandExamen); 
		 
		 Object objNumeroMRQsExamen = session.getAttribute("NumeroMrqsExamenSV"); 
		 long numeroMRQsExamen = utilitariosLocal.objToLong(objNumeroMRQsExamen); 
		 this.mrqsExamen = mrqsExamenesLocal.findByIdWD(numeroMRQsExamen); 
		 
		 rootMrqsGrupo = new DefaultTreeNode("Root", null);
			listMrqsGrupoHdr = mrqsGrupoHdrLocal.findByNumeroExamen(this.getMrqsExamen().getNumero()); 
			for(MrqsGrupoHdr idxHdr:listMrqsGrupoHdr) {
				TreeNode nodeGrupoHdr = new DefaultTreeNode(idxHdr, rootMrqsGrupo);
				listMrqsGrupoPreguntas = mrqsGrupoLinesLocal.findByNumeroHdrWD(idxHdr.getNumero());
				nodeGrupoHdr.setExpanded(true);
				for(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1:listMrqsGrupoPreguntas) {
					TreeNode nodeGrupoPreguntaHdr = new DefaultTreeNode(mrqsPreguntasHdrV1, nodeGrupoHdr);
				}
			}
		
			Object objNumeroMglSV = session.getAttribute("NumeroMglSV"); 
			long numeroMgl = utilitariosLocal.objToLong(objNumeroMglSV); 
			System.out.println("numeroMgl:"+numeroMgl);
			if(0==numeroMgl) {
				for(MrqsGrupoHdr idxHdr:listMrqsGrupoHdr) {
					mrqsGrupoHdr.setNumero(idxHdr.getNumero());
					mrqsGrupoHdr.setTitulo(idxHdr.getTitulo());
					List<MrqsGrupoLinesV2> listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
					for(MrqsGrupoLinesV2 idx:listMrqsGrupoLinesV2) {
						System.out.println("idx.getTextoPregunta():"+idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						this.numeroPreguntaFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
						
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
						break; 
					}
					break; 
				}
			}else {
				MrqsGrupoLinesV2 tmp = mrqsGrupoLinesLocal.findByNumeroV2(numeroMgl);
				
				mrqsGrupoHdr.setNumero(tmp.getNumeroHdr());
				mrqsGrupoHdr.setTitulo(tmp.getTituloGrupo());
				
				mrqsGrupoLinesV2.setNumero(tmp.getNumero());
				mrqsGrupoLinesV2.setTitulo(tmp.getTitulo());
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


}
