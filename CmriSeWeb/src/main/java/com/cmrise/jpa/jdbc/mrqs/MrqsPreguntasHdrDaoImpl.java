package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasHdrDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsPreguntasHdrDaoImpl implements MrqsPreguntasHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_PREGUNTAS_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsPreguntasHdrDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsPreguntasHdrDto.setCreadoPor((long)-1);
		pMrqsPreguntasHdrDto.setActualizadoPor((long)-1);
		pMrqsPreguntasHdrDto.setFechaCreacion(sqlsysdate);
		pMrqsPreguntasHdrDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsPreguntasHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto =em.find(MrqsPreguntasHdrDto.class, pNumero);
		em.remove(mrqsPreguntasHdrDto);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto =em.find(MrqsPreguntasHdrDto.class, pNumero);
		mrqsPreguntasHdrDto.setEstatus(pMrqsPreguntasHdrDto.getEstatus());
		mrqsPreguntasHdrDto.setNombre(pMrqsPreguntasHdrDto.getNombre());
		mrqsPreguntasHdrDto.setTitulo(pMrqsPreguntasHdrDto.getTitulo());
		mrqsPreguntasHdrDto.setTipoPregunta(pMrqsPreguntasHdrDto.getTipoPregunta());
		mrqsPreguntasHdrDto.setTemaPregunta(pMrqsPreguntasHdrDto.getTemaPregunta());
		mrqsPreguntasHdrDto.setEtiquetas(pMrqsPreguntasHdrDto.getEtiquetas());
		mrqsPreguntasHdrDto.setComentarios(pMrqsPreguntasHdrDto.getComentarios());
		
	}

	@Override
	public List<MrqsPreguntasHdrV1Dto> findAll() {
		String strQuery ="SELECT m FROM MrqsPreguntasHdrV1Dto m";
		return em.createQuery(strQuery).getResultList();
	}

	@Override
	public MrqsPreguntasHdrV1Dto findByNumero(long pNumero) {
		MrqsPreguntasHdrV1Dto retval = null;
		String strQuery = "SELECT m FROM MrqsPreguntasHdrV1Dto m WHERE m.numero="+pNumero; 
		Query query = em.createQuery(strQuery); 
		retval = (MrqsPreguntasHdrV1Dto) query.getSingleResult();
		System.out.println("****");
		System.out.println("pNumero:"+pNumero);
		System.out.println(retval);
		return retval;
	}

	@Override
	public MrqsPreguntasHdrDto copyPaste(long pNumero) {
		MrqsPreguntasHdrDto copy =em.find(MrqsPreguntasHdrDto.class, pNumero);
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_PREGUNTAS_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		mrqsPreguntasHdrDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		mrqsPreguntasHdrDto.setCreadoPor((long)-1);
		mrqsPreguntasHdrDto.setActualizadoPor((long)-1);
		mrqsPreguntasHdrDto.setFechaCreacion(sqlsysdate);
		mrqsPreguntasHdrDto.setFechaActualizacion(sqlsysdate);		
		
		mrqsPreguntasHdrDto.setNombre(copy.getNombre());
		mrqsPreguntasHdrDto.setTitulo(copy.getTitulo());
		mrqsPreguntasHdrDto.setTipoPregunta(copy.getTipoPregunta());
		mrqsPreguntasHdrDto.setTemaPregunta(copy.getTemaPregunta());
		mrqsPreguntasHdrDto.setEtiquetas(copy.getEtiquetas());
		mrqsPreguntasHdrDto.setComentarios(copy.getComentarios());
		mrqsPreguntasHdrDto.setEstatus(copy.getEstatus());
		mrqsPreguntasHdrDto.setSociedad(copy.getSociedad());
		mrqsPreguntasHdrDto.setFechaEfectivaDesde(copy.getFechaEfectivaDesde());
		mrqsPreguntasHdrDto.setFechaEfectivaHasta(copy.getFechaEfectivaHasta());
		
		em.persist(mrqsPreguntasHdrDto);
		
		return mrqsPreguntasHdrDto;
	}

	@Override
	public MrqsPreguntasHdrV2Dto findV2ByNumeroHdr(long pNumeroHdr) {
		MrqsPreguntasHdrV2Dto mrqsPreguntasHdrV2Dto = em.find(MrqsPreguntasHdrV2Dto.class, pNumeroHdr); 
		return mrqsPreguntasHdrV2Dto;
	}

	@Override
	public List<Object> findWithFilterExam(long pNumeroExamen) {
		String strQuery ="SELECT [NUMERO]\r\n" + 
						"      ,[NOMBRE]\r" + 
						"      ,[TITULO]\r" + 
						"      ,[TIPO_PREGUNTA]\r" + 
						"      ,[TIPO_PREGUNTA_DESC]\r" + 
						"      ,[TEMA_PREGUNTA]\r" + 
						"      ,[TEMA_PREGUNTA_DESC]\r" + 
						"      ,[ETIQUETAS]\r" + 
						"      ,[COMENTARIOS]\r" + 
						"      ,[ESTATUS]\r" + 
						"      ,[ESTATUS_DESC]\r" + 
						"      ,[SOCIEDAD]\r" + 
						"      ,[FECHA_EFECTIVA_DESDE]\r" + 
						"      ,[FECHA_EFECTIVA_HASTA]\r" + 
						"      ,[CREADO_POR]\r" + 
						"      ,[FECHA_CREACION]\r" + 
						"      ,[ACTUALIZADO_POR]\r" + 
						"      ,[FECHA_ACTUALIZACION]\r" + 
						"  FROM [dbo].[MRQS_PREGUNTAS_HDR_V1]\r" + 
						" WHERE [NUMERO] NOT IN ( SELECT MGL.NUMERO_PREGUNTA\r" + 
						"						   FROM MRQS_GRUPO_LINES MGL\r" + 
						"							   ,MRQS_GRUPO_HDR MGH\r" + 
						"							   ,MRQS_EXAMENES ME\r" + 
						"						  WHERE ME.NUMERO = MGH.NUMERO_EXAMEN\r" + 
						"							AND MGH.NUMERO = MGL.NUMERO_HDR\r" + 
						"							AND ME.NUMERO = "+pNumeroExamen+"\r" + 
						"							)"; 
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

}
