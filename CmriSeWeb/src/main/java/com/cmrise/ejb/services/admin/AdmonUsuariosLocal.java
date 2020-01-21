package com.cmrise.ejb.services.admin;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;

@Local
public interface AdmonUsuariosLocal {

	public void insert(AdmonUsuariosDto pAdmonUsuariosDto);
	
}
