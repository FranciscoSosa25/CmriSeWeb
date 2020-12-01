package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.corecases.CcPreguntasFtaDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaV2Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcPreguntasFtaDaoImpl implements CcPreguntasFtaDao {
	
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcPreguntasFtaDto pCcPreguntasFtaDto) {
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
		return lNumeroS.longValue(); 
	}

	@Override
	public void delete(long pNumero) {
		CcPreguntasFtaDto ccPreguntasFtaDto = em.find(CcPreguntasFtaDto.class, pNumero);
		em.remove(ccPreguntasFtaDto);
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void update(long pNumero, CcPreguntasFtaDto pCcPreguntasFtaDto) {
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcPreguntasFtaDto.setNumero(pNumero);
		pCcPreguntasFtaDto.setCreadoPor((long)-1);
		pCcPreguntasFtaDto.setActualizadoPor((long)-1);
		pCcPreguntasFtaDto.setFechaCreacion(sqlsysdate);
		pCcPreguntasFtaDto.setFechaActualizacion(sqlsysdate);
		pCcPreguntasFtaDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		pCcPreguntasFtaDto.setFechaEfectivaHasta(Utilitarios.endOfTime);		
		em.merge(pCcPreguntasFtaDto);
		em.flush();
	}

	@Override
	public long finNumeroByHdr(long pNumeroHdr) {
	    long retval = 0;
		String strQuery = "SELECT [NUMERO]\r\n" + 
				"  FROM [dbo].[CC_PREGUNTAS_FTA]\r\n" + 
				" WHERE [NUMERO_HDR] = "+pNumeroHdr;
		Query query =  em.createNativeQuery(strQuery); 
		try {
		BigInteger lNumero = (BigInteger)query.getSingleResult();
		  if(null!=lNumero) {
	        	retval = lNumero.longValue();
	        }		
		}catch(NoResultException nre) {
		   System.out.println(this.getClass()+":No entity found for query");
		}
      
		return retval;
	}

	@Override
	public CcPreguntasFtaV1Dto findDtoByNumeroHdr(long pNumeroHdr) {
		CcPreguntasFtaV1Dto ccPreguntasFtaV1Dto = new CcPreguntasFtaV1Dto(); 
		try {
		String strQuery = "SELECT c FROM CcPreguntasFtaV1Dto c WHERE c.numeroHdr ="+pNumeroHdr; 
		Query query = em.createQuery(strQuery); 
		ccPreguntasFtaV1Dto = (CcPreguntasFtaV1Dto)query.getSingleResult(); 
		}catch(javax.persistence.NoResultException nre) {
			System.out.println("Exception CcPreguntasFtaDaoImpl.findDtoByNumeroHdr("+pNumeroHdr+") ,"+nre.getMessage());
		}
		return ccPreguntasFtaV1Dto;
	}

	@Override
	public CcPreguntasFtaDto findDtoByNumeroFta(long pNumeroFta) {
		CcPreguntasFtaDto ccPreguntasFtaDto = em.find(CcPreguntasFtaDto.class, pNumeroFta);
		return ccPreguntasFtaDto;
	}

	@Override
	public CcPreguntasFtaV2Dto findV2DtoByNumeroFta(long pNumeroFta) {
		CcPreguntasFtaV2Dto ccPreguntasFtaV2Dto = em.find(CcPreguntasFtaV2Dto.class, pNumeroFta);
		return ccPreguntasFtaV2Dto;
	}


}
