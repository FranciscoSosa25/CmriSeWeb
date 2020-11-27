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
import com.cmrise.ejb.model.admin.AdmonSubMateria;
import com.cmrise.ejb.services.admin.AdmonSubMateriaLocal;
import com.cmrise.utils.XxSqlConstraints;

@ManagedBean
@ViewScoped
public class AdmonSubMateriasForm {
	private List<AdmonSubMateria> subMaterias = new ArrayList<AdmonSubMateria>();
    private AdmonSubMateria admonSubMateriaForAction = new AdmonSubMateria(); 
	private int idxSubMateria =0; 
    
    @ManagedProperty(value="#{userLogin}")
	private UserLogin userLogin; 

    @Inject 
    AdmonSubMateriaLocal admonSubMateriaLocal; 

    

	 @PostConstruct
	 public void init() {
		 System.out.println("Entra AdmonMateriasForm init()");
		 refreshEntity();
		 System.out.println("Sale AdmonMateriasForm init()");
	 }		 
	
	public void refreshEntity() {
		subMaterias = admonSubMateriaLocal.findAll();
	}
    
	public void onAddNew() {
		System.out.println("userLogin.getNumeroUsuario():"+userLogin.getNumeroUsuario());
		idxSubMateria = idxSubMateria+1; 
		AdmonSubMateria admonSubMateria = new AdmonSubMateria(); 
		admonSubMateria.setFechaEfectivaDesde(new java.util.Date());
		admonSubMateria.setCreadoPor(userLogin.getNumeroUsuario());
		admonSubMateria.setActualizadoPor(userLogin.getNumeroUsuario());
		admonSubMateria.setIdxTemp(idxSubMateria);
		subMaterias.add(admonSubMateria);
	}
	
	public void saveAndUpdate() {
		boolean exceptions = false; 
	 for(AdmonSubMateria i:subMaterias) {
		 
		 if(0!=i.getNumero()) {
				 i.setFechaActualizacion(new java.util.Date());
				 i.setActualizadoPor(userLogin.getNumeroUsuario());
				 admonSubMateriaLocal.update(i); 
		 }else {
			 i.setFechaCreacion(new java.util.Date());
			 i.setFechaActualizacion(new java.util.Date());
			 i.setCreadoPor(userLogin.getNumeroUsuario());
			 i.setActualizadoPor(userLogin.getNumeroUsuario());
			 try {
			 admonSubMateriaLocal.insert(i);
			 }catch(Exception e) {
				 Throwable throwable = e.getCause();
				 while(null!=throwable) {
					 throwable = throwable.getCause();
					 if(null!=throwable) {
						 if(throwable.toString().contains("ADMON_SUBMATERIA_U1")) {
							 exceptions = true; 
							 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", XxSqlConstraints.ADMON_MATERIA_U1+i.getNombre()));
							 break;
						 }
					 }
				 }
			 } /** END  }catch(Exception e) { **/
		 }
	 }	/** END  for(AdmonSubMateria i:subMaterias) { **/
	 
	  if(!exceptions) {
	  FacesMessage msg = new FacesMessage("Se Agregaron", "Los Cambios");
      FacesContext.getCurrentInstance().addMessage(null, msg);
	  }
	 
	}
	
	public void selectForAction(AdmonSubMateria pAdmonMateria) {
		if(0!=pAdmonMateria.getNumero()) {
			admonSubMateriaForAction.setNumero(pAdmonMateria.getNumero());	
		}else {
			admonSubMateriaForAction.setIdxTemp(pAdmonMateria.getIdxTemp());
		}
	}
	
	public void delete() {
		boolean deleteIn = false;
		AdmonSubMateria admonMateriaHdr = null; 
		if(0!=admonSubMateriaForAction.getNumero()) {
			for(AdmonSubMateria i:subMaterias) {
				if(i.getNumero()==admonSubMateriaForAction.getNumero()) {
					admonSubMateriaLocal.delete(admonSubMateriaForAction.getNumero());
					deleteIn = true;
					PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
					admonMateriaHdr = i; 
					break; 
				}
			}	
		}else {
			for(AdmonSubMateria i:subMaterias) {
				if(i.getIdxTemp()==admonSubMateriaForAction.getIdxTemp()) {
					deleteIn = true;
					PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
					admonMateriaHdr = i; 
					break; 
				}
			}	
		}
		if(null!=admonMateriaHdr) {
			subMaterias.remove(admonMateriaHdr); 
		}
	}
	

	
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	
	public List<AdmonSubMateria> getSubMaterias() {
		return subMaterias;
	}

	public void setSubMaterias(List<AdmonSubMateria> subMaterias) {
		this.subMaterias = subMaterias;
	}

	public AdmonSubMateria getAdmonSubMateriaForAction() {
		return admonSubMateriaForAction;
	}

	public void setAdmonSubMateriaForAction(AdmonSubMateria admonSubMateriaForAction) {
		this.admonSubMateriaForAction = admonSubMateriaForAction;
	}	
}
