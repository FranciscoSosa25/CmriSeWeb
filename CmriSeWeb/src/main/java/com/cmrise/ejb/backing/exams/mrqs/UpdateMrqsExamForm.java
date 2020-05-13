package com.cmrise.ejb.backing.exams.mrqs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoLinesLocal;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateMrqsExamForm {
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private MrqsExamenes mrqsExamenesForUpdate = new MrqsExamenes(); 
	
	private long numeroMrqsExamen;
	
	private MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr(); 
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
	private List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas = new ArrayList<MrqsPreguntasHdrV1>();
	
	
	private TreeNode rootMrqsGrupo;
	private TreeNode selectedNode;
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	MrqsExamenesLocal mrqsExamenesLocal; 
	
	@Inject 
	MrqsGrupoHdrLocal mrqsGrupoHdrLocal; 
	
	@Inject 
	MrqsGrupoLinesLocal mrqsGrupoLinesLocal; 
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza UpdateTestExamForm init()");
		
		 examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.MRQS); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroMrqsExamen = session.getAttribute("NumeroMrqsExamenSV");
		numeroMrqsExamen = utilitariosLocal.objToLong(objNumeroMrqsExamen); 
		System.out.println(" numeroMrqsExamen:"+numeroMrqsExamen);
		refreshEntity(); 
	    System.out.println("Finaliza UpdateTestExamForm init()");
	 }

	private void refreshEntity() {
		mrqsExamenesForUpdate = mrqsExamenesLocal.findByNumeroWD(this.getNumeroMrqsExamen()); 
	 
		rootMrqsGrupo = new DefaultTreeNode("Root", null);
		listMrqsGrupoHdr = mrqsGrupoHdrLocal.findByNumeroExamen(this.getNumeroMrqsExamen()); 
		for(MrqsGrupoHdr mrqsGrupoHdr:listMrqsGrupoHdr) {
			TreeNode nodeGrupoHdr = new DefaultTreeNode(mrqsGrupoHdr, rootMrqsGrupo);
			listMrqsGrupoPreguntas = mrqsGrupoLinesLocal.findByNumeroHdrWD(mrqsGrupoHdr.getNumero());
			for(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1:listMrqsGrupoPreguntas) {
				TreeNode nodeGrupoPreguntaHdr = new DefaultTreeNode(mrqsPreguntasHdrV1, nodeGrupoHdr);
			}
		}
		
	}
	
	public void update() {
		System.out.println("Entra UpdateTestExamForm update()");
		boolean updateIn = false; 
		mrqsExamenesLocal.update(mrqsExamenesForUpdate); 
		
		refreshEntity(); 
		updateIn = true; 
		
		PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);
		System.out.println("Sale UpdateTestExamForm update()");
	}
	

	public String updatePregunta(CcExamAsignaciones pCcExamAsignaciones) {
	  FacesContext context = FacesContext.getCurrentInstance(); 
	  HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	  System.out.println("pCcExamAsignaciones.getNumeroCoreCase():"+pCcExamAsignaciones.getNumeroCoreCase());
	  System.out.println("pCcExamAsignaciones.getNumeroPreguntaHdr():"+pCcExamAsignaciones.getNumeroPreguntaHdr());
	  long lNumeroCcHdr = pCcExamAsignaciones.getNumeroCoreCase();
	  long lNumeroPreguntaHdr = pCcExamAsignaciones.getNumeroPreguntaHdr();
	  session.setAttribute("NumeroCcHdrSV", lNumeroCcHdr);
      session.setAttribute("NumeroCcPreguntaHdrSV", lNumeroPreguntaHdr);
      return  "Actualizar-Pregunta-Fta-CoreCase";
	}
    
	
	public void initMrqGroup() {
		/*
		mrqsGrupoHdr.setTitulo(this.getTitulo());
		mrqsGrupoHdr.setTema(this.getTema());
		mrqsGrupoHdr.setComentarios(this.getComentarios());
		*/
	}
	
	public String addMRQsGroup() {
		
		MrqsGrupoHdrDto mrqsGrupoHdrDto = new MrqsGrupoHdrDto(); 
		mrqsGrupoHdrDto.setComentarios(mrqsGrupoHdr.getComentarios());
		mrqsGrupoHdrDto.setNumeroExamen(this.getNumeroMrqsExamen());
		mrqsGrupoHdrLocal.insert(mrqsGrupoHdrDto); 
		return "Exams-MRQs-Update"; 
	}
	
	public void onNodeSelect(NodeSelectEvent event) {
		String strEvent = event.getTreeNode().toString(); 
		if(strEvent.contains("HDR")) {
			 System.out.println("*");
			 long lNumeroGrupoHeader =  Long.parseLong(strEvent.substring(3)); 
			 FacesContext context = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 session.setAttribute("NumeroMrqsExamenSV", this.getNumeroMrqsExamen());
			 session.setAttribute("NumeroMrqsGrupoSV", lNumeroGrupoHeader);
			 context.getApplication().getNavigationHandler().handleNavigation(context, null, "Exams-MRQs-Update-Group");
		     return; 
		}else if(strEvent.contains("LINE")) {
			
		}
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public String assignCandidates() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", this.getNumeroMrqsExamen());
		return "Assign-MRQs-Candidates"; 
	}
	
	public String createNewCandidates() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", this.getNumeroMrqsExamen());
		return "Create-MRQs-Candidates"; 
	}
	
	
	public String cancel() {
		return "Exams-MRQs-Manage"; 
	}
	
	public long getNumeroMrqsExamen() {
		return numeroMrqsExamen;
	}

	public void setNumeroMrqsExamen(long numeroMrqsExamen) {
		this.numeroMrqsExamen = numeroMrqsExamen;
	}

	
	public MrqsGrupoHdr getMrqsGrupoHdr() {
		return mrqsGrupoHdr;
	}

	public void setMrqsGrupoHdr(MrqsGrupoHdr mrqsGrupoHdr) {
		this.mrqsGrupoHdr = mrqsGrupoHdr;
	}

	public List<MrqsGrupoHdr> getListMrqsGrupoHdr() {
		return listMrqsGrupoHdr;
	}

	public void setListMrqsGrupoHdr(List<MrqsGrupoHdr> listMrqsGrupoHdr) {
		this.listMrqsGrupoHdr = listMrqsGrupoHdr;
	}

	public TreeNode getRootMrqsGrupo() {
		return rootMrqsGrupo;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<MrqsPreguntasHdrV1> getListMrqsGrupoPreguntas() {
		return listMrqsGrupoPreguntas;
	}

	public void setListMrqsGrupoPreguntas(List<MrqsPreguntasHdrV1> listMrqsGrupoPreguntas) {
		this.listMrqsGrupoPreguntas = listMrqsGrupoPreguntas;
	}

	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	}

	public List<SelectItem> getSelectExamenesHdr() {
		return selectExamenesHdr;
	}

	public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
		this.selectExamenesHdr = selectExamenesHdr;
	}

	public MrqsExamenes getMrqsExamenesForUpdate() {
		return mrqsExamenesForUpdate;
	}

	public void setMrqsExamenesForUpdate(MrqsExamenes mrqsExamenesForUpdate) {
		this.mrqsExamenesForUpdate = mrqsExamenesForUpdate;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}
	 
}
