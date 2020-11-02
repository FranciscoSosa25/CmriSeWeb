package com.cmrise.ejb.services.dcm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.dcm.DcmInstanceDao;
import com.cmrise.jpa.dto.dcm.DcmInstanceDto;

@Stateless 
public class DcmInstanceLocalImpl implements DcmInstanceLocal {

	@Inject
	DcmInstanceDao dcmInstanceDao; 
	
	@Override
	public long insert(DcmInstanceDto pDcmInstanceDto) {
		return dcmInstanceDao.insert(pDcmInstanceDto);
	}

	@Override
	public void delete(DcmInstanceDto pDcmInstanceDto) {
		dcmInstanceDao.delete(pDcmInstanceDto);
	}

	@Override
	public void deleteById(long pId) {
		dcmInstanceDao.deleteById(pId);
	}

	@Override
	public DcmInstanceDto findById(long pId) {
		return dcmInstanceDao.findById(pId); 
	}

	@Override
	public DcmInstanceDto update(DcmInstanceDto pDcmInstanceDto) {
	   return dcmInstanceDao.update(pDcmInstanceDto); 
	}

}
