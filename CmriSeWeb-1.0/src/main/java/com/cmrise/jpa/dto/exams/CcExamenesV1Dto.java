package com.cmrise.jpa.dto.exams;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the CC_EXAMENES_V1 database table.
 * 
 */
@Entity
@Table(name="CC_EXAMENES_V1")
@NamedQuery(name="CcExamenesV1Dto.findAll", query="SELECT c FROM CcExamenesV1Dto c")
public class CcExamenesV1Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;
	
	@Column(name="CREADO_POR_NOMBRE")
	private String creadoPorNombre;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="ESTATUS")
	private String estatus;

	@Column(name="ESTATUS_DESC")
	private String estatusDesc;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="MENSAJE_FINALIZACION")
	private String mensajeFinalizacion;

	@Column(name="MOSTRAR_RESPUESTAS")
	private boolean mostrarRespuestas;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="SALTAR_CASOS")
	private boolean saltarCasos;

	@Column(name="SOCIEDAD")
	private String sociedad;

	@Column(name="TIEMPO_LIMITE")
	private short tiempoLimite;

	@Column(name="ID_TIPO_EXAMEN")
	private String idTipoExamen;
	
	@Column(name="EXAMEN")
	private String tipoExamen;

	@Column(name="EXAMEN_DESC")
	private String tipoExamenDesc;

	@Column(name="VISIBILIDAD")
	private String visibilidad;

	@Column(name="VISIBILIDAD_DESC")
	private String visibilidadDesc;

	public CcExamenesV1Dto() {
	}

	public long getActualizadoPor() {
		return this.actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public String getCreadoPorNombre() {
		return this.creadoPorNombre;
	}

	public void setCreadoPorNombre(String creadoPorNombre) {
		this.creadoPorNombre = creadoPorNombre;
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

	public String getEstatusDesc() {
		return this.estatusDesc;
	}

	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
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

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
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

	public String getIdTipoExamen() {
		return this.idTipoExamen;
	}

	public void setIdTipoExamen(String idTipoExamen) {
		this.idTipoExamen = idTipoExamen;
	}
	
	public String getTipoExamen() {
		return this.tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public String getTipoExamenDesc() {
		return this.tipoExamenDesc;
	}

	public void setTipoExamenDesc(String tipoExamenDesc) {
		this.tipoExamenDesc = tipoExamenDesc;
	}

	public String getVisibilidad() {
		return this.visibilidad;
	}

	public void setVisibilidad(String visibilidad) {
		this.visibilidad = visibilidad;
	}

	public String getVisibilidadDesc() {
		return this.visibilidadDesc;
	}

	public void setVisibilidadDesc(String visibilidadDesc) {
		this.visibilidadDesc = visibilidadDesc;
	}

}