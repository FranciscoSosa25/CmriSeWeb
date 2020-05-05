package com.cmrise.jpa.dto.corecases;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the CC_PREGUNTAS_HDR database table.
 * 
 */
@Entity
@Table(name="CC_PREGUNTAS_HDR")
@NamedQuery(name="CcPreguntasHdrDto.findAll", query="SELECT c FROM CcPreguntasHdrDto c")
public class CcPreguntasHdrDto implements Serializable {
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
	
	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta; 
	
	@Column(name="ETIQUETAS")
	private String etiquetas; 
	
	@Column(name="COMENTARIOS")
	private String comentarios;
	
	@Column(name="ESTATUS")
	private String estatus; 
	
	
	@Column(name="MAX_PUNTUACION")
	private BigDecimal maxPuntuacion;
	
	@Column(name="NUMERO_CC_HDR")
	private long numeroCcHdr;
	
	@Column(name="ADMON_EXAMEN")
	private long admonExamen; 
	
	@Column(name="ADMON_MATERIA")
	private long admonMateria; 
	
	@Column(name="ADMON_SUBMATERIA")
	private long admonSubmateria; 
	
	@Column(name="FECHA_ELABORACION")
	private Date fechaElaboracion;
	
	public CcPreguntasHdrDto() {
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
	
	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}


	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}


	public BigDecimal getMaxPuntuacion() {
		return maxPuntuacion;
	}

	public void setMaxPuntuacion(BigDecimal maxPuntuacion) {
		this.maxPuntuacion = maxPuntuacion;
	}

	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}

	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}

	public long getAdmonExamen() {
		return admonExamen;
	}

	public void setAdmonExamen(long admonExamen) {
		this.admonExamen = admonExamen;
	}

	public long getAdmonMateria() {
		return admonMateria;
	}

	public void setAdmonMateria(long admonMateria) {
		this.admonMateria = admonMateria;
	}

	public long getAdmonSubmateria() {
		return admonSubmateria;
	}

	public void setAdmonSubmateria(long admonSubmateria) {
		this.admonSubmateria = admonSubmateria;
	}

	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}

	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}

}