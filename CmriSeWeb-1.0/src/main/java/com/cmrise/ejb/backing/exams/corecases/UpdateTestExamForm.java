package com.cmrise.ejb.backing.exams.corecases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import javax.ws.rs.client.ClientRequestContext;

import org.apache.commons.fileupload.RequestContext;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.admin.TablasUtilitariasValoresLocal;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.ejb.services.exams.CcExamAsignacionesLocal;
import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;
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
	private Date fechaCreacion;
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>();
	private List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
	private List<CcHdrV1> listCcHdrV1 = new ArrayList<CcHdrV1>();
	private List<CcHdrV1> selectedlistCcHdrV1 = new ArrayList<CcHdrV1>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdrCC = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>();
	private List<SelectItem> selectEstatusCC = new ArrayList<SelectItem>();
	private List<AdmonMateriaHdr> materiasHdrDelete = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMateriasDelete = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdrDelete = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdrDelete = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMateriasDelete = new ArrayList<SelectItem>();
	private List<SelectItem> selectEstatusDelete = new ArrayList<SelectItem>();
	private List<CcExamAsignaciones> listCcHdrV1Delete = new ArrayList<CcExamAsignaciones>();
	private List<CcExamAsignaciones> selectedlistCcHdrV1Delete = new ArrayList<CcExamAsignaciones>(); 
	private long admonMateriaDelete; 
	private long admonSubmateriaDelete;
	private long admonMateria; 
	private long admonSubmateria;
	private String admonEstatus;
	private String admonEstatusDelete;	
	
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
	
	@Inject 
	AdmonSubMateriaLocal admonSubMateriaLocal;
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal;
	
	@Inject 
	CcHdrLocal ccHdrLocal;
	
	@Inject
	CcExamAsignacionesLocal ccExamAsinacionesLocal;
	
	@Inject 
	TablasUtilitariasValoresLocal tablasUtilitariasValoresLocal;
	
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
		this.setFechaCreacion(ccExamenesObjMod.getFechaCreacion());
		
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
		boolean updateIn = false; 
		CcExamenesDto ccExamenesUpdateDto = new CcExamenesDto();		
		ccExamenesUpdateDto.setNumero(this.getNumeroCcExamen());
		ccExamenesUpdateDto.setEstatus(this.getEstatus());
		ccExamenesUpdateDto.setDescripcion(this.getDescripcion());
		ccExamenesUpdateDto.setIdTipoExamen(this.getIdTipoExamen());
		ccExamenesUpdateDto.setVisibilidad(this.getVisibilidad());
		
		ccExamenesUpdateDto.setFechaEfectivaDesde(new java.sql.Timestamp(this.getFechaEfectivaDesde().getTime()));
		if(null!=this.getFechaEfectivaHasta()) {
			ccExamenesUpdateDto.setFechaEfectivaHasta(new java.sql.Timestamp(this.getFechaEfectivaHasta().getTime()));
		}else {
			ccExamenesUpdateDto.setFechaEfectivaHasta(new java.sql.Timestamp(Utilitarios.endOfTime.getTime()));
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(ccExamenesUpdateDto.getFechaEfectivaHasta() != null) {
			if(ccExamenesUpdateDto.getFechaEfectivaDesde().equals(ccExamenesUpdateDto.getFechaEfectivaHasta()) || 
					!ccExamenesUpdateDto.getFechaEfectivaDesde().before(ccExamenesUpdateDto.getFechaEfectivaHasta())) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"La fecha efectiva desde no puede ser igual o menor que la fecha efectiva hasta"));
				System.out.println("error de fechas!");
			}
		}
		
		ccExamenesUpdateDto.setActualizadoPor(userLogin.getNumeroUsuario());
		ccExamenesUpdateDto.setTiempoLimite(this.getLimiteTiempo());
		ccExamenesUpdateDto.setSaltarCasos(this.isSaltarCasos());
		ccExamenesUpdateDto.setMostrarRespuestas(this.isMostrarRespuestas());
		ccExamenesUpdateDto.setMensajeFinalizacion(this.getMensajeFinalizacion());
		
		try {
			
			if(listCcHdrV1Delete.size()<1) {	
				System.out.println("listCcHdrV1Delete.size()=" + listCcHdrV1Delete.size());
				context.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Por favor agregue al menos un Caso Clínico al examen"));
			}else {
				ccExamenesLocal.update(this.getNumeroCcExamen(), ccExamenesUpdateDto);
				refreshEntity(); 
				updateIn = true; 
				PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);						
			}
		}
		catch(Exception e) {
			System.out.println("ActualizaUsuario: Error "+ e.getMessage());
	        context.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cambios no realizados", e.getMessage()));	        
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
	    context.getExternalContext().redirect("/CmriSeWeb/faces/cmrise/examenes/corecases/ManageTestExam.xhtml");		
	}
	
	public void onAdmonChangeAdd() {				
		selectedlistCcHdrV1 = new ArrayList<CcHdrV1>();		
	}
	
	public void onAdmonChangeDelete() {				
		selectedlistCcHdrV1Delete = new ArrayList<CcExamAsignaciones>();
	}
	
	public void onAdmonMateriaChange() {		
		if(0!=admonMateria) {			
			selectedlistCcHdrV1 = new ArrayList<CcHdrV1>();
			subMaterias = admonSubMateriaLocal.findByNumeroMateria(admonMateria); 
			selectSubMaterias = new ArrayList<SelectItem>(); 
			for(AdmonSubMateria i:subMaterias) {				
				SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				selectSubMaterias.add(selectItem); 
			}
		}else {
			selectedlistCcHdrV1 = new ArrayList<CcHdrV1>();
			selectSubMaterias = new ArrayList<SelectItem>(); 
		}
	}
	
	public void onAdmonMateriaChangeDelete() {		
		if(0!=admonMateriaDelete) {	
			selectedlistCcHdrV1Delete = new ArrayList<CcExamAsignaciones>();
			subMateriasDelete = admonSubMateriaLocal.findByNumeroMateria(admonMateriaDelete); 
			selectSubMateriasDelete = new ArrayList<SelectItem>(); 
			for(AdmonSubMateria i:subMateriasDelete) {
				SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				selectSubMateriasDelete.add(selectItem); 
			}
		}else {
			selectedlistCcHdrV1Delete = new ArrayList<CcExamAsignaciones>();
			selectSubMateriasDelete = new ArrayList<SelectItem>();
		}
	}
	
	public void resetModalAdd() {
		materiasHdr = null;
		selectedlistCcHdrV1 = new ArrayList<CcHdrV1>();
		listCcHdrV1 = ccHdrLocal.findCCNotInExam(numeroCcExamen, getIdTipoExamen());
		selectMateriasHdr = new ArrayList<SelectItem>();
		selectEstatusCC = new ArrayList<SelectItem>();
		this.admonMateria = 0;
		this.admonSubmateria = 0;
		this.admonEstatus = "";
		
		materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(getIdTipoExamen()); 
		selectMateriasHdr = new ArrayList<SelectItem>(); 
		for(AdmonMateriaHdr i:materiasHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectMateriasHdr.add(selectItem); 
		}
		
		List<TablasUtilitariasValoresDto> listEstatusCCValores =  tablasUtilitariasValoresLocal.findByTipoTabla("ESTATUS_CC");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusCCValores = listEstatusCCValores.iterator(); 
		while(iterEstatusCCValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusCCValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			selectEstatusCC.add(selectItem); 
		}
	}	
	
	public void resetModalDelete() {
		selectedlistCcHdrV1Delete = new ArrayList<CcExamAsignaciones>();
		selectMateriasHdrDelete = new ArrayList<SelectItem>();
		selectEstatusDelete = new ArrayList<SelectItem>();
		this.admonMateriaDelete = 0;
		this.admonSubmateriaDelete = 0;
		this.admonEstatusDelete = "";
		listCcHdrV1Delete = ccHdrLocal.findCCInExam(numeroCcExamen);
		
		materiasHdrDelete = admonMateriaHdrLocal.findByNumeroAdmonExamen(getIdTipoExamen());   
		for(AdmonMateriaHdr i:materiasHdrDelete) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectMateriasHdrDelete.add(selectItem); 
		}
	
		List<TablasUtilitariasValoresDto> listEstatusCCValores =  tablasUtilitariasValoresLocal.findByTipoTabla("ESTATUS_CC");  
		Iterator<TablasUtilitariasValoresDto> iterEstatusCCValores = listEstatusCCValores.iterator(); 
		while(iterEstatusCCValores.hasNext()) {
			TablasUtilitariasValoresDto tablasUtilitariasValoresDto = iterEstatusCCValores.next();
			SelectItem selectItem = new SelectItem(tablasUtilitariasValoresDto.getCodigoTabla(),tablasUtilitariasValoresDto.getSignificado()); 
			selectEstatusDelete.add(selectItem);
		}
	}
	
	public void addCCtoExam() {
		for(CcHdrV1 cchdrV1:selectedlistCcHdrV1) {
			CcExamAsignacionesDto ccExamAsigDto = new CcExamAsignacionesDto(); 
			ccExamAsigDto.setNumeroCcExamen(numeroCcExamen);
			ccExamAsigDto.setNumeroCoreCase(cchdrV1.getNumero());			
			ccExamAsigDto.setCreadoPor(userLogin.getNumeroUsuario());
			ccExamAsigDto.setActualizadoPor(userLogin.getNumeroUsuario());
			ccExamAsigDto.setFechaEfectivaDesde(cchdrV1.getFechaEfectivaDesde());
			ccExamAsigDto.setFechaEfectivaHasta(cchdrV1.getFechaEfectivaHasta());
			ccExamAsinacionesLocal.insert(ccExamAsigDto); 
			
		}		
		refreshEntity();
	}
	
	public void deleteCCtoExam() {
		for(CcExamAsignaciones cchdrV1:selectedlistCcHdrV1Delete) {
			ccExamAsinacionesLocal.delete(cchdrV1.getNumero()); 
		}
		refreshEntity();
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<CcHdrV1> getListCcHdrV1() {
		return listCcHdrV1;
	}

	public void setListCcHdrV1(List<CcHdrV1> listCcHdrV1) {
		this.listCcHdrV1 = listCcHdrV1;
	}

	public List<CcHdrV1> getSelectedlistCcHdrV1() {
		return selectedlistCcHdrV1;
	}

	public void setSelectedlistCcHdrV1(List<CcHdrV1> selectedlistCcHdrV1) {
		this.selectedlistCcHdrV1 = selectedlistCcHdrV1;
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

	public List<SelectItem> getSelectExamenesHdrCC() {
		return selectExamenesHdrCC;
	}

	public void setSelectExamenesHdrCC(List<SelectItem> selectExamenesHdrCC) {
		this.selectExamenesHdrCC = selectExamenesHdrCC;
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

	public long getAdmonMateria() {
		return admonMateria;
	}

	public void setAdmonMateria(long admonMateria) {
		this.admonMateria = admonMateria;
	}

	public List<AdmonMateriaHdr> getMateriasHdrDelete() {
		return materiasHdrDelete;
	}

	public void setMateriasHdrDelete(List<AdmonMateriaHdr> materiasHdrDelete) {
		this.materiasHdrDelete = materiasHdrDelete;
	}

	public List<AdmonSubMateria> getSubMateriasDelete() {
		return subMateriasDelete;
	}

	public void setSubMateriasDelete(List<AdmonSubMateria> subMateriasDelete) {
		this.subMateriasDelete = subMateriasDelete;
	}

	public List<SelectItem> getSelectExamenesHdrDelete() {
		return selectExamenesHdrDelete;
	}

	public void setSelectExamenesHdrDelete(List<SelectItem> selectExamenesHdrDelete) {
		this.selectExamenesHdrDelete = selectExamenesHdrDelete;
	}

	public List<SelectItem> getSelectMateriasHdrDelete() {
		return selectMateriasHdrDelete;
	}

	public void setSelectMateriasHdrDelete(List<SelectItem> selectMateriasHdrDelete) {
		this.selectMateriasHdrDelete = selectMateriasHdrDelete;
	}

	public List<SelectItem> getSelectSubMateriasDelete() {
		return selectSubMateriasDelete;
	}

	public void setSelectSubMateriasDelete(List<SelectItem> selectSubMateriasDelete) {
		this.selectSubMateriasDelete = selectSubMateriasDelete;
	}

	public long getAdmonMateriaDelete() {
		return admonMateriaDelete;
	}

	public void setAdmonMateriaDelete(long admonMateriaDelete) {
		this.admonMateriaDelete = admonMateriaDelete;
	}

	public List<CcExamAsignaciones> getListCcHdrV1Delete() {
		return listCcHdrV1Delete;
	}

	public void setListCcHdrV1Delete(List<CcExamAsignaciones> listCcHdrV1Delete) {
		this.listCcHdrV1Delete = listCcHdrV1Delete;
	}

	public List<CcExamAsignaciones> getSelectedlistCcHdrV1Delete() {
		return selectedlistCcHdrV1Delete;
	}

	public void setSelectedlistCcHdrV1Delete(List<CcExamAsignaciones> selectedlistCcHdrV1Delete) {
		this.selectedlistCcHdrV1Delete = selectedlistCcHdrV1Delete;
	}

	public long getAdmonSubmateria() {
		return admonSubmateria;
	}

	public void setAdmonSubmateria(long admonSubmateria) {
		this.admonSubmateria = admonSubmateria;
	}

	public long getAdmonSubmateriaDelete() {
		return admonSubmateriaDelete;
	}

	public void setAdmonSubmateriaDelete(long admonSubmateriaDelete) {
		this.admonSubmateriaDelete = admonSubmateriaDelete;
	}

	public String getAdmonEstatus() {
		return admonEstatus;
	}

	public void setAdmonEstatus(String admonEstatus) {
		this.admonEstatus = admonEstatus;
	}

	public String getAdmonEstatusDelete() {
		return admonEstatusDelete;
	}

	public void setAdmonEstatusDelete(String admonEstatusDelete) {
		this.admonEstatusDelete = admonEstatusDelete;
	}

	public List<SelectItem> getSelectEstatusCC() {
		return selectEstatusCC;
	}

	public void setSelectEstatusCC(List<SelectItem> selectEstatusCC) {
		this.selectEstatusCC = selectEstatusCC;
	}

	public List<SelectItem> getSelectEstatusDelete() {
		return selectEstatusDelete;
	}

	public void setSelectEstatusDelete(List<SelectItem> selectEstatusDelete) {
		this.selectEstatusDelete = selectEstatusDelete;
	}

}
