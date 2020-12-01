package com.cmrise.ejb.backing.exams.corecases;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class CreateTestExamForm {

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
	CcExamenesLocal ccExamenesLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	public String create() {
		CcExamenesDto ccExamenesDto = new CcExamenesDto();
		
		ccExamenesDto.setTitulo(this.getTitulo());
		ccExamenesDto.setNombre(this.getNombre());
		ccExamenesDto.setDescripcion(this.getDescripcion());
		ccExamenesDto.setTipoPregunta(this.getTipoPregunta());
		ccExamenesDto.setTipoExamen(this.getTipoExamen());
		ccExamenesDto.setTema(this.getTema());
		ccExamenesDto.setComentarios(this.getComentarios());
		ccExamenesDto.setVisibilidad(this.getVisibilidad());
		ccExamenesDto.setFechaEfectivaDesde(utilitariosLocal.toSqlDate(this.getFechaEfectivaDesde()));
		if(null!=this.getFechaEfectivaHasta()) {
			ccExamenesDto.setFechaEfectivaHasta(utilitariosLocal.toSqlDate(this.getFechaEfectivaHasta()));
		}else {
			ccExamenesDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		}
		ccExamenesDto.setTiempoLimite(this.getLimiteTiempo());
		ccExamenesDto.setSaltarPreguntas(this.isSaltarPreguntas());
		ccExamenesDto.setSaltarCasos(this.isSaltarCasos());
		ccExamenesDto.setMostrarRespuestas(this.isMostrarRespuestas());
		ccExamenesDto.setTienePassmark(this.isTienePassmark());
		ccExamenesDto.setAleatorioGrupo(this.isAleatorioGrupo());
		ccExamenesDto.setAleatorioPreguntas(this.isAleatorioPreguntas());
		ccExamenesDto.setSeleccionCasosAleatorios(this.isSeleccionCasosAleatorios());
		ccExamenesDto.setMensajeFinalizacion(this.getMensajeFinalizacion());
		ccExamenesDto.setConfirmacionAsistencia(this.isConfirmacionAsistencia());
		ccExamenesDto.setDiploma(this.isDiploma());
		ccExamenesDto.setSociedad(Utilitarios.SOCIEDAD);
		ccExamenesDto.setEstatus(Utilitarios.INITIAL_STATUS_CC_EXAM);
		long numeroCcExamen = ccExamenesLocal.insert(ccExamenesDto); 
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcExamenSV", numeroCcExamen);  
		
		return "Exams-CoreCases-Update"; 
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
