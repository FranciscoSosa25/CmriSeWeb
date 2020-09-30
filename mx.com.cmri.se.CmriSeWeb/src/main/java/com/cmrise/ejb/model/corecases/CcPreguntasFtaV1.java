package com.cmrise.ejb.model.corecases;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;

public class CcPreguntasFtaV1 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long numero;
	private long numeroHdr;
	private long actualizadoPor;
	private long creadoPor;
	private long numeroMpf;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String tituloPregunta; 
	private String textoPregunta;
	private String metodoPuntuacion;
	private String valorPuntuacion;
	private String textoSugerencias; 
	private String respuestaCorrecta;
	private String tipoPregunta; 
	private boolean singleAnswerMode; 
	private boolean suffleAnswerOrder; 
	private List<CcOpcionMultiple> listCcOpcionMultiple; 
	private List<CcImagenesGrp> listCcImagenesGrp; 
	
	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getTipoPregunta() {
		return tipoPregunta;
	}

	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}
	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}

	public String getMetodoPuntuacion() {
		return metodoPuntuacion;
	}
	public void setValorPuntuacion(String valorPuntuacion) {
		this.valorPuntuacion = valorPuntuacion;
	}

	public String getValorPuntuacion() {
		return valorPuntuacion;
	}
	public long getNumeroMpf() {
		return this.numeroMpf;
	}

	public void setNumeroMpf(long numeroMpf) {
		this.numeroMpf = numeroMpf;
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

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoSugerencias() {
		return textoSugerencias;
	}

	public void setTextoSugerencias(String textoSugerencias) {
		this.textoSugerencias = textoSugerencias;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public boolean isSingleAnswerMode() {
		return singleAnswerMode;
	}

	public void setSingleAnswerMode(boolean singleAnswerMode) {
		this.singleAnswerMode = singleAnswerMode;
	}

	public boolean isSuffleAnswerOrder() {
		return suffleAnswerOrder;
	}

	public void setSuffleAnswerOrder(boolean suffleAnswerOrder) {
		this.suffleAnswerOrder = suffleAnswerOrder;
	}

	public List<CcOpcionMultiple> getListCcOpcionMultiple() {
		return listCcOpcionMultiple;
	}

	public void setListCcOpcionMultiple(List<CcOpcionMultiple> listCcOpcionMultiple) {
		this.listCcOpcionMultiple = listCcOpcionMultiple;
	}

	public List<CcImagenesGrp> getListCcImagenesGrp() {
		return listCcImagenesGrp;
	}

	public void setListCcImagenesGrp(List<CcImagenesGrp> listCcImagenesGrp) {
		this.listCcImagenesGrp = listCcImagenesGrp;
	}

	
}
