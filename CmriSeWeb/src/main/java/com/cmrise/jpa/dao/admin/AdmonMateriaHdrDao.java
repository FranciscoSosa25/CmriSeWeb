package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonMateriaHdrDto;

public interface AdmonMateriaHdrDao {

	public long insert(AdmonMateriaHdrDto pAdmonMateriaDto);

	public void update(long numero
			         , AdmonMateriaHdrDto admonMateriaHdrDto);

	public AdmonMateriaHdrDto findByNumero(long numero);

	public List<AdmonMateriaHdrDto> findAll();

	public void delete(long pNumero);

	public List<Object> findByNumeroAdmonExamen(long pAdmonExamen); 
	
}
