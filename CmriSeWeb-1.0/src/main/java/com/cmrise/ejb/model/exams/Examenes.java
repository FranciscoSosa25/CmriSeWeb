package com.cmrise.ejb.model.exams;

import java.io.Serializable;

public class Examenes implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numero; 
	private String titulo; 
	private String nombre; 
	private String descripcion; 
	private short tiempoLimite; 
	private String tipoExamenCode; 
	private String tipoExamenDesc; 
	private java.util.Date fechaEfectivaDesde;
	private java.util.Date fechaEfectivaHasta; 
	private long totalCandidatos;
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public short getTiempoLimite() {
		return tiempoLimite;
	}
	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}
	public String getTipoExamenCode() {
		return tipoExamenCode;
	}
	public void setTipoExamenCode(String tipoExamenCode) {
		this.tipoExamenCode = tipoExamenCode;
	}
	public String getTipoExamenDesc() {
		return tipoExamenDesc;
	}
	public void setTipoExamenDesc(String tipoExamenDesc) {
		this.tipoExamenDesc = tipoExamenDesc;
	}
	public java.util.Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}
	public void setFechaEfectivaDesde(java.util.Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}
	public long getTotalCandidatos() {
		return totalCandidatos;
	}
	public void setTotalCandidatos(long totalCandidatos) {
		this.totalCandidatos = totalCandidatos;
	}
	public java.util.Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}
	public void setFechaEfectivaHasta(java.util.Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	} 
	
	
}
