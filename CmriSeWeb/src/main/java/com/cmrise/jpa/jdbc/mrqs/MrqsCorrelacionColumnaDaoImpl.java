package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import com.cmrise.jpa.dao.mrqs.MrqsCorrelacionColumnasDao;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasDto;
import com.cmrise.jpa.dto.mrqs.MrqsCorrelacionColumnasRespuestasDto;
import com.cmrise.utils.CorrelacionColumnasInsertException;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsCorrelacionColumnaDaoImpl implements MrqsCorrelacionColumnasDao {
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME)
	EntityManager em;
	@PersistenceUnit  
    private EntityManagerFactory emf;  

	

	@Override
	public void update(long pNumero, MrqsCorrelacionColumnasDto item) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MrqsCorrelacionColumnasDto> findByFta(long pNumeroFta) {
		List<MrqsCorrelacionColumnasDto> resultSet = new ArrayList<MrqsCorrelacionColumnasDto>();
		String strQuery = "SELECT m FROM MrqsCorrelacionColumnasDto m WHERE m.numero_fta =" + pNumeroFta;
		try {
			Query query = em.createQuery(strQuery);
			resultSet = query.getResultList();
		} catch (NoResultException k) {
		}
		return resultSet;
	}

	@Override
	public void delete(long pNumero) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MrqsCorrelacionColumnasRespuestasDto> findRespuestasCorrectasByFta(long pNumeroFta) {
		List<MrqsCorrelacionColumnasRespuestasDto> resultSet = new ArrayList<MrqsCorrelacionColumnasRespuestasDto>();
		String strQuery = "SELECT m FROM MrqsCorrelacionColumnasRespuestasDto m WHERE m.numero_fta =" + pNumeroFta;
		try {
			Query query = em.createQuery(strQuery);
			resultSet = query.getResultList();
		} catch (NoResultException k) {
		}
		return resultSet;
	}

	private void establecerFechasColumnas(MrqsCorrelacionColumnasDto item,long lNumeroFta) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_CORRELACION_COLUMNA_S");
		BigInteger lNumero = (BigInteger) q.getSingleResult();
		item.setNumero(lNumero.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		item.setCreadoPor((long) -1);
		item.setActualizadoPor((long) -1);
		item.setFechaCreacion(new java.sql.Timestamp(sysdate.getTime()));
		item.setFechaActualizacion(new java.sql.Timestamp(sysdate.getTime()));
		item.setFechaEfectivaDesde(Utilitarios.startOfTime);
		item.setFechaEfectivaHasta(Utilitarios.endOfTime);
		item.setNumero_fta(lNumeroFta);
	}
	
	private void establecerFechasRespuestas(MrqsCorrelacionColumnasRespuestasDto item,long lNumeroFta) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_CORRELACION_COLUMNA_RESPUESTAS_S");
		BigInteger lNumero = (BigInteger) q.getSingleResult();
		item.setNumero(lNumero.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		item.setCreadoPor((long) -1);
		item.setActualizadoPor((long) -1);
		item.setFechaCreacion(new java.sql.Timestamp(sysdate.getTime()));
		item.setFechaActualizacion(new java.sql.Timestamp(sysdate.getTime()));
		item.setFechaEfectivaDesde(Utilitarios.startOfTime);
		item.setFechaEfectivaHasta(Utilitarios.endOfTime);
		item.setNumero_fta(lNumeroFta);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public long insert(List<MrqsCorrelacionColumnasDto> respuestas,
		List<MrqsCorrelacionColumnasRespuestasDto> preguntas,long lNumeroFta)throws CorrelacionColumnasInsertException {
		EntityManager emtx = null;  
		try {			
			Iterator<MrqsCorrelacionColumnasDto> lista= respuestas.iterator();
			while(lista.hasNext()) {
				MrqsCorrelacionColumnasDto record=lista.next();
				establecerFechasColumnas(record,lNumeroFta);
				em.persist(record);				
			}
			Iterator<MrqsCorrelacionColumnasRespuestasDto> listaRespuestas=  preguntas.iterator();
			while(listaRespuestas.hasNext()) {
				MrqsCorrelacionColumnasRespuestasDto item=listaRespuestas.next();
				establecerFechasRespuestas(item,lNumeroFta);				
				em.persist(item);		
			}
			em.flush();
		}
		catch(PersistenceException ex) {	
			throw new CorrelacionColumnasInsertException(CorrelacionColumnasInsertException.ERROR_INSERTAR,ex);
		}
		
		return 1;
	}

}
