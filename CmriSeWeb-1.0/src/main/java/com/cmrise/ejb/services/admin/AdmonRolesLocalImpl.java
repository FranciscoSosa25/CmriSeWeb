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
	AdmonRolesDao admonRolesDao; 
	
	@Override
	public void insert(AdmonRolesDto pAdmonRolesDto) {
		admonRolesDao.insert(pAdmonRolesDto);
	}

	@Override
	public List<AdmonRolesDto> findAll() {
		return admonRolesDao.findAll();
	}
	
	@Override
	public List<AdmonRolesDto> findCand() {
		return admonRolesDao.findCand();
	}
	
	@Override
	public List<AdmonRolesDto> findNotCand() {
		return admonRolesDao.findNotCand();
	}
	
	@Override
	public List<KeysDto> findKeysCand() {
		return admonRolesDao.findKeysCand();
	}
	
	@Override
	public List<KeysDto> findKeysNotCand() {
		return admonRolesDao.findKeysNotCand(); 
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
	
	@Override
	public AdmonRolesDto findRole(long idRole) {
		return admonRolesDao.findRole(idRole);
	}
}
