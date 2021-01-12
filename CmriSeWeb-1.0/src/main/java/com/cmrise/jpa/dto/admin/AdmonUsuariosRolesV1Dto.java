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
@Table(name="ADMON_USUARIOS_ROLES_V1")
@NamedQuery(name="AdmonUsuariosRolesV1Dto.findAll", query="SELECT a FROM AdmonUsuariosRolesV1Dto a")
public class AdmonUsuariosRolesV1Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="DESCRIPCION_ROL")
	private String descripcionRol;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FED_AR")
	private Date fedAr;

	@Column(name="FED_AU")
	private Date fedAu;

	@Column(name="FED_AUR")
	private Date fedAur;

	@Column(name="FEH_AR")
	private Date fehAr;

	@Column(name="FEH_AU")
	private Date fehAu;

	@Column(name="FEH_AUR")
	private Date fehAur;

	@Column(name="MATRICULA")
	private String matricula;

	@Column(name="NOMBRE_COMPLETO_USUARIO")
	private String nombreCompletoUsuario;

	@Column(name="NOMBRE_ROL")
	private String nombreRol;

	@Column(name="NOMBRE_USUARIO")
	private String nombreUsuario;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="NUMERO_ROL")
	private long numeroRol;

	@Column(name="NUMERO_USUARIO")
	private long numeroUsuario;
	
	@Column(name="APELLIDO_PATERNO")
	private String apellidoPaterno;
	
	@Column(name="APELLIDO_MATERNO")
	private String apellidoMaterno;
	
	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;
	
	@Column(name="CONTRASENIA")
	private String contrasenia; 
	
	@Column(name="CURP")
	private String curp; 
	
	@Column(name="ESTADO")
	private String estado; 
	
	@Column(name="SEDE_HOSPITAL")
	private String sedeHospital; 

	@Column(name="NOMBRE_ACT_POR")
	private String nombreActPor; 

	public AdmonUsuariosRolesV1Dto() {
	}

	public long getActualizadoPor() {
		return this.actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getDescripcionRol() {
		return this.descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFedAr() {
		return this.fedAr;
	}

	public void setFedAr(Date fedAr) {
		this.fedAr = fedAr;
	}

	public Date getFedAu() {
		return this.fedAu;
	}

	public void setFedAu(Date fedAu) {
		this.fedAu = fedAu;
	}

	public Date getFedAur() {
		return this.fedAur;
	}

	public void setFedAur(Date fedAur) {
		this.fedAur = fedAur;
	}

	public Date getFehAr() {
		return this.fehAr;
	}

	public void setFehAr(Date fehAr) {
		this.fehAr = fehAr;
	}

	public Date getFehAu() {
		return this.fehAu;
	}

	public void setFehAu(Date fehAu) {
		this.fehAu = fehAu;
	}

	public Date getFehAur() {
		return this.fehAur;
	}

	public void setFehAur(Date fehAur) {
		this.fehAur = fehAur;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombreCompletoUsuario() {
		return this.nombreCompletoUsuario;
	}

	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}

	public String getNombreRol() {
		return this.nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
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

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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
	
	public String getNombreActPor() {
		return this.nombreActPor;
	}

	public void setActualizadoPor(String nombreActPor) {
		this.nombreActPor = nombreActPor;
	}

}