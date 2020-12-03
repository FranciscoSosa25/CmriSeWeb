package com.cmrise.jpa.jdbc.corecases;

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

import com.cmrise.jpa.dao.corecases.CcPreguntasFtaSinonimosDao;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaSinonimosDao;
import com.cmrise.jpa.dto.corecases.CcPreguntasFtaSinonimos;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasFtaSinonimos;
import com.cmrise.utils.Utilitarios;
@Stateless
public class CcPreguntasFtaSinonimosDaoImpl implements CcPreguntasFtaSinonimosDao {
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME)
	EntityManager em;
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public long insert(List<CcPreguntasFtaSinonimos> sinonimos, long lNumeroFta, String texto) throws Exception {
		try {			
			Iterator<CcPreguntasFtaSinonimos> lista= sinonimos.iterator();
			while(lista.hasNext()) {
				CcPreguntasFtaSinonimos record=lista.next();
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

	@Override
	public List<CcPreguntasFtaSinonimos> findByFta(long pNumeroFta) {
		List<CcPreguntasFtaSinonimos> resultSet = new ArrayList<CcPreguntasFtaSinonimos>();
		String strQuery = "SELECT m FROM CcPreguntasFtaSinonimos m WHERE m.numero_fta =" + pNumeroFta;
		try {
			Query query = em.createQuery(strQuery);
			resultSet = query.getResultList();
		} catch (NoResultException k) {
		}
		return resultSet;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void delete(CcPreguntasFtaSinonimos sinonimo) throws Exception {
		try{	em.remove(em.contains(sinonimo) ? sinonimo : em.merge(sinonimo));
		em.flush();
	}
	catch(PersistenceException ex) {	
	throw new Exception();
	}
		
	}

	
	
	
	

	private void establecerFechasColumnas(CcPreguntasFtaSinonimos item,long lNumeroFta,String texto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_PREGUNTAS_FTA_SINONIMOS_S");
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

	
}
