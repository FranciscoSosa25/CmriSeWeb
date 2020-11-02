package com.cmrise.jpa.dto.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;


/**
 * The persistent class for the ADMON_USUARIOS_ROLES database table.
 * 
 */
@Entity
@Table(name="ADMON_USUARIOS_ROLES")
@NamedQuery(name="AdmonUsuariosRolesDto.findAll", query="SELECT a FROM AdmonUsuariosRolesDto a")
public class AdmonUsuariosRolesDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

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
	
	//bi-directional many-to-one association to AdmonRolesDto
	@ManyToOne
	@JoinColumn(name="NUMERO_ROL")
	private AdmonRolesDto admonRole;

	//bi-directional many-to-one association to AdmonUsuariosDto
	@ManyToOne
	@JoinColumn(name="NUMERO_USUARIO")
	private AdmonUsuariosDto admonUsuario;
	

	public AdmonUsuariosRolesDto() {
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

	public AdmonRolesDto getAdmonRole() {
		return this.admonRole;
	}

	public void setAdmonRole(AdmonRolesDto admonRole) {
		this.admonRole = admonRole;
	}

	public AdmonUsuariosDto getAdmonUsuario() {
		return this.admonUsuario;
	}

	public void setAdmonUsuario(AdmonUsuariosDto admonUsuario) {
		this.admonUsuario = admonUsuario;
	}


}