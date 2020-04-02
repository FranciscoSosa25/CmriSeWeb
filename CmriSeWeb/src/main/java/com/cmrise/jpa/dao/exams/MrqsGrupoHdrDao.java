package com.cmrise.jpa.dao.exams;

import java.util.List;

import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;

public interface MrqsGrupoHdrDao {

	public long insert(MrqsGrupoHdrDto pMrqsGrupoHdrDto); 
	public List<MrqsGrupoHdrDto> findByNumeroExamen(long pNumeroExamen); 
	public MrqsGrupoHdrDto findByNumero(long pNumero);
	public void delete(long pNumero); 
	public void update(long pNumero,MrqsGrupoHdrDto pMrqsGrupoHdrDto); 
}
