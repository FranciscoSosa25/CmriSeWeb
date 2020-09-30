package com.cmrise.ejb.backing.mrq;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.services.admin.AdmonExamenHdrLocal;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
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
	
	private List<AdmonExamenHdr> examenesHdr = new ArrayList<AdmonExamenHdr>();
	private List<AdmonMateriaHdr> materiasHdr = new ArrayList<AdmonMateriaHdr>();
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
	private List<SelectItem> selectExamenesHdr = new ArrayList<SelectItem>(); 
	private List<SelectItem> selectMateriasHdr = new ArrayList<SelectItem>();  
	private List<SelectItem> selectSubMaterias = new ArrayList<SelectItem>(); 
	private List<MrqsPreguntasHdrV1> listMrqsPreguntasHdrV1 = new ArrayList<MrqsPreguntasHdrV1>();
	private MrqsPreguntasHdrV1 mrqsPreguntasHdrV1ForAction = new MrqsPreguntasHdrV1();
	private long admonExamen; 
	private long admonMateria; 
	private long admonSubmateria; 
	
	@Inject 
	MrqsPreguntasHdrLocal mrqsPreguntasHdrLocal;
	
	@Inject
	MrqsPreguntasFtaLocal mrqsPreguntasFtaLocal;
	
	@Inject 
	MrqsOpcionMultipleLocal  mrqsOpcionMultipleLocal; 
	
	@Inject 
	AdmonExamenHdrLocal admonExamenHdrLocal; 
	
	@Inject 
	AdmonMateriaHdrLocal admonMateriaHdrLocal; 
	
	@Inject 
	AdmonSubMateriaLocal admonSubMateriaLocal; 
	
	 @PostConstruct
	 public void init() {
		 System.out.println("Entra ManageMrqsForm init()");
		 
		 examenesHdr = admonExamenHdrLocal.findByTipo(Utilitarios.MRQS); 
		 selectExamenesHdr = new ArrayList<SelectItem>(); 
		 for(AdmonExamenHdr i:examenesHdr) {
			 SelectItem selectItem = new SelectItem(i.getNumero(),i.getNombre());
			 selectExamenesHdr.add(selectItem); 
		 }
		 
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
			mrqsPreguntasHdrV1.setTitulo("AdmonMAteria");
			mrqsPreguntasHdrV1.setTipoPregunta(mrqsPreguntasHdrV1Dto.getTipoPregunta());
			mrqsPreguntasHdrV1.setTipoPreguntaDesc(mrqsPreguntasHdrV1Dto.getTipoPreguntaDesc());
			mrqsPreguntasHdrV1.setTemaPreguntaDesc("AdmonSubMateriaDesc");
			mrqsPreguntasHdrV1.setEstatusDesc(mrqsPreguntasHdrV1Dto.getEstatusDesc());
			mrqsPreguntasHdrV1.setDiagnostico(mrqsPreguntasHdrV1Dto.getDiagnostico());
			mrqsPreguntasHdrV1.setAdmonExamen(mrqsPreguntasHdrV1Dto.getAdmonExamen());
			mrqsPreguntasHdrV1.setAdmonMateria(mrqsPreguntasHdrV1Dto.getAdmonMateria());
			mrqsPreguntasHdrV1.setAdmonSubmateria(mrqsPreguntasHdrV1Dto.getAdmonSubmateria());
			mrqsPreguntasHdrV1.setAdmonExamenDesc(mrqsPreguntasHdrV1Dto.getAdmonExamenDesc());
			mrqsPreguntasHdrV1.setAdmonMateriaDesc(mrqsPreguntasHdrV1Dto.getAdmonMateriaDesc());
			mrqsPreguntasHdrV1.setAdmonSubmateriaDesc(mrqsPreguntasHdrV1Dto.getAdmonSubmateriaDesc());
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
		mrqsPreguntasHdrV1ForAction.setDiagnostico(pMrqsPreguntasHdrV1.getDiagnostico());
	}
	
	
	public void findByTituloPregunta() {
		/** Sin Implementacion  29042020**/
	}
	
    public void delete() {
    	
        String strDeleteMsg = mrqsPreguntasHdrLocal.delete(mrqsPreguntasHdrV1ForAction);
        boolean deleteIn = false; 
        if(mrqsPreguntasHdrV1ForAction.isDependent()) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",strDeleteMsg);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
			return;
        }else {
        	MrqsPreguntasHdrV1 forDelete=null; 
        	for(MrqsPreguntasHdrV1 i:listMrqsPreguntasHdrV1) {
        		if(mrqsPreguntasHdrV1ForAction.getNumero()==i.getNumero()) {
        			forDelete = i; 
        			break;
        		}
        	}
        	if(null!=forDelete) {
        		listMrqsPreguntasHdrV1.remove(forDelete); 
            }
        	
        	deleteIn = true; 
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Precausion!",strDeleteMsg);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
        }
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
