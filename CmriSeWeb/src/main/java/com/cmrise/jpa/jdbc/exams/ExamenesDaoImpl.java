package com.cmrise.jpa.jdbc.exams;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.exams.ExamenesDao;
import com.cmrise.utils.Utilitarios;

@Stateless
public class ExamenesDaoImpl implements ExamenesDao {


	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	
	
	@Override
	public List<Object> findAll() {
		String strQuery ="SELECT DISTINCT ME.[NUMERO]\r" + 
				"      ,'Titulo Sin Implementar' TITULO\r" + 
				"      ,'Nombre Sin Implementar' NOMBRE\r" + 
				"      ,ME.[DESCRIPCION]\r" + 
				"      ,ME.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES TMP WHERE TMP.NUMERO_EXAMEN = ME.NUMERO AND TMP.TIPO='MRQS') TOTAL_CANDIDADATOS\r" + 
				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" + 
				"  FROM [dbo].[MRQS_EXAMENES] ME\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"  WHERE ME.NUMERO = CE.NUMERO_EXAMEN\r" + 
				"    AND CE.TIPO = 'MRQS'\r" + 
				" UNION\r" + 
				"  SELECT DISTINCT CCE.[NUMERO]\r" + 
				"      ,'Titulo Sin Implementar' TITULO\r" + 
				"      ,'Nombre Sin Implementar' NOMBRE\r" + 
				"      ,CCE.[DESCRIPCION]\r" + 
				"      ,CCE.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES TMP WHERE TMP.NUMERO_EXAMEN = CCE.NUMERO AND TMP.TIPO='CORE_CASES') TOTAL_CANDIDADATOS\r" + 
				"	  ,'Casos Clinicos' TIPO_EXAMEN_DESC\r" + 
				"	  ,CCE.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" + 
				"     FROM [dbo].[CC_EXAMENES] CCE\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"  WHERE CCE.NUMERO = CE.NUMERO_EXAMEN\r" + 
				"    AND CE.TIPO = 'CORE_CASES'";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}


}
