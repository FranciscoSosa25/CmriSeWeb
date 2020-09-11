package com.cmrise.ejb.services.candidates.exams;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.jpa.dao.candidates.exams.CandExamRespSkipDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamSkipV1Dto;

@Stateless
public class CandExamRespSkipLocalImpl implements CandExamRespSkipLocal{

	@Inject 
	CandExamRespSkipDao  candExamRespSkipDao;
	
	@Override
	public long insert(CandExamRespSkipDto pCandExamRespSkipDto) {
		return candExamRespSkipDao.insert(pCandExamRespSkipDto);
	}

	@Override
	public int validaRegistro(long pNumeroCandExamen, long pNumeroGrupo, long pNumeroPreguntaHdr,
			long pNumeroPreguntaFta) {
		return 0;
	}

	@Override
	public void update(long pNumero
			          ,CandExamRespSkipDto pCandExamRespSkipDto) {
		 candExamRespSkipDao.update(pNumero, pCandExamRespSkipDto);
	}

	@Override
	public CandExamRespuestasV1 findObjMod(long pNumeroCandExamen, long pNumeroGrupo, long pNumeroPreguntaHdr,
			long pNumeroPreguntaFta) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public boolean existsSkip(long pNumeroExamen,long pNumeroGrupo, long pNumCandExam,long pNumPregHdr, long pNumPregFta, int pEstatus) {
		// TODO Auto-generated method stub
		return candExamRespSkipDao.existsSkip(pNumeroExamen, pNumeroGrupo, pNumCandExam, pNumPregHdr, pNumPregFta,  pEstatus);
	}
	@Override
	public void calificaRespuesta(long pNumeroCandExamen, long pNumeroGrupo, long pNumeroPreguntaHdr,
			long pNumeroPreguntaFta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CandExamSkipV1Dto> findAllListSkip(long pNumeroExamen, long pNumCandExam, int pEstatus, int pSkip) {
		return candExamRespSkipDao.findAllListSkip(pNumeroExamen, pNumCandExam, pEstatus, pSkip);
	}

}
