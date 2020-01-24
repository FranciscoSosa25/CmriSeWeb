package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.jpa.dto.admin.KeysDto;

@Local
public interface AdmonUsuariosLocal {

	public void insert(AdmonUsuariosDto pAdmonUsuariosDto);
	public List<AdmonUsuariosDto> findTop500();
	public void delete(long pNumero);
	public void update(long pNumero,AdmonUsuariosDto pAdmonUsuariosDto);
	public List<KeysDto> findKeys();
}
