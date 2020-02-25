package com.cmrise.ejb.backing.corecases;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class CreateCoreCaseForm {
	
	private String nombre; 
	private String tema; 
	private String historialClinico; 
	private String descripcionTecnica; 
	private String nota; 
	private boolean opcionInsegura; 
	private String etiquetas;
	
	@Inject 
	CcHdrLocal ccHdrLocal; 
	
	public String create() {
		System.out.println("Entra CreateCoreCaseForm create");
		CcHdrDto ccHdrDto = new CcHdrDto();
		ccHdrDto.setNombre(this.nombre);
		ccHdrDto.setTema(this.tema);
		ccHdrDto.setHistorialClinico(this.historialClinico);
		ccHdrDto.setDescripcionTecnica(this.descripcionTecnica);
		ccHdrDto.setEtiquetas(this.etiquetas);
		System.out.println("this.opcionInsegura:"+this.opcionInsegura);
		ccHdrDto.setOpcionInsegura(this.opcionInsegura);
		ccHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		ccHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		ccHdrDto.setEstatus(Utilitarios.INITIAL_STATUS_MRQ);
		ccHdrDto.setSociedad(Utilitarios.SOCIEDAD);
		ccHdrLocal.insert(ccHdrDto);
		System.out.println("Sale CreateCoreCaseForm create");
		return "Preguntas-Manage-CoreCases";
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getHistorialClinico() {
		return historialClinico;
	}
	public void setHistorialClinico(String historialClinico) {
		this.historialClinico = historialClinico;
	}
	public String getDescripcionTecnica() {
		return descripcionTecnica;
	}
	public void setDescripcionTecnica(String descripcionTecnica) {
		this.descripcionTecnica = descripcionTecnica;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public boolean getOpcionInsegura() {
		return opcionInsegura;
	}
	public void setOpcionInsegura(boolean opcionInsegura) {
		this.opcionInsegura = opcionInsegura;
	}
	public String getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	} 
}
