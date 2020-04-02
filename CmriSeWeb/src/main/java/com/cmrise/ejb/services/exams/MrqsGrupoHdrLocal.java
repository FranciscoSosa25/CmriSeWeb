package com.cmrise.ejb.services.exams;

import javax.ejb.Local;
import java.util.List;

import com.cmrise.ejb.model.exams.MrqsGrupoHdr;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;

@Local
public interface MrqsGrupoHdrLocal {
	public long insert(MrqsGrupoHdrDto pMrqsGrupoHdrDto); 
	public List<MrqsGrupoHdr> findByNumeroExamen(long pNumeroExamen); 
	public MrqsGrupoHdrDto findByNumero(long pNumero); 
	public void delete(long pNumero); 
	public void update(long pNumero, MrqsGrupoHdrDto pMrqsGrupoHdrDto); 
}
