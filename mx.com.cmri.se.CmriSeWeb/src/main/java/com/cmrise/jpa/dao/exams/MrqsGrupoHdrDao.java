package com.cmrise.jpa.dao.exams;

import java.util.List;

import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrV1Dto;

public interface MrqsGrupoHdrDao {

	public long insert(MrqsGrupoHdrDto pMrqsGrupoHdrDto); 
	public List<MrqsGrupoHdrDto> findByNumeroExamen(long pNumeroExamen); 
	public MrqsGrupoHdrDto findByNumero(long pNumero);
	public void delete(long pNumero); 
	public void update(long pNumero,MrqsGrupoHdrDto pMrqsGrupoHdrDto);
	public List<MrqsGrupoHdrV1Dto> findByNumeroExamenWD(long pNumero);
	public MrqsGrupoHdrV1Dto findByNumeroWD(long pNumeroMrqsGrupo); 
	
	
}
