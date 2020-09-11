package com.cmrise.jpa.jdbc.candidates.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.candidates.exams.CandExamRespSkipDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamSkipV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CandExamRespSkipDaoImpl implements CandExamRespSkipDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CandExamRespSkipDto pCandExamRespSkipDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CAND_EXAM_RESP_SKIP_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pCandExamRespSkipDto.setNumero(lNumeroS.longValue());
		pCandExamRespSkipDto.setEstatus(1);
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCandExamRespSkipDto.setCreadoPor((long)-1);
		pCandExamRespSkipDto.setActualizadoPor((long)-1);
		pCandExamRespSkipDto.setFechaCreacion(sqlsysdate);
		pCandExamRespSkipDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCandExamRespSkipDto);
		return longNumeroS; 
	}
	
	@Override
	public List<CandExamSkipV1Dto> findByReg(long pNumeroExamen, long pNumCandExam,long pNumeroGrupo,long pNumPregHdr, long pNumPregFta,int pSkip){
		String strQuery ="SELECT c FROM CandExamSkipV1Dto c WHERE c.examen = "+pNumeroExamen+" AND c.numeroCandExamen = "+pNumCandExam+" AND c.numeroGrupo = "+pNumeroGrupo+" AND c.numeroPreguntaHdr = "+pNumPregHdr+" AND c.numeroPreguntaFta = "+pNumPregFta+" AND c.skip = "+pSkip;
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}
	
	@Override
	public List<CandExamSkipV1Dto> findAllListSkip(long pNumeroExamen, long pNumCandExam,int pEstatus,int pSkip){
		String strQuery ="SELECT c FROM CandExamSkipV1Dto c WHERE c.examen = "+pNumeroExamen+" AND c.numeroCandExamen = "+pNumCandExam+" AND c.estatus = "+pEstatus+" AND c.skip = "+pSkip;
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
		
	}
	
	@Override
	public boolean existsSkip(long pNumeroExamen, long pNumCandExam,long pNumeroGrupo,long pNumPregHdr, long pNumPregFta, int pEstatus){
		boolean ret=false;
		String strQuery ="SELECT c FROM CandExamSkipV1Dto c WHERE c.examen = "+pNumeroExamen+" AND c.numeroCandExamen = "+pNumCandExam+" AND c.numeroGrupo = "+pNumeroGrupo+" AND c.numeroPreguntaHdr = "+pNumPregHdr+" AND c.numeroPreguntaFta = "+pNumPregFta+" AND c.estatus = "+pEstatus;
		Query query = em.createQuery(strQuery); 
		if (query.getResultList().size()>0) ret=true;
		return ret;
	}
	
	@Override
	public void update(long pNumero, CandExamRespSkipDto pCandExamRespSkipDto) {
		CandExamRespSkipDto candExamRespSkipDto = em.find(CandExamRespSkipDto.class, pNumero); 
		candExamRespSkipDto.setSkip(0);
		candExamRespSkipDto.setEstatus(0);
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		candExamRespSkipDto.setFechaActualizacion(sqlsysdate);
	}

}
