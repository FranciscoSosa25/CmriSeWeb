package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.cmrise.jpa.dao.mrqs.MrqsOpcionMultipleDao;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsOpcionMultipleDaoImpl implements MrqsOpcionMultipleDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_OPCION_MULTIPLE_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsOpcionMultipleDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsOpcionMultipleDto.setCreadoPor((long)-1);
		pMrqsOpcionMultipleDto.setActualizadoPor((long)-1);
		pMrqsOpcionMultipleDto.setFechaCreacion(sqlsysdate);
		pMrqsOpcionMultipleDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsOpcionMultipleDto);
		return lNumeroS.longValue(); 
	}

	@Override
	public List<MrqsOpcionMultipleDto> findByNumeroFta(long pNumeroFta) {
		String strQuery = "SELECT m FROM MrqsOpcionMultipleDto m WHERE m.numeroFta ="+pNumeroFta; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void update(long pNumero, MrqsOpcionMultipleDto pMrqsOpcionMultipleDto) {
		System.out.println("pNumero:"+pNumero);
		MrqsOpcionMultipleDto mrqsOpcionMultipleDto = em.find(MrqsOpcionMultipleDto.class, pNumero);
		mrqsOpcionMultipleDto.setEstatus(pMrqsOpcionMultipleDto.isEstatus());
		mrqsOpcionMultipleDto.setTextoExplicacion(pMrqsOpcionMultipleDto.getTextoExplicacion());
		mrqsOpcionMultipleDto.setTextoRespuesta(pMrqsOpcionMultipleDto.getTextoRespuesta());
		mrqsOpcionMultipleDto.setNumeroLinea(pMrqsOpcionMultipleDto.getNumeroLinea());
	}

	@Override
	public void delete(long pNumero) {
		MrqsOpcionMultipleDto mrqsOpcionMultipleDto = em.find(MrqsOpcionMultipleDto.class, pNumero);
		em.remove(mrqsOpcionMultipleDto);
	}

	@Override
	public void deleteByNumeroFta(long pNumeroFta) {
		List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto =  findByNumeroFta(pNumeroFta);
		for(MrqsOpcionMultipleDto mrqsOpcionMultipleDto: listMrqsOpcionMultipleDto) {
			em.remove(mrqsOpcionMultipleDto);
		}
		
	}

	@Override
	public void copyPaste(long pNumeroFtaOld
			            , long longpNumeroFtaCopy) {
		List<MrqsOpcionMultipleDto> listMrqsOpcionMultipleDto =  findByNumeroFta(pNumeroFtaOld);
		for(MrqsOpcionMultipleDto oldMrqsOpcionMultipleDto: listMrqsOpcionMultipleDto) {
			MrqsOpcionMultipleDto copyMrqsOpcionMultipleDto = new MrqsOpcionMultipleDto(); 
			copyMrqsOpcionMultipleDto.setNumeroFta(longpNumeroFtaCopy);
			copyMrqsOpcionMultipleDto.setTextoExplicacion(oldMrqsOpcionMultipleDto.getTextoExplicacion());
			copyMrqsOpcionMultipleDto.setTextoRespuesta(oldMrqsOpcionMultipleDto.getTextoRespuesta());
			copyMrqsOpcionMultipleDto.setFechaEfectivaDesde(oldMrqsOpcionMultipleDto.getFechaEfectivaDesde());
			copyMrqsOpcionMultipleDto.setFechaEfectivaHasta(oldMrqsOpcionMultipleDto.getFechaEfectivaHasta());
			insert(copyMrqsOpcionMultipleDto); 
		}
		
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
						  "  FROM [dbo].[MRQS_OPCION_MULTIPLE] MOM\r"+
						  "  WHERE MOM.[NUMERO_FTA] = "+pNumeroFta; 
		
	    if(pShuffleOrder) {
	    	strQuery =strQuery+" ORDER BY NEWID()"; 
	    }else {
	    	strQuery =strQuery+" ORDER BY NUMERO_LINEA ASC"; 
	    }
	    Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public int correctOrWrongAnswer(long pNumero, long pNumetoFta) {
		String strQuery = "SELECT COUNT(1)\r " + 
						  "  FROM [dbo].[MRQS_OPCION_MULTIPLE] MOM\r " + 
						  "  WHERE MOM.[NUMERO] = "+pNumero+"\r" +
						  "   AND MOM.[NUMERO_FTA] = "+pNumetoFta+
						  "   AND ESTATUS = 1"; 
		Query query = em.createNativeQuery(strQuery); 
		Object object = query.getSingleResult(); 
		System.out.println(object);
		Integer integer = (Integer)query.getSingleResult(); 
		return integer.intValue();
	}

}
