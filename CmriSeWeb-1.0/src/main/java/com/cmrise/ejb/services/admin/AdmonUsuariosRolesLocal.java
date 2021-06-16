package com.cmrise.ejb.services.admin;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonUsuariosRolesV1;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

@Local
public interface AdmonUsuariosRolesLocal {

	public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
	public void delete(long pNumero);
	public void deleteLogico(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
	public void update(long pNumero,AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
	public void activaRolUsuario(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
	public List<AdmonUsuariosRolesV1Dto> findAll(); 
	public int validaUsuarioRol(long pNumeroUsuario
					           ,long pNumeroRol
					           );
	public int validaUsuarioRolID(long pNumeroUsuario,long pNumeroRol);
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
	public List<AdmonUsuariosRolesV1> findCandidateNotExam(String cCurp, String cNombre, String c_aPaterno, 
			String c_aMaterno, String actPor, String fechaActu, long   pNumeroExamen, String pTipoExamen);
	List<AdmonUsuariosRolesV1> findUser(long numberUser);
	public List<AdmonUsuariosRolesV1Dto> findUsers();
}
