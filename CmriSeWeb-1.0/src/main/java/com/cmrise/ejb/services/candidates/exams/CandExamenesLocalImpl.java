package com.cmrise.ejb.services.candidates.exams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.cmrise.ejb.model.candidates.exams.CandExamenesV1;
import com.cmrise.ejb.model.candidates.exams.CandExamenesV2;
import com.cmrise.jpa.dao.candidates.exams.CandExamenesDao;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesDto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV1Dto;
import com.cmrise.jpa.dto.candidates.exams.CandExamenesV2Dto;

@Stateless 
public class CandExamenesLocalImpl implements CandExamenesLocal {

	@Inject 
	CandExamenesDao  candExamenesDao; 
	
	@Override
	public long insert(CandExamenesDto pCandExamenesDto) {
		return candExamenesDao.insert(pCandExamenesDto);
	}

	@Override
	public List<CandExamenesV1> findByExamen(long pNumeroExamen, String pTipoExamen) {
		List<CandExamenesV1Dto> listCandExamenesV1Dto = candExamenesDao.findByExamen(pNumeroExamen, pTipoExamen); 
		List<CandExamenesV1> retval = new ArrayList<CandExamenesV1>(); 
		for(CandExamenesV1Dto candExamenesV1Dto:listCandExamenesV1Dto) {
			CandExamenesV1 candExamenesV1 = new CandExamenesV1(); 
			candExamenesV1.setNumero(candExamenesV1Dto.getNumero());
			candExamenesV1.setNumeroExamen(candExamenesV1Dto.getNumeroExamen());
			candExamenesV1.setNumeroUsuario(candExamenesV1Dto.getNumeroUsuario());
			candExamenesV1.setTipo(candExamenesV1Dto.getTipo());
			candExamenesV1.setMatricula(candExamenesV1Dto.getMatricula());
			candExamenesV1.setNombreUsuario(candExamenesV1Dto.getNombreUsuario());
			candExamenesV1.setApellidoPaterno(candExamenesV1Dto.getApellidoPaterno());
			candExamenesV1.setApellidoMaterno(candExamenesV1Dto.getApellidoMaterno());
			candExamenesV1.setDescripcionRol(candExamenesV1Dto.getDescripcionRol());
			candExamenesV1.setNombreCompletoUsuario(candExamenesV1Dto.getNombreCompletoUsuario());
			candExamenesV1.setCurp(candExamenesV1Dto.getCurp());
			candExamenesV1.setEstatus(candExamenesV1Dto.getEstatus());
			candExamenesV1.setTotalPuntuacion(candExamenesV1Dto.getTotalPuntuacion());
			candExamenesV1.setActualizadoPor(candExamenesV1Dto.getActualizadoPor());
			
			if(candExamenesV1Dto.getNombreActPor() != null)
				candExamenesV1.setNombreActPor(candExamenesV1Dto.getNombreActPor());
			else
				candExamenesV1.setNombreActPor("No disponible");
			
			candExamenesV1.setFechaActualizacion(candExamenesV1Dto.getFechaActualizacion());
			candExamenesV1.setFechaActString();
			retval.add(candExamenesV1); 
		}
		return retval;
	}

	@Override
	public void delete(long pNumero) {
		candExamenesDao.delete(pNumero);
	}

