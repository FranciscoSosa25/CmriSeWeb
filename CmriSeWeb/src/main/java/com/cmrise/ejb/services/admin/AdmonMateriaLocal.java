package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonMateria;
import com.cmrise.jpa.dto.admin.AdmonMateriaDto;

@Local
public interface AdmonMateriaLocal {

	public long insert(AdmonMateriaDto pAdmonMateriaDto);
	public void update(AdmonMateria i);
	public void insert(AdmonMateria i);
	public List<AdmonMateria> findAll();
	public void delete(long numero); 
	
}
