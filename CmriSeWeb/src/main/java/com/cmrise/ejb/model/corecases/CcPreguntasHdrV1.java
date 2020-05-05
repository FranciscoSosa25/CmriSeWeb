package com.cmrise.ejb.model.corecases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class CcPreguntasHdrV1 implements Serializable{

	private static final long serialVersionUID = 1L;

	private long numero;
	private long numeroCcHdr;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String nombre;
	private String titulo;
	private String tipoPregunta; 
	private String temaPregunta; 
	private String etiquetas; 
	private String comentarios;
	private String estatus; 
	private String sociedad; 
	private String tipoPreguntaDesc; 
	private String temaPreguntaDesc; 
	private String estatusDesc;
	private BigDecimal maxPuntuacion;
	private long admonExamen; 
	private long admonMateria; 
	private long admonSubMateria; 
	private Date fechaElaboracion; 
	private String admonExamenDesc; 
	private String admonMateriaDesc; 
	private String admonSubMateriaDesc; 
	
	private CcPreguntasFtaV1 ccPreguntasFtaV1; 
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTipoPregunta() {
		return tipoPregunta;
	}
	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}
	public String getTemaPregunta() {
		return temaPregunta;
	}
	public void setTemaPregunta(String temaPregunta) {
		this.temaPregunta = temaPregunta;
	}
	public String getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getTipoPreguntaDesc() {
		return tipoPreguntaDesc;
	}
	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}
	public String getTemaPreguntaDesc() {
		return temaPreguntaDesc;
	}
	public void setTemaPreguntaDesc(String temaPreguntaDesc) {
		this.temaPreguntaDesc = temaPreguntaDesc;
	}
	public String getEstatusDesc() {
		return estatusDesc;
	}
	public void setEstatusDesc(String estatusDesc) {
		this.estatusDesc = estatusDesc;
	}
	public BigDecimal getMaxPuntuacion() {
		return maxPuntuacion;
	}
	public void setMaxPuntuacion(BigDecimal maxPuntuacion) {
		this.maxPuntuacion = maxPuntuacion;
	}
	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}
	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}
	public CcPreguntasFtaV1 getCcPreguntasFtaV1() {
		return ccPreguntasFtaV1;
	}
	public void setCcPreguntasFtaV1(CcPreguntasFtaV1 ccPreguntasFtaV1) {
		this.ccPreguntasFtaV1 = ccPreguntasFtaV1;
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
	public long getAdmonSubMateria() {
		return admonSubMateria;
	}
	public void setAdmonSubMateria(long admonSubMateria) {
		this.admonSubMateria = admonSubMateria;
	}
	public Date getFechaElaboracion() {
		return fechaElaboracion;
	}
	public void setFechaElaboracion(Date fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
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
	

}
