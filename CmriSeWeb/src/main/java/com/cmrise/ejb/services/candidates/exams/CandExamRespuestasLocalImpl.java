package com.cmrise.ejb.services.candidates.exams;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.jpa.dao.candidates.exams.CandExamRespuestasDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasDto;

@Stateless 
public class CandExamRespuestasLocalImpl implements CandExamRespuestasLocal {

	@Inject 
	CandExamRespuestasDao  candExamRespuestasDao;
	
	@Override
	public long insert(CandExamRespuestasDto pCandExamRespuestasDto) {
		return candExamRespuestasDao.insert(pCandExamRespuestasDto);
	}

	@Override
	public int validaRegistro(long pNumeroCandExamen
			                , long pNumeroGrupo
			                , long pNumeroPreguntaHdr
			                , long pNumeroPreguntaFta) {
		return candExamRespuestasDao.validaRegistro(pNumeroCandExamen
				                                  , pNumeroGrupo
				                                  , pNumeroPreguntaHdr
				                                  , pNumeroPreguntaFta);
	}

	@Override
	public void updateRespuesta(long pNumeroCandExamen
			                  , long pNumeroGrupo
			                  , long pNumeroPreguntaHdr
			                  , long pNumeroPreguntaFta
			                  , String pRespuesta) {
		candExamRespuestasDao.updateRespuesta(pNumeroCandExamen
				                            , pNumeroGrupo
				                            , pNumeroPreguntaHdr
				                            , pNumeroPreguntaFta
				                            , pRespuesta
				                            );
	}

	@Override
	public CandExamRespuestasV1 findObjMod(long pNumeroCandExamen
			                             , long pNumeroGrupo
			                             , long pNumeroPreguntaHdr
			                             , long pNumeroPreguntaFta) {
		CandExamRespuestasDto candExamRespuestasDto = candExamRespuestasDao.findDto(pNumeroCandExamen
																			  	  , pNumeroGrupo
																				  , pNumeroPreguntaHdr
																				  , pNumeroPreguntaFta
																				  ); 
		
		CandExamRespuestasV1 candExamRespuestasV1 = new CandExamRespuestasV1(); 
		candExamRespuestasV1.setNumero(candExamRespuestasDto.getNumero());
		candExamRespuestasV1.setNumeroCandExamen(candExamRespuestasDto.getNumeroCandExamen());
		candExamRespuestasV1.setNumeroGrupo(candExamRespuestasDto.getNumeroGrupo());
		candExamRespuestasV1.setNumeroPreguntaHdr(candExamRespuestasDto.getNumeroPreguntaHdr());
		candExamRespuestasV1.setNumeroPreguntaFta(candExamRespuestasDto.getNumeroPreguntaFta());
		candExamRespuestasV1.setRespuesta(candExamRespuestasDto.getRespuesta());
		candExamRespuestasV1.setPuntuacion(candExamRespuestasDto.getPuntuacion());
		return candExamRespuestasV1;
	}

	@Override
	public void calificaRespuesta(long pNumeroCandExamen
			                    , long pNumeroGrupo
			                    , long pNumeroPreguntaHdr
			                    , long pNumeroPreguntaFta) {
		
		candExamRespuestasDao.calificaRespuesta(pNumeroCandExamen
				                              , pNumeroGrupo
				                              , pNumeroPreguntaHdr
				                              , pNumeroPreguntaFta
				                              ); 
	}

}
