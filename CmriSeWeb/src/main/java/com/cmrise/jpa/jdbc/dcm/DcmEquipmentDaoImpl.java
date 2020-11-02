package com.cmrise.jpa.jdbc.dcm;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.Query;
import com.cmrise.jpa.dao.dcm.DcmEquipmentDao;
import com.cmrise.jpa.dto.dcm.DcmEquipmentDto;

@Stateless
public class DcmEquipmentDaoImpl  extends AbstractJpaDao<DcmEquipmentDto> implements DcmEquipmentDao{
	
	public DcmEquipmentDaoImpl() {
		super();
		super.setClazz(DcmEquipmentDto.class);
	}
	
	@Override
	public long insert(DcmEquipmentDto pDcmEquipmentDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.DCM_EQUIPMENT_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pDcmEquipmentDto.setId(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pDcmEquipmentDto.setCreatedDate(sqlsysdate);
		pDcmEquipmentDto.setModifiedDate(sqlsysdate);
		em.persist(pDcmEquipmentDto);
		return lNumeroS.longValue();
	}

}
