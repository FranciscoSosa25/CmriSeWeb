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

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonMateria;
import com.cmrise.ejb.services.admin.AdmonMateriaLocal;
import com.cmrise.utils.XxSqlConstraints;


@ManagedBean
@ViewScoped
public class AdmonMateriasForm {

	private List<AdmonMateria> materias = new ArrayList<AdmonMateria>();
    private AdmonMateria admonMateriaForAction = new AdmonMateria(); 
	private int idxMateria =0; 
    
    @ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 

    @Inject 
    AdmonMateriaLocal admonMateriaLocal; 

    

	 @PostConstruct
	 public void init() {
		 System.out.println("Entra AdmonMateriasForm init()");
		 refreshEntity();
		 System.out.println("Sale AdmonMateriasForm init()");
	 }		 
	
	public void refreshEntity() {
		materias = admonMateriaLocal.findAll();
	}
    
	public void onAddNew() {
		System.out.println("userLogin.getNumeroUsuario():"+userLogin.getNumeroUsuario());
		idxMateria = idxMateria+1; 
		AdmonMateria admonMateria = new AdmonMateria(); 
		admonMateria.setCreadoPor(userLogin.getNumeroUsuario());
		admonMateria.setActualizadoPor(userLogin.getNumeroUsuario());
		admonMateria.setIdxTemp(idxMateria);
		materias.add(admonMateria);
	}
	
	public void saveAndUpdate() {
		boolean exceptions = false; 
	 for(AdmonMateria i:materias) {
		 
		 if(0!=i.getNumero()) {
			 if(i.isaChange()) {
				 i.setFechaActualizacion(new java.util.Date());
				 i.setActualizadoPor(userLogin.getNumeroUsuario());
				 admonMateriaLocal.update(i); 
			 }
		 }else {
			 i.setFechaCreacion(new java.util.Date());
			 i.setFechaActualizacion(new java.util.Date());
			 i.setCreadoPor(userLogin.getNumeroUsuario());
			 i.setActualizadoPor(userLogin.getNumeroUsuario());
			 try {
			 admonMateriaLocal.insert(i);
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
	 }	/** END  for(AdmonMateria i:materias) { **/
	 
	  if(!exceptions) {
	  FacesMessage msg = new FacesMessage("Se Agregaron", "Los Cambios");
      FacesContext.getCurrentInstance().addMessage(null, msg);
	  }
	 
	}
	
	public void selectForAction(AdmonMateria pAdmonMateria) {
		if(0!=pAdmonMateria.getNumero()) {
			admonMateriaForAction.setNumero(pAdmonMateria.getNumero());	
		}else {
			admonMateriaForAction.setIdxTemp(pAdmonMateria.getIdxTemp());
		}
	}
	
	public void delete() {
		boolean deleteIn = false;
		AdmonMateria admonMateria = null; 
		if(0!=admonMateriaForAction.getNumero()) {
			for(AdmonMateria i:materias) {
				if(i.getNumero()==admonMateriaForAction.getNumero()) {
					admonMateriaLocal.delete(admonMateriaForAction.getNumero());
					deleteIn = true;
					PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
					admonMateria = i; 
					break; 
				}
			}	
		}else {
			for(AdmonMateria i:materias) {
				if(i.getIdxTemp()==admonMateriaForAction.getIdxTemp()) {
					deleteIn = true;
					PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
					admonMateria = i; 
					break; 
				}
			}	
		}
		if(null!=admonMateria) {
			materias.remove(admonMateria); 
		}
	}
	
	public List<AdmonMateria> getMaterias() {
		return materias;
	}

	public void setMaterias(List<AdmonMateria> materias) {
		this.materias = materias;
	} 
	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public AdmonMateria getAdmonMateriaForAction() {
		return admonMateriaForAction;
	}

	public void setAdmonMateriaForAction(AdmonMateria admonMateriaForAction) {
		this.admonMateriaForAction = admonMateriaForAction;
	}	
}
