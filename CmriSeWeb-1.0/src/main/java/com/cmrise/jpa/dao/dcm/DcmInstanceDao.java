package com.cmrise.jpa.dao.dcm;

import com.cmrise.jpa.dto.dcm.DcmInstanceDto;

public interface DcmInstanceDao {
	public long insert(DcmInstanceDto pDcmInstanceDto); 
	public void delete(DcmInstanceDto pDcmInstanceDto); 
	public void deleteById(long pId); 
	public DcmInstanceDto findById(long pId); 
	public DcmInstanceDto update(DcmInstanceDto pDcmInstanceDto);
}
