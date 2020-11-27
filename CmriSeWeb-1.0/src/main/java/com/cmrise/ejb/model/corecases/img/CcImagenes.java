package com.cmrise.ejb.model.corecases.img;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class CcImagenes implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private String nombreImagen; 
	private String rutaImagen; 
	private byte[] imagenContent; 
	private long numeroGrp; 
	private String thumbailBase64; 
	private byte[] thumbailContent; 
	private String jpgBase64;
	private byte[] jpgContent; 
	private StreamedContent jpgStreamedContent; 
	
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

	public String getThumbailBase64() {
		return thumbailBase64;
	}

	public void setThumbailBase64(String thumbailBase64) {
		this.thumbailBase64 = thumbailBase64;
	}

	public byte[] getThumbailContent() {
		return thumbailContent;
	}

	public void setThumbailContent(byte[] thumbailContent) {
		this.thumbailContent = thumbailContent;
	}

	public String getJpgBase64() {
		return jpgBase64;
	}

	public void setJpgBase64(String jpgBase64) {
		this.jpgBase64 = jpgBase64;
	}

	public byte[] getJpgContent() {
		return jpgContent;
	}

	public void setJpgContent(byte[] jpgContent) {
		this.jpgContent = jpgContent;
	}

	public StreamedContent getJpgStreamedContent() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		/**
		if(context.getCurrentPhaseId()==PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent(); 
		}
		**/
		jpgStreamedContent = DefaultStreamedContent.builder()
                    .contentType("image/png")
                    .stream(() -> {
                    			InputStream is = new ByteArrayInputStream(getJpgContent());
                  				return is;	
					     })
                     .build()
                     ;
		return jpgStreamedContent;
	}

	public void setJpgStreamedContent(StreamedContent jpgStreamedContent) {
		this.jpgStreamedContent = jpgStreamedContent;
	}

}
