package com.cmrise.ejb.model.exams;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.cmrise.ejb.model.corecases.CcHdrV1;

public class CcExamAsignaciones implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id; 
	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private long numeroCoreCase;
	private String tituloPregunta; 
	private String tipoPreguntaDesc; 
	private String estatusPregunta; 
	private String estatusPreguntaDesc; 
	private BigDecimal maxPuntuacionPregunta; 
	private String temaPregunta; 
	private String temaPreguntaDesc; 
	private String etiquetas; 
	private String tipoPregunta; 
	private long numeroPreguntaHdr; 
	private long numeroCcExamen; 
	
	private CcHdrV1 ccHdrV1; 

	public CcExamAsignaciones() {
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

	public long getNumeroCoreCase() {
		return this.numeroCoreCase;
	}

	public void setNumeroCoreCase(long numeroCoreCase) {
		this.numeroCoreCase = numeroCoreCase;
	}

	public String getTipoPreguntaDesc() {
		return tipoPreguntaDesc;
	}

	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}

	public String getEstatusPreguntaDesc() {
		return estatusPreguntaDesc;
	}

	public void setEstatusPreguntaDesc(String estatusPreguntaDesc) {
		this.estatusPreguntaDesc = estatusPreguntaDesc;
	}

	public BigDecimal getMaxPuntuacionPregunta() {
		return maxPuntuacionPregunta;
	}

	public void setMaxPuntuacionPregunta(BigDecimal maxPuntuacionPregunta) {
		this.maxPuntuacionPregunta = maxPuntuacionPregunta;
	}

	public String getTemaPreguntaDesc() {
		return temaPreguntaDesc;
	}

	public void setTemaPreguntaDesc(String temaPreguntaDesc) {
		this.temaPreguntaDesc = temaPreguntaDesc;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}

	public String getEstatusPregunta() {
		return estatusPregunta;
	}

	public void setEstatusPregunta(String estatusPregunta) {
		this.estatusPregunta = estatusPregunta;
	}

	public String getTemaPregunta() {
		return temaPregunta;
	}

	public void setTemaPregunta(String temaPregunta) {
		this.temaPregunta = temaPregunta;
	}

	public long getNumeroPreguntaHdr() {
		return numeroPreguntaHdr;
	}

	public void setNumeroPreguntaHdr(long numeroPreguntaHdr) {
		this.numeroPreguntaHdr = numeroPreguntaHdr;
	}

	public CcHdrV1 getCcHdrV1() {
		return ccHdrV1;
	}

	public void setCcHdrV1(CcHdrV1 ccHdrV1) {
		this.ccHdrV1 = ccHdrV1;
	}

	public long getNumeroCcExamen() {
		return numeroCcExamen;
	}

	public void setNumeroCcExamen(long numeroCcExamen) {
		this.numeroCcExamen = numeroCcExamen;
	}

	
}
