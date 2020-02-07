package com.cmrise.jpa.dto.corecases;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


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
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="TITULO")
	private String titulo;
	
	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta; 
	
	@Column(name="TEMA_PREGUNTA")
	private String temaPregunta; 
	
	@Column(name="ETIQUETAS")
	private String etiquetas; 
	
	@Column(name="COMENTARIOS")
	private String comentarios;
	
	@Column(name="ESTATUS")
	private String estatus; 
	
	@Column(name="SOCIEDAD")
	private String sociedad; 
	
	@Column(name="MAX_PUNTUACION")
	private BigDecimal maxPuntuacion;
	
	//bi-directional many-to-one association to CcPreguntasFtaDto
	@OneToMany(mappedBy="ccPreguntasHdr")
	private List<CcPreguntasFtaDto> ccPreguntasFtas;

	//bi-directional many-to-one association to CcHdrDto
	@ManyToOne
	@JoinColumn(name="NUMERO_CC_HDR")
	private CcHdrDto ccHdr;

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

	public List<CcPreguntasFtaDto> getCcPreguntasFtas() {
		return this.ccPreguntasFtas;
	}

	public void setCcPreguntasFtas(List<CcPreguntasFtaDto> ccPreguntasFtas) {
		this.ccPreguntasFtas = ccPreguntasFtas;
	}

	public CcPreguntasFtaDto addCcPreguntasFta(CcPreguntasFtaDto ccPreguntasFta) {
		getCcPreguntasFtas().add(ccPreguntasFta);
		ccPreguntasFta.setCcPreguntasHdr(this);

		return ccPreguntasFta;
	}

	public CcPreguntasFtaDto removeCcPreguntasFta(CcPreguntasFtaDto ccPreguntasFta) {
		getCcPreguntasFtas().remove(ccPreguntasFta);
		ccPreguntasFta.setCcPreguntasHdr(null);

		return ccPreguntasFta;
	}

	public CcHdrDto getCcHdr() {
		return this.ccHdr;
	}

	public void setCcHdr(CcHdrDto ccHdr) {
		this.ccHdr = ccHdr;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getTemaPregunta() {
		return temaPregunta;
	}

	public void setTemaPregunta(String temaPregunta) {
		this.temaPregunta = temaPregunta;
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

	public String getSociedad() {
		return sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public BigDecimal getMaxPuntuacion() {
		return maxPuntuacion;
	}

	public void setMaxPuntuacion(BigDecimal maxPuntuacion) {
		this.maxPuntuacion = maxPuntuacion;
	}

}