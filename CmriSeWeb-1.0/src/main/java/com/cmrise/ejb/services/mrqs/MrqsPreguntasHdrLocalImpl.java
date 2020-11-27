package com.cmrise.ejb.services.mrqs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.mrqs.MrqsPreguntasHdrV1;
import com.cmrise.jpa.dao.mrqs.MrqsListasPalabrasDao;
import com.cmrise.jpa.dao.mrqs.MrqsOpcionMultipleDao;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasFtaDao;
import com.cmrise.jpa.dao.mrqs.MrqsPreguntasHdrDao;
import com.cmrise.jpa.dao.mrqs.img.MrqsImagenesGrpDao;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrDto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV1Dto;
import com.cmrise.jpa.dto.mrqs.MrqsPreguntasHdrV2Dto;
import com.cmrise.utils.Utilitarios;

@Stateless 
public class MrqsPreguntasHdrLocalImpl implements MrqsPreguntasHdrLocal {

	@Inject
	MrqsPreguntasHdrDao mrqsPreguntasHdrDao; 
	
	@Inject
	MrqsPreguntasFtaDao mrqsPreguntasFtaDao; 
	
	@Inject 
	MrqsOpcionMultipleDao mrqsOpcionMultipleDao; 
	
	@Inject 
	MrqsListasPalabrasDao mrqsListasPalabrasDao; 
	
	@Inject 
	MrqsImagenesGrpDao mrqsImagenesGrpDao; 
	
	@Override
	public void insert(MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		mrqsPreguntasHdrDao.insert(pMrqsPreguntasHdrDto);
	}

	@Override
	public void delete(long pNumero) {
		mrqsPreguntasHdrDao.delete(pNumero);
	}

	@Override
	public void update(long pNumero, MrqsPreguntasHdrDto pMrqsPreguntasHdrDto) {
		mrqsPreguntasHdrDao.update(pNumero, pMrqsPreguntasHdrDto);
	}

	@Override
	public List<MrqsPreguntasHdrV1Dto> findAll() {
		return mrqsPreguntasHdrDao.findAll();
	}

	@Override
	public MrqsPreguntasHdrV1Dto findByNumero(long pNumero) {
		return mrqsPreguntasHdrDao.findByNumero(pNumero);
	}

	@Override
	public MrqsPreguntasHdrDto copyPaste(long pNumero) {
		return mrqsPreguntasHdrDao.copyPaste(pNumero);
	}
	
	@Override
	public List<MrqsPreguntasHdrV1Dto> findByTituloPregunta(String pTituloPregunta) {
		return mrqsPreguntasHdrDao.findByTituloPregunta(pTituloPregunta);
	}
	@Override
	public MrqsPreguntasHdrV2Dto findV2ByNumeroHdr(long pNumeroHdr) {
		return mrqsPreguntasHdrDao.findV2ByNumeroHdr(pNumeroHdr);
	}

