package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	@Override
	public List<AdmonUsuariosDto> findTop500() {
		List<AdmonUsuariosDto> retval = new ArrayList<AdmonUsuariosDto>();
		String strQuery = "SELECT TOP (500) [NUMERO]\r\n" + 
							"      ,[NOMBRE]\r\n" + 
							"      ,[APELLIDO_PATERNO]\r\n" + 
							"      ,[APELLIDO_MATERNO]\r\n" + 
							"      ,[CONTRASENIA]\r\n" + 
							"      ,[CORREO_ELECTRONICO]\r\n" + 
							"      ,[FECHA_EFECTIVA_DESDE]\r\n" + 
							"      ,[FECHA_EFECTIVA_HASTA]\r\n" + 
							"      ,[CREADO_POR]\r\n" + 
							"      ,[FECHA_CREACION]\r\n" + 
							"      ,[ACTUALIZADO_POR]\r\n" + 
							"      ,[FECHA_ACTUALIZACION]\r\n" + 
							"  FROM [CMRISE].[dbo].[ADMON_USUARIOS]\r\n" + 
							"ORDER BY [FECHA_ACTUALIZACION] DESC \r\n" + 
							"        ,[NUMERO] DESC";
	    Query query = em.createNativeQuery(strQuery);
	    List<Object> listObject=query.getResultList();
	    Iterator<Object> iterObject = listObject.iterator();
	    while(iterObject.hasNext()) {
	    	Object result = iterObject.next();
	    	AdmonUsuariosDto admonUsuariosDto = new AdmonUsuariosDto();
	    	if(result instanceof Object[]) {
				Object[] row = (Object[]) result;
				if(row[0] instanceof BigInteger){
					admonUsuariosDto.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof String) {
					String strNombre = (String)row[1];
					admonUsuariosDto.setNombre(strNombre);
				}
				if(row[2] instanceof String) {
					String strApellidoPaterno = (String)row[2];
					admonUsuariosDto.setApellidoPaterno(strApellidoPaterno);
				}
				if(row[3] instanceof String) {
					String strApellidoMaterno = (String)row[3];
					admonUsuariosDto.setApellidoMaterno(strApellidoMaterno);
				}
				if(row[4] instanceof String) {
					admonUsuariosDto.setContrasenia((String)row[4]);
				}
				if(row[5] instanceof String) {
					String strCorreoElectronico = (String)row[5];
					admonUsuariosDto.setCorreoElectronico(strCorreoElectronico);
				}
				if(row[6] instanceof Date) {
					admonUsuariosDto.setFechaEfectivaDesde((Date)row[6]);
				}
				if(row[7] instanceof Date) {
					admonUsuariosDto.setFechaEfectivaHasta((Date)row[7]);
				}
	    	}	
	    	retval.add(admonUsuariosDto);
	    }
		return retval; 
	}

	@Override
	public void delete(long pNumero) {
		AdmonUsuariosDto admonUsuariosDto =	em.find(AdmonUsuariosDto.class, pNumero);
		em.remove(admonUsuariosDto);
	}

	@Override
	public void update(long pNumero, AdmonUsuariosDto pAdmonUsuariosDto) {
	 AdmonUsuariosDto admonUsuariosDto = em.find(AdmonUsuariosDto.class, pNumero);
	 if(null!=pAdmonUsuariosDto.getNombre()) {
	 admonUsuariosDto.setNombre(pAdmonUsuariosDto.getNombre());
	 }
	 admonUsuariosDto.setApellidoPaterno(pAdmonUsuariosDto.getApellidoPaterno());
	 admonUsuariosDto.setApellidoMaterno(pAdmonUsuariosDto.getApellidoMaterno());
	 admonUsuariosDto.setFechaEfectivaDesde(pAdmonUsuariosDto.getFechaEfectivaDesde());
	 admonUsuariosDto.setFechaEfectivaHasta(pAdmonUsuariosDto.getFechaEfectivaHasta());
	 admonUsuariosDto.setContrasenia(pAdmonUsuariosDto.getContrasenia());
	 admonUsuariosDto.setCorreoElectronico(pAdmonUsuariosDto.getCorreoElectronico());
		
	}

}
