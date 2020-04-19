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

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class CoreCasesExamForm {

	private CandExamenesV1 candExamenesV1 = new CandExamenesV1(); 
	private CcExamenes ccExamen = new CcExamenes(); 
	private List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
	private CcHdrV1 ccHdrV1; 
	private CcPreguntasHdrV1 ccPreguntasHdrV1; 
	private CcPreguntasFtaV1 ccPreguntasFtaV1; 
	private List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
	
	private TreeNode rootCcExamAsignaciones;
	private TreeNode selectedNode;
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private boolean singleAnswerMode;
	private boolean suffleAnswerOrder; 
	private String respuestaCandidato; 
	private String[] respuestasPreguntaCandidato;
	
	@Inject
	UtilitariosLocal utilitariosLocal; 
	
	@Inject
	CandExamenesLocal candExamenesLocal; 
	
	@Inject
	CandExamRespuestasLocal candExamRespuestasLocal; 
	
	@Inject 
	CcExamenesLocal ccExamenesLocal; 
	
	
	@PostConstruct
	public void init() {
		 System.out.println("Comienza CoreCasesExamForm init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroCandExamen = session.getAttribute("NumeroCandExamenSV"); 
		 long numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
		 this.candExamenesV1 = candExamenesLocal.findByNumero(numeroCandExamen); 
		 Object objNumeroCcExamenSV = session.getAttribute("NumeroCcExamenSV"); 
		 long numeroCcExamen = utilitariosLocal.objToLong(objNumeroCcExamenSV); 
		 
		 System.out.println("numeroCandExamen:"+numeroCandExamen);
		 System.out.println("this.candExamenesV1.getNumeroExamen():"+this.candExamenesV1.getNumeroExamen());
		 System.out.println("numeroCcExamen:"+numeroCcExamen);
		 ccExamen = ccExamenesLocal.findByNumeroObjModCand(numeroCcExamen); 
		 
		 listCcExamAsignaciones = ccExamen.getListCcExamAsignaciones(); 
			
			rootCcExamAsignaciones = new DefaultTreeNode("Root", null);
			if(null!=listCcExamAsignaciones) {
				for(CcExamAsignaciones i:listCcExamAsignaciones) {
					CcHdrV1 ccHdrV1 = i.getCcHdrV1(); 
					TreeNode nodeCcExamAsignaciones = new DefaultTreeNode("cCExamAsignacion",ccHdrV1, rootCcExamAsignaciones);
					List<CcPreguntasHdrV1>  listCcPreguntasHdrV1 = ccHdrV1.getListCcPreguntasHdrV1(); 
					if(null!=listCcPreguntasHdrV1) {
						for(CcPreguntasHdrV1 j:listCcPreguntasHdrV1) {
							TreeNode nodeCcPreguntasHdrV1 = new DefaultTreeNode("cCPreguntaHdr",j, nodeCcExamAsignaciones);
						}
					}
				}
			}
			
			if(null!=listCcExamAsignaciones) {
				for(CcExamAsignaciones i:listCcExamAsignaciones) {
					this.setCcHdrV1(i.getCcHdrV1());
					List<CcPreguntasHdrV1> listCcPreguntasHdrV1  = i.getCcHdrV1().getListCcPreguntasHdrV1(); 
					for(CcPreguntasHdrV1 j:listCcPreguntasHdrV1) {
						this.setCcPreguntasHdrV1(j);
						this.setCcPreguntasFtaV1(j.getCcPreguntasFtaV1());
						if(Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(j.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(j.getTipoPregunta())) {
							this.setMultipleChoice(true);
							this.setListCcOpcionMultiple(j.getCcPreguntasFtaV1().getListCcOpcionMultiple());
						    if(j.getCcPreguntasFtaV1().isSingleAnswerMode()) {
						    	this.setSingleAnswerMode(true);
						    }	
						}
						break; 
					}
					break; 
				}
			}
			
		 
		 System.out.println("Finaliza CoreCasesExamForm init()");
	}

	
	public void onNodeSelect(NodeSelectEvent pEvent) {
		if(pEvent.getTreeNode() instanceof DefaultTreeNode) {
			DefaultTreeNode defaultTreeNode = (DefaultTreeNode)pEvent.getTreeNode(); 
			Object obj = defaultTreeNode.getData(); 
			if("cCExamAsignacion".equals(pEvent.getTreeNode().getType())) {
				CcHdrV1 ccHdrV1 = (CcHdrV1)obj;
				System.out.println("ccHdrV1.getNumero():"+ccHdrV1.getNumero());	
			}else if("cCPreguntaHdr".equals(pEvent.getTreeNode().getType())) {
				CcPreguntasHdrV1 ccPreguntasHdrV1 = (CcPreguntasHdrV1)obj;
				System.out.println("ccPreguntasHdrV1.getNumero():"+ccPreguntasHdrV1.getNumero());	
				environmentQuestion(ccPreguntasHdrV1.getNumero()); 
			}
			
		}
	}
	
	private void environmentQuestion(long pNumeroPreguntaHdr) {
		boolean stopFlag = false; 
		if(null!=listCcExamAsignaciones) {
			for(CcExamAsignaciones i:listCcExamAsignaciones) {
				List<CcPreguntasHdrV1> listCcPreguntasHdrV1  = i.getCcHdrV1().getListCcPreguntasHdrV1(); 
				for(CcPreguntasHdrV1 j:listCcPreguntasHdrV1) {
					if(pNumeroPreguntaHdr==j.getNumero()) {
						this.setCcHdrV1(i.getCcHdrV1());
						this.setCcPreguntasHdrV1(j);
						this.setCcPreguntasFtaV1(j.getCcPreguntasFtaV1());
						if(Utilitarios.LIMIT_RESP_TEXTO_LIBRE.equals(j.getTipoPregunta())) {
							this.setLimitedFreeTextAnswer(true);
						}else if(Utilitarios.OPCION_MULTIPLE.equals(j.getTipoPregunta())) {
							this.setMultipleChoice(true);
							this.setListCcOpcionMultiple(j.getCcPreguntasFtaV1().getListCcOpcionMultiple());
							if(j.getCcPreguntasFtaV1().isSingleAnswerMode()) {
						    	this.setSingleAnswerMode(true);
						    }	
						}
						stopFlag = true; 
					}
					if(stopFlag) {
						break;
					}
				}
				if(stopFlag) {
					break;
				}
			}
		}
	}


	public CandExamenesV1 getCandExamenesV1() {
		return candExamenesV1;
	}

	public void setCandExamenesV1(CandExamenesV1 candExamenesV1) {
		this.candExamenesV1 = candExamenesV1;
	}

	public CcExamenes getCcExamen() {
		return ccExamen;
	}

	public void setCcExamen(CcExamenes ccExamen) {
		this.ccExamen = ccExamen;
	}

	public TreeNode getRootCcExamAsignaciones() {
		return rootCcExamAsignaciones;
	}

	public void setRootCcExamAsignaciones(TreeNode rootCcExamAsignaciones) {
		this.rootCcExamAsignaciones = rootCcExamAsignaciones;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<CcExamAsignaciones> getListCcExamAsignaciones() {
		return listCcExamAsignaciones;
	}

	public void setListCcExamAsignaciones(List<CcExamAsignaciones> listCcExamAsignaciones) {
		this.listCcExamAsignaciones = listCcExamAsignaciones;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		if(multipleChoice) {
			this.setLimitedFreeTextAnswer(false);
			this.setIndicateImage(false);
		}
		this.multipleChoice = multipleChoice;
	}

	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}

	public void setLimitedFreeTextAnswer(boolean pLimitedFreeTextAnswer) {
		if(pLimitedFreeTextAnswer) {
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
		}
		this.limitedFreeTextAnswer = pLimitedFreeTextAnswer;
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

	public CcHdrV1 getCcHdrV1() {
		return ccHdrV1;
	}

	public void setCcHdrV1(CcHdrV1 ccHdrV1) {
		this.ccHdrV1 = ccHdrV1;
	}

	public CcPreguntasHdrV1 getCcPreguntasHdrV1() {
		return ccPreguntasHdrV1;
	}

	public void setCcPreguntasHdrV1(CcPreguntasHdrV1 ccPreguntasHdrV1) {
		this.ccPreguntasHdrV1 = ccPreguntasHdrV1;
	}

	public CcPreguntasFtaV1 getCcPreguntasFtaV1() {
		return ccPreguntasFtaV1;
	}

	public void setCcPreguntasFtaV1(CcPreguntasFtaV1 ccPreguntasFtaV1) {
		this.ccPreguntasFtaV1 = ccPreguntasFtaV1;
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


	public List<CcOpcionMultiple> getListCcOpcionMultiple() {
		return listCcOpcionMultiple;
	}


	public void setListCcOpcionMultiple(List<CcOpcionMultiple> listCcOpcionMultiple) {
		this.listCcOpcionMultiple = listCcOpcionMultiple;
	}
}
