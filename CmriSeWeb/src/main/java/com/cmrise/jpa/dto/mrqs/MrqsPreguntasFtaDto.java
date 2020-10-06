package com.cmrise.jpa.dto.mrqs;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the MRQS_PREGUNTAS_FTA database table.
 * 
 */
@Entity
@Table(name="MRQS_PREGUNTAS_FTA")
@NamedQuery(name="MrqsPreguntasFtaDto.findAll", query="SELECT m FROM MrqsPreguntasFtaDto m")
public class MrqsPreguntasFtaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="NUMERO_HDR")
	private long numeroHdr; 
	
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="METODO_PUNTUACION")
	private String metodoPuntuacion;

	@Column(name="RESPUESTA_CORRECTA")
	private String respuestaCorrecta;

	@Column(name="TEXTO_PREGUNTA")
	private String textoPregunta;

	@Column(name="TEXTO_SUGERENCIAS")
	private String textoSugerencias;

	@Column(name="TITULO")
	private String titulo;

	@Column(name="VALOR_PUNTUACION")
	private String valorPuntuacion;

	@Column(name="SINGLE_ANSWER_MODE")
	private boolean singleAnswerMode;
	
	@Column(name="SUFFLE_ANSWER_ORDER")
	private boolean suffleAnswerOrder; 
	
	@Column(name="NOMBRE_IMAGEN")
	private String nombreImagen; 
	
	@Column(name="RUTA_IMAGEN")
	private String rutaImagen; 
	
	@Column(name="CONTENT_TYPE")
	private String contentType; 
	
	@Column(name="POLIGONOS")
	private String poligonos;
	
	@Column(name="WIDTH")
	private int width; 
	
	@Column(name="HEIGHT")
	private int height; 

	@Column(name="ANOTACIONES")
	private String anotaciones; 
	
	@Column(name="RESPUESTAS")
	private String respuestas; 
	
	@Column(name="CORRELACIONES")
	private String correlaciones; 
	
	public MrqsPreguntasFtaDto() {
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

	public String getMetodoPuntuacion() {
		return this.metodoPuntuacion;
	}

	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}

	public String getRespuestaCorrecta() {
		return this.respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getTextoPregunta() {
		return this.textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoSugerencias() {
		return this.textoSugerencias;
	}

	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getValorPuntuacion() {
		return this.valorPuntuacion;
	}

	public void setValorPuntuacion(String valorPuntuacion) {
		this.valorPuntuacion = valorPuntuacion;
	}

	public boolean isSingleAnswerMode() {
		return singleAnswerMode;
	}

	public void setSingleAnswerMode(boolean singleAnswerMode) {
		this.singleAnswerMode = singleAnswerMode;
	}

	public boolean isSuffleAnswerOrder() {
		return suffleAnswerOrder;
	}

	public void setSuffleAnswerOrder(boolean suffleAnswerOrder) {
		this.suffleAnswerOrder = suffleAnswerOrder;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPoligonos() {
		return poligonos;
	}

	public void setPoligonos(String poligonos) {
		this.poligonos = poligonos;
	}

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(String respuestas) {
		this.respuestas = respuestas;
	}

	public String getCorrelaciones() {
		return correlaciones;
	}

	public void setCorrelaciones(String correlaciones) {
		this.correlaciones = correlaciones;
	}

}