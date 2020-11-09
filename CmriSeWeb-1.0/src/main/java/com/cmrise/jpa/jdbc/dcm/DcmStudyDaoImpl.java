package com.cmrise.jpa.jdbc.dcm;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.cmrise.jpa.dao.dcm.DcmStudyDao;
import com.cmrise.jpa.dto.dcm.DcmStudyDto;

@Stateless
public class DcmStudyDaoImpl extends AbstractJpaDao<DcmStudyDto> implements DcmStudyDao {

	@Override
	public long insert(DcmStudyDto pDcmStudyDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.DCM_STUDY_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pDcmStudyDto.setId(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pDcmStudyDto.setCreatedDate(sqlsysdate);
		pDcmStudyDto.setModifiedDate(sqlsysdate);
		super.em.persist(pDcmStudyDto);
		return lNumeroS.longValue();
	}

}
