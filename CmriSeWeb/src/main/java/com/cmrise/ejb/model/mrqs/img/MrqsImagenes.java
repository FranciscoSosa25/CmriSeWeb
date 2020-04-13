package com.cmrise.ejb.model.mrqs.img;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.StreamedContent;

public class MrqsImagenes implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private String nombreImagen; 
	private String rutaImagen; 
	private StreamedContent imagenStreamed; 
	private String imagenBase64; 
	private byte[] imagenContent; 
	private long numeroGrp; 
	
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

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public StreamedContent getImagenStreamed() {
		return imagenStreamed;
	}

	public void setImagenStreamed(StreamedContent imagenStreamed) {
		this.imagenStreamed = imagenStreamed;
	}

	public String getImagenBase64() {
		return imagenBase64;
	}

	public void setImagenBase64(String imagenBase64) {
		this.imagenBase64 = imagenBase64;
	}

	public byte[] getImagenContent() {
		return imagenContent;
	}

	public void setImagenContent(byte[] imagenContent) {
		this.imagenContent = imagenContent;
	}

	public long getNumeroGrp() {
		return numeroGrp;
	}

	public void setNumeroGrp(long numeroGrp) {
		this.numeroGrp = numeroGrp;
	}

}
