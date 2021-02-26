
package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonMaestroMateriaDao;
import com.cmrise.jpa.dto.admin.AdmonMaestroMateriaDto;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonMaestroMateriaDaoImpl implements AdmonMaestroMateriaDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMAteriaDto) {
		em.persist(pAdmonMaestroMAteriaDto);
	}
	
	@Override
	public List<KeysDto> findKeysMaterias() {
		String strQuery = "SELECT new com.cmrise.jpa.dto.admin.KeysDto(a.numero,a.nombre) FROM AdmonMateriaHdrDto a ORDER BY a.nombre";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}

}
