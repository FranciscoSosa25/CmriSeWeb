package com.cmrise.ejb.model.admin;

import java.io.Serializable;
import java.util.Date;

public class AdmonMateriaLine implements Serializable {

	private static final long serialVersionUID = 1L;

	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private long numeroMateriaHdr; 
	private long numeroSubmateria; 
	private int idxTemp; 
	
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

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
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

	public long getNumeroMateriaHdr() {
		return numeroMateriaHdr;
	}

	public void setNumeroMateriaHdr(long numeroMateriaHdr) {
		this.numeroMateriaHdr = numeroMateriaHdr;
	}

	public long getNumeroSubmateria() {
		return numeroSubmateria;
	}

	public void setNumeroSubmateria(long numeroSubmateria) {
		this.numeroSubmateria = numeroSubmateria;
	}

	public int getIdxTemp() {
		return idxTemp;
	}

	public void setIdxTemp(int idxTemp) {
		this.idxTemp = idxTemp;
	}
}
