package com.cmrise.jpa.jdbc.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.exams.MrqsGrupoHdrDao;
import com.cmrise.jpa.dto.exams.MrqsGrupoHdrDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsGrupoHdrDaoImpl implements MrqsGrupoHdrDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	
	@Override
	public long insert(MrqsGrupoHdrDto pMrqsGrupoHdrDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_GRUPO_HDR_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pMrqsGrupoHdrDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pMrqsGrupoHdrDto.setCreadoPor((long)-1);
		pMrqsGrupoHdrDto.setActualizadoPor((long)-1);
		pMrqsGrupoHdrDto.setFechaCreacion(sqlsysdate);
		pMrqsGrupoHdrDto.setFechaActualizacion(sqlsysdate);
		em.persist(pMrqsGrupoHdrDto);
		return longNumeroS; 
	}


	@Override
	public List<MrqsGrupoHdrDto> findByNumeroExamen(long pNumeroExamen) {
		String strQuery = "SELECT m FROM MrqsGrupoHdrDto m WHERE m.numeroExamen="+pNumeroExamen; 
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}


	@Override
	public MrqsGrupoHdrDto findByNumero(long pNumero) {
		return em.find(MrqsGrupoHdrDto.class, pNumero);
	}


	@Override
	public void delete(long pNumero) {
		MrqsGrupoHdrDto mrqsGrupoHdrDto = em.find(MrqsGrupoHdrDto.class, pNumero); 
		em.remove(mrqsGrupoHdrDto);
		
	}


	@Override
	public void update(long pNumero, MrqsGrupoHdrDto pMrqsGrupoHdrDto) {
		MrqsGrupoHdrDto mrqsGrupoHdrDto = em.find(MrqsGrupoHdrDto.class, pNumero); 
		mrqsGrupoHdrDto.setTitulo(pMrqsGrupoHdrDto.getTitulo());
		mrqsGrupoHdrDto.setTema(pMrqsGrupoHdrDto.getTema());
		mrqsGrupoHdrDto.setComentarios(pMrqsGrupoHdrDto.getComentarios());
	}

}
