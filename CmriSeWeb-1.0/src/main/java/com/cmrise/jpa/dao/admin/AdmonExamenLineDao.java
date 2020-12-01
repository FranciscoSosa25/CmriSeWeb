package com.cmrise.jpa.dao.admin;

import java.util.List;

import com.cmrise.jpa.dto.admin.AdmonExamenLineDto;

public interface AdmonExamenLineDao {

	public long insert(AdmonExamenLineDto pAdmonExamenLineDto);

	public List<AdmonExamenLineDto> findByNumeroHdr(long numero);

	public void delete(long pNumero); 
	
}
