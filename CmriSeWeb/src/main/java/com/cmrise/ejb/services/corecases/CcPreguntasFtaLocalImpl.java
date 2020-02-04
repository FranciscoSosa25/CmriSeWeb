package com.cmrise.ejb.services.corecases;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;

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

}
