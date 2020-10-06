package com.cmrise.ejb.backing.exams.corecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.CcExamenes;
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
	private String titulo; 
	private String nombre; 
	private String descripcion; 
	private String tipoPregunta; 
	private String tipoExamen; 
	private String tema; 
	private String comentarios; 
	private String visibilidad; 
	private Date fechaEfectivaDesde; 
	private Date fechaEfectivaHasta; 
	private short limiteTiempo; 
	private boolean saltarPreguntas; 
	private boolean saltarCasos; 
	private boolean mostrarRespuestas; 
	private boolean tienePassmark; 
	private boolean aleatorioGrupo; 
	private boolean aleatorioPreguntas; 
	private boolean seleccionCasosAleatorios; 
	private String mensajeFinalizacion; 
	private boolean confirmacionAsistencia; 
	private boolean diploma;
	private String nombreCaso;
	
	private List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
	
	private TreeNode rootCcExamAsignaciones;
	private TreeNode selectedNode;
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	CcExamenesLocal ccExamenesLocal; 
	
	@Inject 
	CcExamAsignacionesLocal ccExamAsignacionesLocal;
	
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza UpdateTestExamForm init()");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroCcExamen = session.getAttribute("NumeroCcExamenSV");
		numeroCcExamen = utilitariosLocal.objToLong(objNumeroCcExamen); 
		System.out.println(" numeroCcExamen:"+numeroCcExamen);
		refreshEntity(); 
	    System.out.println("Finaliza UpdateTestExamForm init()");
	 }

	private void refreshEntity() {
		CcExamenes ccExamenesObjMod = ccExamenesLocal.findByNumeroObjMod(this.getNumeroCcExamen()); 
		
		this.setEstatus(ccExamenesObjMod.getEstatus());
		this.setTitulo(ccExamenesObjMod.getTitulo());
		this.setNombre(ccExamenesObjMod.getNombre());
		this.setDescripcion(ccExamenesObjMod.getDescripcion());
		this.setTipoPregunta(ccExamenesObjMod.getTipoPregunta());
		this.setTipoExamen(ccExamenesObjMod.getTipoExamen());
		this.setTema(ccExamenesObjMod.getTema());
		this.setComentarios(ccExamenesObjMod.getComentarios());
		this.setVisibilidad(ccExamenesObjMod.getVisibilidad());
		this.setFechaEfectivaDesde(ccExamenesObjMod.getFechaEfectivaDesde());
		this.setFechaEfectivaHasta(ccExamenesObjMod.getFechaEfectivaHasta());
		this.setLimiteTiempo(ccExamenesObjMod.getTiempoLimite());
		this.setSaltarPreguntas(ccExamenesObjMod.getSaltarPreguntas());
		this.setSaltarCasos(ccExamenesObjMod.getSaltarCasos());
		this.setMostrarRespuestas(ccExamenesObjMod.getMostrarRespuestas());
		this.setTienePassmark(ccExamenesObjMod.getTienePassmark());
		this.setAleatorioGrupo(ccExamenesObjMod.getAleatorioGrupo());
		this.setAleatorioPreguntas(ccExamenesObjMod.getAleatorioPreguntas());
		this.setSeleccionCasosAleatorios(ccExamenesObjMod.getSeleccionCasosAleatorios());
		this.setMensajeFinalizacion(ccExamenesObjMod.getMensajeFinalizacion());
		this.setConfirmacionAsistencia(ccExamenesObjMod.getConfirmacionAsistencia());
		this.setDiploma(ccExamenesObjMod.getDiploma());
		
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
		ccExamenesUpdateDto.setTitulo(this.getTitulo());
		ccExamenesUpdateDto.setNombre(this.getNombre());
		
		ccExamenesUpdateDto.setDescripcion(this.getDescripcion());
		ccExamenesUpdateDto.setTipoPregunta(this.getTipoPregunta());
		ccExamenesUpdateDto.setTipoExamen(this.getTipoExamen());
		ccExamenesUpdateDto.setTema(this.getTema());
		ccExamenesUpdateDto.setComentarios(this.getComentarios());
		ccExamenesUpdateDto.setVisibilidad(this.getVisibilidad());
		ccExamenesUpdateDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(this.getFechaEfectivaDesde()));
		if(null!=this.getFechaEfectivaHasta()) {
			ccExamenesUpdateDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(this.getFechaEfectivaHasta()));
		}else {
			ccExamenesUpdateDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}
		ccExamenesUpdateDto.setTiempoLimite(this.getLimiteTiempo());
		ccExamenesUpdateDto.setSaltarPreguntas(this.isSaltarPreguntas());
		ccExamenesUpdateDto.setSaltarCasos(this.isSaltarCasos());
		ccExamenesUpdateDto.setMostrarRespuestas(this.isMostrarRespuestas());
		ccExamenesUpdateDto.setTienePassmark(this.isTienePassmark());
		ccExamenesUpdateDto.setAleatorioGrupo(this.isAleatorioGrupo());
		ccExamenesUpdateDto.setAleatorioPreguntas(this.isAleatorioPreguntas());
		ccExamenesUpdateDto.setSeleccionCasosAleatorios(this.isSeleccionCasosAleatorios());
		ccExamenesUpdateDto.setMensajeFinalizacion(this.getMensajeFinalizacion());
		ccExamenesUpdateDto.setConfirmacionAsistencia(this.isConfirmacionAsistencia());
		ccExamenesUpdateDto.setDiploma(this.isDiploma());
		
		ccExamenesLocal.update(this.getNumeroCcExamen(), ccExamenesUpdateDto);
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
    
	
	public String addCoreCaseGroup() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", this.numeroCcExamen);
		return "Exams-CoreCases-Update-AddCoreCase"; 
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getTipoExamen() {
		return tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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

	public boolean isSaltarPreguntas() {
		return saltarPreguntas;
	}

	public void setSaltarPreguntas(boolean saltarPreguntas) {
		this.saltarPreguntas = saltarPreguntas;
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

	public boolean isTienePassmark() {
		return tienePassmark;
	}

	public void setTienePassmark(boolean tienePassmark) {
		this.tienePassmark = tienePassmark;
	}

	public boolean isAleatorioGrupo() {
		return aleatorioGrupo;
	}

	public void setAleatorioGrupo(boolean aleatorioGrupo) {
		this.aleatorioGrupo = aleatorioGrupo;
	}

	public boolean isAleatorioPreguntas() {
		return aleatorioPreguntas;
	}

	public void setAleatorioPreguntas(boolean aleatorioPreguntas) {
		this.aleatorioPreguntas = aleatorioPreguntas;
	}

	public boolean isSeleccionCasosAleatorios() {
		return seleccionCasosAleatorios;
	}

	public void setSeleccionCasosAleatorios(boolean seleccionCasosAleatorios) {
		this.seleccionCasosAleatorios = seleccionCasosAleatorios;
	}

	public String getMensajeFinalizacion() {
		return mensajeFinalizacion;
	}

	public void setMensajeFinalizacion(String mensajeFinalizacion) {
		this.mensajeFinalizacion = mensajeFinalizacion;
	}

	public boolean isConfirmacionAsistencia() {
		return confirmacionAsistencia;
	}

	public void setConfirmacionAsistencia(boolean confirmacionAsistencia) {
		this.confirmacionAsistencia = confirmacionAsistencia;
	}

	public boolean isDiploma() {
		return diploma;
	}

	public void setDiploma(boolean diploma) {
		this.diploma = diploma;
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
	 
}
