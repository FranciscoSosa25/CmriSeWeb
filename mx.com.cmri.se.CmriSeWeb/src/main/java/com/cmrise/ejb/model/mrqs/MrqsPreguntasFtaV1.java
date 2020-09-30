package com.cmrise.ejb.model.mrqs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cmrise.ejb.model.mrqs.img.MrqsImagenesGrp;

public class MrqsPreguntasFtaV1 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long numero; 
	private long numeroHdr; 
	private String titulo; 
	private String textoPregunta; 
	private String textoSugerencias; 
	private String metodoPuntuacion; 
	private String valorPuntuacion; 
	private String respuestaCorrecta;
	private boolean singleAnswerMode; 
	private boolean suffleAnswerOrder; 
	private String nombreImagen; 
	private String rutaImagen; 
	private String contentType; 
	private String poligonos; 
	private byte[] imagenContent; 
	private String imagenBase64; 
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private int height; 
	private int width; 
	private String anotaciones; 
	private String respuestas; 
	private String correlaciones; 
	private String textoExplicacion; 
	
	
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
	private List<MrqsImagenesGrp> listMrqsImagenesGrp = new ArrayList<MrqsImagenesGrp>(); 
	
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getNumeroHdr() {
		return numeroHdr;
	}
	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTextoPregunta() {
		return textoPregunta;
	}
	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}
	public String getTextoSugerencias() {
		return textoSugerencias;
	}
	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}
	public String getMetodoPuntuacion() {
		return metodoPuntuacion;
	}
	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}
	public String getValorPuntuacion() {
		return valorPuntuacion;
	}
	public void setValorPuntuacion(String valorPuntuacion) {
		this.valorPuntuacion = valorPuntuacion;
	}
	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}
	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}
	public boolean isSingleAnswerMode() {
		return singleAnswerMode;
	}
	public void setSingleAnswerMode(boolean singleAnswerMode) {
		this.singleAnswerMode = singleAnswerMode;
	}
	public boolean isSuffleAnswerOrder() {
		return suffleAnswerOrder;
	}
	public void setSuffleAnswerOrder(boolean suffleAnswerOrder) {
		this.suffleAnswerOrder = suffleAnswerOrder;
	}
	public List<MrqsOpcionMultiple> getListMrqsOpcionMultiple() {
		return listMrqsOpcionMultiple;
	}
	public void setListMrqsOpcionMultiple(List<MrqsOpcionMultiple> listMrqsOpcionMultiple) {
		this.listMrqsOpcionMultiple = listMrqsOpcionMultiple;
	}
	public List<MrqsImagenesGrp> getListMrqsImagenesGrp() {
		return listMrqsImagenesGrp;
	}
	public void setListMrqsImagenesGrp(List<MrqsImagenesGrp> listMrqsImagenesGrp) {
		this.listMrqsImagenesGrp = listMrqsImagenesGrp;
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
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getPoligonos() {
		return poligonos;
	}
	public void setPoligonos(String poligonos) {
		this.poligonos = poligonos;
	}
	public byte[] getImagenContent() {
		return imagenContent;
	}
	public void setImagenContent(byte[] imagenContent) {
		this.imagenContent = imagenContent;
	}
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public long getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getImagenBase64() {
		return imagenBase64;
	}
	public void setImagenBase64(String imagenBase64) {
		this.imagenBase64 = imagenBase64;
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
	public String getAnotaciones() {
		return anotaciones;
	}
	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}
	public String getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(String respuestas) {
		this.respuestas = respuestas;
	}
	public String getCorrelaciones() {
		return correlaciones;
	}
	public void setCorrelaciones(String correlaciones) {
		this.correlaciones = correlaciones;
	} 
	public String getTextoExplicacion() {
		return textoExplicacion;
	}
	public void setTextoExplicacion(String textoExplicacion) {
		this.textoExplicacion = textoExplicacion;
	}

}
