

package com.cmrise.jpa.dto.candidates.exams;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CAND_EXAM_RESP_SKIP")
@NamedQuery(name="CandExamRespSkipDto.findAll", query="SELECT c FROM CandExamRespSkipDto c")
public class CandExamRespSkipDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="EXAMEN")
	private long Examen;
	
	@Column(name="NUMERO_CAND_EXAMEN")
	private long numeroCandExamen;
	
	@Column(name="NUMERO_GRUPO")
	private long numeroGrupo; 
	
	@Column(name="NUMERO_PREGUNTA_HDR")
	private long numeroPreguntaHdr;
	
	@Column(name="NUMERO_PREGUNTA_FTA")
	private long numeroPreguntaFta;
	
	@Column(name="ESTATUS")
	private int estatus; 
	
	@Column(name="SKIP")
	private int skip; 
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public long getExamen() {
		return Examen;
	}

	public void setExamen(long examen) {
		Examen = examen;
	}

	public long getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(long numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}

	public long getNumeroCandExamen() {
		return numeroCandExamen;
	}

	public void setNumeroCandExamen(long numeroCandExamen) {
		this.numeroCandExamen = numeroCandExamen;
	}

	public long getNumeroPreguntaHdr() {
		return numeroPreguntaHdr;
	}

	public void setNumeroPreguntaHdr(long numeroPreguntaHdr) {
		this.numeroPreguntaHdr = numeroPreguntaHdr;
	}

	public long getNumeroPreguntaFta() {
		return numeroPreguntaFta;
	}

	public void setNumeroPreguntaFta(long numeroPreguntaFta) {
		this.numeroPreguntaFta = numeroPreguntaFta;
	}
	
	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public long getActualizadoPor() {
		return actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
}
