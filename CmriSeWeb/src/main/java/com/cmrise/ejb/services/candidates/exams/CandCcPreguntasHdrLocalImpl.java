package com.cmrise.ejb.services.candidates.exams;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.candidates.exams.CandCcPreguntasHdrDao;
import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasHdrDto;

@Stateless 
public class CandCcPreguntasHdrLocalImpl implements CandCcPreguntasHdrLocal {

	@Inject 
	CandCcPreguntasHdrDao candCcPreguntasHdrDao; 
	
	@Override
	public long insert(CandCcPreguntasHdrDto pCandCcPreguntasHdrDto) {
		return candCcPreguntasHdrDao.insert(pCandCcPreguntasHdrDto);
	}

}
