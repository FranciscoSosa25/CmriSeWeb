package com.cmrise.ejb.services.exams;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.exams.MrqsGrupoLinesV2;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.jpa.dao.exams.MrqsGrupoLinesDao;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesV1Dto;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesV2Dto;

@Stateless
public class MrqsGrupoLinesLocalImpl implements MrqsGrupoLinesLocal {

	@Inject 
	MrqsGrupoLinesDao mrqsGrupoLinesDao; 
	
	@Override
	public long insert(MrqsGrupoLinesDto pMrqsGrupoLinesDto) {
		return mrqsGrupoLinesDao.insert(pMrqsGrupoLinesDto);
	}

	@Override
	public List<MrqsGrupoLines> findByNumeroHdr(long pNumeroHdr) {
		List<MrqsGrupoLines> retval = new ArrayList<MrqsGrupoLines>(); 
		List<MrqsGrupoLinesDto> listMrqsGrupoLinesDto = mrqsGrupoLinesDao.findByNumeroHdr(pNumeroHdr); 
		for(MrqsGrupoLinesDto mrqsGrupoLinesDto:listMrqsGrupoLinesDto) {
			MrqsGrupoLines mrqsGrupoLines = new MrqsGrupoLines(); 
			mrqsGrupoLines.setNumero(mrqsGrupoLinesDto.getNumero());
			mrqsGrupoLines.setNumeroHdr(mrqsGrupoLinesDto.getNumeroHdr());
			mrqsGrupoLines.setNumeroPregunta(mrqsGrupoLinesDto.getNumeroPregunta());
			retval.add(mrqsGrupoLines); 
		}
		return retval;
	}

	@Override
	public List<MrqsPreguntasHdrV1> findByNumeroHdrWD(long pNumeroHdr) {
		List<MrqsPreguntasHdrV1> retval = new ArrayList<MrqsPreguntasHdrV1>(); 
		List<MrqsGrupoLinesV1Dto> listMrqsGrupoLinesV1Dto = mrqsGrupoLinesDao.findByNumeroHdrWD(pNumeroHdr);
		for(MrqsGrupoLinesV1Dto mrqsGrupoLinesV1Dto:listMrqsGrupoLinesV1Dto) {
			MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = new MrqsPreguntasHdrV1(); 
			mrqsPreguntasHdrV1.setTitulo(mrqsGrupoLinesV1Dto.getTituloPregunta());
			mrqsPreguntasHdrV1.setNumero(mrqsGrupoLinesV1Dto.getNumero());
			mrqsPreguntasHdrV1.setTipoPregunta(mrqsGrupoLinesV1Dto.getTipoPregunta());
			mrqsPreguntasHdrV1.setTipoPreguntaDesc(mrqsGrupoLinesV1Dto.getTipoPreguntaDesc());
			mrqsPreguntasHdrV1.setTemaPregunta("AdmonSubMateria");
			mrqsPreguntasHdrV1.setTemaPreguntaDesc("AdmonSubMateriaDesc");
			mrqsPreguntasHdrV1.setEstatus(mrqsGrupoLinesV1Dto.getEstatus());
			mrqsPreguntasHdrV1.setEstatusDesc(mrqsGrupoLinesV1Dto.getEstatusDesc());
			mrqsPreguntasHdrV1.setDiagnostico(mrqsGrupoLinesV1Dto.getDiagnostico());
			mrqsPreguntasHdrV1.setAdmonSubmateria(mrqsGrupoLinesV1Dto.getAdmonSubmateria());
			mrqsPreguntasHdrV1.setAdmonSubmateriaDesc(mrqsGrupoLinesV1Dto.getAdmonSubmateriaDesc());
			retval.add(mrqsPreguntasHdrV1); 
		}
		return retval;
	}

	@Override
	public List<MrqsGrupoLinesV2> findByNumeroHdrWDV2(long pNumeroHdr) {
		List<MrqsGrupoLinesV2> retval = new ArrayList<MrqsGrupoLinesV2>(); 
		List<MrqsGrupoLinesV2Dto> listMrqsGrupoLinesV2Dto = mrqsGrupoLinesDao.findByNumeroHdrWDV2(pNumeroHdr);
		for(MrqsGrupoLinesV2Dto mrqsGrupoLinesV2Dto:listMrqsGrupoLinesV2Dto) {
			MrqsGrupoLinesV2 mrqsGrupoLinesV2 = fromMglV2DtoMrqsPreguntasHdrV2(mrqsGrupoLinesV2Dto);
			retval.add(mrqsGrupoLinesV2); 
		}
		return retval;
	}

	@Override
	public void delete(long pNumero) {
		mrqsGrupoLinesDao.delete(pNumero);	
	}

	@Override
	public MrqsGrupoLinesV2 findByNumeroV2(long pNumero) {
		MrqsGrupoLinesV2Dto mrqsGrupoLinesV2Dto = mrqsGrupoLinesDao.findByNumeroV2(pNumero);
		MrqsGrupoLinesV2 mrqsPreguntasHdrV2 =  fromMglV2DtoMrqsPreguntasHdrV2(mrqsGrupoLinesV2Dto);
		return mrqsPreguntasHdrV2;
	}

    public MrqsGrupoLinesV2 fromMglV2DtoMrqsPreguntasHdrV2(MrqsGrupoLinesV2Dto pMrqsGrupoLinesV2Dto) {
    	MrqsGrupoLinesV2 retval = new MrqsGrupoLinesV2(); 
    	
    	retval.setTitulo("AdmonMateria");
    	retval.setNumero(pMrqsGrupoLinesV2Dto.getNumero());
    	retval.setNumeroHdr(pMrqsGrupoLinesV2Dto.getNumeroHdr());
    	retval.setNumeroPregunta(pMrqsGrupoLinesV2Dto.getNumeroPregunta());
    	retval.setTipoPregunta(pMrqsGrupoLinesV2Dto.getTipoPregunta());
    	retval.setTipoPreguntaDesc(pMrqsGrupoLinesV2Dto.getTipoPreguntaDesc());
    	retval.setTemaPregunta("AdmonSubMateria");
    	retval.setTemaPreguntaDesc("AdmonSubMateriaDesc");
    	retval.setEstatus(pMrqsGrupoLinesV2Dto.getEstatus());
    	retval.setEstatusDesc(pMrqsGrupoLinesV2Dto.getEstatusDesc());
    	retval.setDiagnostico(pMrqsGrupoLinesV2Dto.getDiagnostico());
    	retval.setTextoPregunta(pMrqsGrupoLinesV2Dto.getTextoPregunta());
    	retval.setTextoSugerencias(pMrqsGrupoLinesV2Dto.getTextoSugerencias());
    	retval.setSingleAnswerMode(pMrqsGrupoLinesV2Dto.isSingleAnswerMode());
    	retval.setSuffleAnswerOrder(pMrqsGrupoLinesV2Dto.isSuffleAnswerOrder());
		
    	return retval; 
    }
	

}
