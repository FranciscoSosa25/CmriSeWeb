package com.cmrise.jpa.dao.dcm;

import com.cmrise.jpa.dto.dcm.DcmSeriesDto;

public interface DcmSeriesDao {
	public long insert(DcmSeriesDto pDcmSeriesDto); 
	public void delete(DcmSeriesDto pDcmSeriesDto); 
	public void deleteById(long pId); 
	public DcmSeriesDto findById(long pId); 
	public DcmSeriesDto update(DcmSeriesDto pDcmSeriesDto);
}