	@Override
	public List<CandExamenesV2> findByUsuario(long pNumeroUsuario) {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByUsuario(pNumeroUsuario); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
		}
		return retval;
	}
	
	@Override
	public List<CandExamenesV2> findByUsuarioOnlyEfectiveDates(long pNumeroUsuario) {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByUsuarioOnlyEfectiveDates(pNumeroUsuario); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
		}
		return retval;
	}

	@Override
	public List<CandExamenesV2> findAll() {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findAll(); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
		}
		return retval;
	}

	public CandExamenesV2 dtoV2ToObjMod(CandExamenesV2Dto pCandExamenesV2Dto) {
		CandExamenesV2 retval = new CandExamenesV2(); 
		retval.setNumero(pCandExamenesV2Dto.getNumero());
		retval.setNumeroExamen(pCandExamenesV2Dto.getNumeroExamen());
		retval.setNumeroUsuario(pCandExamenesV2Dto.getNumeroUsuario());
		retval.setMatricula(pCandExamenesV2Dto.getMatricula());
		retval.setNombreCompletoUsuario(pCandExamenesV2Dto.getNombreCompletoUsuario());
		retval.setDescripcionRol(pCandExamenesV2Dto.getDescripcionRol());
		retval.setTipo(pCandExamenesV2Dto.getTipo());
		retval.setTitulo(pCandExamenesV2Dto.getTitulo());
		retval.setTiempoLimite(pCandExamenesV2Dto.getTiempoLimite());
		retval.setEstatus(pCandExamenesV2Dto.getEstatus());
		retval.setCurp(pCandExamenesV2Dto.getCurp());
		retval.setFechaEfectivaDesdeExamen(new java.util.Date(pCandExamenesV2Dto.getFechaEfectivaDesdeExamen().getTime()));
		retval.setFechaEfectivaHastaExamen(new java.util.Date(pCandExamenesV2Dto.getFechaEfectivaHastaExamen().getTime()));
		return retval; 
	}

	@Override
	public CandExamenesV1 findByNumero(long pNumero) {
		CandExamenesV1 retval = new CandExamenesV1(); 
		CandExamenesV1Dto candExamenesV1Dto = candExamenesDao.findByNumero(pNumero); 
		retval.setNumero(candExamenesV1Dto.getNumero());
		retval.setNumeroExamen(candExamenesV1Dto.getNumeroExamen());
		retval.setTipo(candExamenesV1Dto.getTipo());
		return retval;
	}

	@Override
	public List<CandExamenesV2> findByCURP(String pCurp,String pNombreUsuario, String pApellidoPaterno,String pApellidoMaterno) {
		List<CandExamenesV2> retval = new ArrayList<CandExamenesV2>(); 
		List<CandExamenesV2Dto> listCandExamenesV2Dto = candExamenesDao.findByCURP(pCurp,pNombreUsuario,pApellidoPaterno,pApellidoMaterno); 
		for(CandExamenesV2Dto candExamenesV2Dto:listCandExamenesV2Dto) {
			CandExamenesV2 candExamenesV2 = dtoV2ToObjMod(candExamenesV2Dto);
			retval.add(candExamenesV2); 
		}
		return retval;
	}
	
	@Override
	public void updateEstatus(long pNumero, CandExamenesDto pCandExamenesDto) {
		 candExamenesDao.updateEstatus(pNumero, pCandExamenesDto);
	}
	
	@Override
	public List<CandExamenesV1> findCandidateByExam(String cCurp, String cNombre, String c_aPaterno, String c_aMaterno, String actPor, String fechaActu, long pNumeroExamen, String pTipoExamen) {
		List<CandExamenesV1Dto> listCandExamenesV1Dto = candExamenesDao.findCandidateByExam(cCurp, cNombre, c_aPaterno, c_aMaterno, actPor, fechaActu, pNumeroExamen, pTipoExamen); 
		List<CandExamenesV1> retval = new ArrayList<CandExamenesV1>(); 
		
		for(CandExamenesV1Dto candExamenesV1Dto:listCandExamenesV1Dto) {
			CandExamenesV1 candExamenesV1 = new CandExamenesV1(); 
			candExamenesV1.setNumero(candExamenesV1Dto.getNumero());
			candExamenesV1.setNumeroExamen(candExamenesV1Dto.getNumeroExamen());
			candExamenesV1.setNumeroUsuario(candExamenesV1Dto.getNumeroUsuario());
			candExamenesV1.setTipo(candExamenesV1Dto.getTipo());
			candExamenesV1.setMatricula(candExamenesV1Dto.getMatricula());
			candExamenesV1.setNombreUsuario(candExamenesV1Dto.getNombreUsuario());
			candExamenesV1.setApellidoPaterno(candExamenesV1Dto.getApellidoPaterno());
			candExamenesV1.setApellidoMaterno(candExamenesV1Dto.getApellidoMaterno());
			candExamenesV1.setDescripcionRol(candExamenesV1Dto.getDescripcionRol());
			candExamenesV1.setNombreCompletoUsuario(candExamenesV1Dto.getNombreCompletoUsuario());
			candExamenesV1.setCurp(candExamenesV1Dto.getCurp());
			candExamenesV1.setEstatus(candExamenesV1Dto.getEstatus());
			candExamenesV1.setTotalPuntuacion(candExamenesV1Dto.getTotalPuntuacion());
			candExamenesV1.setActualizadoPor(candExamenesV1Dto.getActualizadoPor());
			
			if(candExamenesV1Dto.getNombreActPor() != null)
				candExamenesV1.setNombreActPor(candExamenesV1Dto.getNombreActPor());
			else
				candExamenesV1.setNombreActPor("No disponible");
			
			candExamenesV1.setFechaActualizacion(candExamenesV1Dto.getFechaActualizacion());
			candExamenesV1.setFechaActString();
			
			retval.add(candExamenesV1); 
		}
		return retval;
	}
	
}
