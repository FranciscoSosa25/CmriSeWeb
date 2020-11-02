package com.cmrise.jpa.dto.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the ADMON_ROLES database table.
 * 
 */
@Entity
@Table(name="ADMON_ROLES")
@NamedQuery(name="AdmonRolesDto.findAll", query="SELECT a FROM AdmonRolesDto a")
public class AdmonRolesDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="DESCRIPCION")
	private String descripcion;

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
	@OneToMany(mappedBy="admonRole")
	private List<AdmonUsuariosRolesDto> admonUsuariosRoles;

	public AdmonRolesDto() {
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

	public long getCreadoPor() {
		return this.creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		admonUsuariosRole.setAdmonRole(this);

		return admonUsuariosRole;
	}

	public AdmonUsuariosRolesDto removeAdmonUsuariosRole(AdmonUsuariosRolesDto admonUsuariosRole) {
		getAdmonUsuariosRoles().remove(admonUsuariosRole);
		admonUsuariosRole.setAdmonRole(null);

		return admonUsuariosRole;
	}

}