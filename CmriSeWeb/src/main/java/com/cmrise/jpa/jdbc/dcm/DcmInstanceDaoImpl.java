package com.cmrise.jpa.jdbc.dcm;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.cmrise.jpa.dao.dcm.DcmInstanceDao;
import com.cmrise.jpa.dto.dcm.DcmInstanceDto;

@Stateless
public class DcmInstanceDaoImpl extends AbstractJpaDao<DcmInstanceDto> implements DcmInstanceDao {

	public DcmInstanceDaoImpl() {
		super();
		super.setClazz(DcmInstanceDto.class);
	}
	
	@Override
	public long insert(DcmInstanceDto pDcmInstanceDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.DCM_INSTANCE_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pDcmInstanceDto.setId(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pDcmInstanceDto.setCreatedDate(sqlsysdate);
		pDcmInstanceDto.setModifiedDate(sqlsysdate);
		super.em.persist(pDcmInstanceDto);
		return lNumeroS.longValue();
	}

}
