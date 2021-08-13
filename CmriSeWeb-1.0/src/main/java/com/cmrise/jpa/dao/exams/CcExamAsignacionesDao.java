package com.cmrise.jpa.dao.exams;

import java.util.List;

import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;

public interface CcExamAsignacionesDao {

	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto);
	public List<Object> findByNumeroExamenWD(long pNumeroExamen, long nNumeroCC);
	public List<CcExamAsignacionesDto> findByNumeroExamenDtos(long pNumeroCcExamen);
	public void delete(long pNumero);
	public List<Object> findByNumeroExamen(long pNumeroExamen);
	
}
