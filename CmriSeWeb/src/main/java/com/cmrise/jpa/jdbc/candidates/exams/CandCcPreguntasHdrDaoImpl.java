package com.cmrise.jpa.jdbc.candidates.exams;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.candidates.exams.CandCcPreguntasHdrDao;
import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CandCcPreguntasHdrDaoImpl implements CandCcPreguntasHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CandCcPreguntasHdrDto pCandCcPreguntasHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CAND_CC_PREGUNTAS_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCandCcPreguntasHdrDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCandCcPreguntasHdrDto.setCreadoPor((long)-1);
		pCandCcPreguntasHdrDto.setActualizadoPor((long)-1);
		pCandCcPreguntasHdrDto.setFechaCreacion(sqlsysdate);
		pCandCcPreguntasHdrDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCandCcPreguntasHdrDto);
		return lNumeroS.longValue(); 
	}

}
