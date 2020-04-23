package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.admin.AdmonExamenHdr;
import com.cmrise.jpa.dao.admin.AdmonExamenHdrDao;
import com.cmrise.jpa.dto.admin.AdmonExamenHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonExamenHdrDaoImpl implements AdmonExamenHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(AdmonExamenHdrDto pAdmonExamenHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_EXAMEN_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonExamenHdrDto.setNumero(lNumeroS.longValue());
		em.persist(pAdmonExamenHdrDto);
		return lNumeroS.longValue();
	}

	@Override
	public List<AdmonExamenHdrDto> findAll() {
		String strQuery = "SELECT a FROM AdmonExamenHdrDto a"; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public AdmonExamenHdrDto findByNumero(long pNumero) {
		AdmonExamenHdrDto AdmonExamenHdrDto = em.find(AdmonExamenHdrDto.class, pNumero); 
		return AdmonExamenHdrDto;
	}

}
