package com.cmrise.jpa.dto.admin;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the ADMON_USUARIOS_ROLES_V1 database table.
 * 
 */
@Entity
@Table(name="ADMON_CANDIDATOS_V1")
@NamedQuery(name="AdmonCandidatosV1Dto.findAll", query="SELECT a FROM AdmonCandidatosV1Dto a")
public class AdmonCandidatosV1Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="NOMBRE_COMPLETO_USUARIO")
	private String nombreCompletoUsuario;


	@Column(name="NUMERO_ROL")
	private long numeroRol;
	@Id
	@Column(name="NUMERO_USUARIO")
	private long numeroUsuario;
	
	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;
	
	@Column(name="CURP")
	private String curp; 

	public AdmonCandidatosV1Dto() {
	}

	public String getNombreCompletoUsuario() {
		return this.nombreCompletoUsuario;
	}

	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}

	

	public long getNumeroRol() {
		return this.numeroRol;
	}

	public void setNumeroRol(long numeroRol) {
		this.numeroRol = numeroRol;
	}

	public long getNumeroUsuario() {
		return this.numeroUsuario;
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