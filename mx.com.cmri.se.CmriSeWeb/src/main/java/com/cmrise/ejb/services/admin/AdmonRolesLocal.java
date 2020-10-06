package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Local
public interface AdmonRolesLocal {

	public void insert(AdmonRolesDto pAdmonRolesDto);
	public List<AdmonRolesDto> findAll();
	public void delete(long pNumero); 
	public void update(long pNumero,AdmonRolesDto pAdmonRolesDto);
	public List<KeysDto> findKeys();
}
