package com.cmrise.jpa.dao.candidates.exams;

import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasFtaDto;

public interface CandCcPreguntasFtaDao {

	public long insert(CandCcPreguntasFtaDto pCandCcPreguntasFtaDto); 
	public void update(long pNumero,String pRespuesta); 
	
}
