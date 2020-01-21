package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonUsuariosRolesDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosRolesDto;
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

}
