package com.cmrise.ejb.services.mrqs;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;

@Stateless 
public class MrqsPreguntasFtaLocalImpl implements MrqsPreguntasFtaLocal {

	@Inject
	MrqsPreguntasFtaDao mrqsPreguntasFtaDao;
	
	@Override
	public void insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		mrqsPreguntasFtaDao.insert(pMrqsPreguntasFtaDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsPreguntasFtaDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		mrqsPreguntasFtaDao.update(pNumero, pMrqsPreguntasFtaDto);
	}

}
