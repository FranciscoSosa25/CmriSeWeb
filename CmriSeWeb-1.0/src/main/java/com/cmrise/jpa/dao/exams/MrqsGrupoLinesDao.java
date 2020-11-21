package com.cmrise.jpa.dao.exams;

import java.util.List;

import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesV1Dto;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesV2Dto;

public interface MrqsGrupoLinesDao {

	public long insert(MrqsGrupoLinesDto pMrqsGrupoLinesDto); 
	public List<MrqsGrupoLinesDto> findByNumeroHdr(long pNumeroHdr); 
	public List<MrqsGrupoLinesV1Dto> findByNumeroHdrWD(long pNumeroHdr); /** Find By Numero With Descriptions **/
	public List<MrqsGrupoLinesV2Dto> findByNumeroHdrWDV2(long pNumeroHdr); /** Find By Numero With Descriptions **/
	public void delete(long pNumero);
	public void delete(MrqsGrupoLinesDto pMrqsGrupoLinesDto);
    public MrqsGrupoLinesV2Dto findByNumeroV2(long pNumero);
}  

