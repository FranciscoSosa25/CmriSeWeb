package com.cmrise.jpa.dao.dcm;

import com.cmrise.jpa.dto.dcm.DcmEquipmentDto;

public interface DcmEquipmentDao {
	public long insert(DcmEquipmentDto pDcmEquipmentDto); 
	public void delete(DcmEquipmentDto pDcmEquipmentDto); 
	public void deleteById(long pId); 
	public DcmEquipmentDto findById(long pId); 
	public DcmEquipmentDto update(DcmEquipmentDto pDcmEquipmentDto);
}
