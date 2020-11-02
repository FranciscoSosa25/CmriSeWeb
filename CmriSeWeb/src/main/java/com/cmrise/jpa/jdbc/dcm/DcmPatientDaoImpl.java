package com.cmrise.jpa.jdbc.dcm;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.cmrise.jpa.dao.dcm.DcmPatientDao;
import com.cmrise.jpa.dto.dcm.DcmPatientDto;

@Stateless
public class DcmPatientDaoImpl extends AbstractJpaDao<DcmPatientDto> implements DcmPatientDao {

	public DcmPatientDaoImpl() {
		super();
		super.setClazz(DcmPatientDto.class);
	}

	@Override
	public long insert(DcmPatientDto pDcmPatientDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.DCM_PATIENT_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pDcmPatientDto.setId(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pDcmPatientDto.setCreatedDate(sqlsysdate);
		pDcmPatientDto.setModifiedDate(sqlsysdate);
		super.em.persist(pDcmPatientDto);
		return lNumeroS.longValue();
	}

}
