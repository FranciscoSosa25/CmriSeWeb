package com.cmrise.ejb.backing.candidates.exams;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.model.candidates.exams.CandCcExamenesV1;
import com.cmrise.ejb.model.corecases.CcOpcionMultiple;
import com.cmrise.ejb.services.candidates.exams.CandCcExamenesLocal;
import com.cmrise.ejb.services.corecases.CcOpcionMultipleLocal;
import com.cmrise.ejb.services.corecases.CcPreguntasFtaLocal;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV2Dto;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class ExamForm {
	private long numeroCcpf; /** Numero CandidateCoreCasePreguntaFta **/
	private long numeroCandidato;
	private String tituloPregunta; 
	private String textoPregunta; 
	private String textoSugerencias; 
	private String respuestaPregunta; 
	
	private long numeroCandCcExamen; 
	private long numeroCandCcPreguntaHdr; 
	private String nombreCcExamen; 
	private List<CandCcExamenesV1> listCandCcExamenesV1 = new ArrayList<CandCcExamenesV1>();
    private ListIterator<CandCcExamenesV1> listIterCandCcExamenesV1; 
	
    private boolean multipleChoice; 
	private boolean limitedFreeTextAnswer;
	private boolean indicateImage;
    
	/**********************************************************************
	  Atributos Opcion Multiple
	 **********************************************************************/
	private List<CcOpcionMultiple> listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 

	
	@Inject 
	CandCcExamenesLocal candCcExamenesLocal; 
	
	
	@Inject
	CcPreguntasFtaLocal ccPreguntasFtaLocal; 
	
	
	@Inject 
	CcOpcionMultipleLocal ccOpcionMultipleLocal; 
	
	
	@PostConstruct
	public void init() {
		 System.out.println("Entra ExamForm init()");
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 Object objNumeroCandidatoSV = session.getAttribute("NumeroCandidatoSV"); 	
		 Object objNumeroCandCcExamenSV = session.getAttribute("numeroCandCcExamenSV"); 
		 Object objnumeroCandCcPreguntaHdrSV = session.getAttribute("numeroCandCcPreguntaHdrSV"); 
		 this.numeroCandidato = Utilitarios.objToLong(objNumeroCandidatoSV); 
		 this.numeroCandCcExamen = Utilitarios.objToLong(objNumeroCandCcExamenSV); 
		 this.numeroCandCcPreguntaHdr = Utilitarios.objToLong(objnumeroCandCcPreguntaHdrSV); 
		 System.out.println("this.numeroCandCcExamen:"+this.numeroCandCcExamen);
		 System.out.println("this.numeroCandCcPreguntaHdr:"+this.numeroCandCcPreguntaHdr);
		 if(0==numeroCandCcPreguntaHdr) {
		 listCandCcExamenesV1 = new ArrayList<CandCcExamenesV1>();
		 listCandCcExamenesV1 = candCcExamenesLocal.findCandCcExamInfoByNumero(this.numeroCandCcExamen); 
		 listIterCandCcExamenesV1 = listCandCcExamenesV1.listIterator();
		 System.out.println(listIterCandCcExamenesV1);
		 if(listIterCandCcExamenesV1.hasNext()) {
		 System.out.println("*");
		 CandCcExamenesV1 candCcExamenesV1 = listIterCandCcExamenesV1.next();
		 fillFieldsForm(candCcExamenesV1);
		 }
		 } /** if(0==numeroCandCcPreguntaHdr) **/
		 else {
			 listCandCcExamenesV1 = new ArrayList<CandCcExamenesV1>();
			 listCandCcExamenesV1 = candCcExamenesLocal.findCandCcExamInfoByNumero(this.numeroCandCcExamen); 
			 CandCcExamenesV1 candCcExamenesV1 =  candCcExamenesLocal.findCandCcExamenPreguntaInfo(this.getNumeroCandCcExamen()
															                                       ,this.getNumeroCandCcPreguntaHdr() 
															                                       ); 	
			 fillFieldsForm(candCcExamenesV1); 
		 }
		 System.out.println("Entra ExamForm init()");
	}
	
	private void fillFieldsForm(CandCcExamenesV1 pCandCcExamenesV1) {
		
		 this.nombreCcExamen = pCandCcExamenesV1.getNombreExamen();
		 CcPreguntasFtaV2Dto ccPreguntasFtaV2Dto = ccPreguntasFtaLocal.findV2DtoByNumeroFta(pCandCcExamenesV1.getNumeroCpf()); 
		
		 
		 if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 initListCcOpcionMultiple(ccPreguntasFtaV2Dto.getNumero()); 
		 }else if(Utilitarios.RESP_TEXTO_LIBRE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
			 this.setLimitedFreeTextAnswer(true);
		 }
		
	}
	
	public void backAction() {
		 System.out.println("Entra backAction");
		 long previousNumero = 0; 

		System.out.println("previousNumero:"+previousNumero);
		if(0==previousNumero) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		context.addMessage(null, new FacesMessage("Ya no se puede regresar a la","Otra pregunta") );
		return; 
		}
		
		this.setNumeroCandCcPreguntaHdr(previousNumero);
   	    CandCcExamenesV1 candCcExamenesV1 =  candCcExamenesLocal.findCandCcExamenPreguntaInfo(this.getNumeroCandCcExamen()
																			                  ,this.getNumeroCandCcPreguntaHdr() 
																			                  ); 	
        fillFieldsForm(candCcExamenesV1); 
        /***************************************************************************************
		 int previousIndex = getListIterCandCcExamenesV1().previousIndex();
    	 System.out.println("previousIndex:"+previousIndex);
    	 if(getListIterCandCcExamenesV1().hasPrevious()) {
    	 CandCcExamenesV1 candCcExamenesV1 = getListIterCandCcExamenesV1().previous(); 
    	 CcPreguntasFtaV2Dto ccPreguntasFtaV2Dto = ccPreguntasFtaLocal.findV2DtoByNumeroFta(candCcExamenesV1.getNumeroCpf()); 
    	 CandCcPreguntasFtaDto candCcPreguntasFtaDto = candCcPreguntasFtaLocal.findByNumero(candCcExamenesV1.getNumeroCcpf());
 		 this.setTituloPregunta(ccPreguntasFtaV2Dto.getTituloPregunta());
		 this.setTextoPregunta(ccPreguntasFtaV2Dto.getTextoPregunta());
		 this.setTextoSugerencias(ccPreguntasFtaV2Dto.getTextoSugerencias());
		 this.setNumeroCcpf(candCcExamenesV1.getNumeroCcpf());
		 this.setRespuestaPregunta(candCcPreguntasFtaDto.getRespuesta());
		 if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 initListCcOpcionMultiple(ccPreguntasFtaV2Dto.getNumero()); 
		 }else if(Utilitarios.RESP_TEXTO_LIBRE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
			 this.setLimitedFreeTextAnswer(true);
		 }
    	 }
    	 **************************************************************************************/
    	 System.out.println("Sale backAction");
	}
	
    public void saveTrxAction() {
    	    	
    	 System.out.println("Entra saveTrxAction");
    	
    	 System.out.println("this.getRespuestaPregunta():"+this.getRespuestaPregunta());
    	 System.out.println("this.getTextoPregunta():"+this.getTextoPregunta());
    	 System.out.println("this.getTextoSugerencias():"+this.getTextoSugerencias());
    	 
    	    long nextNumero = 0;

			System.out.println("nextNumero:"+nextNumero);
			if(0==nextNumero) {
			FacesContext context = FacesContext.getCurrentInstance(); 
			context.addMessage(null, new FacesMessage("Ya no se puede avanzar a la","Otra pregunta") );
			return; 
			}
			
			this.setNumeroCandCcPreguntaHdr(nextNumero);
			CandCcExamenesV1 candCcExamenesV1 =  candCcExamenesLocal.findCandCcExamenPreguntaInfo(this.getNumeroCandCcExamen()
	                  ,this.getNumeroCandCcPreguntaHdr() 
	                  ); 	
           fillFieldsForm(candCcExamenesV1); 
         /*********************************************************************************************    	 
    	 int nextIndex = getListIterCandCcExamenesV1().nextIndex(); 
    	 System.out.println("nextIndex:"+nextIndex);
    	 if(getListIterCandCcExamenesV1().hasNext()) {
        	 CandCcExamenesV1 candCcExamenesV1 = getListIterCandCcExamenesV1().next(); 
        	 CcPreguntasFtaV2Dto ccPreguntasFtaV2Dto = ccPreguntasFtaLocal.findV2DtoByNumeroFta(candCcExamenesV1.getNumeroCpf()); 
    		 this.setTituloPregunta(ccPreguntasFtaV2Dto.getTituloPregunta());
    		 this.setTextoPregunta(ccPreguntasFtaV2Dto.getTextoPregunta());
    		 this.setTextoSugerencias(ccPreguntasFtaV2Dto.getTextoSugerencias());
    		 this.setNumeroCcpf(candCcExamenesV1.getNumeroCcpf());
    		 if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
    			 this.setMultipleChoice(true);
    			 initListCcOpcionMultiple(ccPreguntasFtaV2Dto.getNumero()); 
    		 }else if(Utilitarios.RESP_TEXTO_LIBRE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
    			 this.setLimitedFreeTextAnswer(true);
    		 }
        	 }
         ********************************************************************************************/
    	 this.setRespuestaPregunta("");
    	 System.out.println("Sale saveTrxAction");
	}

    public void skipAction() {
    	 long nextNumero = 0; 
    	 
    	 System.out.println("nextNumero:"+nextNumero);
    	 if(0==nextNumero) {
    		 FacesContext context = FacesContext.getCurrentInstance(); 
    		 context.addMessage(null, new FacesMessage("Ya no se puede avanzar a la","Otra pregunta") );
    		 return; 
    	 }
    	 
    	 System.out.println("Entra skipAction");
    	 this.setNumeroCandCcPreguntaHdr(nextNumero);
    	 CandCcExamenesV1 candCcExamenesV1 =  candCcExamenesLocal.findCandCcExamenPreguntaInfo(this.getNumeroCandCcExamen()
																			                  ,this.getNumeroCandCcPreguntaHdr() 
																			                  ); 	
         fillFieldsForm(candCcExamenesV1); 
         /**************************************************************************************************    	 
    	 int nextIndex = getListIterCandCcExamenesV1().nextIndex(); 
    	 System.out.println("nextIndex:"+nextIndex);
    	 if(getListIterCandCcExamenesV1().hasNext()) {
    	 CandCcExamenesV1 candCcExamenesV1 = getListIterCandCcExamenesV1().next(); 
    	 CcPreguntasFtaV2Dto ccPreguntasFtaV2Dto = ccPreguntasFtaLocal.findV2DtoByNumeroFta(candCcExamenesV1.getNumeroCpf()); 
    	 CandCcPreguntasFtaDto candCcPreguntasFtaDto = candCcPreguntasFtaLocal.findByNumero(candCcExamenesV1.getNumeroCcpf());
 		 this.setTituloPregunta(ccPreguntasFtaV2Dto.getTituloPregunta());
		 this.setTextoPregunta(ccPreguntasFtaV2Dto.getTextoPregunta());
		 this.setTextoSugerencias(ccPreguntasFtaV2Dto.getTextoSugerencias());
		 this.setNumeroCcpf(candCcExamenesV1.getNumeroCcpf());
		 this.setRespuestaPregunta(candCcPreguntasFtaDto.getRespuesta());
		
		 if(Utilitarios.OPCION_MULTIPLE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
			 this.setMultipleChoice(true);
			 initListCcOpcionMultiple(ccPreguntasFtaV2Dto.getNumero()); 
		 }else if(Utilitarios.RESP_TEXTO_LIBRE.equals(ccPreguntasFtaV2Dto.getTipoPregunta())) {
		     System.out.println("Entra RESP_TEXTO_LIBRE");
			 this.setLimitedFreeTextAnswer(true);
		 }
    	 }
    	 System.out.println("this.isLimitedFreeTextAnswer():"+this.isLimitedFreeTextAnswer());
    	 System.out.println("this.isMultipleChoice():"+this.isMultipleChoice());
    	 ***********************************************************************************************************/
    	 System.out.println("Sale skipAction");
	}
    
    private void initListCcOpcionMultiple(long pNumeroFta) {
    	listCcOpcionMultiple = new ArrayList<CcOpcionMultiple>(); 
    	List<CcOpcionMultipleDto>  listCcOpcionMultipleDto = ccOpcionMultipleLocal.findByNumeroFta(pNumeroFta);
        for(CcOpcionMultipleDto ccOpcionMultipleDto:listCcOpcionMultipleDto) {
        	CcOpcionMultiple ccOpcionMultiple = new CcOpcionMultiple(); 
        	ccOpcionMultiple.setNumero(ccOpcionMultipleDto.getNumero());
        	ccOpcionMultiple.setTextoRespuesta(ccOpcionMultipleDto.getTextoRespuesta());
        	listCcOpcionMultiple.add(ccOpcionMultiple); 
        }

    }
    
    public String question(CandCcExamenesV1 pCandExamCoreCasePregunta) {
    	System.out.println("Entra String question");
    	FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("numeroCandCcPreguntaHdrSV", pCandExamCoreCasePregunta.getNumeroCcph());
    	System.out.println("Sale String question");
    	return "Candidates-Exam";
    }
     
	public long getNumeroCandidato() {
		return numeroCandidato;
	}

	public void setNumeroCandidato(long numeroCandidato) {
		this.numeroCandidato = numeroCandidato;
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

	public long getNumeroCandCcExamen() {
		return numeroCandCcExamen;
	}

	public void setNumeroCandCcExamen(long numeroCandCcExamen) {
		this.numeroCandCcExamen = numeroCandCcExamen;
	}

	public String getTituloPregunta() {
		return tituloPregunta;
	}

	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}

	private ListIterator<CandCcExamenesV1> getListIterCandCcExamenesV1() {
		return listIterCandCcExamenesV1;
	}

	private void setListIterCandCcExamenesV1(ListIterator<CandCcExamenesV1> listIterCandCcExamenesV1) {
		this.listIterCandCcExamenesV1 = listIterCandCcExamenesV1;
	}

	public String getRespuestaPregunta() {
		return respuestaPregunta;
	}

	public void setRespuestaPregunta(String respuestaPregunta) {
		this.respuestaPregunta = respuestaPregunta;
	}

	public long getNumeroCcpf() {
		return numeroCcpf;
	}

	public void setNumeroCcpf(long numeroCcpf) {
		this.numeroCcpf = numeroCcpf;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		if(multipleChoice) {
			this.setLimitedFreeTextAnswer(false);
			this.setIndicateImage(false);
		}
		this.multipleChoice = multipleChoice;
	}

	public boolean isLimitedFreeTextAnswer() {
		return limitedFreeTextAnswer;
	}

	public void setLimitedFreeTextAnswer(boolean limitedFreeTextAnswer) {
		if(limitedFreeTextAnswer) {
			this.setMultipleChoice(false);
			this.setIndicateImage(false);
		}
		this.limitedFreeTextAnswer = limitedFreeTextAnswer;
	}

	public boolean isIndicateImage() {
		return indicateImage;
	}

	public void setIndicateImage(boolean indicateImage) {
		if(indicateImage) {
			this.setMultipleChoice(false);
			this.setLimitedFreeTextAnswer(false);
		}
		this.indicateImage = indicateImage;
	}

	public List<CcOpcionMultiple> getListCcOpcionMultiple() {
		return listCcOpcionMultiple;
	}

	public void setListCcOpcionMultiple(List<CcOpcionMultiple> listCcOpcionMultiple) {
		this.listCcOpcionMultiple = listCcOpcionMultiple;
	}

	public List<CandCcExamenesV1> getListCandCcExamenesV1() {
		return listCandCcExamenesV1;
	}

	public void setListCandCcExamenesV1(List<CandCcExamenesV1> listCandCcExamenesV1) {
		this.listCandCcExamenesV1 = listCandCcExamenesV1;
	}

	public String getNombreCcExamen() {
		return nombreCcExamen;
	}

	public void setNombreCcExamen(String nombreCcExamen) {
		this.nombreCcExamen = nombreCcExamen;
	}

	public long getNumeroCandCcPreguntaHdr() {
		return numeroCandCcPreguntaHdr;
	}

	public void setNumeroCandCcPreguntaHdr(long numeroCandCcPreguntaHdr) {
		this.numeroCandCcPreguntaHdr = numeroCandCcPreguntaHdr;
	}
	
}
