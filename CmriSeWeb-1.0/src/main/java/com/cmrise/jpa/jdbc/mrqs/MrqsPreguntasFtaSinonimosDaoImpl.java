package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaSinonimosDao;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;
import com.cmrise.utils.CorrelacionColumnasInsertException;
import com.cmrise.utils.Utilitarios;
@Stateless
public class MrqsPreguntasFtaSinonimosDaoImpl implements MrqsPreguntasFtaSinonimosDao{
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME)
	EntityManager em;
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public long insert(List<MrqsPreguntasFtaSinonimos> sinonimos, long lNumeroFta,String texto) throws Exception {
		try {			
			Iterator<MrqsPreguntasFtaSinonimos> lista= sinonimos.iterator();
			while(lista.hasNext()) {
				MrqsPreguntasFtaSinonimos record=lista.next();
				establecerFechasColumnas(record,lNumeroFta,texto);
				if(record.isActualizado())em.merge(record);
				else em.persist(record);					
			}			
			em.flush();
		}
		catch(PersistenceException ex) {	
			throw new Exception();
		}
		
		return 1;
	}
	private void establecerFechasColumnas(MrqsPreguntasFtaSinonimos item,long lNumeroFta,String texto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_PREGUNTAS_FTA_SINONIMOS_S");
		BigInteger lNumero = (BigInteger) q.getSingleResult();
		item.setNumero(item.getNumero()==0?lNumero.longValue():item.getNumero());
		item.setActualizado(item.getNumero()==0?false:true);
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		item.setCreadoPor((long) -1);
		item.setActualizadoPor((long) -1);
		item.setFechaCreacion(new java.sql.Timestamp(sysdate.getTime()));
		item.setFechaActualizacion(new java.sql.Timestamp(sysdate.getTime()));
		item.setFechaEfectivaDesde(Utilitarios.startOfTime);
		item.setFechaEfectivaHasta(Utilitarios.endOfTime);
		item.setNumero_fta(lNumeroFta);
		item.setTextoPregunta(texto);
	}

	@Override
	public List<MrqsPreguntasFtaSinonimos> findByFta(long pNumeroFta) {
		List<MrqsPreguntasFtaSinonimos> resultSet = new ArrayList<MrqsPreguntasFtaSinonimos>();
		String strQuery = "SELECT m FROM MrqsPreguntasFtaSinonimos m WHERE m.numero_fta =" + pNumeroFta;
		try {
			Query query = em.createQuery(strQuery);
			resultSet = query.getResultList();
		} catch (NoResultException k) {
		}
		return resultSet;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void delete(MrqsPreguntasFtaSinonimos sinonimo) throws Exception {			
		try{	em.remove(em.contains(sinonimo) ? sinonimo : em.merge(sinonimo));
			em.flush();
		}
		catch(PersistenceException ex) {	
		throw new Exception();
		}
		
	}

}
