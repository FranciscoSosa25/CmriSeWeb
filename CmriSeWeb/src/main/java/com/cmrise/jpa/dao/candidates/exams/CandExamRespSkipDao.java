package com.cmrise.jpa.dao.candidates.exams;

import java.util.List;

import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamSkipV1Dto;

public interface CandExamRespSkipDao {
	public long insert(CandExamRespSkipDto pCandExamRespSkipDto); 
	public List<CandExamSkipV1Dto> findByReg(long pNumeroExamen, long pNumCandExam,long pNumeroGrupo,long pNumPregHdr, long pNumPregFta,int pSkip);
	public List<CandExamSkipV1Dto> findAllListSkip(long pNumeroExamen, long pNumCandExam,int pEstatus,int pSkip);
	public boolean existsSkip(long pNumeroExamen, long pNumCandExam,long pNumeroGrupo,long pNumPregHdr, long pNumPregFta, int pEstatus);
	public void update(long pNumero, CandExamRespSkipDto pCandExamRespSkipDto);
}
