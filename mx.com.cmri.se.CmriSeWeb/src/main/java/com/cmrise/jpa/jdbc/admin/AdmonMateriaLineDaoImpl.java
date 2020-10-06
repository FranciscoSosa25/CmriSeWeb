package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonMateriaLineDao;
import com.cmrise.jpa.dto.admin.AdmonMateriaLineDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonMateriaLineDaoImpl implements AdmonMateriaLineDao{

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	
	@Override
	public long insert(AdmonMateriaLineDto pAdmonMateriaLineDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_MATERIA_LINE_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonMateriaLineDto.setNumero(lNumeroS.longValue());
		em.persist(pAdmonMateriaLineDto);
		return lNumeroS.longValue();
	}


	@Override
	public List<AdmonMateriaLineDto> findByNumeroMateria(long numeroAdmonMateria) {
		String strQuery = "SELECT a FROM AdmonMateriaLineDto a WHERE numeroMateriaHdr = "+numeroAdmonMateria; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

}
