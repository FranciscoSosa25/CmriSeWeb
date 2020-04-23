package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonMateriaDao;
import com.cmrise.jpa.dto.admin.AdmonMateriaDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonMateriaDaoImpl implements AdmonMateriaDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(AdmonMateriaDto pAdmonMateriaDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_MATERIA_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonMateriaDto.setNumero(lNumeroS.longValue());
		em.persist(pAdmonMateriaDto);
		return lNumeroS.longValue();
	}

	@Override
	public void update(long numero, AdmonMateriaDto pAdmonMateriaDto) {
	}

	@Override
	public AdmonMateriaDto findByNumero(long numero) {
		return em.find(AdmonMateriaDto.class, numero);
	}

	@Override
	public List<AdmonMateriaDto> findAll() {
		String strQuery = "SELECT a FROM AdmonMateriaDto a"; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		AdmonMateriaDto admonMateriaDto = em.find(AdmonMateriaDto.class, pNumero); 
		em.remove(admonMateriaDto);
	}

}
