package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonMateriaDto;

public interface AdmonMateriaDao {

	public long insert(AdmonMateriaDto pAdmonMateriaDto);

	public void update(long numero
			         , AdmonMateriaDto admonMateriaDto);

	public AdmonMateriaDto findByNumero(long numero);

	public List<AdmonMateriaDto> findAll();

	public void delete(long pNumero); 
	
}
