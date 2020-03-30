package com.cmrise.jpa.dao.exams;

import java.util.List;

import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;

public interface MrqsGrupoLinesDao {

	public long insert(MrqsGrupoLinesDto pMrqsGrupoLinesDto); 
	public List<MrqsGrupoLinesDto> findByNumeroHdr(long pNumeroHdr); 
	
}
