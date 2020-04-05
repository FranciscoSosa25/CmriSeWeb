package com.cmrise.jpa.jdbc.candidates.exams;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import com.cmrise.jpa.dao.candidates.exams.CandExamRespuestasDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespuestasDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CandExamRespuestasDaoImpl implements CandExamRespuestasDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CandExamRespuestasDto pCandExamRespuestasDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CAND_EXAM_RESPUESTAS_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCandExamRespuestasDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCandExamRespuestasDto.setFechaCreacion(sqlsysdate);
		pCandExamRespuestasDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCandExamRespuestasDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public int validaRegistro(long pNumeroCandExamen
			                 ,long pNumeroGrupo
			                 ,long pNumeroPreguntaHdr
			                 ,long pNumeroPreguntaFta) {
		String strQuery ="SELECT COUNT(c.numero) FROM CandExamRespuestasDto c WHERE c.numeroCandExamen="+pNumeroCandExamen;
		strQuery = strQuery+" AND c.numeroGrupo="+pNumeroGrupo; 
		strQuery = strQuery+" AND c.numeroPreguntaHdr="+pNumeroPreguntaHdr; 
		strQuery = strQuery+" AND c.numeroPreguntaFta="+pNumeroPreguntaFta; 
		Query query = em.createQuery(strQuery); 
		Long longValue = (Long)query.getSingleResult();
		return longValue.intValue();
	}

	@Override
	public void updateRespuesta(long pNumeroCandExamen
			                  , long pNumeroGrupo
			                  , long pNumeroPreguntaHdr
			                  , long pNumeroPreguntaFta
			                  , String pRespuesta) {
		String strQuery ="SELECT c FROM CandExamRespuestasDto c WHERE c.numeroCandExamen="+pNumeroCandExamen;
		strQuery = strQuery+" AND c.numeroGrupo="+pNumeroGrupo; 
		strQuery = strQuery+" AND c.numeroPreguntaHdr="+pNumeroPreguntaHdr; 
		strQuery = strQuery+" AND c.numeroPreguntaFta="+pNumeroPreguntaFta; 
		Query query = em.createQuery(strQuery);
		
		CandExamRespuestasDto candExamRespuestasDto = (CandExamRespuestasDto)query.getSingleResult(); 
		candExamRespuestasDto.setRespuesta(pRespuesta);
		
	}

	@Override
	public CandExamRespuestasDto findDto(long pNumeroCandExamen
			                            ,long pNumeroGrupo
			                            ,long pNumeroPreguntaHdr
			                            ,long pNumeroPreguntaFta) {
		String strQuery ="SELECT c FROM CandExamRespuestasDto c WHERE c.numeroCandExamen="+pNumeroCandExamen;
		strQuery = strQuery+" AND c.numeroGrupo="+pNumeroGrupo; 
		strQuery = strQuery+" AND c.numeroPreguntaHdr="+pNumeroPreguntaHdr; 
		strQuery = strQuery+" AND c.numeroPreguntaFta="+pNumeroPreguntaFta; 
		Query query = em.createQuery(strQuery);
		
		CandExamRespuestasDto candExamRespuestasDto = (CandExamRespuestasDto)query.getSingleResult(); 
		return candExamRespuestasDto;
	}

	@Override
	public void calificaRespuesta(long pNumeroCandExamen
			                    , long pNumeroGrupo
			                    , long pNumeroPreguntaHdr
			                    ,long pNumeroPreguntaFta) {
		StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("XXCMRI_CALIFICA_PREGUNTA"); 
		storedProcedure.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
		storedProcedure.setParameter(1, (Long)pNumeroCandExamen); 
		storedProcedure.setParameter(2, (Long)pNumeroGrupo); 
		storedProcedure.setParameter(3, (Long)pNumeroPreguntaHdr); 
		storedProcedure.setParameter(4, (Long)pNumeroPreguntaFta); 
		storedProcedure.execute();
		
	}

	

}
