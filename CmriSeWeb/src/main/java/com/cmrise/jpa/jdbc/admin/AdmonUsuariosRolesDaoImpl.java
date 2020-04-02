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

	@Override
	public int loginUsuarioRol(String pMatricula
			                 , String pRol
			                 , String pContrasenia
			                 ) {
		   String strQuery ="SELECT COUNT(1)\r" + 
      		     "  FROM [dbo].[ADMON_USUARIOS_ROLES_V1]\r" + 
		         " WHERE [MATRICULA] = '"+pMatricula+"'\r" + 
	 	         "  AND [NOMBRE_ROL] ='"+pRol+"'\r" + 
	 	         "  AND [CONTRASENIA] ='"+pContrasenia+"'";

			Query query = em.createNativeQuery(strQuery); 
			Object object = query.getSingleResult(); 
			System.out.println(object);
			Integer integer = (Integer)query.getSingleResult(); 
			return integer.intValue();
	}

	@Override
	public List<Object> findWithFilterExam(long   pNumeroExamen
			                              ,String pTipoExamen
			                              ) {
		String strQuery =   "SELECT [NUMERO]\r" + 
							"      ,[NUMERO_USUARIO]\r" + 
							"      ,[MATRICULA]\r" + 
							"      ,[NOMBRE_USUARIO]\r" + 
							"      ,[NOMBRE_COMPLETO_USUARIO]\r" + 
							"      ,[NUMERO_ROL]\r" + 
							"      ,[NOMBRE_ROL]\r" + 
							"      ,[DESCRIPCION_ROL]\r" + 
							"      ,[APELLIDO_PATERNO]\r" + 
							"      ,[APELLIDO_MATERNO]\r" + 
							"      ,[CORREO_ELECTRONICO]\r" + 
							"      ,[CONTRASENIA]\r" + 
							"  FROM [dbo].[ADMON_USUARIOS_ROLES_V1]\r" + 
							" WHERE NOMBRE_ROL = '"+Utilitarios.ROL_ALUMNO+"'\r" + 
							"   AND [NUMERO_USUARIO] NOT IN ( SELECT NUMERO_USUARIO\r" + 
							"								    FROM CAND_EXAMENES\r" + 
							"								   WHERE NUMERO_EXAMEN = "+pNumeroExamen+"\r" + 
							"								     AND TIPO = '"+pTipoExamen+"' \r" + 
							"									)";
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public AdmonUsuariosRolesV1Dto findLoginUsusarioRol(String pMatricula
			                                          , String pRol
			                                          , String pContrasenia
			                                          ) {
		String strQuery = "SELECT a FROM AdmonUsuariosRolesV1Dto a WHERE a.matricula='"+pMatricula+"' AND a.nombreRol='"+pRol+"' AND a.contrasenia='"+pContrasenia+"'"; 
		Query query = em.createQuery(strQuery); 
		return (AdmonUsuariosRolesV1Dto)query.getSingleResult();
	}

}
