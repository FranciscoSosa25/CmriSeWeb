package com.cmrise.ejb.services.exams;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.jpa.dao.exams.MrqsGrupoHdrDao;
import com.cmrise.jpa.dao.exams.MrqsGrupoLinesDao;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrV1Dto;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsGrupoHdrLocalImpl implements MrqsGrupoHdrLocal {

	@Inject 
	MrqsGrupoHdrDao mrqsGrupoHdrDao; 
	
	@Inject 
	MrqsGrupoLinesDao mrqsGrupoLinesDao; 
	
	@Override
	public long insert(MrqsGrupoHdrDto pMrqsGrupoHdrDto) {
		return mrqsGrupoHdrDao.insert(pMrqsGrupoHdrDto);
	}

	@Override
	public List<MrqsGrupoHdr> findByNumeroExamen(long pNumeroExamen) {
		List<MrqsGrupoHdr> retval = new ArrayList<MrqsGrupoHdr>(); 
		List<MrqsGrupoHdrDto> listMrqsGrupoHdrDto = mrqsGrupoHdrDao.findByNumeroExamen(pNumeroExamen); 
		for(MrqsGrupoHdrDto mrqsGrupoHdrDto:listMrqsGrupoHdrDto) {
			MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr();
			mrqsGrupoHdr.setNumero(mrqsGrupoHdrDto.getNumero());
			mrqsGrupoHdr.setNumeroExamen(mrqsGrupoHdrDto.getNumeroExamen());
			mrqsGrupoHdr.setAdmonMateria(mrqsGrupoHdrDto.getAdmonMateria());
			retval.add(mrqsGrupoHdr); 
		}
		return retval;
	}

	@Override
	public MrqsGrupoHdrDto findByNumero(long pNumero) {
		return mrqsGrupoHdrDao.findByNumero(pNumero);
	}

	@Override
	public void delete(long pNumero) {
		List<MrqsGrupoLinesDto> listMrqsGrupoLinesDto = mrqsGrupoLinesDao.findByNumeroHdr(pNumero); 
		for(MrqsGrupoLinesDto mrqsGrupoLinesDto:listMrqsGrupoLinesDto) {
			mrqsGrupoLinesDao.delete(mrqsGrupoLinesDto);
		}
		mrqsGrupoHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsGrupoHdrDto pMrqsGrupoHdrDto) {
		mrqsGrupoHdrDao.update(pNumero, pMrqsGrupoHdrDto);
	}

	@Override
	public long insert(MrqsGrupoHdr pMrqsGrupoHdrForInsert) {
		MrqsGrupoHdrDto mrqsGrupoHdrDto = new MrqsGrupoHdrDto(); 
		mrqsGrupoHdrDto.setCreadoPor(pMrqsGrupoHdrForInsert.getCreadoPor());
		mrqsGrupoHdrDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pMrqsGrupoHdrForInsert.getFechaCreacion()));
		mrqsGrupoHdrDto.setActualizadoPor(pMrqsGrupoHdrForInsert.getActualizadoPor());
		mrqsGrupoHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pMrqsGrupoHdrForInsert.getFechaActualizacion()));
		mrqsGrupoHdrDto.setNumeroExamen(pMrqsGrupoHdrForInsert.getNumeroExamen());
		mrqsGrupoHdrDto.setAdmonMateria(pMrqsGrupoHdrForInsert.getAdmonMateria());
		mrqsGrupoHdrDao.insert(mrqsGrupoHdrDto); 
		pMrqsGrupoHdrForInsert.setNumero(mrqsGrupoHdrDto.getNumero());
		return mrqsGrupoHdrDto.getNumero();
	}

	@Override
	public MrqsGrupoHdr findByNumeroWD(long pNumeroMrqsGrupo) {
		MrqsGrupoHdr mrqsGrupoHdr = new MrqsGrupoHdr(); 
		MrqsGrupoHdrV1Dto mrqsGrupoHdrV1Dto = mrqsGrupoHdrDao.findByNumeroWD(pNumeroMrqsGrupo); 
		mrqsGrupoHdr.setNumero(mrqsGrupoHdrV1Dto.getNumero());
		mrqsGrupoHdr.setAdmonMateria(mrqsGrupoHdrV1Dto.getAdmonMateria());
		mrqsGrupoHdr.setAdmonMateriaDesc(mrqsGrupoHdrV1Dto.getAdmonMateriaDesc());
		return mrqsGrupoHdr;
	}

}
