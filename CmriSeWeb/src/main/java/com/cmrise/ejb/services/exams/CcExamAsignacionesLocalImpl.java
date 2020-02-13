package com.cmrise.ejb.services.exams;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.exams.CcExamAsignacionesDao;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;

@Stateless
public class CcExamAsignacionesLocalImpl implements CcExamAsignacionesLocal {

	@Inject 
	CcExamAsignacionesDao ccExamAsignacionesDao; 
	
	@Override
	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto) {
		return ccExamAsignacionesDao.insert(pCcExamAsignacionesDto);
	}

}
