package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

public interface AdmonUsuariosRolesDao {
 public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public void delete(long pNumero);
 public void update(long pNumero,AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
 public List<AdmonUsuariosRolesV1Dto> findAll();
 public int validaUsuarioRol(long pNumeroUsuario
		                    ,long pNumeroRol
		                    );
}
