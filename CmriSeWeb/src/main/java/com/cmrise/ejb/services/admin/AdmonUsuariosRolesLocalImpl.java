package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.admin.AdmonUsuariosRolesDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;

@Stateless
public class AdmonUsuariosRolesLocalImpl implements AdmonUsuariosRolesLocal {

	@Inject 
	AdmonUsuariosRolesDao admonUsuariosRolesDao;
	
	@Override
	public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.insert(pAdmonUsuariosRolesDto);
	}

	@Override
	public void delete(long pNumero) {
		admonUsuariosRolesDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		admonUsuariosRolesDao.update(pNumero, pAdmonUsuariosRolesDto);
	}

	@Override
	public List<AdmonUsuariosRolesV1Dto> findAll() {
		return admonUsuariosRolesDao.findAll();
	}

	@Override
	public int validaUsuarioRol(long pNumeroUsuario, long pNumeroRol) {
		return admonUsuariosRolesDao.validaUsuarioRol(pNumeroUsuario
				                                     ,pNumeroRol
				                                     );
	}

	@Override
	public int loginUsuarioRol(String pMatricula
			                 , String pRol
			                 , String pContrasenia) {
		return admonUsuariosRolesDao.loginUsuarioRol(pMatricula, pRol, pContrasenia);
	}

}
