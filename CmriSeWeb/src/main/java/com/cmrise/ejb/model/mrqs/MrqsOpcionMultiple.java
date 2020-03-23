package com.cmrise.ejb.model.mrqs;

import java.io.Serializable;

public class MrqsOpcionMultiple implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int lineNumber; 
	private long numero; 
	private long numeroFta; 
	private boolean estatus; 
	private String textoRespuesta; 
	private String textoExplicacion;
	
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getNumeroFta() {
		return numeroFta;
	}
	public void setNumeroFta(long numeroFta) {
		this.numeroFta = numeroFta;
	}
	public boolean isEstatus() {
		return estatus;
	}
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}
	public String getTextoRespuesta() {
		return textoRespuesta;
	}
	public void setTextoRespuesta(String textoRespuesta) {
		this.textoRespuesta = textoRespuesta;
	}
	public String getTextoExplicacion() {
		return textoExplicacion;
	}
	public void setTextoExplicacion(String textoExplicacion) {
		this.textoExplicacion = textoExplicacion;
	} 

}
