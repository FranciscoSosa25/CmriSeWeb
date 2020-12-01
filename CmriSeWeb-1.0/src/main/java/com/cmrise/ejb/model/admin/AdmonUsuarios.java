package com.cmrise.ejb.model.admin;

import java.io.Serializable;
import java.util.Date;


public class AdmonUsuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long numero;
	private long actualizadoPor;
    private String actualizadoPorName;
	private String apellidoMaterno;
	private String apellidoPaterno;
	private String contrasenia;
	private String correoElectronico;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String nombre;
	private long numeroRol; 
	private String nombreRol; 
	private String curp; 
	private String estado;
	private String sedeHospital;
	
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public long getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getNumeroRol() {
		return numeroRol;
	}
	public void setNumeroRol(long numeroRol) {
		this.numeroRol = numeroRol;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getSedeHospital() {
		return this.sedeHospital;
	}

	public void setSedeHospital(String sedeHospital) {
		this.sedeHospital = sedeHospital;
	}

	public String getActualizadoPorName() {
		return actualizadoPorName;
	}

	public void setActualizadoPorName(String actualizadoPorName) {
		this.actualizadoPorName = actualizadoPorName;
	}
	

}
