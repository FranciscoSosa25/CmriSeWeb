package com.cmrise.jpa.dto.corecases;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="CC_PREGUNTAS_FTA_SINONIMOS")
public class CcPreguntasFtaSinonimos implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="NUMERO")
	private long numero;
	@Column(name="NUMERO_FTA")
	private long numero_fta;
	@Column(name="ESTATUS")
	private boolean estatus;
	@Column(name="TEXTO_PREGUNTA")
	private String textoPregunta;
	@Column(name="TEXTO_SINONIMO")
	private String textoSinonimo;	
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
	@Transient
	private boolean actualizado;
	
	public CcPreguntasFtaSinonimos() {}
	public CcPreguntasFtaSinonimos(String textoSinonimo){
		setTextoSinonimo(textoSinonimo);
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
	public String getTextoPregunta() {
		return textoPregunta;
	}
	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}
	public String getTextoSinonimo() {
		return textoSinonimo;
	}
	public void setTextoSinonimo(String textoSinonimo) {
		this.textoSinonimo = textoSinonimo;
	}
	
	public boolean isActualizado() {
		return actualizado;
	}
	public void setActualizado(boolean actualizado) {
		this.actualizado = actualizado;
	}
	@Override
	public String toString() {
		return getTextoSinonimo();
	}
}
