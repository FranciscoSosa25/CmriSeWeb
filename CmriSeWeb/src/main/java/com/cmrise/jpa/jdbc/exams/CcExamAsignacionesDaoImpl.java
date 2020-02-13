package com.cmrise.jpa.jdbc.exams;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.exams.CcExamAsignacionesDao;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcExamAsignacionesDaoImpl implements CcExamAsignacionesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_EXAM_ASSIGNACIONES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pCcExamAsignacionesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcExamAsignacionesDto.setCreadoPor((long)-1);
		pCcExamAsignacionesDto.setActualizadoPor((long)-1);
		pCcExamAsignacionesDto.setFechaCreacion(sqlsysdate);
		pCcExamAsignacionesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcExamAsignacionesDto);
		return longNumeroS; 
	}

}
