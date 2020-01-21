package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonRolesDto;

@Local
public interface AdmonRolesLocal {

	public void insert(AdmonRolesDto pAdmonRolesDto);
	public List<AdmonRolesDto> findAll();
}
