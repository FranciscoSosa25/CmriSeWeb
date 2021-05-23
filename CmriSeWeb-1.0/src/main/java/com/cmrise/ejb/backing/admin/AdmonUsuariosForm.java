package com.cmrise.ejb.backing.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.cmrise.ejb.helpers.UserLogin;
import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.utils.Utilitarios;
import com.cmrise.utils.UtilitariosLocal;

@ManagedBean
@ViewScoped
public class AdmonUsuariosForm {
    private String nombre; 
    private String apellidoPaterno; 
    private String apellidoMaterno; 
    private String contrasenia; 
    private String tipoUsuario;
    private String correoElectronico;
    private Date fechaEfectivaDesde;
	private Date fechaEfectivaHasta;
	private List<AdmonUsuarios> listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
	private AdmonUsuarios admonUsuariosForAction = new AdmonUsuarios();
	private long numeroRol; 
	private String curp; 
	
	@Inject 
	AdmonUsuariosLocal admonUsuariosLocal; 
	
	@Inject 
	UtilitariosLocal utilitariosLocal;
	
	@Inject
	AdmonUsuariosRolesLocal admonUsuariosRolesLocal; 
	
    @PostConstruct
	public void init() {    	
		refreshEntity();		 
	 }		 
  
    public void refreshEntity() {
    	List<AdmonUsuariosRolesV1Dto> listAdmonUsuariosRolesV1Dto =  admonUsuariosRolesLocal.findUsers(); 
		Iterator<AdmonUsuariosRolesV1Dto> iterAdmonUsuariosRolesV1 = listAdmonUsuariosRolesV1Dto.iterator(); 
		listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
		while(iterAdmonUsuariosRolesV1.hasNext()) {
			AdmonUsuariosRolesV1Dto admonUsuariosRolesV1Dto = iterAdmonUsuariosRolesV1.next(); 
			AdmonUsuarios admonUsuarios = new AdmonUsuarios();
			admonUsuarios.setNumero(admonUsuariosRolesV1Dto.getNumeroUsuario());
			admonUsuarios.setCurp(admonUsuariosRolesV1Dto.getCurp());
			admonUsuarios.setCorreoElectronico(admonUsuariosRolesV1Dto.getCorreoElectronico());
			admonUsuarios.setNombre(admonUsuariosRolesV1Dto.getNombreCompletoUsuario());
			admonUsuarios.setFechaEfectivaDesde(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFedAu()));
			if(Utilitarios.endOfTime.equals(admonUsuariosRolesV1Dto.getFehAu())) {
				admonUsuarios.setFechaEfectivaHasta(null);	
			}else {
				admonUsuarios.setFechaEfectivaHasta(utilitariosLocal.toUtilDate(admonUsuariosRolesV1Dto.getFehAu()));	
			}
			
			admonUsuarios.setNumeroRol(admonUsuariosRolesV1Dto.getNumeroRol());
			admonUsuarios.setNombreRol(admonUsuariosRolesV1Dto.getNombreRol());
			
			listAdmonUsuarios.add(admonUsuarios);
		}
	}
	
	public void selectForAction(AdmonUsuarios pAdmonUsuarios) {
		System.out.println("*pAdmonUsuarios.getNumero():"+pAdmonUsuarios.getNumero());
		admonUsuariosForAction.setNumero(pAdmonUsuarios.getNumero());
		admonUsuariosForAction.setCurp(pAdmonUsuarios.getCurp());
		admonUsuariosForAction.setNombre(pAdmonUsuarios.getNombre());
		admonUsuariosForAction.setApellidoPaterno(pAdmonUsuarios.getApellidoPaterno());
		admonUsuariosForAction.setApellidoMaterno(pAdmonUsuarios.getApellidoMaterno());
		admonUsuariosForAction.setCorreoElectronico(pAdmonUsuarios.getCorreoElectronico());
		admonUsuariosForAction.setFechaEfectivaDesde(pAdmonUsuarios.getFechaEfectivaDesde());
		admonUsuariosForAction.setFechaEfectivaHasta(pAdmonUsuarios.getFechaEfectivaHasta());
		admonUsuariosForAction.setContrasenia(pAdmonUsuarios.getContrasenia());
		admonUsuariosForAction.setNumeroRol(pAdmonUsuarios.getNumeroRol());
	}
		
	public void delete() {
		boolean deleteIn = false; 
		admonUsuariosLocal.delete(admonUsuariosForAction.getNumero());
		refreshEntity();
		deleteIn = true; 
		PrimeFaces.current().ajax().addCallbackParam("deleteIn", deleteIn);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Date getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}
	public void setFechaEfectivaDesde(Date fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}
	public Date getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}
	public void setFechaEfectivaHasta(Date fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}
	public List<AdmonUsuarios> getListAdmonUsuarios() {
		return listAdmonUsuarios;
	}
	public void setListAdmonUsuarios(List<AdmonUsuarios> listAdmonUsuarios) {
		this.listAdmonUsuarios = listAdmonUsuarios;
	}
	public AdmonUsuarios getAdmonUsuariosForAction() {
		return admonUsuariosForAction;
	}
	public void setAdmonUsuariosForAction(AdmonUsuarios admonUsuariosForAction) {
		this.admonUsuariosForAction = admonUsuariosForAction;
	}
	public long getNumeroRol() {
		return numeroRol;
	}
	public void setNumeroRol(long numeroRol) {
		this.numeroRol = numeroRol;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
		
	public String newUser() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

		System.out.println("Entra "+this.getClass()+" Admon-New-Usuario");
		return "Admon-New-Usuario"; 
	}	
	
	public String update(AdmonUsuarios pAdmonUsuarios) {
		 System.out.println("Entra "+this.getClass()+" update()");	
		 FacesContext context = FacesContext.getCurrentInstance(); 
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		 session.setAttribute("NumeroUsuario", pAdmonUsuarios.getNumero());
		 System.out.println("Sale "+this.getClass()+" Admon-Update-Usuario");	
		 return "Admon-Update-Usuario"; 	
		}
	

}
