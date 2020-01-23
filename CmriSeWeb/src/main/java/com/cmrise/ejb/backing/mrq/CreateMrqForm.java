package com.cmrise.ejb.backing.mrq;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class CreateMrqForm {

	private String nombre;
	private String titulo; 
	private String tipoPregunta;
	private String temaPregunta; 
	private String etiquetas; 
	private String comentarios;
	
	@Inject
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal; 
	
	public void create() {
		System.out.println("Entra CreateMrqForm create");
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		mrqsPreguntasHdrDto.setNombre(this.nombre);
		mrqsPreguntasHdrDto.setTitulo(this.titulo);
		mrqsPreguntasHdrDto.setTipoPregunta(this.tipoPregunta);
		mrqsPreguntasHdrDto.setTemaPregunta(this.temaPregunta);
		mrqsPreguntasHdrDto.setEtiquetas(this.etiquetas);
		mrqsPreguntasHdrDto.setComentarios(this.comentarios);
		mrqsPreguntasHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		mrqsPreguntasHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		mrqsPreguntasHdrDto.setEstatus("PARA_REVISAR");
		mrqsPreguntasHdrDto.setSociedad(Utilitarios.SOCIEDAD);
		mrqsPreguntasHdrLocal.insert(mrqsPreguntasHdrDto);
		System.out.println("Sale CreateMrqForm create");
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

	
}
