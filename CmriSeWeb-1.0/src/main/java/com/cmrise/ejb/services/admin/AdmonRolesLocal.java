package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;
import javax.faces.model.SelectItem;

import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.admin.KeysRolesDto;

@Local
public interface AdmonRolesLocal {

	public void insert(AdmonRolesDto pAdmonRolesDto);
	public List<AdmonRolesDto> findAll();
	public List<AdmonRolesDto> findCand();
	public List<AdmonRolesDto> findNotCand();
	public void delete(long pNumero); 
	public void update(long pNumero,AdmonRolesDto pAdmonRolesDto);
	public List<KeysDto> findKeys();
	public List<KeysDto> findKeysCand();
	public List<KeysDto> findKeysNotCand();
	public AdmonRolesDto findRole(long idRole);

	public String[] findKeysRolesUser(long idUser);
	public List<KeysRolesDto> findKeysRolesUsuario(long idUser);

}
