package com.cmrise.ejb.backing.exams.corecases;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.ejb.services.exams.CcExamenesLocal;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;

@ManagedBean
@ViewScoped
public class ManageTestExamsForm {
 private List<CcExamenes> listCcExamenes = new ArrayList<CcExamenes>();
 private CcExamenes ccExamenesForAction = new CcExamenes();
 
 @Inject 
 CcExamenesLocal ccExamenesLocal; 
 
 @PostConstruct
 public void init() {
	System.out.println("Comienza ManageTestExamsForm init()");
	refreshEntity(); 
    System.out.println("Finaliza ManageTestExamsForm init()");
 }
 
private void refreshEntity() {
	List<CcExamenesV1Dto> listCcExamenesV1Dto = ccExamenesLocal.findAllWD(); 
    System.out.println("listCcExamenesDto.size():"+listCcExamenesV1Dto.size());
    listCcExamenes = new ArrayList<CcExamenes>();
    for(CcExamenesV1Dto ccExamenesV1Dto: listCcExamenesV1Dto) {
    	CcExamenes ccExamenes = new CcExamenes();
    	System.out.println("ccExamenesDto.getNumero():"+ccExamenesV1Dto.getNumero());
    	ccExamenes.setNumero(ccExamenesV1Dto.getNumero());
    	ccExamenes.setTipoExamenDesc(ccExamenesV1Dto.getTipoExamenDesc());
    	ccExamenes.setDescripcion(ccExamenesV1Dto.getDescripcion());
    	ccExamenes.setTipoExamen(ccExamenesV1Dto.getTipoExamen());
    	ccExamenes.setVisibilidad(ccExamenesV1Dto.getVisibilidad());
    	ccExamenes.setVisibilidadDesc(ccExamenesV1Dto.getVisibilidadDesc());
    	ccExamenes.setEstatus(ccExamenesV1Dto.getEstatus());
    	ccExamenes.setEstatusDesc(ccExamenesV1Dto.getEstatusDesc());
    	ccExamenes.setCreadoPorNombre(ccExamenesV1Dto.getCreadoPorNombre());
    	ccExamenes.setFechaCreacion(ccExamenesV1Dto.getFechaCreacion());
    	ccExamenes.setFechaCreacionString();
    	listCcExamenes.add(ccExamenes); 
    }
} 
 
public String update(CcExamenes pCcExamenes) {
 System.out.println("Entra "+this.getClass()+" update()");	
 FacesContext context = FacesContext.getCurrentInstance(); 
 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
 session.setAttribute("NumeroCcExamenSV", pCcExamenes.getNumero());
 System.out.println("Sale "+this.getClass()+" update()");	
 return "Exams-CoreCases-Update"; 	
}

public void selectForAction(CcExamenes pCcExamenes) {
	System.out.println("Entra "+this.getClass()+" selectForAction()");	
	ccExamenesForAction.setNumero(pCcExamenes.getNumero());
	System.out.println("Sale "+this.getClass()+" selectForAction()");
}

public void delete() {
	System.out.println("Entra "+this.getClass()+" delete()");
	boolean deleteIn = false; 
	ccExamenesLocal.delete(ccExamenesForAction.getNumero());
	refreshEntity(); 
	deleteIn = true; 
	PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
	System.out.println("Sale "+this.getClass()+" delete()");	
}

public CcExamenes getCcExamenesForAction() {
	return ccExamenesForAction;
}

public void setCcExamenesForAction(CcExamenes ccExamenesForAction) {
	this.ccExamenesForAction = ccExamenesForAction;
}

public List<CcExamenes> getListCcExamenes() {
	return listCcExamenes;
}

public void setListCcExamenes(List<CcExamenes> listCcExamenes) {
	this.listCcExamenes = listCcExamenes;
}


 
}
