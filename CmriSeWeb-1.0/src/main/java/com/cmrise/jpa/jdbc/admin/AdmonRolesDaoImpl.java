package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonRolesDao;
import com.cmrise.jpa.dto.admin.AdmonRolesDto;
import com.cmrise.jpa.dto.admin.KeysDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonRolesDaoImpl implements AdmonRolesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(AdmonRolesDto pAdmonRolesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_ROLES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonRolesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pAdmonRolesDto.setCreadoPor((long)-1);
		pAdmonRolesDto.setActualizadoPor((long)-1);
		pAdmonRolesDto.setFechaCreacion(sqlsysdate);
		pAdmonRolesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pAdmonRolesDto);
	}

	@Override
	public List<AdmonRolesDto> findAll() {
		String strQuery = "SELECT a FROM AdmonRolesDto a";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}
	
	@Override
	public List<AdmonRolesDto> findCand() {
		String strQuery = "SELECT a FROM AdmonRolesDto a WHERE NUMERO='1'";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}
	
	@Override
	public List<AdmonRolesDto> findNotCand() {
		String strQuery = "SELECT a FROM AdmonRolesDto a WHERE NUMERO !='1'";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}
	
	@Override
	public void delete(long pNumero) {
		AdmonRolesDto admonRolesDto = em.find(AdmonRolesDto.class, pNumero); 
		em.remove(admonRolesDto);
	}

	@Override
	public void update(long pNumero, AdmonRolesDto pAdmonRolesDto) {
		AdmonRolesDto admonRolesDto = em.find(AdmonRolesDto.class, pNumero); 
		admonRolesDto.setNombre(pAdmonRolesDto.getNombre());
		admonRolesDto.setDescripcion(pAdmonRolesDto.getDescripcion());
		admonRolesDto.setFechaEfectivaDesde(pAdmonRolesDto.getFechaEfectivaDesde());
		admonRolesDto.setFechaEfectivaHasta(pAdmonRolesDto.getFechaEfectivaHasta());
	}

	@Override
	public List<KeysDto> findKeys() {
		String strQuery = "SELECT new com.cmrise.jpa.dto.admin.KeysDto(a.numero,a.nombre) FROM AdmonRolesDto a";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}
	
	@Override
	public List<KeysDto> findKeysCand() {
		String strQuery = "SELECT new com.cmrise.jpa.dto.admin.KeysDto(a.numero,a.nombre) FROM AdmonRolesDto a WHERE NUMERO='1'";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}
	
	@Override
	public List<KeysDto> findKeysNotCand() {
		String strQuery = "SELECT new com.cmrise.jpa.dto.admin.KeysDto(a.numero,a.nombre) FROM AdmonRolesDto a WHERE NUMERO != '1'";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}

}
