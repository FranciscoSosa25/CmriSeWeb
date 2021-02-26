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
	@Column(name="ID_MAESTRO")
	private long idMaestro;
	
	@Column(name="ID_MATERIA")
	private long idMateria;
		
	public long getIdMaestro() {
		return this.idMaestro;
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

}