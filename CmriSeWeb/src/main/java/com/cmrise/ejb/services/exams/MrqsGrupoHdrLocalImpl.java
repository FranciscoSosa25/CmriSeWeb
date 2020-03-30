package com.cmrise.ejb.services.exams;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.jpa.dao.exams.MrqsGrupoHdrDao;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;

@Stateless
public class MrqsGrupoHdrLocalImpl implements MrqsGrupoHdrLocal {

	@Inject 
	MrqsGrupoHdrDao mrqsGrupoHdrDao; 
	
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
			mrqsGrupoHdr.setTitulo(mrqsGrupoHdrDto.getTitulo());
			retval.add(mrqsGrupoHdr); 
		}
		return retval;
	}

}
