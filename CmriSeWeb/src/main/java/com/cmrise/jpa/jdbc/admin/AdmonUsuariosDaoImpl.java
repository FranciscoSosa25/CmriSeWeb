package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonUsuariosDao;
import com.cmrise.jpa.dto.admin.AdmonUsuariosDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonUsuariosDaoImpl implements AdmonUsuariosDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public void insert(AdmonUsuariosDto pAdmonUsuariosDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_USUARIOS_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonUsuariosDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pAdmonUsuariosDto.setCreadoPor((long)-1);
		pAdmonUsuariosDto.setActualizadoPor((long)-1);
		pAdmonUsuariosDto.setFechaCreacion(sqlsysdate);
		pAdmonUsuariosDto.setFechaActualizacion(sqlsysdate);
		em.persist(pAdmonUsuariosDto);
	}

}
