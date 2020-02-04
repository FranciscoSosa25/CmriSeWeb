package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcPreguntasFtaDaoImpl implements CcPreguntasFtaDao {
	
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(CcPreguntasFtaDto pCcPreguntasFtaDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_PREGUNTAS_FTA_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcPreguntasFtaDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcPreguntasFtaDto.setCreadoPor((long)-1);
		pCcPreguntasFtaDto.setActualizadoPor((long)-1);
		pCcPreguntasFtaDto.setFechaCreacion(sqlsysdate);
		pCcPreguntasFtaDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcPreguntasFtaDto);
	}

	@Override
	public void delete(long pNumero) {
		CcPreguntasFtaDto ccPreguntasFtaDto = em.find(CcPreguntasFtaDto.class, pNumero);
		em.remove(ccPreguntasFtaDto);
	}

	@Override
	public void update(long pNumero, CcPreguntasFtaDto pCcPreguntasFtaDto) {
		CcPreguntasFtaDto ccPreguntasFtaDto = em.find(CcPreguntasFtaDto.class, pNumero);
		
	}

}
