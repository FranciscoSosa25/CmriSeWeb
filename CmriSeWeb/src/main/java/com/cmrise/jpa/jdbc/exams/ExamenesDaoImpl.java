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
				"      ,ME.[TITULO]\r" + 
				"      ,ME.[NOMBRE]\r" + 
				"      ,ME.[DESCRIPCION]\r" + 
				"      ,ME.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES WHERE NUMERO_EXAMEN = ME.NUMERO) TOTAL_CANDIDADATOS\r" + 
				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" + 
				"  FROM [dbo].[MRQS_EXAMENES] ME\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"  WHERE ME.NUMERO = CE.NUMERO_EXAMEN\r" + 
				"    AND CE.TIPO = 'MRQS'\r" + 
				" UNION\r" + 
				"  SELECT DISTINCT CCE.[NUMERO]\r" + 
				"      ,CCE.[TITULO]\r" + 
				"      ,CCE.[NOMBRE]\r" + 
				"      ,CCE.[DESCRIPCION]\r" + 
				"      ,CCE.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES WHERE NUMERO_EXAMEN = CCE.NUMERO) TOTAL_CANDIDADATOS\r" + 
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

	@Override
	public List<Object> findByTituloExamen(String pTituloExamen,String pTipoExamen) {
		String strQuery ="SELECT DISTINCT ME.[NUMERO]\r" + 
				"      ,ME.[TITULO]\r" + 
				"      ,ME.[NOMBRE]\r" + 
				"      ,ME.[DESCRIPCION]\r" + 
				"      ,ME.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES WHERE NUMERO_EXAMEN = ME.NUMERO) TOTAL_CANDIDADATOS\r" + 
				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" + 
				"  FROM [dbo].[MRQS_EXAMENES] ME\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"  WHERE ME.NUMERO = CE.NUMERO_EXAMEN\r" + 
				"    AND CE.TIPO = 'MRQS'\r" + 
				" UNION\r" + 
				"  SELECT DISTINCT CCE.[NUMERO]\r" + 
				"      ,CCE.[TITULO]\r" + 
				"      ,CCE.[NOMBRE]\r" + 
				"      ,CCE.[DESCRIPCION]\r" + 
				"      ,CCE.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES WHERE NUMERO_EXAMEN = CCE.NUMERO) TOTAL_CANDIDADATOS\r" + 
				"	  ,'Casos Clinicos' TIPO_EXAMEN_DESC\r" + 
				"	  ,CCE.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" + 
				"     FROM [dbo].[CC_EXAMENES] CCE\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"  WHERE CCE.NUMERO = CE.NUMERO_EXAMEN\r" + 
				"    AND CE.TIPO = 'CORE_CASES'";
		strQuery = "SELECT * FROM ("+strQuery+") AS TMP WHERE TITULO like '%"+pTituloExamen+"%' AND TIPO_EXAMEN_DESC like '%"+pTipoExamen+"%'"; 
		System.out.println(strQuery);
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
	
	
//	public List<Object> findByTipoExamen(String pTipoExamen) {
//		String strQuery ="SELECT DISTINCT ME.[NUMERO]\r" + 
//				"      ,ME.[TITULO]\r" + 
//				"      ,ME.[NOMBRE]\r" + 
//				"      ,ME.[DESCRIPCION]\r" + 
//				"      ,ME.[TIEMPO_LIMITE]\r" + 
//				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES WHERE NUMERO_EXAMEN = ME.NUMERO) TOTAL_CANDIDADATOS\r" + 
//				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
//				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
//				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" + 
//				"  FROM [dbo].[MRQS_EXAMENES] ME\r" + 
//				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
//				"  WHERE ME.NUMERO = CE.NUMERO_EXAMEN\r" + 
//				"    AND CE.TIPO = 'MRQS'\r" + 
//				" UNION\r" + 
//				"  SELECT DISTINCT CCE.[NUMERO]\r" + 
//				"      ,CCE.[TITULO]\r" + 
//				"      ,CCE.[NOMBRE]\r" + 
//				"      ,CCE.[DESCRIPCION]\r" + 
//				"      ,CCE.[TIEMPO_LIMITE]\r" + 
//				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES WHERE NUMERO_EXAMEN = CCE.NUMERO) TOTAL_CANDIDADATOS\r" + 
//				"	  ,'Casos Clinicos' TIPO_EXAMEN_DESC\r" + 
//				"	  ,CCE.FECHA_EFECTIVA_DESDE\r" + 
//				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" + 
//				"     FROM [dbo].[CC_EXAMENES] CCE\r" + 
//				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
//				"  WHERE CCE.NUMERO = CE.NUMERO_EXAMEN\r" + 
//				"    AND CE.TIPO = 'CORE_CASES'";
//		strQuery = "SELECT * FROM ("+strQuery+") AS TMP WHERE TIPO like '%"+pTipoExamen+"%'"; 
//		System.out.println(strQuery);
//		Query query = em.createNativeQuery(strQuery); 
//		return query.getResultList();
//	}
}
