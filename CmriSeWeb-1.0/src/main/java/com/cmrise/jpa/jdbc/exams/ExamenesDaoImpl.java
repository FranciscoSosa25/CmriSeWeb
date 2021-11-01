package com.cmrise.jpa.jdbc.exams;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
				"      ,CE1.[ADMON_EXAMEN_DESC]\r" + 
				"      ,ME.[DESCRIPCION]\r" + 
				"      ,ME.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES TMP WHERE TMP.NUMERO_EXAMEN = ME.NUMERO AND TMP.TIPO='MRQS') TOTAL_CANDIDADATOS\r" + 
				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" +
				"	  ,ME.FECHA_EFECTIVA_HASTA\r" + 
				"  FROM [dbo].[MRQS_EXAMENES] ME\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"	   ,[dbo].[MRQS_EXAMENES_V1] CE1\r" + 
				"  WHERE ME.NUMERO = CE.NUMERO_EXAMEN\r" + 
				"  AND ME.NUMERO = CE1.NUMERO\r" + 
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
				"	  ,CCE.FECHA_EFECTIVA_HASTA\r" + 
				"     FROM [dbo].[CC_EXAMENES] CCE\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"  WHERE CCE.NUMERO = CE.NUMERO_EXAMEN\r" + 
				"    AND CE.TIPO = 'CORE_CASES'";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
	
	@Override
	public List<Object> findByTitulo(int idExamen, String nombreExamen, Date fechaDesde, Date fechaHasta, int tiempo, String tipoExamen){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String strQuery ="SELECT DISTINCT ME.[NUMERO]\r" + 
				"      ,'Titulo Sin  ' TITULO\r" + 
				"      ,CE1.[ADMON_EXAMEN_DESC]\r" + 
				"      ,ME.[DESCRIPCION]\r" + 
				"      ,ME.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES TMP WHERE TMP.NUMERO_EXAMEN = ME.NUMERO AND TMP.TIPO='MRQS') TOTAL_CANDIDADATOS\r" + 
				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" +
				"	  ,ME.FECHA_EFECTIVA_HASTA\r" + 
				"  FROM [dbo].[MRQS_EXAMENES] ME\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"	   ,[dbo].[MRQS_EXAMENES_V1] CE1\r" + 
				"  WHERE ME.NUMERO = CE.NUMERO_EXAMEN\r"+
				"  AND ME.NUMERO = CE1.NUMERO\r";
		if(idExamen!=0){
			strQuery += "AND CE.NUMERO_EXAMEN = "+ idExamen+ " \r" ;
		}
		if(!nombreExamen.equals("")) {
			strQuery += "AND CE1.[ADMON_EXAMEN_DESC] LIKE '%"+ nombreExamen+ "%' \r"; //Cambiar campo?
		}
		if(fechaDesde != null) {
			System.out.println("la fecha: " + fechaDesde);
			strQuery += "AND ME.FECHA_EFECTIVA_DESDE = CAST('"+ format.format(fechaDesde)+ "' as datetime) \r";
		}
		if(fechaHasta != null)
		{
			strQuery += "AND ME.FECHA_EFECTIVA_HASTA = CAST('"+ format.format(fechaHasta)+ "' as datetime)  \r";
		}
		if(tiempo != 0) {
			strQuery += "AND ME.[TIEMPO_LIMITE] LIKE '%"+ tiempo+ "%' \r";
		}
		if(!tipoExamen.equals("") ){
			strQuery += "AND CE.TIPO LIKE '%"+ tipoExamen + "%' \r";
		}
		System.out.println(strQuery);
		
		Query query = em.createNativeQuery(strQuery);  
		return query.getResultList();
	}
	
	@Override
	public List<Object> findCandidatesForthisExam(int idExamen) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String strQuery = "SELECT C.NOMBRE_COMPLETO_USUARIO, C.TIPO"
				+ ",(SELECT SUM(NUMERO_REACTIVOS) FROM dbo.MRQS_GRUPO_HDR_V1 WHERE NUMERO_EXAMEN = "
				+ idExamen
				+ " ) AS TOTAL_REACTIVOS "
				+ ",E.TIEMPO_LIMITE, C.RECTIVOS_RESPONDIDOS, C.NUMERO AS NUMERO_CAND_EXAMEN   FROM dbo.CAND_EXAMENES_V1 C LEFT JOIN dbo.MRQS_EXAMENES E "
				+ "ON C.NUMERO_EXAMEN=E.NUMERO WHERE E.NUMERO = "
				+ idExamen + " ORDER BY C.NOMBRE_COMPLETO_USUARIO";

		System.out.println(strQuery);

		Query query = em.createNativeQuery(strQuery);
		return query.getResultList();
	}
	@Override
	public List<Object> findCandidatesExamDetails(int idExamen, long numeroCandExamen){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String strQuery = "SELECT C.NOMBRE_COMPLETO_USUARIO, C.TIPO"
				+ ",(SELECT SUM(NUMERO_REACTIVOS) FROM dbo.MRQS_GRUPO_HDR_V1 WHERE NUMERO_EXAMEN = "
				+ idExamen
				+ " ) AS TOTAL_REACTIVOS "
				+ ",E.TIEMPO_LIMITE, C.RECTIVOS_RESPONDIDOS, C.NUMERO AS NUMERO_CAND_EXAMEN   FROM dbo.CAND_EXAMENES_V1 C LEFT JOIN dbo.MRQS_EXAMENES E "
				+ "ON C.NUMERO_EXAMEN=E.NUMERO WHERE E.NUMERO = "
				+ idExamen + " AND C.NUMERO_USUARIO = "+numeroCandExamen+"  ORDER BY C.NOMBRE_COMPLETO_USUARIO";

		System.out.println(strQuery);

		Query query = em.createNativeQuery(strQuery);
		return query.getResultList();
	}


}
