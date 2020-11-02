package com.cmrise.ejb.model.exams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MrqsExamenes implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numero;
	private long actualizadoPor;
	private boolean aleatorioGrupo;
	private boolean aleatorioPreguntas;
	private long creadoPor;
	private String descripcion;
	private String estatus;
	private String estatusDesc;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String mensajeFinalizacion;
	private boolean mostrarRespuestas;
	private boolean saltarCasos;
	private boolean saltarPreguntas;
	private boolean seleccionCasosAleatorios;
	private short tiempoLimite;
	private String visibilidad;
	private String visibilidadDesc; 
	private long admonExamen; 
	private Date fechaElaboracion;
	private String admonExamenDesc; 
	private String elaborador; 
	
	private List<MrqsGrupoHdr> listMrqsGrupoHdr = new ArrayList<MrqsGrupoHdr>(); 
	
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

	public boolean isAleatorioGrupo() {
		return this.aleatorioGrupo;
	}

	public void setAleatorioGrupo(boolean aleatorioGrupo) {
		this.aleatorioGrupo = aleatorioGrupo;
	}

	public boolean isAleatorioPreguntas() {
		return this.aleatorioPreguntas;
	}

	public void setAleatorioPreguntas(boolean aleatorioPreguntas) {
		this.aleatorioPreguntas = aleatorioPreguntas;
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

	public boolean isMostrarRespuestas() {
		return this.mostrarRespuestas;
	}

	public void setMostrarRespuestas(boolean mostrarRespuestas) {
		this.mostrarRespuestas = mostrarRespuestas;
	}

	public boolean isSaltarCasos() {
		return this.saltarCasos;
	}

	public void setSaltarCasos(boolean saltarCasos) {
		this.saltarCasos = saltarCasos;
	}

	public boolean isSaltarPreguntas() {
		return this.saltarPreguntas;
	}

	public void setSaltarPreguntas(boolean saltarPreguntas) {
		this.saltarPreguntas = saltarPreguntas;
	}

	public boolean isSeleccionCasosAleatorios() {
		return this.seleccionCasosAleatorios;
	}

	public void setSeleccionCasosAleatorios(boolean seleccionCasosAleatorios) {
		this.seleccionCasosAleatorios = seleccionCasosAleatorios;
	}

	public short getTiempoLimite() {
		return this.tiempoLimite;
	}

	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}



	public String getVisibilidad() {
		return this.visibilidad;
	}

	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
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

	public List<MrqsGrupoHdr> getListMrqsGrupoHdr() {
		return listMrqsGrupoHdr;
	}

	public void setListMrqsGrupoHdr(List<MrqsGrupoHdr> listMrqsGrupoHdr) {
		this.listMrqsGrupoHdr = listMrqsGrupoHdr;
	}

	public long getAdmonExamen() {
		return admonExamen;
	}

	public void setAdmonExamen(long admonExamen) {
		this.admonExamen = admonExamen;
	}

	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}

	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}

	public String getAdmonExamenDesc() {
		return admonExamenDesc;
	}

	public void setAdmonExamenDesc(String admonExamenDesc) {
		this.admonExamenDesc = admonExamenDesc;
	}

	public String getElaborador() {
		return elaborador;
	}

	public void setElaborador(String elaborador) {
		this.elaborador = elaborador;
	}

}
