package com.cmrise.jpa.dto.corecases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="CC_PREGUNTAS_HDR_V1")
@NamedQuery(name="CcPreguntasHdrV1Dto.findAll", query="SELECT c FROM CcPreguntasHdrV1Dto c")
public class CcPreguntasHdrV1Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="NUMERO_CC_HDR")
	private long numeroCcHdr;

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
		
	@Column(name="TIPO_PREGUNTA_DESC")
	private String tipoPreguntaDesc; 
	
	@Column(name="ESTATUS_DESC")
	private String estatusDesc;
	
	@Column(name="MAX_PUNTUACION")
	private BigDecimal maxPuntuacion;
		
	@Column(name="ADMON_EXAMEN")
	private long admonExamen; 
	
	@Column(name="ADMON_MATERIA")
	private long admonMateria; 
	
	@Column(name="ADMON_SUBMATERIA")
	private long admonSubmateria; 
	
	@Column(name="FECHA_ELABORACION")
	private Date fechaElaboracion;
	
	@Column(name="ADMON_EXAMEN_DESC")
	private String admonExamenDesc; 
	
	@Column(name="ADMON_MATERIA_DESC")
	private String admonMateriaDesc; 
	
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

	public String getTipoPreguntaDesc() {
		return tipoPreguntaDesc;
	}

	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}

	public String getEstatusDesc() {
		return estatusDesc;
	}

	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
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

	public String getAdmonExamenDesc() {
		return admonExamenDesc;
	}

	public void setAdmonExamenDesc(String admonExamenDesc) {
		this.admonExamenDesc = admonExamenDesc;
	}

	public String getAdmonMateriaDesc() {
		return admonMateriaDesc;
	}

	public void setAdmonMateriaDesc(String admonMateriaDesc) {
		this.admonMateriaDesc = admonMateriaDesc;
	}

	public String getAdmonSubmateriaDesc() {
		return admonSubmateriaDesc;
	}

	public void setAdmonSubmateriaDesc(String admonSubmateriaDesc) {
		this.admonSubmateriaDesc = admonSubmateriaDesc;
	}

}
