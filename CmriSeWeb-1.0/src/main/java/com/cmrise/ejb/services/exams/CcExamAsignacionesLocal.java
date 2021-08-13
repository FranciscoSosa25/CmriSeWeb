package com.cmrise.ejb.services.exams;

import java.util.List;

import javax.ejb.Local;

import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;

@Local
public interface CcExamAsignacionesLocal {
	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto);
	public List<CcExamAsignaciones> findByNumeroExamenWD(long pNumeroExamen, long nNumeroCC);
	public List<CcExamAsignaciones> findByNumeroExamenObjMod(long pNumeroExamen);
	public void delete(long pNumero);
	public List<CcExamAsignaciones> findByNumeroExamen(long pNumeroExamen);
}
