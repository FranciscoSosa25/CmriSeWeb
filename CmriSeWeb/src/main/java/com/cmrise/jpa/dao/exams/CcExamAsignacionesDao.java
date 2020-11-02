package com.cmrise.jpa.dao.exams;

import java.util.List;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;

public interface CcExamAsignacionesDao {

	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto);
	public List<Object> findByNumeroExamenWD(long pNumeroExamen);
	public List<CcExamAsignacionesDto> findByNumeroExamenDtos(long pNumeroCcExamen);

}
