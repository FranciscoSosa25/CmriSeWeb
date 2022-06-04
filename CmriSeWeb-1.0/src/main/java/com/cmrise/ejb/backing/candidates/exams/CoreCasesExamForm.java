package com.cmrise.ejb.backing.candidates.exams;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
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
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.ejb.model.exams.CandExamStatusEnum;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.ejb.services.candidates.exams.CandExamRespuestasLocal;
import com.cmrise.ejb.services.candidates.exams.CandExamenesLocal;
import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ManagedBean
@ViewScoped
public class CoreCasesExamForm {

	private CandExamenesV1 candExamenesV1 = new CandExamenesV1(); 
	private CandExamRespuestasV1 candExamRespuestasV1 = new CandExamRespuestasV1(); 
	private CcExamenes ccExamen = new CcExamenes(); 
	private TreeNode rootCcExamAsignaciones;
	private TreeNode selectedNode;
	private List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
	private CcHdrV1 ccHdrV1; 
	private CcPreguntasHdrV1 ccPreguntasHdrV1; 
	private CcPreguntasFtaV1 ccPreguntasFtaV1; 
	private List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
	private CandExamRespSkipDto candExamRespSkipDto = new CandExamRespSkipDto();
	
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
	private long numCand;
	
	private boolean singleAnswerMode;
	private boolean suffleAnswerOrder; 
	
	 
	
	/********************************************************************
	 * Attributos Imagenes 
	 */
	private List<CcImagenesGrp> listPresentCcImagenesGrp = new ArrayList<CcImagenesGrp>(); 
	
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
		numeroCandExamen = utilitariosLocal.objToLong(objNumeroCandExamen);
		candExamRespSkipDto.setNumeroCandExamen(numeroCandExamen);
		this.candExamenesV1 = candExamenesLocal.findByNumero(numeroCandExamen); 
		Object objNumeroCcExamenSV = session.getAttribute("NumeroCcExamenSV"); 
		numeroCcExamen = utilitariosLocal.objToLong(objNumeroCcExamenSV); 
		 
		setIdxReactivos(0);
		setIdxGrupo(0);
		setFlag2(false);
		setReactivosSize(0);
		setHdrGrupoSize(0);
		 
		//this.mrqsExamen = mrqsExamenesLocal.findByIdWD(numeroMRQsExamen,numCand); 
		candExamRespSkipDto.setExamen(numeroCcExamen);
		ccExamen = ccExamenesLocal.findByNumeroObjModCand(numeroCcExamen); 
		numCand = Long.valueOf(session.getAttribute("numCand").toString()).longValue();
		System.out.println("Numero buscado de examen: "+numCand);
		 
		Date date = new Date();  
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		strDate= formatter.format(date);
		Short intTime = Short.parseShort(String.valueOf(Integer.parseInt(session.getAttribute("tiempoExamen").toString())*sSegundos));
		ccExamen.setTiempoLimite(intTime);
		 
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
						this.setListPresentCcImagenesGrp(j.getCcPreguntasFtaV1().getListCcImagenesGrp());
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
						this.setListPresentCcImagenesGrp(j.getCcPreguntasFtaV1().getListCcImagenesGrp());
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

	public void onTimeout() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FIN DE TIEMPO", "Fin de tiempo"));
		CandExamenesDto candExamenesDto = new CandExamenesDto();
		candExamenesDto.setEstatus(CandExamStatusEnum.REVISADO.getStatus());
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


	public long getNumeroCandExamen() {
		return numeroCandExamen;
	}


	public void setNumeroCandExamen(long numeroCandExamen) {
		this.numeroCandExamen = numeroCandExamen;
	}


	public boolean isFlag2() {
		return flag2;
	}


	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
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


	public long getHdrGrupoSize() {
		return hdrGrupoSize;
	}


	public void setHdrGrupoSize(long hdrGrupoSize) {
		this.hdrGrupoSize = hdrGrupoSize;
	}


	public int getIdxGrupo() {
		return idxGrupo;
	}


	public void setIdxGrupo(int idxGrupo) {
		this.idxGrupo = idxGrupo;
	}


	public boolean isNuevaBusqueda() {
		return nuevaBusqueda;
	}


	public void setNuevaBusqueda(boolean nuevaBusqueda) {
		this.nuevaBusqueda = nuevaBusqueda;
	}


	public int getIdxSkip() {
		return idxSkip;
	}


	public void setIdxSkip(int idxSkip) {
		this.idxSkip = idxSkip;
	}


	public boolean isBusquedaSkip() {
		return busquedaSkip;
	}


	public void setBusquedaSkip(boolean busquedaSkip) {
		this.busquedaSkip = busquedaSkip;
	}


	public int getSkipMax() {
		return skipMax;
	}


	public void setSkipMax(int skipMax) {
		this.skipMax = skipMax;
	}


	public boolean isShowFinalMessage() {
		return showFinalMessage;
	}


	public void setShowFinalMessage(boolean showFinalMessage) {
		this.showFinalMessage = showFinalMessage;
	}


	public int getLimiteCaracteres() {
		return limiteCaracteres;
	}


	public void setLimiteCaracteres(int limiteCaracteres) {
		this.limiteCaracteres = limiteCaracteres;
	}


	public String getTipoPregunta() {
		return tipoPregunta;
	}


	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}


	public CandExamRespuestasV1 getCandExamRespuestasV1() {
		return candExamRespuestasV1;
	}


	public void setCandExamRespuestasV1(CandExamRespuestasV1 candExamRespuestasV1) {
		this.candExamRespuestasV1 = candExamRespuestasV1;
	}


	public long getNumeroPreguntaFta() {
		return numeroPreguntaFta;
	}


	public void setNumeroPreguntaFta(long numeroPreguntaFta) {
		this.numeroPreguntaFta = numeroPreguntaFta;
	}


	public Short getsSegundos() {
		return sSegundos;
	}


	public void setsSegundos(Short sSegundos) {
		this.sSegundos = sSegundos;
	}


	public String getTimerValue() {
		return timerValue;
	}


	public void setTimerValue(String timerValue) {
		this.timerValue = timerValue;
	}


	public String getStrDate() {
		return strDate;
	}


	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}


	public long getNumCand() {
		return numCand;
	}


	public void setNumCand(long numCand) {
		this.numCand = numCand;
	}


	public CandExamRespSkipDto getCandExamRespSkipDto() {
		return candExamRespSkipDto;
	}


	public void setCandExamRespSkipDto(CandExamRespSkipDto candExamRespSkipDto) {
		this.candExamRespSkipDto = candExamRespSkipDto;
	}
}
