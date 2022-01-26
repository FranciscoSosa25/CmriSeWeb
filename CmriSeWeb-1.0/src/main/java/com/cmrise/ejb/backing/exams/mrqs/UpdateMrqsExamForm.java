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

import com.cmrise.ejb.backing.mrq.UpdateFTAMrqForm;
import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
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
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	
	private MrqsExamenes mrqsExamenesForUpdate = new MrqsExamenes(); 	
	private long numeroMrqsExamen;	
	private MrqsGrupoHdr mrqsGrupoHdrForInsert = new MrqsGrupoHdr(); 	
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
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal; 
	
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
		mrqsExamenesForUpdate = mrqsExamenesLocal.findByNumeroWD(this.getNumeroMrqsExamen(),userLogin.getNumeroUsuario()); 
		onAdmonExamenChange(); 
		
		/***************************************************************************
		rootMrqsGrupo = new DefaultTreeNode("Root", null);
		listMrqsGrupoHdr = mrqsGrupoHdrLocal.findByNumeroExamen(this.getNumeroMrqsExamen()); 
		for(MrqsGrupoHdr mrqsGrupoHdr:listMrqsGrupoHdr) {
			TreeNode nodeGrupoHdr = new DefaultTreeNode(mrqsGrupoHdr, rootMrqsGrupo);
			listMrqsGrupoPreguntas = mrqsGrupoLinesLocal.findByNumeroHdrWD(mrqsGrupoHdr.getNumero());
			for(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1:listMrqsGrupoPreguntas) {
				TreeNode nodeGrupoPreguntaHdr = new DefaultTreeNode(mrqsPreguntasHdrV1, nodeGrupoHdr);
			}
		}
		************************************************************/
		
	}
	
	public boolean update() {
		System.out.println("Entra UpdateTestExamForm update()");
		boolean updateIn = false;
		
		FacesContext context = FacesContext.getCurrentInstance();
		// Valida si la fecha efectiva hasta es mayor que la fecha efectiva desde, de lo
		// contrario muestra el error en la pantalla.
		if (mrqsExamenesForUpdate.getFechaEfectivaHasta() != null) {
			if (mrqsExamenesForUpdate.getFechaEfectivaDesde().equals(mrqsExamenesForUpdate.getFechaEfectivaHasta())
					|| !mrqsExamenesForUpdate.getFechaEfectivaDesde()
							.before(mrqsExamenesForUpdate.getFechaEfectivaHasta())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La fecha efectiva desde no puede ser igual o menor que la fecha efectiva hasta"));
				return updateIn;
			}
		}
		
		String mensajeError = "";
		boolean vacio = true;
		for(MrqsGrupoHdr i : mrqsExamenesForUpdate.getListMrqsGrupoHdr()){
			if(i.getNumeroReactivos() ==  0 ) {
				vacio = false;
				mensajeError += "Agregue por lo menos una pregunta a la materia: "+ i.getAdmonMateriaDesc() + '\n';
			}
		}
		
		if(vacio){
			mrqsExamenesLocal.update(mrqsExamenesForUpdate);
			refreshEntity();
			updateIn = true;	
			PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);
					
			return updateIn;
		}
		else {
			System.out.println("una de las materias no tiene preguntas agregadas");
			context.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mensajeError));
			return vacio;
		}
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
    
	
	public void onAdmonExamenChange() {
		if(0!=mrqsExamenesForUpdate.getAdmonExamen()) {
			materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(mrqsExamenesForUpdate.getAdmonExamen()); 
			selectMateriasHdr = new ArrayList<SelectItem>();  
			for(AdmonMateriaHdr i:materiasHdr) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectMateriasHdr.add(selectItem); 
			}
		}
	}
	
	
	
	public String addMRQsGroup() {
		mrqsGrupoHdrForInsert.setCreadoPor(userLogin.getNumeroUsuario());
		mrqsGrupoHdrForInsert.setActualizadoPor(userLogin.getNumeroUsuario());
		mrqsGrupoHdrForInsert.setFechaCreacion(new java.util.Date());
		mrqsGrupoHdrForInsert.setFechaActualizacion(new java.util.Date());
		mrqsGrupoHdrForInsert.setNumeroExamen(mrqsExamenesForUpdate.getNumero());
		long numeroMrqGrupo = mrqsGrupoHdrLocal.insert(mrqsGrupoHdrForInsert); 
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
		session.setAttribute("tipoExamen", Utilitarios.MRQS);
		session.setAttribute("nombrePantalla", "Exams-MRQs-Update");
		return "Assign-MRQs-Candidates"; 
	}
	
	public String createNewCandidates() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", this.getNumeroMrqsExamen());
		return "Create-MRQs-Candidates"; 
	}
	
	public String onMateriaSelect(MrqsGrupoHdr pMrqsGrupoHdr) {
		 FacesContext context = FacesContext.getCurrentInstance();
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 session.setAttribute("NumeroMrqsExamenSV", this.getNumeroMrqsExamen());
		 session.setAttribute("NumeroMrqsGrupoSV", pMrqsGrupoHdr.getNumero());
		 return "Exams-MRQs-Update-Group"; 
	}
	
	public String toPreviewExamenReactivos() {
   	 FacesContext context = FacesContext.getCurrentInstance(); 
   	 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
   	 session.setAttribute("NumeroMrqsExamenSV", mrqsExamenesForUpdate.getNumero());
     /*return "Preview-Examen-Reactivos";*/
   	 return "Prev-Exam-React-OneByOne";
    }
	
	public String validateNoOfQuestions() {
		FacesContext context = FacesContext.getCurrentInstance();

		MrqsExamenes exam = mrqsExamenesLocal.findObjMod(getNumeroMrqsExamen());
		
		//BUG: Despues de crear examen de reactivo y al cerrar la pantalla de actualizar SIEMPRE marca error dado q no se han ligado preguntas
		// y no es posible hacerlo de ninguna forma desde la misma pagina actualizar
		System.out.println("getNumeroReactivos: "+exam.getListMrqsGrupoHdr().get(0).getListMrqsGrupoLines().size());
		//if(exam.getListMrqsGrupoHdr().get(0).getListMrqsGrupoLines().size() <=0)
			//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				//"DEBE TENER AL MENOS UN REACTIVO ASIGNADO AL EXAMEN"));
		
		//else {
			return "Exams-MRQs-Manage";
		//}
		//return null;
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

	public TreeNode getRootMrqsGrupo() {
		return rootMrqsGrupo;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
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

	public List<AdmonMateriaHdr> getMateriasHdr() {
		return materiasHdr;
	}

	public void setMateriasHdr(List<AdmonMateriaHdr> materiasHdr) {
		this.materiasHdr = materiasHdr;
	}

	public List<SelectItem> getSelectMateriasHdr() {
		return selectMateriasHdr;
	}

	public void setSelectMateriasHdr(List<SelectItem> selectMateriasHdr) {
		this.selectMateriasHdr = selectMateriasHdr;
	}

	public MrqsGrupoHdr getMrqsGrupoHdrForInsert() {
		return mrqsGrupoHdrForInsert;
	}

	public void setMrqsGrupoHdrForInsert(MrqsGrupoHdr mrqsGrupoHdrForInsert) {
		this.mrqsGrupoHdrForInsert = mrqsGrupoHdrForInsert;
	}
	 
}
