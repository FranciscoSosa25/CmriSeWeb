package com.cmrise.ejb.services.candidates.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.candidates.exams.CandCcExamenesV1;
import com.cmrise.jpa.dto.candidates.exams.CandCcExamenesDto;

@Local
public interface CandCcExamenesLocal {
	public long insert(CandCcExamenesDto pCandCcExamenesDto); 	
	public List<CandCcExamenesV1> findCandCcExamInfoByNumero(long pNumeroCandCcExamen); 
	public long findNumeroCandCcExamen(long pNumeroCcExamen, long pNumeroCandidato);
	 
}
