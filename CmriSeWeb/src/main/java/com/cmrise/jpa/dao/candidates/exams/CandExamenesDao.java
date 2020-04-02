package com.cmrise.jpa.dao.candidates.exams;

import java.util.List;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;

public interface CandExamenesDao {

	public long insert(CandExamenesDto pCandExamenesDto); 
	public List<CandExamenesV1Dto> findByExamen(long pNumeroExamen
			                                   ,String pTipoExamen
			                                   ); 
	public void delete(long pNumero); 
	public List<CandExamenesV2Dto> findByUsuario(long pNumeroUsuario); 
	public List<CandExamenesV2Dto> findAll(); 
}
