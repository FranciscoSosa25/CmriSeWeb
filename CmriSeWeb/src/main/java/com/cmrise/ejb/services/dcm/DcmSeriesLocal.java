package com.cmrise.ejb.services.dcm;

import javax.ejb.Local;

import com.cmrise.jpa.dto.dcm.DcmSeriesDto;

@Local
public interface DcmSeriesLocal {
	public long insert(DcmSeriesDto pDcmSeriesDto); 
	public void delete(DcmSeriesDto pDcmSeriesDto); 
	public void deleteById(long pId); 
	public DcmSeriesDto findById(long pId); 
	public DcmSeriesDto update(DcmSeriesDto pDcmSeriesDto);
}
