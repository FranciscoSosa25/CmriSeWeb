package com.cmrise.jpa.dao.dcm;

import com.cmrise.jpa.dto.dcm.DcmPatientDto;

public interface DcmPatientDao {

	public long insert(DcmPatientDto pDcmPatientDto); 
	public void delete(DcmPatientDto pDcmPatientDto); 
	public void deleteById(long pId); 
	public DcmPatientDto findById(long pId); 
	public DcmPatientDto update(DcmPatientDto pDcmPatientDto);
}
