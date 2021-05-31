package com.cmrise.ejb.helpers;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.KeysRolesDto;

@ManagedBean
@SessionScoped
public class UserLogin implements Serializable {

	private static final long serialVersionUID = -2846150922850040631L;

	private long numeroUsuario; 
	private String matricula;
	private String nombreCompletoUsuario; 
	private String descripcionRol;
	private String curp; 
	public List<KeysRolesDto> roles;
	public List<KeysRolesDto> selectedRoles;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public long getNumeroUsuario() {
		return numeroUsuario;
	}

	public void setNumeroUsuario(long numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}

	public String getNombreCompletoUsuario() {
		return nombreCompletoUsuario;
	}

	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public List<KeysRolesDto> getRoles() {
		return roles;
	}

	public void setRoles(List<KeysRolesDto> roles) {
		this.roles = roles;
	}
	
	public List<KeysRolesDto> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(List<KeysRolesDto> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}
	
	public void rolActual() {
		for(KeysRolesDto item:roles) {
			if(item.getNombre().contains(descripcionRol.toString().trim())) {//Disabled
				item.setStyle("text-align: right; border: 1px red; background-color:#373738; color:#fcfcfc;");
				item.setSelected(true);//
			}
			else {//enabled
				item.setStyle("");//
				item.setSelected(false);
			}
		}
	}
}
