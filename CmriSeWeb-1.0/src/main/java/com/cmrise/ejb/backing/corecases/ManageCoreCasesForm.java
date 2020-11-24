package com.cmrise.ejb.backing.corecases;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.ejb.services.corecases.CcHdrLocal;
import com.cmrise.utils.Utilitarios;

@ManagedBean
@ViewScoped
public class ManageCoreCasesForm {
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	private List<CcHdrV1> listCcHdrV1 = new ArrayList<CcHdrV1>();
	private CcHdrV1 ccHdrV1ForAction = new CcHdrV1();
	private long admonExamen; 
	private long admonMateria; 
	private long admonSubmateria; 
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal; 
	
	@Inject 
	CcHdrLocal ccHdrLocal;
	
	@Inject 
	AdmonSubMateriaLocal admonSubMateriaLocal;
	
	@Inject
	UpdateCoreCaseForm updateCoreCaseForm;
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal;
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra "+this.getClass()+" init()");

		    System.out.println("Entr onAdmonExmaneChange init");
		    examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.CORE_CASES); 
			 selectExamenesHdr = new ArrayList<SelectItem>(); 
			 for(AdmonExamenHdr i:examenesHdr) {
				 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
				 selectExamenesHdr.add(selectItem); 
			 }
		  
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
		
	
			
//			 ccHdrV1ForAction.setFechaElaboracion(new java.util.Date());
//			
			
			  System.out.println("Entr onAdmonExamenChange init");
				 onAdmonExamenChange();
				  System.out.println("Entr onAdmonMateriaChange init");
				 onAdmonMateriaChange();
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
	public String view(CcHdrV1 pCcHdrV1) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("NumeroCcHdrSV", pCcHdrV1.getNumero());  
		updateCoreCaseForm.init();
		return updateCoreCaseForm.preview(); 
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
	
	public void onAdmonExamenChange() {
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

	public List<SelectItem> getSelectExamenesHdr() {
		return selectExamenesHdr;
	}

	public void setSelectExamenesHdr(List<SelectItem> selectExamenesHdr) {
		this.selectExamenesHdr = selectExamenesHdr;
	}

	public List<SelectItem> getSelectMateriasHdr() {
		return selectMateriasHdr;
	}

	public void setSelectMateriasHdr(List<SelectItem> selectMateriasHdr) {
		this.selectMateriasHdr = selectMateriasHdr;
	}

	public List<SelectItem> getSelectSubMaterias() {
		return selectSubMaterias;
	}

	public void setSelectSubMaterias(List<SelectItem> selectSubMaterias) {
		this.selectSubMaterias = selectSubMaterias;
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

	public long getAdmonSubmateria() {
		return admonSubmateria;
	}

	public void setAdmonSubmateria(long admonSubmateria) {
		this.admonSubmateria = admonSubmateria;
	}

	
}
