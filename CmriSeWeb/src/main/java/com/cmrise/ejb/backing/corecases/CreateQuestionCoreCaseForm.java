package com.cmrise.ejb.backing.corecases;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.services.corecases.CcPreguntasHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class CreateQuestionCoreCaseForm {
	private long numeroCcHdr;
	private String nombrePreguntaHdr; 
    private String tituloPreguntaHdr;
    private String tipoPreguntaHdr;
    private String temaPreguntaHdr;
    private String etiquetasPreguntaHdr;
    private String comentariosPreguntaHdr;
	
    @Inject 
    CcPreguntasHdrLocal ccPreguntasHdrLocal; 
    
	@PostConstruct
    public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroCcHdr= session.getAttribute("NumeroCcHdrSV");
	     if(null!=obNumeroCcHdr) {
	    	 if(obNumeroCcHdr instanceof Long) {
	    		 long numeroCcHdr = (Long)obNumeroCcHdr;
	    		 System.out.println("numeroCcHdr:"+numeroCcHdr);
	    		 this.numeroCcHdr = numeroCcHdr; 
	    	 }else {
	    		 System.out.println("numeroCcHdr instanceof Long:false");
	    	 }
	     }else {
	    	 System.out.println("(null!=numeroCcHdr:false");
	    	 return;
	     }	
		 System.out.println("Entra "+this.getClass()+" init()");
	}
	
	public String create() {
		CcHdrDto ccHdrDto = new CcHdrDto(); 
		CcPreguntasHdrDto ccPreguntasHdrDto = new CcPreguntasHdrDto();
		ccHdrDto.setNumero(this.numeroCcHdr);
		ccPreguntasHdrDto.setCcHdr(ccHdrDto);
		ccPreguntasHdrDto.setNombre(this.nombrePreguntaHdr);
		ccPreguntasHdrDto.setTitulo(this.tituloPreguntaHdr);
		ccPreguntasHdrDto.setTipoPregunta(this.tipoPreguntaHdr);
		ccPreguntasHdrDto.setTemaPregunta(this.temaPreguntaHdr);
		ccPreguntasHdrDto.setComentarios(this.comentariosPreguntaHdr);
		ccPreguntasHdrDto.setSociedad(Utilitarios.SOCIEDAD);
		ccPreguntasHdrDto.setEstatus(Utilitarios.INITIAL_STATUS_MRQ);
		ccPreguntasHdrDto.setEtiquetas(this.etiquetasPreguntaHdr);
		ccPreguntasHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		ccPreguntasHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		long longNumeroPreguntaHdr = ccPreguntasHdrLocal.insert(ccPreguntasHdrDto);
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", this.numeroCcHdr);  
		session.setAttribute("NumeroCcPreguntaHdrSV", longNumeroPreguntaHdr); 
		
		return "Actualizar-Pregunta-Fta-CoreCase";
	}
	
	public long getNumeroCcHdr() {
		return numeroCcHdr;
	}

	public void setNumeroCcHdr(long numeroCcHdr) {
		this.numeroCcHdr = numeroCcHdr;
	}

	public String getNombrePreguntaHdr() {
		return nombrePreguntaHdr;
	}

	public void setNombrePreguntaHdr(String nombrePreguntaHdr) {
		this.nombrePreguntaHdr = nombrePreguntaHdr;
	}

	public String getTituloPreguntaHdr() {
		return tituloPreguntaHdr;
	}

	public void setTituloPreguntaHdr(String tituloPreguntaHdr) {
		this.tituloPreguntaHdr = tituloPreguntaHdr;
	}

	public String getTipoPreguntaHdr() {
		return tipoPreguntaHdr;
	}

	public void setTipoPreguntaHdr(String tipoPreguntaHdr) {
		this.tipoPreguntaHdr = tipoPreguntaHdr;
	}

	public String getTemaPreguntaHdr() {
		return temaPreguntaHdr;
	}

	public void setTemaPreguntaHdr(String temaPreguntaHdr) {
		this.temaPreguntaHdr = temaPreguntaHdr;
	}

	public String getEtiquetasPreguntaHdr() {
		return etiquetasPreguntaHdr;
	}

	public void setEtiquetasPreguntaHdr(String etiquetasPreguntaHdr) {
		this.etiquetasPreguntaHdr = etiquetasPreguntaHdr;
	}

	public String getComentariosPreguntaHdr() {
		return comentariosPreguntaHdr;
	}

	public void setComentariosPreguntaHdr(String comentariosPreguntaHdr) {
		this.comentariosPreguntaHdr = comentariosPreguntaHdr;
	}
}
