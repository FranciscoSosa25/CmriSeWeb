package com.cmrise.jpa.jdbc.candidates.exams;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.cmrise.jpa.dao.candidates.exams.CandExamenesDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamRespSkipDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CandExamenesDaoImpl implements CandExamenesDao {
	
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CandExamenesDto pCandExamenesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CAND_EXAMENES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCandExamenesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCandExamenesDto.setCreadoPor(pCandExamenesDto.getCreadoPor());
		pCandExamenesDto.setActualizadoPor(pCandExamenesDto.getActualizadoPor());
		pCandExamenesDto.setFechaCreacion(sqlsysdate);
		pCandExamenesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCandExamenesDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<CandExamenesV2Dto> findByExamen(long pNumeroExamen, String pTipoExamen) {
		String strQuery ="SELECT c FROM CandExamenesV2Dto c WHERE c.numeroExamen ="+pNumeroExamen+" AND c.tipo='"+pTipoExamen+"'";
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		CandExamenesDto candExamenesDto = em.find(CandExamenesDto.class, pNumero); 
		em.remove(candExamenesDto);
	}

	@Override
	public List<CandExamenesV2Dto> findByUsuario(long pNumeroUsuario) {
		Date date = new Date();
		Object today = new java.sql.Timestamp(date.getTime());
		String strQuery="SELECT c FROM CandExamenesV2Dto c WHERE c.numeroUsuario="+pNumeroUsuario;
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}
	
	@Override
	public List<CandExamenesV2Dto> findByUsuarioOnlyEfectiveDates(long pNumeroUsuario) {
		Date date = new Date();
		Object today = new java.sql.Timestamp(date.getTime());
		String strQuery="SELECT c FROM CandExamenesV2Dto c WHERE c.numeroUsuario="+pNumeroUsuario +" AND CONVERT(DATETIME,'"+ today +"',121) BETWEEN c.fechaEfectivaDesdeExamen AND c.fechaEfectivaHastaExamen ";
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}
	
	@Override
	public List<CandExamenesV2Dto> findAll() {
		String strQuery="SELECT c FROM CandExamenesV2Dto c"; 
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}

	@Override
	public CandExamenesV1Dto findByNumero(long pNumero) {
		String strQuery="SELECT c FROM CandExamenesV1Dto c WHERE c.numero="+pNumero; 
		Query query = em.createQuery(strQuery);
		return (CandExamenesV1Dto)query.getSingleResult(); 
	}

	@Override
	public List<CandExamenesV2Dto> findByCURP(String pCurp, String pNombreUsuario,String pApellidoPaterno,String pApellidoMaterno) {
		String strQuery="SELECT c FROM CandExamenesV2Dto c WHERE c.curp like '%"+pCurp+"%' ";
		if(pNombreUsuario!=null && !"".endsWith(pNombreUsuario)) {
			strQuery += "AND c.numeroUsuario like '%"+pNombreUsuario+"%'  ";
		}
		
			//	+ "AND C.apellidoPaterno like '%"+pApellidoPaterno+"%'AND C.apellidoMaterno like '%"+pApellidoMaterno+"%'"; 
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}
	
	
	public List<Object> findAllByCandidate(long pNumeroUsuario, String matricula, String cCurp){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String strQuery = "SELECT \r\n" + 
				"NUMERO_USUARIO , CURP , NOMBRE_COMPLETO_USUARIO , [MATRICULA] ,COUNT(NUMERO_USUARIO) EXAM_COUNT\r\n" + 
				"\r\n" + 
				"FROM( \r\n" + 
				"	(SELECT CE.[NUMERO]       \r\n" + 
				"	  ,CE.[NUMERO_USUARIO]\r\n" + 
				"      ,CE.[NUMERO_EXAMEN]\r\n" + 
				"	  ,AURV1.[NOMBRE_USUARIO]\r\n" + 
				"	  ,AURV1.[MATRICULA]\r\n" + 
				"      ,AURV1.[NOMBRE_COMPLETO_USUARIO]\r\n" + 
				"	  ,AURV1.[CURP]\r\n" + 
				"  FROM [dbo].[CAND_EXAMENES] CE\r\n" + 
				"      ,[dbo].[MRQS_EXAMENES] ME\r\n" + 
				"	  ,[dbo].[ADMON_USUARIOS_ROLES_V1] AURV1\r\n" + 
				"	  ,[dbo].ADMON_USUARIOS AD\r\n" + 
				"  WHERE CE.TIPO = 'MRQS' AND CE.NUMERO_USUARIO = AURV1.NUMERO_USUARIO \r\n" + 
				" AND   ME.NUMERO = CE.[NUMERO_EXAMEN] AND AD.NUMERO = AURV1.NUMERO_USUARIO\r\n" + 
				" AND CE.[ESTATUS] IN ('ASIGNADO','REVISADO'))\r\n" + 
				" \r\n" + 
				" UNION \r\n" + 
				" (SELECT CE.[NUMERO]\r\n" + 
				"	  ,CE.[NUMERO_USUARIO]\r\n" + 
				"      ,CE.[NUMERO_EXAMEN]\r\n" + 
				"	  ,AURV1.[NOMBRE_USUARIO]\r\n" + 
				"	  ,AURV1.[MATRICULA]\r\n" + 
				"      ,AURV1.[NOMBRE_COMPLETO_USUARIO]\r\n" + 
				"	  ,AURV1.[CURP]\r\n" + 
				"  FROM [dbo].[CAND_EXAMENES] CE\r\n" + 
				"      ,[dbo].[CC_EXAMENES] CCE\r\n" + 
				"	  ,[dbo].[ADMON_USUARIOS_ROLES_V1] AURV1\r\n" + 
				"	  ,[dbo].ADMON_USUARIOS AD\r\n" + 
				"  WHERE CE.TIPO = 'CORE_CASES' AND CE.NUMERO_USUARIO = AURV1.NUMERO_USUARIO\r\n" + 
				" AND   CCE.NUMERO = CE.[NUMERO_EXAMEN] AND AD.NUMERO = AURV1.NUMERO_USUARIO\r\n" + 
				" AND CE.[ESTATUS] IN ('ASIGNADO','REVISADO') )\r\n" + 
				" )\r\n" + 
				"\r\n" + 
				"C_EXMA \r\n WHERE 0=0"; 
				
		if(pNumeroUsuario > 0) {
			strQuery += " AND NUMERO_USUARIO ="+pNumeroUsuario+" ";
		}
		
		if(!StringUtils.isEmpty(matricula)) {
			strQuery += " AND MATRICULA LIKE '"+matricula+"'";
		}
		
		if(!StringUtils.isEmpty(cCurp)) {
			strQuery += " AND CURP LIKE '"+cCurp+"' ";
		}
				
				
				
		strQuery +=	" GROUP BY CURP, NOMBRE_COMPLETO_USUARIO, [MATRICULA], NUMERO_USUARIO \r\n" + 
				"ORDER by C_EXMA.NOMBRE_COMPLETO_USUARIO\r\n" + 
				"";

		System.out.println(strQuery);
		
		

		Query query = em.createNativeQuery(strQuery);
		return query.getResultList();
	}
	@Override
	public void updateEstatus(long pNumero, CandExamenesDto pCandExamenesDto) {
		CandExamenesDto candExamenesDto = em.find(CandExamenesDto.class, pNumero); 
		candExamenesDto.setEstatus("REVISADO");
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		candExamenesDto.setFechaActualizacion(sqlsysdate);
	}

	@Override
	public List<CandExamenesV2Dto> findCandidateByExam(String cCurp, String cNombre, String c_aPaterno, String c_aMaterno, String actPor, String fechaActu
			, long pNumeroExamen, String pTipoExamen) {
				 
		String strQuery = "SELECT c FROM CandExamenesV2Dto c WHERE c.curp like '%"+cCurp.trim()+"%' AND C.nombreUsuario like '%"+cNombre.trim()+"%' AND C.apellidoPaterno like '%"+
				c_aPaterno.trim()+"%' AND C.apellidoMaterno like '%"+c_aMaterno.trim()+"%'";
		
		if(actPor.length()!= 0)
			strQuery = strQuery + " AND c.nombreActualizadoPor like '%"+actPor.trim()+"%'";
		
		if(fechaActu.length()!= 0) {
			strQuery = strQuery + " AND convert(varchar(25),c.fechaActualizacion,126) like '%"+fechaActu.trim()+"%'";
		}
		
		strQuery = strQuery + " AND c.numeroExamen ="+pNumeroExamen+" AND c.tipo='"+pTipoExamen+"'";
		
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}
	
	@Override
	public CandExamenesV2Dto findByNumeroV2(long pNumero) {
		String strQuery="SELECT c FROM CandExamenesV2Dto c WHERE c.numero=" + pNumero; 
		Query query = em.createQuery(strQuery);
		return (CandExamenesV2Dto)query.getSingleResult();
	}
}
