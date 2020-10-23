package com.cmrise.jpa.dto.mrqs;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="MRQS_CORRELACION_COLUMNA_RESPUESTAS")
@NamedQuery(name="MrqsCorrelacionColumnasRespuestasDto.findAll", query="SELECT m FROM MrqsCorrelacionColumnasRespuestasDto m")
public class MrqsCorrelacionColumnasRespuestasDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="NUMERO")
	private long numero;
	@Column(name="NUMERO_FTA")
	private long numero_fta;
	@Column(name="NUMERO_RESPUESTA_CORRECTA")
	private long numero_respuesta_correcta;
	@Column(name="ESTATUS")
	private boolean estatus;
	@Column(name="TEXTO_RESPUESTA")
	private String textoRespuesta;
	@Column(name="TEXTO")
	private String texto;	
	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;
	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;
	@Column(name="CREADO_POR")
	private long creadoPor;
	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;
	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;
	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;
	@Column(name="NUMERO_OPCION")
	private long numeroOpcion;
	@Transient
	private String valorSeleccionado;
	public MrqsCorrelacionColumnasRespuestasDto() {}
	public MrqsCorrelacionColumnasRespuestasDto(String textoRespuesta) {
		setTextoRespuesta(textoRespuesta);
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getNumero_fta() {
		return numero_fta;
	}
	public void setNumero_fta(long numero_fta) {
		this.numero_fta = numero_fta;
	}
	public long getNumero_respuesta_correcta() {
		return numero_respuesta_correcta;
	}
	public void setNumero_respuesta_correcta(long numero_respuesta_correcta) {
		this.numero_respuesta_correcta = numero_respuesta_correcta;
	}
	public boolean isEstatus() {
		return estatus;
	}
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}
	public String getTextoRespuesta() {
		return textoRespuesta;
	}
	public void setTextoRespuesta(String textoRespuesta) {
		this.textoRespuesta = textoRespuesta;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}
	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}
	public Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}
	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}
	public long getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public long getNumeroOpcion() {
		return numeroOpcion;
	}
	public void setNumeroOpcion(long numeroOpcion) {
		this.numeroOpcion = numeroOpcion;
	}
	
	public String getValorSeleccionado() {
		return valorSeleccionado;
	}
	public void setValorSeleccionado(String valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}
	@Override
	public String toString() {
		return getTextoRespuesta();
	}

}
