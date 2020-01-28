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

	//bi-directional one-to-one association to MrqsPreguntasHdrDto
	@OneToOne
	@JoinColumn(name="NUMERO")
	private MrqsPreguntasHdrDto mrqsPreguntasHdr1;

	//bi-directional many-to-one association to MrqsPreguntasHdrDto
	@ManyToOne
	@JoinColumn(name="NUMERO_HDR")
	private MrqsPreguntasHdrDto mrqsPreguntasHdr2;

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

	public MrqsPreguntasHdrDto getMrqsPreguntasHdr1() {
		return this.mrqsPreguntasHdr1;
	}

	public void setMrqsPreguntasHdr1(MrqsPreguntasHdrDto mrqsPreguntasHdr1) {
		this.mrqsPreguntasHdr1 = mrqsPreguntasHdr1;
	}

	public MrqsPreguntasHdrDto getMrqsPreguntasHdr2() {
		return this.mrqsPreguntasHdr2;
	}

	public void setMrqsPreguntasHdr2(MrqsPreguntasHdrDto mrqsPreguntasHdr2) {
		this.mrqsPreguntasHdr2 = mrqsPreguntasHdr2;
	}

}