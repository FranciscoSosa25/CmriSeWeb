package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;

@ManagedBean
@ViewScoped
public class ManageCoreCasesForm {

	private List<CcHdrV1> listCcHdrV1 = new ArrayList<CcHdrV1>();
	private CcHdrV1 ccHdrV1ForAction = new CcHdrV1();

	@Inject 
	CcHdrLocal ccHdrLocal; 
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");
		 refreshEntity();
		 System.out.println("Sale "+this.getClass()+" init()");
	 }		 
	 
	private void refreshEntity() {
		listCcHdrV1 = ccHdrLocal.findAll(); 
		//List<CcHdrV1Dto> listCcHdrV1Dto = ccHdrLocal.findAll(); 
		/**Iterator<CcHdrV1Dto> iterCcHdrV1Dto = listCcHdrV1Dto.iterator(); 
		listCcHdrV1 = new ArrayList<CcHdrV1>();
		while(iterCcHdrV1Dto.hasNext()) {
			CcHdrV1Dto ccHdrV1Dto = iterCcHdrV1Dto.next(); 
			CcHdrV1 ccHdrV1 = new CcHdrV1();
			ccHdrV1.setNumero(ccHdrV1Dto.getNumero());
			//ccHdrV1.setNombre(ccHdrV1Dto.getNombre());
			ccHdrV1.setEstatus(ccHdrV1Dto.getEstatus());
			ccHdrV1.setEstatusDesc(ccHdrV1Dto.getEstatusDesc());
			//ccHdrV1.setTema(ccHdrV1Dto.getTema());
			//ccHdrV1.setTemaDesc(ccHdrV1Dto.getTemaDesc());
			ccHdrV1.setEtiquetas(ccHdrV1Dto.getEtiquetas());
			listCcHdrV1.add(ccHdrV1);
		}
		**/
	}

	public String update(CcHdrV1 pCcHdrV1) {
		String retval = null;
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", pCcHdrV1.getNumero());  
		retval = "Preguntas-Update-CoreCase";
		return retval; 
	}
	
	public void selectForAction(CcHdrV1 pCcHdrV1) {
		ccHdrV1ForAction.setNumero(pCcHdrV1.getNumero());
	}
	
	public void delete() {
		 System.out.println("Entra "+this.getClass()+" delete()");
		 boolean deleteIn = false; 
		 ccHdrLocal.delete(ccHdrV1ForAction.getNumero());
		 refreshEntity();
		 deleteIn = true;
		 PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
		 System.out.println("Sale "+this.getClass()+" delete()");
	}
	
	public List<CcHdrV1> getListCcHdrV1() {
		return listCcHdrV1;
		
	}
	
	public void setListCcHdrV1(List<CcHdrV1> listCcHdrV1) {
		this.listCcHdrV1 = listCcHdrV1;
	}

	public CcHdrV1 getCcHdrV1ForAction() {
		return ccHdrV1ForAction;
	}

	public void setCcHdrV1ForAction(CcHdrV1 ccHdrV1ForAction) {
		this.ccHdrV1ForAction = ccHdrV1ForAction;
	} 	
}
