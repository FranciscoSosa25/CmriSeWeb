package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcOpcionMultipleDaoImpl implements CcOpcionMultipleDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcOpcionMultipleDto pCcOpcionMultipleDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_OPCION_MULTIPLE_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcOpcionMultipleDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcOpcionMultipleDto.setCreadoPor((long)-1);
		pCcOpcionMultipleDto.setActualizadoPor((long)-1);
		pCcOpcionMultipleDto.setFechaCreacion(sqlsysdate);
		pCcOpcionMultipleDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcOpcionMultipleDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<CcOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		String strQuery = "SELECT c FROM CcOpcionMultipleDto c WHERE c.numeroFta ="+pNumeroFta; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void update(long pNumero, CcOpcionMultipleDto pCcOpcionMultipleDto) {
		System.out.println("pNumero:"+pNumero);
		CcOpcionMultipleDto ccOpcionMultipleDto = em.find(CcOpcionMultipleDto.class, pNumero);
		ccOpcionMultipleDto.setEstatus(pCcOpcionMultipleDto.isEstatus());
		ccOpcionMultipleDto.setTextoExplicacion(pCcOpcionMultipleDto.getTextoExplicacion());
		ccOpcionMultipleDto.setTextoRespuesta(pCcOpcionMultipleDto.getTextoRespuesta());
		ccOpcionMultipleDto.setNumeroLinea(pCcOpcionMultipleDto.getNumeroLinea());
	}

	@Override
	public void delete(long pNumero) {
		CcOpcionMultipleDto ccOpcionMultipleDto = em.find(CcOpcionMultipleDto.class, pNumero);
		em.remove(ccOpcionMultipleDto);
	}

}
