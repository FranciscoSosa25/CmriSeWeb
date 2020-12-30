package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonCandidatosV1Dto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.jpa.dto.admin.KeysDto;

public interface AdmonUsuariosDao {

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
	public List<AdmonUsuariosRolesV1Dto> findCandidateBy(String curp, String nombre, String aPaterno, String aMaterno, String nombreActualizo, String fechaAct);
}
