package com.cmrise.ejb.backing.exams.corecases;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

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
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	CcExamenesLocal ccExamenesLocal; 
	
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
		CcExamenesDto ccExamenesDto = ccExamenesLocal.findById(this.getNumeroCcExamen()); 
		this.setEstatus(ccExamenesDto.getEstatus());
		this.setTitulo(ccExamenesDto.getTitulo());
		this.setNombre(ccExamenesDto.getNombre());
		this.setDescripcion(ccExamenesDto.getDescripcion());
		this.setTipoPregunta(ccExamenesDto.getTipoPregunta());
		this.setTipoExamen(ccExamenesDto.getTipoExamen());
		this.setTema(ccExamenesDto.getTema());
		this.setComentarios(ccExamenesDto.getComentarios());
		this.setVisibilidad(ccExamenesDto.getVisibilidad());
		this.setFechaEfectivaDesde(utilitariosLocal.toUtilDate(ccExamenesDto.getFechaEfectivaDesde()));
		if(Utilitarios.endOfTime.equals(ccExamenesDto.getFechaEfectivaHasta())) {
			this.setFechaEfectivaHasta(null);
		}else {
			this.setFechaEfectivaHasta(utilitariosLocal.toUtilDate(ccExamenesDto.getFechaEfectivaHasta()));
		}
		this.setLimiteTiempo(ccExamenesDto.getTiempoLimite());
		this.setSaltarPreguntas(ccExamenesDto.getSaltarPreguntas());
		this.setSaltarCasos(ccExamenesDto.getSaltarCasos());
		this.setMostrarRespuestas(ccExamenesDto.getMostrarRespuestas());
		this.setTienePassmark(ccExamenesDto.getTienePassmark());
		this.setAleatorioGrupo(ccExamenesDto.getAleatorioGrupo());
		this.setAleatorioPreguntas(ccExamenesDto.getAleatorioPreguntas());
		this.setSeleccionCasosAleatorios(ccExamenesDto.getSeleccionCasosAleatorios());
		this.setMensajeFinalizacion(ccExamenesDto.getMensajeFinalizacion());
		this.setConfirmacionAsistencia(ccExamenesDto.getConfirmacionAsistencia());
		this.setDiploma(ccExamenesDto.getDiploma());
		
		
	}
	
	public void update() {
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
		
	}
	

	public String addCoreCaseGroup() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", this.numeroCcExamen);
		return "Exams-CoreCases-Update-AddCoreCase"; 
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
	 
}
