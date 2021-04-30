package com.cmrise.ejb.backing.exams.corecases;

import java.io.IOException;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.exams.CcExamAsignacionesLocal;
import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateTestExamForm {

	private long numeroCcExamen;
	private String estatus; 
	private String descripcion; 
	private long idTipoExamen; 
	private String visibilidad; 
	private Date fechaEfectivaDesde; 
	private Date fechaEfectivaHasta; 
	private short limiteTiempo; 
	private boolean saltarCasos; 
	private boolean mostrarRespuestas; 
	private String mensajeFinalizacion; 
	private String nombreCaso;
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>();
	private List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
	
	private TreeNode rootCcExamAsignaciones;
	private TreeNode selectedNode;
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	CcExamenesLocal ccExamenesLocal; 
	
	@Inject 
	CcExamAsignacionesLocal ccExamAsignacionesLocal;
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal;
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin;
	
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza UpdateTestExamForm init()");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroCcExamen = session.getAttribute("NumeroCcExamenSV");
		numeroCcExamen = utilitariosLocal.objToLong(objNumeroCcExamen); 
		System.out.println(" numeroCcExamen:"+ numeroCcExamen);
		examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.CORE_CASES); 
		 
		for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }		
		
		refreshEntity(); 
	    System.out.println("Finaliza UpdateTestExamForm init()");
	 }

	private void refreshEntity() {
		CcExamenes ccExamenesObjMod = ccExamenesLocal.findByNumeroObjMod(this.getNumeroCcExamen()); 
		
		this.setEstatus(ccExamenesObjMod.getEstatus());
		this.setDescripcion(ccExamenesObjMod.getDescripcion());
		this.setIdTipoExamen(ccExamenesObjMod.getIdTipoExamen());
		this.setVisibilidad(ccExamenesObjMod.getVisibilidad());
		this.setFechaEfectivaDesde(ccExamenesObjMod.getFechaEfectivaDesde());
		this.setFechaEfectivaHasta(ccExamenesObjMod.getFechaEfectivaHasta());
		this.setLimiteTiempo(ccExamenesObjMod.getTiempoLimite());
		this.setSaltarCasos(ccExamenesObjMod.getSaltarCasos());
		this.setMostrarRespuestas(ccExamenesObjMod.getMostrarRespuestas());
		this.setMensajeFinalizacion(ccExamenesObjMod.getMensajeFinalizacion());
		
		//listCcExamAsignaciones = ccExamAsignacionesLocal.findByNumeroExamenWD(this.getNumeroCcExamen());
		listCcExamAsignaciones = ccExamenesObjMod.getListCcExamAsignaciones(); 
		
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
		
	}
	
	public void update() {
		
		System.out.println("Entra UpdateTestExamForm update()");
		boolean updateIn = false; 
		CcExamenesDto ccExamenesUpdateDto = new CcExamenesDto();
		
		ccExamenesUpdateDto.setNumero(this.getNumeroCcExamen());
		ccExamenesUpdateDto.setEstatus(this.getEstatus());
		ccExamenesUpdateDto.setDescripcion(this.getDescripcion());
		ccExamenesUpdateDto.setIdTipoExamen(this.getIdTipoExamen());
		ccExamenesUpdateDto.setVisibilidad(this.getVisibilidad());
		ccExamenesUpdateDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(this.getFechaEfectivaDesde()));
		if(null!=this.getFechaEfectivaHasta()) {
			ccExamenesUpdateDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(this.getFechaEfectivaHasta()));
		}else {
			ccExamenesUpdateDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}
		ccExamenesUpdateDto.setActualizadoPor(userLogin.getNumeroUsuario());
		ccExamenesUpdateDto.setTiempoLimite(this.getLimiteTiempo());
		ccExamenesUpdateDto.setSaltarCasos(this.isSaltarCasos());
		ccExamenesUpdateDto.setMostrarRespuestas(this.isMostrarRespuestas());
		ccExamenesUpdateDto.setMensajeFinalizacion(this.getMensajeFinalizacion());
		try {
			ccExamenesLocal.update(this.getNumeroCcExamen(), ccExamenesUpdateDto);
			refreshEntity(); 
			updateIn = true; 
			PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);
			System.out.println("Sale UpdateTestExamForm update()");		
			salir();
		}
		catch(Exception e) {
			System.out.println("ActualizaUsuario: Error "+ e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cambios no realizados", e.getMessage()));
	        
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
    
	
	public String addCoreCaseGroup() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", this.numeroCcExamen);
		return "Exams-CoreCases-Update-AddCoreCase"; 
	}
	
	public String assignCandidates() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroMrqsExamenSV", this.getNumeroCcExamen());
		session.setAttribute("tipoExamen", Utilitarios.CORE_CASES);
		session.setAttribute("nombrePantalla", "Exams-CoreCases-Update");
		return "Assign-MRQs-Candidates"; 
	}
	
	public void salir() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);		
		System.out.println("Finaliza UpdateUsuarioForm init()");
		context.getExternalContext().redirect("/CmriSeWeb/faces/cmrise/examenes/corecases/ManageTestExam.xhtml");
	}
	
	public String cancel() {
		return "Exams-CoreCases-Manage"; 
	}
	
	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getIdTipoExamen() {
		return idTipoExamen;
	}

	public void setIdTipoExamen(long idTipoExamen) {
		this.idTipoExamen = idTipoExamen;
	}

	public String getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}

	public Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}

	public short getLimiteTiempo() {
		return limiteTiempo;
	}

	public void setLimiteTiempo(short limiteTiempo) {
		this.limiteTiempo = limiteTiempo;
	}

	public boolean isSaltarCasos() {
		return saltarCasos;
	}

	public void setSaltarCasos(boolean saltarCasos) {
		this.saltarCasos = saltarCasos;
	}

	public boolean isMostrarRespuestas() {
		return mostrarRespuestas;
	}

	public void setMostrarRespuestas(boolean mostrarRespuestas) {
		this.mostrarRespuestas = mostrarRespuestas;
	}

	public String getMensajeFinalizacion() {
		return mensajeFinalizacion;
	}

	public void setMensajeFinalizacion(String mensajeFinalizacion) {
		this.mensajeFinalizacion = mensajeFinalizacion;
	}

	public List<CcExamAsignaciones> getListCcExamAsignaciones() {
		return listCcExamAsignaciones;
	}

	public void setListCcExamAsignaciones(List<CcExamAsignaciones> listCcExamAsignaciones) {
		this.listCcExamAsignaciones = listCcExamAsignaciones;
	}

	public String getNombreCaso() {
		return nombreCaso;
	}

	public void setNombreCaso(String nombreCaso) {
		this.nombreCaso = nombreCaso;
	}


	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getRootCcExamAsignaciones() {
		return rootCcExamAsignaciones;
	}

	public void setRootCcExamAsignaciones(TreeNode rootCcExamAsignaciones) {
		this.rootCcExamAsignaciones = rootCcExamAsignaciones;
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
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	 
}
