package com.cmrise.ejb.model.admin;

import java.io.Serializable;

public class AdmonCandidatosV1 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombreCompletoUsuario;
	private long numeroRol;
	private long numeroUsuario;
	private String curp;
	private String correoElectronico; 
	
	
	public String getNombreCompletoUsuario() {
		return nombreCompletoUsuario;
	}
	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}
	public long getNumeroRol() {
		return numeroRol;
	}
	public void setNumeroRol(long numeroRol) {
		this.numeroRol = numeroRol;
	}
	public long getNumeroUsuario() {
		return numeroUsuario;
	}
	public void setNumeroUsuario(long numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}


}
