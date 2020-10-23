package com.cmrise.jpa.dto.mrqs;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MRQS_CORRELACION_COLUMNA_OPCIONES")
@NamedQuery(name="MrqsCorrelacionColumnasOpcionesDto.findAll", query="SELECT m FROM MrqsCorrelacionColumnasDto m")
public class MrqsCorrelacionColumnasDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="NUMERO")
	private long numero;
	@Column(name="NUMERO_FTA")
	private long numero_fta;
	@Column(name="ESTATUS")
	private boolean estatus;
	@Column(name="TEXTO_RESPUESTA")
	private String textoRespuesta;
	@Column(name="TEXTO_EXPLICACION")
	private String textoExplicacion;	
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

	public MrqsCorrelacionColumnasDto() {}
	public MrqsCorrelacionColumnasDto(String textoRespuesta){
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
	public String getTextoExplicacion() {
		return textoExplicacion;
	}
	public void setTextoExplicacion(String textoExplicacion) {
		this.textoExplicacion = textoExplicacion;
	}
	public Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}
	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}
	public long getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}
	public Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}
	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public long getNumeroOpcion() {
		return numeroOpcion;
	}
	public void setNumeroOpcion(long numeroOpcion) {
		this.numeroOpcion = numeroOpcion;
	}
	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	@Override
	public String toString() {
		return getTextoRespuesta();
	}
	
	
}
