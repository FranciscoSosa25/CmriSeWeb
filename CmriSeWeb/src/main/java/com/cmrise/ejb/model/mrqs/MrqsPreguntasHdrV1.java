package com.cmrise.ejb.model.mrqs;

import java.io.Serializable;
import java.util.Date;

public class MrqsPreguntasHdrV1 implements Serializable {

	private static final long serialVersionUID = 1L;

	private long actualizadoPor;
	private String comentarios;
	private long creadoPor;
	private String estatus;
	private String estatusDesc;
	private String etiquetas;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private String nombre;
	private long numero;
	private String sociedad;
	private String temaPregunta;
	private String temaPreguntaDesc;
	private String tipoPregunta;
	private String tipoPreguntaDesc;
	private String titulo;
	
	private long admonExamen; 
	private long admonMateria; 
	private long admonSubmateria; 
	
	private String admonExamenDesc; 
	private String admonMateriaDesc; 
	private String admonSubmateriaDesc; 
	
	private boolean dependent; 
	
	public long getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(long actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public long getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getTemaPregunta() {
		return temaPregunta;
	}
	public void setTemaPregunta(String temaPregunta) {
		this.temaPregunta = temaPregunta;
	}
	public String getTemaPreguntaDesc() {
		return temaPreguntaDesc;
	}
	public void setTemaPreguntaDesc(String temaPreguntaDesc) {
		this.temaPreguntaDesc = temaPreguntaDesc;
	}
	public String getTipoPregunta() {
		return tipoPregunta;
	}
	public void setTipoPregunta(String tipoPregunta) {
		this.tipoPregunta = tipoPregunta;
	}
	public String getTipoPreguntaDesc() {
		return tipoPreguntaDesc;
	}
	public void setTipoPreguntaDesc(String tipoPreguntaDesc) {
		this.tipoPreguntaDesc = tipoPreguntaDesc;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
    public String toString() {
        return "LINE"+this.numero;
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
	public boolean isDependent() {
		return dependent;
	}
	public void setDependent(boolean dependent) {
		this.dependent = dependent;
	}
	
}
