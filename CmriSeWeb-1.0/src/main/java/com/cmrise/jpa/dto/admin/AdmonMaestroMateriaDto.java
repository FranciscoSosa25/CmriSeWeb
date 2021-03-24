package com.cmrise.jpa.dto.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MAESTRO_HAS_MATERIA")
@NamedQuery(name="AdmonMaestroMateriaDto.findAll", query="SELECT a FROM AdmonMaestroMateriaDto a")
public class AdmonMaestroMateriaDto  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="NUMERO")
	private long numero;
	
	@Column(name="ID_MAESTRO")
	private long idMaestro;
	
	@Column(name="ID_MATERIA")
	private long idMateria;
	
	@Column(name="ESTATUS")
	private boolean estatus;
		
	public long getIdMaestro() {
		return this.idMaestro;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}
	
	public long getNumero() {
		return this.numero;
	}
	public void setIdMaestro(long idMaestro) {
		this.idMaestro = idMaestro;
	}

	public long getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(long idMateria) {
		this.idMateria = idMateria;
	}
	
	public boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

}