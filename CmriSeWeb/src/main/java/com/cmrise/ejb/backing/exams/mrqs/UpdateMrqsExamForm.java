package com.cmrise.ejb.backing.exams.mrqs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.ejb.services.exams.MrqsGrupoHdrLocal;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class UpdateMrqsExamForm {

	private long numeroMrqsExamen;
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
	
	//private List<CcExamAsignaciones> listCcExamAsignaciones = new ArrayList<CcExamAsignaciones>(); 
	
	private MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr(); 
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
	
	private TreeNode rootMrqsGrupo;
	private TreeNode selectedNode;
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	MrqsExamenesLocal mrqsExamenesLocal; 
	
	@Inject 
	MrqsGrupoHdrLocal mrqsGrupoHdrLocal; 
	
	
	
	 @PostConstruct
	 public void init() {
		System.out.println("Comienza UpdateTestExamForm init()");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Object objNumeroCcExamen = session.getAttribute("NumeroMrqsExamenSV");
		numeroMrqsExamen = utilitariosLocal.objToLong(objNumeroCcExamen); 
		System.out.println(" numeroMrqsExamen:"+numeroMrqsExamen);
		refreshEntity(); 
		
	    System.out.println("Finaliza UpdateTestExamForm init()");
	 }

	private void refreshEntity() {
		MrqsExamenesDto mrqsExamenesDto = mrqsExamenesLocal.findById(this.getNumeroMrqsExamen()); 
		this.setEstatus(mrqsExamenesDto.getEstatus());
		this.setTitulo(mrqsExamenesDto.getTitulo());
		this.setNombre(mrqsExamenesDto.getNombre());
		this.setDescripcion(mrqsExamenesDto.getDescripcion());
		this.setTipoPregunta(mrqsExamenesDto.getTipoPregunta());
		this.setTipoExamen(mrqsExamenesDto.getTipoExamen());
		this.setTema(mrqsExamenesDto.getTema());
		this.setComentarios(mrqsExamenesDto.getComentarios());
		this.setVisibilidad(mrqsExamenesDto.getVisibilidad());
		this.setFechaEfectivaDesde(utilitariosLocal.toUtilDate(mrqsExamenesDto.getFechaEfectivaDesde()));
		if(Utilitarios.endOfTimeTimestamp.equals(mrqsExamenesDto.getFechaEfectivaHasta())) {
			this.setFechaEfectivaHasta(null);
		}else {
			this.setFechaEfectivaHasta(utilitariosLocal.toUtilDate(mrqsExamenesDto.getFechaEfectivaHasta()));
		}
		this.setLimiteTiempo(mrqsExamenesDto.getTiempoLimite());
		this.setSaltarPreguntas(mrqsExamenesDto.getSaltarPreguntas());
		this.setSaltarCasos(mrqsExamenesDto.getSaltarCasos());
		this.setMostrarRespuestas(mrqsExamenesDto.getMostrarRespuestas());
		this.setTienePassmark(mrqsExamenesDto.getTienePassmark());
		this.setAleatorioGrupo(mrqsExamenesDto.getAleatorioGrupo());
		this.setAleatorioPreguntas(mrqsExamenesDto.getAleatorioPreguntas());
		this.setSeleccionCasosAleatorios(mrqsExamenesDto.getSeleccionCasosAleatorios());
		this.setMensajeFinalizacion(mrqsExamenesDto.getMensajeFinalizacion());
		this.setConfirmacionAsistencia(mrqsExamenesDto.getConfirmacionAsistencia());
		this.setDiploma(mrqsExamenesDto.getDiploma());
		
		/** listCcExamAsignaciones = ccExamAsignacionesLocal.findByNumeroExamenWD(this.getNumeroMrqsExamen()); **/
		
		rootMrqsGrupo = new DefaultTreeNode("Root", null);
		listMrqsGrupoHdr = mrqsGrupoHdrLocal.findByNumeroExamen(this.getNumeroMrqsExamen()); 
		for(MrqsGrupoHdr mrqsGrupoHdr:listMrqsGrupoHdr) {
			TreeNode nodeGrupoHdr = new DefaultTreeNode(mrqsGrupoHdr, rootMrqsGrupo);
		}
		
	}
	
	public void update() {
		System.out.println("Entra UpdateTestExamForm update()");
		boolean updateIn = false; 
		MrqsExamenesDto mrqsExamenesDto = new MrqsExamenesDto();
		
		mrqsExamenesDto.setNumero(this.getNumeroMrqsExamen());
		mrqsExamenesDto.setEstatus(this.getEstatus());
		mrqsExamenesDto.setTitulo(this.getTitulo());
		mrqsExamenesDto.setNombre(this.getNombre());
		mrqsExamenesDto.setDescripcion(this.getDescripcion());
		mrqsExamenesDto.setTipoPregunta(this.getTipoPregunta());
		mrqsExamenesDto.setTipoExamen(this.getTipoExamen());
		mrqsExamenesDto.setTema(this.getTema());
		mrqsExamenesDto.setComentarios(this.getComentarios());
		mrqsExamenesDto.setVisibilidad(this.getVisibilidad());
		mrqsExamenesDto.setFechaEfectivaDesde(utilitariosLocal.toSqlTimestamp(this.getFechaEfectivaDesde()));
		if(null!=this.getFechaEfectivaHasta()) {
			mrqsExamenesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlTimestamp(this.getFechaEfectivaHasta()));
		}else {
			mrqsExamenesDto.setFechaEfectivaHasta(Utilitarios.endOfTimeTimestamp);
		}
		mrqsExamenesDto.setTiempoLimite(this.getLimiteTiempo());
		mrqsExamenesDto.setSaltarPreguntas(this.isSaltarPreguntas());
		mrqsExamenesDto.setSaltarCasos(this.isSaltarCasos());
		mrqsExamenesDto.setMostrarRespuestas(this.isMostrarRespuestas());
		mrqsExamenesDto.setTienePassmark(this.isTienePassmark());
		mrqsExamenesDto.setAleatorioGrupo(this.isAleatorioGrupo());
		mrqsExamenesDto.setAleatorioPreguntas(this.isAleatorioPreguntas());
		mrqsExamenesDto.setSeleccionCasosAleatorios(this.isSeleccionCasosAleatorios());
		mrqsExamenesDto.setMensajeFinalizacion(this.getMensajeFinalizacion());
		mrqsExamenesDto.setConfirmacionAsistencia(this.isConfirmacionAsistencia());
		mrqsExamenesDto.setDiploma(this.isDiploma());
		
		mrqsExamenesLocal.update(this.getNumeroMrqsExamen(), mrqsExamenesDto);
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
		mrqsGrupoHdr.setTitulo(this.getTitulo());
		mrqsGrupoHdr.setTema(this.getTema());
		mrqsGrupoHdr.setComentarios(this.getComentarios());
	}
	
	public String addMRQsGroup() {
		
		MrqsGrupoHdrDto mrqsGrupoHdrDto = new MrqsGrupoHdrDto(); 
		mrqsGrupoHdrDto.setTitulo(mrqsGrupoHdr.getTitulo());
		mrqsGrupoHdrDto.setTema(mrqsGrupoHdr.getTema());
		mrqsGrupoHdrDto.setComentarios(mrqsGrupoHdr.getComentarios());
		mrqsGrupoHdrDto.setNumeroExamen(this.getNumeroMrqsExamen());
		mrqsGrupoHdrLocal.insert(mrqsGrupoHdrDto); 
		return "Exams-MRQs-Update"; 
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

	
	/**
	public List<CcExamAsignaciones> getListCcExamAsignaciones() {
		return listCcExamAsignaciones;
	}

	public void setListCcExamAsignaciones(List<CcExamAsignaciones> listCcExamAsignaciones) {
		this.listCcExamAsignaciones = listCcExamAsignaciones;
	} 
	**/ 
	 
}
