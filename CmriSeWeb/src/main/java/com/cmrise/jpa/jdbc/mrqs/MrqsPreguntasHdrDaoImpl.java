package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasHdrDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsPreguntasHdrDaoImpl implements MrqsPreguntasHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_PREGUNTAS_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsPreguntasHdrDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsPreguntasHdrDto.setCreadoPor((long)-1);
		pMrqsPreguntasHdrDto.setActualizadoPor((long)-1);
		pMrqsPreguntasHdrDto.setFechaCreacion(sqlsysdate);
		pMrqsPreguntasHdrDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsPreguntasHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto =em.find(MrqsPreguntasHdrDto.class, pNumero);
		em.remove(mrqsPreguntasHdrDto);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto =em.find(MrqsPreguntasHdrDto.class, pNumero);
	}

	@Override
	public List<MrqsPreguntasHdrV1Dto> findAll() {
		String strQuery ="SELECT m FROM MrqsPreguntasHdrV1Dto m";
		return em.createQuery(strQuery).getResultList();
	}

}
