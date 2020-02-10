package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.corecases.CcPreguntasHdrDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcPreguntasHdrDaoImpl implements CcPreguntasHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcPreguntasHdrDto pCcPreguntasHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_PREGUNTAS_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pCcPreguntasHdrDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcPreguntasHdrDto.setCreadoPor((long)-1);
		pCcPreguntasHdrDto.setActualizadoPor((long)-1);
		pCcPreguntasHdrDto.setFechaCreacion(sqlsysdate);
		pCcPreguntasHdrDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcPreguntasHdrDto);
		return longNumeroS; 
	}

	@Override
	public void delete(long pNumero) {
		CcPreguntasHdrDto ccPreguntasHdrDto = em.find(CcPreguntasHdrDto.class, pNumero);
		em.remove(ccPreguntasHdrDto);
	}

	@Override
	public void update(long pNumero, CcPreguntasHdrDto pCcPreguntasHdrDto) {
		CcPreguntasHdrDto ccPreguntasHdrDto = em.find(CcPreguntasHdrDto.class, pNumero);
		ccPreguntasHdrDto.setNombre(pCcPreguntasHdrDto.getNombre());
		ccPreguntasHdrDto.setTitulo(pCcPreguntasHdrDto.getTitulo());
		ccPreguntasHdrDto.setEstatus(pCcPreguntasHdrDto.getEstatus());
		ccPreguntasHdrDto.setTipoPregunta(pCcPreguntasHdrDto.getTipoPregunta());
		ccPreguntasHdrDto.setTemaPregunta(pCcPreguntasHdrDto.getTemaPregunta());
		ccPreguntasHdrDto.setMaxPuntuacion(pCcPreguntasHdrDto.getMaxPuntuacion());
		ccPreguntasHdrDto.setEtiquetas(pCcPreguntasHdrDto.getEtiquetas());
		ccPreguntasHdrDto.setComentarios(pCcPreguntasHdrDto.getComentarios());
	}

	@Override
	public CcPreguntasHdrV1Dto findByNumero(long pNumero) {
		return em.find(CcPreguntasHdrV1Dto.class, pNumero);
	}

	@Override
	public List<CcPreguntasHdrV1Dto> findListByNumeroCcHdr(long pNumeroCcHdr) {
		String strQuery = "SELECT c FROM CcPreguntasHdrV1Dto c WHERE c.numeroCcHdr="+pNumeroCcHdr;
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}

}
