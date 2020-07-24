package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.AdmonMateriaHdrDao;
import com.cmrise.jpa.dto.admin.AdmonMateriaHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonMateriaHdrDaoImpl implements AdmonMateriaHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(AdmonMateriaHdrDto pAdmonMateriaDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_MATERIA_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonMateriaDto.setNumero(lNumeroS.longValue());
		em.persist(pAdmonMateriaDto);
		return lNumeroS.longValue();
	}

	@Override
	public void update(long numero, AdmonMateriaHdrDto pAdmonMateriaDto) {
	}

	@Override
	public AdmonMateriaHdrDto findByNumero(long numero) {
		return em.find(AdmonMateriaHdrDto.class, numero);
	}

	@Override
	public List<AdmonMateriaHdrDto> findAll() {
		String strQuery = "SELECT a FROM AdmonMateriaHdrDto a"; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}
	
	

	@Override
	public void delete(long pNumero) {
		AdmonMateriaHdrDto admonMateriaHdrDto = em.find(AdmonMateriaHdrDto.class, pNumero); 
		em.remove(admonMateriaHdrDto);
	}

	@Override
	public List<Object> findByNumeroAdmonExamen(long pAdmonExamen) {
		String strQuery = "SELECT AMH.[NUMERO]\r" + 
						  "      ,AMH.[NOMBRE]\r" + 
						  "      ,AMH.[FECHA_EFECTIVA_DESDE]\r" + 
							"      ,AMH.[FECHA_EFECTIVA_HASTA]\r" + 
							"      ,AMH.[CREADO_POR]\r" + 
							"      ,AMH.[FECHA_CREACION]\r" + 
							"      ,AMH.[ACTUALIZADO_POR]\r" + 
							"      ,AMH.[FECHA_ACTUALIZACION]\r" + 
							"  FROM [dbo].[ADMON_MATERIA_HDR] AMH\r" + 
							"      ,[dbo].[ADMON_EXAMEN_LINE] AEL\r" + 
							"WHERE AEL.NUMERO_MATERIA = AMH.NUMERO\r" + 
							" AND GETDATE() BETWEEN AMH.FECHA_EFECTIVA_DESDE AND AMH.FECHA_EFECTIVA_HASTA\r" + 
							" AND GETDATE() BETWEEN AEL.FECHA_EFECTIVA_DESDE AND AEL.FECHA_EFECTIVA_HASTA\r" + 
							" AND AEl.NUMERO_EXAMEN = "+pAdmonExamen; 
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}

}
