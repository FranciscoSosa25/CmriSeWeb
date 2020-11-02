package com.cmrise.ejb.model.mrqs.img;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MrqsImagenesGrp implements Serializable {

	private static final long serialVersionUID = 1L;
	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private String tipo; 
	private String tituloSuperior; 
	private String tituloInferior; 
	private String texto; 
	private String seccion; 
	
	private List<MrqsImagenes> listMrqsImagenes; 
	
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

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
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

	public List<MrqsImagenes> getListMrqsImagenes() {
		return listMrqsImagenes;
	}

	public void setListMrqsImagenes(List<MrqsImagenes> listMrqsImagenes) {
		this.listMrqsImagenes = listMrqsImagenes;
	}



}
