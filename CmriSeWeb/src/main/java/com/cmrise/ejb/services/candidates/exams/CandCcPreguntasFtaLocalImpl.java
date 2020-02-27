package com.cmrise.ejb.services.candidates.exams;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.candidates.exams.CandCcPreguntasFtaDao;
import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasFtaDto;

@Stateless
public class CandCcPreguntasFtaLocalImpl implements CandCcPreguntasFtaLocal {

	@Inject 
	CandCcPreguntasFtaDao  candCcPreguntasFtaDao; 
	
	@Override
	public long insert(CandCcPreguntasFtaDto pCandCcPreguntasFtaDto) {
		return candCcPreguntasFtaDao.insert(pCandCcPreguntasFtaDto);
	}

	@Override
	public void update(long pNumero, String pRespuesta) {
		candCcPreguntasFtaDao.update(pNumero, pRespuesta);
	}

}
