package com.cmrise.ejb.backing.exams.mrqs;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.cmrise.ejb.model.admin.AdmonRoles;
import com.cmrise.ejb.model.admin.AdmonUsuarios;
import com.cmrise.ejb.services.admin.AdmonRolesLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosLocal;
import com.cmrise.ejb.services.admin.AdmonUsuariosRolesLocal;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

@ManagedBean
@ViewScoped
public class CreateMrqsCandidatesForm {
	
		private long numeroUsuario;
		private String nombre; 
	    private String apellidoPaterno; 
	    private String apellidoMaterno; 
	    private String correoElectronico;
	    private long numeroRol;
	    private List<AdmonUsuarios> listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
	    private List<AdmonRoles> listAdmonRoles = new ArrayList<AdmonRoles>();
	    @Inject 
		AdmonUsuariosLocal admonUsuariosLocal; 
	    
	    @Inject
		AdmonRolesLocal admonRolesLocal;
		
		
	    public void refreshEntity() {
	    	 List<AdmonUsuariosDto> listAdmonUsuariosDto = admonUsuariosLocal.findAll(); 
	    	 List<AdmonRolesDto> listAdmonRolesDto = admonRolesLocal.findAll();
			 Iterator<AdmonUsuariosDto> itertAdmonUsuariosDto = listAdmonUsuariosDto.iterator(); 
			 Iterator<AdmonRolesDto> itertAdmonRolesDto = listAdmonRolesDto.iterator();
			 listAdmonUsuarios = new ArrayList<AdmonUsuarios>();
				while(itertAdmonUsuariosDto.hasNext()) {
				AdmonUsuariosDto admonUsuariosDto = itertAdmonUsuariosDto.next(); 
				AdmonRolesDto admonRolesDto = itertAdmonRolesDto.next();
				AdmonUsuarios admonUsuarios = new AdmonUsuarios();
				AdmonRoles admonRoles = new AdmonRoles();
				admonUsuarios.setNumero(admonUsuariosDto.getNumero());
				admonRoles.setNumero(admonRolesDto.getNumero());
				admonUsuarios.setNombre(admonUsuariosDto.getNombre());
				admonUsuarios.setApellidoPaterno(admonUsuariosDto.getApellidoPaterno());
				admonUsuarios.setApellidoMaterno(admonUsuariosDto.getApellidoMaterno());
				admonUsuarios.setContrasenia(admonUsuariosDto.getContrasenia());
				admonUsuarios.setCorreoElectronico(admonUsuariosDto.getCorreoElectronico());
				listAdmonUsuarios.add(admonUsuarios);
				listAdmonRoles.add(admonRoles);
	    
			}
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
		public String getCorreoElectronico() {
			return correoElectronico;
		}
		public void setCorreoElectronico(String correoElectronico) {
			this.correoElectronico = correoElectronico;
		}
		public long getNumeroRol() {
			return numeroRol;
		}
		public void setNumeroRol(long numeroRol) {
			this.numeroRol = numeroRol;
		}
		public List<AdmonUsuarios> getListAdmonUsuarios() {
			return listAdmonUsuarios;
		}
		public void setListAdmonUsuarios(List<AdmonUsuarios> listAdmonUsuarios) {
			this.listAdmonUsuarios = listAdmonUsuarios;
		}
		public long getNumeroUsuario() {
			return numeroUsuario;
		}
		public void setNumeroUsuario(long numeroUsuario) {
			this.numeroUsuario = numeroUsuario;
		}


		public List<AdmonRoles> getListAdmonRoles() {
			return listAdmonRoles;
		}


		public void setListAdmonRoles(List<AdmonRoles> listAdmonRoles) {
			this.listAdmonRoles = listAdmonRoles;
		}
}
