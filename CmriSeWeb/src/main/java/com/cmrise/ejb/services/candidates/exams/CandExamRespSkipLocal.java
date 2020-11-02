package com.cmrise.ejb.services.candidates.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamSkipV1Dto;

@Local
public interface CandExamRespSkipLocal {
	public long insert(CandExamRespSkipDto pCandExamRespSkipDto);
	public int validaRegistro(long pNumeroCandExamen
	         ,long pNumeroGrupo
	         ,long pNumeroPreguntaHdr
	         ,long pNumeroPreguntaFta); 
    public void update(long pNumero
    		          ,CandExamRespSkipDto pCandExamRespSkipDto); 
    public CandExamRespuestasV1 findObjMod(long pNumeroCandExamen
			              ,long pNumeroGrupo
			              ,long pNumeroPreguntaHdr
			              ,long pNumeroPreguntaFta
			              ); 
    public List<CandExamSkipV1Dto> findAllListSkip(long pNumeroExamen
    		                                      ,long pNumCandExam
    		                                      ,int pEstatus
    		                                      ,int pSkip);
    public boolean existsSkip(long pNumeroExamen
    		                 ,long pNumCandExam
    		                 ,long pNumeroGrupo
    		                 ,long pNumPregHdr
    		                 ,long pNumPregFta
    		                 ,int pEstatus); 
    public void calificaRespuesta(long pNumeroCandExamen
	             ,long pNumeroGrupo
	             ,long pNumeroPreguntaHdr
	             ,long pNumeroPreguntaFta); 
}
