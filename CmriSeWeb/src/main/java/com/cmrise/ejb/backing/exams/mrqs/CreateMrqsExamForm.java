package com.cmrise.ejb.backing.exams.mrqs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.exams.MrqsExamenes;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.exams.MrqsExamenesLocal;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class CreateMrqsExamForm {
	
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private MrqsExamenes mrqsExamenesForInsert = new MrqsExamenes(); 
	
	@Inject
	MrqsExamenesLocal mrqsExamenesLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal; 
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 
	
	 @PostConstruct
	 public void init() {
		 examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.MRQS); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
		 mrqsExamenesForInsert.setFechaElaboracion(new java.util.Date());
	 }
	
	public String create() {
		mrqsExamenesForInsert.setCreadoPor(userLogin.getNumeroUsuario());
		mrqsExamenesForInsert.setActualizadoPor(userLogin.getNumeroUsuario());
		mrqsExamenesForInsert.setFechaCreacion(new java.util.Date());
		mrqsExamenesForInsert.setFechaActualizacion(new java.util.Date());
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		
		// Valida si la fecha efectiva hasta es mayor que la fecha efectiva desde, de lo
		// contrario muestra el error en la pantalla.
		if (mrqsExamenesForInsert.getFechaEfectivaDesde().equals(mrqsExamenesForInsert.getFechaEfectivaHasta())
				|| !mrqsExamenesForInsert.getFechaEfectivaDesde()
						.before(mrqsExamenesForInsert.getFechaEfectivaHasta())) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"La fecha efectiva desde no puede ser igual o menor que la fecha efectiva hasta"));
			System.out.println("error de fechas!");
			return null;
		}

		long numeroMrqExamen = mrqsExamenesLocal.insert(mrqsExamenesForInsert); 
		session.setAttribute("NumeroMrqsExamenSV", numeroMrqExamen);
		
		context.addMessage(null, new FacesMessage("se creo correctamente el examen","Correctamente") );
		return "Exams-MRQs-Update"; 
		 
	}
	
	

	public List<AdmonExamenHdr> getExamenesHdr() {
		return examenesHdr;
	}

	public void setExamenesHdr(List<AdmonExamenHdr> examenesHdr) {
		this.examenesHdr = examenesHdr;
	}

	public List<SelectItem> getSelectExamenesHdr() {
		return selectExamenesHdr;
	}

	public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
		this.selectExamenesHdr = selectExamenesHdr;
	}

	public MrqsExamenes getMrqsExamenesForInsert() {
		return mrqsExamenesForInsert;
	}

	public void setMrqsExamenesForInsert(MrqsExamenes mrqsExamenesForInsert) {
		this.mrqsExamenesForInsert = mrqsExamenesForInsert;
	} 
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}
	
	
}
