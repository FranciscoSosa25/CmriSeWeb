package com.cmrise.jpa.dao.dcm;

import com.cmrise.jpa.dto.dcm.DcmStudyDto;

public interface DcmStudyDao {
	public long insert(DcmStudyDto pDcmStudyDto); 
	public void delete(DcmStudyDto pDcmStudyDto); 
	public void deleteById(long pId); 
	public DcmStudyDto findById(long pId); 
	public DcmStudyDto update(DcmStudyDto pDcmStudyDto);
}
