package com.cmrise.ejb.model.candidates.exams;

import java.io.Serializable;

public class Examinations implements Serializable{

	private static final long serialVersionUID = 1L;

	private long numeroAdmonCcCandidato; 
	private long numeroAdmonUsuario; 
	private long numeroCcExamen; 
	private String tituloExamen; 
	private short tiempoLimite;
	
	public long getNumeroAdmonCcCandidato() {
		return numeroAdmonCcCandidato;
	}
	public void setNumeroAdmonCcCandidato(long numeroAdmonCcCandidato) {
		this.numeroAdmonCcCandidato = numeroAdmonCcCandidato;
	}
	public long getNumeroAdmonUsuario() {
		return numeroAdmonUsuario;
	}
	public void setNumeroAdmonUsuario(long numeroAdmonUsuario) {
		this.numeroAdmonUsuario = numeroAdmonUsuario;
	}
	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}
	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}
	public String getTituloExamen() {
		return tituloExamen;
	}
	public void setTituloExamen(String tituloExamen) {
		this.tituloExamen = tituloExamen;
	}
	public short getTiempoLimite() {
		return tiempoLimite;
	}
	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	} 
	
}
