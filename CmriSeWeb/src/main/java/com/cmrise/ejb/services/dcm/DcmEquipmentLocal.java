package com.cmrise.ejb.services.dcm;

import javax.ejb.Local;

import com.cmrise.jpa.dto.dcm.DcmEquipmentDto;

@Local
public interface DcmEquipmentLocal {
	public long insert(DcmEquipmentDto pDcmEquipmentDto); 
	public void delete(DcmEquipmentDto pDcmEquipmentDto); 
	public void deleteById(long pId); 
	public DcmEquipmentDto findById(long pId); 
	public DcmEquipmentDto update(DcmEquipmentDto pDcmEquipmentDto);
}
