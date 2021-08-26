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
		pCcExamAsignacionesDto.setFechaCreacion(sqlsysdate);
		pCcExamAsignacionesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcExamAsignacionesDto);
		return longNumeroS; 
	}

	@Override
	public List<Object> findByNumeroExamenWD(long pNumeroExamen, long nNumeroCC) {
		String strQuery = " SELECT ROW_NUMBER() OVER (ORDER BY CPF.TITULO_PREGUNTA ASC) I\r\n" + 
				"	 ,CEA.[NUMERO]\r\n" + 
				"	 ,CEA.[NUMERO_CC_EXAMEN]\r\n" + 
				"	 ,CEA.[NUMERO_CORE_CASE]\r\n" + 
				"	 ,CEA.[FECHA_EFECTIVA_DESDE]\r\n" + 
				"	 ,CEA.[FECHA_EFECTIVA_HASTA]\r\n" + 
				"	 ,CEA.[CREADO_POR]\r\n" + 
				"	 ,CEA.[FECHA_CREACION]\r\n" + 
				"	 ,CEA.[ACTUALIZADO_POR]\r\n" + 
				"	 ,CEA.[FECHA_ACTUALIZACION]\r\n" + 
				"	 ,CPH.NUMERO NUMERO_PREGUNTA\r\n" + 
				"	 ,CPF.TITULO_PREGUNTA\r\n" + 
				"	 ,CPH.ESTATUS\r\n" + 
				"	 ,TUV1.SIGNIFICADO ESTATUS_DESC\r\n" + 
				"	 ,ISNULL(CPH.MAX_PUNTUACION,0.00) MAX_PUNTUACION\r\n" + 
				"	 ,CPH.ETIQUETAS\r\n" + 
				"	 ,CPH.TIPO_PREGUNTA\r\n" + 
				"	 ,TUV3.SIGNIFICADO TIPO_PREGUNTA_DESC\r\n" + 
				"FROM [dbo].[CC_EXAM_ASIGNACIONES] CEA\r\n" + 
				"	,[dbo].[CC_PREGUNTAS_HDR] CPH\r\n" + 
				"	,[dbo].[CC_PREGUNTAS_FTA] CPF\r\n" + 
				"	,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV1\r\n" + 
				"	,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV3\r\n" + 
				"WHERE CEA.NUMERO_CORE_CASE = CPH.NUMERO_CC_HDR  \r\n" + 
				"AND TUV1.TIPO_TABLA='ESTATUS_MRQ'\r\n" + 
				"AND TUV1.CODIGO_TABLA = CPH.ESTATUS\r\n" + 
				"AND TUV3.TIPO_TABLA='TIPO_PREGUNTA'\r\n" + 
				"AND TUV3.CODIGO_TABLA = CPH.TIPO_PREGUNTA\r\n" + 
				"AND CPF.NUMERO_HDR = CPH.NUMERO\r\n" + 
				"AND CEA.[NUMERO_CC_EXAMEN] = "+pNumeroExamen +
				"AND CEA.[NUMERO_CORE_CASE] = "+nNumeroCC;
		
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public List<CcExamAsignacionesDto> findByNumeroExamenDtos(long pNumeroCcExamen) {
		String strQuery = "SELECT c FROM CcExamAsignacionesDto c WHERE c.numeroCcExamen = "+pNumeroCcExamen; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}
	
	@Override
	public void delete(long pNumero) {
		CcExamAsignacionesDto ccExamAsignacionesDto = em.find(CcExamAsignacionesDto.class, pNumero);
		em.remove(ccExamAsignacionesDto);
	}
	
	@Override
	public List<Object> findByNumeroExamen(long pNumeroExamen) {
		String strQuery = " SELECT ROW_NUMBER() OVER (ORDER BY CPF.TITULO_PREGUNTA ASC) I\r\n" + 
				"	 ,CEA.[NUMERO]\r\n" + 
				"	 ,CEA.[NUMERO_CC_EXAMEN]\r\n" + 
				"	 ,CEA.[NUMERO_CORE_CASE]\r\n" + 
				"	 ,CEA.[FECHA_EFECTIVA_DESDE]\r\n" + 
				"	 ,CEA.[FECHA_EFECTIVA_HASTA]\r\n" + 
				"	 ,CEA.[CREADO_POR]\r\n" + 
				"	 ,CEA.[FECHA_CREACION]\r\n" + 
				"	 ,CEA.[ACTUALIZADO_POR]\r\n" + 
				"	 ,CEA.[FECHA_ACTUALIZACION]\r\n" + 
				"	 ,CPH.NUMERO NUMERO_PREGUNTA\r\n" + 
				"	 ,CPF.TITULO_PREGUNTA\r\n" + 
				"	 ,CPH.ESTATUS\r\n" + 
				"	 ,TUV1.SIGNIFICADO ESTATUS_DESC\r\n" + 
				"	 ,ISNULL(CPH.MAX_PUNTUACION,0.00) MAX_PUNTUACION\r\n" + 
				"	 ,CPH.ETIQUETAS\r\n" + 
				"	 ,CPH.TIPO_PREGUNTA\r\n" + 
				"	 ,TUV3.SIGNIFICADO TIPO_PREGUNTA_DESC\r\n" + 
				"FROM [dbo].[CC_EXAM_ASIGNACIONES] CEA\r\n" + 
				"	,[dbo].[CC_PREGUNTAS_HDR] CPH\r\n" + 
				"	,[dbo].[CC_PREGUNTAS_FTA] CPF\r\n" + 
				"	,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV1\r\n" + 
				"	,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV3\r\n" + 
				"WHERE CEA.NUMERO_CORE_CASE = CPH.NUMERO_CC_HDR  \r\n" + 
				"AND TUV1.TIPO_TABLA='ESTATUS_MRQ'\r\n" + 
				"AND TUV1.CODIGO_TABLA = CPH.ESTATUS\r\n" + 
				"AND TUV3.TIPO_TABLA='TIPO_PREGUNTA'\r\n" + 
				"AND TUV3.CODIGO_TABLA = CPH.TIPO_PREGUNTA\r\n" + 
				"AND CPF.NUMERO_HDR = CPH.NUMERO\r\n" + 
				"AND CEA.[NUMERO_CC_EXAMEN] = "+pNumeroExamen ;
		
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}


}
