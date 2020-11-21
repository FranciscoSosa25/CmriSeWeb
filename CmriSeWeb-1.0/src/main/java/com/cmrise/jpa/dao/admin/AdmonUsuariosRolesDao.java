package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

public interface AdmonUsuariosRolesDao {
 public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public void delete(long pNumero);
 public void update(long pNumero,AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public List<AdmonUsuariosRolesV1Dto> findAll();
 public List<AdmonUsuariosRolesV1Dto> findCand();
 public List<AdmonUsuariosRolesV1Dto> findNotCand();
 public int validaUsuarioRol(long pNumeroUsuario
		                    ,long pNumeroRol
		                    );
 public int loginUsuarioRol(String pCurp
		                   ,String pRol
		                   ,String pContrasenia
		                   ); 
 
 public List<Object> findWithFilterExam(long pNumeroExamen,String pTipoExamen); 
 
 public AdmonUsuariosRolesV1Dto findLoginUsusarioRol(String pCurp
											        ,String pRol
											        ,String pContrasenia
											        ); 

 
}
