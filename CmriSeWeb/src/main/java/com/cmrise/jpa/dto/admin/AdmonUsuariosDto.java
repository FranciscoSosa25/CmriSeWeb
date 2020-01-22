package com.cmrise.jpa.dto.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the ADMON_USUARIOS database table.
 * 
 */
@Entity
@Table(name="ADMON_USUARIOS")
@NamedQuery(name="AdmonUsuariosDto.findAll", query="SELECT a FROM AdmonUsuariosDto a")
public class AdmonUsuariosDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="APELLIDO_MATERNO")
	private String apellidoMaterno;

	@Column(name="APELLIDO_PATERNO")
	private String apellidoPaterno;

	@Column(name="CONTRASENIA")
	private String contrasenia;

	@Column(name="CORREO_ELECTRONICO")
	private String correoElectronico;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="NOMBRE")
	private String nombre;

	
	//bi-directional many-to-one association to AdmonUsuariosRolesDto
	@OneToMany(mappedBy="admonUsuario")
	private List<AdmonUsuariosRolesDto> admonUsuariosRoles;

	public AdmonUsuariosDto() {
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public long getActualizadoPor() {
		return this.actualizadoPor;
	}

	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
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

	public Date getFechaEfectivaDesde() {
		return this.fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public Date getFechaEfectivaHasta() {
		return this.fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public List<AdmonUsuariosRolesDto> getAdmonUsuariosRoles() {
		return this.admonUsuariosRoles;
	}

	public void setAdmonUsuariosRoles(List<AdmonUsuariosRolesDto> admonUsuariosRoles) {
		this.admonUsuariosRoles = admonUsuariosRoles;
	}

	public AdmonUsuariosRolesDto addAdmonUsuariosRole(AdmonUsuariosRolesDto admonUsuariosRole) {
		getAdmonUsuariosRoles().add(admonUsuariosRole);
		admonUsuariosRole.setAdmonUsuario(this);

		return admonUsuariosRole;
	}

	public AdmonUsuariosRolesDto removeAdmonUsuariosRole(AdmonUsuariosRolesDto admonUsuariosRole) {
		getAdmonUsuariosRoles().remove(admonUsuariosRole);
		admonUsuariosRole.setAdmonUsuario(null);

		return admonUsuariosRole;
	}

}