package com.cmrise.jpa.dto.mrqs;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the MRQS_PREGUNTAS_HDR database table.
 * 
 */
@Entity
@Table(name="MRQS_PREGUNTAS_HDR")
@NamedQuery(name="MrqsPreguntasHdrDto.findAll", query="SELECT m FROM MrqsPreguntasHdrDto m")
public class MrqsPreguntasHdrDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="COMENTARIOS")
	private String comentarios;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="ESTATUS")
	private String estatus;

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

	@Column(name="SOCIEDAD")
	private String sociedad;

	@Column(name="TEMA_PREGUNTA")
	private String temaPregunta;

	@Column(name="TIPO_PREGUNTA")
	private String tipoPregunta;

	@Column(name="TITULO")
	private String titulo;

	//bi-directional one-to-one association to MrqsPreguntasFtaDto
	@OneToOne(mappedBy="mrqsPreguntasHdr1")
	private MrqsPreguntasFtaDto mrqsPreguntasFta;

	//bi-directional many-to-one association to MrqsPreguntasFtaDto
	@OneToMany(mappedBy="mrqsPreguntasHdr2")
	private List<MrqsPreguntasFtaDto> mrqsPreguntasFtas;

	public MrqsPreguntasHdrDto() {
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

	public String getTipoPregunta() {
		return this.tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public MrqsPreguntasFtaDto getMrqsPreguntasFta() {
		return this.mrqsPreguntasFta;
	}

	public void setMrqsPreguntasFta(MrqsPreguntasFtaDto mrqsPreguntasFta) {
		this.mrqsPreguntasFta = mrqsPreguntasFta;
	}

	public List<MrqsPreguntasFtaDto> getMrqsPreguntasFtas() {
		return this.mrqsPreguntasFtas;
	}

	public void setMrqsPreguntasFtas(List<MrqsPreguntasFtaDto> mrqsPreguntasFtas) {
		this.mrqsPreguntasFtas = mrqsPreguntasFtas;
	}

	public MrqsPreguntasFtaDto addMrqsPreguntasFta(MrqsPreguntasFtaDto mrqsPreguntasFta) {
		getMrqsPreguntasFtas().add(mrqsPreguntasFta);
		mrqsPreguntasFta.setMrqsPreguntasHdr2(this);

		return mrqsPreguntasFta;
	}

	public MrqsPreguntasFtaDto removeMrqsPreguntasFta(MrqsPreguntasFtaDto mrqsPreguntasFta) {
		getMrqsPreguntasFtas().remove(mrqsPreguntasFta);
		mrqsPreguntasFta.setMrqsPreguntasHdr2(null);

		return mrqsPreguntasFta;
	}

}