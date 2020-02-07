package com.cmrise.ejb.services.dcm;

import javax.ejb.Local;

import com.cmrise.jpa.dto.dcm.DcmInstanceDto;

@Local
public interface DcmInstanceLocal {
	public long insert(DcmInstanceDto pDcmInstanceDto); 
	public void delete(DcmInstanceDto pDcmInstanceDto); 
	public void deleteById(long pId); 
	public DcmInstanceDto findById(long pId); 
	public DcmInstanceDto update(DcmInstanceDto pDcmInstanceDto);
}
