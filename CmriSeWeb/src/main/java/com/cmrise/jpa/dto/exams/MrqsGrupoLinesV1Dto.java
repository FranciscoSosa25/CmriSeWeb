package com.cmrise.jpa.dto.exams;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the MRQS_GRUPO_LINES_V1 database table.
 * 
 */
@Entity
@Table(name="MRQS_GRUPO_LINES_V1")
@NamedQuery(name="MrqsGrupoLinesV1Dto.findAll", query="SELECT m FROM MrqsGrupoLinesV1Dto m")
public class MrqsGrupoLinesV1Dto implements Serializable {

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

	@Column(name="NUMERO_HDR")
	private long numeroHdr; 
	
	@Column(name="NUMERO_PREGUNTA")
	private long numeroPregunta; 
	
	@Column(name="ADMON_EXAMEN")
	private long admonExamen;
	
	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta; 
	
	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc; 
	
	@Column(name="ESTATUS")
	private String estatus;
	
	@Column(name="ESTATUS_DESC")
	private String estatusDesc;
	
	@Column(name="ADMON_SUBMATERIA")
	private long admonSubmateria; 
	
	@Column(name="DIAGNOSTICO")
	private String diagnostico; 
	
	@Column(name="TITULO_PREGUNTA")
	private String tituloPregunta; 
	
	@Column(name="ADMON_SUBMATERIA_DESC")
	private String admonSubmateriaDesc; 
	
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

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public long getNumeroPregunta() {
		return numeroPregunta;
	}

	public void setNumeroPregunta(long numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getTipoPreguntaDesc() {
		return tipoPreguntaDesc;
	}

	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstatusDesc() {
		return estatusDesc;
	}

	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
	}

	public long getAdmonExamen() {
		return admonExamen;
	}

	public void setAdmonExamen(long admonExamen) {
		this.admonExamen = admonExamen;
	}

	public long getAdmonSubmateria() {
		return admonSubmateria;
	}

	public void setAdmonSubmateria(long admonSubmateria) {
		this.admonSubmateria = admonSubmateria;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getAdmonSubmateriaDesc() {
		return admonSubmateriaDesc;
	}

	public void setAdmonSubmateriaDesc(String admonSubmateriaDesc) {
		this.admonSubmateriaDesc = admonSubmateriaDesc;
	}

	

}

