package com.cmrise.jpa.dao.corecases;

import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;

public interface CcPreguntasFtaDao {

	public void insert(CcPreguntasFtaDto pCcPreguntasFtaDto); 
	public void delete(long pNumero);
	public void update(long pNumero
			          ,CcPreguntasFtaDto pCcPreguntasFtaDto);
	public long finNumeroByHdr(long pNumeroHdr);
	public CcPreguntasFtaV1Dto findDtoByNumeroHdr(long pNumeroHdr);
}
