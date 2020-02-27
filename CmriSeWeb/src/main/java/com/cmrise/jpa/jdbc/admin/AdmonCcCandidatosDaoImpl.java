package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonCcCandidatosDao;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosDto;
import com.cmrise.jpa.dto.admin.AdmonCcCandidatosV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonCcCandidatosDaoImpl implements AdmonCcCandidatosDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;

	@Override
	public long insert(AdmonCcCandidatosDto pAdmonCcCandidatosDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_CC_CANDIDATOS_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonCcCandidatosDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pAdmonCcCandidatosDto.setCreadoPor((long)-1);
		pAdmonCcCandidatosDto.setActualizadoPor((long)-1);
		pAdmonCcCandidatosDto.setFechaCreacion(sqlsysdate);
		pAdmonCcCandidatosDto.setFechaActualizacion(sqlsysdate);
		em.persist(pAdmonCcCandidatosDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<AdmonCcCandidatosV1Dto> findByNumeroCcExamenWD(long pNumeroExamen) {
		String strQuery = "SELECT a FROM AdmonCcCandidatosV1Dto a WHERE a.numeroCcExamen = "+pNumeroExamen; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		System.out.println("AdmonCcCandidatosDaoImpl pNumero:"+pNumero);
		AdmonCcCandidatosDto admonCcCandidatosDto = em.find(AdmonCcCandidatosDto.class, pNumero); 
		em.remove(admonCcCandidatosDto);
	}

	@Override
	public void deleteAll(long pNumeroCcExamen) {
		String strQuery = "SELECT a FROM AdmonCcCandidatosDto a WHERE a.numeroCcExamen = "+pNumeroCcExamen; 
		Query query = em.createQuery(strQuery); 
		List<AdmonCcCandidatosDto> listAdmonCcCandidatosDto = query.getResultList(); 
		for(AdmonCcCandidatosDto admonCcCandidatosDto:listAdmonCcCandidatosDto) {
			em.remove(admonCcCandidatosDto);
		}
		
	}

	@Override
	public List<Object> findExaminationsByCandidato(long pNumeroCandidato) {
		String strQuery = "SELECT ACC.[NUMERO]\r" + 
						    "      ,ACC.[NUMERO_USUARIO]\r" + 
						    "      ,ACC.[NUMERO_CC_EXAMEN]\r" + 
						    "	  ,CE.TITULO\r" + 
							"	  ,CE.TIEMPO_LIMITE\r" + 
							"  FROM [dbo].[ADMON_CC_CANDIDATOS] ACC\r" + 
							"      ,[dbo].[CC_EXAMENES] CE\r" + 
							"  WHERE ACC.NUMERO_CC_EXAMEN = CE.NUMERO\r"+
						    "    AND ACC.[NUMERO_USUARIO] ="+pNumeroCandidato; 
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
	
}
