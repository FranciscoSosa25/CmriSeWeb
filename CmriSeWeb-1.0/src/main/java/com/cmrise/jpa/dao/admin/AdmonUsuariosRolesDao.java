package com.cmrise.jpa.dao.admin;

import java.util.Date;
import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

public interface AdmonUsuariosRolesDao {
 public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public void delete(long pNumero);
 public void deleteLogico(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public void update(long pNumero,AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public void activaRolUsuario(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public List<AdmonUsuariosRolesV1Dto> findAll();
 public List<AdmonUsuariosRolesV1Dto> findCand();
 public List<AdmonUsuariosRolesV1Dto> findNotCand();
 public int validaUsuarioRol(long pNumeroUsuario
		                    ,long pNumeroRol
		                    );
 public int validaUsuarioRolID(long pNumeroUsuario,long pNumeroRol);
 public int loginUsuarioRol(String pCurp
		                   ,String pRol
		                   ,String pContrasenia
		                   ); 
 
 public List<Object> findWithFilterExam(long pNumeroExamen,String pTipoExamen); 
 
 public AdmonUsuariosRolesV1Dto findLoginUsusarioRol(String pCurp
											        ,String pRol
											        ,String pContrasenia
											        ); 

 public List<Object> findCandidateNotExam(String cCurp, String cNombre, String c_aPaterno, 
			String c_aMaterno, String actPor, String fechaActu, long   pNumeroExamen, String pTipoExamen);
 
 public List<Object> findUser(long numberUser);
 public List<Object> findUsers();
}
