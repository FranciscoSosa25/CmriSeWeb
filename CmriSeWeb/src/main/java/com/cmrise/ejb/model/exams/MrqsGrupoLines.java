package com.cmrise.ejb.model.exams;

import java.io.Serializable;
import java.util.Date;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;

public class MrqsGrupoLines implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long numero;
	
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private long numeroHdr; 
	private long numeroPregunta; 
	
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = new MrqsPreguntasHdrV1(); 
	
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

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public long getNumeroPregunta() {
		return numeroPregunta;
	}

	public void setNumeroPregunta(long numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
	}

	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1() {
		return mrqsPreguntasHdrV1;
	}

	public void setMrqsPreguntasHdrV1(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1) {
		this.mrqsPreguntasHdrV1 = mrqsPreguntasHdrV1;
	}

}