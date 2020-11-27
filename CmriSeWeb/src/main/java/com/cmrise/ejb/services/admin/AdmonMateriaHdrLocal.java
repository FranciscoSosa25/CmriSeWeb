package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonMateriaHdr;
import com.cmrise.jpa.dto.admin.AdmonMateriaHdrDto;

@Local
public interface AdmonMateriaHdrLocal {

	public long insert(AdmonMateriaHdrDto pAdmonMateriaDto);
	public void update(AdmonMateriaHdr i);
	public void insert(AdmonMateriaHdr i);
	public List<AdmonMateriaHdr> findAll();
	public void delete(long numero);
	public List<AdmonMateriaHdr> findByNumeroAdmonExamen(long pAdmonExamen); 
	
}
