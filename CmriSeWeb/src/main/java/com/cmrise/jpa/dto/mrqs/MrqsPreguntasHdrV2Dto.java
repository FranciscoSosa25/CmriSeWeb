package com.cmrise.jpa.dto.mrqs;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the MRQS_PREGUNTAS_HDR_V2 database table.
 * 
 */
@Entity
@Table(name="MRQS_PREGUNTAS_HDR_V2")
@NamedQuery(name="MrqsPreguntasHdrV2Dto.findAll", query="SELECT m FROM MrqsPreguntasHdrV2Dto m")
public class MrqsPreguntasHdrV2Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="COMENTARIOS")
	private String comentarios;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="ESTATUS")
	private String estatus;

	@Column(name="ESTATUS_DESC")
	private String estatusDesc;

	@Column(name="ETIQUETAS")
	private String etiquetas;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="NOMBRE")
	private String nombre;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="SOCIEDAD")
	private String sociedad;

	@Column(name="TEMA_PREGUNTA")
	private String temaPregunta;

	@Column(name="TEMA_PREGUNTA_DESC")
	private String temaPreguntaDesc;

	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta;

	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc;

	@Column(name="TITULO")
	private String titulo;
	
	@Column(name="NUMERO_MPF")
	private long numeroMpf;
	
	@Column(name="TEXTO_PREGUNTA")
	private String textoPregunta;
	
	@Column(name="TEXTO_SUGERENCIAS")
	private String textoSugerencias;
	
	@Column(name="RESPUESTA_CORRECTA")
	private String respuestaCorrecta; 

	public MrqsPreguntasHdrV2Dto() {
	}

	public long getActualizadoPor() {
		return this.actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public String getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
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

	public String getEtiquetas() {
		return this.etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public String getSociedad() {
		return this.sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public String getTemaPregunta() {
		return this.temaPregunta;
	}

	public void setTemaPregunta(String temaPregunta) {
		this.temaPregunta = temaPregunta;
	}

	public String getTemaPreguntaDesc() {
		return this.temaPreguntaDesc;
	}

	public void setTemaPreguntaDesc(String temaPreguntaDesc) {
		this.temaPreguntaDesc = temaPreguntaDesc;
	}

	public String getTipoPregunta() {
		return this.tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getTipoPreguntaDesc() {
		return this.tipoPreguntaDesc;
	}

	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public long getNumeroMpf() {
		return numeroMpf;
	}

	public void setNumeroMpf(long numeroMpf) {
		this.numeroMpf = numeroMpf;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoSugerencias() {
		return textoSugerencias;
	}

	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

}