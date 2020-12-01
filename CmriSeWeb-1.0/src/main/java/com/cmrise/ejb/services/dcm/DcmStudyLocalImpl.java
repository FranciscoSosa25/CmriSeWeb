package com.cmrise.ejb.services.dcm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.dcm.DcmStudyDao;
import com.cmrise.jpa.dto.dcm.DcmStudyDto;

@Stateless
public class DcmStudyLocalImpl implements DcmStudyLocal {

	@Inject 
	DcmStudyDao dcmStudyDao; 
	
	@Override
	public long insert(DcmStudyDto pDcmStudyDto) {
		return dcmStudyDao.insert(pDcmStudyDto);
	}

	@Override
	public void delete(DcmStudyDto pDcmStudyDto) {
		dcmStudyDao.delete(pDcmStudyDto);
	}

	@Override
	public void deleteById(long pId) {
		dcmStudyDao.deleteById(pId);
	}

	@Override
	public DcmStudyDto findById(long pId) {
		return dcmStudyDao.findById(pId);
	}

	@Override
	public DcmStudyDto update(DcmStudyDto pDcmStudyDto) {
		return dcmStudyDao.update(pDcmStudyDto);
	}

}