	@Override
	public List<MrqsPreguntasHdrV1> findWithFilterExam(long pNumeroExamen) {
		List<MrqsPreguntasHdrV1> retval = new ArrayList<MrqsPreguntasHdrV1>();
		List<Object> listObjects = mrqsPreguntasHdrDao.findWithFilterExam(pNumeroExamen); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = objectToModel(object); 
				retval.add(mrqsPreguntasHdrV1);
			}
		}	
		return retval;
	}

	@Override
	public long insert(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1) {
		MrqsPreguntasHdrDto mrqsPreguntasHdrDto = new MrqsPreguntasHdrDto();
		mrqsPreguntasHdrDto.setAdmonExamen(pMrqsPreguntasHdrV1.getAdmonExamen());
		mrqsPreguntasHdrDto.setAdmonMateria(pMrqsPreguntasHdrV1.getAdmonMateria());
		mrqsPreguntasHdrDto.setAdmonSubmateria(pMrqsPreguntasHdrV1.getAdmonSubmateria());
		mrqsPreguntasHdrDto.setTipoPregunta(pMrqsPreguntasHdrV1.getTipoPregunta());
		mrqsPreguntasHdrDto.setDiagnostico(pMrqsPreguntasHdrV1.getDiagnostico());
		mrqsPreguntasHdrDto.setNotas(pMrqsPreguntasHdrV1.getNotas());
		mrqsPreguntasHdrDto.setFechaElaboracion(Utilitarios.utilDateToSqlDate(pMrqsPreguntasHdrV1.getFechaElaboracion()));
		mrqsPreguntasHdrDto.setBibliografia(pMrqsPreguntasHdrV1.getBibliografia());
		mrqsPreguntasHdrDto.setFechaEfectivaDesde(Utilitarios.startOfTime);
		mrqsPreguntasHdrDto.setFechaEfectivaHasta(Utilitarios.endOfTime);
		mrqsPreguntasHdrDto.setEstatus(pMrqsPreguntasHdrV1.getEstatus());
		mrqsPreguntasHdrDto.setSociedad(Utilitarios.SOCIEDAD);
		
		mrqsPreguntasHdrDto.setCreadoPor(pMrqsPreguntasHdrV1.getCreadoPor());
		mrqsPreguntasHdrDto.setActualizadoPor(pMrqsPreguntasHdrV1.getActualizadoPor());
		mrqsPreguntasHdrDto.setFechaCreacion(Utilitarios.utilDateToTimestamp(pMrqsPreguntasHdrV1.getFechaCreacion()));
		mrqsPreguntasHdrDto.setFechaActualizacion(Utilitarios.utilDateToTimestamp(pMrqsPreguntasHdrV1.getFechaActualizacion()));
		
		long numeroPreguntaHdr = mrqsPreguntasHdrDao.insert(mrqsPreguntasHdrDto); 
		pMrqsPreguntasHdrV1.setNumero(mrqsPreguntasHdrDto.getNumero());
		return mrqsPreguntasHdrDto.getNumero();
	}

	@Override
	public String delete(MrqsPreguntasHdrV1 pMrqsPreguntasHdrV1) {
		String retval = null; 
		 System.out.println("pMrqsPreguntasHdrV1.getNumero():"+pMrqsPreguntasHdrV1.getNumero());
	   	long countRecMGL = mrqsPreguntasHdrDao.countRecMGL(pMrqsPreguntasHdrV1.getNumero()); 
	    System.out.println("countRecMGL:"+countRecMGL);
	   	if(countRecMGL!=0) {
	   		pMrqsPreguntasHdrV1.setDependent(true);
	   		retval = "No se pueden borrar preguntas que se encuentran ligadas a examenes, porque se perderia el Historial"; 
	   		return retval; 
	   	}else {
	   		pMrqsPreguntasHdrV1.setDependent(false);
	   		long numeroFta = mrqsPreguntasFtaDao.findNumeroFtaByNumeroHdr(pMrqsPreguntasHdrV1.getNumero()); 
	   		if(0!=numeroFta) {
	   			mrqsOpcionMultipleDao.deleteByNumeroFta(numeroFta);
	   			mrqsListasPalabrasDao.deleteByNumeroFta(numeroFta); 
	   			mrqsImagenesGrpDao.deleteByNumeroFta(numeroFta); 
	   			mrqsPreguntasFtaDao.delete(numeroFta);
	   		}
	   		mrqsPreguntasHdrDao.delete(pMrqsPreguntasHdrV1.getNumero());
	   		retval ="Los datos se borraron correctamente"; 
	   	}
	   	return retval;
	}

	private MrqsPreguntasHdrV1 objectToModel(Object pObject) {
		MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = new MrqsPreguntasHdrV1(); 
		Object[] row = (Object[]) pObject;
		if(row[0] instanceof BigInteger) { /** [NUMERO] **/
			mrqsPreguntasHdrV1.setNumero(((BigInteger)row[0]).longValue());
		}
		if(row[1] instanceof String) { /**[TIPO_PREGUNTA]**/
			mrqsPreguntasHdrV1.setTipoPregunta((String)row[1]);
		}
		if(row[2] instanceof String) { /**[TIPO_PREGUNTA_DESC]**/
			mrqsPreguntasHdrV1.setTipoPreguntaDesc((String)row[2]);
		}
		if(row[3] instanceof String) { /**[DIAGNOSTICO]**/
			mrqsPreguntasHdrV1.setDiagnostico((String)row[3]);
		}
		if(row[4] instanceof String) { /**[NOTAS]**/
			mrqsPreguntasHdrV1.setNotas((String)row[4]);
		}
		if(row[5] instanceof String) { /**[ESTATUS]**/
			mrqsPreguntasHdrV1.setEstatus((String)row[5]);
		}
		if(row[6] instanceof String) { /**[ESTATUS_DESC]**/
			mrqsPreguntasHdrV1.setEstatusDesc((String)row[6]);
		}
		if(row[14] instanceof String) { /**[ADMON_MATERIA_DESC]**/
			mrqsPreguntasHdrV1.setAdmonMateriaDesc((String)row[14]);
		}
		if(row[15] instanceof String) { /**[ADMON_SUBMATERIA_DESC]**/
			mrqsPreguntasHdrV1.setAdmonSubmateriaDesc((String)row[15]);
		}
		if(row[16] instanceof java.sql.Date) { /**[FECHA_ELABORACION]**/
			mrqsPreguntasHdrV1.setFechaElaboracion(Utilitarios.sqlDateToUtilDate((java.sql.Date)row[16]));
		}
		if(row[17] instanceof String) { /**[ELABORADOR]**/
			mrqsPreguntasHdrV1.setElaborador((String)row[17]);
		}
		return mrqsPreguntasHdrV1; 
	}
	
	@Override
	public List<MrqsPreguntasHdrV1> findWithFilterExam(long pNumeroMrqsExamen, long pAdmonExamen, long pAdmonMateria) {
		List<MrqsPreguntasHdrV1> retval = new ArrayList<MrqsPreguntasHdrV1>();
		List<Object> listObjects = mrqsPreguntasHdrDao.findWithFilterExam(pNumeroMrqsExamen
				                                                         ,pAdmonExamen
				                                                         ,pAdmonMateria
				                                                         ); 
		for(Object object:listObjects) {
			if(object instanceof Object[]) {
				MrqsPreguntasHdrV1 mrqsPreguntasHdrV1 = objectToModel(object); 
				retval.add(mrqsPreguntasHdrV1);
			}
		}	
		return retval;
	}

	@Override
	public MrqsPreguntasHdrV1 findV1ByNumero(long pNumeroHdr) {
		MrqsPreguntasHdrV1 retval = new MrqsPreguntasHdrV1(); 
		MrqsPreguntasHdrV1Dto mrqsPreguntasHdrV1Dto = mrqsPreguntasHdrDao.findByNumero(pNumeroHdr);
		//mrqsPreguntasHdrV1Dto.setTipoPregunta("0");
		 retval.setNumero(mrqsPreguntasHdrV1Dto.getNumero());
		 retval.setEstatus(mrqsPreguntasHdrV1Dto.getEstatus());
		 retval.setAdmonExamen(mrqsPreguntasHdrV1Dto.getAdmonExamen());
		 retval.setAdmonMateria(mrqsPreguntasHdrV1Dto.getAdmonMateria());
		 retval.setAdmonSubmateria(mrqsPreguntasHdrV1Dto.getAdmonSubmateria());
		 retval.setTipoPregunta(mrqsPreguntasHdrV1Dto.getTipoPregunta());
		 retval.setDiagnostico(mrqsPreguntasHdrV1Dto.getDiagnostico());
		 retval.setNotas(mrqsPreguntasHdrV1Dto.getNotas());
		 retval.setFechaElaboracion(Utilitarios.sqlDateToUtilDate(mrqsPreguntasHdrV1Dto.getFechaElaboracion()));
		 retval.setBibliografia(mrqsPreguntasHdrV1Dto.getBibliografia());
		return retval;
	}

}
