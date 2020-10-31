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
				"      ,'Nombre Sin Implementar' NOMBRE\r" + 
				"      ,ME.[DESCRIPCION]\r" + 
				"      ,ME.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES TMP WHERE TMP.NUMERO_EXAMEN = ME.NUMERO AND TMP.TIPO='MRQS') TOTAL_CANDIDADATOS\r" + 
				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" +
				"	  ,ME.FECHA_EFECTIVA_HASTA\r" + 
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
				"      ,'Nombre Sin Implementar' NOMBRE\r" + 
				"      ,ME.[DESCRIPCION]\r" + 
				"      ,ME.[TIEMPO_LIMITE]\r" + 
				"	  ,(SELECT COUNT(1) FROM CAND_EXAMENES TMP WHERE TMP.NUMERO_EXAMEN = ME.NUMERO AND TMP.TIPO='MRQS') TOTAL_CANDIDADATOS\r" + 
				"	  ,'MRQs' TIPO_EXAMEN_DESC\r" + 
				"	  ,ME.FECHA_EFECTIVA_DESDE\r" + 
				"	  ,CE.TIPO TIPO_EXAMEN_CODE\r" +
				"	  ,ME.FECHA_EFECTIVA_HASTA\r" + 
				"  FROM [dbo].[MRQS_EXAMENES] ME\r" + 
				"      ,[dbo].[CAND_EXAMENES] CE\r" + 
				"  WHERE ME.NUMERO = CE.NUMERO_EXAMEN\r";
				//"    AND CE.TIPO = 'MRQS'\r" +
				//"	 AND CE.NUMERO_EXAMEN LIKE '%"+ idExamen+ "%' \r" +
				//"	 AND ME.FECHA_EFECTIVA_DESDE = "+ fechaDesde+ " \r" +
				//"	 AND ME.FECHA_EFECTIVA_HASTA = "+ fechaHasta+ " \r" +
				//	 AND ME.[TIEMPO_LIMITE] LIKE '%"+ tiempo+ "%' \r";
				/*" UNION\r" + 
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
				"    AND CE.TIPO = 'CORE_CASES'";*/
		if(idExamen!=0){
			strQuery += "AND CE.NUMERO_EXAMEN = "+ idExamen+ " \r" ;
		}
		if(!nombreExamen.equals("")) {
			strQuery += "AND ME.[DESCRIPCION] LIKE '%"+ nombreExamen+ "%' \r"; //Cambiar campo?
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

}
