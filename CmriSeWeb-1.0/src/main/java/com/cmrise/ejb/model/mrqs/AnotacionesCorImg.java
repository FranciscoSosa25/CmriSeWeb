package com.cmrise.ejb.model.mrqs;

import java.io.Serializable;

public class AnotacionesCorImg implements Serializable{

	private static final long serialVersionUID = 1L;
 	private int numero;
	private String nodo;
	private int numeroRespuesta;
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getNodo() {
		return nodo;
	}
	public void setNodo(String nodo) {
		this.nodo = nodo;
	}
	public int getNumeroRespuesta() {
		return numeroRespuesta;
	}
	public void setNumeroRespuesta(int numeroRespuesta) {
		this.numeroRespuesta = numeroRespuesta;
	} 
	
}
