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

import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.exams.MrqsGrupoLinesV2;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV2;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class MRQsExamForm {

	private MrqsExamenes mrqsExamen = new MrqsExamenes(); 
	private MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr();
	private MrqsGrupoLines mrqsGrupoLines = new MrqsGrupoLines(); 
	private TreeNode rootMrqsGrupo;
	private TreeNode selectedNode;
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
	private List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas = new ArrayList<MrqsPreguntasHdrV1>();
    private MrqsGrupoLinesV2 mrqsGrupoLinesV2 = new MrqsGrupoLinesV2(); 
    private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
	
    
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
	
	@PostConstruct
	public void init() {
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
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
					mrqsGrupoHdr.setTitulo(idxHdr.getTitulo());
					List<MrqsGrupoLinesV2> listMrqsGrupoLinesV2 = mrqsGrupoLinesLocal.findByNumeroHdrWDV2(idxHdr.getNumero());
					for(MrqsGrupoLinesV2 idx:listMrqsGrupoLinesV2) {
						System.out.println("idx.getTextoPregunta():"+idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTitulo(idx.getTitulo());
						mrqsGrupoLinesV2.setTextoPregunta(idx.getTextoPregunta());
						mrqsGrupoLinesV2.setTextoSugerencias(idx.getTextoSugerencias());
						mrqsGrupoLinesV2.setNumeroPregunta(idx.getNumeroPregunta());
						if(Utilitarios.RESP_TEXTO_LIBRE.equals(idx.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(idx.getTipoPregunta())) {
							mrqsGrupoLinesV2.setSingleAnswerMode(idx.isSingleAnswerMode());
							mrqsGrupoLinesV2.setSuffleAnswerOrder(idx.isSuffleAnswerOrder());
							long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
							listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(numeroFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
							this.setMultipleChoice(true);
						}
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
				
				if(Utilitarios.RESP_TEXTO_LIBRE.equals(tmp.getTipoPregunta())) {
					this.setLimitedFreeTextAnswer(true);
				}else if(Utilitarios.OPCION_MULTIPLE.equals(tmp.getTipoPregunta())) {
					mrqsGrupoLinesV2.setSingleAnswerMode(tmp.isSingleAnswerMode());
					mrqsGrupoLinesV2.setSuffleAnswerOrder(tmp.isSuffleAnswerOrder());
					long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsGrupoLinesV2.getNumeroPregunta());
					listMrqsOpcionMultiple =  mrqsOpcionMultipleLocal.findByNumeroFtaShuffleOrderOM(numeroFta,mrqsGrupoLinesV2.isSuffleAnswerOrder());
					this.setMultipleChoice(true);
				}
				
			}
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


}
