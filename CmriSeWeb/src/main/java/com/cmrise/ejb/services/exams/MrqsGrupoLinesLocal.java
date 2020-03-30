package com.cmrise.ejb.services.exams;

import javax.ejb.Local;
import java.util.List;

import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;

@Local
public interface MrqsGrupoLinesLocal {
	public long insert(MrqsGrupoLinesDto pMrqsGrupoLinesDto);
	public List<MrqsGrupoLines> findByNumeroHdr(long pNumeroHdr); 
}
