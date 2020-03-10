package com.cmrise.ejb.backing.mrq;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class UpdateFTAMrqForm {
	
	private long numeroHdr;
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction = new MrqsPreguntasHdrV1();
	
	private String tituloPregunta;
	private String metodoPuntuacion;
	private String respuestaCorrecta; 
	private String textoPregunta; 
	private String valorPuntuacion; 

	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal;
	
	@Inject
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal;
	
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra UpdateFTAMrqForm init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object obNumeroHdr= session.getAttribute("NumeroHdrSV");
	     if(null!=obNumeroHdr) {
	    	 if(obNumeroHdr instanceof Long) {
	    		 long numeroHdr = (Long)obNumeroHdr;
	    		 System.out.println("numeroHdr:"+numeroHdr);
	    		 this.numeroHdr = numeroHdr; 
	    	 }else {
	    		 System.out.println("obNumeroHdr instanceof Long:false");
	    	 }
	     }else {
	    	 System.out.println("(null!=obNumeroHdr:false");
	    	 return;
	     }
		
		 refreshEntity();
		 System.out.println("Sale UpdateFTAMrqForm init()");
	 }		 
	 
	private void refreshEntity() {
		 System.out.println("Entra UpdateFTAMrqForm refreshEntity()");
		 MrqsPreguntasHdrV1Dto mrqsPreguntasHdrV1Dto = mrqsPreguntasHdrLocal.findByNumero(this.numeroHdr);
		 mrqsPreguntasHdrV1ForAction.setEstatus(mrqsPreguntasHdrV1Dto.getEstatus());
		 mrqsPreguntasHdrV1ForAction.setNombre(mrqsPreguntasHdrV1Dto.getNombre());
		 mrqsPreguntasHdrV1ForAction.setTitulo(mrqsPreguntasHdrV1Dto.getTitulo());
		 mrqsPreguntasHdrV1ForAction.setTipoPregunta(mrqsPreguntasHdrV1Dto.getTipoPregunta());
		 mrqsPreguntasHdrV1ForAction.setTemaPregunta(mrqsPreguntasHdrV1Dto.getTemaPregunta());
		 mrqsPreguntasHdrV1ForAction.setEtiquetas(mrqsPreguntasHdrV1Dto.getEtiquetas());
		 mrqsPreguntasHdrV1ForAction.setComentarios(mrqsPreguntasHdrV1Dto.getComentarios());
		 
		 long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
		 if(0l!=numeroFta) {
		   /** CONSULTA INFORMACION **/
			MrqsPreguntasFtaDto mrqsPreguntasFtaDto = mrqsPreguntasFtaLocal.findDtoByNumeroFta(numeroFta);
			this.setTituloPregunta(mrqsPreguntasFtaDto.getTitulo());
		 }
		 
		 System.out.println("Sale UpdateFTAMrqForm refreshEntity()");
	}

	public void update() {
		
		System.out.println("Entra UpdateFTAMrqForm update");
		System.out.println("this.getNumeroHdr():"+this.getNumeroHdr());
		long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(this.getNumeroHdr()); 
		System.out.println("numeroFta:"+numeroFta);
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = new MrqsPreguntasFtaDto();
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		if(0l==numeroFta) {
		   /** INSERTA **/	
			mrqsPreguntasHdrDto.setNumero(this.numeroHdr);
			mrqsPreguntasFtaDto.setMrqsPreguntasHdr2(mrqsPreguntasHdrDto);
			mrqsPreguntasFtaDto.setTitulo(this.tituloPregunta);
			mrqsPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			mrqsPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			System.out.println("this.metodoPuntuacion:"+this.metodoPuntuacion);
			mrqsPreguntasFtaDto.setMetodoPuntuacion(Utilitarios.WRONG_CORRECT);
			mrqsPreguntasFtaDto.setRespuestaCorrecta(this.respuestaCorrecta);
			mrqsPreguntasFtaDto.setTextoPregunta(this.textoPregunta);
			mrqsPreguntasFtaDto.setValorPuntuacion(this.valorPuntuacion);
			mrqsPreguntasFtaLocal.insert(mrqsPreguntasFtaDto);
		}else {
		  /** ACTUALIZA **/
			mrqsPreguntasHdrDto.setNumero(this.numeroHdr);
			mrqsPreguntasFtaDto.setMrqsPreguntasHdr2(mrqsPreguntasHdrDto);
			mrqsPreguntasFtaDto.setTitulo(this.tituloPregunta);
			mrqsPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
			mrqsPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
			System.out.println("this.metodoPuntuacion:"+this.metodoPuntuacion);
			mrqsPreguntasFtaDto.setMetodoPuntuacion(Utilitarios.WRONG_CORRECT);
			mrqsPreguntasFtaDto.setRespuestaCorrecta(this.respuestaCorrecta);
			mrqsPreguntasFtaDto.setTextoPregunta(this.textoPregunta);
			mrqsPreguntasFtaDto.setValorPuntuacion(this.valorPuntuacion);
			mrqsPreguntasFtaLocal.update(numeroFta, mrqsPreguntasFtaDto);
		}
		/******************************************************************
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = new MrqsPreguntasFtaDto();
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		mrqsPreguntasHdrDto.setNumero(this.numeroHdr);
		mrqsPreguntasFtaDto.setMrqsPreguntasHdr2(mrqsPreguntasHdrDto);
		mrqsPreguntasFtaDto.setTitulo(this.tituloPregunta);
		mrqsPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		mrqsPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		System.out.println("this.metodoPuntuacion:"+this.metodoPuntuacion);
		mrqsPreguntasFtaDto.setMetodoPuntuacion("WRONG_CORRECT");
		mrqsPreguntasFtaDto.setRespuestaCorrecta(this.respuestaCorrecta);
		mrqsPreguntasFtaDto.setTextoPregunta(this.textoPregunta);
		mrqsPreguntasFtaDto.setValorPuntuacion(this.valorPuntuacion);
		mrqsPreguntasFtaLocal.insert(mrqsPreguntasFtaDto);
		******************************************************************/
		System.out.println("Entra UpdateFTAMrqForm Sale");
		
	}
	
	public String cancel() {
		System.out.println("Entra cancel");
		System.out.println("Sale cancel");
		return "Preguntas-ManageNewMrqs"; 
	}
	
	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1ForAction() {
		return mrqsPreguntasHdrV1ForAction;
	}

	public void setMrqsPreguntasHdrV1ForAction(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction) {
		this.mrqsPreguntasHdrV1ForAction = mrqsPreguntasHdrV1ForAction;
	}

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public String getMetodoPuntuacion() {
		return metodoPuntuacion;
	}

	public void setMetodoPuntuacion(String metodoPuntuacion) {
		this.metodoPuntuacion = metodoPuntuacion;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getValorPuntuacion() {
		return valorPuntuacion;
	}

	public void setValorPuntuacion(String valorPuntuacion) {
		this.valorPuntuacion = valorPuntuacion;
	}

}
