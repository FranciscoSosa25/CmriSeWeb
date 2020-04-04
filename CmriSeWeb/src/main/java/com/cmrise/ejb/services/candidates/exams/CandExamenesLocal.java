package com.cmrise.ejb.services.candidates.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.ejb.model.candidates.exams.Examinations;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;

@Local
public interface CandExamenesLocal {

	public long insert(CandExamenesDto pCandExamenesDto); 
	public List<CandExamenesV1> findByExamen(long pNumeroExamen
								            ,String pTipoExamen
								            ); 
	public void delete(long pNumero); 
	public List<Examinations> findByUsuario(long pNumeroUsuario); 
	public List<CandExamenesV2> findAll(); 
}
