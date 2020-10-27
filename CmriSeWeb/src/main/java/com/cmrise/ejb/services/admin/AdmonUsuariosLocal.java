package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonCandidatosV1Dto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Local
public interface AdmonUsuariosLocal {

	public long insert(AdmonUsuariosDto pAdmonUsuariosDto);
	public List<AdmonUsuariosDto> findTop500();
	public void delete(long pNumero);
	public void update(long pNumero,AdmonUsuariosDto pAdmonUsuariosDto);
	public List<KeysDto> findKeys();
	public List<AdmonUsuariosDto> findTop500ByFilters(long pNumeroCcExamen
			                                         ,String strClave);
	
	public List<AdmonUsuariosRolesV1Dto> findAll();
	public List<AdmonUsuariosRolesV1Dto> findCand();
	public List<AdmonUsuariosRolesV1Dto> findNotCand();	
	public AdmonUsuariosDto selectUsuario(long pNumero);

}
