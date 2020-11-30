package com.cmrise.jpa.dto.corecases;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the CC_PREGUNTAS_FTA database table.
 * 
 */
@Entity
@Table(name="CC_PREGUNTAS_FTA")
@NamedQuery(name="CcPreguntasFtaDto.findAll", query="SELECT c FROM CcPreguntasFtaDto c")
public class CcPreguntasFtaDto implements Serializable {
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

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="TITULO_PREGUNTA")
	private String tituloPregunta; 
	
	@Column(name="TEXTO_PREGUNTA")
	private String textoPregunta; 
	
	@Column(name="TEXTO_SUGERENCIAS")
	private String textoSugerencias; 
	
	@Column(name="RESPUESTA_CORRECTA")
	private String respuestaCorrecta; 
	
	@Column(name="SINGLE_ANSWER_MODE")
	private boolean singleAnswerMode; 
	
	@Column(name="SUFFLE_ANSWER_ORDER")
	private boolean suffleAnswerOrder; 
	
	@Column(name="METODO_PUNTUACION")
	private String metodoPuntuacion; 
	@Column(name="VALOR_PUNTUACION")
	private String valorPuntuacion; 
	@Column(name="LIMITE_CARACTERES")
	private Integer limiteCaracteres;
	//bi-directional many-to-one association to CcPreguntasHdrDto
	@ManyToOne
	@JoinColumn(name="NUMERO_HDR")
	private CcPreguntasHdrDto ccPreguntasHdr;

	public CcPreguntasFtaDto() {
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

	public CcPreguntasHdrDto getCcPreguntasHdr() {
		return this.ccPreguntasHdr;
	}

	public void setCcPreguntasHdr(CcPreguntasHdrDto ccPreguntasHdr) {
		this.ccPreguntasHdr = ccPreguntasHdr;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
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

	public String getMetodoPuntuacion() {
		return metodoPuntuacion;
	}

	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}

	public String getValorPuntuacion() {
		return valorPuntuacion;
	}

	public void setValorPuntuacion(String valorPuntuacion) {
		this.valorPuntuacion = valorPuntuacion;
	}

	public Integer getLimiteCaracteres() {
		return limiteCaracteres;
	}

	public void setLimiteCaracteres(Integer limiteCaracteres) {
		this.limiteCaracteres = limiteCaracteres;
	}	
	
    
}