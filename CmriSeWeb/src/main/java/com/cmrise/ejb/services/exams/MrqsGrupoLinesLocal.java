package com.cmrise.ejb.services.exams;

import javax.ejb.Local;
import java.util.List;

import com.cmrise.ejb.model.exams.MrqsGrupoLines;
import com.cmrise.ejb.model.exams.MrqsGrupoLinesV2;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV2;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;

@Local
public interface MrqsGrupoLinesLocal {
	public long insert(MrqsGrupoLinesDto pMrqsGrupoLinesDto);
	public List<MrqsGrupoLines> findByNumeroHdr(long pNumeroHdr); 
	public List<MrqsPreguntasHdrV1> findByNumeroHdrWD(long pNumeroHdr); /** Find By Numero With Descriptions **/
	public void delete(long pNumero); 
	public List<MrqsGrupoLinesV2> findByNumeroHdrWDV2(long pNumeroHdr); /** Find By Numero With Descriptions **/
	public MrqsGrupoLinesV2 findByNumeroV2(long pNumero); 
}
