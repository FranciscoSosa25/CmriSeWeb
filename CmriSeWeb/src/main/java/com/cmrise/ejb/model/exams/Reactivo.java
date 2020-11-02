package com.cmrise.ejb.model.exams;

import java.io.Serializable;

public class Reactivo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numero; 
	private int materiaIdx; 
	private int preguntaIdx;
	public int getMateriaIdx() {
		return materiaIdx;
	}
	public void setMateriaIdx(int materiaIdx) {
		this.materiaIdx = materiaIdx;
	}
	public int getPreguntaIdx() {
		return preguntaIdx;
	}
	public void setPreguntaIdx(int preguntaIdx) {
		this.preguntaIdx = preguntaIdx;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	} 
	
	@Override
    public String toString() { 
        return "Numero Pregunta:"+numero+", indiceMateria:"+materiaIdx+", indicePregunta:"+preguntaIdx; 
    } 
	
}
