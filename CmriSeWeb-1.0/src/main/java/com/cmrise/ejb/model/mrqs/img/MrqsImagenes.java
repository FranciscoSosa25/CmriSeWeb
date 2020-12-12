package com.cmrise.ejb.model.mrqs.img;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.primefaces.component.graphicimage.GraphicImage;
import org.primefaces.model.DefaultStreamedContent;
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
	private StreamedContent imagen; 
	private StreamedContent videoStreamed; 
	private String imagenBase64; 
	private byte[] imagenContent; 
	private long numeroGrp; 
	private String contentType; 
	private boolean image; 
	private boolean video; 
	
	
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String pContentType) {
		if(pContentType.contains("video")) {
			this.setVideo(true);
		}else if(pContentType.contains("image")) {
			this.setImage(true);
		}
		this.contentType = pContentType;
	}

	public StreamedContent getVideoStreamed() {
		if(video) {
		videoStreamed = DefaultStreamedContent.builder()
        .contentType(getContentType())
        .stream(() -> {
        			InputStream is = new ByteArrayInputStream(getImagenContent());
      				return is;	
		     })
         .build()
         ;
		}
		return videoStreamed;
	}

	public void setVideoStreamed(StreamedContent videoStreamed) {
		this.videoStreamed = videoStreamed;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		if(image) {
			setVideo(false);
		}
		this.image = image;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		if(video) {
			setImage(false); 
		}
		this.video = video;
	}

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}
	

}
