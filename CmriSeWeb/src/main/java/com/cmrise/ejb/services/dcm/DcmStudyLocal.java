package com.cmrise.ejb.services.dcm;

import javax.ejb.Local;

import com.cmrise.jpa.dto.dcm.DcmStudyDto;

@Local
public interface DcmStudyLocal {
	public long insert(DcmStudyDto pDcmStudyDto); 
	public void delete(DcmStudyDto pDcmStudyDto); 
	public void deleteById(long pId); 
	public DcmStudyDto findById(long pId); 
	public DcmStudyDto update(DcmStudyDto pDcmStudyDto);
}
