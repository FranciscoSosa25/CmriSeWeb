package com.cmrise.jpa.dto.exams;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the CC_EXAMENES database table.
 * 
 */
@Entity
@Table(name="CC_EXAMENES")
@NamedQuery(name="CcExamenesDto.findAll", query="SELECT c FROM CcExamenesDto c")
public class CcExamenesDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="ESTATUS")
	private String estatus;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Timestamp fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Timestamp fechaEfectivaHasta;

	@Column(name="MENSAJE_FINALIZACION")
	private String mensajeFinalizacion;

	@Column(name="MOSTRAR_RESPUESTAS")
	private boolean mostrarRespuestas;

	@Column(name="SALTAR_CASOS")
	private boolean saltarCasos;

	@Column(name="SOCIEDAD")
	private String sociedad;

	@Column(name="TIEMPO_LIMITE")
	private short tiempoLimite;

	@Column(name="EXAMEN")
	private long idTipoExamen;

	@Column(name="VISIBILIDAD")
	private String visibilidad;

	public CcExamenesDto() {
	}

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

	public Timestamp getFechaEfectivaDesde() {
		return this.fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Timestamp fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Timestamp getFechaEfectivaHasta() {
		return this.fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Timestamp fechaEfectivaHasta) {
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


}