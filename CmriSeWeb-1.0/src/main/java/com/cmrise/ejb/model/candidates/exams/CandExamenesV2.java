package com.cmrise.ejb.model.candidates.exams;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CandExamenesV2 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long numero; 
	private long numeroUsuario; 
	private long numeroExamen; 
	private String descripcion;
	private String tipo; 
	private String estatus;
	private String matricula; 
	private String nombreUsuario;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreCompletoUsuario;
	private String nombreRol; 
	private String descripcionRol; 
	private String titulo; 
	private short tiempoLimite; 
	private int candExamTime;
	private double totalPuntuacion;
	private String curp; 
	private Date fechaEfectivaDesdeExamen; 
	private Date fechaEfectivaHastaExamen; 
	private long actualizadoPor;
	private String nombreActualizadoPor;
	private Date fechaActualizado;
	private String stringFechaAct;
	
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
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public short getTiempoLimite() {
		return tiempoLimite;
	}
	public void setTiempoLimite(short tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public Date getFechaEfectivaDesdeExamen() {
		return fechaEfectivaDesdeExamen;
	}
	public void setFechaEfectivaDesdeExamen(Date fechaEfectivaDesdeExamen) {
		this.fechaEfectivaDesdeExamen = fechaEfectivaDesdeExamen;
	}
	public Date getFechaEfectivaHastaExamen() {
		return fechaEfectivaHastaExamen;
	}
	public void setFechaEfectivaHastaExamen(Date fechaEfectivaHastaExamen) {
		this.fechaEfectivaHastaExamen = fechaEfectivaHastaExamen;
	}
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public String getNombreActualizadoPor() {
		return nombreActualizadoPor;
	}
	public void setNombreActualizadoPor(String nombreActualizadoPor) {
		this.nombreActualizadoPor = nombreActualizadoPor;
	}
	public Date getFechaActualizado() {
		return fechaActualizado;
	}
	public void setFechaActualizado(Date fechaActualizado) {
		this.fechaActualizado = fechaActualizado;
	}
	public String getStringFechaAct() {
		return stringFechaAct;
	}
	public void setStringFechaAct() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(this.fechaActualizado);  
		this.stringFechaAct = strDate;
	}
	public double getTotalPuntuacion() {
		return totalPuntuacion;
	}
	public void setTotalPuntuacion(double totalPuntuacion) {
		this.totalPuntuacion = totalPuntuacion;
	}
	public int getCandExamTime() {
		return candExamTime;
	}
	public void setCandExamTime(int candExamTime) {
		this.candExamTime = candExamTime;
	} 
	
}