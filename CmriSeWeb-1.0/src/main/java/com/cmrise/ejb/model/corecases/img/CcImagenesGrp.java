package com.cmrise.ejb.model.corecases.img;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CcImagenesGrp implements Serializable {

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
	private String modality;
	private boolean isCcHDR;
	private boolean dicom;

	
	private List<CcImagenes> listCcImagenes; 
	
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

	public List<CcImagenes> getListCcImagenes() {
		return listCcImagenes;
	}

	public void setListCcImagenes(List<CcImagenes> listCcImagenes) {
		this.listCcImagenes = listCcImagenes;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public boolean isCcHDR() {
		return isCcHDR;
	}

	public void setCcHDR(boolean isCcHDR) {
		this.isCcHDR = isCcHDR;
	}

	public boolean isDicom() {
		return dicom;
	}

	public void setDicom(boolean dicom) {
		this.dicom = dicom;
	}

	
	

}

