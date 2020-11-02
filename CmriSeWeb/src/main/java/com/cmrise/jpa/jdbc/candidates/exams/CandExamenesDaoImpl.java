package com.cmrise.jpa.jdbc.candidates.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.candidates.exams.CandExamenesDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CandExamenesDaoImpl implements CandExamenesDao {
	
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CandExamenesDto pCandExamenesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CAND_EXAMENES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCandExamenesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCandExamenesDto.setCreadoPor((long)-1);
		pCandExamenesDto.setActualizadoPor((long)-1);
		pCandExamenesDto.setFechaCreacion(sqlsysdate);
		pCandExamenesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCandExamenesDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<CandExamenesV1Dto> findByExamen(long pNumeroExamen, String pTipoExamen) {
		String strQuery ="SELECT c FROM CandExamenesV1Dto c WHERE c.numeroExamen ="+pNumeroExamen+" AND c.tipo='"+pTipoExamen+"'";
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		CandExamenesDto candExamenesDto = em.find(CandExamenesDto.class, pNumero); 
		em.remove(candExamenesDto);
	}

	@Override
	public List<CandExamenesV2Dto> findByUsuario(long pNumeroUsuario) {
		String strQuery="SELECT c FROM CandExamenesV2Dto c WHERE c.numeroUsuario="+pNumeroUsuario; 
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}

	@Override
	public List<CandExamenesV2Dto> findAll() {
		String strQuery="SELECT c FROM CandExamenesV2Dto c"; 
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}

	@Override
	public CandExamenesV1Dto findByNumero(long pNumero) {
		String strQuery="SELECT c FROM CandExamenesV1Dto c WHERE c.numero="+pNumero; 
		Query query = em.createQuery(strQuery);
		return (CandExamenesV1Dto)query.getSingleResult(); 
	}

	@Override
	public List<CandExamenesV2Dto> findByCURP(String pCurp, String pNombreUsuario,String pApellidoPaterno,String pApellidoMaterno) {
		String strQuery="SELECT c FROM CandExamenesV2Dto c WHERE c.curp like '%"+pCurp+"%'AND C.nombreUsuario like '%"+pNombreUsuario+"%'AND C.apellidoPaterno like '%"+pApellidoPaterno+"%'AND C.apellidoMaterno like '%"+pApellidoMaterno+"%'"; 
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}
	
	@Override
	public void updateEstatus(long pNumero, CandExamenesDto pCandExamenesDto) {
		CandExamenesDto candExamenesDto = em.find(CandExamenesDto.class, pNumero); 
		candExamenesDto.setEstatus("REVISADO");
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		candExamenesDto.setFechaActualizacion(sqlsysdate);
	}

}
