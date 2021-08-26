package com.cmrise.jpa.jdbc.exams;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.cmrise.ejb.model.exams.CcExamenes;
import com.cmrise.jpa.dao.exams.CcExamenesDao;
import com.cmrise.jpa.dto.exams.CcExamenesDto;
import com.cmrise.jpa.dto.exams.CcExamenesV1Dto;
import com.cmrise.jpa.dto.exams.MrqsExamenesV1Dto;
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
		ccExamenesDto.setDescripcion(pCcExamenesDto.getDescripcion());
		ccExamenesDto.setIdTipoExamen(pCcExamenesDto.getIdTipoExamen());
		ccExamenesDto.setActualizadoPor(pCcExamenesDto.getActualizadoPor());
		ccExamenesDto.setVisibilidad(pCcExamenesDto.getVisibilidad());
		ccExamenesDto.setFechaEfectivaDesde(pCcExamenesDto.getFechaEfectivaDesde());
		ccExamenesDto.setFechaEfectivaHasta(pCcExamenesDto.getFechaEfectivaHasta());
		pCcExamenesDto.setFechaActualizacion(pCcExamenesDto.getFechaActualizacion());
		ccExamenesDto.setTiempoLimite(pCcExamenesDto.getTiempoLimite());
		ccExamenesDto.setSaltarCasos(pCcExamenesDto.getSaltarCasos());
		ccExamenesDto.setMostrarRespuestas(pCcExamenesDto.getMostrarRespuestas());
		ccExamenesDto.setMensajeFinalizacion(pCcExamenesDto.getMensajeFinalizacion());
		
	}

	public List<CcExamenes> findByNumeroWD(long pNumero, long pNCandidato) {
		List<CcExamenes> ListCcExam = new ArrayList<CcExamenes>();
		String strQuery = "EXEC CC_EXAMENE_FORCAND "+pNumero+", "+pNCandidato;		
		Query query = em.createNativeQuery(strQuery); 
		List<Object> listObject=query.getResultList();
		Iterator<Object> iterObject = listObject.iterator();
		while(iterObject.hasNext()) {
	    	Object result = iterObject.next();
	    	CcExamenes ccExam = new CcExamenes();
	    	if(result instanceof Object[]) {
				Object[] row = (Object[]) result;
				
				if(row[0] instanceof BigInteger){
					ccExam.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof String) {
					ccExam.setDescripcion((String)row[1]);
				}
				if(row[2] instanceof Short) {
					ccExam.setTiempoLimite(((Short)row[2]).shortValue());
				}
				if(row[3] instanceof Boolean) {
					boolean s_casos = (boolean)row[3];
					ccExam.setSaltarCasos(s_casos);
				}
				if(row[4] instanceof Boolean) {
					ccExam.setMostrarRespuestas((boolean)row[4]);
				}
				if(row[5] instanceof String) {
					ccExam.setMensajeFinalizacion((String)row[5]);
				}
				if(row[7] instanceof String) {
					ccExam.setTipoExamen((String)row[7]);
				}
				if(row[8] instanceof String) {
					ccExam.setTipoExamenDesc((String)row[8]);
				}
				if(row[9] instanceof String) {
					ccExam.setVisibilidad((String)row[9]);
				}
				if(row[10] instanceof String) {
					ccExam.setVisibilidadDesc((String)row[10]);
				}
				if(row[11] instanceof String) {
					ccExam.setEstatus((String)row[11]);
				}
				if(row[12] instanceof String) {
					ccExam.setEstatusDesc((String)row[12]);
				}
				if(row[13] instanceof String) {
					ccExam.setSociedad((String)row[13]);
				}
				if(row[14] instanceof Timestamp) {
					Timestamp timestamp = (Timestamp)row[14];
					ccExam.setFechaEfectivaDesde(new java.util.Date(timestamp.getTime()));
				}
				if(row[15] instanceof Timestamp) {
					Timestamp timestamp = (Timestamp)row[15];
					ccExam.setFechaEfectivaHasta(new java.util.Date(timestamp.getTime()));
				}
				if(row[16] instanceof Timestamp) {
					Timestamp timestamp = (Timestamp)row[16];
					ccExam.setFechaCreacion(new java.util.Date(timestamp.getTime()));
					ccExam.setFechaCreacionString();
				}
				
	    	ListCcExam.add(ccExam);
		    }
	    }
	    return ListCcExam;
	}
	
}
