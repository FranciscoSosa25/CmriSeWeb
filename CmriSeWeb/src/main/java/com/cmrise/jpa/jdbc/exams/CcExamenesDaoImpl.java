package com.cmrise.jpa.jdbc.exams;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.jpa.dao.exams.CcExamenesDao;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;
import com.cmrise.utils.Utilitarios;

@Stateless
public class CcExamenesDaoImpl implements CcExamenesDao {
	
	@PersistenceContext(unitName = Utilitarios.PCUNITNAME) 
	EntityManager em;
	
	@Override
	public long insert(CcExamenesDto pCcExamenesDto) {
		Query q = em.createNativeQuery("SELECT NEXT VALUE FOR dbo.CC_EXAMENES_S");
		BigInteger lNumeroS = (BigInteger)q.getSingleResult();
		long longNumeroS = lNumeroS.longValue(); 
		pCcExamenesDto.setNumero(lNumeroS.longValue());
		java.util.Date sysdate = new java.util.Date();
		java.sql.Timestamp sqlsysdate = new java.sql.Timestamp(sysdate.getTime());
		pCcExamenesDto.setCreadoPor((long)-1);
		pCcExamenesDto.setActualizadoPor((long)-1);
		pCcExamenesDto.setFechaCreacion(sqlsysdate);
		pCcExamenesDto.setFechaActualizacion(sqlsysdate);
		em.persist(pCcExamenesDto);
		return longNumeroS; 
	}

	@Override
	public List<CcExamenesDto> findAll() {
		String strQuery = "SELECT c FROM CcExamenesDto c";
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public List<CcExamenesV1Dto> findAllWD() {
		String strQuery = "SELECT c FROM CcExamenesV1Dto c";
		Query query = em.createQuery(strQuery); 
		return query.getResultList();
	}

	@Override
	public void delete(long pNumero) {
		CcExamenesDto ccExamenesDto = em.find(CcExamenesDto.class, pNumero); 
		em.remove(ccExamenesDto);
	}

	@Override
	public CcExamenesDto findById(long pNumero) {
		return em.find(CcExamenesDto.class, pNumero); 
	}

	@Override
	public void update(long pNumero, CcExamenesDto pCcExamenesDto) {
		CcExamenesDto ccExamenesDto  = em.find(CcExamenesDto.class, pNumero); 
		System.out.println();
		ccExamenesDto.setEstatus(pCcExamenesDto.getEstatus());
		ccExamenesDto.setTitulo(pCcExamenesDto.getTitulo());
		ccExamenesDto.setNombre(pCcExamenesDto.getNombre());
		ccExamenesDto.setDescripcion(pCcExamenesDto.getDescripcion());
		ccExamenesDto.setTipoPregunta(pCcExamenesDto.getTipoPregunta());
		ccExamenesDto.setTipoExamen(pCcExamenesDto.getTipoExamen());
		ccExamenesDto.setTema(pCcExamenesDto.getTema());
		ccExamenesDto.setComentarios(pCcExamenesDto.getComentarios());
		ccExamenesDto.setVisibilidad(pCcExamenesDto.getVisibilidad());
		ccExamenesDto.setFechaEfectivaDesde(pCcExamenesDto.getFechaEfectivaDesde());
		ccExamenesDto.setFechaEfectivaHasta(pCcExamenesDto.getFechaEfectivaHasta());
		ccExamenesDto.setTiempoLimite(pCcExamenesDto.getTiempoLimite());
		ccExamenesDto.setSaltarPreguntas(pCcExamenesDto.getSaltarPreguntas());
		ccExamenesDto.setSaltarCasos(pCcExamenesDto.getSaltarCasos());
		ccExamenesDto.setMostrarRespuestas(pCcExamenesDto.getMostrarRespuestas());
		ccExamenesDto.setTienePassmark(pCcExamenesDto.getTienePassmark());
		ccExamenesDto.setAleatorioGrupo(pCcExamenesDto.getAleatorioGrupo());
		ccExamenesDto.setAleatorioPreguntas(pCcExamenesDto.getAleatorioPreguntas());
		ccExamenesDto.setSeleccionCasosAleatorios(pCcExamenesDto.getSeleccionCasosAleatorios());
		ccExamenesDto.setMensajeFinalizacion(pCcExamenesDto.getMensajeFinalizacion());
		ccExamenesDto.setConfirmacionAsistencia(pCcExamenesDto.getConfirmacionAsistencia());
		ccExamenesDto.setDiploma(pCcExamenesDto.getDiploma());
		
	}

}
