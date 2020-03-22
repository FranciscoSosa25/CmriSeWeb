package com.cmrise.ejb.model.candidates.exams;

import java.io.Serializable;
import java.util.Date;

public class CandCcExamenesV1 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long numero; 
	private long numeroCcExamen; 
	private long numeroCcph; 
	private long numeroCph; 
	private long numeroCcpf; 
	private long numeroCpf; 
	private long numeroCandidato; 
	private String estatus; 
	private Date fechaEfectivaDesde; 
	private Date fechaEfectivaHasta; 
	private String tituloPregunta; 
	private String nombreExamen; 
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}
	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}
	public long getNumeroCcph() {
		return numeroCcph;
	}
	public void setNumeroCcph(long numeroCcph) {
		this.numeroCcph = numeroCcph;
	}
	public long getNumeroCph() {
		return numeroCph;
	}
	public void setNumeroCph(long numeroCph) {
		this.numeroCph = numeroCph;
	}
	public long getNumeroCcpf() {
		return numeroCcpf;
	}
	public void setNumeroCcpf(long numeroCcpf) {
		this.numeroCcpf = numeroCcpf;
	}
	public long getNumeroCpf() {
		return numeroCpf;
	}
	public void setNumeroCpf(long numeroCpf) {
		this.numeroCpf = numeroCpf;
	}
	public long getNumeroCandidato() {
		return numeroCandidato;
	}
	public void setNumeroCandidato(long numeroCandidato) {
		this.numeroCandidato = numeroCandidato;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}
	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}
	public Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}
	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}
	public String getTituloPregunta() {
		return tituloPregunta;
	}
	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}
	public String getNombreExamen() {
		return nombreExamen;
	}
	public void setNombreExamen(String nombreExamen) {
		this.nombreExamen = nombreExamen;
	}
	
}
