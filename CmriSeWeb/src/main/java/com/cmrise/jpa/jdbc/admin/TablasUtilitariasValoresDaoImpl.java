package com.cmrise.jpa.jdbc.admin; 

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.admin.TablasUtilitariasValoresDao;
import com.cmrise.jpa.dto.admin.TablasUtilitariasValoresDto;
import com.cmrise.utils.Utilitarios;


@Stateless
public class TablasUtilitariasValoresDaoImpl implements TablasUtilitariasValoresDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	public void insert(TablasUtilitariasValoresDto pTablasUtilitariasValoresDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.TABLAS_UTILITARIAS_VALORES_S");
		BigInteger lTablasUtilitariasValoresS = (BigInteger)q.getSingleResult();
		pTablasUtilitariasValoresDto.setNumero(lTablasUtilitariasValoresS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pTablasUtilitariasValoresDto.setCreadoPor((short)-1);
		pTablasUtilitariasValoresDto.setActualizadoPor((short)-1);
		pTablasUtilitariasValoresDto.setFechaCreacion(sqlsysdate);
		pTablasUtilitariasValoresDto.setFechaActualizacion(sqlsysdate);
		
		  em.persist(pTablasUtilitariasValoresDto);
	}

	public List<TablasUtilitariasValoresDto> findByTipoTabla(String pTipoTabla) {
		String strQuery = "SELECT t FROM TablasUtilitariasValoresDto t where t.tipoTabla like '"+pTipoTabla+"'";
		Query query = em.createQuery(strQuery); 
		List<TablasUtilitariasValoresDto> retval = query.getResultList(); 
		return retval;
	}

	public void update(long pNumero, TablasUtilitariasValoresDto pTablasUtilitariasValoresDto) {
		TablasUtilitariasValoresDto tablasUtilitariasValoresDto =  em.find(TablasUtilitariasValoresDto.class, pNumero); 
		tablasUtilitariasValoresDto.setCodigoTabla(pTablasUtilitariasValoresDto.getCodigoTabla());
		tablasUtilitariasValoresDto.setDescripcion(pTablasUtilitariasValoresDto.getDescripcion());
		tablasUtilitariasValoresDto.setSignificado(pTablasUtilitariasValoresDto.getSignificado());
		tablasUtilitariasValoresDto.setFechaEfectivaDesde(pTablasUtilitariasValoresDto.getFechaEfectivaDesde());
		tablasUtilitariasValoresDto.setFechaEfectivaHasta(pTablasUtilitariasValoresDto.getFechaEfectivaHasta());
		tablasUtilitariasValoresDto.setEstado(pTablasUtilitariasValoresDto.getEstado());
	}

	
}
