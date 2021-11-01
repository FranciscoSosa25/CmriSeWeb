package com.cmrise.jpa.dao.candidates.exams;

import java.util.Date;
import java.util.List;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;

public interface CandExamenesDao {

	public long insert(CandExamenesDto pCandExamenesDto); 
	public List<CandExamenesV2Dto> findByExamen(long pNumeroExamen
			                                   ,String pTipoExamen
			                                   ); 
	public void delete(long pNumero); 
	public List<CandExamenesV2Dto> findByUsuario(long pNumeroUsuario);
	public List<CandExamenesV2Dto> findByUsuarioOnlyEfectiveDates(long pNumeroUsuario);
	public List<CandExamenesV2Dto> findAll(); 
	public List<Object> findAllByCandidate(long pNumeroUsuario, String matricula, String cCurp);

	public CandExamenesV1Dto findByNumero(long pNumero);
	public CandExamenesDto find(long pNumero);
	public List<CandExamenesV2Dto> findByCURP(String pCurp,
			                                  String pNombreUsuario
			                                  ,String pApellidoPaterno
			                                  ,String pAPellidoMaterno);
	public void updateEstatus(long pNumero, CandExamenesDto pCandExamenesDto);
	public void updateStartTime(long pNumero, Date date);
	public List<CandExamenesV2Dto> findCandidateByExam(String cCurp, String cNombre, String c_aPaterno, 
									String c_aMaterno, String actPor, String fechaActu, long pNumeroExamen, String pTipoExamen);
	public CandExamenesV2Dto findByNumeroV2(long pNumero);
}
