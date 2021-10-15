package com.cmrise.jpa.dto.candidates.exams;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CAND_EXAM_RESPUESTAS")
@NamedQuery(name="CandExamRespuestasDto.findAll", query="SELECT c FROM CandExamRespuestasDto c")
public class CandExamRespuestasDto  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;
	
	@Column(name="NUMERO_CAND_EXAMEN")
	private long numeroCandExamen; 
	
	@Column(name="NUMERO_GRUPO")
	private long numeroGrupo; 
	
	@Column(name="NUMERO_PREGUNTA_HDR")
	private long numeroPreguntaHdr; 
	
	@Column(name="NUMERO_PREGUNTA_FTA")
	private long numeroPreguntaFta; 
	
	@Column(name="RESPUESTA")
	private String respuesta; 
	
	@Column(name="PUNTUACION")
	private double puntuacion; 
	
	@Column(name="DURATION")
	private long duration; 
	
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

	public long getNumeroCandExamen() {
		return numeroCandExamen;
	}

	public void setNumeroCandExamen(long numeroCandExamen) {
		this.numeroCandExamen = numeroCandExamen;
	}

	public long getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(long numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
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

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	

}