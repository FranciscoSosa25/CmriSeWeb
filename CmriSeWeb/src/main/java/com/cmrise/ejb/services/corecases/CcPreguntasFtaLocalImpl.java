package com.cmrise.ejb.services.corecases;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;

@Stateless 
public class CcPreguntasFtaLocalImpl implements CcPreguntasFtaLocal {

	@Inject 
	CcPreguntasFtaDao ccPreguntasFtaDao; 
	
	@Override
	public void insert(CcPreguntasFtaDto pCcPreguntasFtaDto) {
		ccPreguntasFtaDao.insert(pCcPreguntasFtaDto);
	}

	@Override
	public void delete(long pNumero) {
		ccPreguntasFtaDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, CcPreguntasFtaDto pCcPreguntasFtaDto) {
		ccPreguntasFtaDao.update(pNumero, pCcPreguntasFtaDto);
	}

	@Override
	public long finNumeroByHdr(long pNumeroHdr) {
		return ccPreguntasFtaDao.finNumeroByHdr(pNumeroHdr);
	}

	@Override
	public CcPreguntasFtaV1Dto findDtoByNumeroHdr(long pNumeroHdr) {
		return ccPreguntasFtaDao.findDtoByNumeroHdr(pNumeroHdr);
	}

	@Override
	public CcPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta) {
		return ccPreguntasFtaDao.findDtoByNumeroFta(pNumeroFta);
	}

}
