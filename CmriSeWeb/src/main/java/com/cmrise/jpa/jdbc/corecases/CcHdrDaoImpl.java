package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcHdrDaoImpl implements CcHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(CcHdrDto pCcHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcHdrDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcHdrDto.setCreadoPor((long)-1);
		pCcHdrDto.setActualizadoPor((long)-1);
		pCcHdrDto.setFechaCreacion(sqlsysdate);
		pCcHdrDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		CcHdrDto ccHdrDto = em.find(CcHdrDto.class, pNumero);
		em.remove(ccHdrDto);
	}

	@Override
	public void update(long pNumero, CcHdrDto pCcHdrDto) {
		CcHdrDto ccHdrDto = em.find(CcHdrDto.class, pNumero);
		
	}

	@Override
	public List<CcHdrV1Dto> findAll() {
		String strQuery = "SELECT c FROM CcHdrV1Dto c";
		Query query = em.createQuery(strQuery);
		/*String strQuery = "SELECT * FROM CC_HDR_V1";
		Query query = em.createNativeQuery(strQuery);*/
		return query.getResultList();
	}

	@Override
	public CcHdrV1Dto findByNumero(long pNumero) {
		return em.find(CcHdrV1Dto.class, pNumero);
	}

}
