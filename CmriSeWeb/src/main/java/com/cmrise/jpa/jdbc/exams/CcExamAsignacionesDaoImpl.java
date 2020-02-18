package com.cmrise.jpa.jdbc.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.exams.CcExamAsignacionesDao;
import com.cmrise.jpa.dto.exams.CcExamAsignacionesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcExamAsignacionesDaoImpl implements CcExamAsignacionesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcExamAsignacionesDto pCcExamAsignacionesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_EXAM_ASSIGNACIONES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pCcExamAsignacionesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcExamAsignacionesDto.setCreadoPor((long)-1);
		pCcExamAsignacionesDto.setActualizadoPor((long)-1);
		pCcExamAsignacionesDto.setFechaCreacion(sqlsysdate);
		pCcExamAsignacionesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcExamAsignacionesDto);
		return longNumeroS; 
	}

	@Override
	public List<Object> findByNumeroExamenWD(long pNumeroExamen) {
		String strQuery = " SELECT ROW_NUMBER() OVER (ORDER BY CPH.TITULO ASC) ID\r" + 
				"      ,CEA.[NUMERO]\r" + 
				"      ,CEA.[NUMERO_CC_EXAMEN]\r" + 
				"      ,CEA.[NUMERO_CORE_CASE]\r" + 
				"      ,CEA.[FECHA_EFECTIVA_DESDE]\r" + 
				"      ,CEA.[FECHA_EFECTIVA_HASTA]\r" + 
				"      ,CEA.[CREADO_POR]\r" + 
				"      ,CEA.[FECHA_CREACION]\r" + 
				"      ,CEA.[ACTUALIZADO_POR]\r" + 
				"      ,CEA.[FECHA_ACTUALIZACION]\r" + 
				"	  ,CPH.NUMERO NUMERO_PREGUNTA\r" + 
				"	  ,CPH.TITULO TITULO_PREGUNTA\r" + 
				"	  ,CPH.ESTATUS\r" + 
				"	  ,TUV1.SIGNIFICADO ESTATUS_DESC\r" + 
				"	  ,CPH.TEMA_PREGUNTA\r" + 
				"	  ,TUV2.SIGNIFICADO TEMA_PREGUNTA_DESC\r" + 
				"      ,ISNULL(CPH.MAX_PUNTUACION,0.00) MAX_PUNTUACION\r" + 
				"	  ,CPH.ETIQUETAS\r" + 
				"	  ,CPH.TIPO_PREGUNTA\r" + 
				"	  ,TUV3.SIGNIFICADO TIPO_PREGUNTA_DESC\r" + 
				"  FROM [dbo].[CC_EXAM_ASIGNACIONES] CEA\r" + 
				"      ,[dbo].[CC_PREGUNTAS_HDR] CPH\r" + 
				"	  ,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV1\r" + 
				"	  ,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV2\r" + 
				"	  ,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV3\r" + 
				"   WHERE CEA.NUMERO_CORE_CASE = CPH.NUMERO_CC_HDR   \r" + 
				"    AND TUV1.TIPO_TABLA='ESTATUS_MRQ'\r" + 
				"    AND TUV1.CODIGO_TABLA = CPH.ESTATUS\r" + 
				"    AND TUV2.TIPO_TABLA='TEMA_DE_PREGUNTA'\r"+ 
				"    AND TUV2.CODIGO_TABLA = CPH.TEMA_PREGUNTA"+
				"    AND TUV3.TIPO_TABLA='TIPO_PREGUNTA'\r"+ 
				"    AND TUV3.CODIGO_TABLA = CPH.TIPO_PREGUNTA"+
			    "    AND CEA.[NUMERO_CC_EXAMEN] = "+pNumeroExamen; 
		
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

}
