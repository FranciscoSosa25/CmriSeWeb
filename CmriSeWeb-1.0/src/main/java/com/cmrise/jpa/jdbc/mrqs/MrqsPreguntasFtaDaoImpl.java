package com.cmrise.jpa.jdbc.mrqs;

import java.io.File;
import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaV1Dto;
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
		pMrqsPreguntasFtaDto.setRutaImagen(pMrqsPreguntasFtaDto.getRutaImagen()+ File.separator +lNumeroS);
		pMrqsPreguntasFtaDto.setNumero(lNumeroS.longValue());
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
		mrqsPreguntasFtaDto.setNombreImagen(pMrqsPreguntasFtaDto.getNombreImagen());
		mrqsPreguntasFtaDto.setContentType(pMrqsPreguntasFtaDto.getContentType());
		mrqsPreguntasFtaDto.setPoligonos(pMrqsPreguntasFtaDto.getPoligonos());
		mrqsPreguntasFtaDto.setLimiteCaracteres(pMrqsPreguntasFtaDto.getLimiteCaracteres());
		mrqsPreguntasFtaDto.setRespuestas(pMrqsPreguntasFtaDto.getRespuestas());
		mrqsPreguntasFtaDto.setCorrelaciones(pMrqsPreguntasFtaDto.getCorrelaciones());

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
			   System.out.println(this.getClass()+": findNumeroFtaByNumeroHdr No entity found for query pNumeroHdr:"+pNumeroHdr);
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

	/** could not resolve property: numeroHdr of: com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaDto **/
	@Override
	public MrqsPreguntasFtaDto findDtoByNumeroHdr(long pNumeroHdr) {
		 MrqsPreguntasFtaDto retval = new MrqsPreguntasFtaDto(); 
		long numeroFta = findNumeroFtaByNumeroHdr(pNumeroHdr);  
		if(0!=numeroFta) {
			String strQuery = "SELECT m FROM MrqsPreguntasFtaDto m WHERE m.numero = "+numeroFta; 
		    Query query = em.createQuery(strQuery); 
		   
		    try {
		    	retval = (MrqsPreguntasFtaDto)query.getSingleResult(); 
		    }catch(NoResultException nre) {
		    	 System.out.println(this.getClass()+": findDtoByNumeroHdr No entity found for query pNumeroHdr:"+pNumeroHdr);
		    }
		}
	    return retval; 
	}

	@Override
	public MrqsPreguntasFtaV1Dto findV1DtoByNumeroHdr(long pNumeroHdr) {
		String strQuery = "SELECT m FROM MrqsPreguntasFtaV1Dto m WHERE m.numeroHdr="+pNumeroHdr; 
		Query query = em.createQuery(strQuery); 
		return (MrqsPreguntasFtaV1Dto)query.getSingleResult();
	}

	@Override
	public MrqsPreguntasFtaV1Dto findV1DtoByNumeroFta(long pNumeroFta) {
		String strQuery = "SELECT m FROM MrqsPreguntasFtaV1Dto m WHERE m.numero="+pNumeroFta; 
		Query query = em.createQuery(strQuery); 
		return (MrqsPreguntasFtaV1Dto)query.getSingleResult();
	}

}
