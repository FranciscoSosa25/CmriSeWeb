package com.cmrise.jpa.dto.corecases;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the CC_HDR_V1 database table.
 * 
 */
@Entity
@Table(name="CC_HDR_V1")
@NamedQuery(name="CcHdrV1Dto.findAll", query="SELECT c FROM CcHdrV1Dto c")
public class CcHdrV1Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACTUALIZADO_POR")
	private long actualizadoPor;

	@Column(name="CREADO_POR")
	private long creadoPor;

	@Column(name="DESCRIPCION_TECNICA")
	private String descripcionTecnica;

	@Column(name="ESTATUS")
	private String estatus;

	@Column(name="ESTATUS_DESC")
	private String estatusDesc;

	@Column(name="ETIQUETAS")
	private String etiquetas;

	@Column(name="FECHA_ACTUALIZACION")
	private Timestamp fechaActualizacion;

	@Column(name="FECHA_CREACION")
	private Timestamp fechaCreacion;

	@Column(name="FECHA_EFECTIVA_DESDE")
	private Date fechaEfectivaDesde;

	@Column(name="FECHA_EFECTIVA_HASTA")
	private Date fechaEfectivaHasta;

	@Column(name="HISTORIAL_CLINICO")
	private String historialClinico;
	
	@Column(name="NOTA")
	private String nota;

	@Id
	@Column(name="NUMERO")
	private long numero;

	@Column(name="OPCION_INSEGURA")
	private boolean opcionInsegura;

	@Column(name="FECHA_ELABORACION")
	private Date fechaElaboracion; 
	
	@Column(name="ADMON_EXAMEN")
	private long admonExamen; 
	
	@Column(name="ADMON_MATERIA")
	private long admonMateria; 
	
	@Column(name="ADMON_SUBMATERIA")
	private long admonSubmateria; 
	
	@Column(name="ADMON_EXAMEN_DESC")
	private String admonExamenDesc; 
	
	@Column(name="ADMON_MATERIA_DESC")
	private String admonMateriaDesc; 
	
	@Column(name="ADMON_SUBMATERIA_DESC")
	private String admonSubmateriaDesc; 
	
	
	public CcHdrV1Dto() {
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

	public String getDescripcionTecnica() {
		return this.descripcionTecnica;
	}

	public void setDescripcionTecnica(String descripcionTecnica) {
		this.descripcionTecnica = descripcionTecnica;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getEstatusDesc() {
		return this.estatusDesc;
	}

	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
	}

	public String getEtiquetas() {
		return this.etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
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

	public String getHistorialClinico() {
		return this.historialClinico;
	}

	public void setHistorialClinico(String historialClinico) {
		this.historialClinico = historialClinico;
	}

	public String getNota() {
		return this.nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public boolean getOpcionInsegura() {
		return this.opcionInsegura;
	}

	public void setOpcionInsegura(boolean opcionInsegura) {
		this.opcionInsegura = opcionInsegura;
	}

	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}

	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}

	public long getAdmonExamen() {
		return admonExamen;
	}

	public void setAdmonExamen(long admonExamen) {
		this.admonExamen = admonExamen;
	}

	public long getAdmonMateria() {
		return admonMateria;
	}

	public void setAdmonMateria(long admonMateria) {
		this.admonMateria = admonMateria;
	}

	public long getAdmonSubmateria() {
		return admonSubmateria;
	}

	public void setAdmonSubmateria(long admonSubmateria) {
		this.admonSubmateria = admonSubmateria;
	}

	public String getAdmonExamenDesc() {
		return admonExamenDesc;
	}

	public void setAdmonExamenDesc(String admonExamenDesc) {
		this.admonExamenDesc = admonExamenDesc;
	}

	public String getAdmonMateriaDesc() {
		return admonMateriaDesc;
	}

	public void setAdmonMateriaDesc(String admonMateriaDesc) {
		this.admonMateriaDesc = admonMateriaDesc;
	}

	public String getAdmonSubmateriaDesc() {
		return admonSubmateriaDesc;
	}

	public void setAdmonSubmateriaDesc(String admonSubmateriaDesc) {
		this.admonSubmateriaDesc = admonSubmateriaDesc;
	}

}