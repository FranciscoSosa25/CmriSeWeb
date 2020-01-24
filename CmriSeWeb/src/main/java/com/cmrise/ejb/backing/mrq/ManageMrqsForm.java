package com.cmrise.ejb.backing.mrq;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.admin.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.mrqs.MrqsPreguntasHdrLocal;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

@ManagedBean
@ViewScoped
public class ManageMrqsForm {

	private List<MrqsPreguntasHdrV1> listMrqsPreguntasHdrV1 = new ArrayList<MrqsPreguntasHdrV1>();
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction = new MrqsPreguntasHdrV1();
	
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal;
	
	
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
			mrqsPreguntasHdrV1.setTipoPreguntaDesc(mrqsPreguntasHdrV1Dto.getTipoPreguntaDesc());
			mrqsPreguntasHdrV1.setTemaPreguntaDesc(mrqsPreguntasHdrV1Dto.getTemaPreguntaDesc());
			mrqsPreguntasHdrV1.setEstatusDesc(mrqsPreguntasHdrV1Dto.getEstatusDesc());
			mrqsPreguntasHdrV1.setEtiquetas(mrqsPreguntasHdrV1Dto.getEtiquetas());
			listMrqsPreguntasHdrV1.add(mrqsPreguntasHdrV1);
		}
	}
	
	public void selectForAction(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1) {
		mrqsPreguntasHdrV1ForAction.setNumero(pMrqsPreguntasHdrV1.getNumero());
	}
	
    public void delete() {
		boolean deleteIn = false; 
		deleteIn = true;
		mrqsPreguntasHdrLocal.delete(mrqsPreguntasHdrV1ForAction.getNumero());
		refreshEntity();
		deleteIn = true;
	    PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
	}
	
	public void update() {
		
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

	
	
}
