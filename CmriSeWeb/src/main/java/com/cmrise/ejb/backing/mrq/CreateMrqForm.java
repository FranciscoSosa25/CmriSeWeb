package com.cmrise.ejb.backing.mrq;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.XxSqlConstraints;

@ManagedBean
@ViewScoped
public class CreateMrqForm {

	private String nombre;
	private String titulo; 
	private String tipoPregunta;
	private String temaPregunta; 
	private String etiquetas; 
	private String comentarios;
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	private long admonExamen; 
	private long admonMateria;
	private long admonSubMateria; 
 	
	@Inject
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal; 
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal; 
	
	@Inject 
	AdmonSubMateriaLocal admonSubMateriaLocal; 
	
	 @PostConstruct
	 public void init() {
		 examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.MRQS); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
	 }
	
	 
	public String create() {
		System.out.println("Entra CreateMrqForm create");
		boolean exceptions = false; 
		String retval = null; 
		
		MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = new MrqsPreguntasHdrV1(); 
		mrqsPreguntasHdrV1.setAdmonExamen(this.admonExamen);
		mrqsPreguntasHdrV1.setAdmonMateria(this.admonMateria);
		mrqsPreguntasHdrV1.setAdmonSubmateria(this.admonSubMateria);
		mrqsPreguntasHdrV1.setTipoPregunta(this.tipoPregunta);
		mrqsPreguntasHdrV1.setEtiquetas(this.etiquetas);
		mrqsPreguntasHdrV1.setComentarios(this.comentarios);
		mrqsPreguntasHdrV1.setFechaEfectivaDesde(Utilitarios.startOfTime);
		mrqsPreguntasHdrV1.setFechaEfectivaHasta(Utilitarios.endOfTime);
		mrqsPreguntasHdrV1.setEstatus("PARA_REVISAR");
		mrqsPreguntasHdrV1.setSociedad(Utilitarios.SOCIEDAD);
		
		/*
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		mrqsPreguntasHdrDto.setTipoPregunta(this.tipoPregunta);
		mrqsPreguntasHdrDto.setEtiquetas(this.etiquetas);
		mrqsPreguntasHdrDto.setComentarios(this.comentarios);
		mrqsPreguntasHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		mrqsPreguntasHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		mrqsPreguntasHdrDto.setEstatus("PARA_REVISAR");
		mrqsPreguntasHdrDto.setSociedad(Utilitarios.SOCIEDAD);
		*/
		long numeroPreguntaHdr = 0; 
		try {
			numeroPreguntaHdr = mrqsPreguntasHdrLocal.insert(mrqsPreguntasHdrV1);
		}catch(Exception e) {
			 Throwable throwable = e.getCause();
			 while(null!=throwable) {
				 throwable = throwable.getCause();
				 if(null!=throwable) {
					 if(throwable.toString().contains("MRQS_PREGUNTAS_HDR")) {
						 exceptions = true; 
						 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",throwable.toString());
						 FacesContext.getCurrentInstance().addMessage(null, msg);
						 break;
					 }
				 }
			 }
		}
		if(!exceptions) {
			 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Se Agregaron", "Los Cambios");
		     FacesContext.getCurrentInstance().addMessage(null, msg);
		     FacesContext context = FacesContext.getCurrentInstance(); 
			 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			 session.setAttribute("NumeroHdrSV", numeroPreguntaHdr);
			 retval = "Preguntas-UpdateFreeTextAnswer-NewMrqs"; 
		}
		return retval;
	}
	
	public void onAdmonExamenChange() {
		System.out.println("admonExamen:"+admonExamen);
		if(0!=admonExamen) {
			materiasHdr = admonMateriaHdrLocal.findByNumeroAdmonExamen(admonExamen); 
			selectMateriasHdr = new ArrayList<SelectItem>();  
			for(AdmonMateriaHdr i:materiasHdr) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectMateriasHdr.add(selectItem); 
			}
		}
	}
	
	public void onAdmonMateriaChange() {
		System.out.println("admonMateria:"+admonMateria);
		if(0!=admonMateria) {
			subMaterias = admonSubMateriaLocal.findByNumeroMateria(admonMateria); 
			selectSubMaterias = new ArrayList<SelectItem>(); 
			for(AdmonSubMateria i:subMaterias) {
				System.out.println("*");
				SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				selectSubMaterias.add(selectItem); 
			}
		}
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

	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	}

	public List<AdmonMateriaHdr> getMateriasHdr() {
		return materiasHdr;
	}

	public void setMateriasHdr(List<AdmonMateriaHdr> materiasHdr) {
		this.materiasHdr = materiasHdr;
	}

	public List<AdmonSubMateria> getSubMaterias() {
		return subMaterias;
	}

	public void setSubMaterias(List<AdmonSubMateria> subMaterias) {
		this.subMaterias = subMaterias;
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

	public List<SelectItem> getSelectExamenesHdr(){
		return this.selectExamenesHdr; 
	}

	public List<SelectItem> getSelectMateriasHdr(){
		return this.selectMateriasHdr; 
	}
	
	public List<SelectItem>  getSelectSubMaterias() {
		System.out.println("selectSubMaterias:"+selectSubMaterias);
		if(null!=selectSubMaterias) {
			System.out.println("selectSubMaterias.size():"+selectSubMaterias.size());
		}
		return this.selectSubMaterias; 
	}
	
}
