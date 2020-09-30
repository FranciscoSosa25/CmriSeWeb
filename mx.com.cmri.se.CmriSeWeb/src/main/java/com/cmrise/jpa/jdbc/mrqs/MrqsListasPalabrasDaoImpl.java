package com.cmrise.jpa.jdbc.mrqs;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.mrqs.MrqsListasPalabrasDao;
import com.cmrise.jpa.dto.mrqs.MrqsListasPalabrasDto;
import com.cmrise.jpa.dto.mrqs.MrqsOpcionMultipleDto;
import com.cmrise.utils.Utilitarios;


@Stateless
public class MrqsListasPalabrasDaoImpl implements MrqsListasPalabrasDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;

	@Override
	public long insert(MrqsListasPalabrasDto pMrqsListasPalabrasDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_LISTAS_PALABRAS_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pMrqsListasPalabrasDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsListasPalabrasDto.setCreadoPor((long)-1);
		pMrqsListasPalabrasDto.setActualizadoPor((long)-1);
		pMrqsListasPalabrasDto.setFechaCreacion(sqlsysdate);
		pMrqsListasPalabrasDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsListasPalabrasDto);
		return lNumeroS.longValue();
	}

	@Override
	public void update(long pNumero, MrqsListasPalabrasDto pMrqsListasPalabrasDto) {
		MrqsListasPalabrasDto mrqsListasPalabrasDto = em.find(MrqsListasPalabrasDto.class, pNumero);
		mrqsListasPalabrasDto.setPalabra(pMrqsListasPalabrasDto.getPalabra());
		mrqsListasPalabrasDto.setSinonimos(pMrqsListasPalabrasDto.getSinonimos());
	}

	@Override
	public List<MrqsListasPalabrasDto> findByFta(long pNumeroFta
			                                   , String pTipoRegistro) {
		String strQuery = "SELECT m FROM MrqsListasPalabrasDto m WHERE m.numeroFta ="+pNumeroFta+" AND m.tipoRegistro='"+pTipoRegistro+"'"; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		MrqsListasPalabrasDto mrqsListasPalabrasDto = em.find(MrqsListasPalabrasDto.class, pNumero);
		em.remove(mrqsListasPalabrasDto);
	}

	@Override
	public void deleteByNumeroFta(long numeroFta) {
		String strQuery = "SELECT m FROM MrqsListasPalabrasDto m WHERE m.numeroFta ="+numeroFta; 
		Query query = em.createQuery(strQuery); 
		List<MrqsListasPalabrasDto> listMrqsListasPalabrasDto = query.getResultList(); 
		for(MrqsListasPalabrasDto i:listMrqsListasPalabrasDto) {
			em.remove(i);
		}
	}
	
	
}
