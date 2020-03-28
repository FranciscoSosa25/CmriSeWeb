package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

@Local
public interface AdmonUsuariosRolesLocal {

	public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
	public void delete(long pNumero);
	public void update(long pNumero,AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
	public List<AdmonUsuariosRolesV1Dto> findAll(); 
	public int validaUsuarioRol(long pNumeroUsuario
					           ,long pNumeroRol
					           );
	 public int loginUsuarioRol(String pMatricula
					             ,String pRol
					             ,String pContrasenia
					             ); 
}
