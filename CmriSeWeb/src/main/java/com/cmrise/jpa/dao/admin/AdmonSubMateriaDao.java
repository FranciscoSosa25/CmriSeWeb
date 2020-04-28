package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonSubMateriaDto;

public interface AdmonSubMateriaDao {

	public long insert(AdmonSubMateriaDto pAdmonSubMateriaDto);
	public List<AdmonSubMateriaDto> findAll();
	public AdmonSubMateriaDto findByNumero(long numero);
	
}
