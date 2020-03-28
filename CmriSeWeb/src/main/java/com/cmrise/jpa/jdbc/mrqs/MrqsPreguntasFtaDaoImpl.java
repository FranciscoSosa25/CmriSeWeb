package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsPreguntasFtaDaoImpl implements MrqsPreguntasFtaDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_PREGUNTAS_FTA_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsPreguntasFtaDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsPreguntasFtaDto.setCreadoPor((long)-1);
		pMrqsPreguntasFtaDto.setActualizadoPor((long)-1);
		pMrqsPreguntasFtaDto.setFechaCreacion(sqlsysdate);
		pMrqsPreguntasFtaDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsPreguntasFtaDto);
		return lNumeroS.longValue();
	}

	@Override
	public void delete(long pNumero) {
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = em.find(MrqsPreguntasFtaDto.class, pNumero);
		em.remove(mrqsPreguntasFtaDto);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasFtaDto pMrqsPreguntasFtaDto) {
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = em.find(MrqsPreguntasFtaDto.class, pNumero);
		mrqsPreguntasFtaDto.setMrqsPreguntasHdr2(pMrqsPreguntasFtaDto.getMrqsPreguntasHdr2());
		mrqsPreguntasFtaDto.setTitulo(pMrqsPreguntasFtaDto.getTitulo());
		mrqsPreguntasFtaDto.setMetodoPuntuacion(pMrqsPreguntasFtaDto.getMetodoPuntuacion());
		mrqsPreguntasFtaDto.setRespuestaCorrecta(pMrqsPreguntasFtaDto.getRespuestaCorrecta());
		mrqsPreguntasFtaDto.setTextoPregunta(pMrqsPreguntasFtaDto.getTextoPregunta());
		mrqsPreguntasFtaDto.setValorPuntuacion(pMrqsPreguntasFtaDto.getValorPuntuacion());
		mrqsPreguntasFtaDto.setTextoSugerencias(pMrqsPreguntasFtaDto.getTextoSugerencias());
		mrqsPreguntasFtaDto.setSingleAnswerMode(pMrqsPreguntasFtaDto.isSingleAnswerMode());
		mrqsPreguntasFtaDto.setSuffleAnswerOrder(pMrqsPreguntasFtaDto.isSuffleAnswerOrder());
		mrqsPreguntasFtaDto.setMetodoPuntuacion(pMrqsPreguntasFtaDto.getMetodoPuntuacion());
		mrqsPreguntasFtaDto.setValorPuntuacion(pMrqsPreguntasFtaDto.getValorPuntuacion());
	}

	@Override
	public long findNumeroFtaByNumeroHdr(long pNumeroHdr) {
		String strQuery = "SELECT [NUMERO]\r" + 
						  "  FROM [dbo].[MRQS_PREGUNTAS_FTA]\r" + 
						  " WHERE [NUMERO_HDR] = "+pNumeroHdr;
		Query query = em.createNativeQuery(strQuery); 
		long retval = 0l; 
		try {
		Object object = query.getSingleResult();
		if(object instanceof BigInteger) {
			retval = ((BigInteger)object).longValue(); 
		}
		}catch(NoResultException nre) {
			   System.out.println(this.getClass()+":No entity found for query");
		}
		
		return retval;
	}

	@Override
	public MrqsPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta) {
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = em.find(MrqsPreguntasFtaDto.class, pNumeroFta); 
		return mrqsPreguntasFtaDto;
	}

	@Override
	public long copyPaste(long pNumero,MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		MrqsPreguntasFtaDto copy = em.find(MrqsPreguntasFtaDto.class, pNumero);
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_PREGUNTAS_FTA_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		MrqsPreguntasFtaDto mrqsPreguntasFtaDto = new MrqsPreguntasFtaDto();
		mrqsPreguntasFtaDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		mrqsPreguntasFtaDto.setCreadoPor((long)-1);
		mrqsPreguntasFtaDto.setActualizadoPor((long)-1);
		mrqsPreguntasFtaDto.setFechaCreacion(sqlsysdate);
		mrqsPreguntasFtaDto.setFechaActualizacion(sqlsysdate);
		
		mrqsPreguntasFtaDto.setMrqsPreguntasHdr2(pMrqsPreguntasHdrDto);
		mrqsPreguntasFtaDto.setTitulo(copy.getTitulo());
		mrqsPreguntasFtaDto.setTextoPregunta(copy.getTextoPregunta());
		mrqsPreguntasFtaDto.setTextoSugerencias(copy.getTextoSugerencias());
		mrqsPreguntasFtaDto.setMetodoPuntuacion(copy.getMetodoPuntuacion());
		mrqsPreguntasFtaDto.setValorPuntuacion(copy.getValorPuntuacion());
		mrqsPreguntasFtaDto.setRespuestaCorrecta(copy.getRespuestaCorrecta());
		mrqsPreguntasFtaDto.setFechaEfectivaDesde(copy.getFechaEfectivaDesde());
		mrqsPreguntasFtaDto.setFechaEfectivaHasta(copy.getFechaEfectivaHasta());
		em.persist(mrqsPreguntasFtaDto);
		return lNumeroS.longValue();
	}

}
