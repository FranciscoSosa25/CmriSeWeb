package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.KeysDto;

public interface AdmonMaestroMateriaDao {

	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMAteriaDto);
	
	public List<KeysDto> findKeysMaterias();
	
}
