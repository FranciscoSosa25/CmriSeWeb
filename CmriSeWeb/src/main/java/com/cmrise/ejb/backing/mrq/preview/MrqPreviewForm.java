package com.cmrise.ejb.backing.mrq.preview;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.GuestPreferences;
import com.cmrise.ejb.model.mrqs.MrqsOpcionMultiple;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class MrqPreviewForm {

	private long numeroHdr; 
	private long numetoFta; 
	private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
	private boolean annotatedImage;
	private String tituloPregunta; 
	private String textoPregunta; 
	private String textoSugerencias; 
	private String respuestaPregunta; 
	
	/**********************************************************************
	  Atributos Opcion Multiple
	 **********************************************************************/
	private List<MrqsOpcionMultiple> listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
	
	
	@ManagedProperty(value="#{guestPreferences}")
	GuestPreferences guestPreferences; 
	
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal; 
	
	@Inject 
	MrqsOpcionMultipleLocal mrqsOpcionMultipleLocal; 
	
	@PostConstruct
	public void init() {
		 System.out.println("Entra MrqPreviewForm init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
	     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	     Object objMrqNumeroHdr = session.getAttribute("mrqNumeroHdrSV");
	     this.setNumeroHdr(Utilitarios.objToLong(objMrqNumeroHdr)); 
	     MrqsPreguntasHdrV2Dto  mrqsPreguntasHdrV2Dto = mrqsPreguntasHdrLocal.findV2ByNumeroHdr(this.getNumeroHdr()); 
	     this.setTituloPregunta(mrqsPreguntasHdrV2Dto.getTitulo());
	     this.setTextoPregunta(mrqsPreguntasHdrV2Dto.getTextoPregunta());
	     this.setTextoSugerencias(mrqsPreguntasHdrV2Dto.getTextoSugerencias());
	     if(Utilitarios.RESP_TEXTO_LIBRE.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	 this.setLimitedFreeTextAnswer(true);
	     }else if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV2Dto.getTipoPregunta())) {
	    	 this.setMultipleChoice(true);
	    	 initListMrqsOpcionMultiple(mrqsPreguntasHdrV2Dto.getNumeroMpf()); 
	     }
	     System.out.println("Sale MrqPreviewForm init()");
	}		 
	
	private void initListMrqsOpcionMultiple(long numeroMpf) {
		listMrqsOpcionMultiple = new ArrayList<MrqsOpcionMultiple>(); 
		List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto = mrqsOpcionMultipleLocal.findByNumeroFta(numeroMpf);
        for(MrqsOpcionMultipleDto mrqsOpcionMultipleDto:listMrqsOpcionMultipleDto) {
        	MrqsOpcionMultiple mrqsOpcionMultiple = new MrqsOpcionMultiple(); 
        	mrqsOpcionMultiple.setNumero(mrqsOpcionMultipleDto.getNumero());
        	mrqsOpcionMultiple.setTextoRespuesta(mrqsOpcionMultipleDto.getTextoRespuesta());
        	listMrqsOpcionMultiple.add(mrqsOpcionMultiple); 
        }
	}

	public String saveProceed() {
		guestPreferences.setTheme("turquoise");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroHdrSV", this.getNumeroHdr());
		return "Preguntas-UpdateFreeTextAnswer-NewMrqs";
	}
	
	public String skip() {
		guestPreferences.setTheme("turquoise");
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroHdrSV", this.getNumeroHdr());
		return "Preguntas-UpdateFreeTextAnswer-NewMrqs";
	}
	
	public boolean isMultipleChoice() {
		return multipleChoice;
	}
	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}
	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}
	public void setLimitedFreeTextAnswer(boolean limitedFreeTextAnswer) {
		this.limitedFreeTextAnswer = limitedFreeTextAnswer;
	}
	public boolean isIndicateImage() {
		return indicateImage;
	}
	public void setIndicateImage(boolean indicateImage) {
		this.indicateImage = indicateImage;
	}
	public boolean isAnnotatedImage() {
		return annotatedImage;
	}
	public void setAnnotatedImage(boolean annotatedImage) {
		this.annotatedImage = annotatedImage;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	public long getNumeroHdr() {
		return numeroHdr;
	}

	public void setNumeroHdr(long numeroHdr) {
		this.numeroHdr = numeroHdr;
	}

	public long getNumetoFta() {
		return numetoFta;
	}

	public void setNumetoFta(long numetoFta) {
		this.numetoFta = numetoFta;
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

	public String getRespuestaPregunta() {
		return respuestaPregunta;
	}

	public void setRespuestaPregunta(String respuestaPregunta) {
		this.respuestaPregunta = respuestaPregunta;
	}
	
	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}

	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}

	public List<MrqsOpcionMultiple> getListMrqsOpcionMultiple() {
		return listMrqsOpcionMultiple;
	}

	public void setListMrqsOpcionMultiple(List<MrqsOpcionMultiple> listMrqsOpcionMultiple) {
		this.listMrqsOpcionMultiple = listMrqsOpcionMultiple;
	}
	
	
}
