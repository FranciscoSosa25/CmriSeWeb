package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
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
	 public int loginUsuarioRol(String pCurp
					             ,String pRol
					             ,String pContrasenia
					             ); 
	 public List<AdmonUsuariosRolesV1> findWithFilterExam(long   pNumeroExamen
											             ,String pTipoExamen
											             ); 
	 public AdmonUsuariosRolesV1Dto findLoginUsusarioRol(String pCurp
											           , String pRol
											           , String pContrasenia
											            );
	public List<AdmonUsuariosRolesV1Dto> findCand(); 
	public List<AdmonUsuariosRolesV1Dto> findNotCand();
}
