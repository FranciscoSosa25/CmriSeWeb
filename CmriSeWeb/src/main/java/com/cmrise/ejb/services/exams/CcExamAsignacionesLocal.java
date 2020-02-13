package com.cmrise.ejb.services.exams;

import javax.ejb.Local;

import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;

@Local
public interface CcExamAsignacionesLocal {
	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto);
}
