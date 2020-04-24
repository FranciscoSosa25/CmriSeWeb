package com.cmrise.ejb.backing.mrq;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.mrqs.MrqsOpcionMultipleLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasFtaLocal;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

@ManagedBean
@ViewScoped
public class ManageMrqsForm {
	private String titulo;

	private List<MrqsPreguntasHdrV1> listMrqsPreguntasHdrV1 = new ArrayList<MrqsPreguntasHdrV1>();
	private List<MrqsPreguntasHdrV1Dto> listMrqsPreguntasHdrV1Dto = new ArrayList<MrqsPreguntasHdrV1Dto>();
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction = new MrqsPreguntasHdrV1();
	
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal;
	
	@Inject
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal;
	
	@Inject 
	MrqsOpcionMultipleLocal  mrqsOpcionMultipleLocal; 
	
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra ManageMrqsForm init()");
		 refreshEntity();
		 System.out.println("Sale ManageMrqsForm init()");
	 }		 
	
	public void refreshEntity() {
		List<MrqsPreguntasHdrV1Dto> listMrqsPreguntasHdrV1Dto = mrqsPreguntasHdrLocal.findAll();
		Iterator<MrqsPreguntasHdrV1Dto> iterMrqsPreguntasHdrV1Dto = listMrqsPreguntasHdrV1Dto.iterator();
		listMrqsPreguntasHdrV1 = new ArrayList<MrqsPreguntasHdrV1>();
		while(iterMrqsPreguntasHdrV1Dto.hasNext()) {
			MrqsPreguntasHdrV1Dto mrqsPreguntasHdrV1Dto =iterMrqsPreguntasHdrV1Dto.next();
			MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = new MrqsPreguntasHdrV1();
			mrqsPreguntasHdrV1.setNumero(mrqsPreguntasHdrV1Dto.getNumero());
			mrqsPreguntasHdrV1.setTitulo(mrqsPreguntasHdrV1Dto.getTitulo());
			mrqsPreguntasHdrV1.setTipoPregunta(mrqsPreguntasHdrV1Dto.getTipoPregunta());
			mrqsPreguntasHdrV1.setTipoPreguntaDesc(mrqsPreguntasHdrV1Dto.getTipoPreguntaDesc());
			mrqsPreguntasHdrV1.setTemaPreguntaDesc(mrqsPreguntasHdrV1Dto.getTemaPreguntaDesc());
			mrqsPreguntasHdrV1.setEstatusDesc(mrqsPreguntasHdrV1Dto.getEstatusDesc());
			mrqsPreguntasHdrV1.setEtiquetas(mrqsPreguntasHdrV1Dto.getEtiquetas());
			listMrqsPreguntasHdrV1.add(mrqsPreguntasHdrV1);
		}
	}
	
	public void selectForAction(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1) {
		mrqsPreguntasHdrV1ForAction.setNumero(pMrqsPreguntasHdrV1.getNumero());
		mrqsPreguntasHdrV1ForAction.setTitulo(pMrqsPreguntasHdrV1.getTitulo());
		mrqsPreguntasHdrV1ForAction.setTipoPregunta(pMrqsPreguntasHdrV1.getTipoPregunta());
		mrqsPreguntasHdrV1ForAction.setTipoPreguntaDesc(pMrqsPreguntasHdrV1.getTipoPreguntaDesc());
		mrqsPreguntasHdrV1ForAction.setTemaPreguntaDesc(pMrqsPreguntasHdrV1.getTemaPreguntaDesc());
		mrqsPreguntasHdrV1ForAction.setEstatusDesc(pMrqsPreguntasHdrV1.getEstatusDesc());
		mrqsPreguntasHdrV1ForAction.setEtiquetas(pMrqsPreguntasHdrV1.getEtiquetas());
	}
	
	
	public void findByTituloPregunta() {
		listMrqsPreguntasHdrV1Dto = mrqsPreguntasHdrLocal.findByTituloPregunta(this.titulo); 
	}
	
    public void delete() {
		boolean deleteIn = false; 
		long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsPreguntasHdrV1ForAction.getNumero());
		if(0l!=numeroFta) { 
			if(Utilitarios.OPCION_MULTIPLE.equals(mrqsPreguntasHdrV1ForAction.getTipoPregunta())) {
				mrqsOpcionMultipleLocal.deleteByNumeroFta(numeroFta);
			}
			mrqsPreguntasFtaLocal.delete(numeroFta);
			mrqsPreguntasHdrLocal.delete(mrqsPreguntasHdrV1ForAction.getNumero());
			refreshEntity();
			deleteIn = true;
	    }else {
	    	mrqsPreguntasHdrLocal.delete(mrqsPreguntasHdrV1ForAction.getNumero());
			refreshEntity();
			deleteIn = true;
	    }
		PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
	}
   
  
	public String update(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1) {
		System.out.println("Entra update ManageMrqsForm");
		/******************************************** 04032020 **
		boolean updateIn = false; 
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		mrqsPreguntasHdrDto.setNumero(pMrqsPreguntasHdrV1.getNumero());
		mrqsPreguntasHdrDto.setTitulo(pMrqsPreguntasHdrV1.getTitulo());
		mrqsPreguntasHdrDto.setTipoPregunta(pMrqsPreguntasHdrV1.getTipoPreguntaDesc());
		mrqsPreguntasHdrDto.setTemaPregunta(pMrqsPreguntasHdrV1.getTemaPreguntaDesc());
		mrqsPreguntasHdrDto.setEstatus(pMrqsPreguntasHdrV1.getEstatusDesc());
		mrqsPreguntasHdrDto.setEtiquetas(pMrqsPreguntasHdrV1.getEtiquetas());
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		mrqsPreguntasHdrLocal.update(mrqsPreguntasHdrV1ForAction.getNumero(), mrqsPreguntasHdrDto  );
		mrqsPreguntasHdrLocal.update(pMrqsPreguntasHdrV1.getNumero(), mrqsPreguntasHdrDto);
		refreshEntity();
		updateIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("updateIn", updateIn);
		********************************************************************/
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroHdrSV", pMrqsPreguntasHdrV1.getNumero());
		System.out.println("Sale update ManageMrqsForm");
		return "Preguntas-UpdateFreeTextAnswer-NewMrqs";
	}
	

	
	public void duplicate() {
		System.out.println("Entra "+this.getClass()+" duplicate");
		boolean duplicateIn = false;
		long numeroFta = mrqsPreguntasFtaLocal.findNumeroFtaByNumeroHdr(mrqsPreguntasHdrV1ForAction.getNumero());
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = mrqsPreguntasHdrLocal.copyPaste(mrqsPreguntasHdrV1ForAction.getNumero());
		long numeroFtaCopy = mrqsPreguntasFtaLocal.copyPaste(numeroFta, mrqsPreguntasHdrDto);
		mrqsOpcionMultipleLocal.copyPaste(numeroFta, numeroFtaCopy);
		refreshEntity();
		duplicateIn = true;
	    PrimeFaces.current().ajax().addCallbackParam("duplicateIn", duplicateIn);
		System.out.println("Sale "+this.getClass()+" duplicate");
	}
	
	public List<MrqsPreguntasHdrV1> getListMrqsPreguntasHdrV1() {
		return listMrqsPreguntasHdrV1;
	}

	public void setListMrqsPreguntasHdrV1(List<MrqsPreguntasHdrV1> listMrqsPreguntasHdrV1) {
		this.listMrqsPreguntasHdrV1 = listMrqsPreguntasHdrV1;
	}

	public MrqsPreguntasHdrV1 getMrqsPreguntasHdrV1ForAction() {
		return mrqsPreguntasHdrV1ForAction;
	}

	public void setMrqsPreguntasHdrV1ForAction(MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction) {
		this.mrqsPreguntasHdrV1ForAction = mrqsPreguntasHdrV1ForAction;
	}
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
