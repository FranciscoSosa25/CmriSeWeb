package com.cmrise.ejb.model.mrqs;

import java.io.Serializable;

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
	

}
