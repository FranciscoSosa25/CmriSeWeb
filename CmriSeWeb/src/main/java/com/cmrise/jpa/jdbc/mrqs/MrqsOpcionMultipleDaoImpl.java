package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.cmrise.jpa.dao.mrqs.MrqsOpcionMultipleDao;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsOpcionMultipleDaoImpl implements MrqsOpcionMultipleDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_OPCION_MULTIPLE_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsOpcionMultipleDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsOpcionMultipleDto.setCreadoPor((long)-1);
		pMrqsOpcionMultipleDto.setActualizadoPor((long)-1);
		pMrqsOpcionMultipleDto.setFechaCreacion(sqlsysdate);
		pMrqsOpcionMultipleDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsOpcionMultipleDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<MrqsOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		String strQuery = "SELECT m FROM MrqsOpcionMultipleDto m WHERE m.numeroFta ="+pNumeroFta; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void update(long pNumero, MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		System.out.println("pNumero:"+pNumero);
		MrqsOpcionMultipleDto mrqsOpcionMultipleDto = em.find(MrqsOpcionMultipleDto.class, pNumero);
		mrqsOpcionMultipleDto.setEstatus(pMrqsOpcionMultipleDto.isEstatus());
		mrqsOpcionMultipleDto.setTextoExplicacion(pMrqsOpcionMultipleDto.getTextoExplicacion());
		mrqsOpcionMultipleDto.setTextoRespuesta(pMrqsOpcionMultipleDto.getTextoRespuesta());
	}

}
