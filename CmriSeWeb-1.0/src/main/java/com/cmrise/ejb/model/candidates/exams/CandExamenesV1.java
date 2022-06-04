package com.cmrise.ejb.model.candidates.exams;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;

public class CandExamenesV1  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long numero; 
	private long numeroUsuario; 
	private long numeroExamen; 
	private String tipo; 
	private String estatus;
	private String matricula; 
	private String nombreUsuario;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreCompletoUsuario;
	private String nombreRol; 
	private String descripcionRol; 
	private String curp; 
	private double totalPuntuacion; 
	private long actualizadoPor;
	private Date fechaActualizacion;
	private String actualizadoPorString;
	private String fechaActString;
	private String nombreActPor;	
	private int candExamTime;
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getNumeroUsuario() {
		return numeroUsuario;
	}
	public void setNumeroUsuario(long numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}
	public long getNumeroExamen() {
		return numeroExamen;
	}
	public void setNumeroExamen(long numeroExamen) {
		this.numeroExamen = numeroExamen;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombreCompletoUsuario() {
		return nombreCompletoUsuario;
	}
	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	public String getDescripcionRol() {
		return descripcionRol;
	}
	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public double getTotalPuntuacion() {
		return totalPuntuacion;
	}
	public void setTotalPuntuacion(double totalPuntuacion) {
		this.totalPuntuacion = totalPuntuacion;
	}
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getActualizadoPorString() {
		return actualizadoPorString;
	}
	public void setActualizadoPorString() {
		this.actualizadoPorString = Long.toString(this.actualizadoPor);
	}
	public String getFechaActString() {
		return fechaActString;
	}
	public void setFechaActString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(this.fechaActualizacion);  
		this.fechaActString = strDate;
	}
	public String getNombreActPor() {
		return nombreActPor;
	}
	public void setNombreActPor(String nombreActPor) {
		this.nombreActPor = nombreActPor;
	}
	public int getCandExamTime() {
		return candExamTime;
	}
	public void setCandExamTime(int candExamTime) {
		this.candExamTime = candExamTime;
	} 
	

}
