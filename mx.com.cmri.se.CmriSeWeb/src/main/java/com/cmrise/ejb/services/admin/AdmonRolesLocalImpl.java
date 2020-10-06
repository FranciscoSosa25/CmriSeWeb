package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.admin.AdmonRolesDao;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Stateless 
public class AdmonRolesLocalImpl implements AdmonRolesLocal {

	@Inject 
	AdmonRolesDao  admonRolesDao; 
	
	@Override
	public void insert(AdmonRolesDto pAdmonRolesDto) {
		admonRolesDao.insert(pAdmonRolesDto);
	}

	@Override
	public List<AdmonRolesDto> findAll() {
		return admonRolesDao.findAll();
	}

	@Override
	public void delete(long pNumero) {
		admonRolesDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, AdmonRolesDto pAdmonRolesDto) {
		admonRolesDao.update(pNumero, pAdmonRolesDto);	
	}

	@Override
	public List<KeysDto> findKeys() {
		return admonRolesDao.findKeys();
	}

}
