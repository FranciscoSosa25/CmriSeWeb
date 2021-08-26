package com.cmrise.ejb.model.corecases;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmrise.ejb.model.corecases.img.CcImagenesGrp;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;


public class CcHdrV1 implements Serializable {

	private static final long serialVersionUID = 1L;

	private long actualizadoPor;
	private long creadoPor;
	private String descripcionTecnica;
	private String estatus;
	private String estatusDesc;
	private String etiquetas;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String historialClinico;
	private String nombre;
	private String nota;
	private long numero;
	private long numeroExamen;
	private boolean opcionInsegura;
	private String sociedad;
	private String tema;
	private String temaDesc;
	private long admonExamen; 
	private long admonMateria; 
	private long admonSubMateria; 
	private Date fechaElaboracion; 
	private String admonExamenDesc; 
	private String admonMateriaDesc; 
	private String admonSubMateriaDesc; 
	
	private List<CcImagenesGrp> listCcImagenesGrp; 
	
	private List<CcPreguntasHdrV1> listCcPreguntasHdrV1; 
	
	
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public long getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}
	public String getDescripcionTecnica() {
		return descripcionTecnica;
	}
	public void setDescripcionTecnica(String descripcionTecnica) {
		this.descripcionTecnica = descripcionTecnica;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getEstatusDesc() {
		return estatusDesc;
	}
	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
	}
	public String getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}
	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}
	public Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}
	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}
	public String getHistorialClinico() {
		return historialClinico;
	}
	public void setHistorialClinico(String historialClinico) {
		this.historialClinico = historialClinico;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public boolean isOpcionInsegura() {
		return opcionInsegura;
	}
	public void setOpcionInsegura(boolean opcionInsegura) {
		this.opcionInsegura = opcionInsegura;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getTemaDesc() {
		return temaDesc;
	}
	public void setTemaDesc(String temaDesc) {
		this.temaDesc = temaDesc;
	}
	public List<CcPreguntasHdrV1> getListCcPreguntasHdrV1() {
		return listCcPreguntasHdrV1;
	}
	public void setListCcPreguntasHdrV1(List<CcPreguntasHdrV1> listCcPreguntasHdrV1) {
		this.listCcPreguntasHdrV1 = listCcPreguntasHdrV1;
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

	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}
	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}
	public long getAdmonSubMateria() {
		return admonSubMateria;
	}
	public void setAdmonSubMateria(long admonSubMateria) {
		this.admonSubMateria = admonSubMateria;
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
	public String getAdmonSubMateriaDesc() {
		return admonSubMateriaDesc;
	}
	public void setAdmonSubMateriaDesc(String admonSubMateriaDesc) {
		this.admonSubMateriaDesc = admonSubMateriaDesc;
	}
	public List<CcImagenesGrp> getListCcImagenesGrp() {
		return listCcImagenesGrp;
	}
	public void setListCcImagenesGrp(List<CcImagenesGrp> listCcImagenesGrp) {
		this.listCcImagenesGrp = listCcImagenesGrp;
	}
	public long getNumeroExamen() {
		return numeroExamen;
	}
	public void setNumeroExamen(long numeroExamen) {
		this.numeroExamen = numeroExamen;
	}
	
	
}
