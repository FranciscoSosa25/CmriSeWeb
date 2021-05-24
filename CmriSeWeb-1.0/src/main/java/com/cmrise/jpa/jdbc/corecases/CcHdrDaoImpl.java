package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.corecases.CcHdrV1;
import com.cmrise.ejb.model.corecases.CcPreguntasHdrV1;
import com.cmrise.jpa.dao.corecases.CcHdrDao;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.jpa.dto.corecases.CcHdrDto;
import com.cmrise.jpa.dto.corecases.CcHdrV1Dto;
import com.cmrise.jpa.dto.corecases.CcPreguntasHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcHdrDaoImpl implements CcHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(CcHdrDto pCcHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcHdrDto.setNumero(lNumeroS.longValue());
		em.persist(pCcHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		CcHdrDto ccHdrDto = em.find(CcHdrDto.class, pNumero);
		em.remove(ccHdrDto);
	}
	
	@Override
	public void deletePregunta(long pNumero) {
		CcPreguntasHdrDto ccPreguntasHdrDto = em.find(CcPreguntasHdrDto.class, pNumero);
		em.remove(ccPreguntasHdrDto);
	}



	@Override
	public List<CcHdrV1Dto> findAll() {
		String strQuery = "SELECT c FROM CcHdrV1Dto c";
		Query query = em.createQuery(strQuery);
		/*String strQuery = "SELECT * FROM CC_HDR_V1";
		Query query = em.createNativeQuery(strQuery);*/
		return query.getResultList();
	}

	@Override
	public CcHdrV1Dto findByNumero(long pNumero) {
		return em.find(CcHdrV1Dto.class, pNumero);
	}

	@Override
	public List<KeysDto> findKeys() {
		String strQuery = "SELECT new com.cmrise.jpa.dto.admin.KeysDto(c.numero,c.nombre) FROM CcHdrDto c";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}

	@Override
	public List<Object> findCoreCasesForExam(long pNumeroExamen) {
		String strQuery="SELECT [NUMERO]\r" + 
						"      ,[NOMBRE]\r" + 
						"      ,[ESTATUS]\r" + 
						"      ,[ESTATUS_DESC]\r" + 
						"	  ,[TEMA]\r" + 
						"	  ,[TEMA_DESC]\r" + 
						" FROM [dbo].[CC_HDR_V1]\r" + 
						"WHERE [NUMERO] NOT IN ( SELECT CEA.[NUMERO_CORE_CASE]\r" + 
						"                          FROM [dbo].[CC_EXAM_ASIGNACIONES] CEA\r" +
						"                         WHERE CEA.[NUMERO_CC_EXAMEN] ="+pNumeroExamen+"\r"+
						"                       )\r";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
	
	@Override
	public List<Object> findCCNotInExam(long pNumeroExamen, long tipoExamen) {
		String strQuery="SELECT CCV1.[NUMERO]\r" + 
						"     ,CCV1.DESCRIPCION_TECNICA\r" + 
						"     ,CCV1.ADMON_EXAMEN\r" + 
						"     ,CCV1.ADMON_EXAMEN_DESC\r" + 
						"	  ,CCV1.ADMON_MATERIA\r" + 
						"	  ,CCV1.ADMON_MATERIA_DESC\r" + 
						"	  ,CCV1.ADMON_SUBMATERIA\r" + 
						"	  ,CCV1.ADMON_SUBMATERIA_DESC\r" + 
						"	  ,CCV1.ESTATUS\r" +
						"	  ,CCV1.ESTATUS_DESC\r" +
						"	  ,CCV1.[FECHA_EFECTIVA_DESDE]\r" +
					    "     ,CCV1.[FECHA_EFECTIVA_HASTA]\r  "+
						"	  ,CCV1.[FECHA_CREACION]\r"
						+ "	FROM [dbo].[CC_HDR_V1] CCV1\r"
						+ "      ,[dbo].[CC_PREGUNTAS_HDR] CPH\r"
						+ "	  ,[dbo].[TABLAS_UTILITARIAS_VALORES] TUV1\r"
						+ "   WHERE CCV1.NUMERO = CPH.NUMERO_CC_HDR   \r"
						+ "    AND TUV1.TIPO_TABLA='ESTATUS_MRQ'\r"
						+ "    AND TUV1.CODIGO_TABLA = CPH.ESTATUS\r"
						+ "	   AND CCV1.ADMON_EXAMEN = " + tipoExamen + "\r"
						+ "	   AND CCV1.[NUMERO] NOT IN ( SELECT CEA.[NUMERO_CORE_CASE]\r"
						+ "			FROM [dbo].[CC_EXAM_ASIGNACIONES] CEA WHERE CEA.[NUMERO_CC_EXAMEN] = "+ pNumeroExamen +")\r"
						+ "	GROUP BY CCV1.NUMERO, CCV1.DESCRIPCION_TECNICA, CCV1.ADMON_EXAMEN, CCV1.ADMON_EXAMEN_DESC,\r"
						+ "			CCV1.ADMON_MATERIA, CCV1.ADMON_MATERIA_DESC, CCV1.ADMON_SUBMATERIA,\r"
						+ "			CCV1.ADMON_SUBMATERIA_DESC, CCV1.ESTATUS, CCV1.ESTATUS_DESC,  CCV1.FECHA_EFECTIVA_DESDE,\r"
						+ "			CCV1.FECHA_EFECTIVA_HASTA, CCV1.FECHA_CREACION";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
	
	@Override
	public List<Object> findCCInExam(long pNumeroExamen) {
		String strQuery="SELECT CCEA.NUMERO\r" + 
						"     ,CCV1.NUMERO NUMERO_CC\r" + 
						"     ,CCV1.ADMON_EXAMEN\r" + 
						"     ,CCV1.ADMON_EXAMEN_DESC\r" + 
						"	  ,CCV1.ADMON_MATERIA\r" + 
						"	  ,CCV1.ADMON_MATERIA_DESC\r" + 
						"	  ,CCV1.ADMON_SUBMATERIA\r" + 
						"	  ,CCV1.ADMON_SUBMATERIA_DESC\r" + 
						"	  ,CCV1.DESCRIPCION_TECNICA\r" + 
						"	  ,CCV1.ESTATUS\r" + 
						"	  ,CCV1.ESTATUS_DESC\r" + 
						"	  ,CCV1.FECHA_CREACION\r" + 
						" FROM CC_EXAM_ASIGNACIONES_V1 CCEA, [CC_HDR_V1] CCV1 \r" + 
						"WHERE CCEA.NUMERO_CORE_CASE = CCV1.NUMERO AND CCEA.NUMERO_CC_EXAMEN ="+ pNumeroExamen +"\r"+
						"GROUP BY CCEA.NUMERO, CCV1.NUMERO, CCV1.ADMON_EXAMEN, CCV1.ADMON_EXAMEN_DESC\r" +
						" , CCV1.ADMON_MATERIA, CCV1.ADMON_MATERIA_DESC, CCV1.ADMON_SUBMATERIA, CCV1.ADMON_SUBMATERIA_DESC"+
						" , CCV1.DESCRIPCION_TECNICA, CCV1.ESTATUS, CCV1.ESTATUS_DESC, CCV1.FECHA_CREACION\r";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void update(long pNumero, CcHdrDto pCcHdrDto) {
		CcHdrDto ccHdrDto = em.find(CcHdrDto.class, pNumero);	
		
		ccHdrDto.setEstatus(pCcHdrDto.getEstatus());
		ccHdrDto.setAdmonExamen(pCcHdrDto.getAdmonExamen());
		ccHdrDto.setAdmonMateria(pCcHdrDto.getAdmonMateria());
		ccHdrDto.setAdmonSubmateria(pCcHdrDto.getAdmonSubmateria());
		ccHdrDto.setEtiquetas(pCcHdrDto.getEtiquetas());
		ccHdrDto.setHistorialClinico(pCcHdrDto.getHistorialClinico());
		ccHdrDto.setDescripcionTecnica(pCcHdrDto.getDescripcionTecnica());
		ccHdrDto.setNota(pCcHdrDto.getNota());
	

	}
	

}
