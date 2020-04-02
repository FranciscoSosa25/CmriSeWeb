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
	
	@Column(name="TITULO")
	private String titulo;
	
	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta; 
	
	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc; 
	
	@Column(name="ESTATUS")
	private String estatus;
	
	@Column(name="ESTATUS_DESC")
	private String estatusDesc;
	
	@Column(name="TEMA_PREGUNTA")
	private String temaPregunta; 
	
	@Column(name="TEMA_PREGUNTA_DESC")
	private String temaPreguntaDesc; 
	
	@Column(name="ETIQUETAS")
	private String etiquetas; 
	
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public String getTemaPregunta() {
		return temaPregunta;
	}

	public void setTemaPregunta(String temaPregunta) {
		this.temaPregunta = temaPregunta;
	}

	public String getTemaPreguntaDesc() {
		return temaPreguntaDesc;
	}

	public void setTemaPreguntaDesc(String temaPreguntaDesc) {
		this.temaPreguntaDesc = temaPreguntaDesc;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	

}
