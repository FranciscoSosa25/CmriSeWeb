package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.admin.AdmonUsuariosDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Stateless 
public class AdmonUsuariosLocalImpl implements AdmonUsuariosLocal {

	@Inject 
	AdmonUsuariosDao admonUsuariosDao;
	
	@Override
	public long insert(AdmonUsuariosDto pAdmonUsuariosDto) {
		return admonUsuariosDao.insert(pAdmonUsuariosDto);
	}

	@Override
	public List<AdmonUsuariosDto> findTop500() {
		return admonUsuariosDao.findTop500();
	}

	@Override
	public void delete(long pNumero) {
		admonUsuariosDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, AdmonUsuariosDto pAdmonUsuariosDto) {
		admonUsuariosDao.update(pNumero, pAdmonUsuariosDto);
	}

	@Override
	public List<KeysDto> findKeys() {
		return admonUsuariosDao.findKeys();
	}

	@Override
	public List<AdmonUsuariosDto> findTop500ByFilters(long pNumeroCcExamen
			                                         ,String strClave) {
		return admonUsuariosDao.findTop500ByFilters(pNumeroCcExamen
				                                   ,strClave);
	}

}
