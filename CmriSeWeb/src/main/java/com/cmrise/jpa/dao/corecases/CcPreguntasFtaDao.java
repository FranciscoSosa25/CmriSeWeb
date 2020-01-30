package com.cmrise.jpa.dao.corecases;

import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;

public interface CcPreguntasFtaDao {

	public void insert(CcPreguntasFtaDto pCcPreguntasFtaDto); 
	public void delete(long pNumero);
	public void update(long pNumero
			          ,CcPreguntasFtaDto pCcPreguntasFtaDto);
}
