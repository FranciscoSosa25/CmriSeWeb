
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
	public void insert(AdmonMaestroMateriaDto pAdmonMaestroMateriaDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_USUARIOS_ROLES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonMaestroMateriaDto.setNumero(lNumeroS.longValue());
		pAdmonMaestroMateriaDto.setEstatus(true);
		em.persist(pAdmonMaestroMateriaDto);
	}
	
	@Override
	public void deleteLogico(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto) {
		AdmonMaestroMateriaDto admonUsuariosRolesDto = em.find(AdmonMaestroMateriaDto.class, pNumero);
		admonUsuariosRolesDto.setEstatus(false);
	}
	
	@Override
	public void activaRolUsuario(long pNumero, AdmonMaestroMateriaDto pAdmonMaestroMateriaDto) {
		AdmonMaestroMateriaDto admonUsuariosRolesDto = em.find(AdmonMaestroMateriaDto.class, pNumero);
		admonUsuariosRolesDto.setEstatus(true);
	}
	
	@Override
	public int validaMaestroMateria(long pNumeroUsuario, long pNumeroMateria) {
	    String strQuery ="SELECT COUNT(1)\r" + 
	          		     "  FROM [dbo].[MAESTRO_HAS_MATERIA]\r" + 
	    		         " WHERE [ID_MAESTRO] = "+pNumeroUsuario+"\r" + 
	    	 	         "  AND [ID_MATERIA] ="+pNumeroMateria ;
	    
	    Query query = em.createNativeQuery(strQuery); 
		Object object = query.getSingleResult(); 
		System.out.println(object);
		Integer integer = (Integer)query.getSingleResult(); 
		return integer.intValue();
	}
	
	@Override
	public int validaMaestroMateriaID(long pNumeroUsuario, long pNumeroMateria) {
	    String strQuery ="SELECT NUMERO\r" + 
	          		     "  FROM [dbo].[MAESTRO_HAS_MATERIA]\r" + 
	    		         " WHERE [ID_MAESTRO] = "+pNumeroUsuario+"\r" + 
	    	 	         "  AND [ID_MATERIA] ="+pNumeroMateria ;
	    
	    try {
		    Query query = em.createNativeQuery(strQuery); 
			Object object = query.getSingleResult(); 
			System.out.println(object);
			java.math.BigInteger integer = (java.math.BigInteger)query.getSingleResult(); 
			return integer.intValue();
	    }
	    catch(Exception e) {
	    	return 0;
	    }

	}
	
	@Override
	public List<KeysDto> findKeysMaterias() {
		String strQuery = "SELECT new com.cmrise.jpa.dto.admin.KeysDto(a.numero,a.nombre) FROM AdmonMateriaHdrDto a ORDER BY a.nombre";
		Query q = em.createQuery(strQuery); 
		return q.getResultList();
	}

	
	@Override
	public List<Object> findKeysMateriasOfMaestros(long usuario){
		String strQuery = "select \r\n"
				+ "	am.NUMERO,  am.NOMBRE, mhm.ID_MATERIA\r\n"
				+ "	from dbo.ADMON_MATERIA_HDR am inner join dbo.MAESTRO_HAS_MATERIA mhm \r\n"
				+ "on am.NUMERO = mhm.ID_MATERIA\r\n"
				+ "where mhm.ID_MAESTRO = "+ usuario ;
		Query q = em.createNativeQuery(strQuery); 
		return q.getResultList();
	}
	
	@Override
	public void delete(long pNumero) {
		AdmonMaestroMateriaDto pAdmonMaestroMateriaDto = em.find(AdmonMaestroMateriaDto.class, pNumero);
		em.remove(pAdmonMaestroMateriaDto);
	}

}
