package com.cmrise.ejb.services.admin;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.admin.AdmonExamenLine;
import com.cmrise.jpa.dto.admin.AdmonExamenLineDto;

@Local
public interface AdmonExamenLineLocal {

	public long insert(AdmonExamenLineDto pAdmonExamenLineDto);
	public long insert(AdmonExamenLine i);
	public List<AdmonExamenLine> findByNumeroExamen(long numeroAdmonExamen);
	public void delete(long pNumero);
	
}
