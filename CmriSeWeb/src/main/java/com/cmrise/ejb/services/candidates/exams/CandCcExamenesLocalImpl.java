package com.cmrise.ejb.services.candidates.exams;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandCcExamenesV1;
import com.cmrise.ejb.model.exams.CcExamAsignaciones;
import com.cmrise.jpa.dao.candidates.exams.CandCcExamenesDao;
import com.cmrise.jpa.dto.candidates.exams.CandCcExamenesDto;

@Stateless 
public class CandCcExamenesLocalImpl implements CandCcExamenesLocal {

	@Inject 
	CandCcExamenesDao candCcExamenesDao; 
	
	@Override
	public long insert(CandCcExamenesDto pCandCcExamenesDto) {
		return candCcExamenesDao.insert(pCandCcExamenesDto);
	}

	@Override
	public List<CandCcExamenesV1> findCandCcExamInfoByNumero(long pNumeroCandCcExamen) {
		List<CandCcExamenesV1> retval = new ArrayList<CandCcExamenesV1>(); 
		List<Object> listObjects = candCcExamenesDao.findCandCcExamInfoByNumero(pNumeroCandCcExamen); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				CandCcExamenesV1 candCcExamenesV1 = new CandCcExamenesV1(); 
				Object[] row = (Object[]) object;
				if(row[0] instanceof BigInteger) { /** NUMERO **/
					candCcExamenesV1.setNumero(((BigInteger)row[0]).longValue());
				}
				if(row[1] instanceof BigInteger) { /** NUMERO_CC_EXAMEN **/
					candCcExamenesV1.setNumeroCcExamen(((BigInteger)row[1]).longValue());
				}
				if(row[2] instanceof BigInteger) { /** NUMERO_CCPH **/
					candCcExamenesV1.setNumeroCcph(((BigInteger)row[2]).longValue());
				}
				if(row[3] instanceof BigInteger) { /** NUMERO_CPH **/
					candCcExamenesV1.setNumeroCph(((BigInteger)row[3]).longValue());
				}
				if(row[4] instanceof BigInteger) { /** NUMERO_CCPF **/
					candCcExamenesV1.setNumeroCcpf(((BigInteger)row[4]).longValue());
				}
				if(row[5] instanceof BigInteger) { /** NUMERO_CPF **/
					candCcExamenesV1.setNumeroCpf(((BigInteger)row[5]).longValue());
				}
				if(row[6] instanceof BigInteger) { /** NUMERO_CANDIDATO **/
					candCcExamenesV1.setNumeroCandidato(((BigInteger)row[6]).longValue());
				}
				if(row[7] instanceof String) { /** ESTATUS **/
					candCcExamenesV1.setEstatus((String)row[7]);
				}
				if(row[8] instanceof Timestamp) {   /** CCE.[FECHA_EFECTIVA_DESDE] **/
					Timestamp timestamp = (Timestamp)row[8]; 
					candCcExamenesV1.setFechaEfectivaDesde(new java.util.Date(timestamp.getTime()));
				}
				if(row[9] instanceof Timestamp) { /** CCE.[FECHA_EFECTIVA_HASTA] **/
					Timestamp timestamp = (Timestamp)row[9]; 
					candCcExamenesV1.setFechaEfectivaHasta(new java.util.Date(timestamp.getTime()));
				}
				if(row[10] instanceof String) { /** CPF.TITULO_PREGUNTA **/
					candCcExamenesV1.setTituloPregunta((String)row[10]);
				}
				if(row[11] instanceof String) { /** CE.NOMBRE NOMBRE_EXAMEN **/
					candCcExamenesV1.setNombreExamen((String)row[11]);
				}
				retval.add(candCcExamenesV1); 
			}
		}	
		return retval;
	}

	@Override
	public long findNumeroCandCcExamen(long pNumeroCcExamen, long pNumeroCandidato) {
		return candCcExamenesDao.findNumeroCandCcExamen(pNumeroCcExamen, pNumeroCandidato);
	}

	@Override
	public CandCcExamenesV1 findCandCcExamenPreguntaInfo(long pNumeroCandCcExamen, long pNumeroCandCcPreguntaHdr) {
		Object object = candCcExamenesDao.findCandCcExamenPreguntaInfo(pNumeroCandCcExamen, pNumeroCandCcPreguntaHdr);
		CandCcExamenesV1 candCcExamenesV1 = new CandCcExamenesV1(); 
		if(object instanceof Object[]) {
			Object[] row = (Object[]) object;
			if(row[0] instanceof BigInteger) { /** NUMERO **/
				candCcExamenesV1.setNumero(((BigInteger)row[0]).longValue());
			}
			if(row[1] instanceof BigInteger) { /** NUMERO_CC_EXAMEN **/
				candCcExamenesV1.setNumeroCcExamen(((BigInteger)row[1]).longValue());
			}
			if(row[2] instanceof BigInteger) { /** NUMERO_CCPH **/
				candCcExamenesV1.setNumeroCcph(((BigInteger)row[2]).longValue());
			}
			if(row[3] instanceof BigInteger) { /** NUMERO_CPH **/
				candCcExamenesV1.setNumeroCph(((BigInteger)row[3]).longValue());
			}
			if(row[4] instanceof BigInteger) { /** NUMERO_CCPF **/
				candCcExamenesV1.setNumeroCcpf(((BigInteger)row[4]).longValue());
			}
			if(row[5] instanceof BigInteger) { /** NUMERO_CPF **/
				candCcExamenesV1.setNumeroCpf(((BigInteger)row[5]).longValue());
			}
			if(row[6] instanceof BigInteger) { /** NUMERO_CANDIDATO **/
				candCcExamenesV1.setNumeroCandidato(((BigInteger)row[6]).longValue());
			}
			if(row[7] instanceof String) { /** ESTATUS **/
				candCcExamenesV1.setEstatus((String)row[7]);
			}
			if(row[8] instanceof Timestamp) {   /** CCE.[FECHA_EFECTIVA_DESDE] **/
				Timestamp timestamp = (Timestamp)row[8]; 
				candCcExamenesV1.setFechaEfectivaDesde(new java.util.Date(timestamp.getTime()));
			}
			if(row[9] instanceof Timestamp) { /** CCE.[FECHA_EFECTIVA_HASTA] **/
				Timestamp timestamp = (Timestamp)row[9]; 
				candCcExamenesV1.setFechaEfectivaHasta(new java.util.Date(timestamp.getTime()));
			}
			if(row[10] instanceof String) { /** CPF.TITULO_PREGUNTA **/
				candCcExamenesV1.setTituloPregunta((String)row[10]);
			}
			if(row[11] instanceof String) { /** CE.NOMBRE NOMBRE_EXAMEN **/
				candCcExamenesV1.setNombreExamen((String)row[11]);
			}
		}
		return candCcExamenesV1;
	}

}
