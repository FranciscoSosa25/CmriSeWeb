package com.cmrise.jpa.jdbc.candidates.exams;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.candidates.exams.CandCcPreguntasFtaDao;
import com.cmrise.jpa.dto.candidates.exams.CandCcPreguntasFtaDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CandCcPreguntasFtaDaoImpl implements CandCcPreguntasFtaDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	
	@Override
	public long insert(CandCcPreguntasFtaDto pCandCcPreguntasFtaDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CAND_CC_PREGUNTAS_FTA_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCandCcPreguntasFtaDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCandCcPreguntasFtaDto.setCreadoPor((long)-1);
		pCandCcPreguntasFtaDto.setActualizadoPor((long)-1);
		pCandCcPreguntasFtaDto.setFechaCreacion(sqlsysdate);
		pCandCcPreguntasFtaDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCandCcPreguntasFtaDto);
		return lNumeroS.longValue(); 
	}


	@Override
	public void update(long pNumero, String pRespuesta) {
		System.out.println("CandCcPreguntasFtaDaoImpl pNumero:"+pNumero);
		CandCcPreguntasFtaDto candCcPreguntasFtaDto = em.find(CandCcPreguntasFtaDto.class, pNumero); 
		candCcPreguntasFtaDto.setRespuesta(pRespuesta);
	}

	
	
}
