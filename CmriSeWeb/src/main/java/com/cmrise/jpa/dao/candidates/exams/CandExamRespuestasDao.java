package com.cmrise.jpa.dao.candidates.exams;

import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasDto;

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
}
