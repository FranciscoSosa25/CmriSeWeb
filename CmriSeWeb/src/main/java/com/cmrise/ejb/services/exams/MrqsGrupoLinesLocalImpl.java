package com.cmrise.ejb.services.exams;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.jpa.dao.exams.MrqsGrupoLinesDao;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;

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

}
