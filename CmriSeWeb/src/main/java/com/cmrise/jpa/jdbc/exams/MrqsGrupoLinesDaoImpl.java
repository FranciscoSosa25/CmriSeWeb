package com.cmrise.jpa.jdbc.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.exams.MrqsGrupoLinesDao;
import com.cmrise.jpa.dto.exams.MrqsGrupoLinesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsGrupoLinesDaoImpl implements MrqsGrupoLinesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(MrqsGrupoLinesDto pMrqsGrupoLinesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_GRUPO_LINES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pMrqsGrupoLinesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsGrupoLinesDto.setCreadoPor((long)-1);
		pMrqsGrupoLinesDto.setActualizadoPor((long)-1);
		pMrqsGrupoLinesDto.setFechaCreacion(sqlsysdate);
		pMrqsGrupoLinesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsGrupoLinesDto);
		return longNumeroS; 
	}

	@Override
	public List<MrqsGrupoLinesDto> findByNumeroHdr(long pNumeroHdr) {
		String strQuery = "SELECT m FROM MrqsGrupoLinesDto m WHERE m.numeroHdr="+pNumeroHdr; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

}
