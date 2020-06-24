package com.cmrise.ejb.model.mrqs;

import java.io.Serializable;

public class RespReactCorImg implements Serializable{

	private static final long serialVersionUID = 1L;

	private int numero; 
	private String respuesta;

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	} 
	
	@Override
    public String toString() { 
        return respuesta;
    } 
	
}
