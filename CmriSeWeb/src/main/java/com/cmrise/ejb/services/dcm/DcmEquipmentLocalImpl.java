package com.cmrise.ejb.services.dcm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.jpa.dao.dcm.DcmEquipmentDao;
import com.cmrise.jpa.dto.dcm.DcmEquipmentDto;

@Stateless 
public class DcmEquipmentLocalImpl implements DcmEquipmentLocal {

	@Inject 
	DcmEquipmentDao dcmEquipmentDao; 
	
	@Override
	public long insert(DcmEquipmentDto pDcmEquipmentDto) {
		return dcmEquipmentDao.insert(pDcmEquipmentDto);
	}

	@Override
	public void delete(DcmEquipmentDto pDcmEquipmentDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(long pId) {
		// TODO Auto-generated method stub

	}

	@Override
	public DcmEquipmentDto findById(long pId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DcmEquipmentDto update(DcmEquipmentDto pDcmEquipmentDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
