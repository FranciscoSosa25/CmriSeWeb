package com.cmrise.ejb.services.admin;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;

@Local
public interface AdmonUsuariosRolesLocal {

	public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto);
	
}
