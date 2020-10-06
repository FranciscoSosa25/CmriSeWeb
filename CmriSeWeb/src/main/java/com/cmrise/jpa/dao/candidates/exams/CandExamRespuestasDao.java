package com.cmrise.jpa.dao.candidates.exams;

import java.util.List;

import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasV1Dto;

public interface CandExamRespuestasDao {

	public long insert(CandExamRespuestasDto pCandExamRespuestasDto); 
	
	public int validaRegistro(long pNumeroCandExamen
			                 ,long pNumeroGrupo
			                 ,long pNumeroPreguntaHdr
			                 ,long pNumeroPreguntaFta
			                 );
	
	public void updateRespuesta(long pNumeroCandExamen
				               ,long pNumeroGrupo
				               ,long pNumeroPreguntaHdr
				               ,long pNumeroPreguntaFta
				               ,String pRespuesta
				               );
	
	public CandExamRespuestasDto findDto(long pNumeroCandExamen
							            ,long pNumeroGrupo
							            ,long pNumeroPreguntaHdr
							            ,long pNumeroPreguntaFta
							            );
    public void calificaRespuesta(long pNumeroCandExamen
					             ,long pNumeroGrupo
					             ,long pNumeroPreguntaHdr
					             ,long pNumeroPreguntaFta
					             );			
    
    public List<CandExamRespuestasV1Dto> findV1DtoByNumeroCandExamen(long pNumeroCandExamen);
    
}
