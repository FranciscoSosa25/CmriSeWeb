package com.cmrise.jpa.dto.candidates.exams;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CAND_CC_EXAMENES")
@NamedQuery(name="CandCcExamenesDto.findAll", query="SELECT c FROM CandCcExamenesDto c")
public class CandCcExamenesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="NUMERO_CC_EXAMEN")
	private long numeroCcExamen;
	
	@Column(name="NUMERO_USUARIO")
	private long numeroUsuario;
	
	@Column(name="ESTATUS")
	private String estatus; 
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Timestamp fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Timestamp fechaEfectivaHasta;
	
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

	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}

	public long getNumeroUsuario() {
		return numeroUsuario;
	}

	public void setNumeroUsuario(long numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
}
