package com.cmrise.ejb.services.candidates.exams;

import javax.ejb.Local;

import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasHdrDto;

@Local
public interface CandCcPreguntasHdrLocal {

	public long insert(CandCcPreguntasHdrDto pCandCcPreguntasHdrDto); 
	public long findNextNumber(long pNumeroCandCcExamen
                              ,long pNumeroCandCcPreguntaHdr
                               ); 
    public long findPreviousNumber(long pNumeroCandCcExamen
		                          ,long pNumeroCandCcPreguntaHdr
		                           ); 

}
