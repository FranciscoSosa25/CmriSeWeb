package com.cmrise.ejb.services.corecases;

import javax.ejb.Local;

import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;

@Local
public interface CcPreguntasFtaLocal {

	public void insert(CcPreguntasFtaDto pCcPreguntasFtaDto); 
	public void delete(long pNumero);
	public void update(long pNumero
			          ,CcPreguntasFtaDto pCcPreguntasFtaDto);
	public long finNumeroByHdr(long pNumeroHdr);
	public CcPreguntasFtaV1Dto findDtoByNumeroHdr(long pNumeroHdr); 
	public CcPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta);
}
