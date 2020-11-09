package com.cmrise.ejb.services.dcm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.dcm.DcmSeriesDao;
import com.cmrise.jpa.dto.dcm.DcmSeriesDto;

@Stateless 
public class DcmSeriesLocalImpl implements DcmSeriesLocal {

	@Inject 
	DcmSeriesDao dcmSeriesDao; 
	
	@Override
	public long insert(DcmSeriesDto pDcmSeriesDto) {
		return dcmSeriesDao.insert(pDcmSeriesDto);
	}

	@Override
	public void delete(DcmSeriesDto pDcmSeriesDto) {
		dcmSeriesDao.delete(pDcmSeriesDto);
	}

	@Override
	public void deleteById(long pId) {
		dcmSeriesDao.deleteById(pId);
	}

	@Override
	public DcmSeriesDto findById(long pId) {
		return dcmSeriesDao.findById(pId);
	}

	@Override
	public DcmSeriesDto update(DcmSeriesDto pDcmSeriesDto) {
		return dcmSeriesDao.update(pDcmSeriesDto);
	}

}
