package com.cmrise.jpa.jdbc.corecases;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.corecases.CcOpcionMultipleDao;
import com.cmrise.jpa.dto.corecases.CcOpcionMultipleDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcOpcionMultipleDaoImpl implements CcOpcionMultipleDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcOpcionMultipleDto pCcOpcionMultipleDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_OPCION_MULTIPLE_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pCcOpcionMultipleDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcOpcionMultipleDto.setCreadoPor((long)-1);
		pCcOpcionMultipleDto.setActualizadoPor((long)-1);
		pCcOpcionMultipleDto.setFechaCreacion(sqlsysdate);
		pCcOpcionMultipleDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcOpcionMultipleDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<CcOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		String strQuery = "SELECT c FROM CcOpcionMultipleDto c WHERE c.numeroFta ="+pNumeroFta; 
		
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}
	@Override
	public List<CcOpcionMultipleDto> findByNumeroFtaAleatorio(long pNumeroFta) {
		String strQuery = "SELECT c FROM CcOpcionMultipleDto c WHERE c.numeroFta ="+pNumeroFta; 
		strQuery+=" ORDER BY RAND(CHECKSUM(NEWID()))";
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}
	@Override
	public int correctOrWrongAnswer(long pNumero, long pNumetoFta) {
		String strQuery = "SELECT COUNT(1)\r " + 
						  "  FROM [dbo].[CC_OPCION_MULTIPLE] MOM\r " + 
						  "  WHERE MOM.[NUMERO] = "+pNumero+"\r" +
						  "   AND MOM.[NUMERO_FTA] = "+pNumetoFta+
						  "   AND ESTATUS = 1"; 
		Query query = em.createNativeQuery(strQuery); 
		Object object = query.getSingleResult(); 
		System.out.println(object);
		Integer integer = (Integer)query.getSingleResult(); 
		return integer.intValue();
	}
	@Override
	public List<Object> findByNumeroFtaShuffleOrder(long    pNumeroFta
                                                   ,boolean pShuffleOrder
                                                   ) {
		String strQuery = "SELECT MOM.[NUMERO]\r" + 
						  "      ,MOM.[NUMERO_FTA]\r" + 
						  "      ,MOM.[ESTATUS]\r" + 
						  "      ,MOM.[TEXTO_RESPUESTA]\r" + 
						  "      ,MOM.[TEXTO_EXPLICACION]\r" + 
						  "      ,MOM.[FECHA_EFECTIVA_DESDE]\r" + 
						  "      ,MOM.[FECHA_EFECTIVA_HASTA]\r" + 
						  "      ,MOM.[CREADO_POR]\r" + 
						  "      ,MOM.[FECHA_CREACION]\r" + 
						  "      ,MOM.[ACTUALIZADO_POR]\r" + 
						  "      ,MOM.[FECHA_ACTUALIZACION]\r" + 
						  "      ,MOM.[NUMERO_LINEA]\r" + 
						  "  FROM [dbo].[CC_OPCION_MULTIPLE] MOM\r"+
						  "  WHERE MOM.[NUMERO_FTA] = "+pNumeroFta; 
		
	    if(pShuffleOrder) {
	    	strQuery =strQuery.concat(" ORDER BY RAND(CHECKSUM(NEWID()))");
	    }else {
	    	strQuery =strQuery+" ORDER BY NUMERO_LINEA ASC"; 
	    }
	    Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public int totalCorrectAnswers(long pNumeroFta) {
		String strQuery = "SELECT COUNT(1)\r " + 
				  "  FROM [dbo].[CC_OPCION_MULTIPLE] MOM\r " + 
				  "  WHERE 1=1 \r" +
				  "   AND MOM.[NUMERO_FTA] = "+pNumeroFta+
				  "   AND ESTATUS = 1"; 
		Query query = em.createNativeQuery(strQuery); 
		Object object = query.getSingleResult(); 
		System.out.println(object);
		Integer integer = (Integer)query.getSingleResult(); 
		return integer.intValue();
	}

	@Override
	public void update(long pNumero, CcOpcionMultipleDto pCcOpcionMultipleDto) {
		System.out.println("pNumero:"+pNumero);
		CcOpcionMultipleDto ccOpcionMultipleDto = em.find(CcOpcionMultipleDto.class, pNumero);
		ccOpcionMultipleDto.setEstatus(pCcOpcionMultipleDto.isEstatus());
		ccOpcionMultipleDto.setTextoExplicacion(pCcOpcionMultipleDto.getTextoExplicacion());
		ccOpcionMultipleDto.setTextoRespuesta(pCcOpcionMultipleDto.getTextoRespuesta());
		ccOpcionMultipleDto.setNumeroLinea(pCcOpcionMultipleDto.getNumeroLinea());
	}

	@Override
	public void delete(long pNumero) {
		CcOpcionMultipleDto ccOpcionMultipleDto = em.find(CcOpcionMultipleDto.class, pNumero);
		em.remove(ccOpcionMultipleDto);
	}

}
