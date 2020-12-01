package com.cmrise.ejb.services.dcm;

import javax.ejb.Local;

import com.cmrise.jpa.dto.dcm.DcmPatientDto;

@Local
public interface DcmPatientLocal {
	public long insert(DcmPatientDto pDcmPatientDto); 
	public void delete(DcmPatientDto pDcmPatientDto); 
	public void deleteById(long pId); 
	public DcmPatientDto findById(long pId); 
	public DcmPatientDto update(DcmPatientDto pDcmPatientDto);
}
