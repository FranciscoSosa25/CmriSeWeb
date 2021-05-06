package com.cmrise.ejb.model.exams;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class CcExamenes implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private String creadoPorNombre;
	private String descripcion;
	private String estatus;
	private String estatusDesc;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private String fechaCreacionString;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String mensajeFinalizacion;
	private boolean mostrarRespuestas;
	private boolean saltarCasos;
	private String sociedad;
	private short tiempoLimite;
	private String tipoExamen;
	private long idTipoExamen;
	private String tipoExamenDesc; 
	private String visibilidad;
	private String visibilidadDesc; 

	private List<CcExamAsignaciones> listCcExamAsignaciones; 
	
	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public long getActualizadoPor() {
		return this.actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setFechaCreacionString() {
		this.fechaCreacionString = this.fechaCreacion.toString().substring(0, 10);
	}
	public String getFechaCreacionString() {
		return this.fechaCreacionString;
	}
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaEfectivaDesde() {
		return this.fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Date getFechaEfectivaHasta() {
		return this.fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}

	public String getMensajeFinalizacion() {
		return this.mensajeFinalizacion;
	}

	public void setMensajeFinalizacion(String mensajeFinalizacion) {
		this.mensajeFinalizacion = mensajeFinalizacion;
	}

	public boolean getMostrarRespuestas() {
		return this.mostrarRespuestas;
	}

	public void setMostrarRespuestas(boolean mostrarRespuestas) {
		this.mostrarRespuestas = mostrarRespuestas;
	}

	public boolean getSaltarCasos() {
		return this.saltarCasos;
	}

	public void setSaltarCasos(boolean saltarCasos) {
		this.saltarCasos = saltarCasos;
	}

	public String getSociedad() {
		return this.sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public short getTiempoLimite() {
		return this.tiempoLimite;
	}

	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}

	public String getTipoExamen() {
		return this.tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}
	
	public long getIdTipoExamen() {
		return this.idTipoExamen;
	}

	public void setIdTipoExamen(long idTipoExamen) {
		this.idTipoExamen = idTipoExamen;
	}

	public String getVisibilidad() {
		return this.visibilidad;
	}

	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}

	public String getTipoExamenDesc() {
		return tipoExamenDesc;
	}

	public void setTipoExamenDesc(String tipoExamenDesc) {
		this.tipoExamenDesc = tipoExamenDesc;
	}

	public String getVisibilidadDesc() {
		return visibilidadDesc;
	}

	public void setVisibilidadDesc(String visibilidadDesc) {
		this.visibilidadDesc = visibilidadDesc;
	}

	public String getEstatusDesc() {
		return estatusDesc;
	}

	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
	}

	public List<CcExamAsignaciones> getListCcExamAsignaciones() {
		return listCcExamAsignaciones;
	}

	public void setListCcExamAsignaciones(List<CcExamAsignaciones> listCcExamAsignaciones) {
		this.listCcExamAsignaciones = listCcExamAsignaciones;
	}

	public String getCreadoPorNombre() {
		return creadoPorNombre;
	}

	public void setCreadoPorNombre(String creadoPorNombre) {
		this.creadoPorNombre = creadoPorNombre;
	}



}
