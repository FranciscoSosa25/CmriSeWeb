package com.cmrise.jpa.dao.candidates.exams;

import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasHdrDto;

public interface CandCcPreguntasHdrDao {
	
	public long insert(CandCcPreguntasHdrDto pCandCcPreguntasHdrDto); 
    public long findNextNumber(long pNumeroCandCcExamen
    		                  ,long pNumeroCandCcPreguntaHdr
    		                  ); 
    public long findPreviousNumber(long pNumeroCandCcExamen
						          ,long pNumeroCandCcPreguntaHdr
						           ); 
}
