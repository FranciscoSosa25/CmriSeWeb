package com.cmrise.ejb.services.corecases;

import javax.ejb.Local;

import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;

@Local
public interface CcPreguntasFtaLocal {

	public void insert(CcPreguntasFtaDto pCcPreguntasFtaDto); 
	public void delete(long pNumero);
	public void update(long pNumero
			          ,CcPreguntasFtaDto pCcPreguntasFtaDto);
	
}
