package com.cmrise.jpa.dto.mrqs.img;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MRQS_IMAGENES_GRP")
@NamedQuery(name="MrqsImagenesGrpDto.findAll", query="SELECT m FROM MrqsImagenesGrpDto m")
public class MrqsImagenesGrpDto implements Serializable {

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
	
	@Column(name="TIPO")
	private String tipo; 
	
	@Column(name="TITULO_SUPERIOR")
	private String tituloSuperior; 
	
	@Column(name="TITULO_INFERIOR")
	private String tituloInferior; 
	
	@Column(name="TEXTO")
	private String texto; 
	
	@Column(name="SECCION")
	private String seccion; 
	
	@Column(name="NUMERO_FTA")
	private long numeroFta; 
	
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTituloSuperior() {
		return tituloSuperior;
	}

	public void setTituloSuperior(String tituloSuperior) {
		this.tituloSuperior = tituloSuperior;
	}

	public String getTituloInferior() {
		return tituloInferior;
	}

	public void setTituloInferior(String tituloInferior) {
		this.tituloInferior = tituloInferior;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public long getNumeroFta() {
		return numeroFta;
	}

	public void setNumeroFta(long numeroFta) {
		this.numeroFta = numeroFta;
	}

}

