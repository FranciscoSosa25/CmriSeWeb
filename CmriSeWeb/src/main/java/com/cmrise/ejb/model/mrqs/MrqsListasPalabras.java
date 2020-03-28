package com.cmrise.ejb.model.mrqs;

import java.io.Serializable;
import java.sql.Timestamp;

public class MrqsListasPalabras implements Serializable{

	private static final long serialVersionUID = 1L;

	private int idxTemp; 
	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Timestamp fechaActualizacion;
	private Timestamp fechaCreacion;
	private long numeroFta; 
	private String tipoRegistro;
	private String palabra; 
	private String sinonimos; 
	
	
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


	public long getNumeroFta() {
		return numeroFta;
	}

	public void setNumeroFta(long numeroFta) {
		this.numeroFta = numeroFta;
	}


	
	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public String getSinonimos() {
		return sinonimos;
	}

	public void setSinonimos(String sinonimos) {
		this.sinonimos = sinonimos;
	}

	public int getIdxTemp() {
		return idxTemp;
	}

	public void setIdxTemp(int idxTemp) {
		this.idxTemp = idxTemp;
	}


}
