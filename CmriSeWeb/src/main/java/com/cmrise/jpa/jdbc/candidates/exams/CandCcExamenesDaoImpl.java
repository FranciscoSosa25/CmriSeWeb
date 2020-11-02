package com.cmrise.jpa.jdbc.candidates.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.candidates.exams.CandCcExamenesDao;
import com.cmrise.jpa.dto.candidates.exams.CandCcExamenesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CandCcExamenesDaoImpl implements CandCcExamenesDao {


	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CandCcExamenesDto pCandCcExamenesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CAND_CC_EXAMENES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCandCcExamenesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCandCcExamenesDto.setCreadoPor((long)-1);
		pCandCcExamenesDto.setActualizadoPor((long)-1);
		pCandCcExamenesDto.setFechaCreacion(sqlsysdate);
		pCandCcExamenesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCandCcExamenesDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<Object> findCandCcExamInfoByNumero(long pNumeroCandCcExamen) {
		String strQuery = "SELECT CCE.[NUMERO]\r" + 
						    "      ,CCE.[NUMERO_CC_EXAMEN]\r" + 
							"	  ,CCPH.NUMERO                 NUMERO_CCPH\r" + 
							"	  ,CCPH.NUMERO_CC_PREGUNTA_HDR NUMERO_CPH\r" + 
							"	  ,CCPF.NUMERO                 NUMERO_CCPF\r" + 
							"	  ,CCPF.NUMERO_CC_PREGUNTA_FTA NUMERO_CPF\r" + 
							"      ,CCE.[NUMERO_USUARIO]        NUMERO_CANDIDATO\r" + 
							"      ,CCE.[ESTATUS]\r" + 
							"      ,CCE.[FECHA_EFECTIVA_DESDE]\r" + 
							"      ,CCE.[FECHA_EFECTIVA_HASTA]\r" + 
							"      ,CPF.TITULO_PREGUNTA\r" + 
							"      ,CE.NOMBRE NOMBRE_EXAMEN\r" + 
							"  FROM [dbo].[CAND_CC_EXAMENES] CCE\r" + 
							"      ,[dbo].[CAND_CC_PREGUNTAS_HDR] CCPH\r" + 
							"	   ,[dbo].[CAND_CC_PREGUNTAS_FTA] CCPF\r" + 
							"	   ,[dbo].[CC_EXAMENES] CE \r" + 
							"	   ,[dbo].[CC_PREGUNTAS_FTA] CPF\r" + 
							"  WHERE 1=1\r" + 
							"   AND CCE.NUMERO = CCPH.NUMERO_CAND_CC_EXAMEN\r" + 
							"   AND CCPH.NUMERO = CCPF.NUMERO_CC_CAND_PREGUNTA_HDR\r" + 
							"   AND CCE.NUMERO_CC_EXAMEN = CE.NUMERO\r" + 
							"   AND CCPF.NUMERO_CC_PREGUNTA_FTA = CPF.NUMERO\r" + 
							"   AND CCE.NUMERO = "+pNumeroCandCcExamen; 
		System.out.println(strQuery);
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList(); 
	}

	@Override
	public long findNumeroCandCcExamen(long pNumeroCcExamen, long pNumeroCandidato) {
		  long retval = 0;
			String strQuery = "SELECT [NUMERO]\r" + 
						   	  "  FROM [dbo].[CAND_CC_EXAMENES]\r" + 
							  " WHERE [NUMERO_CC_EXAMEN]=" +pNumeroCcExamen+"\r"+ 
							  "   AND [NUMERO_USUARIO]="+pNumeroCandidato; 
			Query query =  em.createNativeQuery(strQuery); 
			try {
			BigInteger lNumero = (BigInteger)query.getSingleResult();
			  if(null!=lNumero) {
		        	retval = lNumero.longValue();
		        }		
			}catch(NoResultException nre) {
			   System.out.println(this.getClass()+":No entity found for query");
			}
	      
			return retval;
	}

	@Override
	public Object findCandCcExamenPreguntaInfo(long pNumeroCandCcExamen
			                                 , long pNumeroCandCcPreguntaHdr
			                                 ) {
		String strQuery = "SELECT CCE.[NUMERO]\r" + 
			    "      ,CCE.[NUMERO_CC_EXAMEN]\r" + 
				"	  ,CCPH.NUMERO                 NUMERO_CCPH\r" + 
				"	  ,CCPH.NUMERO_CC_PREGUNTA_HDR NUMERO_CPH\r" + 
				"	  ,CCPF.NUMERO                 NUMERO_CCPF\r" + 
				"	  ,CCPF.NUMERO_CC_PREGUNTA_FTA NUMERO_CPF\r" + 
				"      ,CCE.[NUMERO_USUARIO]        NUMERO_CANDIDATO\r" + 
				"      ,CCE.[ESTATUS]\r" + 
				"      ,CCE.[FECHA_EFECTIVA_DESDE]\r" + 
				"      ,CCE.[FECHA_EFECTIVA_HASTA]\r" + 
				"      ,CPF.TITULO_PREGUNTA\r" + 
				"      ,CE.NOMBRE NOMBRE_EXAMEN\r" + 
				"  FROM [dbo].[CAND_CC_EXAMENES] CCE\r" + 
				"      ,[dbo].[CAND_CC_PREGUNTAS_HDR] CCPH\r" + 
				"	   ,[dbo].[CAND_CC_PREGUNTAS_FTA] CCPF\r" + 
				"	   ,[dbo].[CC_EXAMENES] CE \r" + 
				"	   ,[dbo].[CC_PREGUNTAS_FTA] CPF\r" + 
				"  WHERE 1=1\r" + 
				"   AND CCE.NUMERO = CCPH.NUMERO_CAND_CC_EXAMEN\r" + 
				"   AND CCPH.NUMERO = CCPF.NUMERO_CC_CAND_PREGUNTA_HDR\r" + 
				"   AND CCE.NUMERO_CC_EXAMEN = CE.NUMERO\r" + 
				"   AND CCPF.NUMERO_CC_PREGUNTA_FTA = CPF.NUMERO\r" + 
				"   AND CCE.NUMERO = "+pNumeroCandCcExamen+"\r"+
				"   AND CCPH.NUMERO = "+pNumeroCandCcPreguntaHdr; 
		System.out.println(strQuery);
		Query query = em.createNativeQuery(strQuery); 
        return query.getSingleResult();
	}

}
