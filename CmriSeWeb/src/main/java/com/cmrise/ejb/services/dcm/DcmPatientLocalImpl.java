package com.cmrise.ejb.services.dcm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.dcm.DcmPatientDao;
import com.cmrise.jpa.dto.dcm.DcmPatientDto;

@Stateless 
public class DcmPatientLocalImpl implements DcmPatientLocal {

	@Inject 
	DcmPatientDao dcmPatientDao; 
	
	@Override
	public long insert(DcmPatientDto pDcmPatientDto) {
		return dcmPatientDao.insert(pDcmPatientDto);
	}

	@Override
	public void delete(DcmPatientDto pDcmPatientDto) {
		dcmPatientDao.delete(pDcmPatientDto);
	}

	@Override
	public void deleteById(long pId) {
		dcmPatientDao.deleteById(pId);
	}

	@Override
	public DcmPatientDto findById(long pId) {
		return dcmPatientDao.findById(pId);
	}

	@Override
	public DcmPatientDto update(DcmPatientDto pDcmPatientDto) {
	  return dcmPatientDao.update(pDcmPatientDto); 
	}

}
