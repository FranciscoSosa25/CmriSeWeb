package com.cmrise.ejb.services.candidates.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasDto;

@Local
public interface CandExamRespuestasLocal {

	public long insert(CandExamRespuestasDto pCandExamRespuestasDto);
	public int validaRegistro(long pNumeroCandExamen
					         ,long pNumeroGrupo
					         ,long pNumeroPreguntaHdr
					         ,long pNumeroPreguntaFta); 
	public void updateRespuesta(long pNumeroCandExamen
					          , long pNumeroGrupo
					          , long pNumeroPreguntaHdr
					          , long pNumeroPreguntaFta
					          ,long duration
					          , String pRespuesta); 
	public CandExamRespuestasV1 findObjMod(long pNumeroCandExamen
							              ,long pNumeroGrupo
							              ,long pNumeroPreguntaHdr
							              ,long pNumeroPreguntaFta
							              ); 
	public void calificaRespuesta(long pNumeroCandExamen
					             ,long pNumeroGrupo
					             ,long pNumeroPreguntaHdr
					             ,long pNumeroPreguntaFta); 
	
	public List<CandExamRespuestasV1> findV1ObjModByNumeroCandExamen(long pNumeroCandExamen); 
	
}
