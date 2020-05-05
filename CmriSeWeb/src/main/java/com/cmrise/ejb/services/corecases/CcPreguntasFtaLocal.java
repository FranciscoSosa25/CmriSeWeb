package com.cmrise.ejb.services.corecases;

import javax.ejb.Local;

import com.cmrise.ejb.model.corecases.CcPreguntasFtaV1;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV2Dto;

@Local
public interface CcPreguntasFtaLocal {

	public long insert(CcPreguntasFtaDto pCcPreguntasFtaDto); 
	public void delete(long pNumero);
	public void update(long pNumero
			          ,CcPreguntasFtaDto pCcPreguntasFtaDto);
	public long finNumeroByHdr(long pNumeroHdr);
	public CcPreguntasFtaV1Dto findDtoByNumeroHdr(long pNumeroHdr); 
	public CcPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta);
	public CcPreguntasFtaV2Dto findV2DtoByNumeroFta(long pNumeroFta);
	public CcPreguntasFtaV1 findObjByNumeroHdr(long longNumeroCcPreguntaHdr); 
}
