package com.cmrise.jpa.dto.candidates.exams;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CAND_CC_PREGUNTAS_FTA")
@NamedQuery(name="CandCcPreguntasFtaDto.findAll", query="SELECT c FROM CandCcPreguntasFtaDto c")
public class CandCcPreguntasFtaDto  implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="NUMERO_CC_CAND_PREGUNTA_HDR")
	private long numeroCcCandPreguntaHdr; 
	
	@Column(name="NUMERO_CC_PREGUNTA_FTA")
	private long numeroCcPreguntaFta; 
	
	@Column(name="RESPUESTA")
	private String respuesta; 
	
	@Column(name="RESPUESTA_CORRECTA")
	private String respuestaCorrecta; 
	
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

	public long getNumeroCcCandPreguntaHdr() {
		return numeroCcCandPreguntaHdr;
	}

	public void setNumeroCcCandPreguntaHdr(long numeroCcCandPreguntaHdr) {
		this.numeroCcCandPreguntaHdr = numeroCcCandPreguntaHdr;
	}

	public long getNumeroCcPreguntaFta() {
		return numeroCcPreguntaFta;
	}

	public void setNumeroCcPreguntaFta(long numeroCcPreguntaFta) {
		this.numeroCcPreguntaFta = numeroCcPreguntaFta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}
	
}
