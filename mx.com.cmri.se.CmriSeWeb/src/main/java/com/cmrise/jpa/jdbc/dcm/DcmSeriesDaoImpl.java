package com.cmrise.jpa.jdbc.dcm;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.cmrise.jpa.dao.dcm.DcmSeriesDao;
import com.cmrise.jpa.dto.dcm.DcmSeriesDto;

@Stateless
public class DcmSeriesDaoImpl extends AbstractJpaDao<DcmSeriesDto> implements DcmSeriesDao {

	@Override
	public long insert(DcmSeriesDto pDcmSeriesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.DCM_SERIES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pDcmSeriesDto.setId(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pDcmSeriesDto.setCreatedDate(sqlsysdate);
		pDcmSeriesDto.setModifiedDate(sqlsysdate);
		super.em.persist(pDcmSeriesDto);
		return lNumeroS.longValue();
	}

}
