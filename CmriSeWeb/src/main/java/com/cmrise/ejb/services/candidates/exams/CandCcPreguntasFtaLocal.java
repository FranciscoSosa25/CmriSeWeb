package com.cmrise.ejb.services.candidates.exams;

import javax.ejb.Local;

import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasFtaDto;

@Local
public interface CandCcPreguntasFtaLocal {
	public long insert(CandCcPreguntasFtaDto pCandCcPreguntasFtaDto); 
	public void update(long pNumero,String pRespuesta); 
}
