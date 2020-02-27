package com.cmrise.ejb.services.candidates.exams;

import javax.ejb.Local;

import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasHdrDto;

@Local
public interface CandCcPreguntasHdrLocal {

	public long insert(CandCcPreguntasHdrDto pCandCcPreguntasHdrDto); 
	
}
