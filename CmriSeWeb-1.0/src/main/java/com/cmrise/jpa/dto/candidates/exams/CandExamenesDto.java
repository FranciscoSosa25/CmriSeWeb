package com.cmrise.jpa.dto.candidates.exams;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CAND_EXAMENES")
@NamedQuery(name="CandExamenesDto.findAll", query="SELECT c FROM CandExamenesDto c")
public class CandExamenesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="NUMERO_EXAMEN")
	private long numeroExamen;
	
	@Column(name="NUMERO_USUARIO")
	private long numeroUsuario;
	
	@Column(name="TIPO")
	private String tipo; 
	
	@Column(name="ESTATUS")
	private String estatus; 
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;
	
	@Column(name="EXAM_START_TIME")
	private Timestamp examStartTime;
	
	@Column(name="EXAM_END_TIME")
	private Timestamp examEndTime;
	
	@Column(name="CAND_EXAM_TIME")
	private Integer candExamTime;
		

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	
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

	
	public long getNumeroExamen() {
		return numeroExamen;
	}

	public void setNumeroExamen(long numeroExamen) {
		this.numeroExamen = numeroExamen;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Timestamp getExamStartTime() {
		return examStartTime;
	}

	public void setExamStartTime(Timestamp examStartTime) {
		this.examStartTime = examStartTime;
	}

	public Timestamp getExamEndTime() {
		return examEndTime;
	}

	public void setExamEndTime(Timestamp examEndTime) {
		this.examEndTime = examEndTime;
	}

	public Integer getCandExamTime() {
		return candExamTime;
	}

	public void setCandExamTime(Integer candExamTime) {
		this.candExamTime = candExamTime;
	}
	
	
	
}