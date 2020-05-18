package com.cmrise.jpa.jdbc.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.exams.MrqsExamenesDao;
import com.cmrise.jpa.dto.exams.MrqsExamenesV1Dto;
import com.cmrise.jpa.dto.exams.MrqsExamenesDto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class MrqsExamenesDaoImpl implements MrqsExamenesDao {

	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(MrqsExamenesDto pMrqsExamenesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.MRQS_EXAMENES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pMrqsExamenesDto.setNumero(lNumeroS.longValue());
		em.persist(pMrqsExamenesDto);
		return longNumeroS; 
	}

	@Override
	public List<MrqsExamenesDto> findAll() {
		String strQuery = "SELECT m FROM MrqsExamenesDto m";
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public List<MrqsExamenesV1Dto> findAllWD() {
		String strQuery = "SELECT m FROM MrqsExamenesV1Dto m";
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		MrqsExamenesDto mrqsExamenesDto = em.find(MrqsExamenesDto.class, pNumero); 
		em.remove(mrqsExamenesDto);
	}

	@Override
	public MrqsExamenesDto findById(long pNumero) {
		return em.find(MrqsExamenesDto.class, pNumero); 
	}

	@Override
	public void update(long pNumero, MrqsExamenesDto pMrqsExamenesDto) {
		MrqsExamenesDto mrqsExamenesDto  = em.find(MrqsExamenesDto.class, pNumero); 
		System.out.println();
		mrqsExamenesDto.setEstatus(pMrqsExamenesDto.getEstatus());
		mrqsExamenesDto.setDescripcion(pMrqsExamenesDto.getDescripcion());
		mrqsExamenesDto.setVisibilidad(pMrqsExamenesDto.getVisibilidad());
		mrqsExamenesDto.setFechaEfectivaDesde(pMrqsExamenesDto.getFechaEfectivaDesde());
		mrqsExamenesDto.setFechaEfectivaHasta(pMrqsExamenesDto.getFechaEfectivaHasta());
		mrqsExamenesDto.setTiempoLimite(pMrqsExamenesDto.getTiempoLimite());
		mrqsExamenesDto.setSaltarPreguntas(pMrqsExamenesDto.getSaltarPreguntas());
		mrqsExamenesDto.setSaltarCasos(pMrqsExamenesDto.getSaltarCasos());
		mrqsExamenesDto.setMostrarRespuestas(pMrqsExamenesDto.getMostrarRespuestas());
		mrqsExamenesDto.setAleatorioGrupo(pMrqsExamenesDto.getAleatorioGrupo());
		mrqsExamenesDto.setAleatorioPreguntas(pMrqsExamenesDto.getAleatorioPreguntas());
		mrqsExamenesDto.setSeleccionCasosAleatorios(pMrqsExamenesDto.getSeleccionCasosAleatorios());
		mrqsExamenesDto.setMensajeFinalizacion(pMrqsExamenesDto.getMensajeFinalizacion());
		
	}

	@Override
	public MrqsExamenesV1Dto findByNumeroWD(long pNumero) {
		String strQuery = "SELECT m FROM MrqsExamenesV1Dto m WHERE m.numero="+pNumero;
		Query query = em.createQuery(strQuery); 
		return (MrqsExamenesV1Dto)query.getSingleResult();
	}



}
