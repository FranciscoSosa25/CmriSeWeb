package com.cmrise.ejb.model.exams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MrqsGrupoHdr implements Serializable {
	private static final long serialVersionUID = 1L;

	private long numero;
	private long actualizadoPor;
	private long creadoPor;
	private Date fechaActualizacion;
	private Date fechaCreacion;
	private long numeroExamen; 
	private String comentarios; 
    private long admonMateria; 
	private String admonMateriaDesc; 
	private String admonSubMateriaDesc;
	private int numeroReactivos; 
	private String elaborador; 
	
	private List<MrqsGrupoLines> listMrqsGrupoLines = new ArrayList<MrqsGrupoLines>(); 
	
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

	public long getNumeroExamen() {
		return numeroExamen;
	}

	public void setNumeroExamen(long numeroExamen) {
		this.numeroExamen = numeroExamen;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@Override
    public String toString() {
        return "HDR"+this.numero;
    }

	public List<MrqsGrupoLines> getListMrqsGrupoLines() {
		return listMrqsGrupoLines;
	}

	public void setListMrqsGrupoLines(List<MrqsGrupoLines> listMrqsGrupoLines) {
		this.listMrqsGrupoLines = listMrqsGrupoLines;
	}

	public long getAdmonMateria() {
		return admonMateria;
	}

	public void setAdmonMateria(long admonMateria) {
		this.admonMateria = admonMateria;
	}

	public String getAdmonMateriaDesc() {
		return admonMateriaDesc;
	}

	public void setAdmonMateriaDesc(String admonMateriaDesc) {
		this.admonMateriaDesc = admonMateriaDesc;
	}

	public int getNumeroReactivos() {
		return numeroReactivos;
	}

	public void setNumeroReactivos(int numeroReactivos) {
		this.numeroReactivos = numeroReactivos;
	}

	public String getElaborador() {
		return elaborador;
	}

	public void setElaborador(String elaborador) {
		this.elaborador = elaborador;
	}

	public String getAdmonSubMateriaDesc() {
		return admonSubMateriaDesc;
	}

	public void setAdmonSubMateriaDesc(String admonSubMateriaDesc) {
		this.admonSubMateriaDesc = admonSubMateriaDesc;
	}
 
	
}
