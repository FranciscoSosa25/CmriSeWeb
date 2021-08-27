package com.cmrise.ejb.services.candidates.exams;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;

@Local
public interface CandExamenesLocal {

	public long insert(CandExamenesDto pCandExamenesDto); 
	public List<CandExamenesV2> findByExamen(long pNumeroExamen
								            ,String pTipoExamen
								            ); 
	public void delete(long pNumero); 
	public List<CandExamenesV2> findByUsuario(long pNumeroUsuario); 
	public List<CandExamenesV2> findByUsuarioOnlyEfectiveDates(long pNumeroUsuario);
	public List<CandExamenesV2> findAll(); 
    public CandExamenesV1 findByNumero(long pNumero); 
    public List<CandExamenesV2> findByCURP(String pCurp,
    		                               String pNombreUsuario, 
    		                               String pApellidoPaterno,
    		                                String pApellidoMaterno);
    public void updateEstatus(long pNumero, CandExamenesDto pCandExamenesDto);
    public List<CandExamenesV2> findCandidateByExam(String cCurp, String cNombre, String c_aPaterno, 
			String c_aMaterno, String actPor, String fechaActu, long pNumeroExamen, String pTipoExamen);
	public CandExamenesV1 findByNumeroV2(long pNumero);
}

