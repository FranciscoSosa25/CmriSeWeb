package com.cmrise.ejb.services.candidates.exams;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandExamRespuestasV1;
import com.cmrise.jpa.dao.candidates.exams.CandExamRespuestasDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasV1Dto;

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
			                  ,long duration
			                  , String pRespuesta) {
		candExamRespuestasDao.updateRespuesta(pNumeroCandExamen
				                            , pNumeroGrupo
				                            , pNumeroPreguntaHdr
				                            , pNumeroPreguntaFta
				                            , duration
				                            , pRespuesta
				                            );
	}
	

	public void updateRespuesta(long pNumeroCandExamen
            ,long pNumeroGrupo
            ,long pNumeroPreguntaHdr
            ,long pNumeroPreguntaFta
            ,long duration
            ,String pRespuesta
            ,String estatus
            ,double puntuacion
            ) {
		
		candExamRespuestasDao.updateRespuesta(pNumeroCandExamen
                , pNumeroGrupo
                , pNumeroPreguntaHdr
                , pNumeroPreguntaFta
                , duration
                , pRespuesta
                , estatus
                ,puntuacion
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

	@Override
	public List<CandExamRespuestasV1> findV1ObjModByNumeroCandExamen(long pNumeroCandExamen) {
		 List<CandExamRespuestasV1Dto> listCandExamRespuestasV1Dto = candExamRespuestasDao.findV1DtoByNumeroCandExamen(pNumeroCandExamen);
		 List<CandExamRespuestasV1> retval = new ArrayList<CandExamRespuestasV1>(); 
		 for(CandExamRespuestasV1Dto candExamRespuestasV1Dto:listCandExamRespuestasV1Dto) {
			 CandExamRespuestasV1 candExamRespuestasV1 = new CandExamRespuestasV1(); 
			 candExamRespuestasV1.setNumero(candExamRespuestasV1Dto.getNumero());
			 candExamRespuestasV1.setNumeroCandExamen(candExamRespuestasV1Dto.getNumeroCandExamen());
			 candExamRespuestasV1.setNumeroGrupo(candExamRespuestasV1Dto.getNumeroGrupo());
			 candExamRespuestasV1.setNumeroPreguntaHdr(candExamRespuestasV1Dto.getNumeroPreguntaHdr());
			 candExamRespuestasV1.setNumeroPreguntaFta(candExamRespuestasV1Dto.getNumeroPreguntaFta());
			 candExamRespuestasV1.setRespuesta(candExamRespuestasV1Dto.getRespuesta());
			 candExamRespuestasV1.setValorPuntuacion(candExamRespuestasV1Dto.getValorPuntuacion());
			 candExamRespuestasV1.setPuntuacion(candExamRespuestasV1Dto.getPuntuacion());
			 candExamRespuestasV1.setEstatus(candExamRespuestasV1Dto.getEstatus());
			 candExamRespuestasV1.setNumOpcCorrectas(candExamRespuestasV1Dto.getNumOpcCorrectas());
			 candExamRespuestasV1.setNumOpcIncorrectas(candExamRespuestasV1Dto.getNumOpcIncorrectas());
			 candExamRespuestasV1.setNumeroExamen(candExamRespuestasV1Dto.getNumeroExamen());
			 candExamRespuestasV1.setNumeroUsuario(candExamRespuestasV1Dto.getNumeroUsuario());
			 candExamRespuestasV1.setTipoExamen(candExamRespuestasV1Dto.getTipoExamen());
			 candExamRespuestasV1.setTituloPregunta(candExamRespuestasV1Dto.getTituloPregunta());
			 candExamRespuestasV1.setTipoPregunta(candExamRespuestasV1Dto.getTipoPregunta());
			 candExamRespuestasV1.setTipoPreguntaDesc(candExamRespuestasV1Dto.getTipoPreguntaDesc());
			 candExamRespuestasV1.setDuration(candExamRespuestasV1Dto.getDuration());
			 if(candExamRespuestasV1Dto.getDuration() != null && candExamRespuestasV1.getDuration() > 0) {
				 double val = candExamRespuestasV1.getDuration() / 1000;
				 candExamRespuestasV1.setTimeSpent(String.format("%.2f",val));
			 }else {
				 candExamRespuestasV1.setTimeSpent("0.00");
			 }
			 
			 retval.add(candExamRespuestasV1); 
			 
		 }
		return retval;
	}

}
