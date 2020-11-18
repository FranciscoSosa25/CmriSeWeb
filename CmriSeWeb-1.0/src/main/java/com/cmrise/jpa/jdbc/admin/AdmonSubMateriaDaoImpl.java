package com.cmrise.jpa.jdbc.admin;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.cmrise.jpa.dao.admin.AdmonSubMateriaDao;
import com.cmrise.jpa.dto.admin.AdmonSubMateriaDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class AdmonSubMateriaDaoImpl implements AdmonSubMateriaDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(AdmonSubMateriaDto pAdmonSubMateriaDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.ADMON_SUBMATERIA_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		pAdmonSubMateriaDto.setNumero(lNumeroS.longValue());
		em.persist(pAdmonSubMateriaDto);
		return lNumeroS.longValue();
	}

	@Override
	public List<AdmonSubMateriaDto> findAll() {
		String strQuery = "SELECT a FROM AdmonSubMateriaDto a WHERE :pCurrentDate BETWEEN fechaEfectivaDesde and fechaEfectivaHasta"; 
		Query query = em.createQuery(strQuery); 
		query.setParameter("pCurrentDate",new java.util.Date(),TemporalType.DATE); 
		return query.getResultList();
	}

	@Override
	public AdmonSubMateriaDto findByNumero(long numero) {
		return em.find(AdmonSubMateriaDto.class, numero);
	}

	@Override
	public List<Object> findByNumeroExamen(long pAdmonMateria) {
		String strQuery = " SELECT A.[NUMERO]\r" + 
						  "      ,A.[NOMBRE]\r" + 
						  "      ,A.[FECHA_EFECTIVA_DESDE]\r" + 
						  "      ,A.[FECHA_EFECTIVA_HASTA]\r" + 
						  "      ,A.[CREADO_POR]\r" + 
						  "      ,A.[FECHA_CREACION]\r" + 
						  "      ,A.[ACTUALIZADO_POR]\r" + 
						  "      ,A.[FECHA_ACTUALIZACION]\r" + 
						  "  FROM [dbo].[ADMON_SUBMATERIA] A\r" + 
						  "      ,[dbo].[ADMON_MATERIA_LINE] AML\r" + 
						  " WHERE AML.NUMERO_SUBMATERIA = A.NUMERO\r" + 
						  " AND GETDATE() BETWEEN A.FECHA_EFECTIVA_DESDE AND A.FECHA_EFECTIVA_HASTA\r" + 
						  " AND GETDATE() BETWEEN AML.FECHA_EFECTIVA_DESDE AND AML.FECHA_EFECTIVA_HASTA\r" + 
						  " AND AML.NUMERO_MATERIA_HDR = "+pAdmonMateria; 
		Query query = em.createNativeQuery(strQuery); 
		return query.getResultList();
	}
	

}
