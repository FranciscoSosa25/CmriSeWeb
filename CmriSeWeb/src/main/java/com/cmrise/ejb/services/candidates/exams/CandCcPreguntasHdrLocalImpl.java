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

	@Override
	public long findNextNumber(long pNumeroCandCcExamen
			                 , long pNumeroCandCcPreguntaHdr) {
		return candCcPreguntasHdrDao.findNextNumber(pNumeroCandCcExamen
				                                  , pNumeroCandCcPreguntaHdr);
	}

	@Override
	public long findPreviousNumber(long pNumeroCandCcExamen
			                     , long pNumeroCandCcPreguntaHdr) {
		return candCcPreguntasHdrDao.findPreviousNumber(pNumeroCandCcExamen
				                                      , pNumeroCandCcPreguntaHdr);
	}

}
