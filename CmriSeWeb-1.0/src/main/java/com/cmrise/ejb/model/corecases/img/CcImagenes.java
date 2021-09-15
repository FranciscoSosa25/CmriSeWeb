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
	private String filePath;
	private String dcmKey;	
	private String poligonoModel;
	private int height;
	private int width;
	private int poligonos; 
	private String respuestasPuntos;
	private float puntoCorrectos;
	private String contentType; 
	private boolean image; 
	private boolean video;
	private boolean dicom;
	private StreamedContent imagenStreamed; 
	private StreamedContent imagen; 
	private StreamedContent videoStreamed; 
	private String imagenBase64; 
 
	
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDcmKey() {
		return dcmKey;
	}

	public void setDcmKey(String dcmKey) {
		this.dcmKey = dcmKey;
	}

	public String getPoligonoModel() {
		return poligonoModel;
	}

	public void setPoligonoModel(String poligonoModel) {
		this.poligonoModel = poligonoModel;
	}

	public int getPoligonos() {
		return poligonos;
	}

	public void setPoligonos(int poligonos) {
		this.poligonos = poligonos;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getRespuestasPuntos() {
		return respuestasPuntos;
	}

	public void setRespuestasPuntos(String respuestasPuntos) {
		this.respuestasPuntos = respuestasPuntos;
	}

	public float getPuntoCorrectos() {
		return puntoCorrectos;
	}

	public void setPuntoCorrectos(float puntoCorrectos) {
		this.puntoCorrectos = puntoCorrectos;
	}

	public StreamedContent getImagenStreamed() {
		return imagenStreamed;
	}

	public void setImagenStreamed(StreamedContent imagenStreamed) {
		this.imagenStreamed = imagenStreamed;
	}

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	public StreamedContent getVideoStreamed() {
		return videoStreamed;
	}

	public void setVideoStreamed(StreamedContent videoStreamed) {
		this.videoStreamed = videoStreamed;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		if(contentType.contains("video")) {
			this.setVideo(true);
		}else if(contentType.contains("image")) {
			this.setImage(true);
		}
		this.contentType = contentType;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public String getImagenBase64() {
		return imagenBase64;
	}

	public void setImagenBase64(String imagenBase64) {
		this.imagenBase64 = imagenBase64;
	}

	public boolean isDicom() {
		return dicom;
	}

	public void setDicom(boolean dicom) {
		this.dicom = dicom;
	}

		
	
	
	
}
