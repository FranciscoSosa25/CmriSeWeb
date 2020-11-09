package com.cmrise.ejb.backing.admin;

import java.util.ArrayList;
import java.util.List;

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

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.ejb.services.admin.AdmonMateriaHdrLocal;
import com.cmrise.utils.XxSqlConstraints;


@ManagedBean
@ViewScoped
public class AdmonMateriasForm {

	private List<AdmonMateriaHdr> materias = new ArrayList<AdmonMateriaHdr>();
    private AdmonMateriaHdr admonMateriaForAction = new AdmonMateriaHdr(); 
	private int idxMateria =0; 
    
    @ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 

    @Inject 
    AdmonMateriaHdrLocal admonMateriaHdrLocal; 

    

	 @PostConstruct
	 public void init() {
		 System.out.println("Entra AdmonMateriasForm init()");
		 refreshEntity();
		 System.out.println("Sale AdmonMateriasForm init()");
	 }		 
	
	public void refreshEntity() {
		materias = admonMateriaHdrLocal.findAll();
	}
    
	public void onAddNew() {
		System.out.println("userLogin.getNumeroUsuario():"+userLogin.getNumeroUsuario());
		idxMateria = idxMateria+1; 
		AdmonMateriaHdr admonMateriaHdr = new AdmonMateriaHdr(); 
		admonMateriaHdr.setFechaEfectivaDesde(new java.util.Date());
		admonMateriaHdr.setCreadoPor(userLogin.getNumeroUsuario());
		admonMateriaHdr.setActualizadoPor(userLogin.getNumeroUsuario());
		admonMateriaHdr.setIdxTemp(idxMateria);
		materias.add(admonMateriaHdr);
	}
	
	public void saveAndUpdate() {
		boolean exceptions = false; 
	 for(AdmonMateriaHdr i:materias) {
		 
		 if(0!=i.getNumero()) {
			 if(i.isaChange()) {
				 i.setFechaActualizacion(new java.util.Date());
				 i.setActualizadoPor(userLogin.getNumeroUsuario());
				 admonMateriaHdrLocal.update(i); 
			 }
		 }else {
			 i.setFechaCreacion(new java.util.Date());
			 i.setFechaActualizacion(new java.util.Date());
			 i.setCreadoPor(userLogin.getNumeroUsuario());
			 i.setActualizadoPor(userLogin.getNumeroUsuario());
			 try {
			 admonMateriaHdrLocal.insert(i);
			 }catch(Exception e) {
				 Throwable throwable = e.getCause();
				 while(null!=throwable) {
					 throwable = throwable.getCause();
					 if(null!=throwable) {
						 if(throwable.toString().contains("ADMON_MATERIA_U1")) {
							 exceptions = true; 
							 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", XxSqlConstraints.ADMON_MATERIA_U1+i.getNombre()));
							 break;
						 }
					 }
				 }
			 } /** END  }catch(Exception e) { **/
		 }
	 }	/** END  for(AdmonMateriaHdr i:materias) { **/
	 
	  if(!exceptions) {
	  FacesMessage msg = new FacesMessage("Se Agregaron", "Los Cambios");
      FacesContext.getCurrentInstance().addMessage(null, msg);
	  }
	 
	}
	
	public void selectForAction(AdmonMateriaHdr pAdmonMateria) {
		if(0!=pAdmonMateria.getNumero()) {
			admonMateriaForAction.setNumero(pAdmonMateria.getNumero());	
		}else {
			admonMateriaForAction.setIdxTemp(pAdmonMateria.getIdxTemp());
		}
	}
	
	public void delete() {
		boolean deleteIn = false;
		AdmonMateriaHdr admonMateriaHdr = null; 
		if(0!=admonMateriaForAction.getNumero()) {
			for(AdmonMateriaHdr i:materias) {
				if(i.getNumero()==admonMateriaForAction.getNumero()) {
					admonMateriaHdrLocal.delete(admonMateriaForAction.getNumero());
					deleteIn = true;
					PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
					admonMateriaHdr = i; 
					break; 
				}
			}	
		}else {
			for(AdmonMateriaHdr i:materias) {
				if(i.getIdxTemp()==admonMateriaForAction.getIdxTemp()) {
					deleteIn = true;
					PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
					admonMateriaHdr = i; 
					break; 
				}
			}	
		}
		if(null!=admonMateriaHdr) {
			materias.remove(admonMateriaHdr); 
		}
	}
	
	public String toAdmonMateriaDetail(AdmonMateriaHdr pAdmonMateriaHdr) {
		System.out.println("Entra toAdmonMateriaDetail");
		String accion = null; 
		if(0==pAdmonMateriaHdr.getNumero()) {
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Antes de continuar hay que guardar"));
	    }else {
	    	 FacesContext context = FacesContext.getCurrentInstance();  
	    	 HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest(); 
		     HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		     session.setAttribute("NumeroAdmonMateriaSV", pAdmonMateriaHdr.getNumero());
		     accion = "Admon-Materias-Detail"; 
	    }
		return accion; 
	}
	
	public List<AdmonMateriaHdr> getMaterias() {
		return materias;
	}

	public void setMaterias(List<AdmonMateriaHdr> materias) {
		this.materias = materias;
	} 
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public AdmonMateriaHdr getAdmonMateriaForAction() {
		return admonMateriaForAction;
	}

	public void setAdmonMateriaForAction(AdmonMateriaHdr admonMateriaForAction) {
		this.admonMateriaForAction = admonMateriaForAction;
	}	
}
