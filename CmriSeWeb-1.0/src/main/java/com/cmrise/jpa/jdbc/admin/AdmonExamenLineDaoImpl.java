package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonExamenLineDao;
import com.cmrise.jpa.dto.admin.AdmonExamenLineDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonExamenLineDaoImpl implements AdmonExamenLineDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(AdmonExamenLineDto pAdmonExamenLineDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_EXAMEN_LINE_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonExamenLineDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pAdmonExamenLineDto.setCreadoPor((long)-1);
		pAdmonExamenLineDto.setActualizadoPor((long)-1);
		pAdmonExamenLineDto.setFechaCreacion(sqlsysdate);
		pAdmonExamenLineDto.setFechaActualizacion(sqlsysdate);
		em.persist(pAdmonExamenLineDto);
		return lNumeroS.longValue();
	}

	@Override
	public List<AdmonExamenLineDto> findByNumeroHdr(long pNumero) {
		String strQuery = "SELECT a FROM AdmonExamenLineDto a WHERE a.numeroExamen = "+pNumero; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		AdmonExamenLineDto admonExamenLineDto = em.find(AdmonExamenLineDto.class, pNumero);
		em.remove(admonExamenLineDto);
	}

}
