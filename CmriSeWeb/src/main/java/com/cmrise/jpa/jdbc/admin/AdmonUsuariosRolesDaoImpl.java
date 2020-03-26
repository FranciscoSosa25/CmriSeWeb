package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonUsuariosRolesDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonUsuariosRolesDaoImpl implements AdmonUsuariosRolesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_USUARIOS_ROLES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonUsuariosRolesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pAdmonUsuariosRolesDto.setCreadoPor((long)-1);
		pAdmonUsuariosRolesDto.setActualizadoPor((long)-1);
		pAdmonUsuariosRolesDto.setFechaCreacion(sqlsysdate);
		pAdmonUsuariosRolesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pAdmonUsuariosRolesDto);
	}

	@Override
	public void delete(long pNumero) {
		AdmonUsuariosRolesDto admonUsuariosRolesDto = em.find(AdmonUsuariosRolesDto.class, pNumero);
		em.remove(admonUsuariosRolesDto);
	}

	@Override
	public void update(long pNumero, AdmonUsuariosRolesDto pAdmonUsuariosRolesDto) {
		AdmonUsuariosRolesDto admonUsuariosRolesDto = em.find(AdmonUsuariosRolesDto.class, pNumero);
		admonUsuariosRolesDto.setAdmonRole(pAdmonUsuariosRolesDto.getAdmonRole());
		admonUsuariosRolesDto.setAdmonUsuario(pAdmonUsuariosRolesDto.getAdmonUsuario());
		admonUsuariosRolesDto.setFechaEfectivaDesde(pAdmonUsuariosRolesDto.getFechaEfectivaDesde());
		admonUsuariosRolesDto.setFechaEfectivaHasta(pAdmonUsuariosRolesDto.getFechaEfectivaHasta());
	}

	@Override
	public List<AdmonUsuariosRolesV1Dto> findAll() {
		String strQuery = "SELECT a FROM AdmonUsuariosRolesV1Dto a"; 
		Query query = em.createQuery(strQuery);
		return query.getResultList();
	}

	@Override
	public int validaUsuarioRol(long pNumeroUsuario
			                   ,long pNumeroRol
			                   ) {
	    String strQuery ="SELECT COUNT(1)\r" + 
	          		     "  FROM [dbo].[ADMON_USUARIOS_ROLES]\r" + 
	    		         " WHERE [NUMERO_USUARIO] = "+pNumeroUsuario+"\r" + 
	    	 	         "  AND [NUMERO_ROL] ="+pNumeroRol ;
	    
	    Query query = em.createNativeQuery(strQuery); 
		Object object = query.getSingleResult(); 
		System.out.println(object);
		Integer integer = (Integer)query.getSingleResult(); 
		return integer.intValue();
	}

}
